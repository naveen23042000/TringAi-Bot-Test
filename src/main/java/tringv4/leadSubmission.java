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

import java.io.File;
import java.time.Duration;

public class leadSubmission {
    public static void main(String[] args) {
        // Setup ExtentReports
        ExtentSparkReporter spark = new ExtentSparkReporter("report.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        ExtentTest test = extent.createTest("Lead Submission Test");

        WebDriver driver = null;

        try {
            // Setup ChromeDriver
            WebDriverManager.chromedriver().setup();

            // Headless Chrome options for Linux/Jenkins
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless"); // Headless mode
            options.addArguments("--no-sandbox"); // Required for Jenkins
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            // ✅ SET BINARY LOCATION EXPLICITLY (if needed)
            File chromeBinary = new File("/usr/bin/google-chrome"); // Change this if using google-chrome-stable
            if (chromeBinary.exists()) {
                options.setBinary(chromeBinary);
                System.out.println("✅ Using Chrome binary at: " + chromeBinary.getAbsolutePath());
            } else {
                System.err.println("❌ Chrome binary not found at: " + chromeBinary.getAbsolutePath());
                throw new RuntimeException("Chrome binary not found");
            }

            // Launch browser
            System.out.println("🚀 Launching ChromeDriver...");
            driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Step 1: Go to site
            driver.get("https://tring-admin.pripod.com");
            driver.manage().window().maximize();

            // Step 2: Login
            driver.findElement(By.name("email")).sendKeys("your-email@example.com"); // Replace
            driver.findElement(By.name("password")).sendKeys("your-password"); // Replace
            driver.findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();

            // Step 3: Wait for dashboard
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Bots')]")));

            // Step 4: Click "Bots"
            driver.findElement(By.xpath("//span[contains(text(),'Bots')]")).click();

            // Step 5: Click Preview icon for the bot
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Preview']")));
            driver.findElement(By.cssSelector("button[title='Preview']")).click();

            // Step 6: Switch to new tab
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }

            // Step 7: Switch to iframe
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));

            // Step 8: Wait for input field and send message
            WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));
            inputField.sendKeys("Schedule Site Visit");
            inputField.sendKeys(Keys.ENTER);

            // Step 9: Wait for response (adjust if needed)
            Thread.sleep(5000);

            test.pass("✅ Lead submission interaction successful.");
        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
            extent.flush();
        }
    }
}
