package stepDefinitions.featureSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import cucumberIntegrationTests.CreateSessionCucumber;
import cucumberIntegrationTests.screens.Base.LoginScreen;
import cucumberIntegrationTests.screens.android.AndroidLoginScreen;
import org.openqa.selenium.WebDriver;
import stepDefinitions.common.BaseSteps;

import java.util.Properties;

public class LoginSteps {
    public WebDriver driver;
    Properties configFileObject;
    BaseSteps baseStepsContext;
    public LoginScreen loginScreen;

    public LoginSteps(BaseSteps baseSteps) {
        baseStepsContext = baseSteps;
        driver = baseStepsContext.driver;
        configFileObject = CreateSessionCucumber.configPlatform;
        if(baseStepsContext.platform.equalsIgnoreCase("android")){
            loginScreen = new AndroidLoginScreen(driver);
        } else if(baseStepsContext.platform.equalsIgnoreCase("iOS")){
            loginScreen = new AndroidLoginScreen(driver);
        }
    }



    @And("^user has \"([^\"]*)\" username and password$")
    public void usernameAndPasswordIs(String credentialsValidations) {
        loginScreen.loadTestData(credentialsValidations);
    }

    @When("^user enters credentials$")
    public void userEntersCredentials() {
        loginScreen.enterCredentials();
    }

    @And("^taps on button$")
    public void tapsOnButton() {
        loginScreen.clickSignInButton();
    }

}
