package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.commonutils.RandomUtils;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class DocumentDisplayPage extends BasePage {

	public DocumentDisplayPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	DocumentDisplayPage docdisplay;
	public void openSearchResult() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlistdocument"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : openSearchResult <br>" + displayErrorMessage(exc));
		}
	}

	public String documentDisplayContentMatching() {
		try {
			return elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultsdocumentdispaly"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : documentDisplayContentMatching <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public boolean DocumentDispalyIncreaseFont() throws InterruptedException {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".increasefontdocumentdispalypage"));
			// flag=(elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".increasefontdocumentdispalypage_"+i))!=null);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : DocumentDispalyIncreaseFont <br>" + displayErrorMessage(exc));
			return false;
		}
		return true;
	}

	public boolean DocumentDispalyDecreaseFont() throws InterruptedException {
		try {

			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".decreasefontdocumentdisplay"));

		} catch (NoSuchElementException exc) {
			extentLogger.log(LogStatus.INFO, "Error in : DocumentDispalyDecreaseFont <br>" + displayErrorMessage(exc));
			return false;
		}
		return true;

	}

	/*
	 * This method clicks on the List of Documents link on the document display
	 * page to return to search results. Returns SearchResultsPage as the user
	 * navigates back to results list
	 */
	public SearchResultsPage clickOnListOfDocumentsToReturnToSearchResults() throws InterruptedException {
		try {
			Thread.sleep(3000);
			elementhandler.clickElement(PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".listofdocumentslinkondocumentdisplaypage"));
			return new SearchResultsPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickOnListOfDocumentsToReturnToSearchResults <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	
	public boolean  isPresentHistoryDateLastReforms(String Expecteddate,String Finaldate) throws InterruptedException{
		 boolean flag=false;
	try {
  
		List<WebElement> allItems = elementhandler.findElements(PropertiesRepository
				.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latest_reforms_items"));
		for(int i=0;i<allItems.size();i++) {
			Thread.sleep(1000);
			
			String ActualDate=allItems.get(i).getText();
			if(ActualDate.contains(Expecteddate)) {
			String  ele4=	"//*[@showfulltextlink='true' and starts-with(@id,'I615d')]";
			WebElement element= driver.findElement(By.xpath(ele4));
			element.click();
			Thread.sleep(3000);
			WebElement ele=	driver.findElement(By.xpath("//*[text()='31/12/2015']"));
			if(ele.getText().contains(Finaldate));
			{
				flag=true;
				break;
				
			}
			
			}
		}
		
	}
	catch (Exception ex) 
	{
		extentLogger.log(LogStatus.INFO, "Error in : isPresentHistoryDateLastReforms <br>" + displayErrorMessage(ex));
		return false;
	}
	return flag;
    }
	
	public void newSearchfromDocumentDispaly() throws InterruptedException {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newsearchlinkondocumentdisplaypage"));
			Thread.sleep(2000);
			WebElement queryKeyword = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".queriesheaderinsearchpage"));
			WebElement freeword = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"));
			if (queryKeyword.isDisplayed() && freeword.getAttribute("value").isEmpty())
				System.out.print("");
			else
				System.out.print("");
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : newSearchfromDocumentDispaly <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks if the opened document has specific search text.
	 * Ensure the document is opened before calling this method.
	 */
	public Boolean isSearchTextDisplayedOnTheFullTextDoc(String searchText) throws Exception {
		try {
			String[] eachString = searchText.split("\\s");

			String docContent = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".documentcontent"))
					.getText();
			for (String text : eachString) {
				docContent.contains(text);
				return true;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSearchTextDisplayedOnTheFullTextDocFromList <br>" + displayErrorMessage(ex));
		}
		return false;
	}

	public void clickSearch() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage"));
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearch <br>" + displayErrorMessage(exc));
		}

	}

	public boolean clickDocumentLink() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlistdocument"));
			Thread.sleep(2000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDocumentLink <br>" + displayErrorMessage(exc));
			return false;
		}
		return true;

	}
