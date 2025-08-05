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

public class leadSubmission {

    public static void main(String[] args) throws InterruptedException {
        // Setup ExtentReports
        ExtentSparkReporter spark = new ExtentSparkReporter("report.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        ExtentTest test = extent.createTest("Lead Submission Test");

        WebDriver driver = null;

        try {
            // Setup ChromeDriver using WebDriverManager
            WebDriverManager.chromedriver().setup();

            // ✅ Run Chrome in headless mode
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // Step 1: Open website
            driver.get("https://tring-admin.pripod.com");
            driver.manage().window().maximize();

            // Step 2: Login
            driver.findElement(By.name("email")).sendKeys("your-email@example.com");
            driver.findElement(By.name("password")).sendKeys("your-password");
            driver.findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();

            // Step 3: Wait for dashboard to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Bots')]")));

            // Step 4: Click "Bots"
            driver.findElement(By.xpath("//span[contains(text(),'Bots')]")).click();

            // Step 5: Click Preview icon
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Preview']")));
            driver.findElement(By.cssSelector("button[title='Preview']")).click();

            // Step 6: Switch to new tab
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }

            // Step 7: Switch to iframe inside the new tab
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));

            // Step 8: Wait for chatbot input to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='text']")));

            // Step 9: Send message to chatbot
            WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));
            inputField.sendKeys("Schedule Site Visit");
            inputField.sendKeys(Keys.ENTER);

            // Step 10: Wait for response
            Thread.sleep(5000); // Adjust if required

            test.pass("Lead submission interaction successful.");

        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // Mark build as failure in Jenkins
        } finally {
            if (driver != null) {
                driver.quit();
            }
            extent.flush(); // Save the report
        }
    }
}
