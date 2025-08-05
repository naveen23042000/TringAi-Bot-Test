package tringv4;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class leadSubmission {
    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            System.out.println("🔧 Setting Chrome options...");
            ChromeOptions options = new ChromeOptions();
            options.addArguments(
                "--headless",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1920,1080",
                "--ignore-certificate-errors",
                "--allow-insecure-localhost",
                "--disable-web-security"
            );

            driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            System.out.println("🌐 Opening login page...");
            driver.get("https://tring-admin.pripod.com");

            // Print partial page source to debug SSL issues
            System.out.println("🔍 Verifying if the page is really loaded:");
            String pageSource = driver.getPageSource();
            if (pageSource.contains("Privacy error") || pageSource.contains("Your connection is not private")) {
                throw new RuntimeException("❌ Chrome blocked the page due to SSL error.");
            }

            // Try locating the login form or iframe
            try {
                // Optional: If iframe is present, switch to it
                WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
                driver.switchTo().frame(iframe);
                System.out.println("✅ Switched to iframe.");
            } catch (TimeoutException e) {
                System.out.println("⚠️ No iframe found. Continuing...");
            }

            System.out.println("🔍 Waiting for email field...");
            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
            emailField.sendKeys("naveen@yopmail.com");

            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
            passwordField.sendKeys("Naveen@123");

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
            loginButton.click();

            System.out.println("✅ Login attempted.");

            // Further actions can be scripted here...

        } catch (TimeoutException e) {
            System.out.println("❌ Timeout: Element not found within wait time.");
            throw new RuntimeException("Test failed due to timeout: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Exception: " + e.getMessage());
            throw new RuntimeException("Test failed due to an exception", e);
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed.");
            }
        }
    }
}
