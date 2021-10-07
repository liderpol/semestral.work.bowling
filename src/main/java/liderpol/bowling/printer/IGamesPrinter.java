package liderpol.bowling.printer;

import liderpol.bowling.dto.GameDTO;

import java.util.Map;

public interface IGamesPrinter {
    void printGames(Map<String, GameDTO> games);
}