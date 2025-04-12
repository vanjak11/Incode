package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.PropertiesUtils;

public class LoginPage extends BasePage {

  private String sLoginPageUrl = PropertiesUtils.getUrl();

  @FindBy(css = "input#email")
  private WebElement usernameInput;

  @FindBy(css = "input#password")
  private WebElement passwordInput;

  @FindBy(css = "button[type='submit']")
  private WebElement submitButton;


  public LoginPage(WebDriver driver) {
    super(driver);
  }

  public LoginPage open() {
    log.debug("open()");
    openUrl(sLoginPageUrl);
    return new LoginPage(driver);
  }

  public boolean isLoginPageLoaded() {
    log.debug("isLoginPageLoaded()");
    return isElementPresent(usernameInput, Time.LONG) && isElementPresent(passwordInput, Time.LONG);
  }

  public void enterUsername(String username) {
    assert isElementPresent(usernameInput, Time.SHORT) : "Username input not present";
    typeText(usernameInput, username);
  }

  public void enterPassword(String password) {
    assert isElementPresent(passwordInput, Time.SHORT) : "Password input not present";
    typeText(passwordInput, password);
  }

  public void clickLoginButton() {
    assert isElementPresent(submitButton, Time.SHORT) : "Submit button not present";
    clickOnElement(submitButton, Time.LONG);
  }

  public DashboardPage loginToDashboard(String username, String password) {
    enterUsername(username);
    clickOnElement(passwordInput);
    waitForWebElementToBeClickable(usernameInput, Time.LONGER);
    enterPassword(password);
    clickLoginButton();
    return new DashboardPage(driver);
  }
}
