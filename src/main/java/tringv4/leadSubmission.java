package tringv4;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public class leadSubmission {
    public static void main(String[] args) {
        // Setup ExtentReports
        ExtentSparkReporter spark = new ExtentSparkReporter("report.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        ExtentTest test = extent.createTest("Lead Submission Test");

        WebDriver driver = null;

        try {
            // Setup ChromeDriver with WebDriverManager
            WebDriverManager.chromedriver().setup();

            // ChromeOptions for headless execution (suitable for Jenkins)
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/usr/bin/google-chrome");
            options.addArguments("--headless"); // Headless mode for Jenkins
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Step 1: Go to site
            driver.get("https://tring-admin.pripod.com");

            // Step 2: Login
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")))
                .sendKeys("your-email@example.com");

            driver.findElement(By.name("password")).sendKeys("your-password");
            driver.findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();

            // Step 3: Wait for dashboard and click "Bots"
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Bots')]")))
                .click();

            // Step 4: Click Preview button
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Preview']")))
                .click();

            // Step 5: Switch to the new tab
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            if (tabs.size() > 1) {
                driver.switchTo().window(tabs.get(1)); // new tab
            }

            // Step 6: Switch to iframe inside preview
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));

            // Step 7: Wait for input field to appear
            WebElement inputField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']"))
            );

            // Step 8: Send "Schedule Site Visit"
            inputField.sendKeys("Schedule Site Visit");
            inputField.sendKeys(Keys.ENTER);

            // Step 9: Wait for form or response to appear (you can change selector if needed)
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Name')]")),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Phone')]"))
            ));

            test.pass("Lead submission interaction successful.");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
            extent.flush();
        }
    }
}
