package TringLogin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class UploadFile {
    static WebDriver driver;

    public static void main(String[] args) {
        // Initialize the WebDriver
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set explicit wait

        // Open the URL and maximize the window
        driver.get("https://tring-admin.pripod.com/");
        driver.manage().window().maximize();

        // Sign In
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("E-mail"))).sendKeys("testb1@yopmail.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Password"))).sendKeys("asdf1234");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();

        // Navigate to sections as per the application flow
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div/div/div[3]/div/div[1]/h3/button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div/div/div[3]/div/div[2]/div[2]/div/a"))).click();

        // Click "Add Chat Bot" button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Add Chat Bot']"))).click();

        // Enter Bot Name
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Bot Name"))).sendKeys("Test Bot1");

        // Select "Real Estate" in the dropdown
        WebElement dropdownBotType = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[contains(@class,'mt-2 focus-visible')]")));
        new Select(dropdownBotType).selectByVisibleText("Real Estate");

        // Submit the form
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Submit']"))).click();

        // Select the bot from the list
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[normalize-space()='Test Bot1']"))).click();

        // Navigate to another section
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__nuxt\"]/div/div[2]/div[2]/div[2]/div/div[2]/div[5]/a/div[1]"))).click();

        // Upload a file
        WebElement fileUpload = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dropzone-file")));
        fileUpload.sendKeys("C:/Users/white/Downloads/Latest Common SIS Bot.pdf");

        // Clicking back after uploading file
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/button[1]/*[name()='svg'][1]"))).click();

        // Adding bot configuration details
        // Clicking bot configuration
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body/div/div/div/div/div/div/div/div[4]/a[1]/div[1]"))).click();

        // Enter Bot Name and Company Name
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Bot Name"))).sendKeys("Chris");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Company Name"))).sendKeys("South India Shelters (SIS)");

        // Select Role dropdown
        WebElement dropdownButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@role='combobox' and contains(span, 'Select Role')]")));
        dropdownButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Sales Executive']"))).click();

        // Select Goal dropdown
        WebElement dropdownButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@role='combobox' and contains(span, 'Select Goal')]")));
        dropdownButton1.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='option' and .//span[text()='Customer Support']]"))).click();

        // Submit the bot configuration
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Submit']"))).click();

        // Adding Intent
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body/div/div/div/div/div/div/div/div[6]/a[1]/div[1]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Add Intents']"))).click();

        // Select Intent dropdown
        WebElement dropdownButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[5]/form[1]/div[1]/button[1]")));
        dropdownButton2.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[6]/div[1]/div[1]/div[3]"))).click();

        // Save changes	
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Save changes']"))).click();

        // Optional: Close the driver after the process is complete
        // driver.quit();
        
        // Go back to the previous page
		/*
		 * driver.navigate().back();
		 * 
		 * // Adding Dynamic form
		 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
		 * "//body/div/div/div/div/div/div/div/div[7]/a[1]/div[1]/div[1]")));
		 * 
		 * WebElement dropdownButton3 =
		 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
		 * "//button[@role='combobox']"))); dropdownButton3.click();
		 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
		 * "/html[1]/body[1]/div[3]/div[1]/div[1]/div[4]"))).click();
		 * 
		 * // Entering Label.
		 * 
		 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
		 * "//input[@id='Label']"))).sendKeys("Guest Phone");
		 * 
		 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
		 * "//input[@id='Placeholder']"))).sendKeys("Enter a Phone");
		 * 
		 * 
		 * wait.until(ExpectedConditions.elementToBeClickable(By.
		 * xpath("//input[@id='Error Message']"))).sendKeys("Invalid Phone");
		 * 
		 * wait.until(ExpectedConditions.elementToBeClickable(By.
		 * xpath("//button[normalize-space()='Add Field']"))).click();
		 * 
		 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
		 * "//button[normalize-space()='Submit']"))).click();
		 */
            													
    }
}
