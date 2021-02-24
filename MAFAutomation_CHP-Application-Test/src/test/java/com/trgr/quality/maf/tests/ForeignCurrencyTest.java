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
import com.trgr.quality.maf.pages.ForeignCurrencyPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
import com.trgr.quality.maf.pages.ToolsPage;
import com.trgr.quality.maf.pages.ValuationWheelsPage;

public class ForeignCurrencyTest extends BaseTest {
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
	ValuationWheelsPage valuationWheelsPage;
	ForeignCurrencyPage foreignCurrencyPage;
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

			extentLogger = setUpExtentTest(extentLogger, "Actions", "ForeignCurrencyTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the ForeignCurrencyTest are not run.<br>"
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

	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-554")
	public void displayOfForeignCurrencyandCheckForNoResultsMsg() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "ForeignCurrency", testResult.getMethod().getMethodName());

			boolean isBienesPersonalesWidgetExpanded = false;

			String jiraNumber = testResult.getMethod().getDescription();

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String alertmsgKey = "alertmsg";
				String alertmsg = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,alertmsgKey,extentLogger);

				String issueSummary = getIssueTitle(jiraNumber, 
						"Verify Search template and No Result Alert Message for Foreign Currency");

				toolsPage = homepage.openToolsPage();
				toolsPage.ExpandBienesPersonalesWidget();
				isBienesPersonalesWidgetExpanded = toolsPage.isBienesPersonalesWidgetExpanded();

				if (isBienesPersonalesWidgetExpanded) {
					foreignCurrencyPage = toolsPage.clickOnForeignCurrencyLink();

				}

				boolean isSearcTemplatePresent = foreignCurrencyPage!=null
						&& foreignCurrencyPage.isForeigCurrencySearcTemplateDisplayed();

				softas.assertTrue(isSearcTemplatePresent, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearcTemplatePresent, "Foreign Currency page is displayed with expected fields", jiraNumber);

				// Ignoring the return handle for search results as it would be
				// null in this case of no search results
				searchResultsPage = foreignCurrencyPage.clickOnSearch();
				boolean isAlertMsgdisplayed = foreignCurrencyPage.isErrorMessageDisplayed(alertmsg);

				softas.assertTrue(isAlertMsgdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isAlertMsgdisplayed, issueSummary,alertmsgKey,alertmsg,jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-556")
	public void searchUsingAllFields() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "ForeignCurrency", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger);
				String coinKey = "coin";
				String coin = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,coinKey,extentLogger);


				String issueSummary = getIssueTitle(jiraNumber, 
						"Verify the Result list of Foreign Currency");

				toolsPage = homepage.openToolsPage();
				foreignCurrencyPage = toolsPage.clickOnForeignCurrencyLink();
				foreignCurrencyPage.selectPeriodFiscalDropdown(period);
				foreignCurrencyPage.selectCoinDropdown(coin);

				searchResultsPage = foreignCurrencyPage.clickOnSearch();

				if (!(foreignCurrencyPage.errorBlockDisplayed() || foreignCurrencyPage.noSearchResultsDisplayed())) {
					boolean searchResultsDisplayed = searchResultsPage.searchResultsDisplayedForPeroidinForeignCurrnecy(period)
							&& 	searchResultsPage.searchResultsDisplayedForcoininForeignCurrnecy(coin);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary,
							periodKey +","+coinKey,period+","+coin, jiraNumber);
				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, "Search results returned zero results",
							periodKey +","+coinKey,period+","+coin, jiraNumber);
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

	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-557")
	public void refreshSearchandclearDataValidations() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "ForeignCurrency", testResult.getMethod().getMethodName());


			String jiraNumber = testResult.getMethod().getDescription();

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger);
				String coinKey = "coin";
				String coin = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,coinKey,extentLogger);


				String issueSummary = getIssueTitle(jiraNumber, 
						"Validate  Modify Search links and clear button");

				toolsPage = homepage.openToolsPage();
				foreignCurrencyPage = toolsPage.clickOnForeignCurrencyLink();

				foreignCurrencyPage.selectPeriodFiscalDropdown(period);
				foreignCurrencyPage.selectCoinDropdown(coin);

				searchResultsPage = foreignCurrencyPage.clickOnSearch();

				searchResultsPage.ClickOnModifySearchLink();
				boolean isSearchDataRetained = foreignCurrencyPage.isGivenSearchDataRetainedOnPage(period, coin);

				softas.assertTrue(isSearchDataRetained, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchDataRetained,
						"Clicking Edit Search link retained values from initial search",
						periodKey +","+coinKey,period+","+coin, jiraNumber);

				foreignCurrencyPage.selectPeriodFiscalDropdown(period);
				foreignCurrencyPage.selectCoinDropdown(coin);

				boolean isClearbuttonPresent= foreignCurrencyPage.clickonClearButton();
				softas.assertTrue(isClearbuttonPresent, jiraNumber+ ":" +issueSummary);
				logExtentStatus(extentLogger, isClearbuttonPresent, "Clear button is Present", jiraNumber);

				boolean isFieldsCleared=foreignCurrencyPage.isSearchFieldsCleared();

				softas.assertTrue(isFieldsCleared, jiraNumber+ ":" + issueSummary);     
				logExtentStatus(extentLogger, isFieldsCleared, "Intail search template is displayed with empty fields after clicking on clear button",
						periodKey +","+coinKey,period+","+coin, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-559")
	public void newSearchandclearDataValidations() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "ForeignCurrency", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Validate  New Search links and clear button");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger);
				
				String coinKey = "coin";
				String coin = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,coinKey,extentLogger);

				toolsPage = homepage.openToolsPage();
				foreignCurrencyPage = toolsPage.clickOnForeignCurrencyLink();
				foreignCurrencyPage.selectPeriodFiscalDropdown(period);
				foreignCurrencyPage.selectCoinDropdown(coin);
				searchResultsPage = foreignCurrencyPage.clickOnSearch();

				searchResultsPage.ClickOnNewSearchLink();
				boolean isSearchDataCleared = foreignCurrencyPage.isSearchFieldsClearedAfterClickingonClearbutton();

				softas.assertTrue(isSearchDataCleared, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchDataCleared,
						"Clicking New Search link clear values from initial search",
						periodKey +","+coinKey,period+","+coin, jiraNumber);

				foreignCurrencyPage.selectPeriodFiscalDropdown(period);
				foreignCurrencyPage.selectCoinDropdown(coin);

				boolean isClearbuttonPresent= foreignCurrencyPage.clickonClearButton();
				softas.assertTrue(isClearbuttonPresent, jiraNumber+ ":" +issueSummary);
				logExtentStatus(extentLogger, isClearbuttonPresent, "Clear button is Present", jiraNumber);

				boolean isFieldsCleared=foreignCurrencyPage.isSearchFieldsCleared();

				softas.assertTrue(isFieldsCleared, jiraNumber+ ":" + issueSummary);     
				logExtentStatus(extentLogger, isFieldsCleared, "Intail search template is displayed with empty fields after clicking on clear button",
						periodKey +","+coinKey,period+","+coin, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-561")
	public void searchWithInOnSearchResults() throws Exception {
		softas = new SoftAssert();
		boolean searchResultsDisplayed = false;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "ForeignCurrency", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger);
				String SearchWithInTextKey = "searchwithintext";
				String SearchWithInText=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,SearchWithInTextKey,extentLogger);

				String issueSummary = getIssueTitle(jiraNumber, 
						"Search with in Search for Foreign Currency");

				toolsPage = homepage.openToolsPage();
				foreignCurrencyPage = toolsPage.clickOnForeignCurrencyLink();
				foreignCurrencyPage.selectPeriodFiscalDropdown(period);
				searchResultsPage = foreignCurrencyPage.clickOnSearch();

				boolean isSearchWithINOptionDisplayed=searchResultsPage.isSearchWithinOptionDisplayed();
				softas.assertTrue(isSearchWithINOptionDisplayed, jiraNumber + ":" + issueSummary+":Search with in option is Present");
				logExtentStatus(extentLogger, isSearchWithINOptionDisplayed, "Search with in option is Present", 
						periodKey ,period, jiraNumber);

				searchResultsPage.enterSearchWithinTerm(SearchWithInText);
				searchResultsPage.clickOnSearchWithInSearch();

				if (!(foreignCurrencyPage.errorBlockDisplayed() || foreignCurrencyPage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.isDenominacionFilteredWithValue(SearchWithInText);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, "Search within results displayed as expected",
							SearchWithInTextKey,SearchWithInText,jiraNumber);
				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, "Search within results returned zero results",
							SearchWithInTextKey,SearchWithInText, jiraNumber);
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

