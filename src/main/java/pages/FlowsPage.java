package pages;

import java.util.ArrayList;
import java.util.List;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FlowsPage extends NavigationMenuPage {

  @FindBy(css = "div.flows-title button .button-with-icon")
  private WebElement createNewFlowLocator;

  @FindBy(xpath = "//table[@class = 'flows-table']/tbody/tr")
  private List<WebElement> flowList;

  @FindBy(className = "modal-dialog__header")
  private WebElement newFlowPopUp;

  @FindBy(id = "name")
  private WebElement nameInputNewFlowLocator;

  @FindBy(css = "button.modal-button")
  private WebElement nextButtonLocator;

  @FindBy(css = "div.search-div input")
  private WebElement searchInputLocator;

  @FindBy(className = "details-text")
  private WebElement detailsTextWindowLocator;

  @FindBy(css = "span.details-tooltip button")
  private WebElement detailsTextButtonLocator;

  private final String toastMessageLocator = "//div[@class='Toastify__toast-body']/div";

  public FlowsPage(WebDriver driver) {
    super(driver);
  }

  public void waitUntilPageIsLoaded() {
    log.debug("waitUntilPageIsLoaded()");
    waitForWebElementToBeVisible(createNewFlowLocator, Time.SHORT);
  }

  /*
   * This method is used to click on the "Create New Flow" button
   * */
  public void clickOnCreateNewFlowButton() {
    log.debug("clickOnCreateNewFlowButton()");
    assert isElementPresent(createNewFlowLocator, Time.SHORT) : "Create New Flow button is not present";
    clickOnElement(createNewFlowLocator);
  }

  /*
   * This method is used to check if the flow is present in the list
   * @name - name of the flow
   *
   * @return - true if flow is present in the list, false otherwise
   * */
  public boolean isFlowPresentInTheList(String name) {
    log.debug("isFlowPresentInTheList()");
    String xpath = ".//td[text() = '" + name + "']";
    for (WebElement flow : flowList) {
      if (flow.findElements(By.xpath(xpath)).size() > 0) {
        return true;
      }
    }
    return false;
  }

  /*
   * This method is used to click on the "Create New Flow" button, populate name of new flow in the New Flow window and click on "Next" button
   * @flowName - name of the new flow
   *
   * @return - SelectModulesPage object
   * */
  public SelectModulesPage clickOnCreateNewFlow(String flowName) {
    log.debug("clickOnAddNewFlow()");
    clickOnCreateNewFlowButton();
    assert isElementPresent(newFlowPopUp, Time.LONG) : "New Flow pop up is not present";
    assert isElementPresent(nameInputNewFlowLocator, Time.SHORT) : "Name input field is not present";
    typeText(nameInputNewFlowLocator, flowName);
    assert isElementPresent(nextButtonLocator, Time.SHORT) : "Next button is not present";
    clickOnElement(nextButtonLocator);
    return new SelectModulesPage(driver);
  }

  /*
   * This method is used to search for the flow in the list by typing the name in the search input field
   * @name - name of the flow
   *
   * @return - true if flow is present in the list, false otherwise
   * */
  public boolean searchForFlow(String name) {
    log.debug("searchForFlow()");
    assert isElementPresent(searchInputLocator, Time.SHORT) : "Search input field is not present";
    typeText(searchInputLocator, name);
    searchInputLocator.sendKeys(Keys.ENTER);
    return isFlowPresentInTheList(name);
  }

  /*
   * This method is waiting for toast message to be invisible after the flow is created
   * */
  public void waitForToastMessageToBeInvisable() {
    log.debug("waitForToastMessageToBeInvisable()");
    By toastMessage = By.xpath(toastMessageLocator);
    getWebDriverWait(Time.SHORT).until(ExpectedConditions.invisibilityOfElementLocated(toastMessage));
  }

  public boolean isModulesDetailsCardPresent() {
    log.debug("isModulesDetailsCardPresent()");
    return isElementPresent(detailsTextWindowLocator, Time.SHORT);
  }

  /*
   * This method is used to get the list of modules in the flow
   * It hovers over Details Text button for searched flow and then pick up the modules from the details card
   *
   * @return - list of modules in the flow
   * */
  public List<String> getModulesInFlow() {
    log.debug("getModulesInFlow()");
    List<String> modules = new ArrayList<>();
    Actions actions = new Actions(driver);
    actions.moveToElement(detailsTextButtonLocator).perform();
    assert isModulesDetailsCardPresent() : "Modules details card is not present";
    List<WebElement> moduleElements = detailsTextWindowLocator.findElements(By.tagName("p"));
    for (WebElement element : moduleElements) {
      String text = element.getText().replaceFirst("^\\d+\\.\\s*", "").trim();;
      if (!text.isEmpty()) {
        modules.add(text);
      }
    }
    return modules;
  }
}
