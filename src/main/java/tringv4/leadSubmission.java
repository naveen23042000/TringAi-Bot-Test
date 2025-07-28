package tringv4;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

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
        // 📄 Setup Extent Report
    	ExtentSparkReporter htmlReporter = new ExtentSparkReporter("leadSubmissionReport.html");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        ExtentTest test = extent.createTest("Lead Submission Bot", "Automated chatbot lead submission test");

        // 🧪 Setup WebDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    //using try catch
        try {
            test.info("Navigating to login page");
            driver.get("https://app.tringlabs.ai/auth/signin");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")))
                .sendKeys("naveenkumar@whitemastery.com");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")))
                .sendKeys("12345678");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Login']")))
                .click();
            test.pass("Login successful");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Chatbots']"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@placeholder='Search bot...'])[2]")))
                .sendKeys("TNPSC");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'View')]"))).click();

            String originalWindow = driver.getWindowHandle();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Preview')]"))).click();

            wait.until(driver1 -> driver.getWindowHandles().size() > 1);
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
                test.info("Preview URL cleaned and redirected");
            }

            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(60));
            WebElement iframe = longWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
            driver.switchTo().frame(iframe);

            WebElement inputField = longWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Type a message...']")));
            inputField.clear();
            inputField.sendKeys("Schedule Site Visit");
            Thread.sleep(20000);
            inputField.sendKeys(Keys.ENTER);
            test.pass("Bot message sent: Schedule Site Visit");

            // Random data
            String randomName = generateRandomName(7);
            String randomEmail = randomName.toLowerCase() + "@example.com";
            String randomPhone = "9" + (long)(Math.random() * 1_000_000_000L);

            WebDriverWait formWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            formWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']")))
                    .sendKeys(randomName);
            formWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Email']")))
                    .sendKeys(randomEmail);
            formWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Phone']")))
                    .sendKeys(randomPhone);

            WebElement submitBtn = formWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Submit']")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
            Thread.sleep(1000);
            try {
                submitBtn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
            }

            test.pass("Lead form submitted with name: " + randomName +
                      ", email: " + randomEmail +   
                      ", phone: " + randomPhone);

        } catch (Exception e) {
            test.fail("❌ Exception occurred: " + e.getMessage());
        } finally {
            // driver.quit();
            extent.flush();
        }
    }
}
