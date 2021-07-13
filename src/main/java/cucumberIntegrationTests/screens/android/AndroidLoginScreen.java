package cucumberIntegrationTests.screens.android;

import UITestFramework.GenericMethods;
import cucumberIntegrationTests.CreateSessionCucumber;
import cucumberIntegrationTests.screens.Base.LoginScreen;
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
	public void launchApp() {
		System.out.println("App is Launched");
	}

	@Override
	public void enterCredentials() {
		System.out.println("App is Launched");
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

