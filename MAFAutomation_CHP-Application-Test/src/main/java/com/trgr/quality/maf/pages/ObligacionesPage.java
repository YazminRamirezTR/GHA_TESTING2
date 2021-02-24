package com.trgr.quality.maf.pages;

import java.io.IOException;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class ObligacionesPage extends ToolsPage {

	public ObligacionesPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void enterPeriodValue(String periodo) {
		
	     WebElement element = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Perido"));
        Select oSelect = new Select(element);
        oSelect.selectByValue(periodo);
		// TODO Auto-generated method stub
	}


	public void enterDenominacion(String denominacion) {
		
		WebElement element1 = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".denominacion"));
       Select oSelect1 = new Select(element1);
       oSelect1.selectByValue(denominacion);
		// TODO Auto-generated method stub
		}


	public void enterClase(String clase) {
	     try {
	    		WebElement element = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Clase"));
	    	      Select claseSelect = new Select(element);
	    	      claseSelect.selectByValue(clase);
			} catch (Exception ex) {
				extentLogger.log(LogStatus.INFO, "Error in : enterClase <br>" + displayErrorMessage(ex));
			}
			
	 }
	//isObligacionesPageDisplayedAsExpected
	
	public boolean isObligacionesPageDisplayedAsExpected()
	{
		boolean flag=false;
		try{	
			
			//elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Obligaciones")).click();
			Thread.sleep(3000);
			flag=elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Perido")).isDisplayed();
			flag=flag && elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".denominacion")).isDisplayed();
			flag=flag && elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Clase")).isDisplayed();
            
			}catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : isObligacionesPageDisplayedAsExpected <br>"+displayErrorMessage(e));
			flag=false;				
		}
		return flag;
	}
/*	public boolean isGivenErrorMsgDisplayed(String errormessage)
	{
		boolean flag=false;
		try{	
			
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Obligaciones")).click();
			Thread.sleep(3000);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".search")).click();
			 String message = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".message")).getText();
			 if(message==errormessage){
					flag=true;
				}
			}catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : isGivenErrorMsgDisplayed <br>"+displayErrorMessage(e));
			flag=false;				
		}
		return flag;
	}*/
	
	public boolean verifyclickingReformular()
	{
		boolean flag=false;
		try{	
			
			
			
			 flag=elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Perido")).isDisplayed();
			 flag=flag && elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Denominacion")).isDisplayed();
			 flag=flag && elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Clase")).isDisplayed();
	            
			
			}catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : verifyclickingReformular <br>"+displayErrorMessage(e));
			flag=false;				
		}
		return flag;
	}
	
	
	
	
	public boolean clicklimpiar()
	{
		boolean flag=false;
		try{	
			
		    flag= elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".limpiar")).isDisplayed();
			 elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".limpiar")).click();
			 
			
			}catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : clicklimpiar <br>"+displayErrorMessage(e));
			flag=false;				
		}
		return flag;
	}


	


	public boolean verifyPeriod(String checkPeriod ) {
		
		
		boolean flag=false;
		try{
			 WebElement element = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Perido"));
	         Select oSelect = new Select(element);
	         WebElement tmp = oSelect.getFirstSelectedOption();
	         if(tmp.getText()==checkPeriod){
					flag=true;
				}
		
		}catch(Exception e){
		extentLogger.log(LogStatus.INFO, "Error in : verifyPeriod <br>"+displayErrorMessage(e));
		flag=false;				
	}
	return flag;
		
	}
	
public boolean verifyDenominacion(String checkDenominacion ) {
		
		
		boolean flag=false;
		try{
			WebElement element = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".denominacion"));
	         Select oSelect = new Select(element);
	         WebElement tmp = oSelect.getFirstSelectedOption();
	         if(tmp.getText()==checkDenominacion){
					flag=true;
				}
		
		}catch(Exception e){
		extentLogger.log(LogStatus.INFO, "Error in : verifyDenominacion <br>"+displayErrorMessage(e));
		flag=false;				
	}
	return flag;
		
	}
	
public boolean verifyClase(String checkClase ) {
	
	
	boolean flag=false;
	try{
		 WebElement element = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".Clase"));
         Select oSelect = new Select(element);
         WebElement tmp = oSelect.getFirstSelectedOption();
         if(tmp.getText()==checkClase){
				flag=true;
			}
	
	}catch(Exception e){
	extentLogger.log(LogStatus.INFO, "Error in : verifyClase <br>"+displayErrorMessage(e));
	flag=false;				
}
return flag;
	
}


/*public SearchResultsPage clickOnSearch() throws Exception {
	try {
		String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage");
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
	 return null;*/

public boolean isSearchFieldsCleared()
{
	boolean flag=false;
	try{
		
		String firstfield=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Perido");
		String secondfield=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".denominacion");
		String thirdfield=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Clase");
		
	flag=	elementhandler.getTextFromValueAttribute(firstfield).contains("")
			&& elementhandler.getTextFromValueAttribute(secondfield).contains("")
			&&  elementhandler.getTextFromValueAttribute(thirdfield).contains("");
				
	}catch(Exception e){
	extentLogger.log(LogStatus.INFO, "Error in : isSearchFieldsCleared <br>"+displayErrorMessage(e));
	flag=false;				
}
return flag;
}



}
	
	


