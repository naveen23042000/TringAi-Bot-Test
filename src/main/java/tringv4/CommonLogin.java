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

    public static void loginToTringApp(String email, String password, WebDriver webDriver, WebDriverWait webWait) {
        if (webDriver == null || webWait == null) {
            ChromeOptions options = new ChromeOptions();

            // ✅ Optional: Run in headless mode for CI/CD
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            // ✅ Unique Chrome user profile to avoid session conflicts
            String userDataDir = "/tmp/chrome-profile-" + System.currentTimeMillis();
            options.addArguments("--user-data-dir=" + userDataDir);
            options.addArguments("--incognito");

            // ✅ Disable popups, autofill, and password manager
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("autofill.profile_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            // ✅ Initialize driver & wait
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        } else {
            driver = webDriver;
            wait = webWait;
        }

        try {
            // Step 1: Open login page
            driver.get("https://app.tringlabs.ai/auth/signin");

            // Step 2: Fill email and password
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")))
                    .sendKeys(email);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")))
                    .sendKeys(password);

            // Step 3: Click Login
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Login']")))
                    .click();

            // Step 4: Wait for home/dashboard to load - OPTIONAL
            wait.until(ExpectedConditions.urlContains("/dashboard"));

            System.out.println("✅ Logged in successfully");

        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }
}
