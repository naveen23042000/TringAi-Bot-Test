package tringv4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public class leadSubmission {
    public static void main(String[] args) {
        try {
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

            ChromeOptions options = new ChromeOptions();
            options.setBinary("/usr/bin/google-chrome");
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            WebDriver driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Step 1: Open login page
            driver.get("https://app.tringlabs.ai");
            System.out.println("Opened Tring login page");

            // Step 2: Login
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys("your_email");
            driver.findElement(By.name("password")).sendKeys("your_password", Keys.ENTER);
            System.out.println("Logged in");

            // Step 3: Wait for dashboard, click a bot (update selector based on actual page)
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, '/bot/')]"))).click();
            System.out.println("Clicked on a bot");

            // Step 4: Click "Preview" to open preview in new tab
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Preview')]"))).click();
            System.out.println("Opened bot preview");

            // Step 5: Switch to new tab
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

            // Step 6: Switch to iframe
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
            System.out.println("Switched to preview iframe");

            // Step 7: Wait for bot response ready (adjust selector if needed)
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']"))).sendKeys("Schedule Site Visit", Keys.ENTER);
            System.out.println("Message sent to bot");

            // Step 8: Wait for lead form to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Lead') or contains(text(),'Form') or contains(text(),'Submit')]")));
            System.out.println("✅ Lead form appeared. Test successful.");

            driver.quit();

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Test failed due to an exception");
        }
    }
}
