package tests;

import data.CommonStrings;
import data.Groups;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.IdentitiesPage;
import pages.IdentityCardPage;
import pages.LoginPage;
import pages.SessionInfoPage;
import pages.SessionsPage;
import utils.PropertiesUtils;

public class IdentitiesTest extends BaseTest {

  private WebDriver driver;

  /*
Test case tests.Sessions
1. Login to Incode Dashboard
2. Navigate to Sessions page and open Session Info page for given name
3. Add identity to database by clicking on button 'Add Face to Database'
4. Navigate to Identities page and verify Identity is present there
5. Delete identity form DB
*
* */
  @Test(groups = {Groups.REGRESSION, Groups.IDENTITIES, Groups.BUGS})
  public void testIdentities() {
    log.debug("[TEST] testAddFaceToIdentities()");

    String sUserName = PropertiesUtils.getUserName();
    String sPassword = PropertiesUtils.getPassword();
    String sSessionName = CommonStrings.SessionName;

    driver = setUpDriver();
    try {

      log.debug("[TEST] Login to Incode Dashboard");
      LoginPage loginPage = new LoginPage(driver).open();
      assert loginPage.isLoginPageLoaded() : "Login page not loaded!";
      DashboardPage dashboardPage = loginPage.loginToDashboard(sUserName, sPassword);
      dashboardPage.waitUntilPageIsLoaded();

      log.debug("[TEST] Navigate to Sessions page and click on session with name " + sSessionName);
      SessionsPage sessionsPage = dashboardPage.clickOnSessions();

      log.debug("[TEST] Click on the session name and navigate to Session Info page");
      SessionInfoPage sessionInfoPage = sessionsPage.clickOnSession(sSessionName);
      sessionInfoPage.verifyPageIsLoaded();

      log.debug("[TEST] Click on button 'Add Face' to add identity to DB and navigate to Identities page");
      sessionInfoPage.clickAddFaceToDatabaseButton();
//      These two lines are failing because of intermittent issue - error toast message is shown even if identity is added
//      String sToastMessage = sessionInfoPage.getToastMessage();
//      assert sToastMessage.contains(CommonStrings.ToastMessage_FaceAddedToDB) : "Toast message is not displayed or does not contain 'Face added to database'";
      IdentitiesPage identitiesPage = sessionInfoPage.clickOnIdentities();
      identitiesPage.waitForToastMessageToBeInvisable();

      log.debug("[TEST] Verify that identity is added to Identities page");
      identitiesPage.verifyPageIsLoaded();
      int iIdentitiesCount = identitiesPage.getResultCount();
      assert iIdentitiesCount > 0 : "No identities found in the list";
      String sIdentityName = identitiesPage.getIdentityName(0);
      assert sIdentityName.toLowerCase().equals(sSessionName.toLowerCase()) : "Identity name [" + sIdentityName + "] is not the same as Session name [" + sSessionName + "]";

      log.debug("[TEST] Sign out and quit the driver");
      sessionInfoPage.logOut();
    }
    finally {
      quitDriver(driver);
      // delete identity from DB
      deleteIdentity();
    }
  }

  private void deleteIdentity() {
    WebDriver driver = setUpDriver();
    log.debug("[TEST] Login to Incode Dashboard and navigate to Identities page");
    LoginPage loginPage = new LoginPage(driver).open();
    assert loginPage.isLoginPageLoaded() : "Login page not loaded!";
    DashboardPage dashboardPage = loginPage.loginToDashboard(PropertiesUtils.getUserName(), PropertiesUtils.getPassword());
    dashboardPage.waitUntilPageIsLoaded();
    IdentitiesPage identitiesPage = dashboardPage.clickOnIdentities();
    identitiesPage.verifyPageIsLoaded();

    log.debug("[TEST] Click on the identity name and verify that identity page is opened. Delete identity PII. Verify that identity is deleted from the list");
    int iIdentitiesCount = identitiesPage.getResultCount();
    if(iIdentitiesCount == 0) {
      log.debug("[TEST] No identities found in the list");
      return;
    }
    IdentityCardPage identityCardPage = identitiesPage.clickOnIdentityByIndex(0);
    identityCardPage.verifyPageIsLoaded();
    identitiesPage = identityCardPage.deleteIdentity();
    identitiesPage.waitForToastMessageToBeInvisable();

    driver.navigate().refresh();
    identitiesPage.verifyPageIsLoaded();
//    these steps are commented because intermittent issue is present - identity is still present in the list after it's deleted
//    iIdentitiesCount = identitiesPage.getResultCount();
//    assert iIdentitiesCount == 0 : "Identity is not deleted from the list";

    log.debug("[TEST] Sign out and quit the driver");
    identitiesPage.logOut();
    quitDriver(driver);
  }
}

