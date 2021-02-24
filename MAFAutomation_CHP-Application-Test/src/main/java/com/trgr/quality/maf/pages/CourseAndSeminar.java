package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.pages.BasePage;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class CourseAndSeminar extends BasePage {

	public CourseAndSeminar(WebDriver driver) throws IllegalArgumentException, IOException {
		super(driver);
	}

	/*
	 * This method is used to verify seminar image
	 */
	public boolean verifySeminarImage() throws Exception {
		boolean flag = false;
		try {
			flag = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".seminarimage"))
					.isDisplayed();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifySeminarImage <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method is used to verify seminar description
	 */
	public boolean verifySeminarDescription() throws Exception {
		boolean flag = false;
		try {
			flag = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".seminardescription"))
					.isDisplayed();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifySeminarDescription <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method is used to verify seminar image
	 */
	public boolean verifySeminarEventLink() throws Exception {
		boolean flag = false;
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clickherelink"));
			//Set st = driver.getWindowHandles();
			//Iterator<String> it = st.iterator();
			String parent = driver.getWindowHandles().toArray()[0].toString();
			String child = driver.getWindowHandles().toArray()[1].toString();
			driver.switchTo().window(child);

			String currenturl = driver.getCurrentUrl();
			String expectedurl = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".eventlink");
			if (currenturl.contentEquals(expectedurl)) {
				flag = true;
				driver.close();
				driver.switchTo().window(parent);
			} else
				flag = false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifySeminarEventLink <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method is used to verify external links available
	 */
	
	/*
	 * This method verifies if link present in Page Header returns true on
	 * success, false on failure
	 */
	public boolean isGivenLinkPresent(String expectedLink) {
		boolean flag = false;
		try {

			List<WebElement> alllinks = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".courseAndSeminarlinks"));

			for (int i = 0; i < alllinks.size(); i++) {
				String actlink = alllinks.get(i).getText().trim();
				if (expectedLink.trim().equalsIgnoreCase(actlink)) {
					flag = true;
					break;
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isGivenLinkPresent <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method is used to verify know max link available
	 */

	public boolean isKnowMaxLinkDisplayed()
	{
		try{
			return WebDriverFactory.isDisplayed(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".knowmax")));
		}catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isKnowMaxLinkDisplayed <br>" + displayErrorMessage(exc));
			return  false;
		}
	}
}
