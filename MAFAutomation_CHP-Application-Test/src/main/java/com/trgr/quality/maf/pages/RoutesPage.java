package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class RoutesPage extends SearchPage {

	public RoutesPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void enterDateDuration(String duration) 
	{
		try
		{
			Date currentDay = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String formattedDate = formatter.format(currentDay);
			
			//enter from date on the page
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".fromdate")).sendKeys(""+formattedDate+"");

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -Integer.parseInt(duration.split("\\s")[0]));
			Date resultDate = cal.getTime();
			formattedDate = formatter.format(resultDate);

			//enter to date on the page
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".todate")).sendKeys(""+formattedDate+"");

		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : enterDateDuration <br>"+displayErrorMessage(ex));
		}	
	
	}

	public void selectValueForDateDuration(String durationVal) 
	{

		try
		{
			Select duration = new Select(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".dateperiod")));
			duration.selectByValue(durationVal);
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : selectValueForDateDuration <br>"+displayErrorMessage(ex));
		}
		
		
	}

	//verify search page for term

	public void enterSearchByTerm(String searchTerm) 
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".searchbytermtxtbox")).sendKeys(searchTerm);;
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : selectSearchByTerm <br>"+displayErrorMessage(ex));
		}
		
	}

	
	//verify standardNum data
	public void enterStandardNumber(String standardNum)
	{
		try
		{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest +".standardnum")).sendKeys(standardNum);
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : selectSearchByTerm <br>"+displayErrorMessage(ex));
		}
		
	}
}
