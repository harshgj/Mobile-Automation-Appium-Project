package cucumberIntegrationTests.screens.Base;

import cucumberIntegrationTests.stepDefinitions.GenericMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class LoginScreen extends GenericMethods {

    public WebDriverWait wait = new WebDriverWait(driver, 90);

    public LoginScreen(WebDriver driver) {
        super(driver);
    }

    public abstract void enterCredentials();

    public abstract void loadTestData(String credentialValidations);

    public abstract void clickSignInButton();

    public abstract void getParameterisedCredentials(String username, String password);

}
