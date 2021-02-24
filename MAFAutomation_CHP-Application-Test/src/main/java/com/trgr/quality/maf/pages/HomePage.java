package com.trgr.quality.maf.pages;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.handlers.BaseHandler;
import com.trgr.quality.maf.webdriver.WebDriverFactory;


public class HomePage extends BasePage {

	BaseHandler basehandler;
	String keyword;
	boolean flag = false;

	public HomePage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		try {
//			elementhandler.clickElement(
//					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"));
//			
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagefooter")),
					80);
//			if(BaseTest.productUnderTest.equals("chpmex")) {
//				WebDriverWait wait = new WebDriverWait(driver,60);
//				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//title[text()='Checkpoint  | Página Principal']")));
//			}
			if (AcceptTermsandConditionflag)
				AccepttermsandConditions();
		} catch (Exception e) {

		}
	}

	public void AccepttermsandConditions() {
		try {
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			boolean flag = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".termsandcondition"))
					.isDisplayed();
			if (flag) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".termsandcondition"));
				AcceptTermsandConditionflag = false;
			}
			driver.manage().timeouts().implicitlyWait(PropertiesRepository.getLong("global.implicit.wait"), TimeUnit.SECONDS);
		} catch (Exception ex) {
			AcceptTermsandConditionflag = false;
			driver.manage().timeouts().implicitlyWait(PropertiesRepository.getLong("global.implicit.wait"), TimeUnit.SECONDS);
		}
	}

	//This method is use to click the help button on the header
	public void clickonHelpButton() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helpbutton"));
			
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonHelpButton <br>" + displayErrorMessage(ex));
			
		}
	}


	//This method is used to verify that the help page is opened in a new window
		public boolean isHelpPageDisplayed()
		{
			boolean flag = false;
			try{
				//Set st = driver.getWindowHandles();
				//Iterator<String> it = st.iterator();
				//String parent = it.next();
				//String child = it.next();
				String parent = driver.getWindowHandles().toArray()[0].toString();
				String child = driver.getWindowHandles().toArray()[1].toString();

				driver.switchTo().window(child);
				String actualtitle = driver.getTitle();
				String expTitle = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helppagetitle");
				if (expTitle.contentEquals(actualtitle)) {
					flag = true;
				} else {
					extentLogger.log(LogStatus.INFO,
							"Error in : isHelpPageDisplayed <br>" + takesScreenshot_Embedded() + "<br>");
				}
				Thread.sleep(3000);
				driver.close();
				Thread.sleep(3000);
				driver.switchTo().window(parent);
				Thread.sleep(3000);
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : isHelpPageDisplayed <br>" + displayErrorMessage(exc));
				return false;
			}
			return flag;
		}

	public void Loadurl(String productUrl) throws IOException, IllegalArgumentException {
		driver.get(productUrl);
	}

	// verifying home page is displayed upon sign on
	public boolean isHomePageDisplayed() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo")), 30);
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo"))
					.isDisplayed()
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"))
							.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isHomePageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	
	/*
	 * This method checks to see if the search button on the home page is displayed
	 */
	public boolean isSearchButtonOnHomePageDisplayed()
	{
		try {
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchButtonOnHomePageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	
	
	//This method is used to verify that the document display page appears
		public boolean isDocumentDisplayPageDisplayed()
		{
			try {
				return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helppage"))
						.isDisplayed();
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : isHelpPageDisplayed <br>" + displayErrorMessage(exc));
				return false;
			}
		}
		
		


		//validating whether "Indicadores" widget is present in the home page
			public boolean ValidateIndicatorsTitle(String expectedText) {
			boolean flag = false;
			try {
			String locator = PropertiesRepository
			.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validateindicatortitle");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
			// driver.wait(2000);
			String actualText = elementhandler.getText(locator);
			flag = actualText.equals(expectedText);

			} catch (Exception exc) {
			flag = false;
			extentLogger.log(LogStatus.INFO, "Error in : ValidateIndicatoresTitle <br>" + displayErrorMessage(exc));
			}
			return flag;
			}
		
		
		
		
		
		
	//click on the '+' button next to "Indicadores" widget  to expand it.
		public void clickOnPlusButtonOfIndicators() {
		boolean expanded=false;
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,350)", "");
            Thread.sleep(2000);
		WebElement expand = elementhandler.getElement(
		PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".clickOnIndicatorsPlusButton"));
		expanded = expand.getAttribute("class").contains("minimize-control");
		if (!expanded) {
		((JavascriptExecutor)driver).executeScript("arguments[0].click();" ,expand);
		}


		} catch (Exception exc) {
		extentLogger.log(LogStatus.INFO, "Error in : clickOnPlusButtonOfIndicators <br>" + displayErrorMessage(exc));
		}
		}
		
	
		
	public NewsPage ClickonNewsTab() throws IllegalArgumentException, IOException {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newstablink")));
			/*WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newstablink")), 30);*/
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newstablink"));
			return new NewsPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonNewsTab <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	/*public DocumentDisplayPage clickonINPCOption() {
		try {
			
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indicatorlink"));
			return new DocumentDisplayPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickonINPCOption <br>" + displayErrorMessage(exc));
			return null;
		}
	}*/
	

//this method is used to click on the link based on the test data
    public DocumentDisplayPage clickOnGivenLink(String linkname) {

	try {
	elementhandler.getElement("partialLinkText=" + linkname + "").click();
	return new DocumentDisplayPage(driver); 
	} catch (Exception exc) {
	extentLogger.log(LogStatus.INFO, "Error in : clickOnGivenLink <br>" + linkname + displayErrorMessage(exc));
	return null;
	}

	}
    
	
	public HistoryPage ClickonHistoryLink() throws IllegalArgumentException, IOException {
		try {
			Thread.sleep(4000);
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".historylinkverify")),
							30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".historylinkverify"));
			return new HistoryPage(driver);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonHistoryLink <br>" + displayErrorMessage(exc));
			return null;
		}
	}
	public void clickSignOff() {
		
		String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".SignOff");
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			elementhandler.clickElement(locator);
			if(BaseTest.productUnderTest.equals("chpmex")) {
				WebDriverWait wait = new WebDriverWait(driver,60);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//title[text()='Checkpoint ']")));
			}
			
			
			
		} catch (Exception exc) {
			//Try java script click in case of failure
			try {
				if(IsPopUpWindowPresent()){
					clickOnAlertPopUP();
				}
				((JavascriptExecutor) driver).executeScript("arguments[0].click();",
						elementhandler.findElement(locator));
			} catch (Exception e) {
			}
			extentLogger.log(LogStatus.INFO, "Error in : clickSignOff <br>" + displayErrorMessage(exc));
		}
	}

	public AlertPage ClickonAlertLink() throws IllegalArgumentException, IOException, InterruptedException {
		try {
			Thread.sleep(3000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".myalertslink")), 60);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".myalertslink"));
			return new AlertPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonAlertLink <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	public boolean isThematicSearchBoxDisplayed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonhomepage");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isThematicSearchBoxDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isSearchBoxDisplayedHomePage() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".enterTextInSearchBox");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchBoxDisplayedHomePage <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	public void clickRefreshforThematicSearch() {
		try {
			boolean flag = isThematicSearchBoxDisplayed();
			if (flag) {
				WebDriverFactory.Refresh();
			} else {
				String selector = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ThematicSearchRadiobutton");
				elementhandler.clickElement(selector);
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickRefreshforThematicSearch <br>" + displayErrorMessage(exc));

		}
	}

	public void enterFreewordOnQuickSearch(String freeword) throws Exception {
		try {
			// for CHP Arg-entering data on the page with automation is not
			// working without refresh
			//driver.navigate().refresh();
			Thread.sleep(4000);
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage")),
					70);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage");
			elementhandler.writeText(locator, freeword);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterFreewordOnQuickSearch <br>" + displayErrorMessage(exc));
		}

	}

	public SearchPage clickOnSearchTab() throws Exception {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpagetab")),
							30);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpagetab"));
			Thread.sleep(3000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster

			return new SearchPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSearchTab <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	public SearchPage clickSearch_Test() throws Exception {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage")),
							30);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage"));
			Thread.sleep(3000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster

			return new SearchPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearch_Test <br>" + displayErrorMessage(exc));
			return null;
		}

	}


	/*
	 * This method is used to navigate to search page from home page
	 * 
	 */

	public SearchPage OpenSearchPage() throws Exception {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 40);
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
//					elementhandler.findElement(locator));
			
			elementhandler.clickElement(locator);
			
			if(BaseTest.productUnderTest.equals("chpmex")) {
				WebDriverWait wait = new WebDriverWait(driver,60);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//title[text()='Checkpoint  | Búsquedas']")));
			}
			Thread.sleep(1000);  // Need to remove this once the search
								// string is updated to more specific to
								// return results faster
			return new SearchPage(driver);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : OpenSearchPage <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public void enterThematicSearchOnQuickSearch(String thematicSearchString) {
		try {
			// Without refresh chp arg & chppe is showing error message
			boolean refreshRequired = productUnderTest.equals("chppe")
					|| productUnderTest.equals("chpchile");
			if (refreshRequired)
				WebDriverFactory.Refresh();
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonhomepage");
			elementhandler.writeText(locator, thematicSearchString);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterThematicSearchOnQuickSearch <br>" + displayErrorMessage(exc));
		}

	}

	/*
	 * Method for clicking search button on the home page.
	 */
	public SearchResultsPage clickSearch() throws Exception {
		try {
			Thread.sleep(2000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementhandler.getElement(locator));
			// elementhandler.clickElement(locator); - This Click is not working
			// for CHP URY.
			Thread.sleep(1000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
			return new SearchResultsPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearch <br>" + displayErrorMessage(exc));
			return null;
		}
	}
	
	public SearchResultsPage clickSearchwhenStandardNumberSelected() throws Exception {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnforstandardnumber");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementhandler.getElement(locator));
			// elementhandler.clickElement(locator); - This Click is not working
			// for CHP URY.
			Thread.sleep(1000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
			return new SearchResultsPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearch <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public boolean noDataEnteredForSearch() {
		try {
			Thread.sleep(2000);
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nodatagivenforsearch")),
							30);
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nodatagivenforsearch"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : NoDataEnteredForSearch <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean zeroInputwithFreeTextField() throws IllegalArgumentException, IOException {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagelink")), 30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagelink"));

			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage"));
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nodatagivenforsearch"))
					.isDisplayed();

			String Actualerrmsg = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nodatagivenforsearch"))
					.getText();
			String Expectederrmsg = "Debe completar al menos un campo de bsqueda";

			if (Actualerrmsg.contains(Expectederrmsg))
				flag = true;
			else {
				//System.out.println("Empty search with Free Text Failed");
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : zeroInputwithFreeTextField <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	public boolean ValdiateErrorMsgforNoResults(String errmsg) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".errorblock");
			String errtext = elementhandler.findElement(locator).getText();
			if (errtext.contains(errmsg))

				return flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : zeroInputwithFreeTextField <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	}

	public boolean searchWithStandardNumber(String standard_number) {
		try {
			// Entering test Data in Standard Number Field
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository.getString(
									"com.trgr.maf." + BaseTest.productUnderTest + ".standardnumberradiobtnonhomepage")),
							30);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".standardnumberradiobtnonhomepage"));
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchfieldwithstandardnumber"))
					.sendKeys(standard_number);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage"));

			// Validating elements on the Result List Page for the search data
			// entered

			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstlinkofstandardnumbersearch"));

			String PageTitle = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".documentdispalypagetitle"))
					.getText();

			if (PageTitle.contains(standard_number)) {
				//System.out.println("Search with Standarad Number validated successfully");
				flag = true;

			} else {
				//System.out.println("Result list does not correspond to the serach keyword entered");
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : searchWithStandardNumber <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;

	}

	/*
	 * This method clicks on the Clear button on the home page.
	 */
	public void clickClear() {
		try {
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonhomepage"));
			Thread.sleep(1000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickClear <br>" + displayErrorMessage(exc));
		}

	}
	
	/*
	 * This method clicks on the 'X' icon on the thematic search box to clear the contents
	 */
	public void clickXToClear() {
		try {
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clickXtoclear"));
			Thread.sleep(1000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickXToClear <br>" + displayErrorMessage(exc));
		}

	}
	
	/*
	 * Close the suggestions dropdown displayed for search criteria
	 */
	public void closeTheSuggestionsDropdownIfDisplayed()
	{
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdownclose");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(locator), 30);
			boolean isSuggestionsDrpDownDisplayed = elementhandler.findElement(locator).isDisplayed();
			if(isSuggestionsDrpDownDisplayed)
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdownclose"));
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : closeTheSuggestionsDropdownIfDisplayed <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isQuickSearchFieldsReset() {
		boolean freewordFieldReset = false, thematicFieldReset = false;
		try {

			if (isThematicSearchCheckboxDisplayed()) {
				clickonThematicSearchbox();
			}
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage"))))
				freewordFieldReset = elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage"))
						.getText().contains("");

			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonhomepage"))))
				thematicFieldReset = elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonhomepage"))
						.getText().contains("");

			return freewordFieldReset && thematicFieldReset;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isQuickSearchFieldsReset <br>" + displayErrorMessage(ex));
			return freewordFieldReset;
		}
	}

	public boolean isThematicSearchCheckboxDisplayed() {
		boolean flag = false;
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchbox_checkbox"))
					.isDisplayed();
			flag = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isThematicSearchCheckboxDisplayed <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	public void clickonThematicSearchbox() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchbox_checkbox"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickonThematicSearchbox <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isModifySearchRetainsSearchStrings() {
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage"))
					.getAttribute("value").length() > 0
					|| elementhandler
							.getElement(PropertiesRepository.getString(
									"com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonhomepage"))
							.getAttribute("value").length() > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isModifySearchRetainsSearchStrings <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	//this method opens the home page
	public HomePage openHomepage() throws Exception {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 40);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 40);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,-250)", "");
			elementhandler.clickElement(locator);
			Thread.sleep(1000);
			return new HomePage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : openHomepage <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	public LegislationPage OpenLegislationPage() throws Exception {
		try {
			Thread.sleep(4000);
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"))
					.isDisplayed()) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new LegislationPage(driver);
			}
		} catch (Exception ex) {
			try {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage"));
					Thread.sleep(3000);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new LegislationPage(driver);
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : OpenLegislationPage <br>" + displayErrorMessage(exc));
				return null;
			}
		}
		return null;

	}

	public DoctrinePage OpenDoctrinaPage() throws Exception {
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"))
					.isDisplayed()) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new DoctrinePage(driver);
			}
		} catch (Exception ex) {
			try {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage"));

				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new DoctrinePage(driver);
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : OpenDoctrinaPage <br>" + displayErrorMessage(exc));
				return null;
			}
		}
		return null;

	}

	public JurisprudencePage openJurisprudencePage() throws Exception {
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchjurisprudence"))
					.isDisplayed()) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchjurisprudence"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new JurisprudencePage(driver);
			}
		} catch (Exception ex) {
			try {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage"));

				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchjurisprudence"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new JurisprudencePage(driver);
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : openJurisprudencePage <br>" + displayErrorMessage(exc));
				return null;
			}
		}
		return null;

	}

	public FormsPage openFormsPage() throws Exception {
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchforms"))
					.isDisplayed()) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchforms"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new FormsPage(driver);
			}
		} catch (Exception ex) {
			try {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage"));

				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchforms"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new FormsPage(driver);
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : openFormsPage <br>" + displayErrorMessage(exc));
				return null;
			}
		}
		return null;

	}
	

	/*
	 * Clicking on the Herramientas link on the home page returns the handle to
	 * the Tools page
	 */
	public ToolsPage openToolsPage() throws IllegalArgumentException, IOException, InterruptedException {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolstab");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			elementhandler.clickElement(locator);
			
			Thread.sleep(3000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster

			return new ToolsPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : openToolsPage <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	public boolean isThematicDropdownDisplayedWithResults() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdownresults")),
							30);
			return WebDriverFactory.isDisplayed(driver,
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdown")))
					&& WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdownresults")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isThematicDropdownDisplayedWithResults <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void closeThematicDropdownResults() {
		try {
			if (WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdownclose"))))
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".selectthematicsearch"));
			Thread.sleep(3000);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : closeThematicDropdownResults <br>" + displayErrorMessage(ex));

		}

	}

	public boolean isSearchResultsDisplayedForFstSearchText() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thinfotext")), 30);
			return WebDriverFactory.isDisplayed(driver,
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thinfotext")))
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thinfotext"))
							.getText().contains("resultado");
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSearchResultsDisplayedForFstSearchText <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void clickOnProductLogo() throws Exception {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);			
			elementhandler.clickElement(locator);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnProductLogo <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isPreferencesLinkDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencelink"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isPreferencesLinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public PreferencePage clickPreferenceLink() throws Exception {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencelink");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			elementhandler.clickElement(locator);
			return new PreferencePage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPreferenceLink <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	public boolean verifyMyDocumentLinkDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentlink"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyMyDocumentLinkDisplayed <br>" + displayErrorMessage(e));
			return false;
		}
	}

	public MyDocumentPage openMyDocumentsPage() throws Exception {
		try {
			WebElement locator = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentlink"));
			WebDriverFactory.waitForElementUsingWebElement(driver, locator ,30);
			locator.click();
			if(BaseTest.productUnderTest.equalsIgnoreCase("chaparg")){
				Thread.sleep(8000);
			}
			Thread.sleep(1000);
			return new MyDocumentPage(driver);
			
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : openMyDocumentsPage <br>" + displayErrorMessage(e));
			return null;
		}
	}

	/*
	 * Checks to see if Advanced search link is displayed on the home page
	 * Returns true / false based on the element display
	 */
	public boolean isAdvancedSearchLinkDisplayed() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advsearch"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyadvanceSearchLinkDisplayed <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean verifyProductLogoDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyProductLogoDisplayed <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean isLoggedInUserInfoDisplayed1() {
		try {

			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".usertext"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyUsertextDisplayed <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean verifyBasicSearchDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".basicsearch"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyBasicSearchDisplayed <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean verifyNavigationSectionDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".navigationsection"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyNavigationSectionDisplayed <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean verifyWidgetDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widgetonhomepage"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyWidgetDisplayed <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean verifySignoutLinkDisplayed() {

		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignOff"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : verifySignoutLinkDisplayed <br>" + displayErrorMessage(e));
			return false;
		}
	}

	public void goToHomePage() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".hometab"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : goToHomePage <br>" + displayErrorMessage(e));
		}
	}

	/*
	 * public boolean isHelpLinkPresent() { try { WebDriverFactory
	 * .waitForElementUsingWebElement(driver,
	 * elementhandler.getElement(PropertiesRepository .getString("com.trgr.maf."
	 * + BaseTest.productUnderTest + ".helplinkonhomepage")), 20); return
	 * elementhandler .getElement(PropertiesRepository
	 * .getString("com.trgr.maf." + BaseTest.productUnderTest +
	 * ".helplinkonhomepage")) .isDisplayed(); } catch (Exception e) {
	 * extentLogger.log(LogStatus.INFO, "Error in : isHelpLinkPresent <br>" +
	 * displayErrorMessage(e)); return false; }
	 * 
	 * }
	 */

	// Click on Help Link
	public void clickHelpLink() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helplinkonhomepage"));

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickHelpLink <br>" + displayErrorMessage(e));

		}
	}

	public boolean validateHelpPage() {

		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helptextinpopup"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : validateHelpPage <br>" + displayErrorMessage(e));
			return false;
		}
	}

	/* Header Section Validation */
	public boolean isHistoryLinkPresent() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".HistoryLink"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isHistoryLinkPresent <br>" + displayErrorMessage(e));
			return false;
		}
	}

	public boolean isPreferenceLinkPresent() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencelink"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isPreferenceLinkPresent <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean isMyDocumentLinkPresent() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentlink"));
			if (element != null) {
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentlink"))
						.isDisplayed();
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isMyDocumentLinkPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;

	}

	public boolean isMyAlertLinkPresent() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".myalertslink"));
			if (element != null) {
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".myalertslink"))
						.isDisplayed();
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isMyAlertLinkPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;

	}

	public boolean isHelpLinkPresent() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helplinkonhomepage"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isHelpLinkPresent <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean isGotoCheckpointWorldLinkPresent() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".GotoCheckpointWorld"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isGotoCheckpointWorldLinkPresent <br>" + displayErrorMessage(e));
			return false;
		}
	}

	public boolean issignoffLinkPresent() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignOff"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : issignoffLinkPresent <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean isLinkUtilityLinkPresent() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LinkUitility"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isLinkUtilityLinkPresent <br>" + displayErrorMessage(e));
			return false;
		}
	}

	public LinkUtilitiesPage openLinkUtilityPage() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LinkUitility"));
			return new LinkUtilitiesPage(driver);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickLinkUtilityLink <br>" + displayErrorMessage(e));
			return null;
		}

	}

	public boolean isGotoLawOnlineLinkPresent() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".GotoLawOnline"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isGotoLawOnlineLinkPresent <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean isGotoTRIntegraLinkPresent() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".GotoTRIntegra"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isGotoTRIntegraLinkPresent <br>" + displayErrorMessage(e));
			return false;
		}
	}

	public boolean isFAQLinkPresent() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".faqslink"));
			if (element != null) {
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".faqslink"))
						.isDisplayed();
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isFAQLinkPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;

	}

	public boolean isGotoCheckpointUSALinkPresent() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".GotoChecpointUSA"));
			if (element != null) {
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".GotoChecpointUSA"))
						.isDisplayed();
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isGotoCheckpointUSALinkPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean isTaskSystemLinkPresent() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".tasksystem"));
			if (element != null) {
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".tasksystem"))
						.isDisplayed();
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isTaskSystemLinkPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean isContacUSLinkPresent() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus"));
			if (element != null) {
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus"))
						.isDisplayed();
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isContacUSLinkPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean isFirstTimeLinkPresent() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firsttime"));
			if (element != null) {
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firsttime"))
						.isDisplayed();
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isFirstTimeLinkPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean isELearningLibrayLinkPresent() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".elearning"));
			if (element != null) {
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".elearning"))
						.isDisplayed();
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isELearningLibrayLinkPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean isGoToJouranlofOnlineTribunalsPresent() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".gotojournal"));
			if (element != null) {
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".gotojournal"))
						.isDisplayed();
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isGoToJouranlofOnlineTribunalsPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean isConsultasLinkPresent() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ConsultastLink"));
			if (element != null) {
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ConsultastLink"))
						.isDisplayed();
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isConsultasLinkPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean isUserLoggedinDetailsPresent(String expdetails) {
		try {
			String loginuser_Actual = elementhandler.getText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".LoginUserDetails"));
			/*
			 * String loginuser_Expected = PropertiesRepository
			 * .getString("com.trgr.maf." + BaseTest.productUnderTest +
			 * ".LoginUserDetails_expected");
			 */
			loginuser_Actual = loginuser_Actual.replaceAll("\n", " ");
			return loginuser_Actual.contains(expdetails);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isUserLoggedinDetailsPresent <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isSignOffLinkPresent() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignOff"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSignOffLinkPresent <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	
	public boolean verifySearchWithOutInput() {
		try {
			String expected_error_msg = "Debe completar al menos un campo de bsqueda";
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage"));

			String actaul_error_msg = elementhandler.getText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".errormesg"));

			if (expected_error_msg.equals(actaul_error_msg))
				;
			{
				flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : verifySearchWithOutInput <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean validateCleanButton() {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonhomepage"));
			Thread.sleep(2000);
//			return elementhandler
//					.getText(PropertiesRepository
//							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage"))
//					.contains("");
			return true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateCleanButton <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	public SearchPage goToSearchPage() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchtab"));
			Thread.sleep(3000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster

			return new SearchPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : goToSearchPage <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	
	
	public boolean isVerifiedTestDataAsTestCase() {
		boolean flag=false;
		try {
			driver.findElement(By.xpath("//*[text()='Se ha producido un error, por favor intente más tarde.']")).isDisplayed();
		}
		catch (Exception exc) {
		
			return flag=false;
		}
		return flag;
	}
	
	
	/*
	 * This method verifies if All expected widgets present is home returns true
	 * on success, and false on failure.
	 */
	public boolean isExpectedWidgetsDisplayedInHomePage() {
		boolean flag = false;
		try {
			String allWidgets = "";

			switch (productUnderTest) {
			case "chparg":
				allWidgets = "Bsqueda rpida," + "Bsqueda avanzada," + "Mis Accesos Directos," + "Destacados,"
						+ "Consultores," + "Herramientas,Cdigo Civil y Comercial,"
						+ "Normas de consulta frecuente,Revistas";
				break;

			case "chpmex":
				allWidgets = "Bsqueda avanzada," + "Bsquedas programadas," + "Documentos en seguimiento,"
						+ "Mis Accesos Directos," + "Bsqueda por Abreviatura," + "Indicadores,"
						+ "ltimos documentos publicados," + "Normas de consulta frecuente," + "Herramientas," + "DOF,"
						+ "Histrico," + "Consultora y Soporte";
				break;

			case "chpbr":
				/*
				 * allWidgets = PropertiesRepository .getString("com.trgr.maf."
				 * + productUnderTest +
				 * ".exp_widgets_brazil").replaceAll("-",","); break;
				 */
				allWidgets = "Localização de atos (pesquisa simples):," + "Meus acessos diretos,"
						+ "Destaques Roteiros," + "Busca automática," + "Documentos seguidos,"
						+ "Ferramentas (calculadoras e create a chart)," + "Consultoria e suporte";
				break;

			case "chppy":
				allWidgets = "Servicios al cliente," + "Documentos en seguimiento," + "Mis Accesos Directos,"
						+ "Destacados," + "Bsquedas programadas," + "Normas de consulta frecuente,"
						+ "Bsqueda avanzada";
				break;

			case "chpury":
				allWidgets = "Destacados," + "Mis Accesos Directos," + "Herramientas," + "Búsquedas programadas,"
						+ "Documentos en seguimiento," + "Normas de consulta frecuente," + "Búsqueda avanzada";
				break;

			case "chppe":
				allWidgets = "Búsqueda rápida," + "Herramientas," + "Estadísticas," + "Mis Accesos Directos,"
						+ "Noticias," + "Destacados," + "Búsqueda avanzada," + "Aplicación Práctica,"
						+ " Normas de consulta frecuente," + "Libros Online";
				break;

			case "chpchile":
				allWidgets = "Búsqueda rápida," + "Herramientas," + "Estadísticas," + "Mis Accesos Directos,"
						+ "Noticias," + "Destacados," + "Búsqueda avanzada," + "Aplicación Práctica,"
						+ "Normas de consulta frecuente," + "Biblioteca Online," + "Tributación Avanzada,"
						+ "Fichas de Análisis," + "Documentos en seguimiento," + "Estadísticas,"
						+ "Arancel Globalizado," + "Arancel Aduanero";
				break;
			}

			String Expected_widgets[] = allWidgets.split(",");

			List<WebElement> Actual_Widget = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widgetonhomepage"));

			// if (Expected_widgets.length == Actual_Widget.size())
			for (int i = 0; i < Actual_Widget.size(); i++) {
				String actualitem = Actual_Widget.get(i).getText().trim();
				for (int j = 0; j < Expected_widgets.length; j++) {
					if (actualitem.equalsIgnoreCase(Expected_widgets[j].trim())) {
						flag = true;
						break;
					}
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isExpectedWidgetsDisplayedInHomePage <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method verifies if All expected widgets present is home returns true
	 * on success, and false on failure.
	 */
	public boolean isDisplayedWidgetsEquals(String Expected_widgets[]) {
		boolean flag = false;
		boolean allMatchFound = true;
		try {
			List<WebElement> Actual_Widget = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widgetonhomepage"));

			allMatchFound = true;
			flag = false;
			if (Expected_widgets.length == Actual_Widget.size())
				for (int i = 0; i < Actual_Widget.size(); i++) {
					String actualitem = Actual_Widget.get(i).getText().trim();
					for (int j = 0; j < Expected_widgets.length; j++) {
						if (actualitem.equalsIgnoreCase(Expected_widgets[j].trim())) {
							flag = true;
							break;
						}
					}
					allMatchFound &= flag;
					if (!allMatchFound)
						break;
				}
			allMatchFound &= flag;

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDisplayedWidgetsEquals <br>" + displayErrorMessage(exc));
			allMatchFound = false;
		}
		return allMatchFound;
	}

	/*
	 * This method checks if thematic search radio button is selected
	 */
	public boolean isThematcSearchSelected() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ThematicSearchRadiobutton"))
					.isSelected();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isThematcSearchSelected <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method checks if thematic search radio button is selected
	 */
	public boolean isThematicRadioButtonDisplayed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ThematicSearchRadiobutton"))
					.isDisplayed();

		} catch (Exception exc) {

			return false;
		}

	}

	public boolean verifyThematicSearchOptions() {
		flag = false;
		
		try {

			if (!isThematcSearchSelected()) {
				clickThematicSearchRadioButton();
				flag = isThematicSearchBoxDisplayed() && 
						isFreewordFieldDisplayed();
			} else {
				flag = isSearchBoxDisplayedHomePage() ;
					//	isFreewordFieldDisplayed();
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyThematicSearchOptions <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	}
	
	
	
	

	/*
	 * This method is used to verify thematic search box displayed in home page
	 */
	
	public boolean isFreewordSearchboxDisplayed() {
		boolean flag = false;
		try {
			Thread.sleep(1000);
			flag = elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFreewordSearchboxDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	public void clickStandardNumberRadioButton() {
		try {
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".StandardNumberRadiobutton"));
			Thread.sleep(1000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickStandardNumberRadioButton <br>" + displayErrorMessage(exc));

		}
	}
	/*
	 * This method is used to verify standard search radio button  is displayed
	 */
	
	public boolean isStandardSearchRadioButtonDisplayed() {
		boolean flag = false;
		try {
			Thread.sleep(1000);
			flag = elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".StandardNumberRadiobutton"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isStandardSearchRadioButtonDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method is used to verify standard search box is displayed
	 */
	public boolean isStandardSearchboxDisplayed() {
		boolean flag = false;
		try {
			Thread.sleep(1000);
			flag = elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchfieldwithstandardnumber"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isStandardSearchboxDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isClientLogoDisplayed() {
		WebDriverFactory.waitForElementUsingWebElement(driver,
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clientlogo")),
				30);
		return elementhandler
				.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clientlogo"))
				.isDisplayed();
	}

	public boolean isProductLogoDisplayed() throws Exception {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".productlogo"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isProductLogoDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean isQuickSearchSectionDisplayed() throws Exception {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".quicksearchsection"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isQuickSearchSectionDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	
	
	public void clickThematicsearchLinkHomePage() {
		try {
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".smartsearchlinkhome"));
			Thread.sleep(1000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickThematicSearchRadioButton <br>" + displayErrorMessage(exc));
		}
	}
	public void clickThematicSearchRadioButton()

	{
		try {
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ThematicSearchRadiobutton"));
			Thread.sleep(1000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickThematicSearchRadioButton <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks signoff, and returns SignOff page
	 */
	public SignOffPage getSignOffPage() {
		SignOffPage signOffPage = null;
		try {

			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignOff")),
					30);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignOff")));
			Thread.sleep(3000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SignOff"));
			signOffPage = new SignOffPage(driver);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getSignOffPage <br>" + displayErrorMessage(exc));
		}
		return signOffPage;
	}

	// This method is used to click on FAQ link
	public void clickOnFAQ() {
		try {
			
			WebElement locator = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".faqslink"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",locator);
			WebDriverFactory.waitForElementUsingWebElement(driver, locator, 30);
			locator.click();
			Thread.sleep(1000); // wait for FAQ's page to open in a different tab
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnFAQ <br>" + displayErrorMessage(exc));
		}
	}

	// This method is used to verify FAQ questions expand and collapse option
	public boolean verifyExpandAndCollapseFaq() {
		boolean flag = false;

		try {
			switchToChildTab(1);
			flag = verfiyFaqQuestionsMaximize() && verfiyFaqQuestionsMinimize();
			Thread.sleep(4000);
			closeAllChildTabs();
            Thread.sleep(1000);
		} catch (Exception exc) {
			flag = false;
			extentLogger.log(LogStatus.INFO, "Error in : verifyExpandAndCollapseFaq <br>" + displayErrorMessage(exc));
		}
		return flag;
	}

	public boolean verfiyFaqQuestionsMaximize() {

		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".faqquestion"));
			Thread.sleep(3000);
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".faqanswer"))
					.isDisplayed();

		} catch (Exception exc) {

			extentLogger.log(LogStatus.INFO, "Error in : verfiyFaqQuestionsMaximize <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	public boolean verfiyFaqQuestionsMinimize() {
		boolean flag = false;
		try {

			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".faqquestion"));
			Thread.sleep(3000);
			if (elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".faqanswer"))
					.isDisplayed())

				flag = false;
			else
				flag = true;
		} catch (Exception exc) {
			flag = false;
			extentLogger.log(LogStatus.INFO, "Error in : verfiyFaqQuestionsMinimize <br>" + displayErrorMessage(exc));
		}
		return flag;
	}

	public boolean ValidateFooterText(String expectedText) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validatecopyright");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
			String actualText = elementhandler.getText(locator);
			// String expectedText =
			// PropertiesRepository.getString("com.trgr.maf." +
			// BaseTest.productUnderTest + ".validatecopyrightText");
			flag = actualText.equals(expectedText);

		} catch (Exception exc) {
			flag = false;
			extentLogger.log(LogStatus.INFO, "Error in : ValidateFooterText <br>" + displayErrorMessage(exc));
		}
		return flag;
	}


	
	

	
	public boolean ValidateFooterLinkText(String expectedText) {
		boolean flag = false;
		try {

			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".policyusagelink");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
			String actualText = elementhandler.getText(locator);
			flag = actualText.equals(expectedText);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ValidateFooterLinkText <br>" + displayErrorMessage(exc));
			flag = false;

		}
		return flag;
	}


	
	public boolean ValidateFooterPrivacyLinks() {
		boolean flag = false;
		String validateTerms;
		String validateConditions;
		String validatePrivacy;

		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validateTerms")),
					20);
			if (elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validateTerms"))
					.isDisplayed()) {
				validateTerms = elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validateTerms"))
						.getAttribute("href");
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validateTerms"));
				if (driver.getCurrentUrl().contains(validateTerms)) {
					driver.navigate().back();
					flag = true;
				}
			}
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validateConditions")),
							20);
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validateConditions"))
					.isDisplayed()) {
				validateConditions = elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validateConditions"))
						.getAttribute("href");
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validateConditions"));
				if (driver.getCurrentUrl().contains(validateConditions)) {
					driver.navigate().back();
					flag = true;
				}
			}
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validatePrivacy")),
					20);
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validatePrivacy"))
					.isDisplayed()) {
				validatePrivacy = elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validatePrivacy"))
						.getAttribute("href");
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".validatePrivacy"));
				if (driver.getCurrentUrl().contains(validatePrivacy)) {
					driver.navigate().back();
					flag = true;
				}
			}

		} catch (Exception exc) {
			flag = false;
			extentLogger.log(LogStatus.INFO, "Error in : ValidateFooterPrivacyLinks <br>" + displayErrorMessage(exc));
		}
		return flag;
	}

	public CourseAndSeminar openCourseAndSeminar() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".courseandseminartab"));
			return new CourseAndSeminar(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : openCourseAndSeminar <br>" + displayErrorMessage(ex));
			return null;
		}

	}

	public boolean verifyThematicareadropdowns() {
		boolean flag = false;
		Actions action = new Actions(driver);
		try {
			Thread.sleep(1000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown"));
			List<WebElement> dropdownoptions = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown"));
			for (WebElement options : dropdownoptions) {
				List<WebElement> thematicDropdownoptions = options.findElements(By.tagName("option"));
				for (WebElement subjectareaoptions : thematicDropdownoptions) {
					
					if(!BaseTest.productUnderTest.equalsIgnoreCase("chpmex")) {
					action.moveToElement(subjectareaoptions).click().build().perform();
					}
					if (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widgetonhomepage"))
							.isDisplayed())
						flag = true;

					else
						flag = false;
				}

			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyThematicareadropdowns <br>" + displayErrorMessage(exc));

		}
		return flag;

	}

	public boolean verifySubjectAreaDrop() {
		boolean flag = false;
		try {
			String subjectarea_dropdown_values[] = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".subjectdropdownvalue").split("-");
			List<String> expitems = new ArrayList<String>();
			for (String item : subjectarea_dropdown_values)
				expitems.add(item);

			List<WebElement> dropdownoptions = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown"));
			for (WebElement options : dropdownoptions) {
				List<WebElement> thematicDropdownoptions = options.findElements(By.tagName("option"));
				for (int i = 0; i < thematicDropdownoptions.size(); i++) {
					String actualitem = thematicDropdownoptions.get(i).getText();
					if (expitems.contains(actualitem))
						flag = true;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : verifySubjectAreaDrop <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;

	}

	public void enterFirstInputTextBySuggestions(String firstsearchtermbysuggestion) {
		try {
			elementhandler.writeText(
					PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".enterTextInSearchBox"),
					firstsearchtermbysuggestion);
			Thread.sleep(1000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstelement"));
			Thread.sleep(1000);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterFirstInputTextBySuggestions <br>" + displayErrorMessage(exc));

		}
	}

	public void enterSecondInputTextBySuggestions(String secondsearchtermbysuggestion) {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".enterTextInSearchBox")), 20);
			elementhandler.writeText(
					PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".enterTextInSearchBox"),
					secondsearchtermbysuggestion);
			Thread.sleep(1000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".secondelement"));
			Thread.sleep(1000);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterSecondInputTextBySuggestions <br>" + displayErrorMessage(exc));

		}
	}

	public void enterThirdInputTextBySuggestions(String thirdsearchtermbysuggestion) {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".enterTextInSearchBox")), 60);
			elementhandler.writeText(
					PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".enterTextInSearchBox"),
					thirdsearchtermbysuggestion);

			Thread.sleep(1000);
		//	elementhandler.clickElement(
		//			PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thirdelement"));
			Thread.sleep(1000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterThirdInputTextBySuggestions <br>" + displayErrorMessage(exc));

		}
	}

	public boolean validateCleanButtonInThematicSearchSuggestion() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonhomepage"));
			return elementhandler
					.getText(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonhomepage"))
					.contains("")
					&& elementhandler
							.getText(PropertiesRepository.getString(
									"com.trgr.maf." + BaseTest.productUnderTest + ".searchbytermtxboxsecond"))
							.contains("")
					&& elementhandler
							.getText(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbytermtxboxthird"))
							.contains("");
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateCleanButton <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	public boolean isIntelligentToolTipDisplayed() {
		boolean flag = false;
		String exptooltiptext = "inteligente es un modo de";
		String actualtooltiptext = "";
		try {

			elementhandler.hoverOverMenuItem(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".intelligenttooltip"));

			/*WebElement tooltipmesg = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".intelligenttooltip"));*/
			
			/*elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".intelligenttooltip"));*/
			
			actualtooltiptext = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".intelligenttooltip")).getAttribute("title");

			String acttooltiptext = actualtooltiptext.replaceAll("[\\\r\\\n]+", "");
			

			if (acttooltiptext.contains(exptooltiptext))
				flag = true;
			else
				flag = false;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyIntelligentToolTip <br>" + displayErrorMessage(exc));

		}
		return flag;
	}

	public void enterSmartSearch(String smartsearchword) {
		try {
			Thread.sleep(2000);
			
			WebElement e=elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".intelligentsearchword"));
			e.clear();
			e.sendKeys(smartsearchword);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterSmartSearch <br>" + displayErrorMessage(exc));

		}
	}

	public void clickSmartSearchRadioButton() {
		try {
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".smartsearchlinkhome"));
			Thread.sleep(2000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSmartSearchRadioButton <br>" + displayErrorMessage(exc));

		}
	}

	/*
	 * Checks if 'Follow Document Widget' exist in the page returns true on
	 * success
	 */
	public boolean isFollowDocumentWidgetDisplayed() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFollowDocumentWidgetDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Checks if 'Follow Document Widget' is having empty list of items returns
	 * true on success
	 */
	public boolean isFollowDocumentWidgetEmpty() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_empty");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isFollowDocumentWidgetEmpty <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Checks if 'Follow Document Widget' is not having empty list of items
	 * returns true on success
	 */
	public boolean isFollowDocumentWidgetNotEmpty() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_empty");
			return !(WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector)));
		} catch (Exception ex) {
			return true;
		}

	}

	/*
	 * Checks if 'Follow Document Widget' is Expanded returns true on success
	 */
	public boolean isFollowDocumentWidgetExpanded() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_expanded");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFollowDocumentWidgetExpanded <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Checks if 'Follow Document Widget' is Collapsed returns true on success
	 */
	public boolean isFollowDocumentWidgetCollapsed() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_collapsed");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFollowDocumentWidgetCollapsed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * This method changes the view of 'Follow Document Widget' (Collapse /
	 * Expand)
	 */
	public void changeFollowDocumentWidgetView() {
		try {
			if (isFollowDocumentWidgetExpanded())
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_expanded"));
			else if (isFollowDocumentWidgetCollapsed())
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_collapsed"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : changeFollowDocumentWidgetView <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method expands the view of 'Follow Document Widget'
	 */
	public void expandFollowDocumentWidgetView() {

		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_changeview"))
					.getAttribute("class");

			if (changeView.contains("minimize-control"))
				return;
			else
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_changeview"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : expandFollowDocumentWidgetView <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method collapses the view of 'Follow Document Widget'
	 */
	public void collapseFollowDocumentWidgetView() {

		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_changeview"))
					.getAttribute("class");

			if (changeView.contains("maximize-control"))
				return;
			else
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_changeview"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : collapseFollowDocumentWidgetView <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method checks if the view of 'Follow Document Widget' is expanded or
	 * not returns true on success
	 */
	public boolean isFollowDocumentWidgetViewExpanded() {
		boolean expanded = false;
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_changeview"))
					.getAttribute("class");
			if (changeView.contains("minimize-control"))
				expanded = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFollowDocumentWidgetViewExpanded <br>" + displayErrorMessage(ex));
			expanded = false;
		}
		return expanded;
	}

	/*
	 * This method checks if the view of 'Follow Document Widget' is collapsed
	 * or not returns true on success
	 */
	public boolean isFollowDocumentWidgetViewCollapsed() {
		boolean expanded = false;
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_changeview"))
					.getAttribute("class");
			if (changeView.contains("maximize-control"))
				expanded = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFollowDocumentWidgetViewCollapsed <br>" + displayErrorMessage(ex));
			expanded = false;
		}
		return expanded;
	}

	/*
	 * Checks if the 'alertName' present in the 'Follow Document Widget' returns
	 * true on success
	 */
	public boolean isAlertPresentInFollowDocumentWidget(String alertName) {
		boolean alertPresent = false;
		try {
			String newalertname = alertName.trim().toLowerCase().toString();
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_alertnames");
			List<WebElement> nameColumn = elementhandler.findElements(selector);
			for (int rowNum = 0; rowNum < nameColumn.size(); rowNum++) {
				String actualName = nameColumn.get(rowNum).getText().trim().toLowerCase().toString();
				if (newalertname.contains(actualName)) {
					alertPresent = true;
					break;
				}
			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isAlertPresentInFollowDocumentWidget <br>" + displayErrorMessage(ex));
			alertPresent = false;
		}
		return alertPresent;
	}

	//check if alertname present after delete also
	public boolean isAlertPresentInFollowDocumentWidgetforchpmex(String alertName) {
		boolean alertPresent = false;
		try {
			String newalertname = alertName.trim().toLowerCase().toString();
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_alertnames");
			List<WebElement> nameColumn = elementhandler.findElements(selector);
			for (int rowNum = 0; rowNum < nameColumn.size(); rowNum++) {
				String actualName = nameColumn.get(rowNum).getText().trim().toLowerCase().toString();
				if (newalertname.contains(actualName)) {
					alertPresent = true;
					break;
				}
			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isAlertPresentInFollowDocumentWidget <br>" + displayErrorMessage(ex));
			alertPresent = false;
		}
		return alertPresent;
	}
	/*
	 * Checks click on remove link for the given alert in 'Follow Document
	 * Widget'
	 */
	public void clickRemoveLinkInFollowDocumentWidget(String alertName) {
		boolean alertPresent = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_alertnames");
			List<WebElement> nameColumn = elementhandler.findElements(selector);
			for (int rowNum = 0; rowNum < nameColumn.size(); rowNum++) {
				String actualName = nameColumn.get(rowNum).getText();
				alertPresent = alertName.trim().contains(actualName.trim());
				if (alertPresent) {
//					selector = PropertiesRepository
//							.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_deletelinks");
					selector = "xpath=//*[@id='widgethtmlidtrackitAlerts']//a[contains(text(),'Remover')]";
					List<WebElement> deleteColumn = elementhandler.findElements(selector);
					WebElement ele = deleteColumn.get(rowNum);
					
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",ele);
					
					//WebDriverFactory.waitForElementUsingWebElement(driver, ele, 50);
					ele.click();
					break;
				}
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickRemoveLinkInFollowDocumentWidget <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * Checks if 'Follow Document Widget' delete confirmation question is
	 * displayed or not. returns true on success
	 */
	public boolean isFollowDocumentDeleteQuestionDisplayed() {
		boolean deleteQuestionPresent = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_delete_question");
			deleteQuestionPresent = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFollowDocumentDeleteQuestionDisplayed <br>" + displayErrorMessage(ex));
			deleteQuestionPresent = false;
		}
		return deleteQuestionPresent;
	}

	/*
	 * Checks if 'Follow Document Widget' delete confirmation question contains
	 * the document title returns true on success
	 */
	public boolean isFollowDocumentDeleteMessageContains(String alertName) {
		boolean deleteQuestionPresent = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_delete_question_alertname");
			deleteQuestionPresent = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector))
					&& elementhandler.findElement(selector).getText().contains(alertName.trim());
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFollowDocumentDeleteMessageContains " + alertName + "<br>" + displayErrorMessage(ex));
			deleteQuestionPresent = false;
		}
		return deleteQuestionPresent;
	}

	/*
	 * Checks if 'Follow Document Widget' delete confirmation question displayed
	 * as expected returns true on success
	 */
	public boolean isFollowDocumentDeleteMessageDisplayed(String alertName) {
		boolean deleteQuestionPresent = false;
		String actualText = null;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_delete_question_message");
			deleteQuestionPresent = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
			if (deleteQuestionPresent) {
				actualText = elementhandler.findElement(selector).getText();
				deleteQuestionPresent = (actualText != null) && actualText.contains(alertName.trim())
						&& actualText.contains("Esta seguro que desea eliminar");
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isFollowDocumentDeleteMessageDisplayed " + alertName + "<br>"
					+ displayErrorMessage(ex));
			deleteQuestionPresent = false;
		}
		return deleteQuestionPresent;
	}

	/*
	 * Checks click on 'Yes' button on 'Follow Document Delete Question'
	 */
	public void clickFollowDocumentDeleteYesButton() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_delete_yesbutton");
			elementhandler.clickElement(selector);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickFollowDocumentDeleteYesButton <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * Checks click on 'No' button on 'Follow Document Delete Question'
	 */
	public void clickFollowDocumentDeleteNoButton() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_followdoc_delete_nobutton");
			elementhandler.clickElement(selector);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickFollowDocumentDeleteNoButton <br>" + displayErrorMessage(ex));
		}
	}
	// verifying Scheduled search widget availability in homepage returns true
	// on success

	public boolean verifyScheduledSearchWidgetDispalyed() {
		try {
			String value = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(value));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyScheduledSearchWidgetDispalyed <br>" + displayErrorMessage(e));
			return false;
		}

	}

	// Verifies whether the Scheduled search widget is in Expanded view return
	// true on success

	public boolean isScheduledSearchWidgetExpanded() {
		boolean flag = false;
		try {
			Thread.sleep(500);
			WebElement we = elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".shedule_widget"));
			String changeView = we.getAttribute("class");

			if (changeView.contains("control minimize-control"))
				flag = true;
			else
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".shedule_widget"));
			flag = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isScheduledSearchWidgetExpanded <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;

	}

	// Verifies whether the Scheduled search widget is in Collapsed view return
	// true on success
	public boolean isScheduledSearchWidgetCollapsed() {
		try {
			String value = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_collapsed");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(value));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isScheduledSearcgWidgetExpanded <br>" + displayErrorMessage(e));
			return false;
		}
	}

	// Verifies whether the Scheduled search widget is Empty return true on
	// success
	public boolean isScheduledSearchWidgetEmpty() {
		boolean flag = false;

		try {

			String name = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_list");

			List<WebElement> namecolumn = elementhandler.findElements(name);
			int alertcount = namecolumn.size();

			if (alertcount == 0) {
				String value = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_empty");
				flag = true;
				verifyMessageforEmptyScheduledSearch();
				return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(value));
			} else {
				extentLogger.log(LogStatus.INFO,
						"Scheduled Search Widget contains alert.Empty message can not be validated");
				flag = true;
			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isScheduledSearchWidgetEmpty <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	// Verifying the message when Scheduled Search Widget is Empty
	public boolean verifyMessageforEmptyScheduledSearch() {
		boolean flag = false;
		try {
			String acterrmsg = elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_empty"));
			String experrmsg = "Actualmente no se registran bï¿½squedas programadas. Para programar una bï¿½squeda, realice una consulta y despuï¿½s seleccione ï¿½Guardar y programar la bï¿½squedaï¿½ en el menï¿½ ï¿½Resultado de la bï¿½squedaï¿½. Si desea informaciï¿½n detallada acerca de esta funcionalidad haga haga clic aquï¿½.";

			if (experrmsg.trim().contentEquals(acterrmsg.trim())) {
				extentLogger.log(LogStatus.INFO, "No Scheduled Search Vailable");
				flag = true;
			} else
				extentLogger.log(LogStatus.INFO, "Empty Message validation for Scheduled Search failed");
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isScheduledSearchWidgetEmpty <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	// Verifies whether the Scheduled search widget contains rows in it return
	// true on success
	public boolean isScheduledSearchWidgetNotEmpty() {
		try {
			String value = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_empty");
			return !(WebDriverFactory.isDisplayed(driver, elementhandler.getElement(value)));
		} catch (Exception e) {
			return true;
		}
	}

	// Expand the Scheduled Search view in HomePage

	public void expandScheduledSearchView() {
		try {
			String changeview = elementhandler
					.getElement("com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_changeview")
					.getAttribute("class");

			if (changeview.contains("minimize-control"))
				return;
			else
				elementhandler.clickElement(
						"com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_changeview");

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : expandScheduledSearchView <br>" + displayErrorMessage(e));
		}
	}

	// Collapse Scheduled Search view in HomePage

	public void collpaseScheduledSearchView() {
		try {
			String changeview = elementhandler
					.getElement("com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_changeview")
					.getAttribute("class");

			if (changeview.contains("maximize-control"))
				return;
			else
				elementhandler.clickElement(
						"com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_changeview");

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : collpaseScheduledSearchView <br>" + displayErrorMessage(e));
		}

	}

	// Verifying whether alert available in the Schedule search Widget
	public boolean isAlertPresentinScheduleSearchWidget(String alertname) {
		boolean alertnamepresent = false;
		try {
			String name = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_list");

			List<WebElement> namecolumn = elementhandler.findElements(name);

			for (int rownum = 0; rownum < namecolumn.size(); rownum++) {
				String actualname = namecolumn.get(rownum).getAttribute("innerHTML") ;
				if (actualname != null && actualname.trim().contains(alertname.trim())) {
					alertnamepresent = true;
					break;
				}
			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isAlertPresentinScheduleSearchWidget <br>" + displayErrorMessage(e));
			alertnamepresent = false;
		}
		return alertnamepresent;
	}

	// Removing the Scheduled Search from the List
	public boolean verifyingRemoveScheduleSearchfromHomePage(String alertname) {
		boolean alertnamepresent = false;
		try {
			String name = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_list");

			List<WebElement> namecolumn = elementhandler.findElements(name);
			for (int rownum = 0; rownum < namecolumn.size(); rownum++) {

				WebElement current_element = namecolumn.get(rownum);
				String actualname = current_element.getAttribute("innerHTML");
				alertnamepresent = (actualname != null) && actualname.trim().contains(alertname.trim());

				if (alertnamepresent) {
					String removelinks = PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_deletelinks");

					List<WebElement> deletecolumn = elementhandler.findElements(removelinks);

					WebElement ele1 = deletecolumn.get(rownum);

					((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele1);
					alertnamepresent = true;

					break;
				}

			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isAlertPresentinScheduleSearchWidget <br>" + displayErrorMessage(e));
		}
		return alertnamepresent;
	}

	
	public void deleteAlertLatestHomeWidject() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widjetdeletefirstalert");
			Thread.sleep(1000);
			elementhandler.clickElement(selector);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickFollowDocumentDeleteNoButton <br>" + displayErrorMessage(ex));
		}
	}
	// Selecting Yes Button to Delete the Scheduled Search

	public void clickScheduledSeacrhDeleteYesButton() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_scheduledsearch_delete_yesbutton");
			if(
			elementhandler
			.getElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".home_widget_scheduledsearch_delete_yesbutton"))
			.isDisplayed()) {
				Thread.sleep(4000);
			
			elementhandler.clickElement(selector);}
			else
			{
				System.out.println("clickScheduledSeacrhDeleteYesButton is not displayed");
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickFollowDocumentDeleteYesButton <br>" + displayErrorMessage(ex));
		}
	}

	// Selecting No Button while deleting Scheduled Search
	public void clickScheduledSeacrhDeleteNoButton() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_widget_scheduledsearch_delete_nobutton");
			elementhandler.clickElement(selector);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickFollowDocumentDeleteNoButton <br>" + displayErrorMessage(ex));
		}
	}

	// verifying view all availability in Scheduled search Widget
	public boolean isViewAllLinkVisibile() {
		boolean flag = false;
		try {
			elementhandler
					.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_viewmore_link"))
					.isDisplayed();
			flag = true;
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isViewAllLinkVisibile <br>" + displayErrorMessage(e));
		}
		return flag;
	}

	// Click on view All Link
	public void clickOnViewAllLink() {
		try {
			Thread.sleep(3000);
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf."
							+ BaseTest.productUnderTest + ".home_widget_scheduledsearch_viewmore_link")),
					30);
			elementhandler.clickElement(PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".home_widget_scheduledsearch_viewmore_link"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnViewAllLink <br>" + displayErrorMessage(e));
		}
	}

	public void expandDOFWidgetView() {

		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,700)", "");
			WebElement we = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".dof_widget_expand"));
			String changeView = we.getAttribute("class");

			if (changeView.contains("control minimize-control"))
				return;
			else
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".dof_widget_expand"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : expandDOFWidgetView <br>" + displayErrorMessage(ex));
		}
	}

	public SearchResultsPage selectYearAndMonth(String expmonth, String expyear) {

		boolean datenotfound = true;
		try {
			while (datenotfound) {
				String actmonth = elementhandler
						.getText(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".month"));
				String actyear = elementhandler
						.getText(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".year"));
				if (actmonth.equals(expmonth) && actyear.equals(expyear)) {
					Thread.sleep(1000);
					selectDate();
					datenotfound = false;
				} else {
					elementhandler.clickElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".calender_previous"));
				}
			}
			return new SearchResultsPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectYearAndMonth <br>" + displayErrorMessage(ex));
			return null;
		}
	}
	
	

	public void selectDate() {
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".home_alldate_list");
			List<WebElement> alldates = elementhandler.findElements(selector);
			for (WebElement ele : alldates) {
				String date = ele.getText();
				if (date.equalsIgnoreCase("12")) {
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("window.scrollBy(0,700)", "");
					ele.click();
					break;
				}
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectDate <br>" + displayErrorMessage(ex));
		}
	}

	// This method selects subject area from Thematic dropdown
	public void SelectThematicareadropdowns(String exp_option) {
		try {
			Thread.sleep(500);

			elementhandler.selectByVisibleText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown"),
					exp_option);

			/*
			 * elementhandler.clickElement(
			 * PropertiesRepository.getString("com.trgr.maf." +
			 * BaseTest.productUnderTest + ".thematicdropdown"));
			 * List<WebElement> dropdownoptions = elementhandler.findElements(
			 * PropertiesRepository.getString("com.trgr.maf." +
			 * BaseTest.productUnderTest + ".thematicdropdown")); for
			 * (WebElement options : dropdownoptions) { List<WebElement>
			 * thematicDropdownoptions =
			 * options.findElements(By.tagName("option")); for (WebElement
			 * subjectareaoptions : thematicDropdownoptions) { if
			 * (exp_option.equals(subjectareaoptions.getText()))
			 * subjectareaoptions.click(); Thread.sleep(1000); } }
			 */
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : SelectThematicareadropdowns <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method expands the view of 'Latest Document published Widget'
	 */
	public void expandLatestDocumentPublishedWidgetView() {

		try {

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,600)", "");
			WebElement we = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".latest_doc_published"));
			String changeView = we.getAttribute("class");

			if (changeView.contains("control minimize-control"))
				return;
			else
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".latest_doc_published"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : expandLatestDocumentPublishedWidgetView <br>" + displayErrorMessage(ex));
		}
	}

	public void clickSeeMoreLinkInDocPublishedWidget() {

		try {
			Thread.sleep(2000);
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".home_doc_published_seemorelink"))
					.isDisplayed())
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".home_doc_published_seemorelink"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickSeeMoreLinkInDocPublishedWidget <br>" + displayErrorMessage(ex));

		}
	}

	public int getDoctrineLinksInPublishedWidget(String expLabel) {
		int doctrinecount = 0;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".publishedwidget_doctrine_linkscount");
			List<WebElement> doclist = elementhandler.findElements(selector);
			doctrinecount = doclist.size();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getDoctrineLinksInPublishedWidget <br>" + displayErrorMessage(ex));

		}
		return doctrinecount;
	}

	public int getJurisprudenceLinksInPublishedWidget(String expLabel) {
		int jurisprudencecount = 0;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".publishedwidget_jurisprudence_linkscount");
			List<WebElement> doclist = elementhandler.findElements(selector);
			jurisprudencecount = doclist.size();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getJurisprudenceLinksInPublishedWidget <br>" + displayErrorMessage(ex));

		}
		return jurisprudencecount;
	}

	public int getLegislationLinksInPublishedWidget(String expLabel) {
		int legislationcount = 0;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".publishedwidget_legislation_linkscount");
			List<WebElement> doclist = elementhandler.findElements(selector);
			legislationcount = doclist.size();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getLegislationLinksInPublishedWidget <br>" + displayErrorMessage(ex));

		}
		return legislationcount;
	}

	public void expandSearchByAbbrevation() {

		try {
			
			WebElement we = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".abbrevation_widget_expand"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", we);
			String changeView = we.getAttribute("class");

			if (changeView.contains("control minimize-control"))
				return;
			else {
				Thread.sleep(2000);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".abbrevation_widget_expand"));
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : expandSearchByAbbrevation <br>" + displayErrorMessage(ex));
		}
	}

	public void enterAbbrevationField(String expabbrevation) {
		try {
			Thread.sleep(2000);
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".abbrevation_field"),
					expabbrevation);
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".abbrevation_field_exp"));
			Thread.sleep(2000);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterAbbrevationField <br>" + displayErrorMessage(ex));
		}
	}

	public void enterAbbrevationArticle(String exparticle) {
		try {
			Thread.sleep(2000);
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".abbrevation_widget_article"),
					exparticle);
             Thread.sleep(2000);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterAbbrevationArticle <br>" + displayErrorMessage(ex));
		}
	}

	public boolean validateCleanButtonInAbbrevationWidget() {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".abbrevation_widget_clear"));
			return elementhandler
					.getText(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".abbrevation_field"))
					.contains("")
					&& elementhandler
							.getText(PropertiesRepository.getString(
									"com.trgr.maf." + BaseTest.productUnderTest + ".abbrevation_widget_article"))
							.contains("");
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : validateCleanButtonInAbbrevationWidget <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	public DocumentDisplayPage clickSearchInAbbrevationWidget() throws Exception {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".abbrevation_widget_search"));
			Thread.sleep(3000);

			return new DocumentDisplayPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickSearchInAbbrevationWidget <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	public void clickOnContactUs() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnContactUs <br>" + displayErrorMessage(exc));
		}
	}

	public boolean verifyContactUS(String name, String email, String phone, String company, String message) {

		boolean flag = false;
		try {

			switchToChildTab(1);
			WebElement helpdropdown = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus_helpdropdown"));
			Select dropdown = new Select(helpdropdown);
			String dropdownvalue = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus_helpdropdown_value");
			dropdown.selectByValue(dropdownvalue);
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus_name"),
					name);
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus_email"),
					email);
			elementhandler.writeText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus_telephone"), phone);
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus_company"),
					company);
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus_message"),
					message);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus_send"));
			String actual_success_message = elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus_successmessage"));
			String exp_success_message = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contactus_expsuccess_message");
			
			if (actual_success_message.equals(exp_success_message)) {
				flag = true;
			}
			closeAllChildTabs();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyContactUS <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void clickOneLearningLink() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".elearning"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOneLearningLink <br>" + displayErrorMessage(exc));
		}
	}

	public void ClickonGoToJournalOnlineCourt() {
		try {
			elementhandler
					.clickElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".gotojournal"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : ClickonGoToJournalOnlineCourt <br>" + displayErrorMessage(exc));
		}
	}

	public boolean VerifyFreeSearchAvailable() {
		boolean flag = false;
		try {
			if (!isFreeSearchSelected()) {
				String s = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freeSearchRadiobutton");
				elementhandler.clickElement(s);
			}
			flag = elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage"))
					.isDisplayed();

		} catch (Exception exc) {
			flag = false;
			extentLogger.log(LogStatus.INFO, "Error in : selectFreeSearchRadioButton <br>" + displayErrorMessage(exc));
		}
		return flag;
	}

	public boolean isFreeSearchSelected() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freeSearchRadiobutton"))
					.isSelected();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFreeSearchSelected <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	public boolean verifySearchByTermAvaialble() {
		boolean flag = false;
		try {
			if (!isSearchTermSelected())
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SearchbytermRadiobutton"));

			flag = elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonhomepage"))
					.isDisplayed();

		} catch (Exception exc) {
			flag = false;
			extentLogger.log(LogStatus.INFO, "Error in : selectSearchTermRadioButton <br>" + displayErrorMessage(exc));
		}
		return flag;
	}

	public boolean isSearchTermSelected() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".SearchbytermRadiobutton"))
					.isSelected();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchTermSelected <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	public boolean verifyNaturalLanguageSearchAvailable() {
		boolean flag = false;
		try {
			if (!isNaturalLanguageSelected())
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".naturallanguageRadiobutton"));

			flag = elementhandler
					.findElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".naturallanguagesearchboxonhomepage"))
					.isDisplayed();

		} catch (Exception exc) {
			flag = false;
			extentLogger.log(LogStatus.INFO,
					"Error in : selectNaturalLanguageRadioButton <br>" + displayErrorMessage(exc));
		}
		return flag;
	}

	public boolean isNaturalLanguageSelected() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".naturallanguageRadiobutton"))
					.isSelected();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isNaturalLanguageSelected <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	public boolean isMyShortcutWidgetAvailable() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".myshortcutwidget"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isMyShortcutWidgetAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	
	public boolean isDocumentFollowupExpanded() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followdocumentchangeview"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocumentFollowupExpanded <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void expandMyShortcut() {
		boolean expanded = false;
		try {
			WebElement expand = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".expandMyShortcut"));
			expanded = expand.getAttribute("class").contains("minimize-control");
			if (!expanded) {
				expand.click();
			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : expandMyShortcut <br>" + displayErrorMessage(ex));
		}
	}

	
	public void expandDocumentFollowup() {
		boolean expanded = false;
		try {
			WebElement expand = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".followdocumentchangeview"));
			expanded = expand.getAttribute("class").contains("minimize-control");
			Thread.sleep(1000);
			if (!expanded) {
				expand.click();
			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : expandDocumentFollowup <br>" + displayErrorMessage(ex));
		}
	}
	public boolean isMyShortcutTextAvailable() {
		try {
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".myShortcutText"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isMyShortcutTextAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	
	public boolean isDocumentFollowupTextAvailable() {
		try {
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linkitememptyrecordsxpath"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isMyShortcutTextAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	/*
	 * This method returns in the information available on the MyShortcut Widget
	 * Returns a string with text from getText element else will return empty
	 * string on the exception
	 */
	public String getMyShortcutTextAvailable() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".myShortcutText"))
					.getText();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isMyShortcutTextAvailable <br>" + displayErrorMessage(exc));
			return "";
		}
	}

	public void clicksearchPageTab() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".searchpagetab"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : expandMyShortcut <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isContentTreeAvailable() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contenttreeonsearchpage"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isContentTreeAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isCreateShortcutAvailable() {
		try {
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".createshortcut"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCreateShortcutAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	
	public boolean isDocumentFollowupAlertPresent() {
		try {
			String xpath = "//div[@id='widgethtmlidtrackitAlerts']//*[@class='delAlert']";
			return driver.findElement(By.xpath(xpath)).isDisplayed();
					

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCreateShortcutAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void clickCreateShortcutLink() {
		try {
			List<WebElement> elements = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".createshortcut"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elements.get(0));
			elements.get(0).click();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCreateShortcutLink <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isCreateShortcutPopupAvailable() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".createshortcutpopup"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCreateShortcutAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	
	public void writeNameinShortcutpopup() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nameinshortcutpopup"))
					.sendKeys("Legis");
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : writeNameinShortcutpopup <br>" + displayErrorMessage(exc));
		}

	}

	public void clickSaveButton() {
		try {
			elementhandler
					.clickElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".savebutton"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveButton <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isSuccessMessageDisplayed() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".successmessage")),
					20);
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".successmessage"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSuccessMessageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void clickAcceptButton() {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clickacceptinshortcut")),
							20);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".clickacceptinshortcut"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickAcceptButton <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isLinkinShortcutWidgetAvailable() {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linkinshortcutwidget")),
							30);
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linkinshortcutwidget"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isLinkinShortcutWidgetAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isShortcutLinkAvailable() {
		try {
			Thread.sleep(1000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".shortcutlink")), 20);
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".shortcutlink"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isShortcutLinkAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	
	public void removeLinkDocumentFollowup() {
		
		try {
		
				elementhandler.findElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".alertdocumentfollowupname")).click();
				Thread.sleep(1000);
				
				String locator2 = PropertiesRepository.getString("com.trgr.maf."+productUnderTest+ ".removealertyesbutton");
				WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator2), 30);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
						elementhandler.findElement(locator2));
				elementhandler.clickElement(locator2);
				Thread.sleep(3000);
					
			

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : removeLinkDocumentFollowup <br>" + displayErrorMessage(exc));
			flag = false;
		}
	}

	public boolean isExactShortcutLinkAvailable(String linkname) {
		boolean flag = false;
		try {
			Thread.sleep(1000);
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".allshortcutlinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);

			for (int i = 0; i < alllinks.size(); i++) {
				String link = alllinks.get(i).getText();
				if (link.contains(linkname)) {
					flag = true;
					break;
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isShortcutLinkAvailable <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isRemoverinShortcutWidgetAvailable() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".removerinshortcutwidget")), 20);
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".removerinshortcutwidget"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isRemoverinShortcutWidgetAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void clickRemoverinShortcutWidget() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".removerinshortcutwidget")), 20);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".removerinshortcutwidget"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickRemoverinShortcutWidget <br>" + displayErrorMessage(ex));
		}
	}

	
	public void clickRemoverinShortcutWidget(String linkname) {
		try {
			String locator = "xpath=.//*[@id='quickLinksContainer']//a[text()='" + linkname
					+ "']/../a[@class='delete']";
			elementhandler.clickElement(locator);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickRemoverinShortcutWidget <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isMyShortcutLinkDisplayed() {
		try {
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".shortcutlink"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isMyShortcutLinkDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isLatestPublishedWidgetAvailable() {
		try {
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latestpublishedwidget")));
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latestpublishedwidget"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isLatestPublishedWidgetAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isLegislationinLatestpublishedAvailable() {
		try {
			Thread.sleep(2000);
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".legislationinlatest"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isLegislationinLatestpublishedAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isJurisprudenceinLatestpublishedAvailable() {
		try {
			Thread.sleep(2000);
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudenceinlatest"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isJurisprudenceinLatestpublishedAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isDoctrineinLatestpublishedAvailable() {
		try {
			Thread.sleep(2000);
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".doctrineinlatest"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDoctrineinLatestpublishedAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void clickShowLessLatestPublishedDocument() {
		try {
			Thread.sleep(2000);
			elementhandler
					.clickElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".showless"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickShowLessLatestPublishedDocument <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isBodyofAdvanceSearchDisplayed() {
		try {
			Thread.sleep(2000);
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".bodyofadvancesearchwidget"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isBodyofAdvanceSearchDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void collapseAdvanceSearchWidget() {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".collapseadvancesearchwidget"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : collapseAdvanceSearchWidget <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isBodyofShortcutDisplayed() {
		try {
			Thread.sleep(2000);
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".bodyofshortcutwidget"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isBodyofShortcutDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void collapseMyShortcutWidget() {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".collapseshortcutwidget"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : collapseMyShortcutWidget <br>" + displayErrorMessage(ex));
		}
	}

	public DocumentDisplayPage clickOnLinkinLatestPublished() throws Exception {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clickondocumentinlatestpublished"));
			Thread.sleep(3000);

			return new DocumentDisplayPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnLinkinLatestPublished <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	public boolean verifyFAQContent(String Subjectarea) {
		
		boolean flag = false;
		try {
			Thread.sleep(2000);
			switch (Subjectarea) {

			case "Fiscal":
				Thread.sleep(500);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_seemorelinks"));
				String faq[] = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".Fiscal_FAQ_list")
						.split("--");
				List<String> expfaqlist = new ArrayList<String>();
				for (String faqitem : faq)
					expfaqlist.add(faqitem);
				List<WebElement> fiscallist = elementhandler.findElements(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_fiscallist"));
				if (expfaqlist.size() == fiscallist.size()) {
					for (int i = 0; i < fiscallist.size(); i++) {
						String actualitem = fiscallist.get(i).getText();
						if (expfaqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;

			case "Mercantil":
				Thread.sleep(500);
				String Mercantilfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".Mercantil_FAQ_list").split("-");
				List<String> expmercantilfaqlist = new ArrayList<String>();
				for (String faqitem : Mercantilfaq)
					expmercantilfaqlist.add(faqitem);
				List<WebElement> mercantillist = elementhandler.findElements(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_tradelist"));
				if (expmercantilfaqlist.size() == mercantillist.size()) {
					for (int i = 0; i < mercantillist.size(); i++) {
						String actualitem = mercantillist.get(i).getText();
						if (expmercantilfaqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;

			case "Penal":
				Thread.sleep(500);
				String Penalfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".Penal_FAQ_list").split("--");
				List<String> exppenalfaqlist = new ArrayList<String>();
				for (String faqitem : Penalfaq)
					exppenalfaqlist.add(faqitem);
				List<WebElement> penallist = elementhandler.findElements(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_penallist"));
				if (exppenalfaqlist.size() == penallist.size()) {
					for (int i = 0; i < penallist.size(); i++) {
						String actualitem = penallist.get(i).getText();
						if (exppenalfaqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;

			case "Civil":
				Thread.sleep(500);
				String civilfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".Civil_FAQ_list").split("-");
				List<String> exp_civil_faqlist = new ArrayList<String>();
				for (String faqitem : civilfaq)
					exp_civil_faqlist.add(faqitem);
				List<WebElement> civillist = elementhandler.findElements(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_civillist"));
				if (exp_civil_faqlist.size() == civillist.size()) {
					for (int i = 0; i < civillist.size(); i++) {
						String actualitem = civillist.get(i).getText();
						if (exp_civil_faqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;

			case "Financiero":
				Thread.sleep(500);
				String finfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".Finance_FAQ_list").split("--");
				List<String> exp_fin_faqlist = new ArrayList<String>();
				for (String faqitem : finfaq)
					exp_fin_faqlist.add(faqitem);
				List<WebElement> finlist = elementhandler.findElements(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_financelist"));
				if (exp_fin_faqlist.size() == finlist.size()) {
					for (int i = 0; i < finlist.size(); i++) {
						String actualitem = finlist.get(i).getText();
						if (exp_fin_faqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;

			case "Laboral":
				Thread.sleep(500);
				String laborfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".Labour_FAQ_list").split("--");
				List<String> exp_labour_faqlist = new ArrayList<String>();
				for (String faqitem : laborfaq)
					exp_labour_faqlist.add(faqitem);
				List<WebElement> labourlist = elementhandler.findElements(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_labourlist"));
				if (exp_labour_faqlist.size() == labourlist.size()) {
					for (int i = 0; i < labourlist.size(); i++) {
						String actualitem = labourlist.get(i).getText();
						if (exp_labour_faqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;
			case "Seguridad Social":
				Thread.sleep(500);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".FAQ_Social_seemorelinks"));
				String socialfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".Social_FAQ_list").split("--");
				List<String> exp_social_faqlist = new ArrayList<String>();
				for (String faqitem : socialfaq)
					exp_social_faqlist.add(faqitem);
				List<WebElement> Sociallist = elementhandler.findElements(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_sociallist"));
				if (exp_social_faqlist.size() == Sociallist.size()) {
					for (int i = 0; i < Sociallist.size(); i++) {
						String actualitem = Sociallist.get(i).getText();
						if (exp_social_faqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;
			case "Contabilidad y Auditoría":
				Thread.sleep(500);
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".FAQ_audit_seemorelinks"));
				String Auditfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".AccountAndAudit_FAQ_list").split("--");
				List<String> exp_audit_faqlist = new ArrayList<String>();
				for (String faqitem : Auditfaq)
					exp_audit_faqlist.add(faqitem);
				List<WebElement> Auditlist = elementhandler.findElements(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_accountandauditlist"));
				if (exp_audit_faqlist.size() == Auditlist.size()) {
					for (int i = 0; i < Auditlist.size(); i++) {
						String actualitem = Auditlist.get(i).getText();
						if (exp_audit_faqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;

			case "Comercio Exterior":
				Thread.sleep(500);
				String Foreigntradefaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".ForeignTrade_FAQ_list").split("--");
				List<String> exp_Foreigntrade_faqlist = new ArrayList<String>();
				for (String faqitem : Foreigntradefaq)
					exp_Foreigntrade_faqlist.add(faqitem);
				List<WebElement> Foreigntradelist = elementhandler.findElements(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_foreigntradelist"));
				if (exp_Foreigntrade_faqlist.size() == Foreigntradelist.size()) {
					for (int i = 0; i < Foreigntradelist.size(); i++) {
						String actualitem = Foreigntradelist.get(i).getText();
						if (exp_Foreigntrade_faqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;
			case "Administrativo":
				Thread.sleep(500);
				String Adminfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".Administrative_FAQ_list").split("--");
				List<String> exp_Admin_faqlist = new ArrayList<String>();
				for (String faqitem : Adminfaq)
					exp_Admin_faqlist.add(faqitem);
				List<WebElement> Adminlist = elementhandler.findElements(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_administrativelist"));
				if (exp_Admin_faqlist.size() == Adminlist.size()) {
					for (int i = 0; i < Adminlist.size(); i++) {
						String actualitem = Adminlist.get(i).getText();
						if (exp_Admin_faqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;

			case "Amparo":
				Thread.sleep(500);
				String Protectionfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".Protection_FAQ_list").split("--");
				List<String> exp_Protection_faqlist = new ArrayList<String>();
				for (String faqitem : Protectionfaq)
					exp_Protection_faqlist.add(faqitem);
				List<WebElement> Protectionlist = elementhandler.findElements(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_protectionlist"));
				if (exp_Protection_faqlist.size() == Protectionlist.size()) {
					for (int i = 0; i < Protectionlist.size(); i++) {

						String actualitem = Protectionlist.get(i).getText();
						if (exp_Protection_faqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;

			case "Constitucional":
				Thread.sleep(500);
				String Consfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".constitutional_FAQ_list").split("--");
				List<String> exp_Cons_faqlist = new ArrayList<String>();
				for (String faqitem : Consfaq)
					exp_Cons_faqlist.add(faqitem);
				List<WebElement> Conslist = elementhandler.findElements(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_constitunationallist"));
				if (exp_Cons_faqlist.size() == Conslist.size()) {
					for (int i = 0; i < Conslist.size(); i++) {
						String actualitem = Conslist.get(i).getText();
						if (exp_Cons_faqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;
			case "Corporativo":
				Thread.sleep(500);
				String Corfaq[] = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".corporate_FAQ_list").split("--");
				List<String> exp_Cor_faqlist = new ArrayList<String>();
				for (String faqitem : Corfaq)
					exp_Cor_faqlist.add(faqitem);
				List<WebElement> Corlist = elementhandler.findElements(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_corporatelist"));
				if (exp_Cor_faqlist.size() == Corlist.size()) {
					for (int i = 0; i < Corlist.size(); i++) {
						String actualitem = Corlist.get(i).getText();
						if (exp_Cor_faqlist.contains(actualitem)) {
							flag = true;
						}
					}
				}
				break;

			/*
			 * case "Todas":
			 * elementhandler.clickElement(PropertiesRepository.getString(
			 * "com.trgr.maf." + productUnderTest + ".FAQ_all_seemorelinks"));
			 * String Todasfaq[] =
			 * PropertiesRepository.getString("com.trgr.maf." + productUnderTest
			 * + ".Todas_FAQ_list").split("--"); List<String>
			 * exp_Todas_faqlist=new ArrayList<String>(); for (String faqitem :
			 * Todasfaq) exp_Todas_faqlist.add(faqitem); List<WebElement>
			 * Todaslist=elementhandler.findElements(PropertiesRepository.
			 * getString("com.trgr.maf." + productUnderTest +
			 * ".FAQ_widget_Alllist"));
			 * if(exp_Todas_faqlist.size()==Todaslist.size()) { for(int i=0;
			 * i<Todaslist.size(); i++) { String
			 * actualitem=Todaslist.get(i).getText();
			 * if(exp_Todas_faqlist.contains(actualitem)) { flag=true; } } }
			 * break;
			 */

			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyFAQContent <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	public void expandFAQWidget() {

		try {
			Thread.sleep(500);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,600)", "");
			WebElement we = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_expand"));
			String changeView = we.getAttribute("class");

			if (changeView.contains("control minimize-control"))
				return;
			else
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".FAQ_widget_expand"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : expandFAQWidget <br>" + displayErrorMessage(ex));
		}
	}

	public boolean thematicAreaDropdownContainsValue(String expectedOption) {
		boolean flag = false;
		try {
			WebElement dropdownoption = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown"));
			List<WebElement> thematicDropdownoptions = dropdownoption.findElements(By.tagName("option"));

			for (int i = 0; i < thematicDropdownoptions.size(); i++) {
				String actualitem = thematicDropdownoptions.get(i).getText();
				if (expectedOption.toUpperCase().trim().equals(actualitem.toUpperCase().trim())) {
					flag = true;
					break;
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : thematicAreaDropdownContainsValue <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean noSearchResultsDisplayed() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".noresults"))
					.isDisplayed();
		} catch (Exception exc) {
			return false;

		}
	}

	public boolean errorBlockDisplayed() {
		try {

			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".errorblock"))
					.isDisplayed();
		} catch (Exception exc) {
			return false;

		}
	}

	/*
	 * This method verifies if logged in Date duisplayed in Page Header returns
	 * true on success, false on failure
	 */
	public boolean isLoggedinDateDispalyed() {
		try {
			Thread.sleep(1000);
			String todaysDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".usertext");
			return elementhandler.getText(locator).contains(todaysDate);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLoggedinDateDispalyed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method verifies all links present in Page Header returns true on
	 * success, false on failure
	 */
	public boolean isAllLinksDisplayedInHeader() {
		boolean flag = false;
		try {
			String explinks[] = { "Ir a Checkpoint World", "Ir a La Ley Online", "Cerrar Sesion", "Links Utiles",
					"Ayuda", "Historial", "Preferencias", "Mis documentos", "Mis Alertas" };
			List<WebElement> alllinks = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".alllinksinhomepageheader"));

			if (explinks.length == alllinks.size()) {
				for (int i = 0; i < alllinks.size(); i++) {
					String actlink = alllinks.get(i).getText();
					for (int j = 0; j < (explinks.length); j++) {
						String actlink1 = explinks[j].trim().toString();
						if (actlink1.equalsIgnoreCase(actlink)) {
							flag = true;
							break;
						}
					}
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isAllLinksDisplayedInHeader <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method verifies all Tabs present in Page Header returns true on
	 * success, false on failure
	 */
	public boolean isAllTabsDisplayed() {
		boolean flag = false;
		try {
			String exptabs[] = { "Pagina Principal", "Busquedas", "Novedades", "Herramientas" };

			List<WebElement> alltabs = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".alltabs"));
			if (exptabs.length == alltabs.size()) {
				for (int i = 0; i < alltabs.size(); i++) {
					String acttab = alltabs.get(i).getText();
					for (int j = 0; j < (exptabs.length); j++) {
						String acttab1 = exptabs[j].trim().toString();
						if (acttab1.equalsIgnoreCase(acttab)) {
							flag = true;
							break;
						}
					}
				}
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	/*
	 * This method verifies all options present in Thematic Area drop down
	 * returns true on success, false on failure
	 */
	public boolean isThematicAreaDropdownOptionsAvailable() {
		boolean flag = false;
		try {
			String exptdropdowns = "";

			switch (productUnderTest) {
			case "chppy":
				exptdropdowns = "Todas,Fiscal,Laboral y Seguridad Social,Contabilidad y Auditoria,Sociedades";
				break;
			case "chpury":
				exptdropdowns = "Todas,Fiscal,Laboral y Seguridad Social,Contabilidad y Comercial";
				break;
			}

			String expDropdownsArray[] = exptdropdowns.split(",");

			List<WebElement> alltabs = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Thematic_Area_DropdownOptions"));
			if (expDropdownsArray.length == alltabs.size()) {
				for (int i = 0; i < alltabs.size(); i++) {
					String actdrpdown = alltabs.get(i).getText();
					for (int j = 0; j < (expDropdownsArray.length); j++) {
						String actdrpdown1 = expDropdownsArray[j].trim().toString();
						if (actdrpdown1.equalsIgnoreCase(actdrpdown)) {
							flag = true;
							break;
						}
					}
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isThematicAreaDropdownOptionsAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	}

	public boolean isFooterAvailableinHomePage() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagefooter"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFooterAvailableinHomePage <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method checks if terms link displayed in footer It also validates
	 * the expected link text returns true on success
	 */
	public boolean isTermsLinkDisplayedInFooter() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".termslinkinfooter");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))) {
				String actualText = elementhandler.getText(selector).trim();
				linkDisplayed = actualText.equals("Condiciones de uso y políticas de privacidad");
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isTermsLinkDisplayedInFooter <br>" + displayErrorMessage(exc));
		}
		return linkDisplayed;
	}

	public void selectGivenValueFromThematicDropdown(String valueOfThematicCombo) {
		try {
			elementhandler.selectByVisibleText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown"),
					valueOfThematicCombo);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : selectGivenValueFromThematicDropdown <br>" + displayErrorMessage(ex));

		}
	}

	public boolean verifywidgetsinhomepageforContabilidadThematicArea() {
		boolean flag = false;
		try {
			String Expected_widget[] = { "Servicios al cliente", "Documentos en seguimiento", "Mis Accesos Directos",
					"Destacados", "Bï¿½squedas programadas", "Bï¿½squeda avanzada" };

			List<WebElement> Actual_Widget3 = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widgetonhomepage"));

			if (Expected_widget.length == Actual_Widget3.size())
				for (int i = 0; i < Actual_Widget3.size(); i++) {
					String actualitem = Actual_Widget3.get(i).getText();
					for (int j = 0; j < (Expected_widget.length); j++) {
						if (actualitem.equalsIgnoreCase(Expected_widget[j].trim().toString())) {
							flag = true;
							break;
						}
					}
				}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifywidgetsinhomepageforContabilidadThematicArea <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks if the current tab is having Orange color returns true
	 * on success
	 */
	public boolean isCurrentTabColorEqualsOrange() throws Exception {
		boolean flag = false;
		try {
			WebElement searchatb = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".curtab"));
			String color = searchatb.getCssValue("color");
			String hex = Color.fromString(color).asHex();
			if (hex.contains("#f89f29"))
				flag = true;

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isCurrentTabColorEqualsOrange <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks if the current tab is Home or not returns true on
	 * success
	 */
	public boolean isCurrentTabEqualsHome() throws Exception {
		boolean flag = false;
		try {
			WebElement currentTab = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".curtab"));
			flag = currentTab.getAttribute("id").equals("home");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCurrentTabEqualsHome <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks if the current tab is Search or not returns true on
	 * success
	 */
	public boolean isCurrentTabEqualsSearch() throws Exception {
		boolean flag = false;
		try {
			WebElement currentTab = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".curtab"));
			flag = currentTab.getAttribute("id").equals("search");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCurrentTabEqualsSearch <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks if the current tab is News or not returns true on
	 * success
	 */
	public boolean isCurrentTabEqualsNews() throws Exception {
		boolean flag = false;
		try {
			WebElement currentTab = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".curtab"));
			flag = currentTab.getAttribute("id").equals("news");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCurrentTabEqualsNews <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks if the current tab is Tools or not returns true on
	 * success
	 */
	public boolean isCurrentTabEqualsTools() throws Exception {
		boolean flag = false;
		try {
			WebElement currentTab = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".curtab"));
			flag = currentTab.getAttribute("id").equals("tools");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCurrentTabEqualsTools <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void minimizeScheduledSearchWidget() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".scheduledsearchminimizecontrol"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".scheduledsearchminimizecontrol"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : minimizeScheduledSearchWidget <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isScheduledSearchWidgetMaximized() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".scheduledsearchminimizecontrol");
			WebElement element = elementhandler.getElement(locator);
			String minimizecontrol = element.getAttribute("class");

			if (minimizecontrol.contains("control minimize-control")) {
				flag = true;
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isScheduledSearchWidgetMaximized <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void maximizeScheduledSearchWidget() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".scheduledsearchmaximizecontrol"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".scheduledsearchmaximizecontrol"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : maximizeScheduledSearchWidget <br>" + displayErrorMessage(exc));
		}
	}

	public String getFreewordTextOnHomePage() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage");
			String text = elementhandler.getElement(selector).getAttribute("value");
			return text;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getFreewordTextOnHomePage <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public String getThematicTextOnHomePage() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonhomepage");
			String text = elementhandler.getElement(selector).getAttribute("value");
			return text;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getThematicTextOnHomePage <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public String getStandardNumberTextOnHomePage() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchfieldwithstandardnumber");
			String text = elementhandler.getText(selector);
			return text;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getStandardNumberTextOnHomePage <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public boolean clickSearchAndValidateErrorText(String expectedText) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage");
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".emptysearcherroralert");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementhandler.getElement(locator));
			String text = elementhandler.getText(selector);
			return text.contains(expectedText);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickSearchAndValidateErrorText <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean clickSearchAndValidateNoDocsFound(String expectedText) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage");
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".errorblock");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementhandler.getElement(locator));
			Thread.sleep(3000);
			String text = elementhandler.getText(selector);
			return text.contains(expectedText);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickSearchAndValidateNoDocsFound <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isSuggestionsEmptyErrorPresent(String expectedText) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nosuggestionserror");
			Thread.sleep(3000);
			String text = elementhandler.getText(locator);
			return text.toUpperCase().contains(expectedText.toUpperCase());
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSuggestionsEmptyErrorPresent <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void enterStandardNumberOnQuickSearch(String standardNumber) {
		try {
			// Without refresh chp arg & chppe is showing error message
			boolean refreshRequired = productUnderTest.equals("chparg") || productUnderTest.equals("chppe")
					|| productUnderTest.equals("chpchile");
			if (refreshRequired)
				WebDriverFactory.Refresh();
			elementhandler.writeText(
					PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchfieldwithstandardnumber"),
					standardNumber);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterStandardNumberOnQuickSearch <br>" + displayErrorMessage(exc));
		}

	}

	/*
	 * This method verifies the search with standard number in Homepage
	 
	public boolean isStandardNumberFieldDisplayed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".searchfieldwithstandardnumber"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isStandardNumberFieldDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}*/

	public boolean selectTitleInSearchSuggestions(String title) {
		boolean selected = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".searchsuggestionslist");
			
			//added newly
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(selector), 40);
			
			WebElement elements = elementhandler.findElement(selector);
			 List<WebElement>  titlesList = elements.findElements(By.tagName("span"));
			if(titlesList.size()==0) {
				String loc = "xpath=//span[@class='thTerm'][@title='DEPOSITO CONVENIDO']";
				WebElement ele = elementhandler.findElement(loc);
				ele.click();
			}
			else {
			for (int row = 0; row < titlesList.size(); row++) {
				if (titlesList.get(row).getText().trim().equalsIgnoreCase(title.trim())) {
					titlesList.get(row).click();
					Thread.sleep(2000);
					selected = true;
					break;
				}
			}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : selectTitleInSearchSuggestions <br>" + displayErrorMessage(exc));
			selected = false;
		}
		return selected;

	}

	public boolean isTitlePresentInSearchSuggestions(String title) {
		boolean titleFound = false;
		try {
			Thread.sleep(2000);
			
		
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".searchsuggestionslist");
			
			//Added newly:
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(selector), 40);
			
			//Thread.sleep(2000);
			List<WebElement> titlesList = elementhandler.findElements(selector);
			for (int row = 0; row < titlesList.size(); row++) {
				titleFound = titlesList.get(row).getText().trim().equalsIgnoreCase(title.trim());
				if (titleFound)
					break;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isTitlePresentInSearchSuggestions <br>" + displayErrorMessage(exc));
		}
		return titleFound;
	}

	public boolean isSecondThematicSearchBoxDisplayed() {
		boolean boxdisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".sndthematicsearchbox");
			boxdisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSecondThematicSearchBoxDisplayed <br>" + displayErrorMessage(exc));
		}
		return boxdisplayed;
	}

	public boolean isSecondThematicSearchBoxNotDisplayed() {
		boolean boxdisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".sndthematicsearchbox");
			boxdisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			return true; // Exception is expected if element is not displayed
		}
		return !boxdisplayed;
	}

	public boolean isThirdThematicSearchBoxDisplayed() {
		boolean boxdisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thrdthematicsearchbox");
			boxdisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isThirdThematicSearchBoxDisplayed <br>" + displayErrorMessage(exc));
		}
		return boxdisplayed;
	}

	public boolean isThirdThematicSearchBoxNotDisplayed() {
		boolean boxdisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thrdthematicsearchbox");
			Thread.sleep(2000);
			boxdisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			return true; // Exception is expected if element is not displayed
		}
		return !boxdisplayed;
	}

	public void enterSecondThematicSearchText(String text) {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".sndthematicsearchbox");
			Thread.sleep(2000);
			elementhandler.writeText(selector, text);
			Thread.sleep(1000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterSecondThematicSearchText <br>" + displayErrorMessage(exc));
		}
	}

	public void enterThirdThematicSearchText(String text) {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thrdthematicsearchbox");
			Thread.sleep(2000);
			elementhandler.writeText(selector, text);
			Thread.sleep(1000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterThirdThematicSearchText <br>" + displayErrorMessage(exc));
		}
	}

	public void clickOnScheduledSearchWidgetClickHere() {
		try {
			Thread.sleep(100);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".scheduledsearchwidget_clickhere")));
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".scheduledsearchwidget_clickhere"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickOnScheduledSearchWidgetClickHere <br>" + displayErrorMessage(exc));
		}
	}

	public void clickOnFollowDocumentWidgetClickHere() {
		try {
			Thread.sleep(100);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".followdocumentwidget_clickhere")));
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".followdocumentwidget_clickhere"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickOnFollowDocumentWidgetClickHere <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isRedirectedUrlEquals(String url) {
		boolean flag = false;

		try {
			Thread.sleep(1000);
			switchToChildTab(1);
			Thread.sleep(5000);
		
			String acturl = driver.getCurrentUrl();
			if (acturl.contains(url)) {
				flag = true;
			} else {
				extentLogger.log(LogStatus.INFO, "Failed : isRedirectedUrlEquals <br>" + takesScreenshot_Embedded());
			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isRedirectedUrlEquals <br>" + displayErrorMessage(ex));
			flag = false;
		}finally{
			closeAllChildTabs();
		}
		return flag;
	}

	
	public boolean isErrorUrlFound() {
		boolean flag = false;
   
		try {
			Thread.sleep(1000);			
			Thread.sleep(3000);
			String url= "http://www.dofiscal.net/";
			Thread.sleep(2000);	
			String acturl = driver.getCurrentUrl();
			if (acturl.contains(url)) {
				flag = true;
				driver.navigate().back();
				Thread.sleep(3000);
			} 

		} catch (Exception ex) {
			//extentLogger.log(LogStatus.INFO, "Error in : isRedirectedUrlEquals <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}
	public void minimizeFollowDocumentWidget() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followdocumentchangeview"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followdocumentchangeview"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : minimizeFollowDocumentWidget <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isFollowDocumentWidgetMaximized() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".followdocumentchangeview");
			WebElement element = elementhandler.getElement(locator);
			String minimizecontrol = element.getAttribute("class");

			if (minimizecontrol.contains("control minimize-control")) {
				flag = true;
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public boolean verifyZeroFollowDocumentMessage(String message) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".followdocument_zerorecord");
			WebElement element = elementhandler.getElement(locator);
			String expclassvalue = element.getAttribute("class");

			if (expclassvalue.contains("noItems")) {
				String actmsg1 = elementhandler
						.findElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followdocument_zerorecord"))
						.getText();
				if (actmsg1.trim().equalsIgnoreCase(message)) {
					flag = true;
				}
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public void maximizeFollowDocumentWidget() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followdocumentchangeview");
			String element = elementhandler.findElement(locator).getAttribute("class");
			if (element.contains("control maximize-control")) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".followdocumentchangeview"));
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : maximizeFollowDocumentWidget <br>" + displayErrorMessage(exc));
		}
	}

	public boolean clearFirstThematicSearchBox() {
		boolean boxcleared = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".frstthematicsearchclear");
			elementhandler.clickElement(selector);
			boxcleared = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clearFirstThematicSearchBox <br>" + displayErrorMessage(exc));
		}
		return boxcleared;
	}

	public boolean isClearLinkNotDisplayedForFirstThematicSearchBox() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".frstthematicsearchclear");
			linkDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			return true; // Exception is expected if element is not displayed
		}
		return !linkDisplayed;
	}

	public boolean isClearLinkDisplayedForFirstThematicSearchBox() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".frstthematicsearchclear");
			linkDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isClearLinkDisplayedForFirstThematicSearchBox <br>" + displayErrorMessage(exc));
		}
		return linkDisplayed;
	}

	public boolean clearSecondThematicSearchBox() {
		boolean boxcleared = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".sndthematicsearchclear");
			elementhandler.clickElement(selector);
			boxcleared = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clearSecondThematicSearchBox <br>" + displayErrorMessage(exc));
		}
		return boxcleared;
	}

	public boolean isClearLinkNotDisplayedForSecondThematicSearchBox() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".sndthematicsearchclear");
			linkDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			return true; // Exception is expected if element is not displayed
		}
		return !linkDisplayed;
	}

	public boolean isClearLinkDisplayedForSecondThematicSearchBox() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".sndthematicsearchclear");
			linkDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isClearLinkDisplayedForSecondThematicSearchBox <br>" + displayErrorMessage(exc));
		}
		return linkDisplayed;
	}

	public boolean clearThirdThematicSearchBox() {
		boolean boxcleared = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thrdthematicsearchclear");
			elementhandler.clickElement(selector);
			boxcleared = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clearThirdThematicSearchBox <br>" + displayErrorMessage(exc));
		}
		return boxcleared;
	}

	public boolean isClearLinkNotDisplayedForThirdThematicSearchBox() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thrdthematicsearchclear");
			linkDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			return true; // Exception is expected if element is not displayed
		}
		return !linkDisplayed;
	}

	public boolean isClearLinkDisplayedForThirdThematicSearchBox() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thrdthematicsearchclear");
			linkDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isClearLinkDisplayedForThirdThematicSearchBox <br>" + displayErrorMessage(exc));
		}
		return linkDisplayed;
	}

	public void minimizeShortcutsWidget() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".shortcuts_changeview"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".shortcuts_changeview"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : minimizeFollowDocumentWidget <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isShortcutsWidgetMaximized() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".shortcutwidgetchangeview");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			WebElement element = elementhandler.getElement(locator);
			String minimizecontrol = element.getAttribute("class");

			if (minimizecontrol.contains("control minimize-control")) {
				flag = true;
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public boolean isZeroShortcutsMessageDisplayed(String message) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".shortcuts_zerorecord");
			WebElement element = elementhandler.getElement(locator);
			String expclassvalue = element.getAttribute("class");

			if (expclassvalue.contains("noItems")) {
				String actmsg1 = elementhandler
						.findElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".shortcuts_zerorecord"))
						.getText();
				if (actmsg1.trim().equalsIgnoreCase(message)) {
					flag = true;
				}
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public void maximizeShortcutsWidget() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".shortcuts_changeview");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			String element = elementhandler.findElement(locator).getAttribute("class");
			if (element.contains("control maximize-control")) {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".shortcuts_changeview"));
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : maximizeShortcutsWidget <br>" + displayErrorMessage(exc));
		}
	}

	public void clickOnShortcutsWidgetClickHere() {
		try {
			Thread.sleep(100);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".shortcuts_clickhere")));
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".shortcuts_clickhere"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickOnShortcutsWidgetClickHere <br>" + displayErrorMessage(exc));
		}
	}

	public void maximizeClientServiceWidget() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clientservice_maximizecontrol");
			String element = elementhandler.findElement(locator).getAttribute("class");
			if (element.contains("control maximize-control")) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".clientservice_maximizecontrol"));
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : maximizeCustServiceWidget <br>" + displayErrorMessage(exc));
		}
	}

	public void minimizeClientServiceWidget() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clientservice_minimizecontrol"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clientservice_minimizecontrol"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : minimizeCustServiceWidget <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isClientServiceWidgetMaximized() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".clientservice_minimizecontrol");
			WebElement element = elementhandler.getElement(locator);
			String minimizecontrol = element.getAttribute("class");

			if (minimizecontrol.contains("control minimize-control")) {
				flag = true;
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public boolean verifyClientServiceContactNum(String contactnum) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".clientservice_contactnumber");
			String number = elementhandler.findElement(locator).getText();
			if (number.contains(contactnum)) {
				flag = true;
			}
		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public boolean verifyClientServiceEmail(String email) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".clientservice_emailid");
			String actemail = elementhandler.findElement(locator).getText();
			if (actemail.contains(email)) {
				flag = true;
			}
		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public boolean isLinksPresentinDestacadosWidget(String expectedLinks[]) {
		boolean linkFound = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".featuredarticles_alllink");
			List<WebElement> actualLinksList = elementhandler.findElements(locator);
			for (int j = 0; j < expectedLinks.length; j++) {
				for (int i = 0; i < actualLinksList.size(); i++) {
					String actualLink = actualLinksList.get(i).getText().trim();
					linkFound = actualLink.equals(expectedLinks[j].trim());
					if (linkFound)
						break;
				}
				if (!linkFound)
					break;
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isLinksPresentinDestacadosWidget <br>" + displayErrorMessage(exc));
			linkFound = false;
		}
		return linkFound;
	}

	public void maximizeFeaturedArticlesWidget() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".featuredarticles_changeview");
			String element = elementhandler.findElement(locator).getAttribute("class");
			if (element.contains("control maximize-control")) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						elementhandler.findElement(locator));
				elementhandler.clickElement(locator);
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : maximizeFeaturedArticlesWidget <br>" + displayErrorMessage(exc));
		}
	}

	public void minimizeFeaturedArticlesWidget() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".featuredarticles_changeview"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".featuredarticles_changeview"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : minimizeFeaturedArticlesWidget <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isFeaturedArticlesWidgetMaximized() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".featuredarticles_changeview");
			WebElement element = elementhandler.getElement(locator);
			String minimizecontrol = element.getAttribute("class");

			if (minimizecontrol.contains("control minimize-control")) {
				flag = true;
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public void clickonFeaturedArtSeeMoreLink() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".featuredarticles_seemore");
			Thread.sleep(1000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickonFeaturedArtSeeMoreLink <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to check if the expected link present in Featured
	 * items widget. Returns true if the field is displayed else returns false.
	 */
	public boolean isLinkPresentInFeaturedArticles(String actlinkname) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".featuredarticles_alllink");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			for (int i = 0; i < alllinks.size(); i++) {
				String linkname = alllinks.get(i).getText();
				if (linkname.equalsIgnoreCase(actlinkname)) {
					flag = true;
					break;
				}

			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isLinkPresentInFeaturedArticles <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method is used to click on Link present in featured articles widget.
	 * Opens DocumentDisplay page in Tab
	 */
	public DocumentDisplayPage clickLinkInFeaturedArticles(String actlinkname) {
		DocumentDisplayPage documentDisplayPage = null;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".featuredarticles_alllink");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			for (int i = 0; i < alllinks.size(); i++) {
				String linkname = alllinks.get(i).getText();
				if (linkname.equalsIgnoreCase(actlinkname)) {
					alllinks.get(i).click();
					documentDisplayPage = new DocumentDisplayPage(driver);
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickLinkInFeaturedArticles <br>" + displayErrorMessage(exc));
			documentDisplayPage = null;
		}
		return documentDisplayPage;
	}

	public void maximizeAdvanceSearchWidget() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancesearch_changeview");
			String element = elementhandler.findElement(locator).getAttribute("class");
			if (element.contains("control maximize-control")) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".advancesearch_changeview"));
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : maximizeAdvanceSearchWidget <br>" + displayErrorMessage(exc));
		}
	}

	public void minimizeAdvanceSearchWidget() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancesearch_changeview"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancesearch_changeview"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : minimizeAdvanceSearchWidget <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isAdvanceSearchWidgetMaximized() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".advancesearch_changeview");
			WebElement element = elementhandler.getElement(locator);
			String minimizecontrol = element.getAttribute("class");

			if (minimizecontrol.contains("control minimize-control")) {
				flag = true;
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public boolean advSearchLinksforTodasThematic(String link[]) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".advancesearch_alllinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			for (int i = 0; i < alllinks.size(); i++) {
				String actlink = alllinks.get(i).getText();
				for (int j = 0; j < link.length; j++) {
					if (actlink.contentEquals(link[j].trim().toString())) {
						flag = true;
					}
				}
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public boolean advSearchLinkDisplayed(String expectedLink) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".advancesearch_alllinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			for (int row = 0; row < alllinks.size(); row++) {
				String autualLink = alllinks.get(row).getText().trim();
				flag = autualLink.equals(expectedLink);
				if (flag)
					break;
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public void maximizeFreqAskedQuestionsWidget() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freqaskques_changeview");
			String element = elementhandler.findElement(locator).getAttribute("class");
			if (element.contains("control maximize-control")) {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".freqaskques_changeview"));
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : maximizeFreqAskedQuestionsWidget <br>" + displayErrorMessage(exc));
		}
	}

	public void minimizeFreqAskedQuestionsWidget() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freqaskques_changeview"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freqaskques_changeview"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : minimizeFreqAskedQuestionsWidget <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isFreqAskedQuestionsWidgetMaximized() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".freqaskques_changeview");
			WebElement element = elementhandler.getElement(locator);
			String minimizecontrol = element.getAttribute("class");

			if (minimizecontrol.contains("control minimize-control")) {
				flag = true;
			}

		} catch (Exception exc) {
			return false;
		}
		return flag;
	}

	public boolean FreqAskQuesLinkDisplayed(String expectedLink) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".freqaskques_alllinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			for (int row = 0; row < alllinks.size(); row++) {
				if (WebDriverFactory.isDisplayed(driver, alllinks.get(row))) {
					String autualLink = alllinks.get(row).getText().trim();
					flag = autualLink.equals(expectedLink);
					if (flag)
						break;
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : FreqAskQuesLinkDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	}

	public void clickSeeMoreLinkInFreqAskQuesWidget() {
		try {
			Thread.sleep(2000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".home_freqaskques_seemorelink");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(locator)))
				elementhandler.clickElement(locator);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickSeeMoreLinkInFreqAskQuesWidget <br>" + displayErrorMessage(ex));

		}
	}

	public SearchPage ClickFreqAskQuesLink(String expectedLink) throws IllegalArgumentException, IOException {
		SearchPage searchpage = null;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".freqaskques_alllinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", alllinks.get(0));
			for (int row = 0; row < alllinks.size(); row++) {
				if (WebDriverFactory.isDisplayed(driver, alllinks.get(row))) {
					String autualLink = alllinks.get(row).getText().trim();
					if (autualLink.equals(expectedLink)) {
						alllinks.get(row).click();
						Thread.sleep(1000);
						searchpage = new SearchPage(driver);
						break;
					}
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickFreqAskQuesLink <br>" + displayErrorMessage(exc));
			searchpage = null;
		}
		return searchpage;
	}

	/*
	 * This method is used to check of the freeword field is displayed on the
	 * given page. Returns true if the field is displayed else returns false.
	 */
	public boolean isFreewordFieldDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage"))
					.isDisplayed();
		} catch (Exception ex) {
			return false;

		}
	}

	/*
	 * This method is used to check the 'zero saved search message' is displayed
	 * on the widget. Returns true if the field is displayed else returns false.
	 */
	public boolean isZeroSavedSearchMessageDisplayedInWidget(String expectedMessage) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".scheduledsearch_zerorecord");
			String actualText = elementhandler.getText(locator).trim();
			flag = actualText.equals(expectedMessage.trim());
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isZeroSavedSearchMessageDisplayedInWidget <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method is used to check the 'zero documents tracked' message is
	 * displayed on follow document widget. Returns true if the field is
	 * displayed else returns false.
	 */
	public boolean isZeroDocumentsTrackedMessageDisplayedInWidget(String expectedError) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".followdocument_zerorecord");
			String actualText = elementhandler.getText(locator).trim();
			flag = actualText.equals(expectedError.trim());
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isZeroDocumentsTrackedMessageDisplayedInWidget <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method is used to check if no items displayed in saved search
	 * widget. Returns true if the field is displayed else returns false.
	 */
	public boolean isNoItemsPresentInSavedSearchWidget() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".scheduledsearch_allrecords");
			List<WebElement> listOfRecords = elementhandler.findElements(locator);
			if (listOfRecords.size() == 1) {
				if (WebDriverFactory.isDisplayed(driver, listOfRecords.get(0)))
					flag = listOfRecords.get(0).getAttribute("class").equals("noItems");
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isZeroSavedSearchMessageDisplayedInWidget <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method is used to click on Go to checkpointWorld Link in the header
	 * section
	 */
	public void ClickonGoToCheckpointWorld() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".GotoCheckpointWorld")));
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".GotoCheckpointWorld"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonGoToCheckpointWorld <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to click on Go to checkpointWorld Link in the header
	 * section
	 */
	public void ClickonGoToLegalArea() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".GotoLegalArea"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonGoToLegalArea <br>" + displayErrorMessage(exc));
		}
	}

	public void ClickonGoToLawOnline() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".GotoLawOnline"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonGoToLawOnline <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to click on Go to checkpointUSA Link in the header
	 * section
	 */
	public void ClickonGoToCheckpointUSA() {
		try {
			// This link is not clickable. So used implemented in different way
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".GotoChecpointUSA"));
			/*
			 * String url =
			 * elementhandler.getlinkFromHrefAttribute(PropertiesRepository
			 * .getString("com.trgr.maf." + BaseTest.productUnderTest +
			 * ".GotoChecpointUSA")); Loadurl(url);
			 */
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonGoToCheckpointUSA <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is used to get Go to checkpointUSA Link in the header section
	 */
	public String getGoToCheckpointUSAUrl() {
		try {
			// This link is not clickable. So used implemented in different way
			return elementhandler.getlinkFromHrefAttribute(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".GotoChecpointUSA"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getGoToCheckpointUSAUrl <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	/*
	 * This method is used to check if no items displayed in Follow Document
	 * widget. Returns true if the field is displayed else returns false.
	 */
	public boolean isNoItemsPresentInFollowDocumentWidget() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".followdocumentwidget_allrecords");
			List<WebElement> listOfRecords = elementhandler.findElements(locator);
			if (listOfRecords.size() == 1) {
				if (WebDriverFactory.isDisplayed(driver, listOfRecords.get(0)))
					flag = listOfRecords.get(0).getAttribute("class").equals("noItems");
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isNoItemsPresentInFollowDocumentWidget <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method is used to check if Tools widget is maximized Returns true on
	 * success else returns false.
	 */
	public boolean isToolsHomeWidgetMaximized() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".hometoolswidget_changeview");
			WebElement element = elementhandler.getElement(locator);
			String minimizecontrol = element.getAttribute("class");

			if (minimizecontrol.contains("control minimize-control")) {
				flag = true;
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isToolsWidgetMaximized <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	}

	/*
	 * This method is used to check if Tools widget is displayed Returns true on
	 * success else returns false.
	 */
	public boolean isToolsHomeWidgetDisplayed() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".hometoolswidget_changeview");
			WebElement element = elementhandler.getElement(locator);
			flag = WebDriverFactory.isDisplayed(driver, element);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isToolsHomeWidgetDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method is used to click on maximize Tools widget
	 */
	public void maximizeToolsHomeWidget() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".hometoolswidget_changeview");
			String element = elementhandler.findElement(locator).getAttribute("class");
			if (element.contains("control maximize-control")) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".hometoolswidget_changeview"));
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : maximizeAdvanceSearchWidget <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks if a tool name exist in the tools widget returns true
	 * on success
	 */
	public boolean isToolPresentInToolsHomeWidget(String expectedLink) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".hometoolswidget_alllinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", alllinks.get(0));
			for (int row = 0; row < alllinks.size(); row++) {
				String autualLink = alllinks.get(row).getText().trim();
				flag = autualLink.equals(expectedLink);
				if (flag)
					break;
			}
			locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".productlogo");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isToolPresentInToolsHomeWidget <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	}

	/*
	 * This method is used to check if FAQ widget is displayed Returns true on
	 * success else returns false.
	 */
	public boolean isFAQWidgetDisplayed() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".freqaskques_changeview");
			WebElement element = elementhandler.getElement(locator);
			flag = WebDriverFactory.isDisplayed(driver, element);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFAQWidgetDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This methods validates the text available under Footer link
	 */
	public boolean isPrivacyPolicyAvailable() {
		try {
			
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".privacypolicytext");
			return elementhandler.findElement(locator).isDisplayed();			

		} catch (Exception ex) {
			return false;
		}

	}

	public void clickFooterLink() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".validatecopyright");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFooterLink <br>" + displayErrorMessage(exc));
		}
	}

	
	
	public void clickUsagePolicyLink() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".policyusagelink");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickUsagePolicyLink <br>" + displayErrorMessage(exc));
		}
	}

	public boolean validateRedirectedUrl(String expurl) {
		boolean flag = false;
		try {
			String url = driver.getCurrentUrl();
			if (url.contains(expurl))
				flag = true;
			driver.navigate().back();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateRedirectedUrl <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method is minimizes all widgets in Home
	 */
	public void minimizeAllWidgetsInHome() {
		try {
			Thread.sleep(2000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".widgetonhomepage_changeview");
			List<WebElement> widgetControlList = elementhandler.findElements(locator);
			for (int row = 0; row < widgetControlList.size(); row++) {
				if (WebDriverFactory.isDisplayed(driver, widgetControlList.get(row))) {
					if (widgetControlList.get(row).getAttribute("class").contains("minimize-control")) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
								widgetControlList.get(row));
						widgetControlList.get(row).click();
						Thread.sleep(1000);
					}
				}
			}
			locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".productlogo");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : minimizeAllWidgetsInHome <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks if all widgets are minimized
	 */
	public boolean isAllWidgetsMinimizedInHome() {
		boolean minimized = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".widgetonhomepage_changeview");
			List<WebElement> widgetControlList = elementhandler.findElements(locator);
			for (int row = 0; row < widgetControlList.size(); row++) {
				if (WebDriverFactory.isDisplayed(driver, widgetControlList.get(row))) {
					minimized = widgetControlList.get(row).getAttribute("class").contains("maximize-control");
					if (!minimized)
						break;
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isAllWidgetsMinimizedInHome <br>" + displayErrorMessage(exc));
			minimized = false;
		}
		return minimized;
	}

	/*
	 * This method checks if all widgets are maximized
	 */
	public boolean isAllWidgetsMaximizedInHome() {
		boolean maximized = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".widgetonhomepage_changeview");
			List<WebElement> widgetControlList = elementhandler.findElements(locator);
			for (int row = 0; row < widgetControlList.size(); row++) {
				if (WebDriverFactory.isDisplayed(driver, widgetControlList.get(row))) {
					maximized = widgetControlList.get(row).getAttribute("class").contains("minimize-control");
					if (!maximized)
						break;
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isAllWidgetsMaximizedInHome <br>" + displayErrorMessage(exc));
			maximized = false;
		}
		return maximized;
	}

	/*
	 * This method is maximizes all widgets in Home
	 */
	public void maximizeAllWidgetsInHome() {
		try {
			Thread.sleep(2000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".widgetonhomepage_changeview");
			
						List<WebElement> widgetControlList = elementhandler.findElements(locator);
			for (int row = 0; row < widgetControlList.size(); row++) {
				if (WebDriverFactory.isDisplayed(driver, widgetControlList.get(row))) {
					if (widgetControlList.get(row).getAttribute("class").contains("maximize-control")) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
								widgetControlList.get(row));
						widgetControlList.get(row).click();
						Thread.sleep(1000);
					}
				}
			}
			locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".productlogo");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : maximizeAllWidgetsInHome <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method returns the position of widget among all widgets in home
	 */
	public int getWidgetPosition(String widget) {
		int position = 0;
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".widgetonhomepage");
			List<WebElement> widgetControlList = elementhandler.findElements(locator);
			for (int row = 0; row < widgetControlList.size(); row++) {
				if (WebDriverFactory.isDisplayed(driver, widgetControlList.get(row)))
					if (widgetControlList.get(row).getText().trim().equals(widget.trim())) {
						position = row + 1;
						break;
					}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getWidgetPosition <br>" + displayErrorMessage(exc));
		}
		return position;
	}

	/*
	 * This method returns the name of widget at given position
	 */
	public String getWidgetNameAtPosition(int position) {
		String name = "";
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".widgetonhomepage");
			List<WebElement> widgetControlList = elementhandler.findElements(locator);
			if (position <= widgetControlList.size())
				if (WebDriverFactory.isDisplayed(driver, widgetControlList.get(position - 1)))
					name = widgetControlList.get(position - 1).getText().trim();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getWidgetNameAtPosition <br>" + displayErrorMessage(exc));
		}
		return name;
	}

	/*
	 * This method is performs drag and drop of widgets It moves the first
	 * widget to the last position
	 */
	public void dragAndDropFirstWidgetToLastPosition() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".widgetonhomepage");
			List<WebElement> widgetList = elementhandler.findElements(locator);
			WebElement source = widgetList.get(0);
			WebElement target = widgetList.get(widgetList.size() - 1);
			// ((JavascriptExecutor)
			// driver).executeScript("arguments[0].scrollIntoView();", source);
			Thread.sleep(3000);
			WebDriverFactory.waitForElementUsingWebElement(driver, source, 20);
			WebDriverFactory.waitForElementUsingWebElement(driver, target, 20);
			dragAndDropWebElement(source, target);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : dragAndDropFirstWidgetToLastPosition <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is performs drag and drop of widgets It moves the specified
	 * widget to the first position
	 */
	public void dragAndDropWidgetToFirstPosition(String widgetToMove) {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".widgetonhomepage");
			List<WebElement> widgetList = elementhandler.findElements(locator);
			locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".productlogo");
			WebElement sourceElement = null;
			WebElement targetElement = elementhandler.findElement(locator);

			for (int row = 0; row < widgetList.size(); row++) {
				if (WebDriverFactory.isDisplayed(driver, widgetList.get(row))) {
					if (widgetList.get(row).getText().trim().equals(widgetToMove.trim())) {
						sourceElement = widgetList.get(row);
						break;
					}
				}
			}
			
			dragAndDropWebElement(sourceElement, targetElement);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : dragAndDropWidgetToFirstPosition <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method is performs drag and drop of widgets It moves the position of
	 * widgets
	 */
	public void moveWidgetToPositionOfAnotherWidget(String sourceWidget, String targetWidget) {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".widgetonhomepage");
			List<WebElement> widgetList = elementhandler.findElements(locator);

			WebElement sourceElement = null;
			WebElement targetElement = null;

			for (int row = 0; row < widgetList.size(); row++) {
				if (WebDriverFactory.isDisplayed(driver, widgetList.get(row))) {
					if (widgetList.get(row).getText().trim().equals(sourceWidget.trim()))
						sourceElement = widgetList.get(row);
					else if (widgetList.get(row).getText().trim().equals(targetWidget.trim()))
						targetElement = widgetList.get(row);
				}

				if (sourceElement != null && targetElement != null)
					break;
			}
			dragAndDropWebElement(sourceElement, targetElement);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : moveWidgetToPositionOfAnotherWidget <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method verifies if link present in Page Header returns true on
	 * success, false on failure
	 */
	public boolean isGivenLinkPresentInHeader(String expectedLink) {
		boolean flag = false;
		try {

			List<WebElement> alllinks = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".alllinksinhomepageheader"));

			for (int i = 0; i < alllinks.size(); i++) {
				String actlink = alllinks.get(i).getText().trim();
				if (expectedLink.trim().equalsIgnoreCase(actlink)) {
					flag = true;
					break;
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isGivenLinkPresentInHeader <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isHistoryWidgetMaximized() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".historywidgetchangeview");
			WebElement element = elementhandler.getElement(locator);
			String minimizecontrol = element.getAttribute("class");

			if (minimizecontrol.contains("control minimize-control")) {
				flag = true;
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isHistoryWidgetMaximized <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	}

	public boolean isHistorybyYearFieldAvailable() {
		try {
			return elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".historicalyear"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isHistorybyYearFieldAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean validateHistorybyYearText(String text) {
		boolean flag = false;
		try {
			String acttext = elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".historicalyear"))
					.getText();
			if (acttext.contains(text))
				flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateHistorybyYearText <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void selectHistorybyYear(String year) {
		try {
			List<WebElement> allyrs = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".allhistoryyears"));

			for (int i = 0; i < allyrs.size(); i++) {
				String acttext = allyrs.get(i).getText();
				if (acttext.contains(year)) {
					allyrs.get(i).click();
					Thread.sleep(4000);
					break;
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : selectHistorybyYear <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isHistorybyOrderFieldAvailable() {
		try {
			return elementhandler
					.findElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".historicalorder"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isHistorybyYearFieldAvailable <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean validateHistorybyOrderText(String text) {
		boolean flag = false;
		try {
			String acttext = elementhandler
					.findElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".historicalorder"))
					.getText();
			if (acttext.contains(text))
				flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateHistorybyOrderText <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void selectHistorybyOrder(String value) {
		try {
			List<WebElement> allyrs = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".allhistoryorders"));

			for (int i = 0; i < allyrs.size(); i++) {
				String acttext = allyrs.get(i).getText();
				if (acttext.contains(value)) {
					allyrs.get(i).click();
					Thread.sleep(4000);
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : selectHistorybyOrder <br>" + displayErrorMessage(exc));
		}
	}

	public void historyWidgetClickClear() {
		try {
			Thread.sleep(1000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".historywidgetclearbutton")), 30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".historywidgetclearbutton"));
			Thread.sleep(4000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : historyWidgetClickClear <br>" + displayErrorMessage(exc));
		}
	}

	public void historyWidgetClickSearch() {
		try {
			Thread.sleep(1000);
			
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".historywidgetsearchbutton");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			
			Thread.sleep(2000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : historyWidgetClickSearch <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isRequiredhistoryOpened(String text) {
		boolean flag = false;
		try {
			WebElement locator = elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".switchtoframe"));
			WebDriverFactory.waitForElementUsingWebElement(driver, locator, 30);
			driver.switchTo().frame(locator);

			String actualtitle = elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".pagetitlehistorywidget"));
			if (actualtitle.contains(text))
				flag = true;

			driver.switchTo().defaultContent();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isRequiredhistoryOpened <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void maximizeHistoryWidget() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".historywidgetchangeview");
			String element = elementhandler.findElement(locator).getAttribute("class");
			if (element.contains("control maximize-control")) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".historywidgetchangeview"));
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : maximizeHistoryWidget <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks to see if the given widget is expanded on the home
	 * page If the given widget is minimized, this method will expand it
	 */
	public void expandTheGivenWidget(String widgetTitleName) {
		try {
			List<WebElement> widgetsOnHome = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widgets"));
			for (WebElement widget : widgetsOnHome) {

				if (widget.getText().contains(widgetTitleName)) {
					boolean isWidgetMinimized = widget.findElement(By.xpath("//div[@class='widget-controls']//a/div"))
							.getAttribute("class").contains("maximize-control");
					if (isWidgetMinimized) {
						// Click on expand button to maximize the widget view
						widget.findElement(By.xpath("//div[@class='widget-controls']//a//div")).click();
						return;

					} else {
						return;
					}
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isGivenWidgetExpanded <br>" + displayErrorMessage(exc));

		}

	}

	public boolean isTermsAndConditionLinkDisplayedInFooter() {
		boolean linkDisplayed = false;
		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".terms_of_use_link");
			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))) {
				String actualText = elementhandler.getText(selector).trim();
				linkDisplayed = actualText.equalsIgnoreCase("Términos De Uso");
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isTermsAndConditionLinkDisplayedInFooter <br>" + displayErrorMessage(exc));
		}
		return linkDisplayed;
	}

	public void clickTermsAndConditonLink() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".terms_of_use_link");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickTermsAndConditonLink <br>" + displayErrorMessage(exc));
		}

	}

	public boolean isTermsAndcondtionisPDF() {
		try {
			return (driver.getCurrentUrl().equals(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".terms_of_use_pdfUrl")));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isTermsAndcondtionisPDF <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isTheSuggestionsDropdownDisplayed() {
		try {
			// do not remove this sleep as application sometimes its not loading
			// immediately the suggestions
			Thread.sleep(3000);
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdown"))
					.isDisplayed()
					|| elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdown"))
							.isEnabled();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isTheSuggestionsDropdownDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}
	
	//	
	
	/*
	 * This method clicks on the search button by scrolling to the element whereever it is on the page
	 * Returns the Handle to the Search Results Page
	 * If the element is not present, catch block will handle the exception and log the screen shot on the extent report
	 */
	public SearchResultsPage clickOnSearchInHomePage() throws Exception {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchBtnInHomePage");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(1000); 
			return new SearchResultsPage(driver);
		} catch (Exception exc) {
			
			//Search Results page is not displayed if there is an error / no search results message displayed
			if(!elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".errormesg")).isDisplayed())
			{
			   extentLogger.log(LogStatus.INFO, "Error in : clickOnSearch <br>" + displayErrorMessage(exc));
				
			}
			
		}
		 return null;

	}
	
	public void clickFollowDocumentWidgetVermasLink() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followdocumentwidget_vermasLink"))
					.isDisplayed();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followdocumentwidget_vermasLink"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFollowDocumentWidgetVermasLink <br>" + displayErrorMessage(exc));
		}
	}

}
	

