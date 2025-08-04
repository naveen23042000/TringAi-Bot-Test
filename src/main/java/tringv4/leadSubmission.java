package tringv4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class leadSubmission {

    public static void main(String[] args) {
        // Setup Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless=new"); // ✅ Recommended for Jenkins
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        // Set path to chromedriver
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

        // ✅ Use final keyword to avoid lambda error
        final WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://tring-admin.pripod.com/login");
            driver.manage().window().maximize();

            // Login
            WebElement emailInput = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text']")));
            emailInput.sendKeys("naveenkumar.r@troontechnologies.com");

            WebElement passwordInput = driver.findElement(By.xpath("//input[@type='password']"));
            passwordInput.sendKeys("Naveen@123");

            WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
            loginButton.click();

            // Wait for the dashboard to load and click "bot"
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'bot')]")))
                .click();

            // Wait for bot name to be clickable
            WebElement botElement = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'V4new')]")));
            botElement.click();

            // Wait for preview icon and click it
            WebElement previewIcon = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='bot-card-footer']//div[@class='h-5 w-5']")));
            previewIcon.click();

            // Wait for new tab to open
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(driver1 -> driver.getWindowHandles().size() > 1);

            // Switch to the new tab
            Set<String> windowHandles = driver.getWindowHandles();
            List<String> windowHandlesList = new ArrayList<>(windowHandles);
            driver.switchTo().window(windowHandlesList.get(1));

            // Wait for iframe and switch to it
            WebElement iframe = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
            driver.switchTo().frame(iframe);

            // Wait for the message input and type message
            WebElement messageInput = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input-box")));
            messageInput.sendKeys("Schedule Site Visit");
            messageInput.sendKeys(Keys.ENTER);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
