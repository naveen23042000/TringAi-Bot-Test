package tringv4;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class CommonLogin {

    public static WebDriver driver;
    public static WebDriverWait wait;

    public static void loginToTringApp(String email, String password, WebDriver webDriver, WebDriverWait webWait) {
        driver = webDriver;
        wait = webWait;

        try {
            // Step 1: Go to Login Page
            driver.get("https://app.tringlabs.ai/auth/signin");

            // Step 2: Enter Email & Password
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")))
                    .sendKeys(email);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")))
                    .sendKeys(password);

            // Step 3: Click Login Button
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Login']")))
                    .click();

            System.out.println("✅ Logged in successfully");

        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
        }
    }
}
