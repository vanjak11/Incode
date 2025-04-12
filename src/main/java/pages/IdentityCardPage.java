package pages;

import java.time.Duration;

import data.CommonStrings;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IdentityCardPage extends NavigationMenuPage {

  @FindBy(className = "card_component__title")
  private WebElement cardTitleLocator;

  @FindBy(className = "identities-menu-list-button")
  private WebElement identitiesMenuLocator;

  @FindBy(css = "ul.menu-list__list div.tooltip-wrapper")
  private WebElement deleteIdentityPIIDataButtonLocator;

  @FindBy(className = "modal-dialog__body")
  private WebElement confirmDeleteIdentityPIIDataPopUp;

  @FindBy(css = "div.modal-buttons button.red")
  private WebElement confirmDeleteIdentityPIIDataButtonLocator;

  private final String toastMessageLocator = "//div[@class='Toastify__toast-body']/div";

  public IdentityCardPage(WebDriver driver) {
    super(driver);
  }

  /*
   * This method is used to verify if the Identity Card page is loaded by checking the presence of the card title.
   * */
  public void verifyPageIsLoaded() {
    log.debug("verifyIdentityCardPageIsLoaded()");
    waitForWebElementToBeVisible(cardTitleLocator, 5);
  }

  public void clickMenuButton() {
    log.debug("clickMenuButton()");
    assert isElementPresent(identitiesMenuLocator, Time.SHORT) : "Identities menu button is not present";
    clickOnElement(identitiesMenuLocator);
  }

  public void clickDeleteIdentityPIIDataButton() {
    log.debug("clickDeleteIdentityPIIDataButton()");
    assert isElementPresent(deleteIdentityPIIDataButtonLocator, Time.SHORT) : "Delete Identity PII button is not present";
    clickOnElement(deleteIdentityPIIDataButtonLocator);
  }

  /*
   * This method is used to delete the identity PII data by clicking the 'Delete' button from the menu.
   * After deletion is confirmed in pop-up, it waits for the toast message to appear.
   *
   * @return IdentitiesPage - The page object for the Identities page.
   * */
  public IdentitiesPage deleteIdentity() {
    log.debug("deleteIdentity()");
    clickMenuButton();
    clickDeleteIdentityPIIDataButton();
    confirmDeleteIdentityPIIData();
    String sDeleteToastMessage = getToastMessage();
    assert sDeleteToastMessage.contains(CommonStrings.ToastMessage_IdentityPIIRemoved) : "Toast message is not displayed or does not contain 'Identity PII data removed'";
    return new IdentitiesPage(driver);
  }

  public boolean isDeleteIdentityPopUpPresent() {
    log.debug("isDeleteIdentityPopUpPresent()");
    return isElementPresent(confirmDeleteIdentityPIIDataPopUp, Time.SHORT);
  }

  /*
   * This method is used to confirm the deletion of identity PII data by clicking the 'Delete' button in the pop-up.
   * */
  public void confirmDeleteIdentityPIIData() {
    log.debug("confirmDeleteIdentityPIIData()");
    assert isDeleteIdentityPopUpPresent() : "Confirm Delete Identity PII button is not present";
    clickOnElement(confirmDeleteIdentityPIIDataButtonLocator);
  }

  /*
   * This method is used to get the toast message after deleting the identity PII data.
   *
   * @return String - The toast message text.
   * */
  public String getToastMessage() {
    log.debug("getToastMessage()");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Time.SHORT));
    By toastMessageDiv = By.xpath(toastMessageLocator);

    WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessageDiv));
    String toastMessageText = toastMessage.getText();
    return toastMessageText;
  }
}
