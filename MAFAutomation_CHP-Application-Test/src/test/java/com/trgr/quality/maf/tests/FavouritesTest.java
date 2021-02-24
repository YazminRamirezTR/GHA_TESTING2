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
import com.trgr.quality.maf.pages.ContenttreeOnsearchResultPage;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.FavouritePage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.MyDocumentPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class FavouritesTest extends BaseTest {

	/*
	 * Module Name : Favorites Feature Names : Description Favorites_TC_003 :
	 * Favourites_Verify 'Add to Favorites' link
	 */

	LoginPage loginpage;
	HomePage homepage,homepagecopy;
	SearchResultsPage searchResultsPage;
	FavouritePage favouritepage;
	DocumentDisplayPage documentdisplaypage;
	MyDocumentPage mydocumentpage;
	Boolean flag = false;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {

		try{
			loginpage = new LoginPage(driver, ProductUrl);		
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
			homepage = loginpage.Login(username, password);
			homepagecopy = this.homepage;
			jsonReader = new JsonReader();

		}catch(Exception e)
		{
			extentLogger = setUpExtentTest(extentLogger, "Favorites", "StartFavouritesTest");			
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the Favorites test are not run.<br>"+ takesScreenshot_Embedded()+ "<br>"+displayErrorMessage(e));
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


	
	
	@Test(groups = {"chparg","chpury" ,"chppe","chpchile"}, description = "MAFQABANG-119")
	public void addDocumentsToFavorites() throws Exception {
		softas = new SoftAssert();	
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Favorites", testResult.getMethod().getMethodName());		
			boolean searchResultsDisplayed=false;
			String folderName;
			
			String jiraNumber = testResult.getMethod().getDescription();		
			String issueSummary = getIssueTitle(jiraNumber,"Common Document Display Features - Document Display - Add to Favourites"); 		

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="freeword";
				String freewordvalue= jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				folderName = "Fav" + RandomUtils.getUniqueNumber();
			
				homepage=homepage.openHomepage();
				homepage.clickRefreshforThematicSearch();
				homepage.enterFreewordOnQuickSearch(freewordvalue);
				searchResultsPage = homepage.clickSearch();
				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordvalue, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}
				documentdisplaypage=searchResultsPage.getFirstDocument();				
				
				documentdisplaypage.ClickOnAddDocumentLinkToFavorite();
				documentdisplaypage.enterFavoritesName(folderName);
				documentdisplaypage.clickOnSaveOnFavoritesPopUp();
				documentdisplaypage.clickOnAlertPopUP();
				
						
				homepage = documentdisplaypage.returnToHomePage();
				mydocumentpage = homepage.openMyDocumentsPage();

				//verify document added to favorites from  document display
				boolean isDocumentAdded=mydocumentpage.isFolderExistInMyDocumentFolder(folderName);
				softas.assertTrue(isDocumentAdded,jiraNumber+":"+issueSummary);
				logExtentStatus(extentLogger, isDocumentAdded, issueSummary, jiraNumber);
				if(!isDocumentAdded){//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-278");
				}
			
				if(isDocumentAdded)
				{
					mydocumentpage.deleteSubFolderInMyDocument(folderName);
				}

			}
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	
	@Test(groups = {"chpbr" }, description = "MAFAUTO-179")
	public void addToFavorites() throws Exception {
		boolean searchResultsDisplayed=false;
		boolean isdocumentalreadyexist=false;
		
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Favorites", testResult.getMethod().getMethodName());		
		String jiraNumber = testResult.getMethod().getDescription();		
		String issueSummary = getIssueTitle(jiraNumber,"Common Document Display Features - Document Display - Add to Favourites"); 		

		try {
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordkey="freeword";
				String freewordvalue= jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordkey,extentLogger);
				
				String expectedErrorKey="errorpopupmessage";
				String expectedError= jsonReader.readKeyValueFromJsonObject(jsonObjectChild,expectedErrorKey,extentLogger);
				
				homepage=homepage.openHomepage();
				homepage.clickRefreshforThematicSearch();
				homepage.enterFreewordOnQuickSearch(freewordvalue);
				//below step needs to be used for chpmex
				//homepage.enterTextInSearchField(freewordvalue);
				searchResultsPage = homepage.clickSearch();
				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordkey,
								freewordvalue, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}
				documentdisplaypage=searchResultsPage.getFirstDocument();	
				String documenttitle=documentdisplaypage.getDocumentDisplayTitleLineOne();
				
				documentdisplaypage.ClickOnAddDocumentLinkToFavorite();
				documentdisplaypage.clickOnSaveOnFavoritesPopUp();
				
				boolean errorPopUpDisplayed =documentdisplaypage.isAddtoFavouriteErrorPopUpPresent();
				String actualError ="";
				
				if(errorPopUpDisplayed){
					actualError =	documentdisplaypage.getErrorMessageFromAddtoFavouriteErrorPopUp();
					documentdisplaypage.clickOKinAddtoFavouriteErrorPopUp();
					isdocumentalreadyexist = (actualError!=null)
							&& actualError.equals(expectedError);
					
				}
				
				if(isdocumentalreadyexist){
					softas.assertTrue(isdocumentalreadyexist,jiraNumber+":"+"Document already added to the MyDocument Folder");
					logExtentStatus(extentLogger, isdocumentalreadyexist, issueSummary, "Document already added to the MyDocument Folder");
				}

				//return back to search results page. Naviagting to home page is failing 100% of the times on jenkins
				homepage = documentdisplaypage.clickOnNewSearchLink();
				
				mydocumentpage = homepage.openMyDocumentsPage();
				//verify document added to favorites from  document display
				boolean isDocumentAdded=mydocumentpage.isGivenDocumentSavedInFolder(documenttitle);
				softas.assertTrue(isDocumentAdded,jiraNumber+":"+issueSummary);
				logExtentStatus(extentLogger, isDocumentAdded, "Doc with title is added: "+ documenttitle, jiraNumber);
						
				//clean up after the test 
				if(isDocumentAdded)
				{
					mydocumentpage.checkSpecificFolder(documenttitle);
					mydocumentpage.clickOnActionManager();
					mydocumentpage.clickDeleteLink();
				}
			}
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	//this particular test case is applicable only for chpmex Application(23/10/2018)
	
	@Test(groups = {"chpmex"}, description = "MAFAUTO-179")
	public void addToFavoritesInMexico() throws Exception {
	
		
		//testResult = Reporter.getCurrentTestResult();
		testResult = Reporter.getCurrentTestResult();
		extentLogger = setUpExtentTest(extentLogger, "Favorites Test", testResult.getMethod().getMethodName());
		softas = new SoftAssert();	
		//extentLogger = setUpExtentTest(extentLogger, "Favorites", testResult.getMethod().getMethodName());		
		String jiraNumber = testResult.getMethod().getDescription();		
		String issueSummary = getIssueTitle(jiraNumber,"Common Document Display Features - Document Display - Add to Favourites"); 		
		DocumentDisplayPage documentPage;
		try {
			


			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"documenttitle",extentLogger); 
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"thematicarea",extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"treeitems",extentLogger).split(",");

				//Check which is default search page area
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
			}
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	
}
