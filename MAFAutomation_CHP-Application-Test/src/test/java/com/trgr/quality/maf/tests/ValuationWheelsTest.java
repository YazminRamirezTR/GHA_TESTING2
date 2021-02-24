package com.trgr.quality.maf.tests;

import org.json.simple.JSONObject;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
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
import com.trgr.quality.maf.pages.ActionsPage;
import com.trgr.quality.maf.pages.DeliveryPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchResultsPage;
import com.trgr.quality.maf.pages.ToolsPage;
import com.trgr.quality.maf.pages.ValuationWheelsPage;

public class ValuationWheelsTest extends BaseTest {
	/*
	 * This Test verifies the presence of News Tab It validates the error
	 * message when the data provided does not correspond to any results It
	 * validates the result list for appropriate data It validates the clear
	 * button functionality
	 */

	LoginPage loginpage;
	HomePage homepage;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	ToolsPage toolsPage;
	ActionsPage actionsPage;
	ValuationWheelsPage valuationWheelsPage;
	DeliveryPage deliveryPage;
	SearchResultsPage searchResultsPage;
	SoftAssert softas;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IllegalArgumentException, IOException, ParseException {
		try {

			loginpage = new LoginPage(driver, ProductUrl);
			String Username = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".username");
			String Password = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".password");
			homepage = loginpage.Login(Username, Password);
			jsonReader = new JsonReader();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Actions", "StartNewsTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the News test are not run.<br>"
					+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
		}
	}



	@Test(priority = 0, groups = { "chparg" }, description = "MAFQABANG-527")
	public void displayOfValuationofWheelsPageAndCheckForNoResultsMsg() throws Exception {
		softas = new SoftAssert();
		boolean isSearcTemplatePresent=false;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "ValuationOfWheels ", testResult.getMethod().getMethodName());

			boolean isBienesPersonalesWidgetExpanded = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Verify Search template and No Result Alert Message for Valuation_of_Wheels");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				
				String alertmsgKey = "alertmsg";
				String alertmsg = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,alertmsgKey,extentLogger); 
				
				toolsPage = homepage.openToolsPage();
				toolsPage.ExpandBienesPersonalesWidget();
				isBienesPersonalesWidgetExpanded = toolsPage.isBienesPersonalesWidgetExpanded();

				if (isBienesPersonalesWidgetExpanded) {
					valuationWheelsPage=toolsPage.clickValuationofWheels();
				}	

				isSearcTemplatePresent = valuationWheelsPage.verifyValuationofWheelsSearchTemplate();

				softas.assertTrue(isSearcTemplatePresent, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearcTemplatePresent, "Valuation of Wheels Page page is displayed with expected fields", jiraNumber);

				valuationWheelsPage.clickOnSearch();
				boolean isAlertMsgdisplayed = valuationWheelsPage.isErrorMessageDisplayed(alertmsg);

				softas.assertTrue(isAlertMsgdisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isAlertMsgdisplayed, issueSummary,alertmsgKey,alertmsg, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}	

	@Test(priority = 1, groups = { "chparg" }, description = "MAFQABANG-528")
	public void searchUsingAllFieldsAndDeliveryOption() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "ValuationOfWheels", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Verify the Search Result page and Email Delivery of Valuation_of_Wheels");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "fiscalyear";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger);
				String brandKey ="brand";
				String brand = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,brandKey,extentLogger); 
				String kindKey ="kind";
				String kind = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,kindKey,extentLogger); 
				String modelKey ="model";
				String model = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,modelKey,extentLogger); 
				String yearofproductionKey ="yearofproduction";
				String yearofproduction = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,yearofproductionKey,extentLogger);
				String emailKey ="email";
				String email=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger); 
				
				toolsPage = homepage.openToolsPage();
				valuationWheelsPage = toolsPage.clickValuationofWheels();
				valuationWheelsPage.selectPeriodFiscalDropdown(period);
				valuationWheelsPage.selectBrandDropdown(brand);
				valuationWheelsPage.selectKindDropdown(kind);
				valuationWheelsPage.selectModelDropdown(model);
				valuationWheelsPage.selectProductionYrDropdown(yearofproduction);
				searchResultsPage = valuationWheelsPage.clickOnSearch();

				if (!(valuationWheelsPage.errorBlockDisplayed() || valuationWheelsPage.noSearchResultsDisplayed())) {
					boolean searchResultsDisplayed = searchResultsPage.searchResultsDisplayedForPeroidValuationWheel(period)
							&& 	searchResultsPage.searchResultsDisplayedForBrand(brand)
							&&  searchResultsPage.searchResultsDisplayedForModel(model)
							&& searchResultsPage.searchResultsDisplayedForkind(kind)
							&& searchResultsPage.searchResultsDisplayedForYrOfProd(yearofproduction);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected",
							periodKey+","+brandKey+","+kindKey+","+modelKey+","+yearofproductionKey,
							period+","+brand+","+kind+","+model+","+yearofproduction,jiraNumber);
				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, "Search results returned zero results",
							periodKey+","+brandKey+","+kindKey+","+modelKey+","+yearofproductionKey,
							period+","+brand+","+kind+","+model+","+yearofproduction,jiraNumber);
					continue;
				}

				boolean isdeliveryoptiondisplay=searchResultsPage.isDeliveryOptionDisplay();
				softas.assertTrue(isdeliveryoptiondisplay, jiraNumber + ":" +issueSummary);
				logExtentStatus(extentLogger, isdeliveryoptiondisplay, " Delivery option is presetn in Result List page", jiraNumber);

				DeliveryPage deliverypage=searchResultsPage.clickEmailbutton();

				deliverypage.emailTo(email);
				deliverypage.clickAcceptButton();

				boolean isDeliveryCompleted=deliverypage.isDeliveryCompleted();
				softas.assertTrue(isDeliveryCompleted, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isDeliveryCompleted, " Email is delivered with Successfull message",
						emailKey,email,jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 2, groups = { "chparg" }, description = "MAFQABANG-531")
	public void modifySearchandclearDataValidations() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "ValuationOfWheels", testResult.getMethod().getMethodName());


			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Validate  Modify Search links and clear button of Valuation_of_Wheels");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "fiscalyear";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger);
				String brandKey ="brand";
				String brand = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,brandKey,extentLogger); 
				String kindKey ="kind";
				String kind = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,kindKey,extentLogger); 
				String modelKey ="model";
				String model = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,modelKey,extentLogger); 
				String yearofproductionKey ="yearofproduction";
				String yearofproduction = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,yearofproductionKey,extentLogger);
				
				toolsPage = homepage.openToolsPage();
				valuationWheelsPage = toolsPage.clickValuationofWheels();
				valuationWheelsPage.selectPeriodFiscalDropdown(period);
				valuationWheelsPage.selectBrandDropdown(brand);
				valuationWheelsPage.selectKindDropdown(kind);
				valuationWheelsPage.selectModelDropdown(model);
				valuationWheelsPage.selectProductionYrDropdown(yearofproduction);


				searchResultsPage = valuationWheelsPage.clickOnSearch();
				
				boolean searchResultsDisplayed = searchResultsPage.searchResultsDisplayedForPeroidValuationWheel(period)
						&& 	searchResultsPage.searchResultsDisplayedForBrand(brand)
						&&  searchResultsPage.searchResultsDisplayedForModel(model)
						&& searchResultsPage.searchResultsDisplayedForkind(kind)
						&& searchResultsPage.searchResultsDisplayedForYrOfProd(yearofproduction);
				softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected",
						periodKey+","+brandKey+","+kindKey+","+modelKey+","+yearofproductionKey,
						period+","+brand+","+kind+","+model+","+yearofproduction,jiraNumber);

				searchResultsPage.ClickOnModifySearchLink();
				boolean isSearchDataRetained = valuationWheelsPage.isIntialSearchValuesDisplay(period, brand, kind, model, yearofproduction);

				softas.assertTrue(isSearchDataRetained, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearchDataRetained,
						"Clicking Edit Search link retained values from initial search",
						periodKey+","+brandKey+","+kindKey+","+modelKey+","+yearofproductionKey,
						period+","+brand+","+kind+","+model+","+yearofproduction,jiraNumber);

				valuationWheelsPage = toolsPage.clickValuationofWheels();
				valuationWheelsPage.selectPeriodFiscalDropdown(period);
				valuationWheelsPage.selectBrandDropdown(brand);
				valuationWheelsPage.selectKindDropdown(kind);
				valuationWheelsPage.selectModelDropdown(model);
				valuationWheelsPage.selectProductionYrDropdown(yearofproduction);

				boolean isClearbuttonPresent= valuationWheelsPage.clickonClearButton();
				softas.assertTrue(isClearbuttonPresent, jiraNumber+ ":" +issueSummary);
				logExtentStatus(extentLogger, isClearbuttonPresent, "Clear button is Present", jiraNumber);

				boolean isFieldsCleared=valuationWheelsPage.isSearchFieldsClearedAfterClickingonClearbutton();

				softas.assertTrue(isFieldsCleared, jiraNumber+ ":" + issueSummary);     
				logExtentStatus(extentLogger, isFieldsCleared, "Intail search template is displayed with empty fields after clicking on clear button",
						periodKey+","+brandKey+","+kindKey+","+modelKey+","+yearofproductionKey,
						period+","+brand+","+kind+","+model+","+yearofproduction,jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 3, groups = { "chparg" }, description = "MAFQABANG-534")
	public void newSearchLinkOnSearchResultsPage() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "ValuationOfWheels", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Verify New Search link in search results page of Valuation_of_Wheels");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);
			for (Object searchString : listOfSearchData) {
				
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String periodKey = "fiscalyear";
				String period = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,periodKey,extentLogger);
				String brandKey ="brand";
				String brand = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,brandKey,extentLogger); 
				String kindKey ="kind";
				String kind = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,kindKey,extentLogger); 
				String modelKey ="model";
				String model = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,modelKey,extentLogger); 
				String yearofproductionKey ="yearofproduction";
				String yearofproduction = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,yearofproductionKey,extentLogger);
				
				
				toolsPage = homepage.openToolsPage();
				valuationWheelsPage = toolsPage.clickValuationofWheels();
				valuationWheelsPage.selectPeriodFiscalDropdown(period);
				valuationWheelsPage.selectBrandDropdown(brand);
				valuationWheelsPage.selectKindDropdown(kind);
				valuationWheelsPage.selectModelDropdown(model);
				valuationWheelsPage.selectProductionYrDropdown(yearofproduction);

				searchResultsPage = valuationWheelsPage.clickOnSearch();
				boolean searchResultsDisplayed = searchResultsPage.searchResultsDisplayedForPeroidValuationWheel(period)
						&& 	searchResultsPage.searchResultsDisplayedForBrand(brand)
						&&  searchResultsPage.searchResultsDisplayedForModel(model)
						&& searchResultsPage.searchResultsDisplayedForkind(kind)
						&& searchResultsPage.searchResultsDisplayedForYrOfProd(yearofproduction);
				softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected",
						periodKey+","+brandKey+","+kindKey+","+modelKey+","+yearofproductionKey,
						period+","+brand+","+kind+","+model+","+yearofproduction,jiraNumber);
				
				searchResultsPage.ClickOnNewSearchLink();

				boolean isSearcTemplatePresent = valuationWheelsPage.verifyValuationofWheelsSearchTemplate();
				softas.assertTrue(isSearcTemplatePresent, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, isSearcTemplatePresent,issueSummary, jiraNumber);
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}	

	@Test(priority = 4, groups = { "chparg" }, description = "MAFQABANG-549")
	public void SearchWithInSearchResults() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "ValuationOfWheels", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, 
					"Search with in Search for Valuation_of_Wheels");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				
				String fiscalYearKey = "fiscalyear";
				String fiscalYear = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,fiscalYearKey,extentLogger); 
				String searchWithInTextKey = "searchwithintext";
				String searchWithInText=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,searchWithInTextKey,extentLogger); 

				toolsPage = homepage.openToolsPage();
				valuationWheelsPage = toolsPage.clickValuationofWheels();
				valuationWheelsPage.selectPeriodFiscalDropdown(fiscalYear);
				searchResultsPage = valuationWheelsPage.clickOnSearch();
				
				boolean searchResultsDisplayed = searchResultsPage!=null
						&&searchResultsPage.searchResultsDisplayedForPeroidValuationWheel(fiscalYear);
				softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, searchResultsDisplayed, "Search results displayed as expected",
						fiscalYearKey,fiscalYear,jiraNumber);
				
				searchResultsPage.enterSearchWithinTerm(searchWithInText);
				searchResultsPage.clickOnSearchWithInSearch();

				if (!(valuationWheelsPage.errorBlockDisplayed() || valuationWheelsPage.noSearchResultsDisplayed())) {
					searchResultsDisplayed = searchResultsPage.isColumndisplayWithSearchData(searchWithInText);
					softas.assertTrue(searchResultsDisplayed, jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary,
							searchWithInTextKey,searchWithInText,jiraNumber);
				} else {
					softas.assertTrue(true, jiraNumber + ":" + "-resulted in no search results");
					logExtentNoResultsAsInfo(extentLogger, "Search Within search returned zero results",
							fiscalYearKey,fiscalYear, jiraNumber);
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