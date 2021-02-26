package com.trgr.quality.maf.pages;
import java.io.IOException;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class SaveAndSchedulePage extends BasePage {

	public SaveAndSchedulePage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".save_and_schedule_search_header")), 30);
	}
	
	/*
	 * This method returns true if Save And Schedule Search page is displayed 
	 */
	public boolean isSaveAndSchedulePageDisplayed() 
	{
		return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".save_and_schedule_search_header")));
	}
	
	
	/*
	 * This method verifies the display of Save and Schedule search page
	 */
	public boolean isSaveAndScheduleTitleDisplayed(String Expected_Title)
	{
		boolean flag = false;
		try{
			String Actual_title = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".save_and_schedule_search_header"));
			if(Actual_title.contains(Expected_Title))
						flag = true;
		}
			catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : isSaveAndScheduleTitleDisplayed <br>"+displayErrorMessage(e));
		}
		return flag;
	}
	
	/*
	 * This method sets the name field in Save and Schedule search page
	 */
	public void setName(String text)
	{
		try{
			Thread.sleep(3000);
			elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".save_and_schedule_search_name"),text);
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : setName <br>"+displayErrorMessage(e));
		}
	}
	
	/*
	 * This method sets the name field in Save and Schedule search page
	 */
	public void setEmail(String text)
	{
		try{
			Thread.sleep(2000);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".save_and_schedule_search_email")).clear();
			elementhandler.writeText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".save_and_schedule_search_email"),text);
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : setEmail <br>"+displayErrorMessage(e));
		}
	}
	
	/*
	 * This method selects the frequency
	 */
	public void selectFrequencyByValue(String text)
	{
		try{
			elementhandler.selectByValue(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".save_and_schedule_search_frequency"), text);
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : selectFrequencyByValue <br>"+displayErrorMessage(e));
		}
	}
	
	/*
	 * This method selects the Daily frequency in Save and Schedule search page
	 */
	public void selectFrequencyDaily()
	{
		try{
			selectFrequencyByValue("DAILY");
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : selectFrequencyDaily <br>"+displayErrorMessage(e));
		}
	}
	
	/*
	 * This method writes email on create alert page
	 */
	public void writeEmailFieldforalertpage(String email) 
	{
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteremailidfield")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteremailidfield")).sendKeys(email);
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : writeEmailField <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method selects the WEEKDAYS frequency in Save and Schedule search page
	 */
	public void selectFrequencyWeekdays()
	{
		try{
			selectFrequencyByValue("WEEKDAYS");
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : selectFrequencyWeekdays <br>"+displayErrorMessage(e));
		}
	}
	
	
	public void selectFrequenceyWeekdaysDeliverypage(String frequencey) {
		try{
			if(frequencey.trim().equals("Días de la semana")) {
				selectFrequencyByValue("WEEKDAYS");
			}
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : selectFrequencyWeekdays <br>"+displayErrorMessage(e));
		}
	}
	/*
	 * This method selects the Weekly frequency in Save and Schedule search page
	 */
	public void selectFrequencyWeekly()
	{
		try{
			selectFrequencyByValue("WEEKLY");
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : selectFrequencyWeekly <br>"+displayErrorMessage(e));
		}
	}
	
	/*
	 * This method selects the BIWEEKLY frequency in Save and Schedule search page
	 */
	public void selectFrequencyBiweekly()
	{
		try{
			selectFrequencyByValue("BIWEEKLY");
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : selectFrequencyBiweekly <br>"+displayErrorMessage(e));
		}
	}
	
	/*
	 * This method selects the MONTHLY frequency in Save and Schedule search page
	 */
	public void selectFrequencyMonthly()
	{
		try{
			Thread.sleep(4000);
			selectFrequencyByValue("MONTHLY");
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : selectFrequencyMonthly <br>"+displayErrorMessage(e));
		}
	}
	
	
	public void selectFrequencyFromDropdownAlertPage(String Frequency) {
		try{
			selectFrequencyByValue(Frequency);
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : selectFrequencyFromDropdownAlertPage <br>"+displayErrorMessage(e));
		}
	}
	/*
	 * This method Click on Save button in Save & schedule search page
	 * Returns AlertPage
	 */
	public AlertPage clickSaveAlertButton()
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".save_and_schedule_search_savebutton"));
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".myalertslink")), 40);
			return new AlertPage(driver);
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveAlertButton <br>"+displayErrorMessage(e));
			return null;
		}
		
	}
	
	/*
	 * This method Click on Cancel button in Save & schedule search page
	 */
	public void clickCancelAlertButton()
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".save_and_schedule_search_cancelbutton"));
		}
		catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelAlertButton <br>"+displayErrorMessage(e));
		}
	}

}