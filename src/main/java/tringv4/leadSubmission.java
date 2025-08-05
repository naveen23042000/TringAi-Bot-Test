package tringv4;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriverService;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class leadSubmission {
    public static void main(String[] args) {
        // ✅ Setup ExtentReports
        ExtentSparkReporter spark = new ExtentSparkReporter("report.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        ExtentTest test = extent.createTest("Lead Submission Test");

        WebDriver driver = null;

        try {
            // ✅ Setup ChromeDriver path explicitly for Jenkins
            File driverExe = new File("/usr/local/bin/chromedriver");
            if (!driverExe.exists()) {
                throw new RuntimeException("❌ chromedriver not found at /usr/local/bin/chromedriver");
            }

            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(driverExe)
                    .usingAnyFreePort()
                    .build();

            // ✅ Headless options for CI/CD
            ChromeOptions options = new ChromeOptions();
            options.setBinary("/usr/bin/google-chrome");
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            // ✅ Launch browser
            driver = new ChromeDriver(service, options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Step 1: Open site
            test.info("🌐 Opening login page...");
            driver.get("https://tring-admin.pripod.com");
            driver.manage().window().maximize();

            // Optional: switch to iframe if login is inside one (uncomment if needed)
            // wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));

            // Step 2: Wait for login fields
            test.info("🔐 Waiting for login form...");
            WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
            emailField.sendKeys("naveenkumar@whitemastery.com");

            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
            passwordField.sendKeys("12345678");

            WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Sign In')]")));
            signInButton.click();
            test.pass("✅ Logged in successfully");

            // Step 3: Wait for dashboard
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Bots')]")));

            // Step 4: Click "Bots"
            test.info("🧠 Navigating to Bot list...");
            driver.findElement(By.xpath("//span[contains(text(),'Bots')]")).click();

            // Step 5: Click Preview icon
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Preview']")));
            driver.findElement(By.cssSelector("button[title='Preview']")).click();

            // Step 6: Switch to new tab
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }

            // Step 7: Switch to iframe (if bot preview uses iframe)
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));

            // Step 8: Wait for input field and send message
            WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));
            inputField.sendKeys("Schedule Site Visit");
            inputField.sendKeys(Keys.ENTER);
            test.info("💬 Message sent to bot");

            // Step 9: Wait for response (adjust time if bot response is slow)
            Thread.sleep(5000);

            test.pass("✅ Lead submission test completed successfully.");
        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Test failed due to an exception");
        } finally {
            if (driver != null) {
                driver.quit();
            }
            extent.flush();
            System.out.println("✅ Script execution completed.");
        }
    }
}
