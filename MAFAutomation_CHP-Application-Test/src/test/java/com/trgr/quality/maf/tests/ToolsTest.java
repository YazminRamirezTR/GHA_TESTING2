package com.trgr.quality.maf.tests;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.DeliveryPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.ObligacionesPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
import com.trgr.quality.maf.pages.ToolsPage;
import org.json.simple.JSONArray;

/**
 * Class for running Tools Test cases This class defines methods for verifying
 * 'Tools' test cases
 * 
 * @author Sarath Manoharam
 * @version 1.0
 * @since December 13, 2016
 */
public class ToolsTest extends BaseTest {

	/*
	 * Tools_TC_001: Verify the display of tools tab in the Menu bar.
	 * Tools_TC_002: Verify the tools tab in the Menu bar. Tools_TC_003: Verify
	 * the expand feature in the tools page Tools_TC_004: Verify the collapse
	 * feature in the tools page
	 */

	ToolsPage toolspage;
	LoginPage loginpage;
	HomePage homepage;
	DeliveryPage deliverypage;
	SearchResultsPage searchResultsPage;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	ObligacionesPage obligacionesPage;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void startToolsTest() throws Exception {
		try {

			loginpage = new LoginPage(driver, ProductUrl);
			homepage = loginpage.Login(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username"),
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password"));
			toolspage = homepage.openToolsPage();
			jsonReader = new JsonReader();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Tools", "startToolsTest");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the Tools test are not run.<br>"
							+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}
	}

	@AfterClass(alwaysRun = true)
	public void endToolsTest(){
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
		}
	}

	
	@Test(priority = 0, groups = { "chparg", "chpmex", "chpbr", "chppe", "chpchile",
			"chpury" }, description = "MAFQABANG-146")
	public void DisplayToolsTab() throws Exception {

		boolean toolsTabDisplayed = false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify the display of Tools tab in menu bar");

			// Verify Tools Tab
			toolsTabDisplayed = toolspage.isToolsTabDisplayed();
			softas.assertTrue(toolsTabDisplayed, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, toolsTabDisplayed, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 1, groups = { "chparg", "chpmex", "chpbr", "chppe", "chpchile","chpury" }, description = "MAFQABANG-147")
	public void WidgetsInToolstab() throws Exception {

		boolean widgetsDisplayed = false;

		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify the Tools tab in menu bar - Verify tools widgets");

			toolspage = homepage.openToolsPage();
			// Verify Tools Widgets
			widgetsDisplayed = toolspage.isAllWidgetsDisplayed();
			softas.assertTrue(widgetsDisplayed, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, widgetsDisplayed, issueSummary, jiraNumber);
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 2, groups = { "chparg", "chpmex", "chpbr", "chpury", "chppe","chpchile" 
			}, description = "MAFQABANG-149")
	
	public void CollapseAllWidgets() throws Exception {

		boolean allWidgetsCollapsed = true;

		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify the Collapse (-symbol) in the tool page : Collapse All widgets");

			toolspage = homepage.openToolsPage();
			// Verify Tools Collapse Widgets
			allWidgetsCollapsed = toolspage.collapseAllWidgets();
			softas.assertTrue(allWidgetsCollapsed, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, allWidgetsCollapsed, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 3, groups = { "chparg", "chpmex", "chpbr", "chppe", "chpchile",
			"chpury" }, description = "MAFQABANG-148")
	public void ExpandAllWidgets() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());
			boolean allWidgetsExpanded = false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify the expand (+ symbol) in the tool page : Expand All widgets");

			toolspage = homepage.openToolsPage();
			toolspage.collapseAllWidgets();
			// Verify Tools Expand Widgets
			allWidgetsExpanded = toolspage.expandAllWidgets();
			softas.assertTrue(allWidgetsExpanded, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, allWidgetsExpanded, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// MAFAUTO-217
	@Test(priority = 4, groups = { "chpmex" }, description = "MAFAUTO-217")
	public void CreateShortcut() throws Exception {
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Primera Hora - Direct Access (Tools Verification on Create Shortcut)");

			toolspage = homepage.openToolsPage();
			boolean iscreateShortclickable = toolspage.validateCreateShortcutinToolsTab();
			softas.assertTrue(iscreateShortclickable, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, iscreateShortclickable, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * Verify DailyExpirationTool : MAFAUTO-208
	 */
	@Test(priority = 5, groups = { "chpmex" }, description = "MAFAUTO-208")
	public void DailyExpirationTool() throws Exception {

		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Tools - TIP - Por Vencimiento - Search Template (Daily Expiration Tools)");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String expirationDateKey = "expirationdate";
				String expirationDate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expirationDateKey,
						extentLogger);

				toolspage = homepage.openToolsPage();

				// Validate the 'Daily Expiration Tool' creation page
				boolean toolPageValidated = false;
				if (toolspage.isDailyExpirationToolAvailable()) {
					toolspage.clickDailyExpirationTool();
					toolPageValidated = toolspage.isDailyExpirationHeaderDisplayed()
							&& toolspage.isDailyExpirationEditBoxDisplayed();
				}

				softas.assertTrue(toolPageValidated,
						jiraNumber + ":" + issueSummary + " : Validated the display of DailyExpirationTool");
				logExtentStatus(extentLogger, toolPageValidated, "Validated the display of DailyExpirationTool",
						jiraNumber);

				// Validate clean button for Tools creation page.
				boolean cleanButtonValidated = false;
				toolspage.setExpirationDate(expirationDate);
				toolspage.clickClearDailyExpirationDate();
				cleanButtonValidated = toolspage.getExpirationDate().equals("");

				softas.assertTrue(cleanButtonValidated, jiraNumber + ":" + issueSummary + " : Clean Alert Validated");
				logExtentStatus(extentLogger, cleanButtonValidated, issueSummary + " : Clean Alert Validated",
						jiraNumber);

				// Validate creating 'Daily Expiration' & verify the display of
				// tools table.
				boolean toolCreated = false;
				toolspage.setExpirationDate(expirationDate);
				toolspage.clickCreateDailyExpiration();

				toolCreated = toolspage.isDailyExpirationTableDisplayed();

				if (!toolCreated) {
					boolean noResultsFound = toolspage.isNoDocsFoundErrorDisplayedInDailyExpirationTool();
					if (noResultsFound) {
						logExtentNoResultsAsInfo(extentLogger, issueSummary, expirationDateKey,
								expirationDate + " -resulted in no search results", jiraNumber);
						continue; // Skip this & Continue with next iteration
					}
				}

				softas.assertTrue(toolCreated, jiraNumber + ":" + issueSummary
						+ " : Validated the display of Daily expiration tool - Smart Table");
				logExtentStatus(extentLogger, toolCreated,
						issueSummary + " : Validate the display of Daily expiration tool - Smart Table",
						expirationDateKey, expirationDate, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 6, groups = { "chparg" }, description = "MAFQABANG-610")
	public void recommendWithNoInputInFiscalNode() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());
			boolean expectedfieldspresent=false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify recommend in Herramientas - Checklist/Fiscal with no input");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String popuptext = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "popuptext", extentLogger);
				String fiscallinks = "fiscalnodelinks";
				JSONArray linkslist = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, fiscallinks,
						extentLogger);
				
				toolspage = homepage.openToolsPage();
				
				for (Object link : linkslist) {
					String linkname = link.toString();
					
					if (toolspage.isCheckListWidgetcollapsed()) {
						toolspage.expandCheckListWidget();
					}
					toolspage.expandFiscalNode();
					toolspage.clickOnGivenLink(linkname);
					JSONArray expectedtextfieldslist = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, "expectedtextfields",
							extentLogger);
					for (Object textfields : expectedtextfieldslist) {
						String expectedtextfield = textfields.toString();
						
					 expectedfieldspresent = toolspage.isTextFieldsPresent(expectedtextfield);
					}
					softas.assertTrue(expectedfieldspresent,
							jiraNumber + ":" + "Expected text fields are displayed ->" + linkname);
					logExtentStatus(extentLogger, expectedfieldspresent, "Expected text fields are displayed -> " + linkname,
							jiraNumber);
					
					boolean expectedradiobuttonspresent =toolspage.isRadioButtonsPresent();
					softas.assertTrue(expectedradiobuttonspresent,
							jiraNumber + ":" + "Expected radio buttons  are displayed ->" + linkname);
					logExtentStatus(extentLogger, expectedradiobuttonspresent, "Expected radio buttons  are displayed -> " + linkname,
							jiraNumber);

					toolspage.clickOnRecommendButton();
					boolean istextdisplayed = toolspage.isRecommendPopUpTextDisplayed(popuptext);
					toolspage.clickOnRecommendPopupClose();
					softas.assertTrue(istextdisplayed, jiraNumber + ":" + "Pop Up Displayed ->" + linkname);
					logExtentStatus(extentLogger, istextdisplayed, "Pop Up Displayed ->" + linkname, jiraNumber);
					
				}
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority = 6, groups = { "chparg" }, description = "MAFQABANG-611")
	public void recommendWithInputInFiscalNode() throws Exception {
		boolean expectedfieldspresent=false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify recommend in Herramientas - Checklist/Fiscal with no input");
			toolspage = homepage.openToolsPage();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String popuptext = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "popuptext", extentLogger);
				String businessinput = "business";
				String empresa = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, businessinput, extentLogger);
				String interviewedinput = "interviewed";
				String Entrevistado = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, interviewedinput,
						extentLogger);

				String fiscallinks = "fiscalnodelinks";
				JSONArray linkslist = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, fiscallinks,
						extentLogger);
				for (Object link : linkslist) {

					String linkname = link.toString();
				
					if (toolspage.isCheckListWidgetcollapsed()) {
						toolspage.expandCheckListWidget();
					}
					toolspage.expandFiscalNode();
					toolspage.clickOnGivenLink(linkname);

					JSONArray expectedtextfieldslist = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, "expectedtextfields",
							extentLogger);
					for (Object textfields : expectedtextfieldslist) {
						String expectedtextfield = textfields.toString();
						
					 expectedfieldspresent = toolspage.isTextFieldsPresent(expectedtextfield);
					}
					softas.assertTrue(expectedfieldspresent,
							jiraNumber + ":" + "Expected text fields are displayed ->" + linkname);
					logExtentStatus(extentLogger, expectedfieldspresent, "Expected text fields are displayed -> " + linkname,
							jiraNumber);
					
					boolean expectedradiobuttonspresent =toolspage.isRadioButtonsPresent();
					softas.assertTrue(expectedradiobuttonspresent,
							jiraNumber + ":" + "Expected radio buttons  are displayed ->" + linkname);
					logExtentStatus(extentLogger, expectedradiobuttonspresent, "Expected radio buttons  are displayed -> " + linkname,
							jiraNumber);

					toolspage.enterBusinessInput(empresa);
					toolspage.enterInterviewedInput(Entrevistado);
					toolspage.checkRadioButtons();
					toolspage.clickOnClearButton();
					boolean isinputfieldscleared = toolspage.isInputFieldsCleared()
							&& !toolspage.isRadioButtonsSelected();

					softas.assertTrue(isinputfieldscleared, jiraNumber + ":" + " Verified Clean button ->" + linkname);
					logExtentStatus(extentLogger, isinputfieldscleared, "Verified Clean button ->" + linkname,
							jiraNumber);
					toolspage.enterBusinessInput(empresa);
					toolspage.enterInterviewedInput(Entrevistado);
					toolspage.clickOnRecommendButton();
					boolean istextdisplayed = toolspage.isRecommendPopUpTextDisplayed(popuptext);
					toolspage.clickOnRecommendPopupClose();
					softas.assertTrue(istextdisplayed, jiraNumber + ":" + " Pop Up displays ->" + linkname);
					logExtentStatus(extentLogger, istextdisplayed, "Pop Up displays" + linkname, jiraNumber);
				}
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority = 7, groups = { "chparg" }, description = "MAFQABANG-612")
	public void recommendWithNoInputInLabourNode() throws Exception {
		boolean  expectedfieldspresent=false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify recommend in Herramientas -  Checklist/Laboral y seguridad social with no input");
			toolspage = homepage.openToolsPage();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String popuptext = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "popuptext", extentLogger);
				String labourlinks = "labournodelinks";
				JSONArray linkslist = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, labourlinks,
						extentLogger);
				for (Object link : linkslist) {

					String linkname = link.toString();
				
					if (toolspage.isCheckListWidgetcollapsed()) {
						toolspage.expandCheckListWidget();
					}
					toolspage.expandLabourAndSocialNode();
					toolspage.clickOnGivenLink(linkname);


					JSONArray expectedtextfieldslist = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, "expectedtextfields",
							extentLogger);
					for (Object textfields : expectedtextfieldslist) {
						String expectedtextfield = textfields.toString();
						
					 expectedfieldspresent = toolspage.isTextFieldsPresent(expectedtextfield);
					}
					softas.assertTrue(expectedfieldspresent,
							jiraNumber + ":" + "Expected text fields are displayed ->" + linkname);
					logExtentStatus(extentLogger, expectedfieldspresent, "Expected text fields are displayed -> " + linkname,
							jiraNumber);
					
					boolean expectedradiobuttonspresent =toolspage.isRadioButtonsPresent();
					softas.assertTrue(expectedradiobuttonspresent,
							jiraNumber + ":" + "Expected radio buttons  are displayed ->" + linkname);
					logExtentStatus(extentLogger, expectedradiobuttonspresent, "Expected radio buttons  are displayed -> " + linkname,
							jiraNumber);
					toolspage.clickOnRecommendButton();
					boolean istextdisplayed = toolspage.isRecommendPopUpTextDisplayed(popuptext);
					softas.assertTrue(istextdisplayed, jiraNumber + ":" + " Pop Up displayed ->" + linkname);
					logExtentStatus(extentLogger, istextdisplayed, " Pop Up displayed ->" + linkname, jiraNumber);

				}
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority = 8, groups = { "chparg" }, description = "MAFQABANG-613")
	public void recommendWithInputInLabourNode() throws Exception {
		boolean expectedfieldspresent=false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					" Verify recommend in Herramientas -Checklist/Laboral y seguridad social  with valid input");
			toolspage = homepage.openToolsPage();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String popuptext = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "popuptext", extentLogger);
				String businessinput = "business";
				String empresa = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, businessinput, extentLogger);
				String interviewedinput = "interviewed";
				String Entrevistado = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, interviewedinput,
						extentLogger);

				String labourlinks = "labournodelinks";
				JSONArray linkslist = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, labourlinks,
						extentLogger);
				for (Object link : linkslist) {

					String linkname = link.toString();
					
					if (toolspage.isCheckListWidgetcollapsed()) {
						toolspage.expandCheckListWidget();
					}
					toolspage.expandLabourAndSocialNode();
					toolspage.clickOnGivenLink(linkname);


					JSONArray expectedtextfieldslist = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, "expectedtextfields",
							extentLogger);
					for (Object textfields : expectedtextfieldslist) {
						String expectedtextfield = textfields.toString();
						
					 expectedfieldspresent = toolspage.isTextFieldsPresent(expectedtextfield);
					}
					softas.assertTrue(expectedfieldspresent,
							jiraNumber + ":" + "Expected text fields are displayed ->" + linkname);
					logExtentStatus(extentLogger, expectedfieldspresent, "Expected text fields are displayed -> " + linkname,
							jiraNumber);
					
					boolean expectedradiobuttonspresent =toolspage.isRadioButtonsPresent();
					softas.assertTrue(expectedradiobuttonspresent,
							jiraNumber + ":" + "Expected radio buttons  are displayed ->" + linkname);
					logExtentStatus(extentLogger, expectedradiobuttonspresent, "Expected radio buttons  are displayed -> " + linkname,
							jiraNumber);

					toolspage.enterBusinessInput(empresa);
					toolspage.enterInterviewedInput(Entrevistado);
					toolspage.checkRadioButtons();
					toolspage.clickOnClearButton();
					boolean isinputfieldscleared = toolspage.isInputFieldsCleared()
							&& !toolspage.isRadioButtonsSelected();

					softas.assertTrue(isinputfieldscleared, jiraNumber + ":" + " Verified Clean button ->" + linkname);
					logExtentStatus(extentLogger, isinputfieldscleared, "Verified Clean button ->" + linkname,
							jiraNumber);

					toolspage.enterBusinessInput(empresa);
					toolspage.enterInterviewedInput(Entrevistado);
					toolspage.clickOnRecommendButton();
					boolean istextdisplayed = toolspage.isRecommendPopUpTextDisplayed(popuptext);
					softas.assertTrue(istextdisplayed, jiraNumber + ":" + "  Pop Up displayed ->" + linkname);
					logExtentStatus(extentLogger, istextdisplayed, " Pop Up displayed ->" + linkname, jiraNumber);

				}
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	
	/* This test requires specific user credentials. Using the user with access rights will result
	in failure of this test case
	Username: 1090274 and Password: LALEYQA
	Always execute this test case as last test case
	*/

	@Test(priority = 10, groups = { "chparg" }, description = "MAFQABANG-614")
	public void linkNotClickableInFiscalNode() throws Exception {
		boolean expectedfieldspresent=false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify link is graded out and not clickable in Herramientas - Checklist/Fiscal  page");
			
			//setting up the new user session for this test case
			homepage.clickSignOff();
			loginpage = new LoginPage(driver, ProductUrl);
			String username = "1090274";
			String password = "LALEYQA";
			homepage = loginpage.Login(username, password);
			
			toolspage = homepage.openToolsPage();
			
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
								
				String fiscallinks = "fiscalnodelink";
				JSONArray linkslist = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, fiscallinks,
						extentLogger);
				for (Object link : linkslist) {

					String linkname = link.toString();
				
					if (toolspage.isCheckListWidgetcollapsed()) {
						toolspage.expandCheckListWidget();
					}
					toolspage.expandFiscalNode();
					boolean isLinkClickable = toolspage.isGivenLinkClickable(linkname);

					softas.assertTrue(isLinkClickable,
							jiraNumber + ":" + "Given link:" + linkname + " is grayed out with no access for this user");
					logExtentStatus(extentLogger, isLinkClickable, "Given link:" + linkname + " is grayed out with no access for this user<br> username:" + username
							 + "<br> password: " + password,
							jiraNumber);
					
				}
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}
	
	//MAFQABANG-625- Tools - TIP Obligations of Foreign Trade - Payment of exploitations  (User story :CPMX-3718)
	
	@Test(priority = 4, groups = { "chpmex" }, description = "MAFQABANG-625")
	public void ObligationsOfForeignTrade() throws Exception {
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				// Reading data from jason file
				String obligaciones = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "obligaciones",
						extentLogger);
				String month = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "month", extentLogger);
				String obligaciones_checkbox = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"obligacioneschkbox", extentLogger);
				String smart_table_tittle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "smart_table_tittle",
						extentLogger);
				String message_display = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "_comment",
						extentLogger);
				String expiry_year=jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "expiry_year",
						extentLogger);
				String issueSummary = getIssueTitle(jiraNumber, message_display);
				String checkBoxValue="ALL";
				// Opening Tools page
				toolspage = homepage.openToolsPage();
				// Click on the link obligaciones
				toolspage.clickOnGivenLink(obligaciones);
				// Click on the link in Federal page
				toolspage.oblicgationFollowingPages();
				// Passing checkbox parameter to select the value in obligaciones page
				toolspage.selectObligacionesCheckBox(obligaciones_checkbox);
				// Click on the link in Obligaciones page
				toolspage.oblicgationFollowingPages();
				// selecting dynamic checkbox's in the subject page
				toolspage.subjectdynamiccheckbox(checkBoxValue);
				// Click on the link in subject page
				toolspage.oblicgationFollowingPages();
				// selecting dynamic checkbox's in the concept page
				toolspage.conceptdynamiccheckbox(checkBoxValue);
				// Click on the link in concept page
				toolspage.oblicgationFollowingPages();
				// clicking radio button in expiry year
				toolspage.clickRadioButtonOnExpiryYear(expiry_year);
				// Click on the link in concept page
				toolspage.oblicgationFollowingPages();
				// Click on the radio button in Expiry month page
				toolspage.selectSpecificRadioButton(month);
				// click on createbutton in Expiry month page
				toolspage.clickObligationsCreateButton();
				// validating the Smart Table - Obligations of Foreign Trade
				boolean smartTableHeaderIsDispayed = toolspage.smartTableHeaderIsDispayed(smart_table_tittle);
				softas.assertTrue(smartTableHeaderIsDispayed, jiraNumber + ":" + issueSummary);
				//logExtentStatus(extentLogger, smartTableHeaderIsDispayed, issueSummary, jiraNumber);
				logExtentStatus(extentLogger, smartTableHeaderIsDispayed, issueSummary+"-> \n",
						"- Obligaciones: "+obligaciones+"\n","\n"+
						" Month: "+month+"\n"+"- Expiry Year: "+expiry_year , jiraNumber);

			}
		
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	//Obligations of federal contributions  (User story :CPMX-3651)
	@Test(priority = 4, groups = { "chpmex" }, description = "MAFQABANG-656")
	public void ObligationsOfFederalContribution() throws Exception {
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				// Reading data from jason file
				String obligaciones = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "obligaciones",
						extentLogger);
				String month = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "month", extentLogger);
				String obligaciones_checkbox = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"obligacioneschkbox", extentLogger);
				String subjects_chkbox = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "subjectschkbox",
						extentLogger);
				String concepts_chkbox = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "conceptschkbox",
						extentLogger);
				String smart_table_tittle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "smart_table_tittle",
						extentLogger);
				String message_display = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "_comment",
						extentLogger);
				String expiry_year=jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "expiry_year",
						extentLogger);
				String issueSummary = getIssueTitle(jiraNumber, message_display);
				// Opening Tools page
				toolspage = homepage.openToolsPage();
				// Click on the link obligaciones
				toolspage.clickOnGivenLink(obligaciones);
				// Click on the link in Federal page
				toolspage.oblicgationFollowingPages();
				// Passing checkbox parameter to select the value in obligaciones page
				toolspage.selectObligacionesCheckBox(obligaciones_checkbox);
				// Click on the link in Obligaciones page
				toolspage.oblicgationFollowingPages();
				// selecting dynamic checkbox's in the subject page
				toolspage.subjectdynamiccheckbox(subjects_chkbox);
				// Click on the link in subject page
				toolspage.oblicgationFollowingPages();
				// selecting dynamic checkbox's in the concept page
				toolspage.conceptdynamiccheckbox(concepts_chkbox);
				// Click on the link in concept page
				toolspage.oblicgationFollowingPages();
				Thread.sleep(3000);
				// clicking radio button in expiry year
				toolspage.clickRadioButtonOnExpiryYear(expiry_year);
				// Click on the link in concept page
				toolspage.oblicgationFollowingPages();
				// Click on the radio button in Expiry month page
				toolspage.selectSpecificRadioButton(month);
				// click on createbutton in Expiry month page
				toolspage.clickObligationsCreateButton();
				// validating the Smart Table - Obligations of Foreign Trade
				boolean smartTableHeaderIsDispayed = toolspage.smartTableHeaderIsDispayed(smart_table_tittle);
				softas.assertTrue(smartTableHeaderIsDispayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, smartTableHeaderIsDispayed, issueSummary+"-> \n",
						"- Obligaciones: "+obligaciones+"\n",
						"\n - Month: "+month+"\n"+"-Expiry Year: "+expiry_year , jiraNumber);

			}
			

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	//Obligations of social security contributions  (User story :CPMX-3723)
	@Test(priority = 4, groups = { "chpmex" }, description = "MAFQABANG-624")
    public void Obligationsofsocialsecuritycontributions() throws Exception {
           try {
                  testResult = Reporter.getCurrentTestResult();
                  softas = new SoftAssert();
                  extentLogger = setUpExtentTest(extentLogger, "Tools", testResult.getMethod().getMethodName());
                  String jiraNumber = testResult.getMethod().getDescription();
                  JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
                  for (Object searchString : listOfSearchData) {
                        JSONObject jsonObjectChild = (JSONObject) searchString;
                        // Reading data from jason file
                        String obligaciones = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "obligaciones",
                                      extentLogger);
                        String month = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "month", extentLogger);
                        String obligaciones_checkbox = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
                                      "obligacioneschkbox", extentLogger);
                        String subjects_chkbox = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "subjectschkbox",
                                      extentLogger);
                        String concepts_chkbox = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "conceptschkbox",
                                      extentLogger);
                        String smart_table_tittle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "smart_table_tittle",
                                      extentLogger);
                        String message_display = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "_comment",
                                      extentLogger);
                        String expiry_year=jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "expiry_year",
                                      extentLogger);
                        String issueSummary = getIssueTitle(jiraNumber, message_display);
                        // Opening Tools page
                        toolspage = homepage.openToolsPage();
                        // Expand the ObligationsWidget
                        //toolspage.expandObligationsWidget();
                        // Click on the link obligaciones
                        toolspage.clickOnGivenLink(obligaciones);
                        // Click on the link in Federal page
                        toolspage.oblicgationFollowingPages();
                        // Passing checkbox parameter to select the value in obligaciones page
                        toolspage.selectObligacionesCheckBox(obligaciones_checkbox);
                        // Click on the link in Obligaciones page
                        toolspage.oblicgationFollowingPages();
                        // selecting dynamic checkbox's in the subject page
                        toolspage.subjectdynamiccheckbox(subjects_chkbox);
                        // Click on the link in subject page
                        toolspage.oblicgationFollowingPages();
                        // selecting dynamic checkbox's in the concept page
                        toolspage.conceptdynamiccheckbox(concepts_chkbox);
                        // Click on the link in concept page
                        toolspage.oblicgationFollowingPages();
                        // clicking radio button in expiry year
                        toolspage.clickRadioButtonOnExpiryYear(expiry_year);
                        // Click on the link in concept page
                        toolspage.oblicgationFollowingPages();
                        // Click on the radio button in Expiry month page
                        toolspage.selectSpecificRadioButton(month);
                         // click on createbutton in Expiry month page
                        toolspage.clickObligationsCreateButton();
                        // validating the Smart Table - Obligations of Foreign Trade
                        boolean smartTableHeaderIsDispayed = toolspage.smartTableHeaderIsDispayed(smart_table_tittle);
                        softas.assertTrue(smartTableHeaderIsDispayed, jiraNumber + ":" + issueSummary);
                        logExtentStatus(extentLogger, smartTableHeaderIsDispayed, issueSummary+"-> \n",
                                      "- Obligaciones: "+obligaciones+"\n",
                                      "\n - Month: "+month+"\n"+"-Expiry Year: "+expiry_year , jiraNumber);

                  }

           } catch (Exception exc) {
                  logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
                  softas.assertTrue(false, "Exception in Test");

           } finally {
                  extentReports.endTest(extentLogger);
                  softas.assertAll();
           }
    }


}
