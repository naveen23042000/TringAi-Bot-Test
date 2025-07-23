package TringLogin;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HomePage {
	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException {
		driver = new ChromeDriver();
		driver.get("https://tring-admin.pripod.com/");
		driver.manage().window().maximize();
		Thread.sleep(3000);

	}

}
