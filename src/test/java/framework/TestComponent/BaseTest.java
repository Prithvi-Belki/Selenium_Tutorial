package framework.TestComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import framework.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
	public LandingPage landingPage;
	public WebDriver initializeDriver() throws IOException {
		
		// In Java there is a class called properties. which can read the global properties.
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +  "//src//main//java//framework//resources//GlobalData.properties");
		prop.load(fis); // Load method expects an Input stream object. FileInputSteam class converts the properties file to Input stream.
		String browserName =System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		//if we provide the details on which browser to run on CMD it will pick that browser. or else it will pick the property provided on the GlobalData.properties
		// prop.getProperty("browser");
		if(browserName.contains("chrome")) {
		ChromeOptions options = new ChromeOptions(); // running the testcases in an headless mode. And providing that options as an argument to the chromedriver. L43-L46
		WebDriverManager.chromedriver().setup(); /*WebDriverManager is an open-source Java library that carries out the management(i.e., download, 
		setup, and maintenance)of the drivers required by Selenium WebDriver (e.g., chromedriver, geckodriver, msedgedriver, etc.) in a fully automated manner.*/
		if(browserName.contains("headless")) {
		options.addArguments("headless");
		}
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1440,900));
		
		} else if(browserName.equalsIgnoreCase("firefox")) {
			//Firefox
		} else if(browserName.equalsIgnoreCase("edge")) {
			//Edge
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //implicit wait.
		
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		//COnverting Json to String
		String jsonContent = FileUtils.readFileToString(new File(filePath),
				StandardCharsets.UTF_8);
		
		//To Convert String to HashMap we need a new dependency called Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
			
		}); 
		return data;
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user,dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user,dir") + "//reports//" + testCaseName + ".png";
	}
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		 landingPage = new LandingPage(driver); //Creating the object for the landing page, which we can use to send the argument to the constructor of that class.
		// Landing page class
		landingPage.goTo(); // We are creating another method on Landing page to navigate to that page.
		return landingPage;
		
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void closeBrowser() {
		driver.close();
	}
	
}
