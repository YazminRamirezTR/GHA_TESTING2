
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
import com.trgr.quality.maf.pages.JurisprudencePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class JurisprudenceSearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	JSONObject jsonObject;
	JurisprudencePage jurisprudencepage;
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

	@Test(priority = 1, groups = { "chpmex", "chparg", "chpury","chppe" }, description = "MAFQABANG-68")
	public void searchJurisprudenceUsingThematicAndFreeword() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify AdvanceSearch  for Jurisprudence with Thematic Search and Freeword");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicsearchKey = "thematicsearch";
				String thematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicsearchKey,
						extentLogger);
				
				String suggestionKey = "suggestionvaltobeselected";
				String thematicsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestionKey,
						extentLogger);
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String thematicareaVal =jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "thematicarea",
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
					searchpage.selectAreaFromContentTree(thematicareaVal);
				}
				
				jurisprudencepage = searchpage.openJurisprudencePage();
				jurisprudencepage.enterFreeWordOnSearchPage(freewordVal);
				jurisprudencepage.enterThematicTextOnJurisprudencePage(thematicsearchVal);

				boolean suggestionsDisplayed = jurisprudencepage.isTheSuggestionsDropdownDisplayed();
				if(suggestionsDisplayed)
				{
					jurisprudencepage.selectTitleInSearchSuggestions(thematicsuggestionVal);
				}
						
				softAssert.assertTrue(suggestionsDisplayed,
						jiraNumber + "Combo suggestions with highlighting the search string");
				logExtentStatus(extentLogger, suggestionsDisplayed,
						"Combo suggestions with highlighting the search string", jiraNumber);

				searchResultsPage = jurisprudencepage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(thematicsearchVal)
						&& searchResultsPage.searchReturnedResultsAsExpected(freewordVal);

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
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, freewordKey + ":" + freewordVal,
						thematicsearchKey + ":" + thematicsearchVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			softAssert.assertAll();
			extentReports.endTest(extentLogger);
		}
	}

	@Test(priority = 2, groups = { "chparg", "chpury" ,"chppe"}, description = "MAFQABANG-425")
	public void searchJurisprudenceUsingPartiesCitaOnlineCourt() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify AdvanceSearch Jurisprudence with Tribunal,Partes and CitaOnline");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String citaonlineKey = "citaonline";
				String citaonlineVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, citaonlineKey,
						extentLogger);
				String courtKey = "court";
				String courtVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, courtKey, extentLogger);
				String partesKey = "partes";
				String partesVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, partesKey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				jurisprudencepage = searchpage.openJurisprudencePage();
				jurisprudencepage.enterCitaOnline(citaonlineVal);
				jurisprudencepage.enterCourtInfo(courtVal);
				jurisprudencepage.enterPartiesName(partesVal);
				searchResultsPage = jurisprudencepage.clickOnSearch();
                Thread.sleep(4000);
				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(citaonlineVal)
						&& searchResultsPage.searchReturnedResultsAsExpected(courtVal)
						&& searchResultsPage.searchReturnedResultsAsExpected(partesVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", citaonlineKey,
								citaonlineVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, citaonlineKey + ":" + citaonlineVal,
						courtKey + ":" + courtVal + "," + " " + partesKey + ":" + partesVal, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			softAssert.assertAll();
			extentReports.endTest(extentLogger);
		}
	}

	@Test(priority = 3, groups = { "chpmex" }, description = "MAFQABANG-428")
	public void searchJurisprudenceUsingThesisandRubro() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify AdvanceSearch Jurisprudence with Thesis no and Rubro");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thesisKey = "thesisnumber";
				String thesisVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thesisKey, extentLogger);
				String rubroKey = "rubro";
				String rubroVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, rubroKey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				jurisprudencepage = searchpage.openJurisprudencePage();
				jurisprudencepage.clickIssuerAndSeason();
				jurisprudencepage.clickOnSaveOnIssuerAndSeasonPopUp();
				jurisprudencepage.enterNumberThesis(thesisVal);
				jurisprudencepage.enterRubroValue(rubroVal);
				searchResultsPage = jurisprudencepage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(thesisVal)
						&& searchResultsPage.searchReturnedResultsAsExpected(rubroVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								thesisKey + ":" + rubroKey, thesisVal + ":" + rubroVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, thesisKey + ":" + rubroKey,
						thesisVal + ":" + rubroVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			softAssert.assertAll();
			extentReports.endTest(extentLogger);
		}
	}

	@Test(priority = 4, groups = { "chpmex" }, description = "MAFAUTO-203")
	public void searchJurisprudenceUsingThesisNumber() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Advance search Jurisprudence with Thesis Number");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thesisnumKey = "thesisnumber";
				String thesisnumVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thesisnumKey,
						extentLogger);
				String firstsuggestionkey = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "firstsuggestion",
						extentLogger);
				String thematicareaVal =jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "thematicarea",
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
					searchpage.selectAreaFromContentTree(thematicareaVal);
				}
				jurisprudencepage = searchpage.openJurisprudencePage();
				Thread.sleep(3000);
				jurisprudencepage.enternumberThesisMexJurisprudencePage(thesisnumVal,firstsuggestionkey);
				
				
				searchResultsPage = jurisprudencepage.clickOnSearch();
				

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
					

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", thesisnumKey,
								thesisnumVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, thesisnumKey, thesisnumVal,
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

	@Test(priority = 5, groups = { "chpmex" }, description = "MAFAUTO-245")
	public void searchJurisprudenceUsingRubro() throws Exception {
		softAssert = new SoftAssert();
		try {
			boolean searchResultsDisplayed = false;

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Advance search with rubro Link text validated");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String rubrotextKey = "rubrotext";
				String rubrotextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, rubrotextKey,
						extentLogger);
				String rubrolinktextKey = "rubrolinktext";
				String rubrolinktextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, rubrolinktextKey,
						extentLogger);
				String AdvanceSearchkey = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "AdvanceSearch",
						extentLogger);

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
					searchpage.selectAreaFromContentTree(thematicareaVal);
				}
				
				boolean advsearchlink = searchpage.clickOnExpectedAdvancedSearch(AdvanceSearchkey);

				softAssert.assertTrue(advsearchlink, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, advsearchlink,"Advance Search link  displayed as expected",jiraNumber);
				
				jurisprudencepage =searchpage.isDisplayedAdvPage();
				
				boolean isjurisprudencepagedisplayed = jurisprudencepage.isJurisprudencePageDispalyed();
					Thread.sleep(4000);
				if (isjurisprudencepagedisplayed) {
					jurisprudencepage.enterRubroValue(rubrotextVal);
				}

				searchResultsPage = jurisprudencepage.clickSearchButton();
				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", rubrotextKey,
								rubrotextVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean istitledisplayed = searchResultsPage.validateRubroLinkText(rubrolinktextVal);

				if (istitledisplayed) {
					documentPage = searchResultsPage.clickOnRubroTextLink();
				}

				boolean istextvaldiated = documentPage.validateRubroLinkText(rubrolinktextVal);
				softAssert.assertTrue(
						istextvaldiated && istitledisplayed && searchResultsDisplayed && isjurisprudencepagedisplayed,
						jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger,
						istextvaldiated && istitledisplayed && searchResultsDisplayed && isjurisprudencepagedisplayed,
						issueSummary, rubrolinktextKey, rubrolinktextVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 6, groups = { "chpmex" }, description = "MAFAUTO-244")
	public void searchJurisprudenceImproveRanking() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Jurisprudence - Result List - Improve ranking feature");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String rubrotextKey = "rubrotext";
				String rubrotextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, rubrotextKey,
						extentLogger);
				String rubrolinktextKey = "rubrolinktext";
				String rubrolinktextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, rubrolinktextKey,
						extentLogger);
                String AdvanceSearchkey= jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "AdvanceSearch",
						extentLogger);
				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
					searchpage.selectAreaFromContentTree(thematicareaVal);
				}

				boolean advsearchlink = searchpage.clickOnExpectedAdvancedSearch(AdvanceSearchkey);
				softAssert.assertTrue(advsearchlink, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, advsearchlink,"Advance Search link  displayed as expected",jiraNumber);
				jurisprudencepage =searchpage.isDisplayedAdvPage();
				
				boolean isjurisprudencepagedisplayed = jurisprudencepage.isJurisprudencePageDispalyed();
      Thread.sleep(4000);
				if (isjurisprudencepagedisplayed) {
					jurisprudencepage.enterRubroValue(rubrotextVal);
				}

				searchResultsPage = jurisprudencepage.clickSearchButton();
				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, issueSummary, rubrotextKey,
								rubrotextVal + " -resulted in no search results", jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean istitledisplayed = searchResultsPage.validateRubroLinkText1(rubrolinktextVal);

				softAssert.assertTrue(istitledisplayed && searchResultsDisplayed && isjurisprudencepagedisplayed,
						jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger,
						istitledisplayed && searchResultsDisplayed && isjurisprudencepagedisplayed, issueSummary,
						rubrolinktextKey, rubrolinktextVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 7,groups = { "chpmex" }, description = "MAFAUTO-111")
	public void applyFiltersOnFacetsUsingSearchOnJurisprudence() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "JurisprudenceSearchTest", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verify applying filters on different facets on the search results page using Jurisprudence page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				
				String TFJAselectionkey = "TFJAselection";
				String TFJAselectionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, TFJAselectionkey, extentLogger);
				
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				
				String filterbyyearkey = "filterbyyear";
				String filterbyyearVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, filterbyyearkey, extentLogger);
				
				String filterbyinstancelinkKey = "filterbyinstancelink";
				String filterbyinstancelinkVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, filterbyinstancelinkKey, extentLogger);
				
				String ThematicArea ="ThematicArea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, ThematicArea, extentLogger);
				
				String FilterbyÉpocaVal="FilterbyÉpoca";
				String filterbyEpocalinkVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, FilterbyÉpocaVal, extentLogger);
				searchpage = homepage.OpenSearchPage();
				

				searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
					searchpage.selectAreaFromContentTree(thematicareaVal);
				}
				Thread.sleep(3000);
				jurisprudencepage = searchpage.openJurisprudencePage();
				Thread.sleep(1000);
				jurisprudencepage.clickIssuerAndSeason();
				jurisprudencepage.selectSelectionOnIssuerAndSeasonPopUp(TFJAselectionVal);
				jurisprudencepage.clickOnSaveOnIssuerAndSeasonPopUp();
				jurisprudencepage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = jurisprudencepage.clickOnSearch();
				Thread.sleep(9000);
				//Ensure the search results text is based on the all the options of TFJA by comaparing the search text
				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								freewordkey ,freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", issueSummary);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Search results displayed as expected");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected", 
						TFJAselectionVal , "TFJA (Octava, Séptima, Sexta, Quinta)", jiraNumber);
				Thread.sleep(5000);
				//filter by year
				searchResultsPage.clickValueInFilterByYearWidget(filterbyyearVal);
				searchResultsDisplayed = searchResultsPage.searchReturnedResultsAsExpected("Clasificación (2016)");
				
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Filter By year displayed results as expected");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Filter By year displayed results as expected", 
						filterbyyearkey , filterbyyearVal , jiraNumber);
				
				//click on the Jurisprudence to back to initial results
				searchResultsPage.clickOnGivenLinkByLinkText("Jurisprudencia", false);
				searchResultsDisplayed = searchResultsPage.searchReturnedResultsAsExpected("Clasificación (2016)");
				
				//Checking the false as clicking on the Jurisprudence will not have the 2016 filter
				softAssert.assertFalse(searchResultsDisplayed, jiraNumber + "Removed the filter by year results");
				logExtentStatus(extentLogger, !searchResultsDisplayed, "Removed the filter by year and navigate back to original search results list", 
						filterbyyearkey , filterbyyearVal , jiraNumber);
				
				//link to click on the filter by instance 
				searchResultsPage.clickOnGivenLinkByLinkText(filterbyinstancelinkVal, false);
				
				
			/*	searchResultsDisplayed = searchResultsPage.searchReturnedResultsAsExpected(filterbyinstancelinkVal);
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Filter By Instance widget displayed results as expected");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Filter By Instance displayed results as expected", 
						filterbyinstancelinkKey , filterbyinstancelinkVal , jiraNumber);*/
				
			   searchResultsPage.verifyEpocaFilterElementPresent(filterbyEpocalinkVal, true);
			 
				searchResultsDisplayed = searchResultsPage.searchReturnedResultsAsExpected(filterbyEpocalinkVal);
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Filter By Time displayed results as expected");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Filter By Time  displayed results as expected", 
						filterbyEpocalinkVal , filterbyEpocalinkVal , jiraNumber);
				
		
				searchResultsPage.clickOnGivenLinkByLinkText("Jurisprudencia", false);
				Thread.sleep(3000);
