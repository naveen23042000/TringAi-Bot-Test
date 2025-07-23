package TringLogin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class tabHandles {
	static WebDriver driver;
	public static void main(String[] args) throws Exception {
		driver = new ChromeDriver();
		driver.get("https://tring-admin.pripod.com/");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		
		// Sign In option
		
		driver.findElement(By.xpath("//input[@id=\"E-mail\"]")).sendKeys("john@asdf.com");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id=\"Password\"]")).sendKeys("asdf1234");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
		Thread.sleep(7000);
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div[3]/div/div[1]/h3/button")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div/div[3]/div/div[2]/div[2]/div/a")).click();
		
		Thread.sleep(3500);
		
		// searching for an SIS Florence.
		
		driver.findElement(By.xpath("//input[@placeholder='Search bot...']")).sendKeys("SIS Florence");
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//td[normalize-space()='SIS Florence']")).click();
		
		Thread.sleep(2000);
		
		String windowHandle =driver.getWindowHandle();
		System.out.println("First tab is " + windowHandle);
		
		
		driver.findElement(By.xpath("//a[contains(@href, 'preview.html') and contains(@class, 'inline-flex') and contains(@class, 'bg-[#474df9]')]")).click();
		
		Thread.sleep(10000);
		
		
		Set<String>windowHandles=driver.getWindowHandles();
		System.out.println(windowHandles);
		
		List<String> list = new ArrayList<String>(windowHandles);
		driver.switchTo().window(list.get(1));
		System.out.println(driver.getCurrentUrl());
		driver.switchTo().window(list.get(0));
		driver.close();
		
		
		Set<String> WindowHandles2 = driver.getWindowHandles();
		list.clear();
		list.addAll(WindowHandles2);
		driver.switchTo().window(list.get(0));
		
		String currentUrl = driver.getCurrentUrl();
		System.out.println(currentUrl);
		
	 
	}
}	
	