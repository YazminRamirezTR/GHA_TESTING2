package com.trgr.quality.maf.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.jsonreader.JsonReader;
import com.trgr.quality.maf.pages.DeliveryPage;
import com.trgr.quality.maf.pages.DoctrinePage;
import com.trgr.quality.maf.pages.DocumentDisplayPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SearchPage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class DeliveryTest extends BaseTest {

	/*
	 * Delivery_TC_001 - Verify the Document sent to intended Email address with
	 * word document attachment Delivery_TC_002 - Verify the Document sent to
	 * intended Email address with PDF attachment Delivery_TC_003 - Verify page
	 * navigates back to result list page after clicking on email option
	 * Delivery_TC_004 - Verify the Email is not arrived in recipient inbox
	 * Delivery_TC_005 - Verify the Document sent to intended Email address with
	 * word document attachment Delivery_TC_006 - Verify the Document sent to
	 * intended Email address with PDF attachment Delivery_TC_007 - Verify the
	 * Email is not arrived in recipient inbox Prerequisite: Enter the wrong
	 * email id in preferences
	 *
	 * Delivery_TC_0011: Verify the export document in Result List
	 * Delivery_TC_0012: Verify the export document in Document Display Added
	 * MAFAUTO - Pablo Request MAFAUTO-228 : Legislation - Document Display -
	 * Delivery Selected Text
	 */

	HomePage homepage;
	LoginPage loginpage;
	DeliveryPage deliverypage; 
	SearchPage searchpage;
	SearchResultsPage searchresultpage;
	JSONObject jsonObject;
	JsonReader jsonReader;
	JSONArray listOfSearchData;
	JSONArray listOfSearchDatajuris;
	JSONArray listOfSearchDatalegis;
	DocumentDisplayPage documentDisplayPage;
	DoctrinePage doctrinepage;
	SearchResultsPage searchResultsPage;
 
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	JSONParser parser = new JSONParser();

	JSONArray listOfSearchDataDoctrine;

	@BeforeClass(alwaysRun = true)
	public void startDeliveryTest() throws Exception {

		try {

			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");

			jsonReader = new JsonReader();
			homepage = loginpage.Login(username, password);

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Delivery", "startDeliveryTest");
			extentLogger.log(LogStatus.ERROR,
					"Due to PreRequest Failed : Validations on the Delivery test are not run.<br>"
							+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			Assert.assertTrue(false);
		}

	}

	@AfterClass(alwaysRun = true)
	public void endDeliveryTest(){
		try {
			if(homepage!=null)
				homepage.clickSignOff();
			else
				(new HomePage(driver)).clickSignOff();
			//Adding this code to avoid sign off skip in case of unexpected conditions, which sets home page to null
		} catch (Exception e) {
		}
	}

	@Test(priority=0,groups = { "chparg", "chpmex", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-133")
	public void deliveryOfResultListUsingEmailWithWordAndPDF() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			boolean isdeliverytextdisplayed=false;
			
			String jiraNumber = testResult.getMethod().getDescription();	
			String issueSummary = getIssueTitle(jiraNumber,"Verify delivery using Email with word and PDF document attached");
			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);            
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);   

				String emailKey = "emailto";
				String email_to = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger); 
				String documentnumberKey = "documentnumber";
				String documnetNumber = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,documentnumberKey,extentLogger); 

				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
				//searchpage.enterFreeWordOnSearchPage(freewordVal);
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}

				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchresultpage.clickVertodosLink();
				}
				searchresultpage.clickCheckBox();
				
				deliverypage = searchresultpage.clickOnEmailOption();

				deliverypage.emailToClear();
				deliverypage.emailTo(email_to);
				if (deliverypage.radiobuttonResultListDocument()) {
					deliverypage.clickFullDocumentRdioButton();
					deliverypage.clickResultListRdioButton();
				} else {

					deliverypage.clickResultListRdioButton();
				}

				if (deliverypage.radiobuttonRTF()) {
					//deliverypage.clickPDFRdioButton();
					//deliverypage.clickRTFRadioButton();
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
					isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
					softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isdeliverytextdisplayed,
							"Verify email sent with document RTF", jiraNumber);


				} else {
					deliverypage.clickRTFRadioButton();

					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
					softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isdeliverytextdisplayed,
							"Verify email sent with document RTF", jiraNumber);
				}
				
				searchpage = deliverypage.deliveryReturnToSearchPage();

				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordVal);
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchresultpage.clickVertodosLink();
				}
				searchresultpage.clickCheckBox();
				deliverypage = searchresultpage.clickOnEmailOption();
				deliverypage.emailToClear();
				deliverypage.emailTo(email_to);

				if (deliverypage.radiobuttonFullDocument()) {

					deliverypage.clickResultListRdioButton();
					deliverypage.clickFullDocumentRdioButton();
					deliverypage.enternumberDocument(documnetNumber);

				} else {
					deliverypage.clickFullDocumentRdioButton();
					deliverypage.enternumberDocument(documnetNumber);
				}

				if (deliverypage.radiobuttonPDF()) {

					//deliverypage.clickRTFRadioButton();
					//deliverypage.clickPDFRdioButton();
					JavascriptExecutor jse11 = (JavascriptExecutor) driver;
					jse11.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
					softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isdeliverytextdisplayed,
							"Verify email sent with document PDF", jiraNumber);
				} else {
					deliverypage.clickPDFRdioButton();


					JavascriptExecutor jse11 = (JavascriptExecutor) driver;
					jse11.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
					softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isdeliverytextdisplayed,
							"Verify email sent with document PDF", jiraNumber);
				}
				
				
				searchpage = deliverypage.deliveryReturnToSearchPage();
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=1,groups = { "chparg", "chpmex", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-135")
	public void ResultListcancelDeliveryByEmail() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			boolean flag=false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"verify page navigates back to result list page after clicking on cancel in email option");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger); 

				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordVal);
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}

				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchresultpage.clickVertodosLink();
				}
				searchresultpage.clickCheckBox();
				deliverypage = searchresultpage.clickOnEmailOption();
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,250)", "");
				deliverypage.clickCancelButton();
				flag=deliverypage.deliverySearchResultList();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				
				if (!flag)
				{
					testResult.setAttribute("defect", "MAFAUTO-287");
					logExtentStatus(extentLogger, flag, issueSummary,freewordKey,freewordVal, jiraNumber);
				}
				else
				{
					logExtentStatus(extentLogger, flag, issueSummary,freewordKey,freewordVal, jiraNumber);	
				}
				

				searchpage = deliverypage.deliveryReturnToSearchPage();
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);					
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=2,groups = { "chparg", "chpmex", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-136")
	public void DeliveryWithInvalidEmailFormat() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			boolean isDeliveryTextDisplayed=false;
						
			String jiraNumber = testResult.getMethod().getDescription();			
			String issueSummary = getIssueTitle(jiraNumber,"Verify the Email is not arrived with invalid email Id"); 

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);   

				String emailKey = "invalidemailid";
				String emailvalue= jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger); 
				String documentnumberKey = "documentnumber";
				String documnetNumbervalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,documentnumberKey,extentLogger);


				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordVal);
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				
				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				Thread.sleep(3000);
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchresultpage.clickVertodosLink();
					Thread.sleep(2000);
				}
				
				searchresultpage.clickCheckBox();
				deliverypage = searchresultpage.clickOnEmailOption();
				Thread.sleep(3000);
				deliverypage.emailToClear();
				deliverypage.emailTo(emailvalue);

				if (deliverypage.radiobuttonFullDocument()) {
					deliverypage.clickResultListRdioButton();
					deliverypage.clickFullDocumentRdioButton();
					deliverypage.enternumberDocument(documnetNumbervalue);

				} else {
					deliverypage.clickFullDocumentRdioButton();
					deliverypage.enternumberDocument(documnetNumbervalue);
				}

				if (deliverypage.radiobuttonPDF()) {

					//deliverypage.clickRTFRadioButton();
					//deliverypage.clickPDFRdioButton();
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
					isDeliveryTextDisplayed=deliverypage.deliverTextDisplayed();
					softas.assertTrue(isDeliveryTextDisplayed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isDeliveryTextDisplayed, issueSummary,freewordKey,freewordVal, jiraNumber);

				} else {
					deliverypage.clickPDFRdioButton();

					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
					softas.assertTrue(isDeliveryTextDisplayed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isDeliveryTextDisplayed, issueSummary,freewordKey,freewordVal, jiraNumber);
				}
				
				if(!isDeliveryTextDisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-297");
				}
				searchpage = deliverypage.deliveryReturnToSearchPage();
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority=21, groups = { "chparg", "chpmex", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-134")
	public void SingleDocumentDelivery() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();		
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			boolean isDeliveryTextDisplayed=false;
			
			String jiraNumber = testResult.getMethod().getDescription();			
			String issueSummary = getIssueTitle(jiraNumber,"Verify delivery using Email with PDF document attached"); 
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger); 
				String emailKey = "Email_To";
				String email_to = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger); 
				/*	String documentnumberKey = "documentnumber";
					String documnetNumber = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,documentnumberKey,extentLogger);
				 */
				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordVal);
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				
				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchresultpage.clickVertodosLink();
				}
				
				searchresultpage.clickCheckBox();
				deliverypage = searchresultpage.clickOnEmailOption();
				deliverypage.emailToClear();
				deliverypage.emailTo(email_to);
				if (deliverypage.radiobuttonRTF()) {
					deliverypage.clickPDFRadioButtonforDelivery();
					
					deliverypage.clickRTFRadioButton();
				} else {
					deliverypage.clickRTFRadioButton();
				}
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,250)", "");
				deliverypage.clickOkButton();
				isDeliveryTextDisplayed=deliverypage.deliverTextDisplayed();
				softas.assertTrue(isDeliveryTextDisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isDeliveryTextDisplayed, issueSummary + " document type: RTF",
						freewordKey,freewordVal, jiraNumber);
				if(!isDeliveryTextDisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-297");
				}

				deliverypage.deliveryReturnToSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordVal);
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				
				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchresultpage.clickVertodosLink();
				}
				
				searchresultpage.clickCheckBox();
				deliverypage = searchresultpage.clickOnEmailOption();
				deliverypage.emailToClear();
				deliverypage.emailTo(email_to);
				if (deliverypage.radiobuttonPDF()) {
					//deliverypage.clickRTFRadioButton();
					//deliverypage.clickPDFRdioButton();
					JavascriptExecutor jse2 = (JavascriptExecutor) driver;
					jse2.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
					softas.assertTrue(isDeliveryTextDisplayed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isDeliveryTextDisplayed, issueSummary + " document type: PDF",
							freewordKey,freewordVal, jiraNumber);
				} else {
					deliverypage.clickPDFRdioButton();

					JavascriptExecutor jse2 = (JavascriptExecutor) driver;
					jse2.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
					softas.assertTrue(isDeliveryTextDisplayed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isDeliveryTextDisplayed, issueSummary + " document type: PDF",
							freewordKey,freewordVal, jiraNumber);
				}
				searchpage = deliverypage.deliveryReturnToSearchPage();
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
	 * Delivery_TC_0011: Verify the export document in Result List
	 */
	@Test(priority=25,groups = { "chpmex", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-143")
	public void exportDocumentsFromResultList() throws Exception {
		boolean flag = false;
		softas = new SoftAssert();

		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify Export Document from result list page"); 
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);   

				// PerformSearch() ensures that the result list page is displayed
				// before starting this test.

				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordVal);
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				
				searchresultpage = searchpage.clickOnSearchButtonDocumentDisplay();
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchresultpage.clickVertodosLink();
				}
				Thread.sleep(3000);
				deliverypage = searchresultpage.clickExportButton();
					
				// Export 'list of results' in RTF document
