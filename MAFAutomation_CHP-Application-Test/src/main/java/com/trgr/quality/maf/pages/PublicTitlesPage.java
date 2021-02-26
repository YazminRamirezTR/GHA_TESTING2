package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class PublicTitlesPage extends ToolsPage {

	public PublicTitlesPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/*
	 * This method checks to see if the page is displayed with expected fields on the page. They are:
	 * 1. Fiscal Period Dropdown
	 * 2. Denominacion
	 * 3. Clase
	 * Return True if all of the fields are present else returns False
	 * If the elements are not present, catch block will handle the exception and log the screen shot on the extent report
	 */
	public boolean isPageDisplayedAsExpected() 
	{
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".fiscalperiod")).isDisplayed()
					&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".denominacion")).isDisplayed() 
					&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".Clase")).isDisplayed();
			
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : isPageDisplayedAsExpected <br>" + displayErrorMessage(ex));
			return false;
		}
	}
	
	/*
	 * This method enters the given data to Period dropdown based on Value
	 * If the element is not present, catch block will handle the exception and log the screen shot on the extent report
	 */
	public void enterPeriodValue(String periodo) {
       try
		{
    	   WebElement element = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Perido"));
           Select period = new Select(element);
           period.selectByValue(periodo);
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : enterPeriodValue <br>" + displayErrorMessage(ex));
		}
	}


	/*
	 * This method enters the given data to Denominacion dropdown based on Value
	 * If the element is not present, catch block will handle the exception and log the screen shot on the extent report
	 */
	public void enterDenominacion(String denominacion) {
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".denominacion"));
			Select denomicion = new Select(element);
			denomicion.selectByValue(denominacion);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterDenominacion <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method enters the given data to Clase dropdown based on Value
	 * If the element is not present, catch block will handle the exception and log the screen shot on the extent report
	 */
	public void enterClase(String clase) {
     try {
    		WebElement element = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Clase"));
    	      Select claseSelect = new Select(element);
    	      claseSelect.selectByValue(clase);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterClase <br>" + displayErrorMessage(ex));
		}
		
 }

	

	
}
