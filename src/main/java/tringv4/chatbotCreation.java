package tringv4;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class chatbotCreation {
    public static void main(String[] args) {
        try {
            // 🔐 Login
            CommonLogin.loginToTringApp("naveenkumar@whitemastery.com", "12345678");
            WebDriver driver = CommonLogin.driver;
            WebDriverWait wait = CommonLogin.wait;

            // 📂 Navigate to Chatbots
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Chatbots']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Add Chat Bot')]"))).click();

            // 🏭 Select Industry
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='mb-6']//button)[1]"))).click();

            // 🌐 Website Source
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Website']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//main[@class='bg-white min-h-full']//input"))).sendKeys("https://tringlabs.ai");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Import']"))).click();

            System.out.println("🕐 Importing website content...");
            By nextBtnLocator = By.xpath("//button[normalize-space()='Next']");
            WebElement nextBtn = wait.until(ExpectedConditions.presenceOfElementLocated(nextBtnLocator));
            while (!nextBtn.isEnabled()) Thread.sleep(1000);
            nextBtn.click();
            System.out.println("✅ Step 2 completed");

            // 📝 Bot Name
            wait.until(ExpectedConditions.elementToBeClickable(By.id("botName"))).sendKeys("RealEstateBot");
            wait.until(ExpectedConditions.elementToBeClickable(nextBtnLocator)).click();

            // 🎭 Bot Role
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[@for='Customer Support Executive'])[1]"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(nextBtnLocator)).click();

            // 🎯 Goal
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[@for='Enhancing Property Buying & Selling Experience'])[1]"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(nextBtnLocator)).click();

            // 🚀 Create Bot
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Create Bot']"))).click();
            System.out.println("🛠️ Bot creation initiated...");

            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(120));
            longWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Preview Bot']"))).click();
            System.out.println("🔍 Preview Bot clicked");

            // 🧭 Switch Tab
            String parent = driver.getWindowHandle();
            for (String win : driver.getWindowHandles()) {
                if (!win.equals(parent)) {
                    driver.switchTo().window(win);
                    break;
                }
            }

            // 🔄 Clean preview URL
            String url = driver.getCurrentUrl();
            if (url.contains("mode=preview")) {
                url = url.replaceAll("[&?]mode=preview", "").replaceAll("&&", "&").replaceAll("\\?$", "");
                driver.get(url);
                System.out.println("🔄 Cleaned preview URL loaded");
            }

            // 💬 Switch to iframe and wait for bot to respond
            WebElement iframe = longWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
            driver.switchTo().frame(iframe);
            System.out.println("🤖 Waiting for bot response...");
            Thread.sleep(20000); // ⏳ Wait for bot to complete its initial response

            // 📩 Send "Schedule Site Visit"
            WebElement input = longWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Type a message...']")));
            input.clear();
            input.sendKeys("Schedule Site Visit");
            input.sendKeys(Keys.ENTER);
            System.out.println("📨 Message sent: Schedule Site Visit");

            // ✍️ Wait and Fill Lead Form
            WebDriverWait formWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            formWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Name']"))).sendKeys(generateRandomName(6));
            formWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Email']"))).sendKeys("demo" + new Random().nextInt(9999) + "@mail.com");
            formWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Phone']"))).sendKeys("9" + (long)(Math.random() * 1_000_000_000L));

            WebElement submitBtn = formWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Submit']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
            Thread.sleep(1000);
            try {
                submitBtn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
            }

            System.out.println("✅ Lead form submitted successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // 🔠 Random Name Generator
    public static String generateRandomName(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder name = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            name.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return name.toString();
    }
}
