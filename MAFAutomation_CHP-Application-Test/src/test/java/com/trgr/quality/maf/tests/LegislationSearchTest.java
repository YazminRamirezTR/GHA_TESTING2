
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
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LegislationPage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class LegislationSearchTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage, homepagecopy;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	JSONObject jsonObject;
	LegislationPage legislationpage;
	DocumentDisplayPage documentPage;
	JsonReader jsonReader;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		try {
			loginpage = new LoginPage(driver, ProductUrl);

			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");

			homepage = loginpage.Login(username, password);
			jsonReader = new JsonReader();
		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Search", "StartSearchTest");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the Search test are not run.<br>"
							+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
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

	/*
	 * This test verifies Legislation search for No Results
	 */
	@Test(priority = 1, groups = { "chpmex", "chparg", "chpbr", "chpury", "chppe",
			"chpchile" }, description = "MAFQABANG-63")
	public void searchLegislationForNoResultsMessage() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "LegislationSearchTest",
					testResult.getMethod().getMethodName());
			boolean zeroresultsdiaplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify the zero results for AdvanceSearch Legislation");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String zeroresultkeywordKey = "zeroresultkeyword";
				String zeroresultkeywordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						zeroresultkeywordKey, extentLogger);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(4000);
				legislationpage = searchpage.OpenLegislationPage();
				Thread.sleep(2000);
				legislationpage.enterFreeWordOnlegilationPage(zeroresultkeywordVal);
				legislationpage.clickOnSearch();
				zeroresultsdiaplayed = legislationpage.noResultsDisplayed();

				softAssert.assertTrue(zeroresultsdiaplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, zeroresultsdiaplayed, issueSummary, zeroresultkeywordKey,
						zeroresultkeywordVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	/*
	 * This test verifies legislation search with Thematic and Freeword
	 */
	@Test(priority = 2, groups = { "chparg", "chpmex", "chpury", "chppe","chpchile" }, description = "MAFQABANG-61")
	public void searchLegislationUsingThematicAndFreeword() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "LegislationSearchTest",
					testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify advance search  for Legislation with Thematic Keyword and Freeword");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String thematicareaKey = "thematicarea";
				String thematicareaVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicareaKey,
						extentLogger);
				String thematicsearchKey = "thematicsearch";
				String thematicsearchVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, thematicsearchKey,
						extentLogger);
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);

				searchpage = homepage.OpenSearchPage();

				searchpage.selectGivenValueFromThematicDropdown(thematicareaVal);
				if (searchpage.isAreaFoundInContentTree(thematicareaVal)) {
					searchpage.selectAreaFromContentTree(thematicareaVal);
				}

				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.enterThematicTextOnlegilationPage(thematicsearchVal);
				boolean issuggestiondisplayed = legislationpage.isTheSuggestionsDropdownDisplayed();
				if (issuggestiondisplayed) {
					legislationpage.selectTitleInSearchSuggestions(thematicsearchVal);
				}
				
				legislationpage.enterFreeWordOnlegilationPage(freewordVal);
				searchResultsPage = legislationpage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", freewordKey,
								freewordVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean istitledispalyed = searchResultsPage.isFilterByIndexDispalyed();
				softAssert.assertTrue(istitledispalyed && searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, istitledispalyed && searchResultsDisplayed, issueSummary, freewordKey,
						freewordVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 3, groups = { "chparg" }, description = "MAFQABANG-470")
	public void searchLegislationUsingTypeofStandardAmbitMunicipality() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "LegislationSearchTest",
					testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify advance search  for Legislation with Type of Standard,ambit and Municipality");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String typeofStandardkey = "typeofStandard";
				String typeofstandardVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, typeofStandardkey,
						extentLogger);
				String ambitkey = "ambit";
				String ambitVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, ambitkey, extentLogger);
				String muncipalitykey = "muncipality";
				String municipalVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, muncipalitykey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();

				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.selectTypeOfStandard(typeofstandardVal);
				legislationpage.selectAmbit(ambitVal);
				legislationpage.selectMuncipio(municipalVal);
				searchResultsPage = legislationpage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								typeofStandardkey + "," + ambitkey + "," + muncipalitykey,
								typeofstandardVal + "," + ambitVal + "," + municipalVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean istitledispalyed = searchResultsPage.isFilterByIndexDispalyed();
				softAssert.assertTrue(istitledispalyed && searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, istitledispalyed && searchResultsDisplayed, issueSummary,
						typeofStandardkey + "," + ambitkey + "," + muncipalitykey,
						typeofstandardVal + "," + ambitVal + "," + municipalVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 4, groups = { "chpmex" }, description = "MAFQABANG-471")
	public void searchLegislationUsingTypeofOrderAmbit() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "LegislationSearchTest",
					testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify advance search  for Legislation with Type of Order and ambit");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String typeoforderkey = "typeoforder";
				String typeoforderVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, typeoforderkey,
						extentLogger);
				String ambitkey = "ambit";
				String ambitVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, ambitkey, extentLogger);

				searchpage = homepage.OpenSearchPage();

				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.selectTypeOfOrder(typeoforderVal);
				
				legislationpage.selectAmbit(ambitVal);
				searchResultsPage = legislationpage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, issueSummary, typeoforderkey + "," + ambitkey,
								typeoforderVal + "," + ambitVal + " -resulted in no search results", jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean istitledispalyed = searchResultsPage.isFilterByIndexDispalyed();
				softAssert.assertTrue(istitledispalyed && searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary, typeoforderkey + "," + ambitkey,
						typeoforderVal + "," + ambitVal, jiraNumber);

			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	// Test data not working as of now
	@Test(priority = 5, groups = { "chpbr" }, description = "MAFQABANG-472")
	public void searchLegislationUsingEditedByOrgan() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "LegislationSearchTest",
					testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify advance search  for Legislation with Edited By Organ");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String editedbyorgankey = "editedbyorgan";
				String editedbyorganvalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, editedbyorgankey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();

				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.enterEditedByOrgan(editedbyorganvalue);

				searchResultsPage = legislationpage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", editedbyorgankey,
								editedbyorganvalue, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean istitledispalyed = searchResultsPage.isFilterByIndexDispalyed();
				softAssert.assertTrue(istitledispalyed && searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, istitledispalyed && searchResultsDisplayed, issueSummary,
						editedbyorgankey, editedbyorganvalue, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 6, groups = { "chpury", "chppe","chpchile" }, description = "MAFQABANG-62")
	public void searchLegislationUsingStandardNum() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "LegislationSearchTest",
					testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify advance search  for Legislation with standard number");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String stdnumberkey = "standardnum";
				String stdnumberVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, stdnumberkey,
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				legislationpage = searchpage.OpenLegislationPage();
				legislationpage.enterStandardNumber(stdnumberVal);

				searchResultsPage = legislationpage.clickOnSearch();
				Thread.sleep(4000);
				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results", stdnumberkey,
								stdnumberVal, jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				boolean istitledispalyed = searchResultsPage.isFilterByIndexDispalyed();
				softAssert.assertTrue(istitledispalyed && searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, istitledispalyed && searchResultsDisplayed, issueSummary, stdnumberkey,
						stdnumberVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 7, groups = { "chpmex" }, description = "MAFAUTO-212")
	public void verifyFlagAssociatedwithVersionForLegislationDocs() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "LegislationSearchTest",
					testResult.getMethod().getMethodName());
			boolean searchResultsDisplayed = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Verify Flags associated with versions for Legislation Documents");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordkey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordkey, extentLogger);
				String valuewithoutlinkkey = "valuewithoutlink";
				String valuewithoutlink = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, valuewithoutlinkkey,
						extentLogger);
				String valuewithlinkkey = "valuewithlink";
				String valuewithlink = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, valuewithlinkkey,
						extentLogger);
				
				String Docname =jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "Document",
						extentLogger);

				searchpage = homepage.OpenSearchPage();
				Thread.sleep(4000);
				legislationpage = searchpage.OpenLegislationPage();
				Thread.sleep(4000);
				legislationpage.enterFreeWordOnlegilationPage(freewordVal);
				searchResultsPage = legislationpage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed();
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, issueSummary, freewordkey,
								freewordVal + " -resulted in no search results", jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}
				documentPage=searchResultsPage.isPresentResultList();
				Thread.sleep(5000);
				documentPage.clickExpectedLinkFromResultsPage(Docname);
				//this particular document is taking long time and trying to watch for any element is not returning
				//success consistently so trying the sleep method to watch the next one week results.
				Thread.sleep(9000);
				documentPage.clickLatestReforms();
				Thread.sleep(2000);
				boolean iscurverwithoutlink = documentPage.validateUltimateReformWithoutLink(valuewithoutlink, 0);
				Thread.sleep(2000);
				boolean isprevverwithlink = documentPage.validateUltimateReformWithLink(valuewithlink, 1);
				Thread.sleep(5000);
				if (isprevverwithlink) {
					documentPage.clickonReformLink(valuewithlink);
				}
				Thread.sleep(2000);
				boolean colorvalidated = documentPage.validateColor();

				softAssert.assertTrue(
						colorvalidated && searchResultsDisplayed && iscurverwithoutlink && isprevverwithlink,
						jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger,
						colorvalidated && searchResultsDisplayed && iscurverwithoutlink && isprevverwithlink,
						issueSummary, freewordkey, freewordVal, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 8, groups = { "chpmex" }, description = "MAFAUTO-151")
	public void searchLegislationUsingSortTypeAndDisposition() throws Exception {
		softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "LegislationSearchTest",
					testResult.getMethod().getMethodName());

			boolean searchResultsDisplayed = false;
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Advance Search Legislation with SortType and Disposition");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String sorttypeKey = "typeoforder";
				String sorttypeVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, sorttypeKey, extentLogger);
				String dispositionKey = "typeoforder";
				String dispositionVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, dispositionKey,
						extentLogger);

				homepage = homepage.openHomepage();
				legislationpage = homepage.OpenLegislationPage();
				legislationpage.selectTypeOfOrder(sorttypeVal);
				legislationpage.enterDispositionDetails(dispositionVal);

				searchResultsPage = legislationpage.clickOnSearch();

				searchResultsDisplayed = searchResultsPage != null
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(sorttypeVal);
				if (!searchResultsDisplayed) {
					boolean noResultsFound = homepage.errorBlockDisplayed() || homepage.noSearchResultsDisplayed();
					if (noResultsFound)
						logExtentNoResultsAsInfo(extentLogger, " -resulted in no search results",
								"Tipo de Ordenamiento" + sorttypeVal, "and  Nombre de la Disposición" + dispositionVal,
								jiraNumber);
					else {
						// adding this else part for the error page we are
						// getting when application is down
						logExtentStatus(extentLogger, noResultsFound, "Search failed :", jiraNumber);
						softAssert.assertTrue(noResultsFound, jiraNumber + ":Search failed :");
					}

					continue; // Skip this & Continue with next iteration
				}

				softAssert.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed,
						"Tipo de Ordenamiento" + sorttypeVal + "and  Nombre de la Disposición" + dispositionVal,
						jiraNumber);

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
