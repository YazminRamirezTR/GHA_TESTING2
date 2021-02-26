package com.trgr.quality.maf.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.commonutils.RandomUtils;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.AlertPage;
import com.trgr.quality.maf.pages.DeliveryPage;
import com.trgr.quality.maf.pages.DoctrinePage;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.FormsPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.JurisprudencePage;
import com.trgr.quality.maf.pages.LegislationPage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SaveAndSchedulePage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class DocumentDisplayTest extends BaseTest {

	/*
	 * DocDisplay_TC_001 - DocDisplay_Accessing Document Display Page
	 * DocDisplay_TC_002 - DocDisplay_Changing Document Font Size
	 * DocDisplay_TC_003 - DocDisplay_Result List Tab in Document Display
	 * DocDisplay_TC_004 - DocDisplay_Navigation links and Other Links in top
	 * bar of Document display page DocDisplay_TC_005 - DocDisplay_Save To My
	 * Documents from Document Display Page DocDisplay_TC_009 -
	 * DocDisplay_Informacion clave <key information> tab in Left Pane
	 * 
	 * DocDisplay_TC_023 - DocDisplay_Follow This document (Seguir este doc)
	 * [Note: Email sent / not sent scenarios are not considered for
	 * automation.] DocDisplay_TC_031 - DocDisplay_Follow This document (Seguir
	 * este doc) - UnFollow document [Note: Email sent / not sent scenarios are
	 * not considered for automation.] DocDisplay_TC_007 - DocDisplay_Save and
	 * Schedule Search DocDisplay_TC_028 - ResultList_Save and Schedule Search
	 * available in Result List page
	 * 
	 * DocDisplay_TC_030 - DocDisplay_Verify navigation through index feature in
	 * the document display : In progress & Blocked DocDisplay_TC_032 -
	 * DocDisplay_Verify clicking 'Terms of use' link and navigate back to
	 * document display DocDisplay_TC_010 - DocDisplay_Tree Content Tab (árbol
	 * de contenido) in Left Pane DocDisplay_TC_026 - ResultList_Search Results
	 * Widget in Results List page DocDisplay_TC_025 - ResultList_Second level
	 * Filter Facets in result List page
	 * 
	 * MAFAUTO-150 Common Result List Features - Result List - Pagination
	 * MAFAUTO-100 : Verify Follow Document Widget in Home page:- follow
	 * document in doc display, verify in widget, and delete from widget.
	 * MAFAUTO-126 : Verify Legislation - tree - Document Display - Legislation
	 * Versions MAFAUTO-108 : Verify Legislation - tree - Document Display -
	 * Show Content Tree MAFAUTO-229 : Legislation - Reform's Analysis for
	 * Federal Legislation
	 * 
	 */

	HomePage homepage;
	LoginPage loginpage;
	AlertPage alertpage;
	SearchPage searchpage;
	DoctrinePage doctrinePage;
	DeliveryPage deliverypage;
	SearchResultsPage searchresultspage;
	DocumentDisplayPage documentdisplaypage;
	SaveAndSchedulePage saveAndSchedulePage;
	LegislationPage legislationpage;
	JurisprudencePage jurisprudencePage;
	JsonReader jsonReader;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	

	@BeforeClass(alwaysRun = true)
	public void startDocumentDisplayTest() throws Exception {

		try {

			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");

			jsonReader= new JsonReader();
			homepage = loginpage.Login(username, password);
		} catch (Exception exc) {
			extentLogger = setUpExtentTest(extentLogger, "Document Display", "startDocumentDisplayTest");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the DocumentDisplay test are not run.<br>"
							+ "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
 
		}

	}

	@AfterClass(alwaysRun = true)
	public void endTest(){
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
		}
	}

	@Test(priority=0, groups = { "chparg", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-78")
	public void fontSizeChangesOnDocument() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Document Display Change Font Size");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordVal);
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				 
				searchresultspage = searchpage.clickonSearchwhenThematicisSelected();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordKey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();

				boolean isFontIncreased = documentdisplaypage.DocumentDispalyIncreaseFont();
				softas.assertTrue(isFontIncreased, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isFontIncreased, "Document display page enlarged the font",jiraNumber);
				


				if(!isFontIncreased){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-283");
				}
				
				boolean isFontDecreased = documentdisplaypage.DocumentDispalyDecreaseFont();
				softas.assertTrue(isFontDecreased, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isFontDecreased, "Document display page reduced the font",jiraNumber);

				softas.assertTrue(isFontIncreased && isFontDecreased, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isFontDecreased && isFontIncreased, issueSummary,freewordKey,freewordVal,jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	//this test case is same as MAFQABANG-78
	@Test(priority=0, groups = { "chpmex"}, description = "MAFAUTO-180")
	public void enlargeAndReduceFontOnDocument() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		DocumentDisplayPage documentPage;
		try {
			boolean searchResultsDisplayed = false;
			String issueSummary = getIssueTitle(jiraNumber, "Document Display Change Font Size");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"documenttitle",extentLogger); 
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"treeitems",extentLogger).split(",");

				
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
				
				
				//documentdisplaypage = searchresultspage.clickFirstLink();

				boolean isFontIncreased = documentPage.DocumentDispalyIncreaseFont();
				softas.assertTrue(isFontIncreased, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isFontIncreased, "Document display page enlarged the font",jiraNumber);

				boolean isFontDecreased = documentPage.DocumentDispalyDecreaseFont();
				softas.assertTrue(isFontDecreased, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isFontDecreased, "Document display page reduced the font",jiraNumber);

				
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	
	
	@Test(priority=1, groups = { "chparg",  "chpmex","chpury","chppe" },  description = "MAFQABANG-77")
	public void documentDisplayForDoctrinaAndJurisprudence() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		try {
			boolean searchResultsDisplayed = false;
			String issueSummary = getIssueTitle(jiraNumber, "Accessing Documet Display Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String authorKey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,authorKey,extentLogger);
				String jurisFreewordKey = "freeword";
				String jurisFreewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,jurisFreewordKey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinePage=searchpage.OpenDoctrinaPage();
				
				
				doctrinePage.enterAuthor(authorVal);
				
				searchresultspage = searchpage.clickOnSearch();
				Thread.sleep(2000);
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", authorKey,
								authorVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}

				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean isdocumentDisplayforDoctrine=documentdisplaypage.isDocumentTitlePresent();
				softas.assertTrue(isdocumentDisplayforDoctrine, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdocumentDisplayforDoctrine,issueSummary + " content type:Doctrina", authorKey, authorVal, jiraNumber);


				searchpage = documentdisplaypage.OpenSearchPage();
				jurisprudencePage=searchpage.openJurisprudencePage();
				jurisprudencePage.enterFreeWordOnSearchPage(jurisFreewordVal);
				searchresultspage = searchpage.clickOnSearch();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", jurisFreewordKey,
								jurisFreewordKey, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}

				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean isdocumentDisplayforJuris=documentdisplaypage.isDocumentTitlePresent();
				softas.assertTrue(isdocumentDisplayforJuris, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdocumentDisplayforJuris,
						issueSummary + " content type:Jurisprudencia",jurisFreewordKey, jurisFreewordVal,jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	

	@Test(priority=2, groups = {"chparg","chpmex","chpbr","chpury","chppe","chpchile"}, description = "MAFQABANG-80")
	public void documentDisplayNavigationOptions() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;
			boolean isnavigationsdisplayed=false; 
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Document Dispaly Navigation Links");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				boolean isfreeworddisplayed = searchpage.isFreewordFieldDisplayed();
				//No thematic search box available separately now in chpmex				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					if (!isfreeworddisplayed) {
						searchpage.clickThematicSearchRadioButton();
					}
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				searchresultspage = searchpage.clickonSearchwhenThematicisSelected();
				
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
						if(!noResultsFound)
						{
							//Log existing bug
							testResult.setAttribute("defect", "MAFAUTO-271");
						}
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();

				documentdisplaypage.clickOnNextLink();

				 isnavigationsdisplayed = documentdisplaypage.isPreviousLinkDisplayed() 
						 && documentdisplaypage.isFirstLinkDisplayed() 
						 && documentdisplaypage.isNextLinkDisplayed() 
						 && documentdisplaypage.isLastLinkDisplayed();
				softas.assertTrue(isnavigationsdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isnavigationsdisplayed, issueSummary + " top link next button",freewordkey, freewordVal,
						jiraNumber);
				


				if(!isnavigationsdisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-283");
				}
				
				
				documentdisplaypage.clickOnLastLink();
				isnavigationsdisplayed = documentdisplaypage.isPreviousLinkDisplayed() && documentdisplaypage.isFirstLinkDisplayed() ;
				softas.assertTrue(isnavigationsdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isnavigationsdisplayed, issueSummary + " top link last button",freewordkey, freewordVal,
						jiraNumber);

				documentdisplaypage.clickOnPreviousLink();
				 isnavigationsdisplayed =  documentdisplaypage.isPreviousLinkDisplayed() 
						 && documentdisplaypage.isFirstLinkDisplayed() 
						 && documentdisplaypage.isNextLinkDisplayed() 
						 && documentdisplaypage.isLastLinkDisplayed();
				softas.assertTrue(isnavigationsdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isnavigationsdisplayed, issueSummary + " top link previous button",freewordkey, freewordVal,
						jiraNumber);

				documentdisplaypage.clickOnFirstLink();
				 isnavigationsdisplayed =  documentdisplaypage.isNextLinkDisplayed() && documentdisplaypage.isLastLinkDisplayed();
				softas.assertTrue(isnavigationsdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isnavigationsdisplayed, issueSummary + " top link first button",freewordkey, freewordVal,
						jiraNumber);

				/*searchpage = documentdisplaypage.clickNewSearch();
				boolean isSearchPageDisplayed = searchpage.searchPageDisplayed();
				softas.assertTrue(isSearchPageDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isSearchPageDisplayed, issueSummary + " new search button",freewordkey, freewordVal,
						jiraNumber);*/
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=3,   groups = {"chparg","chpmex","chpury","chppe","chpchile" }, description = "MAFQABANG-79")
	public void resultListTabNaviagtionOptions() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
		boolean searchResultsDisplayed = false;
		String jiraNumber = testResult.getMethod().getDescription();

		try {

			String issueSummary = getIssueTitle(jiraNumber, "Result List Tab in Document Display");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewrodkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewrodkey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				boolean isfreeworddisplayed = searchpage.isFreewordFieldDisplayed();
				if (!isfreeworddisplayed) {
				searchpage.clickThematicSearchRadioButton();
				}

				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchpage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				
				//searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultspage = searchpage.clickonSearchwhenThematicisSelected();

				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewrodkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean isResultListTabDisplayed=documentdisplaypage.resultListTabDisplayed();
				softas.assertTrue(isResultListTabDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isResultListTabDisplayed,"Result list tab display at left pane", freewrodkey, freewordVal, jiraNumber);

				documentdisplaypage.clickResultListTab();

				boolean isResultListTabClicked=documentdisplaypage.clickResultListTabNumber();
				softas.assertTrue(isResultListTabClicked, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isResultListTabClicked,"Result list tab document links", freewrodkey, freewordVal, jiraNumber);
				if(!isResultListTabClicked){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-283");
					}
				
				boolean isResultListTabNextBnClicked=documentdisplaypage.clickResultListTabNext();
				softas.assertTrue(isResultListTabNextBnClicked, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isResultListTabNextBnClicked,"Result list tab next link button", freewrodkey, freewordVal, jiraNumber);

				boolean isResultListTabPreBnClicked=documentdisplaypage.clickResultListTabPrevious();
				softas.assertTrue(isResultListTabPreBnClicked, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isResultListTabPreBnClicked,"Result list tab previous link button", freewrodkey, freewordVal, jiraNumber);

				documentdisplaypage.clickDocumentLink();
				boolean isDocDisplayed = documentdisplaypage.isDocumentDisplayed();
				softas.assertTrue(isDocDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isDocDisplayed,"Result list document display", freewrodkey, freewordVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=4, groups = { "chparg",  "chpmex", "chpbr","chpury","chpchile" }, description = "MAFQABANG-99")
	public void followTheDocument() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed,flag= false;
			//String docTitleFirstLine = null;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Follow Document");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				// perform search to display the result list. Then select the first
				// document
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
				
				searchresultspage =  searchpage.clickonSearchwhenThematicisSelected();
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchresultspage.clickVertodosLink();
				}

				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}

				documentdisplaypage = searchresultspage.getFirstDocument();
				boolean isFollowDicDisplayed= documentdisplaypage.isFollowDocumentDisplayed();
				softas.assertTrue(isFollowDicDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger,isFollowDicDisplayed,"Follow document option is available", jiraNumber);

				// Following document
				documentdisplaypage.clickFollowDocument();
				documentdisplaypage.clickFollowDocumentInLeftPane();

				// Verifies confirmation message
				flag = documentdisplaypage.isFollowDocConfirmationDisplayed()
						|| documentdisplaypage.isDocumentAlreadyTracked();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,	"Document Followed successfully. Validated the confirmation message", freewordkey,freewordVal,jiraNumber);

				// Tries to follow document again, to validate the information
				// message - 'Document is already followed'

				documentdisplaypage.clickFollowDocument();
				//if (documentdisplaypage.isFollowDocumentDisplayedInLeftPane())
				//documentdisplaypage.clickFollowDocumentInLeftPane();
				boolean isDocAlreadyTracked = documentdisplaypage.isDocumentAlreadyTracked();
				softas.assertTrue(isDocAlreadyTracked, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isDocAlreadyTracked,"Information Message validated for an already tracked document", jiraNumber);
				//This title is used to identify the saved alert in alert page(used in followDocumentAlert)
				//docTitleFirstLine = documentdisplaypage.getDocumentDisplayTitleLineOne();

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=4, groups = { "chpmex"}, description = "MAFAUTO-139")
	public void followExistingDocument() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

			//String docTitleFirstLine = null;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Follow Document ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="widgetname";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				// perform search to display the result list. Then select the first
				// document
			   String linktextvalue="noavailablelinksmsg";
			   String linktextkey =jsonReader.readKeyValueFromJsonObject(jsonObjectChild,linktextvalue,extentLogger);
			
			   homepage.openHomepage();
				if(homepage.isDocumentFollowupExpanded()) {
				homepage.expandDocumentFollowup();
				}
			
				boolean createShortCutAvailable = homepage.isDocumentFollowupAlertPresent();

			
				if (createShortCutAvailable) {
					homepage.removeLinkDocumentFollowup();
				}
			
                 boolean myShortcutText = homepage.isDocumentFollowupTextAvailable();
				
				softas.assertTrue(myShortcutText, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, myShortcutText, "Follow document option is available", jiraNumber);	

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		} 
	}
	
	/*This test will follow a document then delete the same followed document 
	 * from follow document widget available in home page
	 */
	@Test(priority = 5, groups = { "chpmex" }, description = "MAFAUTO-186")
	public void followDocumentAlert() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed, flag = false;
			boolean isalertpresent = false;
			String docTitleFirstLine = null;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Legislation - Document Display - Trackit");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);

				String documentkey = "document";
				String documentkeyname = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, documentkey, extentLogger);

				homepage.clickOnProductLogo();
				searchpage = homepage.OpenSearchPage();
				legislationpage = searchpage.OpenLegislationPage();
                 Thread.sleep(5000);
				legislationpage.enterFreeWordOnlegilationPage(freewordVal);
			//	searchpage.enterThematicOnSearchPage(freewordVal);
				searchresultspage = legislationpage.clickOnSearch();
				searchResultsDisplayed = searchresultspage != null
						&& searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage=searchresultspage.clickOnGivenDocumentToClickOnResults(documentkeyname);
				// opening the document from search results page
			//	documentdisplaypage = searchresultspage.clickFirstLink();

				// Checking whether follow document link is getting displayed or
				// not
				Thread.sleep(3000);
				boolean isFollowDicDisplayed = documentdisplaypage.isFollowDocumentDisplayed();
				softas.assertTrue(isFollowDicDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isFollowDicDisplayed, "Follow document option is available", jiraNumber);

				// Following document
				documentdisplaypage.clickFollowDocument();
				Thread.sleep(4000);
				documentdisplaypage.clickFollowDocumentInLeftPane();

				// Verifies confirmation message
				flag = documentdisplaypage.isFollowDocConfirmationDisplayed()
						|| documentdisplaypage.isDocumentAlreadyTracked();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,
						"Document Followed successfully. Validated the confirmation message", freewordkey, freewordVal,
						jiraNumber);

				// Tries to follow document again, to validate the information
				// message - 'Document is already followed'

				documentdisplaypage.clickFollowDocument();
				// if
				// (documentdisplaypage.isFollowDocumentDisplayedInLeftPane())
				// documentdisplaypage.clickFollowDocumentInLeftPane();
				boolean isDocAlreadyTracked = documentdisplaypage.isDocumentAlreadyTracked();
				softas.assertTrue(isDocAlreadyTracked, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isDocAlreadyTracked,
						"Information Message validated for an already tracked document", jiraNumber);
				// This title is used to identify the saved alert in alert
				// page(used in followDocumentAlert)
				docTitleFirstLine = documentdisplaypage.getDocumentDisplayTitleLineOne();

				// navigating to home page to see whether followed document
				// exists or not

				homepage = documentdisplaypage.openHomepage();
				boolean isfollowdocumentwidgetmaximized = homepage.isFollowDocumentWidgetMaximized();
				if (!isfollowdocumentwidgetmaximized) {
					homepage.maximizeFollowDocumentWidget();
				}

