package pages;

import java.time.Duration;

import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SessionInfoPage extends NavigationMenuPage {

  @FindBy(css = "div.session-action > button")
  private WebElement addFaceToDatabaseButton;

  @FindBy(className = "card_component__title")
  private WebElement cardComponentTitle;

  private final String sOCRSessionInfoXPath = "//div[@class='card__inner id-info']//span[text()='Full Name (OCR)']";
  private final String toastMessageLocator = "//div[@class='Toastify__toast-body']/div";


  public SessionInfoPage(WebDriver driver) {
    super(driver);
  }

  /*
   * This method verifies that the Session Info page is loaded by checking the presence of the card component title.
   * */
  public void verifyPageIsLoaded() {
    log.debug("verifyPageIsLoaded()");
    assert isElementPresent(cardComponentTitle, Time.LONG) : "Session Info page not loaded";
  }

  /*
   * This method retrieves the full name from the session info page.
   * */
  public String getFullName() {
    log.debug("getFullName()");
    WebElement fullNameElement = getWebElement(By.xpath(sOCRSessionInfoXPath), Time.SHORT);
    String fullName = fullNameElement.findElement(By.xpath("following-sibling::span")).getText();
    return fullName;
  }

  /*
   * This method clicks on the "Add Face to Database" button.
   * */
  public void clickAddFaceToDatabaseButton() {
    log.debug("clickAddFaceToDatabaseButton()");
    assert isElementPresent(addFaceToDatabaseButton, Time.SHORT) : "Add Face to Database button not present";
    clickOnElement(addFaceToDatabaseButton);
  }

  /*
   * This method retrieves the toast message after clicking the "Add Face to Database" button.
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
    assert waitForWebElementToBeDisabled(addFaceToDatabaseButton, Time.LONG);
    return toastMessageText;
  }
}
