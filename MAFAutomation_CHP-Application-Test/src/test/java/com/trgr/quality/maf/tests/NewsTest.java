package com.trgr.quality.maf.tests;

import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
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
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.NewsPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class NewsTest extends BaseTest {
	/*
	 * This Test verifies the presence of News Tab It validates the error
	 * message when the data provided does not correspond to any results It
	 * validates the result list for appropriate data It validates the clear
	 * button functionality
	 */

	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	NewsPage newspage;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	SearchResultsPage searchResultsPage;
	DocumentDisplayPage documentpage;
	SearchPage searchpage;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void startNewsTest() throws IllegalArgumentException, IOException, ParseException {
		try {
			//driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			loginpage = new LoginPage(driver, ProductUrl);
			String Username = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".username");
			String Password = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".password");
			homepage = loginpage.Login(Username, Password);
			jsonReader = new JsonReader();
			newspage = homepage.ClickonNewsTab();
		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "News", "StartNewsTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the News test are not run.<br>"
					+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() throws IllegalArgumentException, IOException{
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
			(new HomePage(driver)).clickSignOff();
			System.out.println("Exception in AfterClass: "+e);
		}
	}

	/*
	 * This test verifies News search with invalid date format then verifying the error message
	 */
	@Test(priority = 1, groups = { "chparg", "chpmex", "chpbr", "chpury",
			"chppe","chpchile" }, description = "MAFQABANG-572")
	public void SearchNewswithInvalidDateFormat() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());
			boolean ismsgverified = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Search News with Invalid date format then verify the error message");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String invaliduntildateKey = "invaliduntildate";
				String invaliduntildateVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, invaliduntildateKey,
						extentLogger);
				String invalidsincedateKey = "invalidsincedate";
				String invalidsincedateVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, invalidsincedateKey,
						extentLogger);
				String errorMessagekey = "error_message";
				String errorMessageVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, errorMessagekey,
						extentLogger);

				newspage = homepage.ClickonNewsTab();
				newspage.enterSinceDate(invalidsincedateVal);
				newspage.enterUntildate(invaliduntildateVal);
				newspage.clicksearch();

				ismsgverified = newspage.InvalidDateFormatMessage(errorMessageVal);
				softas.assertTrue(ismsgverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, ismsgverified, issueSummary, errorMessagekey, errorMessageVal,
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

	/*
	 * This test verifies news search with valid Input
	 */
	@Test(priority = 2, groups = { "chparg", "chpmex", "chpbr", "chpury",
			"chppe","chpchile" }, description = "MAFQABANG-152")
	public void NewsResultsWithValidInput()
			throws IllegalArgumentException, IOException, InterruptedException, ParseException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Search news with valid Since and Until date");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String untildateKey = "untildate";
				String untildate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, untildateKey, extentLogger);
				String sincedateKey = "sincedate";
				String sincedate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, sincedateKey, extentLogger);

				newspage = homepage.ClickonNewsTab();
				newspage.enterSinceDate(sincedate);
				newspage.enterUntildate(untildate);

				searchResultsPage = newspage.clicksearch();
				searchResultsDisplayed = searchResultsPage != null;

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, issueSummary, untildateKey + "," + sincedateKey,
								untildate + "," + sincedate, jiraNumber);
				}

				softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, untildateKey + "," + sincedateKey,
						untildate + "," + sincedate, jiraNumber);
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
	 * This test verifies the clear button functionality in News Page
	 */
	@Test(priority = 3, groups = { "chparg", "chpmex", "chpbr", "chpury",
			"chppe","chpchile" }, description = "MAFQABANG-154")
	public void ClearButtonInNewsSearch() throws IOException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());
			boolean isclearbuttonworking = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Clearing test data Entered for News in the Filter by date");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String untildateKey = "untildate";
				String untildate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, untildateKey, extentLogger);
				String sincedateKey = "sincedate";
				String sincedate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, sincedateKey, extentLogger);

				newspage = homepage.ClickonNewsTab();
				newspage.enterSinceDate(sincedate);
				newspage.enterUntildate(untildate);
				newspage.clickClearButton();

				isclearbuttonworking = newspage.isSinceDateEmpty() && newspage.isUntilDateEmpty();

				softas.assertTrue(isclearbuttonworking, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isclearbuttonworking, issueSummary, untildateKey + "," + sincedateKey,
						untildate + "," + sincedate, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 4, groups = { "chparg" }, description = "MAFQABANG-582")
	public void ValidateViewNewsIFRSLink() throws InterruptedException, IOException {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Click IFRS link on News page and verify NIIF link");

			newspage = homepage.ClickonNewsTab();
			newspage.clickIFRSNewsLink();

			boolean islinkverified = newspage.isNIIFLinkDisplayed();
			softas.assertTrue(islinkverified, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, islinkverified, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * This method verifies news search for no results keyword
	 */
	@Test(priority = 5, groups = { "chparg", "chpmex", "chpbr", "chpury",
			"chppe","chpchile" }, description = "MAFQABANG-481")
	public void searchNewsforNoResults() throws IOException, InterruptedException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());
			boolean errmsgDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Searching news with invalid data and verify error message");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String untildateKey = "invaliduntildate";
				String invaliduntildate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, untildateKey,
						extentLogger);
				String sincedateKey = "invalidsincedate";
				String invalidsincedate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, sincedateKey,
						extentLogger);
				String errorMessageKey = "errormessage";
				String errorMessage = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, errorMessageKey,
						extentLogger);
				homepage.openHomepage();
				NewsPage newspage = homepage.ClickonNewsTab();
				Thread.sleep(5000);
				newspage.enterSinceDate(invalidsincedate);
				newspage.enterUntildate(invaliduntildate);
				newspage.clicksearch();

				errmsgDisplayed = newspage.isErrorMsgDisplayed(errorMessage);

				System.out.println(errmsgDisplayed);
				softas.assertTrue(errmsgDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, errmsgDisplayed, issueSummary, errorMessageKey, errorMessage, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 6, groups = { "chparg", "chpury" }, description = "MAFQABANG-547")
	public void checkdatepicker() throws InterruptedException, IOException {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "checking date picker functionality");

			newspage = homepage.ClickonNewsTab();
			newspage.selectdatepicker();
			newspage.selecttodaysdateinpicker();

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
			// get current date time with Date()
			Date date = new Date();
			// Now format the date
			String todaysdate = dateFormat.format(date);
			newspage.enterUntildate(todaysdate);

			searchResultsPage = newspage.clicksearch();
			boolean searchResultsDisplayed = searchResultsPage != null;

			if (!searchResultsDisplayed) {
				boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
				if (noResultsFound)
					logExtentNoResultsAsInfo(extentLogger, issueSummary, "resulted in zero results : ", todaysdate,
							jiraNumber);
			}

			softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, "", todaysdate, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * This test verifies the display of news page
	 */
	@Test(priority = 7, groups = { "chpury", "chparg", "chppe", "chpchile" }, description = "MAFQABANG-550")
	public void NewsPageValidation() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());
			boolean newspageverified = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "News Page validation - Verify All Expected Fields");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String seeifrslinkavailable = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"seeifrslinkavailable", extentLogger);

				newspage = homepage.ClickonNewsTab();

				newspageverified = newspage.isFilterbydateDispalyed() && newspage.isSincedateDispalyed()
						&& newspage.isUntildateDispalyed() && newspage.isClearButtonDisplayed()
						&& newspage.isSearchButtonDisplayed() && newspage.isNoResultMsgDispalyed();

				softas.assertTrue(newspageverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, newspageverified, issueSummary, jiraNumber);
				if (seeifrslinkavailable.equalsIgnoreCase("Y")) {
					boolean isspecialfieldsdisplayed = newspage.isSeeIFRSNewsDispalyed();

					softas.assertTrue(isspecialfieldsdisplayed, jiraNumber + ":" + "IFRS News Link is displayed");
					logExtentStatus(extentLogger, isspecialfieldsdisplayed, "IFRS News Link is displayed", jiraNumber);
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

	// This method will validate news page for chpmex and chpbr and the display
	// is bit different from other chp applications
	@Test(priority = 8, groups = { "chpmex", "chpbr" }, description = "MAFQABANG-479")
	public void NewsPageDisplayValidation() throws InterruptedException, IOException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());
			boolean newspageverified = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "News Page validation - Verify All Expected Fields");

			newspage = homepage.ClickonNewsTab();
			newspageverified = newspage.isFilterbydateDispalyed() && newspage.isSincedateDispalyed()
					&& newspage.isUntildateDispalyed() && newspage.isClearButtonDisplayed()
					&& newspage.isSearchButtonDisplayed() && newspage.isWhatsNewDisplayed()
					&& newspage.isNoResultMsgDispalyed();

			softas.assertTrue(newspageverified, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, newspageverified, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * Providing only Since Date and searching for News
	 */
	@Test(priority = 9, groups = { "chpury" }, description = "MAFQABANG-566")
	public void SearchForNewsWithSinceDate() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed, docpageverified = false;

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Verify Search results,document page when only Since Date is entered for News");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String sincedateKey = "sincedate";
				String sincedate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, sincedateKey, extentLogger);

				newspage = homepage.ClickonNewsTab();
				newspage.enterSinceDate(sincedate);
				searchResultsPage = newspage.clicksearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound) {
						logExtentNoResultsAsInfo(extentLogger, issueSummary, sincedateKey,
								sincedate + " -resulted in no search results", jiraNumber);
						continue;
					}

				}

				// Verifying Document display page displayed properly or not
				documentpage = searchResultsPage.clickFirstLink();

				docpageverified = documentpage.isInformationClaveTabDisplayed()
						&& documentpage.isTreeOfContentTabDisplayed() && documentpage.resultListTabDisplayed()
						&& documentpage.isRevOfSearchTabDisplayed() && documentpage.isListofDocumentsLinkDispalyed()
						&& documentpage.isNewSearchLinkDispalyed()  && documentpage.isDeliveryBarDispalyed();

				softas.assertTrue(docpageverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, docpageverified, issueSummary, sincedateKey, sincedate, jiraNumber);
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
	 * This test verifies News Search without input
	 */
	@Test(priority = 11, groups = { "chparg", "chpmex", "chpbr", "chpury",
			"chppe","chpchile"}, description = "MAFQABANG-153")
	public void SearchNewswithBlankInput()
			throws IllegalArgumentException, IOException, InterruptedException, ParseException {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());
			boolean ismsgverified = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Search for News with blank input");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "error_message";
				String errrMessage = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);

				newspage = homepage.ClickonNewsTab();
				Thread.sleep(2000);
				newspage.enterSinceDate("");
				Thread.sleep(1000);
				newspage.enterUntildate("");
				Thread.sleep(1000);
				newspage.clicksearch();

				ismsgverified = newspage.BlankInputMessage(errrMessage);
				softas.assertTrue(ismsgverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, ismsgverified, issueSummary, key, errrMessage, jiraNumber);

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
	 * This test verifies whether results displayed  corresponding to the specified dates .
	 */
	@Test(priority = 1, groups = { "chparg"}, description = "LLOT-4619")
	public void NewsSearch() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "News", testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"News - Error in dates");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String untildateKey = "date";
				String untildate = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, untildateKey, extentLogger);
				String sincedateKey = "date";
				String sincedate =untildate;

				newspage = homepage.ClickonNewsTab();
				newspage.enterSinceDate(sincedate);
				newspage.enterUntildate(untildate);

				String date[] = untildate.split("/");
				String yyyyMMDD = date[2]+"/"+date[1]+"/"+date[0];
				
				
				searchResultsPage = newspage.clicksearch();
				searchResultsDisplayed = searchResultsPage != null;

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, issueSummary, untildateKey + "," + sincedateKey,
								untildate + "," + sincedate, jiraNumber);
				}

				softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, untildateKey + "," + sincedateKey,
						untildate + "," + sincedate, jiraNumber);
			
				boolean isexpectedresultsdisplayed=searchResultsPage.isSpecifiedDateDisplayedInTitle(yyyyMMDD);
				softas.assertTrue(isexpectedresultsdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isexpectedresultsdisplayed, issueSummary,  jiraNumber);
				if(!isexpectedresultsdisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "LLOT-4619");
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

	
	
}
