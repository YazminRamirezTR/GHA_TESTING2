package com.trgr.quality.maf.tests;



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
import com.trgr.quality.maf.pages.MyDocumentPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class MyDocumentTest extends BaseTest {

	/*
	 * Module Name : My Document Feature Names : Description My Documents_TC_001
	 * : Verify_MY Documents Link Page My Documents_TC_002 : Verify_MY Documents
	 * Create My Documents_TC_003 : Verify_My Documents _Rename_delete My
	 * Documents_TC_004 : Verify_My Documents_Move document from one folder to
	 * another folder
	 */

	LoginPage loginpage;
	HomePage homepage;
	MyDocumentPage mydocumentpage;	
	SearchPage searchpage;
	SearchResultsPage searchresultpage;
	AnnotationsPage  annotationsPage;
	DocumentDisplayPage docdisplaypage;
	String expname,document_title,docNote;
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
			docNote = "Test Note";
			jsonReader = new JsonReader();
		}
		catch(Exception exc){
			
			extentLogger = setUpExtentTest(extentLogger, "MyDocuments", "StartMyDocumentTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the MyDocument test are not run.<br>"+ takesScreenshot_Embedded()+ "<br>"+displayErrorMessage(exc));
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
	
	@Test(priority=0,groups = {"chparg","chpbr","chpury","chppe","chpchile"}, description="MAFQABANG-115")
	public void MyDocumentBasicFolders() throws Exception {
		softas = new SoftAssert();	
		testResult = Reporter.getCurrentTestResult();			
		extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());		  
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify my Documents Page"); 
		try{
			
			mydocumentpage = homepage.openMyDocumentsPage();
			boolean isMyDocumentPageDisplayed = mydocumentpage.isMyDocumentTextDisplayed();
			softas.assertTrue(isMyDocumentPageDisplayed, jiraNumber+ ":" + issueSummary +":Verified My Document link");
			logExtentStatus(extentLogger, isMyDocumentPageDisplayed, issueSummary + ":Verified My Document link", jiraNumber);
			
			boolean isBasicFoldersDisplayed = mydocumentpage.isBasicFoldersPresent();
			softas.assertTrue(isBasicFoldersDisplayed, jiraNumber+":" + issueSummary +":Verified Basic folders in My Folder page");
			logExtentStatus(extentLogger, isBasicFoldersDisplayed, issueSummary + ":Verified Basic folders in My Folder page",  jiraNumber);
			
		
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=0,groups = {"chpmex"}, description="MAFAUTO-143")
	public void displayOfMyDocumentsFolders() throws Exception {
		softas = new SoftAssert();	
		testResult = Reporter.getCurrentTestResult();			
		extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());		  
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify my Documents Page"); 
		try{
			
			mydocumentpage = homepage.openMyDocumentsPage();
			boolean isMyDocumentPageDisplayed = mydocumentpage.isMyDocumentTextDisplayed();
			softas.assertTrue(isMyDocumentPageDisplayed, jiraNumber+ ":" + issueSummary +":Verified My Document link");
			logExtentStatus(extentLogger, isMyDocumentPageDisplayed, issueSummary + ":Verified My Document link", jiraNumber);
			
			boolean isBasicFoldersDisplayed = mydocumentpage.isBasicFoldersPresent();
			softas.assertTrue(isBasicFoldersDisplayed, jiraNumber+":" + issueSummary +":Verified Basic folders in My Folder page");
			logExtentStatus(extentLogger, isBasicFoldersDisplayed, issueSummary + ":Verified Basic folders in My Folder page",  jiraNumber);
			
		
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	
	@Test(priority=5,groups = {"chparg","chpury", "chppe","chpchile"}, description="MAFQABANG-619")
	public void ExpandAndCollapseFolders() throws Exception {
		softas = new SoftAssert();	
		testResult = Reporter.getCurrentTestResult();			
		extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());		  
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify Expand and collapse  folders in my Documents Page"); 
		try{
			
			mydocumentpage = homepage.openMyDocumentsPage();			
			boolean isfolderexpandAndcollapse = mydocumentpage.ExpandAndCollapse();
			softas.assertTrue(isfolderexpandAndcollapse, jiraNumber+ ":" +issueSummary);
			logExtentStatus(extentLogger, isfolderexpandAndcollapse, issueSummary, jiraNumber);
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	@Test(priority=5,groups = {"chpmex"}, description="MAFAUTO-170")
	public void expandAndCollapseFolder() throws Exception {
		softas = new SoftAssert();	
		testResult = Reporter.getCurrentTestResult();			
		extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());		  
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify Expand and collapse  folders in my Documents Page"); 
		try{
			
			mydocumentpage = homepage.openMyDocumentsPage();	
			
			boolean isfoldercollapsed=mydocumentpage.isMyDocumentFoldercollapsed();			
			if(isfoldercollapsed)			
			{
			softas.assertTrue(isfoldercollapsed, jiraNumber+ ":" +" Folder Collapsed");
			logExtentStatus(extentLogger, isfoldercollapsed, " Folder Collapsed", jiraNumber);
			mydocumentpage.clickOnMyDocument();
			}
			boolean isfolderexpanded=mydocumentpage.isMyDocumentFolderExpanded();
			softas.assertTrue(isfolderexpanded, jiraNumber+ ":" +issueSummary);
			logExtentStatus(extentLogger, isfolderexpanded, issueSummary, jiraNumber);
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=1,groups = { "chparg","chpury","chppe","chpchile" },description = "MAFQABANG-116")	
	public void CreateNewFolderInMydocument() throws Exception {
		softas = new SoftAssert();	
		try{
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"MY Documents_Verify create folder");
			
			expname = "Test" + RandomUtils.getUniqueNumber(); 
			homepage=homepage.openHomepage();
			mydocumentpage = homepage.openMyDocumentsPage();
			mydocumentpage.clickOnMyDocument();
			mydocumentpage.clickCreateFolder();
			mydocumentpage.enterFolderName(expname);
			mydocumentpage.saveFolder();
			boolean isFolderCreated = mydocumentpage.isFolderExistInMyDocumentFolder(expname);
			if(!isFolderCreated){
				//Sometimes my document folder is not expanded first time. Re-check.
				homepage.openMyDocumentsPage();
				mydocumentpage.clickOnMyDocument();
				isFolderCreated = mydocumentpage.isFolderExistInMyDocumentFolder(expname);
			}
			softas.assertTrue(isFolderCreated, jiraNumber+":"+ issueSummary +"Folder is created");
			logExtentStatus(extentLogger, isFolderCreated, issueSummary+": Folder is created", jiraNumber);
			
			if(isFolderCreated)
			{
				mydocumentpage.deleteSubFolderInMyDocument(expname);
			}
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
			
	}
	
	@Test(priority=1,groups = { "chpbr" },description = "MAFQABANG-116")	
	public void CreateNewFolder() throws Exception {
		softas = new SoftAssert();	
		testResult = Reporter.getCurrentTestResult();			
		extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());
		
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"MY Documents_Verify create folder");
		try{
			
			expname = "Test" + RandomUtils.getUniqueNumber(); 
			homepage=homepage.openHomepage();
			mydocumentpage = homepage.openMyDocumentsPage();			
			mydocumentpage.clickCreateFolder();
			mydocumentpage.enterFolderName(expname);
			mydocumentpage.saveFolder();
			boolean isFolderCreated = mydocumentpage.isFolderExistInMyDocumentFolder(expname);
			softas.assertTrue(isFolderCreated, jiraNumber+":"+ issueSummary +"Folder is created");
			logExtentStatus(extentLogger, isFolderCreated, issueSummary+": Folder is created", jiraNumber);
			if(isFolderCreated)
			{
				
				mydocumentpage.checkSpecificFolder(expname);
				mydocumentpage.clickOnActionManager();
				mydocumentpage.clickDeleteLink();
				
			}
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
			
	}
	
	
	@Test(priority=1,groups = { "chpmex"},description = "MAFAUTO-142")	
	public void createNewFolder() throws Exception {
		softas = new SoftAssert();	
		testResult = Reporter.getCurrentTestResult();			
		extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());
		
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"MY Documents_Verify create folder");
		try{
			
			expname = "Test" + RandomUtils.getUniqueNumber(); 
			homepage=homepage.openHomepage();
			Thread.sleep(2000);
			mydocumentpage = homepage.openMyDocumentsPage();
			Thread.sleep(2000);
			mydocumentpage.clickCreateFolder();
			mydocumentpage.enterFolderName(expname);
			mydocumentpage.saveFolder();
			boolean isFolderCreated = mydocumentpage.isFolderExistInMyDocumentFolder(expname);
			softas.assertTrue(isFolderCreated, jiraNumber+":"+ issueSummary +"Folder is created");
			logExtentStatus(extentLogger, isFolderCreated, issueSummary+": Folder is created", jiraNumber);
			
			//Clean up needed for next test run
			if(isFolderCreated)
			{
				
				mydocumentpage.checkSpecificFolder(expname);
				mydocumentpage.clickOnActionManager();
				mydocumentpage.clickDeleteLink();
				
			}
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
			
	}
	
	@Test(priority=2,groups = { "chpmex","chpbr" },description = "MAFQABANG-117")	
	public void renameAndDeleteNewFolder() throws Exception {
		softas = new SoftAssert();	
		try{
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"My Documents  _Verify rename and delete folder");
			
			
			expname = "Test" + RandomUtils.getUniqueNumber(); 
			String renamekey = "RenameFolder"+ RandomUtils.getUniqueNumber();
			homepage=homepage.openHomepage();
			Thread.sleep(2000);
			mydocumentpage = homepage.openMyDocumentsPage();
			Thread.sleep(2000);
			mydocumentpage.clickCreateFolder();
			mydocumentpage.enterFolderName(expname);
			mydocumentpage.saveFolder();
			boolean isFolderCreated = mydocumentpage.isFolderExistInMyDocumentFolder(expname);
			softas.assertTrue(isFolderCreated, jiraNumber+"Folder is created");
			logExtentStatus(extentLogger, isFolderCreated, " Folder is created", jiraNumber);
			if(isFolderCreated)
			{
				mydocumentpage.selectspecificFolder(expname);
				mydocumentpage.clickOnRenameFolder();
				mydocumentpage.enterRenameInput(renamekey);
				mydocumentpage.saveRename();
				boolean isfolderrenamed=
						!(mydocumentpage.isFolderExistInMyDocumentFolder(expname)) &&
						mydocumentpage.isFolderExistInMyDocumentFolder(renamekey);
				
				softas.assertTrue(isfolderrenamed, jiraNumber+"Folder is Renamed");
				logExtentStatus(extentLogger, isfolderrenamed, " Folder is Renamed", jiraNumber);
				mydocumentpage.clickOnMyDocument();
				mydocumentpage.checkSpecificFolder(renamekey);
				mydocumentpage.clickOnActionManager();
				
				mydocumentpage.clickDeleteLink();
				
				boolean isfolderdeleted=!(mydocumentpage.isFolderExistInMyDocumentFolder(renamekey));
				
				softas.assertTrue(isfolderdeleted, jiraNumber+issueSummary);
				logExtentStatus(extentLogger, isfolderdeleted, issueSummary, jiraNumber);
				
			}
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
			
	}
	
	@Test(priority=2,groups = { "chparg","chpury","chppe","chpchile" },description = "MAFQABANG-117")	
	public void RenameAndDeleteMyDocumentFolder() throws Exception {
		softas = new SoftAssert();
		try{
			testResult = Reporter.getCurrentTestResult();				
			extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"My Documents  _Verify rename and delete folder"); 
				
			expname = "Test" + RandomUtils.getUniqueNumber(); 
			String renameinput="Rename" + expname;
			homepage=homepage.openHomepage();
			mydocumentpage = homepage.openMyDocumentsPage();
			mydocumentpage.clickOnMyDocument();
			mydocumentpage.clickCreateFolder();
			mydocumentpage.enterFolderName(expname);
			mydocumentpage.saveFolder();
			boolean isFolderCreated = mydocumentpage.isFolderExistInMyDocumentFolder(expname);
			if(!isFolderCreated){//Some times MyDocument is not expanded. Hence trying again for this scenario
				mydocumentpage.clickOnMyDocument();
				isFolderCreated = mydocumentpage.isFolderExistInMyDocumentFolder(expname);
			}
			softas.assertTrue(isFolderCreated, jiraNumber+"Folder is created");
			logExtentStatus(extentLogger, isFolderCreated,"Folder is created", jiraNumber);
			if(isFolderCreated){
				//selecting the folder created then renaming it
				mydocumentpage.selectFolderToRename(expname);
				mydocumentpage.enterRenameInput(renameinput);
				mydocumentpage.saveRename();
				
				boolean isfolderrenamed=mydocumentpage.isFolderExistInMyDocumentFolder(renameinput);
				softas.assertTrue(isfolderrenamed, jiraNumber+":"+ "Folder Renamed" );
				logExtentStatus(extentLogger, isfolderrenamed, "Folder Renamed" , jiraNumber);
				
				if(isfolderrenamed){
					expname = renameinput;
				}
				
				//deleting the created folder
				mydocumentpage.deleteSubFolderInMyDocument(expname);
				boolean folderDeleted = !(mydocumentpage.isFolderExistInMyDocumentFolder(expname));
				softas.assertTrue(folderDeleted, jiraNumber+":"+ issueSummary );
				logExtentStatus(extentLogger, folderDeleted, issueSummary, jiraNumber);
			}
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	/*@Test(priority=3, groups = { "chpmex","chpbr"}, description="MAFQABANG-118")
	public void MoveFolder() throws Exception
	{
		softas = new SoftAssert();
		testResult = Reporter.getCurrentTestResult();
		extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());
		
		String jiraNumber = testResult.getMethod().getDescription();			
		String issueSummary = getIssueTitle(jiraNumber,"My Documents_ Verify move document from one folder to another folder"); 
		
		try{
			    
			expname = "Test" + RandomUtils.getUniqueNumber(); 			
			homepage=homepage.openHomepage();
			mydocumentpage = homepage.openMyDocumentsPage();			
			mydocumentpage.clickCreateFolder();
			mydocumentpage.enterFolderName(expname);
			mydocumentpage.saveFolder();
			boolean isFolderCreated = mydocumentpage.isFolderExistInMyDocumentFolder(expname);
			softas.assertTrue(isFolderCreated, jiraNumber+"Folder is created");
			logExtentStatus(extentLogger, isFolderCreated, " Folder is created", jiraNumber);
			if(isFolderCreated)
			{			
			//Validate moving folder
				mydocumentpage.checkSpecificFolder(expname);
				mydocumentpage.clickOnActionManager();
				mydocumentpage.clickOnMoveFolderLink();
				mydocumentpage.SelectFolderToMove();
				mydocumentpage.clickOnMoveButton();
				mydocumentpage.clickMyNotes();
				boolean isfoldermovedToMyNotes=mydocumentpage.isFolderExistInMyNotesFolder(expname);
			
			softas.assertTrue(isfoldermovedToMyNotes, jiraNumber+":"+issueSummary);
			logExtentStatus(extentLogger, isfoldermovedToMyNotes,issueSummary, jiraNumber);
			}
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}*/
	
	/*//MAFAUTO-144
	@Test(priority=4, groups = { "chpmex"}, description="MAFAUTO-144")
	public void ViewMyDocumentNotes() throws Exception
	{
		
		softas = new SoftAssert();
		testResult = Reporter.getCurrentTestResult();
		extentLogger = setUpExtentTest(extentLogger, "MyDocuments", testResult.getMethod().getMethodName());				
		String jiraNumber = testResult.getMethod().getDescription();		 
		String issueSummary = getIssueTitle(jiraNumber,"My Documents - See Notes");    
		try{
			            
			AnnotationsPage  annotationsPage;	   
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey="Freeword";
				String freeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);
				String carpeta= "Carpeta2";
				
				String carpetakey = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,carpeta,extentLogger);
					
				searchpage = homepage.OpenSearchPage();
				searchpage.enterTextInSearchField(freeword);
				searchresultpage = searchpage.clickOnSearchButtonDocumentDisplay();
				docdisplaypage=searchresultpage.getFirstDocument();
				document_title=docdisplaypage.getDocumentDisplayTitleLineOne();
				annotationsPage=docdisplaypage.getAnnotations();
				docNote = docNote+" "+ RandomUtils.getUniqueNumber();
				//Add new note in document display
				boolean addedNote = annotationsPage.clickAnnotationsDropDown() &&
						annotationsPage.clickAddNoteOption() &&
						annotationsPage.writeDocumentNote(docNote) &&
						annotationsPage.clickAddNoteButton() &&
						annotationsPage.verifyAddNoteConfirmation();
			
				softas.assertTrue(addedNote, jiraNumber+":"+issueSummary+":Added annotation note in document display");
				logExtentStatus(extentLogger, addedNote,issueSummary+ ":Added annotation note in document display", jiraNumber);
				
				homepage.openHomepage();
				mydocumentpage = homepage.openMyDocumentsPage();
				Thread.sleep(8000);
				mydocumentpage.clickMyNotes();
				
				boolean isMynotesDisplayed= mydocumentpage.isMisDocumentLinkPresent(carpetakey);
			         
			
				softas.assertTrue(isMynotesDisplayed, jiraNumber+":"+issueSummary+":Verified Added notes in Document Display from  my document");
				logExtentStatus(extentLogger, isMynotesDisplayed,issueSummary+ ":Verified Added notes in Document Display from  my document", jiraNumber);
				
				if(isMynotesDisplayed){
					//Cleaning up - delete the note created in this test
					annotationsPage.clickDocumentNoteDeleteImage(annotationsPage.findDocumentNotePosition(docNote));
					annotationsPage.clickEliminarButton();
				}
			
            }
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}*/
	
	
	
	
}
