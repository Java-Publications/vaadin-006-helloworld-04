package junit.org.rapidpm.vaadin.junit.rules;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import junit.org.rapidpm.vaadin.helloworld.server.BaseSeleniumTest;

/**
 *
 */
public class ScreenshotTestRule implements MethodRule {

  protected Optional<WebDriver> driverOptional;

  public void setDriverOptional(Optional<WebDriver> driverOptional) {
    this.driverOptional = driverOptional;
  }

  public Statement apply(final Statement statement,
                         final FrameworkMethod frameworkMethod,
                         final Object o) {

    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        try {
          statement.evaluate();
          captureScreenshot(frameworkMethod.getName());
        } catch (Throwable t) {
          captureScreenshot(frameworkMethod.getName());
          throw t; // rethrow to allow the failure to be reported to JUnit
        }
      }

      public void captureScreenshot(String fileName) {
        driverOptional.ifPresent(driver -> {
          try {
            new File("target/surefire-reports/").mkdirs(); // Insure directory is there
            FileOutputStream out = new FileOutputStream("target/surefire-reports/screenshot-" + fileName + ".png");
            out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            out.close();
          } catch (Exception e) {
            // No need to crash the tests if the screenshot fails
            System.out.println("e = " + e);
          }
        });
      }
    };
  }
}
