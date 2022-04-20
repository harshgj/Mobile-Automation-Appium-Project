import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        features = "src/test/java/cucumberTests/features",
        tags = "@login",
        glue = {"cucumberIntegrationTests/stepDefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports/report.html"},
        stepNotifications = true,
        publish = true

)
public class CucumberRunnerUtilTest {

}
