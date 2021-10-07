package liderpol.bowling.processor;

import liderpol.bowling.dto.GameDTO;

import java.util.Map;

public interface IScoresProcessor {
    void processScores(Map<String, GameDTO> games);
}