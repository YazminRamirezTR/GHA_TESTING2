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
import com.trgr.quality.maf.handlers.BaseHandler;

public class ContenttreeOnsearchResultPage extends BasePage {

	BaseHandler basehandler;
	String keyword;
	boolean flag = false;

	public ContenttreeOnsearchResultPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// WebDriverFactory.waitForElementUsingWebElement(driver,
		// elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."
		// + BaseTest.productUnderTest+".resultsList")), 20);
		// This element is not loading in some cases.
	}

	// verifying Document View page is displayed
	public boolean isTreeContentViewDisplayed() throws IOException {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".DocumentViewdisplayedheader"))
					.isDisplayed();
			String ValidateHeader = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".DocumentViewdisplayedheader"))
					.getText();
			String ValidateSubHeader = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".DocumentViewdisplayedSubheader"))
					.getText();
			if (ValidateHeader
					.contains(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest
							+ ".DocumentViewdisplayedvalidatepagekeyStringone"))
					&& ValidateSubHeader.contains(PropertiesRepository.getString("com.trgr.maf."
							+ BaseTest.productUnderTest + ".DocumentViewdisplayedvalidatepagekeyStringtwo")))
				return true;
			else
				return false;
		} catch (Exception exc) {

			extentLogger.log(LogStatus.INFO,
					"Error in : isDocumentDisplayPageDispalyed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method clicks on item in first level content tree
	 */
	public void clickExpandItemInContentTree(String itemName) {

		try {
			WebElement element = elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".content_tree"));
			List<WebElement> listOfCategories = element.findElements(By.tagName("li"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getText().contains(itemName)) {
						WebElement expandImg = listOfCategories.get(rowNum).findElement(By.tagName("span"));
						if (expandImg.getAttribute("class").equals("collapsed")) {
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", expandImg);
							expandImg.click();
							Thread.sleep(2000);
						}
						break;
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickItemInContentTree <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks if an item displayed in the content tree returns true
	 * on success
	 */
	public boolean isItemDisplayedInContentTree(String itemName) {
		boolean itemDisplayed = false;
		try {
			WebElement element = elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".content_tree"));
			List<WebElement> listOfCategories = element.findElements(By.tagName("li"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getText().contains(itemName)) {
						itemDisplayed = true;
						break;
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isItemDisplayedInContentTree <br>" + displayErrorMessage(exc));
			itemDisplayed = false;
		}
		return itemDisplayed;
	}

	/*
	 * This method checks if an item displayed in the content tree returns true
	 * on success
	 */
	public boolean isAreaFoundInContentTree(String itemName) {
		boolean itemDisplayed = false;
		try {
			WebElement element = elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".content_tree"));
			List<WebElement> listOfCategories = element.findElements(By.tagName("li"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					List<WebElement> listOfCategoriesLink = listOfCategories.get(rowNum).findElements(By.tagName("a"));
					if (listOfCategoriesLink.size() > 0) {
						if (listOfCategoriesLink.get(0).getText().equals(itemName)) {
							itemDisplayed = true;
							break;
						}
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isAreaFoundInContentTree <br>" + displayErrorMessage(exc));
			itemDisplayed = false;
		}
		return itemDisplayed;
	}

	/*
	 * This method selects Area from content tree returns true on success
	 */
	public boolean selectAreaFromContentTree(String itemName) {
		boolean itemDisplayed = false;
		try {
			WebElement element = elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".content_tree"));
			List<WebElement> listOfCategories = element.findElements(By.tagName("li"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					List<WebElement> listOfCategoriesLink = listOfCategories.get(rowNum).findElements(By.tagName("a"));
					if (listOfCategoriesLink.get(0).getText().equals(itemName)) {
						listOfCategoriesLink.get(0).click();
						Thread.sleep(2000);
						break;
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : selectAreaFromContentTree <br>" + displayErrorMessage(exc));
			itemDisplayed = false;
		}
		return itemDisplayed;
	}

	/*
	 * This method returns the content sub tree webelement
	 */
	public WebElement getFirstLevelContentTree(String itemName) {

		try {
			WebElement element = elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".content_tree"));
			List<WebElement> listOfCategories = element.findElements(By.tagName("li"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getText().contains(itemName)) {
						return listOfCategories.get(rowNum);
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getContentSubTree <br>" + displayErrorMessage(exc));
		}
		return null;
	}

	/*
	 * This method clicks on the item in visible content sub tree webelement
	 * Returns document display page
	 */
	public ContenttreeOnsearchResultPage clickDocumentInSubContentTree(WebElement element, String itemName) {
		ContenttreeOnsearchResultPage contenttreeOnsearchResultPage = null;
		try {
			List<WebElement> listOfCategories = element.findElements(By.tagName("div"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getAttribute("class").equals("docName")) {
						WebElement docLink = listOfCategories.get(rowNum).findElement(By.tagName("a"));
						String linkText = docLink.getText();
						if (linkText.trim().equals(itemName.trim())) {
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", docLink);
							docLink.click();
							Thread.sleep(5000);
							contenttreeOnsearchResultPage = new ContenttreeOnsearchResultPage(driver);
							break;
						}
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSubContentTreeDocument <br>" + displayErrorMessage(exc));
		}
		return contenttreeOnsearchResultPage;
	}

	/*
	 * This method checks the display of document in content sub tree webelement
	 * Returns boolean
	 */
	public boolean isDocumentDisplayedInSubContentTree(WebElement element, String itemName) {
		boolean docDisplayed = false;
		try {
			List<WebElement> listOfCategories = element.findElements(By.tagName("div"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getAttribute("class").equals("docName")) {
						WebElement docLink = listOfCategories.get(rowNum).findElement(By.tagName("a"));
						String linkText = docLink.getText();
						if (linkText.trim().equals(itemName.trim())) {
							docDisplayed = true;
							break;
						}
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDocumentDisplayedInSubContentTree <br>" + displayErrorMessage(exc));
		}
		return docDisplayed;
	}

	/*
	 * This method checks the display of document in content sub tree webelement
	 * Returns boolean
	 */
	public boolean isAnyDocumentVisibileInSubContentTree(WebElement element) {
		boolean docDisplayed = false;
		try {
			List<WebElement> listOfCategories = element.findElements(By.tagName("div"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getAttribute("class").equals("docName")) {
						WebElement docLink = listOfCategories.get(rowNum).findElement(By.tagName("a"));
						if (docLink.getText().length() > 0) {
							docDisplayed = true;
							break;
						}
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isAnyDocumentVisibileInSubContentTree <br>" + displayErrorMessage(exc));
		}
		return docDisplayed;
	}

	/*
	 * This method returns the content sub tree webelement
	 */
	public WebElement getSubContentTreeElement(WebElement element, String itemName) {

		try {
			List<WebElement> listOfCategories = element.findElements(By.tagName("ul"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getText().contains(itemName)) {
						return listOfCategories.get(rowNum);
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getSubContentTreeElement <br>" + displayErrorMessage(exc));
		}
		return null;
	}

	/*
	 * This method checks if the content sub tree exist returns true on success
	 */
	public boolean isSubContentTreeElementPresent(WebElement element) {
		boolean subTreeDsiplayed = false;
		try {
			List<WebElement> listOfCategories = element.findElements(By.tagName("ul"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					subTreeDsiplayed = true;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSubContentTreeElementPresent <br>" + displayErrorMessage(exc));
		}
		return subTreeDsiplayed;
	}

	/*
	 * This method clicks on expand item in any item in content tree
	 */
	public void expandContentTreeElement(WebElement element) {

		try {
			if (WebDriverFactory.isDisplayed(driver, element)) {
				WebElement expandImg = element.findElement(By.tagName("span"));
				if (expandImg.getAttribute("class").equals("collapsed")) {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", expandImg);
					expandImg.click();
					Thread.sleep(2000);
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : expandContentTreeElement <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks if content tree element is expanded
	 */
	public boolean isContentTreeElementExpanded(WebElement element) {

		try {
			if (WebDriverFactory.isDisplayed(driver, element)) {
				WebElement expandImg = element.findElement(By.tagName("span"));
				if (expandImg.getAttribute("class").equals("expanded")) {
					return true;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isContentTreeElementExpanded <br>" + displayErrorMessage(exc));
		}
		return false;
	}

	/*
	 * This method checks if content tree element is collapsed
	 */
	public boolean isContentTreeElementCollapsed(WebElement element) {

		try {
			if (WebDriverFactory.isDisplayed(driver, element)) {
				WebElement expandImg = element.findElement(By.tagName("span"));
				if (expandImg.getAttribute("class").equals("collapsed")) {
					return true;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isContentTreeElementCollapsed <br>" + displayErrorMessage(exc));
		}
		return false;
	}

	/*
	 * This method clicks on item in any level of content tree
	 */
	public void clickExpandItemInSubContentTree(WebElement element, String itemName) {

		try {
			List<WebElement> listOfCategories = element.findElements(By.tagName("li"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getText().startsWith(itemName)) {
						WebElement expandImg = listOfCategories.get(rowNum).findElement(By.tagName("span"));
						if (expandImg.getAttribute("class").equals("collapsed")) {
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", expandImg);
							expandImg.click();
							Thread.sleep(2000);
						}
						break;
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickExpandItemInSubContentTree <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks on item in any level of content tree
	 */
	public boolean isItemPresentInSubContentTree(WebElement element, String itemName) {

		try {
			List<WebElement> listOfCategories = element.findElements(By.tagName("li"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					//System.out.println(listOfCategories.get(rowNum).getText());
					if (listOfCategories.get(rowNum).getText().trim().startsWith(itemName.trim())) {
						return true;
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isItemPresentInSubContentTree <br>" + displayErrorMessage(exc));
		}
		return false;
	}

	/*
	 * This method clicks on item in any level of content tree
	 */
	public WebElement getFirstItemInSubContentTree(WebElement element) {

		try {
			List<WebElement> listOfCategories = element.findElements(By.tagName("ul"));
			for (int row = 0; row < listOfCategories.size(); row++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(row))) {
					List<WebElement> listOfSubCategories = listOfCategories.get(row).findElements(By.tagName("li"));
					for (int subrow = 0; subrow < listOfSubCategories.size(); subrow++) {
						if (WebDriverFactory.isDisplayed(driver, listOfSubCategories.get(subrow))) {
							return listOfSubCategories.get(subrow);
						}
					}
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getFirstItemInSubContentTree <br>" + displayErrorMessage(exc));
		}
		return null;
	}

	public boolean isListOfDocumentsLinkDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".list_of_document_link"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isListOfDocumentsLinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public SearchResultsPage clickListOfDocumentsLink() {
		SearchResultsPage docView = null;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".list_of_document_link");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			docView = new SearchResultsPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickListOfDocumentsLink <br>" + displayErrorMessage(ex));
		}
		return docView;
	}

	public void ClickOnExpandLevel2Link() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandlevel2");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(2000);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnExpandLevel2Link <br>" + displayErrorMessage(ex));

		}

	}

	public void ClickOnCollapseLevel1Link() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".collapsetolevel1");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(1000);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnCollapseLevel1Link <br>" + displayErrorMessage(ex));

		}

	}

	public boolean isContentTreeExpanded() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".treenodesexpanded"))
					.isDisplayed();

		} catch (Exception ex) {
			// need to check for the false values so logging info is not needed
			// in this case.
			// extentLogger.log(LogStatus.INFO, "Error in :
			// isContentTreeExpanded <br>"+displayErrorMessage(ex));
			return false;
		}
	}

}
