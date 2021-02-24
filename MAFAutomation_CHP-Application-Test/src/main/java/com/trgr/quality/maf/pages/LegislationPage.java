package com.trgr.quality.maf.pages;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class LegislationPage extends SearchPage {

	public LegislationPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		
	}
	
	public void selectTypeOfOrder(String typeOfOrder) 
	{
		
		try
		{
			if(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".legislationtypeoforder").contains("select"))
			{
				Select orderType = new Select(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".legislationtypeoforder")));
				orderType.selectByValue(typeOfOrder);
			}
			else
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".legislationtypeoforder")).sendKeys(typeOfOrder);
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error on Legislation <br>"+displayErrorMessage(ex));
			
		}
		
	}
	
	public void selectTypeOfStandard(String typeOfStandard) 
	{
		
		try
		{
			if(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".legislationtypeofstandard").contains("select"))
			{
				Select orderType = new Select(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".legislationtypeofstandard")));
				orderType.selectByValue(typeOfStandard);
			}
			else
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".legislationtypeofstandard")).sendKeys(typeOfStandard);
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error on Legislation <br>"+displayErrorMessage(ex));
			
		}
		
	}
		

	public void selectAmbit(String ambit) 
	{
		Select scope = new Select(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".legislationambit")));
		switch(ambit)
		{
		case "Internacional":
			scope.selectByIndex(3);
			break;
		case "NACI":
			scope.selectByValue(ambit);
			break;
		case "Federal":
			scope.selectByValue(ambit);
			break;
		case "Municipal":
			scope.selectByValue(ambit);
			break;
		case "Nacional":
			scope.selectByIndex(1);
			break;
			
		}
	}
	

	public void selectMuncipio(String muncipio) 
	{
		try{
			Select orderType = new Select(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".legislationmuncipality")));
			orderType.selectByValue(muncipio);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : selectMuncipio <br>" + displayErrorMessage(exc));
		}
		
	}
	
	public void selectIncludeModifications() 
	{
		elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".legislationincludemodifications"));
		
	}

	public void enterFreeWordOnlegilationPage(String freeword) {
		try {
			elementhandler
			.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
			.click();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
					.sendKeys(freeword);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterFreeWordOnSearchPage <br>" + displayErrorMessage(exc));
		}
	}
	
	public void enterThematicTextOnlegilationPage(String key) 
	{
		try{
			
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonjurisprudencepage")).click();
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonjurisprudencepage")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".thematicsearchboxonjurisprudencepage")).sendKeys(key);
			Thread.sleep(2000);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterThematicTextOnlegilationPage <br>"+displayErrorMessage(exc));
		}
	}
	
		
	public void enterEditedByOrgan(String searchText) {
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".editedbyorgan")).sendKeys(searchText);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterEditedByOrgan <br>"+displayErrorMessage(exc));
		}
		
	}

	public void enterDispositionDetails(String disposition) 
	{
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".legislationdisposition")).sendKeys(disposition);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterDispositionDetails <br>"+displayErrorMessage(exc));
		}
		
	}
	
	public void enterStandardNumber(String stdnum) 
	{
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".advsearchlegis_standardnumber");
			elementhandler.writeText(locator, stdnum);;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterStandardNumber <br>"+displayErrorMessage(exc));
		}
		
	}
	public void enterSearchinLayoutName(String layoutname) {
		try {
			Thread.sleep(4000);
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".legislationdisposition"))
					.sendKeys(layoutname);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterSerachinLayoutName <br>" + displayErrorMessage(exc));
		}
	}


}
