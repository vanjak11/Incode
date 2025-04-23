package tests;

import data.CommonStrings;
import data.Groups;
import org.openqa.selenium.WebDriver;

import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SessionInfoPage;
import pages.SessionsPage;
import utils.PropertiesUtils;

public class SessionsTest extends BaseTest {

  private WebDriver driver;

  /*
  Test case tests.Sessions
  1. Login to Incode Dashboard
  2. Navigate to Sessions page
  3. Observe the name of the session and click on it
  4. In the session info page, verify that name from previous step is the same as te full name - FULL NAME (OCR)
  *
  * */
  @Test(groups = {Groups.REGRESSION, Groups.SESSIONS})
  public void testSessions() {
    log.debug("[TEST] testSessionsFlow()");

    String sUserName = PropertiesUtils.getUserName();
    String sPassword = PropertiesUtils.getPassword();
    String sSessionId = CommonStrings.SessionId;

    driver = setUpDriver();
    try {

      log.debug("[TEST] Login to Incode Dashboard");
      LoginPage loginPage = new LoginPage(driver).open();
      assert loginPage.isLoginPageLoaded() : "Login page not loaded!";
      DashboardPage dashboardPage = loginPage.loginToDashboard(sUserName, sPassword);
      dashboardPage.waitUntilPageIsLoaded();

      log.debug("[TEST] Navigate to Sessions page and get name of the session from the session id");
      SessionsPage sessionsPage = dashboardPage.clickOnSessions();
      String sSessionName = sessionsPage.getSessionName(sSessionId);

      log.debug("[TEST] Click on the session name and verify that name from Session page is the same as te full name - FULL NAME (OCR)");
      SessionInfoPage sessionInfoPage = sessionsPage.clickOnSession(sSessionName);
      sessionInfoPage.verifyPageIsLoaded();
      String sFullName = sessionInfoPage.getFullName();
      assert normalizeString(sSessionName).equals(normalizeString(sFullName)) : "Session name [" + sSessionName + "] is not the same as Full Name [" + sFullName + "]";

      log.debug("[TEST] Sign out and quit the driver");
      sessionInfoPage.logOut();
    }
    finally {
      quitDriver(driver);
    }
  }
}
