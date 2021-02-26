package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class SearchResultsPage extends BasePage {

	boolean flag;
	DocumentDisplayPage documentPage;
	public SearchResultsPage(WebDriver driver) throws IOException, IllegalArgumentException, InterruptedException {
		super(driver);
		/*
		 * WebDriverFactory.waitForElementUsingWebElement(driver,
		 * elementhandler.getElement(
		 * PropertiesRepository.getString("com.trgr.maf." +
		 * BaseTest.productUnderTest + ".resultsList")), 20);
		 */
		Thread.sleep(500);
	}

	public boolean searchResultsDisplayed() {

		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".container")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public boolean searchResultsHeaderContainerDisplayed() {
		try { 
			Thread.sleep(9000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".result_list_header_title")), 20);
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".result_list_header_title")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsHeaderContainerDisplayed <br>" + displayErrorMessage(ex));
			
			return false;
		}

	}
	
	
	
	/*
	 * This method clicks on the New Search link from the search results page / Search page
	 * Returns HomePage - Clicking on New Search returns handle to Home Page for certain use cases
	 * 
	 */
	public HomePage ClickOnNewSearchLink() throws Exception {
		try {
			String locator=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newsearchlink");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(6000);
			return new HomePage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnNewSearchLink <br>" + displayErrorMessage(ex));
			return null;
		}
	}
	
	/*
	 * This method clicks on the New Search link from the search results page / Search page
	 * Returns SearchPage - Clicking on New Search returns handle to Home Page for certain use cases
	 * 
	 */	
	public SearchPage ClickNewSearchLink() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newsearchlink"));
			return new SearchPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnNewSearchLink <br>" + displayErrorMessage(ex));
			return null;
		}
	}
	
	public boolean isErrorMessageDisplayedHomePage() {
		 try {
      	  String locator = "//*[contains(text(),'Debe completar al menos un campo de búsqueda.')]";
					
      	WebElement ele=  driver.findElement(By.xpath(locator));
     boolean flag= 	ele.isDisplayed();
     
      	  
				if(!flag) {
					String locator2 = PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".searchbtnonsearchpage");
				
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementhandler.getElement(locator2));
					
					WebElement element=  driver.findElement(By.xpath(locator));
				        flag= 	element.isDisplayed();
				}
					return flag;
					
				} catch (Exception exc) {
					extentLogger.log(LogStatus.INFO, "Error in : isMsgDisplayed <br>" + displayErrorMessage(exc));
					return false;
				}
	}

	//this method clicks on the searches
	public SearchPage ClickOnSearches() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchestab"));
			return new SearchPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnSearches <br>" + displayErrorMessage(ex));
			return null;
		}
	}
	public HomePage ClickOnModifySearchLink() throws Exception {
		try {
			String locator=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".modifysearchlink");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(6000);
								
			return new HomePage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnModifySearchLink <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	public SearchPage ClickModifySearchLink() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".modifysearchlink"));
			return new SearchPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnModifySearchLink <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	public boolean searchReturnedResultsAsExpected(String searchText) {
		boolean flag = false;
		try {

			if (searchText.contains("-")) {
				searchText = searchText.split("-")[0].toLowerCase();
			}
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchtext");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 20);
			String text = elementhandler.getElement(locator).getText().toLowerCase();
			String exptext=searchText.toLowerCase();
			if (text.contains(exptext)) {
				flag = true;
			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : searchReturnedResultsAsExpected <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	/*
	 * verifies if given highlighted text variable is highlighted on the page
	 */
	public boolean isSearchStringHighlighted(String highlightedText) {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".highlighttext"))
					.getText().toUpperCase().contains(highlightedText.toUpperCase())
					| highlightedText.toUpperCase()
							.contains(elementhandler
									.getElement(PropertiesRepository
											.getString("com.trgr.maf." + BaseTest.productUnderTest + ".highlighttext"))
									.getText().toUpperCase());
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchStringHighlighted <br>" + displayErrorMessage(ex));
			return false;
		}

	}

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
	 * This method enters the given search within term on the search results page
	 * If the element is not present, catch block will handle the exception and log the screen shot on the extent report
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

	public void clickOnSearchWithInSearch() {
		try {
			
			WebElement searchBtnLocator = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchwithinsearchbtn"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",searchBtnLocator);
			searchBtnLocator.click();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSearchWithInSearch <br>" + displayErrorMessage(ex));
		}
	}

	public Boolean searchWithInResultsDisplayed(String searchwithintext) {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchtext"))
					.getText().contains(searchwithintext);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchWithInResultsDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public String getDocCountFromResultSet() {

		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".totalreturneddocs"))
					.getText();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : getDocCountFromResultSet <br>" + displayErrorMessage(ex));
			return "document count is not found";
		}

	}

	public Boolean isFacetingDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".facetinglegislation"))
					.isDisplayed() &&

					elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".facetingdoctrina"))
							.isDisplayed();
		} catch (Exception ex) {
			// extentLogger.log(LogStatus.INFO, "Error in : isFacetingDisplayed
			// <br>"+displayErrorMessage(ex));
			return false;
		}
	}

	public boolean resultSetDisplayBasedOnContentSet(String contenttype) {
		try {
			List<WebElement> list = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contenttypesonresultset"));

			for (WebElement webElement : list) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",webElement);
				if (WebDriverFactory.isDisplayed(driver, webElement)) {
					if (webElement.getText().contains(contenttype)) {
						return true;
					}
				}
			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : resultSetDisplayBasedOnContentSet <br>" + displayErrorMessage(ex));
			return false;
		}
		return false;
	}

	public boolean isFilterFacetHasOnlyGivenContentType(String contentType) {
		try {
			WebElement element = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".availablefacets"));
			List<WebElement> availableFacetlinks = element.findElements(By.tagName("li"));
			if (availableFacetlinks.size() == 1) {
				return availableFacetlinks.get(0).getText() == contentType
						| availableFacetlinks.get(0).getText().contains(contentType);
			}
			return false;

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFilterFacetHasOnlyGivenContentType <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public Boolean isDocNavigationDisplayed() {
		try {
			Boolean navigationBarDisplayed = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docnavigation")));
			if (navigationBarDisplayed) {
				WebElement element = elementhandler.findElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".docnavigation"));
				List<WebElement> availableFacetlinks = element.findElements(By.xpath("//a[@class='navPages']"));
				if (availableFacetlinks.size() > 0)
					navigationBarDisplayed = true;
				Thread.sleep(3000);
				navigationBarDisplayed = navigationBarDisplayed
						&& WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".currentpage")));
				navigationBarDisplayed = navigationBarDisplayed
						&& WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".nextpage")));
				Thread.sleep(3000);
				navigationBarDisplayed = navigationBarDisplayed
						&& WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastpage")));
			}
			return navigationBarDisplayed;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocNavigationDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public boolean DocumentInfoOnResultSetHasSearchText(String searchText) {
		try {
			Boolean isResultListHasSearchText = false;
			WebElement element = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultsetinfo"));
			List<WebElement> documentlinks = element.findElements(By.xpath("//div[@class='result']"));
			for (WebElement webElement : documentlinks) {
				isResultListHasSearchText = webElement.findElement(By.tagName("td")).findElement(By.xpath("p"))
						.getText().contains(searchText);

			}

			return isResultListHasSearchText;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : DocumentInfoOnResultSetHasSearchText <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public boolean isExpectedCountOfDocumentsReturned() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultdocs"))
					.getText().contains("1 resultado");
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isExpectedCountOfDocumentsReturned <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	/*
	 * This method clicks on first document in search results, and returns
	 * document display page
	 */
	public DocumentDisplayPage getFirstDocument() {

		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlistdocument"));
			Thread.sleep(2000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
			return new DocumentDisplayPage(driver);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : getFirstDocument <br>" + displayErrorMessage(e));
			return null;
		}


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

	public DocumentDisplayPage clickFirstLink() throws Exception {
		// Thread.sleep(8000);
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstdocfromresultset"));
			return new DocumentDisplayPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFirstLink <br>" + displayErrorMessage(exc));
			return null;
		}

	}
	
	public DocumentDisplayPage isPresentResultList() throws Exception {
		
		try {
			elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultsdocumentdispaly"));
			return new DocumentDisplayPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFirstLink <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public boolean isFacetwidgetDisplayed() {

		try {
			Thread.sleep(1000);
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fuentewidget"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isFacetwidgetDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public boolean validateFuentoFacetitems() {
		try {
			flag = false;
			String[] expected = { "Consultor práctico", "Doctrina", "Jurisprudencia - Sumario", "Legislación",
					"Legislación Modificatoria", "Dictámenes y Opiniones del Fisco",
					"Jurisprudencia - Texto Completo" };
			List<String> expitems = new ArrayList<String>();
			for (String s : expected)
				expitems.add(s);
			List<WebElement> FuentoFacetlist = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fuentewidgetlist"));
			if (expitems.size() == FuentoFacetlist.size()) {
				for (int i = 0; i < expected.length; i++) {
					String actualitem = FuentoFacetlist.get(i).getText();
					String expitem = expitems.get(i).toString();
					if (expitem.equalsIgnoreCase(actualitem))
						flag = true;
				}
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : validateFuentoFacetitems <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	public boolean isCategoryResultDisplayed() {
		try {
			flag = false;
			List<WebElement> categoryresultlist = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".categoryresultlist"));
			for (int i = 0; i < categoryresultlist.size(); i++) {
				if (categoryresultlist.get(i).isDisplayed())
					flag = true;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isCategoryResultDisplayed <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	public boolean isExpectedResultOpened(String ExpectedResult) {
		try {
			flag = false;
			String xpath="//a[@class='documentLink']";
			String newpath="(//div[@class='navigationLinks']//a)[3]";
			WebElement ele= driver.findElement(By.xpath(newpath));
			ele.click();
			
			List<WebElement> categoryresultlist = driver.findElements(By.xpath(xpath));			
			for (int i = 0; i < categoryresultlist.size(); i++)
			{
				String data =categoryresultlist.get(i).getText();
				if (data.equals(ExpectedResult)) 
				{
					flag = true;
					break;
				}
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isExpectedResultOpened <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}
	public boolean clickOnAnyItem() {
		boolean flag = false;
		try {
			List<WebElement> FuentoFacetlist = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fuentewidgetlist"));
			//List<WebElement> categoryresultlist = elementhandler.findElements(PropertiesRepository
				//	.getString("com.trgr.maf." + BaseTest.productUnderTest + ".categoryresultlist"));
			for (int i = 0; i < FuentoFacetlist.size(); i++) {
				String expectedcategory = FuentoFacetlist.get(i).getText();
				FuentoFacetlist.get(i).click();
				String actualcategory = elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".categoryname"))
						.getText();
				//System.out.println(actualcategory);
				if (expectedcategory.equalsIgnoreCase(actualcategory))
					flag = true;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnAnyItem <br>" + displayErrorMessage(ex));
			return false;
		}
		return flag;
	}

	public boolean isSearchResultWidgetDisplayed() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchresultwidget")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchResultWidgetDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean verifySearchTermUpdated(String search_term, String freeword) {
		boolean flag = false;
		try {
			elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchkey"))
					.sendKeys(search_term);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbutton"));
			String actualsearchterm = elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchterm"))
					.getText();
			if (actualsearchterm.contains(freeword) && actualsearchterm.contains(search_term)) {
				flag = true;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifySearchTermUpdated <br>" + displayErrorMessage(ex));
			return false;
		}
		return flag;
	}

	public void clickCheckBox() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkbox"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCheckBox <br>" + displayErrorMessage(ex));
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

	// verifying Document View Link is displayed
	public boolean isDocumentViewLinkDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".DocumentViewLink"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocumentViewLinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}
	
	
		
	
		
	// verifying 'table of Contents' link is not displayed
	public boolean isTableOfContentsLinkNotVisible() {
		boolean tableOfContentsLinkNotFound = false;
		try {
			String locator = "xpath=.//*[text()='Árbol de Contenidos']";
			tableOfContentsLinkNotFound = !(WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator)));
		} catch (Exception ex) {
			//Expected - negative test
			tableOfContentsLinkNotFound = true;
		}
		return tableOfContentsLinkNotFound;
	}
	
	// verifying 'Forms' title is displayed 
	public boolean isFormstitleDisplayed() {
		boolean formsfound = false;
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".globalresulttitlelabel");
			formsfound = elementhandler.getElement(locator).getText().contains("Formularios");
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isFormstitleDisplayed <br>" + displayErrorMessage(ex));
		}
		return formsfound;
	}

	public ContenttreeOnsearchResultPage ClickDocumentViewLink() {
		try {
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".DocumentViewLink"))
					.click();
			return new ContenttreeOnsearchResultPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickDocumentViewLink <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	/*
	 * This method checks if 'Total Documents Count' Label is available in
	 * Search Results Widget returns boolean
	 */
	public boolean isTotalDocumentLabelPresent() {

		boolean flag = false;

		try {

			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".totalreturneddocs_label");
			flag = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator));
			if (flag) {
				String actualLabel = elementhandler.getText(locator);
				flag = actualLabel.contains("Total de documentos");
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isTotalDocumentLabelPresent <br>" + displayErrorMessage(ex));
			return false;
		}

		return flag;
	}

	/*
	 * This method checks if 'Total Documents Count' number is available in
	 * Search Results Widget returns boolean
	 */
	public boolean isTotalDocumentCountPresent() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".totalreturneddocs")));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isTotalDocumentCountPresent <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	/*
	 * This method checks if 'Search Terms' Label is available in Search Results
	 * Widget returns boolean
	 */
	public boolean isSearchTermsLabelPresentInWidget() {

		boolean flag = false;
		try {

			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchtext_labelinwidget");
			flag = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator));
			if (flag) {
				String actualLabel = elementhandler.getText(locator);
				flag = actualLabel.contains("Términos buscados") || actualLabel.contains("Termos pesquisados");
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSearchTermsLabelPresentInWidget <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks if 'Search Term' keyword is available in Search
	 * Results Widget returns boolean
	 */
	public boolean isSearchTermPresentInWidget() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchtextinwidget")));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchTermPresentInWidget <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method checks if 'Search Term' keyword is available in Search
	 * Results Widget returns boolean
	 */
	public boolean isSearchTermPresentInWidget(String searchTerm) {
		try {
			String locator =PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchtextinwidget");
			String actualText = elementhandler.getText(locator);
			return actualText.toUpperCase().trim().contains(searchTerm.trim().toUpperCase());
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchTermPresentInWidget <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	
	/*
	 * This method checks if all expected hyper links are available in Search
	 * Results Widget returns boolean
	 */
	public boolean isHyperLinksPresentInSearchResultWidget() {

		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".links_in_search_results_widget");
			List<WebElement> linksInWidget = elementhandler.findElements(locator);
			String actualText = null;
			flag = false;
			for (int i = 0; i < linksInWidget.size(); i++) {
				actualText = linksInWidget.get(i).getText();
				flag = actualText.contains("Reformular la búsqueda") || actualText.contains("Nueva Búsqueda")
						|| actualText.contains("Guardar y Programar la Búsqueda")
						|| actualText.contains("Reformular sua pesquisa") || actualText.contains("Nova Pesquisa")
						|| actualText.contains("Salvar e programar sua pesquisa")
						|| actualText.contains("Modificar los datos de la búsqueda")
						|| actualText.contains("Cargar datos para una nueva búsqueda")
						|| actualText.contains("Configurar una alerta para esta búsqueda");
				if (!flag)
					break;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isHyperLinksPresentInSearchResultWidget <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	/*
	 * This method checks if all 'search' button is available in Search Results
	 * Widget returns boolean
	 */
	public boolean isSearchButtonDisplayedInWidget() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".searchbutton_in_search_results_widget")));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSearchButtonDisplayedInWidget <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void clickOnRemoveFilter() {
		try {
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
//					elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".removefilter")));
			WebElement ele = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".removefilter"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", ele);
			ele.click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRemoveFilter <br>" + displayErrorMessage(exc));
		}

	}

	public boolean isFilterRemoved(String searchText) {
		try {
			elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".removefilter1"))
					.isDisplayed();

			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchterm"))
					.getText().contains(searchText);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFilterRemoved <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	/*
	 * This method clicks on 'Legislación' in the source widget
	 */
	public boolean clickLegislationInSourceWidget() {
		boolean flag = false;
		if (isItemPresentInSourceWidget("Legislación"))
			flag = clickItemInSourceWidget("Legislación");
		else {
			//System.out.println("Unable to find Legislation in source widget");
			flag = false;
		}
		return flag;
	}

	/*
	 * This method clicks on 'Modifying Legislation' in the source widget
	 */
	public boolean clickModifyingLegislationInSourceWidget() {
		boolean flag = false;
		if (isItemPresentInSourceWidget("Legislación Modificatoria"))
			flag = clickItemInSourceWidget("Legislación Modificatoria");
		else {
			//System.out.println("Unable to find 'Modifying Legislation' in source widget");
			flag = false;
		}
		return flag;
	}

	/*
	 * This method clicks on 'Doctrina' in the source widget
	 */
	public boolean clickDoctrinaInSourceWidget() {
		boolean flag = false;
		if (isItemPresentInSourceWidget("Doctrina"))
			flag = clickItemInSourceWidget("Doctrina");
		else {
			//System.out.println("Unable to find Doctrina in source widget");
			flag = false;
		}
		return flag;
	}

	/*
	 * This method clicks on 'Jurisprudencia - Sumario' in the source widget
	 * returns boolean
	 */
	public boolean clickJurisprudenceSummaryInSourceWidget() {
		boolean flag = false;
		if (isItemPresentInSourceWidget("Jurisprudencia - Sumario"))
			flag = clickItemInSourceWidget("Jurisprudencia - Sumario");
		else {
			//System.out.println("Unable to find 'Jurisprudencia - Sumario ' in source widget");
			flag = false;
		}
		return flag;
	}

	/*
	 * This method clicks on 'expitem' in the source widget
	 */
	public boolean clickItemInSourceWidget(String expitem) {

		boolean flag = false;
		try {
			List<WebElement> fuentoFacetlist = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fuentewidgetlist"));

			for (int i = 0; i < fuentoFacetlist.size(); i++)
				if (fuentoFacetlist.get(i).getText().trim().equalsIgnoreCase(expitem)) {
					fuentoFacetlist.get(i).click();
					flag = true;
					break;
				}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickItemInSourceWidget <br>" + displayErrorMessage(exc));
		}
		return flag;
	}

	/*
	 * This method checks if 'expitem' is present in the source widget
	 */
	public boolean isItemPresentInSourceWidget(String expitem) {

		boolean flag = false;
		try {
			List<WebElement> fuentoFacetlist = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fuentewidgetlist"));

			for (int i = 0; i < fuentoFacetlist.size(); i++)
				if (fuentoFacetlist.get(i).getText().trim().equalsIgnoreCase(expitem)) {
					flag = true;
					break;
				}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isItemPresentInSourceWidget <br>" + displayErrorMessage(exc));
		}
		return flag;
	}

	/*
	 * This method checks if all expected second level widgets are displayed for
	 * 'Doctrine' returns boolean
	 */
	public boolean isAllWidgetsExistForDoctrina() {
		boolean flag = false;
		switch (productUnderTest) {
		case "chparg":
			flag = isWidgetExist("Autor", "com.trgr.maf." + productUnderTest + ".second_level_widget_author")
					&& isWidgetExist("Tipo de Doctrina",
							"com.trgr.maf." + productUnderTest + ".widget_type_of_doctrine");
			break;
		case "chppe":
			flag = isWidgetExist("Autor", "com.trgr.maf." + productUnderTest + ".second_level_widget_author")
					&& isWidgetExist("Tipo de Doctrina",
							"com.trgr.maf." + productUnderTest + ".widget_type_of_doctrine");
			break;
		}
		return flag;
	}

	/*
	 * This method checks if all expected second level widgets are displayed for
	 * 'Legislation' returns boolean
	 */
	public boolean isAllWidgetsExistForLegislation() {

		boolean flag = false;

		switch (productUnderTest) {
		case "chparg":

			flag = isWidgetExist("Jurisdicción", "com.trgr.maf." + productUnderTest + ".widget_jurisdiction")
					&& isWidgetExist("Tipo de Norma", "com.trgr.maf." + productUnderTest + ".widget_standard_type")
					&& isWidgetExist("Fechas", "com.trgr.maf." + productUnderTest + ".legislationwidget_dates");
			break;
		case "chppe"://Need new implementation using data input

			flag = isWidgetExist("Tipo de Norma", "com.trgr.maf." + productUnderTest + ".widget_standard_type")
					&& isWidgetExist("Fechas", "com.trgr.maf." + productUnderTest + ".widget_dates");
			break;
		}

		return flag;
	}

	/*
	 * This method checks if all expected second level widgets are displayed for
	 * 'Jurisprudence Summary' returns boolean
	 */
	public boolean isAllWidgetsExistForJurisprudenceSummary() {

		boolean flag = false;

		switch (productUnderTest) {
		case "chparg":

			flag = isWidgetExist("Tribunal", "com.trgr.maf." + productUnderTest + ".widget_tribunal")
					&& isWidgetExist("Fechas", "com.trgr.maf." + productUnderTest + ".widget_dates");
			break;
		}

		return flag;
	}

	/*
	 * Verify the display of any widget in SearchResultsPage. (Accepts widget
	 * name, & element locator as argument). returns boolean
	 */
	public boolean isWidgetExist(String widgetName, String elementLocator) {

		boolean flag = false;
		String text = null;

		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.findElement(PropertiesRepository.getString(elementLocator)));
			text = elementhandler.getText(PropertiesRepository.getString(elementLocator));
			if (text.toString().equals(widgetName.toString()))
				flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isWidgetExist <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	public int getResultListCountPerPage() throws Exception {
		try {
			List<WebElement> result = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofresultlist"));
			int count = result.size();
			return count;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getResultListCountPerPage <br>" + displayErrorMessage(exc));
			return 0;
		}

	}
	
	public DeliveryPage clickOnResultListSave() throws Exception
	{
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlistsave"));
		}catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnResultListSave <br>" + displayErrorMessage(exc));
			return null;
		}
		return new DeliveryPage(driver);
	}

	

	/*
	 * This method Click on Save and Schedule Search in search results page.
	 * Returns SaveAndSchedulePage
	 */
	public SaveAndSchedulePage clickSaveAndScheduleSearch() {
		SaveAndSchedulePage saveAndSchedulePage = null;
		try {
		 String locator=PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".save_and_schedule_search_link");
		 
		 WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(locator), 30);
			elementhandler.clickElement(locator);
		
			saveAndSchedulePage = new SaveAndSchedulePage(driver);
			return saveAndSchedulePage;
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveAndScheduleSearch <br>" + displayErrorMessage(e));
			return null;
		}

		
	}
	
	public boolean isSaveAndScheduleSearchLinkDisplayed() {
		
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".save_and_schedule_search_link_specificpage")));
		
			
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isSaveAndScheduleSearchLinkDisplayed <br>" + displayErrorMessage(e));
			return false;
		}

		
	}

	public void clickOnGivenFacetLink(String linkname) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".allcontenttypelinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);

			for (int i = 0; i < alllinks.size(); i++) {
				String link = alllinks.get(i).getText();
				if (link.toUpperCase().contains(linkname.toUpperCase())) {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",alllinks.get(i));
					alllinks.get(i).click();
					break;
				}
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnGivenFacetLink <br>" + displayErrorMessage(ex));
		}
	}
	
	public void clickOnGivenLinkAdvanceSearch(String linkname) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".givenlinkadvancesearch");
			List<WebElement> alllinks = elementhandler.findElements(locator);

			for (int i = 0; i < alllinks.size(); i++) {
				String link = alllinks.get(i).getText();
				if (link.toUpperCase().contains(linkname.toUpperCase())) {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",alllinks.get(i));
					alllinks.get(i).click();
					break;
				}
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnGivenLinkAdvanceSearch <br>" + displayErrorMessage(ex));
		}
	}
	
	public boolean isGivenFacetLinkDisplayed(String linkname) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".allcontenttypelinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			
			for (int i = 0; i < alllinks.size(); i++) {
				String link = alllinks.get(i).getText();
				if (link.toUpperCase().contains(linkname.toUpperCase())) {
					flag = true;
					break;
				}
			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnGivenFacetLink <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	public void returnBackToResultsPage() {
		try {
			elementhandler.clickElement(PropertiesRepository.getString(
					"com.trgr.maf." + BaseTest.productUnderTest + ".listofdocumentslinkondocumentdisplaypage"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : DocumentListfromDocumentDisplay <br>" + displayErrorMessage(exc));

		}
	}

	public boolean isFilterByYearWidgetSorted() {
		String displayedFstYr = "", displayedSndYr = "";
		try {
			WebElement element = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".filterbyyear"));
			List<WebElement> listOfYears = element.findElements(By.tagName("li"));

			for (WebElement webElement : listOfYears) {
				if (displayedFstYr.isEmpty())
					displayedFstYr = webElement.getText().split("\\s")[0].trim();
				else
					displayedSndYr = webElement.getText().split("\\s")[0].trim();

				if (!displayedFstYr.isEmpty() & !displayedSndYr.isEmpty()) {
					return Integer.parseInt(displayedFstYr) > Integer.parseInt(displayedSndYr);
				}

			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : DocumentListfromDocumentDisplay <br>" + displayErrorMessage(exc));
			return false;

		}
		return false;
	}

	public DocumentDisplayPage OpenSpecificDocUsingUrl(String docGuid) throws Exception {
		String Url = "http://www.checkpointmexico.com.qc.int.westgroup.com/maf/app/resultList/document?&src=rl&srguid=i0acc044a0000015a3b0f3701d6963658&"
				+ "docguid=" + docGuid
				+ "&hitguid=I9e8ca9b0bc7d11e4ace6010000000000&spos=1&epos=1&td=&startChunk=1&endChunk=1&searchFrom=undefined";
		driver.get(Url);

		return new DocumentDisplayPage(driver);

	}

	/*
	 * Checks if Next link displayed in the top navigation bar returns true on
	 * success
	 */
	public boolean isNextPageLinkDisplayed() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".header_pagenavigation_nextpage");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isNextPageLinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Checks if Previous link displayed in the top navigation bar returns true
	 * on success
	 */
	public boolean isPreviousPageLinkDisplayed() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".header_pagenavigation_previouspage");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isPreviousPageLinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Checks if Last link displayed in the top navigation bar returns true on
	 * success
	 */
	public boolean isLastPageLinkDisplayed() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".header_pagenavigation_lastpage");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isLastPageLinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Checks if First link displayed in the top navigation bar returns true on
	 * success
	 */
	public boolean isFirstPageLinkDisplayed() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".header_pagenavigation_firstpage");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isFirstPageLinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * This method clicks on NextPage link in the top navigation bar
	 */
	public void clickNextPageLink() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".header_pagenavigation_nextpage"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickNextPageLink <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method clicks on PreviousPage link in the top navigation bar
	 */
	public void clickPreviousPageLink() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".header_pagenavigation_previouspage"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPreviousPageLink <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method clicks on LastPage link in the top navigation bar
	 */
	public void clickLastPageLink() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".header_pagenavigation_lastpage"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickLastPageLink <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method clicks on FirstPage link in the top navigation bar
	 */
	public void clickFirstPageLink() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".header_pagenavigation_firstpage"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickNextPage <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method returns the the current page
	 */
	public int getCurruntPageNumber() {
		int currentPageNum = 0;
		try {
			String text = elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".header_pagenavigation_currentpage"));
			currentPageNum = Integer.parseInt(text);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : getCurruntPageNumber <br>" + displayErrorMessage(ex));
		}
		return currentPageNum;
	}

	public boolean IsFilterLinksDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".filterlinks"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : IsFilterLinksDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public String getFirstDocumentTitle() {

		String firstdoc_Title = null;
		try {
			firstdoc_Title = elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlistdocument"));
			Thread.sleep(2000);

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : getFirstDocumentTitle <br>" + displayErrorMessage(e));
		}
		return firstdoc_Title;

	}
	
	public String getExpectedTitleSearchResultsPage() {
		String titleexpected =null;
		try {
			titleexpected = elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchresulttitledocumentpage"));
			Thread.sleep(2000); }
			catch (Exception e) {
				extentLogger.log(LogStatus.INFO, "Error in : getExpectedTitleSearchResultsPage <br>" + displayErrorMessage(e));
		}
			return titleexpected;
	}

	public void clickOnGivenLinkTextOnContentTree(String linkTextToClick) {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linktext2click")
							+ linkTextToClick);
			Thread.sleep(5000);
			
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnGivenLinkTextOnContentTree <br>" + displayErrorMessage(ex));

		}

	}

	public void ClickonGivenLinkTextonArticleSearchResultsPage(String Articlenum) {
		try {
			boolean found=false;
			Thread.sleep(5000);
			List<WebElement>ResultsLink= driver.findElements(By.xpath("//*[@class='documentLinkLegis_toUnit']"));
			for (WebElement myElement : ResultsLink) {
				String Data = myElement.getText();
				if(Data.contains(Articlenum))
				{
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", myElement);
					myElement.click();
					found=true;
					break;
		       }  
			}
		}catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonGivenLinkTextonArticleSearchResultsPage <br>" + displayErrorMessage(ex));

		}
	}
	public void clickonGivenLinkonTextonContentTreeText() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultspagecontenttreetext"));
			Thread.sleep(5000);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnGivenLinkTextOnContentTree <br>" + displayErrorMessage(ex));

		}
	}
	
	/*
	 * This method returns the first line text - 'year(count)' in the
	 * FilterByYearWidget
	 */
	public String getFirstYearInFilterByYearWidget() {
		String fstYearText = "";
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_filterbyYear"));
			List<WebElement> listOfYears = element.findElements(By.tagName("li"));
			WebElement fstYear = listOfYears.get(0).findElement(By.tagName("a"));
			fstYearText = fstYear.getText().split("\\s")[0].trim();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getFirstYearInFilterByYearWidget <br>" + displayErrorMessage(exc));
		}
		return fstYearText;
	}

	/*
	 * This method clicks the first line text - 'year(count)' in the
	 * FilterByYearWidget
	 */
	public void clickFirstYearInFilterByYearWidget() {
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_filterbyYear"));
			List<WebElement> listOfYears = element.findElements(By.tagName("li"));
			WebElement fstYear = listOfYears.get(0).findElement(By.tagName("a"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", fstYear);
			fstYear.click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickFirstYearInFilterByYearWidget <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks the given value - 'Name(count)' in the
	 * FilterByScopeWidget
	 */
	public void clickValueInFilterByScopeWidget(String startsWithName) {
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_filterbyScope"));
			List<WebElement> listOfItems = element.findElements(By.tagName("li"));
			for (int row = 0; row < listOfItems.size(); row++) {
				if (listOfItems.get(row).findElement(By.tagName("a")).getText().startsWith(startsWithName)) {
					listOfItems.get(row).findElement(By.tagName("a")).click();
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickValueInFilterByScopeWidget <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks the given value - 'Name(count)' in the
	 * FilterBySortType Widget
	 */
	public void clickValueInFilterBySortTypeWidget(String startsWithName) {
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_filterbySortType"));
			List<WebElement> listOfItems = element.findElements(By.tagName("li"));
			for (int row = 0; row < listOfItems.size(); row++) {
				WebElement item = listOfItems.get(row).findElement(By.tagName("a"));
				if (WebDriverFactory.isDisplayed(driver, item)) {
					if (item.getText().startsWith(startsWithName)) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", item);
						item.click();
						Thread.sleep(1000);
						break;
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickValueInFilterBySortTypeWidget <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks the given value - 'Name(filter)' in the
	 * FilterBySortType Widget
	 */
	
	public void clickTextInFilterBySortOrgano(String startsWithName ) {
		try {
			String locator =PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_filterbytextorgan");
			List<WebElement> listOfItems = elementhandler.findElements(locator);
			for (int row = 0; row < listOfItems.size(); row++) {
				WebElement item = listOfItems.get(row);
					if (item.getText().contains(startsWithName)) {
						item.click();
						Thread.sleep(1000);
						break;
					}
				}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickTextInFilterBySortOrgano <br>" + displayErrorMessage(exc));
		}
	}
	/*
	 * This method clicks the given value - 'Name(count)' in the FilterByArea
	 * Widget
	 */
	public void clickValueInFilterByAreaWidget(String startsWithName) {
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_filterbyArea"));
			List<WebElement> listOfItems = element.findElements(By.tagName("li"));
			for (int row = 0; row < listOfItems.size(); row++) {
				WebElement item = listOfItems.get(row).findElement(By.tagName("a"));
				if (WebDriverFactory.isDisplayed(driver, item)) {
					if (item.getText().startsWith(startsWithName)) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", item);
						item.click();
						Thread.sleep(1000);
						break;
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickValueInFilterByAreaWidget <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks the given value - 'Year(count)' in the FilterByYear
	 * Widget
	 */
	public void clickValueInFilterByYearWidget(String startsWithName) {
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_filterbyYear"));
			List<WebElement> listOfItems = element.findElements(By.tagName("li"));
			for (int row = 0; row < listOfItems.size(); row++) {
				WebElement item = listOfItems.get(row).findElement(By.tagName("a"));
				if (WebDriverFactory.isDisplayed(driver, item)) {
					if (item.getText().startsWith(startsWithName)) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", item);
						item.click();
						Thread.sleep(3000);
						break;
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickValueInFilterByYearWidget <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks if the result is filtered by year
	 */
	public boolean isFilteredByYear(String year) {
		boolean yearSelected = false;
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_filterbyYear"));
			List<WebElement> listOfYears = element.findElements(By.tagName("li"));
			yearSelected = (listOfYears.size() == 1) && listOfYears.get(0).getText().contains(year);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFilteredByYear <br>" + displayErrorMessage(exc));
		}
		return yearSelected;
	}

	/*
	 * This method checks if the result is filtered by 'text'
	 */
	public boolean isFilteredByValue(String textFilter) {
		boolean filteredByText = false;
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_appliedfilter"));
			List<WebElement> listFilters = element.findElements(By.tagName("li"));
			List<WebElement> linksInFilter = listFilters.get(0).findElements(By.tagName("a"));
			for (int row = 0; row < linksInFilter.size(); row++) {
				if (linksInFilter.get(row).getText().startsWith(textFilter)) {
					filteredByText = true;
					break;
				}
			}
			// If filter displayed is not a link
			if (!filteredByText) {
				filteredByText = listFilters.get(0).getText().contains(textFilter);
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFilteredByValue <br>" + displayErrorMessage(exc));
		}
		return filteredByText;
	}

	
	
	public boolean isLinkPresentforResultspage(String linkToClick) 
	{
		try {
			Thread.sleep(1000);
			return driver.findElement(By.partialLinkText(linkToClick)).isDisplayed();				
			
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "clickOnGivenLinkIfPresent" + linkToClick );
			return false;
		}
		
	}
	/*
	 * This method expands if the view of 'Filter By Year Widget'
	 */
	public void expandFilterByYearWidgetView() {
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyYear_changeView"))
					.getAttribute("class");
			if (changeView.contains("minimize-control"))
				return;
			else if (changeView.contains("maximize-control")) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyYear_changeView"));
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : expandFilterByYearWidgetView <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method expands if the view of 'Filter By Scope Widget'
	 */
	public void expandFilterByScopeWidgetView() {
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyScope_changeView"))
					.getAttribute("class");
			if (changeView.contains("minimize-control"))
				return;
			else if (changeView.contains("maximize-control")) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyScope_changeView"));
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : expandFilterByScopeWidgetView <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method expands if the view of 'Filter By Sort Widget'
	 */
	public void expandFilterBySortTypeWidgetView() {
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".widget_filterbySortType_changeView"))
					.getAttribute("class");
			if (changeView.contains("minimize-control"))
				return;
			else if (changeView.contains("maximize-control")) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".widget_filterbySortType_changeView"));
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : expandFilterByScopeWidgetView <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method expands the view of 'Filter By Area Widget'
	 */
	public void expandFilterByAreaWidgetView() {
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyArea_changeView"))
					.getAttribute("class");
			if (changeView.contains("minimize-control"))
				return;
			else if (changeView.contains("maximize-control")) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyArea_changeView"));
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : expandFilterByAreaWidgetView <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method checks if the view of 'Filter By Year Widget' is expanded
	 */
	public boolean isFilterByYearWidgetViewExpanded() {
		boolean expanded = false;
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyYear_changeView"))
					.getAttribute("class");
			if (changeView.contains("minimize-control"))
				expanded = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFilterByYearWidgetViewExpanded <br>" + displayErrorMessage(ex));
			expanded = false;
		}
		return expanded;
	}

	/*
	 * This method checks if the view of 'Filter By Year Widget' is collapsed
	 */
	public boolean isFilterByYearWidgetViewCollapsed() {
		boolean collapsed = false;
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyYear_changeView"))
					.getAttribute("class");
			if (changeView.contains("maximize-control"))
				collapsed = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFilterByYearWidgetViewCollapsed <br>" + displayErrorMessage(ex));
			collapsed = false;
		}
		return collapsed;
	}

	/*
	 * This method checks if the view of 'Filter By Scope Widget' is collapsed
	 */
	public boolean isFilterByScopeWidgetViewCollapsed() {
		boolean collapsed = false;
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyScope_changeView"))
					.getAttribute("class");
			if (changeView.contains("maximize-control"))
				collapsed = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFilterByScopeWidgetViewCollapsed <br>" + displayErrorMessage(ex));
			collapsed = false;
		}
		return collapsed;
	}

	/*
	 * This method checks if the view of 'Filter By Sorttype Widget' is
	 * collapsed
	 */
	public boolean isFilterBySortTypeWidgetViewCollapsed() {
		boolean collapsed = false;
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".widget_filterbySortType_changeView"))
					.getAttribute("class");
			if (changeView.contains("maximize-control"))
				collapsed = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFilterBySortTypeWidgetViewCollapsed <br>" + displayErrorMessage(ex));
			collapsed = false;
		}
		return collapsed;
	}

	/*
	 * This method checks if the view of 'Filter By Area Widget' is collapsed
	 */
	public boolean isFilterByAreaWidgetViewCollapsed() {
		boolean collapsed = false;
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyArea_changeView"))
					.getAttribute("class");
			if (changeView.contains("maximize-control"))
				collapsed = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFilterByAreaWidgetViewCollapsed <br>" + displayErrorMessage(ex));
			collapsed = false;
		}
		return collapsed;
	}

	/*
	 * This method collapses the view of 'Filter By Year Widget'
	 */
	public void collapseFilterByYearWidgetView() {
		try {
			String changeView = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyYear_changeView"))
					.getAttribute("class");
			if (changeView.contains("maximize-control"))
				return;
			else if (changeView.contains("minimize-control"))
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyYear_changeView"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : collapseFilterByYearWidgetView <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method clicks on applied filter link in the 'Filter Widget' returns
	 * true on success
	 */
	public boolean clcikLinkInAppliedFilterWidget(String partialLinkName) {
		boolean partialLinkClicked = false;
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_appliedfilter"));
			List<WebElement> list = element.findElements(By.tagName("a"));
			for (int row = 0; row < list.size(); row++) {
				if (list.get(row).getText().contains(partialLinkName)) {
					list.get(row).click();
					partialLinkClicked = true;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clcikLinkInAppliedFilterWidget <br>" + displayErrorMessage(exc));
		}
		return partialLinkClicked;
	}

	/*
	 * This method clicks on applied filter link in the 'Filter Widget' returns
	 * true on success
	 */
	public boolean clcikSecondLevelLinkInAppliedFilterWidget(String parentLinkName, String filterLink) {
		boolean parentLinkClicked = false, filterLinkClicked = false;
		try {
			WebElement element = elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_appliedfilter"));
			List<WebElement> listFilters = element.findElements(By.tagName("li"));
			List<WebElement> linksInFilter = listFilters.get(0).findElements(By.tagName("a"));
			for (int row = 0; row < linksInFilter.size(); row++) {
				WebElement item = linksInFilter.get(row);
				if (item.getText().contains(parentLinkName)) {
					if (row == linksInFilter.size() - 1) {
						item.click();
						parentLinkClicked = true;
						break;
					} else if (linksInFilter.get(row + 1).getText().contains(filterLink)) {
						linksInFilter.get(row + 1).click();
						filterLinkClicked = true;
						break;
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clcikSecondLevelLinkInAppliedFilterWidget <br>" + displayErrorMessage(exc));
		}
		return filterLinkClicked || parentLinkClicked;
	}

	/*
	 * This method expands if the view of Search Result Widget'
	 */
	public void collapseSearchResultsWidget() {
		try {
			List<WebElement> elements = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".widget_searchResults_changeView"));
			String changeView = elements.get(0).getAttribute("class");
			if (changeView.contains("maximize-control"))
				return;
			else if (changeView.contains("minimize-control")) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".widget_searchResults_changeView"));
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : collapseSearchResultsWidget <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method scrolls into the view of 'Filter By Year Widget'
	 */
	public void scrollIntoViewFilterByYearWidget() {
		try {
			WebElement changeView = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyYear_changeView"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", changeView);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : scrollIntoViewFilterWidget <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method scrolls into the view of 'Filter By Scoper Widget'
	 */
	public void scrollIntoViewFilterByScopeWidget() {
		try {
			WebElement changeView = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyScope_changeView"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", changeView);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : scrollIntoViewFilterByScopeWidget <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method scrolls into the view of 'Filter By SortType Widget'
	 */
	public void scrollIntoViewFilterBySortTypeWidget() {
		try {
			WebElement changeView = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".widget_filterbySortType_changeView"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", changeView);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : scrollIntoViewFilterBySortTypeWidget <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method scrolls into the view of 'Filter By Area Widget'
	 */
	public void scrollIntoViewFilterByAreaWidget() {
		try {
			WebElement changeView = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".widget_filterbyArea_changeView"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", changeView);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : scrollIntoViewFilterByAreaWidget <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method scrolls into the view of 'Items in applied filter Widget'
	 */
	public void scrollIntoViewAppliedFilterWidget() {
		try {
			WebElement changeView = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".widget_appliedfilter"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", changeView);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : scrollIntoViewAppliedFilterWidget <br>" + displayErrorMessage(ex));
		}
	}

	public DeliveryPage ClickOnSearchLink() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlistdocument"));
			return new DeliveryPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : ClickOnSearchLinkToDocumentDispaly <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	public DeliveryPage clickPrint() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".printlink")), 30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".printlink"));
			return new DeliveryPage(driver);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPrint <br>" + displayErrorMessage(e));
			return null;
		}
	}

	public String getSecondContentTypeNameFromFacetList() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".secondfacetlink"))
					.getText();

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getFirstContentTypeNameFromFacetList <br>" + displayErrorMessage(e));
			return "";
		}
	}

	public boolean keywordOnResultList() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchtext"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : keywordResult <br>" + displayErrorMessage(ex));
			return false;
		}
		return true;
	}

	/*
	 * This method is used to verify result facet is displayed in the search result page.
	 */
	public boolean isResultFecetDisplayed() {
		try {
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultfecet"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : resultFecet <br>" + displayErrorMessage(ex));
			return false;
		}
		return true;
	}

	public boolean isTableOfContentsLinkDisplayed() {
		try {

			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".tree_of_content_link"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isTableOfContentsLinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public ContenttreeOnsearchResultPage clickTableOfContentsLink() {
		ContenttreeOnsearchResultPage docView = null;
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".tree_of_content_link"));
			docView = new ContenttreeOnsearchResultPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickTableOfContentsLink <br>" + displayErrorMessage(ex));
		}
		return docView;
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
	
	public DeliveryPage clickExportButtons() {
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
	 * //to get the title of first document in the search result page public
	 * String getFirstDocumentTitle() { String first_doc=null; try{
	 * List<WebElement> result=
	 * elementhandler.findElements(PropertiesRepository.getString(
	 * "com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofresultlist"));
	 * int count = result.size(); first_doc=result.get(0).toString();
	 * 
	 * }catch(Exception ex) { extentLogger.log(LogStatus.INFO,
	 * "Error in : selectThematicArea <br>"+displayErrorMessage(ex)); } return
	 * first_doc; }
	 */
	// Verify NIF Update in First document title
	public boolean verifyNIFUpdateInResultList() {
		boolean flag = false;
		try {
			String actualtitle = getFirstDocumentTitle();
			String exptitle = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".NIF_Firstdocumenttitle");
			if (actualtitle.contains(exptitle)) {
				flag = true;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyNIFUpdateInResultList <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	// Adding this method for MAFAUTO-245
	public DocumentDisplayPage clickOnRubroTextLink() {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rubrotextlink"));
			return new DocumentDisplayPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRubroTextLink <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	public boolean validateRubroLinkText(String linktext) {
		boolean flag = false;
		try {

			String text = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rubrokeywordtext"))
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

	public boolean validateRubroLinkText1(String linktext) {
		boolean flag = false;
		try {

			String text = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rubrotextlink"))
					.getText();
			if (text.toLowerCase().contains(linktext)) {
				flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateRubroLinkText <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isFilterByIndexDispalyed() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".filterbyindexlabel"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFilterByIndexDispalyed <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	
	

	
	/*
	 * This method iterates through the search results list and clicks on the link given as input value
	 * Returns the DocumentDisplayPage if the given link is found to click
	 */
	public DocumentDisplayPage clickOnGivenDocumentToClickOnResults(String linkname)
			throws IllegalArgumentException, IOException, InterruptedException {
		try {
			Thread.sleep(2000);
			String alllinks = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".alllinksonformspage");
			List<WebElement> locators = elementhandler.findElements(alllinks);

			for (int i = 0; i < locators.size(); i++) {
				String linkname1 = locators.get(i).getText();
				if (linkname1.contains(linkname)) {
					Thread.sleep(1000);
					locators.get(i).click();
				}

			}

		} catch (Exception exc) {
			//extentLogger.log(LogStatus.INFO, "Error in : clickOnGivenDocumentToClickOnResults <br>" + displayErrorMessage(exc));
		}
		return new DocumentDisplayPage(driver);
	}
	
	public DocumentDisplayPage clickOnGivenDocumentToClickOnResultsmatches(String linkname)throws IllegalArgumentException, IOException, InterruptedException {
		try {
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".filterlinkpageresultsmatches"));
			Thread.sleep(2000);
			String alllinks = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linksallseematchesresulstpage");
			List<WebElement> locators = elementhandler.findElements(alllinks);

			for (int i = 0; i < locators.size(); i++) {
				String linkname1 = locators.get(i).getText();
				if (linkname1.contains(linkname)) {
					Thread.sleep(1000);
					locators.get(i).click();
					break;
				}

			}

		} catch (Exception exc) {
			//extentLogger.log(LogStatus.INFO, "Error in : clickOnGivenDocumentToClickOnResults <br>" + displayErrorMessage(exc));
		}
		return new DocumentDisplayPage(driver);
	}
	
	public HomePage clickonHomeTab() throws IllegalArgumentException, IOException
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".hometab"));
			
		}catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnGivenLInk <br>" + displayErrorMessage(exc));
		}
		return new HomePage(driver);
	}

	
	/*
	 * This method checks to see if the search results displayed are based on the given data
	 * Returns True if the results are based on the data else returns false.
	 * 
	 */
	public boolean searchResultsDisplayedBasedOnSearchData(String period, String denominacios, String clase ) {
		boolean resultDisplayedAsExpected = false;
		
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".resultscontainer");
		if(WebDriverFactory.isDisplayed(driver, elementhandler.getElement(locator)))
			{
			    locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".getallresults"); 
			    List<WebElement> getAllResults = elementhandler.findElements(locator);
			    
			    for (WebElement webElement : getAllResults) 
			    {
			    	List<WebElement> getAllRowTexts = webElement.findElements(By.xpath("//table[@class='tableResult']//tr[2]//td"));
			    	String actualPeriod = getAllRowTexts.get(1).getText();
			    	String actualDenominacios = getAllRowTexts.get(2).getText();
			    	String actualClase = getAllRowTexts.get(4).getText();
			    	
			    	resultDisplayedAsExpected = actualPeriod.contains(period)
			    			&& actualDenominacios.contains(denominacios)
			    			&& actualClase.contains(clase);
			    	if(!resultDisplayedAsExpected)
			    		break;
			  }
			}
			
		}catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayedBasedOnSearchData <br>"+displayErrorMessage(e));
			resultDisplayedAsExpected=false;				
		}
		return resultDisplayedAsExpected;

	}
	



	
