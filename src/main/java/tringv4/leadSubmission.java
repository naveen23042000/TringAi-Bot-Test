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
            // ✅ Setup ChromeDriver
            File driverExe = new File("/usr/local/bin/chromedriver");
            if (!driverExe.exists()) {
                throw new RuntimeException("❌ chromedriver not found at /usr/local/bin/chromedriver");
            }

            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(driverExe)
                    .usingAnyFreePort()
                    .build();

            ChromeOptions options = new ChromeOptions();
            options.setBinary("/usr/bin/google-chrome");
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            driver = new ChromeDriver(service, options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // ✅ Step 1: Open site
            test.info("🌐 Navigating to site...");
            driver.get("https://tring-admin.pripod.com");
            driver.manage().window().maximize();

            // ✅ Step 2: Login
            driver.findElement(By.name("email")).sendKeys("naveenkumar@whitemastery.com");
            driver.findElement(By.name("password")).sendKeys("12345678");
            driver.findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();
            test.pass("🔐 Logged in successfully.");

            // ✅ Step 3: Wait for dashboard
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Bots')]")));

            // ✅ Step 4: Click "Bots"
            driver.findElement(By.xpath("//span[contains(text(),'Bots')]")).click();
            test.info("🤖 Navigated to Bots section.");

            // ✅ Step 5: Click Preview
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Preview']")));
            driver.findElement(By.cssSelector("button[title='Preview']")).click();
            test.info("📤 Clicked on preview.");

            // ✅ Step 6: Switch to preview tab
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }
            test.info("🪟 Switched to preview tab.");

            // ✅ Step 7: Switch to iframe
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));
            test.info("🔁 Switched to iframe.");

            // ✅ Step 8: Wait for input box
            WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));

            // ✅ Step 9: Send message
            inputField.sendKeys("Schedule Site Visit");
            inputField.sendKeys(Keys.ENTER);
            test.info("💬 Message sent to bot: Schedule Site Visit");

            // ✅ Step 10: Wait for email input (lead form)
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
            emailField.sendKeys("test@example.com");
            test.pass("📩 Lead form appeared and email entered.");

            // ✅ You can continue to fill phone/name/submit here

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
