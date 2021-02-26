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
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;


public class HelpTest extends BaseTest {

	/* Help_TC_001-Help Link_Verify Help Link */
	HomePage homepage;
	LoginPage loginpage;
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
			jsonReader = new JsonReader();

		}catch(Exception exc){

			extentLogger = setUpExtentTest(extentLogger, "Help", "Start HelpTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the Help test are not run.<br>"+ takesScreenshot_Embedded()+ "<br>"+displayErrorMessage(exc));
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

	@Test(priority = 0, groups = { "chpmex"}, description = "MAFAUTO-172")
	public void HelpLinkPresence() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Help", testResult.getMethod().getMethodName());
			boolean isHelpLinkPresent=false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Help - Show Help ");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String expectedurlkey = "expectedurl";
				String qcurlkey="expectedurlqckey";
				
				
				homepage=homepage.openHomepage();
				isHelpLinkPresent = homepage.isHelpLinkPresent();			
				softas.assertTrue(isHelpLinkPresent, jiraNumber + ":" + issueSummary+ " : Help link present on the page");
				logExtentStatus(extentLogger, isHelpLinkPresent, issueSummary+ " : Help link present on the page", jiraNumber);
				homepage.clickHelpLink();
				if(BaseTest.Envirnomentundertest.equals("client")) {
					String expectedurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expectedurlkey, extentLogger);
					boolean urlverified = homepage.isRedirectedUrlEquals(expectedurl);
					softas.assertTrue(urlverified, jiraNumber + ":" +issueSummary + ":validated help page");
					logExtentStatus(extentLogger, urlverified, issueSummary + ":validated help page", jiraNumber);
				}
				else {
					String expectedurlqc = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, qcurlkey, extentLogger);
					boolean urlverified = homepage.isRedirectedUrlEquals(expectedurlqc);
					softas.assertTrue(urlverified, jiraNumber + ":" +issueSummary + ":validated help page");
					logExtentStatus(extentLogger, urlverified, issueSummary + ":validated help page", jiraNumber);
				}
				
				  
				
				
				//boolean urlverified = homepage.isRedirectedUrlEquals(expectedurl);
				//softas.assertTrue(urlverified, jiraNumber + ":" +issueSummary + ":validated help page");
				//logExtentStatus(extentLogger, urlverified, issueSummary + ":validated help page", jiraNumber);
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
	 * This test is checking the same redirected url for 
	 * all other countries except mex.same as MAFAUTO-172
	 */
	@Test(priority = 1, groups = { "chparg","chpury","chppe","chpchile"}, description = "MAFQABANG-616")
	public void HelpLinkPresenceforAll() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Help", testResult.getMethod().getMethodName());
			boolean isHelpLinkPresent=false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Help - Show Help ");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String expectedurlkey = "expectedurl";
				String expectedurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expectedurlkey, extentLogger);
				
				homepage=homepage.openHomepage();
				//verifying is help link present in the heade4r section
				isHelpLinkPresent = homepage.isHelpLinkPresent();			
				softas.assertTrue(isHelpLinkPresent, jiraNumber + ":" + issueSummary+ " : Help link present on the page");
				logExtentStatus(extentLogger, isHelpLinkPresent, issueSummary+ " : Help link present on the page", jiraNumber);
				
				//click on help link
				homepage.clickHelpLink();
				boolean urlverified = homepage.isRedirectedUrlEquals(expectedurl);
				softas.assertTrue(urlverified, jiraNumber + ":" +issueSummary + ":validated help page");
				logExtentStatus(extentLogger, urlverified, issueSummary + ":validated help page", jiraNumber);
				if(!urlverified){//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-277");
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

	@Test(priority = 1, groups = {  "chpbr" }, description = "MAFQABANG-145")
	public void RedirectedHelpLinkValidation() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Help", testResult.getMethod().getMethodName());
			boolean isHelpLinkPresent=false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Help Link_Verify Help Link");
			
			homepage=homepage.openHomepage();
			isHelpLinkPresent = homepage.isHelpLinkPresent();			
			softas.assertTrue(isHelpLinkPresent, jiraNumber + ":" +  "Help link present on the page");
			logExtentStatus(extentLogger, isHelpLinkPresent,  " Help link present on the page", jiraNumber);
			homepage.clickHelpLink();
			boolean ishelptextpresent = homepage.validateHelpPage();
			softas.assertTrue(ishelptextpresent, jiraNumber + ":" +issueSummary + ":validated help page");
			logExtentStatus(extentLogger, ishelptextpresent, issueSummary + ":validated help page", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

}
