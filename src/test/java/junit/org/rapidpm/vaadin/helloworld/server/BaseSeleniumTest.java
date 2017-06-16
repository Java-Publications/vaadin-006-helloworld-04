package junit.org.rapidpm.vaadin.helloworld.server;

import static java.util.Optional.ofNullable;
import static org.openqa.selenium.By.id;
import static org.rapidpm.vaadin.helloworld.server.MyUI.BUTTON_ID;
import static org.rapidpm.vaadin.helloworld.server.MyUI.INPUT_ID_A;
import static org.rapidpm.vaadin.helloworld.server.MyUI.INPUT_ID_B;
import static org.rapidpm.vaadin.helloworld.server.MyUI.OUTPUT_ID;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import junit.org.rapidpm.vaadin.junit.rules.ScreenshotTestRule;

/**
 *
 */
public class BaseSeleniumTest extends BaseTest {

  protected Optional<WebDriver> driver;

//  @Rule
//  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule();

//  public Optional<WebDriver> driver() {
//    System.out.println("driver = " + driver);
//    return (driver == null)
//           ? Optional.empty()
//           : driver;
//  }

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();

    // init webDriver here
    System.setProperty("webdriver.chrome.driver", "_data/chromedriver");

//    ChromeOptions chromeOptions = new ChromeOptions();
//    chromeOptions.addArguments("window-size=1024,768");

    DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
//    chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

    driver = Optional.of(new ChromeDriver(chromeCapabilities));
//    driver = Optional.of(new ChromeDriver());

//    screenshotTestRule.setDriverOptional(driver);
    //TODO setzen der Bildschirmgröße
    driver.ifPresent(d -> {
      d.manage().window().maximize();
//      d.manage().window().setSize(new Dimension(1024, 768));
    });
    //final WebDriverWait wait = new WebDriverWait(driver, 10);
  }


  @Override
  @After
  public void tearDown() throws Exception {
    // kill webdriver / Browser here
    driver.ifPresent(d -> {
      d.close();
      d.quit();
    });
    driver = Optional.empty();
    super.tearDown();
  }


  //generic version - need it later
  public BiFunction<WebDriver, String, Optional<WebElement>> elementFor
      = (driver, id) -> ofNullable(driver.findElement(id(id)));

  //localized version
  private Function<String, WebElement> element
      = (id) -> driver
      .flatMap(driverOptional -> elementFor.apply(driverOptional, id))
      .orElseThrow(()-> new RuntimeException("WebElement with the ID " + id +" is not available"));

  protected Supplier<WebElement> button = () -> element.apply(BUTTON_ID);
  protected Supplier<WebElement> output = () -> element.apply(OUTPUT_ID);
  protected Supplier<WebElement> inputA = () -> element.apply(INPUT_ID_A);
  protected Supplier<WebElement> inputB = () -> element.apply(INPUT_ID_B);




  protected void takeScreenShot() {
    //take Screenshot
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      //TODO check if possible
      outputStream.write(((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.BYTES));
      //write to target/screenshot-[timestamp].jpg
      final FileOutputStream out = new FileOutputStream("target/screenshot-" + LocalDateTime.now() + ".png");
      out.write(outputStream.toByteArray());
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