//verifies the document displayed
	public boolean isFirstDocumentDisplayed() throws Exception {
		try {
			Thread.sleep(4000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultsdocumentdispaly");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.getElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFirstDocumentDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}

		return true;
	}
	
	//verifies the document displayed
	public boolean isSecondDocumentDisplayed() throws Exception {
		try {
			Thread.sleep(4000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".documentdisplaypage");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.getElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocumentDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}

		return true;
	}
	
	public boolean isDocumentDisplayed() throws Exception {
		try {
			
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultsdocumentdispaly");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(locator));
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocumentDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}


	public boolean increasefontdocumentDisplayed() throws Exception {
		try {
			elementhandler
					.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".increasefontdocumentdispalypage"))
					.isDisplayed();
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : increasefontdocumentDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}

		return true;
	}

	public boolean resultListTabDisplayed() throws Exception {
		try {
			Thread.sleep(2000);
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlisttab"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : resultListTabDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void clickResultListTab() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlisttab"));
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickResultListTab <br>" + displayErrorMessage(e));
		}

	}

	public boolean clickResultListTabNumber() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".pagenavigationnumber"));
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickResultListTabNumber <br>" + displayErrorMessage(e));
			return false;
		}

		return true;
	}

	public boolean clickResultListTabNext() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".pagenavigationnext"));
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickResultListTabNext <br>" + displayErrorMessage(e));
			return false;
		}

		return true;
	}

	public boolean clickResultListTabPrevious() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".pagenavigationprevious"));
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickResultListTabPrevious <br>" + displayErrorMessage(e));
			return false;
		}

		return true;
	}

	public SearchPage clickNewSearch() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newsearch"));
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickNewSearch <br>" + displayErrorMessage(e));
		}

		return new SearchPage(driver);

	}

	/*
	 * This method is used to click on next link
	 */
	public void clickOnNextLink() throws Exception {
		try {
			Thread.sleep(3000);
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextlink")),
					30);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextlink")));
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextlink"));
			Thread.sleep(5000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnNextLink <br>" + displayErrorMessage(e));
		}

	}

	/*
	 * This method is used to click on last link
	 */
	public void clickOnLastLink() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastlink")),
					20);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastlink"));
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnLastLink <br>" + displayErrorMessage(e));
		}

	}

	/*
	 * This method is used to click on previous link
	 */
	public void clickOnPreviousLink() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".previouslink"));
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnPreviousLink <br>" + displayErrorMessage(e));
		}

	}

	/*
	 * This method is used to click on first link
	 */
	public void clickOnFirstLink() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".navigationoptions_firstlink"));
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnFirstLink <br>" + displayErrorMessage(e));
		}

	}

	
	
	/*public SearchPage clickDoctrineInFilterresultswidget() {
		try {
		Thread.sleep(3000);
		String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".doctrinelink");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
		elementhandler.findElement(locator));
		elementhandler.clickElement(locator);
		return new SearchPage(driver);
		} catch (Exception ex) {
		extentLogger.log(LogStatus.INFO, "Error in : clickDoctrineInFilterresultswidget <br>" + displayErrorMessage(ex));
		return null;
		}
	}*/
	

	
	/*
	 * This method is used to verify previous link is enabled
	 */

	public boolean isPreviousLinkDisplayed() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".previouslink")), 20);
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".previouslink"))
					.isDisplayed();
			// Need to remove this once the search string is
			// updated to more specific to return results
			// faster
		} catch (Exception e) {

			return false;
		}

	}

	/*
	 * This method is used to verify first link is enabled
	 */
	public boolean isFirstLinkDisplayed() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".navigationoptions_firstlink")),
					20);
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".navigationoptions_firstlink"))
					.isDisplayed();
			// Need to remove this once the search string is
			// updated to more specific to return results
			// faster
		} catch (Exception e) {

			return false;
		}

	}

	/*
	 * This method is used to verify next link is enabled
	 */
	public boolean isNextLinkDisplayed() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextlink")),
					20);
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextlink"))
					.isDisplayed();
			// Need to remove this once the search string is
			// updated to more specific to return results
			// faster
		} catch (Exception e) {

			return false;
		}

	}

	/*
	 * This method is used to verify last link is enabled
	 */
	public boolean isLastLinkDisplayed() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastlink")),
					20);
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastlink"))
					.isDisplayed();
			// Need to remove this once the search string is
			// updated to more specific to return results
			// faster
		} catch (Exception e) {

			return false;
		}

	}

	public boolean searchPageDisplayed() throws Exception {
		try {
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbutton"))
					.isDisplayed();
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : searchPageDisplayed <br>" + displayErrorMessage(e));
			return false;
		}

		return true;
	}

	//this method is used to return to home page
	public HomePage returnToHomePage() {
		try {

			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab")));
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab")), 30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"));
			return new HomePage(driver);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : returnToHomePage <br>" + displayErrorMessage(e));
			return null;
		}
	}

	//clicks on my document icon
	public void clickMyDocumentFolder() {
		try {
			Thread.sleep(3000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtomyfolder"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickMyDocumentFolder <br>" + displayErrorMessage(e));
            
		}
	
	}

	public void clickCreateFolder() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".createmyfolder"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCreateFolder <br>" + displayErrorMessage(e));

		}
	}

	public void enterFolderName(String foldername) {
		try {
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".enterfoldername"),
					foldername);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : enterFolderName <br>" + displayErrorMessage(e));

		}
	}

	public void clickSaveButton() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".submitnewfolder"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveButton <br>" + displayErrorMessage(e));

		}
	}

	public void clickMyfolderHeader() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".headermydocument"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickMyfolderHeader <br>" + displayErrorMessage(e));

		}
	}

	public void clickPopupCancelButton() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".closemyfolederpopup"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPopupCancelButton <br>" + displayErrorMessage(e));

		}
	}

	public void clickMydocumentsUnderMyFolder() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentsheader"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickMydocumentsUnderMyFolder <br>" + displayErrorMessage(e));

		}
	}

	public String myfolderNamePresent() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentsheader"))
					.getText();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : myfolderNamePresent <br>" + displayErrorMessage(e));

		}
		return null;

	}

	public boolean showMyDocumentFolder() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtomyfolder"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : showMyDocumentFolder <br>" + displayErrorMessage(e));
			return false;

		}

	}

	public void clickKeyInformation() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".keyinformation"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickKeyInformation <br>" + displayErrorMessage(e));

		}
	}

	//verfies the Related Content Tab
	public boolean isRelatedContentTabDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".keyinformation"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : Related Content Tab verification <br>" + displayErrorMessage(e));
			return false;

		}
	}
	
	public boolean isExpectedFormAvaliable(String Expectevalue) {
		boolean flag=false;
		try {
		WebElement ele=	 elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".titledocumentsearchpage"));
		if(ele.getText().trim().equalsIgnoreCase(Expectevalue.trim()))
				return flag= true;
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isExpectedFormAvaliable <br>" + displayErrorMessage(e));
			return flag=false;

		}
		return flag;
	}
	
	
	public boolean verifyLeftPanelText(JSONArray listOfData) {
		boolean flag=false;
		try {
			
				String removedOption = listOfData.toString();
			System.out.println(removedOption);
		/*WebElement ele=	 elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".leftpanetextdata"));
		System.out.println(ele.getText().trim());
		
		if(Expectedvalue.equalsIgnoreCase("Rdo. de la Búsqueda")) {
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".leftpaneldatalink")).click();
			
		}
		*/
		elementhandler.getElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".leftpanetextlink")).click();
		
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyLeftPanelText <br>" + displayErrorMessage(e));
			return flag=false;

		}
		return flag;
	}
	//verfies the Content tree Tab
		public boolean isContentTreeTabDisplayed() {
			try {
				return elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contenttreetab"))
						.isDisplayed();
			} catch (Exception e) {
				extentLogger.log(LogStatus.INFO,
						"Error in : Related Content Tab verification <br>" + displayErrorMessage(e));
				return false;

			}
		}
	
	
	//Checks whether the Search button is present 
	public boolean isSearchButtonDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchtab"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSearchTabDisplayed <br>" + displayErrorMessage(e));
			return false;

		}
	}
	
	//Checks whether the AnnotationDropDown is present 
	public boolean isAnnotationDropDownDisplayed() {
		try {
			Thread.sleep(3000);
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".annotationdropdown"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isAnnotationDropDownDisplayed <br>" + displayErrorMessage(e));
			return false;

		}
	}
	
	//Checks whether the Text Up is present 
	public boolean isEnlargeTextOptionDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".enlargetext"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isEnlargeTextOptionDisplayed <br>" + displayErrorMessage(e));
			return false;

		}
	}
	
	//Checks whether the Text Down is present 
	public boolean isChangeTextOptionDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".changetext"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isChangeTextOptionDisplayed <br>" + displayErrorMessage(e));
			return false;

		}
	}
	


	//this method is used to scroll and click on cancel button in  a popup when click on my documents icon


		public boolean clickCancelInPopUp() {
		try {

		Thread.sleep(3000);
		String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".cancelbuttonmydocpopup");
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
		elementhandler.findElement(locator));
		elementhandler.clickElement(locator);

		} catch (Exception e) {
		extentLogger.log(LogStatus.INFO, "Error in : clickCancelInPopUp <br>" + displayErrorMessage(e));
	            return false;
		}
		return true;
		}
	
	

	public void clickKeyInformationTree() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".keyinformationtree"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickKeyInformationTree <br>" + displayErrorMessage(e));

		}
	}

	public void clickKeyInformationSubTree() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".keyinformationsubtree"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickKeyInformationSubTree <br>" + displayErrorMessage(e));

		}
	}

	public boolean windowTitleSubTree() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".windowkeyinformationsubtree"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : windowTitleSubTree <br>" + displayErrorMessage(e));
			return false;
		}
		return true;
	}

	/*
	 * This method clicks on the Follow Document option in document display page
	 */
	public void clickFollowDocument() {

		try {
			Thread.sleep(2000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".follow_document");
			WebElement webElement = elementhandler.getElement(locator);
			webElement.click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFollowDocument <br>" + displayErrorMessage(exc));
		}

	}

	/*
	 * This method clicks on the 'Index' option in document display page
	 */
	public void clickDocumentIndex() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indice_de_lo_norma");
			WebElement webElement = elementhandler.getElement(locator);
			webElement.click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDocumentIndex <br>" + displayErrorMessage(exc));
		}

	}

	/*
	 * This method checks if Follow Document option is visible or not returns
	 * boolean
	 */
	public boolean isFollowDocumentDisplayed() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".follow_document");
			WebElement webElement = elementhandler.getElement(locator);
			Thread.sleep(1000);
			WebDriverFactory.waitForElementUsingWebElement(driver, webElement, 40);
			return webElement.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFollowDocumentDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method checks if 'Index' option is visible or not returns boolean
	 */
	public boolean isIndexDisplayed() {

		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".indice_de_lo_norma");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isIndexDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method checks if Follow Document option(left pane) is visible or not
	 * returns boolean
	 */
	public boolean isFollowDocumentDisplayedInLeftPane() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".follow_document_leftpane_link");
			WebElement webElement = elementhandler.getElement(locator);
			return WebDriverFactory.isDisplayed(driver, webElement);
		} catch (Exception exc) {
			// extentLogger.log(LogStatus.INFO,
			// "Error in : isFollowDocumentDisplayedInLeftPane <br>" +
			// displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method clicks on the Follow Document option in left pane of document
	 * display page
	 */
	public void clickFollowDocumentInLeftPane() {
         
		WebElement confirmMsg = elementhandler.getElement(PropertiesRepository.getString
				("com.trgr.maf." + BaseTest.productUnderTest + ".follow_document_leftpane_confirmation"));
		try {
			Thread.sleep(6000);
			if(confirmMsg.isDisplayed() && confirmMsg.getText().contains("Marcar este documento para seguimiento"))
			{
					WebElement webElement = elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".follow_document_leftpane_link"));
					WebDriverFactory.waitForElementUsingWebElement(driver, webElement, 40);
					webElement.click();
				return; // Message is displayed if the document is already in tracking mode. In this case do nothing
			}
			else
			{
//				WebElement webElement = elementhandler.getElement(PropertiesRepository
//						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".follow_document_leftpane_link"));
//				webElement.click();	
				return;
			}
			
		} catch (Exception exc) {
			
			extentLogger.log(LogStatus.INFO,
						"Error in : clickFollowDocumentInLeftPane <br>" + displayErrorMessage(exc));				
		}

	}

	/*
	 * This method checks if Follow Document option is visible or not returns
	 * boolean
	 */
	public boolean isFollowDocConfirmationDisplayed() {
		boolean flag = false;
		try {
			Thread.sleep(6000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".follow_document_leftpane_confirmation");
			flag = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator));
			if (flag) {
				String expectedText = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".follow_document_confirmation_text");
				Thread.sleep(7000);
				if (!(flag = elementhandler.getText(locator).contains(expectedText)))
					if (elementhandler.getText(locator).contains("Processando")) {
						Thread.sleep(10000);
						flag = elementhandler.getText(locator).contains(expectedText);
					}
			}
 
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFollowDocConfirmationDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks if 'Document is already tracked' message is visible or
	 * not returns boolean
	 */
	public boolean isDocumentAlreadyTracked() {

		boolean flag = false;

		try {
			Thread.sleep(6000);
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".follow_document_leftpane_confirmation")),
					80);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".follow_document_leftpane_confirmation");
			String message = elementhandler.getText(locator);
		
			flag = message.contains("Documento ya trackeado") || message.contains("Documento en seguimiento")
					|| message.contains("Documento já está em seus alertas")
					|| message.contains("Este documento ya está en seguimiento") || message.contains("Este documento está en seguimiento");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocumentAlreadyTracked <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	/*
	 * This method checks if 'Index unavailable' message is visible or not
	 * returns boolean
	 */
	public boolean isIndexNotAvailable() {
		boolean flag = false;
		try {

			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indice_de_lo_norma_text");
			String message = elementhandler.getText(locator);

			flag = message.contains("El indice de la norma no se encuentra disponible en estos momentos.");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isIndexNotAvailable <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	/*
	 * This method checks if 'Index available' in left side returns boolean
	 */
	public boolean isIndexAvailable() {

		boolean flag = false;

		try {

			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indice_de_lo_norma_link");
			flag = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isIndexAvailable <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	/*
	 * This method opens alert page
	 */
	public AlertPage openAlertPage() throws IllegalArgumentException, IOException {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".myalertslink"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : openAlertPage <br>" + displayErrorMessage(exc));
		}

		return new AlertPage(driver);
	}

	/*
	 * This method returns the title of the document selected
	 */
	public String getDocumentDisplayTitleLineOne() throws IllegalArgumentException, IOException {
		try {
			return elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".document_title_line_one"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getDocumentDisplayTitleLineOne <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	/*
	 * This method returns the title of the document selected (reads from the
	 * hidden form) [ This can be different from DocumentDisplayTitleLineOne Can
	 * be used to find alert name etc ]
	 */
	public String getDocumentTitleFromHiddenForm() {
		try {
			return elementhandler.getTextFromValueAttribute(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".hidden_document_title"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getDocumentTitleFromHiddenForm <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	/*
	 * This method Click on 'Rdo. de la busqueda <Search Results tab >' in left
	 * pane in document display page
	 */
	public void clickSearchResultsTab() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".search_results_tab"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearchResultsTab <br>" + displayErrorMessage(e));
		}
	}

	/*
	 * This method verifies of Rev of Search tab is displayed on the left panel
	 * tab
	 */
	public boolean isRevOfSearchTabDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".search_results_tab"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isRevOfSearchTabDisplayed <br>" + displayErrorMessage(e));
			return false;
		}
	}

	/*
	 * This method Click on Save and Schedule Search in 'Rdo. de la busqueda
	 * <Search Results tab > in document display page
	 */
	public SaveAndSchedulePage clickSaveAndScheduleSearch() {
		SaveAndSchedulePage saveAndSchedulePage = null;
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".save_and_schedule_search_link"));
			saveAndSchedulePage = new SaveAndSchedulePage(driver);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveAndScheduleSearch <br>" + displayErrorMessage(e));
		}

		return saveAndSchedulePage;
	}

	/*
	 * This method clicks on Export button, and returns DeliveryPage
	 */
	public DeliveryPage clickExportButton() {
		DeliveryPage deliveryPage = null;
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".export_document_image"));
			deliveryPage = new DeliveryPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickExportButton <br>" + displayErrorMessage(exc));
		}

		return deliveryPage;
	}

	/*
	 * This method returns AnnotationsPage
	 */
	public AnnotationsPage getAnnotations() {
		AnnotationsPage annotations = null;
		try {
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".annotations_label"));
			annotations = new AnnotationsPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getAnnotations <br>" + displayErrorMessage(exc));
		}

		return annotations;
	}

	/*
	 * Returns a 10 digit number based on the current time. (to get unique
	 * number).
	 */
	public String getUniqueNumber() {

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmm");
		String datetime = ft.format(dNow);

		return datetime;
	}

	public boolean isHighlightedTextDisplayed() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".highlightedtext")),
					20);
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".highlightedtext"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isHighlightedTextDisplayed <br>" + displayErrorMessage(e));
			return false;
		}
		return true;
	}

	public boolean verifymyfolder(String name) {
		boolean flag = false;
		try {
			List<WebElement> foldernames = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".verifymyfolder"));
			String actualfoldername = null;
			for (int i = 0; i < foldernames.size(); i++) {
				actualfoldername = foldernames.get(i).getText();
				if (name.equals(actualfoldername))
					flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : verifymyfolder <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;

	}

	/*
	 * This method checks if 'Terms of use and privacy policies' link is
	 * available in footer returns boolean
	 */
	public boolean isTermsOfUseLinkPresent() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".terms_of_use_link");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			flag = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator));
			if (flag) {
				String actualText = elementhandler.getText(locator);
				flag = actualText.contains("Condiciones de uso y políticas de privacidad")
						|| actualText.contains("Términos de uso")
						|| actualText.contains("Política de privacidade e termos de uso");
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isTermsOfUseLinkPresent <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	/*
	 * This method clicks 'Terms of use and privacy policies' link
	 */
	public void clickTermsOfUseLink() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".terms_of_use_link");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickTermsOfUseLink <br>" + displayErrorMessage(exc));
		}

	}

	/*
	 * This method checks for 'Terms of use and privacy policies' content
	 */
	public boolean isTermsOfUseRedirectedtoCorrectURl(String url, String text) {
		boolean flag = false;
		;
		try {
			String acturl = driver.getCurrentUrl();
			if (acturl.contains(url)) {
				flag = true;
			} else {
				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".terms_of_use_header");
				flag = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator));
				if (flag) {
					String actualText = elementhandler.getText(locator);
					flag = actualText.contains(text);
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isTermsOfUsePresent <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;

	}

	/*
	 * This method checks for 'Terms of use and privacy policies' page
	 */
	public boolean isTermsOfUsePagePresent() {

		boolean flag;
		try {
			flag = driver.getCurrentUrl().equals(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".terms_of_use_pdfUrl"));
			if (!flag) {
				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".terms_of_use_header");
				flag = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator));
				if (flag) {
					String actualText = elementhandler.getText(locator);
					flag = actualText.contains("POLÍTICA DE PRIVACIDAD")
							|| actualText.contains("POLÍTICA DE PRIVACIDADE");
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isTermsOfUsePagePresent <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;

	}

	/*
	 * This method checks if 'Tree Content Tab' is available in left pane
	 * returns boolean
	 */
	public boolean isTreeContentTabDisplayed() {

		boolean flag = false;

		try {

			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".content_tree_tab");
			flag = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator));
			if (flag) {
				String expectedText = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".content_tree_tab_nametext");
				flag = elementhandler.getText(locator).contains(expectedText);
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isTreeContentTabDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	/*
	 * This method clicks 'Content Tree Tab'
	 */
	public void clickContentTreeTab() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".content_tree_tab");
			elementhandler.clickElement(locator);
			Thread.sleep(2000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickContentTreeTab <br>" + displayErrorMessage(exc));
		}
		
	}

	/*
	 * This method checks if ' Content Tree Structure' is available in left pane
	 * returns boolean
	 */
	public boolean isContentTreeStructureDisplayed() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".content_tree_all_links");
			List<WebElement> contentTree = elementhandler.findElements(locator);
			flag = (contentTree != null) && (!contentTree.isEmpty());
			if (!flag) {
				locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".content_tree_all_links_treeView");
				contentTree = elementhandler.findElements(locator);
				flag = (contentTree != null) && (!contentTree.isEmpty());
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isContentTreeStructureDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	/*
	 * This method checks if 'Content tree info unavailable' message is visible
	 * or not returns boolean
	 */
	public boolean isContentTreeInfoNotAvailable() {

		boolean flag = false;

		try {

			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".content_tree_tab_message");

			flag = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator));
			if (flag) {
				String expectedText = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".content_tree_tab_message_text");
				flag = elementhandler.getText(locator).contains(expectedText);
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isContentTreeInfoNotAvailable <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	public void ClickOnAddDocumentLinkToFavorite() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavourite"));
			;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : ClickOnAddDocumentLinkToFavorite <br>" + displayErrorMessage(exc));
		}
	}

	public void enterFavoritesName(String expectedname) {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritename"))
					.sendKeys(expectedname);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterFavoritesName <br>" + displayErrorMessage(exc));
		}
	}

	public void clickOnSaveOnFavoritesPopUp() {
		try {
			Thread.sleep(2000); // the pop up opening is longer than expected.
			WebElement element = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritesave"));
			//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			WebDriverFactory.waitForElementUsingWebElement(driver, element, 40);
			element.click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSaveOnFavoritesPopUp <br>" + displayErrorMessage(exc));
		}
	}

	public void addDocumentToGivenFavorites(String favname) {
		switch (productUnderTest) {
		case "chparg":
			try {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavourite"));
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritename"))
						.sendKeys(favname);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritesave"));
				clickOnAlertPopUP();
				Thread.sleep(2000);
			} catch (Exception e) {
				extentLogger.log(LogStatus.INFO, "Error in : AddDocumentToFavorite <br>" + displayErrorMessage(e));
			}
			break;

		case "chpury":
			try {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavourite"));
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritename"))
						.sendKeys(favname);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritesave"));
				clickOnAlertPopUP();
				Thread.sleep(2000);
			} catch (Exception e) {
				extentLogger.log(LogStatus.INFO, "Error in : AddDocumentToFavorite <br>" + displayErrorMessage(e));
			}
			break;

		case "chppy":
			try {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavourite"));
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritename"))
						.sendKeys(favname);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritesave"));
				clickOnAlertPopUP();
				Thread.sleep(2000);
			} catch (Exception e) {
				extentLogger.log(LogStatus.INFO, "Error in : AddDocumentToFavorite <br>" + displayErrorMessage(e));
			}
			break;

		case "chppe":
			try {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavourite"));
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritename"))
						.sendKeys(favname);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritesave"));
				clickOnAlertPopUP();
				Thread.sleep(2000);
			} catch (Exception e) {
				extentLogger.log(LogStatus.INFO, "Error in : AddDocumentToFavorite <br>" + displayErrorMessage(e));
			}
			break;

		case "chpmex":
			try {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavourite"));
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritename"))
						.sendKeys(favname);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritesave"));
				// clickOnAlertPopUP();
				Thread.sleep(2000);
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".closepopup"));

			} catch (Exception e) {
				extentLogger.log(LogStatus.INFO, "Error in : AddDocumentToFavorite <br>" + displayErrorMessage(e));
			}
			break;

		case "chpbr":
			try {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavourite"));
				String newname = "New" + RandomUtils.getUniqueNumber();
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritename"))
						.sendKeys(favname);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addFolderToSave"));
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritename"))
						.clear();
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritename"))
						.sendKeys(newname);
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addtofavouritesave"));
				// clickOnAlertPopUP();
				Thread.sleep(2000);

			} catch (Exception e) {
				extentLogger.log(LogStatus.INFO, "Error in : AddDocumentToFavorite <br>" + displayErrorMessage(e));
			}
			break;
		}
	}

	public void ClickOnLastReformsOnDocument() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastreform"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnLastReformsOnDocument <br>" + displayErrorMessage(ex));
		}

	}

	public boolean isLastReformOptionDisplayed() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastreform"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isLastReformOptionDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	/*
	 * Returns text of items present at related content tab (for latest reforms)
	 *
	 */
	public String readItemOnRelatedContentTab(int position) {
		try {
			List<WebElement> allItems = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latest_reforms_items"));
			if (allItems.size() >= position) {
				return allItems.get(position - 1).getText();
			} else {
				return null;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : readItemOnRelatedContentTab <br>" + displayErrorMessage(ex));
			return null;
		}

	}

	/*
	 * Clicks item present at given position. [related content tab (for latest
	 * reforms)]
	 *
	 */
	public void clickItemOnRelatedContentTab(int position) {
		try {
			List<WebElement> allItems = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latest_reforms_items"));
			allItems.get(position - 1).findElement(By.tagName("a")).click();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickItemOnRelatedContentTab <br>" + displayErrorMessage(ex));
		}

	}

	public boolean isFirstItemOnRelatedContentTabALink() {
		try {

			List<WebElement> allItems = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latest_reforms_items"));
			return (allItems.size() > 0) && allItems.get(0).findElements(By.tagName("a")).size() == 0;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFirstItemOnRelatedContentTabALink <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public boolean isSecondItemOnRelatedContentTabALink() {
		try {
			List<WebElement> allItems = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latest_reforms_items"));
			return (allItems.size() > 1) && allItems.get(1).findElements(By.tagName("a")).size() > 0;

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSecondItemOnRelatedContentTabALink <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void ClickSecondLinkOnRelatedTab() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sndlinkonrelatedtab"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickSecondLinkOnRelatedTab <br>" + displayErrorMessage(ex));
		}

	}

	public boolean isDocumentTitleHasRedColoredFlag() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".redflagondoctitle"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDocumentTitleHasRedColoredFlag <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void clickOnDocToScroll() {
		try {
			JavascriptExecutor js = ((JavascriptExecutor) driver);

			js.executeScript("window.scrollTo(0, 500)");

		} catch (Exception ex) {

		}

	}

	public boolean isGivenSectionDisplayedOnDoc(String sectionOnDoc) {
		try {
			// return
			// elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."
			// + BaseTest.productUnderTest +
			// ".maquilaoperation")).isDisplayed();
			return driver.findElement(By.xpath("//span[contains(text(),'operación de maquila')]")).isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isGivenSectionDisplayedOnDoc <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public boolean Clickscrollbutton() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".scrollbutton"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollDown = arguments[1];", 250);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Clicking Last Reform <br>" + displayErrorMessage(ex));
			return false;
		}
		return true;

	}

	public boolean Clickscontenttabtree() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contenettree"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Clicking Last Reform <br>" + displayErrorMessage(ex));
			return false;
		}

		return true;
	}

	public boolean Clickrevtabtree() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".reversetree"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Clicking Last Reform <br>" + displayErrorMessage(ex));
			return false;
		}

		return true;
	}

	public void clickonVoces() {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".voceslink"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Clicking vocessection <br>" + displayErrorMessage(ex));

		}

	}
	
	public void clickArticleLinkResultsPage() {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".articlelinkforvoces"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickArticleLinkResultsPage <br>" + displayErrorMessage(ex));

		}
	}

	public boolean isVocesRelatedSectionDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".vocessectionlink"))
					.isDisplayed();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Voces section <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	/*
	 * This method checks to see if the given text is displayed on the voces
	 * section Returns true / false based on the element display
	 */
	public boolean isGivenSearchTextOnTheVocesSection(String searchText) {
		try {
			Thread.sleep(5000);
			Thread.sleep(5000);		
			Thread.sleep(6000);
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".vocessectionlink"));
			
			
			List<WebElement> vocesList = element.findElements(By.tagName("li"));
		
			for (WebElement webElement : vocesList) {

				Thread.sleep(2000);
				if (webElement.getText().contains(searchText)) {
					return true;
				}
			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Voces section <br>" + displayErrorMessage(ex));
			return false;
		}
		return false;
	}

	
	
	public SearchPage Clicksearchtab() throws IllegalArgumentException, IOException {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpagetab"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Clicking Last Reform <br>" + displayErrorMessage(ex));

		}

		return new SearchPage(driver);
	}

	public boolean verifyDefaultPositioningArticleEntered() {
		try {
			Thread.sleep(2000);
		
			
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".abbrevation_article_docdisplay"))
					.isDisplayed() || 
					elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".abbrevation_artcile_docdisplayresultspage"))
					.isDisplayed();
			}

       catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyDefaultPositioningArticleEntered Last Reform <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public   boolean isResultsFormPageDisplayed() {
		boolean flag=false;
		try {
			Thread.sleep(5000);
			driver.findElement(By.xpath("//li[@id='legisRel']")).isDisplayed();
		    }
		  catch (Exception ex) {
				
				return flag=false;
			}
        return flag;
	}
	public boolean vocestooltip() {
		try {
			WebElement tooltip = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".voceslink"));
			String tooltiptext = tooltip.getAttribute("title");
			String text = PropertiesRepository.getString("com.trgr.maf.chpmex.vocestooltiptext");
			if (tooltiptext.equals(text))
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in :  vocestooltip <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean followtooltip() {
		try {
			WebElement tooltip = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followtooltip"));
			String tooltiptext = tooltip.getAttribute("title");
			String text = PropertiesRepository.getString("com.trgr.maf.chpmex.followtooltiptext");
			if (tooltiptext.equals(text))
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : followtooltip <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean indextooltip() {
		try {
			WebElement tooltip = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".indextooltip"));
			String tooltiptext = tooltip.getAttribute("title");
			String text = "Estructura de un ordenamiento (Ley, Reglamento, Decreto, etc.) clasificado por sus títulos, capítulos, secciones, etc.";
			if (tooltiptext.equals(text))
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : indextooltip <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void clickCorrelations() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".correlations"));
			Thread.sleep(60000);
			driver.switchTo().alert().accept();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCorrelations <br>" + displayErrorMessage(ex));

		}
	}

	public boolean clickSubCorrelations() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".subcorrelations"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in :clickSubCorrelations <br>" + displayErrorMessage(ex));
			return false;
		}
		return true;
	}

	public void clickFollowDoc() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followtooltip"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFollowDoc <br>" + displayErrorMessage(ex));

		}
	}

	public void clickLeftFollowDoc() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followdoc"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickLeftFollowDoc <br>" + displayErrorMessage(ex));

		}
	}

	public boolean followdocmessage() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".followdocmessage"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : followdocmessage <br>" + displayErrorMessage(ex));
			return false;
		}
		return true;
	}

	public void clickHomeTab() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickHomeTab <br>" + displayErrorMessage(ex));

		}
	}

	public boolean hometrakitalerts() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".trakitalerts"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : hometrakitalerts <br>" + displayErrorMessage(ex));
			return false;
		}
		return true;
	}

	public void clickLeftFollowDocremove() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".trakitalertsremove"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickLeftFollowDocremove <br>" + displayErrorMessage(ex));

		}
	}

	public boolean searchResultTab() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchreults"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultTab<br>" + displayErrorMessage(ex));
			return false;
		}
		return true;
	}

	public boolean keywordResultdocumentdispaly() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlistdocument"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : keywordResultdocumentdispaly<br>" + displayErrorMessage(ex));
			return false;
		}
		return true;
	}

	public boolean deliveryDocument() throws Exception {
		try {
			elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".printlink"))
					.isDisplayed();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".export_document_image"))
					.isDisplayed();
			return true;
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : deliveryDocument <br>" + displayErrorMessage(e));
			return false;
		}
	}

	/*
	 * This method checks if 'LatestReforms' option is visible or not returns
	 * boolean
	 */
	public boolean isLatestReformsOptionDisplayed() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latest_reforms");
			WebElement webElement = elementhandler.getElement(locator);
			return webElement.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLatestReformsDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method clicks on the 'LatestReforms' option in document display page
	 */
	public void clickLatestReforms() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latest_reforms");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
			WebElement webElement = elementhandler.getElement(locator);
			webElement.click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickLatestReforms <br>" + displayErrorMessage(exc));
		}

	}

