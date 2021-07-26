import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        features = "classpath:cucumberTests/features",
        glue = {"classpath:stepDefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports/report.html"},
        stepNotifications = true,
        publish = true

)
public class CucumberRunnerUtil extends AbstractTestNGCucumberTests {

}
