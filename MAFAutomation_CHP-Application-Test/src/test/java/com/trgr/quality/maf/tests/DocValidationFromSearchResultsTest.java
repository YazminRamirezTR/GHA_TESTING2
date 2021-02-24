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
import com.trgr.quality.maf.pages.ContenttreeOnsearchResultPage;
import com.trgr.quality.maf.pages.DoctrinePage;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.FormsPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LegislationPage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class DocValidationFromSearchResultsTest extends BaseTest {
	LoginPage loginpage;
	HomePage homepage;
	SearchPage searchpage;
	SearchResultsPage searchResultsPage;
	DoctrinePage doctrinepage;
	LegislationPage legislationPage;
	ContenttreeOnsearchResultPage contenttreeOnsearchResultPage;
	JSONObject jsonObject;
	JsonReader jsonReader;
	DocumentDisplayPage documentPage;
	public ITestResult testResult;
	JiraConnector jiraConnect;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		try {
			loginpage = new LoginPage(driver, ProductUrl);

			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");

			homepage = loginpage.Login(username, password);
			jsonReader = new JsonReader();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Search", "DocValidationFromSearchResultsTest");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the DocValidationFromSearchResultsTest are not run.<br>"
							+ takesScreenshot_Embedded()+ "<br>" + displayErrorMessage(exc)); 
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
	
	@Test(priority=0,groups = {"chpmex"},description="MAFAUTO-207")
	public void improvedRankingForDoctrineSearch() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		SoftAssert softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Doctrine - Result List - Improve ranking feature"); 

		JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
		try {
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey="freeword";
				String freeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);
				String validateword=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"validateword",extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();
				doctrinepage.enterFreeWordOnSearchPage(freeword);
				searchResultsPage = doctrinepage.clickOnSearch();
				String docTitle = searchResultsPage.getFirstDocumentTitle();
				boolean rankingValidated =  (docTitle!=null) &&	docTitle.toUpperCase().contains(validateword.toUpperCase());
				softas.assertTrue(rankingValidated, jiraNumber+":Search key present in the first document title");
				logExtentStatus(extentLogger, rankingValidated, issueSummary+": Search key present in the first document title", 
						freewordKey,freeword,jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}
	
	
	@Test(priority=0,groups = {"chpmex"},description="MAFAUTO-201")
	public void improvedRankingForLegislationSearch() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		SoftAssert softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Doctrine - Result List - Improve ranking feature"); 

		JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
		try {
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey="freeword";
				String freeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				legislationPage = searchpage.OpenLegislationPage();
				legislationPage.enterFreeWordOnSearchPage(freeword);
				searchResultsPage = legislationPage.clickOnSearch();
				String docTitle = searchResultsPage.getFirstDocumentTitle();
				boolean rankingValidated =  (docTitle!=null) &&	docTitle.toUpperCase().contains(freeword.toUpperCase());
				softas.assertTrue(rankingValidated, jiraNumber+":Search key present in the first document title");
				logExtentStatus(extentLogger, rankingValidated, issueSummary+": Search key present in the first document title", 
						freewordKey,freeword,jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority = 1, groups = {"chpmex"},description="MAFAUTO-213")
	public void OfficialFormsResultList() throws Exception {
		SoftAssert softas = new SoftAssert();
		try{
			testResult = Reporter.getCurrentTestResult();

			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Official Forms - Result List - Displayed");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey="freeword";
				String freeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				FormsPage formspage = searchpage.openFormsPage();
				formspage.enterFreeWordOnSearchPage(freeword);
				searchResultsPage = formspage.clickOnSearch();

				boolean searchResultsDisplayed = searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage.searchReturnedResultsAsExpected(freeword);
				softas.assertTrue(searchResultsDisplayed, issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary,freewordKey,freeword,jiraNumber);

				boolean formsTitleDisplayed = searchResultsPage.isTableOfContentsLinkNotVisible()&&
						searchResultsPage.isFormstitleDisplayed();
				softas.assertTrue(formsTitleDisplayed, "Official Forms - Result List - Remove “ARBOL DE CONTENIDOS” view");
				logExtentStatus(extentLogger, formsTitleDisplayed, "Official Forms - Result List - Remove “ARBOL DE CONTENIDOS” view",jiraNumber);

			}
		}catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*@Test(priority=2,groups = {"chpmex"},description = "MAFAUTO-107")
	public void LegislationSearchFilters() throws Exception {
		SoftAssert softas = new SoftAssert();	
		LegislationPage legislationpage;

		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			//String issueSummary = getIssueTitle(jiraNumber,"Legislation - Result List - Filters");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				// MAFAUTO-107 : Filter by Validation : Validates for multiple filter widgets
				// Read data
				
				String freewordSearchText =  jsonReader.readKeyValueFromJsonObject(jsonObjectChild,"freeword",extentLogger);
				String scopeKey = "scope"; 
				String filterScope = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,scopeKey,extentLogger);
				
				String thematicAreaKey = "thematicarea";
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,thematicAreaKey,extentLogger);
				
				String legislationKey = "legislation";
				String legislation = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,legislationKey,extentLogger);
				
				String sortTypeKey = "type";
				String sortType = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,sortTypeKey,extentLogger);
				
				String sortYearKey = "year";
				String sortYear = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,sortYearKey,extentLogger);
				
				String sortSubjectKey = "subject";
				String sortSubject = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,sortSubjectKey,extentLogger);
				
				String sortOrganoemisorKey = "Organoemisor";
				String sortOrgano = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,sortOrganoemisorKey,extentLogger);
				// Perform Search
				searchpage = homepage.OpenSearchPage();
			
				searchpage.selectGivenValueFromThematicDropdown(thematicArea);
				legislationpage = searchpage.OpenLegislationPage();
				Thread.sleep(2000);
				legislationpage.enterFreeWordOnSearchPage(freewordSearchText);
				Thread.sleep(1000);
				searchResultsPage = legislationpage.clickOnSearch();
				
				// Validate Filter By Scope Widget
				searchResultsPage.scrollIntoViewFilterByScopeWidget();
				if (searchResultsPage.isFilterByScopeWidgetViewCollapsed())
					searchResultsPage.expandFilterByScopeWidgetView();
				// Filter by Scope - click given value in widget
				searchResultsPage.clickValueInFilterByScopeWidget(filterScope);
				// View Filter By Scope Widget & verify Filter is
				// applied
				searchResultsPage.scrollIntoViewAppliedFilterWidget();
				boolean searchResultsDisplayed = searchResultsPage.isFilteredByValue(filterScope);
				// View Applied filter widget & verify clicking view
				// all
				searchResultsPage.scrollIntoViewAppliedFilterWidget();
				searchResultsDisplayed = searchResultsDisplayed && searchResultsPage
						.clcikSecondLevelLinkInAppliedFilterWidget(legislation, filterScope);

				softas.assertTrue(searchResultsDisplayed,
						jiraNumber+" : Filter By Scope & View All link validated for Legislation search. :"
								+ freewordSearchText);
				logExtentStatus(extentLogger, searchResultsDisplayed, "Filter By Scope & View All link validated for Legislation search",
						scopeKey,filterScope,jiraNumber);

				// Validate Filter By SortType Widget
				searchResultsPage.scrollIntoViewFilterBySortTypeWidget();
				if (searchResultsPage.isFilterBySortTypeWidgetViewCollapsed())
					searchResultsPage.expandFilterBySortTypeWidgetView();
				// Filter by SortType - click given value in widget
				searchResultsPage.clickValueInFilterBySortTypeWidget(sortType);
				// View Filter By SortType Widget & verify Filter is
				// applied
				searchResultsPage.scrollIntoViewAppliedFilterWidget();
				searchResultsDisplayed = searchResultsPage.isFilteredByValue(sortType);
				// View Applied filter widget & verify clicking view
				// all
				searchResultsPage.scrollIntoViewAppliedFilterWidget();
				searchResultsDisplayed = searchResultsDisplayed && searchResultsPage
						.clcikSecondLevelLinkInAppliedFilterWidget(legislation, sortType);
				softas.assertTrue(searchResultsDisplayed,
						jiraNumber+" : Filter By SortType & View All link validated for Legislation search. :"
								+ freewordSearchText);
				logExtentStatus(extentLogger, searchResultsDisplayed, "Filter By SortType & View All link validated for Legislation search", 
						sortTypeKey,sortType,jiraNumber);

				// Validate Filter By Year Widget
				searchResultsPage.scrollIntoViewFilterByYearWidget();
				if (searchResultsPage.isFilterByYearWidgetViewCollapsed())
					searchResultsPage.expandFilterByYearWidgetView();

				// Filter by Year - click given value in widget
				searchResultsPage.clickValueInFilterByYearWidget(sortYear);

				// View Filter By Year Widget & verify Filter is applied
				searchResultsPage.scrollIntoViewAppliedFilterWidget();

				searchResultsDisplayed = searchResultsPage.isFilteredByValue(sortYear);

				// View Applied filter widget & verify clicking view all

				searchResultsPage.scrollIntoViewAppliedFilterWidget();
			searchResultsDisplayed = searchResultsDisplayed && searchResultsPage
					.clcikSecondLevelLinkInAppliedFilterWidget(legislation, sortYear);
			softas.assertTrue(searchResultsDisplayed,
						jiraNumber+" : Filter By Year & View All link validated for Legislation search. :"
								+ freewordSearchText);
				logExtentStatus(extentLogger, searchResultsDisplayed, "Filter By Year & View All link validated for Legislation search", 
						sortYearKey,sortYear,jiraNumber);
					Thread.sleep(4000);
				// Validate Filter By SubjectArea Widget
				searchResultsPage.scrollIntoViewFilterByAreaWidget();
				if (searchResultsPage.isFilterByAreaWidgetViewCollapsed())
					searchResultsPage.expandFilterByAreaWidgetView();
				// Filter by SubjectArea - click given value in widget
				searchResultsPage.clickValueInFilterByAreaWidget(sortSubject);
				// View Filter By SubjectArea Widget & verify Filter is applied
				searchResultsPage.scrollIntoViewAppliedFilterWidget();
				searchResultsDisplayed = searchResultsPage.isFilteredByValue(sortSubject);
				// View Applied filter widget & verify clicking view all
				searchResultsPage.scrollIntoViewAppliedFilterWidget();
				searchResultsDisplayed = searchResultsDisplayed && searchResultsPage
						.clcikSecondLevelLinkInAppliedFilterWidget(legislation, sortSubject);
				softas.assertTrue(searchResultsDisplayed,
						jiraNumber+" : Filter By SubjectArea & View All link validated for Legislation search. :"
								+ freewordSearchText);
				logExtentStatus(extentLogger, searchResultsDisplayed, "Filter By SubjectArea & View All link validated for Legislation search", 
						sortSubjectKey,sortSubject,jiraNumber);
				searchResultsPage.scrollIntoViewFilterByAreaWidget();
				if (searchResultsPage.isFilterByAreaWidgetViewCollapsed())
					searchResultsPage.expandFilterByAreaWidgetView();
				searchResultsPage.clickTextInFilterBySortOrgano(sortOrgano);
				softas.assertTrue(searchResultsDisplayed,
						jiraNumber+" : Filter By SubjectArea & View All link validated for Legislation search. :"
								+ freewordSearchText);
				logExtentStatus(extentLogger, searchResultsDisplayed, "Filter By SubjectArea & View All link validated for Legislation search", 
						sortOrganoemisorKey,sortOrgano,jiraNumber);
				
			
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}*/


	//@Test(priority=2,groups = {"chparg", "chpmex", "chpbr" },description = "MAFQABANG-61")
	public void AdvancedSearchLegislation() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		SoftAssert softas = new SoftAssert();	
		extentLogger = setUpExtentTest(extentLogger, "Search", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		//String issueSummary = getIssueTitle(jiraNumber,"Advanced Search Legislation"); 

		LegislationPage legislationpage;

		try {
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				searchpage = homepage.OpenSearchPage();
				legislationpage = searchpage.OpenLegislationPage();

				JSONArray listOfFreeword = (JSONArray) jsonObject.get("freeword");
				legislationpage.selectTypeOfOrder(searchString.toString().split("-")[0]);
				legislationpage.enterFreeWordOnSearchPage(listOfFreeword.get(1).toString());
				searchResultsPage = legislationpage.clickOnSearch();
				boolean searchResultsDisplayed = (searchResultsPage!=null)
						&& searchResultsPage.searchResultsHeaderContainerDisplayed()
						&& searchResultsPage
						.searchReturnedResultsAsExpected(listOfFreeword.get(1).toString());

				softas.assertTrue(searchResultsDisplayed, "MAFQABANG-61 : Legislation search with freeword");
				logExtentStatus(extentLogger, searchResultsDisplayed, "Legislation search with freeword",jiraNumber);

				DocumentDisplayPage documentPage = searchResultsPage.clickFirstLink();
				if (documentPage.isLastReformOptionDisplayed()) {
					documentPage.ClickOnLastReformsOnDocument();
					searchResultsDisplayed = documentPage.isFirstItemOnRelatedContentTabALink();

					softas.assertFalse(searchResultsDisplayed,
							"MAFQABANG-61 : First link on Related Content is not a link");
					logExtentStatus(extentLogger, searchResultsDisplayed, "First link on Related Content is not a link", jiraNumber);

					searchResultsDisplayed = documentPage.isSecondItemOnRelatedContentTabALink();

					softas.assertTrue(searchResultsDisplayed,
							"MAFQABANG-61 : Second link on Related Content is not a link");
					logExtentStatus(extentLogger, !searchResultsDisplayed, "Second link on Related Content is not a link",jiraNumber);

					documentPage.ClickSecondLinkOnRelatedTab();

					boolean redFlagDisplayed = documentPage.isDocumentTitleHasRedColoredFlag();
					softas.assertTrue(redFlagDisplayed, "MAFQABANG-61 : Link On the Related Tab has Red Flag");
					logExtentStatus(extentLogger, redFlagDisplayed, "Link On the Related Tab has Red Flag", jiraNumber);
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
