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
import com.trgr.quality.maf.pages.AnnotationsPage;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
/**
 * Class for running Annotations Test cases
 * This class defines methods for verifying 'Annotations' test cases
 * @author Sarath Manoharam
 * @version 1.0
 * @since December 27, 2016
 */
public class AnnotationsTest extends BaseTest {

	/*
	 * DocDisplay_TC_013: Verify Annotations : Add notes in document display page (Doc level / headline notes)
	 * DocDisplay_TC_014: Verify Annotations: Hide Or Show existing notes in document display page (Doc level / headline notes)
	 * DocDisplay_TC_015: Verify Annotations: Edit existing notes in document display page (Doc level / headline notes)
	 * DocDisplay_TC_016: Verify Annotations: Remove existing notes in document display page (Doc level / headline notes)
	 * 
	 * MAFAUTO-125: Verify creating notes for legislation search result from content tree
	 */

	AnnotationsPage annotationsPage;
	LoginPage loginpage;
	HomePage homepage;
	String docNote,docEditNote;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JsonReader jsonReader;
	
	@BeforeClass(alwaysRun = true)
	public void startAnnotationsTest() throws Exception
	{
		try{
			loginpage=new LoginPage(driver,ProductUrl);
			homepage=loginpage.Login(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username"), PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password"));
			for(int i=0;i<10;i++) {
				if(driver.getTitle().equalsIgnoreCase("Checkpoint  | Búsquedas")) {
					break;
				}
				else {
					Thread.sleep(3000);
				}
			}
			docNote = "Test Note";
			docEditNote = "Edited Note";
			jsonReader = new JsonReader();

		}catch(Exception exc){

			extentLogger = setUpExtentTest(extentLogger, "Annotations", "startAnnotationsTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the annotations test are not run.<br>"+ takesScreenshot_Embedded()+ "<br>"+displayErrorMessage(exc));
			extentReports.endTest(extentLogger);			
			Assert.assertTrue(false);

		}
	}

	@AfterClass(alwaysRun = true)
	public void endAnnotationsTest(){
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
	 * DocDisplay_TC_013: Verify Annotations : Add notes in document display page (Doc level / headline notes) 
	 */

	@Test(priority=0,groups={"chpbr","chpmex"},description = "MAFQABANG-89")
	public void addAnnotationOnDocumentDisplay() throws Exception
	{
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		SearchResultsPage searchresultspage;
		extentLogger = setUpExtentTest(extentLogger, "Annotations", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, 
				"Add New Notes in document display");
		try{ 

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String searchkey="freeword";
				String searchFreeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,searchkey,extentLogger);

				/*Perform search, go to document display & get annotations object */
				
				//Check if application is landing on home page / search page
				homepage.openHomepage();
				if(homepage.isSearchButtonOnHomePageDisplayed())
				{
					homepage.enterFreewordOnQuickSearch(searchFreeword);
					searchresultspage = homepage.clickSearch();
				}
				else
				{
					//if home page is not displayed then assumption is that search page is displayed
					SearchPage searchPage = new SearchPage(driver);
//					searchPage.enterFreeWordOnSearchPage(searchFreeword);
					homepage.enterTextInSearchField(searchFreeword);
					searchresultspage = searchPage.clickOnSearch();			
				}
				annotationsPage = searchresultspage.getFirstDocument().getAnnotations();

				//Validating document note pop-up
				boolean annotationPopUpValidated=
						annotationsPage.isAnnotationsOptionPresent() &&
						annotationsPage.clickAnnotationsDropDown() &&
						annotationsPage.isAddNoteOptionVisible() &&
						annotationsPage.clickAddNoteOption() &&
						annotationsPage.isAddNotePopUpPresent();

				softas.assertTrue(annotationPopUpValidated,":"+jiraNumber);
				logExtentStatus(extentLogger, annotationPopUpValidated, "Add document note option is displayed",jiraNumber);
				
				

				//Creating document note
				docNote = docNote+" "+ RandomUtils.getUniqueNumber();
				boolean createDocNote =
						annotationsPage.writeDocumentNote(docNote) &&
						annotationsPage.clickAddNoteButton() &&
						annotationsPage.verifyAddNoteConfirmation();
				boolean icreateDocNote=annotationsPage.isDocumentNoteExist(docNote);
				softas.assertTrue(createDocNote & icreateDocNote,":"+jiraNumber);
				logExtentStatus(extentLogger, createDocNote & icreateDocNote, issueSummary+" New note is created",
						searchkey,searchFreeword,jiraNumber);

				//Re-opening pop-up and then closing using Cancelar button
				boolean validateCancelButton =
						annotationsPage.clickAnnotationsDropDown() &&
						annotationsPage.clickAddNoteOption() &&
						annotationsPage.isAddNotePopUpPresent() &&
						annotationsPage.clickCancelAnnotationsPopUp();
				logExtentStatus(extentLogger, validateCancelButton, "Pop-up window closed on clicking 'Cancelar <Cancel>' button in the pop-up window",jiraNumber);
				softas.assertTrue(validateCancelButton,":"+jiraNumber);

				//Reopening pop-up and then Closing using Close(X) button
				boolean validateCloseButton =
						annotationsPage.clickAnnotationsDropDown() &&
						annotationsPage.clickAddNoteOption() &&
						annotationsPage.isAddNotePopUpPresent() &&
						annotationsPage.closeAnnotationsPopUp();
				
			//	annotationsPage.clickDocumentNoteDeleteImage(1);

				softas.assertTrue(validateCloseButton,":"+jiraNumber);
				logExtentStatus(extentLogger, validateCloseButton,"Verify close button in the pop-up window",jiraNumber);
			}	
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	/*
	 * DocDisplay_TC_014: Verify Annotations: Hide Or Show existing notes in document display page (Doc level / headline notes) 
	 */
	@Test(priority=1,dependsOnMethods={"addAnnotationOnDocumentDisplay"},groups={"chpmex","chpbr"},description = "MAFQABANG-90")
	public void hideOrShowAnnotationsOnDocument() throws Exception
	{
		testResult = Reporter.getCurrentTestResult();softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Annotations", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		//String issueSummary = getIssueTitle(jiraNumber, "verify Hide Or Show Document Note");

		try{

			/* perform search and find document display page for a document which has visible document notes.
			 * Alternatively, run this test as a dependent on  addNewNotesInDocumentDisplay()
			 */

			//Verify annotations dropdown
			boolean dropdownValidated =
					annotationsPage.isDocumentNoteDisplayed() &&
					annotationsPage.clickAnnotationsDropDown() &&
					annotationsPage.isHideNoteOptionVisible() &&
					annotationsPage.isAddNoteOptionVisible();

			softas.assertTrue(dropdownValidated,jiraNumber+":"+"Validate annotations dropdown.");
			logExtentStatus(extentLogger, dropdownValidated, "Validated annotations dropdown before hiding notes",jiraNumber);

			//Hide annotations
			boolean annotationsHidden =
					annotationsPage.clickHideNoteOption() &&
					!annotationsPage.isDocumentNoteDisplayed();
			softas.assertTrue(annotationsHidden,jiraNumber+":"+"Validate Hiding annotations");
			logExtentStatus(extentLogger, annotationsHidden,"Annotations are hidden using Hide note option",jiraNumber);

			//Validate annotations drop down - after hiding notes
			dropdownValidated =
					annotationsPage.clickAnnotationsDropDown() &&
					annotationsPage.isShowNoteOptionVisible() &&
					annotationsPage.isHideNoteOptionHidden() &&
					annotationsPage.isAddNoteOptionVisible();

			softas.assertTrue(dropdownValidated, jiraNumber+":"+"Validate Hiding annotations");
			logExtentStatus(extentLogger, dropdownValidated, "Annotations are hidden using Hide note option",jiraNumber);

			//Show hidden annotations
			boolean annotationsDisplayed =
					annotationsPage.clickShowNoteOption() &&
					annotationsPage.isDocumentNoteDisplayed();
			softas.assertTrue(annotationsDisplayed,jiraNumber+":"+"Validate Showing annotations");
			logExtentStatus(extentLogger, annotationsDisplayed, "Annotations are displayed using Show annotations option",jiraNumber);

			//Validate annotations drop down - after showing hidden notes
			dropdownValidated =	
					annotationsPage.clickAnnotationsDropDown() &&
					annotationsPage.isShowNoteOptionHidden() &&
					annotationsPage.isHideNoteOptionVisible() &&
					annotationsPage.isAddNoteOptionVisible();

			softas.assertTrue(dropdownValidated,jiraNumber+":"+"Validate Showing annotations.");
			logExtentStatus(extentLogger, dropdownValidated, "Annotations are displayed using Show annotations option",jiraNumber);

		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	/*
	 * DocDisplay_TC_015: Verify Annotations: Edit existing notes in document display page (Doc level / headline notes)
	 * 
	 */
	@Test(priority=2,dependsOnMethods={"addAnnotationOnDocumentDisplay"},groups={"chpmex","chpbr"},description = "MAFQABANG-91")
	public void editExistingAnnotationOnDocument() throws Exception
	{
		int docPosition = 0;
		testResult = Reporter.getCurrentTestResult();softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Annotations", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Edit existing Notes in document display");

		try{
			/* 
			 * This test tries to edit the document note added in verifyAddNoteInDocumentDisplay()
			 * Run this test as a dependent on  verifyAddNoteInDocumentDisplay(). 
			 */
			boolean popupValidated =
					annotationsPage.isDocumentNoteExist(docNote) &&
					((docPosition=annotationsPage.findDocumentNotePosition(docNote))>0) &&
					annotationsPage.clickDocumentNoteEditImage(docPosition) &&
					annotationsPage.isAddNotePopUpPresent() &&
					annotationsPage.verifyEditPopUpContent(docNote);
			softas.assertTrue(popupValidated,jiraNumber+":"+"Validate edit pop-up : Pop-up is pre-populated with existing note");
			logExtentStatus(extentLogger, popupValidated, "Validate edit pop-up: Pop-up is pre-populated with existing note",jiraNumber);

			//Updating document content
			docEditNote = docNote+"\n"+docEditNote;
			boolean docNoteUpdated =
					annotationsPage.editDocumentNote(docEditNote) &&
					annotationsPage.clickEditConfirmButton();
				//	annotationsPage.verifyEditNoteConfirmation();
			//Validating the old note is unavailable, and updated note is available
			if(!annotationsPage.isNotDocumentNoteExist(docNote))
				extentLogger.log(LogStatus.WARNING, "Updated document note exist with the old content<br>"+ takesScreenshot_Embedded());
			docNoteUpdated=annotationsPage.isDocumentNoteExist(docEditNote);

			softas.assertTrue(docNoteUpdated,jiraNumber+":"+issueSummary);
			logExtentStatus(extentLogger, docNoteUpdated, issueSummary,jiraNumber);

			//Updating the docNote, can be used in other tests
			docNote = docEditNote;

			//Verify Closing the edit pop-up using Cancelar button
			boolean editCancelled =
					annotationsPage.clickDocumentNoteEditImage(1) &&
					annotationsPage.clickCancelAnnotationsPopUp();
			softas.assertTrue(editCancelled,jiraNumber+":"+"verify pop-up Cancel button");
			logExtentStatus(extentLogger, editCancelled, "Validated closing Pop-up window using 'Cancelar <Cancel>' button",jiraNumber);

			//Verify Closing the pop-up using Close(X) button
			editCancelled = 	annotationsPage.clickDocumentNoteEditImage(1) &&
					annotationsPage.closeAnnotationsPopUp();

			softas.assertTrue(editCancelled,jiraNumber+":"+"verify pop-up Close button.");
			logExtentStatus(extentLogger, editCancelled, "Validated closing Pop-up window using 'Cancelar <Cancel>' button",jiraNumber);
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	/*
	 * DocDisplay_TC_016: Verify Annotations: Remove existing notes in document display page (Doc level / headline notes)
	 */
	@Test(priority=3,dependsOnMethods={"addAnnotationOnDocumentDisplay","editExistingAnnotationOnDocument"},groups={"chpmex","chpbr"},description = "MAFQABANG-92")
	public void deleteExistingAnnotationOnDocument() throws Exception
	{
		int docPosition = 0;

		try{
			testResult = Reporter.getCurrentTestResult();softas = new SoftAssert();	
			extentLogger = setUpExtentTest(extentLogger, "Annotations", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Remove existing Notes from document display");

			/* 
			 * This test tries to delete the document note added in verifyAddNoteInDocumentDisplay()
			 * Run this test as a dependent on  verifyEditDocumentNote(). 
			 */

			//Validate the delete dialogue box
			boolean dialogueBoxValidated =
					annotationsPage.isDocumentNoteExist(docNote) &&
					((docPosition=annotationsPage.findDocumentNotePosition(docNote))>0) &&
				//	annotationsPage.clickDocumentNoteDeleteImage(docPosition) &&
					annotationsPage.isDeleteBoxClicking() &&
					annotationsPage.isDeleteDialoguePresent() &&
					annotationsPage.verifyDeleteDialogueMessage();
			softas.assertTrue(dialogueBoxValidated,jiraNumber+":"+"validate delete dialogue.");
			logExtentStatus(extentLogger, dialogueBoxValidated, "Validate delete pop-up : Are you sure you want to delete this note?",jiraNumber);

			//Verify Closing the pop-up using Cancelar button
			dialogueBoxValidated=annotationsPage.clickCancelDeletePopUp();
			softas.assertTrue(dialogueBoxValidated,jiraNumber+":"+"Validate cancel button in delete dialogue");
			logExtentStatus(extentLogger, dialogueBoxValidated, "Validated closing  Delete dialogue using 'Cancelar <Cancel>' button",jiraNumber);

			//Re-opening the delete dialogue and Closing using Close(X) button
			dialogueBoxValidated =  annotationsPage.isDeleteBoxClicking() &&
					annotationsPage.closeDeletePopUp();

			softas.assertTrue(dialogueBoxValidated,jiraNumber+":Validate close button in delete dialogue");
			logExtentStatus(extentLogger, dialogueBoxValidated, "Validated closing delete dialogue box using 'close button(X)", jiraNumber);

			//Re-opening the delete dialogue & Deleting the document note
			boolean docNoteDeleted =
					annotationsPage.isDeleteBoxClicking() &&
					annotationsPage.clickEliminarButton() &&
					annotationsPage.verifyDeleteConfirmation();
		//	docNoteDeleted=annotationsPage.isNotDocumentNoteExist(docNote);
			softas.assertTrue(docNoteDeleted, jiraNumber+":"+issueSummary);
		//	logExtentStatus(extentLogger, docNoteDeleted, issueSummary,jiraNumber);*/

		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	/*
	 * MAFAUTO-125: Verify creating notes for document from content tree 
	 */
	@Test(priority=4,dependsOnMethods={"deleteExistingAnnotationOnDocument"},groups={"chpmex"},description = "MAFAUTO-125")
	public void createAnnotationForDocFrmContentTree() throws Exception
	{

		DocumentDisplayPage docDisplayPage = null;
		SearchPage searchPage = null;
		AnnotationsPage annotationsPage=null;
		testResult = Reporter.getCurrentTestResult();softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Annotations", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();


		String issueSummary = getIssueTitle(jiraNumber, 
				"Add Note For Document In TreeView");

		try{
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey="thematicarea";
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,thematicareaKey,extentLogger);

				String treeitemsKey="treeitems";
				String  treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,treeitemsKey,extentLogger).split(",");

				String documenttitleKey="documenttitle";
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,documenttitleKey,extentLogger);
				
			String docnotename ="Addnote";
			String Addnote = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,docnotename,extentLogger);


				searchPage = homepage.OpenSearchPage();

				//Select Thematic Area & Click on area in tree(if it is not already selected)
				searchPage.selectGivenValueFromThematicDropdown(thematicArea);

				if (searchPage.isAreaFoundInContentTree(thematicArea)) {
					searchPage.selectAreaFromContentTree(thematicArea);
				}

				//Expand First level item in the tree structure
				WebElement treeElement = searchPage.getFirstLevelContentTree(treeItems[0]);
				searchPage.expandContentTreeElement(treeElement);
				//Expand till document in the sub tree
				for(int row=1; row<treeItems.length ;row++){
					if(searchPage.isItemPresentInSubContentTree(treeElement,treeItems[row])){
						searchPage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = searchPage.getSubContentTreeElement(treeElement, treeItems[row]);
					}
				}
				//Open document from the tree
				boolean docDisplayedInTree = searchPage.isItemPresentInSubContentTree(treeElement,documentTitle);
				softas.assertTrue(docDisplayedInTree,jiraNumber+":"+"Document found in tree content");
				logExtentStatus(extentLogger, docDisplayedInTree, "Document found in tree content",
						documenttitleKey,documentTitle,jiraNumber);

				//if(docDisplayedInTree)
				docDisplayPage = searchPage.clickDocumentInSubContentTree(treeElement,documentTitle);
				softas.assertTrue(docDisplayPage!=null,jiraNumber+":"+"Document Display Page is displayed");
				logExtentStatus(extentLogger, docDisplayPage!=null, "Document Display Page is displayed",jiraNumber);

				//Creating document note
				annotationsPage = docDisplayPage.getAnnotations();
			//	docNote = docNote+" "+ RandomUtils.getUniqueNumber();
               docNote=Addnote;
				boolean createDocNote =
						annotationsPage.clickAnnotationsDropDown() &&
						annotationsPage.clickAddNoteOption() &&
						annotationsPage.isAddNotePopUpPresent() &&
						annotationsPage.writeDocumentNote(docNote) &&
						annotationsPage.clickAddNoteButton() &&
						annotationsPage.verifyAddNoteConfirmation();
				createDocNote=annotationsPage.isDocumentNoteExist(docNote);
				softas.assertTrue(createDocNote,jiraNumber+":"+issueSummary+" New note is created");
				logExtentStatus(extentLogger, createDocNote,issueSummary+" New note is created",jiraNumber);

				//Cleaning up - Deleting the document note created for this test run
				int docPosition = annotationsPage.findDocumentNotePosition(docNote);
				boolean docNoteDeleted =
						annotationsPage.clickDocumentNoteDeleteImage(docPosition) ;
						/*annotationsPage.clickEliminarButton() &&
						annotationsPage.verifyDeleteConfirmation();*/
			//	docNoteDeleted=annotationsPage.isNotDocumentNoteExist(docNote);
				softas.assertTrue(docNoteDeleted,jiraNumber+":Document note is deleted.");
				logExtentStatus(extentLogger, docNoteDeleted,"Deleting notes on the document",jiraNumber);
			}
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}
	
	/*
	 * DocDisplay_TC_013: Verify Annotations : Add notes in document display page (Doc level / headline notes) 
	 */

	@Test(priority=0,groups={"chpmex"},description = "MAFQABANG-89")
	public void addAnnotationOnDocumentDisplayInMex() throws Exception
	{
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		SearchResultsPage searchresultspage;
		extentLogger = setUpExtentTest(extentLogger, "Annotations", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, 
				"Add New Notes in document display");
		try{ 

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String searchkey="freeword";
				String searchFreeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,searchkey,extentLogger);

				/*Perform search, go to document display & get annotations object */
				
				//Check if application is landing on home page / search page
				//if(homepage.isSearchButtonOnHomePageDisplayed())
				//{
				//	homepage.enterTextInSearchField(searchFreeword);
				//	searchresultspage = homepage.clickSearch();
			//	}
			//	else
			//	{
					//if home page is not displayed then assumption is that search page is displayed
				
					SearchPage searchPage = new SearchPage(driver);
					searchPage = homepage.OpenSearchPage();
					Thread.sleep(5000);
					searchPage.enterTextInSearchField(searchFreeword);
					searchresultspage = searchPage.clickOnSearch();			
					if(searchPage.isErrorPageDisplayedAgain()) {
						 searchPage = new SearchPage(driver);
						searchPage = homepage.OpenSearchPage();
						Thread.sleep(5000);
						searchPage.enterTextInSearchField(searchFreeword);
						searchresultspage = searchPage.clickOnSearch();	
						
						
					}
					
				annotationsPage = searchresultspage.getFirstDocument().getAnnotations();

				//Validating document note pop-up
				boolean annotationPopUpValidated=
						annotationsPage.isAnnotationsOptionPresent() &&
						annotationsPage.clickAnnotationsDropDown() &&
						annotationsPage.isAddNoteOptionVisible() &&
						annotationsPage.clickAddNoteOption() &&
						annotationsPage.isAddNotePopUpPresent();

				softas.assertTrue(annotationPopUpValidated,":"+jiraNumber);
				logExtentStatus(extentLogger, annotationPopUpValidated, "Add document note option is displayed",jiraNumber);
				
				

				//Creating document note
				docNote = docNote+" "+ RandomUtils.getUniqueNumber();
				boolean createDocNote =
						annotationsPage.writeDocumentNote(docNote) &&
						annotationsPage.clickAddNoteButton() ;
					//	annotationsPage.verifyAddNoteConfirmation();
				boolean icreateDocNote=annotationsPage.isDocumentNoteExist(docNote);
				softas.assertTrue(createDocNote & icreateDocNote,":"+jiraNumber);
				logExtentStatus(extentLogger, createDocNote & icreateDocNote, issueSummary+" New note is created",
						searchkey,searchFreeword,jiraNumber);

				//Re-opening pop-up and then closing using Cancelar button
				boolean validateCancelButton =
						annotationsPage.clickAnnotationsDropDown() &&
						annotationsPage.clickAddNoteOption() &&
						annotationsPage.isAddNotePopUpPresent() &&
						annotationsPage.clickCancelAnnotationsPopUp();
				logExtentStatus(extentLogger, validateCancelButton, "Pop-up window closed on clicking 'Cancelar <Cancel>' button in the pop-up window",jiraNumber);
				softas.assertTrue(validateCancelButton,":"+jiraNumber);

				//Reopening pop-up and then Closing using Close(X) button
				boolean validateCloseButton =
						annotationsPage.clickAnnotationsDropDown() &&
						annotationsPage.clickAddNoteOption() &&
						annotationsPage.isAddNotePopUpPresent() &&
						annotationsPage.closeAnnotationsPopUp();
				
				annotationsPage.clickDocumentNoteDeleteImage(1);

				softas.assertTrue(validateCloseButton,":"+jiraNumber);
				logExtentStatus(extentLogger, validateCloseButton,"Verify close button in the pop-up window",jiraNumber);
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