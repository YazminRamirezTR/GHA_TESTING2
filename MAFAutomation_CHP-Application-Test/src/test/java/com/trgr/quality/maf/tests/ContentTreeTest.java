package com.trgr.quality.maf.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
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
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.JurisprudencePage;
import com.trgr.quality.maf.pages.LegislationPage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class ContentTreeTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	ContenttreeOnsearchResultPage contenttreeOnsearchResultPage;
	DocumentDisplayPage documentPage;
	JsonReader jsonReader;

	public ITestResult testResult;
	JiraConnector jiraConnect;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		try {
			loginpage = new LoginPage(driver, ProductUrl);

			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");

			jsonReader = new JsonReader();
			homepage = loginpage.Login(username, password);

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Content Tree", "StartContentTreeTest");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the ContentTreeTest are not run.<br>"
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

	/*
	 * Document View_TC_002 : Verify the search result Content Tree view
	 */
	@Test(priority = 1, groups = {"chparg", "chpmex", "chpbr","chpury","chppe","chpchile" },description="MAFQABANG-388")
	public void ContentTreeDisplay() throws Exception {
		SoftAssert softAssert = new SoftAssert(); HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Validate Content Tree ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String searchFreeWord = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);

				homepage = homepage.openHomepage();
				homepage.clickClear();
				homepage.clickRefreshforThematicSearch();
				Thread.sleep(1000);
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(searchFreeWord);
				}
				else {
				homepage.enterFreewordOnQuickSearch(searchFreeWord);
				}
				searchResultsPage = homepage.clickSearch();

				boolean searchResultsDisplayed = searchResultsPage!=null 
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if(!searchResultsDisplayed){
					boolean noResultsFound = 
							homepage.errorBlockDisplayed() || 
							homepage.noSearchResultsDisplayed();
					if(noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " Search resulted in no search results",freewordkey, searchFreeWord, jiraNumber);
					else{
						logExtentStatus(extentLogger, noResultsFound, "Search resulted in error", freewordkey, searchFreeWord, jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber+":Search failed :"+searchFreeWord);
					}

					continue; //Skip this & Continue with next iteration
				}
				// Document View_TC_002 : Verify the search result Content Tree view
				boolean treeLinkDisplayed = false;
				boolean documentview = false;
				treeLinkDisplayed = searchResultsPage.isTableOfContentsLinkDisplayed();

				softAssert.assertTrue(treeLinkDisplayed,jiraNumber+":"+issueSummary +": Tree of contents link Displayed");
				logExtentStatus(extentLogger, treeLinkDisplayed,"Tree of contents link displayed",  jiraNumber);

				contenttreeOnsearchResultPage = searchResultsPage.clickTableOfContentsLink();
				documentview = treeLinkDisplayed && (contenttreeOnsearchResultPage != null)
						&& contenttreeOnsearchResultPage.isTreeContentViewDisplayed();
				softAssert.assertTrue(documentview,jiraNumber+":"+issueSummary +": Tree of Content view"+"<br> Search Key -'" + searchFreeWord + "'");
				logExtentStatus(extentLogger, documentview, "Tree of Content view", freewordkey, searchFreeWord, jiraNumber);

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
	 * Document View_TC_003 : Document View_Content Tree_Also show 2nd level
	 */
	@Test(priority = 2, groups = {"chparg", "chpmex", "chpbr","chpury","chppe","chpchile" },description="MAFQABANG-391")
	public void showSecondLevelContentTree() throws Exception {
		SoftAssert softAssert = new SoftAssert(); HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();	
			extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, 
					"Validate Content Tree & Expand Second Level");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String searchFreeWord = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);

				homepage.openHomepage();
				Thread.sleep(3000);
				homepage.clickClear();
				homepage.clickRefreshforThematicSearch();
				homepage.enterFreewordOnQuickSearch(searchFreeWord);
				searchResultsPage = homepage.clickSearch();

				boolean searchResultsDisplayed = searchResultsPage!=null 
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if(!searchResultsDisplayed){
					boolean noResultsFound = 
							homepage.errorBlockDisplayed() || 
							homepage.noSearchResultsDisplayed();
					if(noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, issueSummary,freewordkey, searchFreeWord +" -resulted in no search results", jiraNumber);
					else{
						logExtentStatus(extentLogger, noResultsFound, "Search resulted in error", freewordkey, searchFreeWord, jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber+":Search failed :"+searchFreeWord);
					}

					continue; //Skip this & Continue with next iteration
				}

				boolean treeLinkDisplayed = searchResultsPage.isTableOfContentsLinkDisplayed();
				contenttreeOnsearchResultPage = searchResultsPage.clickTableOfContentsLink();
				boolean documentview = treeLinkDisplayed && (contenttreeOnsearchResultPage != null)
						&& contenttreeOnsearchResultPage.isTreeContentViewDisplayed();

				// Document View_TC_003 : Document View_Content Tree_Also show 2nd level
				Thread.sleep(3000);
				if(documentview)
					contenttreeOnsearchResultPage.ClickOnExpandLevel2Link();
				Thread.sleep(3000);
				boolean contentTreeExpanded = documentview && 
						contenttreeOnsearchResultPage.isContentTreeExpanded();
				softAssert.assertTrue(contentTreeExpanded,jiraNumber+":"+issueSummary +"Search Key -'" + searchFreeWord + "'");
				logExtentStatus(extentLogger, contentTreeExpanded,issueSummary, freewordkey, searchFreeWord, jiraNumber);

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
	 * Document View_TC_004 : Document View_List of the documents
	 */
	@Test(priority = 3, groups = {"chparg", "chpmex", "chpbr","chpury","chppe","chpchile"},description="MAFQABANG-392")
	public void showListOfDocumentFrmContentTree() throws Exception {
		SoftAssert softAssert = new SoftAssert(); HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();	
			extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, 
					"Navigate to List of Documents from Content Tree");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "freeword";
				String searchFreeWord = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,key,extentLogger);

				homepage.openHomepage();
				homepage.clickClear();
				homepage.clickRefreshforThematicSearch();
				homepage.enterFreewordOnQuickSearch(searchFreeWord);
				searchResultsPage = homepage.clickSearch();

				boolean searchResultsDisplayed = searchResultsPage!=null 
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if(!searchResultsDisplayed){
					boolean noResultsFound = 
							homepage.errorBlockDisplayed() || 
							homepage.noSearchResultsDisplayed();
					if(noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",key, searchFreeWord,jiraNumber);
					else{
						logExtentStatus(extentLogger, noResultsFound, "Search resulted in error", key, searchFreeWord, jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber+":Search failed :"+searchFreeWord);
					}

					continue; //Skip this & Continue with next iteration
				}

				boolean treeLinkDisplayed = searchResultsPage.isTableOfContentsLinkDisplayed();
				contenttreeOnsearchResultPage = searchResultsPage.clickTableOfContentsLink();
				boolean documentview = treeLinkDisplayed && (contenttreeOnsearchResultPage != null)
						&& contenttreeOnsearchResultPage.isTreeContentViewDisplayed();

				if(documentview)
					contenttreeOnsearchResultPage.ClickOnExpandLevel2Link();

				// Document View_TC_004 : Document View_List of the documents
				boolean listLinkDisplayed = false;
				boolean documentListDisplayed = false;
				listLinkDisplayed = contenttreeOnsearchResultPage.isListOfDocumentsLinkDisplayed();
				softAssert.assertTrue(listLinkDisplayed,jiraNumber+":"+issueSummary +":List Of Documents link"+"<br> Search Key -'" + searchFreeWord + "'");
				logExtentStatus(extentLogger, listLinkDisplayed, "List Of Documents link", key, searchFreeWord, jiraNumber);

				searchResultsPage = contenttreeOnsearchResultPage.clickListOfDocumentsLink();
				documentListDisplayed = listLinkDisplayed
						&& (searchResultsPage != null)
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
					

				softAssert.assertTrue(documentListDisplayed,jiraNumber+":"+issueSummary +": Navigate to List Of Documents view"+"<br> Search Key -'" + searchFreeWord + "'");
				logExtentStatus(extentLogger, documentListDisplayed, issueSummary, key, searchFreeWord, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	//MAFAUTO-147
	@Test(priority = 4, groups = {"chpmex"},description="MAFAUTO-147")
	public void ContentTreeValidations() throws Exception {
		SoftAssert softAssert = new SoftAssert(); HomePage homepage = this.homepage;
		testResult = Reporter.getCurrentTestResult();	
		extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, 
				"Global Search - Result List - Content Tree");

		try {
			homepage = homepage.openHomepage();

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				// Read data
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordGlobal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"Freeword",extentLogger); 
				String contentArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"treeitems",extentLogger).split(",");
				searchpage = homepage.OpenSearchPage();
				searchpage.enterTextInSearchField(freewordGlobal);
				searchResultsPage = searchpage.clickOnSearch();

				// Validate search results page
				boolean searchResultsListDisplayed = searchResultsPage
						.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(freewordGlobal);

				softAssert.assertTrue(searchResultsListDisplayed,
						jiraNumber+" : Search for entire content (List View) from Search Page");
				logExtentStatus(extentLogger, searchResultsListDisplayed, issueSummary+"<br>Search for entire content (List View) from Search Page", jiraNumber);

				// Navigate to tree view page & Expand second level
				ContenttreeOnsearchResultPage docViewPage = searchResultsPage.clickTableOfContentsLink();
				if (searchpage.isAreaFoundInContentTree(contentArea)) {
					searchpage.selectAreaFromContentTree(contentArea);
				}
				docViewPage.ClickOnExpandLevel2Link();
				// Expand till document in the sub tree
				WebElement treeElement = docViewPage.getFirstLevelContentTree(treeItems[0]);
				searchpage.isContentTreeElementExpanded(treeElement);

				//String treeTraversedItems = treeItems[0];
				for (int row = 1; row < treeItems.length; row++) {
					if (docViewPage.isItemPresentInSubContentTree(treeElement, treeItems[row])) {
						docViewPage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = docViewPage.getSubContentTreeElement(treeElement, treeItems[row]);
						//treeTraversedItems += ">><br>" + treeItems[row];
					}
				}
				// Validate the visibility of document in the tree
				boolean docVisibleInTree = docViewPage.isAnyDocumentVisibileInSubContentTree(treeElement);

				softAssert.assertTrue(docVisibleInTree,jiraNumber+" : Navigate to tree view & expand till document");
				logExtentStatus(extentLogger, docVisibleInTree, "Navigate to tree view & expand till document", jiraNumber);

				// Validate collapse to level 1
				docViewPage.ClickOnCollapseLevel1Link();
				treeElement = docViewPage.getFirstLevelContentTree(treeItems[0]);
				boolean treeCollapsed = docViewPage.isContentTreeElementCollapsed(treeElement);
				logExtentStatus(extentLogger, treeCollapsed, "Collapse content tree view to level one", jiraNumber);
				softAssert.assertTrue(treeCollapsed, jiraNumber+" : Collapse tree view to level one");

				// Navigate back to List View
				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-250)", "");
				boolean listOfDocLinkPresent = docViewPage.isListOfDocumentsLinkDisplayed();
			
				if (listOfDocLinkPresent) {
					searchResultsPage = docViewPage.clickListOfDocumentsLink();
				}
				boolean navigatedtoListView = listOfDocLinkPresent && (searchResultsPage != null)
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(freewordGlobal);

				softAssert.assertTrue(navigatedtoListView,
						jiraNumber+" : Navigate to List View (SearchResultsPage) from tree content view");
				logExtentStatus(extentLogger, navigatedtoListView, "Navigate to List View (SearchResultsPage) from tree content view", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	//MAFAUTO-105
	@Test(priority = 5, groups = {"chpmex"},description="MAFAUTO-105")
	public void ResultListTocectoryShow() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		SoftAssert softas = new SoftAssert(); HomePage homepage = this.homepage;	
		extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, 
				"Result List - Show Tocectory ");

		try {
			homepage = homepage.openHomepage();

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				// MAFAUTO-105 : Freeword search - traverse through tree document
				String freewordGlobal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"freeword",extentLogger); 
				String contentArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"treeitems",extentLogger).split(",");
				searchpage = homepage.OpenSearchPage();
				searchpage.enterTextInSearchField(freewordGlobal);
				searchResultsPage = searchpage.clickOnSearch();

				// Validate search results page
				boolean searchResultsListDisplayed = searchResultsPage
						.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(freewordGlobal);

				softas.assertTrue(searchResultsListDisplayed,
						"MAFAUTO-105 : Search from Search Page :" + freewordGlobal);
				logExtentStatus(extentLogger, searchResultsListDisplayed, issueSummary+" : Search results displayed", jiraNumber);

				// Navigate to tree view page & Expand second level
				ContenttreeOnsearchResultPage docViewPage = searchResultsPage.clickTableOfContentsLink();
				if (searchpage.isAreaFoundInContentTree(contentArea)) {
					searchpage.selectAreaFromContentTree(contentArea);
				}
				// Expand till document in the sub tree
				docViewPage.clickExpandItemInContentTree(treeItems[0]);
				WebElement treeElement = docViewPage.getFirstLevelContentTree(treeItems[0]);

				//String treeTraversedItems = treeItems[0];
				for (int row = 1; row < treeItems.length; row++) {
					if (docViewPage.isItemPresentInSubContentTree(treeElement, treeItems[row])) {
						docViewPage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = docViewPage.getSubContentTreeElement(treeElement, treeItems[row]);
						//treeTraversedItems += ">><br>" + treeItems[row];
					}
				}
				// Validate the visibility of document in the tree
				boolean docVisibleInTree = docViewPage.isAnyDocumentVisibileInSubContentTree(treeElement);
				softas.assertTrue(docVisibleInTree,
						jiraNumber+": Navigate to tree view & expand till document");
				logExtentStatus(extentLogger, docVisibleInTree, issueSummary+": Navigate to tree view & expand till document", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	//MAFAUTO-106, MAFAUTO-122,MAFAUTO-152
	@Test(priority = 6, groups = {"chpmex"},description="MAFAUTO-106")
	public void LegislationContentTreeNavigation() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		SoftAssert softas = new SoftAssert(); HomePage homepage = this.homepage;	
		extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		//String issueSummary = getIssueTitle(jiraNumber, "Legislation - Result List - Content Tree ");

		try {
			homepage = homepage.openHomepage();

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				// MAFAUTO-106 : Content tree validation for Legislation
				// Read data
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordSearchText = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"freeword",extentLogger); 
				String contentArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String contentTreeNodeName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"nodename",extentLogger);

				// Perform Search
				searchpage = homepage.OpenSearchPage();
				LegislationPage legislationpage = searchpage.OpenLegislationPage();
				Thread.sleep(2000);
				searchpage.enterFreeWordOnSearchPage(freewordSearchText);
				Thread.sleep(2000);
				searchResultsPage = legislationpage.clickOnSearch();
				
				// Open Content Tree view
				searchResultsPage.ClickDocumentViewLink();
			//	searchpage.selectGivenValueFromThematicDropdown(contentArea);
				if (searchpage.isAreaFoundInContentTree(contentArea)) {
					searchpage.selectAreaFromContentTree(contentArea);
				}
				Thread.sleep(2000);
				// Expand second level
				searchpage.ClickOnExpandLevel2Link();
				Thread.sleep(2000);
				WebElement treeElement = searchpage.getFirstLevelContentTree(contentTreeNodeName);
				boolean treeValidated = (treeElement != null)
						&& searchpage.isContentTreeElementExpanded(treeElement);

				softas.assertTrue(treeValidated,
						jiraNumber+":Content tree navigation & Expand Level 2 link validated for legislation : "
								+ contentTreeNodeName);
				logExtentStatus(extentLogger, treeValidated, "Content tree navigation & Expand Level 2 link validated for legislation"+contentTreeNodeName, new String[]{jiraNumber,"MAFAUTO-122","MAFAUTO-152"});

				// Collapse first level
				Thread.sleep(2000);
				searchpage.ClickOnCollapseLevel1Link();
				treeElement = searchpage.getFirstLevelContentTree(contentTreeNodeName);
				treeValidated = (treeElement != null)
						&& searchpage.isContentTreeElementCollapsed(treeElement);
				softas.assertTrue(treeValidated,
						jiraNumber+" : Content tree navigation & Collapse Level 1 link validated for legislation : "
								+ contentTreeNodeName);
				logExtentStatus(extentLogger, treeValidated, "Content tree navigation & Collapse Level 1 link validated for legislation"+contentTreeNodeName, new String[]{jiraNumber,"MAFAUTO-122","MAFAUTO-152"});

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	//MAFAUTO-101
	@Test(priority = 7, groups = { "chpmex" },description="MAFAUTO-101")
	public void resultListTreeForThematicSearchCheckVoces() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		SoftAssert softas = new SoftAssert(); HomePage homepage = this.homepage;	
		extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, 
				"Thematic Search - Resultlist Tree -  and Voces");

		try {
			homepage = homepage.openHomepage();

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String vocessearchKey="firstthematicsuggestion";
				String vocessearch = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,vocessearchKey,extentLogger); 
				String vocessearchKey2="secondthematicsuggestion";
				String vocessearch2 = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,vocessearchKey2,extentLogger); 
				String vocessearchKey3="thirdthematicsuggestion";
				String vocessearch3 = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,vocessearchKey3,extentLogger); 
				String ResultSearch="resultsearch";
				String ResultsSearchPage = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,ResultSearch,extentLogger); 
				String articleresultserach="articleresultserach";
				String articleresultserachLink = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,articleresultserach,extentLogger); 
				searchpage = homepage.OpenSearchPage();
				searchpage.selectGivenValueFromThematicDropdown("Todas");
				searchpage.enterTextInSearchField(vocessearch);
				boolean suggestionsDisplayed = searchpage.isTheSuggestionsDropdownDisplayed();

				softas.assertTrue(suggestionsDisplayed, "Suggestions dropdown displayed");
				logExtentStatus(extentLogger, suggestionsDisplayed, "Suggestions dropdown displayed for fisrt thematic search", jiraNumber);
				searchpage.clickClearonSearchPage();
				Thread.sleep(2000);
				boolean clearsearch=searchpage.isSearchPageFilledWithData();
				if(clearsearch) {
					searchpage.clickClearonSearchPage();
				}
				Thread.sleep(3000);
				searchpage.enterTextInSearchField(vocessearch2);
				boolean suggestionsDisplayedforSecondThematic = searchpage.isTheSuggestionsDropdownDisplayed();
				softas.assertTrue(suggestionsDisplayedforSecondThematic, "Suggestions dropdown displayed");
				logExtentStatus(extentLogger, suggestionsDisplayedforSecondThematic, "Suggestions dropdown displayed for second thematic search", jiraNumber);
				searchpage.clickClearonSearchPage();
				Thread.sleep(2000);
				 clearsearch=searchpage.isSearchPageFilledWithData();
				if(clearsearch) {
					searchpage.clickClearonSearchPage();
				}
				Thread.sleep(4000);
				searchpage.enterTextInSearchField(vocessearch3);
				boolean suggestionsDisplayedforthirdThematic = searchpage.isTheSuggestionsDropdownDisplayed();
				softas.assertTrue(suggestionsDisplayedforthirdThematic, "Suggestions dropdown displayed ");
				logExtentStatus(extentLogger, suggestionsDisplayedforthirdThematic, "Suggestions dropdown displayed for third thematic search", jiraNumber);
				searchpage.clickClearonSearchPage();
				Thread.sleep(2000);
			 clearsearch=searchpage.isSearchPageFilledWithData();
				if(clearsearch) {
					searchpage.clickClearonSearchPage();
				}
				searchpage.enterTextInSearchField(vocessearch);
				Thread.sleep(2000);
				searchpage.ScrollToGivenSearchString(vocessearch);
				searchResultsPage = searchpage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed();
				softas.assertTrue(searchResultsDisplayed,
						"MAFQABANG-49 : Using thematic search from search page");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Using thematic search from search page", jiraNumber);
				searchResultsPage.clickVertodosLinkSearchResultsPage();

				boolean searchResultsDisplayedforvocesearch = searchResultsPage.compareTitleArticlePresentResultsPageExpected(ResultsSearchPage);
				softas.assertTrue(searchResultsDisplayedforvocesearch,
						"MAFQABANG-49 : Using thematic search from search page");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Results Search Displayed ", jiraNumber);
			   
			//	searchResultsPage.clickFirstLink();
				searchResultsPage.ClickonGivenLinkTextonArticleSearchResultsPage(articleresultserachLink);
				documentPage = new DocumentDisplayPage(driver);
             Thread.sleep(3000);
				documentPage.clickonVoces();

				boolean validateVoces = documentPage.isVocesRelatedSectionDisplayed();
				/*validateVoces = validateVoces
						&& documentPage.isGivenSearchTextOnTheVocesSection(vocessearch);
*/
				softas.assertTrue(validateVoces, issueSummary);
				logExtentStatus(extentLogger, validateVoces, issueSummary,vocessearchKey,vocessearch, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	//MAFAUTO-110
	@Test(priority = 8, groups = { "chpmex" },description="MAFAUTO-110")
	public void JurisprudenceContentTreeNavigation() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		SoftAssert softas = new SoftAssert(); HomePage homepage = this.homepage;	
		extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		//String issueSummary = getIssueTitle(jiraNumber,"Jurisprudence - Result List - Content Tree ");

		try {
			homepage = homepage.openHomepage();

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordSearchText = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"freeword",extentLogger); 
				String contentArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String contentTreeNodeName = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"nodename",extentLogger);

				// Perform Search
				searchpage = homepage.OpenSearchPage();
				JurisprudencePage jurisprudence = searchpage.openJurisprudencePage();
				searchpage.enterFreeWordOnSearchPage(freewordSearchText);
				searchResultsPage = jurisprudence.clickOnSearch();
				// Open Content Tree view
				searchResultsPage.ClickDocumentViewLink();
				/*if (searchpage.isAreaFoundInContentTree(contentArea)) {
					searchpage.selectAreaFromContentTree(contentArea); 
				}*/
				// Expand second level
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,250)", "");
				Thread.sleep(3000);
				searchpage.ClickOnExpandLevel2Link();
				Thread.sleep(3000);
				WebElement treeElement = searchpage.getFirstLevelContentTree(contentTreeNodeName);
				boolean treeValidated = (treeElement != null)
						&& searchpage.isContentTreeElementExpanded(treeElement);

				softas.assertTrue(treeValidated,
						jiraNumber+" : Content tree navigation & Expand Level 2 link validated for :" + contentTreeNodeName);
				logExtentStatus(extentLogger, treeValidated, "Content tree navigation & Expand Level 2 link validated for : <br>" +contentTreeNodeName, jiraNumber);

				// Collapse first level
				searchpage.ClickOnCollapseLevel1Link();
				Thread.sleep(3000);
				treeElement = searchpage.getFirstLevelContentTree(contentTreeNodeName);
				treeValidated = (treeElement != null)
						&& searchpage.isContentTreeElementCollapsed(treeElement);
				softas.assertTrue(treeValidated,
						jiraNumber+" : Content tree navigation & Collapse Level 1 link validated: "
								+ contentTreeNodeName);
				logExtentStatus(extentLogger, treeValidated, "Content tree navigation & Collapse Level 1 link validated for :<br>" +contentTreeNodeName, jiraNumber);

				// Scroll up to Menu view
				jse.executeScript("window.scrollBy(0,-250)", "");

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// MAFAUTO-234 
	@Test(priority = 9, groups = {"chpmex"},description="MAFAUTO-234")
	public void ValidatingContentTreeAndFinalValidatyDate() throws Exception {
		SoftAssert softas = new SoftAssert(); HomePage homepage = this.homepage;	
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Validate Content Tree & Final Validity Date");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"documenttitle",extentLogger); 
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"treeitems",extentLogger).split(",");
				String dateval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"date",extentLogger); 

				homepage = homepage.openHomepage();
				SearchPage searchPage = homepage.OpenSearchPage();

				//Select Thematic Area & Click on area in tree(if it is not already selected)
				searchPage.selectGivenValueFromThematicDropdown(thematicArea);
				if (searchPage.isAreaFoundInContentTree(thematicArea)) {
					searchPage.selectAreaFromContentTree(thematicArea);
				}

				//Expand First level item in the tree structure
				WebElement treeElement = searchPage.getFirstLevelContentTree(treeItems[0]);
				searchPage.expandContentTreeElement(treeElement);
				//String treeTraversed = treeItems[0];
				//Expand till document in the sub tree
				for(int row=1; row<treeItems.length ;row++){
					if(searchPage.isItemPresentInSubContentTree(treeElement,treeItems[row])){
						searchPage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = searchPage.getSubContentTreeElement(treeElement, treeItems[row]);
					}
				}

				//Open document from the tree
				boolean docDisplayedInTree = searchPage.isItemPresentInSubContentTree(treeElement,documentTitle);
				documentPage = searchPage.clickDocumentInSubContentTree(treeElement,documentTitle);

				softas.assertTrue(docDisplayedInTree,jiraNumber+" : "+issueSummary+" : Tree Content");
				logExtentStatus(extentLogger, documentPage!=null, issueSummary+" : Document Display", jiraNumber);

				softas.assertTrue(documentPage!=null,jiraNumber+" : "+issueSummary+" : Document Display");
				logExtentStatus(extentLogger, documentPage!=null, issueSummary+" : Document Display<br>Doc Title: "+documentTitle, jiraNumber);

				//Validating Final validity Date
				boolean isfinalvaliditydatedisplayed = documentPage.isFinalDatePresentResolution(dateval);
				softas.assertTrue(isfinalvaliditydatedisplayed,jiraNumber+" : "+issueSummary+" : Date displayed");
				logExtentStatus(extentLogger, isfinalvaliditydatedisplayed, issueSummary+" : Date displayed", jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// MAFAUTO-236
	@Test(priority = 10, groups = {"chpmex"},description="MAFAUTO-236")
	public void validatingContentTreeAndIssuingBody() throws Exception {

		SoftAssert softas = new SoftAssert(); HomePage homepage = this.homepage;	

		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, 
					"Validate Content Tree & Issuing Body ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"documenttitle",extentLogger); 
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"treeitems",extentLogger).split(",");
				String OrrEs = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"OrrEs",extentLogger); 

				homepage = homepage.openHomepage();
				SearchPage searchPage = homepage.OpenSearchPage();

				//Select Thematic Area & Click on area in tree(if it is not already selected)
				searchPage.selectGivenValueFromThematicDropdown(thematicArea);
				if (searchPage.isAreaFoundInContentTree(thematicArea)) {
					searchPage.selectAreaFromContentTree(thematicArea);
				}

				//Expand First level item in the tree structure
				WebElement treeElement = searchPage.getFirstLevelContentTree(treeItems[0]);
				searchPage.expandContentTreeElement(treeElement);
				String treeTraversed = treeItems[0];
				//Expand till document in the sub tree
				for(int row=1; row<treeItems.length ;row++){
					if(searchPage.isItemPresentInSubContentTree(treeElement,treeItems[row])){
						searchPage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = searchPage.getSubContentTreeElement(treeElement, treeItems[row]);
						treeTraversed = treeTraversed + ">><br>"+treeItems[row];
					}
				}

				//Open document from the tree
				boolean docDisplayedInTree = searchPage.isItemPresentInSubContentTree(treeElement,documentTitle);
				if(docDisplayedInTree){
					treeTraversed = treeTraversed + ">><br>"+ documentTitle;
					documentPage = searchPage.clickDocumentInSubContentTree(treeElement,documentTitle);
				}

				softas.assertTrue(docDisplayedInTree,jiraNumber+" : "+issueSummary+" : Tree Content");
				logExtentStatus(extentLogger, docDisplayedInTree, issueSummary+" : Tree Content", jiraNumber);

				softas.assertTrue(documentPage!=null,jiraNumber+" : "+issueSummary+" : Document Display");
				logExtentStatus(extentLogger, documentPage!=null, issueSummary+" : Document Display", jiraNumber);

				//validating Issuing Body
				boolean issuingbodydisplayed = documentPage.isVerifyIsuuingBodyText(OrrEs);
				softas.assertTrue(issuingbodydisplayed,jiraNumber+" : "+issueSummary+" : Issuing Body");
				logExtentStatus(extentLogger, issuingbodydisplayed, issueSummary+" : Issuing Body", jiraNumber);

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
	 * MAFAUTO-108 : Verify Legislation - tree - Document Display - Show Content Tree 
	 */
	@Test(priority=11, groups = { "chpmex" }, description = "MAFAUTO-108")
	public void LegislationTreeAndDocumentDisplay() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		SoftAssert softas = new SoftAssert(); HomePage homepage = this.homepage;
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, 
				"Legislation - Document Display - Show Content Tree");
		JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
		
		try {

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey="thematicarea";
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,thematicareaKey,extentLogger);

				String treeitemsKey="treeitems";
				String  treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,treeitemsKey,extentLogger).split(",");

				String documenttitleKey="documenttitle";
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,documenttitleKey,extentLogger);

				searchpage = homepage.OpenSearchPage();

				// Select Thematic Area & Click on area in tree(if it is not
				// already
				// selected)
				searchpage.selectGivenValueFromThematicDropdown(thematicArea);
				if (searchpage.isAreaFoundInContentTree(thematicArea)) {
					searchpage.selectAreaFromContentTree(thematicArea);
				}
				Thread.sleep(5000);
				// Expand First level item in the tree structure
				WebElement treeElement = searchpage.getFirstLevelContentTree(treeItems[0]);
				searchpage.expandContentTreeElement(treeElement);
				// Expand till document in the sub tree
				for (int row = 1; row < treeItems.length; row++) {
					if (searchpage.isItemPresentInSubContentTree(treeElement, treeItems[row])) {
						searchpage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = searchpage.getSubContentTreeElement(treeElement, treeItems[row]);
					}
				}
				// Open document from the tree
				boolean docDisplayedInTree = searchpage.isItemPresentInSubContentTree(treeElement, documentTitle);
				softas.assertTrue(docDisplayedInTree, "Document found in tree view");
				logExtentStatus(extentLogger, docDisplayedInTree, "Document found in tree view", jiraNumber);
				if (docDisplayedInTree)
					documentPage = searchpage.clickDocumentInSubContentTree(treeElement, documentTitle);
				
				docDisplayedInTree &=  (documentPage != null);
				softas.assertTrue(docDisplayedInTree,jiraNumber + ":Fetching Document from tree view");
				logExtentStatus(extentLogger, docDisplayedInTree,"Fetching Document from tree view",
						documenttitleKey,documentTitle,jiraNumber);
				
				documentPage.clickContentTreeTab();
				boolean treeContentValidated = documentPage.isContentTreeStructureDisplayed()
						&& documentPage.isContentTreeEqualsNodes(thematicArea, treeItems);
				softas.assertTrue(treeContentValidated,jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, treeContentValidated, issueSummary,jiraNumber);
				
				if (!treeContentValidated && documentPage.isContentTreeInfoNotAvailable()) {
					softas.assertTrue(true, jiraNumber + 
							":Info message displayed - Content tree information is not available for the selected document");
					logExtentStatus(extentLogger, true,
							"Info message displayed - Content tree information is not available for the selected document",
							jiraNumber);
				}

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, jiraNumber+": Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}
	
	@Test(priority = 12, groups = {"chpmex"},description="MAFAUTO-254")
	public void ValidatingDocumentContentfromContentTree() throws Exception {
		SoftAssert softas = new SoftAssert(); HomePage homepage = this.homepage;	
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Indicators - Legislation - Global Search (Result List / Delivery) and TOC Search (Content Tree)");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"documenttitle",extentLogger); 
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"treeitems",extentLogger).split(",");
				
				homepage = homepage.openHomepage();
				SearchPage searchPage = homepage.OpenSearchPage();

				//Select Thematic Area & Click on area in tree(if it is not already selected)
				searchPage.selectGivenValueFromThematicDropdown(thematicArea);
				if (searchPage.isAreaFoundInContentTree(thematicArea)) {
					searchPage.selectAreaFromContentTree(thematicArea);
				}

				//Expand First level item in the tree structure
				WebElement treeElement = searchPage.getFirstLevelContentTree(treeItems[0]);
				searchPage.expandContentTreeElement(treeElement);
				//String treeTraversed = treeItems[0];
				//Expand till document in the sub tree
				for(int row=1; row<treeItems.length ;row++){
					if(searchPage.isItemPresentInSubContentTree(treeElement,treeItems[row])){
						searchPage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = searchPage.getSubContentTreeElement(treeElement, treeItems[row]);
					}
				}
				
				searchPage.enterTextInSearchField(documentTitle);
				searchResultsPage = searchPage.clickOnSearch();
				
			//	boolean expectedDocOnResultList = searchResultsPage.getFirstDocumentTitle().contains(documentTitle);
				boolean expectedDocOnResultList = searchResultsPage.getExpectedTitleSearchResultsPage().contains(documentTitle);			

				softas.assertTrue(expectedDocOnResultList,jiraNumber + "Expected document is displayed on the result list");
				logExtentStatus(extentLogger, expectedDocOnResultList, issueSummary, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	
	@Test(priority = 12, groups = {"chpmex"},description="MAFAUTO-202")
	public void automaticChunk() throws Exception {
		SoftAssert softas = new SoftAssert(); HomePage homepage = this.homepage;	
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Verify document display with automatic chunking when document is opened from content tree");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"documenttitle",extentLogger); 
				String contentArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"treeitems",extentLogger).split(",");
				
				homepage = homepage.openHomepage();
				SearchPage searchPage = homepage.OpenSearchPage();
				//Select Thematic Area & Click on area in tree(if it is not already selected)
				searchPage.selectGivenValueFromThematicDropdown(contentArea);
				if (searchPage.isAreaFoundInContentTree(contentArea)) {
					searchPage.selectAreaFromContentTree(contentArea);
				}
				//Expand First level item in the tree structure
				WebElement treeElement = searchPage.getFirstLevelContentTree(treeItems[0]);
				searchPage.expandContentTreeElement(treeElement);
				//String treeTraversed = treeItems[0];
				//Expand till document in the sub tree
				for(int row=1; row<treeItems.length ;row++){
					if(searchPage.isItemPresentInSubContentTree(treeElement,treeItems[row])){
						searchPage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = searchPage.getSubContentTreeElement(treeElement, treeItems[row]);
					}
				}

				//Open document from the tree
				//boolean docDisplayedInTree = searchPage.isItemPresentInSubContentTree(treeElement,documentTitle);
				documentPage = searchPage.clickDocumentInSubContentTree(treeElement,documentTitle);
				
			   boolean isDocumentDisplayed = documentPage.getDocumentDisplayTitleLineOne().contains(documentTitle);

				softas.assertTrue(isDocumentDisplayed,jiraNumber + issueSummary);
				logExtentStatus(extentLogger, isDocumentDisplayed, issueSummary, jiraNumber);

				//verifying the content tree header
				documentPage.scrollOnpageByGivenPixels(0, 250);
				boolean isContentLoaded = documentPage.isDocumentContentDisplayed();
				softas.assertTrue(isContentLoaded,jiraNumber + "Document content is loaded upon scrolling");
				logExtentStatus(extentLogger, isContentLoaded, "Document content is loaded upon scrolling", jiraNumber);
				
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
	 * Document View_TC_004 : Document View_List of the documents
	 */
	@Test(priority = 3, groups = {"chpmex"},description="MAFQABANG-392")
	public void showListOfDocumentFrmContentTreeInMex() throws Exception {
		SoftAssert softAssert = new SoftAssert(); HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();	
			extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, 
					"Navigate to List of Documents from Content Tree");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "freeword";
				String searchFreeWord = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,key,extentLogger);

				homepage.openHomepage();
				homepage.clickClear();
				homepage.clickRefreshforThematicSearch();
				homepage.enterTextInSearchField(searchFreeWord);
				searchResultsPage = homepage.clickSearch();

				boolean searchResultsDisplayed = searchResultsPage!=null 
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if(!searchResultsDisplayed){
					boolean noResultsFound = 
							homepage.errorBlockDisplayed() || 
							homepage.noSearchResultsDisplayed();
					if(noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",key, searchFreeWord,jiraNumber);
					else{
						logExtentStatus(extentLogger, noResultsFound, "Search resulted in error", key, searchFreeWord, jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber+":Search failed :"+searchFreeWord);
					}

					continue; //Skip this & Continue with next iteration
				}

				boolean treeLinkDisplayed = searchResultsPage.isTableOfContentsLinkDisplayed();
				contenttreeOnsearchResultPage = searchResultsPage.clickTableOfContentsLink();
				boolean documentview = treeLinkDisplayed && (contenttreeOnsearchResultPage != null)
						&& contenttreeOnsearchResultPage.isTreeContentViewDisplayed();

				if(documentview)
					contenttreeOnsearchResultPage.ClickOnExpandLevel2Link();

				// Document View_TC_004 : Document View_List of the documents
				boolean listLinkDisplayed = false;
				boolean documentListDisplayed = false;
				listLinkDisplayed = contenttreeOnsearchResultPage.isListOfDocumentsLinkDisplayed();
				softAssert.assertTrue(listLinkDisplayed,jiraNumber+":"+issueSummary +":List Of Documents link"+"<br> Search Key -'" + searchFreeWord + "'");
				logExtentStatus(extentLogger, listLinkDisplayed, "List Of Documents link", key, searchFreeWord, jiraNumber);

				searchResultsPage = contenttreeOnsearchResultPage.clickListOfDocumentsLink();
				documentListDisplayed = listLinkDisplayed
						&& (searchResultsPage != null)
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
					

				softAssert.assertTrue(documentListDisplayed,jiraNumber+":"+issueSummary +": Navigate to List Of Documents view"+"<br> Search Key -'" + searchFreeWord + "'");
				logExtentStatus(extentLogger, documentListDisplayed, issueSummary, key, searchFreeWord, jiraNumber);
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
	 * Document View_TC_003 : Document View_Content Tree_Also show 2nd level
	 */
	@Test(priority = 2, groups = {"chpmex"},description="MAFQABANG-391")
	public void showSecondLevelContentTreeInMex() throws Exception {
		SoftAssert softAssert = new SoftAssert(); HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();	
			extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, 
					"Validate Content Tree & Expand Second Level");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String searchFreeWord = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);

				homepage.openHomepage();
				homepage.clickClear();
			//	homepage.clickRefreshforThematicSearch();
				homepage.enterTextInSearchField(searchFreeWord);
				searchResultsPage = homepage.clickSearch();
				if(homepage.isVerifiedTestDataAsTestCase()) {
					homepage.openHomepage();
					homepage.clickClear();
				//	homepage.clickRefreshforThematicSearch();
					homepage.enterTextInSearchField(searchFreeWord);
					searchResultsPage = homepage.clickSearch();
				}
				boolean searchResultsDisplayed = searchResultsPage!=null 
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if(!searchResultsDisplayed){
					boolean noResultsFound = 
							homepage.errorBlockDisplayed() || 
							homepage.noSearchResultsDisplayed();
					if(noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, issueSummary,freewordkey, searchFreeWord +" -resulted in no search results", jiraNumber);
					else{
						logExtentStatus(extentLogger, noResultsFound, "Search resulted in error", freewordkey, searchFreeWord, jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber+":Search failed :"+searchFreeWord);
					}

					continue; //Skip this & Continue with next iteration
				}
             Thread.sleep(3000);
				boolean treeLinkDisplayed = searchResultsPage.isTableOfContentsLinkDisplayed();
				contenttreeOnsearchResultPage = searchResultsPage.clickTableOfContentsLink();
				Thread.sleep(3000);
				boolean documentview = treeLinkDisplayed && (contenttreeOnsearchResultPage != null)
						&& contenttreeOnsearchResultPage.isTreeContentViewDisplayed();
				Thread.sleep(3000);
				// Document View_TC_003 : Document View_Content Tree_Also show 2nd level
				if(documentview)
					contenttreeOnsearchResultPage.ClickOnExpandLevel2Link();
				Thread.sleep(3000);
				boolean contentTreeExpanded = documentview && 
						contenttreeOnsearchResultPage.isContentTreeExpanded();
				softAssert.assertTrue(contentTreeExpanded,jiraNumber+":"+issueSummary +"Search Key -'" + searchFreeWord + "'");
				logExtentStatus(extentLogger, contentTreeExpanded,issueSummary, freewordkey, searchFreeWord, jiraNumber);

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

