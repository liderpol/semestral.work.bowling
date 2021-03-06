package liderpol.bowling.processor;

import liderpol.bowling.dto.FrameDTO;
import liderpol.bowling.dto.GameDTO;
import liderpol.bowling.dto.PinFallDTO;
import liderpol.bowling.dto.PlayerChanceDTO;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PinFallsProcessor implements IPinFallsProcessor {
    private static final Logger LOGGER = Logger.getLogger(PinFallsProcessor.class.getName());

    @Override
    public void processPinFalls(Map<String, GameDTO> games) {
        games.forEach((gameOwner, gameDTO) -> {
            PlayerChanceDTO playerChanceDTO;
            for (int i = 0; i < gameDTO.getPlayerChanceDTOs().size(); i++) {
                playerChanceDTO = gameDTO.getPlayerChanceDTOs().get(i);

                if (gameDTO.getFrameDTOS().size() == 0) {
                    //Add first frame to the game
                    this.addNewFrameToGame(gameDTO, null);
                }

                int currentFrameIndex = gameDTO.getFrameDTOS().size() - 1;
                FrameDTO currentFrameDTO = gameDTO.getFrameDTOS().get(currentFrameIndex);
                int currentPinFallIndex = currentFrameDTO.getPinFalls().size();

                if (currentFrameIndex == 9) {
                    //10th frame (last)
                    if (currentPinFallIndex == 0) {
                        //First chance
                        currentFrameDTO.getPinFalls().add(new PinFallDTO(playerChanceDTO.isFoul(), playerChanceDTO.getKnockedDownPins()));
                    } else if (currentPinFallIndex == 1
                            &&
                            ((currentFrameDTO.getPinFalls().get(0).getValue() == 10)
                                    || (currentFrameDTO.getPinFalls().get(0).getValue() + playerChanceDTO.getKnockedDownPins()) <= 10)) {
                        //Second chance
                        currentFrameDTO.getPinFalls().add(new PinFallDTO(playerChanceDTO.isFoul(), playerChanceDTO.getKnockedDownPins()));
                    } else if (currentPinFallIndex == 2
                            &&
                            ((currentFrameDTO.getPinFalls().get(0).getValue() == 10)
                                    || (currentFrameDTO.getPinFalls().get(0).getValue() + currentFrameDTO.getPinFalls().get(1).getValue()) == 10)) {
                        //Third (extra) chance only allowed when there is a strike on the first chance, or a spare in the first two chances
                        currentFrameDTO.getPinFalls().add(new PinFallDTO(playerChanceDTO.isFoul(), playerChanceDTO.getKnockedDownPins()));
                    } else {
                        gameDTO.setValid(false);
                        if (currentPinFallIndex == 1) {
                            //This is an error, because if this is the second chance, pin fall sum must be NOT greater than 10
                            String message = "Game for player: " + gameDTO.getPlayerName() + ", invalidated. Frame " + (currentFrameIndex + 1) + ": exceeds pin falls max sum of 10";
                            System.out.println(message);
                            LOGGER.log(Level.SEVERE, message);
                        } else {
                            //Third (extra) chance only allowed when there is a strike nn the first chance, or a spare in the first two chances
                            String message = "Game for player: " + gameDTO.getPlayerName() + ", invalidated. Max chances exceeded";
                            System.out.println(message);
                            LOGGER.log(Level.SEVERE, message);
                        }
                        break;
                    }
                } else {
                    //Frames 1 to 9
                    if (playerChanceDTO.getKnockedDownPins() == 10) {
                        //Strike or spare chance
                        if (currentPinFallIndex == 0
                                || (currentPinFallIndex == 1 && currentFrameDTO.getPinFalls().get(0).getValue() == 0)) {
                            //First chance. If second chance, first one must have knocked down pins equal to zero to be a spare
                            currentFrameDTO.getPinFalls().add(new PinFallDTO(playerChanceDTO.isFoul(), playerChanceDTO.getKnockedDownPins()));
                            this.addNewFrameToGame(gameDTO, null);
                        } else if (currentPinFallIndex == 1 && currentFrameDTO.getPinFalls().get(0).getValue() == 10) {
                            //Second chance, chance data is meant for the next frame, because the first chance was a strike
                            this.addNewFrameToGame(gameDTO, new PinFallDTO(playerChanceDTO.isFoul(), playerChanceDTO.getKnockedDownPins()));
                        } else {
                            //This is an error, because if this is the second chance, pin fall sum must be NOT greater than 10
                            gameDTO.setValid(false);
                            String message = "Game for player: " + gameDTO.getPlayerName() + ", invalidated. Frame " + (currentFrameIndex + 1) + ": exceeds pin falls max sum of 10";
                            System.out.println(message);
                            LOGGER.log(Level.SEVERE, message);
                            break;
                        }
                    } else {
                        if (currentPinFallIndex == 0) {
                            //First chance.
                            currentFrameDTO.getPinFalls().add(new PinFallDTO(playerChanceDTO.isFoul(), playerChanceDTO.getKnockedDownPins()));
                        } else if (currentPinFallIndex == 1
                                && (currentFrameDTO.getPinFalls().get(0).getValue() + playerChanceDTO.getKnockedDownPins()) <= 10) {
                            //Second chance, sum for the first and second must be NOT greater than 10
                            currentFrameDTO.getPinFalls().add(new PinFallDTO(playerChanceDTO.isFoul(), playerChanceDTO.getKnockedDownPins()));
                            this.addNewFrameToGame(gameDTO, null);
                        } else if (currentPinFallIndex == 2) {
                            //The chance data is meant for the next frame
                            this.addNewFrameToGame(gameDTO, new PinFallDTO(playerChanceDTO.isFoul(), playerChanceDTO.getKnockedDownPins()));
                        } else {
                            //This is an error, because if this is the second chance, pin fall sum must be NOT greater than 10
                            gameDTO.setValid(false);
                            String message = "Game for player: " + gameDTO.getPlayerName() + ", invalidated. Frame " + (currentFrameIndex + 1) + ": exceeds pin falls max sum of 10";
                            System.out.println(message);
                            LOGGER.log(Level.SEVERE, message);
                            break;
                        }
                    }
                }
            }

            if (gameDTO.isValid()) {
                //Validate if there was not enough chances data to build all 10 frames completely
                boolean invalidateGame = false;
                if (gameDTO.getFrameDTOS().size() < 10) {
                    invalidateGame = true;
                } else if (gameDTO.getFrameDTOS().size() == 10) {
                    FrameDTO lastFrame = gameDTO.getFrameDTOS().get(9);
                    if (lastFrame.getPinFalls().size() < 2) {
                        invalidateGame = true;
                    } else if (lastFrame.getPinFalls().size() == 2
                            &&
                            ((lastFrame.getPinFalls().get(0).getValue() == 10)
                                    || ((lastFrame.getPinFalls().get(0).getValue() == 10) && (lastFrame.getPinFalls().get(1).getValue() == 10))
                                    || ((lastFrame.getPinFalls().get(0).getValue() + lastFrame.getPinFalls().get(1).getValue()) == 10))) {
                        //Third (extra) chance on last frame is missing
                        invalidateGame = true;
                    }
                }

                if (invalidateGame) {
                    //This is an error, player has not enough chance data on the file
                    gameDTO.setValid(false);
                    String message = "Game for player: " + gameDTO.getPlayerName() + ", invalidated. Not enough chances data";
                    System.out.println(message);
                    LOGGER.log(Level.SEVERE, message);
                }
            }
        });
    }

    private void addNewFrameToGame(GameDTO gameDTO, PinFallDTO pinFallDTO) {
        FrameDTO nextFrameDTO = new FrameDTO();
        if (pinFallDTO != null) {
            nextFrameDTO.getPinFalls().add(pinFallDTO);
        }
        gameDTO.getFrameDTOS().add(nextFrameDTO);
    }
}