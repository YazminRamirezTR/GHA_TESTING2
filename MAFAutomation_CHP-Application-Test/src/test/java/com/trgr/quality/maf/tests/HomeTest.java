package com.trgr.quality.maf.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
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
import com.trgr.quality.maf.handlers.ElementHandler;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.AlertPage;
import com.trgr.quality.maf.pages.CourseAndSeminar;
import com.trgr.quality.maf.pages.DeliveryPage;
import com.trgr.quality.maf.pages.DoctrinePage;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.FormsPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.JurisprudencePage;
import com.trgr.quality.maf.pages.LegislationPage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.NewsPage;
import com.trgr.quality.maf.pages.SaveAndSchedulePage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
import com.trgr.quality.maf.pages.SignOffPage;

public class HomeTest extends BaseTest {

	/*
	 * Module Name : Application Verification Feature Names : Description
	 * Application Verification_TC_001 : Verify the tabs available in the
	 * application post login //MAFAUTO-134 - Schedule search widget on home Page
	 * (Scheduled search creation/deletion and empty Widget message validation)
	 * 
	 */

	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	CourseAndSeminar courseandseminar;
	SearchResultsPage searchresultpage;
	DocumentDisplayPage docdisplaypage;
	DeliveryPage deliveryPage;
	AlertPage alertpage;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	NewsPage newspage;
	String username, password;
	JsonReader jsonReader;
	ElementHandler elementhandler;
 
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		try {

			loginpage = new LoginPage(driver, ProductUrl);
			username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
			homepage = loginpage.Login(username, password);
			jsonReader = new JsonReader();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "HomePage", "StartHomeTest");

			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the Home test are not run.<br>"
					+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false, "Exception in Test");
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		try {
			if (homepage != null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			// Adding this code to avoid sign off skip in case of unexpected conditions,
			// which sets home page to null
		} catch (Exception e) {
		}
	}

	@Test(priority = 0, groups = { "chparg", "chpmex", "chpbr", "chppe", "chpchile" }, description = "MAFQABANG-73")
	public void ApplicationVerificationpostlogin() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Application Verification of Home Page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String advanceSearchLinkExpected = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"checkadvancesearchoptiononhomepage", extentLogger);

				if (advanceSearchLinkExpected.equalsIgnoreCase("Y")) {
					boolean isadvanceSearchLinkDisplayed = homepage.isAdvancedSearchLinkDisplayed();
					softas.assertTrue(isadvanceSearchLinkDisplayed, jiraNumber + ":" + issueSummary
							+ " : Verified Advance search link present in the homepage");
					logExtentStatus(extentLogger, isadvanceSearchLinkDisplayed,
							issueSummary + " :Verified Advance search link present in the homepage", jiraNumber);
				}

				boolean isProductLogoDisplayed = homepage.verifyProductLogoDisplayed();
				softas.assertTrue(isProductLogoDisplayed,
						jiraNumber + ":" + issueSummary + ": Verified product logo present in the homepage");
				logExtentStatus(extentLogger, isProductLogoDisplayed,
						issueSummary + " : Verified product logo present in the homepage", jiraNumber);

				boolean isUsertextDisplayed = homepage.isLoggedInUserInfoDisplayed1();
				softas.assertTrue(isUsertextDisplayed,
						jiraNumber + ":" + issueSummary + " : Verified UserText present in the homepage");
				logExtentStatus(extentLogger, isUsertextDisplayed,
						issueSummary + " :Verified UserText present in the homepage", jiraNumber);

				boolean isBasicSearchDisplayed = homepage.verifyBasicSearchDisplayed();
				softas.assertTrue(isBasicSearchDisplayed,
						jiraNumber + ":" + issueSummary + " : Verified Basic search section present in the homepage");
				logExtentStatus(extentLogger, isBasicSearchDisplayed,
						issueSummary + " :Verified Basic search section present in the homepage", jiraNumber);

				boolean isSignoutLinkDisplayed = homepage.verifySignoutLinkDisplayed();
				softas.assertTrue(isSignoutLinkDisplayed,
						jiraNumber + ":" + issueSummary + " : Verified SignOut link present in the homepage");
				logExtentStatus(extentLogger, isSignoutLinkDisplayed,
						issueSummary + " : Verified SignOut link present in the homepage", jiraNumber);

				boolean isWidgetDisplayed = homepage.verifyWidgetDisplayed();
				softas.assertTrue(isWidgetDisplayed,
						jiraNumber + ":" + issueSummary + " : Verified Widget present in the homepage");
				logExtentStatus(extentLogger, isSignoutLinkDisplayed, "Verified Widget present in the homepage",
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

	@Test(priority = 1, groups = { "chparg", "chpmex", "chpbr", "chppe", "chpchile" }, description = "MAFQABANG-76")
	public void widgetsinhomepage() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		
		
		boolean flag = false;
		
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Application verification on Widgets in Homepage");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String checkwidgetforallthematicareas = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"checkwidgetforallthematicareas", extentLogger);
				Thread.sleep(1500);
				
				homepage.openHomepage();
					
				flag = homepage.isExpectedWidgetsDisplayedInHomePage();

				softas.assertTrue(flag, jiraNumber + ":" + issueSummary + " : Verify is Widget Displayed");
				logExtentStatus(extentLogger, flag, issueSummary + " : Verify is Widget Displayed", jiraNumber);

				if (checkwidgetforallthematicareas.equalsIgnoreCase("Y")) {
					// MAFAUTO-137
					boolean iswidgetsdisplayed = homepage.verifyThematicareadropdowns();
					softas.assertTrue(iswidgetsdisplayed, jiraNumber + ":" + issueSummary
							+ " : Verified Widget displayed as thematicdropdown options in the homepage");
					logExtentStatus(extentLogger, iswidgetsdisplayed,
							issueSummary + " : Verified Widget displayed as thematicdropdown options in the homepage",
							jiraNumber);
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

	@Test(priority = 2, groups = {"chparg", "chppe", "chpchile" }, description = "MAFQABANG-75")
	public void availableSearchOptionsInHomepage() throws Exception {
		
		boolean flag = false; 
		boolean standardSearchValidated = false;
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Types of Search Available");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicSearchApplicable = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"checkthematicsearch", extentLogger);
				String standardSearchApplicable = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"checkstandardnumbersearch", extentLogger);

				if (thematicSearchApplicable.equalsIgnoreCase("Y")) {
					homepage.openHomepage();
					homepage.clickRefreshforThematicSearch();
					flag = homepage.isThematicSearchBoxDisplayed();

					flag &= homepage.isFreewordFieldDisplayed();
					if (!flag) {
						homepage.clickRefreshforThematicSearch();
						flag = homepage.isThematicSearchBoxDisplayed();

						flag &= homepage.isFreewordFieldDisplayed();
					}

					softas.assertTrue(flag, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, flag, issueSummary + ":Thematic Search validated", jiraNumber);

				}

				if (standardSearchApplicable.equalsIgnoreCase("Y")) {
					homepage.openHomepage();
					standardSearchValidated = homepage.isStandardSearchboxDisplayed();
					if (!standardSearchValidated) {
						homepage.clickStandardNumberRadioButton();
						standardSearchValidated = homepage.isStandardSearchboxDisplayed();
					}

					softas.assertTrue(standardSearchValidated,
							jiraNumber + ":" + issueSummary + " : Verified Standard Search Option");
					logExtentStatus(extentLogger, standardSearchValidated,
							issueSummary + " : Verified standard Search option", jiraNumber);

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

	@Test(priority = 2, groups = { "chpmex" }, description = "MAFAUTO-98")
	public void thematicSearchAndCleanInHomepage() throws Exception {
		boolean flag = false;
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Home Page - Widget - Thesaurus Search");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				homepage.openHomepage();
				flag = homepage.verifyThematicSearchOptions();

				softas.assertTrue(flag, jiraNumber + ":" + issueSummary + " : Verify Thematic Search Option");
				logExtentStatus(extentLogger, flag, issueSummary + " : Verify Thematic Search Option", jiraNumber);

				String firstsearchtermbysuggestions = "firstthematicsuggestion";
				String firstsearchtermbysuggestion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						firstsearchtermbysuggestions, extentLogger);

				String secondsearchtermbysuggestions = "secondthematicsuggestion";
				String secondsearchtermbysuggestion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						secondsearchtermbysuggestions, extentLogger);

				String thirdsearchtermbysuggestions = "thirdthematicsuggestion";
				String thirdsearchtermbysuggestion = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						thirdsearchtermbysuggestions, extentLogger);

				// MAFAUTO-98
				homepage.enterFirstInputTextBySuggestions(firstsearchtermbysuggestion);
				softas.assertTrue(flag, jiraNumber + ":" + firstsearchtermbysuggestion + " : Verified Search by suggestions");
				logExtentStatus(extentLogger, flag, firstsearchtermbysuggestion + " : Verified Search by suggestions", jiraNumber);
				homepage.enterSecondInputTextBySuggestions(secondsearchtermbysuggestion);
				softas.assertTrue(flag, jiraNumber + ":" + secondsearchtermbysuggestion + " : Verified Search by suggestions");
				logExtentStatus(extentLogger, flag, secondsearchtermbysuggestion + " : Verified Search by suggestions", jiraNumber);
				homepage.enterThirdInputTextBySuggestions(thirdsearchtermbysuggestion);
               homepage.clickClear();
				homepage.enterFirstInputTextBySuggestions(firstsearchtermbysuggestion);
				searchresultpage = homepage.clickSearch();
				flag = searchresultpage.isTotalDocumentCountPresent() && searchresultpage.IsFilterLinksDisplayed();

				softas.assertTrue(flag, jiraNumber + ":" + thirdsearchtermbysuggestion + " : Verified Search by suggestions");
				logExtentStatus(extentLogger, flag, thirdsearchtermbysuggestion + " : Verified Search by suggestions", jiraNumber);
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
	 * This test also covers MAFQABANG-54 and MAFQABANG-53
	 */
	/*@Test(priority = 3, groups = { "chpmex" }, description = "MAFQABANG-53")
	public void searchByQuery() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Search By Query");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String smartsearchkey = "smartsearchword";
				String smartsearch = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, smartsearchkey,
						extentLogger);

				String smartsearchnoresultskey = "smartsearchnoresults";
				String smartsearchnoresults = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						smartsearchnoresultskey, extentLogger);

				homepage.openHomepage();
				homepage.clickSmartSearchRadioButton();

				// Verify search with no results MAFQABANG-54
				homepage.enterSmartSearch(smartsearchnoresults);
				searchresultpage = homepage.clickSearch();
				boolean noResultsValidated = (searchresultpage == null)
						|| !searchresultpage.searchResultsHeaderContainerDisplayed();
				softas.assertTrue(noResultsValidated, jiraNumber + ":" + issueSummary + " : Search with no results");
				logExtentStatus(extentLogger, noResultsValidated, " Search with no results", smartsearchnoresultskey,
						smartsearchnoresults, jiraNumber);

				// Verify search with results MAFQABANG-53
				homepage.enterSmartSearch(smartsearch);
				searchresultpage = homepage.clickSearch();
				boolean resultsValidated = (searchresultpage == null)
						|| searchresultpage.searchResultsHeaderContainerDisplayed();

				softas.assertTrue(resultsValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, resultsValidated, issueSummary, smartsearchkey, smartsearch, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}*/

	@Test(priority = 3, groups = { "chpmex" }, description = "MAFAUTO-200")
	public void smartSearchAndToolTip() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Natural Language - Development");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String smartsearchkey = "smartsearchword";
				String smartsearch = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, smartsearchkey,
						extentLogger);

				homepage.openHomepage();
				homepage.clickSmartSearchRadioButton();
				boolean istooltiptextdisplayed = homepage.isIntelligentToolTipDisplayed();

				softas.assertTrue(istooltiptextdisplayed,
						jiraNumber + ":" + issueSummary + " : Verified Tooltip message for intelligent search field");
				logExtentStatus(extentLogger, istooltiptextdisplayed,
						" Verified Tooltip message for intelligent search field", jiraNumber);

				homepage.enterSmartSearch(smartsearch);
				boolean istextcleared = homepage.validateCleanButton();

				softas.assertTrue(istextcleared,
						jiraNumber + ":" + issueSummary + " : Verified Clean button for intelligent search field");
				logExtentStatus(extentLogger, istextcleared, " Verified Clean button for intelligent search field",
						jiraNumber);
				homepage.clickThematicsearchLinkHomePage();
				homepage.clickSmartSearchRadioButton();

				homepage.enterSmartSearch(smartsearch);
				searchresultpage = homepage.clickSearch();
				String n = searchresultpage.getDocCountFromResultSet();

				softas.assertTrue(n.contains("100"),
						jiraNumber + ":" + issueSummary + " : Verified smart search for intelligent search field");
				logExtentStatus(extentLogger, n.contains("100"), " Verified smart search for intelligent search field",
						smartsearchkey, smartsearch, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	// MAFQABANG-393 is taking care of testcase MAFAUTO-157
	// disabled as this testcase in no more valid
	@Test(priority = 4, groups = { "chpmex" }, description = "MAFQABANG-393", enabled = false)
	public void FooterInHomepage() throws Exception {
		boolean ValidateFooterText;
		boolean ValidateFooterPrivacylink;
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Footer links are availble in home page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String key = "footertext";
				String footerText = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);

				ValidateFooterText = homepage.ValidateFooterText(footerText);

				softas.assertTrue(ValidateFooterText,
						jiraNumber + ":" + issueSummary + " : Validated  Copyright FooterText");
				logExtentStatus(extentLogger, ValidateFooterText, issueSummary + " : Validated  Copyright FooterText",
						key, footerText, jiraNumber);

				ValidateFooterPrivacylink = homepage.ValidateFooterPrivacyLinks();

				logExtentStatus(extentLogger, ValidateFooterPrivacylink,
						issueSummary + " : Validate Footer Privacy link are present in  homepage", jiraNumber);
				softas.assertTrue(ValidateFooterPrivacylink,
						jiraNumber + ":Validate Footer Privacy link are present in  homepage");
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// disabled as this testcase is no more valid
	/*@Test(priority = 4, groups = { "chpmex" }, description = "MAFAUTO-157", enabled = false)
	public void copyRightFooterInHomepage() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean ValidateFooterText = false;

			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Validate copyright Footer links availability in home page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String key = "footertext";
				String footerText = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);

				ValidateFooterText = homepage.ValidateFooterText(footerText);

				softas.assertTrue(ValidateFooterText, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, ValidateFooterText, issueSummary, key, footerText, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}*/

	// MAFAUTO-178
	// @Test(priority = 15, groups = { "chpmex" }, description = "MAFAUTO-178")
	public void CourseAndSeminar() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Home", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Courses and Seminars - Show Courses & Seminars");

			homepage.openHomepage();
			courseandseminar = homepage.openCourseAndSeminar();
			boolean isimageisplayed = courseandseminar.verifySeminarImage();

			softas.assertTrue(isimageisplayed,
					jiraNumber + ":" + issueSummary + " : Verified Course And Seminar Image ");
			logExtentStatus(extentLogger, isimageisplayed, issueSummary + " : Verified Course And Seminar Image",
					jiraNumber);

			boolean iscoursedescriptiondisplayed = courseandseminar.verifySeminarDescription();
			logExtentStatus(extentLogger, iscoursedescriptiondisplayed,
					issueSummary + " : Verified Course And Seminar description", jiraNumber);

			softas.assertTrue(iscoursedescriptiondisplayed,
					jiraNumber + ":" + issueSummary + " :Verified Course And Seminar description ");

			boolean isseminareventAsexpected = courseandseminar.verifySeminarEventLink();
			logExtentStatus(extentLogger, isseminareventAsexpected, issueSummary + " : Verified  Seminar event link",
					jiraNumber);
			softas.assertTrue(isseminareventAsexpected,
					jiraNumber + ":" + issueSummary + " : Verified  Seminar event link ");

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// MAFAUTO-134
	@Test(priority = 6, groups = { "chpmex" }, description = "MAFAUTO-134")
	public void ScheduledSearchWidget() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		

		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Home Page - Widget - Alerts (Scheduled Search)");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String docexpmonthKey = "widgetname";
				String widgetnamekey = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, docexpmonthKey,
						extentLogger);
				String searchFreeWord = null;
				String key = "availablelinkmsg";
				searchFreeWord = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);

		
				

				homepage = homepage.openHomepage();
				boolean scheduledserachwidgetavailable = homepage.verifyScheduledSearchWidgetDispalyed();
				softas.assertTrue(scheduledserachwidgetavailable,
						jiraNumber + ":" + issueSummary + " : Scheduled search Widget is available in HomePage");
				logExtentStatus(extentLogger, scheduledserachwidgetavailable,
						issueSummary + " : Scheduled search Widget is available in HomePage", jiraNumber);

				boolean isscheduledsearchwidgetexpanded = homepage.isScheduledSearchWidgetExpanded();
				softas.assertTrue(isscheduledsearchwidgetexpanded,
						jiraNumber + ":" + issueSummary + " : Scheduled search Widget is expanded");
				logExtentStatus(extentLogger, isscheduledsearchwidgetexpanded,
						issueSummary + " : Scheduled search Widget is expanded", jiraNumber);

				
			
				 homepage.deleteAlertLatestHomeWidject();
				
				Thread.sleep(4000);
				homepage.clickScheduledSeacrhDeleteYesButton();

			
				// Scheduled Search Widget contains alert.Empty message count be validated
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	// MAFAUTO-158 And MAFAUTO-97
	/*@Test(priority = 7, groups = { "chpmex" }, description = "MAFAUTO-97")
	public void DOFWidget() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Validated Homepage widget DOF");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String docexpmonthKey = "month";
				String docexpmonth = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, docexpmonthKey,
						extentLogger);
				String docexpyearKey = "year";
				String docexpyear = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, docexpyearKey, extentLogger);

				homepage.openHomepage();
				homepage.expandDOFWidgetView();
				searchresultpage = homepage.selectYearAndMonth(docexpmonth, docexpyear);

				boolean doc_displayed = searchresultpage.isTotalDocumentCountPresent();

				softas.assertTrue(doc_displayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, doc_displayed, issueSummary, docexpmonthKey + "," + docexpyearKey,
						docexpmonth + "," + docexpyear, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}*/

	// MAFAUTO-159 && MAFAUTO-156
	// This test case also covers MAFAUTO 109
	@Test(priority = 8, groups = { "chpmex" }, description = "MAFAUTO-159")
	public void LatestDocPublished() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verification of Latest documents published");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String published_widget_leg_contentKey = "legislationspublished";
				String published_widget_leg_content = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						published_widget_leg_contentKey, extentLogger);

				String published_widget_jur_contentsKey = "jurisprudencepublished";
				String published_widget_jur_content = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						published_widget_jur_contentsKey, extentLogger);

				String published_widget_doc_contentKey = "doctrinapublished";
				String published_widget_doc_content = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						published_widget_doc_contentKey, extentLogger);

				homepage.openHomepage();
				homepage.SelectThematicareadropdowns(thematicarea);
				homepage.expandLatestDocumentPublishedWidgetView();
				homepage.clickSeeMoreLinkInDocPublishedWidget();
				int legislationcount = homepage.getLegislationLinksInPublishedWidget(published_widget_leg_content);

				softas.assertTrue((legislationcount > 0 && legislationcount == 8), jiraNumber + ":" + issueSummary
						+ " :Verified latest published legislation documents in the widget ");
				logExtentStatus(extentLogger, legislationcount > 0 && legislationcount == 8,
						"Verified latest published-legislation documents in the widget", jiraNumber);

				int jurisprudencecount = homepage.getJurisprudenceLinksInPublishedWidget(published_widget_jur_content);

				logExtentStatus(extentLogger, jurisprudencecount > 0 && jurisprudencecount == 1,
						"Verified latest published-Jurisprudence documents in the widget", jiraNumber);
				softas.assertTrue((jurisprudencecount > 0 && jurisprudencecount == 1), jiraNumber + ":" + issueSummary
						+ " : Verified latest published Jurisprudence-documents in the widget ");

				int doctrinecount = homepage.getDoctrineLinksInPublishedWidget(published_widget_doc_content);
				logExtentStatus(extentLogger, doctrinecount > 0 && doctrinecount == 4,
						"Verified latest published-Doctrine documents in the widget", jiraNumber);

				softas.assertTrue((doctrinecount > 0 && doctrinecount == 4), jiraNumber + ":" + issueSummary
						+ " : Verified latest published Doctrine-documents in the widget ");

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// MAFAUTO-99
	@Test(priority = 9, groups = { "chpmex" }, description = "MAFAUTO-99")
	public void SearchByAbbrevation() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Home Page - Widget - One Click - Search by Abbrevation");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String abbrevationKey = "abbrevation";
				String abbrevation = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, abbrevationKey,
						extentLogger);
				String articlenumberKey = "articlenumber";
				String articlenumber = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, articlenumberKey,
						extentLogger);

				homepage.openHomepage();
				homepage.expandSearchByAbbrevation();
				homepage.enterAbbrevationField(abbrevation);
				homepage.enterAbbrevationArticle(articlenumber);
				boolean flag = homepage.validateCleanButtonInAbbrevationWidget();

				softas.assertTrue(flag,
						jiraNumber + ":" + issueSummary + " :Verified  Clean Button in Search By Abbrevation widget ");
				logExtentStatus(extentLogger, flag,
						issueSummary + " : Verified  Clean Button in Search By Abbrevation widget", jiraNumber);
				Thread.sleep(5000);
				homepage.enterAbbrevationField(abbrevation);
				homepage.enterAbbrevationArticle(articlenumber);
				docdisplaypage = homepage.clickSearchInAbbrevationWidget();
				if(docdisplaypage.isResultsFormPageDisplayed()) {
					homepage.enterAbbrevationField(abbrevation);
					homepage.enterAbbrevationArticle(articlenumber);
					docdisplaypage = homepage.clickSearchInAbbrevationWidget();
				}
				boolean defaultpositiondisplayed = docdisplaypage.verifyDefaultPositioningArticleEntered();
				

				softas.assertTrue(defaultpositiondisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, defaultpositiondisplayed, issueSummary,
						abbrevationKey + "," + articlenumberKey, abbrevation + "," + articlenumber, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 10, groups = { "chpbr" }, description = "MAFQABANG-74")
	public void ThematicDropdownContent() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Application Verification on Thematic Area");

			homepage.openHomepage();
			boolean flag = homepage.verifySubjectAreaDrop();

			softas.assertTrue(flag, jiraNumber + issueSummary);
			logExtentStatus(extentLogger, flag, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority = 11, groups = { "chpbr" }, description = "MAFAUTO-75")
	public void AvailabilityOfVariousSearchs() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Application verification on availability of various search");

			homepage.openHomepage();
			boolean isvarioussearches_available = homepage.VerifyFreeSearchAvailable()
					&& homepage.verifySearchByTermAvaialble() && homepage.verifyNaturalLanguageSearchAvailable();

			softas.assertTrue(isvarioussearches_available, jiraNumber + ":" + issueSummary
					+ " : Verified availability of Various searches  in the home page ");
			logExtentStatus(extentLogger, isvarioussearches_available,
					issueSummary + " : Verified availability of Various searches  in the home page", jiraNumber);
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	/*// MAFAUTO-132
	 @Test(priority = 12, groups = { "chpmex" }, description = "MAFAUTO-132")
	public void MyShortcutWidget() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Home", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Home Page - Widget - My Shortcuts");
		
		

		try {

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String linktext = "noavailablelinksmsg";
				String Linktextname = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, linktext,
						extentLogger);
			      String widgetname = "widgetname";
			      String widgetnamelink = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, widgetname,
							extentLogger);
			      
			homepage.openHomepage();
			homepage.isMyShortcutWidgetAvailable();
			homepage.expandMyShortcut();
			if (homepage.isBodyofAdvanceSearchDisplayed()) {
				homepage.collapseAdvanceSearchWidget();
			}

			
			
			boolean myShortcutText = homepage.isMyShortcutTextAvailable();
			if (!myShortcutText) {
				while (homepage.isShortcutLinkAvailable()) {
					homepage.clickRemoverinShortcutWidget();
				}
			}

			myShortcutText = homepage.isMyShortcutTextAvailable();
	
			if (myShortcutText) {

				homepage.clicksearchPageTab();
				boolean shortcutCreated = false;
				boolean createShortCutAvailable = homepage.isContentTreeAvailable()
						&& homepage.isCreateShortcutAvailable();
				if (createShortCutAvailable) {
					homepage.clickCreateShortcutLink();
					homepage.isCreateShortcutPopupAvailable();
					homepage.writeNameinShortcutpopup();
					homepage.clickSaveButton();
					shortcutCreated = homepage.isSuccessMessageDisplayed();
					homepage.clickAcceptButton();
				}
				homepage.openHomepage();

				shortcutCreated = shortcutCreated && homepage.isMyShortcutLinkDisplayed();
				softas.assertTrue(shortcutCreated,
						jiraNumber + ":" + issueSummary + " : Added shortcut & validated link in My Shortcut Widget ");
				logExtentStatus(extentLogger, shortcutCreated,
						issueSummary + " : Added shortcut & validated link in My Shortcut Widget", jiraNumber);

				homepage.clickRemoverinShortcutWidget();
				
			}

			}
		}
		catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}*/

	// MAFAUTO-95
	// @Test(priority = 16, groups = { "chpmex" }, description = "MAFAUTO-95")
	public void LatestPublishedDocuments() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Home", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Home Page - Widget - Highlights- published Documents");

			homepage.openHomepage();
			if (homepage.isBodyofAdvanceSearchDisplayed()) {
				homepage.collapseAdvanceSearchWidget();
			}

			if (homepage.isBodyofShortcutDisplayed()) {
				homepage.collapseMyShortcutWidget();
			}

			homepage.isLatestPublishedWidgetAvailable();
			homepage.clickSeeMoreLinkInDocPublishedWidget();

			boolean isListings_Available = homepage.isLegislationinLatestpublishedAvailable()
					&& homepage.isJurisprudenceinLatestpublishedAvailable()
					&& homepage.isDoctrineinLatestpublishedAvailable();
			softas.assertTrue(isListings_Available, jiraNumber + ":" + issueSummary
					+ " : Verified availability of Listings in Latest published Documents ");
			logExtentStatus(extentLogger, isListings_Available,
					" : Verified availability of Listings in Latest published Documents", jiraNumber);

			homepage.clickShowLessLatestPublishedDocument();
			docdisplaypage = homepage.clickOnLinkinLatestPublished();
			docdisplaypage.isDocumentTitlePresent();
			boolean isDocumentPage_Available = docdisplaypage.isDocumentContentDisplayed();
			softas.assertTrue(isDocumentPage_Available, jiraNumber + ":" + issueSummary
					+ " : Verified availability of document display page from Latest Published document ");
			logExtentStatus(extentLogger, isDocumentPage_Available,
					issueSummary + " : Verified availability of document display page from Latest Published document",
					jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// MAFAUTO-140
	@Test(priority = 14, groups = { "chpmex" }, description = "MAFAUTO-140")
	public void FrequentAskedQuestionsWidget() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Home Page - Widget - Usual Laws (Frequently Asked Questions)");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String key = "thematicdropdownvalues";
				JSONArray listOfData = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, key, extentLogger);
				homepage.openHomepage();
				boolean isfaqcontentdisplayed = false;
				for (int row = 0; row < listOfData.size(); row++) {

					String thematicArea = listOfData.get(row).toString();
					// homepage.SelectThematicareadropdowns(thematicArea);
					boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicArea);
					softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, key, thematicArea, jiraNumber);
					Thread.sleep(1000);
					homepage.expandFAQWidget();
					Thread.sleep(6000);
					isfaqcontentdisplayed = homepage.verifyFAQContent(thematicArea);
					softas.assertTrue(isfaqcontentdisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, isfaqcontentdisplayed, issueSummary, key, thematicArea, jiraNumber);
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

	// MAFAUTO-227
	@Test(priority = 0, groups = { "chpmex" }, description = "MAFAUTO-227")
	public void RemovedThematicAreaOptions() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Home Page - Removed Thematic Area");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				homepage.openHomepage();

				String key = "removedthematicoptions";
				JSONArray listOfData = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, key, extentLogger);
				boolean optionNotPresent = false;
				for (int row = 0; row < listOfData.size(); row++) {
					String removedOption = listOfData.get(row).toString();
					optionNotPresent = !(homepage.thematicAreaDropdownContainsValue(removedOption));
					softas.assertTrue(optionNotPresent, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, optionNotPresent, issueSummary, key, removedOption, jiraNumber);
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

	// methods added for Paraguay & Uruguay Test Cases shared by Pablo
	@Test(priority = 15, groups = { "chpury" }, description = "MAFQABANG-475")
	public void ApplicationVerification() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean isapplciationverified = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Application verification post successful Login");
			homepage.clickOnProductLogo();
			boolean islogodisplayed = homepage.isProductLogoDisplayed() || homepage.isClientLogoDisplayed();
			softas.assertTrue(islogodisplayed, jiraNumber + ":" + "Application Logo verified");
			logExtentStatus(extentLogger, islogodisplayed, "Application Logo verified", jiraNumber);

			boolean isloggedindatedisplayed = homepage.isLoggedinDateDispalyed();
			softas.assertTrue(isloggedindatedisplayed, jiraNumber + ":" + "User logged in date verified");
			logExtentStatus(extentLogger, isloggedindatedisplayed, "User logged in date verified", jiraNumber);

			boolean isalllinksdispalyed = homepage.isAllLinksDisplayedInHeader();
			softas.assertTrue(isalllinksdispalyed, jiraNumber + ":" + "Header links verified");
			logExtentStatus(extentLogger, isalllinksdispalyed, "Header links verified", jiraNumber);

			boolean exptabsdispalyed = homepage.isAllTabsDisplayed();
			softas.assertTrue(exptabsdispalyed, jiraNumber + ":" + "Tabs in Application verified");
			logExtentStatus(extentLogger, exptabsdispalyed, "Tabs in Application verified", jiraNumber);

			boolean quickSearchSectionDisplayed = homepage.isQuickSearchSectionDisplayed();
			softas.assertTrue(quickSearchSectionDisplayed, jiraNumber + ":" + "Quick Search Section Displayed");
			logExtentStatus(extentLogger, quickSearchSectionDisplayed, "Quick Search Section Displayed", jiraNumber);

			boolean expwidgetsdispalyed = homepage.isExpectedWidgetsDisplayedInHomePage();
			softas.assertTrue(expwidgetsdispalyed, jiraNumber + ":" + "Expected widgets in HomePage verified");
			logExtentStatus(extentLogger, expwidgetsdispalyed, "Expected widgets in HomePage verified", jiraNumber);

			boolean isthematicoptionsdispalyed = homepage.isThematicAreaDropdownOptionsAvailable();
			softas.assertTrue(isthematicoptionsdispalyed, jiraNumber + ":" + "Thematic dropdown options verified");
			logExtentStatus(extentLogger, isthematicoptionsdispalyed, "Thematic dropdown options verified", jiraNumber);

			boolean isfooterdispalyed = homepage.isFooterAvailableinHomePage()
					&& homepage.isTermsLinkDisplayedInFooter();

			softas.assertTrue(isfooterdispalyed, jiraNumber + ":" + "Footer with terms link available in HomePage");
			logExtentStatus(extentLogger, isfooterdispalyed, "Footer with terms link available in HomePage",
					jiraNumber);

			isapplciationverified = islogodisplayed && isloggedindatedisplayed && isalllinksdispalyed
					&& isfooterdispalyed && isthematicoptionsdispalyed && expwidgetsdispalyed && exptabsdispalyed
					&& quickSearchSectionDisplayed;
			softas.assertTrue(isapplciationverified, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, isapplciationverified, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 16, groups = { "chpury" }, description = "MAFQABANG-482")
	public void WidgetsforTodasThematicArea() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean iswidgetverified = false;

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Homepage widget verification post Todas Thematic dropdown selected");

			String key = "thematic area value";
			String thematic_area_value = "Todas";
			homepage.clickOnProductLogo();
			homepage.selectGivenValueFromThematicDropdown(thematic_area_value);

			iswidgetverified = homepage.isExpectedWidgetsDisplayedInHomePage();
			softas.assertTrue(iswidgetverified, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, iswidgetverified, issueSummary, key, thematic_area_value, jiraNumber);
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 17, groups = { "chpury" }, description = "MAFQABANG-483")
	public void WidgetsforFiscalThematicArea() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean iswidgetverified = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Homepage widget verification post Fiscal Thematic dropdown selected");
			String key = "thematic area value";
			String thematic_area_value = "Fiscal";
			homepage.clickOnProductLogo();
			homepage.selectGivenValueFromThematicDropdown(thematic_area_value);

			iswidgetverified = homepage.isExpectedWidgetsDisplayedInHomePage();
			softas.assertTrue(iswidgetverified, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, iswidgetverified, issueSummary, key, thematic_area_value, jiraNumber);
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	

	@Test(priority = 19, groups = { "chpury" }, description = "MAFQABANG-486")
	public void textColorChangePostTabSelection() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean iscolorverified = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Tab Text color change verification when particular tab is selected");

			homepage.clickOnProductLogo();
			iscolorverified = homepage.isCurrentTabEqualsHome() && homepage.isCurrentTabColorEqualsOrange();
			softas.assertTrue(iscolorverified, jiraNumber + ":" + issueSummary + ":" + "HomeTab color Verified");
			logExtentStatus(extentLogger, iscolorverified, issueSummary, "Tab Selected", "Home Tab", jiraNumber);

			searchpage = homepage.OpenSearchPage();
			iscolorverified = searchpage.isCurrentTabEqualsSearch() && searchpage.isCurrentTabColorEqualsOrange();
			softas.assertTrue(iscolorverified, jiraNumber + ":" + issueSummary + ":" + "SearchTab color Verified");
			logExtentStatus(extentLogger, iscolorverified, issueSummary, "Tab Selected", "Search Tab", jiraNumber);

			newspage = searchpage.ClickonNewsTab();
			iscolorverified = newspage.isCurrentTabEqualsNews() && newspage.isCurrentTabColorEqualsOrange();

			softas.assertTrue(iscolorverified, jiraNumber + ":" + issueSummary + ":" + "NewsTab color Verified");
			logExtentStatus(extentLogger, iscolorverified, issueSummary, "Tab Selected", "News Tab", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * MAFQABANG-565 verify Links inside Advance Search Widget in HomePage based on
	 * Thematic Area
	 */
	@Test(priority = 29, groups = { "chpury" }, description = "MAFQABANG-565")
	public void AdvanceSearchWidgetForAllThematicAreas() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify Expected search Links available in Advance Search Widget");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "thematic_area_value";
				String thematicareavalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);
				String explinksKey = "expectedlinks";
				JSONArray explinksArray = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, explinksKey,
						extentLogger);

				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicareavalue);
				boolean isadvsearchwidgetmaximized = homepage.isAdvanceSearchWidgetMaximized();
				if (!isadvsearchwidgetmaximized) {
					homepage.maximizeAdvanceSearchWidget();
				}

				boolean linkverified = false;
				for (int row = 0; row < explinksArray.size(); row++) {
					linkverified = homepage.advSearchLinkDisplayed(explinksArray.get(row).toString());
					if (!linkverified)
						break;
				}

				softas.assertTrue(linkverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linkverified, issueSummary, key, thematicareavalue, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 20, groups = { "chpury" }, description = "MAFQABANG-487")
	public void SavedSearchWidgetZeroEntryMessage() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Scheduled Search widget zero record message verification");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String key = "zeroentry_message";
				String message = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);
				homepage.clickOnProductLogo();
				Thread.sleep(4000);
				if (!homepage.isScheduledSearchWidgetMaximized()) {
					homepage.maximizeScheduledSearchWidget();
					Thread.sleep(2000);
				}

				boolean ismsgverified = false;
				if (homepage.isNoItemsPresentInSavedSearchWidget()) {
					Thread.sleep(2000);
					ismsgverified = !homepage.isZeroSavedSearchMessageDisplayedInWidget(message);
					softas.assertTrue(ismsgverified, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, ismsgverified, issueSummary, key, message, jiraNumber);
				} else {
					logExtentNoResultsAsInfo(extentLogger, issueSummary, "Saved Search Widget",
							"Items present in the widget", jiraNumber);
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

	@Test(priority = 21, groups = { "chpury" }, description = "MAFQABANG-492")
	public void SavedSearchWidgetClickHereUrlVerification() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Redirected url verification when Click Here for Scheduled Search widget is clicked");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "expected_url";
				String expectedurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);

				homepage.clickOnProductLogo();
				boolean isscheduledsearchwidgetmaximized = homepage.isScheduledSearchWidgetMaximized();
				if (!isscheduledsearchwidgetmaximized) {
					homepage.maximizeScheduledSearchWidget();
				}

				boolean urlverified = false;
				homepage.closeAllChildTabs();

				// deleting all the existing alerts to display the no entry message

				alertpage = homepage.ClickonAlertLink();
				alertpage.deleteAllAlerts();
				Thread.sleep(3000);

				// returning back to homepage
				homepage = alertpage.clickHomeTab();
				homepage.clickOnScheduledSearchWidgetClickHere();
				urlverified = homepage.isRedirectedUrlEquals(expectedurl);

				softas.assertTrue(urlverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, urlverified, issueSummary, key, expectedurl, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 22, groups = { "chpury" }, description = "MAFQABANG-495")
	public void FollowDocumentWidgetZeroEntryMessage() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean ismsgverified = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Follow Document widget zero record message verification");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicAreaKey = "thematicarea";
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicAreaKey,
						extentLogger);
				String messageKey = "zeroentry_message";
				String message = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, messageKey, extentLogger);
				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicArea);
				if (!homepage.isFollowDocumentWidgetMaximized()) {
					homepage.maximizeFollowDocumentWidget();
				}

				if (homepage.isNoItemsPresentInFollowDocumentWidget()) {
					ismsgverified = homepage.isZeroDocumentsTrackedMessageDisplayedInWidget(message);
					softas.assertTrue(ismsgverified, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, ismsgverified, issueSummary, messageKey, message, jiraNumber);
				} else {
					logExtentNoResultsAsInfo(extentLogger, issueSummary, "Follow Document Widget",
							"Items present in the widget", jiraNumber);
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

	@Test(priority = 23, groups = { "chpury" }, description = "MAFQABANG-496")
	public void FollowDocumentWidgetClickHereUrlVerification() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean urlverified = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Redirected url verification when Click Here for Follow document widget is clicked");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String expectedurlKey = "expected_url";
				String expectedurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expectedurlKey,
						extentLogger);
				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicarea);

				// deleting the documents followed to get the no entry message
