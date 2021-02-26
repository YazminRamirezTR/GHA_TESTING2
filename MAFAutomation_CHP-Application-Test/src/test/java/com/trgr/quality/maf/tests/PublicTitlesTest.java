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
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.PublicTitlesPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
import com.trgr.quality.maf.pages.ToolsPage;

public class PublicTitlesTest extends BaseTest {
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
	PublicTitlesPage publicTitlesPage;
	SearchResultsPage searchResultsPage;
	SoftAssert softas;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void startPublicTitlesTest() throws IllegalArgumentException, IOException, ParseException {
		try {

			loginpage = new LoginPage(driver, ProductUrl);
			String Username = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".username");
			String Password = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".password");
			homepage = loginpage.Login(Username, Password);
			toolsPage = homepage.openToolsPage();
			jsonReader = new JsonReader();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Public Titles", "PublicTitlesTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the PublicTitlesTest are not run.<br>"
					+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}
	}

	@AfterClass(alwaysRun = true)
	public void stopPublicTitlesTest(){
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
	 * This test verifies the display of the Titulos Publicos page, verifies the
	 * error message displayed when atleast one field is not entered with test
	 * data verifies search results are displayed based on the search data.
	 */
	@Test(groups = { "chparg" }, description = "MAFQABANG-535")
	public void basicFeaturesAndSearchUsingAllFields() throws Exception {

		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			boolean searchResultsDisplayed = false;
			extentLogger = setUpExtentTest(extentLogger, "Public Titles", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, 
					"Validate basic functionality of Titulos Publicos link and search using all fields on the page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String errorMessageKey = "errormsg";
				String errorMessage = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,errorMessageKey,extentLogger); 
				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger); 
				String denominacionKey = "denominacion";
				String denominacion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,denominacionKey,extentLogger);
				String claseKey = "clase";
				String clase = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,claseKey,extentLogger); 

				toolsPage = homepage.openToolsPage();
				toolsPage.expandCotizaciones();
				// Clicking on Titulos Publicos link
				publicTitlesPage = toolsPage.clickOnPublicTitlesLink();

				// validate the public titles page fields and display
				boolean expectedFieldsDisplayed = publicTitlesPage.isPageDisplayedAsExpected();

				softas.assertTrue(expectedFieldsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, expectedFieldsDisplayed,
						"Public Titles page is displayed with expected fields", jiraNumber);

				// Click on the search without entering any data to validate the
				// error message- Not capturing the returning as it would be null in
				// this case
				publicTitlesPage.clickOnSearch();

				boolean atleastOneFieldIsMandatory = publicTitlesPage.isErrorMessageDisplayed(errorMessage);
				softas.assertTrue(atleastOneFieldIsMandatory, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, atleastOneFieldIsMandatory,
						"Searching with no search data returns expected error message for mandatory fields",
						errorMessageKey,errorMessage,jiraNumber);

				// Entering the given search data from test data file
				publicTitlesPage.enterPeriodValue(period);
				publicTitlesPage.enterDenominacion(denominacion);
				publicTitlesPage.enterClase(clase);

				searchResultsPage = publicTitlesPage.clickOnSearch();
				if (!(homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsDisplayedBasedOnSearchData(period,
							denominacion, clase);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected",
							periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase, jiraNumber);
				} else {
					searchResultsDisplayed= false;
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, "Search results returned zero results",
							periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase, jiraNumber);
				}

				boolean allvalidationsPassed = expectedFieldsDisplayed
						&& atleastOneFieldIsMandatory && searchResultsDisplayed;
				softas.assertTrue(true, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, allvalidationsPassed,issueSummary,jiraNumber);
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
	 * This test does the initial search and checks the functionality for Edit
	 * Search link and New Search Link
	 */
	@Test(groups = { "chparg" }, description = "MAFQABANG-552")
	public void newAndModifySearchLinkValidations() throws IOException {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Public Titles", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Validate clicking Edit and New Search links from search results page for Titulos Publicos");

			toolsPage = homepage.openToolsPage();
			toolsPage.expandCotizaciones();

			// Clicking on Titulos Publicos link
			publicTitlesPage = toolsPage.clickOnPublicTitlesLink();

			// Not reading the data from JSON as the goal of this test case is
			// to validate the new and edit search links
			// creation test data file is not necessary for this test
			String period = "2012";
			String denominacion = "BONO ACONCAGUA - PCIA.MENDOZA";
			String clase = "2007 - DÓLAR (U$S)";

			// Entering the given search data from test data file
			publicTitlesPage.enterPeriodValue(period);
			publicTitlesPage.enterDenominacion(denominacion);
			publicTitlesPage.enterClase(clase);

			searchResultsPage = publicTitlesPage.clickOnSearch();

			// clicking on the edit searchlink///defect has been raised for
			// data is not retaining---MAFQABANG-553
			searchResultsPage.ClickOnModifySearchLink();
			boolean isSearchDataRetained = publicTitlesPage.isGivenSearchDataRetainedOnPage(period);

			softas.assertTrue(isSearchDataRetained, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, isSearchDataRetained,
					"Clicking Edit Search link retained values from initial search","defect : MAFQABANG-553" + "<br>"+
							"period,denominacion,clase",period+","+denominacion+","+clase, jiraNumber);

			// validating new search link
			searchResultsPage = publicTitlesPage.clickOnSearch();
			searchResultsPage.ClickOnNewSearchLink();

			isSearchDataRetained = publicTitlesPage.isGivenSearchDataDeletedOnPage(period);

			softas.assertFalse(isSearchDataRetained, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, !isSearchDataRetained,
					"Clicking New Search link clear values from initial search",
					"period,denominacion,clase",period+","+denominacion+","+clase, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * This test performs the search on the Titulos Publicos page and then
	 * performs the search within on the search results.
	 */
	@Test(groups = { "chparg" }, description = "MAFQABANG-539")
	public void searchWithInOnSearchResults() throws IOException {
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			boolean searchResultsDisplayed = false;
			extentLogger = setUpExtentTest(extentLogger, "Public Titles", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, 
					"Search Within functionality of the search results on Titulos Publicos Page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger); 
				String denominacionKey = "denominacion";
				String denominacion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,denominacionKey,extentLogger);
				String claseKey = "clase";
				String clase = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,claseKey,extentLogger); 
				String searchWithinKey = "searchwithintext";
				String searchWithInText = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,searchWithinKey,extentLogger); 
				
				toolsPage = homepage.openToolsPage();
				toolsPage.expandCotizaciones();

				// Clicking on Titulos Publicos link
				publicTitlesPage = toolsPage.clickOnPublicTitlesLink();
				// Entering the given search data from test data file
				publicTitlesPage.enterPeriodValue(period);
				publicTitlesPage.enterDenominacion(denominacion);
				publicTitlesPage.enterClase(clase);

				searchResultsPage = publicTitlesPage.clickOnSearch();
				searchResultsDisplayed = searchResultsPage.searchResultsDisplayedBasedOnSearchData(period, denominacion,
						clase);
				softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected",
						periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase, jiraNumber);

				// Perform search within
				searchResultsPage.enterSearchWithinTerm(searchWithInText);
				searchResultsPage.clickOnSearchWithInSearch();

				if (!(homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsDisplayedBasedOnSearchWithInTerm(searchWithInText);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary,
							searchWithinKey,searchWithInText, jiraNumber);
				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, "SearchWithin results returned zero results",
							searchWithinKey,searchWithInText, jiraNumber);
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
