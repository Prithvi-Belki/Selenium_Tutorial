package framework.Tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import framework.TestComponent.BaseTest;
import framework.TestComponent.Retry;
import framework.pageobjects.CartPage;
import framework.pageobjects.CheckoutPage;
import framework.pageobjects.ConfirmationPage;
import framework.pageobjects.LandingPage;
import framework.pageobjects.ProductCatalog;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ErrorValidationsTest extends BaseTest {
	// This is the same class as StandAloneTest class. The changes reflecting on this page is because of page object Model.
	//public static void main(String[] args) throws InterruptedException { // We can use the TestNG Annotation instead of main method.
		// TODO Auto-generated method stub
		@Test(groups = {"ErrorHandling"}, retryAnalyzer= Retry.class)
		public void LoginErrorValidatoin() throws IOException, InterruptedException {
		String productName = "ZARA COAT 3";
		// The below 4 limes will be moved to BaseTest class. for the driver Initialization
		/*WebDriverManager.chromedriver().setup(); WebDriverManager is an open-source Java library that carries out the management(i.e., download, 
		setup, and maintenance)of the drivers required by Selenium WebDriver (e.g., chromedriver, geckodriver, msedgedriver, etc.) in a fully automated manner.*/
		/*WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //implicit wait.
		
		LandingPage landingPage = new LandingPage(driver); //Creating the object for the landing page, which we can use to send the argument to the constructor of that class.
		// Landing page class
		landingPage.goTo(); // We are creating another method on Landing page to navigate to that page.
		// These two above lines for launching the application are called in launch application method in BaseTest class.
		*/
		//LandingPage landingPage = launchApplication();
		// The above object creation is removed and by adding @BeforeTest TestNG annotation for launchApplication Method
		landingPage.loginApplication("Hiroki@gamil.com", "FEF!@#2334"); // the changes made here, because of landing page class. Reference: StandAloneTest class
		Assert.assertEquals("Incorrect email or password", landingPage.getErrorMessage());
	}
		
		@Test
		public void ProductErrorValidation() throws IOException, InterruptedException {
		String productName = "ZARA COAT 3";
		// The below 4 limes will be moved to BaseTest class. for the driver Initialization
		/*WebDriverManager.chromedriver().setup(); WebDriverManager is an open-source Java library that carries out the management(i.e., download, 
		setup, and maintenance)of the drivers required by Selenium WebDriver (e.g., chromedriver, geckodriver, msedgedriver, etc.) in a fully automated manner.*/
		/*WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //implicit wait.
		
		LandingPage landingPage = new LandingPage(driver); //Creating the object for the landing page, which we can use to send the argument to the constructor of that class.
		// Landing page class
		landingPage.goTo(); // We are creating another method on Landing page to navigate to that page.
		// These two above lines for launching the application are called in launch application method in BaseTest class.
		*/
		//LandingPage landingPage = launchApplication();
		// The above object creation is removed and by adding @BeforeTest TestNG annotation for launchApplication Method
		
		ProductCatalog productCatalog = landingPage.loginApplication("fdgbtgder@gmail.com", "FEFmhcws!@#20134"); // the changes made here, because of landing page class. Reference: StandAloneTest class
		List<WebElement> products = productCatalog.getProductList(); // Reference Line 35-37 in StandAloneTest class
		productCatalog.addProductToCart(productName);
		CartPage cartPage = productCatalog.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
	}

}
