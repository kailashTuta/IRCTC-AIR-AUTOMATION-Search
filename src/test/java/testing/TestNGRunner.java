package testing;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

public class TestNGRunner {
	@CucumberOptions(features = "src/test/resources/features", glue = "stepDefnitions")
	public class RunTestNGTest extends AbstractTestNGCucumberTests {

	}
}
