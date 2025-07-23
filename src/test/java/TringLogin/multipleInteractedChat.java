package TringLogin;

import java.time.Duration;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class multipleInteractedChat {
    public static void main(String[] args) throws InterruptedException {
        // Initialize the WebDriver
        WebDriver driver = new ChromeDriver();
        
        // Open the initial URL
        driver.get("https://tring-admin.pripod.com/auth/sign-in");
        driver.manage().window().maximize();	
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));  // Increase explicit wait to 15 seconds
        
        // Sign in process
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("E-mail"))).sendKeys("raviraj@g.com");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Password"))).sendKeys("Raj@123");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))).click();

        // Navigate through the application to reach the desired link
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div/div/div[3]/div/div[1]/h3/button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div/div/div[3]/div/div[2]/div[2]/div/a"))).click();

        // Search for "SIS Florence"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search bot...']"))).sendKeys("SIS Test");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[normalize-space()='SIS Test']"))).click();
        	
        
        // Loop for 70 iterations
        for (int i = 0; i < 70; i++) {
            System.out.println("Iteration: " + (i + 1));

            // Locate the link to open in a new tab
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'preview.html') and contains(@class, 'inline-flex') and contains(@class, 'bg-[#474df9]')]")));
            link.sendKeys(Keys.CONTROL, Keys.RETURN);  // Opens link in a new tab (use Keys.COMMAND on Mac)

            // Store the current window handle
            String originalWindow = driver.getWindowHandle();

            // Wait for the new tab to open and switch to it
            Set<String> allWindows = driver.getWindowHandles();
            for (String windowHandle : allWindows) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            
            // Add a wait for page load in the new tab
            wait.until(ExpectedConditions.urlContains("preview.html")); // Ensure we are on the new page

            // Switch to iframe if the target element is inside one
            try {
                WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
                driver.switchTo().frame(iframe);
            } catch (Exception e) {
                System.out.println("No iframe found, continuing...");
            }
            		
            // aWait for and interact with the target element by sending "Site Visit" and pressing Enter
            WebElement elementOnNewTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"__nuxt\"]/div/div/div[2]/div/footer/div[1]/input")));
            elementOnNewTab.sendKeys("Hello" + Keys.ENTER);

            // Wait for the response (adjust sleep duration as needed)
            // Thread.sleep(5000);
            						
            // Close the new tab
            driver.close();
            							
            // Switch back to the original tab
            driver.switchTo().window(originalWindow);
        }

        // Close the driver at the end of the session
        driver.quit();
    }
}