//				alertpage = homepage.ClickonAlertLink();
//				alertpage.deleteAllAlerts();
//				homepage = alertpage.clickHomeTab();

				boolean isfollowdocwidgetmaximized = homepage.isFollowDocumentWidgetMaximized();
				if (!isfollowdocwidgetmaximized) {
					homepage.maximizeFollowDocumentWidget();
				}
				homepage.closeAllChildTabs();

				homepage.clickOnFollowDocumentWidgetClickHere();
				urlverified = homepage.isRedirectedUrlEquals(expectedurl);

				softas.assertTrue(urlverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, urlverified, issueSummary, expectedurlKey, expectedurl, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}


	@Test(priority = 27, groups = { "chpury" }, description = "MAFQABANG-522")
	public void validateFeaturedArticlesWidget() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean linksverified = false;

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Featured Articles widget links verification");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicareavalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String linksKeyKey = "expectedlinks";
				String linksString = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, linksKeyKey, extentLogger);
				String expectedLinks[] = linksString.split(",");
				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicareavalue);
				if (!homepage.isFeaturedArticlesWidgetMaximized()) {
					homepage.maximizeFeaturedArticlesWidget();
				}

				homepage.clickonFeaturedArtSeeMoreLink();
				linksverified = homepage.isLinksPresentinDestacadosWidget(expectedLinks);

				softas.assertTrue(linksverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linksverified, issueSummary, "Links Verified ", linksString, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 28, groups = { "chpury" }, description = "MAFQABANG-529")
	public void FeaturedArticlesWidgetClikonSpecificLink() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean linkpresence = false;
			boolean linkverified = false;

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Specific link to be clicked inside Featured Articles widget & validate document");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicareaKey = "thematicarea";
				String thematicareavalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String linkKey = "expectedlink";
				String link = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, linkKey, extentLogger);

				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicareavalue);
				if (!homepage.isFeaturedArticlesWidgetMaximized()) {
					homepage.maximizeFeaturedArticlesWidget();
				}

				homepage.clickonFeaturedArtSeeMoreLink();
				linkpresence = homepage.isLinkPresentInFeaturedArticles(link);
				if (linkpresence) {
					linkverified = false;
					docdisplaypage = homepage.clickLinkInFeaturedArticles(link);
					if (docdisplaypage != null) {
						docdisplaypage.switchToChildTab(1);

						linkverified = docdisplaypage.isInformationClaveTabDisplayed()
								&& docdisplaypage.isTreeOfContentTabDisplayed()
								&& docdisplaypage.isVocesDisplayedForFeaturedArticle()
								&& docdisplaypage.isMainTextDisplayedForFeaturedArticle();
						docdisplaypage.closeAllChildTabs();
					}
				}

				softas.assertTrue(linkverified && linkpresence, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linkverified && linkpresence, issueSummary, linkKey, link, jiraNumber);
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
	 * MAFQABANG-577 Validate Widgets in HomePage based on Selected Thematic Area
	 */
	@Test(priority = 29, groups = { "chpury" }, description = "MAFQABANG-577")
	public void WidgetsForSelectedThematicArea() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Validate Widgets in HomePage based on Selected Thematic Area");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedwidgetsKey = "expectedwidgets";
				JSONArray expectedwidgets = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedwidgetsKey,
						extentLogger);

				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicarea);

				boolean widgetValidated = false;
				String expectedWidgets[] = new String[expectedwidgets.size()];
				for (int row = 0; row < expectedwidgets.size(); row++) {
					expectedWidgets[row] = expectedwidgets.get(row).toString();
				}
				widgetValidated = homepage.isDisplayedWidgetsEquals(expectedWidgets);
				softas.assertTrue(widgetValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, widgetValidated, issueSummary, thematicareaKey, thematicarea, jiraNumber);
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
	 * MAFQABANG-570 verify Links inside Advance Search Widget in HomePage based on
	 * Thematic Area
	 */
	@Test(priority = 29, groups = { "chpury" }, description = "MAFQABANG-570")
	public void AllLinksAvailableUnderFrequentlyAskedQuestions() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Validated All Links available in Frequently Asked Questions Widget");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedlinksKey = "expectedlinks";
				JSONArray expectedlinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedlinksKey,
						extentLogger);
				homepage.clickOnProductLogo();
				Thread.sleep(6000);
				homepage.SelectThematicareadropdowns(thematicarea);
				boolean isFAQWidgetDisplayed = homepage.isFAQWidgetDisplayed();
				softas.assertTrue(isFAQWidgetDisplayed, jiraNumber + ":" + "Validated The display of FAQ Widget ");
				logExtentStatus(extentLogger, isFAQWidgetDisplayed, "Validated The display of FAQ Widget ", jiraNumber);

				boolean isfreqaskedqueswidgetmaximized = isFAQWidgetDisplayed
						&& homepage.isFreqAskedQuestionsWidgetMaximized();
				if (!isfreqaskedqueswidgetmaximized) {
					homepage.maximizeFreqAskedQuestionsWidget();
				}

				homepage.clickSeeMoreLinkInFreqAskQuesWidget();

				boolean allContentTreeValidated = true;

				for (int row = 0; row < expectedlinks.size(); row++) {
					homepage.clickOnProductLogo();
					Thread.sleep(3000);
					homepage.clickSeeMoreLinkInFreqAskQuesWidget();
					boolean linkverified = homepage.FreqAskQuesLinkDisplayed(expectedlinks.get(row).toString());
					if (linkverified)
						searchpage = homepage.ClickFreqAskQuesLink(expectedlinks.get(row).toString());
					boolean contenttreevalidated = linkverified && (searchpage != null)
							&& searchpage.isLastLinkDisplayedInContentTreeEquals(expectedlinks.get(row).toString());
					softas.assertTrue(contenttreevalidated, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, contenttreevalidated, "Validate clicking link in FAQ Widget ", "Link",
							expectedlinks.get(row).toString(), jiraNumber);

					allContentTreeValidated &= contenttreevalidated;
				}
				softas.assertTrue(allContentTreeValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, allContentTreeValidated, issueSummary, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}


	@Test(priority = 33, groups = { "chpury" }, description = "MAFQABANG-562")
	public void GoToChkWorldRedirectedUrlVerification() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean urlverified = false;

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Redirected url verification when Go to Checkpoint World is clicked");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "expected_url";
				String expectedurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);
				homepage.clickOnProductLogo();
				homepage.closeAllChildTabs();
				homepage.ClickonGoToCheckpointWorld();
				urlverified = homepage.isRedirectedUrlEquals(expectedurl);

				softas.assertTrue(urlverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, urlverified, issueSummary, key, expectedurl, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 34, groups = { "chpury" }, description = "MAFQABANG-563")
	public void GoToLawOnlineRedirectedUrlVerification() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean urlverified = false;

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Redirected url verification when Go to Law Online is clicked");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "expected_url";
				String expectedurl = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);
				homepage.clickOnProductLogo();
				homepage.closeAllChildTabs();
				homepage.ClickonGoToLawOnline();
				urlverified = homepage.isRedirectedUrlEquals(expectedurl);

				softas.assertTrue(urlverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, urlverified, issueSummary, key, expectedurl, jiraNumber);
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
	 * MAFQABANG-568 CHPURY_Home : verify tools Widget contents in Home
	 */
	@Test(priority = 35, groups = { "chpury" }, description = "MAFQABANG-568")
	public void AvailableToolsInHomeWidget() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verify Expected tools Links available in Tools Widget");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String expectedtoolslinksKey = "expectedtoolslinks";
				JSONArray expectedtoolslinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild,
						expectedtoolslinksKey, extentLogger);
				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicarea);
				boolean toolsWidgetValidated = homepage.isToolsHomeWidgetDisplayed();
				softas.assertTrue(toolsWidgetValidated, jiraNumber + ":" + "Tools Widget Displayed on Home Page");
				logExtentStatus(extentLogger, toolsWidgetValidated, "Tools Widget Displayed on Home Page", jiraNumber);

				boolean widgetExpanded = toolsWidgetValidated && homepage.isToolsHomeWidgetMaximized();
				if (!widgetExpanded) {
					homepage.maximizeToolsHomeWidget();
				}

				boolean toolDisplayed = false;
				int row = 0;
				while (row < expectedtoolslinks.size()) {
					toolDisplayed = homepage.isToolPresentInToolsHomeWidget(expectedtoolslinks.get(row++).toString());
					softas.assertTrue(toolDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, toolDisplayed, issueSummary, "Tool verified in widget ",
							expectedtoolslinks.get(row - 1).toString(), jiraNumber);
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

	// This method validate the footer Text once footer link clicked on Homepage
	@Test(priority = 25, groups = { "chpury" }, description = "MAFQABANG-574")
	public void ValidateFooterTextAndTermsPage() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean ValidateFooterLink = false;
			boolean ValidateFooterText = false;

			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Terms & conditions (Footer link) validated in Home Page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "footer_text";
				String footerText = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, key, extentLogger);

				ValidateFooterText = homepage.ValidateFooterText(footerText);
				homepage.clickFooterLink();

				softas.assertTrue(ValidateFooterText, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, ValidateFooterText, issueSummary, jiraNumber);

				ValidateFooterLink = homepage.isPrivacyPolicyAvailable();

				logExtentStatus(extentLogger, ValidateFooterLink, issueSummary, jiraNumber);
				softas.assertTrue(ValidateFooterLink, jiraNumber + issueSummary);
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
	 * MAFQABANG-573 Drag & Drop Porlets and validate the new position is saved on
	 * re-login
	 */
	@Test(priority = 36, groups = { "chpury" }, description = "MAFQABANG-573")
	public void DragAndDropFirstWidgetToLastPosition() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Drag & Drop Porlets and validate the new position is saved");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicarea);
				homepage.minimizeAllWidgetsInHome();
				boolean allWidgetCollapsed = homepage.isAllWidgetsMinimizedInHome();

				softas.assertTrue(allWidgetCollapsed, jiraNumber + ":" + "All widgets minimized on Home Page");
				logExtentStatus(extentLogger, allWidgetCollapsed, "All widgets minimized on Home Page", jiraNumber);

				String widgetToMove = homepage.getWidgetNameAtPosition(1);
				homepage.dragAndDropFirstWidgetToLastPosition();
				int droppedPosition = homepage.getWidgetPosition(widgetToMove);

				boolean widgetMoved = droppedPosition >= 1;
				softas.assertTrue(widgetMoved, jiraNumber + ":" + "Dragged & dropped first widget to new position");
				logExtentStatus(extentLogger, widgetMoved, "Dragged & dropped first widget to new position", "Widget ",
						widgetToMove, jiraNumber);

				boolean positionSaved = false;
				if (widgetMoved) {
					// Logout and re-login
					SignOffPage signOff = homepage.getSignOffPage();
					signOff.clikNewSession();
					Thread.sleep(4000);
					HomePage homepageOnRelogin = loginpage.Login(username, password);
					if (homepageOnRelogin != null)
						homepage = homepageOnRelogin;
					homepage.clickOnProductLogo();
					homepage.SelectThematicareadropdowns(thematicarea);
					positionSaved = (droppedPosition == homepage.getWidgetPosition(widgetToMove));
					softas.assertTrue(positionSaved, jiraNumber + ":" + "Validate new position saved after re-login");
					logExtentStatus(extentLogger, positionSaved, "Validate new position saved after re-login",
							"Widget ", widgetToMove, jiraNumber);
				}

				softas.assertTrue(widgetMoved && positionSaved, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, widgetMoved && positionSaved, issueSummary, jiraNumber);

				// Move the widget back to the first position
				homepage.dragAndDropWidgetToFirstPosition(widgetToMove);
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
	 * MAFQABANG-576 Expand or Collapse widgets and verify the changes are saved
	 * after re-login
	 */
	@Test(priority = 35, groups = { "chpury" }, description = "MAFQABANG-576")
	public void RememberWidgetExpandedOrCollapsed() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			// String issueSummary = getIssueTitle(jiraNumber,
			// "Expand and Collapse widgets and verify the changes are saved
			// after re-login");

			String thematicArea = "Todas";

			homepage.clickOnProductLogo();
			homepage.SelectThematicareadropdowns(thematicArea);
			// if some un-collapsed widgets exist, then follow this flow.
			// Otherwise go to else part
			if (!homepage.isAllWidgetsMinimizedInHome()) {
				// Verify for Minimize widget -START
				homepage.minimizeAllWidgetsInHome();
				boolean allWidgetCollapsed = homepage.isAllWidgetsMinimizedInHome();
				softas.assertTrue(allWidgetCollapsed, jiraNumber + ":" + "Widgets minimized on Home Page");
				logExtentStatus(extentLogger, allWidgetCollapsed, "Widgets minimized on Home Page", jiraNumber);
				// Logout and re-login
				SignOffPage signOff = homepage.getSignOffPage();
				signOff.clikNewSession();
				Thread.sleep(4000);
				HomePage homepageOnRelogin = loginpage.Login(username, password);
				if (homepageOnRelogin != null)
					homepage = homepageOnRelogin;
				// Verify widgets are minimized on relogin
				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicArea);
				allWidgetCollapsed = homepageOnRelogin != null && homepage.isAllWidgetsMinimizedInHome();
				softas.assertTrue(allWidgetCollapsed, jiraNumber + ":" + "validated minimized widgets after re-login");
				logExtentStatus(extentLogger, allWidgetCollapsed, "validated minimized widgets after re-login",
						jiraNumber);
				// Verify for Minimize widget -END

				// Verify for Maximize widget -START
				boolean allWidgetNotExpanded = !homepage.isAllWidgetsMaximizedInHome();
				homepage.maximizeAllWidgetsInHome();
				boolean allWidgetExpanded = homepage.isAllWidgetsMaximizedInHome();
				softas.assertTrue(allWidgetExpanded && allWidgetNotExpanded,
						jiraNumber + ":" + "Widgets maximized on Home Page");
				logExtentStatus(extentLogger, allWidgetExpanded && allWidgetNotExpanded,
						"Widgets maximized on Home Page", jiraNumber);
				// Logout and re-login
				signOff = homepage.getSignOffPage();
				signOff.clikNewSession();
				Thread.sleep(4000);
				homepageOnRelogin = loginpage.Login(username, password);
				if (homepageOnRelogin != null)
					homepage = homepageOnRelogin;
				// Verify widgets are minimized on relogin
				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicArea);
				allWidgetExpanded = homepageOnRelogin != null && homepage.isAllWidgetsMaximizedInHome();
				softas.assertTrue(allWidgetExpanded && allWidgetNotExpanded,
						jiraNumber + ":" + "validated maximized widgets after re-login");
				logExtentStatus(extentLogger, allWidgetExpanded && allWidgetNotExpanded,
						"validated maximized widgets after re-login", jiraNumber);
				// Verify for Maximize widget -END

			} else {
				// Verify for Maximize widget -START
				boolean allWidgetNotExpanded = !homepage.isAllWidgetsMaximizedInHome();
				homepage.maximizeAllWidgetsInHome();
				boolean allWidgetExpanded = homepage.isAllWidgetsMaximizedInHome();
				softas.assertTrue(allWidgetExpanded && allWidgetNotExpanded,
						jiraNumber + ":" + "Widgets maximized on Home Page");
				logExtentStatus(extentLogger, allWidgetExpanded && allWidgetNotExpanded,
						"Widgets maximized on Home Page", jiraNumber);
				// Logout and re-login
				SignOffPage signOff = homepage.getSignOffPage();
				signOff.clikNewSession();
				Thread.sleep(4000);
				HomePage homepageOnRelogin = loginpage.Login(username, password);
				if (homepageOnRelogin != null)
					homepage = homepageOnRelogin;
				// Verify widgets are minimized on relogin
				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicArea);
				allWidgetExpanded = homepageOnRelogin != null && homepage.isAllWidgetsMaximizedInHome();
				softas.assertTrue(allWidgetExpanded && allWidgetNotExpanded,
						jiraNumber + ":" + "validated maximized widgets after re-login");
				logExtentStatus(extentLogger, allWidgetExpanded && allWidgetNotExpanded,
						"validated maximized widgets after re-login", jiraNumber);
				// Verify for Maximize widget -END

				// Verify for Minimize widget -START
				homepage.minimizeAllWidgetsInHome();
				boolean allWidgetCollapsed = homepage.isAllWidgetsMinimizedInHome();
				softas.assertTrue(allWidgetCollapsed, jiraNumber + ":" + "Widgets minimized on Home Page");
				logExtentStatus(extentLogger, allWidgetCollapsed, "Widgets minimized on Home Page", jiraNumber);
				// Logout and re-login
				signOff = homepage.getSignOffPage();
				signOff.clikNewSession();
				Thread.sleep(4000);
				homepageOnRelogin = loginpage.Login(username, password);
				if (homepageOnRelogin != null)
					homepage = homepageOnRelogin;
				// Verify widgets are minimized on relogin
				homepage.clickOnProductLogo();
				homepage.SelectThematicareadropdowns(thematicArea);
				allWidgetCollapsed = homepageOnRelogin != null && homepage.isAllWidgetsMinimizedInHome();
				softas.assertTrue(allWidgetCollapsed, jiraNumber + ":" + "validated minimized widgets after re-login");
				logExtentStatus(extentLogger, allWidgetCollapsed, "validated minimized widgets after re-login",
						jiraNumber);
				// Verify for Minimize widget -END

			}
			// To make widgets visible for other tests
			homepage.maximizeAllWidgetsInHome();
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * MAFQABANG-137 Validate Widgets in HomePage based on Selected Thematic Area
	 * for checkpoint Mexico
	 * 
	 */
	/*@Test(priority = 37, groups = { "chpmex" }, description = "MAFAUTO-137")
	public void WidgetsAvailableForSelectedThematicArea() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Home Page - View by Thematic Area");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedwidgetsKey = "expectedwidgets";
				JSONArray expectedwidgets = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedwidgetsKey,
						extentLogger);

				homepage.clickOnProductLogo();
				// homepage.SelectThematicareadropdowns(thematicarea);
				boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
						jiraNumber);
				boolean widgetValidated = false;
				String expectedWidgets[] = new String[expectedwidgets.size()];
				for (int row = 0; row < expectedwidgets.size(); row++) {
					expectedWidgets[row] = expectedwidgets.get(row).toString();
				}
				widgetValidated = homepage.isDisplayedWidgetsEquals(expectedWidgets);
				softas.assertTrue(widgetValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, widgetValidated, issueSummary, thematicareaKey, thematicarea, jiraNumber);
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
	 * MAFAUTO-138 Verify if the search links from Advanced search widget on Home
	 * page are working
	 */
	@Test(groups = { "chpmex" }, description = "MAFAUTO-138")
	public void searchLinksFromAdavnceSearchWidget() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify if the search links from Advanced search widget on Home page are working");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String linktoclickKey = "linktoclick";
				String linktoclickVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, linktoclickKey,
						extentLogger);

				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				homepage = homepage.openHomepage();
				homepage.expandTheGivenWidget("Búsqueda avanzada");

				switch (linktoclickVal) {
				case "Legislación":
					Thread.sleep(4000);
					LegislationPage legislationPage = homepage.OpenLegislationPage();
					
					legislationPage.enterFreeWordOnSearchPage(freewordVal);
					Thread.sleep(4000);
					searchresultpage = legislationPage.clickOnSearch();
					break;
				case "Doctrina":
					Thread.sleep(4000);
					DoctrinePage doctrinePage = homepage.OpenDoctrinaPage();
		
					doctrinePage.enterFreeWordOnSearchPage(freewordVal);
					Thread.sleep(4000);
					searchresultpage = doctrinePage.clickOnSearch();
					break;
				case "Jurisprudencia":
					Thread.sleep(4000);
					JurisprudencePage jurisprudencePage = homepage.openJurisprudencePage();
			
					jurisprudencePage.enterFreeWordOnSearchPage(freewordVal);
					Thread.sleep(4000);
					searchresultpage = jurisprudencePage.clickOnSearch();
					break;
					
				case "Formularios":
					Thread.sleep(4000);
					FormsPage formsPage = homepage.openFormsPage();
					
					formsPage.enterFreeWordOnSearchPage(freewordVal);
					Thread.sleep(4000);
					searchresultpage = formsPage.clickOnSearch();
					break;

				}
				boolean searchResultsDisplayed = searchresultpage != null
						&& searchresultpage.searchResultsHeaderContainerDisplayed()
						&& searchresultpage.searchReturnedResultsAsExpected(freewordVal);

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordKey,
								freewordVal, jiraNumber);
					else {
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softas.assertTrue(searchResultsDisplayed, jiraNumber + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, linktoclickVal,
						"" + freewordKey + ": " + freewordVal, jiraNumber);
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
	 * This method validate the footer Text once footer link clicked on Homepage for
	 * chpmex This method also includes MAFAUTO-155
	 */
/*	@Test(priority = 28, groups = { "chpmex" }, description = "MAFAUTO-136")
	public void ValidateFooterUsagePolicies() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			boolean ValidateFooterLink = false;
			boolean ValidateFooterText = false;

			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Terms & conditions (Footer link) validated in Home Page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String footertextkey = "footertext";
				String footertextVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, footertextkey,
						extentLogger);
				String expurlkey = "expurl";
				String expurlVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, expurlkey, extentLogger);

				ValidateFooterText = homepage.ValidateFooterLinkText(footertextVal);
				homepage.clickUsagePolicyLink();

				softas.assertTrue(ValidateFooterText, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, ValidateFooterText, issueSummary, jiraNumber);

				ValidateFooterLink = homepage.validateRedirectedUrl(expurlVal);

				softas.assertTrue(ValidateFooterLink, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, ValidateFooterLink, issueSummary, footertextkey + ":" + footertextVal,
						expurlkey + ":" + expurlVal, jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}*/

	@Test(priority = 15, groups = { "chpmex" }, description = "MAFAUTO-191")
	public void footerTermsAndConditions() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify Terms and conditon link in home page footer");

			boolean isTermsOfUseLinkDisplay = homepage.isTermsAndConditionLinkDisplayedInFooter();
			softas.assertTrue(isTermsOfUseLinkDisplay, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, isTermsOfUseLinkDisplay,
					"Terms and conditon Link is present in footer of home page", jiraNumber);

			// Click terms of use link
			homepage.clickTermsAndConditonLink();
			boolean errorurl=homepage.isErrorUrlFound();
			softas.assertTrue(errorurl, issueSummary + ":" + jiraNumber);

			/*boolean isTermsAndcondtionisPDF = homepage.isTermsAndcondtionisPDF();
			softas.assertTrue(isTermsAndcondtionisPDF, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, isTermsAndcondtionisPDF, "Terms and Condtion Page is of PDF format",
					jiraNumber);
			// Navigate back to document display page.
			if (homepage.isTermsAndcondtionisPDF())
				driver.navigate().back();*/

			boolean isPageNavigatesBackToHomePage = homepage.isHomePageDisplayed();

			softas.assertTrue(isPageNavigatesBackToHomePage, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, isPageNavigatesBackToHomePage, "Navigated back to Home page", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 14, groups = { "chpmex" }, description = "MAFAUTO-192")
	public void FrequentAskedQuestionsWidgetforAdministrativo() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean linkverified = false;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Home Page Validate Frequently Asked Questions widget for Thematic Area Administrativo");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedlinksKey = "expectedlinks";
				JSONArray expectedlinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedlinksKey,
						extentLogger);
				homepage.clickOnProductLogo();
				// homepage.SelectThematicareadropdowns(thematicarea);
				boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
						jiraNumber);
				Thread.sleep(1000);
				homepage.expandFAQWidget();
				// homepage.clickSeeMoreLinkInFreqAskQuesWidget();
				for (int row = 0; row < expectedlinks.size(); row++) {
					linkverified = homepage.FreqAskQuesLinkDisplayed(expectedlinks.get(row).toString());
					if (!linkverified)
						break;
				}

				softas.assertTrue(linkverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linkverified, issueSummary, thematicareaKey, thematicarea, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 14, groups = { "chpmex" }, description = "MAFAUTO-193")
	public void FrequentAskedQuestionsWidgetforAmparo() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean linkverified = false;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Home Page Validate Frequently Asked Questions widget for Thematic Area Amparo");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedlinksKey = "expectedlinks";
				JSONArray expectedlinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedlinksKey,
						extentLogger);
				homepage.clickOnProductLogo();
				// homepage.SelectThematicareadropdowns(thematicarea);

				boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
						jiraNumber);

				Thread.sleep(2000);
				homepage.expandFAQWidget();
				// homepage.clickSeeMoreLinkInFreqAskQuesWidget();
				for (int row = 0; row < expectedlinks.size(); row++) {
					linkverified = homepage.FreqAskQuesLinkDisplayed(expectedlinks.get(row).toString());
					if (!linkverified)
						break;
				}

				softas.assertTrue(linkverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linkverified, issueSummary, thematicareaKey, thematicarea, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 14, groups = { "chpmex" }, description = "MAFAUTO-194")
	public void FrequentAskedQuestionsWidgetforCivil() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean linkverified = false;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Home Page Validate Frequently Asked Questions widget for Thematic Area Civil");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedlinksKey = "expectedlinks";
				JSONArray expectedlinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedlinksKey,
						extentLogger);
				homepage.clickOnProductLogo();
				boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
						jiraNumber);
				Thread.sleep(1000);
				homepage.expandFAQWidget();
				// homepage.clickSeeMoreLinkInFreqAskQuesWidget();
				for (int row = 0; row < expectedlinks.size(); row++) {
					linkverified = homepage.FreqAskQuesLinkDisplayed(expectedlinks.get(row).toString());
					if (!linkverified)
						break;
				}

				softas.assertTrue(linkverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linkverified, issueSummary, thematicareaKey, thematicarea, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 14, groups = { "chpmex" }, description = "MAFAUTO-195")
	public void FrequentAskedQuestionsWidgetforConstitucional() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean linkverified = false;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Home Page Validate Frequently Asked Questions widget for Thematic Area Constitucional");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedlinksKey = "expectedlinks";
				JSONArray expectedlinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedlinksKey,
						extentLogger);
				homepage.clickOnProductLogo();
				// homepage.SelectThematicareadropdowns(thematicarea);
				boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
						jiraNumber);
				Thread.sleep(1000);
				homepage.expandFAQWidget();
				// homepage.clickSeeMoreLinkInFreqAskQuesWidget();
				for (int row = 0; row < expectedlinks.size(); row++) {
					linkverified = homepage.FreqAskQuesLinkDisplayed(expectedlinks.get(row).toString());
					if (!linkverified)
						break;
				}

				softas.assertTrue(linkverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linkverified, issueSummary, thematicareaKey, thematicarea, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 14, groups = { "chpmex" }, description = "MAFAUTO-196")
	public void FrequentAskedQuestionsWidgetforCorporativo() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean linkverified = false;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Home Page Validate Frequently Asked Questions widget for Thematic Area Corporativo");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedlinksKey = "expectedlinks";
				JSONArray expectedlinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedlinksKey,
						extentLogger);
				homepage.clickOnProductLogo();
				 homepage.SelectThematicareadropdowns(thematicarea);
				boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
						jiraNumber);
				Thread.sleep(1000);
				homepage.expandFAQWidget();
				// homepage.clickSeeMoreLinkInFreqAskQuesWidget();
				for (int row = 0; row < expectedlinks.size(); row++) {
					linkverified = homepage.FreqAskQuesLinkDisplayed(expectedlinks.get(row).toString());
					if (!linkverified)
						break;
				}

				softas.assertTrue(linkverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linkverified, issueSummary, thematicareaKey, thematicarea, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 14, groups = { "chpmex" }, description = "MAFAUTO-197")
	public void FrequentAskedQuestionsWidgetforFinanciero() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean linkverified = false;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Home Page Validate Frequently Asked Questions widget for Thematic Area Financiero");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedlinksKey = "expectedlinks";
				JSONArray expectedlinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedlinksKey,
						extentLogger);
				homepage.clickOnProductLogo();
				// homepage.SelectThematicareadropdowns(thematicarea);
				boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
						jiraNumber);
				Thread.sleep(1000);
				homepage.expandFAQWidget();
				// homepage.clickSeeMoreLinkInFreqAskQuesWidget();
				for (int row = 0; row < expectedlinks.size(); row++) {
					linkverified = homepage.FreqAskQuesLinkDisplayed(expectedlinks.get(row).toString());
					if (!linkverified)
						break;
				}

				softas.assertTrue(linkverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linkverified, issueSummary, thematicareaKey, thematicarea, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 14, groups = { "chpmex" }, description = "MAFAUTO-198")
	public void FrequentAskedQuestionsWidgetforMercantil() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean linkverified = false;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Home Page Validate Frequently Asked Questions widget for Thematic Area Mercantil");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedlinksKey = "expectedlinks";
				JSONArray expectedlinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedlinksKey,
						extentLogger);
				homepage.clickOnProductLogo();
				// homepage.SelectThematicareadropdowns(thematicarea);
				boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
						jiraNumber);
				Thread.sleep(1000);
				homepage.expandFAQWidget();
				// homepage.clickSeeMoreLinkInFreqAskQuesWidget();
				for (int row = 0; row < expectedlinks.size(); row++) {
					linkverified = homepage.FreqAskQuesLinkDisplayed(expectedlinks.get(row).toString());
					if (!linkverified)
						break;
				}

				softas.assertTrue(linkverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linkverified, issueSummary, thematicareaKey, thematicarea, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 14, groups = { "chpmex" }, description = "MAFAUTO-199")
	public void FrequentAskedQuestionsWidgetforPenal() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean linkverified = false;
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Home Page Validate Frequently Asked Questions widget for Thematic Area Penal");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String expectedlinksKey = "expectedlinks";
				JSONArray expectedlinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, expectedlinksKey,
						extentLogger);
				homepage.clickOnProductLogo();
				// homepage.SelectThematicareadropdowns(thematicarea);
				boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
						jiraNumber);
				Thread.sleep(1000);
				homepage.expandFAQWidget();
				// homepage.clickSeeMoreLinkInFreqAskQuesWidget();
				for (int row = 0; row < expectedlinks.size(); row++) {
					linkverified = homepage.FreqAskQuesLinkDisplayed(expectedlinks.get(row).toString());
					if (!linkverified)
						break;
				}

				softas.assertTrue(linkverified, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, linkverified, issueSummary, thematicareaKey, thematicarea, jiraNumber);
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
	 * This test verifies externals links available in Course and Seminar page
	 */
	@Test(groups = { "chppe" }, description = "CPPE-980")
	public void courseAndSeminarExternalLinks() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		boolean isLinkPresent = false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Courses and Seminars - External Links");

			homepage.openHomepage();
			courseandseminar = homepage.openCourseAndSeminar();

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String externallinksKey = "externallinks";
				JSONArray externallinks = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, externallinksKey,
						extentLogger);

				for (Object links : externallinks) {
					String linkname = links.toString().toUpperCase();
					isLinkPresent = courseandseminar.isGivenLinkPresent(linkname);
					if (!isLinkPresent) {
						testResult.setAttribute("Defect", "CPPE-980");
					}

					softas.assertTrue(isLinkPresent, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isLinkPresent, issueSummary, "Link", linkname, jiraNumber);
				}

			}

			boolean isknowmaxlinkpresent = courseandseminar.isKnowMaxLinkDisplayed();
			softas.assertTrue(isknowmaxlinkpresent, "Know Max Link is displayed" + ":" + jiraNumber);
			logExtentStatus(extentLogger, isknowmaxlinkpresent, "Know Max Link is displayed", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// MAFQABANG-638="Help-Data Update"
	@Test(priority = 40, groups = { "chpmex" }, description = "MAFQABANG-638")
	public void ClickHelpButontoOpenHelpPage() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Help page opens when clicked on HELP button");
	        Thread.sleep(2000);
			homepage.openHomepage();
			Thread.sleep(2000);
			homepage.clickonHelpButton();
			boolean ishelpageddisplayed = homepage.isHelpPageDisplayed();
			softas.assertTrue(ishelpageddisplayed, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, ishelpageddisplayed, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// MAFQABANG-649="Indicators - Legislation - Document Display (myDocuments, top
	// line links, 4 tabs left-panel, access by widget)"
	@Test(priority = 25, groups = { "chpmex" }, description = "MAFQABANG-649")
	public void ValidationOfIndicatorWidget() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			boolean ValidateIndicatorsTitle = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Validate the options in the document display page of indicator widget");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String Indicatorkey = "indicators_widget";
				String widgetText = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, Indicatorkey, extentLogger);
				String link_title1key = "indicator_link1";
				String link_Text1val = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, link_title1key,
						extentLogger);
				String link_title2key = "indicator_link2";
				String link_Text2val = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, link_title2key,
						extentLogger);
				String thematicareaKey = "thematicarea";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				String freewordKey = "freeword";
				String freewordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				String nodeunderfiscalKey = "firstnodeunderFiscal";
				String nodeunderfiscalval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, nodeunderfiscalKey,
						extentLogger);

				String indicatorKey = "Indicatornode";
				String indicatorval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, indicatorKey,
						extentLogger);

				String firsttitleKey = "firsttitle";
				String firsttitleval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, firsttitleKey,
						extentLogger);

				homepage.openHomepage();

				ValidateIndicatorsTitle = homepage.ValidateIndicatorsTitle(widgetText);
				homepage.clickOnPlusButtonOfIndicators();
				softas.assertTrue(ValidateIndicatorsTitle, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, ValidateIndicatorsTitle, "Indicators title displayed", jiraNumber);

				docdisplaypage = homepage.clickOnGivenLink(link_Text1val);

				boolean isfirstdocumentdisplayed = docdisplaypage.isFirstDocumentDisplayed();
				softas.assertTrue(isfirstdocumentdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isfirstdocumentdisplayed, "First document title displayed", jiraNumber);

				homepage = docdisplaypage.returnToHomePage();

				homepage.clickOnGivenLinkByLinkText(link_Text2val, false);

				boolean isseconddocumentdisplayed = docdisplaypage.isSecondDocumentDisplayed();
				softas.assertTrue(isseconddocumentdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isseconddocumentdisplayed, "Second document title displayed", jiraNumber);

				boolean isrelatedcontenttabdisplayed = docdisplaypage.isRelatedContentTabDisplayed();
				softas.assertTrue(isrelatedcontenttabdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isrelatedcontenttabdisplayed, "Related Content tab is present",
						jiraNumber);

				docdisplaypage.clickContentTreeTab();
				boolean isTOCdisplayed = docdisplaypage.isContentTreeTabDisplayed();
				softas.assertTrue(isTOCdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isTOCdisplayed, "Table of Contents tab is present", jiraNumber);

				boolean issearchbuttondisplayed = docdisplaypage.isSearchButtonDisplayed();
				softas.assertTrue(issearchbuttondisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, issearchbuttondisplayed, "Search buttton is present", jiraNumber);

				boolean isannotationdropdowndisplayed = docdisplaypage.isAnnotationDropDownDisplayed();
				softas.assertTrue(isannotationdropdowndisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isannotationdropdowndisplayed, "Annotation Dropdown is present",
						jiraNumber);

				boolean istextupdisplayed = docdisplaypage.isEnlargeTextOptionDisplayed();
				softas.assertTrue(istextupdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, istextupdisplayed, "Enlarge text icon is present", jiraNumber);

				boolean istextdowndisplayed = docdisplaypage.isChangeTextOptionDisplayed();
				softas.assertTrue(istextdowndisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, istextdowndisplayed, "Change text icon is present", jiraNumber);

				docdisplaypage.clickMyDocumentFolder();
				boolean iscancelbuttonpresent = docdisplaypage.clickCancelInPopUp();
				softas.assertTrue(iscancelbuttonpresent, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, iscancelbuttonpresent, "the My document icon is present", jiraNumber);

				deliveryPage = docdisplaypage.clickExportButton();
				deliveryPage.clickCancelButton();
				boolean ispopupclosedafterexport = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(ispopupclosedafterexport, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !ispopupclosedafterexport, "the Export icon is present", jiraNumber);

				deliveryPage.clickPrint();
				deliveryPage.navigateBack();
				boolean ispopupclosedafterprint = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(ispopupclosedafterprint, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !ispopupclosedafterprint, "the print icon is present", jiraNumber);

				deliveryPage.emailClicked();
				deliveryPage.clickCancelButton();
				boolean ispopupclosedaftermail = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(ispopupclosedaftermail, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !ispopupclosedaftermail, "the mail icon is present", jiraNumber);

				homepage = docdisplaypage.returnToHomePage();
				searchpage = homepage.clickOnSearchTab();
				// searchpage.selectThematicArea(thematicarea);
				// searchpage.enterFreeWordOnSearchPage(freewordval);
				boolean isThematicAreaSelected = searchpage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, "the thematic area is selected", jiraNumber);
				searchpage.enterTextInSearchField(freewordval);

				searchresultpage = searchpage.clickOnSearch();

				docdisplaypage = searchresultpage.getFirstDocument();
				boolean isdocumentdisplayed = docdisplaypage.isDocumentDisplayed();
				softas.assertTrue(isdocumentdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isdocumentdisplayed, "Documents are displayed", jiraNumber);

				boolean isrelatedcontentdisplayed = docdisplaypage.isRelatedContentTabDisplayed();
				softas.assertTrue(isrelatedcontentdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isrelatedcontentdisplayed, "Related Content tab is present", jiraNumber);

				boolean istocdisplayed = docdisplaypage.isContentTreeTabDisplayed();
				softas.assertTrue(istocdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, istocdisplayed, "Table of Contents tab is present", jiraNumber);

				boolean islistofdocdisplayed = docdisplaypage.isListofDocumentsLinkDispalyed();
				softas.assertTrue(islistofdocdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, islistofdocdisplayed, "List of documents tab is present", jiraNumber);

				boolean isrevsearchtabdisplayed = docdisplaypage.isRevOfSearchTabDisplayed();
				softas.assertTrue(isrevsearchtabdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isrevsearchtabdisplayed, "RDO of the search tab is present", jiraNumber);

				boolean issearchbttndisplayed = docdisplaypage.isSearchButtonDisplayed();
				softas.assertTrue(issearchbttndisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, issearchbttndisplayed, "Search buttton is present", jiraNumber);

				boolean isannotatndropdowndisplayed = docdisplaypage.isAnnotationDropDownDisplayed();
				softas.assertTrue(isannotatndropdowndisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isannotatndropdowndisplayed, "Annotation Dropdown is present",
						jiraNumber);

				boolean istextUpdisplayed = docdisplaypage.isEnlargeTextOptionDisplayed();
				softas.assertTrue(istextUpdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, istextUpdisplayed, "Enlarge text icon is present", jiraNumber);

				boolean istextDowndisplayed = docdisplaypage.isChangeTextOptionDisplayed();
				softas.assertTrue(istextDowndisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, istextDowndisplayed, "Change text icon is present", jiraNumber);

				docdisplaypage.clickMyDocumentFolder();
				boolean isCancelbuttonpresent = docdisplaypage.clickCancelInPopUp();
				softas.assertTrue(isCancelbuttonpresent, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isCancelbuttonpresent, "the My document icon is present", jiraNumber);

				deliveryPage = docdisplaypage.clickExportButton();
				deliveryPage.clickCancelButton();
				boolean isPopupclosedafterexport = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(isPopupclosedafterexport, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !isPopupclosedafterexport, "the Export icon is present", jiraNumber);

				deliveryPage.clickPrint();
				deliveryPage.navigateBack();
				boolean isPopupclosedafterprint = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(isPopupclosedafterprint, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !isPopupclosedafterprint, "the print icon is present", jiraNumber);

				deliveryPage.emailClicked();
				deliveryPage.clickCancelButton();
				boolean isPopupclosedaftermail = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(isPopupclosedaftermail, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !isPopupclosedaftermail, "the mail icon is present", jiraNumber);

				searchpage = docdisplaypage.Clicksearchtab();

				searchpage.clickOnFirstNodeofTOC();
				searchpage.clickOnGivenLinkByLinkText(nodeunderfiscalval, false);
				searchpage.clickOnGivenLinkByLinkText(indicatorval, false);
				searchpage.clickOnGivenLinkByLinkText(firsttitleval, false);

				boolean isdocumenttitledisplayed = docdisplaypage.isDocumentDisplayed();
				softas.assertTrue(isdocumenttitledisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isdocumenttitledisplayed, "Document title is displayed", jiraNumber);

				boolean isRelatedContentdisplayed = docdisplaypage.isRelatedContentTabDisplayed();
				softas.assertTrue(isRelatedContentdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isRelatedContentdisplayed, "Related Content tab is present", jiraNumber);

				docdisplaypage.clickContentTreeTab();
				boolean isTOCDisplayed = docdisplaypage.isContentTreeTabDisplayed();
				softas.assertTrue(isTOCDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isTOCDisplayed, "Table of Contents tab is present", jiraNumber);

				boolean isSearchbttndisplayed = docdisplaypage.isSearchButtonDisplayed();
				softas.assertTrue(isSearchbttndisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchbttndisplayed, "Search buttton is present", jiraNumber);

				boolean isAnnotatndropdowndisplayed = docdisplaypage.isAnnotationDropDownDisplayed();
				softas.assertTrue(isAnnotatndropdowndisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isAnnotatndropdowndisplayed, "Annotation Dropdown is present",
						jiraNumber);

				boolean isTextUpdisplayed = docdisplaypage.isEnlargeTextOptionDisplayed();
				softas.assertTrue(isTextUpdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isTextUpdisplayed, "Enlarge text icon is present", jiraNumber);

				boolean isTextDowndisplayed = docdisplaypage.isChangeTextOptionDisplayed();
				softas.assertTrue(isTextDowndisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isTextDowndisplayed, "Change text icon is present", jiraNumber);

				docdisplaypage.clickMyDocumentFolder();
				boolean isCancelButtonpresent = docdisplaypage.clickCancelInPopUp();
				softas.assertTrue(isCancelButtonpresent, issueSummary + "the My document icon is present" + jiraNumber);

				deliveryPage = docdisplaypage.clickExportButton();
				deliveryPage.clickCancelButton();
				boolean isPopupClosedafterexport = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(isPopupClosedafterexport, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !isPopupClosedafterexport, "the Export icon is present", jiraNumber);

				deliveryPage.clickPrint();
				deliveryPage.navigateBack();
				boolean ispopupclosedafterPrint = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(ispopupclosedafterPrint, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !ispopupclosedafterPrint, "the print icon is present", jiraNumber);

				deliveryPage.emailClicked();
				deliveryPage.clickCancelButton();
				boolean ispopupclosedafterMail = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(ispopupclosedafterMail, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !ispopupclosedafterMail, "the mail icon is present", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// this test selects and enters data in "thematic area" and "freeword" and once
	// the document page is
	// displayed,it validates whether the "mydocuments,export,print,email" icon are
	// displayed.
	@Test(priority = 25, groups = { "chpmex" }, description = "MAFQABANG-639")
	public void ValidationOfIconsInDocumentDisplayPage() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareakey = "thematicarea";
				String thematicareaval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareakey,
						extentLogger);
				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);

				homepage.openHomepage();

				// Performing Search
				searchpage = homepage.OpenSearchPage();
				searchpage.selectThematicArea(thematicareaval);
