package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.handlers.BaseHandler;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class PreferencePage extends BasePage {

	BaseHandler Basehandler;
	HomePage homepage;
	public PreferencePage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void writeEmailInPreferencePage(String email)
	{
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".emailnotification")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".emailnotification")).sendKeys(email); 
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : writeEmailInPreferencePage <br>"+displayErrorMessage(e));	
		}
	}
	
	public HomePage savePreference() throws Exception
	{
		try{
			//JavascriptExecutor jse = (JavascriptExecutor) driver;
		   // jse.executeScript("window.scrollBy(0,920)", "");	  	    
		    WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".savepreferences")), 20);
		    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".savepreferences")));
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".savepreferences"));
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : savePreference <br>"+displayErrorMessage(e));	
			return null;
		}
		return new HomePage(driver);
	}
	
	public HomePage goToHomePage() throws IllegalArgumentException, IOException
	{
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".hometab");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 40);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			elementhandler.clickElement(locator);
		
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : goToHomePage <br>"+displayErrorMessage(e));	
			return null;
		}
		return new HomePage(driver);
	}
	
	
	public String getEmail()
	{
		try{
		return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".emailnotification")).getAttribute("value");
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : getEmail <br>"+displayErrorMessage(e));	
			return null;
		}
	}
	
	public boolean verifyPasswordResetDisplayed()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".passwordreset")).isDisplayed();
			}catch(Exception e)
			{
				extentLogger.log(LogStatus.INFO, "Error in : verifyPasswordResetDisplayed <br>"+displayErrorMessage(e));	
				return false;
			}
	}
	
	public boolean verifyRecoveryPasswordDisplayed()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".recoverypassword")).isDisplayed();
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : verifyRecoveryPasswordDisplayed <br>"+displayErrorMessage(e));	
			return false;
		}
	}

	public boolean isDeliveryOptionsPdfSelected() 
	{
		try{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
		    jse.executeScript("window.scrollBy(0,700)", "");
		    return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".deliveryoptionpdf")).isSelected();
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isDeliveryOptionsPdfSelected <br>"+displayErrorMessage(e));	
			return false;
		}
	}
	
	public void clickOnRadioButtonPDF() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliveryoptionpdf"));

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRadioButtonPDF <br>" + displayErrorMessage(e));

		}
	}
	
	
	
	
	public boolean isPrintOptionA4Enabled()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".printoptiona4")).isSelected();
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isPrintOptionA4Enabled <br>"+displayErrorMessage(e));	
			return false;
		}
	}
	
	public void clickOnRadioButtonPrintOptionA4()
	{
		try{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
		    jse.executeScript("window.scrollBy(0,920)", "");	
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".printoptiona4"));		
			}catch(Exception e)
			{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRadioButtonPrintOptionA4 <br>"+displayErrorMessage(e));	
			
			}
	}
	
	public boolean isPrintOptionLetterEnabled()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".printoptionletter")).isSelected();
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isPrintOptionLetterEnabled <br>"+displayErrorMessage(e));	
			return false;
		}
	}
	
	public void clickOnRadioButtonPrintOptionLetter()
	{
		try{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
		    jse.executeScript("window.scrollBy(0,920)", "");	
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".printoptionletter"));		
			}catch(Exception e)
			{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRadioButtonPrintOptionLetter <br>"+displayErrorMessage(e));	
			
			}
	}
	
	public boolean isRadioButton10Enabled()
	{
		try{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
		    jse.executeScript("window.scrollBy(0,920)", "");	
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".radiobutton10")).isSelected();
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isRadioButton10Enabled <br>"+displayErrorMessage(e));	
			return false;
		}
	}
	
	public void clickOnRadioButton10()
	{
		try{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
		    jse.executeScript("window.scrollBy(0,920)", "");		
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".radiobutton10"));		
			}catch(Exception e)
			{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRadioButton10 <br>"+displayErrorMessage(e));	
			
			}
	}
	
	public boolean isSearchEnabled()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".searchradiobutton")).isSelected();
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isSearchEnabled <br>"+displayErrorMessage(e));	
			return false;
		}
	}
	
	public void clickOnRadioButtonSearch()
	{
		try{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
		    jse.executeScript("window.scrollBy(0,920)", "");	
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".searchradiobutton"));		
			}catch(Exception e)
			{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRadioButtonSearch <br>"+displayErrorMessage(e));	
			
			}
	}
	
	public boolean isFiscalRadioButtonEnabled()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".fiscalradiobutton")).isSelected();
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isSearchEnabled <br>"+displayErrorMessage(e));	
			return false;
		}
	}
	
	public void clickOnRadioButtonFiscal()
	{
		try{
			JavascriptExecutor jse = (JavascriptExecutor) driver;
		    jse.executeScript("window.scrollBy(0,920)", "");	
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".fiscalradiobutton"));		
			}catch(Exception e)
			{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRadioButtonSearch <br>"+displayErrorMessage(e));	
			
			}
	}
	public boolean verifyDefaultPreferences()
	{
		boolean flag= false;
		try{
			flag=verifyUserInformation() && isDefaultbrowsingHomeSelected() && isDefaultTopicAreaLookForSelected() && isDefaultSearchResultPerPageSelected() && isDefaultDeliveryoptionSelected() && isDefaultPrintingoptionSelected(); 
			
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : verifyDefaultPreferences <br>"+displayErrorMessage(e));	
			flag=  false;
		}
		return flag;
	}
	public boolean verifyUserInformation()
	{
		boolean flag= false;
		try{
			String userinfo=elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".emailnotification")).getText();
			if(userinfo != null && !userinfo.isEmpty())
			{
				flag=true;
			}
			
		}catch(Exception e)
		{
			extentLogger.log(LogStatus.INFO, "Error in : verifyUserInformation <br>"+displayErrorMessage(e));	
			flag=  false;
		}
		return flag;
	}

	public boolean isDefaultbrowsingHomeSelected() {

		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".browsinghomepage"))
					.isSelected();

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isDefaultbrowsingHomeSelected <br>" + displayErrorMessage(e));
			return false;
		}

	}
	
	public boolean isDefaultTopicAreaLookForSelected() {

		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".topicarea"))
					.isSelected();

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDefaultTopicAreaLookForSelected <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean isDefaultSearchResultPerPageSelected() {

		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobutton25"))
					.isSelected();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyDefaultSearchResultPerPage <br>" + displayErrorMessage(e));
			return false;
		}

	}
	
	public boolean isDefaultDeliveryoptionSelected() {

		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliveryoptionpdf"))
					.isSelected();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDefaultDeliveryoptionSelected <br>" + displayErrorMessage(e));
			return false;
		}

	}

	public boolean isDefaultPrintingoptionSelected() {

		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".printoptiona4"))
					.isSelected();

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDefaultPrintingoptionSelected <br>" + displayErrorMessage(e));
			return false;
		}

	}
	
	public boolean isUserInformationDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_userinformation")));
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_userinformation"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isUserInformationDisplayed <br>"+displayErrorMessage(e));
			return false;
		}

	}
	
	public boolean isBrowsingServiceDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_browseservices")));
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_browseservices"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isBrowsingServiceDisplayed <br>"+displayErrorMessage(e));
			return false;
		}

	}
	public boolean isSubjectAreaDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_subjectarea")));
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_subjectarea"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isSubjectAreaDisplayed <br>"+displayErrorMessage(e));
			return false;
		}

	}
	public boolean isSearchResultsperPageDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_searchresultsperpage")));
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_searchresultsperpage"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchResultsperPageDisplayed <br>"+displayErrorMessage(e));
			return false;
		}

	}
	public boolean isDeliveryOptionsDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_deliveryoption")));
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_deliveryoption"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isDeliveryOptionsDisplayed <br>"+displayErrorMessage(e));
			return false;
		}

	}
	public boolean isPrintingOptionsDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_printoption")));
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_printoption"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isPrintingOptionsDisplayed <br>"+displayErrorMessage(e));
			return false;
		}

	}
	public boolean isChangePasswordDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_changepassword")));
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_changepassword"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isChangePasswordDisplayed <br>"+displayErrorMessage(e));
			return false;
		}

	}
	public boolean isRecoverPasswordDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_recoverpassword")));
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_recoverpassword"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isRecoverPasswordDisplayed <br>"+displayErrorMessage(e));
			return false;
		}

	}
	public boolean isConfigureAlertsDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_configurealerts")));
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".preferencepage_configurealerts"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isConfigureAlertsDisplayed <br>"+displayErrorMessage(e));
			return false;
		}

	}
	
	
}
