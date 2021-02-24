package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class ActionsPage extends ToolsPage {

	public ActionsPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isActionSearchTemplateDisplayed()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".periodfiscal")).isDisplayed()
				&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".denominaciontextbox")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".denominacionselectoption")).isDisplayed()
			&& isClassFieldDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionrestbutton")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionsearchbutton")).isDisplayed();
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : verifyActionSearchTemplate <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	public boolean isClassFieldDisplayed() {
		boolean classDisplayed =false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".Clase");
			classDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isClassFieldDisplayed  <br>" + displayErrorMessage(exc));
		}
		return classDisplayed;
	}
	

	
	public void selectDenominacion(String Denominacion) {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".denominacionselectoption");
			elementhandler.clickElement(locator);	

			locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".selectdenominacion");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualTitle = elements.get(row).getText();
				if(actualTitle.equals(Denominacion)){
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",  elements.get(row));
					 elements.get(row).click();
					 break;
				}
			}
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : selectDenominaci�n <br>" + displayErrorMessage(exc));
		}
	}
	

	public void selectPClaseDropdown(String clase) {
		try {
			Thread.sleep(1000);
			String selctor = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Clase");
			elementhandler.selectByVisibleText(selctor, clase);
			Thread.sleep(1000);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectPClaseDropdown <br>" + displayErrorMessage(ex));
		}
	}
	
	public boolean clickonClearButton()
	{
		boolean flag=false;
		try{			
				if(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".toolsclearbutton")).isDisplayed())
				{
					elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".clearbutton"));
					flag=true;
				}			
			}catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : clickonClearButton <br>"+displayErrorMessage(e));
			flag=false;				
		}
		return flag;
	}
	
	public boolean isSearchFieldsCleared()
	{
		boolean flag=false;
		try{
			
			String firstfield=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".periodfiscal");
			String secondfield=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".denominaciontextbox");
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
	
	public boolean clickonNewSearhButton()
	{
		boolean flag=false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".newsearchaction");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
				flag=true;					
			}catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : clickonNewSearhButton <br>"+displayErrorMessage(e));
			flag=false;				
		}
		return flag;
	}
	
	public boolean clickOnReformulateSearch()
	{
		boolean flag=false;
		try {
			String locator= PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".reformulatesearch");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			flag=true;					
		}catch(Exception e){
		extentLogger.log(LogStatus.INFO, "Error in : clickonNewSearhButton <br>"+displayErrorMessage(e));
		flag=false;				
	}
		return flag;
	}
	
}
