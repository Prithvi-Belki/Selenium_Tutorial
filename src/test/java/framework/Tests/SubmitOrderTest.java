package framework.Tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework.TestComponent.BaseTest;
import framework.pageobjects.CartPage;
import framework.pageobjects.CheckoutPage;
import framework.pageobjects.ConfirmationPage;
import framework.pageobjects.LandingPage;
import framework.pageobjects.OrderPage;
import framework.pageobjects.ProductCatalog;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest {
	// This is the same class as StandAloneTest class. The changes reflecting on this page is because of page object Model.
	//public static void main(String[] args) throws InterruptedException { // We can use the TestNG Annotation instead of main method.
		// TODO Auto-generated method stub
	String productName = "ZARA COAT 3";
		@Test(dataProvider="getData", groups={"Purchase"})
		public void SubmitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
		
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
		
		ProductCatalog productCatalog = landingPage.loginApplication(input.get("email"), input.get("password") ); // the changes made here, because of landing page class. Reference: StandAloneTest class
		List<WebElement> products = productCatalog.getProductList(); // Reference Line 35-37 in StandAloneTest class
		productCatalog.addProductToCart(input.get("productName"));
		CartPage cartPage = productCatalog.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckOut();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmaitonMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
	}
		
		@Test(dependsOnMethods = {"SubmitOrder"})
		public void OrderHistoryTest() {
			ProductCatalog productCatalog = landingPage.loginApplication("Hirokami@gamil.com", "FEFWwws!@#2334");
			OrderPage ordersPage = productCatalog.goToOrdersPage();
			Assert.assertTrue(ordersPage.verifyOrderDisplay(productName));
		}
		
		
//		@DataProvider 
//		public Object[][] getData() {
//			return new Object[][]  {{"Hirokami@gamil.com","FEFWwws!@#2334", "ZARA COAT 3" }, {"fdgbtgder@gmail.com", "FEFmhcws!@#20134", "ADIDAS ORIGINAL"}};
//		}
		
		//The other way to get the data for the test case. One way is the one we provided the data in the above line.
		
		// But if we have many variables we cannot just add it as an argument for the method.
		@DataProvider 
		public Object[][] getData() throws IOException {
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put("email", "Hirokami@gamil.com");
//			map.put("password", "FEFWwws!@#2334");
//			map.put("productName", "ZARA COAT 3");
//			
//			HashMap<String, String> map1 = new HashMap<String, String>();
//			map1.put("email", "fdgbtgder@gmail.com");
//			map1.put("password", "FEFmhcws!@#20134");
//			map1.put("productName", "ADIDAS ORIGINAL");
//			
			
			List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\src\\test\\java\\framework\\data\\Purchaseorder.json");
			return new Object[][]  {{data.get(0)}, {data.get(1)}};
		}
		// The other way is to create the json file and add all the test data to that file.
}
