package methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class login {
	
	public static void abrimosNavegador() {
		ChromeOptions options = new ChromeOptions();
		WebDriver driver = new ChromeDriver();
		
		options.addArguments("--incognito");
    	driver.manage().window().maximize();
    	driver.get("https://google.com/");
    	
    	driver.findElement(By.name("q")).sendKeys("Selenium");	
	}

}
