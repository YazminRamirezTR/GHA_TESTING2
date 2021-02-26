package com.trgr.quality.maf.pages;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class ForeignCurrencyPage extends ToolsPage {

	public ForeignCurrencyPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	//isForeigCurrencySearcTemplateDisplayed
	
	public boolean isForeigCurrencySearcTemplateDisplayed()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".periodfiscal")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".foreigncurrencycoin")).isDisplayed();
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isForeigCurrencySearcTemplateDisplayed <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	public void selectCoinDropdown(String coin) {
		try {
			Thread.sleep(1000);
			String selctor = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".foreigncurrencycoin");
			elementhandler.selectByVisibleText(selctor, coin);
			Thread.sleep(1000);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectCoinDropdown <br>" + displayErrorMessage(ex));
		}
	}
	
	public boolean isGivenSearchDataRetainedOnPage(String period, String coin) 
	{
		   try {
			    return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".periodfiscal")).getText().contains(period) &&
	    		elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".foreigncurrencycoin")).getText().contains(coin);
	    				
	    	    
			} catch (Exception ex) {
				extentLogger.log(LogStatus.INFO, "Error in : enterClase <br>" + displayErrorMessage(ex));
				return false;
			}
		
	}
	
	public boolean isSearchFieldsCleared()
	{
		boolean flag=false;
		try{
			
			String firstfield=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".periodfiscal");
			String secondfield=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".foreigncurrencycoin");

			
		flag=	elementhandler.getTextFromValueAttribute(firstfield).contains("")
				&& elementhandler.getTextFromValueAttribute(secondfield).contains("");
					
		}catch(Exception e){
		extentLogger.log(LogStatus.INFO, "Error in : isSearchFieldsCleared <br>"+displayErrorMessage(e));
		flag=false;				
	}
	return flag;
	}
	
	public boolean isSearchFieldsClearedAfterClickingonClearbutton()
	{
		boolean flag=false;
		try{
			
			String Period=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".periodfiscal");
			String coin=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".foreigncurrencycoin");
			
			
		flag=	elementhandler.getTextFromValueAttribute(Period).contains("")
				&& elementhandler.getTextFromValueAttribute(coin).contains("");
					
		}catch(Exception e){
		extentLogger.log(LogStatus.INFO, "Error in : isSearchFieldsCleared <br>"+displayErrorMessage(e));
		flag=false;				
	}
	return flag;
	}
}


	
	