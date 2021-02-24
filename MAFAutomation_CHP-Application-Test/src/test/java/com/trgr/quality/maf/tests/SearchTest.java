
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
import com.trgr.quality.maf.commonutils.RandomUtils;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.DeliveryPage;
import com.trgr.quality.maf.pages.DoctrinePage;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.FormsPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.JurisprudencePage;
import com.trgr.quality.maf.pages.LegislationPage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class SearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	JSONObject jsonObject;
	DeliveryPage deliverypage;
	JurisprudencePage jurisprudencepage;
	DoctrinePage doctrinepage;
	LegislationPage legislationpage;
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
			if (homepage != null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			// Adding this code to avoid sign off skip in case of unexpected conditions,
			// which sets home page to null
		} catch (Exception e) {
		}
	}

	@Test(priority = 1, groups = { "chpmex" }, description = "MAFAUTO-102")
	public void displayOfSearchResultsBasedOnContentType() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Display of SearchResults based on Content type");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String contenttypeKey = "contenttypelinkname";
				JSONArray contenttypeVal = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, contenttypeKey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				/*
				 * boolean isfreeworddispalyed = searchpage.isFreewordFieldDisplayed(); if
				 * (!isfreeworddispalyed) { searchpage.clickThematicSearchRadioButton(); }
				 * 
				 * searchpage.enterFreeWordOnSearchPage(freewordVal); searchResultsPage =
				 * searchpage.clickonSearchwhenThematicisSelected();
				 */

				searchpage.enterTextInSearchField(freewordVal);
				searchResultsPage = searchpage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound) {
						softAssert.assertTrue(true,
								jiraNumber + ":-resulted in no search results" + " :" + freewordVal);
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordKey,
								freewordVal, jiraNumber);
					} else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				for (int i = 0; i < contenttypeVal.size(); i++) {
					boolean islinkdisplayed = searchResultsPage
							.isGivenFacetLinkDisplayed(contenttypeVal.get(i).toString());
					if (islinkdisplayed) {
						searchResultsPage.clickOnGivenFacetLink(contenttypeVal.get(i).toString());
						searchResultsDisplayed = searchResultsPage
								.resultSetDisplayBasedOnContentSet(contenttypeVal.get(i).toString());
					}
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, freewordKey + ":" + freewordVal,
						contenttypeKey + ":" + contenttypeVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 2, groups = { "chpbr", "chpury" }, description = "MAFQABANG-55")
	public void searchWithNaturalLanguage() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify search with Natural language field");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String naturallangKey = "naturallang";
				String naturallangVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, naturallangKey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				searchpage.selectNaturalLanguageOption();
				searchpage.enterNaturalLanguageSearchOnSearchPage(naturallangVal);
				searchResultsPage = searchpage.clickonSearchwhenNatLanguageisSelected();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", naturallangKey,
								naturallangVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, naturallangKey, naturallangVal,
						jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			softAssert.assertAll();
			extentReports.endTest(extentLogger);
		}

	}

	@Test(priority = 3, groups = { "chpury", "chppe", "chpchile" }, description = "MAFQABANG-103")
	public void searchWithInSearchResults() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify the search with in search result");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String searchtextKey = "searchtext";
				String searchtextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searchtextKey,
						extentLogger);
				String searchwithintextKey = "searchwithintext";
				String searchwithintextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searchwithintextKey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();

				boolean isfreeworddisplayed = searchpage.isFreewordFieldDisplayed();
				if (!isfreeworddisplayed) {
					searchpage.clickThematicSearchRadioButton();
				}

				searchpage.enterFreeWordOnSearchPage(searchtextVal);
				searchResultsPage = searchpage.clickonSearchwhenThematicisSelected();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(searchtextVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", searchtextKey,
								searchtextVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				// Get total number of documents displayed on the search
				// results
				int docCount = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());

				softAssert.assertTrue(docCount > 0, "Total document count is returned on the result set");
				logExtentStatus(extentLogger, docCount > 0, "Total document count is returned on the result set",
						jiraNumber);

				// verify if search within option displayed on the
				// results page
				searchResultsDisplayed = searchResultsPage.isSearchWithinOptionDisplayed();
				softAssert.assertTrue(searchResultsDisplayed, issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, jiraNumber);

				// verify search within is working
				searchResultsPage.enterSearchWithinTerm(searchwithintextVal);
				searchResultsPage.clickOnSearchWithInSearch();

				// Verify the count of the documents returned is less
				// than the count from the parent set
				int docCountAfterSearchWithin = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());

				softAssert.assertTrue(docCount > docCountAfterSearchWithin,
						"Doc count after search within is less than doc count with initial search");
				logExtentStatus(extentLogger, docCount > docCountAfterSearchWithin,
						"Doc count after search within is less than doc count with initial search", jiraNumber);

				boolean isSearchWithinWorking = searchResultsPage.searchWithInResultsDisplayed(searchwithintextVal);

				softAssert.assertTrue(isSearchWithinWorking, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchWithinWorking, issueSummary, searchtextKey + ":" + searchtextVal,
						searchwithintextKey + ":" + searchwithintextVal, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			softAssert.assertAll();
			extentReports.endTest(extentLogger);
		}
	}

	@Test(priority = 4, groups = { "chparg", "chpmex", "chpbr", "chppe", "chpchile" }, description = "MAFQABANG-48")
	public void displayOfSearchPage() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify SearchPage display with expected sections");

			homepage.openHomepage();
			searchpage = homepage.OpenSearchPage();
			boolean flag1 = searchpage.searchPageLeftPaneValdiation();
			softAssert.assertTrue(flag1, "content tree left pane validated successfully" + ":" + jiraNumber);
			logExtentStatus(extentLogger, flag1, "content tree left pane validated successfully", jiraNumber);
            if(!productUnderTest.equalsIgnoreCase("chpmex")) {
			if (!productUnderTest.equalsIgnoreCase("chparg")  ) {
				searchpage.clickThematicSearchRadioButton();
				boolean flag3 = searchpage.searchPageMiddlePaneValdiation();
				softAssert.assertTrue(flag3, "content tree middle pane validated successfully" + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag3, "content tree middle pane validated successfully", jiraNumber);
			}
            }
			// Added for verifying content tree
			boolean contentTreeDisplayed = searchpage.isDefaultContentTreeDisplayed();
			softAssert.assertTrue(contentTreeDisplayed, "Default content tree displayed" + ":" + jiraNumber);
			logExtentStatus(extentLogger, contentTreeDisplayed, "Default content tree displayed", jiraNumber);

			searchpage.ClickOnExpandLevel2Link();
			Thread.sleep(2000);
			boolean contentTreeExpanded = searchpage.isContentTreeExpanded();
			softAssert.assertTrue(contentTreeExpanded, "content tree Expanded" + ":" + jiraNumber);
			logExtentStatus(extentLogger, contentTreeExpanded, "content tree Expanded", jiraNumber);

			searchpage.ClickOnCollapseLevel1Link();
			Thread.sleep(2000);
			boolean contentTreecollapsed = searchpage.isContentTreeCollapsed();
			softAssert.assertTrue(contentTreecollapsed, "content tree Collapsed" + ":" + jiraNumber);
			logExtentStatus(extentLogger, contentTreecollapsed, "content tree Collapsed", jiraNumber);

			/*
			 * // Expanding again to verify the "Ley" node documents display
			 * searchpage.ClickOnExpandLevel2Link(); Thread.sleep(2000);
			 * softAssert.assertTrue(contentTreeExpanded,issueSummary + ":" + jiraNumber);
			 * logExtentStatus(extentLogger, contentTreeExpanded, issueSummary,jiraNumber);
			 */

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 5, groups = { "chpmex" }, description = "MAFAUTO-233")
	public void verifyNIFMappingOnResultsAndDocDisplay() throws Exception {
		softAssert = new SoftAssert();
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Jurisprudence - Result List - Improve ranking feature");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicareavalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String titleKey = "exptitle";
				String titleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, titleKey, extentLogger);

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				/*
				 * searchpage.selectGivenValueFromThematicDropdown(thematicareavalue);
				 * 
				 * if (searchpage.isAreaFoundInContentTree(thematicareavalue)) {
				 * searchpage.selectAreaFromContentTree(thematicareavalue); }
				 * searchpage.enterFreeWordOnSearchPage(freewordVal);
				 */

				searchpage.thematicAreaDropdownContainsValueInMex(thematicareavalue);
				Thread.sleep(2000);
				searchpage.enterTextInSearchField(freewordVal);
				searchResultsPage = searchpage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage.verifyNIFUpdateInResultList();
				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordKey,
								freewordVal + " -resulted in no search results", jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				documentPage = searchResultsPage.clickFirstLink();
				boolean isNIFupdateverifeid = documentPage.verifyNIFUpdateInDocDisplayPage(titleVal);

				softAssert.assertTrue(isNIFupdateverifeid && searchResultsDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isNIFupdateverifeid && searchResultsDisplayed, issueSummary, titleKey,
						titleVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 6, groups = { "chpmex" }, description = "MAFAUTO-231")
	public void NIFParagraphNumber() throws Exception {
		softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Legislation - NIF - Paragraph NUM");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicareaKey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String treeItemsKey = "NIFtreeitems";
				JSONArray data_array = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, treeItemsKey,
						extentLogger);
				String treeItems[] = data_array.get(0).toString().split(",");
				String documentKey = "NIFdoctreedocument";
				String documentVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, documentKey, extentLogger);
				

				String NIFTitledocumentval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "NIFTitledocument", extentLogger);
				

				homepage.openHomepage();
				Thread.sleep(3000);
				searchpage = homepage.OpenSearchPage();

				// Select Thematic Area & Click on area in tree(if it is not
				// already
				// selected)
				/*
				 * searchpage.selectGivenValueFromThematicDropdown(thematicareaVal); if
				 * (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
				 * searchpage.selectAreaFromContentTree(thematicareaVal); }
				 */
				searchpage.thematicAreaDropdownContainsValueInMex(thematicareaVal);

				// Expand First level item in the tree structure
				WebElement treeElement = searchpage.getFirstLevelContentTree(treeItems[0]);
				Thread.sleep(3000);
				searchpage.expandContentTreeElement(treeElement);
				// Expand till document in the sub tree
				for (int row = 1; row < treeItems.length; row++) {
					if (searchpage.isItemPresentInSubContentTree(treeElement, treeItems[row])) {
						Thread.sleep(2000);
						searchpage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = searchpage.getSubContentTreeElement(treeElement, treeItems[row]);
					}
				}
				Thread.sleep(3000);
				// Open document from the tree
				boolean docDisplayedInTree = searchpage.isItemPresentInSubContentTree(treeElement, documentVal);
				if (docDisplayedInTree)
					documentPage = searchpage.clickDocumentInSubContentTree(treeElement, documentVal);

				boolean paragraph = documentPage.verifyDisplayOfNIFParagraph();
				boolean paragraph_number = documentPage.verifyDisplayOfNIFParagraph();
                boolean niftext=documentPage.isPresentDocumentNIF(NIFTitledocumentval);
                
				deliverypage = documentPage.clickExportButton();

				boolean deliveryCompleted = deliverypage.verifyDocumentExportPage()
						&& deliverypage.enableRadioButton("formato_de_archivo_rtf") && deliverypage.clickAcceptButton();
					//	&& deliverypage.isDeliveryCompleted();

				boolean returnToDocument = deliverypage.verifyReturnToDocumentDisplay();
				softAssert.assertTrue(
						returnToDocument && deliveryCompleted && paragraph_number && paragraph && docDisplayedInTree && niftext,
						issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger,
						returnToDocument && deliveryCompleted && paragraph_number && paragraph && docDisplayedInTree & niftext,
						issueSummary, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 7, groups = { "chpmex" }, description = "MAFAUTO-242")
	public void suggestionsDisplayedForAllSearchPages() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Suggestions drop down getting dispalyed for Advance Search");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String thematicsearchKey = "thematicsearch";
				String thematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicsearchKey,
						extentLogger);
				String thematicsuggestionKey = "thematicsuggestion";
				String thematicsuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thematicsuggestionKey, extentLogger);
				String formssearchKey = "searchkey";
				String formssearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, formssearchKey,
						extentLogger);
				String formssuggestionKey = "suggestionkey";
				String formssuggestionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, formssuggestionKey,
						extentLogger);
				JSONArray contenttypeVal = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, "advancedsearch",
						extentLogger);

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				// searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				searchpage.thematicAreaDropdownContainsValueInMex(thematicareaVal);

				/*
				 * if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
				 * searchpage.selectAreaFromContentTree(thematicareaVal); }
				 */
				
				

				

				// entering keyword on legislation page
				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.enterThematicTextOnlegilationPage(thematicsearchVal);
				Thread.sleep(1000);
				boolean issuggestionsdisplayed = legislationpage.isTheSuggestionsDropdownDisplayed();
					//	&& legislationpage.isSearchStringhighlightedOnCombo(thematicsuggestionVal);
				legislationpage.ScrollToGivenSearchString(thematicsuggestionVal);
				issuggestionsdisplayed = legislationpage.isResultCountDisplayedForFstSearchString();
				softAssert.assertTrue(issuggestionsdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, issuggestionsdisplayed, issueSummary + ":legislation page",
						thematicsuggestionKey, thematicsuggestionVal, jiraNumber);
				legislationpage.clickClear();

				// entering keyword on Jurisprudence Page
				jurisprudencepage = searchpage.openJurisprudencePage();
				Thread.sleep(1000);
				jurisprudencepage.enterThematicTextOnJurisprudencePage(thematicsearchVal);
				
				boolean issuggestionsJuris = jurisprudencepage.isTheSuggestionsDropdownDisplayed();
