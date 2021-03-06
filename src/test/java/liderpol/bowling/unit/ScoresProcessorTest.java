package liderpol.bowling.unit;

import liderpol.bowling.common.AssertionsHelper;
import liderpol.bowling.common.GamesMocker;
import liderpol.bowling.dto.GameDTO;
import liderpol.bowling.processor.IScoresProcessor;
import liderpol.bowling.processor.ScoresProcessor;
import org.junit.Test;

public class ScoresProcessorTest {
    private final IScoresProcessor scoresProcessor;

    public ScoresProcessorTest() {
        this.scoresProcessor = new ScoresProcessor();
    }

    @Test
    public void allZeroesGameScoreShouldBeCorrect() {
        GameDTO gameDTO = GamesMocker.mockAllZeroesGame(true);
        this.scoresProcessor.processScores(GamesMocker.wrapGame(gameDTO));
        AssertionsHelper.allZeroesGameScoresAssertions(gameDTO);
    }

    @Test
    public void allFoulsGameScoreShouldBeCorrect() {
        GameDTO gameDTO = GamesMocker.mockAllFoulsGame(true);
        this.scoresProcessor.processScores(GamesMocker.wrapGame(gameDTO));
        AssertionsHelper.allFoulsGameScoresAssertions(gameDTO);
    }

    @Test
    public void perfectGameScoreShouldBeCorrect() {
        GameDTO gameDTO = GamesMocker.mockPerfectGame(true);
        this.scoresProcessor.processScores(GamesMocker.wrapGame(gameDTO));
        AssertionsHelper.perfectGameScoresAssertions(gameDTO);
    }

    @Test
    public void jeffGameScoreShouldBeCorrect() {
        GameDTO gameDTO = GamesMocker.mockJeffGame(true);
        this.scoresProcessor.processScores(GamesMocker.wrapGame(gameDTO));
        AssertionsHelper.jeffGameScoresAssertions(gameDTO);
    }

    @Test
    public void johnGameScoreShouldBeCorrect() {
        GameDTO gameDTO = GamesMocker.mockJohnGame(true);
        this.scoresProcessor.processScores(GamesMocker.wrapGame(gameDTO));
        AssertionsHelper.johnGameScoresAssertions(gameDTO);
    }
}