public void  clickExpectedLinkFromResultsPage(String DocName) {
	
		try {
			Thread.sleep(2000);
			String alllinks = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".alllinksonformspage");
			List<WebElement> locators = elementhandler.findElements(alllinks);

			for (int i = 0; i < locators.size(); i++) {
				String linkname1 = locators.get(i).getText();
		
				if (linkname1.contains(DocName)) {
					Thread.sleep(1000);
					locators.get(i).click();
					break;
				}
				
			}
			 
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickExpectedLinkFromResultsPage <br>" + displayErrorMessage(exc));
		}
		
	}
	/*
	 * This method checks if 'Loading/error message for LatestReforms' is
	 * visible or not returns boolean
	 */
	public boolean isLatestReformsLoaded() {

		boolean reformsLoaded = false;

		try {
			boolean reformsLoading = false;
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latest_reforms_leftpane");
			String message = elementhandler.getText(locator);
			reformsLoading = message.trim().equals("Cargando");
			for (int count = 0; reformsLoading && count < 5; count++) {
				Thread.sleep(2000);
				message = elementhandler.getText(locator);
				reformsLoading = message.trim().equals("Cargando");
			}
			reformsLoaded = !reformsLoading;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLatestReformsLoading <br>" + displayErrorMessage(exc));
		}

		return reformsLoaded;
	}

	/*
	 * This method verifies ' Content Tree Structure links' against the
	 * listofitems returns boolean
	 */
	public boolean isContentTreeEqualsNodes(String thematicArea, String treeItems[]) {

		boolean flag = false;
		boolean contentNodesFound = false;

		try {
			List<WebElement> contentTree = null;
			try {
				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".content_tree_all_links");
				contentTree = elementhandler.findElements(locator);
				flag = (contentTree != null) && (!contentTree.isEmpty());
				if (!flag) {
					locator = PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".content_tree_all_links_treeView");
					contentTree = elementhandler.findElements(locator);
					flag = (contentTree != null) && (!contentTree.isEmpty());
				}
			} catch (Exception exc) {
				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".content_tree_all_links_treeView");
				contentTree = elementhandler.findElements(locator);
				flag = (contentTree != null) && (!contentTree.isEmpty());
			}

			if (flag) {
				contentNodesFound = contentTree.get(0).getText().trim().equals(thematicArea);
				if (contentNodesFound) {
					for (int row = 1; row < contentTree.size(); row++) {
						if (contentTree.get(row).getText().trim().equals(treeItems[row - 1]))
							contentNodesFound = true;
						else {
							contentNodesFound = false;
							break;
						}
					}
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isContentTreeEqualsNodes <br>" + displayErrorMessage(exc));
			contentNodesFound = false;
		}

		return contentNodesFound;
	}

	/*
	 * This method checks to see if the document title is displayed on the
	 * document display page Returns true / false based on the element
	 */
	public boolean isDocumentTitlePresent() {
		try {
			Thread.sleep(2000);
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".document_title"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocumentTitleDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isDocumentContentDisplayed() {
		try {
			Thread.sleep(2000);
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".documentdisplaycontent"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocumentTitleDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * Scroll to on the page by given X and Y Pixels
	 */
	public void scrollOnpageByGivenPixels(int xCount, int yCount) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(" + xCount + "," + yCount + ")", "");

	}

	/*
	 * Open the Correlations Icon to view the section links displayed on the
	 * document page.
	 */
	public void clickOnCorrelationsIcon(String marginalText, int articleNum) {
		try {
			String locator = "//span[@id='" + marginalText + "|A." + articleNum + "']/following-sibling::div";
			elementhandler.findElement("xpath=" + locator).click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnCorrelationsIcon <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isLegislationBoxDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sectionlegislation")));
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sectionlegislation"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLegislationBoxDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void ClickOnLegislationsLink() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sectionlegislation")));
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sectionlegislation"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnLegislationsLink <br>" + displayErrorMessage(exc));
		}

	}

	public boolean isJurisprudenciasBoxDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sectionjurisprudencia")));
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sectionjurisprudencia"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isJurisprudenciasBoxDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isDoctrinaBoxDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sectiondoctrina"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDoctrinaBoxDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * Marignal Text used for locators has this format: LEG-1931-1-A-329 This
	 * method is trying to look for locator with above format and ensures
	 * document is scrolled to appropriate place when opened from search results
	 * Return true if the document is scrolled to expected article of document
	 */
	public boolean isDocumentViewScrolledToGivenArticleNum(String marginalText, String articleNum) {
		try {

			Thread.sleep(500);
			String locator = "//div[@id='" + marginalText + "-A-" + articleNum + "']/div";
			return elementhandler.findElement("xpath=" + locator).getText().contains(articleNum);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDocumentViewScrolledToGivenArticleNum <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isRelationContainerDisplayedForLegislations() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relationcontainer"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDoctrinaBoxDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isToolTipDisplayed(String docTitle) {
		try {
			String locator = "//div[@class='relationshipHover']";
			return elementhandler.findElement("xpath=" + locator).isEnabled();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isToolTipDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	// Verify NIF Update in First document title in Document page
	public boolean verifyNIFUpdateInDocDisplayPage(String exptitle) {
		boolean flag = false;
		try {
			String actualtitle = getDocumentDisplayTitleLineOne();
			if (actualtitle.contains(exptitle)) {
				flag = true;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyNIFUpdateInDocDisplayPage <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method Click on 'Show reforma's icon for Article 1' in the document
	 * content
	 */
	public void clickReformasIconForArticleOne() {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".reformasiconarticleone")));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".articlereform")));
			String text = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".articlereform"))
					.getText();
			
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".reformasiconarticleone"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickReformasIconForArticleOne <br>" + displayErrorMessage(e));
		}
	}

	
	public boolean isPresentArtcileReforms(String value) {
		boolean flag=false;
		try {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,250)", "");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".articlereform")));
			String text = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".articlereform"))
					.getText();
		
			if(text.trim().equalsIgnoreCase(value.trim())) {
				flag=true;
			}
			/*elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".reformasiconarticleone"));*/
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isPresentArtcileReforms <br>" + displayErrorMessage(e));
		  flag=false;
		}
		return flag;
	}
	/*
	 * This method checks if Reformas table is displayed in the document content
	 */
	public boolean isReformasTableDisplayedForArticleOne() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".reformastable"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isReformasTableDisplayedForArticleOne <br>" + displayErrorMessage(e));
			return false;
		}
	}

	/*
	 * This method verifies if Reformas table header in the document content
	 */
	public boolean isReformasTableHeaderDisplayedForArticle(String reforms) {
		boolean headerDisplayed = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".reformastableheader"));
			if (WebDriverFactory.isDisplayed(driver, element)) {
				String tableHeader = element.getText();
				headerDisplayed = (tableHeader != null) && tableHeader.contains(reforms);
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isReformasTableHeaderDisplayedForArticle <br>" + displayErrorMessage(e));
			headerDisplayed = false;
		}
		return headerDisplayed;
	}

	/*
	 * This method double clicks(selects) on one word in ArticleTitle
	 */
	public boolean selectOneWordInArticleOneTitle() {
		try {
			WebElement elementOne = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".documentarticleonetitle"));
			// WebElement elementTwo
			// =elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest
			// +".documentarticleparaone"));
			// WebElement elementThree
			// =elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest
			// +".documentarticleparatwo"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementOne);
			Actions select = new Actions(driver);
			select.click(elementOne).build().perform();
			Thread.sleep(2000);
			select.moveToElement(elementOne, 25, 0).doubleClick().build().perform();

			return true;
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : selectOneWordInArticleOneTitle <br>" + displayErrorMessage(e));
			return false;
		}
	}

	/*
	 * This method checks if pop-up menu is displayed
	 */
	public boolean isPopUpMenuDisplayedForSelectedText() {
		try {
			Thread.sleep(1000);
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".selectmenufordelivery"));
			return WebDriverFactory.isDisplayed(driver, element);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isPopUpMenuDisplayedForSelectedText <br>" + displayErrorMessage(e));
			return false;
		}
	}

	/*
	 * This method clicks on Export in menu
	 */
	public boolean clickExportInPopUpMenu() {
		boolean exported = false;
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".menuoptionadddelivery"));
			exported = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickExportInPopUpMenu <br>" + displayErrorMessage(exc));
			exported = false;
		}

		return exported;
	}

	/*
	 * This method clicks on Print in menu
	 */
	public boolean clickPrintInPopUpMenu() {
		boolean printed = false;
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".menuoptionaddprint"));
			printed = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPrintInPopUpMenu <br>" + displayErrorMessage(exc));
			printed = false;
		}

		return printed;
	}

	/*
	 * This method clicks on Email in menu
	 */
	public boolean clickEmailInPopUpMenu() {
		boolean emailSent = false;
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".menuoptionaddemail"));
			Thread.sleep(2000);
			emailSent = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickEmailInPopUpMenu <br>" + displayErrorMessage(exc));
			emailSent = false;
		}

		return emailSent;
	}

	/*
	 * This method checks if confirmation message is displayed for sent Email
	 */
	public boolean isSelectedContentSentAsEmail() {
		boolean emailSent = false;
		try {
			WebElement element = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".emailsentconfirmation"));
			emailSent = WebDriverFactory.isDisplayed(driver, element)
					&& element.getText().contains("El contenido se ha enviado al mail configurado en preferencias");
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSelectedContentSentAsEmail <br>" + displayErrorMessage(exc));
			emailSent = false;
		}

		return emailSent;
	}

	/*
	 * This method closes sent Email pop-up
	 */
	public void closeSentEmailConfirmationPopUp() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".emailsentclosebutton"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : closeSentEmailConfirmationPopUp <br>" + displayErrorMessage(exc));
		}
	}

	// Scroll Down till Preamble and Verify the display of NIF paragraph
	public boolean verifyDisplayOfNIFParagraph() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docdisplay_preambletitle"));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(500);
			flag = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docdisplay_firstparagrapgh")));
			Thread.sleep(1000);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyDisplayOfNIFParagraph <br>" + displayErrorMessage(ex));
			flag = false;
		}

		return flag;
	}
	public boolean isPresentDocumentNIF(String text) {
		boolean flag= false;
		try {
			
       String locator =PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".documentexpectedreform" );
			
		String	actualText = elementhandler.getElement(locator).getText();
			if (actualText.contains(text))
				flag = true;
		}
		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isPresentDocumentNIF <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	// Scroll Down till Preamble and Verify the display of NIF Number
	public boolean verifyDisplayOfNIFNumber() {
		boolean flag = false;
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docdisplay_applicationretrospective"));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(500);
			flag = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docdisplay_paragrapgh_num")));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyDisplayOfNIFNumber <br>" + displayErrorMessage(ex));
			flag = false;
		}

		return flag;
	}
	// MAFAUTO-236,234,test cases to be automated for Chp Mex

	public boolean validateIssuingBody() {
		boolean flag = false;
		try {
			String exptext = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".issuingbodyfield")).getText();
					
			String acttext = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".issuingbodytext");

			if (acttext.contains(exptext)) {
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isReformasTableHeaderDisplayedForArticle <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}
	
	public boolean isVerifyIsuuingBodyText(String expvalue) {
		
		boolean flag = false;
		try {
			String exptext = "//*[@id=\"cv-button-1\"]//following-sibling::p/b[contains(text(),'Emisor')]//ancestor::p";
		String ele=driver.findElement(By.xpath(exptext)).getText();
				
			if (ele.contains(expvalue)) {
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isVerifyIsuuingBodyText <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean validateFinalValidityDate() {
		boolean flag = false;
		try {
			String exptext = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".finalvalidityfield"))
					.getText();
			String acttext = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".finalvaliditytext");

			if (acttext.contains(exptext)) {
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isReformasTableHeaderDisplayedForArticle <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean isFinalDatePresentResolution(String expvalue) {
		boolean flag = false;
		try {
			String exptext = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".finaldatevalidation"))
					.getAttribute("value");
			

			if (expvalue.contains(exptext)) {
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFinalDatePresentResolution <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}
	public boolean validateRubroLinkText(String linktext) {
		boolean flag = false;
		try {
			String text = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rubotitletext"))
					.getText();
			if (text.contains(linktext)) {
				flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateRubroLinkText <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isRelatedLegislationDisplayedInLeftPane() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislationleftpanel"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isRelatedLegislationDisplayedInLeftPane <br>" + displayErrorMessage(e));
			return false;
		}
	}

	public boolean isRelatedLegislationLinkExist(String expectedLink) {
		boolean linkFound = false;
		try {
			WebElement leftpanel = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislationleftpanel"));
			List<WebElement> leftpanellinks = leftpanel.findElements(By.tagName("a"));
			String actualLink = "";
			for (int row = 0; row < leftpanellinks.size(); row++) {
				actualLink = leftpanellinks.get(row).getText();
				if (actualLink != null && actualLink.equals(expectedLink)) {
					linkFound = true;
					break;
				}
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isRelatedLegislationLinkExist <br>" + displayErrorMessage(e));
			linkFound = false;
		}
		return linkFound;
	}

	public boolean clickRelatedLegislationLink(String expectedLink) {
		boolean linkFound = false;
		try {
			WebElement leftpanel = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislationleftpanel"));
			List<WebElement> leftpanellinks = leftpanel.findElements(By.tagName("a"));
			String actualLink = "";
			for (int row = 0; row < leftpanellinks.size(); row++) {
				actualLink = leftpanellinks.get(row).getText();
				if (actualLink != null && actualLink.equals(expectedLink)) {
					linkFound = true;
					leftpanellinks.get(row).click();
					break;
				}
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickRelatedLegislationLink <br>" + displayErrorMessage(e));
			linkFound = false;
		}
		return linkFound;
	}

	/*
	 * This method checks if pop-up document displayed or not
	 */
	public boolean isRelatedDocumentPopUpDisplayed() {
		boolean isDisplayed = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislationpopup");
			isDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isRelatedDocumentPopUpDisplayed <br>" + displayErrorMessage(exc));
		}
		return isDisplayed;
	}

	public boolean isEqualsRelatedDocumentPopUpTitleAbbrev(String expectedAbbrev) {
		boolean isDisplayed = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislationpopuptitleabbrev");
			isDisplayed = elementhandler.getElement(locator).getText().equals(expectedAbbrev);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isEqualsRelatedDocumentPopUpTitleAbbrev <br>" + displayErrorMessage(exc));
		}
		return isDisplayed;
	}

	public boolean closeRelatedDocumentPopUp() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislationpopupclose");
			elementhandler.clickElement(locator);
			flag = true;
			Thread.sleep(500);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : closeRelatedDocumentPopUp <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isRelatedLegislationToolTipPresent() {
		boolean isDisplayed = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislationrightpanel");
			WebElement element = elementhandler.getElement(locator);
			isDisplayed = element.getAttribute("title")
					.equals("Consulta de documentos relacionados con este ordenamiento");
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isRelatedLegislationToolTipPresent <br>" + displayErrorMessage(exc));
		}
		return isDisplayed;
	}

	public boolean isRelatedLegislationOptionDisplayed() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislation");
			WebElement webElement = elementhandler.getElement(locator);
			return webElement.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLatestReformsDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method click On Related Legislation option in document display page
	 */
	public void clickOnRelatedLegislation() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislation");
			WebElement webElement = elementhandler.getElement(locator);
			webElement.click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRelatedLegislation <br>" + displayErrorMessage(exc));
		}

	}

	public boolean tooltipmsgofRelatedLegislation() {
		try {
			Thread.sleep(7000);
			WebElement tooltip = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislation"));
			String tooltiptext = tooltip.getAttribute("title");
			String text = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".relatedlegislationtolltiptext");
			if (tooltiptext.equals(text))
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in :  tooltipmsgofRelatedLegislation <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean tooltipmsgofLatestReforms() {
		try {
			WebElement tooltip = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latest_reforms"));
			String tooltiptext = tooltip.getAttribute("title");
			String text = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".latestreformstolltiptext");
			if (tooltiptext.equals(text))
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in :  tooltipmsgofLatestReforms <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	// verifying 'table of Contents' is not displayed
	public boolean isTableOfContentsNotVisible() {
		boolean tableOfContentsLinkNotFound = false;
		try {
			String locator = "xpath=.//*[text()='Árbol de Contenidos']";
			tableOfContentsLinkNotFound = !(WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator)));
		} catch (Exception ex) {
			// Expected - negative test
			tableOfContentsLinkNotFound = true;
		}
		return tableOfContentsLinkNotFound;
	}

	// verify title for selected form // MAFAUTO-246
	public boolean validateFormTitle(String title) {
		boolean flag = false;
		try {
			String leftpane = elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".leftpanetitle"))
					.getText().replaceAll("\n", " ");
			////System.out.println(leftpane);
			////System.out.println(title);
			if (leftpane.toLowerCase().contains(title)) {
				flag = true;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyNIFUpdateInResultList <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	public boolean validateUltimateReformWithoutLink(String withoutlinkone, int position) {
		boolean flag = false;
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linkavailableunderlatestreforms")), 40);
			List<WebElement> items = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linkavailableunderlatestreforms"));

			List<WebElement> spanInFirstItem = items.get(position).findElements(By.tagName("span"));
			List<WebElement> linkInFirstItem = items.get(position).findElements(By.tagName("a"));

			flag = (spanInFirstItem.size() == 1) && (linkInFirstItem.size() == 0)
					&& spanInFirstItem.get(0).getText().trim().equals(withoutlinkone);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : validateUltimateReformWithoutLink <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	public boolean validateUltimateReformWithLink(String withlinkone, int position) {
		boolean flag = false;
		try {
			List<WebElement> items = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linkavailableunderlatestreforms"));

			List<WebElement> spanInFirstItem = items.get(position).findElements(By.tagName("span"));
			List<WebElement> linkInFirstItem = items.get(position).findElements(By.tagName("a"));

			flag = (spanInFirstItem.size() == 0) && (linkInFirstItem.size() == 1)
					&& linkInFirstItem.get(0).getText().trim().equals(withlinkone);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : validateUltimateReformWithLink <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	public void clickonReformLink(String linkname) {
		try {
			List<WebElement> links = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linkavailableunderlatestreforms"));
			String actualLink = "";
			for (int row = 0; row < links.size(); row++) {
				actualLink = links.get(row).getText();
				if (actualLink != null && actualLink.equals(linkname)) {

					links.get(row).findElement(By.tagName("a")).click();
					break;
				}
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyNIFUpdateInResultList <br>" + displayErrorMessage(ex));
		}
	}

	public boolean validateColor() {
		boolean flag = false;
		try {
			WebElement colorlink = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".colorofheaderarrow"));
			String colorvalue = colorlink.getAttribute("class");
			if (colorvalue != "red")
				flag = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyNIFUpdateInResultList <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	public boolean isInformationClaveTabDisplayed() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".informationclave");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isInformationClaveTabDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isTreeOfContentTabDisplayed() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".treeofccontent");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isTreeOfContentTabDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isVocesDisplayedForFeaturedArticle() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".featuredarticles_voices");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isVocesDisplayedForFeaturedArticle <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isVersionsDisplayedForCorrelation(String marginalid, String articlenum) {
		try {
			Thread.sleep(2000);
			/*String newmarginalid = marginalid.replaceAll("\\\\", "-");
			String locator = "xpath=.//*[@id='relationshipInContent" + newmarginalid + "-A." + articlenum + "'"
					+ "]//a[text()='Versiones']";*/
			
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".clickversionslink");

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					elementhandler.findElement(locator));
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			return false;
		}
	}

	public void clickVersionsDisplayedForCorrelation(String marginalid, String articlenum) {
		try {
			/*String newmarginalid = marginalid.replaceAll("\\\\", "-");
			String locator = "xpath=.//*[@id='relationshipInContent" + newmarginalid + "-A." + articlenum + "'"
					+ "]//a[text()='Versiones']";

			elementhandler.clickElement(locator);*/
			
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".clickversionslink");

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					elementhandler.findElement(locator));
			elementhandler.findElement(locator).click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickVersionsDisplayedForCorrelation <br>" + displayErrorMessage(exc));
		}
	}

	public void clickAllVersions() {
		boolean flag = false;
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".allversionslink");
			List<WebElement> alllinks = elementhandler.findElements(locator);

			for (int i = 0; i < alllinks.size(); i++) {
				flag = alllinks.get(i).isSelected();
				if (!flag) {
					alllinks.get(i).click();
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickAllVersions <br>" + displayErrorMessage(exc));
		}
	}

	public void clickCompareAllVersion() {
		try {
			Thread.sleep(3000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".compareversionlink"));
			Thread.sleep(3000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCompareAllVersion <br>" + displayErrorMessage(exc));
		}
	}

	public void closePopUp() {
		try {
			elementhandler
					.clickElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".closepopup"));
			Thread.sleep(1000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : closePopUp <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isMainTextDisplayedForFeaturedArticle() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".featuredarticles_maintext");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isMainTextDisplayedForFeaturedArticle <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isListofDocumentsLinkDispalyed() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".listofdocuments_link");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isListofDocumentsLinkDispalyed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isNewSearchLinkDispalyed() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".newsearch_link");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isNewSearchLinkDispalyed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isDeliveryBarDispalyed() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".deliverybar");
			return elementhandler.findElement(locator).isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDeliveryBarDispalyed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	// This method checks whether Date of Issue field is present or not
	public boolean isDateofIssueFieldAvailable() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".dateofissuefield");
			return elementhandler.findElement(locator).isDisplayed();		
		} catch (Exception ex) {
			//Not required as if Date of Issue is not available then Date of Sanction would be
			//extentLogger.log(LogStatus.INFO, "Error in : isDateofIssueFieldAvailable <br>" + displayErrorMessage(ex));
		}
		return false;
	}
	
	// This method checks whether Date of Sanction field is present or not
		public boolean isDateofSanctionIsFieldAvailable() {
			try {
				String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".dateofsanctionfield");
				return elementhandler.findElement(locator).isDisplayed();
	
			} catch (Exception ex) {
				extentLogger.log(LogStatus.INFO, "Error in : isDateofSanctionIsFieldAvailable <br>" + displayErrorMessage(ex));
			}
            return false;
		}
		

	public boolean validateDateToBeInGivenRange(String fromDate, String toDate, String locator)
	{
		try {
			
			if(locator.contains("Issue"))
			{
				locator = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".dateofissuefieldtext");
			}
			else
			{
				locator = PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".dateofsanctionfieldtext");
			}
			
			String text = elementhandler.findElement(locator).getText();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        Date givenFromDate = sdf.parse(fromDate);
	        Date givenToDate = sdf.parse(toDate);
			
	        Date dateOnDoc = sdf.parse(text);
	        if(dateOnDoc.compareTo(givenFromDate) >= 0) // dateOnDoc is after givenFromDate
	        {
	        	if(dateOnDoc.compareTo(givenToDate) <0) // dateOnDoc is before the givenEndDate
	        	{
	        		return true;
	        	}
	        }
						
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : validateDateToBeInGivenRange <br>" + displayErrorMessage(ex));
		
		}
		return false;
	}

	/*
	 * This method returns the marginal text displayed on the document display
	 * page Returns String format of the marginal text Returns empty string when
	 * the marginal text is not found on the document
	 */
	public String getMarginalText() {
		try {
			return elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".marginaltext"))
					.getText();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : getMarginalText <br>" + displayErrorMessage(ex));
			return "";

		}

	}

	// This method will read the marginal text from pop up window
	public String getMarginalTextfromPopUp() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".popupmarginaltext"))
					.getText();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : getMarginalText <br>" + displayErrorMessage(ex));
			return "";

		}

	}

	/*
	 * This method takes marginal text and transforms in the format that can be
	 * used for locator Input Marginal text is in this format : LEG\1931\1
	 * Marignal Text used for locators has this format: LEG-1931-1 Returns the
	 * formatted marginal text
	 */
	public String formatMarginalTextForSubmissionAsLocator(String marginalText) {
		try {
			return marginalText.replaceAll("\\\\", "-");

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : formatMarginalTextForSubmissionAsLocator <br>" + displayErrorMessage(ex));
			return "";
		}

	}

	public SearchPage OpenSearchPage() throws Exception {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage")));
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage")), 30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage"));
			Thread.sleep(1000); // Need to remove this once the search
								// string is updated to more specific to
								// return results faster
			return new SearchPage(driver);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : OpenSearchPage <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public HomePage openHomepage() throws Exception {
		try {
			WebElement locator = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".homepagetab"));
			WebDriverFactory.waitForElementUsingWebElement(driver, locator , 30);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",locator);
			locator.click();
			return new HomePage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : openHomepage <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	public boolean clickCorrelationForTitleCenter(String title) {
		try {
			String relationshiplocator = "xpath=//*[text()='" + title + "']"			
							.replace("xpath=", "");

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					elementhandler.findElement(relationshiplocator));
			return true;

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCorrelationForTitle <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void clickCorrelationForTitleInline(String marginalID, String articleNumber) {
		try {
			String relationshiplocator = "xpath=.//*[@id='" + marginalID + "#RL." + articleNumber + "']";

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					elementhandler.findElement(relationshiplocator));
			elementhandler.clickElement(relationshiplocator);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickCorrelationForTitleInline <br>" + displayErrorMessage(ex));
		}
	}

	public void clickCorrelationForArticleNo(String marginalID, String articleNumber) {
		try {
			Thread.sleep(1000);
			String relationshiplocator = "xpath=.//*[@id='" + marginalID + "#A." + articleNumber + "']";

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					elementhandler.findElement(relationshiplocator));
			elementhandler.clickElement(relationshiplocator);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCorrelationForArticleNo <br>" + displayErrorMessage(ex));
		}
	}

	public void scrollTillArticleNumber(String marginalID, String articleNumber) {
		try {
			String relationshiplocator = "xpath=.//*[@id='" + marginalID + "-A-" + articleNumber + "']/div";

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					elementhandler.findElement(relationshiplocator));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : scrollTillArticleNumber <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isPopUpTitleDisplayed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".popuptitle"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isPopUpTitleDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean isVocesDisplayed() {
		try {
			return elementhandler
					.findElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".correspondingvoces"))
					.isDisplayed();
		} catch (Exception ex) {

			extentLogger.log(LogStatus.INFO, "Error in : isVocesDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}
	
	
	public boolean isVocesDisplayedSearchPage() {
		try {
			return elementhandler
					.findElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".voicesvalue"))
					.isDisplayed();
		} catch (Exception ex) {

			extentLogger.log(LogStatus.INFO, "Error in : isVocesDisplayedSearchPage <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean iscontentDispalyed(String text) {
		boolean flag = false;
		try {
			String acttext = elementhandler
					.findElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".correspondingtext"))
					.getText();
			if (acttext.toLowerCase().contains(text.toLowerCase()))
				flag = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : iscontentDispalyed <br>" + displayErrorMessage(ex));
			flag = false;
			;
		}
		return flag;
	}
	
	public boolean isVerifyTextResultsDocument(String text) {
		
		boolean flag = false;
		try {
			String acttext = elementhandler
					.findElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".naturaldocumentresult"))
					.getText();
			if (acttext.toLowerCase().contains(text.toLowerCase()))
				flag = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isVerifyTextResultsDocument <br>" + displayErrorMessage(ex));
			flag = false;
			
		}
		return flag;
	}

	public boolean isWithoutEffectOptionDisplayed() {

		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".withouteffect");
			WebElement webElement = elementhandler.getElement(locator);
			WebDriverFactory.waitForElementUsingWebElement(driver, webElement, 30);
			return webElement.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isWithoutEffectOptionDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean tooltipmsgofWithoutEffect(String tolltipmsg) {
		try {
			WebElement tooltip = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".withouteffect"));
			String tooltiptext = tooltip.getAttribute("title");

			if (tooltiptext.equals(tolltipmsg))
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in :  tooltipmsgofWithoutEffect <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void clickWithoutEffect() {

		try {
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".withouteffect"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickWithoutEffect <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isTextdisplayforWithoutEffect() {
		boolean flag = false;
		try {
			Thread.sleep(8000);
			String text = elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".withouteffectleftpanelmsg"));
			if (text != null)
				flag = true;

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isTextdisplayforWithoutEffect <br>" + displayErrorMessage(exc));
			return false;
		}

		return flag;
	}

	public boolean isAdicionesTabDisplayed() {
		try {
			Thread.sleep(2000);
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".correspondingadicionestab"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isAdicionesTabDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean isPopUpforVersionsDisplayed() {
		try {
			Thread.sleep(1000);
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".correlationpopupwindow"))
					.isDisplayed();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isPopUpforVersionsDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void clickonPopUpComOverTip() {
		try {
			Thread.sleep(1000);
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.findElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".popuptip")),
					30);
			elementhandler
					.clickElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".popuptip"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickonPopUpComOverTip <br>" + displayErrorMessage(ex));
		}
	}

	public void verifyAddedText() {
		try {
			clickonPopUpComOverTip();
			String value = elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".addedtext"))
					.getCssValue("border-bottom");
			//System.out.println(value);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyAddedText <br>" + displayErrorMessage(ex));
		}
	}

	public void clickAdicionesTabDisplayed() {
		try {

			Thread.sleep(2000);
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".correspondingadicionestab")),
					60);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".correspondingadicionestab"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickAdicionesTabDisplayed <br>" + displayErrorMessage(ex));

		}
	}

	public boolean isLinkofAdicionesTabDisplayed(String text) {
		boolean flag = false;
		try {
			Thread.sleep(2000);
			WebElement element =elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".correspondingadicionestext"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			
			String acttext = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".correspondingadicionestext"))
					.getText();
			if (acttext.toLowerCase().contains(text.toLowerCase()))
				flag = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isLinkofAdicionesTabDisplayed <br>" + displayErrorMessage(ex));
			flag = false;
			;
		}
		return flag;
	}

	public void clickAdditionLinkOfAdiciones() {

		try {
			WebElement locator = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".correspondingadicionestext"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", locator);
			WebDriverFactory.waitForElementUsingWebElement(driver, locator, 30);
			locator.click();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickAdditionLinkOfAdiciones <br>" + displayErrorMessage(ex));

		}
	}

	public boolean isadicionesPopDisplay() 
	{
		WebElement locator = elementhandler.findElement(PropertiesRepository
				.getString("com.trgr.maf." + productUnderTest + ".correspondingadicionespop"));
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, locator, 30);
			return locator.isDisplayed();
		} catch (Exception ex) {
			//If the pop up is not displayed but the link is displayed, try clicking on the link again to ensure click is not missed.
			clickAdditionLinkOfAdiciones();
			if(locator.isDisplayed())
			{
				return true;
			}
			else
			{
				extentLogger.log(LogStatus.INFO, "Error in : isadicionesPopDisplay <br>" + displayErrorMessage(ex));
				return false;
			}
		
		}
	}

	// Clickin on Start Footnote
	public void clickFirstStarFootnote() {
		try {
			List<WebElement> footnote = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstfootnote"));
			for (int i = 0; i < footnote.size(); i++)
				if (WebDriverFactory.isDisplayed(driver, footnote.get(i))) {
					if (footnote.get(i).getText().contains("*")) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", footnote.get(i));
						footnote.get(i).click();
						break;
					}
				}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFirstStarFootnote <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isPresentExpectedDocument(String actvalue) {
		boolean flag =false;;
		try {
			String docContent = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".verifysearchtitle"))
					.getText();
		if(docContent.contains(actvalue))
		{
			flag=true;
	   }
	
		} 
	catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isPresentExpectedDocument <br>" + displayErrorMessage(exc));
			flag=false;
		}
		return flag;
	}


	public void clickOnSecondStarFootnote() {
		try {
			List<WebElement> footnote = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".secondonenote"));
			for (int i = 0; i < footnote.size(); i++)
				if (WebDriverFactory.isDisplayed(driver, footnote.get(i))) {
					if (footnote.get(i).getText().contains("*")) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", footnote.get(i));
						footnote.get(i).click();
						break;
					}
				}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFirstStarFootnote <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isSecondStarFootnoteVisible() {
		boolean itemDisplayed = false;
		try {
			List<WebElement> footnote = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".secondonenote"));
			for (int i = 0; i < footnote.size(); i++)
				if (WebDriverFactory.isDisplayed(driver, footnote.get(i))) {
					if (footnote.get(i).getText().contains("*")) {
						itemDisplayed = true;
						break;
					}
				}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSecondStarFootnoteVisible <br>" + displayErrorMessage(exc));
			itemDisplayed = false;
		}
		return itemDisplayed;
	}

	public boolean isFirstStarFootnoteVisible() {
		boolean itemDisplayed = false;
		try {
			List<WebElement> footnote = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstfootnote"));
			for (int i = 0; i < footnote.size(); i++)
				if (WebDriverFactory.isDisplayed(driver, footnote.get(i))) {
					if (footnote.get(i).getText().contains("*")) {
						itemDisplayed = true;
						break;
					}
				}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFirstStarFootnoteVisible <br>" + displayErrorMessage(exc));
			itemDisplayed = false;
		}
		return itemDisplayed;
	}
	// clicking on 1 Footenote

	public void clickFirstOneFootnote() {
		try {
			List<WebElement> footnote = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstfootnote"));
			for (int i = 0; i < footnote.size(); i++)
				if (WebDriverFactory.isDisplayed(driver, footnote.get(i))) {
					if (footnote.get(i).getText().contains("1")) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", footnote.get(i));
						footnote.get(i).click();
						break;
					}
				}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFirstOneFootnote <br>" + displayErrorMessage(exc));
		}
	}

	public void clickOnSecondOneFootnote() {
		try {
			List<WebElement> footnote = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".secondonenote"));
			for (int i = 0; i < footnote.size(); i++)
				if (WebDriverFactory.isDisplayed(driver, footnote.get(i))) {
					if (footnote.get(i).getText().contains("1")) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", footnote.get(i));
						footnote.get(i).click();
						break;
					}
				}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSecondOneFootnote <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isSecondOneFootnoteVisible() {
		boolean itemDisplayed = false;
		try {
			List<WebElement> footnote = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".secondonenote"));
			for (int i = 0; i < footnote.size(); i++)
				if (WebDriverFactory.isDisplayed(driver, footnote.get(i))) {
					if (footnote.get(i).getText().contains("1")) {
						itemDisplayed = true;
						break;
					}
				}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSecondOneFootnoteVisible <br>" + displayErrorMessage(exc));
			itemDisplayed = false;
		}
		return itemDisplayed;
	}

	public boolean isFirstOneFootnoteVisible() {
		boolean itemDisplayed = false;
		try {
			List<WebElement> footnote = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstfootnote"));
			for (int i = 0; i < footnote.size(); i++)
				if (WebDriverFactory.isDisplayed(driver, footnote.get(i))) {
					if (footnote.get(i).getText().contains("1")) {
						itemDisplayed = true;
						break;
					}
				}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFirstOneFootnoteVisible <br>" + displayErrorMessage(exc));
			itemDisplayed = false;
		}
		return itemDisplayed;
	}

	public boolean isContentDisplayed(String header) {
		boolean flag = false;
		try {

			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".contentheader");
			String text = elementhandler.findElement(locator).getText();
			if (text.contains(header)) {
				flag = true;
				Thread.sleep(2000);
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isContentDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void clickSubNode(String linkname) {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".allsubnodelinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);

			for (int i = 0; i < alllinks.size(); i++) {
				String link = alllinks.get(i).getText();
				if (link.contains(linkname)) {
					alllinks.get(i).click();
					Thread.sleep(2000);
					break;
				}

			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSubNode <br>" + displayErrorMessage(exc));
		}
	}

	public boolean isLinkPresent(String linkname) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".allleftsidelinks");
			List<WebElement> links = elementhandler.findElements(locator);

			for (int i = 0; i < links.size(); i++) {
				String link = links.get(i).getText();
				if (link.contains(linkname)) {
					flag = true;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLinkPresent <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void clickLeftPaneLink(String linkname) {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".allleftsidelinks");
			List<WebElement> links = elementhandler.findElements(locator);

			for (int i = 0; i < links.size(); i++) {
				String link = links.get(i).getText();
				if (link.contains(linkname)) {
					links.get(i).click();
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickLeftPaneLink <br>" + displayErrorMessage(exc));
		}
	}

	public DeliveryPage clickOnEmailOption() throws IllegalArgumentException, IOException {
		try {
			Thread.sleep(2500);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".emaillink"));
			return new DeliveryPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : emailClicked <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	public DeliveryPage clickPrint() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".printlink")),
					30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".printlink"));
			return new DeliveryPage(driver);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPrint <br>" + displayErrorMessage(e));
			return null;
		}
	}

	/*
	 * This method will take the number of ordered list to verify on the
	 * document returns true / false based on the ordered list display
	 */
	public boolean isExpectedOrderedListDisplayedOnDoc(int numOrderedList, String textOnDoc) {
		try {
					
			List<WebElement> lstOfItems  = driver.findElement(By.xpath("//span[contains(text(),'"+textOnDoc +"')]")).findElement(By.xpath("//*[starts-with(@class,'ulItemizedList')]")).findElements(By.cssSelector("li"));

			return lstOfItems.size() == numOrderedList;

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isExpectedOrderedListDisplayedOnDoc <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public MyDocumentPage openMyDocumentsPage() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentlink")),
					30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentlink"));
			Thread.sleep(1000);
			return new MyDocumentPage(driver);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : openMyDocumentsPage <br>" + displayErrorMessage(e));
			return null;
		}
	}

	/*
	 * This method verifies the visibility of addto favourite error popup
	 * returns true on success
	 */
	public boolean isAddtoFavouriteErrorPopUpPresent() throws Exception {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".favouritepopuptext")));
		} catch (Exception e) {
			// This is expected in success scenario
			// extentLogger.log(LogStatus.INFO, "Error in :
			// isAddtoFavouriteErrorPopUpPresent <br>" +
			// displayErrorMessage(e));
			return false;
		}
	}

	/*
	 * This method returns the error message in add to favourite error popup
	 * returns null on failure
	 */
	public String getErrorMessageFromAddtoFavouriteErrorPopUp() throws Exception {
		try {
			return elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".favouritepopuptext"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getErrorMessageFromAddtoFavouriteErrorPopUp <br>" + displayErrorMessage(e));
			return null;
		}
	}

	/*
	 * This method clicks on OK button in add to favourite error pop up
	 */
	public void clickOKinAddtoFavouriteErrorPopUp() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".favouritepopupokbutton"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickOKinAddtoFavouriteErrorPopUp <br>" + displayErrorMessage(e));
		}
	}


	/*
	 * Checks the display of search with in search field
	 * returns true on success 
	 */
	public boolean isSearchWithinOptionDisplayed() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchwithinfield")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchWithinOptionDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	/*
	 * This method enters the given search within term on the search with in text box
	 */
	public void enterSearchWithinTerm(String searchwithintext) {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchwithinfield"))
					.sendKeys(searchwithintext);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterSearchWithinTerm <br>" + displayErrorMessage(ex));
		}
	}
	
	/*
	 * Clicks on search button displayed next to search within text box
	 */
	public void clickOnSearchWithInSearch() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchwithinsearchbtn"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSearchWithInSearch <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method clicks on the New search link on the document display page. 
	 * This option/ link is displayed beside the List of documents link on the document display page
	 */
	public HomePage clickOnNewSearchLink()
	{
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clickonnewsearchlink"));
			return new HomePage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnNewSearchLink <br>" + displayErrorMessage(ex));
			return null;
		}
	
	}
	
	//Validating the results for the search term
	//this function applicable for home page and search page
	//<Created Date :  26-Oct-2018 >   ; <author : Saikiran>
	
	public void clickOnListOfTheDocumentsInLeftPanel()
	{
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".listofdocuments_link"));
			
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnListOdTheDocuments <br>" + displayErrorMessage(ex));
			
		}
	
	}
	
	
	//Validating the Ver Coincidencias Link in Left panel in documnet display page
	//this function applicable for home page and search page
	//<Created Date :  26-Oct-2018 >   ; <author : Saikiran>
	
	public boolean isVerCoincidenciasLinkDisplayed() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".VerCoincidenciasLink")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isVerCoincidenciasLinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

  }
	
	
	
	
}