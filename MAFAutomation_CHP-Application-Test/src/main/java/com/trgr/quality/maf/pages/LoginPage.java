package com.trgr.quality.maf.pages;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class LoginPage extends BasePage {

	/*
	 * This method is used to navigate to the url
	 */
	public LoginPage(WebDriver driver, String productUrl) throws IOException, IllegalArgumentException {
		super(driver);
		try {
			driver.get(productUrl);
			if (isDisplayedAdvancedButton()) {
				Thread.sleep(1000);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedlinkpage"));
				Thread.sleep(1000);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedlinkproceed"));

			}
			if (BaseTest.productUnderTest.equals("chpmex")) {
				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("//title[text()='Checkpoint Mexico Signon']")));

			}
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginButton")), 20);
		} catch (Exception e) {
			// If automatically logged in, then log off & reload URL
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".SignOff");
			try {
				if (IsPopUpWindowPresent()) {
					clickOnAlertPopUP();
				}
				if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator))) {
					elementhandler.clickElement(locator);
				}
				driver.get(productUrl);// Re-load URL
				if (BaseTest.productUnderTest.equals("chpmex")) {
					WebDriverWait wait = new WebDriverWait(driver, 60);
					wait.until(ExpectedConditions
							.presenceOfElementLocated(By.xpath("//title[text()='Checkpoint Mexico Signon']")));
				}
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : LoginPage <br>" + displayErrorMessage(exc));
			}
		}

	}

	/*
	 * This method is used to load the url
	 */
	public void LoadLoginPage(String productUrl) throws IOException, IllegalArgumentException {
		driver.get(productUrl);
		if (isDisplayedAdvancedButton()) {

			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedlinkpage"));

			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedlinkproceed"));

		}
		WebDriverFactory.waitForElementUsingWebElement(driver,
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginButton")),
				20);

	}

	/*
	 * This method is used to log into application. This will return home page drive
	 * on success
	 */
	public HomePage Login(String Username, String Password) throws IllegalArgumentException, IOException {
		try {
			//Wait and get username and password input boxes
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername")),
					20);
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername"))
					.clear();
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginPassword"))
					.clear();
			//Write on input boxes
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername"),
					Username);

			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginPassword"),
					Password);
			Thread.sleep(1000);

			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginButton"));
			if (BaseTest.productUnderTest.equals("chpmex")) {
				WebDriverWait wait = new WebDriverWait(driver, 60);
				wait.until(ExpectedConditions
						.presenceOfElementLocated(By.xpath("//title[text()='Checkpoint  | Búsquedas']")));
			}
			return new HomePage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : Login <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public static boolean isDisplayedAdvancedButton() {
		boolean flag = false;
		try {
			return driver.findElement(By.xpath("//button[@id='details-button']")).isDisplayed();
		} catch (Exception exc) {
			flag = false;
		}
		return flag;
	}

	public HomePage LoginInvalidScenarios(String Username, String Password)
			throws IllegalArgumentException, IOException {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername")),
					20);
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername"))
					.clear();
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginPassword"))
					.clear();
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername"),
					Username);

			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginPassword"),
					Password);
			Thread.sleep(1000);

			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginButton"));

			return new HomePage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : Login <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public void Validateloginwithusernamepwd(String Username, String Password)
			throws IllegalArgumentException, IOException {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername")),
					20);
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername"),
					Username);
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginPassword"),
					Password);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".updateProfileLoginButton"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : Login <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to verify error message is displayed for invalid
	 * credentials
	 */
	public boolean invalidCredentialsMsgDisplayed() {
		try {
			String errorMsg = elementhandler.getText(
					PropertiesRepository.getString(("com.trgr.maf." + BaseTest.productUnderTest + ".InvalidLoginMsg")));

			// Making sure the error message is validated for other languages.
			return errorMsg.contains("Please try again") | errorMsg.contains("Por favor inténtelo de nuevo")
					| errorMsg.contains("We don't recognize that username and/or password. Both are case sensitive.")
					| errorMsg.contains("Su usuario o contraseña es incorrecto. Por favor inténtelo de nuevo.")
					| errorMsg.contains("Su usuario o contraseña es incorrecto. Por favor inténtelo nuevamente.")
					| errorMsg.contains("We don't recognize that username and/or password.");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : invalidCredentialsMsgDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method is used to check the save both user and password check box in the
	 * login page
	 */
	public void SelectSaveUsernameAndPassword() {
		try {
			WebElement loc = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".saveboth"));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".saveboth")));
			
			// normal click is throwing Exception.class hence added java script click
			// elementhandler.clickElement(
			// PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest +
			// ".saveboth"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : SelectSaveUsernameAndPassword <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to check the save user name check box in the login page
	 */
	public void SelectSaveUsername() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".saveusernamecheckbox")));
			/*
			 * elementhandler.clickElement(PropertiesRepository .getString("com.trgr.maf." +
			 * BaseTest.productUnderTest + ".saveusernamecheckbox"));
			 */
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : SelectSaveUsername <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to uncheck the save user name check box in the login page
	 */
	public void UnselectSaveUsername() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".saveusernamecheckbox"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : UnselectSaveUsername <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to verify password saved
	 */
	public boolean isPasswordSaved(String password) {
		try {
			String pwdfield = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginPassword");

			return elementhandler.getTextFromValueAttribute(pwdfield).contains(password);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isPasswordSaved <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method is used to verify username saved
	 */
	public boolean isUsernameSaved(String username) {
		try {
			String unamefield = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername");
			return elementhandler.getTextFromValueAttribute(unamefield).contains(username);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isUsernameSaved <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method is used to verify username text box is empty
	 */
	public boolean isUsernameEmpty() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername");
			return elementhandler.getTextFromValueAttribute(locator).isEmpty();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isUsernameEmpty <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method is used to verify password text box is empty
	 */
	public boolean isPasswordEmpty() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginPassword");
			return elementhandler.getTextFromValueAttribute(locator).isEmpty();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isPasswordEmpty <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method is used to verify login page is displayed
	 */
	public boolean isLoginPageDisplayed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUsername");
			return elementhandler.getElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLoginPageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method is used to verify service link is displayed in the login page.
	 */
	public boolean isServiceLinkDisplayed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".customerservicelink");
			return elementhandler.getElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isServiceLinkDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method is used to click service link in the login page.
	 */
	public void clickServiceLink() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".customerservicelink");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickServiceLink <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to verify service link page displayed on clicking service
	 * link
	 */
	public boolean isServiceLinkPageDisplayed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".knowtheservicekeyword");
			return elementhandler.getElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isServiceLinkPageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method is used to go back to login page
	 */
	public void clickBacktoLoginPage() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".backtologinpagelink");
			elementhandler.clickElement(locator);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickBacktoLoginPage <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to click Interactive Demo Link
	 */
	public void clickInteractiveDemoLink() throws InterruptedException {
		try {
			Thread.sleep(1000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".interactivedemolink");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickInteractiveDemoLink <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to validate user is redirected to respective url on post
	 * clicking header section links
	 */
	public boolean validateRedirectedUrl() {
		boolean flag = false;
		try {
			// Set st = driver.getWindowHandles();
			// Iterator<String> it = st.iterator();
			// String parent = it.next();
			// String child = it.next();
			String parent = driver.getWindowHandles().toArray()[0].toString();
			String child = driver.getWindowHandles().toArray()[1].toString();

			driver.switchTo().window(child);
			String actualtitle = driver.getTitle();
			String expTitle = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".interactivedemotitle");
			if (expTitle.contentEquals(actualtitle)) {
				flag = true;
			} else {
				extentLogger.log(LogStatus.INFO,
						"Error in : validateRedirectedUrl <br>" + takesScreenshot_Embedded() + "<br>");
			}
			driver.close();
			driver.switchTo().window(parent);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateRedirectedUrl <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	}

	/*
	 * This method is used to verify FQA Link is available
	 */
	public boolean isFrequentQuestionsLinkAvailable() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".frequentlyaskedquestionslink");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFrequentQuestionsLinkAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method is used to click FQA link
	 */
	public void clickFrequentQuestionsLink() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".frequentlyaskedquestionslink");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator))) {
				elementhandler.clickElement(locator);
				Thread.sleep(3000);
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFrequentQuestionsLink <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to verify FQA page is displayed
	 */
	public boolean isFreqQuesPageDisplayed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".frequentquestionslabel");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(locator)))
				;

			boolean flag;
			flag = elementhandler.getElement(locator).isDisplayed();
			return flag;

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFreqQuesPageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method is used to refresh the page
	 */
	public void refereshPage() {
		driver.navigate().refresh();
	}

	/*
	 * This method is used to click on client id link
	 */
	public void clickonClientIDLink() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_clientid_link");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickonClientIDLink <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to verify client id page is displayed
	 */
	public boolean isClientIDPageDisplayed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_enterclientid_txtbox");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isClientIDPageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method is used to enter client id
	 */
	public void enterClientID(String clientid) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_enterclientid_txtbox");
			elementhandler.writeText(locator, clientid);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterClientID <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is checking the client id is displayed
	 */
	public boolean ClientIdIsDisplayed(String clientid) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_enterclientid_txtbox");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClientIdIsDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method is used to verify Forgot USername link displayed
	 */
	public boolean isForgotUsernameLinkDispalyed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_forgotusername");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isForgotUsernameLinkDispalyed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method is used to click Forgot USername link
	 */
	public void clickForgotUsernameLink() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_forgotusername");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : validateforgotusername  in onepass page <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to verify create one pass profile link displayed
	 */
	public boolean isCreateOnePassProfileLinkAvailable() {
		try {
			String locator = PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_validatecreateonepassprofilelink");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isCreateOnePassProfileLinkAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	/*
	 * This method is used to verify one pass signin link displayed
	 */

	public boolean isOnePassSignInLinkAvailable() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_onepasssigninlink");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isOnePassSignInLinkAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method is used to verify user is redirected to one pass signin page
	 */
	public boolean isOnePassSignInPageDisplayed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_onepasssigninpage");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isOnePassSignInPageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method is used to click One pass profile link
	 */
	public void clickOnePassProfileLink() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_onepasssigninlink");
			elementhandler.clickElement(locator);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnePassProfileLink<br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to verify back to checkpoint link is available
	 */
	public boolean isbacktoCheckpointLinkAvailable() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest
					+ ".loginpage_validateonepassprofilelinkwithreturntocheckpoint");
			return elementhandler.findElement(locator).isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isbacktoCheckpointLinkAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method is used to click back to checkpoint link
	 */
	public void clickbacktoCheckpointLink() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest
					+ ".loginpage_validateonepassprofilelinkwithreturntocheckpoint");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickbacktoCheckpointLink<br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to verify update one pass profile link available
	 */
	public boolean isUpdateOnePassProfileLinkDisplayed() {
		try {
			String locator = PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_validateupdatepassprofilelink");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isUpdateOnePassProfileLinkDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method is used to click on update one pass profile link
	 */
	public void clickUpdateonepassprofileLink() {
		try {
			String locator = PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_validateupdatepassprofilelink");
			elementhandler.clickElement(locator);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickUpdateonepassprofileLink <br>" + displayErrorMessage(exc));
		}
	}

	// This is duplicate method
	public boolean isupdateonepassprofileLinkPresent() {
		try {

			return elementhandler
					.findElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_validateupdatepassprofilelink"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isupdateonepassprofileLinkPresent <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void Validateheader(String property, String Validateheader) throws Exception {
		elementhandler.findElement(property).isDisplayed();
		elementhandler.findElement(property).getText().contains(Validateheader);
	}

	public boolean isUseOnePassPasswordLinkDisplayed() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".onepassloginlink");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))) {
				String linkText = elementhandler.getText(selector).trim();
				linkDisplayed = linkText.equals("Utilizar contraseña OnePass")
						|| linkText.equals("Use OnePass Password");
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isUseOnePassPasswordLinkDisplayed <br>" + displayErrorMessage(exc));
		}
		return linkDisplayed;
	}

	/*
	 * This method is used to verify client id link is available
	 */
	public boolean isClientIDLinkDisplayed() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".loginpage_clientid_link");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))) {
				String linkText = elementhandler.getText(selector).trim();
				linkDisplayed = linkText.equals("Asignar consultas al cliente")
						|| linkText.equals("Assign customer queries");
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isClientIDLinkDisplayed <br>" + displayErrorMessage(exc));
		}
		return linkDisplayed;
	}

	/*
	 * This method is used to verify forgot password link is available
	 */
	public boolean isForgotPasswordLinkDisplayed() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".loginpage_forgotpassword");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))) {
				String linkText = elementhandler.getText(selector).trim();
				linkDisplayed = linkText.equals("¿Has olvidado la contraseña?")
						|| linkText.equals("Forgot your password?") || linkText.equals("Forgot password?")
						|| linkText.equals("Recuperar contraseña");
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isForgotPasswordLinkDisplayed <br>" + displayErrorMessage(exc));
		}
		return linkDisplayed;
	}

	/*
	 * This method is used to click forgot password link
	 */
	public void clickForgotPasswordLink() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_forgotpassword"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickForgotPasswordLink <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to verify forgot password page is displayed
	 */
	public boolean isForgotCredentialsPageDisplayed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".loginpage_forgotusernamepwdvalidateheader");
			return elementhandler.getElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isForgotPasswordPageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isemailforforgotuname_pwddisplayed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_Sendemailforverifcation");
			return elementhandler.getElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isemailforforgotuname_pwddisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void enterRecoveryEmail(String email) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_Sendemailforverifcation");
			elementhandler.writeText(locator, email);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterRecoveryEmail <br>" + displayErrorMessage(exc));
		}
	}

	public void enterUserName(String username) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_username");
			elementhandler.writeText(locator, username);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterUserName <br>" + displayErrorMessage(exc));
		}
	}

	public void clickOnSubmitButton() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_submit");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSubmitButton <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isAnswerTheQuestionDisplayed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_answerthequestion");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isAnswerTheQuestionDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isContinuebtnForSendEmailDisplayed() {
		try {

			String locator = PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_Sendemailforverifcationcontinuebutton");
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf."
									+ BaseTest.productUnderTest + ".loginpage_Sendemailforverifcationcontinuebutton")),
							30);
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			// extentLogger.log(LogStatus.INFO,
			// "Error in : isContinuebtnForSendEmailDisplayed <br>" +
			// displayErrorMessage(exc));
			return false;
		}
	}

	public void clickContinueButton() {
		try {
			elementhandler.clickElement(PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".loginpage_Sendemailforverifcationcontinuebutton"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickContinueButton <br>" + displayErrorMessage(exc));

		}
	}

	public boolean isReturnToCheckpointDisplayed(String email) {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest
					+ ".loginpage_Sendemailforverifcationreturntocheckpoint");
			return elementhandler.findElement(locator).isDisplayed() && driver.getPageSource().contains(email);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isReturnToCheckpointDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void clickReturnToCheckpoint() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest
					+ ".loginpage_Sendemailforverifcationreturntocheckpoint");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickReturnToCheckpoint <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isRememberDataOnThisComputerEnabled() {
		boolean checkBoxEnabled = false;
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".saveboth");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))) {
				String checked = elementhandler.findElement(selector).getAttribute("checked");
				checkBoxEnabled = checked != null && (checked.equals("checked") || checked.equals("true"));
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isRememberDataOnThisComputerEnabled <br>" + displayErrorMessage(exc));
		}
		return checkBoxEnabled;
	}

	public void enableRememberDataOnThisComputer() {
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".saveboth");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))) {
				elementhandler.findElement(selector).click();
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enableRememberDataOnThisComputer <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isUserNameAndPasswordFieldDisplayed() {
		boolean usernameDisplayed = false, passwordDisplayed = false;
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".LoginUsername");
			usernameDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
			selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".LoginPassword");
			passwordDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isUserNameAndPasswordFieldDisplayed <br>" + displayErrorMessage(exc));
		}
		return usernameDisplayed && passwordDisplayed;
	}

	public void enterUserNameAndPassword(String user, String password) {
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".LoginUsername");
			elementhandler.writeText(selector, user);

			selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".LoginPassword");
			elementhandler.writeText(selector, password);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterUserNameAndPassword <br>" + displayErrorMessage(exc));
		}
	}

	public HomePage clickLoginButton() {
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".LoginButton");
			elementhandler.clickElement(selector);
			return new HomePage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickLoginButton <br>" + displayErrorMessage(exc));
		}
		return null;
	}

	public boolean isLoginButtonDisplayed() {
		boolean loginButtonDisplayed = false;
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".LoginButton");
			loginButtonDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLoginButtonDisplayed <br>" + displayErrorMessage(exc));
		}
		return loginButtonDisplayed;
	}

	public boolean isCustomerServicesPortletDisplayed() {
		boolean titleDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".customerserviceportlettitle");
			titleDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isCustomerServicesPortletDisplayed <br>" + displayErrorMessage(exc));
		}
		return titleDisplayed;
	}

	public boolean isCustomerServicesNumberDisplayed(String expectedNumber) {
		boolean numberDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".customerservicenumber");
			numberDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))
					&& elementhandler.findElement(selector).getText().trim().equals(expectedNumber);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isCustomerServicesNumberDisplayed <br>" + displayErrorMessage(exc));
		}
		return numberDisplayed;
	}

	public boolean isCustomerServicesEmailDisplayed(String expectedEmail) {
		boolean emailDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".customerserviceemail");
			emailDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))
					&& elementhandler.findElement(selector).getText().trim().equals(expectedEmail);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isCustomerServicesEmailDisplayed <br>" + displayErrorMessage(exc));
		}
		return emailDisplayed;
	}

	public boolean isLoginFooterDisplayed() {
		boolean loginFooterDisplayed = false;
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".loginfooter");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))) {
				String actualText = elementhandler.getText(selector).trim();
				loginFooterDisplayed = actualText.equals("© LA LEY S.A.E. e I. (2011) | Todos los derechos reservados");
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLoginFooterDisplayed <br>" + displayErrorMessage(exc));
		}
		return loginFooterDisplayed;
	}

	public void clickonepasslink() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".onepasslink"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickonepasslink <br>" + displayErrorMessage(exc));
		}
	}

	public void Validateonepassloginwithusernamepwd(String Username, String Password)
			throws IllegalArgumentException, IOException {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".onepassusernamefield")),
							20);
			elementhandler.writeText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".onepassusernamefield"), Username);
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".onepasspwd"),
					Password);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".onepassloginbutton"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : Login <br>" + displayErrorMessage(exc));
		}
	}

	public HomePage OnepassloginEnterclientIdentifier(String ClientIdentifier)
			throws IllegalArgumentException, IOException {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".onepassclientid");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 20);
			elementhandler.writeText(locator, ClientIdentifier);
			String loginlocator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".onepassloginbutton");
			elementhandler.clickElement(loginlocator);
			return new HomePage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : Login <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public boolean isEmailLinkDirectsToDefaultEmailClient(String expectedMail) {
		boolean emailHref = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".customerserviceemail");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))) {
				WebElement link = elementhandler.findElement(selector).findElement(By.tagName("a"));
				emailHref = link.getAttribute("href").equals("mailto:" + expectedMail.toLowerCase());
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isEmailLinkDirectsToDefaultEmailClient <br>" + displayErrorMessage(exc));
		}
		return emailHref;
	}

}
