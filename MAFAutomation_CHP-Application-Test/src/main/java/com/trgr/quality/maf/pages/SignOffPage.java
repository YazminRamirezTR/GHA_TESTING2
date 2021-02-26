package com.trgr.quality.maf.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class SignOffPage extends BasePage{

	 String keyword;
	 boolean flag= false;

	public SignOffPage(WebDriver driver, String productUrl) throws IllegalArgumentException, IOException {
		super(driver);
	}
	
	public SignOffPage(WebDriver driver) throws Exception  {
		super(driver);
	}
	public boolean issignoffpagedisplay()
	{
		
		flag=false;
		try{
		String ProductType = BaseTest.productUnderTest;
		if(ProductType.equalsIgnoreCase(PropertiesRepository.getString("Brazil"))){
			keyword =elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".SignoffText"));
			if (keyword.equals("Sessão Encerrada"))
				flag = true;			
		}
		else{
				keyword =elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".SignoffText"));
				if (keyword.equals("Cerrar sesión"))
					flag = true;				
		}
		}catch(Exception exc){
			
			extentLogger.log(LogStatus.INFO, "Error in : issignoffpagedisplay <br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
			
		
	}
	public boolean validateSignoffSummary()
	{
		flag = false;
		String text="";
		try{
		switch(BaseTest.productUnderTest){
		
			case "chparg":
			case "chppy":
			case "chpury":
				text = "Gracias por usar La Ley Online – Checkpoint";
				break;
				
			case "chpperu":
					text = "Gracias por usar Checkpoint Perú";
					break;
				
			case "chpmex":
					text= "Gracias por usar Checkpoint";
					break;
					
			case "chpbr":
				text = "Obrigado por utilizar o Checkpoint.";
				break;
				
			case "chppe":
				text = "Gracias por usar Checkpoint Perú";
				break;
		}
		
		keyword =elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".Signoffsummary"));
		if (keyword.equals(text))
			flag = true;
		
		if(BaseTest.productUnderTest.equals("chpbr")){
			flag = false;
			keyword=elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".sessiondata"));
			if (keyword.equals("Dados da sessão"))
				flag = true;
		}
		
		}catch(Exception exc){
			
			extentLogger.log(LogStatus.INFO, "Error in : validateSignoffSummary <br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}	
	
	//isNewsesssionlinkPresent
	public  boolean isNewsesssionlinkPresent() 
	{
		try{
			
		 flag = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".newsessionlink")).isDisplayed();
		 
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isNewsesssionlinkPresent <br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
		 
	}
	public boolean isSesssionDataPresent()
	{
		try{
	
		flag= elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".sessiondata")).isDisplayed();
		
		}catch(Exception exc){
			
			extentLogger.log(LogStatus.INFO, "Error in : isSesssionDataPresent <br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	
	public void  clikNewSession()
	{ 
		try{
			Thread.sleep(4000);
	
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".newsessionlink"));
		
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clikNewSession <br>"+displayErrorMessage(exc));
			
		}
		
	}
	
	public boolean verifySignoffSummary()
	{
		boolean flag=false;
		try{
		if(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".SignoffText")).isDisplayed() && elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".Signoffsummary")).isDisplayed())
		{	
			flag= true;
		}
		}catch(Exception exc)
		{
			extentLogger.log(LogStatus.INFO, "Error in : verifySignoffSummary <br>"+displayErrorMessage(exc));
			flag= false;
		}
		return flag;
	}
	
	public boolean isRestartSessionLinkDisplayed() {
		boolean linkDisplayed =false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".restartsessionlink");
			linkDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isRestarSessiontLinkDisplayed <br>" + displayErrorMessage(exc));
		}
		return linkDisplayed;
	}
	
	public void clickRestarSessiontLink() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".restartsessionlink");
			elementhandler.clickElement(selector);
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickRestarSessiontLink <br>" + displayErrorMessage(exc));
		}
	}
	
	public boolean isSessionExpired() {
		boolean sessionExpired = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".SignoffText");
			sessionExpired = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector))
					&& elementhandler.getText(selector).trim().equals("Su sesión ha expirado");
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSessionExpired <br>" + displayErrorMessage(exc));
		}
		return sessionExpired;
	}
}
