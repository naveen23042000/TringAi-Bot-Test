package TringLogin;
		
import java.sql.Driver;
import java.sql.DriverManager;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
									
public class signin {				
	static WebDriver driver;         
	public static void main(String[] args) throws InterruptedException {								
		driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set explicit wait for 10 seconds
		
		driver.get("https://app.tringlabs.ai/auth/sign-in");
		driver.manage().window().maximize();
									
									
		
		// Enter Login Details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("E-mail"))).sendKeys("adminn@tyker.co");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Password"))).sendKeys("123456");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Sign in']"))).click();
			
		// clicking My account page, Using Company name and choosing the data.
				
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(@class, 'font-bold') and contains(., 'South Indian Shelters (SIS)')]\r\n"
				+ ""))).click();
						
		// Entering Address Information.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Mobile number"))).sendKeys("6767676767");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Street name"))).sendKeys("Gulmohar Avenue");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("city name"))).sendKeys("Velachery");
					
		/*
		 * WebElement dropdownIndustry =
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
		 * "//*[@id=\"__nuxt\"]/div/div[1]/div[2]/div/div[2]/form/div[2]/div")));
		 * dropdownIndustry.click();
		 * 
		 * driver.findElement(By.xpath("/html/body/div[3]/div/div/div[3]")).click();
		 */
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='radix-v-1-4-4-form-item']")));
        dropdown.click();
        							
        // Search for "India" in the dropdown
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"radix-vue-popover-content-v-1-4-7\"]/div/div[1]/input"))); // Replace with the correct locator
        searchBox.sendKeys("Tamil Nadu");
        
        // Press "Enter" to select the top search result
        searchBox.sendKeys(Keys.ENTER);
		
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("zip code"))).sendKeys("600123");
        
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div[2]/div/div[3]/div/div[2]/div/form/div[4]/button"))).click();
        			
		
		// Optional: Close the browser after the process is complete
		//driver.quit();	
	}
}