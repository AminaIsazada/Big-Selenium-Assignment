package  mytest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ClientFormTest extends BasePage {
    
    @Test
    public void testCompleteClientFormSubmission() throws InterruptedException {
        this.driver.get("https://erat-travel.com/forms/489a6b78-2212-4f8b-98e9-0eff066c1271");

        // Set the cookie
        consentCookie();

        // Fill in first name
        WebElement firstName = wait.until(ExpectedConditions.elementToBeClickable(By.name("firstName")));
        firstName.sendKeys("John");

        // Fill in last name
        WebElement lastName = wait.until(ExpectedConditions.elementToBeClickable(By.name("lastName")));
        lastName.sendKeys("Doe");

        // Fill in birth date
        WebElement birthDate = wait.until(ExpectedConditions.elementToBeClickable(By.name("birthDate")));
        birthDate.click();
        WebElement birthDateSelect = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".react-datepicker__day--001")));
        birthDateSelect.click();

        // Select occupation
        selectDropdownOption(By.id("react-select-3-input"), By.id("react-select-3-listbox"), "div[class*='option']:first-child", false);

        // Fill in location
        WebElement location = wait.until(ExpectedConditions.elementToBeClickable(By.name("location")));
        location.sendKeys("1234 Main St, Anytown, USA");

        // Fill in contact email
        WebElement contactEmail = wait.until(ExpectedConditions.elementToBeClickable(By.name("contactEmail")));
        contactEmail.sendKeys("john.doe@example.com");

        // Select travel with
        selectDropdownOption(By.id("react-select-4-input"), By.id("react-select-4-listbox"), "div[class*='option']:first-child", false);

        // Select travel history
        selectDropdownOption(By.id("react-select-5-input"), By.id("react-select-5-listbox"), "div[class*='option']:first-child", false);

        // Select service
        selectDropdownOption(By.id("react-select-6-input"), By.id("react-select-6-listbox"), "//div[contains(text(), 'Signature Travel')]", true);

        // Select service child 
        selectRadio(By.id("service_child_Uncover the Marvels of Europe 5 countries | 15 Days"));

        // Fill in preferences
        selectDropdownOption(By.id("react-select-7-input"), By.id("react-select-7-listbox"), "//div[@id='react-select-7-option-1']", true);
        selectDropdownOption(By.id("react-select-10-input"), By.id("react-select-10-listbox"), "//div[@id='react-select-10-option-2']", true);
        selectDropdownOption(By.id("react-select-11-input"), By.id("react-select-11-listbox"), "//div[@id='react-select-11-option-3']", true);

        // Select travel preference
        selectDropdownOption(By.id("react-select-8-input"), By.id("react-select-8-listbox"), "div[class*='option']:first-child", false);

        // Fill in motivation
        WebElement motivation = wait.until(ExpectedConditions.elementToBeClickable(By.name("motivation")));
        motivation.sendKeys("Exploring new cultures.");

        // Select media
        selectDropdownOption(By.id("react-select-9-input"), By.id("react-select-9-listbox"), "div[class*='option']:first-child", false);

        // Submit form
        System.out.println("Submitting form...");
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        submitButton.click();

        // Wait for the modal to appear
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("headlessui-dialog-:r0:")));
        WebElement agreeButton = modal.findElement(By.xpath("//*[@id=\"headlessui-dialog-:r0:\"]/div/div[2]/div[3]/button[1]"));
        agreeButton.click();

        // Wait for the route to change to home
        wait.until(ExpectedConditions.urlToBe("https://erat-travel.com/"));

        assertTrue(true);
    }

   
}
