package com.trgr.quality.maf.tests;

import java.io.IOException;

import org.json.simple.JSONArray;
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
import com.trgr.quality.maf.commonutils.RandomUtils;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HistoryPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class HistoryTest extends BaseTest {
	/*
	 * Historail_001 Verify History Page, MAFAUTO-167. Historail_002 Verify view
	 * all events Historail_003 Start a New History Historail_004 Delete a
	 * Single History, MAFAUTO-163. Historail_005 Delete All History,
	 * MAFAUTO-141. Verify Rename History, MAFAUTO-164. Verify record & View
	 * document consulted in History, MAFAUTO-166. Verify reset History,
	 * MAFAUTO-165.
	 */

	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	HistoryPage historypage;
	static String historyName;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void startHistoryTest() throws IllegalArgumentException, IOException {
		try {
			loginpage = new LoginPage(driver, ProductUrl);
			String Username = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".username");
			String Password = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".password");
			homepage = loginpage.Login(Username, Password);
			jsonReader = new JsonReader();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "History", "StartHistoryTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the News test are not run.<br>"
					+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}

	}

	@AfterClass(alwaysRun = true)
	public void afterClass(){
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
		}
	}

	/*
	 * Historail_001 Verify History Page MAFAUTO-167
	 */
	@Test(priority = 0, groups = { "chparg", "chpury","chppe","chpchile" }, description = "MAFQABANG-128")
	public void HistoryPage() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());
			boolean historyLinkValidated,historyPageValidated = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "History Page Verification");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "columnnames";
				JSONArray colnamearray = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, key, extentLogger);

				// Validate History Link
				historypage = homepage.ClickonHistoryLink();
				historyLinkValidated = historypage.isHistoryLinkvalidated();
				softas.assertTrue(historyLinkValidated,
						jiraNumber + ":" + issueSummary + " :History Link is available");
				logExtentStatus(extentLogger, historyLinkValidated, issueSummary + ": History Link is available",
						jiraNumber);

				historypage = homepage.ClickonHistoryLink();

				// Validate History Page
				for(int i=0;i<colnamearray.size();i++)
				{
					historyPageValidated = historypage.validateHistoryPage(colnamearray.get(i).toString());
					if(!historyPageValidated)
						break;

				}

				softas.assertTrue(historyPageValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, historyPageValidated, issueSummary, key, colnamearray.toString() ,jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Historail_001 Verify History Page MAFAUTO-167
	/*@Test(priority = 0, groups = { "chpmex"}, description = "MAFAUTO-167")
	public void displayOfSessionInformation() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());
			boolean historyLinkValidated,historyPageValidated = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "History Page Verification");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "columnnames";
				JSONArray colnamearray = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, key, extentLogger);

				// Validate History Link
				historypage = homepage.ClickonHistoryLink();
				historyLinkValidated = historypage.isHistoryLinkvalidated();
				softas.assertTrue(historyLinkValidated,
						jiraNumber + ":" + issueSummary + " :History Link is available");
				logExtentStatus(extentLogger, historyLinkValidated, issueSummary + ": History Link is available",
						jiraNumber);

				
				historypage = homepage.ClickonHistoryLink();

				// Validate History Page
				for(int i=0;i<colnamearray.size();i++)
				{
					historyPageValidated = historypage.validateHistoryPage(colnamearray.get(i).toString());
					if(!historyPageValidated)
						break;

				}

				softas.assertTrue(historyPageValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, historyPageValidated, issueSummary, key, colnamearray.toString() ,jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}*/

	
	@Test(priority = 1, groups = { "chparg", "chpmex", "chpury","chppe","chpchile"}, description = "MAFQABANG-129")
	public void ViewallEventsList() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());
			boolean linkpresent, colvalidated = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Column Verification for View All events");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "columnnames";
				JSONArray colnamearray = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, key, extentLogger);

				historypage = homepage.ClickonHistoryLink();

				// checking view all events link availability
				linkpresent = historypage.isViewAllHistoryLinkDisplayed();
				if (linkpresent) 
				{
					historypage.clickViewAllEvents();
				}

				for (int i = 0; i < colnamearray.size(); i++)
				{
					colvalidated = historypage.validatingSeeAllEventsColumns(colnamearray.get(i).toString());
					if (!colvalidated)
						break;
				}
				softas.assertTrue(colvalidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, colvalidated, issueSummary, key, colnamearray.toString(), jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/* NA : CHPMEXICO */
	@Test(priority = 2, groups = { "chparg", "chpury","chppe","chpchile"}, description = "MAFQABANG-130")
	public void CreateNewHistory() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Creating New History");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String nameKey = "name";
				String name = jsonObjectChild.get(nameKey).toString();
				String customeridKey = "customerid";
				String customerid = jsonObjectChild.get(customeridKey).toString();

				historypage = homepage.ClickonHistoryLink();
				historypage.clickViewAllEvents();
				historypage.clickStartNewHistory();

				String uniquename = historypage.getUniqueHistoryName(name);
				Thread.sleep(4000);
				historypage.enterName(uniquename);
				Thread.sleep(4000);
				historypage.enterClientID(customerid);
				Thread.sleep(4000);
				historypage.newHistoryClickSave();
				historypage.clickBacktoHistoryPage();

				// Re-login to get history updated.
				homepage.clickSignOff();
				loginpage = new LoginPage(driver, ProductUrl);
				String Username = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".username");
				String Password = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".password");
				homepage = loginpage.Login(Username, Password);

				historypage = homepage.ClickonHistoryLink();
				historypage.clickViewAllEvents();
				Thread.sleep(4000);
				boolean isnewhistorycreated = historypage.isHistoryNamePresent(uniquename);
				softas.assertTrue(isnewhistorycreated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isnewhistorycreated, issueSummary,
						nameKey+", "+customeridKey,name+", "+customerid,jiraNumber);

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
	 * Historail_004 Delete a Single History, .
	 */
	@Test(priority = 3, groups = { "chparg", "chpmex", "chpury","chppe" }, description = "MAFQABANG-132")
	public void DeleteSingleHistory() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Deleting Single History from View All events Table");

			historypage = homepage.ClickonHistoryLink();
			historypage.clickViewAllEvents();
			Thread.sleep(4000);
			// Select history name to delete, if it is not already set.
			if (historyName == null || historyName.equals(""))
				historyName = historypage.getFirstHistoryWithDeleteLink();
			softas.assertTrue(historyName != null,
					jiraNumber + ":" + issueSummary + "Validated History Name selected to delete");
			logExtentStatus(extentLogger, historyName != null, "Validated History Name selected to delete", jiraNumber);

			// Delete history
			boolean historyDeleted = false;
			historypage = homepage.ClickonHistoryLink();
			historypage.clickViewAllEvents();
			Thread.sleep(4000);
			if (historypage.isHistoryNamePresent(historyName)) {
				int eventRowToDelete = historypage.getHistoryRowNumber(historyName);
				historypage.clickDeleteLinkForItem(eventRowToDelete);
				historypage.clickDeleteConfirmButton();
				historyDeleted = !historypage.isHistoryNamePresent(historyName);
			}
			softas.assertTrue(historyDeleted, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, historyDeleted, issueSummary, "HistoryName", historyName, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	//@Test(priority = 3, groups = {  "chpmex" }, description = "MAFAUTO-163")
	public void deleteSingleHistory() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Deleting Single History from View All events Table");

			historypage = homepage.ClickonHistoryLink();
			historypage.clickViewAllEvents();
			// Select history name to delete, if it is not already set.
			if (historyName == null || historyName.equals(""))
				historyName = historypage.getFirstHistoryWithDeleteLink();
			softas.assertTrue(historyName != null,
					jiraNumber + ":" + issueSummary + "Validated History Name selected to delete");
			logExtentStatus(extentLogger, historyName != null, "Validated History Name selected to delete", jiraNumber);

			// Delete history
			boolean historyDeleted = false;
			historypage = homepage.ClickonHistoryLink();
			Thread.sleep(2000);
			historypage.clickViewAllEvents();
			Thread.sleep(4000);
			if (historypage.isHistoryNamePresent(historyName)) {
				int eventRowToDelete = historypage.getHistoryRowNumber(historyName);
				historypage.clickDeleteLinkForItem(eventRowToDelete);
				historypage.clickDeleteConfirmButton();
				historyDeleted = !historypage.isHistoryNamePresent(historyName);
			}
			softas.assertTrue(historyDeleted, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, historyDeleted, issueSummary, "HistoryName", historyName, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	
	/*
	 * Historail_005 Delete All History, MAFAUTO-141.
	 */
	@Test(priority = 4, groups = { "chparg",  "chpury","chppe","chpchile" }, description = "MAFQABANG-131")
	public void DeleteAllHistory() throws IOException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Application Verification on Delete All History");

			historypage = homepage.ClickonHistoryLink();
			historypage.clickViewAllEvents();
			// Delete all history
			boolean historyDeleted = historypage.clickClearAllHistoryLink()
					&& historypage.clickDeleteConfirmButton();
			// Verify all history is deleted, except the current.
			int numberOfEventsInTable = historypage.getNumberOfEventsPresent();
			historyDeleted = historyDeleted && (numberOfEventsInTable == 1) ? true : false;

			softas.assertTrue(historyDeleted, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, historyDeleted, issueSummary,jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 4, groups = { "chpmex"}, description = "MAFAUTO-141")
	public void deleteAllHistoryToHaveOnlyCurrentHistory() throws IOException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, 
					"Application Verification on Delete All History");

			historypage = homepage.ClickonHistoryLink();
			Thread.sleep(3000);
			historypage.clickViewAllEvents();
			Thread.sleep(2000);
			// Delete all history
			boolean historyDeleted = historypage.clickClearAllHistoryLink()
					&& historypage.clickDeleteConfirmButton();
			// Verify all history is deleted, except the current.
			int numberOfEventsInTable = historypage.getNumberOfEventsPresent();
			historyDeleted = historyDeleted && (numberOfEventsInTable == 1) ? true : false;

			softas.assertTrue(historyDeleted, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, historyDeleted, issueSummary,jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	
	/*
	 * Verify Rename History, MAFAUTO-164.
	 */
	@Test(priority = 5, groups = {"chpmex"}, description = "MAFAUTO-164")
	public void RenameActualHistory() throws IOException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Application Verification on Rename History");

			historypage = homepage.ClickonHistoryLink();
			historypage.clickViewAllEvents();
			Thread.sleep(4000);
			// Selecting name of the current (Actual) history
			historyName = historypage.getActualHistoryName();

			softas.assertTrue(historyName != null,
					jiraNumber + ":" + issueSummary + " : Validate History Name selected to delete");
			logExtentStatus(extentLogger, historyName != null, "Validate History Name selected to delete", jiraNumber);

			// Validate Rename history page
			String historyNewName = "EditedName " + RandomUtils.getUniqueNumber();
			historypage = homepage.ClickonHistoryLink();
			Thread.sleep(3000);
			historypage.clickViewAllEvents();
			Thread.sleep(4000);
			int eventRowToRename = historypage.getHistoryRowNumber(historyName);
			historypage.clickRenameLinkForItem(eventRowToRename);
			Thread.sleep(3000);
			boolean renamePageDisplayed = historypage.isRenameHistoryTitlePresent();
			softas.assertTrue(renamePageDisplayed,
					jiraNumber + ":" + issueSummary + " : Rename history page is displayed");
			logExtentStatus(extentLogger, renamePageDisplayed, "Rename history page is displayed", jiraNumber);

			// Validate Renaming History
			boolean historyRenamed = false;
			Thread.sleep(3000);
			historypage.writeRenameText(historyNewName);
			Thread.sleep(1000);
			historypage.clickSaveRenameButton();
			Thread.sleep(2000);
			historyRenamed = historypage.isHistoryNamePresent(historyNewName);

			if (historyRenamed)
				historyName = historyNewName;
			softas.assertTrue(historyRenamed, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, historyRenamed, issueSummary, "Rename History", historyName, jiraNumber);

			// Validate Renaming History - Cancel
			boolean renameCancelled = false;
			Thread.sleep(3000);
			eventRowToRename = historypage.getHistoryRowNumber(historyName);
			Thread.sleep(3000);
			historypage.clickRenameLinkForItem(eventRowToRename);
			if (historypage.isRenameHistoryTitlePresent()) {
				Thread.sleep(3000);
				historypage.clickCancelRenameButton();
				Thread.sleep(3000);
			//	renameCancelled = historypage.isAllEventsListDisplayed();
			}

//			softas.assertTrue(renameCancelled, jiraNumber + ":" + "Validated Clicking cancel from Rename page");
//			logExtentStatus(extentLogger, renameCancelled, " Validated Clicking cancel from Rename page",
//					"Rename History", historyName, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	/*
	 * This test is doing the same thing as MAFAUTO-164
	 * for all other countries except mexico
	 */
	@Test(priority = 5, groups = { "chparg","chpury","chppe" }, description = "MAFQABANG-617")
	public void RenameActualHistoryforAll() throws IOException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Application Verification on Rename History");

			historypage = homepage.ClickonHistoryLink();
			historypage.clickViewAllEvents();
			Thread.sleep(3000);
			// Selecting name of the current (Actual) history
			historyName = historypage.getActualHistoryName();

			softas.assertTrue(historyName != null,
					jiraNumber + ":" + issueSummary + " : Validate History Name selected to delete");
			logExtentStatus(extentLogger, historyName != null, "Validate History Name selected to delete", jiraNumber);

			// Validate Rename history page
			String historyNewName = "EditedName " + RandomUtils.getUniqueNumber();
			historypage = homepage.ClickonHistoryLink();
			historypage.clickViewAllEvents();
			Thread.sleep(3000);
			int eventRowToRename = historypage.getHistoryRowNumber(historyName);
			historypage.clickRenameLinkForItem(eventRowToRename);

			boolean renamePageDisplayed = historypage.isRenameHistoryTitlePresent();
			softas.assertTrue(renamePageDisplayed,
					jiraNumber + ":" + issueSummary + " : Rename history page is displayed");
			logExtentStatus(extentLogger, renamePageDisplayed, "Rename history page is displayed", jiraNumber);

			// Validate Renaming History
			boolean historyRenamed = false;
			historypage.writeRenameText(historyNewName);
			historypage.clickSaveRenameButton();
			Thread.sleep(3000);
			historyRenamed = historypage.isHistoryNamePresent(historyNewName);

			if (historyRenamed)
				historyName = historyNewName;
			softas.assertTrue(historyRenamed, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, historyRenamed, issueSummary, "Rename History", historyName, jiraNumber);

			// Validate Renaming History - Cancel
			boolean renameCancelled = false;
			eventRowToRename = historypage.getHistoryRowNumber(historyName);
			historypage.clickRenameLinkForItem(eventRowToRename);
			if (historypage.isRenameHistoryTitlePresent()) {
				historypage.clickCancelRenameButton();
				renameCancelled = historypage.isAllEventsListDisplayed();
			}

			softas.assertTrue(renameCancelled, jiraNumber + ":" + "Validated Clicking cancel from Rename page");
			logExtentStatus(extentLogger, renameCancelled, " Validated Clicking cancel from Rename page",
					"Rename History", historyName, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * Verify record & View document consulted in History, MAFAUTO-166.
	 */
	@Test(priority = 6, groups = { "chparg"}, description = "MAFAUTO-166")
	public void RecordAndViewDocument() throws IOException {
		SoftAssert softas = new SoftAssert();
		SearchPage searchpage;
		SearchResultsPage searchResultsPage;
		DocumentDisplayPage documentDisplayPage;
		testResult = Reporter.getCurrentTestResult();
		extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());

		try {
			/*
			 * //Re-login to start new history. [Start new history]
			 * homepage.clickSignOff(); loginpage = new LoginPage(driver,
			 * ProductUrl); String Username =
			 * PropertiesRepository.getString("com.trgr.maf." +
			 * BaseTest.productUnderTest + ".username"); String Password =
			 * PropertiesRepository.getString("com.trgr.maf." +
			 * BaseTest.productUnderTest + ".password"); homepage =
			 * loginpage.Login(Username, Password);
			 */
			// Perform search & open one document in document display page
			String jiraNumber = testResult.getMethod().getDescription();

			HomePage homepage = this.homepage;
			//String issueSummary = getIssueTitle(jiraNumber, "Trail - See Trail: Verify Record and view");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freeword = jsonObjectChild.get("freeword").toString();

				searchpage = homepage.OpenSearchPage();
				searchpage.clickThematicSearchRadioButton();
				searchpage.enterFreeWordOnSearchPage(freeword);
				searchResultsPage = searchpage.clickonSearchwhenThematicisSelected();
				documentDisplayPage = searchResultsPage.getFirstDocument();

				String documentTitle = documentDisplayPage.getDocumentDisplayTitleLineOne();
				historypage = homepage.ClickonHistoryLink();
				historypage.clickViewAllEvents();

				// Selecting current (Actual) history from the list of history
				historyName = historypage.getActualHistoryName();
				historypage.clickViewLinkForItem(historyName);
				boolean viewAllLinkPresent = historypage.isViewAllHistoryLinkDisplayed();
				softas.assertTrue(historyName != null && viewAllLinkPresent, "Failed to select actual history");
				logExtentStatus(extentLogger, historyName != null && viewAllLinkPresent,
						"View all history link validation", jiraNumber);

				// Verifying the current document is recorded in history
				boolean documentRecordedInHistory = false;
				if (documentRecordedInHistory = historypage.isFirstTrailEqualsDocument())
					documentRecordedInHistory = historypage.isEqualsFirstTrailDescription(documentTitle);
				logExtentStatus(extentLogger, documentRecordedInHistory, "Document search recorded in history",
						jiraNumber);
				softas.assertTrue(documentRecordedInHistory, "Failed to verify record history");

				// Validate 'Go To Document' from Recorded history
				documentDisplayPage = historypage.clickGoToDocument(1);
				boolean navigatedTorecordedDocument = false;
				if (documentDisplayPage != null)
					navigatedTorecordedDocument = documentDisplayPage.getDocumentDisplayTitleLineOne()
					.equals(documentTitle);

				softas.assertTrue(navigatedTorecordedDocument,
						jiraNumber + ":" + "Navigated to saved document from record history");
				logExtentStatus(extentLogger, navigatedTorecordedDocument,
						"Navigated to saved document from record history", "Freeword", freeword, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*@Test(priority = 6, groups = { "chpmex"}, description = "MAFAUTO-166")
	public void RecordAndViewDocumentforchpmex() throws IOException {
		SoftAssert softas = new SoftAssert();
		
		

		try {
				testResult = Reporter.getCurrentTestResult();
				extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());
				boolean historyLinkValidated,historyPageValidated = false;

				String jiraNumber = testResult.getMethod().getDescription();
				String issueSummary = getIssueTitle(jiraNumber, "History Page Verification");

				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

				for (Object searchString : listOfSearchData) {
					JSONObject jsonObjectChild = (JSONObject) searchString;
					String key = "columnnames";
					JSONArray colnamearray = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, key, extentLogger);

					// Validate History Link
					historypage = homepage.ClickonHistoryLink();
					historyLinkValidated = historypage.isHistoryLinkvalidated();
					softas.assertTrue(historyLinkValidated,
							jiraNumber + ":" + issueSummary + " :History Link is available");
					logExtentStatus(extentLogger, historyLinkValidated, issueSummary + ": History Link is available",
							jiraNumber);

					
					historypage = homepage.ClickonHistoryLink();

					// Validate History Page
					for(int i=0;i<colnamearray.size();i++)
					{
						historyPageValidated = historypage.validateHistoryPage(colnamearray.get(i).toString());
						if(!historyPageValidated)
							break;

					}

					softas.assertTrue(historyPageValidated, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, historyPageValidated, issueSummary, key, colnamearray.toString() ,jiraNumber);

				}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}*/
	
	/*
	 * Verify reset History, MAFAUTO-165.
	 */
	@Test(priority = 7, groups = { "chparg", "chpmex", "chpury" }, description = "MAFAUTO-165")
	public void ResetHistory() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Application Verification on Reset History");

			historypage = homepage.ClickonHistoryLink();
			Thread.sleep(5000);
			historypage.clickViewAllEvents();
			Thread.sleep(5000);
			// Select the oldest history item to reset
			int lastItemRow = historypage.countOfHistoryItems() + 1;
			historyName = historypage.getHistoryNameAtRow(lastItemRow);
			// Reset & verify the item is moved to top of the list
			boolean resetLinkPresent = false;
			if (resetLinkPresent = historypage.isResetLinkPresetForItem(lastItemRow))
				historypage.clickResetLinkForItem(lastItemRow);
			softas.assertTrue(historyName != null && resetLinkPresent, jiraNumber + ":" + issueSummary
					+ " :Reset Link not found for the selected history :" + historyName);
			logExtentStatus(extentLogger, historyName != null && resetLinkPresent,
					issueSummary + ": Reset Link not found for the selected history", jiraNumber);

			// Verify the event reset is successful
			boolean resetSuccess = false;
			if (resetLinkPresent) {
				String firstHistoryName = historypage.getHistoryNameAtRow(2);
				if (historyName.equals(firstHistoryName))
					resetSuccess = historypage.isDeleteAfterTwoWeeks(2);
			}
			softas.assertTrue(resetSuccess, jiraNumber + ":" + "Reset history Successfull");
			logExtentStatus(extentLogger, resetSuccess, "Reset history Successfull", "History Name", historyName,
					jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Verifying Client ID for the logged in Individual

	@Test(priority = 7, groups = { "chparg", "chpury" ,"chppe","chpchile"}, description = "MAFQABANG-423")
	public void ClientID() throws IOException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "History", testResult.getMethod().getMethodName());

			homepage.clickSignOff();

			loginpage = new LoginPage(driver, ProductUrl);
			String Username = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".username");
			String Password = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".password");
			HomePage homepage = this.homepage;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify the Client ID of the logged in user with");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String cidKey = "clientid";
				String cid = jsonObjectChild.get(cidKey).toString();

				// verifying whether client id Link is available in the
				// loginpage or not
				boolean isclientidlinkdisplayed = loginpage.isClientIDLinkDisplayed();
				if (isclientidlinkdisplayed) {
					loginpage.clickonClientIDLink();
					loginpage.enterClientID(cid);
				}
				boolean isClientIdIsDisplayed = loginpage.ClientIdIsDisplayed(cid);
				if(!isClientIdIsDisplayed){//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-292");
				}
				softas.assertTrue(isclientidlinkdisplayed, "Failed to find Client ID Link");
				logExtentStatus(extentLogger, isclientidlinkdisplayed, "Client ID Link Available", jiraNumber);
				homepage = loginpage.Login(Username, Password);
				// Naviagting to History Page

				historypage = homepage.ClickonHistoryLink();
				historypage.clickViewAllEvents();

				String actualHistoryName = historypage.getActualHistoryName();
				int rowNum = historypage.getHistoryRowNumber(actualHistoryName);
				String actualClientID = historypage.getClientIDAtRow(rowNum);
				boolean isclientidverified = cid.equals(actualClientID);

				softas.assertTrue(isclientidverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isclientidverified, issueSummary, cidKey, cid, jiraNumber);
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
