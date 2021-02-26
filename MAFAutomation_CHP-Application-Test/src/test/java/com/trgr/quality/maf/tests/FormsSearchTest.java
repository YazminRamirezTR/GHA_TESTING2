
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
import com.trgr.quality.maf.pages.FormsPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class FormsSearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	JSONObject jsonObject;
	FormsPage formspage;
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
			/* Saving homepage driver copy for signout */
			homepagecopy = this.homepage;

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

	
	
	@Test(priority = 1, groups = { "chpmex" }, description = "MAFAUTO-246")
	public void searchFormsUsingIssuingbody() throws Exception {
		softAssert = new SoftAssert();

		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "FormsSearch", testResult.getMethod().getMethodName());

			boolean searchResultsDisplayed = false;
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Official Forms - Search Template - Update filters - Add Authority filter and remove Title filter");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String issuingbodyKey = "issuingbody";
				String issuingbodyVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, issuingbodyKey,
						extentLogger);
				String listissuingbodyKey = "listissuingbody";
				String listissuingbodyVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, listissuingbodyKey,
						extentLogger);
				
				String AdvanceSearchKey = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "AdvanceSearch",
						extentLogger);

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();
				searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
					searchpage.selectAreaFromContentTree(thematicareaVal);
				}

				formspage = searchpage.openFormsPage();
				
				boolean advsearchlink = searchpage.clickOnExpectedAdvancedSearch(AdvanceSearchKey);

				softAssert.assertTrue(advsearchlink, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, advsearchlink,"Advance Search link  displayed as expected",jiraNumber);
				formspage=searchpage.isDisplayedAdvPageFormsPage();
				
				boolean isformspagedisplayed = formspage.isFormsPageDispalyed();
				if (isformspagedisplayed) {
					formspage.enterIssuingBody(issuingbodyVal);
					formspage.clickCleanButton();
				}

				formspage.enterIssuingBody(issuingbodyVal);
				searchResultsPage = formspage.clickSearchButton();
				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", issuingbodyKey,
								issuingbodyVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}
				documentPage = searchResultsPage.clickOnGivenDocumentToClickOnResults(listissuingbodyVal);
				boolean istitledisplayed = documentPage.validateFormTitle(issuingbodyVal);
				Thread.sleep(1000);

				softAssert.assertTrue(istitledisplayed && searchResultsDisplayed && isformspagedisplayed,
						jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, istitledisplayed && searchResultsDisplayed && isformspagedisplayed,
						issueSummary, listissuingbodyKey + ":" + listissuingbodyVal,
						issuingbodyKey + ":" + issuingbodyVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	
	@Test(priority = 2, groups = { "chpmex" }, description = "MAFAUTO-216")
	public void searchFormsUsingThematic() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Thematic Search with Forms");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				JSONArray thematic_array = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild,
						"thematicsearchandsugestion", extentLogger);
				String thematicsearchVal = thematic_array.get(0).toString();
				String thematicSuggestionVal = thematic_array.get(1).toString();
				JSONArray forms_array = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild,
						"formssearchandsuggestion", extentLogger);
				String formssearchVal = forms_array.get(0).toString();
				String formsSuggestionVal = forms_array.get(1).toString();

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
					searchpage.selectAreaFromContentTree(thematicareaVal);
				}

				formspage = searchpage.openFormsPage();
				formspage.enterThematicOnSearchPage(thematicsearchVal);
				Thread.sleep(1000); // this wait is needed for dropdown
									// to display
				boolean suggestionsDisplayed = formspage.isTheSuggestionsDropdownDisplayed()
						&& formspage.isSearchStringhighlightedOnCombo(thematicsearchVal);

				formspage.ScrollToGivenSearchString(thematicSuggestionVal);
				suggestionsDisplayed = formspage.isResultCountDisplayedForFstSearchString();
				formspage.enterSecondThematicSearchString(formssearchVal);

				suggestionsDisplayed = formspage.isTheSuggestionsDropdownDisplayed()
						&& formspage.isSearchStringhighlightedOnCombo(formssearchVal);

				formspage.ScrollToGivenSearchString(formsSuggestionVal);
				suggestionsDisplayed = formspage.isResultCountDisplayedForFstSearchString();
				formspage.clickClear();

				formspage.enterThematicOnSearchPage(thematicsearchVal);
				Thread.sleep(1000);
				formspage.ScrollToGivenSearchString(thematicSuggestionVal);
				Thread.sleep(2000);

				searchResultsPage = formspage.clickOnSearch();
				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", thematicsearchVal,
								thematicSuggestionVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean iffilterbyindexdispalyed = searchResultsPage.isFilterByIndexDispalyed();
				if (iffilterbyindexdispalyed) {
					softAssert.assertTrue(suggestionsDisplayed && iffilterbyindexdispalyed,
							jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, formssearchVal,
							formsSuggestionVal, jiraNumber);
				}
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 3, groups = { "chpmex", "chpbr" }, description = "MAFQABANG-426")
	public void searchFormsUsingFreeword() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			HomePage homepage = this.homepage;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify Advance search  Forms using freeword");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchpage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				
				searchResultsPage = searchpage.clickOnSearch();
				if (!(homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					boolean searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
							&& searchResultsPage.isFacetingDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(freewordVal);
					softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, freewordKey, freewordVal,
							jiraNumber);

				}
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}

	}

	@Test(priority = 4, groups = { "chpmex" }, description = "MAFQABANG-427")
	public void searchFormsUsingIssuingBody() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			HomePage homepage = this.homepage;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify advance search form using Issuing Body");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				
				String thematicareaKey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String issuingbodyKey = "issuingbody";
				String issuingbodyVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, issuingbodyKey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
					searchpage.selectAreaFromContentTree(thematicareaVal);
				}

				formspage = searchpage.openFormsPage();
				boolean isformspagedisplayed = formspage.isFormsPageDispalyed();
				if (isformspagedisplayed) {
					formspage.enterIssuingBody(issuingbodyVal);
					formspage.clickCleanButton();
				}
			
				formspage.enterIssuingBody(issuingbodyVal);
				searchResultsPage = formspage.clickOnSearch();
				if (!(homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed())) {
					boolean searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()							
							&& searchResultsPage.isSearchWithinOptionDisplayed()
							&& searchResultsPage.searchReturnedResultsAsExpected(issuingbodyVal);
					softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, issuingbodyKey, issuingbodyVal,
							jiraNumber);
				} else {

					logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", issuingbodyKey,
							issuingbodyVal, jiraNumber);
				}
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
