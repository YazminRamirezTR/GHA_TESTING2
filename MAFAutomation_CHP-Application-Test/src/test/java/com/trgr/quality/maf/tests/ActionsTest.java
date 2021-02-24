package com.trgr.quality.maf.tests;

import org.json.simple.JSONObject;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
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
import com.trgr.quality.maf.pages.ActionsPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
import com.trgr.quality.maf.pages.ToolsPage;

public class ActionsTest extends BaseTest {
	/*
	 * This Test verifies the presence of News Tab It validates the error
	 * message when the data provided does not correspond to any results It
	 * validates the result list for appropriate data It validates the clear
	 * button functionality
	 */

	LoginPage loginpage;
	HomePage homepage;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	ToolsPage toolsPage;
	ActionsPage actionsPage;
	SearchResultsPage searchResultsPage;
	SoftAssert softas;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IllegalArgumentException, IOException, ParseException {
		try {

			loginpage = new LoginPage(driver, ProductUrl);
			String Username = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".username");
			String Password = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".password");
			homepage = loginpage.Login(Username, Password);
			jsonReader = new JsonReader();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Actions", "StartActionsTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the Actions test are not run.<br>"
					+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
		}
	}

	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-477")
	public void displayOfActionsPageAndCheckForNoResultsMsg() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Actions", testResult.getMethod().getMethodName());

			boolean isBienesPersonalesWidgetExpanded = false;

			String jiraNumber = testResult.getMethod().getDescription();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String alertmsgKey = "alertmsg";
				String alertmsg = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,alertmsgKey,extentLogger); 

				String issueSummary = getIssueTitle(jiraNumber,
						"Verfiy Search template and no Result Alert Message"); 
				toolsPage = homepage.openToolsPage();
				toolsPage.ExpandBienesPersonalesWidget();
				isBienesPersonalesWidgetExpanded = toolsPage.isBienesPersonalesWidgetExpanded();

				if (isBienesPersonalesWidgetExpanded) {
					toolsPage.expandCotizaciones();
					actionsPage = toolsPage.openActionsPage();
				}

				boolean isSearcTemplatePresent = actionsPage.isActionSearchTemplateDisplayed();
				softas.assertTrue(isSearcTemplatePresent, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearcTemplatePresent, "Action page is displayed with expected fields",
						jiraNumber);

				// Ignoring the return handle for search results as it would be
				// null in this case of no search results
				searchResultsPage = actionsPage.clickOnSearch();
				boolean isAlertMsgdisplayed = actionsPage.isErrorMessageDisplayed(alertmsg);
				softas.assertTrue(isAlertMsgdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isAlertMsgdisplayed,issueSummary,alertmsgKey,alertmsg,jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-480")
	public void searchUsingAllFieldsOnActionsPage() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Actions", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger); 
				String denominacionKey = "denominación";
				String denominacion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,denominacionKey,extentLogger);
				String claseKey = "clase";
				String clase = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,claseKey,extentLogger);

				String issueSummary = getIssueTitle(jiraNumber,"Verify the Search Result page of actions");

				toolsPage = homepage.openToolsPage();
				toolsPage.expandCotizaciones();
				actionsPage = toolsPage.openActionsPage();
				actionsPage.selectPeriodFiscalDropdown(period);
				actionsPage.selectDenominacion(denominacion);
				actionsPage.selectPClaseDropdown(clase);

				searchResultsPage = actionsPage.clickOnSearch();

				if (!(homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					boolean searchResultsDisplayed = searchResultsPage.searchResultsDisplayedForPeroidinForeignCurrnecy(period)
							&& 	searchResultsPage.searchResultsDisplayedForcoininForeignCurrnecy(denominacion)
							&& searchResultsPage.searchResultsDisplayedForPeroidValuationWheel(clase);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected",
							periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase,jiraNumber);
				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, "Search results returned zero results",
							periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase,jiraNumber);
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

	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-491")
	public void newSearchLinkAndRefreshSearchValidations() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Actions", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger); 
				String denominacionKey = "denominación";
				String denominacion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,denominacionKey,extentLogger);
				String claseKey = "clase";
				String clase = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,claseKey,extentLogger);

				String issueSummary = getIssueTitle(jiraNumber,
						"Verify the New Search and modify search in Actions");

				toolsPage = homepage.openToolsPage();
				toolsPage.expandCotizaciones();
				actionsPage = toolsPage.openActionsPage();
				actionsPage.selectPeriodFiscalDropdown(period);
				actionsPage.selectDenominacion(denominacion);
				actionsPage.selectPClaseDropdown(clase);

				searchResultsPage = actionsPage.clickOnSearch();

				// clicking on the edit searchlink///defect has been raised for
				// data is not retaining---MAFQABANG-553
				searchResultsPage.ClickOnModifySearchLink();
				boolean isSearchDataRetained = actionsPage.isGivenSearchDataRetainedOnPage(period);

				softas.assertTrue(isSearchDataRetained, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchDataRetained,
						"Clicking Edit Search link retained values from initial search",
						"defect : MAFQABANG-553" + "<br>" + 
						periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase,jiraNumber);

				searchResultsPage = actionsPage.clickOnSearch();
				searchResultsPage.ClickOnNewSearchLink();

				isSearchDataRetained = actionsPage.isGivenSearchDataDeletedFromPage();

				softas.assertFalse(isSearchDataRetained, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, !isSearchDataRetained,
						"Clicking New Search link clear values from initial search",
						periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase,jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-493")
	public void actionNewSearchClearbutton() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Actions", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger); 
				String denominacionKey = "denominación";
				String denominacion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,denominacionKey,extentLogger);
				String claseKey = "clase";
				String clase = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,claseKey,extentLogger);

				String issueSummary = getIssueTitle(jiraNumber,"Verify the Clear button in action");

				toolsPage = homepage.openToolsPage();
				toolsPage.expandCotizaciones();
				actionsPage = toolsPage.openActionsPage();
				actionsPage.selectPeriodFiscalDropdown(period);
				actionsPage.selectDenominacion(denominacion);
				actionsPage.selectPClaseDropdown(clase);

				boolean isClearbuttonPresent = actionsPage.clickonClearButton();

				softas.assertTrue(isClearbuttonPresent, jiraNumber + ":" + issueSummary + ": Clear button is Present");
				logExtentStatus(extentLogger, isClearbuttonPresent, issueSummary + ":Clear button is Present",
						jiraNumber);

				boolean isSearchFieldsCleared = actionsPage.isSearchFieldsCleared();
				softas.assertTrue(isSearchFieldsCleared,jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchFieldsCleared,issueSummary,
						periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase,jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-494")
	public void searchWithInOnSearchResults() throws Exception {
		softas = new SoftAssert();
		boolean searchResultsDisplayed = false;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Actions", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger);
				String searchwithintextKey ="searchwithintext";
				String searchWithInText = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,searchwithintextKey,extentLogger);

				String issueSummary = getIssueTitle(jiraNumber,"Verify Search Within Search for Actions");

				toolsPage = homepage.openToolsPage();
				toolsPage.expandCotizaciones();
				actionsPage = toolsPage.openActionsPage();
				actionsPage.selectPeriodFiscalDropdown(period);

				searchResultsPage = actionsPage.clickOnSearch();

				boolean isSearchWithINOptionDisplayed = searchResultsPage.isSearchWithinOptionDisplayed();
				softas.assertTrue(isSearchWithINOptionDisplayed,
						jiraNumber + ":" + issueSummary + ":Search within Search option is Present");
				logExtentStatus(extentLogger, isSearchWithINOptionDisplayed,
						issueSummary + "Search within search option is Present",periodKey,period, jiraNumber);

				searchResultsPage.enterSearchWithinTerm(searchWithInText);
				
				searchResultsPage.clickOnSearchWithInSearch();
				
				if (!(homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.isDenominacionFilteredWithValue(searchWithInText);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary,
							searchwithintextKey,searchWithInText,jiraNumber);
				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentStatus(extentLogger, searchResultsDisplayed,issueSummary,
							searchwithintextKey,searchWithInText,jiraNumber);
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

}
