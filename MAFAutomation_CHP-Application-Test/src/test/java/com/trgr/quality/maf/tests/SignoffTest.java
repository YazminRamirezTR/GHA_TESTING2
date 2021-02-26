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
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
import com.trgr.quality.maf.pages.SignOffPage;

public class SignoffTest extends BaseTest {

	/* Help_TC_001-Help Link_Verify Help Link 
	 * MAFAUTO-221
	 */
	HomePage homepage;
	LoginPage loginpage;
	SignOffPage signoff;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas ;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		try{

			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
			homepage = loginpage.Login(username, password);

		}catch(Exception exc){

			extentLogger = setUpExtentTest(extentLogger, "Signoff", "StartSignoffTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the Signoff test are not run.<br>"+ takesScreenshot_Embedded()+ "<br>"+displayErrorMessage(exc));
			extentReports.endTest(extentLogger);			
			Assert.assertTrue(false);
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void afterClass() {//Use this incase the signoff fails in test
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
		}
	}


	/* Help_TC_001-Help Link_Verify Help Link 
	 * MAFAUTO-221
	 */

	// please dont remove priority for sign off very important
	@Test(priority = 0,groups = {"chparg","chpbr","chpury","chppe","chpchile"}, description="MAFQABANG-389")
	public void Signoff() throws  IOException {
		try{
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();	
			extentLogger = setUpExtentTest(extentLogger, "Signoff", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Sign off validation");

			boolean signOffLinkPresent = homepage.isSignOffLinkPresent();
			softas.assertTrue(signOffLinkPresent,  jiraNumber+": Sign Off Link Displayed");
			logExtentStatus(extentLogger, signOffLinkPresent, "Sign Off Link Displayed", jiraNumber);

			signoff = homepage.getSignOffPage();

			boolean signOffPageDisplayed = signoff.issignoffpagedisplay();
			logExtentStatus(extentLogger, signOffPageDisplayed, issueSummary, jiraNumber);
			softas.assertTrue(signOffPageDisplayed, jiraNumber+":"+issueSummary);

			if (productUnderTest.equals("chpbr")){
				boolean sessionDataPresent =signoff.isSesssionDataPresent();

				softas.assertTrue(sessionDataPresent, jiraNumber+":"+issueSummary+" : Verify Session Data");
				logExtentStatus(extentLogger, sessionDataPresent, "Verify Session Data", jiraNumber);
			}

		}catch(Exception exc){
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		}finally{
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	
	
	// please dont remove priority for sign off very important
	@Test(priority = 0,groups = {"chpmex"}, description="MAFAUTO-221")
	public void displayOfSignOffSummary() throws  IOException {
			try{
				testResult = Reporter.getCurrentTestResult();
				softas = new SoftAssert();	
				extentLogger = setUpExtentTest(extentLogger, "Signoff", testResult.getMethod().getMethodName());

				String jiraNumber = testResult.getMethod().getDescription();
				String issueSummary = getIssueTitle(jiraNumber, 
						"Sign off validation");

				boolean signOffLinkPresent = homepage.isSignOffLinkPresent();
				softas.assertTrue(signOffLinkPresent,  jiraNumber+": Sign Off Link Displayed");
				logExtentStatus(extentLogger, signOffLinkPresent, "Sign Off Link Displayed", jiraNumber);

				signoff = homepage.getSignOffPage();

				boolean signOffPageDisplayed = signoff.issignoffpagedisplay();
				logExtentStatus(extentLogger, signOffPageDisplayed, issueSummary, jiraNumber);
				softas.assertTrue(signOffPageDisplayed, jiraNumber+":"+issueSummary);

				//MAFAUTO-221
				boolean signOffSummaryValidated = signoff.validateSignoffSummary(); 

				softas.assertTrue(signOffSummaryValidated, jiraNumber+":"+issueSummary+" : Verify SignoffSummary");
				logExtentStatus(extentLogger, signOffSummaryValidated, "Verify SignoffSummary", new String[] {"MAFAUTO-221",jiraNumber});


				if (productUnderTest.equals("chpbr")){
					boolean sessionDataPresent =signoff.isSesssionDataPresent();

					softas.assertTrue(sessionDataPresent, jiraNumber+":"+issueSummary+" : Verify Session Data");
					logExtentStatus(extentLogger, sessionDataPresent, "Verify Session Data", jiraNumber);
				}

			}catch(Exception exc){
				logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
				softas.assertTrue(false, "Exception in Test");
			}finally{
				extentReports.endTest(extentLogger);
				softas.assertAll();
			}
		}


	// please dont remove priority for sign off very important
	@Test(priority = 1, groups = { "chparg","chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-390")
	public void NavigationfromSignofftologinPage() throws IOException {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Signoff", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Validate New Session Link on Sign Off");

			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
			homepage = loginpage.Login(username, password);

			signoff = homepage.getSignOffPage();
			Thread.sleep(4000);
			if(signoff.isNewsesssionlinkPresent())
			{
			signoff.clikNewSession();
			}
			boolean loginPageDisplayed = loginpage.isLoginPageDisplayed();
			softas.assertTrue(loginPageDisplayed, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, loginPageDisplayed, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	//Warning : Running this test is time consuming.
	//@Test(priority=2,groups = { "chpury" }, description = "MAFQABANG-546")
	public void WaitForTimeOutAndRestartSession() throws Exception {
		//Warning : Running this test is time consuming.
		//It stays idle for time out period. (Ex: 30 Minits)
		// Set waitfortimeout in Data File to "Y" to run this scenario
		SoftAssert softAssert = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			JsonReader jsonReader = new JsonReader();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Restart Session after time out");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String timeoutKey = "sessiontimeoutinminits";
				int timeOutInMinits = Integer.parseInt(jsonReader.readKeyValueFromJsonObject(jsonObjectChild,timeoutKey,extentLogger));
				boolean waitForTimeOut = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"waitfortimeout",extentLogger).equals("Y");
	
				String freewordKey = "freeword";
				String freeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);
	
				String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
				String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
	
				if(!loginpage.isLoginPageDisplayed()){
					this.homepage.clickSignOff();
				}
				HomePage homepage = loginpage.Login(username, password);
				homepage = homepage.openHomepage();
				homepage.clickClear();
				homepage.clickThematicSearchRadioButton();
				homepage.enterFreewordOnQuickSearch(freeword);
				SearchResultsPage searchResultsPage = homepage.clickSearch();
	
				boolean searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
	
				SignOffPage signOffPage=null;
				if(searchResultsDisplayed&&waitForTimeOut){
					signOffPage = searchResultsPage.waitTillSessionTimeOut(timeOutInMinits);
				}
	
				boolean sessionTimedOut = searchResultsDisplayed
						&& waitForTimeOut
						&& (signOffPage!=null)
						&& signOffPage.isSessionExpired()
						&& signOffPage.isRestartSessionLinkDisplayed();
	
				if(sessionTimedOut){
					signOffPage.clickRestarSessiontLink();
					loginpage.Login(username, password);
				}
	
				boolean sessionResumed = sessionTimedOut
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
	
				softAssert.assertTrue(sessionResumed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, sessionResumed, issueSummary, jiraNumber);
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
