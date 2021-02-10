package Selenium.Demo.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class UtilitiesWebDriver{


	public static WebDriver getDriver() {
		WebDriver driver = null;
	
		String browserName = System.getProperty("browser");

		if (browserName!=null) {
			if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-notifications");
				driver = new ChromeDriver(options);
			}else if (browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "webdriver/geckodriver.exe");
				driver = new FirefoxDriver();
			}
			else if (browserName.equalsIgnoreCase("ie")) {
				driver = new InternetExplorerDriver();
			}
		}
		else {
			System.out.println("You haven't entered a browser. Test will be executed in Chrome");
			System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			//options.addArguments("--user-data-dir=C:/Users/msantosa/AppData/Local/Google/Chrome/User Data");
			driver = new ChromeDriver(options);
		}

		return driver;

	}

	private static String getParameter(String name) { 
		String value = System.getProperty(name);
		if (value == null)
			throw new RuntimeException(name + " is not a parameter!");

		if (value.isEmpty())
			throw new RuntimeException(name + " is empty!");

		return value;
	}
	
	
	public static void saveCookies(WebDriver driver, String cookieData) {
		// create file named Cookies to store Login Information		
        File file = new File(cookieData);							
        try		
        {	  
            // Delete old file if exists
			file.delete();		
            file.createNewFile();			
            FileWriter fileWrite = new FileWriter(file);							
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);							
            // loop for getting the cookie information 		
            	
            // loop for getting the cookie information 		
            for(Cookie ck : driver.manage().getCookies())							
            {			
                Bwrite.write((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));																									
                Bwrite.newLine();             
            }			
            Bwrite.close();			
            fileWrite.close();	
            
        }
        catch(Exception ex)					
        {		
            ex.printStackTrace();			
        }		
	}

	public static void setCookie(WebDriver driver, String cookieData) throws IOException, ParseException {
		File file = new File(cookieData);							
		FileReader fileReader = new FileReader(file);							
		BufferedReader Buffreader = new BufferedReader(fileReader);							
		String strline;			
		while((strline=Buffreader.readLine())!=null){		
			System.out.println("strline="+strline);
			StringTokenizer token = new StringTokenizer(strline,";");									
			while(token.hasMoreTokens()){					
				String name = token.nextToken();					
				String value = token.nextToken();					
				String domain = token.nextToken();					
				String path = token.nextToken();										

				String val=token.nextToken();			
				Date expiry = null;
				if(!val.equals("null"))
				{	
					DateFormat dateFormat = new SimpleDateFormat(
							"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
					System.out.println(val);
					expiry = dateFormat.parse(val);					
				}		
				Boolean isSecure = new Boolean(token.nextToken()).								
						booleanValue();		
				try {
					Cookie ck = new Cookie(name,value,domain,path,expiry,isSecure);			
					//System.out.println(ck);
					//driver.manage().addCookie(ck); // This will add the stored cookie to your current session
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}