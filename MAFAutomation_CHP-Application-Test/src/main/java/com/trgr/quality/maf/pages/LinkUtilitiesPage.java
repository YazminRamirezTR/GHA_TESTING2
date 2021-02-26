package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class LinkUtilitiesPage extends BasePage {

	public LinkUtilitiesPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	
	public boolean isLinkUtilityPageDisplayed() 
	{
		try {
			return elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linkutilitypage_heading")).getText().equals("Links útiles" );

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isLinkUtilityPageDisplayed <br>" + displayErrorMessage(e));
			return false;
		}

	}
	
	//verify link is present or in linkutilities page
	
	public boolean validateLinksPresent(String linkname)
	{
		boolean flag = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf."+ productUnderTest + ".utiltypage_alllinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			
			for(int i=0;i<alllinks.size();i++)
			{
				String actlink = alllinks.get(i).getText();
				if(actlink.equalsIgnoreCase(linkname))
				{
					flag=true;
					break;
				}
			}
		}catch(Exception ex){
			return false;
		}
		return flag;
	}
}
