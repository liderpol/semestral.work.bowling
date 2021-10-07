package liderpol.bowling.parser;

import liderpol.bowling.dto.GameDTO;
import liderpol.bowling.dto.PlayerChanceDTO;
import liderpol.bowling.dto.PlayerChancesFileLineError;
import liderpol.bowling.dto.PlayerChancesFileParserResult;
import liderpol.bowling.validator.IPlayerChancesFileLineValidator;
import liderpol.bowling.validator.PlayerChancesFileLineValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlayerChancesFileParser implements IPlayerChancesFileParser {

    @Override
    public PlayerChancesFileParserResult parsePlayerChancesFile(String playerChancesFilePath) throws IOException {
        List<PlayerChancesFileLineError> errors = new ArrayList<>();
        //Use linked hashmap to build player games order from the order of the chances inside the file
        Map<String, GameDTO> games = new LinkedHashMap<String, GameDTO>();

        //Using the old way of reading files just to be able to grab the line number for the errors report
        int currentLineNumber = 1;
        IPlayerChancesFileLineValidator playerChancesFileLineValidator = new PlayerChancesFileLineValidator();
        PlayerChancesFileLineParser playerChancesFileLineParser = new PlayerChancesFileLineParser();

        try (FileReader fr = new FileReader(playerChancesFilePath);
             BufferedReader buff = new BufferedReader(fr)) {
            String playerChancesFileLine = buff.readLine();
            while (playerChancesFileLine != null) {
                PlayerChancesFileLineError error = playerChancesFileLineValidator.validate(playerChancesFileLine, currentLineNumber);
                if (error == null) {
                    PlayerChanceDTO playerChanceDTO = playerChancesFileLineParser.parseLine(playerChancesFileLine);
                    GameDTO gameDTO = games.get(playerChanceDTO.getPlayerName());

                    if (gameDTO == null) {
                        gameDTO = new GameDTO(playerChanceDTO.getPlayerName());
                        games.put(playerChanceDTO.getPlayerName(), gameDTO);
                    }

                    gameDTO.getPlayerChanceDTOs().add(playerChanceDTO);
                } else {
                    //Line is not valid
                    errors.add(error);
                }

                playerChancesFileLine = buff.readLine();
                currentLineNumber++;
            }
        }

        return new PlayerChancesFileParserResult(errors, games);
    }
}