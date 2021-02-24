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
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LinkUtilitiesPage;
import com.trgr.quality.maf.pages.LoginPage;

public class LinkUtilityTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage;
	public ITestResult testResult;
	LinkUtilitiesPage linkutilitypage;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void startAlertTest() throws Exception {
		try {
			loginpage = new LoginPage(driver, ProductUrl);
			String Username = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".username");
			String Password = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".password");
			homepage = loginpage.Login(Username, Password);
			jsonReader = new JsonReader();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Link Utility", "StartLinkUtilityTest");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the Link Utility test are not run.<br>"
							+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}
	}

	@AfterClass(alwaysRun = true)
	public void endAlertTest(){
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
		}
	}

	@Test(priority = 0, groups = { "chpury","chppe"}, description = "MAFQABANG-560")
	public void valdiateLinksPresent() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "LinkUtilityPage", testResult.getMethod().getMethodName());
			boolean linkverified = false;
			boolean islinkutilitypagelanded = false;
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Link Utility Page verification");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String linksKey = "links_present";
				JSONArray linksArray = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild,linksKey, extentLogger);

				linkutilitypage = homepage.openLinkUtilityPage();
				islinkutilitypagelanded = linkutilitypage.isLinkUtilityPageDisplayed();

				softas.assertTrue(islinkutilitypagelanded, jiraNumber + ": Link Utility Page Displayed");
				logExtentStatus(extentLogger, islinkutilitypagelanded, "Link Utility Page Displayed", jiraNumber);

				boolean allLinksVerified = true;
				for (int row = 0; row < linksArray.size(); row++) {
					linkverified = linkutilitypage.validateLinksPresent(linksArray.get(row).toString());
					softas.assertTrue(linkverified, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, linkverified, issueSummary, "Link ", linksArray.get(row).toString(),
							jiraNumber);
					allLinksVerified &= linkverified;
				}
				
				allLinksVerified &= linkverified;
				softas.assertTrue(islinkutilitypagelanded && allLinksVerified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, islinkutilitypagelanded && allLinksVerified, issueSummary, jiraNumber);
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
