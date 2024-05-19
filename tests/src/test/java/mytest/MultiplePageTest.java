package mytest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MultiplePageTest extends BasePage {

    private static final List<String> PAGE_URLS = Arrays.asList(
        "https://erat-travel.com/destinations/western-europe",
        "https://erat-travel.com/destinations/southern-europe",
        "https://erat-travel.com/destinations/eastern-central-europe",
        "https://erat-travel.com/destinations/south-eastern-europe"
    );

    @Test
    public void testMultiplePages() throws InterruptedException, IOException {
        // Multiple page test
        for (String url : PAGE_URLS) {
            System.out.println("Testing page: " + url);
            this.driver.get(url);

            performPageTests();
        }
    }

    private void performPageTests() throws InterruptedException {
        // object pattern
        // static page test
        WebElement exampleElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/main/section[1]/div/div/p")));
        assertTrue("Title is present", exampleElement.isDisplayed());
        // read title
        String pageTitle = driver.getTitle();
        assertTrue("Page title length is not 0", pageTitle.length() > 0);

        Thread.sleep(2000);
    }
}