//						&& jurisprudencepage.isSearchStringhighlightedOnCombo(thematicsuggestionVal);
				jurisprudencepage.ScrollToGivenSearchString(thematicsuggestionVal);
				issuggestionsJuris = jurisprudencepage.isResultCountDisplayedForFstSearchString();
				softAssert.assertTrue(issuggestionsJuris, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, issuggestionsJuris, issueSummary + "Jurisprudence page",
						thematicsuggestionKey, thematicsuggestionVal, jiraNumber);

				jurisprudencepage.clickClear();

				// entering keyword on Doctrine Page
				doctrinepage = searchpage.OpenDoctrinaPage();
				Thread.sleep(1000);
				doctrinepage.enterThematicValue(thematicsearchVal);
//				Thread.sleep(1000);
				boolean issuggestionsDoctrine = doctrinepage.isTheSuggestionsDropdownDisplayed();
//						&& doctrinepage.isSearchStringhighlightedOnCombo(thematicsuggestionVal);
				doctrinepage.ScrollToGivenSearchString(thematicsuggestionVal);
				issuggestionsDoctrine = doctrinepage.isResultCountDisplayedForFstSearchString();
				softAssert.assertTrue(issuggestionsDoctrine, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, issuggestionsDoctrine, issueSummary + "Doctrine page",
						thematicsuggestionKey, thematicsuggestionVal, jiraNumber);

				doctrinepage.clickClear();

				// entering keyword on Forms Page
				formspage = searchpage.openFormsPage();
				Thread.sleep(1000);
				formspage.enterThematicTextOnFormsPage(formssearchVal);
				//Thread.sleep(1000);
				boolean issuggestionsForms = formspage.isTheSuggestionsDropdownDisplayed();
