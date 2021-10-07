package liderpol.bowling.processor;

import liderpol.bowling.dto.GameDTO;

import java.util.Map;

public interface IPinFallsProcessor {
    void processPinFalls(Map<String, GameDTO> games);
}