package basicweb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class testAmazon {
    public static void main(String[] args) { 
        // Set up WebDriver
     //   System.setProperty("webdriver.chrome.driver", "ChromeDriver");
        WebDriver driver = new ChromeDriver();

        try {
            // Open Amazon
            driver.get("https://www.amazon.com/");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().window().maximize();

            // Click on Sign-In
            WebElement signInButton = driver.findElement(By.id("nav-link-accountList"));
            signInButton.click();

            // Enter email and continue
            WebElement emailField = driver.findElement(By.id("ap_email"));
            emailField.sendKeys("j25praveenkumar001@gmail.com");
            driver.findElement(By.id("continue")).click();
            
            Thread.sleep(4000);

            // Enter password and log in
            WebElement passwordField = driver.findElement(By.id("ap_password"));
            passwordField.sendKeys(".thunderbird12345@..");
            driver.findElement(By.id("signInSubmit")).click();

            // Search for an item
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys("laptop");
            driver.findElement(By.id("nav-search-submit-button")).click();

            // Click on the first item in search results
            WebElement firstItem = driver.findElement(By.cssSelector("div.s-main-slot div.s-result-item"));
            firstItem.click();

            // Add the item to cart
            WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
            addToCartButton.click();

            // Proceed to checkout
            WebElement proceedToCheckout = driver.findElement(By.id("hlb-ptc-btn-native"));
            proceedToCheckout.click();

            // Print a success message
            System.out.println("Login and checkout flow completed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
          //  driver.quit();
        }
    }
}