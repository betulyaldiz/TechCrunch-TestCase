import com.thoughtworks.gauge.Step;
import methods.Methods;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;


public class StepImplementation {

    Methods methods;


    private static final Logger log = Logger.getLogger(String.valueOf(StepImplementation.class));


    public StepImplementation() {
        methods = new Methods();


    }

    @Step("Go to URL <url>")
    public void navigateTo(String url) {
        methods.navigateTo(url);
    }


    @Step("Wait for <seconds> seconds")
    public void waitForSecond(int seconds) {
        methods.waitForSeconds(seconds);
    }

    @Step("The <key> element is checked.")
    public void checkIsExist(String key) {
        WebElement element = methods.findElement(methods.getBy(key));
        Assert.assertTrue("The element is not visible.", element.isDisplayed());
    }

    @Step("Check that the url is <key>.")
    public void checkEqualsCurrentUrl(String key) throws UnsupportedEncodingException {
        methods.isCurrentUrlEquals(key);
    }

    @Step("The <key> element is scrolled.")
    public void scrollToElement(String key) {
        methods.scrollToElement(methods.getBy(key));
    }

    @Step("Each <text> news has an <key> author")
    public void eachNewsHasAnAuthor(String text, String key) {
        methods.checkToAuthor(methods.getBy(text), methods.getBy(key));
    }

    @Step("Each <text> news has an <key> image")
    public void eachNewsHasAImage(String text, String key) {
        methods.checkToImages(methods.getBy(text), methods.getBy(key));
    }

    @Step("Click on <key> random news")
    public void clickRandomNews(String key) {
        methods.clickRandomNews(methods.getBy(key));
    }

    @Step("Verify that the browser title is the same as the news <key> tittle")
    public void theBrowserTitleIsTheSameWithTheNewsTitle(String key) {
        methods.checkBrowserTitleAndNewsTitle(methods.getBy(key));
    }
    @Step("Verify the links <key> within the news <title> content")
    public void linksWithinTheNewsContent(String key, String title) {
        methods.verifyNewsLinks(methods.getBy(key));
        methods.checkURLAndTheNewsContent(methods.getBy(title));
    }
}


