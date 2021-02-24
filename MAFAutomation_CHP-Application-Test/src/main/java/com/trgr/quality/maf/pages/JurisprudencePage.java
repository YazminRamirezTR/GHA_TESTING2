package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class JurisprudencePage extends SearchPage {

	public JurisprudencePage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void enterPartiesName(String partyName) {
		elementhandler
				.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".partynamefield"))
				.sendKeys(partyName);
	}
	
	public void enterNumberThesis(String numerThesis) {
		elementhandler
				.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".numberthesis"))
				.sendKeys(numerThesis);
	
	
	}
	
	public void enternumberThesisMexJurisprudencePage(String numerThesis,String fistSuggestion) {
		
		try 
		{
			elementhandler
		.getElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".numberthesis"))
		.sendKeys(numerThesis);
		String xpath=	"//div[@id='termSuggestions']//div[contains(@id,'WnumResolucion_')]";
		List<WebElement> ele=driver.findElements(By.xpath(xpath));
		for(int i=0;i<ele.size();i++) {
			if (ele.get(i).getText().trim().equals(fistSuggestion.trim())) {
				ele.get(i).click();
				break;
			}
			}
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : enternumberThesisMexJurisprudencePage <br>" + displayErrorMessage(ex));
		}
	}

	
	/*
	 * This method clicks on the SAVE option on the Issuer and Season  ( Emisor y Época ) pop up
	 */
	public void clickOnSaveOnIssuerAndSeasonPopUp() {
		
		try
		{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudencecheckboxgrid")), 30);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudencecheckboxgrid"));	
		}
		catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSaveOnIssuerAndSeasonPopUp <br>" + displayErrorMessage(ex));
		}
		
		
	}
	
	
	/*
	 * This method clicks on the Issue and season ( Emisor y Época ) field to display the pop up window
	 */
	public void clickIssuerAndSeason() {
		
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudenceissueandseason")), 30);
		elementhandler.clickElement(PropertiesRepository
				.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudenceissueandseason"));
		}catch(Exception ex)
		{
			extentLogger.log(LogStatus.INFO, "Error in : clickIssuerAndSeason <br>" + displayErrorMessage(ex));
		}
	}

	public void enterRubroValue(String rubro) {
		elementhandler
				.getElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudencerubro"))
				.sendKeys(rubro);

	}
	
	/*
	 * This method is used to select the required options / check boxes on the Issue and Season
	 * (( Emisor y Época ) pop up window
	 */
	public void selectSelectionOnIssuerAndSeasonPopUp(String selections) 
	{
		try {
			//uncheck all the checkboxes on the pop up window. By default all the options are selected hence unselect 
			String locator=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkallforTFJA");
			String selector=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkallforSCJN");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkallforTFJA")), 30);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkallforSCJN")), 30);
			if(WebDriverFactory.isDisplayed(driver, elementhandler.findElement(locator)) && WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector)) )
			{
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkallforTFJA")).click();
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkallforSCJN")).click();
			}
			//Per the request select the option on the pop up
			if(selections.contains("TFJATodas"))
			{
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkallforTFJA")).click();	
			}
			else if(selections.contains("SCJNTodas"))
			{
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkallforSCJN")).click();
			}
			else
			{
				//select both the SCJN and TFJA all check box 
				//TODO : perform any operation here as needed if the new test case is created.
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkallforTFJA")).click();
				elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkallforSCJN")).click();
			}
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : selectSelectionOnIssuerAndSeasonPopUp <br>" + displayErrorMessage(exc));
		}
	}

	public void enterCourtInfo(String courtName) {
		elementhandler
				.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".courtName"))
				.sendKeys(courtName);
	}

	public void enterCitaOnline(String citaOnline) {
		elementhandler
				.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".citaonline"))
				.sendKeys(citaOnline);
	}

	public boolean courtNamesAvailableOnTreeView() {
		elementhandler.clickElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".courttreelink"));

		List<WebElement> element = elementhandler.findElements(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastcourtonlist"));
		int count = element.size();

		String idOfElement = element.get(count - 2).getAttribute("id");
		element.get(count - 2).click();

		return driver.findElement(By.xpath("//li[@id='" + idOfElement + "']/span")).isDisplayed();
	}

	public void closeCourtNameTreeView() {
		elementhandler.clickElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".closecourttreeview"));
	}

	public boolean isJurisprudencePageDispalyed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudenceadvancesearchlabel"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isJurisprudencePageDispalyed <br>" + displayErrorMessage(exc));
			return false;

		}
	}

	public SearchResultsPage clickSearchButton() throws IllegalArgumentException, IOException, InterruptedException {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudencepagesearchbutton"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearchButton <br>" + displayErrorMessage(exc));
		}
		return new SearchResultsPage(driver);
	}

	public void enterThematicTextOnJurisprudencePage(String key) {
		try {
			
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonjurisprudencepage")).click();
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonjurisprudencepage")).clear();
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thematicsearchboxonjurisprudencepage");
			elementhandler.writeText(locator, key);
			Thread.sleep(2000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterThematicTextOnJurisprudencePage <br>" + displayErrorMessage(exc));
		}
	}
	
	public boolean isTheSuggestionsDropdownDisplayed() 
	{
		try
		{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".thdropdown")).isDisplayed() || 
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".thdropdown")).isEnabled();
		}
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isTheSuggestionsDropdownDisplayed <br>"+displayErrorMessage(ex));
			return false;
		}

	}

	
}
