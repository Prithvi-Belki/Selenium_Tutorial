package framework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {
	WebDriver driver;
	public LandingPage(WebDriver driver) { // The argument driver is from the StandaloneTest class. Which we are initializing it to the local variable(Difference between 
		// the two driver can be seen by the color).
		//initializes at the beginning of the class.
		super(driver); // We are sending variables from child to parent using super method. Child being Landingpage class and AbstarctComponet being parent class. 
		// As we are sending a variable in order to catch that variable in the parent class we are declaring the constructor, with help of super keyword
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	//pageFacrory:- this is a class provided by the selenuimWebDriver to support PageObject Design pattern
	//Page Factory is exclusively for driver.findElemnt(By.locators)
	@FindBy(id="userEmail")
	WebElement userEmail; // Line 20 and 21 does the same thing as Line 18. But, as we don't have a driver defined in these two lines, we have added line 15
	
	// How @FindBy() annotation knows about driver.
	// Ans: There is a method called initElement which we have to write first, which will take care of constructing driver object which we said in the method.
	
	@FindBy(id="userPassword")
	WebElement passwordElement; // we are giving a variable name, Give the name that is identifiable.
	
	@FindBy(id="login")
	WebElement login;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	public ProductCatalog loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		passwordElement.sendKeys(password);
		login.click();
		ProductCatalog productCatalog = new ProductCatalog(driver); // creating  the objects for the product catalog.
		return productCatalog;
	}
	
	public String getErrorMessage() {
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}


}
