package com.trgr.quality.maf.pages;

import java.io.IOException;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class FormsPage extends SearchPage {

	public FormsPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void enterIssuingBody(String issuingBody) throws InterruptedException {
		elementhandler
				.getElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".formsissuingbody"))
				.sendKeys(issuingBody);
		Thread.sleep(2000);
	}

	public boolean noResultsDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".errormessage"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : DocumentListfromDocumentDisplay <br>" + displayErrorMessage(exc));
			return false;

		}
	}

	public boolean isFormsPageDispalyed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".formadvancesearchlabel"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFormsPageDispalyed <br>" + displayErrorMessage(exc));
			return false;

		}
	}

	public void clickCleanButton() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".formspagecleanbutton"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCleanButton <br>" + displayErrorMessage(exc));
		}
	}

	public SearchResultsPage clickSearchButton() throws IllegalArgumentException, IOException, InterruptedException {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".formspagesearchbutton"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearchButton <br>" + displayErrorMessage(exc));
		}
		return new SearchResultsPage(driver);
	}

	public void enterThematicTextOnFormsPage(String key) {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonsearchpage")),
					90);
			elementhandler
			.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonsearchpage")).click();
			elementhandler
			.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonsearchpage")).clear();
			
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonsearchpage"))
					.sendKeys(key);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterThematicTextOnFormsPage <br>" + displayErrorMessage(exc));
		}
	}

		
}
