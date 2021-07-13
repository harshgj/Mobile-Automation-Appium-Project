package cucumberIntegrationTests.screens.Base;

import cucumberIntegrationTests.GenericMethods;
import org.openqa.selenium.WebDriver;

public abstract class LoginScreen extends GenericMethods {

    public LoginScreen(WebDriver driver) {super(driver);}

    public abstract void launchApp();

    public abstract void enterCredentials();

    public abstract void loadTestData(String credentialValidations);

}