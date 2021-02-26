package com.trgr.quality.maf.tests;

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
import com.trgr.quality.maf.pages.ObligacionesPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
import com.trgr.quality.maf.pages.ToolsPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Class for running Tools Test cases This class defines methods for verifying
 * 'Tools' test cases
 * 
 * @author Avanish Singh
 * @version 1.0
 */
public class ObligacionesTest extends BaseTest {

	/*
	 * Tools_TC_001: Verify the display of tools tab in the Menu bar.
	 * Tools_TC_002: Verify the tools tab in the Menu bar. Tools_TC_003: Verify
	 * the expand feature in the tools page Tools_TC_004: Verify the collapse
	 * feature in the tools page
	 */

	ToolsPage toolspage;
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchResultsPage searchResultsPage;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	ObligacionesPage obligacionesPage;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void startObligacionesTest() throws Exception {
		try {

			loginpage = new LoginPage(driver, ProductUrl);
			homepage = loginpage.Login(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username"),
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password"));
			toolspage = homepage.openToolsPage();
			jsonReader = new JsonReader();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Obligaciones", "startObligacionesTest");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the Tools test are not run.<br>"
							+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}
	}

	@AfterClass(alwaysRun = true)
	public void endObligacionesTest(){
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
		}
	}


	@Test(groups = { "chparg" }, description = "MAFQABANG-489")
	public void displayOfObligationPageAndCheckForNoResultsMsg() throws Exception {
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Obligaciones", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"To verify error after clicking search(buscar) of empty Obligaciones");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				toolspage = homepage.openToolsPage();
				toolspage.expandCotizaciones();
				obligacionesPage = toolspage.clickToOpenObligacionesPage();
				String errormessageKey = "errormessage";
				String errormessage = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,errormessageKey,extentLogger); 

				boolean isSearchTemplatevalidated = obligacionesPage.isObligacionesPageDisplayedAsExpected();
				softas.assertTrue(isSearchTemplatevalidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchTemplatevalidated,
						"Obligation page is displayed with expected fields", jiraNumber);

				searchResultsPage = obligacionesPage.clickOnSearch();
				boolean iserrorvalidated = obligacionesPage.isErrorMessageDisplayed(errormessage);

				softas.assertTrue(iserrorvalidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, iserrorvalidated,
						issueSummary,errormessageKey,errormessage, jiraNumber);
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
	 * This test checks Limpar (Clear) and Reformular(Modify Searchlink)
	 * functionality
	 */
	@Test(groups = { "chparg" }, description = "MAFQABANG-490")
	public void checkClearAndModifySearch() throws Exception {
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();

			extentLogger = setUpExtentTest(extentLogger, "Obligaciones", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger);
				String denominacionKey = "denominación";
				String denominacion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,denominacionKey,extentLogger);
				String claseKey = "clase";
				String clase = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,claseKey,extentLogger); 

				String issueSummary = getIssueTitle(jiraNumber, "Validate  Modify Search links and clear button");
				toolspage = homepage.openToolsPage();
				toolspage.expandCotizaciones();
				obligacionesPage = toolspage.clickToOpenObligacionesPage();
				obligacionesPage.enterPeriodValue(period);
				obligacionesPage.enterDenominacion(denominacion);
				obligacionesPage.enterClase(clase);

				searchResultsPage = obligacionesPage.clickOnSearch();

				if (!(homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsDisplayedBasedOnSearchData(period,
							denominacion, clase);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed,"Search results displayed as expected",
							periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase,jiraNumber);
				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, "Search results returned zero results",
							periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase,jiraNumber);
				}

				searchResultsPage.ClickOnModifySearchLink();

				boolean isSearchDataRetained = obligacionesPage.isGivenSearchDataRetainedOnPage(period);

				softas.assertTrue(isSearchDataRetained, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchDataRetained,
						"Clicking Edit Search link retained values from initial search",
						"defect : MAFQABANG-553" + "<br>"+
								periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase,jiraNumber);

				boolean isClearbuttonPresent = obligacionesPage.clicklimpiar();

				softas.assertTrue(isClearbuttonPresent, jiraNumber + ":" + issueSummary + ": Clear button is Present");
				logExtentStatus(extentLogger, isClearbuttonPresent, ":Clear button is Present",
						jiraNumber);

				boolean isSearchFieldsCleared = obligacionesPage.isSearchFieldsCleared();
				softas.assertTrue(isSearchFieldsCleared,
						jiraNumber + ":" + issueSummary + " : Fileds are empty after clicking on clear button");
				logExtentStatus(extentLogger, isSearchFieldsCleared,
						" Fileds are empty after clicking on clear button",
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

	/*
	 * This test checks for functionality of New (Nuvea) search link and Limpar
	 * (Clear) feature
	 */
	@Test(groups = { "chparg" }, description = "MAFQABANG-544")
	public void checkNewSearchLink() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Obligaciones", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Check functionality of Nuvea(New) button for obligaciones");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger);
				String denominacionKey = "denominación";
				String denominacion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,denominacionKey,extentLogger);
				String claseKey = "clase";
				String clase = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,claseKey,extentLogger); 

				toolspage = homepage.openToolsPage();
				toolspage.expandCotizaciones();
				obligacionesPage = toolspage.clickToOpenObligacionesPage();
				obligacionesPage.enterPeriodValue(period);
				obligacionesPage.enterDenominacion(denominacion);
				obligacionesPage.enterClase(clase);

				searchResultsPage = obligacionesPage.clickOnSearch();

				if (!(homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.searchResultsDisplayedBasedOnSearchData(period,
							denominacion, clase);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected",
							periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase,jiraNumber);
				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, "Search results returned zero results",
							periodKey+","+denominacionKey+","+claseKey,period+","+denominacion+","+clase,jiraNumber);

				}
				searchResultsPage.ClickOnNewSearchLink();

				boolean isSearchDataRetained = obligacionesPage.isGivenSearchDataDeletedFromPage();

				softas.assertFalse(isSearchDataRetained, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, !isSearchDataRetained,issueSummary,
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

	/*
	 * This test is validating the search within feature
	 */
	@Test(groups = { "chparg" }, description = "MAFQABANG-545")
	public void searchWithInOnResultSet() throws Exception {
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Obligaciones", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Search within search for Obligaciones");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String periodKey = "period";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger); 

				String searchwithinKey = "searchwithintext";
				String searchwithintext = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,searchwithinKey,extentLogger); 
				toolspage = homepage.openToolsPage();
				toolspage.expandCotizaciones();
				obligacionesPage = toolspage.clickToOpenObligacionesPage();
				obligacionesPage.enterPeriodValue(period);

				searchResultsPage = obligacionesPage.clickOnSearch();

				searchResultsPage.enterSearchWithinTerm(searchwithintext);
				searchResultsPage = obligacionesPage.clickOnSearch();
				boolean searchResultsverfired = searchResultsPage
						.searchResultsDisplayedBasedOnSearchWithInTerm(searchwithintext);

				softas.assertTrue(searchResultsverfired, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsverfired,
						issueSummary, searchwithinKey, searchwithintext,jiraNumber);
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
