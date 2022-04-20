package cucumberIntegrationTests.screens.android;

import cucumberIntegrationTests.screens.Base.LoginScreen;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


/**
 * contains all the locators present on the login Screen
 */
public class AndroidLoginScreen extends LoginScreen {

    String username;
    String password;

    public AndroidLoginScreen(WebDriver driver) {
        super(driver);
    }

    // Locators on the login screen
    private final By user = By.xpath("//android.widget.RelativeLayout//android.widget.EditText");
    private final By pass = By.xpath("//android.widget.RelativeLayout//android.widget.EditText[2]");
    private final By signInButton = By.xpath("//android.widget.RelativeLayout//android.widget.Button");
    private final By loginViaSlideShare = By.xpath("//android.widget.TextView[@text='" + configFileObject.getProperty("signInLinkLocator") + "']");

    @Override
    public void clickSignInButton() {
        try {
            findElement(signInButton).click();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void enterCredentials() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginViaSlideShare));
            findElement(loginViaSlideShare).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(user));
            findElement(user).sendKeys(username);
            findElement(pass).sendKeys(password);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void loadTestData(String credentialValidations) {
        try {
            if (credentialValidations.equalsIgnoreCase("valid")) {
                username = configFileObject.getProperty("userName");
            } else {
                username = "abc@gmail.com";
            }
            password = configFileObject.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Override
    public void getParameterisedCredentials(String user, String pass) {
        try {
            username = configFileObject.getProperty(user);
            password = configFileObject.getProperty(pass);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}

