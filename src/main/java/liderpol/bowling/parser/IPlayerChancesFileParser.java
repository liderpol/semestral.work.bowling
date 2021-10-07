package liderpol.bowling.parser;

import liderpol.bowling.dto.PlayerChancesFileParserResult;

import java.io.IOException;

public interface IPlayerChancesFileParser {
    PlayerChancesFileParserResult parsePlayerChancesFile(String playerChancesFilePath) throws IOException;
}