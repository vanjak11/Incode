package pages;

import java.time.Duration;
import java.util.List;

import data.CommonStrings;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectModulesPage extends NavigationMenuPage {

  @FindBy(className = "flow-info")
  private WebElement flowInfoLocator;

  @FindBy(xpath = "//div[contains(@class,'table-modules-row')]")
  private List<WebElement> modulesList;

  @FindBy(css = "button.module-configuration-button")
  private WebElement addModuleButtonLocator;

  @FindBy(className = "modal-dialog-close")
  private WebElement closeModalButtonLocator;

  @FindBy(css = "div.title__buttons button.green")
  private WebElement saveFlowButtonLocator;

  private final String toastMessageLocator = "//div[@class='Toastify__toast-body']/div";

  public SelectModulesPage(WebDriver driver) {
    super(driver);
  }

  public void waitUntilPageIsLoaded() {
    log.debug("waitUntilPageIsLoaded()");
    waitForWebElementToBeVisible(flowInfoLocator, Time.SHORT);
  }

  /*
   * This method is used to select modules from the list on Select Modules page
   * It fins modules by name and clicks on them, then clicks on the "Add Module" button and then close the modal
   * @param modules - list of modules to be selected
   * */
  public void selectModules(List<String> modules) {
    log.debug("selectModules()");
    for (String module : modules) {
      By moduleLocator = By.xpath("//div[contains(@class,'table-modules-row')]//span[text() = '" + module + "']");
      WebElement moduleElement = getWebElement(moduleLocator, Time.LONG);
      clickOnElement(moduleElement, Time.SHORT);
      clickAddModuleButton();
      clickCloseModalButton();
    }
  }

  /*
   * This method is used to click on the "Add Module" button
   * */
  public void clickAddModuleButton() {
    log.debug("clickAddModuleButton()");
    waitForWebElementToBeVisible(addModuleButtonLocator, Time.SHORT);
    assert isElementPresent(addModuleButtonLocator, Time.LONG) : "Add Module button is not present";
    clickOnElement(addModuleButtonLocator);
  }

  public void clickCloseModalButton() {
    log.debug("clickCloseModalButton()");
    assert isElementPresent(closeModalButtonLocator, Time.SHORT) : "Close Modal button is not present";
    clickOnElement(closeModalButtonLocator);
  }

  /*
   * This method is used to click on the "Save Flow" button, verify the toast message and return to Flows page
   * */
  public FlowsPage clickSaveFlowButton() {
    log.debug("clickSaveFlowButton()");
    assert isElementPresent(saveFlowButtonLocator, Time.SHORT) : "Save Flow button is not present";
    clickOnElement(saveFlowButtonLocator);
    String sFlowSavedToastMessage = getToastMessage();
    assert sFlowSavedToastMessage.contains(CommonStrings.ToastMessage_FlowAdded) : "Toast message is not displayed or does not contain 'Identity PII data removed'";
    return new FlowsPage(driver);
  }

  /*
   * This method retrieves the toast message after clicking the "Save Flow" button.
   * It waits for button to be disabled before returning the message.
   *
   * @return String - the text of the toast message.
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
