package junit.org.rapidpm.vaadin.helloworld.server;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.lang.Thread.sleep;

/**
 *
 */
public class MyUITest extends BaseSeleniumTest {

  @Test
  public void test001() throws Exception {

    final WebDriver webDriver = driver
        .orElseThrow(
            () -> new RuntimeException("WebDriver not available"));

    webDriver.get("http://127.0.0.1:8080/");

    inputA.get().sendKeys("5");
//    sleep(2_000); // for demo only
    inputB.get().sendKeys("5");
//    sleep(2_000); // for demo only
    button.get().click();
//    sleep(2_000); // for demo only
    String value = output.get().getAttribute("value");

    takeScreenShot();

    Assert.assertEquals("55", value);
  }
}
