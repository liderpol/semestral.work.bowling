package liderpol.bowling.printer;

import liderpol.bowling.dto.PlayerChancesFileLineError;

import java.util.List;

public interface IPlayerChancesFileLineErrorsPrinter {
    void printErrors(List<PlayerChancesFileLineError> errors);
}