//				homepage.clickFollowDocumentWidgetVermasLink();
				isalertpresent = homepage.isAlertPresentInFollowDocumentWidget(docTitleFirstLine);
				if (isalertpresent) {
					homepage.clickRemoveLinkInFollowDocumentWidget(docTitleFirstLine);
					homepage.clickFollowDocumentDeleteYesButton();
				}

				softas.assertTrue(isalertpresent, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isalertpresent, issueSummary, freewordkey, freewordVal, jiraNumber);

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
	 * DocDisplay_TC_028 - ResultList_Save and Schedule Search available in
	 * Result List page
	 */

	@Test(priority=6, groups = {"chparg","chpmex","chpury" }, description = "MAFQABANG-104")
	public void saveAndScheduleResultList() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			String saveName = null;
			boolean searchResultsDisplayed , flag= false;
			

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Save And Schedule Result List");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);
				String TitleKey = "Title";
				String TitleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,TitleKey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultspage = legislationpage.clickOnSearch();

				searchResultsDisplayed = searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordKey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}

				// Open Save and Schedule Search Page
				saveAndSchedulePage = searchresultspage.clickSaveAndScheduleSearch();
				if (saveAndSchedulePage != null)
				{
					flag = saveAndSchedulePage.isSaveAndScheduleTitleDisplayed(TitleVal);
				}

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,  "Validate the display of Save and Schedule Search Page",
						jiraNumber);

				saveName = "ATest" + RandomUtils.getUniqueNumber();
				saveAndSchedulePage.setName(saveName);
				saveAndSchedulePage.selectFrequencyMonthly();

				// Click on save button, open alert page & verifies the new alert is
				// saved
				alertpage = saveAndSchedulePage.clickSaveAlertButton();
				if (flag = (alertpage != null))
						softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
						logExtentStatus(extentLogger, flag,"Validate the new alert is created & displayed in the alerts list",
						"Alert",saveName,jiraNumber);
				flag = alertpage.DeleteAlert(saveName);
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,"Validate the new alert is deleted from the alerts list",
						"Alert",saveName,jiraNumber);
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
	 * DocDisplay_TC_007 - DocDisplay_Save and Schedule Search
	 */
	@Test(priority=7, groups = { "chparg",  "chpmex","chpury" }, description = "MAFQABANG-83")
	public void saveAndScheduleDocDisplay() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed,flag = false;
			String saveName = null;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,	"Save And Schedule Document");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);

				String Title = "Title";
				String TitleVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,Title,extentLogger);

				searchpage = homepage.OpenSearchPage();
				legislationpage=searchpage.OpenLegislationPage();
				legislationpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultspage = searchpage.clickOnSearch();

				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				
				documentdisplaypage = searchresultspage.getFirstDocument();
				documentdisplaypage.clickSearchResultsTab();

				// Open Save and Schedule Search Page
				saveAndSchedulePage = documentdisplaypage.clickSaveAndScheduleSearch();
				if (flag = (saveAndSchedulePage != null))
					flag = saveAndSchedulePage.isSaveAndScheduleTitleDisplayed(TitleVal);
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,  "Validate the display of Save and Schedule Search Page",
						freewordkey,freewordVal,jiraNumber);

				saveName = "Schedule Search " + RandomUtils.getUniqueNumber();
				saveAndSchedulePage.setName(saveName);
				saveAndSchedulePage.selectFrequencyMonthly();

				// Click on save button, open alert page & verifies the new alert is
				// saved
				alertpage = saveAndSchedulePage.clickSaveAlertButton();
				if (flag = (alertpage != null))
					softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, flag,
							"Validate the new alert is created & displayed in the alerts list", freewordkey,freewordVal,jiraNumber);
				
				//deleting the created alert
				if(flag)
				{
					alertpage.DeleteAlert(saveName);
				
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


	@Test(priority=8, groups = { "chparg","chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-87")
	public void highlightOfSearchTermOnDocDisplay() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Highlight of Search Term in DocDisplay");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				// Perform search and go to document display page
				homepage.openHomepage();
				homepage.clickRefreshforThematicSearch();
				homepage.enterFreewordOnQuickSearch(freewordVal);
				searchresultspage = homepage.clickSearch();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
						if(noResultsFound)
						{
							//Log existing bug
							testResult.setAttribute("defect", "MAFAUTO-271");
						}
					}

					continue; // Skip this & Continue with next iteration

				}	
				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean isTextHighlighted=documentdisplaypage.isHighlightedTextDisplayed();
				softas.assertTrue(isTextHighlighted, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isTextHighlighted,"Verify Highlighted search term",freewordkey,freewordVal, jiraNumber);


				if(!isTextHighlighted){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-283");
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

	//This test case is same as MAFQABANG-87
	@Test(priority=8, groups = { "chpmex" }, description = "MAFAUTO-182")
	public void highlightOfSearchTextOnDocDisplay() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Highlight of Search Term in DocDisplay");
		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				// Perform search and go to document display page
				homepage.openHomepage();
				homepage.clickRefreshforThematicSearch();
				homepage.enterTextInSearchField(freewordVal);
				//homepage.enterFreewordOnQuickSearch(freewordVal);
				searchresultspage = homepage.clickSearch();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}	
				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean isTextHighlighted=documentdisplaypage.isHighlightedTextDisplayed();
				softas.assertTrue(isTextHighlighted, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isTextHighlighted,"Verify Highlighted search term",freewordkey,freewordVal, jiraNumber);

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
	 * DocDisplay_TC_030 - DocDisplay_Verify navigation through index feature in
	 * the document display
	 * this feature is not applicable for chpury
	 */
	@Test(priority = 9, groups = { "chparg","chpbr","chppe","chpchile","chpmex" }, description = "MAFQABANG-105")
	public void navigationWithIndexOnDocDisplayforAll() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed, flag = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Document Display Index Feature");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);

				homepage.openHomepage();
				homepage.clickRefreshforThematicSearch();
