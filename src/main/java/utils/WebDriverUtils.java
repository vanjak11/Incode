package utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class WebDriverUtils extends LoggerUtils {

  public static WebDriver setUpDriver() {
    LoggerUtils.log.debug("setUpChromeDriver()");
    WebDriver driver = null;
    String sBrowser = PropertiesUtils.getBrowser();
    String sDriverPath = PropertiesUtils.getDriverPath();
    try {
      switch (sBrowser) {
        case "chrome": {
          ChromeOptions options = new ChromeOptions();
          System.setProperty("webdriver.chrome.driver", sDriverPath);
          driver = new ChromeDriver(options);
          driver.manage().deleteAllCookies();
          break;
        }
        case "firefox": {
          driver = new FirefoxDriver();
          break;
        }
        default: {
          Assert.fail("Cannot create driver! Browser '" + sBrowser + "' is not recognized");
        }
      }
    }
    catch (Exception e) {
      Assert.fail("Cannot create driver! Error Message: " + e.getMessage());
    }
    driver.manage().window().maximize();
    setImplicitWait(driver, 10);
    return driver;
  }

  public static void quitDriver(WebDriver driver) {
    LoggerUtils.log.debug("quitDriver()");
    if (driver != null) {
      driver.quit();
    }
  }

  public static void setImplicitWait(WebDriver driver, int timeout) {
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
  }
}

