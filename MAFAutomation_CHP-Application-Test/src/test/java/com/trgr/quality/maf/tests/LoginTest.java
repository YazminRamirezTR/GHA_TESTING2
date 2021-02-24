package com.trgr.quality.maf.tests;

import java.io.IOException;
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
import com.trgr.quality.maf.pages.HomePage;
import com.trgr.quality.maf.pages.LoginPage;
import com.trgr.quality.maf.pages.SignOffPage;

public class LoginTest extends BaseTest {

	String username;
	String password;
	String clientidentifier;
	HomePage homepage;
	SignOffPage signoffpage;
	public ITestResult testResult;
	JiraConnector jiraConnect;
	SoftAssert softas;
	LoginPage loginpage;
	JsonReader jsonReader;

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		jsonReader = new JsonReader();
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
	 * Login_TC_001:Verify user is able to login with valid credentials and home
	 * page is displayed Login_TC_002:Verify user is not able to login with
	 * invalid credentials
	 * 
	 * 
	 */
	@Test(priority = 0, groups = { "chparg", "chpury", "chpmex", "chpbr" ,"chppe","chpchile"}, description = "MAFQABANG-32")
	public void loginWithValidAndInvalidCredentials() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, "Login with valid and invalid credentails");

		try {
			username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);

			{
				// verify login with invalid credentials
				loginpage.LoginInvalidScenarios(username + "test", password);
				boolean flag = loginpage.invalidCredentialsMsgDisplayed() || loginpage.ValidateandclickOnAlertPopUP();
				softas.assertTrue(flag, jiraNumber + ":" + issueSummary + " : Validated Login with invalid username");
				logExtentStatus(extentLogger, flag, "ValidatedLogin with invalid usename scenario successful",
						jiraNumber);

				loginpage.LoginInvalidScenarios(username, password + "test");
				flag = loginpage.invalidCredentialsMsgDisplayed() || loginpage.ValidateandclickOnAlertPopUP();
				softas.assertTrue(flag, jiraNumber + ":" + issueSummary + " : Validated Login with invalid password");
				logExtentStatus(extentLogger, flag, "ValidatedLogin with invalid password scenario successful",
						jiraNumber);

			}
			;
			// login with valid credentials
			{
				username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
				password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");

				HomePage homepage = loginpage.Login(username, password);
				if (homepage.isHomePageDisplayed()) {

					softas.assertTrue(homepage.isHomePageDisplayed(),
							jiraNumber + ":" + issueSummary + " : Validated Login with valid credentials failed");
					logExtentStatus(extentLogger, homepage.isHomePageDisplayed(),
							"Validated Login with valid credentials successful", jiraNumber);
					homepage.clickSignOff();
					loginpage.LoadLoginPage(ProductUrl);

					if (loginpage.isUsernameEmpty() && loginpage.isPasswordEmpty())
						logExtentStatus(extentLogger, loginpage.isUsernameEmpty() && loginpage.isPasswordEmpty(),
								"Login with SaveUserNameAndPwd option unchecked successful", jiraNumber);

				} else {
					extentLogger.log(LogStatus.FAIL,
							"Login with valid credentials scenario failed" + takesScreenshot_Embedded());
					softas.assertTrue(false, "Login with valid credentials failed");
				}

			}
			;

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}


	/*
	 * MAFQABANG-35
	 * Login_Verify valid customer id
	 */
	@Test(priority = 0, groups = { "chparg", "chpury", "chppe","chpchile"}, description = "MAFQABANG-35")
	public void loginWithClientID() throws Exception {
		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();

		String issueSummary = getIssueTitle(jiraNumber, "Login with Client ID");

		try {
			username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {
				JSONObject jsonObjectChild = (JSONObject) searchString;
				String clientIDkey = "clientid";
				String clientIDVal = jsonReader.readKeyValueFromJsonObject(jsonObjectChild, clientIDkey, extentLogger);

				LoginPage loginpage = new LoginPage(driver, ProductUrl);
				boolean clientIDLinkFound = loginpage.isClientIDLinkDisplayed();
				softas.assertTrue(clientIDLinkFound,jiraNumber + ":" + "Assign Client ID link found");
				logExtentStatus(extentLogger, clientIDLinkFound,"Assign Client ID link found", jiraNumber);
				
				if(clientIDLinkFound) {
					loginpage.clickonClientIDLink();
					loginpage.enterClientID(clientIDVal);
					HomePage homepage = loginpage.Login(username, password);
					boolean loggedIn = (homepage!=null) && homepage.isHomePageDisplayed();
					softas.assertTrue(loggedIn,jiraNumber + ":" + issueSummary);
					logExtentStatus(extentLogger, loggedIn,issueSummary,clientIDkey,clientIDVal, jiraNumber);
					homepage.clickSignOff();
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

	
	@Test(priority = 1, groups = { "chparg", "chpury", "chpmex", "chpbr","chppe","chpchile" }, description = "MAFQABANG-45")
	public void loginUsingSaveUserNameAndPwd() throws Exception {
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Login with Save Username and password enabled");

			username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
			LoginPage loginpage = new LoginPage(driver, ProductUrl);

			loginpage.SelectSaveUsernameAndPassword();

			homepage = loginpage.Login(username, password);

			softas.assertTrue(homepage.isHomePageDisplayed(), jiraNumber + ":" + issueSummary + ": Login Success");
			logExtentStatus(extentLogger, homepage.isHomePageDisplayed(), issueSummary + ": Login Success", jiraNumber);

			// sign off for logging again
			homepage.clickSignOff();

			loginpage = new LoginPage(driver, ProductUrl);
//			Thread.sleep(2000);
 			boolean flag = loginpage.isUsernameSaved(username) && loginpage.isPasswordSaved(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".passwordkey"));
			softas.assertTrue(flag,
					jiraNumber + ":" + issueSummary + ": Logout & verify details are saved on login screen");
			logExtentStatus(extentLogger, flag, issueSummary + ": Logout & verify details are saved on login screen",
					jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
			homepage.clickSignOff();
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 2, groups = { "chparg", "chpury", "chppe","chpchile" }, description = "MAFQABANG-37")
	public void KnowtheServiceLink() throws IllegalArgumentException, IOException {
	boolean serviceLinkValidated, serviceLinkdisplayed=false;
		try {
			testResult = Reporter.getCurrentTestResult();
			softas = new SoftAssert();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();

			String issueSummary = getIssueTitle(jiraNumber, "Verify the link About the Service in the login page");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			 serviceLinkdisplayed = loginpage.isServiceLinkDisplayed();
			if (serviceLinkdisplayed) {
				loginpage.clickServiceLink();
				serviceLinkValidated = loginpage.isServiceLinkPageDisplayed();
			}
			softas.assertTrue(serviceLinkdisplayed,jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, serviceLinkdisplayed, issueSummary,jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
			homepage.clickSignOff();
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 3, groups = { "chparg", "chpury","chppe","chpchile"}, description = "MAFQABANG-38")
	public void InteractiveDemoLink() throws IllegalArgumentException, IOException, InterruptedException {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verification of Interactive Demo Link");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			loginpage.clickInteractiveDemoLink();
			boolean interactiveDemolinkValidated = loginpage.validateRedirectedUrl();

			softas.assertTrue(interactiveDemolinkValidated,jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, interactiveDemolinkValidated, issueSummary,jiraNumber);
			if(!interactiveDemolinkValidated){//Log existing bug
				//testResult.setAttribute("defect", "MAFAUTO-276");
				testResult.setAttribute("defect", "MAFAUTO-269");
			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 4, groups = { "chparg", "chpury","chppe","chpchile"}, description = " ")
	public void FrequentQuestionsLink() throws IllegalArgumentException, IOException {
		softas = new SoftAssert();
		boolean validateFAQPage = false;
		boolean isfaqlinkdisplayed=false;
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());		
			
			//String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle("", "Verification of Frequent question Link");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);

			isfaqlinkdisplayed = loginpage.isFrequentQuestionsLinkAvailable();
			if (isfaqlinkdisplayed) {
				loginpage.clickFrequentQuestionsLink();
				validateFAQPage = loginpage.isFreqQuesPageDisplayed();
			}
			if(!validateFAQPage){//Log existing bug
				testResult.setAttribute("defect", "MAFAUTO-294");
			}
			softas.assertTrue(validateFAQPage,  issueSummary);
			logExtentStatus(extentLogger, validateFAQPage, issueSummary,"");
			

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");

		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	// MAFAUTO-220
	@Test(priority = 5, groups = { "chpmex" }, description = "MAFAUTO-220")
	public void LoginBranding() throws Exception {

		testResult = Reporter.getCurrentTestResult();
		softas = new SoftAssert();
		extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

		String jiraNumber = testResult.getMethod().getDescription();
		String issueSummary = getIssueTitle(jiraNumber, "Application Verification on Login Branding");

		try {
			String url[] = { "http://www.deloitte.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.osy.Checkpointmexico.com.clt.int.westgroup.com",
					//"http://www.bt.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.uvm.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.ey.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.kpmg.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.ssgt.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.gossler.checkpointmexico.com.clt.int.westgroup.com",				
					"http://www.natera.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.basham.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.unam.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.itam.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.ulsa.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.sat.checkpointmexico.com.clt.int.westgroup.com",
					"http://www.prodecon.checkpointmexico.com.clt.int.westgroup.com",
			"http://www.pwc.checkpointmexico.com.clt.int.westgroup.com" };
			for (int i = 0; i < url.length; i++) {

				LoginPage loginpage = new LoginPage(driver, url[i]);
				username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
				password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
				HomePage homepage = loginpage.Login(username, password);
				boolean isClientLogoDisplayed = homepage.isClientLogoDisplayed();
				softas.assertTrue(isClientLogoDisplayed, jiraNumber + ":" + issueSummary + " : Verified Client logo");
				logExtentStatus(extentLogger, isClientLogoDisplayed, "Verified Client logo for : " + url[i],
						jiraNumber);

				if(!isClientLogoDisplayed)
				{
					//Log existing bug
					testResult.setAttribute("defect", "MAFAUTO-258");
				}
				signoffpage = homepage.getSignOffPage();
				boolean isSignSummaryDisplayed = signoffpage.validateSignoffSummary();
				softas.assertTrue(isSignSummaryDisplayed,
						jiraNumber + ":" + issueSummary + " : Verified SignOff Summary");
				logExtentStatus(extentLogger, isSignSummaryDisplayed, "Verified SignOff Summary", jiraNumber);

			}

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in LoginBranding");
		}finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}

	}

	@Test(priority = 6, groups = { "chpmex", "chpbr" }, description = "MAFQABANG-42")
	public void loginUsingSaveUserName() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Application Verification on Save my User name");

			username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");
			LoginPage loginpage = new LoginPage(driver, ProductUrl);

			loginpage.SelectSaveUsername();

			HomePage homepage = loginpage.Login(username, password);

			softas.assertTrue(homepage.isHomePageDisplayed(),
					jiraNumber + ":Validated Login with Select Save option enabled");
			logExtentStatus(extentLogger, homepage.isHomePageDisplayed(),
					"Validated Login with Select Save option enabled", jiraNumber);

			// sign off for logging again
			homepage.clickSignOff();

			loginpage = new LoginPage(driver, ProductUrl);

			softas.assertTrue(loginpage.isUsernameSaved(username),
					jiraNumber + ":" + issueSummary + " : Validated Username details are saved");
			logExtentStatus(extentLogger, loginpage.isUsernameSaved(username), "Validate Username details are saved",
					jiraNumber);

			loginpage.SelectSaveUsername();

			homepage = loginpage.Login(username, password);
			homepage.clickSignOff();

			loginpage = new LoginPage(driver, ProductUrl);

			softas.assertTrue(loginpage.isUsernameEmpty(),
					jiraNumber + ":" + issueSummary + " : Validated Login with Select Save option disabled");
			logExtentStatus(extentLogger, loginpage.isUsernameEmpty(),
					"Validated Login with Select Save option disabled", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 7, groups = { "chpmex" }, description = "MAFQABANG-36")
	public void ForgotUsernamePasswordLinks() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Application Verification on Forgot username and password");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "email";
				String email = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,key, extentLogger);

				LoginPage loginpage = new LoginPage(driver, ProductUrl);

				boolean validateforgotusername = loginpage.isForgotUsernameLinkDispalyed();
				if (validateforgotusername) {
					loginpage.clickForgotUsernameLink();
					boolean isforgotunamepagedisplayed = loginpage.isForgotCredentialsPageDisplayed();
					if (isforgotunamepagedisplayed) {
						loginpage.enterRecoveryEmail(email);
						boolean continuebtndisplayed = loginpage.isContinuebtnForSendEmailDisplayed();
						if (continuebtndisplayed) {
							loginpage.clickContinueButton();
							loginpage.isReturnToCheckpointDisplayed(email);
							loginpage.clickReturnToCheckpoint();
						}

					}
				}
				softas.assertTrue(validateforgotusername,
						jiraNumber + ":" + issueSummary + " : Validated forgot username Link is verified");
				logExtentStatus(extentLogger, validateforgotusername,
						"validated forgot username Link is verified successfully", jiraNumber);

				boolean forgotpwdlinkpresent = loginpage.isForgotPasswordLinkDisplayed();
				if (forgotpwdlinkpresent) {
					loginpage.clickForgotPasswordLink();
					boolean isforgotpwdpagedisplayed = loginpage.isForgotCredentialsPageDisplayed();
					if (isforgotpwdpagedisplayed) {
						loginpage.enterRecoveryEmail(email);
						boolean continuebtndisplayed = loginpage.isContinuebtnForSendEmailDisplayed();
						if (continuebtndisplayed) {
							loginpage.clickContinueButton();
							loginpage.isReturnToCheckpointDisplayed(email);
							loginpage.clickReturnToCheckpoint();
						}
					}
				}
				softas.assertTrue(forgotpwdlinkpresent,
						jiraNumber + ":" + issueSummary + " : validated forgot password Link is verified");
				logExtentStatus(extentLogger, forgotpwdlinkpresent,
						"validated forgot password Link is verified successfully", jiraNumber);
			}
			
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	
	@Test(priority = 14, groups = { "chppe","chpchile" }, description = "MAFQABANG-36")
	public void ForgotPasswordLinks() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Application Verification on Forgot username and password");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);
			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;
				String key = "username";
				String username = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,key, extentLogger);

				LoginPage loginpage = new LoginPage(driver, ProductUrl);

				

				boolean forgotpwdlinkpresent = loginpage.isForgotPasswordLinkDisplayed();
				if (forgotpwdlinkpresent) {
					loginpage.clickForgotPasswordLink();
					Thread.sleep(1000);
					boolean isforgotpwdpagedisplayed = loginpage.isForgotCredentialsPageDisplayed();
					if (isforgotpwdpagedisplayed) {
						loginpage.enterUserName(username);						
						loginpage.clickOnSubmitButton();
					boolean isanswethequestiondisplayed=loginpage.isAnswerTheQuestionDisplayed();		
					softas.assertTrue(isanswethequestiondisplayed,
							jiraNumber + ":" + issueSummary + " : validated Answer the question section");
					logExtentStatus(extentLogger, isanswethequestiondisplayed,
							"validated Answer the question section", jiraNumber);
					}
				}
				softas.assertTrue(forgotpwdlinkpresent,
						jiraNumber + ":" + issueSummary + " : validated forgot password Link is verified");
				logExtentStatus(extentLogger, forgotpwdlinkpresent,
						"validated forgot password Link is verified successfully", jiraNumber);
			}
			
		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	@Test(priority = 8, groups = { "chpmex" }, description = "MAFQABANG-40")
	public void onepasspageprofilelinks() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());
			boolean validatecreateonepassprofileLink,validateupdateonepassprofileLink = false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify valid One pass sign in");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			validatecreateonepassprofileLink = loginpage.isCreateOnePassProfileLinkAvailable();
			
			softas.assertTrue(validatecreateonepassprofileLink,
					jiraNumber + ":" + issueSummary + " : validated create onepass profile Link is verified");
			logExtentStatus(extentLogger, validatecreateonepassprofileLink,
					"validated create onepass profile Link is verified", jiraNumber);

			validateupdateonepassprofileLink = loginpage.isupdateonepassprofileLinkPresent();
			if (validateupdateonepassprofileLink) {
				loginpage.clickOnePassProfileLink();
				loginpage.clickReturnToCheckpoint();
			}

			softas.assertTrue(validateupdateonepassprofileLink,
					jiraNumber + ":" + issueSummary + " : Validated update onepass profile Link is verified");
			logExtentStatus(extentLogger, validateupdateonepassprofileLink,
					"Validated update onepass profile Link Link is verified", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	
	@Test(priority = 8, groups = { "chppe","chpchile" }, description = "MAFQABANG-618")
	public void onepassSignInlinks() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());
			boolean isOnePassSignInLinkAvailable, validateonepasssigninpage = false;
			
			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Verify valid One pass sign in");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			isOnePassSignInLinkAvailable = loginpage.isOnePassSignInLinkAvailable();
			if (isOnePassSignInLinkAvailable) {
				loginpage.clickOnePassProfileLink();
				validateonepasssigninpage= loginpage.isOnePassSignInPageDisplayed();
			}

			softas.assertTrue(validateonepasssigninpage,
					jiraNumber + ":" + issueSummary + " : validated  onepass profile Link ");
			logExtentStatus(extentLogger, validateonepasssigninpage,
					"validated  onepass profile Link ", jiraNumber);

			

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}
	/////////// CHP URY - New Test Cases Automation
	// This test should run before login with save user name & password
	// Hence setting the priority to 0
	@Test(priority = 0, groups = { "chpury" }, description = "MAFQABANG-530")
	public void LoginScreenDesignForFirstTimeUser() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Check the design of the login screen - First time User");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);

			boolean loginPageValidated = loginpage.isLoginPageDisplayed() && loginpage.isUsernameEmpty()
					&& loginpage.isPasswordEmpty() && loginpage.isUseOnePassPasswordLinkDisplayed()
					&& loginpage.isClientIDLinkDisplayed() && loginpage.isForgotPasswordLinkDisplayed();

			softAssert.assertTrue(loginPageValidated, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, loginPageValidated, issueSummary + ": Expected fields Validated", jiraNumber);

			boolean saveDataUnChecked = !loginpage.isRememberDataOnThisComputerEnabled();
			softAssert.assertTrue(saveDataUnChecked, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, saveDataUnChecked, issueSummary + " : Remember Data is not Enabled",
					jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 10, groups = { "chpury" }, description = "MAFQABANG-532")
	public void LoginScreenDesignForAnyUser() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Check the design of the login screen - Any User");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);

			boolean loginPageValidated = loginpage.isLoginPageDisplayed()
					&& loginpage.isUserNameAndPasswordFieldDisplayed() && loginpage.isUseOnePassPasswordLinkDisplayed()
					&& loginpage.isClientIDLinkDisplayed() && loginpage.isForgotPasswordLinkDisplayed()
					&& loginpage.isLoginFooterDisplayed() && loginpage.isCustomerServicesPortletDisplayed();

			softAssert.assertTrue(loginPageValidated, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, loginPageValidated, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softAssert.assertTrue(false, "Exception in Test");
		} finally {

			extentReports.endTest(extentLogger);
			softAssert.assertAll();
		}
	}

	@Test(priority = 11, groups = { "chpury" }, description = "MAFQABANG-536")
	public void GoToHomeFromServicesPage() throws IllegalArgumentException, IOException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber,
					"Click Services link in login page and then Go To login Homepage");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);

			boolean serviceLinkValidated = loginpage.isServiceLinkDisplayed();
			if (serviceLinkValidated) {
				loginpage.clickServiceLink();
				serviceLinkValidated = loginpage.isServiceLinkPageDisplayed();
			}
			loginpage.clickBacktoLoginPage();
			boolean backToHomepage = loginpage.isLoginPageDisplayed();
			
			//Log existing bug
			if(!serviceLinkValidated){
				testResult.setAttribute("defect", "MAFAUTO-294");
			}

			softas.assertTrue(serviceLinkValidated && backToHomepage, jiraNumber + ":" + issueSummary);
			logExtentStatus(extentLogger, serviceLinkValidated && backToHomepage, issueSummary, jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 12, groups = { "chpury", "chparg","chppe","chpchile" }, description = "MAFQABANG-542")
	public void CustomerServicePortlet() throws IOException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Customer Service Portlet in Login page");

			JSONArray listOfSearchData = jsonReader.readJSONDataFromFile(jiraNumber, extentLogger);

			for (Object searchString : listOfSearchData) {

				JSONObject jsonObjectChild = (JSONObject) searchString;

				String emailKey = "customerserviceemail";
				String email = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,emailKey,extentLogger);
	
				String numberKey = "customerservicenumber";
				String phone = jsonReader.readKeyValueFromJsonObject(jsonObjectChild,numberKey,extentLogger);
	
				LoginPage loginpage = new LoginPage(driver, ProductUrl);
	
				boolean portletValidated = loginpage.isCustomerServicesPortletDisplayed();
				softas.assertTrue(portletValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, portletValidated, issueSummary + " : Portlet displayed", jiraNumber);
	
				boolean numberValidated = loginpage.isCustomerServicesNumberDisplayed(phone);
				softas.assertTrue(numberValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, numberValidated, issueSummary + " : Validate Phone number ", numberKey, phone,
						jiraNumber);
	
				boolean emailValidated = loginpage.isCustomerServicesEmailDisplayed(email);
				softas.assertTrue(emailValidated, jiraNumber + ":" + issueSummary);
				logExtentStatus(extentLogger, emailValidated, issueSummary + " : Validate Email ", emailKey, email, jiraNumber);
	
				emailValidated = loginpage.isEmailLinkDirectsToDefaultEmailClient(email);
				softas.assertTrue(emailValidated, jiraNumber + ":Email link set to open in Default email client");
				logExtentStatus(extentLogger, emailValidated, "Email link set to open in Default email client", emailKey, email,
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

	// Commenting the test because credentails are not valid for one pass
	// @Test(priority=1, groups={"chparg"}, description="MAFQABANG-564")
	public void onePassValidation() throws Exception {
		softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "One Pass Login Validation");

			username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepassusername");
			password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepasspassword");
			clientidentifier = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".clientidentifier");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			loginpage.clickonepasslink();
			loginpage.Validateonepassloginwithusernamepwd(username, password);
			homepage = loginpage.OnepassloginEnterclientIdentifier(clientidentifier);

			softas.assertTrue(homepage.isHomePageDisplayed(), jiraNumber + ":" + issueSummary + ": Login Success");
			logExtentStatus(extentLogger, homepage.isHomePageDisplayed(),
					issueSummary + ": Login Success and page navigates to home page", jiraNumber);

		} catch (Exception exc) {
			logTestExceptionToExtentLogger(testResult.getMethod().getMethodName(), exc, extentLogger);
			softas.assertTrue(false, "Exception in Test");
		} finally {
			extentReports.endTest(extentLogger);
			softas.assertAll();
		}
	}

	@Test(priority = 13, groups = { "chpury" }, description = "MAFQABANG-579")
	public void LoginwithSaveUnamePwdUnchecked() throws IOException {
		SoftAssert softas = new SoftAssert();
		try {
			testResult = Reporter.getCurrentTestResult();
			extentLogger = setUpExtentTest(extentLogger, "Login", testResult.getMethod().getMethodName());

			String jiraNumber = testResult.getMethod().getDescription();
			String issueSummary = getIssueTitle(jiraNumber, "Login with Save my Username and Password unchecked");

			username = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".username");
			password = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".password");

			LoginPage loginpage = new LoginPage(driver, ProductUrl);
			boolean issavebothenabled = loginpage.isRememberDataOnThisComputerEnabled();
			if (issavebothenabled) {
				loginpage.SelectSaveUsernameAndPassword();
			}
			homepage = loginpage.Login(username, password);

			homepage.clickOnProductLogo();
			signoffpage = homepage.getSignOffPage();
			boolean newsessionlinkpresent = signoffpage.isNewsesssionlinkPresent();

			if (newsessionlinkpresent) {
				signoffpage.clikNewSession();
			}

			boolean isloginpagedisplayed = loginpage.isLoginButtonDisplayed();
			if (!isloginpagedisplayed) {
				logExtentNoResultsAsInfo(extentLogger, issueSummary, "Login",
						"Navigation from Signoff page is not working properly", jiraNumber);
			}
			boolean flag = loginpage.isUsernameEmpty() && loginpage.isPasswordEmpty();
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

}