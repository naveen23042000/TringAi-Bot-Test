package TringLogin;
		
import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
			
public class newSignup {
	static WebDriver driver;
		
	public static void main(String[] args) throws InterruptedException {								
		driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set explicit wait for 10 seconds
									
		driver.get("https://tring-admin.pripod.com/auth/sign-up");
		driver.manage().window().maximize();
									
		// Signup    																					
		
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("E-mail"))).
		  sendKeys("billingcancel@yopmail.com");
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Password"))).
		  sendKeys("asdf1234");
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.
		  id("Confirm Password"))).sendKeys("asdf1234");
		  
		  wait.until(ExpectedConditions.elementToBeClickable(By.
		  xpath("//button[normalize-space()='Sign Up']"))).click();
		  
		  Thread.sleep(20000);
		  
		  // Entering OTP (assuming no actual OTP entry, just a button click)
		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
		  "//button[normalize-space()='Continue']"))).click();
		 
			
		// Entering Personal Details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Full Name")))
				.sendKeys("South Indian Shelters (SIS)");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("What do you plan to build with")))
		.sendKeys("Create ChatBot");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("What brought you to")))
		.sendKeys("Explore Ai Chatbot");
							
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("your estimated monthly budget")))
		.sendKeys("100000");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(" Where you found us")))
		.sendKeys("LinkedIn");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Business Name")))
		.sendKeys("TringChatBotAi");
		
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'inline-flex items-center')]")));
        dropdown.click();

        // Search for "India" in the dropdown
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Search country...']"))); // Replace with the correct locator
        searchBox.sendKeys("India");

        // Press "Enter" to select the top search result
        searchBox.sendKeys(Keys.ENTER);
															
															
		
		WebElement dropdownRole = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//select[contains(@class,'mt-2 focus-visible')]")));
		Select selectRole = new Select(dropdownRole);
		selectRole.selectByVisibleText("Chief Executive Officer");
						
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Proceed']"))).click();
		
		// Entering Company Details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Company Name")))
				.sendKeys("South Indian Shelters (SIS)");
							
		// Select Industry from dropdown
		WebElement dropdownIndustry = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__nuxt\"]/div/div[1]/div[2]/div/div[2]/form/div[2]/div")));
		dropdownIndustry.click();
			
		driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]")).click();
		
		
		//driver.findElement(By.xpath("//*[@id=\"radix-vue-select-content-v-0-11\"]/div/div[3]")).click();
		
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("E-mail"))).sendKeys("billingcancel@yopmail.com");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Password"))).sendKeys("asdf1234");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Sign in']"))).click();
		
		// clicking My account page
		
		
		
		// Optional: Close the browser after the process is complete
		//driver.quit();	
	}
}