//				searchResultsDisplayed = searchResultsPage.searchReturnedResultsAsExpected(filterbyinstancelinkVal);
//				
//				//Checking the false as clicking on the Jurisprudence will not have the 2016 filter
//				softAssert.assertFalse(searchResultsDisplayed, jiraNumber + "Removed the filter by instance results");
//				logExtentStatus(extentLogger, !searchResultsDisplayed, "Removed the filter by instance and navigate back to original search results list", 
//						filterbyyearkey , filterbyyearVal , jiraNumber);
				
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}


	@Test(priority = 8,groups = { "chpmex" }, description = "MAFAUTO-112")
	public void docDisplayUsingFreewordSearchOnJurisprudence() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verify if scrolling is working as expected for Jurisprudence documents");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				String thematicareaKey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String expectedresult = "resulstsearch";
				String expectedresults = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expectedresult,
						extentLogger);
				searchpage = homepage.OpenSearchPage();
				
				 searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
					if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
						searchpage.selectAreaFromContentTree(thematicareaVal);
					}
				jurisprudencepage = searchpage.openJurisprudencePage();

				jurisprudencepage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = jurisprudencepage.clickOnSearch();

				//Ensure the search results text is based on the all the options of TFJA by comaparing the search text
				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(freewordVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								freewordkey ,freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Search results displayed as expected");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected", 
						freewordkey, freewordVal, jiraNumber);
				boolean ExpectedResult=searchResultsPage.isExpectedResultOpened(expectedresults);
				softAssert.assertTrue(ExpectedResult, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, ExpectedResult,"Search results displayed as expected",jiraNumber);	
				documentPage = searchResultsPage.clickFirstLink();
				documentPage.scrollOnpageByGivenPixels(0, 600);
				
				boolean isDocHeaderConstantWithScroll = documentPage.isDocumentTitlePresent();
				
				softAssert.assertTrue(isDocHeaderConstantWithScroll, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, isDocHeaderConstantWithScroll,issueSummary,jiraNumber);					
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
	 * This test verifies the filter by Instance for Advance search with Jurisprudence
	 */

	@Test(priority = 9,groups = { "chpmex" }, description = "MAFAUTO-248")
	public void jurisprudenceSearchWithFilterbyInstance() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Jurisprudence - Result List - Update facets (summarization) - Add Applied Filters widget (“Filtros aplicados”)");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				
				String filterbyinstancelinkKey = "filterbyinstancelink";
				String filterbyinstancelinkVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, filterbyinstancelinkKey, extentLogger);
				
				
				String AdvanceSearchkey = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "AdvanceSearch", extentLogger);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(3000);
		//		jurisprudencepage = searchpage.openJurisprudencePage();
				
				boolean advsearchlink = searchpage.clickOnExpectedAdvancedSearch(AdvanceSearchkey);
				softAssert.assertTrue(advsearchlink, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, advsearchlink,"Advance Search link  displayed as expected",jiraNumber);
				jurisprudencepage =searchpage.isDisplayedAdvPage();
				Thread.sleep(1000);
				jurisprudencepage.enterFreeWordOnSearchPage(freewordVal);
				Thread.sleep(4000);
				searchResultsPage = jurisprudencepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
					

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								freewordkey ,freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Search results displayed as expected");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected", 
						freewordkey , freewordVal, jiraNumber);
				Thread.sleep(4000);
				//link to click on the filter by instance 
				searchResultsPage.clickOnGivenLinkByLinkText(filterbyinstancelinkVal, false);
				searchResultsDisplayed = searchResultsPage.searchReturnedResultsAsExpected(filterbyinstancelinkVal);
				
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Filter By Instance widget displayed results as expected");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Filter By Instance displayed results as expected", 
						filterbyinstancelinkKey , filterbyinstancelinkVal , jiraNumber);
				Thread.sleep(5000);
				//clicking back the Jurisprudence link
				searchResultsPage.clickOnGivenLinkByLinkText("Jurisprudencia", false);
				searchResultsDisplayed = searchResultsPage.searchReturnedResultsAsExpected(filterbyinstancelinkVal);
				
				//Checking the false as clicking on the Jurisprudence will not have the 2016 filter
				softAssert.assertFalse(searchResultsDisplayed, jiraNumber + "Removed the filter by instance results");
				logExtentStatus(extentLogger, !searchResultsDisplayed, "Removed the filter by instance and navigate back to original search results list", 
						filterbyinstancelinkKey , filterbyinstancelinkVal , jiraNumber);
				
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
