package tringv4;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;

public class leadSubmission {

    public static String generateRandomName(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder name = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            name.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return name.toString();
    }

    public static void main(String[] args) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportDir = System.getProperty("user.dir") + "/test-output/leadReport";
        new File(reportDir).mkdirs();

        ExtentSparkReporter spark = new ExtentSparkReporter(reportDir + "/leadSubmissionReport_" + timestamp + ".html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(spark);
        ExtentTest test = extent.createTest("Lead Submission Bot", "Automated chatbot lead submission test");

        // ✅ WebDriver Setup
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless=new"); // ✅ Headless for CI/CD
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            test.info("Navigating to login page");
            driver.get("https://app.tringlabs.ai/auth/signin");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")))
                .sendKeys("naveenkumar@whitemastery.com");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")))
                .sendKeys("12345678");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Login']")))
                .click();
            test.pass("✅ Login successful");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Chatbots']"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@placeholder='Search bot...'])[2]")))
                .sendKeys("TNPSC");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'View')]"))).click();

            String originalWindow = driver.getWindowHandle();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Preview')]"))).click();

            wait.until(d -> driver.getWindowHandles().size() > 1);
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            String previewUrl = driver.getCurrentUrl();
            if (previewUrl.contains("mode=preview")) {
                previewUrl = previewUrl.replaceAll("[&?]mode=preview", "")
                                       .replaceAll("\\?&", "?")
                                       .replaceAll("&&", "&")
                                       .replaceAll("\\?$", "");
                driver.get(previewUrl);
                test.info("🔄 Cleaned preview URL");
            }

            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
            driver.switchTo().frame(iframe);

            WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Type a message...']")));
            inputField.clear();
            inputField.sendKeys("Schedule Site Visit");
            Thread.sleep(7000); // Wait for bot response
            inputField.sendKeys(Keys.ENTER);
            test.pass("📝 Sent: Schedule Site Visit");

            // 🔀 Random user data
            String randomName = generateRandomName(7);
            String randomEmail = randomName.toLowerCase() + "@example.com";
            String randomPhone = "9" + (long)(Math.random() * 1_000_000_000L);

            WebDriverWait formWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            formWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']"))).sendKeys(randomName);
            formWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Email']"))).sendKeys(randomEmail);
            formWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Phone']"))).sendKeys(randomPhone);

            WebElement submitBtn = formWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Submit']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
            Thread.sleep(1000);
            try {
                submitBtn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
            }

            test.pass("✅ Lead submitted: " + randomName + ", " + randomEmail + ", " + randomPhone);

        } catch (Exception e) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotPath = reportDir + "/error_screenshot.png";
                File dest = new File(screenshotPath);
                org.openqa.selenium.io.FileHandler.copy(screenshot, dest);
                test.fail("❌ Exception: " + e.getMessage(),
                          MediaEntityBuilder.createScreenCaptureFromPath("leadReport/error_screenshot.png").build());
            } catch (Exception screenshotException) {
                test.fail("❌ Error during screenshot: " + screenshotException.getMessage());
            }
        } finally {
            driver.quit(); // ✅ Enabled for CI/CD
            extent.flush();
        }
    }
}
