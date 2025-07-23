package tringv4;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class leadSubmission {
    public static void main(String[] args) {
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

        try {
            driver.get("https://app.tringlabs.ai/auth/signin");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")))
                .sendKeys("naveenkumar@whitemastery.com");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")))
                .sendKeys("12345678");

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Login']")))
                .click();
            System.out.println("✅ Logged in.");

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

            System.out.println("✅ Switched to preview tab.");
            String previewUrl = driver.getCurrentUrl();

            // Remove mode=preview if present in the URL
            if (previewUrl.contains("mode=preview")) {
                previewUrl = previewUrl.replaceAll("[&?]mode=preview", "")
                                       .replaceAll("\\?&", "?")
                                       .replaceAll("&&", "&")
                                       .replaceAll("\\?$", "");
                driver.get(previewUrl);
                System.out.println("🔁 Redirected to cleaned preview URL: " + previewUrl);
            } else {
                System.out.println("ℹ️ Preview URL does not contain mode=preview: " + previewUrl);
            }

            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(60));
            WebElement iframe = longWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
            driver.switchTo().frame(iframe);

            // Send message to chatbot
            WebElement inputField = longWait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Type a message...']")));
            inputField.clear();
            inputField.sendKeys("Schedule Site Visit");
            System.out.println("✏️ Typed: Schedule Site Visit");

            Thread.sleep(20000); // Wait for bot response to complete
            inputField.sendKeys(Keys.ENTER);
            System.out.println("✅ Message sent after 20 seconds delay.");

            // Fill out the lead form
            WebDriverWait formWait = new WebDriverWait(driver, Duration.ofSeconds(30));

            WebElement nameField = formWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Name']")));
            nameField.sendKeys("Naveen Kumar");

            WebElement emailField = formWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Email']")));
            emailField.sendKeys("naveen@example.com");

            WebElement phoneField = formWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Phone']")));
            phoneField.sendKeys("9876543210");

            // Locate Submit button and handle edge cases
            formWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[normalize-space()='Submit']")));
            WebElement submitBtn = driver.findElement(By.xpath("//button[normalize-space()='Submit']"));

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
            Thread.sleep(1000); // Give UI time to settle

            try {
                submitBtn.click();
            } catch (Exception e) {
                System.out.println("⚠️ Normal click failed, retrying with JS.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
            }

            System.out.println("✅ Lead form submitted successfully.");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        } finally {
            // Uncomment if you want the browser to close automatically
            // driver.quit();
        }
    }
}
