package liderpol.bowling.it;

import liderpol.bowling.common.AssertionsHelper;
import liderpol.bowling.dto.GameDTO;
import liderpol.bowling.dto.PlayerChancesFileParserResult;
import liderpol.bowling.parser.IPlayerChancesFileParser;
import liderpol.bowling.parser.PlayerChancesFileParser;
import liderpol.bowling.processor.IPinFallsProcessor;
import liderpol.bowling.processor.IScoresProcessor;
import liderpol.bowling.processor.PinFallsProcessor;
import liderpol.bowling.processor.ScoresProcessor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertFalse;

public class IntegrationTests {
    private static final String PLAYER_CHANCES_INTEGRATION_TESTS_FILE_PATH = "src/test/resources/playerChancesFileForIntegrationTests.txt";
    private static Map<String, GameDTO> games;

    @BeforeClass
    public static void setUp() throws IOException {
        //Read and parse the player chances file
        IPlayerChancesFileParser playerChancesFileParser = new PlayerChancesFileParser();
        PlayerChancesFileParserResult result = playerChancesFileParser.parsePlayerChancesFile(PLAYER_CHANCES_INTEGRATION_TESTS_FILE_PATH);
        Map<String, GameDTO> games = result.getGames();
        //Process pinFalls
        IPinFallsProcessor pinFallsProcessor = new PinFallsProcessor();
        pinFallsProcessor.processPinFalls(games);
        //Process scores
        IScoresProcessor scoresProcessor = new ScoresProcessor();
        scoresProcessor.processScores(games);
        IntegrationTests.games = games;
    }

    @Test
    public void lackOfChancesGameShouldBeInvalid() {
        GameDTO gameDTO = games.get("Lack Of Chances");
        assertFalse(gameDTO.isValid());
    }

    @Test
    public void chancesExcessGameShouldBeInvalid() {
        GameDTO gameDTO = games.get("Chances Excess");
        assertFalse(gameDTO.isValid());
    }

    @Test
    public void allZeroesGamePinFallsShouldBeCorrect() {
        GameDTO gameDTO = games.get("Zeroes");
        AssertionsHelper.allZeroesGamePinFallsAssertions(gameDTO);
    }

    @Test
    public void allZeroesGameScoresShouldBeCorrect() {
        GameDTO gameDTO = games.get("Zeroes");
        AssertionsHelper.allZeroesGameScoresAssertions(gameDTO);
    }

    @Test
    public void allFoulsGamePinFallsShouldBeCorrect() {
        GameDTO gameDTO = games.get("Fouls");
        AssertionsHelper.allFoulsGamePinFallsAssertions(gameDTO);
    }

    @Test
    public void allFoulsGameScoresShouldBeCorrect() {
        GameDTO gameDTO = games.get("Fouls");
        AssertionsHelper.allFoulsGameScoresAssertions(gameDTO);
    }

    @Test
    public void perfectGamePinFallsShouldBeCorrect() {
        GameDTO gameDTO = games.get("Perfect");
        AssertionsHelper.perfectGamePinFallsAssertions(gameDTO);
    }

    @Test
    public void perfectGameScoresShouldBeCorrect() {
        GameDTO gameDTO = games.get("Perfect");
        AssertionsHelper.perfectGameScoresAssertions(gameDTO);
    }

    @Test
    public void jeffGamePinFallsShouldBeCorrect() {
        GameDTO gameDTO = games.get("Jeff");
        AssertionsHelper.jeffGamePinFallsAssertions(gameDTO);
    }

    @Test
    public void jeffGameScoresShouldBeCorrect() {
        GameDTO gameDTO = games.get("Jeff");
        AssertionsHelper.jeffGameScoresAssertions(gameDTO);
    }

    @Test
    public void johnGamePinFallsShouldBeCorrect() {
        GameDTO gameDTO = games.get("John");
        AssertionsHelper.johnGamePinFallsAssertions(gameDTO);
    }

    @Test
    public void johnGameScoresShouldBeCorrect() {
        GameDTO gameDTO = games.get("John");
        AssertionsHelper.johnGameScoresAssertions(gameDTO);
    }
}