//				homepage.enterTextInSearchField(freewordVal);
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					homepage.clickRefreshforThematicSearch();
					homepage.enterFreewordOnQuickSearch(freewordVal);
				}
				
				
				searchresultspage = homepage.clickSearch();

				searchResultsDisplayed = searchresultspage != null
						&& searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed ", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed ");
					}

					continue; // Skip this & Continue with next iteration

				}

				documentdisplaypage = searchresultspage.getFirstDocument();
				
				if(!(BaseTest.productUnderTest.equals("chparg") || BaseTest.productUnderTest.equals("chppe")) )
				{
				flag = documentdisplaypage.isIndexDisplayed();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + "Index option is available", jiraNumber);


				if(!flag){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-283");
				}
				
				// Index document
				documentdisplaypage.clickDocumentIndex();
				}
				// TODO add warning to log extent method
				if (documentdisplaypage.isIndexNotAvailable())
					extentLogger.log(LogStatus.WARNING, "The index of the standard is not available at the moment.<br>"
							+ takesScreenshot_Embedded());

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
	 * DocDisplay_TC_030 - DocDisplay_Verify navigation through index feature in
	 * the document display for chpmex
	 * this test is similar to MAFQABANG-105 with one step extra validation which is applciable only for chpmex
	 */
	@Test(priority = 10, groups = { "chpmex" }, description = "MAFQABANG-105")
	public void navigationWithIndexOnDocDisplay() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed, flag = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Document Display Index Feature");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);

				homepage.openHomepage();
				homepage.clickRefreshforThematicSearch();
				homepage.enterFreewordOnQuickSearch(freewordVal);
				searchresultspage = homepage.clickSearch();

				searchResultsDisplayed = searchresultspage != null
						&& searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}

				documentdisplaypage = searchresultspage.getFirstDocument();
				flag = documentdisplaypage.isIndexDisplayed();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + "Index option is available", jiraNumber);
				// Index document
				documentdisplaypage.clickDocumentIndex();

				if (documentdisplaypage.isIndexNotAvailable())
					extentLogger.log(LogStatus.WARNING,
							"The index of the standard is not available at the moment.<br>" + takesScreenshot_Embedded());

				// Verifies index in left pane
				flag = documentdisplaypage.isIndexAvailable();

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + "Document Index validated.",freewordkey,freewordVal, jiraNumber);


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
	 * DocDisplay_TC_032 - DocDisplay_Verify clicking 'Terms of use' link and
	 * navigate back to document display
	 */
	/*@Test(priority=10, groups = {"chpmex","chpury","chppe","chpchile"}, description = "MAFQABANG-108")
	public void navigateToTermsLinkAndBackToDocDisplay() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed,flag = false;
			
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,	"Navigate To TermsLink from Document Display");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				String redirectedurl="redirectedurl";
				String redirectedurlVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,redirectedurl,extentLogger);
				String redirectedtext="redirectedtext";
				String redirectedtextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,redirectedtext,extentLogger);
				
				
				homepage.openHomepage();
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					homepage.clickRefreshforThematicSearch();
					homepage.enterFreewordOnQuickSearch(freewordVal);
				}
				
				searchresultspage = homepage.clickSearch();

				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				
				documentdisplaypage = searchresultspage.getFirstDocument();
				boolean isTermsOfUseLinkDisplay=documentdisplaypage.isTermsOfUseLinkPresent();
				softas.assertTrue(isTermsOfUseLinkDisplay, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isTermsOfUseLinkDisplay,"Terms of use link in DocumentDisplay page", freewordkey,freewordVal, jiraNumber);
				


				if(!isTermsOfUseLinkDisplay){
					//Log existing bug
					testResult.setAttribute("defect", "LLOT-4619");
				}
				// Click terms of use link
				documentdisplaypage.clickTermsOfUseLink();
				flag = documentdisplaypage.isTermsOfUseRedirectedtoCorrectURl(redirectedurlVal,redirectedtextVal);
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,  "verify navigate to terms of link",jiraNumber);
				
				// Navigate back to document display page.
				if (flag)
				{
					driver.navigate().back();
				}
				// Verifies document display
				flag = documentdisplaypage.isDocumentDisplayed();

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, "Navigated back to Document display page", jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}*/

	/*
	 * DocDisplay_TC_010 - DocDisplay_Tree Content Tab (árbol de contenido) in
	 * Left Pane
	 */
	@Test(priority=11, groups = { "chparg",  "chpmex", "chpbr","chpury","chppe" }, description = "MAFQABANG-86")
	public void navigationUsingcontentTreeOnDocumentDisplay() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed,flag = false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,	"Tree Content in Document Display");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);

				// perform search to display the result list. Then select the first
				// document 
				homepage.openHomepage();
				homepage.clickRefreshforThematicSearch();
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
				homepage.enterFreewordOnQuickSearch(freewordVal);
				}
				searchresultspage = homepage.clickSearch();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.getFirstDocument();

				flag = documentdisplaypage.isTreeContentTabDisplayed();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, "Tree Content tab Displayed in Document display page", jiraNumber);
				
				// Click Content Tree tab
				documentdisplaypage.clickContentTreeTab();
				boolean treeDisplayed = documentdisplaypage.isContentTreeStructureDisplayed();
				softas.assertTrue(treeDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, treeDisplayed, issueSummary,freewordkey,freewordVal, jiraNumber);
				


				if(!treeDisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-283");
				}
				if (!treeDisplayed) {
					flag = documentdisplaypage.isContentTreeInfoNotAvailable();
					softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, flag,  "Content tree is not available for this document",
							freewordkey,freewordVal, jiraNumber);
				}


			} 
		}catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * DocDisplay_TC_026 - ResultList_Search Results Widget in Results List page
	 */
	@Test(priority=12,   groups = { "chparg",  "chpmex", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-102")
	public void searchResultsWidget() throws Exception {

		boolean flag = true;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		String jiraNumber = testResult.getMethod().getDescription();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
		String issueSummary = getIssueTitle(jiraNumber, "Contents of Search Results Widget in Results List");

		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				// perform search to display the result list.
				searchpage = homepage.OpenSearchPage();
				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultspage = legislationpage.clickOnSearch();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				boolean isSearchResultWidgetDisplayed =searchresultspage.isSearchResultWidgetDisplayed();
				softas.assertTrue(isSearchResultWidgetDisplayed, "Search Results Widget displayed");
				logExtentStatus(extentLogger,isSearchResultWidgetDisplayed,
						"Search Results Widget displayed", jiraNumber);
				// Verify Search Results Widget(verify the presence of all expected
				// elements)
				flag = searchresultspage.isTotalDocumentLabelPresent() && searchresultspage.isTotalDocumentCountPresent()
						&& searchresultspage.isSearchTermsLabelPresentInWidget()
						&& searchresultspage.isSearchTermPresentInWidget()
						&& searchresultspage.isHyperLinksPresentInSearchResultWidget()
						&& searchresultspage.isSearchWithinOptionDisplayed()
						&& searchresultspage.isSearchButtonDisplayedInWidget();

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,issueSummary,freewordkey,freewordVal, jiraNumber);

			}
		}catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * DocDisplay_TC_025 - ResultList_Second level Filter Facets in result List
	 * page
	 */
	@Test(priority=13,   groups = { "chparg" }, description = "MAFQABANG-101")
	public void secondLevelFilterOnResultList() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;
			boolean flag = false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Second Level Facet");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				
				// perform search to display the result list.
				homepage.openHomepage();
				homepage.clickRefreshforThematicSearch();
//				homepage.enterFreewordOnQuickSearch(freewordVal);
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					homepage.enterFreewordOnQuickSearch(freewordVal);
				}
				searchresultspage = homepage.clickSearch();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}

				flag = searchresultspage.isFacetwidgetDisplayed();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + "Verify Source widget displayed", jiraNumber);
				
				// Verify clicking 'Doctrina' in source widget & then verify second
				// level widgets
				flag = searchresultspage.clickDoctrinaInSourceWidget() && searchresultspage.isAllWidgetsExistForDoctrina();

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,  "Validated Widgets for 'Doctrina", jiraNumber);

				// Verify clicking 'Legislation' in source widget & then verify
				// second level widgets
				flag = searchresultspage.clickLegislationInSourceWidget()
						&& searchresultspage.isAllWidgetsExistForLegislation();

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,  "verify widget for Legislation", jiraNumber);

				// Verify clicking 'Jurisprudence Summary' in source widget & then
				// verify second level widgets
				flag = searchresultspage.clickJurisprudenceSummaryInSourceWidget()
						&& searchresultspage.isAllWidgetsExistForJurisprudenceSummary();

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,  "verify widget for 'Jurisprudence Summary", jiraNumber);

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
	 * DocDisplay_TC_025 - ResultList_Second level Filter Facets in result List
	 * page
	 */
	@Test(priority=13,   groups = { "chppe" }, description = "MAFQABANG-615")
	public void secondLevelWidgetsForDoctrineAndLegislation() throws Exception {

		boolean flag = true;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Second Level Facet");

		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				// perform search to display the result list.
				homepage.enterFreewordOnQuickSearch(freewordVal);
				searchresultspage = homepage.clickSearch();
				Thread.sleep(3000);
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}

				flag = searchresultspage.isFacetwidgetDisplayed();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + " Verify Source widget displayed", jiraNumber);
				// Verify clicking 'Doctrina' in source widget & then verify second
				// level widgets
				flag = searchresultspage.clickDoctrinaInSourceWidget() && searchresultspage.isAllWidgetsExistForDoctrina();

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,  "Validated Widgets for 'Doctrina", jiraNumber);

				// Verify clicking 'Legislation' in source widget & then verify
				// second level widgets
				flag = searchresultspage.clickLegislationInSourceWidget()
						&& searchresultspage.isAllWidgetsExistForLegislation();

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,  "verify widget for Legislation", jiraNumber);

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
	 * MAFAUTO-150 Common Result List Features - Result List - Pagination
	 */
	@Test(priority=14, groups = { "chpmex" }, description = "MAFAUTO-150")
	public void pageNavigationOnResultList() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Page Navigation In Result List");

		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				
