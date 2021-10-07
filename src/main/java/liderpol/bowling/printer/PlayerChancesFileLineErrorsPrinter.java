package liderpol.bowling.printer;

import liderpol.bowling.common.Constants;
import liderpol.bowling.dto.PlayerChancesFileLineError;

import java.util.List;

public class PlayerChancesFileLineErrorsPrinter implements IPlayerChancesFileLineErrorsPrinter {
    @Override
    public void printErrors(List<PlayerChancesFileLineError> errors) {
        System.out.println(Constants.PCFLEP_PRINT__ERRORS_TITLE);
        errors.forEach(playerChancesFileLineError -> {
            System.out.println("\n" + Constants.PCFLEP_PRINT__ERRORS_LINE_NUMBER_LABEL + playerChancesFileLineError.getLineNumber());
            System.out.println(Constants.PCFLEP_PRINT__ERRORS_LINE_LABEL + playerChancesFileLineError.getLine());
            System.out.println(Constants.PCFLEP_PRINT__ERRORS_LINE_ERROR_LABEL + playerChancesFileLineError.getErrorMessage());
        });
    }
}