//				deliverypage.verifyResultListExportPage() &&
				flag = 	 deliverypage.enableRadioButton("lista_de_resultados_radio")
						&& deliverypage.setListOfResultsRange("1-2")
						&& deliverypage.enableRadioButton("formato_de_archivo_rtf") && deliverypage.clickAcceptButton();
				
//						&& deliverypage.isDeliveryCompleted();

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + " document type:RTF", freewordKey,freewordVal, jiraNumber);

				// Go back to result list after exporting 'list of results' in RTF
				// document
				flag = deliverypage.verifyReturnToResultList();
			     //driver.navigate().back();
				softas.assertTrue(flag, " Verify Result List is displayed"+ jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + " display of result list page", 
						freewordKey,freewordVal, jiraNumber);

				// Export 'complete document' in PDF document
				flag = deliverypage.clickDocumentExportButton()
						&& deliverypage.enableRadioButton("documentos_completos_radio")
						&& deliverypage.setDocumentRange("1-2") && deliverypage.enableRadioButton("formato_de_archivo_pdf")
						&& deliverypage.clickAcceptButton();
//				&& deliverypage.isDeliveryCompleted();
				if (flag) {
					softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, flag, issueSummary + " document type:PDF",freewordKey,freewordVal, jiraNumber);

				}
				// Go back to result list after exporting 'Complete Documents' in
				// PDF Format
				flag = deliverypage.verifyReturnToResultList();
				 
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + " Verify Result List is displayed", freewordKey,freewordVal, jiraNumber);

				// Verify Cancel button in export page
				flag = deliverypage.clickDocumentExportButton() && deliverypage.clickCancel();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + " button in export page", jiraNumber);
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
	 * Delivery_TC_0012: Verify the export document in Document Display
	 */
	@Test(priority=6,groups = { "chparg", "chpmex", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-144")
	public void exportSingleDocumentFromDocumentDisplay() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			boolean flag = false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Delivery_Document Display__Verify the export document in Document Display"); 
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger); 

				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordVal);
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				
				searchresultpage=searchpage.clickonSearchwhenThematicisSelected();
				documentDisplayPage=searchresultpage.getFirstDocument();

				deliverypage = searchresultpage.clickExportButton();

				// Export 'list of results' in RTF document
				flag =  deliverypage.enableRadioButton("formato_de_archivo_rtf")
						&& deliverypage.clickAcceptButton() && deliverypage.isDeliveryCompleted();
				
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + " document type:RTF",
						freewordKey,freewordVal, jiraNumber);



				if(!flag){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-283");
				}
				// Go back to document display after exporting 'list of results' in
				// RTF document
				flag = deliverypage.verifyReturnToDocumentDisplay();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + " Document displayed", 
						freewordKey,freewordVal, jiraNumber);

				// Repeat delivery with PDF, and complete document
				flag = deliverypage.clickDocumentExportButton() && deliverypage.enableRadioButton("formato_de_archivo_pdf")
						&& deliverypage.clickAcceptButton() && deliverypage.isDeliveryCompleted();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + " document type: PDF",
						freewordKey,freewordVal, jiraNumber);

				// Go back to document display
				flag = deliverypage.verifyReturnToDocumentDisplay();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + " Document displayed",
						freewordKey,freewordVal, jiraNumber);

				// Verify Cancel button in export page
				flag = deliverypage.clickDocumentExportButton() && deliverypage.clickCancel();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, issueSummary + " Cancel button",
						freewordKey,freewordVal, jiraNumber);
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	//*********************************************************************************
	@Test(priority = 7, groups = { "chpmex" }, description = "MAFAUTO-154")
	public void exportEmailandPrintforSingleDocument() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			boolean flag = false;

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Legislation - Document Display - Delivery");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "documenttitle",
						extentLogger);
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "thematicarea",
						extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "treeitems", extentLogger)
						.split(",");
				String emailKey = "emailto";
				String email = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger); 
				

				homepage = homepage.openHomepage();
				SearchPage searchPage = homepage.OpenSearchPage();

				// Select Thematic Area & Click on area in tree(if it is not
				// already selected)
				searchPage.selectGivenValueFromThematicDropdown(thematicArea);
				if (searchPage.isAreaFoundInContentTree(thematicArea)) {
					searchPage.selectAreaFromContentTree(thematicArea);
				}

				// Expand First level item in the tree structure
				WebElement treeElement = searchPage.getFirstLevelContentTree(treeItems[0]);
				searchPage.expandContentTreeElement(treeElement);
				// String treeTraversed = treeItems[0];
				// Expand till document in the sub tree 
				for (int row = 1; row < treeItems.length; row++) {
					if (searchPage.isItemPresentInSubContentTree(treeElement, treeItems[row])) {
						searchPage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
						treeElement = searchPage.getSubContentTreeElement(treeElement, treeItems[row]);
					}
				}

				// Open document from the tree
				boolean docDisplayedInTree = searchPage.isItemPresentInSubContentTree(treeElement, documentTitle);
				documentDisplayPage = searchPage.clickDocumentInSubContentTree(treeElement, documentTitle);

				softas.assertTrue(docDisplayedInTree, jiraNumber + "Document opened from Tree Content");
				logExtentStatus(extentLogger, documentDisplayPage != null, "Document opened from Tree Content",
						jiraNumber);

				// exporting the document with RTF Format
				deliverypage = documentDisplayPage.clickExportButton();
				flag = deliverypage.enableRadioButton("formato_de_archivo_rtf") && deliverypage.clickAcceptButton()
						&& deliverypage.isDeliveryCompleted();
				softas.assertTrue(flag, "Document Exported with RTF Format" + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, "Document Exported with RTF Format", thematicArea, documentTitle,
						jiraNumber);

				// Go back to document display after exporting 'list of results'
				// in
				// RTF document
				flag = deliverypage.verifyReturnToDocumentDisplay();
				softas.assertTrue(flag, "Returned back to Document Display Page" + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,"Returned back to Document Display Page", jiraNumber);

				// Email Document with pdf format
				deliverypage = documentDisplayPage.clickOnEmailOption();
				deliverypage.emailToClear();
				deliverypage.emailTo(email);
				
				if (!deliverypage.radiobuttonPDF()) 
				{
					deliverypage.clickPDFRdioButton();
				}
					JavascriptExecutor jse2 = (JavascriptExecutor) driver;
					jse2.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
					boolean isDeliveryTextDisplayed = deliverypage.deliverTextDisplayed();
					softas.assertTrue(isDeliveryTextDisplayed, "Email Document with pdf format" + ":" + jiraNumber);
					logExtentStatus(extentLogger, isDeliveryTextDisplayed, "Email Document with pdf format",
							thematicArea, documentTitle, jiraNumber);
					

                     if(!isDeliveryTextDisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-297");
				}
				

				// Going back to Document Display Page
				flag = deliverypage.verifyReturnToDocumentDisplay();
				softas.assertTrue(flag, "Returned back to Document Display Page" + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag, "Returned back to Document Display Page", thematicArea, documentTitle,
						jiraNumber);

				// Verify Print button in export page
				deliverypage = documentDisplayPage.clickPrint();
				boolean isdeliverytextdisplayed = deliverypage.deliverTextDisplayed();
				softas.assertTrue(isdeliverytextdisplayed, issueSummary +"Document Printed" + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdeliverytextdisplayed, issueSummary + "Document Printed", thematicArea, documentTitle,
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
	
	//********************************************************************************8
	// MAFAUTO-228 : Legislation - Document Display - Delivery Selected Text
	@Test(priority=20,groups = { "chpmex" }, description = "MAFAUTO-228")
	public void ExportSelectedWord() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber,"Export Selected Word");
		DocumentDisplayPage docDisplayPage = null;

		try {

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String documentTitle = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "exportdocument",
						extentLogger);
				
				
				String Articlevalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "Article",
						extentLogger);
				String SelectParagraphvalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "SelectParagraph",
						extentLogger);
				String OptionsDeliveryvalue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "OptionsDelivery",
						extentLogger);
				
				String thematicArea = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "thematicarea",
						extentLogger);
				String treeItems[] = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, "treeitems", extentLogger)
						.split(",");

			searchpage = homepage.OpenSearchPage();

			// Select Thematic Area & Click on area in tree(if it is not already
			// selected)
			searchpage.selectGivenValueFromThematicDropdown(thematicArea);
			if (searchpage.isAreaFoundInContentTree(thematicArea)) {
				searchpage.selectAreaFromContentTree(thematicArea);
			}

			// Expand First level item in the tree structure
			WebElement treeElement = searchpage.getFirstLevelContentTree(treeItems[0]);
			searchpage.expandContentTreeElement(treeElement);
			// Expand till document in the sub tree
			for (int row = 1; row < treeItems.length; row++) {
				if (searchpage.isItemPresentInSubContentTree(treeElement, treeItems[row])) {
					searchpage.clickExpandItemInSubContentTree(treeElement, treeItems[row]);
					treeElement = searchpage.getSubContentTreeElement(treeElement, treeItems[row]);
				}
			}
			// Open document from the tree
			boolean docDisplayedInTree = searchpage.isItemPresentInSubContentTree(treeElement, documentTitle);
			if (docDisplayedInTree)
				docDisplayPage = searchpage.clickDocumentInSubContentTree(treeElement, documentTitle);

			softas.assertTrue(docDisplayedInTree && docDisplayPage != null, "Document fetched from tree content");
			logExtentStatus(extentLogger, docDisplayedInTree && docDisplayPage != null,
					"Document fetched from tree content", jiraNumber);

			// Select document content
			boolean wordSelected = false;
			if (docDisplayPage != null) {
				docDisplayPage.selectOneWordInArticleOneTitle();
				wordSelected = docDisplayPage.isPopUpMenuDisplayedForSelectedText();
			}

			softas.assertTrue(wordSelected, "Validate Selected portion of the document content");
			logExtentStatus(extentLogger, wordSelected, "Validate selected portion of the document content",
					jiraNumber);

			// Export selected content
			boolean exported = docDisplayPage.clickExportInPopUpMenu();

			softas.assertTrue(exported, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, exported,  "Export Selected part of the document", jiraNumber);

			// Select document content
			wordSelected = false;
			if (docDisplayPage != null) {
				docDisplayPage.selectOneWordInArticleOneTitle();
				wordSelected = docDisplayPage.isPopUpMenuDisplayedForSelectedText();
			}
			// Print selected content
			boolean printed = docDisplayPage.clickPrintInPopUpMenu();
			softas.assertTrue(printed, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, printed, "Print Selected part of the document", jiraNumber);

			docDisplayPage.cancelPrintViewInBrowser(driver);

			// Select document content
			wordSelected = false;
			if (docDisplayPage != null) {
				docDisplayPage.selectOneWordInArticleOneTitle();
				wordSelected = docDisplayPage.isPopUpMenuDisplayedForSelectedText();
			}
			// Email selected content
			boolean emailSent = docDisplayPage.clickEmailInPopUpMenu() && docDisplayPage.isSelectedContentSentAsEmail();
			softas.assertTrue(emailSent, issueSummary + ":" + jiraNumber);
			logExtentStatus(extentLogger, emailSent,  "Email Selected part of the document", jiraNumber);

			docDisplayPage.closeSentEmailConfirmationPopUp();
			}
		} catch (Exception exc) {
			 logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);

			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority=7,groups = { "chparg", "chpmex", "chpbr" ,"chpury","chppe","chpchile"}, description = "MAFQABANG-137")
	public void docDisplayPageEmailDeliveryWithRTFFormat() throws Exception {
		softas = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();				
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();				
			String issueSummary = getIssueTitle(jiraNumber,"Verify the Document sent to intended Email address with word document attachment"); 

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);   

				String emailKey = "Email_To";
				String email_to = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger); 

				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				deliverypage = searchresultpage.ClickOnSearchLink();
				deliverypage = searchresultpage.clickOnEmailOption();
				deliverypage.emailToClear();
				deliverypage.emailTo(email_to);
				if (deliverypage.radiobuttonRTF()) {
					deliverypage.clickPDFRdioButton();
					deliverypage.clickRTFRadioButton();
				} else {
					deliverypage.clickRTFRadioButton();
				}
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,250)", "");
				deliverypage.clickOkButton();
				boolean isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
				softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdeliverytextdisplayed, issueSummary,emailKey,email_to, jiraNumber);
				searchpage = deliverypage.deliveryReturnToSearchPage();
				


				if(!isdeliverytextdisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-297");
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


	@Test(priority=8, groups = { "chparg", "chpmex","chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-138")
	public void docDisplayEmailDeliveryWithPDFFormat() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,"Verify the Document sent to intended Email address with PDF attachment"); 

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);   

				String emailKey = "Email_To";
				String email_to = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger); 

				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				} 		
				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				deliverypage = searchresultpage.ClickOnSearchLink();				
				deliverypage = searchresultpage.clickOnEmailOption();
				deliverypage.emailToClear();
				deliverypage.emailTo(email_to);
				if (deliverypage.radiobuttonPDF()) {
					deliverypage.clickRTFRadioButton();
					deliverypage.clickPDFRdioButton();
				} else {
					deliverypage.clickPDFRdioButton();
				}
				JavascriptExecutor jse2 = (JavascriptExecutor) driver;
				jse2.executeScript("window.scrollBy(0,250)", "");
				deliverypage.clickOkButton();
				boolean isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
				softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdeliverytextdisplayed, issueSummary,emailKey,email_to, jiraNumber);
				searchpage = deliverypage.deliveryReturnToSearchPage();
				if(!isdeliverytextdisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-297");
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

	@Test(priority=9, groups = { "chparg", "chpmex","chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-139")
	public void documentDisplayDeliveryWithInvalidEmail() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			
			String jiraNumber = testResult.getMethod().getDescription();			
			String issueSummary = getIssueTitle(jiraNumber,"Verify the Email not send from document display page"); 

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);   

				String emailKey = "Email_To";
				String email_to = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger); 

				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				} 				
				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				deliverypage = searchresultpage.ClickOnSearchLink();
				deliverypage = searchresultpage.clickOnEmailOption();
				deliverypage.emailToClear();
				deliverypage.emailTo(email_to);
				if (deliverypage.radiobuttonPDF()) {
					deliverypage.clickRTFRadioButton();
					deliverypage.clickPDFRdioButton();
				} else {
					deliverypage.clickPDFRdioButton();
				}
				JavascriptExecutor jse3 = (JavascriptExecutor) driver;
				jse3.executeScript("window.scrollBy(0,250)", "");
				deliverypage.clickOkButton();
				boolean isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
				softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdeliverytextdisplayed, issueSummary,emailKey,email_to, jiraNumber);


				if(!isdeliverytextdisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-297");
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

	@Test(priority=10,groups = { "chparg", "chpbr","chpury","chppe","chpchile" }, description = "MAFQABANG-140")
	public void resultListDocDeliveryByPrint() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber,"Verify the print in Result list page"); 			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);   

				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				searchresultpage.clickCheckBox();
				deliverypage = searchresultpage.clickPrint();
				if (deliverypage.radiobuttonResultListDocument()) {
					deliverypage.clickFullDocumentRdioButton();
					deliverypage.clickResultListRdioButton();
				} else {
					deliverypage.clickResultListRdioButton();
				}
				deliverypage.clickOkButton();
				boolean isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
				softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdeliverytextdisplayed, issueSummary,freewordKey,freewordVal, jiraNumber);
				searchpage = deliverypage.deliveryReturnToSearchPage();
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}


	@Test(priority=12, groups = { "chparg", "chpbr" ,"chpury","chpmex","chppe","chpchile"}, description = "MAFQABANG-142")
	public void documentDislplayDeliveryByPrint() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			
			String jiraNumber = testResult.getMethod().getDescription();			
			String issueSummary = getIssueTitle(jiraNumber,"Verify the print in document list page"); 

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);   

				searchpage = homepage.OpenSearchPage();
				
				if(BaseTest.productUnderTest.equals("chpury")) 
				{			
					if (!searchpage.isFreewordFieldDisplayed()) {
						searchpage.clickThematicSearchRadioButton();
						searchpage.enterFreeWordOnSearchPage(freewordVal);
					
				}

				}
				
				else if(BaseTest.productUnderTest.equals("chpmex")) {
				    homepage.enterTextInSearchField(freewordVal);
				}
				else 
				{
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
//				if(!BaseTest.productUnderTest.equals("chpmex")) {
//				searchresultpage = searchpage.clickonSearchwhenThematicisSelected();
				searchresultpage = searchpage.clickOnSearch();
				if(BaseTest.productUnderTest.equals("chpury")) {
					searchresultpage= searchpage.clickSearchUrySearchPage();
				}
//				}
				searchresultpage.ClickOnSearchLink();
				Thread.sleep(4000);
				deliverypage = searchresultpage.clickPrint();
				Thread.sleep(1000);
				boolean isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
				softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdeliverytextdisplayed, issueSummary,freewordKey,freewordVal, jiraNumber);


				if(!isdeliverytextdisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-283");
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


	@Test(priority=14, groups = {"chpmex","chpury","chppe","chpchile" }, description = "MAFQABANG-141")
	public void deliverDocumentFromDocumentDisplayUsingPrint() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();           
			String issueSummary = getIssueTitle(jiraNumber,"Verify Print Result List Full Document"); 			
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey="freeword";
				String freewordValue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);
				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordValue);
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordValue);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordValue);
				}
				
				searchresultpage = searchpage.clickOnSearchButtonDocumentDisplay();
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					
					Thread.sleep(5000);
					if(searchresultpage.isErrorPageDisplayed()) {
						searchpage = homepage.OpenSearchPage();
						if (!searchpage.isFreewordFieldDisplayed()) {
							searchpage.clickThematicSearchRadioButton();
						}
//						searchpage.enterFreeWordOnSearchPage(freewordValue);
						
						if(BaseTest.productUnderTest.equals("chpmex")) {
							homepage.enterTextInSearchField(freewordValue);
						}
						else {
							searchpage.enterFreeWordOnSearchPage(freewordValue);
						}
						searchresultpage = searchpage.clickOnSearchButtonDocumentDisplay();
					}
					searchresultpage.clickVertodosLink();
				}
				
				searchresultpage.clickCheckBox();
				deliverypage = searchresultpage.clickPrint();
				Thread.sleep(2000);
				if (deliverypage.radiobuttonFullDocument()) {
					Thread.sleep(2000);
					deliverypage.clickResultListRdioButton();
					deliverypage.clickFullDocumentRdioButton();
					Thread.sleep(2000);
					deliverypage.enternumberDocument("1");
				} else {
					deliverypage.clickFullDocumentRdioButton();
				}
				deliverypage.clickOkButton();
				boolean isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
				softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdeliverytextdisplayed, issueSummary,freewordKey,freewordValue, jiraNumber);
				searchpage = deliverypage.deliveryReturnToSearchPage();
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}



	@Test(priority=16,groups = { "chpmex" }, description = "MAFAUTO-187")
	public void JurisprudencePageDelivery() throws Exception {
		boolean flag = false;
		boolean isdeliverytextdisplayed=false;
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();		
			String issueSummary = getIssueTitle(jiraNumber,"Jurisprudence - Result List - Delivery"); 		
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey="freeword";
				String freewordVal =  jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);
				searchpage = homepage.OpenSearchPage();
				searchpage.clickjurisprudencia();
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultpage = searchpage.clickOnSearchButtonDocumentDisplay();
				boolean resultlistjuri = searchresultpage.deliveryDocument();
				softas.assertTrue(resultlistjuri, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, resultlistjuri,
						"Verify print and export options displayed (Jurisprudencia)", jiraNumber);

				deliverypage = searchresultpage.clickExportButtons();
				flag = deliverypage.verifyDocumentExportPage() && deliverypage.enableRadioButton("formato_de_archivo_rtf")
						&& deliverypage.clickAcceptButton() && deliverypage.isDeliveryCompleted();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,
						"Delivery completed for 'list of results' as RTF document (Jurisprudencia)",
						freewordKey,freewordVal, jiraNumber);

				deliverypage.navigateBack();
				deliverypage.clickPrint();
				if (deliverypage.radiobuttonFullDocument()) {
					deliverypage.clickResultListRdioButton();
					deliverypage.clickFullDocumentRdioButton();
					deliverypage.enternumberDocument("1");
				} else {
					deliverypage.clickFullDocumentRdioButton();
				}
				deliverypage.clickOkButton();
				isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
				softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdeliverytextdisplayed,issueSummary,freewordKey,freewordVal, jiraNumber);
				searchpage = deliverypage.deliveryReturnToSearchPage();
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);					
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();

		}

	}

	@Test(priority=17,groups = { "chpmex" },  description = "MAFAUTO-184")
	public void LegislationDeliveryByPrint() throws Exception {
		softas = new SoftAssert();
		boolean isdeliverytextdisplayed=false;
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Legislation - Result List - Delivery");		

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);            
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;			
				String freewordKey="freeword";
				String freewordVal =  jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);

				String Formatfilekey="Formatfile";
				String Formatfilekeyradio=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,Formatfilekey,extentLogger);
               
				String Email_Tokey="Email_To";
				String Emailname=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,Email_Tokey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				searchpage.openLegislationPage();
				searchpage.enterFreeWordOnSearchPage(freewordVal);
				searchresultpage = searchpage.clickOnSearchButtonDocumentDisplay();
				boolean resultlistlegis = searchresultpage.deliveryDocument();
				softas.assertTrue(resultlistlegis, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, resultlistlegis,
						"Verify print and export options displayed (Legislation)", jiraNumber);

				deliverypage = searchresultpage.clickExportButtons();
				boolean flag = deliverypage.verifyDocumentExportPage()
						&& deliverypage.enableRadioButton("formato_de_archivo_rtf") && deliverypage.clickAcceptButton()
						&& deliverypage.isDeliveryCompleted();

				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,
						"Delivery completed for 'list of results' as RTF document (Legislation)",
						freewordKey,freewordVal, jiraNumber);

				deliverypage.navigateBack();
				deliverypage.clickPrint();
				if (deliverypage.radiobuttonFullDocument()) {
					deliverypage.clickResultListRdioButton();
					deliverypage.clickFullDocumentRdioButton();
					deliverypage.enternumberDocument("1");
				} else {
					deliverypage.clickFullDocumentRdioButton();
				}
				deliverypage.clickOkButton();
				flag = deliverypage.deliverTextDisplayed();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,
						" Verify print from result list with fulldocument (Legislation)", jiraNumber);
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, flag,
						issueSummary, jiraNumber);
				isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
				softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isdeliverytextdisplayed,
						issueSummary,freewordKey,freewordVal, jiraNumber);
				
				if(BaseTest.productUnderTest.equalsIgnoreCase("chpmex")) {
					deliverypage.navigateBack();
					deliverypage.emailClicked();
	                deliverypage.emailTo(Emailname);
	                deliverypage.clickFullDocumentRdioButton();
					deliverypage.enternumberDocument("1");
					deliverypage.selectRadioButtonDeliveryPage(Formatfilekeyradio);
					deliverypage.clickOkButton();
					flag = deliverypage.deliverTextDisplayed();
					softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, flag,
							" Verifie with Email name and Radio button of Delivery page ", jiraNumber);
				}
				searchpage = deliverypage.deliveryReturnToSearchPage();
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);					
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();

		}

	}

	@Test(priority=18,groups = { "chpmex" },  description = "MAFAUTO-189")
	public void deliverGlobalSearchReportUsingPrint() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();         
			String issueSummary = getIssueTitle(jiraNumber,"Doctrine - Result List - Delivery");			         
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);            
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordValue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);
				

				String authorkey = "author";
				String authorVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, authorkey, extentLogger);
				String Formatfilekey="Formatfile";
				String Formatfilekeyradio=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,Formatfilekey,extentLogger);
               
				String Email_Tokey="email-notification";
				String Emailname=jsonReader.readKeyValueFromJsonObject(jsonObjectChild,Email_Tokey,extentLogger);

				searchpage = homepage.OpenSearchPage();
				doctrinepage = searchpage.OpenDoctrinaPage();
				doctrinepage.enterAuthor(authorVal);
				doctrinepage.enterAuthorDropDownValue();
			    searchpage.enterFreeWordOnSearchPage(freewordValue);
			    searchResultsPage = doctrinepage.clickOnSearch();
				//searchresultpage.clickOnGivenLinkByLinkText(link_click, false);
				Thread.sleep(2000);				
				boolean resultlistDoctrinesearch = searchResultsPage.deliveryDocument();
				softas.assertTrue(resultlistDoctrinesearch, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, resultlistDoctrinesearch,
						"Verify print and export options displayed (Doctrine Search)", jiraNumber);

				deliverypage = searchResultsPage.clickExportButtons();
				boolean flag = deliverypage.verifyDocumentExportPage()
						&& deliverypage.enableRadioButton("formato_de_archivo_rtf") && deliverypage.clickAcceptButton()
						&& deliverypage.isDeliveryCompleted();
				softas.assertTrue(flag, issueSummary + ":" + jiraNumber);

				logExtentStatus(extentLogger, flag,
						"Verify print and export options  (Global Search)", jiraNumber);

			
	
			
				


				deliverypage.navigateBack();
				deliverypage.clickPrint();
				if (deliverypage.radiobuttonFullDocument()) {
					deliverypage.clickResultListRdioButton();
					deliverypage.clickFullDocumentRdioButton();
					deliverypage.enternumberDocument("1");
				} else {
					deliverypage.clickFullDocumentRdioButton();
				}
				deliverypage.clickOkButton();
				if(BaseTest.productUnderTest.equalsIgnoreCase("chpmex")) {
					deliverypage.navigateBack();
					deliverypage.emailClicked();
	                deliverypage.emailTo(Emailname);
	                deliverypage.clickFullDocumentRdioButton();
					deliverypage.enternumberDocument("1");
					deliverypage.selectRadioButtonDeliveryPage(Formatfilekeyradio);
					deliverypage.clickOkButton();
					flag = deliverypage.deliverTextDisplayed();
					softas.assertTrue(flag, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, flag,
							" Verifie with Email name and Radio button of Delivery page ", jiraNumber);
				}
				searchpage = deliverypage.deliveryReturnToSearchPage();
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);					
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority=19,groups = { "chpmex" },  description = "MAFAUTO-104")
	public void deliverQuickSearchReportUsingPrint() throws Exception {
		softas = new SoftAssert();
		try {

			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();          
			String issueSummary = getIssueTitle(jiraNumber,"Common Document Display Features - Document Display - Show Delivery Options");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);            
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String freewordKey="freeword";
				String freewordValue = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);
				searchpage = homepage.OpenSearchPage();
				String link="resultsearch";
				String link_click = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,link,extentLogger);
				searchpage = homepage.OpenSearchPage();

				searchpage.enterTextInSearchField(freewordValue);
				searchresultpage = searchpage.clickOnSearchButtonDocumentDisplay();
				boolean searchResultsDisplayed = searchresultpage.isLinkPresentforResultspage(link_click);
				softas.assertTrue(searchResultsDisplayed, issueSummary +":"+ jiraNumber);

				logExtentStatus(extentLogger, searchResultsDisplayed, issueSummary,link_click,link_click, jiraNumber);
				
				searchresultpage.clickOnGivenLinkByLinkText(link_click, false);
				Thread.sleep(2000);
				boolean resultlistcommonsearch = searchresultpage.deliveryDocument();				
				softas.assertTrue(resultlistcommonsearch,
						"Verify print and export options displayed in result list");
				logExtentStatus(extentLogger, resultlistcommonsearch,
						"Verify print and export options displayed in result list", jiraNumber);

				deliverypage = searchresultpage.clickExportButtons();
				boolean flag = deliverypage.verifyDocumentExportPage()
						&& deliverypage.enableRadioButton("formato_de_archivo_rtf") && deliverypage.clickAcceptButton()
						&& deliverypage.isDeliveryCompleted();
				softas.assertTrue(flag, issueSummary +":"+ jiraNumber);

				logExtentStatus(extentLogger, flag, issueSummary,freewordKey,freewordValue, jiraNumber);

				/*deliverypage.navigateBack();
				deliverypage.clickPrint();
				if (deliverypage.radiobuttonFullDocument()) {
					deliverypage.clickResultListRdioButton();
					deliverypage.clickFullDocumentRdioButton();
					deliverypage.enternumberDocument("1");
				} else {
					deliverypage.clickFullDocumentRdioButton();
				}
				deliverypage.clickOkButton();*/
			}
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);		
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}
	
	
	@Test(priority=5,groups = { "chpmex"}, description = "MAFAUTO-183")
	public void resultListDeliveryOptions() throws Exception {
		boolean flag = false;
		softas = new SoftAssert();
		boolean isdeliverytextdisplayed=false;

		try {
			testResult = Reporter.getCurrentTestResult();			
			extentLogger = setUpExtentTest(extentLogger, "Delivery", testResult.getMethod().getMethodName());
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,"Verify Delivery Option from result list page"); 
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber,extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String freewordKey = "freeword";
				String freewordVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,freewordKey,extentLogger);
				
				String emailKey = "Email_To";
				String email_to = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger);

				// PerformSearch() ensures that the result list page is displayed
				// before starting this test.

				searchpage = homepage.OpenSearchPage();
				if (!searchpage.isFreewordFieldDisplayed()) {
					searchpage.clickThematicSearchRadioButton();
				}
