import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

public class SeleniumVoteTest {
    private final Random random = spy(new Random());
    private final TestWebServer webServer = new TestWebServer(random);

    @Test
    public void top_10_carpets_should_have_no_vote() throws InterruptedException {
        WebDriver driver = new FirefoxDriver();

        driver.get("http://localhost:" + webServer.port());

        List<WebElement> element = driver.findElements(By.cssSelector(".rank"));

        assertThat(element).hasSize(10);
    }
}
