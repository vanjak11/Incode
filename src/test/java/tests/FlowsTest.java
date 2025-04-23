package tests;

import java.util.Arrays;
import java.util.List;

import data.Groups;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.FlowsPage;
import pages.LoginPage;
import pages.SelectModulesPage;
import utils.PropertiesUtils;

public class FlowsTest extends BaseTest {

  private WebDriver driver;

  /*
Test case Sessions
1. Login to Incode Dashboard
2. Navigate to Flows page and click on create new button
3. Add  flow name and click on 'Continue' button
4. In Select Modules page select given modules and click on 'Save' button
5. Verify flow is present in the flow list
*
* */
  @Test(groups = {Groups.REGRESSION, Groups.FLOWS})
  public void testFlows() {
    log.debug("[TEST] testCreateNewFlow()");

    String sUserName = PropertiesUtils.getUserName();
    String sPassword = PropertiesUtils.getPassword();
    String sFlowName = "Test Flow" + System.currentTimeMillis();
    List<String> modules = Arrays.asList("ID Capture", "ID Validation", "Face Capture");

    driver = setUpDriver();
    try {

      log.debug("[TEST] Login to Incode Dashboard");
      LoginPage loginPage = new LoginPage(driver).open();
      assert loginPage.isLoginPageLoaded() : "Login page not loaded!";
      DashboardPage dashboardPage = loginPage.loginToDashboard(sUserName, sPassword);
      dashboardPage.waitUntilPageIsLoaded();

      log.debug("[TEST] Navigate to Flows page and click on 'Create new' button");
      FlowsPage flowsPage = dashboardPage.clickOnFlows();
      flowsPage.waitUntilPageIsLoaded();

      log.debug("[TEST] Add flow name " + sFlowName + " and click on 'Continue' button");
      SelectModulesPage selectModulesPage = flowsPage.clickOnCreateNewFlow(sFlowName);

      log.debug("[TEST] In Select Modules page select modules " + modules + " and click on 'Save' button");
      selectModulesPage.waitUntilPageIsLoaded();
      selectModulesPage.selectModules(modules);
      flowsPage = selectModulesPage.clickSaveFlowButton();
      flowsPage.waitForToastMessageToBeInvisable();

      log.debug("[TEST] Verify flow is present in the flow list");
      assert flowsPage.searchForFlow(sFlowName) : "Flow is not present in the flow list";

      log.debug("[TEST] Verify created flow contains selected modules");
      List<String> modulesInFlow = flowsPage.getModulesInFlow();
      assert modulesInFlow.containsAll(modules) : "Flow does not contain selected modules";

      flowsPage.logOut();
    }
    finally {
      quitDriver(driver);
    }
  }
}