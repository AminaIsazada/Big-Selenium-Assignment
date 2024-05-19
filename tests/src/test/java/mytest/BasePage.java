package mytest;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Cookie;

import java.net.URL;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Properties;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String username;
    protected String password;
    protected int waitTime;
    protected JavascriptExecutor js;
    protected String authToken;
    protected String csrfToken;

    @Before
    public void setup() throws MalformedURLException, IOException {
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");

        FirefoxOptions options = new FirefoxOptions();
        // options.addArguments("--headless");
        options.setCapability("marionette", true);
        this.driver = new RemoteWebDriver(new URL("http://selenium-docker-sandbox-selenium-1:4444/wd/hub"), options);
        this.driver.manage().window().maximize();

        // Load environment variables from .env.properties file
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(".env.properties")) {
            if (input == null) {
                throw new IOException("Unable to find .env.properties");
            }
            props.load(input);
        }

        this.js = (JavascriptExecutor) driver;

        this.username = props.getProperty("LOGIN_USERNAME");
        this.password = props.getProperty("LOGIN_PASSWORD");
        this.waitTime = Integer.parseInt(props.getProperty("WAIT_TIME"));
        this.authToken = props.getProperty("AUTH_TOKEN");
        this.csrfToken = props.getProperty("CSRF_TOKEN");
        // Initialize WebDriverWait
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    }
     //#region //* =========== Private Helper methods ===========
    private void setCookies(String cookieName, String cookieValue) {
        // Set the cookie
        Cookie consentCookie = new Cookie("erat-travel-cookie-consent", "true");
        this.driver.manage().addCookie(consentCookie);

        // Refresh to apply the cookie
        this.driver.navigate().refresh();
    }
    //#endregion //* =========== Private Helper methods ===========
     //#region //* =========== Helper methods ===========
     protected void selectDropdownOption(By dropdownLocator, By listboxLocator, String optionSelector, boolean isXpath) throws InterruptedException {
        WebElement dropdown = driver.findElement(dropdownLocator);
        dropdown.click();
        
        WebElement firstOption;
        if (isXpath) {
            firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(optionSelector)));
        } else {
            firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(optionSelector)));
        }
        firstOption.click();
    }

    protected void selectRadio(By radioLocator) {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(radioLocator));
        radio.click();
    }
    
    protected void consentCookie() {
        setCookies("erat-travel-cookie-consent", "true");
    }
    
    protected void authenticate(String usernameProps, String passwordProps) {
        
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        WebElement password = this.driver.findElement(By.id("password"));
        WebElement loginButton = this.driver.findElement(By.cssSelector("button[type='submit']"));

        username.sendKeys(usernameProps);
        password.sendKeys(passwordProps);
        loginButton.click();

    }
    protected void assertToastMessage(String processingMsg, String expectedMsg) {
        WebDriverWait errorWait = new WebDriverWait(driver, Duration.ofSeconds(30)); 
        WebElement statusMessage = null;
        for (int i = 0; i < 60; i++) { 
            try {
                statusMessage = errorWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='status'][aria-live='polite']")));
                if (!statusMessage.getText().equals(processingMsg)) {
                    break;
                }
            } catch (Exception e) {
            }
            
        }
        if (statusMessage == null) {
            System.out.println(this.driver.getPageSource());
        } else {
            System.out.println("Failed to log in with status message text: " + statusMessage.getText());
        }

        assertTrue(statusMessage.getText().contains(expectedMsg));
    }
    
    //#endregion //* =========== Helper methods ===========

   
    @After
    public void teardown() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
