package com.trgr.quality.maf.tests;

import java.io.IOException;
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
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LinkUtilitiesPage;
import com.trgr.quality.maf.pages.LoginPage;

public class HeaderSectionTest extends BaseTest {
	HomePage homepage,homepagecopy;
	LinkUtilitiesPage linkutilitypage;
	LoginPage loginpage;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IllegalArgumentException, IOException {
		try{
			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
			homepage = loginpage.Login(username, password);			
			jsonReader = new JsonReader();

		}catch(Exception exc){

			extentLogger = setUpExtentTest(extentLogger, "HeaderSection", "Start HeaderSectionTest");			
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the HeaderSectionTest are not run.<br>"+ takesScreenshot_Embedded()+ "<br>"+displayErrorMessage(exc));
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

	

	@Test(priority=0, groups={"chparg","chpmex", "chpbr","chpury","chppe","chpchile"},description = "MAFQABANG-394")
	public void headerSectionLinks() throws Exception 
	{
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "HeaderSection", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify Header Section links available");

		try{
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String headerlinksKey = "headerlinks";
				JSONArray headerlinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild,headerlinksKey,extentLogger);

				for (Object headersectionlinks : headerlinks) {
					String linkname = headersectionlinks.toString().toUpperCase();
					boolean isLinkPresent = homepage.isGivenLinkPresentInHeader(linkname);					
					softas.assertTrue(isLinkPresent, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isLinkPresent, issueSummary,"Link",linkname, jiraNumber);
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

	

	// Verify the logged in user details
	@Test(priority=2, groups={"chparg","chpmex", "chpbr","chpury"}, description = "MAFQABANG-422")
	private void loggedinUserDetails() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "HeaderSection", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Verify Logged in UserDetails"); 

		try{
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				
				String expecteduserdetails = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "expecteduserdetails", extentLogger);
				Thread.sleep(3000);
			boolean userDetailsDisplayed = homepage.isUserLoggedinDetailsPresent(expecteduserdetails);
			softas.assertTrue(userDetailsDisplayed, jiraNumber+":"+issueSummary);
			logExtentStatus(extentLogger, userDetailsDisplayed, issueSummary, jiraNumber);
			}
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	//MAFAUTO-160
	@Test(priority=6, groups={"chpmex","chpbr"}, description = "MAFAUTO-160")
	public void goToCheckpointUSA() throws Exception{

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "HeaderSection", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Top Menu - Go to Checkpoint USA"); 
		
		try{
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String expectedurlkey = "expected_url";
				String expectedurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expectedurlkey, extentLogger);
				homepage.clickOnProductLogo();
				homepage.closeAllChildTabs();
				//homepage.ClickonGoToCheckpointUSA();		
				String actualHref =homepage.getGoToCheckpointUSAUrl();
				boolean urlverified =  actualHref!=null
						&& actualHref.contains(expectedurl);
						//homepage.isRedirectedUrlEquals(expectedurl);
				softas.assertTrue(urlverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, urlverified, issueSummary, expectedurlkey, expectedurl, jiraNumber);
				//homepage.switchToParentTab();
			}
		
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	//MAFAUTO-162
	@Test(priority=3, groups={"chpmex","chpbr","chppe","chpchile"}, description = "MAFAUTO-162")
	public void goToCheckpointWorld() throws Exception{

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "HeaderSection", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Top Menu - Go to Checkpoint World");	

		try{
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String expectedurlkey = "expected_url";
				String expectedurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expectedurlkey, extentLogger);
				homepage.clickOnProductLogo();
				Thread.sleep(3000);
			//	homepage.closeAllChildTabs();
				homepage.ClickonGoToCheckpointWorld();
				boolean urlverified = homepage.isRedirectedUrlEquals(expectedurl);

				softas.assertTrue(urlverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, urlverified, issueSummary, expectedurlkey, expectedurl, jiraNumber);
			}
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	//MAFAUTO-161
	@Test(priority=4, groups={"chpmex"}, description = "MAFAUTO-161")
	public void FAQ() throws Exception{
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "HeaderSection", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Top Menu - FAQS");

		try{
				homepage.openHomepage();
				homepage.clickOnFAQ();
				Thread.sleep(5000);
				boolean isfaqlinkclicked=homepage.verifyExpandAndCollapseFaq();
				softas.assertTrue(isfaqlinkclicked, jiraNumber+":"+ issueSummary+"Verified expand and collapse of FAQ questions");
				logExtentStatus(extentLogger, isfaqlinkclicked, issueSummary+ "Verified expand and collapse of FAQ questions", jiraNumber);
			
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=5, groups={"chpbr"}, description = "MAFQABANG-151")
	public void headerSectionUniqueUrls() throws Exception{

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "HeaderSection", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Verify HeaderSectionUniqueUrls");
		JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

		try{
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String name=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"name",extentLogger);
				String email=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"email",extentLogger);
				String phone=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"phone",extentLogger);
				String company=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"company",extentLogger);
				String message=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"Message",extentLogger);				
				String expectedurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "rtbrazilurl", extentLogger);
				String elearningurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "elearningurl", extentLogger);
				homepage.clickOnProductLogo();
				homepage.openHomepage();
				homepage.ClickonGoToJournalOnlineCourt();
				boolean journalurlverified = homepage.isRedirectedUrlEquals(expectedurl);		

				softas.assertTrue(journalurlverified, jiraNumber+": On Clicking Go To Journal link opens new tab");
				logExtentStatus(extentLogger, journalurlverified, "On Clicking Go To Journal link opens new tab", jiraNumber);
				
				homepage.openHomepage();
				homepage.clickOneLearningLink();
				boolean elearningurlverified = homepage.isRedirectedUrlEquals(elearningurl);				

				softas.assertTrue(elearningurlverified, jiraNumber+":On Clicking eLearning link opens new tab");
				logExtentStatus(extentLogger, elearningurlverified, "On Clicking eLearning link opens new tab", jiraNumber);

				homepage.openHomepage();
				/* contact us link not applicable in application
				  homepage.clickOnContactUs();
				boolean iscontactusexpected=homepage.verifyContactUS(name, email, phone, company, message);

				softas.assertTrue(iscontactusexpected, jiraNumber+":Verified Contact Us ");
				logExtentStatus(extentLogger, iscontactusexpected, "Verified Contact Us ", jiraNumber);*/

				boolean allLinksValidated =
						journalurlverified &&
						elearningurlverified ;
						//iscontactusexpected;

				softas.assertTrue(allLinksValidated, jiraNumber+":"+issueSummary);
				logExtentStatus(extentLogger, allLinksValidated, issueSummary, jiraNumber);

			}
		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	
	@Test(priority=5, groups={"chpchile"}, description = "MAFQABANG-621")
	public void goToLegalArea() throws Exception{

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "HeaderSection", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Top Menu - Go to Legal Area");
		JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

		try{
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;				
				String expectedurlkey = "expected_url";
				String expectedurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expectedurlkey, extentLogger);
				homepage.clickOnProductLogo();
				homepage.closeAllChildTabs();
				homepage.ClickonGoToLegalArea();
				boolean urlverified = homepage.isRedirectedUrlEquals(expectedurl);

				softas.assertTrue(urlverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, urlverified, issueSummary, expectedurlkey, expectedurl, jiraNumber);

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
