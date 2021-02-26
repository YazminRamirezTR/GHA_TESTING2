package com.trgr.quality.maf.tests;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.commonutils.RandomUtils;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.AlertPage;
import com.trgr.quality.maf.pages.BasePage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LegislationPage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SaveAndSchedulePage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

/* Alerts_TC_002 Verify create Alert Functionality , MAFAUTO-225, MAFAUTO-173
 * Alerts_TC_004 Verify Suspend Alert Functionality , MAFAUTO-223, MAFAUTO-174
 * Alerts_TC_005 Verify Modify Alert Functionality
 * Alerts_TC_004 Verify Reactivate Alert Functionality
 * Alerts_TC_006 Verify Error Message creating an alert 
 * Alerts_TC_003 Verify Delete Alert Functionality , MAFAUTO-175
 * MAFAUTO-210
 * 
 * Creating alerts for various search results: 
 * 		MAFAUTO-224, MAFAUTO-177, MAFAUTO-145, MAFAUTO-190, MAFAUTO-185, MAFAUTO-114
 * 
 */

public class AlertTest extends BaseTest {

	LoginPage loginpage;
	HomePage homepage;
	AlertPage alertpage;
	String alertName;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void startAlertTest() throws IllegalArgumentException, IOException, ParseException {
		try {
			loginpage = new LoginPage(driver, ProductUrl);
			String Username = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".username");
			String Password = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".password");
			homepage = loginpage.Login(Username, Password);
			/*for(int i=0;i<10;i++) {
				if(driver.getTitle().equalsIgnoreCase("Checkpoint  | Búsquedas")) {
					break;
				} 
				else {
					Thread.sleep(3000);
				}
			}
			*/
			jsonReader = new JsonReader();
			alertpage = homepage.ClickonAlertLink();
			
		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Alerts", "StartAlertTest");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the Alert test are not run.<br>"
							+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}
	}

	@AfterClass(alwaysRun = true)
	public void endAlertTest() throws IllegalArgumentException, IOException {
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
			(new HomePage(driver)).clickSignOff();
			System.out.println("Exception in AfterClass: "+e);
		}
	}
	
	@BeforeMethod(alwaysRun = true)
	public void backToHomePage() {
		try {
			homepage = new HomePage(driver);
			homepage.OpenSearchPage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * MAFAUTO-173 : Verify cancel alert from alerts page
	 */
	@Test(priority = 0, groups = { "chparg", "chpmex", "chpury" }, description = "MAFAUTO-173")
	public void cancelBtnOnAlertCreatePage() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Cancel button in AlertCreatePage");

		try {

			alertpage = homepage.ClickonAlertLink();
			alertpage.clickCreateNewAlert();
			Thread.sleep(3000);
			boolean predesignAlertDisplayed = alertpage.isPredesignAlertsPresent();

			// MAFAUTO-173 : Verify cancel alert from alerts page
			if (predesignAlertDisplayed)
				alertpage.clickCancelButtonInPredesignAlert();
			Thread.sleep(5000);
			boolean createAlertCancelled = predesignAlertDisplayed && alertpage.isAlertPageTitleDisplayed();
			softas.assertTrue(createAlertCancelled, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, createAlertCancelled,
					issueSummary + ": Validated clicking Cancel in Predesign Alerts navigates to Alerts page",
					jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * MAFAUTO-225 : Verify alert page with pre-defined alerts
	 */
	@Test(priority = 0, groups = { "chparg", "chpmex", "chpury" }, description = "MAFAUTO-225")
	public void predesignAlertsOnAlertCreatePage() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Pre-Design Alerts in AlertCreatePage");

		try {
			// MAFAUTO-225 : Verify alert page with pre-defined alerts
			alertpage = homepage.ClickonAlertLink();
			alertpage.clickCreateNewAlert();
			if(	alertpage.isPresentAlertLink()) {
			Thread.sleep(2000);
			alertpage.clickCreateNewAlert();
			}
			Thread.sleep(3000);
			boolean predesignAlertValidated = false;
			predesignAlertValidated = alertpage.isPredesignAlertsPresent()
					&& alertpage.isPredesignAlertsSubjectDisplayed();
			softas.assertTrue(predesignAlertValidated, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, predesignAlertValidated, issueSummary + ": Predesign Alerts validated",
					jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// please dont change the priority
	@Test(priority = 1, groups = { "chparg", "chpmex", "chpury", "chpchile" }, description = "MAFQABANG-110")
	public void createAnAlert() throws Exception {

		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify Create Alert");

			boolean alertCreated = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String alertNameKey = "alertname";
				alertName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, alertNameKey, extentLogger);
				alertName += "_" + RandomUtils.getUniqueNumber();

				String emailKey = "email";
				String email = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, emailKey, extentLogger);

				alertpage = homepage.ClickonAlertLink();
				if(	alertpage.isPresentAlertLink()) {
					Thread.sleep(2000);
					alertpage.clickCreateNewAlert();
					}
				alertpage.CreateAlert(alertName, email);

				boolean createNewAlertLinkIsDisplayed = alertpage.newAlertLinkIsDisplayed();
				if (!createNewAlertLinkIsDisplayed) {// Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-295");
				}

				homepage.ClickonAlertLink();
				Thread.sleep(3000);
				alertCreated = alertpage.isAlertNameExist(alertName);
				softas.assertTrue(alertCreated, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, alertCreated, issueSummary, alertNameKey + "," + emailKey,
						alertName + "," + email, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/* Alerts_TC_005 Verify Modify Alert Functionality */
	// Please done change the prority - this test modifies the Alert Created in
	// previous test
	@Test(priority = 2,dependsOnMethods={"createAnAlert"}, groups = { "chparg", "chpmex", "chpury", "chpchile" }, description = "MAFQABANG-113")
	public void modifyAnExistingAlert() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify Modify Alert");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String alertNameKey = "alertname";
				// Use the alert created. If not created, try to find it in data
				// file
				if (alertName == null || alertName.equals(""))
					alertName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, alertNameKey, extentLogger);

				alertpage = homepage.ClickonAlertLink();
				Thread.sleep(1000);
				boolean isalertmodified = alertpage.ModifyAlert(alertName);
				softas.assertTrue(isalertmodified, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isalertmodified, issueSummary, alertNameKey, alertName, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * Alerts_TC_004 Verify Suspend Alert Functionality MAFAUTO-223 MAFAUTO-174
	 */

	@Test(priority = 3, groups = {"chpmex"}, description = "MAFAUTO-223")
	public void suspendAnExistingAlert() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Suspend Alert");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String alertNameKey = "alertname";
				// Use the alert created. If not created, try to find it in data
				// file
				if (alertName == null || alertName.equals(""))
					alertName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, alertNameKey, extentLogger);

				String suspendDateKey = "suspendtodate";
				String suspendDate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suspendDateKey,
						extentLogger);
				
				alertpage = homepage.ClickonAlertLink();
				alertpage.filterAlertListByText(alertName);
				/*boolean isalertsuspended = alertpage.SuspendAlert(suspendDate);
				softas.assertTrue(isalertsuspended, issueSummary + ":" + jiraNumber);

				logExtentStatus(extentLogger, isalertsuspended, issueSummary, alertNameKey, alertName, jiraNumber);*/
				alertpage.clickReactivateAlert();
				/*JSONArray listOfData = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, "validate-non-existent fields", extentLogger);
				for(int i=0;i <listOfData.size();i++) {
					String verify=listOfData.get(i).toString();
					System.out.println(verify);
					alertpage.isPresentNonExistingFields(verify);
				}*/
				// MAFAUTO-223
				boolean fieldsNotPresentInPage = false;
				
				fieldsNotPresentInPage = alertpage.isClientIDNotDisplayed() && alertpage.isFormatNotDisplayed()
						&& alertpage.isNoticeNotDisplayed();
				softas.assertTrue(fieldsNotPresentInPage, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, fieldsNotPresentInPage,
						"Validate the absence of fields (ClientID,Format,Notice) in suspend page", jiraNumber);
				alertpage.clickCancelOnSuspendOrReactivatePage();

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	//This test verifies the suspend alert functionality for all other products except mexico
	@Test(priority = 3,dependsOnMethods={"createAnAlert"}, groups = { "chparg","chpury","chpchile"}, description = "MAFQABANG-620")
	public void suspendAlertforAll() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Suspend Alert");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String alertNameKey = "alertname";
				// Use the alert created. If not created, try to find it in data
				// file
				if (alertName == null || alertName.equals(""))
					alertName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, alertNameKey, extentLogger);

				String suspendDateKey = "suspendtodate";
				String suspendDate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suspendDateKey,
						extentLogger);

				alertpage = homepage.ClickonAlertLink();
				alertpage.filterAlertListByText(alertName);
				
				boolean isalertsuspended = alertpage.SuspendAlert(suspendDate);
				softas.assertTrue(isalertsuspended, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isalertsuspended, issueSummary, alertNameKey, alertName, jiraNumber);
				
				}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/* Alerts_TC_004 Verify Reactivate Alert Functionality */

	@Test(priority = 4,dependsOnMethods={"createAnAlert"}, groups = { "chparg", "chpmex", "chpury", "chpchile"}, description = "MAFQABANG-111")
	public void reactivateAnExistingAlert() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, " Verify Reactivate Alert");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String alertNameKey = "alertname";
				if (alertName == null || alertName.equals(""))
					alertName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, alertNameKey, extentLogger);
				
				alertpage = homepage.ClickonAlertLink();
				alertpage.filterAlertListByText(alertName);

				boolean isalertreactivated = alertpage.ReactivateAlert();
				softas.assertTrue(isalertreactivated, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isalertreactivated, issueSummary, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/* Alerts_TC_006 Verify Error Message creating an alert */

	@Test(priority = 5, groups = { "chparg", "chpmex", "chpury" }, description = "MAFQABANG-114")
	public void emptyFieldsValidation() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,
				"Verify Error Message creating an alert for missing mandatory fields");
		try {
			alertpage = homepage.ClickonAlertLink();
			if(	alertpage.isPresentAlertLink()) {
				Thread.sleep(2000);
				alertpage.clickCreateNewAlert();
				}
			boolean isEmptyFieldValidated = alertpage.EmpltyFieldValidation();
			softas.assertTrue(isEmptyFieldValidated, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, isEmptyFieldValidated, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/* Alerts_TC_003 Verify Delete Alert Functionality */

	@Test(priority = 6,dependsOnMethods={"createAnAlert"}, groups = { "chparg", "chpury", "chpchile" }, description = "MAFQABANG-112")
	public void deleteAnExistingAlert() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "verify DeleteAlert");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			int deleteCount = 0;
			for (Object searchString : listOfSearchData) {
				deleteCount++;
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "alertname";
				String alertToDelete = "";
				if (deleteCount == 1)
					alertToDelete = alertName;
				// Use the alert created. If not created, try to find it in data
				// file
				if (alertToDelete == null || alertToDelete.equals(""))
					alertToDelete = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);

				alertpage = homepage.ClickonAlertLink();

				boolean isalertdeleted = alertpage.DeleteAlert(alertToDelete);
				softas.assertTrue(isalertdeleted, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isalertdeleted, issueSummary,key,alertToDelete, jiraNumber);
				
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 6, dependsOnMethods= {"createAnAlert"},groups = { "chpmex" }, description = "MAFAUTO-175")
	public void deleteExistingAlert() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, "verify DeleteAlert");
		try {

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			int deleteCount = 0;
			for (Object searchString : listOfSearchData) {
				deleteCount++;
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "alertname";
				String alertToDelete = "";
				if (deleteCount == 1)
					alertToDelete = alertName;
				// Use the alert created. If not created, try to find it in data
				// file
				if (alertToDelete == null || alertToDelete.equals(""))
					alertToDelete = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);

				AlertPage alertpage = homepage.ClickonAlertLink();

				boolean isalertdeleted = alertpage.DeleteAlert(alertToDelete);
				softas.assertTrue(isalertdeleted, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isalertdeleted, issueSummary, jiraNumber);
				alertToDelete = null;
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * 
	 * MAFAUTO-145, MAFAUTO-185,
	 */
	@Test(priority = 7, groups = { "chpmex" }, description = "MAFAUTO-185")
	public void createAlertFromSearchResultsOnLegislation() throws Exception {
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Create Alert From the SearchResults Page for Legislation");

			SearchPage searchpage;
			SearchResultsPage searchresultspage;
			SaveAndSchedulePage saveAndSchedulePage;
			AlertPage alertpage;
			String saveName;

			// Reading freeword
			String searchFreeWord = null;
			String searchFreeWordKey = null;

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object key : listOfSearchData) {
				try {

					JSONObject jsonObjectChild = (JSONObject) key;
					searchFreeWordKey = "freeword";
					searchFreeWord = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searchFreeWordKey,
							extentLogger);
					
					String Frequencykey= "frequency";
					String Frequencytime=jsonReader.readKeyValueFromJsonObject(jsonObjectChild, Frequencykey,
							extentLogger);
					
					String email ="email-notification";
					
					String emailname=jsonReader.readKeyValueFromJsonObject(jsonObjectChild, email,
							extentLogger);
					
					String jiraIDs = "";
				
					searchpage = homepage.OpenSearchPage();
					searchpage.OpenLegislationPage();
					Thread.sleep(1000);
					jiraIDs = "MAFAUTO-224" + ",";
					jiraIDs = jiraIDs + "MAFAUTO-145" + ",";
					jiraIDs = jiraIDs + "MAFAUTO-185" + "," + "MAFQABANG-104";
					Thread.sleep(1000);

					searchpage.enterFreeWordOnSearchPage(searchFreeWord);
					Thread.sleep(1000);
					searchresultspage = searchpage.clickOnSearch();
					// Open Save and Schedule Search Page
					saveName = searchFreeWordKey + " " + RandomUtils.getUniqueNumber();
					saveAndSchedulePage = searchresultspage.clickSaveAndScheduleSearch();
					if (saveAndSchedulePage == null) {
						logExtentStatus(extentLogger, false,
								"Alert created for search " + searchFreeWordKey + " : " + searchFreeWord,
								jiraIDs.split(","));
						softas.assertTrue(false,
								"Alert created for search :" + searchFreeWordKey + "  '" + searchFreeWord + "'");
						continue;
					}
					
					saveAndSchedulePage.setName(saveName);
					saveAndSchedulePage.selectFrequencyFromDropdownAlertPage(Frequencytime);
					
					saveAndSchedulePage.writeEmailFieldforalertpage(emailname);
					// Click on save button, open alert page & verifies the new
					// alert is saved
					boolean alertCreated = false;
					alertpage = saveAndSchedulePage.clickSaveAlertButton();
					if (alertCreated = (alertpage != null))
						alertCreated = alertpage.isAlertNameExist(saveName);
					logExtentStatus(extentLogger, alertCreated,
							issueSummary + "<br>" + searchFreeWordKey + ":" + searchFreeWord, jiraIDs.split(","));
					softas.assertTrue(alertCreated,
							"Alert created for search :" + searchFreeWordKey + "  '" + searchFreeWord + "'");
					// Cleaning up - deleting the alert created for this
					// scenario
					if (alertCreated)
						alertpage.DeleteAlert(saveName);

				} catch (Exception exc) {
					extentLogger.log(LogStatus.FAIL,
							"Failed to Create Alert for search :" + searchFreeWordKey + "  '" + searchFreeWord + "'<br>"
									+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
					softas.assertTrue(false,
							"Failed to Create Alert for search :" + searchFreeWordKey + "  '" + searchFreeWord);
					// Skip creating alert, continue with next data.
					continue;
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

	@Test(priority = 8, groups = { "chpbr" }, description = "MAFQABANG-567")
	public void CreateAlertFromSearchResultsforLegislationPage() throws Exception {
		SearchPage searchpage;
		SearchResultsPage searchresultspage;
		SaveAndSchedulePage saveAndSchedulePage;
		LegislationPage legislationpage;
		AlertPage alertpage;
		String saveName;

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, "Create Alert for advance search Legislation Page");
		try {

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey = "freeword";
				String freeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				String alertNameKey = "alertname";
				String alertName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, alertNameKey, extentLogger);

				String suspendDateKey = "suspendtodate";
				String suspendDate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suspendDateKey,
						extentLogger);

				String emailKey = "email";
				String email = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, emailKey, extentLogger);

				// Performing Search
				searchpage = homepage.OpenSearchPage();
				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.enterFreeWordOnlegilationPage(freeword);
				Thread.sleep(1000);

				searchresultspage = searchpage.clickOnSearch();
				// Open Save and Schedule Search Page
				saveName = alertName + " " + RandomUtils.getUniqueNumber();
				saveAndSchedulePage = searchresultspage.clickSaveAndScheduleSearch();
				// Providing Alert Name,email and frequency
				saveAndSchedulePage.setName(saveName);
				saveAndSchedulePage.setEmail(email);
				saveAndSchedulePage.selectFrequencyMonthly();
				// Click on save button, open alert page & verifies the new
				// alert is saved
				boolean alertCreated = false;
				alertpage = saveAndSchedulePage.clickSaveAlertButton();
				if (alertCreated = (alertpage != null)) {
					alertCreated = alertpage.isAlertNameExist(saveName);
				}

				logExtentStatus(extentLogger, alertCreated, "Alert '" + saveName + "' created for search : " + freeword,
						jiraNumber);
				softas.assertTrue(alertCreated, "Alert created for search :'" + freeword + "'");

				// searching Alert in Alert List
				alertpage.filterAlertListByText(saveName);

				// Suspending Alert
				boolean isalertsuspended = alertpage.SuspendAlert(suspendDate);
				softas.assertTrue(isalertsuspended, "Suspend Alert");
				logExtentStatus(extentLogger, isalertsuspended, "Suspend Alert Functionality Validated", jiraNumber);

				// Reactivate Alert
				boolean alertReactivated = alertpage.ReactivateAlert();
				softas.assertTrue(alertReactivated, "Reactivate Alert");
				logExtentStatus(extentLogger, alertReactivated, "Reactivate Alert Functionality", jiraNumber);

				// Deleting Alert
				boolean isalertdeleted = alertpage.DeleteAlert(saveName);
				softas.assertTrue(isalertdeleted, "Delete Alert");
				logExtentStatus(extentLogger, isalertdeleted, "Alert deleted successfully", jiraNumber);

				boolean alertValidated = alertCreated && isalertsuspended && alertReactivated && isalertdeleted;
				logExtentStatus(extentLogger, alertValidated, issueSummary, freewordKey, freeword, jiraNumber);
				softas.assertTrue(isalertdeleted, issueSummary);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * Creating alerts for various search results. MAFAUTO-114
	 */
	@Test(priority = 9, groups = { "chpmex" }, description = "MAFAUTO-114")
	public void createAlertFromSearchResultsOnJurisprudence() throws Exception {
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"verify  Create Alert From the SearchResults Page for Jurisprudence");

			SearchPage searchpage;
			SearchResultsPage searchresultspage;
			SaveAndSchedulePage saveAndSchedulePage;
			AlertPage alertpage;
			String saveName;

			String searchFreeWord = null;
			String searchFreeWordKey = null;

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object key : listOfSearchData) {
				try {

					JSONObject jsonObjectChild = (JSONObject) key;
					searchFreeWordKey = "freeword";
					searchFreeWord = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searchFreeWordKey,
							extentLogger);
					String jiraIDs = "";
					searchpage = homepage.OpenSearchPage();
					searchpage.openJurisprudencePage();
					jiraIDs = "MAFAUTO-145" + ",";
					jiraIDs = jiraIDs + "MAFAUTO-114" + "," + "MAFQABANG-104";
					Thread.sleep(1000);

					searchpage.enterFreeWordOnSearchPage(searchFreeWord);
					Thread.sleep(1000);
					searchresultspage = searchpage.clickOnSearch();
					if(searchpage.isPresentTextExpected()) {
						Thread.sleep(2000);
						searchresultspage = searchpage.clickOnSearch();
					}
					// Open Save and Schedule Search Page
					saveName = "Alert " + RandomUtils.getUniqueNumber();
					saveAndSchedulePage = searchresultspage.clickSaveAndScheduleSearch();
					if (saveAndSchedulePage == null) {
						logExtentStatus(extentLogger, false,
								"Alert created for search " + searchFreeWordKey + " : " + searchFreeWord,
								jiraIDs.split(","));
						softas.assertTrue(false,
								"Alert created for search :" + searchFreeWordKey + "  '" + searchFreeWord + "'");
						continue;
					}

					saveAndSchedulePage.setName(saveName);
					saveAndSchedulePage.selectFrequencyMonthly();
					// Click on save button, open alert page & verifies the new
					// alert is saved
					boolean alertCreated = false;
					Thread.sleep(2000);
					alertpage = saveAndSchedulePage.clickSaveAlertButton();
					if (alertCreated = (alertpage != null))
						alertCreated = alertpage.isAlertNameExist(saveName);
					logExtentStatus(extentLogger, alertCreated,
							issueSummary + "<br> " + searchFreeWordKey + ":" + searchFreeWord, jiraIDs.split(","));

					softas.assertTrue(alertCreated,
							issueSummary + " " + searchFreeWordKey + "  '" + searchFreeWord + "'");
					// Cleaning up - deleting the alert created for this
					// scenario
					if (alertCreated)
						alertpage.DeleteAlert(saveName);

				} catch (Exception exc) {
					extentLogger.log(LogStatus.FAIL,
							"Failed to Create Alert for search :" + searchFreeWordKey + "  '" + searchFreeWord + "'<br>"
									+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
					softas.assertTrue(false,
							"Failed to Create Alert for search :" + searchFreeWordKey + "  '" + searchFreeWord);
					// Skip creating alert, continue with next data.
					continue;
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.FAIL, "Something went wrong. Exiting test.<br>" + takesScreenshot_Embedded()
					+ "<br>" + displayErrorMessage(exc));
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * Creating alerts for various search results. MAFAUTO-190
	 */
	@Test(priority = 10, groups = { "chpmex" }, description = "MAFAUTO-190")
	public void CreateAlertFromSearchResultsFromDoctrine() throws Exception {
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"verify  Create Alert From the SearchResults Page for Doctrine");

			SearchPage searchpage;
			SearchResultsPage searchresultspage;
			SaveAndSchedulePage saveAndSchedulePage;
			AlertPage alertpage;
			String saveName;

			String searchFreeWord = null;
			String searchFreeWordKey = null;

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object key : listOfSearchData) {
				try {
					JSONObject jsonObjectChild = (JSONObject) key;
					searchFreeWordKey = "freeword";
					searchFreeWord = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "freeword", extentLogger);
					String Frequencykey= "Frecuencyalert";
					String Frequencytime=jsonReader.readKeyValueFromJsonObject(jsonObjectChild, Frequencykey,
							extentLogger);
					
					String email ="Emailalert";
					
					String emailname=jsonReader.readKeyValueFromJsonObject(jsonObjectChild, email,
							extentLogger);
					String jiraIDs = "";
					searchpage = homepage.OpenSearchPage();
					searchpage.OpenDoctrinaPage();
					jiraIDs = "MAFAUTO-145" + ",";
					jiraIDs = jiraIDs + "MAFAUTO-190";
					jiraIDs = jiraIDs + "," + "MAFQABANG-104";

					searchpage.enterFreeWordOnSearchPage(searchFreeWord);
					Thread.sleep(1000);
					searchresultspage = searchpage.clickOnSearch();
					
					if(searchpage.isPresentTextExpected()) {
						Thread.sleep(2000);
						searchresultspage = searchpage.clickOnSearch();
					}
					// Open Save and Schedule Search Page
					saveName = searchFreeWordKey + " " + RandomUtils.getUniqueNumber();
					saveAndSchedulePage = searchresultspage.clickSaveAndScheduleSearch();
					if (saveAndSchedulePage == null) {
						logExtentStatus(extentLogger, false,
								"Alert created for search " + searchFreeWordKey + " : " + searchFreeWord,
								jiraIDs.split(","));
						softas.assertTrue(false,
								"Alert created for search :" + searchFreeWordKey + "  '" + searchFreeWord + "'");
						continue;
					}

					saveAndSchedulePage.setName(saveName);

             saveAndSchedulePage.selectFrequenceyWeekdaysDeliverypage(Frequencytime);
					
					saveAndSchedulePage.writeEmailFieldforalertpage(emailname);
					// Click on save button, open alert page & verifies the new
					// alert is saved
					boolean alertCreated = false;
					alertpage = saveAndSchedulePage.clickSaveAlertButton();
					if (alertCreated = (alertpage != null))
						alertCreated = alertpage.isAlertNameExist(saveName);
					logExtentStatus(extentLogger, alertCreated,
							issueSummary + "<br>" + searchFreeWordKey + ":" + searchFreeWord, jiraIDs.split(","));

					softas.assertTrue(alertCreated,
							issueSummary + ":" + searchFreeWordKey + "  '" + searchFreeWord + "'");
					// Cleaning up - deleting the alert created for this
					// scenario
					if (alertCreated)
						alertpage.DeleteAlert(saveName);

				} catch (Exception exc) {
					extentLogger.log(LogStatus.FAIL,
							"Failed to Create Alert for search :" + searchFreeWordKey + "  '" + searchFreeWord + "'<br>"
									+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
					softas.assertTrue(false,
							"Failed to Create Alert for search :" + searchFreeWordKey + "  '" + searchFreeWord);
					// Skip creating alert, continue with next data.
					continue;
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

	/*
	 * Creating alerts for various search results. MAFAUTO-177,
	 * 
	 */

	@Test(priority = 11, groups = { "chpmex" }, description = "MAFAUTO-177")
	public void CreateAlertForSearchfreeword_globalnotapplicable() throws Exception {
		try {
			LegislationPage legislationpage;
			AlertPage alertpage;
			String saveName;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Alerts", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "verify  Create Alert for Search Global - Not Applicable");

			SearchPage searchpage;
			SearchResultsPage searchresultspage;
			SaveAndSchedulePage saveAndSchedulePage;
			String searchFreeWord = null;
			String searchFreeWordKey = null;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object key : listOfSearchData) {
				try {

					JSONObject jsonObjectChild = (JSONObject) key;
					searchFreeWordKey = "freeword";
					searchFreeWord = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searchFreeWordKey,
							extentLogger);
					
				String 	email = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "Emailalert",
							extentLogger);
				
				
				String 	Namealert = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "Namealert",
						extentLogger);
					String jiraIDs = "";
					
					jiraIDs = "MAFAUTO-177";
					Thread.sleep(1000);
					jiraIDs = jiraIDs + "," + "MAFQABANG-104";
					searchpage = homepage.OpenSearchPage();
					legislationpage = searchpage.OpenLegislationPage();
					legislationpage.enterFreeWordOnlegilationPage(searchFreeWord);
					Thread.sleep(1000);

					searchresultspage = searchpage.clickOnSearch();
					
					if(!searchpage.isResultsPageDisplayedLegislation()) {
						Thread.sleep(2000);
						searchpage = homepage.OpenSearchPage();
						legislationpage = searchpage.OpenLegislationPage();
						Thread.sleep(1000);
						legislationpage.enterFreeWordOnlegilationPage(searchFreeWord);
						searchresultspage = searchpage.clickOnSearch();
					}
					
					// Open Save and Schedule Search Page
					// Open Save and Schedule Search Page
					saveName = Namealert + " " + RandomUtils.getUniqueNumber();
					saveAndSchedulePage = searchresultspage.clickSaveAndScheduleSearch();
					// Providing Alert Name,email and frequency
					saveAndSchedulePage.setName(saveName);
					saveAndSchedulePage.setEmail(email);
					saveAndSchedulePage.selectFrequencyMonthly();
					// Click on save button, open alert page & verifies the new
					// alert is saved
					boolean alertCreated = false;
					Thread.sleep(2000);
					alertpage = saveAndSchedulePage.clickSaveAlertButton();
					if (alertCreated = (alertpage != null)) {
						alertCreated = alertpage.isAlertNameExist(saveName);
					}

					

					logExtentStatus(extentLogger, alertCreated,
							issueSummary + "<br>" + searchFreeWordKey + ":" + searchFreeWord, jiraIDs.split(","));
					softas.assertTrue(alertCreated, issueSummary + "  '" + searchFreeWord + "'");
					
					String	alertToDelete = saveName;
					// Use the alert created. If not created, try to find it in data
					// file
					;

					alertpage = homepage.ClickonAlertLink();

					boolean isalertdeleted = alertpage.DeleteAlert(alertToDelete);
					softas.assertTrue(isalertdeleted, issueSummary + "  '" + searchFreeWord + "'");
				} catch (Exception exc) {
					extentLogger.log(LogStatus.FAIL,
							"Failed to Create Alert for search :" + searchFreeWordKey + "  '" + searchFreeWord + "'<br>"
									+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
					softas.assertTrue(false, "Exception in Test");
					// Skip creating alert, continue with next data.
					continue;
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

	@Test(priority = 31, groups = { "chpury" }, description = "MAFQABANG-551")
	public void AlertPageDisplayValidation() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "AlertPage", testResult.getMethod().getMethodName());
			boolean linkverified = false;
			boolean typeofalertverified = false;

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Alert page verification - Validate All Expected fields");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String alerttypeKey = "alerttype";
				JSONArray alerttypeArray = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, alerttypeKey,
						extentLogger);

				alertpage = homepage.ClickonAlertLink();
				linkverified = alertpage.isCreateNewAlertLinkPresent() && alertpage.validateColumnsinALertTable()
						&& alertpage.isFilterAlertPortletDisplayed();

				for (int row = 0; row < alerttypeArray.size(); row++) {
					typeofalertverified = alertpage.validateAlertTypesAlertPage(alerttypeArray.get(row).toString());
					if (!typeofalertverified)
						break;
				}

				softas.assertTrue(typeofalertverified && linkverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, typeofalertverified && linkverified, issueSummary, jiraNumber);

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