/*	public ObligacionesPage clickingReformular()

	{
		
		try{	
			
			
			 elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".reformular")).click();
			 return new ObligacionesPage(driver);
			 
			
			}catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : validateCreateShortcutinToolsTab <br>"+displayErrorMessage(e));
						
		}
		return null;
	}
*/
	public ObligacionesPage clickingNueva() {
		

		try{	
			
			
			 elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".nueva")).click();
			 return new ObligacionesPage(driver);
			 
			
			}catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : validateCreateShortcutinToolsTab <br>"+displayErrorMessage(e));
						
		}
		return null;
	}
	
	
	/*public void enterSearchWithInTerm(String enterbuscaren) {
		
		try{	
			 elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".buscarenresultados")).sendKeys(enterbuscaren);
			 		
			}catch(Exception e){
			extentLogger.log(LogStatus.INFO, "Error in : enterSearchWithInTerm <br>"+displayErrorMessage(e));
						
		}
	}*/


	/*
	 * This method ensures the given search term is displayed on the search results.
	 * Returns True if the search term is displayed on the result set 
	 * Returns False if the search term is not displayed on the result set
	 * * If the element is not present, catch block will handle the exception and log the screen shot on the report
	 */
	public boolean searchResultsDisplayedBasedOnSearchWithInTerm(String enterbuscaren) {
		
		boolean flag=false;
		try
		{
		if(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest+".resultscontainer")).isDisplayed())
		{
		    String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".getallresults"); 
		    List<WebElement> getAllResults = elementhandler.findElements(locator);
		    
		    for (WebElement webElement : getAllResults) 
		    {
		    	List<WebElement> getAllRowTexts = webElement.findElements(By.xpath("//table[@class='tableResult']//tr[2]//td"));
		    	
		    	for(int i=0; i<getAllRowTexts.size();i++)
		    	{
		    		switch(i)
		    		{
		    			case 2:
		    				flag = getAllRowTexts.get(i).getText().contains(enterbuscaren);
		    				break;
		    			
		    				
		    			default:
		    				//continue;
		    		}
		    			
		    	}
				
		    }
		
		}
		
	}catch(Exception e){
		extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayedBasedOnSearchData <br>"+displayErrorMessage(e));
		flag=false;				
	}
	return flag;

}
	
	public SignOffPage waitTillSessionTimeOut(int sessionTimeOutInMinits){
		SignOffPage signOffPage = null;
		try {
			Thread.sleep((sessionTimeOutInMinits*60+60)*1000);
			driver.navigate().refresh();
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".SignoffText");
			boolean sessionExpired = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(selector));
			if(sessionExpired)
				signOffPage = new SignOffPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : waitTillSessionTimeOut <br>" + displayErrorMessage(exc));
		}
		return signOffPage;
	}
	
	public boolean isDenominacionFilteredWithValue(String expectedValue){
		boolean validated = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".denominationcolumnvalues");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualValue= elements.get(row).getText();
				validated = (actualValue.contains(expectedValue));
				if(!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyDenominacionFilteredWithValue <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}
	
	public boolean searchResultsDisplayedForPeroidinForeignCurrnecy(String period){
		boolean validated = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".periodsearchcolumn");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualValue= elements.get(row).getText();
				validated = (actualValue.contains(period));
				if(!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayedForPeroidinForeignCurrnecy <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}
	
	public boolean searchResultsDisplayedForcoininForeignCurrnecy(String coin){
		boolean validated = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".coinsearchcolumn");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualValue= elements.get(row).getText();
				validated = (actualValue.contains(coin));
				if(!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayedForcoininForeignCurrnecy <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}
	
	public boolean searchResultsDisplayedForPeroidValuationWheel(String period){
		boolean validated = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".periodcolumn");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualValue= elements.get(row).getText();
				validated = (actualValue.contains(period));
				if(!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayedForPeroidinForeignCurrnecy <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}
	
	public boolean searchResultsDisplayedForBrand(String brand){
		boolean validated = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".brandcolumn");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualValue= elements.get(row).getText();
				validated = (actualValue.contains(brand));
				if(!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayedForBrand <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}
	
	public boolean searchResultsDisplayedForModel(String model){
		boolean validated = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".modelcolumn");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualValue= elements.get(row).getText();
				validated = (actualValue.contains(model));
				if(!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayedForModel <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}
	
	public boolean searchResultsDisplayedForkind(String kind){
		boolean validated = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".kindcolumn");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualValue= elements.get(row).getText();
				validated = (actualValue.contains(kind));
				if(!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayedForkind <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}
	
	public boolean searchResultsDisplayedForYrOfProd(String yrprod){
		boolean validated = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".yrofmancolumn");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualValue= elements.get(row).getText();
				validated = (actualValue.contains(yrprod));
				if(!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayedForYrOfProd <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}
	
	public boolean isDeliveryOptionDisplay()
	{
		boolean flag=false;
		try{
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliverysave")).isDisplayed()
				&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliveryprint")).isDisplayed()
			&& elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliveryemail")).isDisplayed();
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isDeliveryOptionDisplay <br>"+displayErrorMessage(exc));
			
		}
		return flag;
	}
	
	public DeliveryPage clickEmailbutton()
	{
		DeliveryPage deliverypage= null;
		try {
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deliveryemail"));
			deliverypage=new DeliveryPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickEmailbutton <br>" + displayErrorMessage(exc));
		}
		return deliverypage;
	}
	
	public boolean isColumndisplayWithSearchData(String expectedValue){
		boolean validated = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".brandcolumn");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualValue= elements.get(row).getText();
				validated = (actualValue.contains(expectedValue));
				if(!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isColumndisplayWithSearchData <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}
	
	public boolean searchResultsDisplayedclase(String clase){
		boolean validated = false;
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest+".clasecolumn");
			List<WebElement> elements=elementhandler.findElements(locator);
			for(int row=0;row<elements.size();row++){
				String actualValue= elements.get(row).getText();
				validated = (actualValue.contains(clase));
				if(!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayedclase <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}

	/*
	 * This method checks to see if the last document viewed row is displayed on the search results
	 * and the row is highlighted as expected
	 * Return true / false based on the element display
	 */
	public boolean isLastDocumentViewedHighlightDisplayedOnResults() {
		try {
			return elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lastdocumentviewedonresults")).isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isLastDocumentViewedHighlightDisplayedOnResults <br>" + displayErrorMessage(exc));
			return false;
		}
	}
	
	//This method verifies whether filter by year widget is displayed or not
	public boolean isFilterByYearWidgetDisplayed() {
		try {
			return elementhandler
					.findElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".filterbyyearwidget"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFilterByYearWidgetDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isDeliveryExportButtonDisplay() {

		try {
			return elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".export_document_image"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDeliveryExportButtonDisplay <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isDeliveryEmailButtonDisplay() {

		try {
			return elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".emaillink"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isExportButtonDisplay <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isDeliveryPrintButtonDisplay() {

		try {
			return elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".printlink"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isExportButtonDisplay <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isResultsFacetDisplayed() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allcontenttypelinks")));
		} catch (Exception ex) {
			// extentLogger.log(LogStatus.INFO, "Error in : isResultsFacetDisplayed <br>" +
			// displayErrorMessage(ex));
			return false;
		}
	}

	public void expandFilterbyYearPublication() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".widget_filterbyyear_expand");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : expandFilterbyYearPublication <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method used to get title names
	 */

	public boolean isSpecifiedDateDisplayedInTitle(String date) {
		boolean flag = false;
		try {
			List<WebElement> titlelist = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultlistdocument"));
			for (int i = 0; i < titlelist.size(); i++) {
				if (WebDriverFactory.isDisplayed(driver, titlelist.get(i))) {
					String actualtitle = titlelist.get(i).getText();
					if (actualtitle.contains(date)) {
						flag = true;
						break;
					} else {
						flag = false;
						
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getResultListTitle <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method is used to verify Autor Filter is displayed
	 */

	public boolean isAutorFilterLabelDisplayed(String Filterlabel) {
		boolean flag = false;
		try {
			/*
			 * ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
			 * elementhandler.getElement( PropertiesRepository.getString("com.trgr.maf." +
			 * BaseTest.productUnderTest + ".AutorFilterLabel")));
			 */
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".AutorFilterLabel")),
					30);

			if (WebDriverFactory.isDisplayed(driver, elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".AutorFilterLabel"))))

			{
				flag = elementhandler
						.getText(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".AutorFilterLabel"))
						.contains(Filterlabel);
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isAutorFilterLabelDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public SearchResultsPage clickOnGivenContentTypeOnFacet(String contentType) throws Exception {
		try {
			Thread.sleep(3000);

			WebElement locator = driver.findElement(By.partialLinkText(contentType));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", locator);
			if (locator.isDisplayed()) {
				locator.click();
			}
			return new SearchResultsPage(driver);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickOnGivenContentTypeOnFacet <br>" + displayErrorMessage(ex));
			return null;
		}

	}

	// to check for the given text present in the document results titles
	public boolean isGivenTextPresentInResultList(String title) throws IOException {
		boolean titleFound = false;
		try {
			Thread.sleep(3000);

			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".listofdocumentsonsearchresults");
			List<WebElement> titlesList = elementhandler.findElements(selector);
			for (int row = 0; row < titlesList.size(); row++) {
				// title= title.toLowerCase();
				titleFound = titlesList.get(row).getText().toLowerCase().contains(title.toLowerCase());

				if (titleFound)
					continue;
			}
			return titleFound;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isGivenTextPresentInResultList <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	// clicks on 'See All' link in the result list page
	public void clickSeeAllLink(String docType) {
		try {
			Thread.sleep(3000);
			String docTitle = "xpath=.//span[contains(text(),'" + docType + "')]//a[contains(text(), 'Ver todos')]";
			WebElement element1 = elementhandler.getElement(docTitle);
			Thread.sleep(3000);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",element1);
			element1.click();
		}

		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSeeAllLink <br>" + displayErrorMessage(exc));

		}
	}

	// Description:"This method is used to check whether the number of results text
	// is displayed next to the document type"
	// <Created Date : 25-Oct-2018 > ; <author : Havya>

	public boolean verifyTotalResultsText() {
		try {

			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".verifyTotalNofResultsText");
			return elementhandler.findElement(locator).isDisplayed();

		}

		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSeeAllLink <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	// Description:"This method is used to check whether the left panel has 'Result
	// of the search' and 'Filter results'"
	// <Created Date : 25-Oct-2018 > ; <author : Havya>

	public boolean isWidgetsDisplayedInLeftPanel(String Widgetname) {
		try {

			String locator = "xpath=.//div[@class='search-left-panel-box box']//div[text()='" + Widgetname + "']";
			Thread.sleep(1000);
			return elementhandler.findElement(locator).isDisplayed();

		}

		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isWidgetsDisplayedInLeftPanel <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	// Description:"This method is used to check whether the results of the expected
	// document type is displayed"
	// <Created Date : 25-Oct-2018 > ; <author : Havya>

	public boolean isDocTypeLabelDisplayed(String doctype) {
		try {

			String locator = "xpath=.//span[@id='globalResultTitleLabel'][contains(text(),'" + doctype + "')]";
			Thread.sleep(1000);
			return elementhandler.findElement(locator).isDisplayed();

		}

		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocTypeLabelDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean verifyCountOfDocs() {
		boolean flag = false;
		try {

			String locator1 = "xpath=.//li[@class='selectedRL']//a";
			String locator2 = "xpath=(//div[@class='globalResultTitle']//span)[3]";

			WebElement element1 = elementhandler.findElement(locator1);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",element1);
			WebElement element2 = elementhandler.findElement(locator2);

			String value1 = (element1.getText().split("\\("))[1];
			value1 = value1.split("\\)")[0];
			String text = element2.getText();
			if (text.contains(value1)) {
				flag = true;
			}
		}

		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDocTypeLabelDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	}

	// This method validates whether the print,email and print icons are present.
	// this function applicable for home page and search page
	// <Created Date : 25-Oct-2018 > ; <author : Saikiran>
	public boolean validateActions(String docType) {
		try {

			String docTitle = "xpath=//img[@title='" + docType + "']";
			return elementhandler.getElement(docTitle).isDisplayed();
		}

		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSeeAllLink <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	// This method will click the unit and rubric link
	// this function applicable for home page and search page
	// <Created Date : 26-Oct-2018 > ; <author : Saikiran>
	public DocumentDisplayPage clickUnitAndRubric() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".unitandrubric"));
			return new DocumentDisplayPage(driver);
		}

		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickUnitAndRubric <br>" + displayErrorMessage(exc));
			return null;
		}

	}
	
	//Description:clicking on ver más(SeeMore) link
    //<Created Date :  29-Oct-2018 >   ; <author : Roja> 
	public boolean clickOnVermasLink() {
		boolean flag = false;
		try {
			
			WebElement vermas = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".verMasLink"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",vermas);
			
			if(vermas.isDisplayed()) {
			vermas.click();
			flag=true;
			}
		
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnVermasLink <br>" + displayErrorMessage(ex));
			flag=false;
		}
		return flag;
	}

	
	public boolean compareCountInSeeAllLink(int index) {
	
		boolean flag = false;
		try {

			Thread.sleep(1000);
			String locator1 = "xpath=((//div[@class=\"search-left-panel-content box-container\"])[2]//a)[%s]";
			locator1 = String.format(locator1, index);
			String locator2 = "xpath=(//span[@id=\"globalResultTitleLabel\"]//a)[%s]";
			locator2 = String.format(locator2, index-1);
			
			
			WebElement element1 = elementhandler.findElement(locator1);
			WebElement element2 = elementhandler.findElement(locator2);

			String value1 = (element1.getText().split("\\("))[1];
			value1 = value1.split("\\)")[0];
			String value2 = (element2.getText().split("\\("))[1];
			value2 = value2.split("\\)")[0];
			if (value2.equals(value1)) {
				flag = true;
			}
		}

		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : compareCountInSeeAllLink <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	
	}
	
	//to click on "ver todos" link in chpmex results page
	//Description:clicking on ver más(SeeMore) link
    //<Created Date :  01-Feb-2019 >   ; <author : Kavitha> 
	public boolean clickVertodosLink()
	{
		try {
			String loc="xpath=//a[contains(text(),'Ver todos')]";
			WebElement ele = elementhandler.getElement(loc);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",ele);
			
			if(ele.isDisplayed()) {
			ele.click();
			flag=true;
			}
		
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickVertodosLink <br>" + displayErrorMessage(ex));
			flag=false;
		}
		return flag;
	}
	
	
	public boolean isErrorPageDisplayed() {
		try {
			return driver.findElement(By.xpath("//div[@id='errorPage']//p[@class='msg']")).isDisplayed();
			
		    }
		
		catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickVertodosLink <br>" + displayErrorMessage(ex));
			flag=false;
		}
		return flag;
	}
	public boolean compareTitleArticlePresentResultsPageExpected(String ResultsSearchPage) {
		boolean found=false;
		try {
		String locator="(//a[contains(text(),'Ver coincidencias')])[3]";
		
			List<WebElement>ResultsLink= driver.findElements(By.xpath("//div//a[@class='documentLink']"));
			for (WebElement myElement : ResultsLink) {
				String Data = myElement.getText();
				if(Data.contains(ResultsSearchPage)) {
					found= true;
					Thread.sleep(2000);
					WebElement element=driver.findElement(By.xpath(locator));
					element.click();							
					break;
				}						
			}		
			
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : selectingSuggestionsDropdownDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
		
		return found;
	}
	
	public void clickVertodosLinkSearchResultsPage() {
		try {
			String loc="xpath=//a[contains(text(),'Ver todos')]";
			WebElement ele = elementhandler.getElement(loc);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",ele);
			
			if(ele.isDisplayed()) {
			ele.click();
			}
		
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickVertodosLinkSearchResultsPage <br>" + displayErrorMessage(ex));
		}
	}
}
