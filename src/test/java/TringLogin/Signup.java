package TringLogin;
		
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
		
public class Signup {
	static WebDriver driver;
														
	public static void main(String[] args) throws InterruptedException {								
		driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set explicit wait for 10 seconds
																																																																																																																																																																																																																																																																																																																																																																																				
		driver.get("https://tring-admin.pripod.com/auth/sign-up");
		driver.manage().window().maximize();
		
		// Signup    																					
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("E-mail"))).sendKeys("stag7@yopmail.com");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Password"))).sendKeys("asdf1234");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Confirm Password"))).sendKeys("asdf1234");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Sign Up']"))).click();
		
		Thread.sleep(20000);																																																
		
		// Entering OTP (assuming no actual OTP entry, just a button click)
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Continue']"))).click();
		
		// Entering Personal Details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Full Name")))
				.sendKeys("South Indian Shelters (SIS)");
		
		WebElement dropdownRole = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//select[contains(@class,'mt-2 focus-visible')]")));
		Select selectRole = new Select(dropdownRole);
		selectRole.selectByVisibleText("Chief Executive Officer");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Proceed']"))).click();
		
		// Entering Company Details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Company Name")))
				.sendKeys("South Indian Shelters (SIS)");
			
		// Select Industry from dropdown
		WebElement dropdownIndustry = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@role='combobox' and .='Select Industry']")));
		dropdownIndustry.click();
		
		driver.findElement(By.xpath("//*[@id=\"radix-vue-select-content-v-0-11\"]/div/div[3]")).click();
		/*
		 * Select selectIndustry = new Select(dropdownIndustry);
		 * selectIndustry.selectByVisibleText("Healthcare");
		 */
			
					
			
		// Select Monthly Website Traffic from dropdown
		WebElement dropdownMonthlyWebsiteTraffic = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[option[text()='1000-5000 visits']]")));
		Select selectTraffic = new Select(dropdownMonthlyWebsiteTraffic);
		selectTraffic.selectByVisibleText("10000+ visits");
		
		// Select Number of Employees from dropdown
		WebElement dropdownNumOfEmployees = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[option[text()='1000+ employees']]")));
		Select selectEmployees = new Select(dropdownNumOfEmployees);
		selectEmployees.selectByVisibleText("1000+ employees");
												
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();
		
		// Enter Login Details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("E-mail"))).sendKeys("stag1@yopmail.com");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Password"))).sendKeys("asdf1234");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Sign in']"))).click();
		
		// Optional: Close the browser after the process is complete
		//driver.quit();	
	}
}