//				homepage.enterFreewordOnQuickSearch(freewordVal);
				searchpage = homepage.OpenSearchPage();
				homepage.enterTextInSearchField(freewordVal);
				searchresultspage = homepage.clickOnSearch();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				Thread.sleep(3000);
				searchresultspage.clickVertodosLink();
				Thread.sleep(3000);
				Boolean isNavigationBarDisplayed = searchresultspage.isDocNavigationDisplayed();
				softas.assertTrue(isNavigationBarDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isNavigationBarDisplayed,
						"Search shows doc navigation bar and page links", freewordkey,freewordVal, jiraNumber);
				Thread.sleep(3000);
				if (isNavigationBarDisplayed) {
					// Validate 'Next' link navigation
					int currentPage = searchresultspage.getCurruntPageNumber();
					searchresultspage.clickNextPageLink();
					Thread.sleep(3000);
					boolean linkClicked = false;
					if (linkClicked = searchresultspage.getCurruntPageNumber() == currentPage + 1)
						currentPage += 1;
					linkClicked = linkClicked && searchresultspage.isPreviousPageLinkDisplayed()
							&& searchresultspage.isFirstPageLinkDisplayed();

					softas.assertTrue(linkClicked, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, linkClicked,
							"Validate clicking Next Link in the top navigation bar", jiraNumber);
					// Validate 'Previous' link navigation
					searchresultspage.clickPreviousPageLink();
					if (linkClicked = searchresultspage.getCurruntPageNumber() == currentPage - 1)
						currentPage -= 1;
					linkClicked = linkClicked && searchresultspage.isNextPageLinkDisplayed()
							&& searchresultspage.isLastPageLinkDisplayed();

					softas.assertTrue(linkClicked, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, linkClicked,
							"Validate clicking Next Previous in the top navigation bar", jiraNumber);
					// Validate 'LastPage' link navigation
					Thread.sleep(3000);
					searchresultspage.clickLastPageLink();
					currentPage = searchresultspage.getCurruntPageNumber();
					linkClicked = (currentPage > 0) && searchresultspage.isPreviousPageLinkDisplayed()
							&& searchresultspage.isFirstPageLinkDisplayed();

					softas.assertTrue(linkClicked, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, linkClicked,
							"Validate clicking LastPage link in the top navigation bar", jiraNumber);

					// Validate 'FirstPage' link navigation
					Thread.sleep(3000);
					searchresultspage.clickFirstPageLink();
					currentPage = searchresultspage.getCurruntPageNumber();
					linkClicked = (currentPage == 1) && searchresultspage.isNextPageLinkDisplayed()
							&& searchresultspage.isLastPageLinkDisplayed();

					softas.assertTrue(linkClicked, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, linkClicked,
							"Validate clicking FirstPage link in the top navigation bar", jiraNumber);
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

	@Test(priority=15, groups = { "chpmex" }, description = "MAFAUTO-211")
	public void headerDisplayOnDocument() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, 
				"Document Display Header");
		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				String docuname=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"Document",extentLogger);
				searchpage = homepage.OpenSearchPage();
				searchpage.enterTextInSearchField(freewordVal);
				searchresultspage = searchpage.clickOnSearchButtonDocumentDisplay();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				 searchresultspage.clickExpectedLinkFromResultsPage(docuname);

				boolean documentDisplayscroll = searchresultspage.Clickscrollbutton();
				softas.assertTrue(documentDisplayscroll, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentDisplayscroll,  issueSummary,freewordkey,freewordVal,jiraNumber);
			}

		}catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority=16, groups = { "chpmex" }, description = "MAFAUTO-181")
	public void displayOfDocumentDisplayPage() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber,"Document Display Outline");
		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey ="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				searchpage = homepage.OpenSearchPage();
				//searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchpage.enterTextInSearchField(freewordVal);
				searchresultspage = searchpage.clickOnSearchButtonDocumentDisplay();

				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();

				boolean documentDisplaycontenttree = documentdisplaypage.Clickscontenttabtree();
				softas.assertTrue(documentDisplaycontenttree, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentDisplaycontenttree, issueSummary + " document display content tree",
						jiraNumber);

				boolean documentDisplayreversetree = documentdisplaypage.Clickrevtabtree();
				softas.assertTrue(documentDisplayreversetree, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentDisplayreversetree, issueSummary + " document display reverse search",
						jiraNumber);

				searchresultspage = documentdisplaypage.clickOnListOfDocumentsToReturnToSearchResults();
				boolean documentDisplaylistofdocument = searchresultspage.searchResultsHeaderContainerDisplayed();
				softas.assertTrue(documentDisplaylistofdocument, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentDisplaylistofdocument,
						issueSummary + " document display list of document",freewordkey,freewordVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}
	
	@Test(priority=17, groups = { "chpmex" }, description = "MAFAUTO-100")
	public void followDocumentWidget() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Validate Content Tree and Corelation POP UP ");

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

				//Open document from the tree
				boolean docDisplayedInTree = searchPage.isItemPresentInSubContentTree(treeElement,documentTitle);
				if(docDisplayedInTree){
					documentdisplaypage = searchPage.clickDocumentInSubContentTree(treeElement,documentTitle);
				}
				docDisplayedInTree &= (documentdisplaypage!=null);
				softas.assertTrue(docDisplayedInTree,jiraNumber+" : "+issueSummary+" : Document Display");
				logExtentStatus(extentLogger, docDisplayedInTree, issueSummary+" : Document Display", jiraNumber);
				
				// Following document
				documentdisplaypage.clickFollowDocument();
				Thread.sleep(6000);
				documentdisplaypage.clickFollowDocumentInLeftPane();

				// Verifies confirmation message
				boolean documentFollowed = false;

				if (documentFollowed = documentdisplaypage.isFollowDocConfirmationDisplayed()){
					softas.assertTrue(documentFollowed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, documentFollowed,
							"Document Followed successfully. Validated the confirmation message",
							documentTitle,documentTitle,jiraNumber);
				} else if (documentFollowed = documentdisplaypage.isDocumentAlreadyTracked()){
					softas.assertTrue(documentFollowed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, documentFollowed,
							"Document is already followed in document display",
							documentTitle,documentTitle,jiraNumber);
				} else{
					softas.assertTrue(documentFollowed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, documentFollowed,
							"Document followed in document display",documentTitle,documentTitle,jiraNumber);
				}

				homepage=documentdisplaypage.openHomepage();

				// Opening home page to verify the alert is created in home page
				// widget.
				if (homepage.isFollowDocumentWidgetDisplayed())
					homepage.expandFollowDocumentWidgetView();
				boolean alertPresentInWidget = homepage.isAlertPresentInFollowDocumentWidget(documentTitle);

				softas.assertTrue(alertPresentInWidget, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, alertPresentInWidget,
						"Follow Document alert displayed in the homepage widget", jiraNumber);

				// Delete alert for current follow document - validate confirmation
				// message
				homepage.clickRemoveLinkInFollowDocumentWidget(documentTitle);
				boolean deleteMessageDisplayed = homepage.isFollowDocumentDeleteQuestionDisplayed()
						&& homepage.isFollowDocumentDeleteMessageDisplayed(documentTitle);

				softas.assertTrue(deleteMessageDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, deleteMessageDisplayed,
						"Delete Alert - confirmation Question displayed", jiraNumber);

				// Delete the alert for current follow document
				homepage.clickFollowDocumentDeleteYesButton();
				homepage.expandFollowDocumentWidgetView();
				boolean alertDeleted = !(homepage.isAlertPresentInFollowDocumentWidget(documentTitle));

			//	boolean alertdeleted = homepage.isAlertPresentInFollowDocumentWidgetforchpmex(documentTitle);
				softas.assertTrue(alertDeleted, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, alertDeleted,
						"Alert for 'follow document' is deleted to unfollow the document", jiraNumber);
						
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	
	@Test(priority=19, groups = { "chpbr" }, description = "MAFQABANG-82")
	public void documentDisplayAndResultListTab() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, "Search Results (Rdo. de la busqueda)Tab in Left Pane");
		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey ="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultspage = searchpage.clickOnSearchButtonDocumentDisplay();

				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean isresultListTabDisplayed=documentdisplaypage.resultListTabDisplayed();
				softas.assertTrue(isresultListTabDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isresultListTabDisplayed,
						"Verify result list tab display at left pane", freewordkey,freewordVal,jiraNumber);

				documentdisplaypage.clickResultListTab();
				documentdisplaypage.clickDocumentLink();
				boolean isDocumentDisplayed= documentdisplaypage.isDocumentDisplayed();
				softas.assertTrue(isDocumentDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isDocumentDisplayed,
						"Verify result list document display",freewordkey,freewordVal,jiraNumber);

				boolean documentdisplaysearchtab = documentdisplaypage.searchResultTab();
				softas.assertTrue(documentdisplaysearchtab, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentdisplaysearchtab,  "Verify search result tab display",
						freewordkey,freewordVal,jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=20,   groups = { "chpmex" }, description = "MAFAUTO-124")
	public void toolTipsOnDocumentDisplay() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, "Document Display ToolTips");
		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey ="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				
				String treeItemscount="contenttree";
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,treeItemscount,extentLogger).split("/");
				
				String document="document";
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,document,extentLogger);
				
				String thematicarea="thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,thematicarea,extentLogger);
				
				String firsttooltipname ="firsttooltip";
				String firsttooltip = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,firsttooltipname,extentLogger);
				
				String secondtooltipname ="secondtooltip";
				String secondtooltip = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,secondtooltipname,extentLogger);
				
				String thirdtooltipname ="thirdtooltip";
				String thirdtooltip = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,thirdtooltipname,extentLogger);
				
				String fourttooltipname ="fourttooltip";
				String fourttooltip = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,fourttooltipname,extentLogger);
				
				String fivetooltipname ="fivetooltip";
				String fivetooltip = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,fivetooltipname,extentLogger);
				
				searchpage = homepage.OpenSearchPage();
				
				 searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
					if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
						searchpage.selectAreaFromContentTree(thematicareaVal);
					}
				//Expand First level item in the tree structure
				WebElement treeElement = searchpage.getFirstLevelContentTree(treeItems[0]);
				searchpage.expandContentTreeElement(treeElement);
				//String treeTraversed = treeItems[0];
				//Expand till document in the sub tree
				for(int row=1; row<treeItems.length ;row++){
					if(searchpage.isItemPresentInSubContentTree(treeElement,treeItems[row])){
						searchpage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = searchpage.getSubContentTreeElement(treeElement, treeItems[row]);
					}
				}

				//Open document from the tree
				boolean docDisplayedInTree = searchpage.isItemPresentInSubContentTree(treeElement,documentTitle);
				if(docDisplayedInTree){
					documentdisplaypage = searchpage.clickDocumentInSubContentTree(treeElement,documentTitle);
				}
				docDisplayedInTree &= (documentdisplaypage!=null);
				softas.assertTrue(docDisplayedInTree,jiraNumber+" : "+issueSummary+" : Document Display");
				logExtentStatus(extentLogger, docDisplayedInTree, issueSummary+" : Document Display", jiraNumber);
				
				
				boolean documentlastreforms=documentdisplaypage.tooltipmsgofLatestReforms();
				softas.assertTrue(documentlastreforms, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentlastreforms,  "Verify Last Reforms tip",firsttooltipname,firsttooltip, jiraNumber);
				
				boolean documentrelatedlegislation = documentdisplaypage.tooltipmsgofRelatedLegislation();
				softas.assertTrue(documentrelatedlegislation, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentrelatedlegislation,  "Verify Related Legislation tip",secondtooltip,secondtooltipname, jiraNumber);
		
				boolean documentvocestooltips = documentdisplaypage.vocestooltip();
				softas.assertTrue(documentvocestooltips, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentvocestooltips,  "Verify voces tool tip",thirdtooltip,thirdtooltipname, jiraNumber);

				boolean documentfollowtooltips = documentdisplaypage.followtooltip();
				softas.assertTrue(documentfollowtooltips, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentfollowtooltips,  "Verify follow tool tip", fourttooltipname,fourttooltip, jiraNumber);

				boolean documentindextooltips = documentdisplaypage.indextooltip();
				softas.assertTrue(documentindextooltips, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentindextooltips,  "Verify index tool tip",fivetooltipname,fivetooltip, jiraNumber);
				
				
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=22,   groups = { "chparg",  "chpmex", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-88")
	public void keywordsOnDocumentDisplay() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, "Document Display Keywords");
		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey ="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);

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
				
				searchresultspage = searchpage.clickonSearchwhenThematicisSelected();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				boolean resultkeyword = searchresultspage.keywordOnResultList();
				softas.assertTrue(resultkeyword, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, resultkeyword, "Verify keyword in search result", jiraNumber);

				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean documentkeyword = documentdisplaypage.keywordResultdocumentdispaly();
				softas.assertTrue(documentkeyword, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, documentkeyword, issueSummary,freewordkey,freewordVal,jiraNumber);
				
				if(!documentkeyword){
					//Log existing bug
						testResult.setAttribute("defect", "MAFAUTO-283");
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

	@Test(priority=23,   groups = { "chparg",  "chpmex", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-100")
	public void facteingOnResultList() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, 
				"Verify fecets in resultlist");
		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey ="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				searchpage = homepage.OpenSearchPage();
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else { 
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				searchresultspage = searchpage.clickonSearchwhenThematicisSelected();
				//searchresultspage = searchpage.clickOnSearch();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				boolean resultfecet = searchresultspage.isResultFecetDisplayed();
				softas.assertTrue(resultfecet, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, resultfecet,  issueSummary,freewordkey,freewordVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=24,   groups = { "chpmex" }, description = "MAFAUTO-117")
	public void displayDocumentForDoctrine() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Verify document display for Doctrine");
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		try {
			boolean searchResultsDisplayed = false;	
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String authorkey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,authorkey,extentLogger);
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				searchpage = homepage.OpenSearchPage();
				doctrinePage=searchpage.OpenDoctrinaPage();
				doctrinePage.enterAuthor(authorVal);
				doctrinePage.enterAuthorDropDownValue();
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultspage = doctrinePage.clickOnSearch();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", authorkey,
								authorVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean documentDisplayed =  documentdisplaypage != null
						&& documentdisplaypage.isDocumentTitlePresent();
				softas.assertTrue(documentDisplayed, jiraNumber+":"+issueSummary);
				logExtentStatus(extentLogger, documentDisplayed, issueSummary,authorkey,authorVal,jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority=25,   groups = { "chpmex" },  description = "MAFAUTO-118")
	public void deliveryUsingPrintAndExportOnDocDisplay() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Document Display - Delivery for Doctrine");
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
		boolean flag = false;

		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String authorkey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,authorkey,extentLogger);
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				
				searchpage = homepage.OpenSearchPage();
				Thread.sleep(2000);
				doctrinePage=searchpage.OpenDoctrinaPage();
				Thread.sleep(2000);
				doctrinePage.enterAuthor(authorVal);
				doctrinePage.enterAuthorDropDownValue();
				Thread.sleep(2000);
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultspage = doctrinePage.clickOnSearch();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", authorkey,
								authorVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();

				boolean documentdisplaydelivery = documentdisplaypage.deliveryDocument();
				softas.assertTrue(documentdisplaydelivery,jiraNumber + ":"+issueSummary);
				logExtentStatus(extentLogger, documentdisplaydelivery,issueSummary,authorkey,authorVal, jiraNumber);

				deliverypage = documentdisplaypage.clickExportButton();
				flag = deliverypage.verifyDocumentExportPage() && deliverypage.enableRadioButton("formato_de_archivo_rtf")
						&& deliverypage.clickAcceptButton() ;
//						&& deliverypage.isDeliveryCompleted();
				softas.assertTrue(flag, "Delivery completed for 'list of results' as RTF document (Doctrina)");
				logExtentStatus(extentLogger, flag, "Delivery completed for 'list of results' as RTF document (Doctrina)",jiraNumber);

				deliverypage.navigateBack();
//				deliverypage.clickPrint();
				softas.assertTrue(flag,"MAFAUTO-118:Verify print from docuemnt display with fulldocument (Doctrina)");
				logExtentStatus(extentLogger, flag,"Verify print from docuemnt display with fulldocument (Doctrina)",jiraNumber);
				searchpage = deliverypage.deliveryReturnToSearchPage();

			}

		}catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority=26,   groups = { "chpmex" },  description = "MAFAUTO-113")
	public void deliveryOfJurisprudenceDocument() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		boolean flag = false;
		String issueSummary = getIssueTitle(jiraNumber, "Document Display - Delivery for Jurisprudence document");
		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				String expectedresult = "resulstsearch";
				String expectedresults = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expectedresult,
						extentLogger);
				String thematicareaKey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				
				searchpage = homepage.OpenSearchPage();
				 searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
					if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
						searchpage.selectAreaFromContentTree(thematicareaVal);
					}
					Thread.sleep(2000);
				jurisprudencePage=searchpage.clickjurisprudencia();
				Thread.sleep(2000);
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultspage = searchpage.clickOnSearchButtonDocumentDisplay();
				Thread.sleep(2000);
				searchResultsDisplayed = searchresultspage!=null ;
						//&& searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}	
				boolean ExpectedResult=searchresultspage.isExpectedResultOpened(expectedresults);
				softas.assertTrue(ExpectedResult, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, ExpectedResult,"Search results displayed as expected",jiraNumber);	
				
				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean documentdisplaydeliveryjr = documentdisplaypage.deliveryDocument();
				softas.assertTrue(documentdisplaydeliveryjr,jiraNumber + ":"+issueSummary);
				logExtentStatus(extentLogger, documentdisplaydeliveryjr,issueSummary,freewordkey,freewordVal, jiraNumber);

				deliverypage = documentdisplaypage.clickExportButton();
				flag = deliverypage.verifyDocumentExportPage() && deliverypage.enableRadioButton("formato_de_archivo_rtf")
						&& deliverypage.clickAcceptButton();
						
