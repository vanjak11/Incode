package pages;

import java.util.List;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IdentitiesPage extends NavigationMenuPage {

  @FindBy(css = "button.blue")
  private WebElement exportButton;

  @FindBy(className = "identity-row")
  private List<WebElement> IdentityList;

  private final String toastMessageLocator = "//div[@class='Toastify__toast-body']/div";


  public IdentitiesPage(WebDriver driver) {
    super(driver);
  }

  public void verifyPageIsLoaded() {
    log.debug("verifyIdentityPageIsLoaded()");
    waitForWebElementToBeVisible(exportButton, Time.SHORT);
  }

  /*
   * This method is used to get the identities count.
   * */
  public int getResultCount() {
    log.debug("getResultCount()");
    int resultCount = (IdentityList != null) ? IdentityList.size() : 0;
    return resultCount;
  }

  public String getIdentityName(int index) {
    log.debug("getIdentityName(" + index + ")");
    return IdentityList.get(index).findElement(By.xpath("td[1]")).getText();
  }

  public IdentityCardPage clickOnIdentityByIndex(int index) {
    log.debug("clickOnIdentityByIndex(" + index + ")");
    WebElement element = IdentityList.get(index).findElement(By.xpath("td[1]"));
    clickOnElement(element);
    return new IdentityCardPage(driver);
  }

  public void waitForToastMessageToBeInvisable() {
    log.debug("waitForToastMessageToBeInvisable()");
    By toastMessage = By.xpath(toastMessageLocator);
    getWebDriverWait(Time.SHORT).until(ExpectedConditions.invisibilityOfElementLocated(toastMessage));
  }
}
