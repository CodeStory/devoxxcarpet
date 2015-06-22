import net.codestory.simplelenium.FluentTest;
import net.codestory.simplelenium.SeleniumTest;
import org.junit.Test;

import java.util.Random;

import static helpers.MoreAnswers.cycleOn;
import static java.util.stream.IntStream.range;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.spy;

public class SimpleniumVoteTest extends SeleniumTest {
    private final Random random = spy(new Random());
    private final TestWebServer webServer = new TestWebServer(random);

    @Override
    protected String getDefaultBaseUrl() {
        return "http://localhost:" + webServer.port();
    }

    @Test
    public void top_10_carpets_should_have_no_vote() {
        goTo("/");

        find(".rank").withText("no vote").should().haveSize(10);
    }

    @Test
    public void voting_for_left_carpet_makes_it_first() {
        cycleOn(1, 5).when(random).nextInt(anyInt());

        goTo("/");
        find("#left button").click();

        find(".rank0 img").withAttribute("src").containing("small1.jpg").should().exist();
        find(".rank").withText("no vote").should().haveSize(9);
        find(".rank").withText("#1").withText("1 vote").should().haveSize(1);
    }

    @Test
    public void voting_for_right_carpet_makes_it_first() {
        cycleOn(1, 5).when(random).nextInt(anyInt());

        goTo("/");
        find("#right button").click();

        find(".rank0 img").withAttribute("src").containing("small5.jpg").should().exist();
        find(".rank").withText("no vote").should().haveSize(9);
        find(".rank").withText("#1").withText("1 vote").should().haveSize(1);
    }

    @Test
    public void should_vote_for_the_same_carpet() {
        cycleOn(1, 5).when(random).nextInt(anyInt());

        goTo("/");
        find("#left button").click().click().click().click();

        find(".rank").withText("#1").withText("4 votes").should().haveSize(1);
    }

    @Test
    public void lots_of_threads() {
        cycleOn(1, 5).when(random).nextInt(anyInt());

        range(0, 1000).parallel().forEach(i ->
                new FluentTest()
                    .goTo("http://localhost:" + webServer.port())
                    .find("#left button").click().click().click().click()
        );

        find(".rank0 img").withAttribute("src").containing("small1.jpg").should().beDisplayed();
    }
}