//						&& deliverypage.isDeliveryCompleted();
				softas.assertTrue(flag, "Delivery completed for 'list of results' as RTF document (Jurisprudencia)");
				logExtentStatus(extentLogger, flag,
						"Delivery completed for 'list of results' as RTF document (Jurisprudencia)", jiraNumber);
				//deliverypage.navigateBack();
				driver.navigate().back();
				Thread.sleep(3000);
				deliverypage.clickPrint();
				softas.assertTrue(deliverypage.deliverTextDisplayed(),
						jiraNumber + ":Verify print from docuemnt display with fulldocument (Jurisprudencia)");
				logExtentStatus(extentLogger, deliverypage.deliverTextDisplayed(),
						"Verify print from docuemnt display with fulldocument (Jurisprudencia)", jiraNumber);

				searchpage = deliverypage.deliveryReturnToSearchPage();
				searchpage = homepage.OpenSearchPage();
				legislationpage=searchpage.openLegislationPage();
				legislationpage.enterFreeWordOnSearchPage("permanent establishment");
				searchresultspage = searchpage.clickOnSearchButtonDocumentDisplay();
				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean documentdisplaydeliverylegis = documentdisplaypage.deliveryDocument();
				softas.assertTrue(documentdisplaydeliverylegis, "Verify print and export options displayed (Legislation)");
				logExtentStatus(extentLogger, documentdisplaydeliverylegis,
						"Verify print and export options displayed (Legislation)", jiraNumber);

				deliverypage = documentdisplaypage.clickExportButton();
				flag = deliverypage.verifyDocumentExportPage() && deliverypage.enableRadioButton("formato_de_archivo_rtf")
						&& deliverypage.clickAcceptButton();
						
//						&& deliverypage.isDeliveryCompleted();
				softas.assertTrue(flag, "Delivery completed for 'list of results' as RTF document (Legislation)");
				logExtentStatus(extentLogger, flag,
						"Delivery completed for 'list of results' as RTF document (Legislation)", jiraNumber);
