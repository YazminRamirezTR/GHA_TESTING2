package com.trgr.quality.maf.pages;

import java.io.IOException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class NewsPage extends BasePage {

	public NewsPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		WebDriverFactory.waitForElementUsingWebElement(driver,
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newstablink")),
				20);
	}

	public boolean ValidateNewsPage() throws InterruptedException {
		boolean flag = false;
		try {

			flag = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sincedatefield"))
					.isDisplayed()
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".untildatefield"))
							.isDisplayed()
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbutton"))
							.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ValidateNewsPage <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isErrorMsgDisplayed(String message) {
		boolean flag = false;
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nosearchresultmessage"))
					.isDisplayed();

			String actualerrormsg = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nosearchresultmessage"))
					.getText();

			System.out.println(actualerrormsg);
			if (actualerrormsg.contains(message)) {
				flag = true;

			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isErrorMsgDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void clickClearButton() {
		try {
			
			Thread.sleep(1000);
			
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbutton"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickClearButton <br>" + displayErrorMessage(exc));
		}
	}
	
	public boolean isSinceDateEmpty() {
		boolean flag = false;
		try {
			String sincedate = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".newspage_sincedate");
			String value = elementhandler.getText(sincedate).toString();
			if (value.isEmpty()) {
				flag = true;
				}
		} catch (Exception ex) {
			return false;
		}
		return flag;
	}
	
	public boolean isUntilDateEmpty() {
		boolean flag = false;
		try {
			String untildate = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_untildate");
			String value = elementhandler.getText(untildate);
			if (value.isEmpty()) {
				flag = true;
				}
		} catch (Exception ex) {
			return false;
		}
		return flag;
	}
	
	public boolean isNIIFLinkDisplayed() {
		boolean flag = false;
		try {

			flag = elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".NIIF"))
					.isDisplayed();

			return flag;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isNIIFLinkDisplayed <br>" + displayErrorMessage(exc));
			return flag;
		}
	}

	public boolean ValidateNewsPageObjects() throws InterruptedException {
		boolean flag = false;
		try {

			flag = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sincedatefield"))
					.isDisplayed();
			flag = flag
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".untildatefield"))
							.isDisplayed();
			flag = flag && elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".link"))
					.isDisplayed();
			;
			flag = flag && elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown"))
					.isDisplayed();

			return flag;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ValidateNewsPage <br>" + displayErrorMessage(exc));
			return flag;
		}
	}

	public boolean Checkdatebox() throws InterruptedException {
		boolean flag = false;
		try {

			flag = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".filterbydate"))
					.isDisplayed();
			flag = flag
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sincedatefield"))
							.isDisplayed();
			flag = flag
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".untildatefield"))
							.isDisplayed();
			flag = flag
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".seeifrsnewslink"))
							.isDisplayed();
			flag = flag && elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown"))
					.isDisplayed();

			return flag;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ValidateNewsPage <br>" + displayErrorMessage(exc));
			return flag;
		}
	}

	public void selecttodaysdateinpicker() {
		try {
			elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".todaysdate"))
					.click();
			elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".todaysdate"))
					.sendKeys(Keys.ENTER);
			//once again clicking this so that the calendar will disappear
			elementhandler
			.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".todaysdate"))
			.click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : selecttodaysdateinpicker <br>" + displayErrorMessage(exc));

		}

	}

	public void selectdatepicker() {
		try {
			elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".datepicker"))
					.click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : selectdatepicker <br>" + displayErrorMessage(exc));

		}

	}

	public void enterUntildate(String date) {
		try {
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_untildate")).clear();
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_untildate"))
					.sendKeys(date);
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_untildate"))
					.sendKeys(Keys.TAB);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterfromdate <br>" + displayErrorMessage(exc));

		}

	}

	public SearchResultsPage clicksearch() {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".searchbutton"));
//			WebDriverWait wait=new WebDriverWait(driver,120);
			Thread.sleep(5000);
