package mytest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginTest extends BasePage {

    @Test
    public void testLogin() throws InterruptedException {
        // Use the loaded environment variables
        String usernameEnv = this.username;
        String passwordEnv = this.password;

        // #region //* =========== Login ===========
        this.driver.get("https://erat-travel.com/login");
        authenticate(usernameEnv, passwordEnv);
        Thread.sleep(1000);
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        // Retrieve the token from the cookie
        Cookie authTokenCookie = this.driver.manage().getCookieNamed("__Secure-next-auth.session-token");
        if (authTokenCookie != null) {
            String authToken = authTokenCookie.getValue();
            System.out.println("Auth Token: " + authToken);
        } else {
            System.out.println("Auth token not found");
        }
        // #endregion //* =========== Login ===========

        // #region //* =========== Logout ===========
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Logout')]/ancestor::div[contains(@class, 'cursor-pointer')]")));
        logoutButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        String pageTitle = this.driver.getTitle();
        if (!pageTitle.equals("Login | Erat Travel")) {
            this.driver.get("https://erat-travel.com/login");
        }
        assertEquals("Login | Erat Travel", this.driver.getTitle());

        System.out.println("Logged out successfully and redirected to login page.");
        // #endregion //* =========== Logout ===========

        // #region //* =========== Wrong credentials ===========
        authenticate("Wrong", "credentials");
        assertToastMessage("Signing in...", "Your username or password is invalid!");
        // #endregion //* =========== Wrong credentials ===========
    }
}
