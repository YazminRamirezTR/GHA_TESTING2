package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class ValuationWheelsPage extends ToolsPage {

	public ValuationWheelsPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean verifyValuationofWheelsSearchTemplate()
	{
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".periodfiscal")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsbranddrodown")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelskinddropdown")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsmodeldropdown")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsproductionyrdropdown")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionrestbutton")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionsearchbutton")).isDisplayed();
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : verifyValuationofWheelsSearchTemplate <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	public void selectBrandDropdown(String brand) {
		try {
			Thread.sleep(1000);
			String selctor = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsbranddrodown");
			elementhandler.selectByVisibleText(selctor, brand);
			Thread.sleep(1000);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectBrandDropdown <br>" + displayErrorMessage(ex));
		}
	}
	
	public void selectKindDropdown(String kind) {
		try {
			Thread.sleep(1000);
			String selctor = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelskinddropdown");
			elementhandler.selectByVisibleText(selctor, kind);
			Thread.sleep(1000);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectKindDropdown <br>" + displayErrorMessage(ex));
		}
	}
	
	public void selectModelDropdown(String model) {
		try {
			Thread.sleep(1000);
			String selctor = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsmodeldropdown");
			elementhandler.selectByVisibleText(selctor, model);
			Thread.sleep(1000);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectModelDropdown <br>" + displayErrorMessage(ex));
		}
	}
	
	public void selectProductionYrDropdown(String productionyr) {
		try {
			Thread.sleep(1000);
			String selctor = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsproductionyrdropdown");
			elementhandler.selectByVisibleText(selctor, productionyr);
			Thread.sleep(1000);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectProductionYrDropdown <br>" + displayErrorMessage(ex));
		}
	}
	
	public boolean isIntialSearchValuesDisplay(String fiscalyr,String brand,String kind, String model,String productionyr)
	{
		boolean flag=false;
		try{
			
			String Period=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".periodfiscal");
			String Brand=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsbranddrodown");
			String Kind=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelskinddropdown");
			String Model=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsmodeldropdown");
			String Prodyr=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsproductionyrdropdown");
					
			
		flag=	elementhandler.getTextFromValueAttribute(Period).equals(fiscalyr)
				&& elementhandler.getTextFromValueAttribute(Brand).equals(brand)
				&&  elementhandler.getTextFromValueAttribute(Kind).equals(kind)
				&& elementhandler.getTextFromValueAttribute(Model).equals(model)
				&& elementhandler.getTextFromValueAttribute(Prodyr).equals(productionyr);
					
		}catch(Exception e){
		extentLogger.log(LogStatus.INFO, "Error in : isIntialSearchValuesDisplay <br>"+displayErrorMessage(e));
		flag=false;				
	}
	return flag;
	}
	
	public boolean isSearchFieldsClearedAfterClickingonClearbutton()
	{
		boolean flag=false;
		try{
			
			String Period=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".periodfiscal");
			String Brand=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsbranddrodown");
			String Kind=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelskinddropdown");
			String Model=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsmodeldropdown");
			String Prodyr=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelsproductionyrdropdown");
			
		flag=	elementhandler.getTextFromValueAttribute(Period).contains("")
				&& elementhandler.getTextFromValueAttribute(Brand).contains("")
				&&  elementhandler.getTextFromValueAttribute(Kind).contains("")
				&& elementhandler.getTextFromValueAttribute(Model).contains("")
				&& elementhandler.getTextFromValueAttribute(Prodyr).contains("");
				
					
		}catch(Exception e){
		extentLogger.log(LogStatus.INFO, "Error in : isSearchFieldsCleared <br>"+displayErrorMessage(e));
		flag=false;				
	}
	return flag;
	}
	
	public boolean isDeliveryOptionDisplay()
	{
		boolean flag=false;
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliverysave")).isDisplayed()
				&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliveryprint")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliveryemail")).isDisplayed();
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isDeliveryOptionDisplay <br>"+displayErrorMessage(exc));
			flag=false;
		}
		return flag;
		
	}

	public DeliveryPage clickEmailbutton()
	{
		DeliveryPage deliverypage= null;
		try {
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliveryemail"));
			deliverypage=new DeliveryPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickEmailbutton <br>" + displayErrorMessage(exc));
		}
		return deliverypage;
	}
	
	
	
}
