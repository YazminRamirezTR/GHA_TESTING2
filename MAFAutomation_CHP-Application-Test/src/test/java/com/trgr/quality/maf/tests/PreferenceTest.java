package com.trgr.quality.maf.tests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import com.trgr.quality.maf.pages.DeliveryPage;
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.PreferencePage;
import com.trgr.quality.maf.pages.SearchResultsPage;

public class PreferenceTest extends BaseTest {

	/*
	 * Module Name : Preferences Feature Names : Description Preferences_TC_001
	 * : Preferences_Verify Preferences link Preferences_TC_002 :
	 * Preferences_Verify Email Notification services Preferences_TC_003 :
	 * Preferences_Verify Search Results per page is enabled Preferences_TC_004
	 * : Preferences_Verify Options Delivery in preferences is enabled
	 * Preferences_TC_005 : Preferences_Verify Print options Preferences_TC_006
	 * : Preferences_Verify Password reset and Recover password
	 */

	LoginPage loginpage;
	HomePage homepage;
	PreferencePage preferencepage;
	SearchResultsPage searchresultpage;
	DeliveryPage deliverypage;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			loginpage = new LoginPage(driver, ProductUrl);
			String username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			String password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
			homepage = loginpage.Login(username, password);
			jsonReader = new JsonReader();

		} catch (Exception exc) {

			extentLogger = setUpExtentTest(extentLogger, "Preferences", "StartPreferenceTest");
			extentLogger.log(LogStatus.ERROR, "Due to PreRequest Failed : Validations on the Home test are not run.<br>"
					+ takesScreenshot_Embedded() + "<br>" + displayErrorMessage(exc));
			extentReports.endTest(extentLogger);
			softas.assertTrue(false, "Exception in Test");
			softas.assertAll();
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


	@Test(priority = 0, groups = { "chparg","chpbr", "chpury","chppe" }, description = "MAFQABANG-505")
	public void UpdatesToPreferencesPageAllProducts() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Preferences", testResult.getMethod().getMethodName());
			boolean expectedcountperpage = false;

			HomePage homepage = this.homepage.openHomepage();
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Preferences_Verify Preferences End to End Functionality");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String passwordResetApplicable = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"passwordresetapplicable", extentLogger);
				String passwordRecoveryApplicable = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"passwordrecoveryapplicable", extentLogger);
				String fiscalradioapplicable = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"fiscalradioapplicable", extentLogger);
				String freewordKey = "freeword";
				String freeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String emailKey = "email";
				String emailid = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, emailKey, extentLogger);

				homepage = homepage.openHomepage();
				preferencepage = homepage.clickPreferenceLink();
				preferencepage.writeEmailInPreferencePage(emailid);
				homepage = preferencepage.savePreference();
				preferencepage = homepage.clickPreferenceLink();
				String actualemail = preferencepage.getEmail();
				boolean isPreferenceSavedAsExpected = emailid.equals(actualemail);
				softas.assertTrue(isPreferenceSavedAsExpected, jiraNumber + ":Email  has been saved");
				logExtentStatus(extentLogger, isPreferenceSavedAsExpected, ":Email  has been saved", jiraNumber);

				if (passwordResetApplicable.equalsIgnoreCase("Y")) {
					isPreferenceSavedAsExpected = preferencepage.verifyPasswordResetDisplayed();
					softas.assertTrue(isPreferenceSavedAsExpected,
							jiraNumber + ":Verified Password reset section in the preference page");
					logExtentStatus(extentLogger, isPreferenceSavedAsExpected,
							":Verified Password reset section in the preference page", jiraNumber);
				}

				if (passwordRecoveryApplicable.equalsIgnoreCase("Y")) {
					isPreferenceSavedAsExpected = preferencepage.verifyRecoveryPasswordDisplayed();
					softas.assertTrue(isPreferenceSavedAsExpected,
							jiraNumber + ":Verified  Recovery Password  section in the preference page");
					logExtentStatus(extentLogger, isPreferenceSavedAsExpected,
							":Verified  Recovery Password  section in the preference page", jiraNumber);
				}

				boolean isprintoptionA4enabled = preferencepage.isPrintOptionA4Enabled();
				if (!isprintoptionA4enabled) {
					preferencepage.clickOnRadioButtonPrintOptionA4();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				isprintoptionA4enabled = preferencepage.isPrintOptionA4Enabled();

				softas.assertTrue(isprintoptionA4enabled,
						jiraNumber + ":Verified  Print Option A4 enabled in the preference page");
				logExtentStatus(extentLogger, isprintoptionA4enabled,
						":Verified  Print Option A4 enabled in the preference page", jiraNumber);

				boolean isSearchSelected = preferencepage.isSearchEnabled();
				if (!isSearchSelected) {
					preferencepage.clickOnRadioButtonSearch();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				isSearchSelected = preferencepage.isSearchEnabled();
				softas.assertTrue(isSearchSelected, jiraNumber + ": Validated Search radio button in preference page");
				logExtentStatus(extentLogger, isSearchSelected, " :Validated Search radio button in preference page",
						jiraNumber);
				if(fiscalradioapplicable.equalsIgnoreCase("Y"))
				{
				boolean isFiscalRadioButtonSelected = preferencepage.isFiscalRadioButtonEnabled();
				if (!isFiscalRadioButtonSelected) {
					preferencepage.clickOnRadioButtonFiscal();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				isFiscalRadioButtonSelected = preferencepage.isFiscalRadioButtonEnabled();
				softas.assertTrue(isFiscalRadioButtonSelected,
						jiraNumber + " : Validated Fiscal radio button in preference page");
				logExtentStatus(extentLogger, isFiscalRadioButtonSelected,
						":Validated Fiscal radio button in preference page", jiraNumber);
				}
				boolean isprintoptionletterselected = preferencepage.isPrintOptionLetterEnabled();
				if (!isprintoptionletterselected) {
					preferencepage.clickOnRadioButtonPrintOptionLetter();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				isprintoptionletterselected = preferencepage.isPrintOptionLetterEnabled();
				softas.assertTrue(isprintoptionletterselected,
						jiraNumber + ":Verified  Print Option Letter enabled in the preference page");
				logExtentStatus(extentLogger, isprintoptionletterselected,
						":Verified  Print Option Letter enabled in the preference page", jiraNumber);

				boolean ispdfselected = preferencepage.isDeliveryOptionsPdfSelected();
				if (!ispdfselected) {
					preferencepage.clickOnRadioButtonPDF();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				ispdfselected = preferencepage.isDeliveryOptionsPdfSelected();
				if (ispdfselected) {
					homepage = preferencepage.goToHomePage();
					homepage.clickRefreshforThematicSearch();
					homepage.enterFreewordOnQuickSearch(freeword);
					searchresultpage = homepage.clickSearch();
					deliverypage = searchresultpage.clickOnResultListSave();

					boolean isradiobuttonpdfselected = deliverypage.radiobuttonPDF();
					softas.assertTrue(isradiobuttonpdfselected, jiraNumber + ": Delivery optionis set to PDF");
					logExtentStatus(extentLogger, isradiobuttonpdfselected, ":Delivery optionis set to PDF",
							jiraNumber);

				}

				preferencepage = homepage.clickPreferenceLink();

				boolean isRadioButtonresultsperpage = preferencepage.isRadioButton10Enabled();
				if (!isRadioButtonresultsperpage) {
					preferencepage.clickOnRadioButton10();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				isRadioButtonresultsperpage = preferencepage.isRadioButton10Enabled();
				softas.assertTrue(isRadioButtonresultsperpage,
						jiraNumber + ":Search result per page is set to 10 in the preference page");
				logExtentStatus(extentLogger, isRadioButtonresultsperpage,
						":Search result per page is set to 10 in the preference page", jiraNumber);

				if (isRadioButtonresultsperpage) {
					homepage = preferencepage.goToHomePage();
					homepage.clickRefreshforThematicSearch();
					homepage.enterFreewordOnQuickSearch(freeword);
					searchresultpage = homepage.clickSearch();
					int n = searchresultpage.getResultListCountPerPage();
					if(BaseTest.productUnderTest.equalsIgnoreCase("chpbr")) {
						expectedcountperpage = (n >= 10);
					}
					else
					{
					expectedcountperpage = (n <= 10);
					}
					softas.assertTrue(expectedcountperpage,
							jiraNumber + ":" + issueSummary + " :Displayed 10 documents per page");
					logExtentStatus(extentLogger, expectedcountperpage,
							issueSummary + "Displayed 10 documents per page", jiraNumber);
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

	
	@Test(priority = 0, groups = { "chpchile"}, description = "MAFQABANG-505")
	public void UpdatesToPreferencesPage() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Preferences", testResult.getMethod().getMethodName());
			boolean expectedcountperpage = false;

			HomePage homepage = this.homepage.openHomepage();
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Preferences_Verify Preferences End to End Functionality");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;

				String passwordResetApplicable = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"passwordresetapplicable", extentLogger);
				String passwordRecoveryApplicable = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"passwordrecoveryapplicable", extentLogger);
				String fiscalradioapplicable = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,
						"fiscalradioapplicable", extentLogger);
				String freewordKey = "freeword";
				String freeword = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, freewordKey, extentLogger);
				String emailKey = "email";
				String emailid = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, emailKey, extentLogger);

				homepage = homepage.openHomepage();
				preferencepage = homepage.clickPreferenceLink();
				preferencepage.writeEmailInPreferencePage(emailid);
				homepage = preferencepage.savePreference();
				preferencepage = homepage.clickPreferenceLink();
				String actualemail = preferencepage.getEmail();
				boolean isPreferenceSavedAsExpected = emailid.equals(actualemail);
				softas.assertTrue(isPreferenceSavedAsExpected, jiraNumber + ":Email  has been saved");
				logExtentStatus(extentLogger, isPreferenceSavedAsExpected, ":Email  has been saved", jiraNumber);

				if (passwordResetApplicable.equalsIgnoreCase("Y")) {
					isPreferenceSavedAsExpected = preferencepage.verifyPasswordResetDisplayed();
					softas.assertTrue(isPreferenceSavedAsExpected,
							jiraNumber + ":Verified Password reset section in the preference page");
					logExtentStatus(extentLogger, isPreferenceSavedAsExpected,
							":Verified Password reset section in the preference page", jiraNumber);
				}

				if (passwordRecoveryApplicable.equalsIgnoreCase("Y")) {
					isPreferenceSavedAsExpected = preferencepage.verifyRecoveryPasswordDisplayed();
					softas.assertTrue(isPreferenceSavedAsExpected,
							jiraNumber + ":Verified  Recovery Password  section in the preference page");
					logExtentStatus(extentLogger, isPreferenceSavedAsExpected,
							":Verified  Recovery Password  section in the preference page", jiraNumber);
				}

				boolean isprintoptionA4enabled = preferencepage.isPrintOptionA4Enabled();
				if (!isprintoptionA4enabled) {
					preferencepage.clickOnRadioButtonPrintOptionA4();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				isprintoptionA4enabled = preferencepage.isPrintOptionA4Enabled();

				softas.assertTrue(isprintoptionA4enabled,
						jiraNumber + ":Verified  Print Option A4 enabled in the preference page");
				logExtentStatus(extentLogger, isprintoptionA4enabled,
						":Verified  Print Option A4 enabled in the preference page", jiraNumber);

				boolean isSearchSelected = preferencepage.isSearchEnabled();
				if (!isSearchSelected) {
					preferencepage.clickOnRadioButtonSearch();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				isSearchSelected = preferencepage.isSearchEnabled();
				softas.assertTrue(isSearchSelected, jiraNumber + ": Validated Search radio button in preference page");
				logExtentStatus(extentLogger, isSearchSelected, " :Validated Search radio button in preference page",
						jiraNumber);
				if(fiscalradioapplicable.equalsIgnoreCase("Y"))
				{
				boolean isFiscalRadioButtonSelected = preferencepage.isFiscalRadioButtonEnabled();
				if (!isFiscalRadioButtonSelected) {
					preferencepage.clickOnRadioButtonFiscal();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				isFiscalRadioButtonSelected = preferencepage.isFiscalRadioButtonEnabled();
				softas.assertTrue(isFiscalRadioButtonSelected,
						jiraNumber + " : Validated Fiscal radio button in preference page");
				logExtentStatus(extentLogger, isFiscalRadioButtonSelected,
						":Validated Fiscal radio button in preference page", jiraNumber);
				}
				boolean isprintoptionletterselected = preferencepage.isPrintOptionLetterEnabled();
				if (!isprintoptionletterselected) {
					preferencepage.clickOnRadioButtonPrintOptionLetter();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				isprintoptionletterselected = preferencepage.isPrintOptionLetterEnabled();
				softas.assertTrue(isprintoptionletterselected,
						jiraNumber + ":Verified  Print Option Letter enabled in the preference page");
				logExtentStatus(extentLogger, isprintoptionletterselected,
						":Verified  Print Option Letter enabled in the preference page", jiraNumber);

				boolean ispdfselected = preferencepage.isDeliveryOptionsPdfSelected();
				if (!ispdfselected) {
					preferencepage.clickOnRadioButtonPDF();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				ispdfselected = preferencepage.isDeliveryOptionsPdfSelected();
				if (ispdfselected) {
					homepage = preferencepage.goToHomePage();					
					homepage.enterFreewordOnQuickSearch(freeword);
					searchresultpage = homepage.clickSearch();
					deliverypage = searchresultpage.clickOnResultListSave();

					boolean isradiobuttonpdfselected = deliverypage.radiobuttonPDF();
					softas.assertTrue(isradiobuttonpdfselected, jiraNumber + ": Delivery optionis set to PDF");
					logExtentStatus(extentLogger, isradiobuttonpdfselected, ":Delivery optionis set to PDF",
							jiraNumber);

				}

				preferencepage = homepage.clickPreferenceLink();

				boolean isRadioButtonresultsperpage = preferencepage.isRadioButton10Enabled();
				if (!isRadioButtonresultsperpage) {
					preferencepage.clickOnRadioButton10();
					homepage = preferencepage.savePreference();
				}
				preferencepage = homepage.clickPreferenceLink();
				isRadioButtonresultsperpage = preferencepage.isRadioButton10Enabled();
				softas.assertTrue(isRadioButtonresultsperpage,
						jiraNumber + ":Search result per page is set to 10 in the preference page");
				logExtentStatus(extentLogger, isRadioButtonresultsperpage,
						":Search result per page is set to 10 in the preference page", jiraNumber);

				if (isRadioButtonresultsperpage) {
					homepage = preferencepage.goToHomePage();				
					homepage.enterFreewordOnQuickSearch(freeword);
					searchresultpage = homepage.clickSearch();
					int n = searchresultpage.getResultListCountPerPage();
					expectedcountperpage = (n <= 10);
					softas.assertTrue(expectedcountperpage,
							jiraNumber + ":" + issueSummary + " :Displayed 10 documents per page");
					logExtentStatus(extentLogger, expectedcountperpage,
							issueSummary + "Displayed 10 documents per page", jiraNumber);
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

	@Test(priority = 1, groups = { "chpmex" }, description = "MAFAUTO-168")
	public void updatesSpecificFieldsofPreferencesPage() throws Exception {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Preferences", testResult.getMethod().getMethodName());

			HomePage homepage = this.homepage.openHomepage();
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Preferences - Edit Preferences");

			homepage = homepage.openHomepage();
			preferencepage = homepage.clickPreferenceLink();

			// verifying whether search option is selected or not
			boolean isSearchSelected = preferencepage.isSearchEnabled();
			if (!isSearchSelected) {
				preferencepage.clickOnRadioButtonSearch();
				homepage = preferencepage.savePreference();
			}
			preferencepage = homepage.clickPreferenceLink();
			isSearchSelected = preferencepage.isSearchEnabled();
			softas.assertTrue(isSearchSelected,
					jiraNumber + issueSummary + ": Validated Search radio button in preference page");
			logExtentStatus(extentLogger, isSearchSelected,
					issueSummary + " :Validated Search radio button in preference page", jiraNumber);

			// verifying whether Fiscal option is selected or not
			boolean isFiscalRadioButtonSelected = preferencepage.isFiscalRadioButtonEnabled();
			if (!isFiscalRadioButtonSelected) {
				preferencepage.clickOnRadioButtonFiscal();
				homepage = preferencepage.savePreference();
			}
			preferencepage = homepage.clickPreferenceLink();
			isFiscalRadioButtonSelected = preferencepage.isFiscalRadioButtonEnabled();
			softas.assertTrue(isFiscalRadioButtonSelected,
					jiraNumber + issueSummary + " : Validated Fiscal radio button in preference page");
			logExtentStatus(extentLogger, isFiscalRadioButtonSelected,
					issueSummary + ":Validated Fiscal radio button in preference page", jiraNumber);

			// verifying whether print letters option is selected or not
			boolean isprintoptionletterselected = preferencepage.isPrintOptionLetterEnabled();
			if (!isprintoptionletterselected) {
				preferencepage.clickOnRadioButtonPrintOptionLetter();
				homepage = preferencepage.savePreference();
			}
			preferencepage = homepage.clickPreferenceLink();
			isprintoptionletterselected = preferencepage.isPrintOptionLetterEnabled();
			softas.assertTrue(isprintoptionletterselected,
					jiraNumber + issueSummary + ":Verified  Print Option Letter enabled in the preference page");
			logExtentStatus(extentLogger, isprintoptionletterselected,
					issueSummary + ":Verified  Print Option Letter enabled in the preference page", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	/*
	 * MAFQABANG-558 Preference Page validation
	 */
	@Test(priority = 1, groups = { "chpury" }, description = "MAFQABANG-558")
	public void MyDocumentsPageValidation() throws Exception {
		SoftAssert softas = new SoftAssert();
		boolean pageverified = false;
		testResult = Reporter.getCurrentTestResult();
		extentLogger = setUpExtentTest(extentLogger, "PreferencePage", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Preference Page validation");
		try {
			preferencepage = homepage.clickPreferenceLink();
			pageverified = preferencepage.isUserInformationDisplayed() && preferencepage.isBrowsingServiceDisplayed()
					&& preferencepage.isSubjectAreaDisplayed() && preferencepage.isSearchResultsperPageDisplayed()
					&& preferencepage.isDeliveryOptionsDisplayed() && preferencepage.isPrintingOptionsDisplayed()
					&& preferencepage.isChangePasswordDisplayed() && preferencepage.isRecoverPasswordDisplayed()
					&& preferencepage.isConfigureAlertsDisplayed();

			softas.assertTrue(pageverified, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, pageverified, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// This test will work only for the newly registered One pass user
	// @Test(priority = 2, groups = { "chpmex" }, description = "MAFAUTO-218")
	public void DefaultPreferences() throws Exception {
		SoftAssert softas = new SoftAssert();
		testResult = Reporter.getCurrentTestResult();
		extentLogger = setUpExtentTest(extentLogger, "PreferencePage", testResult.getMethod().getMethodName());
		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Login - First Time Login");
		try {
			preferencepage = homepage.clickPreferenceLink();
			boolean isDefaultPreferenceSaved = preferencepage.verifyDefaultPreferences();
			softas.assertTrue(isDefaultPreferenceSaved, issueSummary + ": Default preferences has been saved");
			logExtentStatus(extentLogger, isDefaultPreferenceSaved,
					issueSummary + " :Default preferences has been saved", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

}