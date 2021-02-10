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

import Selenium.Demo.utilities.UtilitiesLightning;
import io.github.bonigarcia.wdm.WebDriverManager;
import Selenium.Demo.utilities.UtilitiesSelector;

public class NewEmergencyCase
{
	private WebDriver driver;

	@BeforeClass
	public void setUp()
	{
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        //To run in Github Action comment this line
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.MILLISECONDS);
		driver.get("https://test.salesforce.com/");
	}

	/*@Test
	public void userLogin()
	{
		UtilitiesLightning.loginSF(driver,"test-4xqgtapsgk0j@example.com","iY^y)a7I7");
	}*/
	
	@Test
	public void createNewEmergencyCase() throws InterruptedException
	{
		UtilitiesLightning.loginSF(driver,"jean.pierre.leon.davila@seat.de.uat","J*-dM=&y5p+5GVQ_1");
		UtilitiesLightning.launchApp(driver, "Vorgänge");
		UtilitiesLightning.clickButtonListView(driver,"Neu");
        UtilitiesLightning.setRadioButton(driver,"Notdienst Vorgang");
		UtilitiesLightning.clickNextButton(driver,"Weiter");
		
		UtilitiesLightning.inputSelect(driver,"Eingangskanal","Kunde");
		UtilitiesLightning.inputSelect(driver,"Kategorie","Panne");
		UtilitiesLightning.inputSelect(driver,"Hauptkategorie Panne","Elektrik");
		UtilitiesLightning.inputSelect(driver,"Inland/Ausland","Inland");
		UtilitiesLightning.inputSelect(driver,"Unterkategorie Panne","Beleuchtung defekt");
		UtilitiesLightning.inputFromLabelWithInfo(driver, "Pannen-Standort", "Frankfurter Stasse 103");
		UtilitiesLightning.inputFromLabelWithInfo(driver,"Fahrzeug Kennzeichen","FB-ED-294");
		UtilitiesLightning.inputSelect(driver,"Fahrzeug MOB","Ja");
		UtilitiesLightning.clickButton(driver, "Speichern");
        
		Thread.sleep(5000);
	}
	

	@AfterClass
	public void tearDown(){
		if (driver != null) {
			driver.quit();
		}
	}
}