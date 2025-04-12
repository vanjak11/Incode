package tests;

import java.text.Normalizer;

import org.openqa.selenium.WebDriver;
import utils.LoggerUtils;
import utils.WebDriverUtils;

public class BaseTest extends LoggerUtils {

  protected WebDriver setUpDriver(){
    return WebDriverUtils.setUpDriver();
  }

  protected void quitDriver(WebDriver driver) {
    WebDriverUtils.quitDriver(driver);
  }

  public String normalizeString(String input) {
    return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
            .toLowerCase();
  }


}