//				searchpage.enterFreeWordOnSearchPage(free_wordval);
				homepage.enterTextInSearchField(free_wordval);
				searchresultpage = searchpage.clickOnSearch();

				searchResultsDisplayed = searchresultpage != null
						&& searchresultpage.searchResultsHeaderContainerDisplayed();

				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", free_wordkey,
								free_wordval, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softas.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration

				}

				docdisplaypage = searchresultpage.getFirstDocument();
				boolean isDocDisplayed = docdisplaypage.isDocumentDisplayed();
				softas.assertTrue(isDocDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isDocDisplayed, "Result list document display", free_wordkey,
						free_wordval, jiraNumber);

				// validating whether "MyDocuments,export,email,print" options are present by
				// clicking on them
				docdisplaypage.clickMyDocumentFolder();

				boolean iscancelbuttonpresent = docdisplaypage.clickCancelInPopUp();
				softas.assertTrue(iscancelbuttonpresent, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, iscancelbuttonpresent, "cancel button is present inside the popup",
						free_wordkey, free_wordval, jiraNumber);

				deliveryPage = docdisplaypage.clickExportButton();
				deliveryPage.clickCancelButton();
				boolean ispopupclosedafterexport = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(ispopupclosedafterexport, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !ispopupclosedafterexport,
						"the Export window is closed and displays document display page", free_wordkey, free_wordval,
						jiraNumber);

				deliveryPage.clickPrint();
				deliveryPage.navigateBack();
				boolean ispopupclosedafterprint = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(ispopupclosedafterprint, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !ispopupclosedafterprint,
						"the print window is closed and displays document display page", free_wordkey, free_wordval,
						jiraNumber);

				deliveryPage.emailClicked();
				deliveryPage.clickCancelButton();
				boolean ispopupclosedaftermail = deliveryPage.IsPopUpWindowPresent();
				softas.assertFalse(ispopupclosedaftermail, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, !ispopupclosedaftermail,
						"the mail window is closed and displays document display page", free_wordkey, free_wordval,
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

////////////////////////////////////Implementation of the CHPMEX New Functionality Testcases//////////////////////////////////////////////////		

	// Description : "This testcase checks whether the Global Search title is
	// displayed in the homepage and checks the placeholder text in search textbox"
	// <Created Date : 24-Oct-2018 > ; <author : Havya>
	// this testcase covers 1 and 2
	// this testcase covers MAFQABANG-662 and MAFQABANG-664

	@Test(priority = 3, groups = { "chpmex" }, description = "MAFQABANG-662")
	public void ValidationOfGlobalSearchInHome() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Validation of Global search");

			homepage.openHomepage();
			boolean isglobalsearchtitleDisplayed = homepage.isGlobalSearchTitleDisplayed(1);
			softas.assertTrue(isglobalsearchtitleDisplayed, jiraNumber + ":" + issueSummary + " : title is displayed");
			logExtentStatus(extentLogger, isglobalsearchtitleDisplayed, " Global search title is displayed",
					jiraNumber);

			// Verify the placeholder text in global search textbox - MAFQABANG-664
			boolean isPlaceHolderTextPresent = homepage.isPlaceHolderTextDisplayed();
			softas.assertTrue(isPlaceHolderTextPresent,
					jiraNumber + ":" + issueSummary + " : PlaceHolder Text is displyed");
			logExtentStatus(extentLogger, isPlaceHolderTextPresent, " PlaceHolder Text is displyed", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	// Description : "This testacse validates the options present in thematic area
	// dropdown and also selects one option from the dropdown"
	// <Created Date : 24-Oct-2018 > ; <author : Havya>
	// testcase 3 and 4
	@Test(priority = 3, groups = { "chpmex" }, description = "MAFQABANG-666")
	public void ValidationofThematicAreaDropdownInHome() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Validation of Thematic area dropdown and its options");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String thematicareaKey = "thematic_area";
				String thematicarea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);

				homepage.openHomepage();

				// Below code is to check the options of thematic area from dropdown
				// -MAFQABANG-665
				boolean isThematicAreaOptionsDisplayed = homepage.isThematicAreaDropdownOptionsAvailableInMex();
				softas.assertTrue(isThematicAreaOptionsDisplayed,
						jiraNumber + ":" + issueSummary + " : all the options are present in the dropdown");
				logExtentStatus(extentLogger, isThematicAreaOptionsDisplayed,
						" all the options are present in the dropdown", jiraNumber);

				// Below code is to select the given thematic area from dropdown -MAFQABANG-666

				boolean isThematicAreaSelected = homepage.thematicAreaDropdownContainsValueInMex(thematicarea);
				softas.assertTrue(isThematicAreaSelected, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isThematicAreaSelected, issueSummary, thematicareaKey, thematicarea,
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

	// This test is to check the place holder value in the search box in home page
	// <Created Date : 24-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 26, groups = { "chpmex" }, description = "MAFQABANG-701")
	public void verifyPlaceHolderValueForAskChpInHome() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				homepage.openHomepage();
				homepage.clickAskCheckpointLink();
				Thread.sleep(2000);
				boolean verifiedPlaceHolder = homepage.isPlaceHolderTextDisplayedForAskChp();
				softas.assertTrue(verifiedPlaceHolder, jiraNumber + ":" + issueSummary
						+ " : Verified Place Holder Value in the ask checkpoint search box");
				logExtentStatus(extentLogger, verifiedPlaceHolder,
						" Verified Place Holder Value in the ask checkpoint search box", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// This test case is to check the text present when hovering the help icon in
	// home page
	// <Created Date : 24-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 27, groups = { "chpmex" }, description = "MAFQABANG-668")
	public void verifyTextInHelpIconInHome() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				homepage.openHomepage();
				Thread.sleep(2000);
				boolean verifiedText = homepage.helpIconText();
				softas.assertTrue(verifiedText,
						jiraNumber + ":" + issueSummary + " : Verified the text present when hover on help icon");
				logExtentStatus(extentLogger, verifiedText, " Verified the text present when hover on help icon",
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

	// This test case is to validate the clean button home page
	// <Created Date : 24-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 28, groups = { "chpmex" }, description = "MAFQABANG-678")
	public void verifyCleanButtonInHome() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);

				homepage.openHomepage();
				homepage.enterTextInSearchField(free_wordval);
				homepage.clickClear();
				Thread.sleep(2000);
				boolean verifiedPlaceHolder = homepage.isPlaceHolderTextDisplayed();
				softas.assertTrue(verifiedPlaceHolder,
						jiraNumber + ":" + issueSummary + " : Validated the clean button in the Home Page");
				logExtentStatus(extentLogger, verifiedPlaceHolder, " Validated the clean button in the Home Page",
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

	// Description: "This testcase checks whether the see all link is present next
	// to each document type"
	// <Created Date : 25-Oct-2018 > ; <author : Havya>
	// testcase no:36
	// this testcase covers 36

	@Test(priority = 3, groups = { "chpmex" }, description = "MAFQABANG-686")
	public void ValidationofSeeAllLinkInHome() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify See all Link next to each documentary");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey = "freeword";
				String freewordValue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey,
						extentLogger);

				String docTypeKey = "doc_type";
				JSONArray docTypeValue = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, docTypeKey,
						extentLogger);

				homepage.openHomepage();
				homepage.enterTextInSearchField(freewordValue);
				Thread.sleep(1000);

				searchresultpage = homepage.clickOnSearchInHomePage();

				for (int row = 0; row < docTypeValue.size(); row++) {

					String docTypeLinks = docTypeValue.get(row).toString();
					searchresultpage.clickSeeAllLink(docTypeLinks);

					/*
					 * boolean isTotalNoOfResultsDisplayed =
					 * searchresultpage.verifyTotalResultsText();
					 * softas.assertTrue(isTotalNoOfResultsDisplayed, jiraNumber + ":" +
					 * issueSummary + " :The total number of results text is displayed");
					 * logExtentStatus(extentLogger, isTotalNoOfResultsDisplayed,
					 * ": total number of results text is displayed", jiraNumber);
					 */

					docdisplaypage = searchresultpage.getFirstDocument();
					boolean isDocDisplayed = docdisplaypage.isDocumentDisplayed();
					softas.assertTrue(isDocDisplayed,
							jiraNumber + ":" + issueSummary + " :The Document display page is displayed");
					logExtentStatus(extentLogger, isDocDisplayed, ":The Document display page is displayed",
							jiraNumber);

					driver.navigate().back();
					driver.navigate().back();

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

	// Description: "This testcase checks whether the filter results widget is
	// present
	// and also validates the document type links present inside it"
	// <Created Date : 25-Oct-2018 > ; <author : Havya>
	// this testcase covers 43,47

	@Test(priority = 3, groups = { "chpmex" }, description = "MAFQABANG-690")
	public void ValidationOfFilterResultsWidgetInHome() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Validate the filter results widget and the links present inside it from home page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey = "freeword";
				String freewordValue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey,
						extentLogger);

				String docTypeKey = "doc_type";
				JSONArray docTypeValue = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, docTypeKey,
						extentLogger);

				String widgetKey = "widget_name";
				String widgetValue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, widgetKey, extentLogger);
				;

				homepage.openHomepage();
				homepage.enterTextInSearchField(freewordValue);
				Thread.sleep(1000);

				searchresultpage = homepage.clickOnSearchInHomePage();

				// Verify User should able to see box appears filter results under the search
				// results - MAFQABANG-690

				boolean iswidgetdisplayed = searchresultpage.isWidgetsDisplayedInLeftPanel(widgetValue);
				softas.assertTrue(iswidgetdisplayed, jiraNumber + ":" + issueSummary + " :The widget is displayed");
				logExtentStatus(extentLogger, iswidgetdisplayed, ": widget is displayed", widgetKey, widgetValue,
						jiraNumber);

				for (int row = 0; row < docTypeValue.size(); row++) {

					String docTypeLinks = docTypeValue.get(row).toString();

					// Verify the clicking of links in filter results leads to specific results list
					// - MAFQABANG-714
					searchresultpage.clickOnGivenLinkByLinkText(docTypeLinks, false);

					boolean isDocTypeLabelDisplayed = searchresultpage.isDocTypeLabelDisplayed(docTypeLinks);
					softas.assertTrue(isDocTypeLabelDisplayed,
							jiraNumber + ":" + issueSummary + " :The Document type is displayed");
					logExtentStatus(extentLogger, isDocTypeLabelDisplayed, ": document type is displayed", jiraNumber);

					docdisplaypage = searchresultpage.getFirstDocument();
					boolean isDocDisplayed = docdisplaypage.isDocumentDisplayed();
					softas.assertTrue(isDocDisplayed,
							jiraNumber + ":" + issueSummary + " :The Document display page is displayed");
					logExtentStatus(extentLogger, isDocDisplayed, ": document is displayed", jiraNumber);

					driver.navigate().back();
					driver.navigate().back();

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

	// This test case is to validate the results in home page
	// <Created Date : 25-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 27, groups = { "chpmex" }, description = "MAFQABANG-679")
	public void validatingResultsForSearchInHome() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);
				String suggestion = "suggestion_search";
				String suggestion_word = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestion,
						extentLogger);

				homepage.openHomepage();
				Thread.sleep(2000);
				homepage.enterTextInSearchField(free_wordval);
				homepage.selectingSuggestionsDropdownDisplayed(suggestion_word);
