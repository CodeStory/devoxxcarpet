import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;

import java.util.Random;

import static helpers.MoreAnswers.cycleOn;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.spy;

public class FluentleniumVoteTest extends FluentTest {
    private final Random random = spy(new Random());
    private final TestWebServer webServer = new TestWebServer(random);

    //public MainPage mainPage;

    @Test
    public void top_10_carpets_should_have_no_vote() throws InterruptedException {
        goTo("http://localhost:" + webServer.port());

        await().until(".rank").withText().contains("no vote").hasSize(10);
    }

    @Test
    public void voting_for_left_carpet_makes_it_first() {
        cycleOn(1, 5).when(random).nextInt(anyInt());

        goTo("http://localhost:" + webServer.port());

        find("#left button").click();

        await().until(".rank").withText().contains("#1").isPresent();
        await().until(".rank").withText().contains("no vote").hasSize(8);
        await().until(".rank img").with("src").contains("small1.jpg").isPresent();
    }

    @Test
    public void should_vote_for_the_same_carpet() {
        cycleOn(1, 5).when(random).nextInt(anyInt());

        goTo("http://localhost:" + webServer.port());
        find("#left button").click().click().click().click();

        await().until(".rank").withText().contains("#1").withText().contains("4 votes").isPresent();
    }
//
//    public static class MainPage extends FluentPage {
//        @AjaxElement
//        @FindBy(css = ".ranks")
//        FluentWebElement ranks;
//    }
}
