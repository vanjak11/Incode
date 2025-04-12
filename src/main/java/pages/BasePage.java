package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerUtils;

public class BasePage extends LoggerUtils {

  protected WebDriver driver;

  public BasePage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public WebDriverWait getWebDriverWait(int timeout) {
    log.trace("getWebDriverWait(" + timeout + ")");
    return new WebDriverWait(driver, Duration.ofSeconds(timeout));
  }

  public void openUrl(String url) {
    log.trace("openUrl(" + url + ")");
    driver.get(url);
  }

  public WebElement getWebElement(By locator, int timeout) {
    log.trace("getWebElement(" + locator + " , " + timeout + ")");
    WebDriverWait wait = getWebDriverWait(timeout);
    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    return element;
  }

  public List<WebElement> getWebElements(By locator) {
    log.trace("getWebElements(" + locator + ")");
    return driver.findElements(locator);
  }

  public boolean isElementPresent(WebElement element, int timeout) {
    log.trace("isElementPresent(" + element + ", " + timeout + ")");
    try {
      return element.isDisplayed();
    }
    catch (Exception e) {
      return false;
    }
  }

  public WebElement waitForWebElementToBeClickable(WebElement element, int timeout) {
    log.trace("waitForWebElementToBeVisible(" + element + ", " + timeout + ")");
    WebDriverWait wait = getWebDriverWait(timeout);
    return wait.until(ExpectedConditions.elementToBeClickable(element));
  }

  public WebElement waitForWebElementToBeVisible(WebElement element, int timeout) {
    log.trace("waitForWebElementToBeVisible(" + element + ", " + timeout + ")");
    WebDriverWait wait = getWebDriverWait(timeout);
    return wait.until(ExpectedConditions.visibilityOf(element));
  }

  public boolean waitForWebElementToBeDisabled(WebElement element, int timeout) {
    log.trace("waitForWebElementToBeDisabled(" + element + ", " + timeout + ")");
    WebDriverWait wait = getWebDriverWait(timeout);
    return wait.until(ExpectedConditions.attributeToBe(element, "disabled", "true"));
  }

  public boolean waitForWebElementToBeInvisible(WebElement element, int timeout) {
    log.trace("waitForWebElementToBeInvisible(" + element + ", " + timeout + ")");
    WebDriverWait wait = getWebDriverWait(timeout);
    return wait.until(ExpectedConditions.invisibilityOf(element));
  }

  public void clickOnElement(WebElement element) {
    log.trace("clickOnElement(" + element + ")");
    element.click();
  }

  public void clickOnElement(WebElement element, int timeout) {
    log.trace("typeText(" + element + ", " + timeout + ")");
    WebElement webElement = waitForWebElementToBeClickable(element, timeout);
    clickOnElement(webElement);
  }

  public String getAttributeFromWebElement(WebElement element, String attribute) {
    log.trace("getAttributeFromWebElement(" + element + ", " + attribute + ")");
    return element.getAttribute(attribute);
  }

  public void typeText(WebElement element, String text) {
    log.trace("typeText(" + element + ", " + text + ")");
    element.clear();
    element.sendKeys(text);
  }

  public void selectFromDropdown(WebElement element, String text) {
    log.trace("selectFromDropdown(" + element + ", " + text + ")");
    element.click();
    List<WebElement> options = getWebElements(By.xpath("//li[contains(text(),'" + text + "')]"));
    if (options.size() > 0) {
      options.get(0).click();
    }
    else {
      log.error("Option not found: " + text);
    }
  }
}