//				homepage.clickClear();
//				Thread.sleep(2000);
				boolean validatedSearchResult = homepage.isSearchResultsDisplayed();
				softas.assertTrue(validatedSearchResult,
						jiraNumber + ":" + issueSummary + " : Validated the results for search in the Home Page");
				logExtentStatus(extentLogger, validatedSearchResult,
						" Validated the results for search in the Home Page", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// This test case is to validate the actions buttons in the result page in home
	// page
	// covers both MAFQABANG-721 and MAFQABANG-717 test cases
	// <Created Date : 25-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 29, groups = { "chpmex" }, description = "MAFQABANG-721")
	public void validatingActionsInResultPageFromHome() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);
				String linkToClick = "link";
				String link_click = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, linkToClick, extentLogger);
				String to_export = "export";
				String export = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, to_export, extentLogger);
				String to_print = "print";
				String print = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, to_print, extentLogger);
				String to_email = "email";
				String email = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, to_email, extentLogger);

				homepage.openHomepage();
				Thread.sleep(2000);
				homepage.enterTextInSearchField(free_wordval);
				searchresultpage = homepage.clickSearch();
				homepage.clickOnGivenLinkByLinkText(link_click, false);
				Thread.sleep(2000);
				boolean validatedExportButton = searchresultpage.validateActions(export);
				softas.assertTrue(validatedExportButton,
						jiraNumber + ":" + issueSummary + " : Validated the Export button in the result Page");
				logExtentStatus(extentLogger, validatedExportButton,
						" Validated the results for search in the Search Page", jiraNumber);
				boolean validatedPrintButton = searchresultpage.validateActions(print);
				softas.assertTrue(validatedPrintButton,
						jiraNumber + ":" + issueSummary + " : Validated the print button in the result Page");
				logExtentStatus(extentLogger, validatedPrintButton,
						" Validated the results for search in the Search Page", jiraNumber);
				boolean validatedEmailButton = searchresultpage.validateActions(email);
				softas.assertTrue(validatedEmailButton,
						jiraNumber + ":" + issueSummary + " : Validated the email button in the result Page");
				logExtentStatus(extentLogger, validatedEmailButton,
						" Validated the results for search in the Search Page", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description:"This testcase is used to compare the count that is in
	// paranthesis next to each document type in
	// left panel and see all link in right panel
	// <Created Date : 29-Oct-2018 > ; <author : Havya>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-713")
	public void verifyCountOfSeeAllLinkInHome() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify along with filter results the link of each document type found and in paranthesis number of matches from home page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);

				homepage.openHomepage();
				Thread.sleep(2000);
				homepage.enterTextInSearchField(free_wordval);

				searchresultpage = homepage.clickSearch();
				Thread.sleep(3000);
				// comparing the count in the paranthesis which is present next to legislation
				// document type in left panel and the see all link in right panel
				boolean isCountMatch1 = searchresultpage.compareCountInSeeAllLink(2);
				softas.assertTrue(isCountMatch1, jiraNumber + ":" + issueSummary + " :The Document type is displayed");
				logExtentStatus(extentLogger, isCountMatch1, ": document type is displayed", jiraNumber);
				Thread.sleep(5000);
				// comparing the count in the paranthesis which is present next to Doctrina
				// document type in left panel and the see all link in right panel
				boolean isCountMatch2 = searchresultpage.compareCountInSeeAllLink(3);
				softas.assertTrue(isCountMatch2, jiraNumber + ":" + issueSummary + " :The Document type is displayed");
				logExtentStatus(extentLogger, isCountMatch2, ": document type is displayed", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description:"This testcase is used to compare the count that is in
	// paranthesis next to each document type in
	// left panel and the count in right panel
	// <Created Date : 29-Oct-2018 > ; <author : Havya>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-689")
	public void verifyCountOfDocumentTypeInHome() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify along with filter results the link of each document type found and in paranthesis number of matches from home page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);

				String docTypeKey = "doc_type";
				JSONArray docTypeValue = jsonReader.readJSonArrayFromJsonObject(jsonObjectChild, docTypeKey,
						extentLogger);

				homepage.openHomepage();

				homepage.enterTextInSearchField(free_wordval);

				searchresultpage = homepage.clickSearch();

				for (int row = 0; row < docTypeValue.size(); row++) {

					String docTypeLinks = docTypeValue.get(row).toString();

					searchresultpage.clickSeeAllLink(docTypeLinks);

					boolean isDocTypeLabelDisplayed = searchresultpage.verifyCountOfDocs();
					softas.assertTrue(isDocTypeLabelDisplayed, jiraNumber + ":" + issueSummary
							+ " :The count of the document type in left panel is equal to the count in right panel");
					logExtentStatus(extentLogger, isDocTypeLabelDisplayed,
							":The count of the document type in left panel is equal to the count in right panel",
							jiraNumber);

					driver.navigate().back();

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

	// This test case is to validate the list of the documnets button in Home page
	// <Created Date : 29-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 28, groups = { "chpmex" }, description = "MAFQABANG-696")
	public void validatingListaDeDocumentosInHomePage() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);
				String suggestion = "suggestion_search";
				String suggestion_word = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestion,
						extentLogger);

				homepage.openHomepage();
				Thread.sleep(2000);
				homepage.enterTextInSearchField(free_wordval);
				homepage.selectingSuggestionsDropdownDisplayed(suggestion_word);
				Thread.sleep(2000);
				searchresultpage = homepage.clickSearch();
				searchresultpage.clickOnVerCoincidenciasLink(1);
				docdisplaypage = searchresultpage.clickUnitAndRubric();
				searchresultpage = docdisplaypage.clickOnListOfDocumentsToReturnToSearchResults();
				boolean isLinkDisplayed = searchresultpage.isVerCoincidenciasLinkDisplayed(1);
				softas.assertTrue(isLinkDisplayed, jiraNumber + ":" + issueSummary
						+ " : Validated the Lista De Documentos in the right Panel in Home page");
				logExtentStatus(extentLogger, isLinkDisplayed,
						" Validated the Lista De Documentos in the right Panel in Home page", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// This test case is to validate the list of the documnets button in Home
	// page(Left Panel)
	// <Created Date : 29-Oct-2018 > ; <author : Saikiran>

	@Test(priority = 28, groups = { "chpmex" }, description = "MAFQABANG-697")
	public void validatingListaDeDocumentosLeftPanelInHomePage() throws Exception {

		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			boolean searchResultsDisplayed = false;
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Indicators validated in Home Page");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String free_wordkey = "freeword";
				String free_wordval = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, free_wordkey,
						extentLogger);
				String suggestion = "suggestion_search";
				String suggestion_word = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, suggestion,
						extentLogger);

				homepage.openHomepage();
				Thread.sleep(2000);
				homepage.enterTextInSearchField(free_wordval);
				homepage.selectingSuggestionsDropdownDisplayed(suggestion_word);
				Thread.sleep(2000);
				searchresultpage = homepage.clickSearch();
				searchresultpage.clickOnVerCoincidenciasLink(1);
				docdisplaypage = searchresultpage.clickUnitAndRubric();
				docdisplaypage.clickOnListOfTheDocumentsInLeftPanel();
				Thread.sleep(2000);
				boolean isLinkDisplayed = docdisplaypage.isVerCoincidenciasLinkDisplayed();
				softas.assertTrue(isLinkDisplayed, jiraNumber + ":" + issueSummary
						+ " : Validated the Lista De Documentos in the left Panel in Home page");
				logExtentStatus(extentLogger, isLinkDisplayed,
						" Validated the Lista De Documentos in the left Panel in Home page", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description:Thesaurus suggestion in the "Búsqueda Global" search suggestions
	// in home page
	// created on 24/10/2018 author< roja>

	@Test(priority = 01, groups = { "chpmex" }, description = "MAFQABANG-669")
	public void verifyThesaurussuggestions() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Thesaurus suggestion in the Búsqueda Global search suggestions  in home page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();

				homepage.enterTextInSearchField(searchword);
				boolean isSuggestionDisplayed = homepage.isTheSuggestionsDropdownDisplayed();

				softas.assertTrue(isSuggestionDisplayed,
						"Verifying the suggestion are displyed in home page" + ":" + jiraNumber);
				logExtentStatus(extentLogger, isSuggestionDisplayed, ": suggestion is displayed", jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description:Verify the number of results displayed for the "Ver
	// coincidencias" link
	// <Created Date : 26-Oct-2018 > ; <author : Roja>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-729")
	public void VerifyTheNumberOfResultsInHome() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Verify the number of results displayed for the Ver coincidencias link");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);
				homepage.openHomepage();

				homepage.enterTextInSearchField(searchword);
				homepage.selectingSuggestionsDropdownDisplayed("ley aduanera");

				searchresultpage = homepage.clickSearch();

				searchresultpage.clickOnVerCoincidenciasLink(2);
				Thread.sleep(3000);
				boolean IsVerMasLinkDisplayed = searchresultpage.isVerMasLinkDisplayed();

				softas.assertTrue(IsVerMasLinkDisplayed,
						"Verify the number of results displayed for the Ver coincidencias link" + ":" + jiraNumber);
				logExtentStatus(extentLogger, IsVerMasLinkDisplayed,
						": Verify the number of results displayed for the Ver coincidencias link", jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description: Verify whether VerCoincidencias link is displayed(Home Page)
	// <Created Date : 29-Oct-2018 > ; <author : roja>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-722")
	public void isVerCoincidenciasLinkDisplayedInHome() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Verify whether VerCoincidencias link is displayed(Home page)");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();
				homepage.enterTextInSearchField(searchword);
				homepage.selectingSuggestionsDropdownDisplayed("actualización de la ");

				searchresultpage = homepage.clickSearch();
				boolean IsVerCoincidenciasLink = searchresultpage.isVerCoincidenciasLinkDisplayed(1);

				softas.assertTrue(IsVerCoincidenciasLink,
						"Verify whether VerCoincidencias link is displayed(Home page)" + ":" + jiraNumber);
				logExtentStatus(extentLogger, IsVerCoincidenciasLink,
						":Verify whether VerCoincidencias link is displayed(Home page)", jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description:Verify the unit+rubric link(Home Page)
	// <Created Date : 29-Oct-2018 > ; <author : Roja>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-726")
	public void verifyUnitrubricLinkInHome() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verify the unit+rubric link");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();

				homepage.enterTextInSearchField(searchword);
				homepage.selectingSuggestionsDropdownDisplayed("actualización de la ");

				searchresultpage = homepage.clickSearch();

				searchresultpage.clickOnVerCoincidenciasLink(1);

				boolean IsUnitRubricLinkDisplayed = searchresultpage.isUnitRubricLinkDisplayed();

				softas.assertTrue(IsUnitRubricLinkDisplayed, "Verify the unit+rubric link" + ":" + jiraNumber);
				logExtentStatus(extentLogger, IsUnitRubricLinkDisplayed, ":Verify the unit+rubric link", jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Description: verify whether unit+rubric is a link
	// <Created Date : 30-Oct-2018 > ; <author : roja>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-732")
	public void verifyUnitandRubricLink() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "verify whether unit+rubric is a link");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();
				homepage.enterTextInSearchField(searchword);
				homepage.selectingSuggestionsDropdownDisplayed("actualización de la ");
			
				searchresultpage = homepage.clickSearch();
				  Thread.sleep(5000);
				searchresultpage.clickOnVerCoincidenciasLink(1);
				boolean IsUnitRubricLinkDisplayed = searchresultpage.isUnitRubricLinkDisplayed();
				searchresultpage.clickOnUnitAndRubricLink("A.121. Deducciones autorizadas");

				softas.assertTrue(IsUnitRubricLinkDisplayed,issueSummary+ ":" + jiraNumber);
				logExtentStatus(extentLogger, IsUnitRubricLinkDisplayed, ":verify whether unit+rubric is a link",
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

	// Search by thesaurus suggestion in the "Búsqueda Global" search in home page
	// <Created Date : 26-Oct-2018 > ; <author : roja>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-670")
	public void searchByThesaurusSsuggestion() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Search by  thesaurus suggestion in the Búsqueda Global search in home page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();
				homepage.enterTextInSearchField(searchword);
				homepage.selectingSuggestionsDropdownDisplayed("actualización de la ");

				searchresultpage = homepage.clickSearch();

				docdisplaypage = searchresultpage.getFirstDocument();
				boolean isdocumentdisplayed = docdisplaypage.isDocumentDisplayed();

				softas.assertTrue(isdocumentdisplayed,
						"Search by  thesaurus suggestion in the Búsqueda Global search in home page" + ":"
								+ jiraNumber);
				logExtentStatus(extentLogger, isdocumentdisplayed,
						":Search by  thesaurus suggestion in the Búsqueda Global search in home page", jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// Search by non - thesaurus suggestion(free word) in the "Búsqueda Global"
	// search in home page
	// <Created Date : 26-Oct-2018 > ; <author : roja>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-671")
	public void SearchByNonThesaurusSuggestion() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {

			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Search by non - thesaurus suggestion(free word) in the Búsqueda Global search in home page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();
				homepage.enterTextInSearchField(searchword);

				searchresultpage = homepage.clickSearch();

				docdisplaypage = searchresultpage.getFirstDocument();
				boolean isdocumentdisplayed = docdisplaypage.isDocumentDisplayed();

				softas.assertTrue(isdocumentdisplayed,
						"Search by non - thesaurus suggestion(free word) in the Búsqueda Global search in home page"
								+ ":" + jiraNumber);
				logExtentStatus(extentLogger, isdocumentdisplayed,
						":Search by non - thesaurus suggestion(free word) in the Búsqueda Global search in home page",
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

	// Search by keyword in the "Búsqueda Global" search without suggessions in home
	// page
	// <Created Date : 26-Oct-2018 > ; <author : roja>

	@Test(priority = 02, groups = { "chpmex" }, description = "MAFQABANG-672")
	public void SearchByKeyword() throws Exception {
		SoftAssert softas = new SoftAssert();
		HomePage homepage = this.homepage;
		try {
                
					testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
			homepage.openHomepage();

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,
					"Search by keyword in the Búsqueda Global search without suggessions  in home page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "searchKeyword",
						extentLogger);

				homepage.openHomepage();
				homepage.enterTextInSearchField(searchword);

				searchresultpage = homepage.clickSearch();

				docdisplaypage = searchresultpage.getFirstDocument();
				boolean isdocumentdisplayed = docdisplaypage.isDocumentDisplayed();

				softas.assertTrue(isdocumentdisplayed,
						"Search by keyword in the Búsqueda Global search without suggessions  in home page" + ":"
								+ jiraNumber);
				logExtentStatus(extentLogger, isdocumentdisplayed,
						":Search by keyword in the Búsqueda Global search without suggessions  in home page",
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

				
				
				
           
		// Verify the Error message when user search with empty search box.
	// <Created Date : 31-Oct-2018 > ; <author : roja>
						
						@Test(priority = 02,groups = { "chpmex" }, description = "MAFQABANG-676")
						public void verifyErrorMsgInHome() throws Exception {
							SoftAssert softas = new SoftAssert();
							HomePage homepage = this.homepage;
							try {
							
								testResult = Reporter.getCurrentTestResult();

								extentLogger = setUpExtentTest(extentLogger, "HomePage", testResult.getMethod().getMethodName());
							

								String jiraNumber = testResult.getMethod().getDescription();

								String issueSummary = getIssueTitle(jiraNumber, "Verify the Error message when user search with empty search box.");

								JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

								for (Object searchString : listOfSearchData) {

									JSONObject jsonObjectChild = (JSONObject) searchString;

									String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
											"searchKeyword", extentLogger);
									


								homepage.openHomepage();
							//	homepage.enterTextInSearchField(searchword);
								

								searchresultpage = homepage.clickSearch();
							   boolean	IsMsgDisplayed=searchresultpage.isErrorMessageDisplayedHomePage();				
								
								 softas.assertTrue(IsMsgDisplayed,
										"Verify the Error message when user search with empty search box in homepage" + ":" + jiraNumber);
								logExtentStatus(extentLogger, IsMsgDisplayed, ":Verify the Error message when user search with empty search box in homepage", jiraNumber);
								}
							} catch (Exception exc) {
								logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

								softas.assertTrue(false, "Exception in Test");
							} finally {
								extentReports.endTest(extentLogger);
								softas.assertAll();
							}
						}
						
						//Verify the number of results displayed for each doc type in result page.
						 //<Created Date :  02-Nov-2018 >   ; <author : Kavitha> 

						@Test(priority = 02,groups = { "chpmex" }, description = "MAFQABANG")
						public void verifyResultCountForEachDocTypeInHome() throws Exception {
							SoftAssert softas = new SoftAssert();
							HomePage homepage = this.homepage;
							try {
								testResult = Reporter.getCurrentTestResult();

								extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());

								String jiraNumber = testResult.getMethod().getDescription();

								//String issueSummary = getIssueTitle(jiraNumber, "Verify the Error message when user search with empty search box");

								JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

								for (Object searchString : listOfSearchData) {

									JSONObject jsonObjectChild = (JSONObject) searchString;

									String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
											"searchKeyword", extentLogger);
									
									String issueSummary = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
											"Summary", extentLogger);

								homepage.openHomepage();
								
								homepage.enterTextInSearchField(searchword);
								

								searchresultpage = homepage.clickSearch();
								boolean isCountValid = searchresultpage.DocTypeResultCountInSearch();					
								
								
							     softas.assertTrue(isCountValid,issueSummary+":"+jiraNumber);
								logExtentStatus(extentLogger, isCountValid, "verifyResultCountForEachDocTypeInHome", jiraNumber);
								}
							} catch (Exception exc) {
								logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

								softas.assertTrue(false, "Exception in Test");
							} finally {
								extentReports.endTest(extentLogger);
								softas.assertAll();
							}
						}
						
						//Verify the Error message when user search with empty search box.
						 //<Created Date :  02-Nov-2018 >   ; <author : Kavitha> 
						@Test(priority = 02,groups = { "chpmex" }, description = "MAFQABANG-676")
						public void verifyErrorMsgForEmptySearchInHome() throws Exception {
							SoftAssert softas = new SoftAssert();
							HomePage homepage = this.homepage;
							try {
								testResult = Reporter.getCurrentTestResult();

								extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());

								String jiraNumber = testResult.getMethod().getDescription();

								String issueSummary = "Verify the Error message when user search with empty search box";
								
								homepage.openHomepage();
								homepage.clickSearch();
								
								
								 boolean IsMsgDisplayed=homepage.isEmptySearchErrorMsgDisplayed();					
								
								
							     softas.assertTrue(IsMsgDisplayed,
							    		 issueSummary + ":" + jiraNumber);
								logExtentStatus(extentLogger, IsMsgDisplayed, "verifyErrorMsgForEmptySearchInHome", jiraNumber);
								
							} catch (Exception exc) {
								logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

								softas.assertTrue(false, "Exception in Test");
							} finally {
								extentReports.endTest(extentLogger);
								softas.assertAll();
							}
						}
						
						//Verify whether the document snippet is displayed for documents with no units+rubric in result page.
						 //<Created Date :  02-Nov-2018 >   ; <author : Kavitha> 
						@Test(priority = 02,groups = { "chpmex" }, description = "MAFQABANG-725")
						public void verifyDocumentSnippetIsDisplayedInHome() throws Exception {
							SoftAssert softas = new SoftAssert();
							HomePage homepage = this.homepage;
							try {
								testResult = Reporter.getCurrentTestResult();

								extentLogger = setUpExtentTest(extentLogger, "SearchPage", testResult.getMethod().getMethodName());
								//homepage.openHomepage();

								String jiraNumber = testResult.getMethod().getDescription();

								//String issueSummary = getIssueTitle(jiraNumber, "Verify the Error message when user search with empty search box");

								JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

								for (Object searchString : listOfSearchData) {

									JSONObject jsonObjectChild = (JSONObject) searchString;

									String searchword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
											"searchKeyword", extentLogger);
									
									String issueSummary = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
											"Summary", extentLogger);

									homepage.openHomepage();
									homepage.enterTextInSearchField(searchword);
								

								searchresultpage = homepage.clickSearch();
								boolean isDisplayed = searchresultpage.isDocumentSnippetDisplayed();					
								
								
							     softas.assertTrue(isDisplayed,issueSummary+":"+jiraNumber);
								logExtentStatus(extentLogger, isDisplayed, ":verifyDocumentSnippetIsDisplayedInHome", jiraNumber);
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
