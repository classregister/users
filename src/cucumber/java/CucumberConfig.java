import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/cucumber/resources")
public class CucumberConfig {

    public static final String URL = "http://localhost:8000/users/";
}
