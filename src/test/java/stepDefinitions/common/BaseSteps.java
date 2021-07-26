package stepDefinitions.common;

import io.cucumber.java.en.Given;
import cucumberIntegrationTests.CreateSessionCucumber;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;


public class BaseSteps {
    CreateSessionCucumber createSessionCucumber;
    public WebDriver driver;
    public String platform;

    @Parameters({"os"})
    @Given("^User has slideshare app$")
    public void userHasSlideshareApp() throws Exception {

        //Use this when running via testng
        // platform= Reporter.getCurrentTestResult().getTestContext().getCurrentXMLTest().getParameter("os");
        
        //Use this when running via junit
        platform = System.getProperty("platform");
        createSessionCucumber = new CreateSessionCucumber();
        createSessionCucumber.createDriver(platform, BaseSteps.class.getDeclaredMethod("userHasSlideshareApp"));
        driver = createSessionCucumber.getWebDriver();
    }

}
