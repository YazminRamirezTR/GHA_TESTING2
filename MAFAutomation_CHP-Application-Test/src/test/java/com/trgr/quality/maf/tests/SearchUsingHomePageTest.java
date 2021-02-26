
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
import com.trgr.quality.maf.pages.DeliveryPage;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class SearchUsingHomePageTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchResultsPage searchResultsPage;
	JSONObject jsonObject;
	DeliveryPage deliverypage;
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


	@Test(priority = 0, groups = {"chpmex","chparg","chppe","chpchile" }, description = "MAFQABANG-50")
	public void searchUsingFreewordOnHomepage() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify search with free text in Home Page with Valid and Invalid Keyword");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String zeroresultsKey = "zeroresults";
				String zeroresultsVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, zeroresultsKey,
						extentLogger);
				String errormsgKey = "errormsg";
				String errormsgVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, errormsgKey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
					searchResultsPage = homepage.clickSearch();
				}
				 
				else {
				// CHP URY has clicking of additional radio button for
				// displaying
				// the freeword field to search
				if (!homepage.isFreewordFieldDisplayed()) {
					homepage.clickThematicSearchRadioButton();
				}
				homepage.clickRefreshforThematicSearch();
				homepage.enterFreewordOnQuickSearch(freewordVal);
				searchResultsPage = homepage.clickSearch();
				}

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordKey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + "SearchResults Page Displayed");
				logExtentStatus(extentLogger, searchResultsDisplayed, "SearchResults Page Displayed", jiraNumber);
                
				homepage = searchResultsPage.clickonHomeTab();
				

				if(BaseTest.productUnderTest.equals("chpmex")) {

					homepage.enterTextInSearchField(zeroresultsVal);
					searchResultsPage = homepage.clickSearch();
				}

				else {
				    homepage.clickRefreshforThematicSearch();
					homepage.enterFreewordOnQuickSearch(zeroresultsVal);
					searchResultsPage = homepage.clickSearch();
				} 
			
				
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", zeroresultsKey,
								zeroresultsVal, jiraNumber);
				}

				boolean iszeroresultfreesearchexist = homepage.ValdiateErrorMsgforNoResults(errormsgVal);

				softAssert.assertTrue(iszeroresultfreesearchexist, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, iszeroresultfreesearchexist, issueSummary, errormsgKey, errormsgVal,
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

	// this test to be checked for chpbr.currently homepage giving error
	@Test(priority = 1, groups = { "chparg", "chpmex","chppe","chpchile" }, description = "MAFQABANG-49")
	public void thematicSearchOnHomepage() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			boolean searchResultsDisplayed = false;
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verify thematic search with valid input on homepage");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicKey = "thematicsearch";
				String thematicVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicKey, extentLogger);
				String suggestionKey = "suggestionvalue";
				String suggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestionKey, extentLogger);
				

				homepage = homepage.openHomepage();
				homepage.clickClear();
				boolean isfreeworddisplayed = homepage.isFreewordFieldDisplayed();
				if (!isfreeworddisplayed) {
					homepage.clickThematicSearchRadioButton();
				}
								
				if(BaseTest.productUnderTest.equals("chpmex"))
				{
				homepage.enterTextInSearchField(thematicVal);
				}
				else
				{
				homepage.enterThematicSearchOnQuickSearch(thematicVal);
				}
				
				boolean issuggestiondisplayed = homepage.isTheSuggestionsDropdownDisplayed();
				
				if(issuggestiondisplayed)
				{
					homepage.selectTitleInSearchSuggestions(suggestionVal);
				}
				
				searchResultsPage = homepage.clickSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound =  homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", thematicKey,
								thematicVal, jiraNumber);
					else {
						boolean isErrorDisplayed = homepage.errorBlockDisplayed();
						logExtentStatus(extentLogger, !isErrorDisplayed, "Search failed :", jiraNumber);
						softAssert.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed :");
						if(isErrorDisplayed){//Log existing bug
							testResult.setAttribute("defect", "MAFAUTO-271");
						}
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, thematicKey, thematicVal,
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

	/////////// CHP URY - New Test Cases Automation
	@Test(priority = 3, groups = { "chpury" }, description = "MAFQABANG-499")
	public void ThematicSearchUsingFreewordInHome() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Search using freeword on Home page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();
				homepage.clickThematicSearchRadioButton();
				homepage.enterFreewordOnQuickSearch(freewordVal);
				searchResultsPage = homepage.clickSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordKey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, freewordKey, freewordVal,
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

	@Test(priority = 4, groups = { "chpury","chppe","chpchile" }, description = "MAFQABANG-500")
	public void ClickClearWithFreewordAndThematicKey() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Clear Thematic and Free Word when fields have no data");

			String freeword = "Impuesto";
			String suggestionvalue = "ACTIVO POR IMPUESTO DIFERIDO";

			homepage = homepage.openHomepage();
			homepage.maximizeAllWidgetsInHome();
			homepage.clickClear();
			
			boolean isfreeworddispalyed = homepage.isFreewordFieldDisplayed();
			if (!isfreeworddispalyed) {
				homepage.clickThematicSearchRadioButton();
			}
			
			homepage.enterFreewordOnQuickSearch(freeword);
			homepage.enterThematicSearchOnQuickSearch(freeword);
			
			boolean issuggestiondisplayed = homepage.isTheSuggestionsDropdownDisplayed();
			if(issuggestiondisplayed)
			{
				homepage.selectTitleInSearchSuggestions(suggestionvalue);
			}
			
			homepage.clickClear();
			Thread.sleep(2000);
			String clearedText = homepage.getFreewordTextOnHomePage();

			boolean freewordCleared = clearedText.equals("");
			clearedText = homepage.getThematicTextOnHomePage();
			freewordCleared = freewordCleared && (clearedText.equals("") || clearedText.equals("Búsqueda Temática"));

			softAssert.assertTrue(freewordCleared, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, freewordCleared, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 5, groups = { "chpury" }, description = "MAFQABANG-501")
	public void searchUsingThematicQuickSearch() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Search for Thesaurus with a voice - With results");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();
				homepage.clickThematicSearchRadioButton();
				homepage.enterThematicSearchOnQuickSearch(freewordVal);
				searchResultsPage = homepage.clickSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				searchResultsDisplayed = searchResultsDisplayed
						&& searchResultsPage.isSearchTermPresentInWidget(freewordVal);
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, freewordkey, freewordVal,
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

	@Test(priority = 6, groups = { "chpury" }, description = "MAFQABANG-502")
	public void clearSearchDataSubmittedOnQuickSearch() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Clearing the first Thesaurus voice when there is a single voice");

			// Not using the test data file as the goal of this test is to
			// verify the clear button functionality
			// which does not depend on the test data.
			homepage = homepage.openHomepage();
			homepage.clickClear();
			homepage.clickThematicSearchRadioButton();
			homepage.enterThematicSearchOnQuickSearch("test");
			homepage.closeTheSuggestionsDropdownIfDisplayed();
			homepage.clickXToClear();
			String clearedText = homepage.getThematicTextOnHomePage();

			boolean searchCleared = clearedText.equals("") | clearedText.equals("Búsqueda Temática");
			softAssert.assertTrue(searchCleared, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, searchCleared, "Clicking clear after entering Thematic validated",
					jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 7, groups = { "chpury", "chppe","chpchile"}, description = "MAFQABANG-506")
	public void StandardNumberSearchInHome() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Search by Standard number with results");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String standardnumberKey = "standardnumber";
				String standardnumberVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, standardnumberKey,
						extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();
				
				//this verification is done to make the standard number field visible if not displaying already
				boolean isstandardnofielddisplayed = homepage.isStandardSearchboxDisplayed();
				if(!isstandardnofielddisplayed)
				{
					homepage.clickStandardNumberRadioButton();
				}
				
				homepage.enterStandardNumberOnQuickSearch(standardnumberVal);
				searchResultsPage = homepage.clickSearchwhenStandardNumberSelected();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", standardnumberKey,
								standardnumberVal, jiraNumber);
					else {
						boolean isErrorDisplayed = homepage.errorBlockDisplayed();
						logExtentStatus(extentLogger, !isErrorDisplayed, "Search failed :", jiraNumber);
						softAssert.assertTrue(!isErrorDisplayed, jiraNumber + ":Search failed");
					}

					continue; // Skip this & Continue with next iteration
				}

				searchResultsDisplayed = searchResultsDisplayed
						&& searchResultsPage.isSearchTermPresentInWidget(standardnumberVal);

				if(!searchResultsDisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-284");
				}
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, standardnumberKey,
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

	@Test(priority = 8, groups = { "chpury" }, description = "MAFQABANG-508")
	public void ThematicSearchWithThreeLevelIntersection() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Thematic Search With Three level intersection");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicfirstlevelkey = "thematickeyword";
				String thematicfirstlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thematicfirstlevelkey, extentLogger);
				String suggestionfirstlevelkey = "thematicsuggestiontitle";
				String suggestionfirstlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						suggestionfirstlevelkey, extentLogger);

				String thematicsecondlevelkey = "thematickeywordsecondlevel";
				String thematicsecondlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thematicsecondlevelkey, extentLogger);
				String suggestionsecondlevelkey = "thematicsuggestiontitlesecondlevel";
				String suggestionsecondlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						suggestionsecondlevelkey, extentLogger);

				String thematicthirdlevelkey = "thematickeywordthirdlevel";
				String thematicthirdlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thematicthirdlevelkey, extentLogger);
				String suggestionthirdlevelkey = "thematicsuggestiontitlethirdlevel";
				String suggestionthirdlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						suggestionthirdlevelkey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();

				homepage.clickThematicSearchRadioButton();
				homepage.enterThematicSearchOnQuickSearch(thematicfirstlevelVal);

				boolean suggestions1Selected = homepage.isThematicDropdownDisplayedWithResults()
						&& homepage.isTitlePresentInSearchSuggestions(suggestionfirstlevelVal)
						&& homepage.selectTitleInSearchSuggestions(suggestionfirstlevelVal);
				softAssert.assertTrue(suggestions1Selected,
						jiraNumber + ":Suggestion Title selected for first search box");
				logExtentStatus(extentLogger, suggestions1Selected, "Suggestion Title selected for first search box",
						thematicfirstlevelkey + ":" + thematicfirstlevelVal,
						suggestionfirstlevelkey + ":" + suggestionfirstlevelVal, jiraNumber);

				boolean suggestions2Selected = homepage.isSecondThematicSearchBoxDisplayed();
				if (suggestions2Selected) {
					homepage.enterSecondThematicSearchText(thematicsecondlevelVal);
					suggestions2Selected = homepage.isTitlePresentInSearchSuggestions(suggestionsecondlevelVal)
							&& homepage.selectTitleInSearchSuggestions(suggestionsecondlevelVal);
				}
				softAssert.assertTrue(suggestions2Selected,
						jiraNumber + ": Suggestion Title selected for Second search box");
				logExtentStatus(extentLogger, suggestions2Selected, "Suggestion Title selected for Second search box",
						thematicsecondlevelkey + ":" + thematicsecondlevelVal,
						suggestionsecondlevelkey + ":" + suggestionsecondlevelVal, jiraNumber);

				boolean suggestions3Selected = homepage.isThirdThematicSearchBoxDisplayed();
				if (suggestions3Selected) {
					homepage.enterThirdThematicSearchText(thematicthirdlevelVal);
					suggestions3Selected = homepage.isTitlePresentInSearchSuggestions(suggestionthirdlevelVal)
							&& homepage.selectTitleInSearchSuggestions(suggestionthirdlevelVal);
				}
				softAssert.assertTrue(suggestions3Selected,
						jiraNumber + ": Suggestion Title selected for Third search box");
				logExtentStatus(extentLogger, suggestions3Selected, "Suggestion Title selected for Third search box",
						thematicthirdlevelkey + ":" + thematicthirdlevelVal,
						suggestionthirdlevelkey + ":" + suggestionthirdlevelVal, jiraNumber);

				searchResultsPage = homepage.clickSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", thematicfirstlevelkey,
								thematicfirstlevelVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				searchResultsDisplayed = searchResultsDisplayed
						&& searchResultsPage.isSearchTermPresentInWidget(suggestionfirstlevelVal)
						&& searchResultsPage.isSearchTermPresentInWidget(suggestionsecondlevelVal)
						&& searchResultsPage.isSearchTermPresentInWidget(suggestionthirdlevelVal);

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary,
						thematicfirstlevelkey + ":" + thematicsecondlevelkey + ":" + thematicthirdlevelkey,
						thematicfirstlevelVal + ":" + thematicsecondlevelVal + ":" + thematicthirdlevelVal, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 9, groups = { "chpury" }, description = "MAFQABANG-509")
	public void ThematicSearchWithSecondLevelIntersection() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			boolean searchResultsDisplayed = false;
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Thematic Search With Two level intersection ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicfirstlevelkey = "thematickeyword";
				String thematicfirstlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thematicfirstlevelkey, extentLogger);
				String suggestionfirstlevelkey = "thematicsuggestiontitle";
				String suggestionfirstlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						suggestionfirstlevelkey, extentLogger);

				String thematicsecondlevelkey = "thematickeywordsecondlevel";
				String thematicsecondlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thematicsecondlevelkey, extentLogger);
				String suggestionsecondlevelkey = "thematicsuggestiontitlesecondlevel";
				String suggestionsecondlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						suggestionsecondlevelkey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();

				homepage.clickThematicSearchRadioButton();
				homepage.enterThematicSearchOnQuickSearch(thematicfirstlevelVal);

				boolean suggestions1Selected = homepage.isThematicDropdownDisplayedWithResults()
						&& homepage.isTitlePresentInSearchSuggestions(suggestionfirstlevelVal)
						&& homepage.selectTitleInSearchSuggestions(suggestionfirstlevelVal);
				softAssert.assertTrue(suggestions1Selected,
						jiraNumber + ":Suggestion Title selected for first search box");
				logExtentStatus(extentLogger, suggestions1Selected, "Suggestion Title selected for first search box",
						thematicfirstlevelkey + ":" + thematicfirstlevelVal,
						suggestionfirstlevelkey + ":" + suggestionfirstlevelVal, jiraNumber);

				boolean suggestions2Selected = homepage.isSecondThematicSearchBoxDisplayed();
				if (suggestions2Selected) {
					homepage.enterSecondThematicSearchText(thematicsecondlevelVal);
					suggestions2Selected = homepage.isTitlePresentInSearchSuggestions(suggestionsecondlevelVal)
							&& homepage.selectTitleInSearchSuggestions(suggestionsecondlevelVal);
				}
				softAssert.assertTrue(suggestions2Selected,
						jiraNumber + ":Suggestion Title selected for Second search box");
				logExtentStatus(extentLogger, suggestions2Selected, "Suggestion Title selected for Second search box",
						thematicsecondlevelkey + ":" + thematicsecondlevelVal,
						suggestionsecondlevelkey + ":" + suggestionsecondlevelVal, jiraNumber);

				searchResultsPage = homepage.clickSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", thematicfirstlevelkey,
								thematicfirstlevelVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				searchResultsDisplayed = searchResultsDisplayed
						&& searchResultsPage.isSearchTermPresentInWidget(suggestionfirstlevelVal)
						&& searchResultsPage.isSearchTermPresentInWidget(suggestionsecondlevelVal);
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary,
						thematicfirstlevelkey + ":" + thematicsecondlevelkey,
						thematicfirstlevelVal + ":" + thematicsecondlevelVal, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 10, groups = { "chpury" }, description = "MAFQABANG-510")
	public void clearStandardNumberSearchInHome() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Clear Standard Number Search on Home page");
			// Test data file is not required.
			String testData = "25252";

			homepage = homepage.openHomepage();
			homepage.clickClear();
			homepage.clickStandardNumberRadioButton();
			homepage.enterStandardNumberOnQuickSearch(testData);
			homepage.clickClear();
			String clearedText = homepage.getStandardNumberTextOnHomePage();

			boolean searchCleared = clearedText.equals("");
			softAssert.assertTrue(searchCleared, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, searchCleared, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 11, groups = { "chpury" }, description = "MAFQABANG-525")
	public void EmptyFreewordAndThematicKeySearch() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Search With Empty Freeword  and verify error message");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String expectedErrorMessagekey = "expectederrormessage";
				String expectedErrorMessageVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						expectedErrorMessagekey, extentLogger);
				homepage = homepage.openHomepage();

				homepage.clickThematicSearchRadioButton();
				homepage.clickClear();
				boolean freewordErrorValidated = homepage.clickSearchAndValidateErrorText(expectedErrorMessageVal);

				softAssert.assertTrue(freewordErrorValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, freewordErrorValidated, issueSummary, expectedErrorMessagekey,
						expectedErrorMessageVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 12, groups = { "chpury" }, description = "MAFQABANG-511")
	public void EmptyStandardNumberErroMessageInHome() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Search With Empty Standard Number and verify error message ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String expectedErrorMessagekey = "errormsg";
				String expectedErrorMessageVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						expectedErrorMessagekey, extentLogger);
				homepage = homepage.openHomepage();

				homepage.clickStandardNumberRadioButton();
				homepage.clickClear();
				boolean numberErrorValidated = homepage.clickSearchAndValidateErrorText(expectedErrorMessageVal);

				softAssert.assertTrue(numberErrorValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, numberErrorValidated, issueSummary, expectedErrorMessagekey,
						expectedErrorMessageVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 13, groups = { "chpury" }, description = "MAFQABANG-512")
	public void StandardNumberSearchWithLetters() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Search Standard Number with letters and verify error message ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String invalidStandardNumberkey = "invalildstandardnumber";
				String invalidStandardNumberVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						invalidStandardNumberkey, extentLogger);
				String expectedErrorMessagekey = "expectederrormessage";
				String expectedErrorMessageVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						expectedErrorMessagekey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickStandardNumberRadioButton();
				homepage.clickClear();
				homepage.enterStandardNumberOnQuickSearch(invalidStandardNumberVal);
				boolean numberErrorValidated = homepage.clickSearchAndValidateNoDocsFound(expectedErrorMessageVal);

				softAssert.assertTrue(numberErrorValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, numberErrorValidated, issueSummary, invalidStandardNumberkey,
						invalidStandardNumberVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 14, groups = { "chpury" }, description = "MAFQABANG-513")
	public void ClearThematicSearchWithMultipleFields() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Clear Freeword & Thematic Search With Multiple Fields ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicsearchkey = "thematickeyword";
				String thematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicsearchkey,
						extentLogger);
				String freeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "freeword", extentLogger);
				String suggestiontitleKey = "thematicsuggestiontitle";
				String suggestiontitleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestiontitleKey,
						extentLogger);

				String thematicsecondsearchkey = "thematickeywordsecondlevel";
				String thematicsecondsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thematicsecondsearchkey, extentLogger);
				String secondsuggestiontitleKey = "thematicsuggestiontitlesecondlevel";
				String secondsuggestiontitleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						secondsuggestiontitleKey, extentLogger);

				String thematicthirdsearchkey = "thematickeywordthirdlevel";
				String thematicthirdsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thematicthirdsearchkey, extentLogger);
				String thirdsuggestiontitleKey = "thematicsuggestiontitlethirdlevel";
				String thirdsuggestiontitleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thirdsuggestiontitleKey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();

				homepage.clickThematicSearchRadioButton();
				homepage.enterThematicSearchOnQuickSearch(thematicsearchVal);

				boolean suggestions1Selected = homepage.isThematicDropdownDisplayedWithResults()
						&& homepage.isTitlePresentInSearchSuggestions(suggestiontitleVal)
						&& homepage.selectTitleInSearchSuggestions(suggestiontitleVal);
				softAssert.assertTrue(suggestions1Selected,
						jiraNumber + ":Suggestion Title selected for first search box");
				logExtentStatus(extentLogger, suggestions1Selected, "Suggestion Title selected for first search box",
						suggestiontitleKey, suggestiontitleVal, jiraNumber);

				boolean suggestions2Selected = homepage.isSecondThematicSearchBoxDisplayed();
				if (suggestions2Selected) {
					homepage.enterSecondThematicSearchText(thematicsecondsearchVal);
					suggestions2Selected = homepage.isTitlePresentInSearchSuggestions(secondsuggestiontitleVal)
							&& homepage.selectTitleInSearchSuggestions(secondsuggestiontitleVal);
				}
				softAssert.assertTrue(suggestions2Selected,
						jiraNumber + ": Suggestion Title selected for Second search box");
				logExtentStatus(extentLogger, suggestions2Selected, " Suggestion Title selected for Second search box",
						thematicsecondsearchkey, thematicsecondsearchVal, jiraNumber);

				boolean suggestions3Selected = homepage.isThirdThematicSearchBoxDisplayed();
				if (suggestions3Selected) {
					homepage.enterThirdThematicSearchText(thematicthirdsearchVal);
					suggestions3Selected = homepage.isTitlePresentInSearchSuggestions(thirdsuggestiontitleVal)
							&& homepage.selectTitleInSearchSuggestions(thirdsuggestiontitleVal);
				}
				softAssert.assertTrue(suggestions3Selected,
						jiraNumber + ":  Suggestion Title selected for Third search box");
				logExtentStatus(extentLogger, suggestions3Selected, "Suggestion Title selected for Third search box",
						thematicthirdsearchkey, thematicthirdsearchVal, jiraNumber);
				 Thread.sleep(5000);
				homepage.enterFreewordOnQuickSearch(freeword);

				homepage.clickClear();
                Thread.sleep(5000);
				String clearedText = homepage.getFreewordTextOnHomePage();
				boolean searchCleared = clearedText.equals("");
			//	boolean searchBoxesHidden = homepage.isSecondThematicSearchBoxNotDisplayed()
				//		&& homepage.isThirdThematicSearchBoxNotDisplayed();

				softAssert.assertTrue(searchCleared , jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchCleared , issueSummary,
						thematicsearchkey + ":" + thematicsecondsearchkey + ":" + thematicthirdsearchkey,
						thematicsearchVal + ":" + thematicsecondsearchVal + ":" + thematicthirdsearchVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 15, groups = { "chpury" }, description = "MAFQABANG-514")
	public void ThematicSearchWithNoSuggestionsInHome() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Search With Thematic key for no results");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String invalildthematickey = "invalildthematickey";
				String invalildthematicVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, invalildthematickey,
						extentLogger);
				String nosuggestionserrorKey = "nosuggestionserror";
				String nosuggestionserrorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						nosuggestionserrorKey, extentLogger);
				String expectedErrorMessagekey = "expectederrormessage";
				String expectedErrorMessageVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						expectedErrorMessagekey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickThematicSearchRadioButton();
				homepage.enterThematicSearchOnQuickSearch(invalildthematicVal);

				//boolean suggestionsErrorValidated = homepage.isSuggestionsEmptyErrorPresent(nosuggestionserrorVal);
				//softAssert.assertTrue(suggestionsErrorValidated, jiraNumber + ":  No Suggestions error message");
				//logExtentStatus(extentLogger, suggestionsErrorValidated, "No Suggestions error message",
						//nosuggestionserrorKey, nosuggestionserrorVal, jiraNumber);

				boolean ErrorValidated = homepage.clickSearchAndValidateNoDocsFound(expectedErrorMessageVal);
				softAssert.assertTrue(ErrorValidated, jiraNumber + ": No results error message");
				logExtentStatus(extentLogger, ErrorValidated, issueSummary + ":No results error message",
						expectedErrorMessagekey, expectedErrorMessageVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 16, groups = { "chpury" }, description = "MAFQABANG-515")
	public void StandardNumberSearchWithSpecialCharactersInHome() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Standard number search with special characters");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String invalidStandardNumberkey = "invalidstandardnumber";
				String invalidStandardNumberVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						invalidStandardNumberkey, extentLogger);
				String expectedErrorMessagekey = "expectederrormessage";
				String expectedErrorMessageVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						expectedErrorMessagekey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickStandardNumberRadioButton();
				homepage.clickClear();
				homepage.enterStandardNumberOnQuickSearch(invalidStandardNumberVal);
				boolean numberErrorValidated = homepage.clickSearchAndValidateNoDocsFound(expectedErrorMessageVal);

				softAssert.assertTrue(numberErrorValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, numberErrorValidated, issueSummary, invalidStandardNumberkey,
						invalidStandardNumberVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 17, groups = { "chpury" }, description = "MAFQABANG-516")
	public void FreewordSearchWithNoResultsInHome() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Freeword Search for no results in Homepage and verify error message ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				String errmsgkey = "expectederrormessage";
				String errmsgVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, errmsgkey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickThematicSearchRadioButton();
				homepage.clickClear();
				homepage.enterFreewordOnQuickSearch(freewordVal);
				boolean errorValidated = homepage.clickSearchAndValidateNoDocsFound(errmsgVal);

				softAssert.assertTrue(errorValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, errorValidated, issueSummary, freewordkey, freewordVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 18, groups = { "chpury" }, description = "MAFQABANG-517")
	public void SearchWithFreewordAndThematicKeyInHome() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Search by Thematic and Free word in HomePage");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				String thematickey = "thematic";
				String thematicVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematickey, extentLogger);
				String suggestionkey = "thematicsuggestiontitle";
				String suggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestionkey,
						extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();

				homepage.clickThematicSearchRadioButton();
				homepage.enterThematicSearchOnQuickSearch(thematicVal);

				boolean suggestions1Selected = homepage.isThematicDropdownDisplayedWithResults()
						&& homepage.isTitlePresentInSearchSuggestions(suggestionVal)
						&& homepage.selectTitleInSearchSuggestions(suggestionVal);
				softAssert.assertTrue(suggestions1Selected, jiraNumber + ": Thematic Suggestion Title selected");
				logExtentStatus(extentLogger, suggestions1Selected, "Thematic Suggestion Title selected", suggestionkey,
						suggestionVal, jiraNumber);

				homepage.enterFreewordOnQuickSearch(freewordVal);
				searchResultsPage = homepage.clickSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				searchResultsDisplayed = searchResultsDisplayed
						&& searchResultsPage.isSearchTermPresentInWidget(freewordVal)
						&& searchResultsPage.isSearchTermPresentInWidget(suggestionVal);

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, freewordkey, freewordVal,
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

	@Test(priority = 19, groups = { "chpury" }, description = "MAFQABANG-518")
	public void FreewordSearchWithSpecialCharactersInHome() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Free word Search with special characters in HomePage and verify error message ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freewordwithspecialcharacters";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				String errmsgkey = "expectederrormessage";
				String errmsgVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, errmsgkey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickThematicSearchRadioButton();
				homepage.clickClear();
				homepage.enterFreewordOnQuickSearch(freewordVal);
				boolean errorValidated = homepage.clickSearchAndValidateNoDocsFound(errmsgVal);

				softAssert.assertTrue(errorValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, errorValidated, issueSummary, freewordkey, freewordVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 20, groups = { "chpury" }, description = "MAFQABANG-519")
	public void ThematicSearchWithSpecialCharactersInHome() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Thematic search with special characters in Homepage and verify the error message");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematickey = "thematicsearchwithspecialchar";
				String thematicVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematickey, extentLogger);
				String errmsgkey = "expectederrormessage";
				String errmsgVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, errmsgkey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickThematicSearchRadioButton();
				homepage.clickClear();
				homepage.enterThematicSearchOnQuickSearch(thematicVal);
				boolean errorValidated = homepage.clickSearchAndValidateNoDocsFound(errmsgVal);

				softAssert.assertTrue(errorValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, errorValidated, issueSummary, thematickey, thematicVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 21, groups = { "chpury" }, description = "MAFQABANG-520")
	public void ClearSecondThematicSearchWithTwoLevelInputs() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Clearing the second Thesaurus voice when there are two voices");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicsearchKey = "thematickeyword";
				String thematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicsearchKey,
						extentLogger);
				String firstsuggestionKey = "thematicsuggestiontitle";
				String firstsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, firstsuggestionKey,
						extentLogger);

				String secondthematicsearchkey = "thematickeywordsecondlevel";
				String secondthematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						secondthematicsearchkey, extentLogger);
				String secondsuggestionKey = "thematicsuggestiontitlesecondlevel";
				String secondsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, secondsuggestionKey,
						extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();

				homepage.clickThematicSearchRadioButton();
				homepage.enterThematicSearchOnQuickSearch(thematicsearchVal);

				boolean suggestions1Selected = homepage.isThematicDropdownDisplayedWithResults()
						&& homepage.isTitlePresentInSearchSuggestions(firstsuggestionVal)
						&& homepage.selectTitleInSearchSuggestions(firstsuggestionVal);
				softAssert.assertTrue(suggestions1Selected,
						jiraNumber + ": Suggestion Title selected for first search box");
				logExtentStatus(extentLogger, suggestions1Selected, "Suggestion Title selected for first search box",
						thematicsearchKey, firstsuggestionVal, jiraNumber);

				boolean suggestions2Selected = homepage.isSecondThematicSearchBoxDisplayed();
				if (suggestions2Selected) {
					homepage.enterSecondThematicSearchText(secondthematicsearchVal);
					suggestions2Selected = homepage.isTitlePresentInSearchSuggestions(secondsuggestionVal)
							&& homepage.selectTitleInSearchSuggestions(secondsuggestionVal);
				}
				softAssert.assertTrue(suggestions2Selected,
						jiraNumber + ": Suggestion Title selected for Second search box");
				logExtentStatus(extentLogger, suggestions2Selected, "Suggestion Title selected for Second search box",
						secondthematicsearchkey, secondsuggestionVal, jiraNumber);

				boolean searchBox2Cleared = homepage.clearSecondThematicSearchBox()
						|| homepage.isClearLinkNotDisplayedForSecondThematicSearchBox();
				
				//
				Thread.sleep(2000);

				softAssert.assertTrue(searchBox2Cleared, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchBox2Cleared, issueSummary, secondthematicsearchkey,
						secondsuggestionVal, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 22, groups = { "chpury" }, description = "MAFQABANG-521")
	public void ClearThirdThematicSearchWithThreeLevelInputs() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Clear Third Thematic Search input with Three intersection inputs");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicsearchKey = "thematickeyword";
				String thematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicsearchKey,
						extentLogger);
				String firstsuggestionKey = "thematicsuggestiontitle";
				String firstsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, firstsuggestionKey,
						extentLogger);

				String secondthematicsearchkey = "thematickeywordsecondlevel";
				String secondthematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						secondthematicsearchkey, extentLogger);
				String secondsuggestionKey = "thematicsuggestiontitlesecondlevel";
				String secondsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, secondsuggestionKey,
						extentLogger);

				String thirdthematicsearchkey = "thematickeywordthirdlevel";
				String thirdthematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thirdthematicsearchkey, extentLogger);
				String thirdsuggestionKey = "thematicsuggestiontitlethirdlevel";
				String thirdsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thirdsuggestionKey,
						extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();

				homepage.clickThematicSearchRadioButton();
				homepage.enterThematicSearchOnQuickSearch(thematicsearchVal);

				boolean suggestions1Selected = homepage.isThematicDropdownDisplayedWithResults()
						&& homepage.isTitlePresentInSearchSuggestions(firstsuggestionVal)
						&& homepage.selectTitleInSearchSuggestions(firstsuggestionVal);
				softAssert.assertTrue(suggestions1Selected,
						jiraNumber + ":Suggestion Title selected for first search box");
				logExtentStatus(extentLogger, suggestions1Selected, "Suggestion Title selected for first search box",
						thematicsearchKey, firstsuggestionVal, jiraNumber);

				boolean suggestions2Selected = homepage.isSecondThematicSearchBoxDisplayed();
				if (suggestions2Selected) {
					homepage.enterSecondThematicSearchText(secondthematicsearchVal);
					suggestions2Selected = homepage.isTitlePresentInSearchSuggestions(secondsuggestionVal)
							&& homepage.selectTitleInSearchSuggestions(secondsuggestionVal);
				}
				softAssert.assertTrue(suggestions2Selected,
						jiraNumber + ":  Suggestion Title selected for Second search box");
				logExtentStatus(extentLogger, suggestions2Selected, "Suggestion Title selected for Second search box",
						secondthematicsearchkey, secondsuggestionVal, jiraNumber);

				boolean suggestions3Selected = homepage.isThirdThematicSearchBoxDisplayed();
				if (suggestions3Selected) {
					homepage.enterThirdThematicSearchText(thirdthematicsearchVal);
					suggestions3Selected = homepage.isTitlePresentInSearchSuggestions(thirdsuggestionVal)
							&& homepage.selectTitleInSearchSuggestions(thirdsuggestionVal);
				}
				softAssert.assertTrue(suggestions3Selected,
						jiraNumber + ":  Suggestion Title selected for Third search box");
				logExtentStatus(extentLogger, suggestions3Selected, "Suggestion Title selected for Third search box",
						thirdthematicsearchkey, thirdsuggestionVal, jiraNumber);

				boolean searchBox3Cleared = homepage.clearThirdThematicSearchBox()
						&& homepage.isClearLinkNotDisplayedForThirdThematicSearchBox()
						&& homepage.isClearLinkDisplayedForFirstThematicSearchBox()
						&& homepage.isClearLinkDisplayedForSecondThematicSearchBox();

				softAssert.assertTrue(searchBox3Cleared, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchBox3Cleared, issueSummary, thirdthematicsearchkey,
						thirdsuggestionVal, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 23, groups = { "chpury" }, description = "MAFQABANG-523")
	public void ClearSecondThematicSearchWithThreeLevelInputs() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Clear Second Thematic Search input with Three intersection inputs");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String firstthematicKey = "thematickeyword";
				String firstthematicVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, firstthematicKey,
						extentLogger);
				String firstsuggestionKey = "thematicsuggestiontitle";
				String firstsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, firstsuggestionKey,
						extentLogger);

				String secondthematickey = "thematickeywordsecondlevel";
				String secondthematicVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, secondthematickey,
						extentLogger);
				String secondsuggestionKey = "thematicsuggestiontitlesecondlevel";
				String secondsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, secondsuggestionKey,
						extentLogger);

				String thirdthematickey = "thematickeywordthirdlevel";
				String thirdthematicVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thirdthematickey,
						extentLogger);
				String thirdsuggestionKey = "thematicsuggestiontitlethirdlevel";
				String thirdsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thirdsuggestionKey,
						extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();

				homepage.clickThematicSearchRadioButton();
				homepage.enterThematicSearchOnQuickSearch(firstthematicVal);

				boolean suggestions1Selected = homepage.isThematicDropdownDisplayedWithResults()
						&& homepage.isTitlePresentInSearchSuggestions(firstsuggestionVal)
						&& homepage.selectTitleInSearchSuggestions(firstsuggestionVal);
				softAssert.assertTrue(suggestions1Selected,
						jiraNumber + ": Suggestion Title selected for first search box");
				logExtentStatus(extentLogger, suggestions1Selected, "Suggestion Title selected for first search box",
						firstthematicKey, firstsuggestionVal, jiraNumber);

				boolean suggestions2Selected = homepage.isSecondThematicSearchBoxDisplayed();
				if (suggestions2Selected) {
					homepage.enterSecondThematicSearchText(secondthematicVal);
					suggestions2Selected = homepage.isTitlePresentInSearchSuggestions(secondsuggestionVal)
							&& homepage.selectTitleInSearchSuggestions(secondsuggestionVal);
				}
				softAssert.assertTrue(suggestions2Selected,
						jiraNumber + ": Suggestion Title selected for Second search box");
				logExtentStatus(extentLogger, suggestions2Selected, " Suggestion Title selected for Second search box",
						secondthematickey, secondsuggestionVal, jiraNumber);

				boolean suggestions3Selected = homepage.isThirdThematicSearchBoxDisplayed();
				if (suggestions3Selected) {
					homepage.enterThirdThematicSearchText(thirdthematicVal);
					suggestions3Selected = homepage.isTitlePresentInSearchSuggestions(thirdsuggestionVal)
							&& homepage.selectTitleInSearchSuggestions(thirdsuggestionVal);
				}
				softAssert.assertTrue(suggestions3Selected,
						jiraNumber + ":  Suggestion Title selected for Third search box");
				logExtentStatus(extentLogger, suggestions3Selected, "Suggestion Title selected for Third search box",
						thirdsuggestionKey, thirdsuggestionVal, jiraNumber);

				boolean searchBox3Cleared = homepage.clearSecondThematicSearchBox()
						&& homepage.isClearLinkNotDisplayedForSecondThematicSearchBox()
						&& homepage.isClearLinkDisplayedForFirstThematicSearchBox()
						&& homepage.isClearLinkDisplayedForThirdThematicSearchBox();

				softAssert.assertTrue(searchBox3Cleared, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchBox3Cleared, issueSummary, secondthematickey, secondsuggestionVal,
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

	@Test(priority = 24, groups = { "chpury" }, description = "MAFQABANG-524")
	public void ClearFirstThematicSearchWithThreeLevelInputs() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Clear First Thematic Search input with Three intersection inputs");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String firstthematicsearchkey = "thematickeyword";
				String firstthematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						firstthematicsearchkey, extentLogger);
				String firstsuggestionKey = "thematicsuggestiontitle";
				String firstsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, firstsuggestionKey,
						extentLogger);

				String secondthematicsearchKey = "thematickeywordsecondlevel";
				String secondthematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						secondthematicsearchKey, extentLogger);
				String secondsuggestionKey = "thematicsuggestiontitlesecondlevel";
				String secondsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, secondsuggestionKey,
						extentLogger);

				String thirdthematicsearchKey = "thematickeywordthirdlevel";
				String thirdthematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thirdthematicsearchKey, extentLogger);
				String thirdsuggestionKey = "thematicsuggestiontitlethirdlevel";
				String thirdsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thirdsuggestionKey,
						extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();

				homepage.clickThematicSearchRadioButton();
				homepage.enterThematicSearchOnQuickSearch(firstthematicsearchVal);

				boolean suggestions1Selected = homepage.isThematicDropdownDisplayedWithResults()
						&& homepage.isTitlePresentInSearchSuggestions(firstsuggestionVal)
						&& homepage.selectTitleInSearchSuggestions(firstsuggestionVal);
				softAssert.assertTrue(suggestions1Selected,
						jiraNumber + ": Suggestion Title selected for first search box");
				logExtentStatus(extentLogger, suggestions1Selected, "Suggestion Title selected for first search box",
						firstthematicsearchkey, firstsuggestionVal, jiraNumber);

				boolean suggestions2Selected = homepage.isSecondThematicSearchBoxDisplayed();
				if (suggestions2Selected) {
					homepage.enterSecondThematicSearchText(secondthematicsearchVal);
					suggestions2Selected = homepage.isTitlePresentInSearchSuggestions(secondsuggestionVal)
							&& homepage.selectTitleInSearchSuggestions(secondsuggestionVal);
				}
				softAssert.assertTrue(suggestions2Selected,
						jiraNumber + ":  Suggestion Title selected for Second search box");
				logExtentStatus(extentLogger, suggestions2Selected, "Suggestion Title selected for Second search box",
						secondthematicsearchKey, secondsuggestionVal, jiraNumber);

				boolean suggestions3Selected = homepage.isThirdThematicSearchBoxDisplayed();
				if (suggestions3Selected) {
					homepage.enterThirdThematicSearchText(thirdthematicsearchVal);
					suggestions3Selected = homepage.isTitlePresentInSearchSuggestions(thirdsuggestionVal)
							&& homepage.selectTitleInSearchSuggestions(thirdsuggestionVal);
				}
				softAssert.assertTrue(suggestions3Selected,
						jiraNumber + ": Suggestion Title selected for Third search box");
				logExtentStatus(extentLogger, suggestions3Selected, "Suggestion Title selected for Third search box",
						thirdthematicsearchKey, thirdsuggestionVal, jiraNumber);

				boolean searchBox3Cleared = homepage.clearFirstThematicSearchBox()
						&& homepage.isClearLinkNotDisplayedForFirstThematicSearchBox()
						&& homepage.isClearLinkDisplayedForSecondThematicSearchBox()
						&& homepage.isClearLinkDisplayedForThirdThematicSearchBox();

				softAssert.assertTrue(searchBox3Cleared, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchBox3Cleared, issueSummary, firstthematicsearchkey,
						firstsuggestionVal, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	/*
	 * This test verifies the search from History widget available in HomePage
	 */

	/*@Test(priority = 25, groups = { "chpmex" }, description = "MAFAUTO-135")
	public void searchFromHistoryWidget() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean ishistorywidgetmaximized, isreqhistoryopened = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Home Page - Widget - Historical content to legacy");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String yearexistingKey = "yearexistingval";
				String yearexistingVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, yearexistingKey,
						extentLogger);
				String yearreqKey = "yearreqval";
				String yearreqVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, yearreqKey, extentLogger);
				String existingtextKey = "seconddropdowninitialvalue";
				String existingVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, existingtextKey,
						extentLogger);
				String finaltextKey = "finalvalue";
				String finaltextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, finaltextKey,
						extentLogger);

				homepage.clickOnProductLogo();
				homepage.minimizeAllWidgetsInHome();
				ishistorywidgetmaximized = homepage.isHistoryWidgetMaximized();
				if (!ishistorywidgetmaximized) {
					homepage.maximizeHistoryWidget();
				}
				boolean bothfieldsavailable = homepage.isHistorybyOrderFieldAvailable()
						&& homepage.isHistorybyYearFieldAvailable();
				if (bothfieldsavailable) {
					boolean isexpyeardisplayed = homepage.validateHistorybyYearText(yearexistingVal);
					boolean isexporderdisplayed = homepage.validateHistorybyOrderText(existingVal);

					if (isexpyeardisplayed && isexporderdisplayed) {
						homepage.selectHistorybyYear(yearreqVal);
						homepage.selectHistorybyOrder(finaltextVal);

						homepage.historyWidgetClickSearch();
						isreqhistoryopened = homepage.isRequiredhistoryOpened(finaltextVal);
						
					}
				}

				softAssert.assertTrue(isreqhistoryopened, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isreqhistoryopened, issueSummary, finaltextKey + ":" + finaltextVal,
						yearreqKey + ":" + yearreqVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}
*/
	
		
}
