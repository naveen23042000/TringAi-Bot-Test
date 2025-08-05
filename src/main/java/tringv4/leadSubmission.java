package tringv4;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class leadSubmission {
    public static void main(String[] args) {
        ExtentSparkReporter spark = new ExtentSparkReporter("report.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        ExtentTest test = extent.createTest("Lead Submission Bot Preview");

        WebDriver driver = null;

        try {
            File driverExe = new File("/usr/local/bin/chromedriver");
            if (!driverExe.exists()) throw new RuntimeException("chromedriver not found!");

            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(driverExe)
                    .usingAnyFreePort()
                    .build();

            ChromeOptions options = new ChromeOptions();
            options.setBinary("/usr/bin/google-chrome");
            options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1920,1080");

            driver = new ChromeDriver(service, options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            test.info("🌐 Navigating to https://tring-admin.pripod.com...");
            driver.get("https://tring-admin.pripod.com");

            // 🕵️ Check for iframe if email field is not directly visible
            boolean loggedIn = false;
            try {
                WebElement email = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
                email.sendKeys("naveenkumar@whitemastery.com");
                driver.findElement(By.name("password")).sendKeys("12345678");
                driver.findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();
                loggedIn = true;
                test.pass("✅ Logged in successfully (no iframe).");
            } catch (TimeoutException e1) {
                test.warning("⚠️ Email field not found directly. Trying iframe...");
                List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
                if (!iframes.isEmpty()) {
                    driver.switchTo().frame(iframes.get(0));
                    WebElement email = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
                    email.sendKeys("naveenkumar@whitemastery.com");
                    driver.findElement(By.name("password")).sendKeys("12345678");
                    driver.findElement(By.xpath("//button[contains(text(),'Sign In')]")).click();
                    loggedIn = true;
                    test.pass("✅ Logged in successfully (via iframe).");
                    driver.switchTo().defaultContent();
                }
            }

            if (!loggedIn) {
                throw new RuntimeException("❌ Unable to locate login fields even after iframe check.");
            }

            // Wait for Bots page
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Bots')]"))).click();
            test.info("🧠 Opened Bots section.");

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Preview']"))).click();
            test.info("🧪 Clicked preview button.");

            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
            }

            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.tagName("iframe")));

            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));
            input.sendKeys("Schedule Site Visit");
            input.sendKeys(Keys.ENTER);

            Thread.sleep(5000);
            test.pass("✅ Lead submission message sent successfully.");
        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());

            if (driver != null) {
                try {
                    String pageSource = driver.getPageSource();
                    System.out.println("\n🔍 Current Page Source:\n" + pageSource.substring(0, Math.min(5000, pageSource.length())));
                } catch (Exception ignored) {}
            }

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
