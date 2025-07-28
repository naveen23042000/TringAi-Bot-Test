package tringv4;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CommonLogin {

    public static WebDriver driver;
    public static WebDriverWait wait;

    public static void loginToTringApp(String email, String password) {
        // ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // Driver + Wait
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

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