//				deliverypage.navigateBack();
				driver.navigate().back();
				Thread.sleep(3000);
				deliverypage.clickPrint();
				softas.assertTrue(deliverypage.deliverTextDisplayed(),
						"MAFAUTO-113:Verify print from document display with full document (Legislation)");
				logExtentStatus(extentLogger, deliverypage.deliverTextDisplayed(),
						"Verify print from document display with fulldocument (Legislation)", jiraNumber);
				searchpage = deliverypage.deliveryReturnToSearchPage();
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority=27,   groups = { "chpmex" },  description = "MAFAUTO-176")
	public void displayOfDocumentFromGlobalSearch() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		boolean flag = false;
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Verify document delivery for Global Search");

		try {
			boolean searchResultsDisplayed = false;
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(2000);
				searchpage.enterTextInSearchField(freewordVal);
				searchresultspage = searchpage.clickOnSearchButtonDocumentDisplay();
				
				if(searchpage.isErrorPageDisplayedAgain()) {
					searchpage = homepage.OpenSearchPage();
					Thread.sleep(2000);
					searchpage.enterTextInSearchField(freewordVal);
					Thread.sleep(2000);
					searchresultspage = searchpage.clickOnSearchButtonDocumentDisplay();
				}
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean documentdisplaydeliveryglobalssearch = documentdisplaypage.deliveryDocument();
				softas.assertTrue(documentdisplaydeliveryglobalssearch,issueSummary);
				logExtentStatus(extentLogger, documentdisplaydeliveryglobalssearch,issueSummary,freewordkey,freewordVal, jiraNumber);

				deliverypage = documentdisplaypage.clickExportButton();
				flag = deliverypage.verifyDocumentExportPage() && deliverypage.enableRadioButton("formato_de_archivo_rtf")
						&& deliverypage.clickAcceptButton(); 
						
//						&& deliverypage.isDeliveryCompleted();
				softas.assertTrue(flag, "Delivery completed for 'list of results' as RTF document (GlobalSearch)");
				logExtentStatus(extentLogger, flag,
						"Delivery completed for 'list of results' as RTF document (GlobalSearch)", jiraNumber);
				//deliverypage.navigateBack();
				driver.navigate().back();
				Thread.sleep(3000);
				deliverypage.clickPrint();
				softas.assertTrue(deliverypage.deliverTextDisplayed(),
						jiraNumber + ":Verify print from docuemnt display with fulldocument (GlobalSearch)");
				logExtentStatus(extentLogger, deliverypage.deliverTextDisplayed(),
						"Verify print from docuemnt display with fulldocument (GlobalSearch)", jiraNumber);
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
	 * MAFAUTO-126 : Verify Legislation - tree - Document Display - Legislation Versions 
	 * [MAFAUTO-126 : Implementation is in progress -Blocked due to data not present.]
	 */
	@Test(priority=23, groups = { "chpmex" }, description = "MAFAUTO-126")
	public void LegislationReformsAndContentTree() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, 
				"Legislation - Document Display - Legislation Versions (Combo Histórico Legislativo)");
		
		try {
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey="thematicarea";
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,thematicareaKey,extentLogger);

				String treeitemsKey="treeitems";
				String  treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,treeitemsKey,extentLogger).split(",");

				String documenttitleKey="documenttitle";
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,documenttitleKey,extentLogger);

				String HistoricVersionkey= "HistoricVersion";
				String HistoricVersion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,HistoricVersionkey,extentLogger);
				
				String finalvaliditykey = "finalvalidity";
				String finalvalidity = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,finalvaliditykey,extentLogger);
				searchpage = homepage.OpenSearchPage();

				// Select Thematic Area & Click on area in tree(if it is not
				// already
				// selected)
				searchpage.selectGivenValueFromThematicDropdown(thematicArea);
				if (searchpage.isAreaFoundInContentTree(thematicArea)) {
					searchpage.selectAreaFromContentTree(thematicArea);
				}

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
				if (docDisplayedInTree)
					documentdisplaypage = searchpage.clickDocumentInSubContentTree(treeElement, documentTitle);
				docDisplayedInTree &= (documentdisplaypage!=null);
				
				softas.assertTrue(docDisplayedInTree,
						jiraNumber + ":Fetching Document from tree view");
				logExtentStatus(extentLogger, docDisplayedInTree,"Fetching Document from tree view",
						documenttitleKey,documentTitle,jiraNumber);
				
				//Verify tooltip message
				boolean latestReformsPresent = documentdisplaypage.isLatestReformsOptionDisplayed()
						&& documentdisplaypage.isRelatedLegislationToolTipPresent();
				softas.assertTrue(latestReformsPresent,
						jiraNumber + ":" + issueSummary + " : Latest Reform option is available with Tootip message");
				logExtentStatus(extentLogger, latestReformsPresent,
						"Latest Reform option is available with Tootip message", jiraNumber);
				//Verify related docs is displayed
				documentdisplaypage.clickLatestReforms();
				
				boolean verifyhistorydate=documentdisplaypage.isPresentHistoryDateLastReforms(HistoricVersion,finalvalidity);
				documentdisplaypage.closeRelatedDocumentPopUp();
				
				softas.assertTrue(verifyhistorydate,
						jiraNumber + ":" + issueSummary + " : Left panel displays History and Date ");
				logExtentStatus(extentLogger, 
						verifyhistorydate, "Left panel displays history and Date",jiraNumber);
				boolean latestReformsLoaded = documentdisplaypage.isLatestReformsLoaded();
				
				softas.assertTrue(latestReformsLoaded,
						jiraNumber + ":" + issueSummary + " : Left panel displays related documents");
				logExtentStatus(extentLogger, 
						latestReformsLoaded, "Left panel displays related documents",jiraNumber);
				//Verify first item is not a link
				boolean firstReformIsNotALink = documentdisplaypage.isFirstItemOnRelatedContentTabALink();
				softas.assertTrue(firstReformIsNotALink,
						jiraNumber + ":" + issueSummary + " : First Item in related Document is not a link");
				logExtentStatus(extentLogger, 
						firstReformIsNotALink, "First Item in related Document is not a link",
						"Item ",documentdisplaypage.readItemOnRelatedContentTab(1),jiraNumber);
				
				//Verify second item is a link
				boolean secondReformIsALink = documentdisplaypage.isFirstItemOnRelatedContentTabALink();
				softas.assertTrue(secondReformIsALink,
						jiraNumber + ":" + issueSummary + " : Second Item in related Document is a link");
				logExtentStatus(extentLogger, secondReformIsALink,"Second Item in related Document is a link",
						"Item ",documentdisplaypage.readItemOnRelatedContentTab(2),jiraNumber);			
				
				
			}
			
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, jiraNumber+": Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	/*
	 * [MAFAUTO-129, 153 : Implementation is in progress - Blocked due to data
	 * not present.]
	 */
	//This needs to be fixed - Jagannath
	//@Test(priority=0,  24, groups = { "Issueinjenkins" })
	public void SectionLinksDisplayedOnDoc() throws Exception {

		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			//String issueSummary = getIssueTitle(jiraNumber, "");

			// Read data for document in tree

			// Read data
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				JSONArray listOfSearchItems = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild,"abbrevation_article",extentLogger);
				String articleNum = listOfSearchItems.get(1).toString();
				JSONArray abbrevationTexts = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild,"abbrevation_field",extentLogger);
				String abbrevationText = abbrevationTexts.get(0).toString();
				searchpage = homepage.OpenSearchPage();
				searchpage.enterAbbrevationText(abbrevationText);
				searchpage.enterArticleNumber(articleNum);

				documentdisplaypage = searchpage.clickOnAbbrevationSearch();
				String marginalText = searchpage.getMarginalText();
				marginalText = searchpage.buildDocumentViewElement(marginalText);

				boolean isExpectedSectionDispalyed = documentdisplaypage
						.isDocumentViewScrolledToGivenArticleNum(marginalText, articleNum);
				softas.assertTrue(isExpectedSectionDispalyed,
						"Document is showing the given article numberat the top of the page");
				logExtentStatus(extentLogger, isExpectedSectionDispalyed,
						"Document is showing the given article numberat the top of the page",
						new String[] { "MAFAUTO-202", "MAFAUTO-129", "MAFAUTO-153" });

				//documentdisplaypage.scrollToGivenElement();
				documentdisplaypage.clickOnCorrelationsIcon(marginalText, Integer.parseInt(articleNum) + 1);

				isExpectedSectionDispalyed = documentdisplaypage.isLegislationBoxDisplayed();
				softas.assertTrue(isExpectedSectionDispalyed,
						"MAFAUTO-129 :Legislation Links displayed on the document correlations section");
				logExtentStatus(extentLogger, isExpectedSectionDispalyed,
						"Legislation Links displayed on the document correlations section",
						new String[] { "MAFAUTO-202", "MAFAUTO-129", "MAFAUTO-153" });

				isExpectedSectionDispalyed = documentdisplaypage.isJurisprudenciasBoxDisplayed();
				softas.assertTrue(isExpectedSectionDispalyed,
						"MAFAUTO-129 :Jurisprudencias Links displayed on the document correlations section");
				logExtentStatus(extentLogger, isExpectedSectionDispalyed,
						"Jurisprudencias Links displayed on the document correlations section",
						new String[] { "MAFAUTO-202", "MAFAUTO-129", "MAFAUTO-153" });

				isExpectedSectionDispalyed = documentdisplaypage.isDoctrinaBoxDisplayed();
				softas.assertTrue(isExpectedSectionDispalyed,
						"MAFAUTO-129: Doctrina Links displayed on the document correlations section");
				logExtentStatus(extentLogger, isExpectedSectionDispalyed,
						"Doctrina Links displayed on the document correlations section",
						new String[] { "MAFAUTO-129", "MAFAUTO-153" });

				// Checking for the tooltip for the section links
				documentdisplaypage.ClickOnLegislationsLink();
				isExpectedSectionDispalyed = documentdisplaypage.isRelationContainerDisplayedForLegislations();
				softas.assertTrue(isExpectedSectionDispalyed,
						"MAFAUTO-130: Clicking on Legislations box displayed relation container with links");
				logExtentStatus(extentLogger, isExpectedSectionDispalyed,
						"Clicking on Legislations box displayed relation container with links",
						new String[] { "MAFAUTO-130", "MAFAUTO-153" });

				String docTitle = documentdisplaypage.getDocumentDisplayTitleLineOne();
				isExpectedSectionDispalyed = documentdisplaypage.isToolTipDisplayed(docTitle);
				softas.assertTrue(isExpectedSectionDispalyed,
						"MAFAUTO-130: Tooltip of the relation container has document title");
				logExtentStatus(extentLogger, isExpectedSectionDispalyed,
						"Tooltip of the relation container has document title",
						new String[] { "MAFAUTO-130", "MAFAUTO-153" });
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "MAFAUTO-129,130,153 : Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority=28, groups = { "chpmex" }, description = "MAFAUTO-238")
	public void toolTipOnDocumentDisplay() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber,"DocDisplay Toll Tip");

		try { 
			boolean searchResultsDisplayed = false;
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				
				String toplinelinkUltimasReformasval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"toplinelinkUltimasReformas",extentLogger);
				String toplinelinkLegislacionRelacionadaval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"toplinelinkLegislacionRelacionada",extentLogger);


				searchpage = homepage.OpenSearchPage();
				searchpage.enterTextInSearchField(freewordVal);

				searchresultspage = searchpage.clickOnSearchButtonDocumentDisplay();
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();

				boolean latestReformsPresent = documentdisplaypage.isLatestReformsOptionDisplayed();
				softas.assertTrue(latestReformsPresent,  issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, latestReformsPresent, "Latest Reform option is  available", 
						freewordkey,freewordVal,jiraNumber);
				/*
				 * if (latestReformsPresent) { boolean name =
				 * documentdisplaypage.tooltipmsgofRelatedLegislation(); }
				 */
				boolean latestReformsToolTipVerified = latestReformsPresent
						&& documentdisplaypage.tooltipmsgofRelatedLegislation();
				softas.assertTrue(latestReformsToolTipVerified, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, latestReformsToolTipVerified, "Tool Tip for Latest Reform  is  available",
						jiraNumber);

				boolean RelatedLegislation = documentdisplaypage.isRelatedLegislationOptionDisplayed();
				softas.assertTrue(RelatedLegislation,  issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, RelatedLegislation, "Related Legislation option is  available", jiraNumber);

				boolean RelatedLegislationTollTipVerified = RelatedLegislation
						&& documentdisplaypage.tooltipmsgofRelatedLegislation();
				softas.assertTrue(RelatedLegislationTollTipVerified,  issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, RelatedLegislationTollTipVerified,
						"Tool Tip for RelatedLegislation is  available", jiraNumber);

				softas.assertTrue(latestReformsPresent,  issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, latestReformsPresent, "Tool Tip for Related Legislation option is  available",
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

	@Test(priority=29,   groups = { "chpmex" }, description = "MAFAUTO-214")
	public void displayOfTabOnFormsSearch() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());


		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber,"Official Forms - Document Display - Validate Display Tabs");

		try {
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey="freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);
				String ExpectedFormKey="ExpectedForm";
				String ExpectedFormVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,ExpectedFormKey,extentLogger);
				
				String ExpectedFormVal2 = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"ledtfa",extentLogger);
				System.out.println(ExpectedFormVal2);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(3000);
				FormsPage formspage = searchpage.openFormsPage();
				Thread.sleep(3000);
				formspage.enterFreeWordOnSearchPage(freewordVal);
				SearchResultsPage searchResultsPage = formspage.clickOnSearch();

				boolean formsTitleDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.isFormstitleDisplayed();

				softas.assertTrue(formsTitleDisplayed, "Official Forms - Result List - Display Forms title");
				logExtentStatus(extentLogger, formsTitleDisplayed, "Official Forms - Result List - Display Forms title",
						freewordKey,freewordVal,jiraNumber);

				DocumentDisplayPage documentPage = searchResultsPage.getFirstDocument();
				Thread.sleep(3000);
				boolean expectedvalue=documentPage.isExpectedFormAvaliable(ExpectedFormVal);
				Thread.sleep(3000);
				boolean expectedTabsFound = documentPage.isRelatedContentTabDisplayed()
						&& documentPage.resultListTabDisplayed() && documentPage.isRevOfSearchTabDisplayed();

				softas.assertTrue(expectedvalue, issueSummary);
				logExtentStatus(extentLogger, expectedvalue,expectedTabsFound+
						": <br> ExpectedForm Value found",jiraNumber);
				JSONArray listOfData = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, "LeftpanelExpected", extentLogger);
				
					boolean expectleftpanelvalue=documentPage.verifyLeftPanelText(listOfData);
					
					Thread.sleep(3000);
				boolean tableOfContentRemoved = documentPage.isTableOfContentsNotVisible();
				softas.assertTrue(tableOfContentRemoved,
						"Official Forms - Document Display - Validate 'Table of Content' Tab is removed");
				logExtentStatus(extentLogger, tableOfContentRemoved,
						"Official Forms - Document Display - Validate 'Table of Content' Tab is removed", jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			Assert.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();

		}
	}

	// MAFAUTO-229 : Legislation - Reform's Analysis for Federal Legislation
	@Test(priority=30,   groups = { "chpmex" }, description = "MAFAUTO-229")
	public void reformsAnalysisForLegislation() throws Exception {
		try {
			DocumentDisplayPage docDisplayPage = null;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();

			extentLogger = setUpExtentTest(extentLogger, "search", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Legislation - Reform's Analysis for Federal Legislation");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,thematicareaKey,extentLogger);
				String documentKey = "documentname";
				String reformsdocument = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,documentKey,extentLogger);
				String treeitemsKey = "treeitems";
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,treeitemsKey,extentLogger).split(",");
				String Articlevalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"Article",extentLogger);
				
				String grayboxvalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"graybox",extentLogger);


				searchpage = homepage.OpenSearchPage();

				// Select Thematic Area & Click on area in tree(if it is not
				// already
				// selected)
				searchpage.selectGivenValueFromThematicDropdown(thematicArea);
				if (searchpage.isAreaFoundInContentTree(thematicArea)) {
					searchpage.selectAreaFromContentTree(thematicArea);
				}

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
				boolean docDisplayedInTree = searchpage.isItemPresentInSubContentTree(treeElement, reformsdocument);
				if (docDisplayedInTree)
					docDisplayPage = searchpage.clickDocumentInSubContentTree(treeElement, reformsdocument);

				softas.assertTrue(docDisplayedInTree && docDisplayPage != null, "Document fetched from tree content");
				logExtentStatus(extentLogger, docDisplayedInTree && docDisplayPage != null,
						"Document fetched from tree content",documentKey,reformsdocument, jiraNumber);

				// Verify Reformas table for article 1
				boolean reformasTableValidated = false;
			//	if (docDisplayPage != null) {
				boolean value=	docDisplayPage.isPresentArtcileReforms(Articlevalue);
				
					softas.assertTrue(value,jiraNumber+":"+issueSummary);
					logExtentStatus(extentLogger, value,issueSummary,documentKey, reformsdocument, jiraNumber);

			//	}

				Thread.sleep(4000);
				
				reformasTableValidated =  docDisplayPage.isReformasTableHeaderDisplayedForArticle(grayboxvalue);
				softas.assertTrue(reformasTableValidated,jiraNumber+":"+issueSummary);
				logExtentStatus(extentLogger, reformasTableValidated,issueSummary,documentKey, reformsdocument, jiraNumber);
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
	 * MAFAUTO-230 : Legislation - Document Display - Add in main docs a top
	 * line link at document level that will display most important related
	 * docs.
	 */
	@Test(priority=31, groups = { "chpmex" }, description = "MAFAUTO-230")
	public void relatedDocsForLegislation() throws Exception {

		try {
			DocumentDisplayPage docDisplayPage = null;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();

			extentLogger = setUpExtentTest(extentLogger, "search", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Legislation - Document Display - Top line link that will display important related docs.");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,thematicareaKey,extentLogger);
				String documentKey = "documentname";
				String document = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,documentKey,extentLogger);
				String treeitemsKey = "treeitems";
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,treeitemsKey,extentLogger).split(",");

				//All expected links to verify in page 1
				String relatedlinksOneKey = "relatedlinksonpageone";
				JSONArray relatedlinksOneJSONArray = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, relatedlinksOneKey, extentLogger);
				String pageOneLInks[] =new String[relatedlinksOneJSONArray.size()];
				for(int row=0;row<relatedlinksOneJSONArray.size();row++)
					pageOneLInks[row] = relatedlinksOneJSONArray.get(row).toString();

				//All expected links to verify in page 2
				String relatedlinksTwoKey = "relatedlinksonpagetwo";
				JSONArray relatedlinksTwoJSONArray = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, relatedlinksTwoKey, extentLogger);
				String pageTwoLInks[] =new String[relatedlinksTwoJSONArray.size()];
				for(int row=0;row<relatedlinksTwoJSONArray.size();row++)
					pageTwoLInks[row] = relatedlinksTwoJSONArray.get(row).toString();

				// Expected link to select & navigate
				String relatedLegislationLink = pageTwoLInks[1];


				searchpage = homepage.OpenSearchPage();

				// Select Thematic Area & Click on area in tree(if it is not
				// already
				// selected)
				searchpage.selectGivenValueFromThematicDropdown(thematicArea);
				if (searchpage.isAreaFoundInContentTree(thematicArea)) {
					searchpage.selectAreaFromContentTree(thematicArea);
				}

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
				boolean docDisplayedInTree = searchpage.isItemPresentInSubContentTree(treeElement, document);
				if (docDisplayedInTree)
					docDisplayPage = searchpage.clickDocumentInSubContentTree(treeElement, document);
				boolean documentFetched = docDisplayedInTree && docDisplayPage != null;
				softas.assertTrue(documentFetched, "Document fetched from tree content");
				logExtentStatus(extentLogger, documentFetched,
						"Document fetched from tree content",documentKey, document, jiraNumber);

				// Validate expected links in related legislation list
				boolean relatedContentDisplayed = documentFetched
						&& docDisplayPage.isRelatedLegislationDisplayedInLeftPane();
				boolean relatedLegislationPage1Validated = relatedContentDisplayed;
				boolean relatedLegislationPage2Validated = relatedContentDisplayed;
				if (relatedContentDisplayed) {
					for (int row = 0; row < pageOneLInks.length; row++) {
						relatedLegislationPage1Validated = docDisplayPage
								.isRelatedLegislationLinkExist(pageOneLInks[row]);
						if (!relatedLegislationPage1Validated)
							break;
					}
					// Go to page 2 of related legislation list
					docDisplayPage.clickRelatedLegislationLink("2");
					for (int row = 0; row < pageTwoLInks.length; row++) {
						relatedLegislationPage2Validated = docDisplayPage
								.isRelatedLegislationLinkExist(pageTwoLInks[row]);
						if (!relatedLegislationPage2Validated)
							break;
					}
				}

				boolean relatedLegislationLinksValidated = relatedLegislationPage1Validated
						&& relatedLegislationPage2Validated;

				softas.assertTrue(relatedLegislationPage1Validated, "Verify links for all Related Legislations in page One");
				logExtentStatus(extentLogger, relatedLegislationLinksValidated,"Verify links for all Related Legislations in page One",
						relatedlinksOneKey,relatedlinksOneJSONArray.toString(), jiraNumber);

				softas.assertTrue(relatedLegislationPage1Validated, "Verify links for all Related Legislations in page Two");
				logExtentStatus(extentLogger, relatedLegislationLinksValidated,"Verify links for all Related Legislations in page Two",
						relatedlinksTwoKey,relatedlinksTwoJSONArray.toString(), jiraNumber);

				// Click on LAMP link in related legislation list & verify
				// pop-up document
				boolean relatedDocPopUpValidated = false;
				if (relatedContentDisplayed) {
					docDisplayPage.clickRelatedLegislationLink(relatedLegislationLink);
					relatedDocPopUpValidated = docDisplayPage.isRelatedDocumentPopUpDisplayed()
							&& docDisplayPage.isEqualsRelatedDocumentPopUpTitleAbbrev(relatedLegislationLink)
							&& docDisplayPage.closeRelatedDocumentPopUp();
				}
				softas.assertTrue(relatedDocPopUpValidated,issueSummary+": Related document fetched " + relatedLegislationLink);
				logExtentStatus(extentLogger, relatedDocPopUpValidated,issueSummary,
						"Related document fetched ",relatedLegislationLink, jiraNumber);

				boolean relatedInfoToolTip = docDisplayPage.isRelatedLegislationToolTipPresent();
				softas.assertTrue(relatedInfoToolTip, "Verify related legislation tool tip for top link");
				logExtentStatus(extentLogger, relatedInfoToolTip, "Verify related legislation tool tip for top link",
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

	//MAFAUTO -131 takes care of the same test case but with different test data link to click.
	//Updated the MAFAUTO-127 test data with the test data and hence different test case is not written for the MAFAUTO-131
	@Test(priority = 0, groups = { "chpmex" }, description = "MAFAUTO-127")
	public void documentValidationForArticleAndVoces() throws Exception {
		softas = new SoftAssert();
		testResult = Reporter.getCurrentTestResult();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber,"Tesaurus at article level with links to article, alphabetical thematic index");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicKey = "thematic";
				String thematicVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicKey, extentLogger);

				String linktoclickonresultsKey = "linktoclickonresults";
				String linktoclickonresultsVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, linktoclickonresultsKey, extentLogger);
				
				String thematicAreakey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicAreakey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				
				searchpage.selectGivenValueFromThematicDropdown(thematicarea);
				if (searchpage.isAreaFoundInContentTree(thematicarea)) {
					searchpage.selectAreaFromContentTree(thematicarea);
				}

				legislationpage = searchpage.OpenLegislationPage();
				Thread.sleep(5000);
				
				legislationpage.enterThematicOnSearchPage(thematicVal);
				Thread.sleep(2000);
				searchresultspage = legislationpage.clickOnSearch();

				boolean searchResultsDisplayed = searchresultspage != null
						&& searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound) {
						softas.assertTrue(true,
								jiraNumber + ":-resulted in no search results" + " :" + thematicVal);
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", thematicKey,
								thematicVal, jiraNumber);
					} else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				//click on the given link on the results set 
				
				documentdisplaypage = searchresultspage.clickOnGivenDocumentToClickOnResultsmatches(linktoclickonresultsVal);
				String marginalText = documentdisplaypage.getMarginalText();
				marginalText = documentdisplaypage.formatMarginalTextForSubmissionAsLocator(marginalText);
				boolean isExpectedArticleDisplayed = documentdisplaypage.isDocumentViewScrolledToGivenArticleNum(marginalText, linktoclickonresultsVal.split("\\.")[1]);			

				softas.assertTrue(isExpectedArticleDisplayed,issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isExpectedArticleDisplayed,"Document is scrolled to expected article section","Correlations icon for article ", linktoclickonresultsVal, jiraNumber);

				//verify if the search term is displayed on the voces box on the left panel
				documentdisplaypage.clickonVoces();
			boolean isTextDisplayedOnVocesBox = documentdisplaypage.isGivenSearchTextOnTheVocesSection(thematicVal);
			
				
				softas.assertTrue(isTextDisplayedOnVocesBox,issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isTextDisplayedOnVocesBox,"Search term is displayed on the voces box on left panel", thematicKey, thematicVal, jiraNumber);

				//returning to search results
				searchresultspage = documentdisplaypage.clickOnListOfDocumentsToReturnToSearchResults();
				searchResultsDisplayed = searchresultspage != null
						&& searchresultspage.searchResultsHeaderContainerDisplayed();

				softas.assertTrue(searchResultsDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, searchResultsDisplayed,"User is able to return back to search results", jiraNumber);

				//verify if the previously selected document row is highlighted upon returning back to search results

				searchResultsDisplayed = searchresultspage.isLastDocumentViewedHighlightDisplayedOnResults();
				softas.assertTrue(searchResultsDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, searchResultsDisplayed,"Upon returning back to results last document viewed highlighted row is displayed", jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);					
			softas.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	

	/*@Test(priority = 0, groups = { "chpmex" }, description = "MAFAUTO-128")
	public void typesOfRelationShipsOnDocument() throws Exception {
		softas = new SoftAssert();
		testResult = Reporter.getCurrentTestResult();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, 
					"Tesaurus at article level with links to article, alphabetical thematic index");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String abbreviationKey = "abbreviation";
				String abbreviationVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, abbreviationKey, extentLogger);
				
				String itemnumberKey = "itemnumber";
				String itemnumberVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, itemnumberKey, extentLogger);
				
				String linktoclickondocKey = "linktoclickondoc";
				String linktoclickondocVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, linktoclickondocKey, extentLogger);
				
				String relationshiplinktextKey = "relationshiplinktext";
				String relationshiplinktextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, relationshiplinktextKey, extentLogger);
				 
				
				
				searchpage = homepage.OpenSearchPage();
				searchpage.enterAbbrevationText(abbreviationVal);
				searchpage.enterArticleNumber(itemnumberVal);
				documentdisplaypage = searchpage.clickOnAbbrevationSearch();
				Thread.sleep(4000);
			
				
				
				boolean documentPageDisplayed = documentdisplaypage.isDocumentDisplayed();
				if (!documentPageDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound) {
						softas.assertTrue(true,
								jiraNumber + ":-resulted in no search results" + " :" + abbreviationVal);
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", abbreviationKey,
								abbreviationVal, jiraNumber);
					} else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}
				
				String marginalText = documentdisplaypage.getMarginalText();
				marginalText = documentdisplaypage.formatMarginalTextForSubmissionAsLocator(marginalText);
				boolean isExpectedArticleDisplayed = documentdisplaypage.isDocumentViewScrolledToGivenArticleNum(marginalText, itemnumberVal);			
				
				softas.assertTrue(isExpectedArticleDisplayed, jiraNumber + ":" + "Document is scrolled to expected article");
				logExtentStatus(extentLogger, isExpectedArticleDisplayed,"Document is scrolled to expected article section","Correlations icon for article ", itemnumberVal, jiraNumber);
							
				//click on the correlacions icon
				documentdisplaypage.clickOnCorrelationsIcon(marginalText, Integer.parseInt(itemnumberVal));
				boolean isGivenLinkDisplayed = documentdisplaypage.isGivenLinkDisplayed(linktoclickondocVal);
				if(isGivenLinkDisplayed)
				{
					//Clicking on the Reform link on the section
					documentdisplaypage.clickOnGivenLinkByLinkText(linktoclickondocVal, true);
					
					//Clicking on the relationship link 
					documentdisplaypage.clickOnGivenLinkByLinkText(relationshiplinktextVal,false);
				}			
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);					
			softas.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}*/
	
	@Test(priority = 0, groups = { "chpmex" }, description = "MAFAUTO-130")
	public void relationshipAtParagraphLevel() throws Exception {
		softas = new SoftAssert();
		testResult = Reporter.getCurrentTestResult();
		extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		try {
			String issueSummary = getIssueTitle(jiraNumber, 
					"Verify adding relationship at the paragraph level on document");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String abbreviationKey = "abbreviation";
				String abbreviationVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, abbreviationKey, extentLogger);
				
				String itemnumberKey = "itemnumber";
				String itemnumberVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, itemnumberKey, extentLogger);

				String expectedlinkKey = "expectedlink";
				String expectedlinkVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expectedlinkKey, extentLogger);
				
				String expectedtooltipKey = "expectedtooltip";
				String expectedtooltipVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expectedtooltipKey, extentLogger);
				
				searchpage = homepage.OpenSearchPage();
				searchpage.enterAbbrevationText(abbreviationVal);
				searchpage.enterArticleNumber(itemnumberVal);
				documentdisplaypage = searchpage.clickOnAbbrevationSearch();
				
				boolean documentPageDisplayed = documentdisplaypage.isDocumentDisplayed();
				if (!documentPageDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound) {
						softas.assertTrue(true,
								jiraNumber + ":-resulted in no search results" + " :" + abbreviationVal);
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", abbreviationKey,
								abbreviationVal, jiraNumber);
					} else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}
				
				//click on the  relaciones de Legislación ( Legislation Relations)
				documentdisplaypage.clickOnRelatedLegislation();
				boolean isExpectedAbreviaturaLinkDisplayed = documentdisplaypage.isGivenLinkDisplayed(expectedlinkVal);
							
				softas.assertTrue(isExpectedAbreviaturaLinkDisplayed, jiraNumber + ":" + "Upon selecting Legislation relations expected Abbreviation is displayed");
				logExtentStatus(extentLogger, isExpectedAbreviaturaLinkDisplayed,"Upon selecting Legislation relations expected Abbreviation is displayed",expectedlinkKey, expectedlinkVal, jiraNumber);
							
				//Verify the tool tip on the link
				boolean isExpectedToolTipDisplayed = documentdisplaypage.isGivenToolTipDisplayed(expectedtooltipVal, expectedlinkVal);
				softas.assertTrue(isExpectedToolTipDisplayed, jiraNumber + ":" + "Expected Tooltip displayed on the link");
				logExtentStatus(extentLogger, isExpectedToolTipDisplayed,"Expected Tooltip displayed on the link",expectedlinkVal, expectedtooltipVal, jiraNumber);						
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);					
			softas.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
		
