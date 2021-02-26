
package com.trgr.quality.maf.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
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
import com.trgr.quality.maf.pages.ContenttreeOnsearchResultPage;
import com.trgr.quality.maf.pages.DoctrinePage;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;


/*
 * Most of the test data on this class is focused on the search criteria using
 * Author and freeword search. Changing the test data to cover other fields will result in
 * better usage of various search parameters
 */

public class DoctrineSearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	JSONObject jsonObject;
	DoctrinePage doctrinepage;
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
			extentLogger.log(LogStatus.ERROR,
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

	
	@Test( groups = { "chpmex" }, description = "MAFAUTO-188")
	public void searchDoctrineUsingAuthorAndFreeword() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Advance search for doctrine with Author and Freeword");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String authorkey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, authorkey, extentLogger);
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();

				doctrinepage.enterAuthor(authorVal);
				doctrinepage.enterAuthorDropDownValue();
				Thread.sleep(2000);
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = doctrinepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(authorVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								authorkey + ":" + authorVal, freewordkey + ":" + freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, authorkey + ":" + authorVal,
						freewordkey + ":" + freewordVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}


	@Test(groups = { "chpmex" }, description = "MAFAUTO-206")
	public void searchDoctrineUsingFilterbyYear() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Doctrine Search with Filter By year and sorting fo searchResults");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String authorKey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, authorKey, extentLogger);
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String authorfindkey=jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "authorFind", extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();

				doctrinepage.enterAuthor(authorfindkey);
				doctrinepage.enterAuthorFindDropDownPage(authorVal);
				doctrinepage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = doctrinepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(authorVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								authorKey + ":" + authorVal, freewordKey + ":" + freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean isFilterByYearWidgetSorted = searchResultsPage.isFilterByYearWidgetSorted();
				softAssert.assertTrue(isFilterByYearWidgetSorted, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, authorKey + ":" + authorVal,
						freewordKey + ":" + freewordVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}


	@Test(groups = { "chpmex" }, description = "MAFQABANG-469")
	public void searchDoctrineUsingMonthYear() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify Advance Search doctrine with month/year field");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String monthyearKey = "monthandyear";
				String monthyearVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, monthyearKey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();
				Thread.sleep(2000);
				doctrinepage.enterMonthAndYear(monthyearVal);
				searchResultsPage = doctrinepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", monthyearKey,
								monthyearVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, monthyearKey, monthyearVal,
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


	@Test(groups = { "chpmex", "chparg","chppe","chpchile"}, description = "MAFQABANG-462")
	public void searchDoctrineAndVerifySearchwithin() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify  the search with in search result for Advance Search doctrine");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String searcwithinKey = "searchwithintext";
				String searcwithinVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searcwithinKey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();

				searchpage.enterFreeWordOnSearchPage(freewordVal);

				searchResultsPage = doctrinepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
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

				// verify if search within option displayed on the
				// results page
				searchResultsDisplayed = searchResultsPage.isSearchWithinOptionDisplayed();
				softAssert.assertTrue(searchResultsDisplayed, issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, jiraNumber);

				int docCount = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());

				softAssert.assertTrue(docCount > 0, "Total document count is returned on the result set");

				logExtentStatus(extentLogger, docCount > 0, "Total document count is returned on the result set",
						jiraNumber);

				// verify search within is working

				searchResultsPage.enterSearchWithinTerm(searcwithinVal);
				searchResultsPage.clickOnSearchWithInSearch();

				// Verify the count of the documents returned is less
				// than the count from the parent set
				int docCountAfterSearchWithin = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());

				softAssert.assertTrue(docCount > docCountAfterSearchWithin,
						"Doc count after search within is less than doc count with initial search");
				logExtentStatus(extentLogger, docCount > docCountAfterSearchWithin,
						"Doc count after search within is less than doc count with initial search", jiraNumber);

				boolean isSearchWithinWorking = searchResultsPage.searchWithInResultsDisplayed(searcwithinVal);

				softAssert.assertTrue(isSearchWithinWorking, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchWithinWorking, issueSummary, freewordKey + ":" + freewordVal,
						searcwithinKey + ":" + searcwithinVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}


	@Test(groups = { "chpmex", "chparg" }, description = "MAFQABANG-66")
	public void searchDoctrineusingAuthor() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify Advance search  for Doctrine with author field");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String authorKey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, authorKey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();
				doctrinepage.enterAuthor(authorVal);
				searchResultsPage = doctrinepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(authorVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", authorKey, authorVal,
								jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, authorKey, authorVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}


	@Test(groups = { "chpury", "chppe","chpchile" }, description = "MAFQABANG-581")
	public void searchDoctrineusingThematicAuthorTitleandFreeword() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify Advance search for doctrine with Thematic,Author,Title and freeword field");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicsearchKey = "thematicsearch";
				String thematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicsearchKey,
						extentLogger);
				String suggestionKey = "suggestionvaltobeselected";
				String suggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestionKey,
						extentLogger);
				String authorKey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, authorKey, extentLogger);
				String titleKey = "title";
				String titleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, titleKey, extentLogger);
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage.clickOnProductLogo();
				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();

				doctrinepage.enterThematicValue(thematicsearchVal);
				boolean issuggestiondispalyed = doctrinepage.isTheSuggestionsDropdownDisplayed();
				if (issuggestiondispalyed) {
					doctrinepage.selectTitleInSearchSuggestions(suggestionVal);
				}

				doctrinepage.enterAuthor(authorVal);
				doctrinepage.enterTitle(titleVal);
				doctrinepage.enterFreeWordOnSearchPage(freewordVal);

				searchResultsPage = doctrinepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(freewordVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								thematicsearchKey + ":" + authorKey + ":" + titleKey + ":" + freewordKey,
								thematicsearchVal + ":" + authorVal + ":" + titleVal + ":" + freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary,
						thematicsearchKey + ":" + thematicsearchVal + ":" + titleKey + ":" + titleVal,
						authorKey + ":" + authorVal + ":" + freewordKey + ":" + freewordVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}


	@Test(groups = { "chparg","chppe","chpchile" }, description = "MAFQABANG-65")
	public void searchDoctrineUsingAuthorandTitle() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify Advance search for Doctrine with Author and Title");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String titleKey = "title";
				String titleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, titleKey, extentLogger);
				String authorKey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, authorKey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(2000);
				doctrinepage = searchpage.OpenDoctrinaPage();

				doctrinepage.enterAuthor(authorVal);
				doctrinepage.enterTitle(titleVal);
				searchResultsPage = doctrinepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(authorVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								authorKey + ":" + authorVal, titleKey + ":" + titleVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, authorKey + ":" + authorVal,
						titleKey + ":" + titleVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}
	
	@Test(groups = { "chpmex" }, description = "MAFAUTO-115")
	public void collapseResultsToLevelOneWithSearchOnDoctrine() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verify collapsing document list on the content treee to level one");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String authorkey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, authorkey, extentLogger);
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				String contenttreenodenameKey = "contenttreenodename";
				String contentTreeNodeName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,contenttreenodenameKey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();

				doctrinepage.enterAuthor(authorVal);
				doctrinepage.enterAuthorDropDownValue();
				Thread.sleep(2000);
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = doctrinepage.clickOnSearch();
				Thread.sleep(2000);
				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(authorVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								authorkey + ":" + authorVal, freewordkey + ":" + freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, authorkey + ":" + authorVal,
						freewordkey + ":" + freewordVal, jiraNumber);
				
				ContenttreeOnsearchResultPage contentTree = searchResultsPage.clickTableOfContentsLink();
				contentTree.clickExpandItemInContentTree("Casos Prácticos");
				
				contentTree.ClickOnCollapseLevel1Link();
				WebElement treeElement = searchpage.getFirstLevelContentTree(contentTreeNodeName);
				boolean isContentTreeCollapsed =	contentTree.isContentTreeElementCollapsed(treeElement);
				
				softAssert.assertTrue(isContentTreeCollapsed, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, isContentTreeCollapsed, issueSummary, jiraNumber);
				
				//Returning back to list of documents
				searchResultsPage = contentTree.clickListOfDocumentsLink();
				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Returning to list of documents is successful");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Returning to list of documents is successful", jiraNumber);
				
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(groups = { "chpmex" }, description = "MAFAUTO-116")
	public void filterByYearAndReturnToResultListWithSearchOnDoctrine() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verify user can filterby year and return to complete search results list");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String authorkey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, authorkey, extentLogger);
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				String filterbyyearKey = "filterbyyear";
				String filterbyyearVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,filterbyyearKey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();

				doctrinepage.enterAuthor(authorVal);
				doctrinepage.enterAuthorDropDownValue();
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = doctrinepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(authorVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								authorkey + ":" + authorVal, freewordkey + ":" + freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, authorkey + ":" + authorVal,
						freewordkey + ":" + freewordVal, jiraNumber);
				
				int docCount = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());
				
				searchResultsPage.clickValueInFilterByYearWidget(filterbyyearVal);
				
				int docCountAfterFilter = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());
				
				searchResultsDisplayed = docCount > docCountAfterFilter;
				
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Clicking on filter by year displayed expected results");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Clicking on filter by year displayed expected results", jiraNumber);
				
				//Returning back to list of documents by clicking on removing filter
				searchResultsPage.clickOnRemoveFilter();
				int returnToOriginalList = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());
				searchResultsDisplayed = returnToOriginalList == docCount;
				
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Returning to initial search results list of documents is successful");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Returning  to initial search results list is successful", jiraNumber);
				
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
	 * This test verifies the advance search from doctrine
	 * then filter search result based on year then again filter search results based on content type
	 * */
	
	@Test(groups = { "chpmex" }, description = "MAFAUTO-250")
	public void filterSearchResultsforAdvSearchDoctrine() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Doctrine Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed,isresultsfiltered = false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Doctrine – Result List – Update facets (summarization) - Add Applied Filters widget (“Filtros aplicados”)");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey,
						extentLogger);
				String yearKey = "year";
				String yearVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, yearKey,
						extentLogger);
				String contenttypeKey = "contenttype";
				String contenttypeVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, contenttypeKey, extentLogger);
				
				homepage.clickOnProductLogo();
				
				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();
				Thread.sleep(1000);
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				Thread.sleep(2000);
				searchResultsPage = doctrinepage.clickOnSearch();
				Thread.sleep(2000);
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
				
				searchResultsPage.scrollIntoViewFilterByYearWidget();
				boolean isfilterbyyearwidgetvisible = searchResultsPage.isFilterByYearWidgetDisplayed();
				if(isfilterbyyearwidgetvisible)
				{
					boolean isfilterbyyearwidgetexpanded = searchResultsPage.isFilterByYearWidgetViewExpanded();
					if(!isfilterbyyearwidgetexpanded)
					{
						searchResultsPage.expandFilterByYearWidgetView();
					}
					searchResultsPage.clickValueInFilterByYearWidget(yearVal);
					isresultsfiltered = searchResultsPage.isFilteredByYear(yearVal);
					
				}
				
				searchResultsPage.clcikLinkInAppliedFilterWidget(contenttypeVal);
						
				softAssert.assertTrue(isresultsfiltered , jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isresultsfiltered, issueSummary, freewordKey + ":" + freewordVal,
						yearKey + ":" + yearVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}
	
	@Test(groups = { "chpmex" }, description = "MAFAUTO-205")
	public void removeResultsFacet() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verify left panel not found the widget Resultados");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String authorkey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, authorkey, extentLogger);
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				
				String authorfind=jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "authorFind", extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();

				doctrinepage.enterAuthor(authorfind);
				doctrinepage.enterAuthorFindDropDownPage(authorVal);
				doctrinepage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = doctrinepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
						

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								authorkey + ":" + authorVal, freewordkey + ":" + freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}
				boolean isResultFacetRemoved= !(searchResultsPage.isResultsFacetDisplayed());
				

				softAssert.assertTrue(isResultFacetRemoved, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, isResultFacetRemoved, issueSummary, authorkey + ":" + authorVal,
						freewordkey + ":" + freewordVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}
	
	@Test(groups = { "chpmex" }, description = "MAFAUTO-204")
	public void itemizedListForMaquilaOperationOnDoctrineDoc() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verify expected itemized list is displayed for maquila operation");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String titleKey = "title";
				String titleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, titleKey, extentLogger);
				
				String scrolltotextondocKey = "scrolltotextondoc";
				String scrolltotextondocVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, scrolltotextondocKey, extentLogger);
				
				String numberoforderedlistKey = "numberoforderedlist";
				String numOfListItemVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, numberoforderedlistKey, extentLogger);
			   String 	DocName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "Document", extentLogger);
				
				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();

			    doctrinepage.enterTitle(titleVal);
				searchResultsPage = doctrinepage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								titleKey, titleVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}
				
				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + "Search results displayed for given title");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed for given title", titleKey, titleVal, jiraNumber);
				
				 searchResultsPage.clickExpectedLinkFromResultsPage(DocName);
								
				//check if the expected ordered list if displayed for the given definition
				
				boolean isItemizedListDisplayed = searchResultsPage.isExpectedOrderedListDisplayedOnDoc(Integer.parseInt(numOfListItemVal), scrolltotextondocVal);
				softAssert.assertTrue(isItemizedListDisplayed, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, isItemizedListDisplayed, issueSummary, titleKey, titleVal, jiraNumber);
		
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
