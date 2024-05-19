package mytest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BrowserHistoryTest extends BasePage {

    @Test
    public void testBrowserBackButton() throws InterruptedException {
        // Navigate to the first page
        this.driver.get("https://erat-travel.com");
        System.out.println("Navigated to main page");
       

        // Navigate to the second page
        this.driver.get("https://erat-travel.com/our-services/personalised-itinerary");
        System.out.println("Navigated to Personalised Itinerary page");
        // Verify the content of the second page
        WebElement secondPageContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Personalised Itinerary')]")));
        assertTrue("Personalised Itinerary content is not present", secondPageContent.isDisplayed());

        // Navigate to the third page
        this.driver.get("https://erat-travel.com/our-services/european-premium-package");
        System.out.println("Navigated to European Premium Package page");
        // Verify the content of the third page
        WebElement thirdPageContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'European Premium Package')]")));
        assertTrue("European Premium Package content is not present", thirdPageContent.isDisplayed());

        // Navigate to the fourth page
        this.driver.get("https://erat-travel.com/our-services/transportation");
        System.out.println("Navigated to Transportation page");
        // Verify the content of the fourth page
        WebElement fourthPageContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Transportation')]")));
        assertTrue("Transportation content is not present", fourthPageContent.isDisplayed());

        // Navigate to the fifth page
        this.driver.get("https://erat-travel.com/our-services/european-business-trip");
        System.out.println("Navigated to European Business Trip page");
        // Verify the content of the fifth page
        WebElement fifthPageContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'European Business Trip')]")));
        assertTrue("European Business Trip content is not present", fifthPageContent.isDisplayed());

        // Navigate to the sixth page
        this.driver.get("https://erat-travel.com/our-services/signature-travel");
        System.out.println("Navigated to Signature Travel page");
        // Verify the content of the sixth page
        WebElement sixthPageContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Signature Travel')]")));
        assertTrue("Signature Travel content is not present", sixthPageContent.isDisplayed());

        // Now navigate back through the history
        driver.navigate().back();
        System.out.println("Navigated back to European Business Trip page");
        // Verify the fifth page again
        WebElement backToFifthPageContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'European Business Trip')]")));
        assertTrue("Back to European Business Trip content is not present", backToFifthPageContent.isDisplayed());

        driver.navigate().back();
        System.out.println("Navigated back to Transportation page");
        // Verify the fourth page again
        WebElement backToFourthPageContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Transportation')]")));
        assertTrue("Back to Transportation content is not present", backToFourthPageContent.isDisplayed());

        driver.navigate().back();
        System.out.println("Navigated back to European Premium Package page");
        // Verify the third page again
        WebElement backToThirdPageContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'European Premium Package')]")));
        assertTrue("Back to European Premium Package content is not present", backToThirdPageContent.isDisplayed());

        driver.navigate().back();
        System.out.println("Navigated back to Personalised Itinerary page");
        // Verify the second page again
        WebElement backToSecondPageContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Personalised Itinerary')]")));
        assertTrue("Back to Personalised Itinerary content is not present", backToSecondPageContent.isDisplayed());

      
    }
}