//			String loc = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultsList");
//			wait.until(ExpectedConditions.visibilityOf(elementhandler.getElement(
//					loc)));
//			 WebDriverFactory.waitForElementUsingWebElement(driver,
//					  elementhandler.getElement(
//					  PropertiesRepository.getString("com.trgr.maf." +
//					  BaseTest.productUnderTest + ".resultsList")), 50);
			return new SearchResultsPage(driver);
		} catch (Exception exc) {
			
			//Search Results page is not displayed if there is an error / no search results message displayed
			if(!elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nosearchresultmessage")).isDisplayed())
			{
			   extentLogger.log(LogStatus.INFO, "Error in : clicksearch <br>" + displayErrorMessage(exc));
				
			}
			 return null;
		}

	}

	public boolean isFilterbydateDispalyed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_filterbyindex"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFilterbydateDispalyed <br>" + displayErrorMessage(exc));
			return false;

		}
	}

	public boolean isSincedateDispalyed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_sincedate"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSincedateDispalyed <br>" + displayErrorMessage(exc));

		}
		return false;
	}


	public boolean isUntildateDispalyed()

	{
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_untildate"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isUntildateDispalyed <br>" + displayErrorMessage(ex));
			return false;

		}
	}

	public boolean isClearButtonDisplayed() {
		try {
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbutton"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isClearButtonDisplayed <br>" + displayErrorMessage(exc));
			return false;

		}
	}

	public boolean isSearchButtonDisplayed() {
		try {
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbutton"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchButtonDisplayed <br>" + displayErrorMessage(exc));
			return false;

		}
	}

	public boolean isNoResultMsgDispalyed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_noresultsmsg"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isNoResultMsgDispalyed <br>" + displayErrorMessage(exc));
			return false;

		}
	}

	public boolean isWhatsNewDisplayed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_whatsnewtext"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isWhatsNewDisplayed <br>" + displayErrorMessage(exc));
			return false;

		}
	}

	public boolean isSeeIFRSNewsDispalyed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_seeifrsnews"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSeeIFRSNewsDispalyed <br>" + displayErrorMessage(exc));
			return false;

		}
	}
	
	public void enterSinceDate(String sincedate)
	{
		try{
			Thread.sleep(1000);
		String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".newspage_sincedate");
		elementhandler.getElement(locator).clear();
		elementhandler.writeText(locator,sincedate);
		elementhandler.findElement(locator).sendKeys(Keys.TAB);
		}catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterSinceDate <br>" + displayErrorMessage(ex));
			}
	}

	public SearchPage clickIFRSNewsLink() {
		try {
			elementhandler
					.clickElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newspage_seeifrsnews"));
				return new SearchPage(driver);	
		} catch (Exception exc) {
			return null;

		}
	}
	
	public boolean InvalidDateFormatMessage(String message) {
		boolean flag = false;
		try {
			clicksearch();
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".errormsg");
			String actmsg = elementhandler.getText(locator);
			if(actmsg.contains(message))
				flag=true;
		} catch (Exception ex) {
			return false;
		}
		return flag;
	}
	
	public boolean BlankInputMessage(String message) {
		boolean flag = false;
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".searchbutton"));
			
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".zeroinput_message");
			String actmsg = elementhandler.getText(locator);
			if(actmsg.contains(message))
			{
				flag=true;
			}
		} catch (Exception ex) {
			return false;
		}
		return flag;
	}
	
	/*
	 * This method checks if the current tab is having Orange colour
	 * returns true on success
	 */
	public boolean isCurrentTabColorEqualsOrange() throws Exception {
		boolean flag = false;
		try {
			WebElement searchatb = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".curtab"));
			String color = searchatb.getCssValue("color");
			String hex = Color.fromString(color).asHex();
			if(hex.contains("#f89f29"))
				flag = true;
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCurrentTabColorEqualsOrange <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method checks if the current tab is News or not
	 * returns true on success
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
	
	
}
