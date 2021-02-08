package Selenium.Demo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumDemo
{
	private WebDriver driver;

	@BeforeClass
	public void setUp()
	{
		WebDriverManager.chromedriver().setup();
		//To run in Github Action uncomment these liness
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
		//driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.MILLISECONDS);
		driver.get("https://nosoftware-data-7156-dev-ed.cs102.my.salesforce.com/");
	}

	@Test
	public void userLogin() throws InterruptedException
	{
		WebElement usernameTxt = driver.findElement(By.id("username"));
		usernameTxt.sendKeys("test-4xqgtapsgk0j@example.com");
		WebElement passwordTxt = driver.findElement(By.id("password"));
		passwordTxt.sendKeys("iY^y)a7I7");
		driver.findElement(By.id("Login")).click();

		
		if(driver.findElements(By.partialLinkText ("I Don't Want to Register My Phone")).size() != 0) {
			driver.findElement(By.partialLinkText ("I Don't Want to Register My Phone")).click();
		}
		
		if(driver.findElements(By.className("continue") ).size() != 0) {
			driver.findElement(By.className("continue")).click();
		}
		
		//navigateViaAppLauncher(By.xpath("//a[@data-label='Accounts']"));
		
		Thread.sleep(2000);
	}

	@AfterClass
	public void tearDown(){
		if (driver != null) {
			driver.quit();
		}
	}

	public void jsClick(By element) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void navigateViaAppLauncher(By element) {
		driver.findElement(By.xpath("//nav[contains(@class,'appLauncher')]//button")).click();
		jsClick(By.xpath("//button[text()='View All']"));
		jsClick(element);
	}
}