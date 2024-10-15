package framework.Tests;

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

import framework.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup(); /*WebDriverManager is an open-source Java library that carries out the management(i.e., download, 
		setup, and maintenance)of the drivers required by Selenium WebDriver (e.g., chromedriver, geckodriver, msedgedriver, etc.) in a fully automated manner.*/
		String productName = "ZARA COAT 3";
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //implicit wait.
		driver.get("https://rahulshettyacademy.com/client/");
		LandingPage landingPage = new LandingPage(driver); //Creating the object for the landing page, which we can use to send the argument to the constructor of that class.
		// Landing page class
		driver.findElement(By.id("userEmail")).sendKeys("Hirokami@gamil.com");
		driver.findElement(By.id("userPassword")).sendKeys("FEFWwws!@#2334");
		driver.findElement(By.id("login")).click(); /* Line 32-33 constitute a login, Instead of these three steps, we can write the action method of what it does, we
		can push these 3 lines of code into the method. Action method is written in LandingPage class, line 32-36*/
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3"))); // Line 35 & 36 these are the reusable lines that we will use in AbstractComponent class.
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		// 35 - 37 are the lines incorporated in productCatalogue class. 
	WebElement prod = products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
	/*In the first Iteration, we get the first product, and we are searching in that container for the tag that is <b>*/
	prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container"))); /* We are waiting for the "product added to the cart"
	 pop up is displayed */
	//ng-animating is the class name where in the loading image is seen.
	wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
	//wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-custom"))).click();
	//driver.findElement(By.cssSelector(".btn-custom")).click();
	//Added line 46 and 47, After trying with both line 43 and 44, which does the same thing as the below line.
	Actions act =  new Actions(driver);
	act.moveToElement(driver.findElement(By.cssSelector("[routerlink*='cart']"))).click().build().perform();
	List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
	Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
	Assert.assertTrue(match);
	driver.findElement(By.cssSelector(".totalRow button")).click();
	Actions a = new Actions(driver);
	a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]")).click();
	driver.findElement(By.cssSelector(".action__submit")).click();
	String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
	Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
	driver.close();
	
	}

}
