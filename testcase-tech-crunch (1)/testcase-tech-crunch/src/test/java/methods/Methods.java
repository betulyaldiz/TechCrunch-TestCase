package methods;

import driver.DriverExec;
import helper.ElementHelper;
import helper.StoreHelper;
import helper.UrlHelper;
import model.ElementInfo;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.fail;

public class Methods {

    private static final Logger log = Logger.getLogger(String.valueOf(Methods.class));
    public static WebDriverWait wait;
    private final WebDriver driver;


    public Methods() {
        this.driver = DriverExec.driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(30));


    }

    public ElementInfo getElementInfo(String key) {
        return StoreHelper.INSTANCE.findElementInfoByKey(key);
    }

    public By getBy(String key) {
        log.info("Element is kept at " + key + "value.");
        return ElementHelper.getElementInfoToBy(getElementInfo(key));
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public WebElement findElement(By by) {
        log.info("The element has the value " + by.toString());
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void clickElement(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
        findElement(by).click();
        log.info(by + " element has been clicked.");
    }

    public void sendKeys(By by, String key) {
        wait.until(ExpectedConditions.visibilityOf(findElement(by)));
        findElement(by).sendKeys(key);
    }

    public void waitForSeconds(int seconds) {
        try {
            log.info(seconds + " seconds wait...");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Select getSelect(By by) {
        return new Select(findElement(by));
    }

    public void selectByVisibleText(By by, String text) {
        getSelect(by).selectByVisibleText(text);
    }

    public void selectByValue(By by, String value) {
        getSelect(by).selectByValue(value);
    }

    public void selectByIndex(By by, int index) {
        getSelect(by).selectByIndex(index);
    }

    private List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public void findElementGetText(By by, String text, By optionalBy) {
        List<WebElement> allRows = findElements(by);
        for (WebElement element : allRows) {
            if (element.getText().contains(text)) {
                element.findElement(optionalBy).click();
            }
        }
    }

    public String getCurrentUrl() {
        log.info("Current Url:" + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    public String decoderUrl(String url) throws UnsupportedEncodingException {
        String decodedUrl = URLDecoder.decode(url, "UTF-8");
        log.info("Decoded Url:" + decodedUrl);
        return decodedUrl;
    }

    public void isCurrentUrlEquals(String url) throws UnsupportedEncodingException {
        log.info("====== Url Control =====");
        Assert.assertEquals(getCurrentUrl(), decoderUrl(url));
    }

    public void scrollToElement(By selector) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', inline: 'center', block: 'center'});", findElement(selector));
        log.info(  " scrolled to element" + selector.toString());
    }

    public void checkToAuthor(By newsTitle, By newsAuthors) {
        List<WebElement> news = driver.findElements(newsTitle);

        for (WebElement eachNews : news) {
            WebElement author = eachNews.findElement(newsAuthors);
            boolean isDisplayedAuthorNews = author.getText().length() > 0 && eachNews.isDisplayed();
            Assert.assertTrue(isDisplayedAuthorNews);
            log.info(author.getText() + " author of ---> " + eachNews.getText() + " news " + isDisplayedAuthorNews);
        }
    }

    public void checkToImages(By newsTitle, By newsImages) {
        List<WebElement> news = driver.findElements(newsTitle);

        for (WebElement eachNews : news) {
            List<WebElement> imageSources = eachNews.findElements(newsImages);

            for (WebElement image : imageSources) {
                String imageLink = image.getAttribute("srcset").split(" ")[0];
                boolean isDisplayedImageNews = UrlHelper.isValidURL(imageLink);
                Assert.assertTrue(isDisplayedImageNews);
                log.info("Image Link----> " + imageLink);

            }

        }
    }

    public void clickRandomNews(By newsTitle) {
        List<WebElement> news = driver.findElements(newsTitle);
        int randomIndex = UrlHelper.getRandomIndex(0, news.size()-1);
        news.get(randomIndex).click();
    }

    public void checkBrowserTitleAndNewsTitle(By newsPageTitle) {
        WebElement text = findElement(newsPageTitle);
        Assert.assertTrue("Article title is not same with the browser title", driver.getTitle().contains(text.getText()));
        log.info("Compatible with browser title news title ----> " + text.getText());
    }

    public void verifyNewsLinks(By newsPageLinks) {
        List<WebElement> linkElements = driver.findElements(newsPageLinks);

        for (WebElement linkElement : linkElements) {
            String link = linkElement.getAttribute("href");

            if (!link.contains("mailto:")) Assert.assertTrue(UrlHelper.isValidURL(link));
        }
    }

    public void checkURLAndTheNewsContent(By newsPageTitle) {
        WebElement text = findElement(newsPageTitle);
        String articleTitleWithoutSpecialCharacters = text.getText().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String[] urlWithoutSlashes = driver.getCurrentUrl().split("/");
        String urlArticleWithoutDashes = urlWithoutSlashes[urlWithoutSlashes.length - 1].replaceAll("-", "");

        Assert.assertEquals(String.format("Article title is not same with the URL Title: %s", driver.getCurrentUrl()), articleTitleWithoutSpecialCharacters, urlArticleWithoutDashes);
        log.info("Url -->" + getCurrentUrl() + "and Content -->" + text.getText()+ " matches. ----> " );
    }

}
