package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationMenuPage extends BasePage {

  @FindBy(css = "ul.links > li > a[href*='sessions']")
  private WebElement sessionsLink;

  @FindBy(css = "ul.links > li > a[href*='identities']")
  private WebElement identitiesLink;

  @FindBy(css = "ul.links > li > a[href*='flows']")
  private WebElement flowsLink;

  @FindBy(css = "ul.links > li > a[href*='users']")
  private WebElement usersLink;

  @FindBy(className = "support-nav")
  private WebElement supportNavButton;

  @FindBy(className = "logout")
  private WebElement logoutButton;

  public NavigationMenuPage(WebDriver driver) {
    super(driver);
  }

  public SessionsPage clickOnSessions() {
    log.debug("clickOnSessions()");
    assert isElementPresent(sessionsLink, Time.SHORT) : "Sessions Link not present";
    clickOnElement(sessionsLink);
    return new SessionsPage(driver);
  }

  public void clickOnUsers() {
    log.debug("clickOnUsers()");
    assert isElementPresent(usersLink, Time.SHORT) : "Users Link not present";
    clickOnElement(usersLink);
  }

  public IdentitiesPage clickOnIdentities() {
    log.debug("clickOnIdentities()");
    assert isElementPresent(identitiesLink, Time.SHORT) : "Identities Link not present";
    clickOnElement(identitiesLink);
    return new IdentitiesPage(driver);
  }

  public FlowsPage clickOnFlows() {
    log.debug("clickOnFlows()");
    assert isElementPresent(flowsLink, Time.SHORT) : "Flows Link not present";
    clickOnElement(flowsLink);
    return new FlowsPage(driver);
  }

  /*
   * This method clicks on three dots to expand the support nav menu.
   * */
  public void clickSupportNavButton() {
    log.debug("clickSupportNavButton()");
    assert isElementPresent(supportNavButton, Time.SHORT) : "Support Nav button not present";
    clickOnElement(supportNavButton);
  }

  public void clickLogoutButton() {
    log.debug("clickLogoutButton()");
    assert isElementPresent(logoutButton, Time.SHORT) : "Logout button not present";
    clickOnElement(logoutButton, Time.SHORT);
  }

  /*
   * This method logs out the user by clicking on the support nav button and then clicking the logout button.
   * */
  public void logOut() {
    log.debug("logOut()");
    clickSupportNavButton();
    clickLogoutButton();
  }
}
