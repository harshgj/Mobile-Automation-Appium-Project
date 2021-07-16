package cucumberIntegrationTests.screens.android;

import cucumberIntegrationTests.CreateSessionCucumber;
import cucumberIntegrationTests.screens.Base.LoginScreen;
import logger.log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Properties;


/**
 * contains all the locators present on the login Screen
 */
public class AndroidLoginScreen extends LoginScreen {

	public AndroidLoginScreen androidLoginScreen;
	Properties configFileObject;
	String username;
	String password;

	public AndroidLoginScreen(WebDriver driver) {
		super(driver);
		configFileObject = CreateSessionCucumber.configPlatoform;
	}

	// Locators on the login screen
	public By user = By.xpath("//android.widget.RelativeLayout//android.widget.EditText");
	public By pass = By.xpath("//android.widget.RelativeLayout//android.widget.EditText[2]");
	public By signInButton = By.xpath("//android.widget.RelativeLayout//android.widget.Button");


	@Override
	public void clickSignInButton() {
        androidLoginScreen.findElement(androidLoginScreen.signInButton).click();
	}

	@Override
	public void enterCredentials() {
		
        androidLoginScreen.waitForVisibility(androidLoginScreen.loginViaSlideShare);
        androidLoginScreen.findElement(androidLoginScreen.loginViaSlideShare).click();
        androidLoginScreen.waitForVisibility(androidLoginScreen.user);
        androidLoginScreen.findElement(androidLoginScreen.user).sendKeys(userName);
        androidLoginScreen.findElement(androidLoginScreen.pass).sendKeys(password);
	}

	@Override
	public void loadTestData(String credentialValidations) {
       androidLoginScreen=new AndroidLoginScreen(driver);

       if(credentialValidations.equalsIgnoreCase("valid")){
       	username = configFileObject.getProperty("userName");
       	password = configFileObject.getProperty("password");
	   }else
	   {
		   username = configFileObject.getProperty("abc@gmail.com");
		   password = configFileObject.getProperty("password");
	   }
	}
}

