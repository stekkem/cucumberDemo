package test.java.testRun;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(

        features = {"src/test/java/features"},
        glue = {"/test/java/stepDefinitions"},
        tags = "@mytest",
        plugin = {
                "pretty",
                "html:/Users/stekkem/Documents/Reports/report.html",
                //"json:/Users/stekkem/Documents/Reports/report.json",
                //"junit:/Users/stekkem/Documents/Reports/report.xml",
        },
        publish = true,
        monochrome = true,
        //strict = true,
        dryRun = false

    )

public class runTest {

}
