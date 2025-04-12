package pages;

import java.util.List;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SessionsPage extends NavigationMenuPage {

  @FindBy(className = "session-row")
  private List<WebElement> sessionsList;

  @FindBy(css = "  div.menu-list div[data-testid='tooltip-wrapper']")
  private WebElement menuListButton;

  private final String addSessionButtonCSS = "g#ButtonIcon_add";

  public SessionsPage(WebDriver driver) {
    super(driver);
  }

  /*
   * This method is used to click on a session in the sessions list.
   * It takes the session name as a parameter and clicks on the corresponding session.
   *
   * @param name The name of the session to click on.
   * @return A new instance of SessionInfoPage.
   * */
  public SessionInfoPage clickOnSession(String name) {
    log.debug("clickOnSession(" + name + ")");
    String xpath = "//tr[@class='session-row']//td[contains(text(),'" + name + "')]";
    WebElement element = getWebElement(By.xpath(xpath), Time.SHORT);
    clickOnElement(element);
    return new SessionInfoPage(driver);
  }

  /*
   * This method is used to get the session ID of a session based on its name.
   * */
  public String getSessionId(String name) {
    log.debug("getSessionId(" + name + ")");
    String xpath = "//tr[@class='session-row']//td[contains(text(),'" + name + "')]";
    WebElement element = getWebElement(By.xpath(xpath), Time.SHORT);
    String sessionId = element.findElement(By.xpath("parent::tr/td[2]")).getText();
    return sessionId;
  }

  /*
   * This method is used to get the session name based on the session ID.
   * */
  public String getSessionName(String sessionId) {
    String xpath = "//tr[@class='session-row']//td[contains(text(),'" + sessionId + "')]";
    WebElement element = getWebElement(By.xpath(xpath), Time.SHORT);
    String sessionName = element.findElement(By.xpath("following::td[2]")).getText();
    return sessionName;
  }

  /*
   * This method is used to get the session count.
   * */
  public int getResultCount() {
    log.debug("getResultCount()");
    int resultCount = (sessionsList != null) ? sessionsList.size() : 0;
    return resultCount;
  }
}