//				searchpage.enterFreeWordOnSearchPage(freewordVal);
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					homepage.enterTextInSearchField(freewordVal);
				}
				else {
					searchpage.enterFreeWordOnSearchPage(freewordVal);
				}
				
				searchresultpage = searchpage.clickOnSearchButtonDocumentDisplay();
				
				if(BaseTest.productUnderTest.equals("chpmex")) {
					searchresultpage.clickVertodosLink();
				}
				
				boolean isDeliveryOptionPresent=searchresultpage.isDeliveryEmailButtonDisplay()&&
												searchresultpage.isDeliveryExportButtonDisplay()&&
												searchresultpage.isDeliveryPrintButtonDisplay();
				
				softas.assertTrue(isDeliveryOptionPresent, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isDeliveryOptionPresent, "Delivery Options-To Print,To Export and E-Mail  is Present in Result List Page",jiraNumber);
				
				deliverypage = searchresultpage.clickExportButton();
				
				boolean isDeliveryExportMandatoryFieldsPresent=deliverypage.listofResultDisplay()&& deliverypage.exForListofResult()
															&&deliverypage.isCompleteDocumentOptionPresent()&&deliverypage.isRangeCompleteDocumentOptionPresent()
															&& deliverypage.isRTFandPDFFileformatDispaly();
				
				softas.assertTrue(isDeliveryExportMandatoryFieldsPresent, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isDeliveryExportMandatoryFieldsPresent, "Mandatory Fields for Export Delivery Optionsis Present ",jiraNumber);
				
				deliverypage.enableRadioButton("lista_de_resultados_radio");
				deliverypage.enableRadioButton("formato_de_archivo_rtf");
				deliverypage.clickAcceptButton();
				boolean isExportDeliveryCompleted=	 deliverypage.isDeliveryCompleted() && deliverypage.verifyDeliveryStatus();
				softas.assertTrue(isExportDeliveryCompleted, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isExportDeliveryCompleted, "Delivery Export is completed Successfully ",jiraNumber);

				

				// Go back to result list after exporting 'list of results' in RTF
				// document
				flag = deliverypage.verifyReturnToResultList();
				softas.assertTrue(flag, " Verify Result List is displayed"+ jiraNumber);
				logExtentStatus(extentLogger, flag, "Page Navigates back to Result list page after clickin on back button", 
						freewordKey,freewordVal, jiraNumber);
				
				//PrintDelivery
				deliverypage = searchresultpage.clickPrint();
				
				
				boolean isDeliveryPrintMandatoryFieldsPresent=deliverypage.listofResultDisplay()&& deliverypage.exForListofResult()
						&&deliverypage.isCompleteDocumentOptionPresent()&&deliverypage.isRangeCompleteDocumentOptionPresent();
				softas.assertTrue(isDeliveryPrintMandatoryFieldsPresent, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isDeliveryPrintMandatoryFieldsPresent, "Mandatory Fields for Print Delivery Optionsis Present ",jiraNumber);
				
				deliverypage.enableRadioButton("lista_de_resultados_radio");
				deliverypage.clickAcceptButton();
				boolean isPrintDeliveryCompleted=	 deliverypage.isDeliveryCompleted() && deliverypage.verifyDeliveryStatus();
				softas.assertTrue(isPrintDeliveryCompleted, issueSummary + ":" + jiraNumber);
				logExtentStatus(extentLogger, isPrintDeliveryCompleted, "Delivery Print is completed Successfully ",jiraNumber);

				// Go back to result list after exporting 'list of results' in RTF
				// document
				flag = deliverypage.verifyReturnToResultList();
				softas.assertTrue(flag, " Verify Result List is displayed"+ jiraNumber);
				logExtentStatus(extentLogger, flag, "Page Navigates back to Result list page after clickin on back button ", 
						freewordKey,freewordVal, jiraNumber);
				

				// Email Delivery
				
				deliverypage = searchresultpage.clickOnEmailOption();
				deliverypage.emailToClear();
				deliverypage.emailTo(email_to);
				deliverypage.clickResultListRdioButton();
				

				if (deliverypage.radiobuttonPDF()) {

					//deliverypage.clickRTFRadioButton();
					//deliverypage.clickPDFRdioButton();
					JavascriptExecutor jse11 = (JavascriptExecutor) driver;
					jse11.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
					isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
					softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isdeliverytextdisplayed,
							"Verify email sent with document PDF", jiraNumber);
				} else {
					deliverypage.clickPDFRdioButton();


					JavascriptExecutor jse11 = (JavascriptExecutor) driver;
					jse11.executeScript("window.scrollBy(0,250)", "");
					deliverypage.clickOkButton();
				    isdeliverytextdisplayed=deliverypage.deliverTextDisplayed();
					softas.assertTrue(isdeliverytextdisplayed, issueSummary + ":" + jiraNumber);
					logExtentStatus(extentLogger, isdeliverytextdisplayed,
							"Verify email sent with document PDF", jiraNumber);
				}
				searchpage = deliverypage.deliveryReturnToSearchPage();
				
				if(!isdeliverytextdisplayed){
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-297");
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
