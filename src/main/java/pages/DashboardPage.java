package pages;

import data.Time;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends NavigationMenuPage {

  @FindBy(css = "h1[aria-label='Incode Dashboard']")
  private WebElement header;

  public DashboardPage(WebDriver driver) {
    super(driver);
  }

  /*
   * Method is verifying is Dashboard page loaded - is header present
   * */
  public void waitUntilPageIsLoaded() {
    log.debug("waitUntilPageIsLoaded()");
    waitForWebElementToBeVisible(header, Time.LONG);
  }
}
