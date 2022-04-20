package cucumberIntegrationTests.stepDefinitions.featureSteps;


import cucumberIntegrationTests.stepDefinitions.common.BaseSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import cucumberIntegrationTests.stepDefinitions.CreateSessionCucumber;
import cucumberIntegrationTests.screens.Base.LoginScreen;
import cucumberIntegrationTests.screens.android.AndroidLoginScreen;
import org.openqa.selenium.WebDriver;

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
        if (baseStepsContext.platform.equalsIgnoreCase("android")) {
            loginScreen = new AndroidLoginScreen(driver);
        } else if (baseStepsContext.platform.equalsIgnoreCase("iOS")) {
            loginScreen = new AndroidLoginScreen(driver);
        } else if (baseStepsContext.platform.equalsIgnoreCase("windows")) {
            loginScreen = new AndroidLoginScreen(driver);
        }
    }

    @Given("^user has \"(.*)\" username and password$")
    public void usernameAndPasswordIs(String credentialsValidations) {
        loginScreen.loadTestData(credentialsValidations);
    }

    @Given("^user has \"(.*)\" and \"(.*)\"$")
    public void usernameAndPassword(String username, String password) {
        loginScreen.getParameterisedCredentials(username, password);
    }

    @When("user enters credentials")
    public void userEntersCredentials() {
        loginScreen.enterCredentials();
    }

    @And("taps on button")
    public void tapsOnButton() {
        loginScreen.clickSignInButton();
    }

}
