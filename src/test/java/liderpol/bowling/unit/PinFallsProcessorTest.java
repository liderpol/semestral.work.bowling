package liderpol.bowling.unit;

import liderpol.bowling.common.AssertionsHelper;
import liderpol.bowling.common.GamesMocker;
import liderpol.bowling.dto.GameDTO;
import liderpol.bowling.processor.IPinFallsProcessor;
import liderpol.bowling.processor.PinFallsProcessor;
import org.junit.Assert;
import org.junit.Test;

public class PinFallsProcessorTest {
    private final IPinFallsProcessor pinFallsProcessor;

    public PinFallsProcessorTest() {
        this.pinFallsProcessor = new PinFallsProcessor();
    }

    @Test
    public void lackOfChancesGameShouldBeInvalid() {
        GameDTO gameDTO = GamesMocker.mockLackOfChancesGame();
        this.pinFallsProcessor.processPinFalls(GamesMocker.wrapGame(gameDTO));
        //Game should be invalid
        Assert.assertFalse(gameDTO.isValid());
    }

    @Test
    public void chancesExcessGameShouldBeInvalid() {
        GameDTO gameDTO = GamesMocker.mockChancesExcessGame();
        this.pinFallsProcessor.processPinFalls(GamesMocker.wrapGame(gameDTO));
        //Game should be invalid
        Assert.assertFalse(gameDTO.isValid());
    }

    @Test
    public void allZeroesGameShouldBeCorrect() {
        GameDTO gameDTO = GamesMocker.mockAllZeroesGame(false);
        this.pinFallsProcessor.processPinFalls(GamesMocker.wrapGame(gameDTO));
        AssertionsHelper.allZeroesGamePinFallsAssertions(gameDTO);
    }

    @Test
    public void allFoulsGameShouldBeCorrect() {
        GameDTO gameDTO = GamesMocker.mockAllFoulsGame(false);
        this.pinFallsProcessor.processPinFalls(GamesMocker.wrapGame(gameDTO));
        AssertionsHelper.allFoulsGamePinFallsAssertions(gameDTO);
    }

    @Test
    public void perfectGameShouldBeCorrect() {
        GameDTO gameDTO = GamesMocker.mockPerfectGame(false);
        this.pinFallsProcessor.processPinFalls(GamesMocker.wrapGame(gameDTO));
        AssertionsHelper.perfectGamePinFallsAssertions(gameDTO);
    }

    @Test
    public void jeffGameShouldBeCorrect() {
        GameDTO gameDTO = GamesMocker.mockJeffGame(false);
        this.pinFallsProcessor.processPinFalls(GamesMocker.wrapGame(gameDTO));
        AssertionsHelper.jeffGamePinFallsAssertions(gameDTO);
    }

    @Test
    public void johnGameShouldBeCorrect() {
        GameDTO gameDTO = GamesMocker.mockJohnGame(false);
        this.pinFallsProcessor.processPinFalls(GamesMocker.wrapGame(gameDTO));
        AssertionsHelper.johnGamePinFallsAssertions(gameDTO);
    }
}