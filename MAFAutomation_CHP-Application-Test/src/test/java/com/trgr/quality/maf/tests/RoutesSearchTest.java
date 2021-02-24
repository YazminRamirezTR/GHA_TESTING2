
package com.trgr.quality.maf.tests;

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
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.RoutesPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class RoutesSearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	JSONObject jsonObject;
	RoutesPage routespage;
	DocumentDisplayPage documentPage;
	JsonReader jsonReader;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		try {
			loginpage = new LoginPage(driver, ProductUrl);

			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");

			homepage = loginpage.Login(username, password);
			jsonReader = new JsonReader();
		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Search", "StartSearchTest");
			extentLogger.log(LogStatus.FAIL,
					"Due to PreRequest Failed : Validations on the Search test are not run.<br>"
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


	// Roteiros (Routes) page is available only in CHP Brazil
	@Test(priority = 1, groups = { "chpbr" }, description = "MAFQABANG-71")
	public void searchRoutesAndSearchwithinUsingFreeword() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Search for Routes and then Searchwithin with Freeword");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String searchKey = "searchtext";
				String searchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searchKey, extentLogger);
				String searchwithinKey = "searchwithintext";
				String searchwithinVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searchwithinKey,
						extentLogger);

				homepage = homepage.openHomepage();
				RoutesPage routespage;
				searchpage = homepage.OpenSearchPage();
				routespage = searchpage.OpenRoutesPage();
				routespage.selectValueForDateDuration("any");
				routespage.selectSearchByTerm();
				routespage.enterFreeWordOnSearchPage(searchVal);
				searchResultsPage = routespage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", searchKey, searchVal,
								jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean searchWithInOptionDisplayed = searchResultsPage.isSearchWithinOptionDisplayed();

				// getting document Count
				int docCount = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());

				// verify search within is working
				searchResultsPage.enterSearchWithinTerm(searchwithinVal);
				searchResultsPage.clickOnSearchWithInSearch();

				// Verify the count of the documents returned is less
				// than the count from parent set
				int docCountAfterSearchWithin = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());
				softAssert.assertTrue(docCount > docCountAfterSearchWithin,
						"MAFQABANG-71 : Doc count after search within is less than doc count with initial search");
				logExtentStatus(extentLogger, docCount > docCountAfterSearchWithin,
						"Doc count after search within is less than doc count with initial search", searchwithinKey,
						searchwithinVal, jiraNumber);

				Boolean isSearchWithinWorking = searchResultsPage.searchWithInResultsDisplayed(searchwithinVal);

				documentPage = searchResultsPage.clickFirstLink();
				isSearchWithinWorking = isSearchWithinWorking
						&& documentPage.isSearchTextDisplayedOnTheFullTextDoc(searchwithinVal);

				softAssert.assertTrue(isSearchWithinWorking && searchWithInOptionDisplayed && searchResultsDisplayed,
						jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchWithinWorking, issueSummary, searchwithinKey, searchwithinVal,
						jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	// Roteiros (Routes) page is available only in CHP Brazil
	@Test(priority = 2, groups = { "chpbr" }, description = "MAFQABANG-56")
	public void searchRoutesUsingStandardNumber() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify search with standard number field");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String standardnumberkey = "standardnumber";
				String standardnumberVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, standardnumberkey,
						extentLogger);

				homepage = homepage.openHomepage();
				RoutesPage routespage;
				searchpage = homepage.OpenSearchPage();
				routespage = searchpage.OpenRoutesPage();
				routespage.enterStandardNumber(standardnumberVal);
				searchResultsPage = routespage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", standardnumberkey,
								standardnumberVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				int doccount = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());
				if (doccount > 0) {
					searchResultsDisplayed = true;
				} else
					searchResultsDisplayed = false;

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, standardnumberkey,
						standardnumberVal, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}

	}
	
	
}
