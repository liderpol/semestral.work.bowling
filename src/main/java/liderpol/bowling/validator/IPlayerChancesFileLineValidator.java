
package liderpol.bowling.validator;

import liderpol.bowling.dto.PlayerChancesFileLineError;

public interface IPlayerChancesFileLineValidator {
    PlayerChancesFileLineError validate(String playerChancesFileLine, int playerChancesFileLineNumber);
}