//						&& formspage.isSearchStringhighlightedOnCombo(formssuggestionVal);
				formspage.ScrollToGivenSearchString(formssuggestionVal);
				issuggestionsForms = formspage.isResultCountDisplayedForFstSearchString();
				softAssert.assertTrue(issuggestionsForms, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, issuggestionsForms, issueSummary + "Forms page", formssuggestionKey,
						formssuggestionVal, jiraNumber);

				formspage.clickClear();

				boolean issuggestionsdispalyedall = issuggestionsdisplayed && issuggestionsJuris
						&& issuggestionsDoctrine && issuggestionsForms;

				softAssert.assertTrue(issuggestionsdispalyedall, jiraNumber);
				logExtentStatus(extentLogger, issuggestionsdispalyedall, issueSummary,
						formssuggestionKey + ":" + formssuggestionVal,
						thematicsuggestionKey + ":" + thematicsuggestionVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 8, groups = { "chpmex" }, description = "MAFAUTO-123")
	public void suggestionsDisplayedTillThirdLevelOnAllSearchPages() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verifying suggestions dropdown getting dispalyed for all Advance Search and Normal search upto 3 levels");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicareakey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareakey,
						extentLogger);

				String thematicfirstlevelkey = "thematickeyword";
				String thematicfirstlevelVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thematicfirstlevelkey, extentLogger);
				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
					searchpage.selectAreaFromContentTree(thematicareaVal);
				}

				searchpage.enterTextInSearchField(thematicfirstlevelVal);
				Thread.sleep(1000); // this wait is needed for dropdown
									// to display
				boolean firstlevelsuggestionsDisplayed = searchpage.isTheSuggestionsDropdownDisplayed()
						&& searchpage.isTitlePresentInSearchSuggestions(thematicfirstlevelVal);
				if (firstlevelsuggestionsDisplayed) {
					searchpage.selectFirstTitleOnSearchSuggestions(1);
					boolean firstlevelresultcount = searchpage.isResultCountDisplayedForFstSearchString();

					softAssert.assertTrue(firstlevelsuggestionsDisplayed && firstlevelresultcount, jiraNumber);
					logExtentStatus(extentLogger, firstlevelsuggestionsDisplayed && firstlevelresultcount, issueSummary,
							jiraNumber);
				}
				
				

				searchpage.clickClear();

				// entering keyword on legislation page
				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.enterThematicTextOnlegilationPage(thematicfirstlevelVal);
				Thread.sleep(1000);
				firstlevelsuggestionsDisplayed = legislationpage.isTheSuggestionsDropdownDisplayed()
						&& legislationpage.isTitlePresentInSearchSuggestions(thematicfirstlevelVal);

				if (firstlevelsuggestionsDisplayed) {
					searchpage.selectFirstTilteonSuggestionPage();
					boolean firstlevelresultcount = legislationpage.isResultCountDisplayedForFstSearchString();
					softAssert.assertTrue(firstlevelresultcount, jiraNumber);
					logExtentStatus(extentLogger, firstlevelresultcount, issueSummary, jiraNumber);
				}

			
				legislationpage.clickClear();

				// Open Doctrine page
				doctrinepage = searchpage.OpenDoctrinaPage();
				doctrinepage.enterThematicOnSearchPage(thematicfirstlevelVal);
				Thread.sleep(1000); // this wait is needed for dropdown
									// to display
				firstlevelsuggestionsDisplayed = doctrinepage.isTheSuggestionsDropdownDisplayed()
						&& doctrinepage.isTitlePresentInSearchSuggestions(thematicfirstlevelVal);

				if (firstlevelsuggestionsDisplayed) {
					searchpage.selectFirstTilteonSuggestionPage();
					boolean firstlevelresultcount = doctrinepage.isResultCountDisplayedForFstSearchString();

					softAssert.assertTrue(firstlevelsuggestionsDisplayed && firstlevelresultcount, jiraNumber);
					logExtentStatus(extentLogger, firstlevelsuggestionsDisplayed && firstlevelresultcount, issueSummary,
							jiraNumber);
				}			
				doctrinepage.clickClear();
				// Open jurisprudence page

				jurisprudencepage = searchpage.openJurisprudencePage();

				jurisprudencepage.enterThematicOnSearchPage(thematicfirstlevelVal);
				Thread.sleep(1000); // this wait is needed for dropdown
									// to display
				firstlevelsuggestionsDisplayed = jurisprudencepage.isTheSuggestionsDropdownDisplayed()
						&& jurisprudencepage.isTitlePresentInSearchSuggestions(thematicfirstlevelVal);

				if (firstlevelsuggestionsDisplayed) {
					searchpage.selectFirstTilteonSuggestionPage();

					boolean firstlevelresultcount = jurisprudencepage.isResultCountDisplayedForFstSearchString();

					softAssert.assertTrue(firstlevelsuggestionsDisplayed && firstlevelresultcount, jiraNumber);
					logExtentStatus(extentLogger, firstlevelsuggestionsDisplayed && firstlevelresultcount, issueSummary,
							jiraNumber);
				}

				
				jurisprudencepage.clickClear();

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	/*multiple thematic search boxes are not working for mexico now, hence commenting the test case*/
	@Test(priority = 9, groups = { "chpmex" }, description = "MAFAUTO-103")
	public void validateThesaurusRelatedTerms() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			HomePage homepage = this.homepage;

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Global Search - Thesaurus Related Terms");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicsearchKey = "thematicsearch";
				String thematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicsearchKey,
						extentLogger);

				String secondthematicsearchKey = "secondthematicsearch";
				String secondthematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						secondthematicsearchKey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				homepage.enterTextInSearchField(thematicsearchVal);
				boolean suggestionsDisplayed = searchpage.isSearchStringhighlightedOnCombo(thematicsearchVal);
				if (suggestionsDisplayed) {
					if(BaseTest.productUnderTest.equals("chpmex")) {
						searchpage.selectFirstTitleOnSearchSuggestionsInMex(1);
					}
					else {
					searchpage.selectFirstTitleOnSearchSuggestions(1);
					}
				}
				softAssert.assertTrue(suggestionsDisplayed,
						jiraNumber + ": Combo suggestions with highlighting the search string");
				logExtentStatus(extentLogger, suggestionsDisplayed,
						issueSummary + " : Combo suggestions with highlighting the search string", jiraNumber);

				// checking whether clear button is displayed
				boolean isClearbuttonandNoOfResultDisplay = searchpage.isClearBelowThematicTextVisible()
						&& searchpage.isResultCountDisplayedForFstSearchString();
				softAssert.assertTrue(isClearbuttonandNoOfResultDisplay,
						jiraNumber + "Clean link below the suggestion box and the number of results found");
				logExtentStatus(extentLogger, isClearbuttonandNoOfResultDisplay,
						" Clean link below the suggestion box and the number of results found", jiraNumber);

				// enter second thematic value
			//	searchpage.enterSecondThematicSearchString(secondthematicsearchVal);
				searchpage.clickClear();
				homepage.enterTextInSearchField(secondthematicsearchVal);
				boolean suggestionsDisplayedForSndField = searchpage.isTheSuggestionsDropdownDisplayed();
				if (suggestionsDisplayedForSndField) {
					searchpage.selectFirstTitleOnSearchSuggestions(2);
				}

				softAssert.assertTrue(suggestionsDisplayedForSndField,
						jiraNumber + "Combo suggestions with highlighting the search string");
				logExtentStatus(extentLogger, suggestionsDisplayed,
						"Combo suggestions with highlighting the search string", jiraNumber);

				boolean issecondClearbuttonandNoOfResultDisplay = searchpage.isClearBelowThematicTextVisible()
						&& searchpage.isResultCountDisplayedForFstSearchString();

				softAssert.assertTrue(issecondClearbuttonandNoOfResultDisplay,
						jiraNumber + "Clean link below the suggestion box and the number of results found");
				logExtentStatus(extentLogger, suggestionsDisplayed,
						"Clicking Clear is removing result counts and extra search boxes added",
						thematicsearchKey + ":" + thematicsearchVal,
						secondthematicsearchKey + ":" + secondthematicsearchVal, jiraNumber);

				// clearing the data entered
			//	searchpage.clickClear();

			//	suggestionsDisplayed = searchpage.OneThematicSearchTxtBoxIsRemainingUponClear();

				softAssert.assertTrue(suggestionsDisplayed,
						jiraNumber + "Clicking Clear is removing result counts and extra search boxes added");
				logExtentStatus(extentLogger, suggestionsDisplayed,
						"Clicking Clear is removing result counts and extra search boxes added",
						thematicsearchKey + ":" + thematicsearchVal,
						secondthematicsearchKey + ":" + secondthematicsearchVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}

	}

	@Test(priority = 10, groups = { "chparg", "chpury", "chppe", "chpchile" }, description = "MAFQABANG-57")
	public void modifySearchFromSearchResultsPage() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify the modify/edit/reformulate search in search result page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				boolean isfreeworddisplayed = searchpage.isFreewordFieldDisplayed();
				if (!isfreeworddisplayed) {
					searchpage.clickThematicSearchRadioButton();
				}

				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = searchpage.clickonSearchwhenThematicisSelected();

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

				searchpage = searchResultsPage.ClickModifySearchLink();

				boolean ismodifysearchworking = searchpage.searchPageDisplayed()
						&& searchpage.isModifySearchRetainsSearchStrings();

				softAssert.assertTrue(ismodifysearchworking && searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, ismodifysearchworking && searchResultsDisplayed, issueSummary,
						freewordkey, freewordVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 11, groups = { "chpmex", "chparg", "chpury", "chppe",
			"chpchile" }, description = "MAFQABANG-58")
	public void newSearchFromSearchResultsPage() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify the new search from search result page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				boolean isfreeworddisplayed = searchpage.isFreewordFieldDisplayed();
				if (!isfreeworddisplayed) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordVal);
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				
				searchResultsPage = searchpage.clickonSearchwhenThematicisSelected();

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

				searchpage = searchResultsPage.ClickNewSearchLink();

				boolean isnewsearchworking = searchpage.searchPageDisplayed()
						&& searchpage.isNewSearchEmptiesSearchStrings();

				softAssert.assertTrue(isnewsearchworking && searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isnewsearchworking && searchResultsDisplayed, issueSummary, freewordkey,
						freewordVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 12, groups = { "chparg", "chpury", "chpbr", "chppe",
			"chpchile" }, description = "MAFQABANG-468")
	public void displayofSearchResultsBasedonContentTypeAll() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Display of SearchResults based on Content type");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String contenttypeKey = "contenttypelinkname";
				JSONArray contenttypeVal = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, contenttypeKey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				boolean isfreeworddispalyed = searchpage.isFreewordFieldDisplayed();
				if (!isfreeworddispalyed) {
					searchpage.clickThematicSearchRadioButton();
				}

				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = searchpage.clickonSearchwhenThematicisSelected();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound) {
						softAssert.assertTrue(true,
								jiraNumber + ":-resulted in no search results" + " :" + freewordVal);
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordKey,
								freewordVal, jiraNumber);
					} else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				for (int i = 0; i < contenttypeVal.size(); i++) {
					boolean islinkdisplayed = searchResultsPage
							.isGivenFacetLinkDisplayed(contenttypeVal.get(i).toString());
					if (islinkdisplayed) {
						searchResultsPage.clickOnGivenFacetLink(contenttypeVal.get(i).toString());
						searchResultsDisplayed = searchResultsPage
								.resultSetDisplayBasedOnContentSet(contenttypeVal.get(i).toString());
					}
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, freewordKey + ":" + freewordVal,
						contenttypeKey + ":" + contenttypeVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 13, groups = { "chpury" }, description = "MAFQABANG-548")
	public void SearchPageValidation() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchpageverified = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Validate All expected widgets and contents on search page");

			searchpage = homepage.OpenSearchPage();
			searchpageverified = searchpage.isAdvancedSearchWidgetDisplayed()
					&& searchpage.isDefaultContentTreeDisplayed() && searchpage.isLinkPresentInAdvancedSearchWidget()
					&& searchpage.isExpandLevel2LinkDisplayed() && searchpage.isCollapseToLevel1LinkDisplayed()
					&& searchpage.isShowCurrentVersionRadioDisplayed() && searchpage.isShowAllVersionRadioDisplayed();

			softas.assertTrue(searchpageverified, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, searchpageverified, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 14, groups = { "chppe" }, description = "MAFQABANG-580")
	public void searchwithnotestdataandvalidthematicvalue() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Thematic Search without providing input and with valid input");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicsearchKey = "thematicsearch";
				String thematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicsearchKey,
						extentLogger);
				String errormsgKey = "errormsg";
				String errormsgVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, errormsgKey, extentLogger);

				homepage.clickOnProductLogo();
				searchpage = homepage.OpenSearchPage();
				boolean isthematicradionotdisplayed = searchpage.isThematicRadioButtonDisplayed();
				if (isthematicradionotdisplayed) {
					// without providing nay data
					searchpage.clickThematicSearchRadioButton();
				}
				searchpage.clickonSearchwhenThematicisSelected();
				boolean errmsgdisplayed = searchpage.isErrorMessageDisplayed(errormsgVal);
				softAssert.assertTrue(errmsgdisplayed,
						jiraNumber + "Error message dispalyed for searching without data");
				logExtentStatus(extentLogger, errmsgdisplayed, "Error message dispalyed for searching without data",
						jiraNumber);

				// after providing test data
				searchpage.enterThematicOnSearchPage(thematicsearchVal);
				boolean suggestionsDisplayed = searchpage.isTheSuggestionsDropdownDisplayed()
						&& searchpage.isSearchStringhighlightedOnCombo(thematicsearchVal);

				if (suggestionsDisplayed) {
					searchpage.selectTitleInTheamaticSearchSuggestions(thematicsearchVal);
				}
				softAssert.assertTrue(suggestionsDisplayed,
						jiraNumber + "Combo suggestions with highlighting the search string");
				logExtentStatus(extentLogger, suggestionsDisplayed,
						"Combo suggestions with highlighting the search string", jiraNumber);

				boolean isResultCountDisplayed = searchpage.isResultCountDisplayedForFstSearchString();
				if (isResultCountDisplayed) {
					searchResultsPage = searchpage.clickonSearchwhenThematicisSelected();
					searchResultsDisplayed = searchResultsPage != null
							&& searchResultsPage.searchResultsHeaderContainerDisplayed();

					if (!searchResultsDisplayed) {
						boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
						if (noResultsFound)
							logExtentNoResultsAsInfo(extentLogger, issueSummary, thematicsearchKey,
									thematicsearchVal + " -resulted in no search results", jiraNumber);
						else {
							// adding this else part for the error page we are
							// getting when application is down
							logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
							softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
						}

						continue; // Skip this & Continue with next iteration
					}
					softAssert.assertTrue(searchResultsDisplayed, jiraNumber + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, thematicsearchKey,
							thematicsearchVal, jiraNumber);
				} else {
					boolean errmsgdispalyed = searchpage.isErrorMessageDisplayed(errormsgVal);
					logExtentStatus(extentLogger, errmsgdispalyed, "Search failed :", jiraNumber);
					softAssert.assertTrue(errmsgdispalyed, jiraNumber + ":Search failed :");
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

	@Test(priority = 15, groups = { "chpury", "chppe", "chpchile" }, description = "MAFQABANG-52")
	public void SearchPageClearWithFreewordAndThematicKey() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify clean button in the search page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicsearchKey = "thematicsearch";
				String thematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicsearchKey,
						extentLogger);
				String suggestionvaltoselectKey = "suggestionvaltoselect";
				String suggestionvaltoselectVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						suggestionvaltoselectKey, extentLogger);
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage.clickOnProductLogo();
				searchpage = homepage.OpenSearchPage();

				boolean isfreeworddispalyed = searchpage.isFreewordFieldDisplayed();
				if (!isfreeworddispalyed) {
					searchpage.clickThematicSearchRadioButton();
				}

				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchpage.enterThematicOnSearchPage(thematicsearchVal);
				boolean isuggestionsdisplayed = searchpage.isTheSuggestionsDropdownDisplayed();
				if (isuggestionsdisplayed) {
					searchpage.selectTitleInSearchSuggestions(suggestionvaltoselectVal);
				}

				searchpage.clickonClearwhenThematicisSelected();
				String clearedText = searchpage.getFreewordTextOnSearchPage();

				boolean freewordCleared = clearedText.equals("");
				clearedText = searchpage.getThematicTextOnSearchPage();
				freewordCleared = freewordCleared
						&& (clearedText.equals("") || clearedText.equals("Búsqueda Temática"));

				softAssert.assertTrue(freewordCleared, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, freewordCleared, issueSummary, freewordKey + ":" + freewordVal,
						thematicsearchKey + ":" + thematicsearchVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 16, groups = { "chpmex" }, description = "MAFAUTO-237")
	public void correlationsVoicesContextValidation() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed, iscontentdispalyed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Legislation - En las unidades RB no se incluye el icono correlaciones ni despliega las voces que tiene indizadas");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicdropdownKey = "thematicarea";
				String thematicdropdownVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicdropdownKey,
						extentLogger);
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String titleKey = "title";
				String titleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, titleKey, extentLogger);
				
				String validation_Voz = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "validation_Voz", extentLogger);
				homepage.clickOnProductLogo();
				searchpage = homepage.OpenSearchPage();

				// searchpage.selectGivenValueFromThematicDropdown(thematicdropdownVal);
				// searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchpage.thematicAreaDropdownContainsValueInMex(thematicdropdownVal);
				searchpage.enterTextInSearchField(freewordVal);
				searchResultsPage = searchpage.clickOnSearch();

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

				documentPage = searchResultsPage.clickFirstLink();
				
				boolean isvocesdisplayed = documentPage.isVocesDisplayedSearchPage();
				

				softAssert.assertTrue(isvocesdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isvocesdisplayed, issueSummary, freewordKey + ":" + freewordVal,
						thematicdropdownKey + ":" + thematicdropdownVal, jiraNumber);
				
				boolean verifiedtext=documentPage.isVerifyTextResultsDocument(validation_Voz);
						
						softAssert.assertTrue(verifiedtext, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, verifiedtext, issueSummary, freewordKey + ":" + freewordVal,
						thematicdropdownKey + ":" + thematicdropdownVal, jiraNumber);
				
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 18, groups = { "chpmex" }, description = "MAFAUTO-226")
	public void creatingShortcutforContentTreeNode() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Tocectory - Add icon in Tocectory");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "thematicarea",
						extentLogger);
				String treeItems = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "treeitems", extentLogger);
				String nodename = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "nodename", extentLogger);

				// HomePage homepage = this.homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				// Select Thematic Area & Click on area in tree(if it is not already selected)
				searchpage.selectGivenValueFromThematicDropdown(thematicArea);
				if (searchpage.isAreaFoundInContentTree(thematicArea)) {
					searchpage.selectAreaFromContentTree(thematicArea);
				}

				// Expand First level item in the tree structure
				WebElement treeElement = searchpage.getFirstLevelContentTree(treeItems);
				searchpage.expandContentTreeElement(treeElement);

				String shortcutname = RandomUtils.getUniqueNumber();
				searchpage.clickonCreateShortcutforSpecificNode(nodename);
				searchpage.enterShortcutLinkName(nodename + shortcutname);
				searchpage.clickSaveShortcut();
				searchpage.acceptSaveShortcut();

				homepage = searchpage.clickHomeTab();
				boolean isshortcutwidgetmaximized = homepage.isShortcutsWidgetMaximized();
				if (!isshortcutwidgetmaximized) {
					homepage.maximizeShortcutsWidget();
				}

				String expectedshortcutname = nodename + shortcutname;
				boolean isshortcutlinkavailable = homepage.isExactShortcutLinkAvailable(expectedshortcutname);
				softAssert.assertTrue(isshortcutlinkavailable, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, isshortcutlinkavailable, issueSummary, thematicArea, nodename,
						jiraNumber);

				// deleting the shortcut created
				boolean isremoveinshortcutwidgetavailable = homepage.isRemoverinShortcutWidgetAvailable();
				homepage.clickRemoverinShortcutWidget(expectedshortcutname);
				softAssert.assertTrue(isremoveinshortcutwidgetavailable,
						jiraNumber + "created shortcut widget is removed successfully");
				logExtentStatus(extentLogger, isremoveinshortcutwidgetavailable,
						"created shortcut widget is removed successfully", thematicArea, nodename, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 3, groups = { "chparg", "chpury", "chppe" }, description = "LLOAR-4933")
	public void authorFilterLabelDisplayed() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Result list - incorrect filter label");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String searchtextKey = "searchtext";
				String searchtextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searchtextKey,
						extentLogger);

				String filterlabelname = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "filterlabelname",
						extentLogger);
				String contenttypeKey = "contenttypelinkname";
				JSONArray contenttypeVal = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, contenttypeKey,
						extentLogger);
				searchpage = homepage.OpenSearchPage();

				boolean isfreeworddisplayed = searchpage.isFreewordFieldDisplayed();
				if (!isfreeworddisplayed) {
					searchpage.clickThematicSearchRadioButton();
				}

				searchpage.enterFreeWordOnSearchPage(searchtextVal);
				searchResultsPage = searchpage.clickonSearchwhenThematicisSelected();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(searchtextVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", searchtextKey,
								searchtextVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
						if (!noResultsFound) {
							// Log existing bug
							testResult.setAttribute("defect", "MAFAUTO-271");

						}
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean isAutorFilterDisplayed = false;
				for (int i = 0; i < contenttypeVal.size(); i++) {
					boolean islinkdisplayed = searchResultsPage
							.isGivenFacetLinkDisplayed(contenttypeVal.get(i).toString());
					if (islinkdisplayed) {
						searchResultsPage.clickOnGivenFacetLink(contenttypeVal.get(i).toString());
						isAutorFilterDisplayed = searchResultsPage.isAutorFilterLabelDisplayed(filterlabelname);
					}
				}
				softAssert.assertTrue(isAutorFilterDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isAutorFilterDisplayed, issueSummary, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			softAssert.assertAll();
			extentReports.endTest(extentLogger);
		}
	}

	/*
	 * MAFQABANG-72 Search_Add and Remove Filters in Search Results Note: remove
	 * filter is not applicable in chp
	 */
	@Test(priority = 14, groups = { "chparg" }, description = "MAFQABANG-72")
	public void addFilterOnResultList() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Add and Remove Filters in Search Results");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);

				String doctrinekey = "doctrinelabel";
				String doctrineVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, doctrinekey, extentLogger);

				// perform search to display the result list.
				searchpage = homepage.OpenSearchPage();
				boolean isfreeworddisplayed = searchpage.isFreewordFieldDisplayed();
				if (!isfreeworddisplayed) {
					searchpage.clickThematicSearchRadioButton();
				}
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = searchpage.clickonSearchwhenThematicisSelected();
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
						logExtentStatus(extentLogger, noResultsFound, "Search failed", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + "Search failed");
					}

					continue; // Skip this & Continue with next iteration

				}

				// Verify filter by clicking 'Doctrina' in source widget
				searchResultsPage.clickDoctrinaInSourceWidget();
				boolean filterByDoctrine = searchResultsPage.isAllWidgetsExistForDoctrina()
						&& searchResultsPage.resultSetDisplayBasedOnContentSet(doctrineVal);

				softas.assertTrue(filterByDoctrine, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, filterByDoctrine, issueSummary, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 3, groups = { "chparg", "chpury", "chppe", "chpchile" }, description = "MAFQABANG-84")
	public void searchWithInDocumentDisplay() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify Search Within Search Results from Document Display");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String searchtextKey = "searchtext";
				String searchtextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searchtextKey,
						extentLogger);
				String searchwithintextKey = "searchwithintext";
				String searchwithintextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, searchwithintextKey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();

				boolean isfreeworddisplayed = searchpage.isFreewordFieldDisplayed();
				if (!isfreeworddisplayed) {
					searchpage.clickThematicSearchRadioButton();
				}

				searchpage.enterFreeWordOnSearchPage(searchtextVal);
				searchResultsPage = searchpage.clickonSearchwhenThematicisSelected();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(searchtextVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", searchtextKey,
								searchtextVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				// Get total number of documents displayed on the search results
				int docCount = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());

				documentPage = searchResultsPage.getFirstDocument();

				documentPage.clickSearchResultsTab();
				searchResultsDisplayed = documentPage.isSearchWithinOptionDisplayed();
				softAssert.assertTrue(searchResultsDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, searchResultsDisplayed,
						"Search within Search Option displayed in document display", jiraNumber);

				// verify search within is working
				documentPage.enterSearchWithinTerm(searchwithintextVal);
				documentPage.clickOnSearchWithInSearch();
				Thread.sleep(4000);

				// Verify the count of the documents returned is less
				// than the count from the parent set
				int docCountAfterSearchWithin = Integer.parseInt(searchResultsPage.getDocCountFromResultSet());

				System.out.println(docCountAfterSearchWithin);
				boolean isSearchWithinWorking = searchResultsPage.searchWithInResultsDisplayed(searchtextVal)
						&& searchResultsPage.searchWithInResultsDisplayed(searchwithintextVal)
						&& docCount > docCountAfterSearchWithin;

						Thread.sleep(1000);
				softAssert.assertTrue(isSearchWithinWorking, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchWithinWorking, issueSummary, searchtextKey + ":" + searchtextVal,
						searchwithintextKey + ":" + searchwithintextVal, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			softAssert.assertAll();
			extentReports.endTest(extentLogger);
		}
	}

	// To check the given text present in document titles for all thematic areas
	// disabled right now as taking more time to execute
	@Test(priority = 18, groups = { "chpmex" }, description = "MAFQABANG-637", enabled = false)
	public void DocumentsAvailableForSelectedThematicArea() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Documents present for the given thematic area and given text is present in document titles");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String thematicarea = "thematicarea";
				String thematicareaval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicarea,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				homepage.SelectThematicareadropdowns(thematicareaval);
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchResultsPage = searchpage.clickOnSearch();

				// Check for given text in the document titles for legislation
				searchResultsPage.clickOnGivenContentTypeOnFacet("Legislación");
				boolean freewordPresentInLegislationDocs = searchResultsPage
						.isGivenTextPresentInResultList(freewordVal);

				softAssert.assertTrue(freewordPresentInLegislationDocs, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, freewordPresentInLegislationDocs,
						"Test data displayed on doc title with freeword:" + freewordVal + " and thematicarea:"
								+ thematicareaval,
						jiraNumber);

				// Check for given text in the document titles for jurisprudence
				searchResultsPage.clickOnGivenContentTypeOnFacet("Jurisprudencia");
				searchResultsPage.clickNextPageLink();
				boolean freewordPresentInjurisprudenceDocs = searchResultsPage
						.isGivenTextPresentInResultList(freewordVal);
				softAssert.assertTrue(freewordPresentInjurisprudenceDocs, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, freewordPresentInjurisprudenceDocs,
						"Test data displayed on doc title with freeword:" + freewordVal + " and thematicarea:"
								+ thematicareaval,
						jiraNumber);

				// Check for given text in the document titles for doctrine
				searchResultsPage.clickOnGivenContentTypeOnFacet("Doctrina");
				searchResultsPage.clickNextPageLink();
				boolean freewordPresentIndoctrineDocs = searchResultsPage.isGivenTextPresentInResultList(freewordVal);

				softAssert.assertTrue(freewordPresentIndoctrineDocs, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, freewordPresentIndoctrineDocs,
						"Test data displayed on doc title with freeword:" + freewordVal + " and thematicarea:"
								+ thematicareaval,
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

	////////////////////////////////// Implementation of CHPMEX New Functionality
	////////////////////////////////// testcases//////////////////////////////////////////////////////////////

	// Description : "This testcase checks whether the Global Search title is
	// displayed in the homepage and checks the placeholder text in search textbox"
	// <Created Date : 24-Oct-2018 > ; <author : Havya>

	@Test(priority = 3, groups = { "chpmex" }, description = "MAFQABANG-663")
	public void ValidationOfGlobalSearchInSearch() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Validation of global search");

			homepage.openHomepage();
			searchpage = homepage.OpenSearchPage();

			boolean isglobalsearchtitleDisplayed = searchpage.isGlobalSearchTitleDisplayed(1);
			softAssert.assertTrue(isglobalsearchtitleDisplayed, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, isglobalsearchtitleDisplayed, ": title is displayed", jiraNumber);

			// Verify the placeholder text in global search textbox - MAFQABANG-673
			boolean isPlaceHolderTextPresent = searchpage.isPlaceHolderTextDisplayed();
			softAssert.assertTrue(isPlaceHolderTextPresent, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, isPlaceHolderTextPresent, ": PlaceHolder Text is displyed", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	// Description : "This testcase validates the options present in thematic area
	// dropdown and also selects one option from the dropdown"
	// <Created Date : 24-Oct-2018 > ; <author : Havya>

	@Test(priority = 3, groups = { "chpmex" }, description = "MAFQABANG-700")
	public void ValidationofThematicAreaDropdownInSearch() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Validation of Thematic area dropdown and its options");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematic_area";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				// Below code is to verify the option present in thematic area dropdown -
				// MAFQABANG-699
				boolean isThematicAreaOptionsDisplayed = searchpage.isThematicAreaDropdownOptionsAvailableInMex();
				softAssert.assertTrue(isThematicAreaOptionsDisplayed,
						jiraNumber + ":" + issueSummary + " : all the options are present in the dropdown");
				logExtentStatus(extentLogger, isThematicAreaOptionsDisplayed,
						" all the options are present in the dropdown", jiraNumber);

				// Below code is to select the given thematic area from dropdown - MAFQABANG-700

				boolean isThematicAreaSelected = searchpage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softAssert.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
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

	// Description: "This testcase checks whether the see all link is present next
	// to each document type"
	// <Created Date : 25-Oct-2018 > ; <author : Havya>
	// this testcase covers 37

	@Test(priority = 3, groups = { "chpmex" }, description = "MAFQABANG-687")
	public void ValidationofSeeAllLinkInSearch() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify See all Link next to each documentary");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey = "freeword";
				String freewordValue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey,
						extentLogger);

				String docTypeKey = "doc_type";
				JSONArray docTypeValue = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, docTypeKey,
						extentLogger);

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				searchpage.enterTextInSearchField(freewordValue);
				searchResultsPage = searchpage.clickOnSearch();

				for (int row = 0; row < docTypeValue.size(); row++) {

					String docTypeLinks = docTypeValue.get(row).toString();
					searchResultsPage.clickSeeAllLink(docTypeLinks);

					/*
					 * boolean isTotalNoOfResultsDisplayed =
					 * searchResultsPage.verifyTotalResultsText();
					 * softAssert.assertTrue(isTotalNoOfResultsDisplayed,jiraNumber + ":" +
					 * issueSummary + " :The total number of results text is displayed");
					 * logExtentStatus(extentLogger, isTotalNoOfResultsDisplayed,
					 * ": total number of results text is displayed", jiraNumber);
					 */
					Thread.sleep(3000);
					documentPage = searchResultsPage.getFirstDocument();
					boolean isDocDisplayed = documentPage.isDocumentDisplayed();
					softAssert.assertTrue(isDocDisplayed,
							jiraNumber + ":" + issueSummary + " :The Document display page is displayed");
					logExtentStatus(extentLogger, isDocDisplayed, ": document is displayed", jiraNumber);

					driver.navigate().back();
        			driver.navigate().back();

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

	// This test is to check the place holder value in the search box in search page
	// <Created Date : 24-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 25, groups = { "chpmex" }, description = "MAFQABANG-701")
	public void verifyPlaceHolderValueForAskChpInSearchPage() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				searchpage = homepage.OpenSearchPage();
				searchpage.clickAskCheckpointLink();
				Thread.sleep(2000);
				boolean verifiedPlaceHolder = searchpage.isPlaceHolderTextDisplayedForAskChp();
				softas.assertTrue(verifiedPlaceHolder, jiraNumber + ":" + issueSummary
						+ " : Verified Place Holder Value in the ask checkpoint search box");
				logExtentStatus(extentLogger, verifiedPlaceHolder,
						" Verified Place Holder Value in the ask checkpoint search box", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// This test case is to check the text present when hovering the help icon in
	// search page
	// <Created Date : 24-Oct-2018 > ; <author : Saikiran>
	@Test(priority = 26, groups = { "chpmex" }, description = "MAFQABANG-702")
	public void verifyTextInHelpIconInSearchPage() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(2000);
				boolean verifiedText = searchpage.helpIconText();
				softas.assertTrue(verifiedText,
						jiraNumber + ":" + issueSummary + " : Verified the text present when hover on help icon");
				logExtentStatus(extentLogger, verifiedText, " Verified the text present when hover on help icon",
						jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// This test case is to validate the clean button home page
	// <Created Date : 24-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 27, groups = { "chpmex" }, description = "MAFQABANG-708")
	public void verifyCleanButtonInSearchPage() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(2000);
				searchpage.enterTextInSearchField(free_wordval);
				searchpage.clickOnCleanSearch();
				Thread.sleep(2000);
				boolean verifiedPlaceHolder = homepage.isPlaceHolderTextDisplayed();
				softas.assertTrue(verifiedPlaceHolder,
						jiraNumber + ":" + issueSummary + " : Validated the clean button in the Search Page");
				logExtentStatus(extentLogger, verifiedPlaceHolder, " Validated the clean button in the Search Page",
						jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description: "This testcase checks whether the see all link is present next
	// to each document type"
	// <Created Date : 25-Oct-2018 > ; <author : Havya>
	// this testcase covers 44,48

	@Test(priority = 3, groups = { "chpmex" }, description = "MAFQABANG-691")
	public void ValidationOfFilterResultsWidgetInSearch() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify See all Link next to each documentary from search page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey = "freeword";
				String freewordValue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey,
						extentLogger);

				String docTypeKey = "doc_type";
				JSONArray docTypeValue = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, docTypeKey,
						extentLogger);

				String widgetKey = "widget_name";
				String widgetValue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, widgetKey, extentLogger);
				;

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				searchpage.enterTextInSearchField(freewordValue);
				searchResultsPage = searchpage.clickOnSearch();

				// Verify User should able to see box appears filter results under the search
				// results - MAFQABANG-691

				boolean iswidgetdisplayed = searchResultsPage.isWidgetsDisplayedInLeftPanel(widgetValue);
				softAssert.assertTrue(iswidgetdisplayed, jiraNumber + ":" + issueSummary + " :The widget is displayed");
				logExtentStatus(extentLogger, iswidgetdisplayed, ": widget is displayed", widgetKey, widgetValue,
						jiraNumber);

				for (int row = 0; row < docTypeValue.size(); row++) {

					String docTypeLinks = docTypeValue.get(row).toString();

					// Verify the clicking of links in filter results leads to specific results list
					// - MAFQABANG-715
					searchResultsPage.clickOnGivenLinkByLinkText(docTypeLinks, false);

					boolean isDocTypeLabelDisplayed = searchResultsPage.isDocTypeLabelDisplayed(docTypeLinks);
					softAssert.assertTrue(isDocTypeLabelDisplayed,
							jiraNumber + ":" + issueSummary + " :The Document type is displayed");
					logExtentStatus(extentLogger, isDocTypeLabelDisplayed, ": document type is displayed", jiraNumber);

					documentPage = searchResultsPage.getFirstDocument();
					boolean isDocDisplayed = documentPage.isDocumentDisplayed();
					softAssert.assertTrue(isDocDisplayed,
							jiraNumber + ":" + issueSummary + " :The Document display page is displayed");
					logExtentStatus(extentLogger, isDocDisplayed, ": document is displayed", jiraNumber);

					driver.navigate().back();
					driver.navigate().back();

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

	// This test case is to validate the results in search page
	// <Created Date : 25-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 28, groups = { "chpmex" }, description = "MAFQABANG-680")
	public void validatingResultsForSearchInSearchPage() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);
				String suggestion = "suggestion_search";
				String suggestion_word = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestion,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(2000);
				searchpage.enterTextInSearchField(free_wordval);
				searchpage.selectingSuggestionsDropdownDisplayed(suggestion_word);
				// searchpage.clickOnCleanSearch();
				// Thread.sleep(4000);
				boolean validatedSearchResult = homepage.isSearchResultsDisplayed();
				softas.assertTrue(validatedSearchResult,
						jiraNumber + ":" + issueSummary + " : Validated the results for search in the Search Page");
				logExtentStatus(extentLogger, validatedSearchResult,
						" Validated the results for search in the Search Page", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// This test case is to validate the actions buttons in the result page in
	// search page
	// covers both MAFQABANG-720 and MAFQABANG-716 test cases
	// <Created Date : 25-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 29, groups = { "chpmex" }, description = "MAFQABANG-720")
	public void validatingActionsInResultPageFromSearch() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);
				String linkToClick = "link";
				String link_click = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, linkToClick, extentLogger);
				String to_export = "export";
				String export = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, to_export, extentLogger);
				String to_print = "print";
				String print = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, to_print, extentLogger);
				String to_email = "email";
				String email = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, to_email, extentLogger);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(2000);
				searchpage.enterTextInSearchField(free_wordval);
				searchResultsPage = searchpage.clickOnSearch();
				searchResultsPage.clickOnGivenLinkByLinkText(link_click, false);
				Thread.sleep(2000);
				boolean validatedExportButton = searchResultsPage.validateActions(export);
				softas.assertTrue(validatedExportButton,
						jiraNumber + ":" + issueSummary + " : Validated the Export button in the result Page");
				logExtentStatus(extentLogger, validatedExportButton,
						" Validated the results for search in the Search Page", jiraNumber);
				boolean validatedPrintButton = searchResultsPage.validateActions(print);
				softas.assertTrue(validatedPrintButton,
						jiraNumber + ":" + issueSummary + " : Validated the print button in the result Page");
				logExtentStatus(extentLogger, validatedPrintButton,
						" Validated the results for search in the Search Page", jiraNumber);
				boolean validatedEmailButton = searchResultsPage.validateActions(email);
				softas.assertTrue(validatedEmailButton,
						jiraNumber + ":" + issueSummary + " : Validated the email button in the result Page");
				logExtentStatus(extentLogger, validatedEmailButton,
						" Validated the results for search in the Search Page", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description:"This testcase is used to compare the count that is in
	// paranthesis next to each document type in
	// left panel and see all link in right panel
	// <Created Date : 29-Oct-2018 > ; <author : Havya>
	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-712")
	public void verifyCountOfSeeAllLinkInSearch() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify along with filter results the link of each document type found and in paranthesis number of matches from search page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();
				searchpage.enterTextInSearchField(free_wordval);

				searchResultsPage = searchpage.clickOnSearch();

				// comparing the count in the paranthesis which is present next to legislation
				// document type in left panel and the see all link in right panel
				boolean isCountMatch1 = searchResultsPage.compareCountInSeeAllLink(2);
				softAssert.assertTrue(isCountMatch1,
						jiraNumber + ":" + issueSummary + " :The Document type is displayed");
				logExtentStatus(extentLogger, isCountMatch1, ": document type is displayed", jiraNumber);

				// comparing the count in the paranthesis which is present next to Doctrina
				// document type in left panel and the see all link in right panel
				boolean isCountMatch2 = searchResultsPage.compareCountInSeeAllLink(3);
				softAssert.assertTrue(isCountMatch2,
						jiraNumber + ":" + issueSummary + " :The Document type is displayed");
				logExtentStatus(extentLogger, isCountMatch2, ": document type is displayed", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	// Description:"This testcase is used to compare the count that is in
	// paranthesis next to each document type in
	// left panel and the count in right panel
	// <Created Date : 29-Oct-2018 > ; <author : Havya>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-711")
	public void verifyCountOfDocumentTypeInSearch() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify list of results after clicking see all and count of documents next to it from home page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);

				String docTypeKey = "doc_type";
				JSONArray docTypeValue = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, docTypeKey,
						extentLogger);

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();
				searchpage.enterTextInSearchField(free_wordval);

				searchResultsPage = searchpage.clickOnSearch();

				for (int row = 0; row < docTypeValue.size(); row++) {

					String docTypeLinks = docTypeValue.get(row).toString();

					searchResultsPage.clickSeeAllLink(docTypeLinks);

					boolean isDocTypeLabelDisplayed = searchResultsPage.verifyCountOfDocs();
					softAssert.assertTrue(isDocTypeLabelDisplayed, jiraNumber + ":" + issueSummary
							+ " :The count of the document type in left panel is equal to the count in right panel");
					logExtentStatus(extentLogger, isDocTypeLabelDisplayed,
							":The count of the document type in left panel is equal to the count in right panel",
							jiraNumber);

					driver.navigate().back();

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

	// This test case is to validate the list of the documnets button in search page
	// <Created Date : 29-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 28, groups = { "chpmex" }, description = "MAFQABANG-733")
	public void validatingListaDeDocumentosInSearchPage() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);
				String suggestion = "suggestion_search";
				String suggestion_word = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestion,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(2000);
				searchpage.enterTextInSearchField(free_wordval);
				searchpage.selectingSuggestionsDropdownDisplayed(suggestion_word);
				Thread.sleep(2000);
				searchResultsPage = searchpage.clickOnSearch();
				searchResultsPage.clickOnVerCoincidenciasLink(1);
				documentPage = searchResultsPage.clickUnitAndRubric();
				searchResultsPage = documentPage.clickOnListOfDocumentsToReturnToSearchResults();
				boolean isLinkDisplayed = searchResultsPage.isVerCoincidenciasLinkDisplayed(1);
				softas.assertTrue(isLinkDisplayed, jiraNumber + ":" + issueSummary
						+ " : Validated the Lista De Documentos in the right Panel in search page");
				logExtentStatus(extentLogger, isLinkDisplayed,
						" Validated the Lista De Documentos in the right Panel in search page", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// This test case is to validate the list of the documnets button in search
	// page(Left Panel)
	// <Created Date : 29-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 28, groups = { "chpmex" }, description = "MAFQABANG-698")
	public void validatingListaDeDocumentosLeftPanelInSearchPage() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search Test ", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);
				String suggestion = "suggestion";
				String suggestion_word = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestion,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(2000);
				searchpage.enterTextInSearchField(free_wordval);
				searchpage.selectingSuggestionsDropdownDisplayed(suggestion_word);
				Thread.sleep(2000);
				searchResultsPage = searchpage.clickOnSearch();
				Thread.sleep(2000);
				searchResultsPage.clickOnVerCoincidenciasLink(1);
				Thread.sleep(1000);
				documentPage = searchResultsPage.clickUnitAndRubric();
				documentPage.clickOnListOfTheDocumentsInLeftPanel();
				boolean isLinkDisplayed = documentPage.isVerCoincidenciasLinkDisplayed();
				softas.assertTrue(isLinkDisplayed, jiraNumber + ":" + issueSummary
						+ " : Validated the Lista De Documentos in the left Panel in search page");
				logExtentStatus(extentLogger, isLinkDisplayed,
						" Validated the Lista De Documentos in the left Panel in search page", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 11, groups = { "chpmex" }, description = "MAFQABANG-58")
	public void newSearchFromSearchResultsPageInMex() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify the new search from search result page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				/*
				 * boolean isfreeworddisplayed = searchpage.isFreewordFieldDisplayed(); if
				 * (!isfreeworddisplayed) { searchpage.clickThematicSearchRadioButton(); }
				 * searchpage.enterFreeWordOnSearchPage(freewordVal); searchResultsPage =
				 * searchpage.clickonSearchwhenThematicisSelected();
				 */
				searchpage.enterTextInSearchField(freewordVal);
				searchResultsPage = searchpage.clickOnSearch();

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

				searchpage = searchResultsPage.ClickNewSearchLink();

				boolean isnewsearchworking = searchpage.searchPageDisplayed()
						&& searchpage.isNewSearchEmptiesSearchStrings();

				softAssert.assertTrue(isnewsearchworking && searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isnewsearchworking && searchResultsDisplayed, issueSummary, freewordkey,
						freewordVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 10, groups = { "chpmex" }, description = "MAFQABANG-57")
	public void modifySearchFromSearchResultsPageInMex() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify the modify/edit/reformulate search in search result page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				/*
				 * boolean isfreeworddisplayed = searchpage.isFreewordFieldDisplayed(); if
				 * (!isfreeworddisplayed) { searchpage.clickThematicSearchRadioButton(); }
				 * 
				 * searchpage.enterFreeWordOnSearchPage(freewordVal); searchResultsPage =
				 * searchpage.clickonSearchwhenThematicisSelected();
				 */
				searchpage.enterTextInSearchField(freewordVal);
				searchResultsPage = searchpage.clickOnSearch();
                Thread.sleep(4000);
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

				searchpage = searchResultsPage.ClickModifySearchLink();

				boolean ismodifysearchworking = searchpage.searchPageDisplayed()
						&& searchpage.isModifySearchRetainsSearchStringsInMex();

				softAssert.assertTrue(ismodifysearchworking && searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, ismodifysearchworking && searchResultsDisplayed, issueSummary,
						freewordkey, freewordVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	// Description: Thesaurus suggestion in the "Búsqueda Global" search suggestions
	// in search page
	// <Created Date : 25-Oct-2018 > ; <author : roja>

	@Test(priority = 223, groups = { "chpmex" }, description = "MAFQABANG-703")
	public void verifyThesaurussuggestionsSearch() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Thesaurus suggestion in the Búsqueda Global search suggestions  in search page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();

				searchpage = homepage.OpenSearchPage();

				searchpage.enterTextInSearchField(searchword);
				boolean isSuggestionDisplayed = searchpage.isTheSuggestionsDropdownDisplayed();

				softas.assertTrue(isSuggestionDisplayed,
						"Thesaurus suggestion in the Búsqueda Global search suggestions  in search page" + ":"
								+ jiraNumber);
				logExtentStatus(extentLogger, isSuggestionDisplayed,
						": Thesaurus suggestion in the Búsqueda Global search suggestions  in search page", jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description:Search by thesaurus suggestion in the "Búsqueda Global" search in
	// search page
	// <Created Date : 25-Oct-2018 > ; <author : roja>

	@Test(priority = 23, groups = { "chpmex" }, description = "MAFQABANG-704")
	public void SearchByThesaurusSuggestionInSearch() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Search by  thesaurus suggestion in the Búsqueda Global search  in search page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();

				searchpage = homepage.OpenSearchPage();

				searchpage.enterTextInSearchField(searchword);
				searchpage.selectingSuggestionsDropdownDisplayed("actualización de la");
				searchResultsPage = searchpage.clickOnSearch();

				documentPage = searchResultsPage.getFirstDocument();
				boolean isDocDisplayed = documentPage.isDocumentDisplayed();

				softas.assertTrue(isDocDisplayed,
						"Search by  thesaurus suggestion in the Búsqueda Global search  in search page" + ":"
								+ jiraNumber);
				logExtentStatus(extentLogger, isDocDisplayed,
						": Search by  thesaurus suggestion in the Búsqueda Global search  in search page", jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description:Search by non - thesaurus suggestion(free word) in the "Búsqueda
	// Global" search in search page
	// <Created Date : 25-Oct-2018 > ; <author : roja>

	@Test(priority = 25, groups = { "chpmex" }, description = "MAFQABANG-705")
	public void SearchByNonThesaurusSuggestionSearch() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Search by non - thesaurus suggestion(free word) in the Búsqueda Global search  in search page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();

				searchpage = homepage.OpenSearchPage();

				searchpage.enterTextInSearchField(searchword);

				searchResultsPage = searchpage.clickOnSearch();

				documentPage = searchResultsPage.getFirstDocument();
				boolean isDocDisplayed = documentPage.isDocumentDisplayed();

				softas.assertTrue(isDocDisplayed,
						"Search by non - thesaurus suggestion(free word) in the Búsqueda Globalsearch  in search page"
								+ ":" + jiraNumber);
				logExtentStatus(extentLogger, isDocDisplayed,
						":Search by non - thesaurus suggestion(free word) in theBúsqueda Globalsearch  in search page",
						jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	// keyword in the "Búsqueda Global" in search page
	// <Created Date : 25-Oct-2018 > ; <author : roja>

	@Test(priority = 26, groups = { "chpmex" }, description = "MAFQABANG-706")
	public void verifyNonThesaurusKeywordSearch() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "keyword in the Búsqueda Global in search page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);
				homepage.openHomepage();

				searchpage = homepage.OpenSearchPage();

				searchpage.enterTextInSearchField(searchword);
				Thread.sleep(2000);
				boolean isSuggestionDisplayed = !searchpage.isTheSuggestionsDropdownDisplayed();

				softas.assertTrue(isSuggestionDisplayed,
						"keyword in the Búsqueda Global in search page" + ":" + jiraNumber);

				logExtentStatus(extentLogger, isSuggestionDisplayed, ":keyword in the Búsqueda Global in search page",
						jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description: Search by keyword in the "Búsqueda Global" search without
	// suggessions in search page
	// <Created Date : 25-Oct-2018 > ; <author : Roja>

	@Test(priority = 27, groups = { "chpmex" }, description = "MAFQABANG-707")
	public void searchWithoutSuggessionsInsearch() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Search by keyword in the Búsqueda Global search without suggessions  in search page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();

				searchpage.enterTextInSearchField(searchword);

				searchResultsPage = searchpage.clickOnSearch();

				documentPage = searchResultsPage.getFirstDocument();
				boolean isDocDisplayed = documentPage.isDocumentDisplayed();

				softAssert.assertTrue(isDocDisplayed,
						"Search by keyword in the Búsqueda Global search without suggessions  in search page" + ":"
								+ jiraNumber);
				logExtentStatus(extentLogger, isDocDisplayed,
						":Search by keyword in the Búsqueda Global search without suggessions  in search page",
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

	// Description:Verify whether "See Matches" link is displayed(search page)
	// <Created Date : 29-Oct-2018 > ; <author : roja>

	@Test(priority = 13, groups = { "chpmex" }, description = "MAFQABANG-723")
	public void isVerCoincidenciasLinkDisplayedInSearch() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Verify whether VerCoincidencias link is displayed(search page)");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();

				searchpage = homepage.OpenSearchPage();

				searchpage.enterTextInSearchField(searchword);
				searchpage.selectingSuggestionsDropdownDisplayed("actualización de la");
				searchResultsPage = searchpage.clickOnSearch();
				boolean IsVerCoincidenciasLink = searchResultsPage.isVerCoincidenciasLinkDisplayed(1);

				softas.assertTrue(IsVerCoincidenciasLink,
						"Verify whether VerCoincidencias link is displayed(search page)" + ":" + jiraNumber);
				logExtentStatus(extentLogger, IsVerCoincidenciasLink,
						":Verify whether VerCoincidencias link is displayed(search page)", jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description:Verify the number of results displayed for the "Ver
	// coincidencias" link(search page)
	// <Created Date : 30-Oct-2018 > ; <author : Roja>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-692")
	public void VerifyTheNumberOfResultsInSearch() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify the number of results displayed for the Ver coincidencias link(search page)");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);
				homepage.openHomepage();
				searchpage = homepage.OpenSearchPage();
				searchpage.enterTextInSearchField(searchword);
				searchpage.selectingSuggestionsDropdownDisplayed("ley aduanera");

				searchResultsPage = searchpage.clickOnSearch();

				boolean IsElementPresent = searchResultsPage.isVerCoincidenciasLinkDisplayed(2);
				softas.assertTrue(IsElementPresent, "Ver coincidencias link is present" + ":" + jiraNumber);

				logExtentStatus(extentLogger, IsElementPresent, ":Ver coincidencias link is present", jiraNumber);
				searchResultsPage.clickOnVerCoincidenciasLink(2);
				Thread.sleep(1000);
				boolean results = searchResultsPage.isVerMasLinkDisplayed();

				softas.assertTrue(results,
						"Verify the number of results displayed for the Ver coincidencias link(search page)" + ":"
								+ jiraNumber);

				logExtentStatus(extentLogger, results,
						":Verify the number of results displayed for the Ver coincidencias link(search page)",
						jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description:verify whether unit+rubric is a link(search Page)
	// <Created Date : 29-Oct-2018 > ; <author : Roja>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-693")
	public void verifyUnitrubricLinkInSearchPage() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

              //Description:verify whether unit+rubric is a link(search Page)
			 //<Created Date :  29-Oct-2018 >   ; <author : Roja> 
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "verify whether unit+rubric is a link(search Page)");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();
				Thread.sleep(4000);
				searchpage = homepage.OpenSearchPage();
				searchpage.enterTextInSearchField(searchword);
				searchpage.selectingSuggestionsDropdownDisplayed("actualización de la ");

				searchResultsPage = searchpage.clickOnSearch();

				searchResultsPage.clickOnVerCoincidenciasLink(1);
				boolean IsUnitRubricLinkDisplayed = searchResultsPage.isUnitRubricLinkDisplayed();
				searchResultsPage.clickOnUnitAndRubricLink("A.121. Deducciones autorizadas");

				softas.assertTrue(IsUnitRubricLinkDisplayed,
						"verify whether unit+rubric is a link(Search Page)" + ":" + jiraNumber);
				logExtentStatus(extentLogger, IsUnitRubricLinkDisplayed,
						":verify whether unit+rubric is a link(Search Page)", jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
			
			//Verify the Error message when user search with empty search box.
			 //<Created Date :  31-Oct-2018 >   ; <author : Roja> 

			@Test(priority = 02,groups = { "chpmex" }, description = "MAFQABANG-677")
			public void verifyErrorMsgInSearch() throws Exception {
				SoftAssert softas = new SoftAssert();
				HomePage homepage = this.homepage;
				try {
					testResult = Reporter.getCurrentTestResult();

					extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
			

					String jiraNumber = testResult.getMethod().getDescription();

					String issueSummary = getIssueTitle(jiraNumber, "Verify the Error message when user search with empty search box");

					JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

					for (Object searchString : listOfSearchData) {

						JSONObject jsonObjectChild = (JSONObject) searchString;

						String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
								"searchKeyword", extentLogger);

				
					searchpage = homepage.OpenSearchPage();
				searchpage.enterTextInSearchField(searchword);
					

					searchResultsPage = searchpage.clickOnSearch();
					 boolean IsMsgDisplayed=searchResultsPage.isErrorMessageDisplayedHomePage();					
					
					
				     softas.assertTrue(IsMsgDisplayed,
							"Verify the Error message when user search with empty search box in search page" + ":" + jiraNumber);
					logExtentStatus(extentLogger, IsMsgDisplayed, ":Verify the Error message when user search with empty search box in search page", jiraNumber);
					}
				} catch (Exception exc) {
					logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

					softas.assertTrue(false, "Exception in Test");
				} finally {
					extentReports.endTest(extentLogger);
					softas.assertAll();
				}
			}
			
			//Verify the number of results displayed for each doc type in result page.
			 //<Created Date :  02-Nov-2018 >   ; <author : Kavitha> 

			@Test(priority = 02,groups = { "chpmex" }, description = "MAFQABANG")
			public void verifyResultCountForEachDocType() throws Exception {
				SoftAssert softas = new SoftAssert();
				HomePage homepage = this.homepage;
				try {
					testResult = Reporter.getCurrentTestResult();

					extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
					homepage.openHomepage();

					String jiraNumber = testResult.getMethod().getDescription();

					//String issueSummary = getIssueTitle(jiraNumber, "Verify the Error message when user search with empty search box");

					JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

					for (Object searchString : listOfSearchData) {

						JSONObject jsonObjectChild = (JSONObject) searchString;

						String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
								"searchKeyword", extentLogger);
						
						String issueSummary = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
								"Summary", extentLogger);

					//homepage.openHomepage();
					searchpage = homepage.OpenSearchPage();
					searchpage.enterTextInSearchField(searchword);
					

					searchResultsPage = searchpage.clickOnSearch();
					boolean isCountValid = searchResultsPage.DocTypeResultCountInSearch();					
					
					
				     softas.assertTrue(isCountValid,issueSummary+":"+jiraNumber);
					logExtentStatus(extentLogger, isCountValid, ":verifyResultCountForEachDocType", jiraNumber);
					}
				} catch (Exception exc) {
					logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

					softas.assertTrue(false, "Exception in Test");
				} finally {
					extentReports.endTest(extentLogger);
					softas.assertAll();
				}
			}
			
			//Verify the Error message when user search with empty search box.
			 //<Created Date :  02-Nov-2018 >   ; <author : Kavitha> 
			@Test(priority = 02,groups = { "chpmex" }, description = "MAFQABANG-677")
			public void verifyErrorMsgForEmptySearch() throws Exception {
				SoftAssert softas = new SoftAssert();
				HomePage homepage = this.homepage;
				try {
					testResult = Reporter.getCurrentTestResult();

					extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());

					String jiraNumber = testResult.getMethod().getDescription();

					String issueSummary = "Verify the Error message when user search with empty search box";
					searchpage = homepage.OpenSearchPage();

					searchResultsPage = searchpage.clickOnSearch();
					 boolean IsMsgDisplayed=homepage.isEmptySearchErrorMsgDisplayed();					
					
					
				     softas.assertTrue(IsMsgDisplayed,
				    		 issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, IsMsgDisplayed, "verifyErrorMsgForEmptySearch", jiraNumber);
					
				} catch (Exception exc) {
					logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

					softas.assertTrue(false, "Exception in Test");
				} finally {
					extentReports.endTest(extentLogger);
					softas.assertAll();
				}
			}
	
			//Verify whether the document snippet is displayed for documents with no units+rubric in result page.
			 //<Created Date :  02-Nov-2018 >   ; <author : Kavitha> 

			@Test(priority = 02,groups = { "chpmex" }, description = "MAFQABANG-724")
			public void verifyDocumentSnippetIsDisplayed() throws Exception {
				SoftAssert softas = new SoftAssert();
				HomePage homepage = this.homepage;
				try {
					testResult = Reporter.getCurrentTestResult();

					extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
					//homepage.openHomepage();

					String jiraNumber = testResult.getMethod().getDescription();

					//String issueSummary = getIssueTitle(jiraNumber, "Verify the Error message when user search with empty search box");

					JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

					for (Object searchString : listOfSearchData) {

						JSONObject jsonObjectChild = (JSONObject) searchString;

						String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
								"searchKeyword", extentLogger);
						
						String issueSummary = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
								"Summary", extentLogger);

					//homepage.openHomepage();
					searchpage = homepage.OpenSearchPage();
					searchpage.enterTextInSearchField(searchword);
					

					searchResultsPage = searchpage.clickOnSearch();
					boolean isDisplayed = searchResultsPage.isDocumentSnippetDisplayed();					
					
					
				     softas.assertTrue(isDisplayed,issueSummary+":"+jiraNumber);
					logExtentStatus(extentLogger, isDisplayed, ":verifyDocumentSnippetIsDisplayed", jiraNumber);
					}
				} catch (Exception exc) {
					logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

					softas.assertTrue(false, "Exception in Test");
				} finally {
					extentReports.endTest(extentLogger);
					softas.assertAll();
				}
			}
			
			//Verify whether the error message "No documents were found for your search." is displayed or not
			 //<Created Date :  05-Nov-2018 >   ; <author : Kavitha> 

			@Test(priority = 02,groups = { "chpmex" }, description = "MAFQABANG-667")
			public void verifyNoDocumentsErrorMsg() throws Exception {
				SoftAssert softas = new SoftAssert();
				HomePage homepage = this.homepage;
				try {
					testResult = Reporter.getCurrentTestResult();

					extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
					//homepage.openHomepage();

					String jiraNumber = testResult.getMethod().getDescription();

					//String issueSummary = getIssueTitle(jiraNumber, "Verify the Error message when user search with empty search box");

					JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

					for (Object searchString : listOfSearchData) {

						JSONObject jsonObjectChild = (JSONObject) searchString;

						String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
								"searchKeyword", extentLogger);
						
						String issueSummary = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
								"Summary", extentLogger);

					//homepage.openHomepage();
					searchpage = homepage.OpenSearchPage();
					searchpage.enterTextInSearchField(searchword);
					

					searchResultsPage = searchpage.clickOnSearch();
					boolean isDisplayed = searchResultsPage.isNoDocumentErrorDisplayed();					
					
					
				     softas.assertTrue(isDisplayed,issueSummary+":"+jiraNumber);
					logExtentStatus(extentLogger, isDisplayed, ":verifyNoDocumentsErrorMsg", jiraNumber);
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
