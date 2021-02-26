package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class DoctrinePage extends SearchPage{

	public DoctrinePage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void enterMonthAndYear(String monthAndYear)
	{
		String locator;
		try {
			locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".doctrinamonthandyear");
			elementhandler.writeText(locator, monthAndYear);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : enterMonthAndYear <br>"+displayErrorMessage(e));
		}
		
	}
	
	//verify title of the page 

	public void enterTitle(String title) 
	{
		String locator;
		try {
			locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".doctrinetitle");
			elementhandler.writeText(locator, title);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : enterTitle <br>"+displayErrorMessage(e));
		}
	}
	
	public void enterThematicValue(String key) 
	{
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".thematicsearchboxonsearchpage")).click();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".thematicsearchboxonsearchpage")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".thematicsearchboxonsearchpage")).sendKeys(key);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : enterThematicTextOnDoctrinePage <br>"+displayErrorMessage(exc));
		}
	}
	


}