//MAFAUTO-241	
	@Test(priority=28, groups = { "chpmex" }, description = "MAFAUTO-241")
	public void toolTipOnDocumentDisplayforWithoutEffect() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber,"DocDisplay Toll Tip for Without Effect");

		try { 
			boolean searchResultsDisplayed = false;
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String layoutnamekey = "layoutnamesearch";
				String layoutnameVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,layoutnamekey,extentLogger);
				
				String tooltipmsgKey = "tooltipmsg";
				String tooltipmsgVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,tooltipmsgKey,extentLogger);
				
				String thematicdrpdownKey = "thematicdropdown";
				String thematicdrpdownVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,thematicdrpdownKey,extentLogger);
				
				
				String SearchDocument=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"SearchDocument",extentLogger);
				

				searchpage = homepage.OpenSearchPage();
				searchpage.selectThematicArea(thematicdrpdownVal);
				Thread.sleep(3000);
				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.enterSearchinLayoutName(layoutnameVal);
				searchresultspage = legislationpage.clickOnSearch();

				
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", layoutnamekey,
								layoutnameVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();
				Thread.sleep(3000);

				boolean withoutEffectPresent = documentdisplaypage.isWithoutEffectOptionDisplayed();
				softas.assertTrue(withoutEffectPresent,  issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, withoutEffectPresent, "Without Effect option is  available", 
						layoutnamekey,layoutnameVal,jiraNumber);
				Thread.sleep(3000);
				boolean withoutEffectToolTipMsgVerified = withoutEffectPresent
						&& documentdisplaypage.tooltipmsgofWithoutEffect(tooltipmsgVal);
				softas.assertTrue(withoutEffectToolTipMsgVerified,issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, withoutEffectToolTipMsgVerified, "Tool Tip option is available for without Effect",
						jiraNumber);
				Thread.sleep(3000);
				documentdisplaypage.clickWithoutEffect();
				Thread.sleep(2000);
				boolean isTextdisplayed =documentdisplaypage.isTextdisplayforWithoutEffect();
				softas.assertTrue(isTextdisplayed,issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isTextdisplayed, " Without Effect text is  available in left panel",
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
	
	@Test(priority = 10, groups = {"chpmex"},description="MAFAUTO-235")
	public void popUpRelationOfCorelation() throws Exception {

		SoftAssert softas = new SoftAssert();	

		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Validate Content Tree and Corelation POP UP ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"documenttitle",extentLogger); 
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String articleNumber=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"articleno",extentLogger);
				String adicionestextVal=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"adicionestext",extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"treeitems",extentLogger).split(",");


				homepage = homepage.openHomepage();
				Thread.sleep(2000);
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
				Thread.sleep(2000);
				//Open document from the tree
				boolean docDisplayedInTree = searchPage.isItemPresentInSubContentTree(treeElement,documentTitle);
				if(docDisplayedInTree){
					documentdisplaypage = searchPage.clickDocumentInSubContentTree(treeElement,documentTitle);
				}
				Thread.sleep(2000);
				docDisplayedInTree &= (documentdisplaypage!=null);
				softas.assertTrue(docDisplayedInTree,jiraNumber+" : "+issueSummary+" : Document Display");
				logExtentStatus(extentLogger, docDisplayedInTree, issueSummary+" : Document Display", jiraNumber);
				Thread.sleep(2000);
				String marginalText = documentdisplaypage.getMarginalText();
				documentdisplaypage.clickCorrelationForTitleInline(marginalText,articleNumber);
				boolean isAdicionesTabDisplayed=documentdisplaypage.isAdicionesTabDisplayed();
				documentdisplaypage.clickAdicionesTabDisplayed();
				/*if (isAdicionesTabDisplayed)
				{
					documentdisplaypage.clickAdicionesTabDisplayed();
				}*/
				Thread.sleep(2000);
				boolean isAddtionLinksDisplay=documentdisplaypage.isLinkofAdicionesTabDisplayed(adicionestextVal);
				softas.assertTrue(isAddtionLinksDisplay,jiraNumber+" : "+issueSummary);
				logExtentStatus(extentLogger, isAddtionLinksDisplay,"Link is present for adiciones", jiraNumber);
				Thread.sleep(2000);
				documentdisplaypage.clickAdditionLinkOfAdiciones();
				Thread.sleep(2000);
				boolean isPopUpdisplay=documentdisplaypage.isadicionesPopDisplay();
				softas.assertTrue(isPopUpdisplay,jiraNumber+" : "+issueSummary);
				logExtentStatus(extentLogger, isPopUpdisplay, "Pop UP for adiciones is displayed", jiraNumber);
						
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority = 11, groups = {"chpmex"},description="MAFAUTO-252")
	public void corelationVersionPopupComparison() throws Exception {
		SoftAssert softas = new SoftAssert();	
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Document Display", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Legislation - Tooltip - Add Help icon when versions of articles are compared");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"documenttitle",extentLogger); 
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String articleNumber=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"articleno",extentLogger);
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

				//Open document from the tree
				boolean docDisplayedInTree = searchPage.isItemPresentInSubContentTree(treeElement,documentTitle);
				if(docDisplayedInTree){
					documentdisplaypage = searchPage.clickDocumentInSubContentTree(treeElement,documentTitle);
				}
				docDisplayedInTree &= (documentdisplaypage!=null);
				softas.assertTrue(docDisplayedInTree,jiraNumber+" : "+issueSummary+" : Document Display");
				logExtentStatus(extentLogger, docDisplayedInTree, issueSummary+" : Document Display", jiraNumber);
				
				String marginalText = documentdisplaypage.getMarginalText();
				documentdisplaypage.clickCorrelationForArticleNo(marginalText,articleNumber);
				boolean isVersionTabDisplayed=documentdisplaypage.isVersionsDisplayedForCorrelation(marginalText,articleNumber);
				
				if (isVersionTabDisplayed)
				{
					documentdisplaypage.clickVersionsDisplayedForCorrelation(marginalText,articleNumber);
				}
				
				
				documentdisplaypage.clickAllVersions();
				documentdisplaypage.clickCompareAllVersion();
				
				boolean ispopupdisplayed =documentdisplaypage.isPopUpforVersionsDisplayed();
				if(ispopupdisplayed)
				{
				documentdisplaypage.clickonPopUpComOverTip();
				}
				
				//need to be checked from here
				documentdisplaypage.verifyAddedText();
				documentdisplaypage.closePopUp();
				softas.assertTrue(ispopupdisplayed,jiraNumber+" : "+issueSummary);
				logExtentStatus(extentLogger, ispopupdisplayed, "Pop UP for adiciones is displayed", jiraNumber);
						
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=28, groups = { "chpmex" }, description = "MAFAUTO-243")
	public void footNotesInDocDisplay() throws Exception {
		SoftAssert softas = new SoftAssert();	

		try {
			boolean searchResultsDisplayed = false;
				testResult = Reporter.getCurrentTestResult();
				extentLogger = setUpExtentTest(extentLogger, "Content Tree", testResult.getMethod().getMethodName());

				String jiraNumber = testResult.getMethod().getDescription();

				String issueSummary = getIssueTitle(jiraNumber, "Validate Content Tree and Corelation POP UP ");

				JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

				for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String rubrosearchkey = "rubrosearch";
				String rubrosearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,rubrosearchkey,extentLogger);
				
				String thematicdrpdownKey = "thematicdropdown";
				String thematicdrpdownVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,thematicdrpdownKey,extentLogger);
                String footnote=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"footnote",extentLogger);
                String Title=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"Title",extentLogger);
                String value=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"value",extentLogger);
				searchpage = homepage.OpenSearchPage();
				searchpage.selectThematicArea(thematicdrpdownVal);
				Thread.sleep(2000);
				jurisprudencePage = searchpage.openJurisprudencePage();
				jurisprudencePage.enterRubroValue(rubrosearchVal);
				searchresultspage = jurisprudencePage.clickOnSearch();

				
				searchResultsDisplayed = searchresultspage!=null && searchresultspage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", rubrosearchkey,
								rubrosearchVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}
				documentdisplaypage = searchresultspage.clickFirstLink();
				boolean verifyresult=documentdisplaypage.isPresentExpectedDocument(value);
				softas.assertTrue(verifyresult,jiraNumber+" : "+issueSummary);
				logExtentStatus(extentLogger, verifyresult, "Application positions the text of the footnote under the header of the document ", jiraNumber);
				documentdisplaypage.clickFirstStarFootnote();
				boolean secondFootStarVisible = documentdisplaypage.isSecondStarFootnoteVisible();
				softas.assertTrue(secondFootStarVisible,jiraNumber+" : "+issueSummary);
				logExtentStatus(extentLogger, secondFootStarVisible, "Paragraph end Star footnote is visible after clicking on Star inside the paragraph", jiraNumber);
				
				documentdisplaypage.clickOnSecondStarFootnote();
				boolean firstFootStarVisible = documentdisplaypage.isFirstStarFootnoteVisible();
				softas.assertTrue(firstFootStarVisible,jiraNumber+" : "+issueSummary);
				logExtentStatus(extentLogger, firstFootStarVisible, "Paragraph  Star footnote is visible after clicking on star footnote at the end of paragraph", jiraNumber);
				
				
				documentdisplaypage.clickFirstOneFootnote();
				boolean secondOneFootNoteVisible = documentdisplaypage.isSecondOneFootnoteVisible();
				softas.assertTrue(secondOneFootNoteVisible,jiraNumber+" : "+issueSummary);
				logExtentStatus(extentLogger, secondOneFootNoteVisible, "Application positions the text of the footnote under the header of the document ", jiraNumber);
				
				documentdisplaypage.clickOnSecondOneFootnote();
				boolean firstoneFootNoteVisible = documentdisplaypage.isFirstOneFootnoteVisible();
				softas.assertTrue(firstoneFootNoteVisible,jiraNumber+" : "+issueSummary);
				logExtentStatus(extentLogger, firstoneFootNoteVisible, " Application positions the text of the document where the foot call is located below the document ", jiraNumber);
				
				
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}
	
	@Test(priority = 11, groups = {"chpmex"},description="MAFAUTO-247")
	public void withScrollValdiateHeaderTitle() throws Exception {
		SoftAssert softas = new SoftAssert();	
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "DocumentDisplay", testResult.getMethod().getMethodName());
			boolean linksdisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Legislación pop-up views with automatic chunking");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"documenttitle",extentLogger); 
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String articleNumber=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"articleno",extentLogger);
				String hiperlink=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"hiperlink",extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"treeitems",extentLogger).split(",");
				String linkavailableKey = "linksavailable";
				JSONArray linksavailable = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, linkavailableKey, extentLogger);
				
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
				if(docDisplayedInTree){
					documentdisplaypage = searchPage.clickDocumentInSubContentTree(treeElement,documentTitle);
				}
				docDisplayedInTree &= (documentdisplaypage!=null);
				softas.assertTrue(docDisplayedInTree,jiraNumber+" : "+issueSummary+" : Document Display");
				logExtentStatus(extentLogger, docDisplayedInTree, issueSummary+" : Document Display", jiraNumber);
				
				//validating the links available in left pane
				for(int i=0;i<linksavailable.size();i++)
				{
					linksdisplayed = documentdisplaypage.isLinkPresent(linksavailable.get(i).toString());
					if(!linksdisplayed)
					{
						break;
					}
					
				}
				softas.assertTrue(linksdisplayed,jiraNumber+" : "+ "all sub links are present in the left section");
				logExtentStatus(extentLogger, linksdisplayed, "all sub links are present in the left section", jiraNumber);
				
				documentdisplaypage.clickLeftPaneLink(hiperlink);
				boolean isheadingdispalyedatfirst = documentdisplaypage.isPopUpTitleDisplayed();
				softas.assertTrue(isheadingdispalyedatfirst,jiraNumber+" : "+ "heading displayed when pop up opened");
				logExtentStatus(extentLogger, isheadingdispalyedatfirst, "heading displayed when pop up opened", jiraNumber);
				
				String marginalText = documentdisplaypage.getMarginalTextfromPopUp();
				documentdisplaypage.scrollTillArticleNumber(marginalText.replaceAll("\\\\", "-"),articleNumber);
				boolean isheadingdispalyedlater = documentdisplaypage.isPopUpTitleDisplayed();
				softas.assertTrue(isheadingdispalyedlater,jiraNumber+" : "+ "heading displayed when scrolled to particular article number");
				logExtentStatus(extentLogger, isheadingdispalyedlater, "heading displayed when scrolled to particular article number", jiraNumber);
				
				
				documentdisplaypage.closePopUp();
				softas.assertTrue(isheadingdispalyedatfirst && isheadingdispalyedlater,jiraNumber+" : "+issueSummary);
				logExtentStatus(extentLogger, isheadingdispalyedatfirst && isheadingdispalyedlater,issueSummary, jiraNumber);
						
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
