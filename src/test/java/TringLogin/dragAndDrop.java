package TringLogin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class dragAndDrop {
	static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {
		driver = new ChromeDriver();
		driver.get("http://demo.guru99.com/test/drag_drop.html");
		driver.manage().window().maximize();
		
		Actions ac = new Actions(driver);
		WebElement source = driver.findElement(By.xpath("//a[text()=' BANK ']"));
		WebElement destination = driver.findElement(By.xpath("(//li[@class='placeholder'])[1]"));
		ac.dragAndDrop(source, destination).perform();
		
		Thread.sleep(3000);
		
		WebElement Source2 = driver.findElement(By.xpath("//a[text()=' 5000 ']"));
		WebElement destination2 = driver.findElement(By.id("amt7"));
		ac.dragAndDrop(Source2, destination2).perform();
	}

}
