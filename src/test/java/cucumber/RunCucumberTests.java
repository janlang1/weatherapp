package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Run all the cucumber tests in the current package.
 */
@RunWith(Cucumber.class)
@CucumberOptions(strict = true, 


features = {
		//"src/test/resources/cucumber/hello.feature", 
		"src/test/resources/cucumber/input_validation.feature",
		//"src/test/resources/cucumber/newjungtests.feature", 
		"src/test/resources/cucumber/pagination.feature", 
		"src/test/resources/cucumber/radius.feature",
		"src/test/resources/cucumber/searchHistory.feature",
		//"src/test/resources/cucumber/test.feature", 
		//"src/test/resources/cucumber/uiTests.feature",
		"src/test/resources/cucumber/user_auth.feature",
		"src/test/resources/cucumber/user_data.feature"


		})
public class RunCucumberTests {

	@BeforeClass
	public static void setup() {
		WebDriverManager.chromedriver().setup();
	}
}

