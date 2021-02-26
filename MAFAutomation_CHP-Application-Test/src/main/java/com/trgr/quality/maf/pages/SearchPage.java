package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class SearchPage extends BasePage {
	SearchPage searchpage;
	Boolean flag = false;

	public SearchPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		/*
		 * WebDriverFactory .waitForElementUsingWebElement(driver,
		 * elementhandler.getElement(PropertiesRepository
		 * .getString("com.trgr.maf." + BaseTest.productUnderTest +
		 * ".searchbtnonsearchpage")), 20);
		 */
	}

	public RoutesPage OpenRoutesPage() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchroutes"));
			return new RoutesPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : OpenRoutesPage <br>" + displayErrorMessage(ex));
			return null;
		}
	}

	public boolean searchResultsDisplayed() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Legislacin"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : searchResultsDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean searchResultsHeaderContainerDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultsheadercontainer"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : searchResultsHeaderContainerDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public void clickOnCleanSearch() {
		try {
			elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage")).click();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnCleanSearch <br>" + displayErrorMessage(exc));
		}
	}

	public JurisprudencePage openJurisprudencePage() throws Exception {
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchjurisprudence"))
					.isDisplayed()) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchjurisprudence"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new JurisprudencePage(driver);
			}
		} catch (Exception ex) {
			try {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage"));

				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchjurisprudence"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new JurisprudencePage(driver);
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : openJurisprudencePage <br>" + displayErrorMessage(exc));
				return null;
			}
		}
		return null;

	}
	
	public JurisprudencePage isDisplayedAdvPage() {
		try {
			elementhandler
			.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancesearchlinktag"))
			.isDisplayed();
			return new JurisprudencePage(driver);
		}
		
		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDisplayedAdvPage <br>" + displayErrorMessage(exc));
			return null;
		}
	}
	
	public FormsPage isDisplayedAdvPageFormsPage() {
		try {
			elementhandler
			.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancesearchlinktag"))
			.isDisplayed();
			return new FormsPage(driver);
		}
		
		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDisplayedAdvPageFormsPage <br>" + displayErrorMessage(exc));
			return null;
		}
	}
	
	
	public boolean clickOnExpectedAdvancedSearch(String Expected) {
		
	boolean flag=false;
		try{
			List<WebElement> FuentoFacetlist = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".AdvanceSearchLinks"));
			   Thread.sleep(1000);
				for (int i = 0; i < FuentoFacetlist.size(); i++) {
					String actualitem = FuentoFacetlist.get(i).getText();
					
					if (Expected.equalsIgnoreCase(actualitem))
					{
						
			           Thread.sleep(3000);
						FuentoFacetlist.get(i).click();
						flag = true;
					    break;
				}
		}
		}
		
		catch (Exception ex) {
		
		if(flag=false) {
			try {
				List<WebElement> FuentoFacetlist = elementhandler.findElements(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".AdvanceSearchLinks"));
				   Thread.sleep(1000);
					for (int i = 0; i < FuentoFacetlist.size(); i++) {
						String actualitem = FuentoFacetlist.get(i).getText();
						
						if (Expected.equalsIgnoreCase(actualitem))
						{
							
				           Thread.sleep(3000);
							FuentoFacetlist.get(i).click();
							flag = true;
						    break;
					}
					}
			     }
					catch (Exception exc) {
						extentLogger.log(LogStatus.INFO, "Error in : clickOnExpectedAdvancedSearch <br>" + displayErrorMessage(exc));
					 flag = false;
			}
		}
		}
		return flag;
		
		}
		
	

	public void enterFreeWordOnSearchPage(String freeword) {
		try {

			Thread.sleep(2000);
			elementhandler
			.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage")).click();
			Thread.sleep(2000);
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
					.sendKeys(freeword);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterFreeWordOnSearchPage <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * Enter thematic search field value on the search page.
	 * Takes the input value as string for the test data to search
	 */
	public void enterThematicOnSearchPage(String thematicSearchString) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonsearchpage");
			elementhandler.writeText(locator, thematicSearchString);
			Thread.sleep(3000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterThematicSearchOnQuickSearch <br>" + displayErrorMessage(exc));
		}

	}

	/*
	 * This method clicks on the search button by scrolling to the element
	 * whereever it is on the page Returns the Handle to the Search Results Page
	 * If the element is not present, catch block will handle the exception and
	 * log the screen shot on the extent report
	 */
	public SearchResultsPage clickOnSearch() throws Exception {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".searchbtnonsearchpage");
			// webelementclick is not working for Ury and ppy
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementhandler.getElement(locator));
			Thread.sleep(6000);
			// elementhandler.clickElement(locator);
			
		

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnSearch <br>" + displayErrorMessage(exc));
			return null;
		}
		return new SearchResultsPage(driver);
	}

	
	public SearchResultsPage clickSearchUrySearchPage() throws Exception {
		try {
		
		
			WebElement element =	driver.findElement(By.xpath("(//input[@id='searchButton'])[2]"));
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
			Thread.sleep(6000);
			

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearchUrySearchPage <br>" + displayErrorMessage(exc));
			return null;
		}
		return new SearchResultsPage(driver);
	}
	public LegislationPage OpenLegislationPage() throws Exception {
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"))
					.isDisplayed()) {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"));
							
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new LegislationPage(driver);
			}
		} catch (Exception ex) {
			try {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage"));
					Thread.sleep(3000);
					
					String locator = PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation");
					elementhandler.getElement(locator);
					
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", locator);
				
					if(searchpage.isPresentLegsilationSearch()) {
						elementhandler.clickElement(PropertiesRepository
										.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"));
					}
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new LegislationPage(driver);
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : OpenLegislationPage <br>" + displayErrorMessage(exc));
				return null;
			}
		}
		return null;

	}

	
	public boolean  isPresentLegsilationSearch() {
		try {
			String xpath="//p//*[@id='nameDispositionLabel']";
			
			driver.findElement(By.xpath(xpath));
			
			return false;
		}
		 catch (Exception exc) {
			
				return true;
			}
	}

	public DoctrinePage OpenDoctrinaPage() throws Exception {
		try {
			Thread.sleep(2000);
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"))
					.isDisplayed()) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new DoctrinePage(driver);
			}
		} catch (Exception ex) {
			try {
				Thread.sleep(2000);
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage"));

				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new DoctrinePage(driver);
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : OpenDoctrinaPage <br>" + displayErrorMessage(exc));
				return null;
			}
		}
		return null;

	}

	public void enterAuthor(String author) {
		try {
			Thread.sleep(3000);
			elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".author"))
					.sendKeys(author);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterAuthor <br>" + displayErrorMessage(exc));
		}

	}
	public void enterAuthorFindDropDownPage(String author) {
		try {
			String xpath="//div[@class='ac_results']//li";
			Thread.sleep(1000);
			List<WebElement>data= driver.findElements(By.xpath(xpath));
			if(data.size()>=1) {
				for(int i=0;i<data.size();i++) {
					String CurrentOption = data.get(i).getText();
					 if(CurrentOption.equalsIgnoreCase(author)){
			                data.get(i).click();
			                break;
			            }
				}
		
			}			
					
		} 
			catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterAuthorDropDownValue <br>" + displayErrorMessage(exc));
		}
	}
	
	
	public boolean isPresentTextExpected() {
		boolean flag=false;
		try {
			driver.findElement(By.xpath("//p[text()='ha ocurrido un error desconocido. Por favor, póngase en contacto con soporte para más datos']")).isDisplayed();
			
		}
		catch (Exception exc) {
			flag=false;		
		}
		return flag;
	}
	
	public boolean isResultsPageDisplayedLegislation() {
		try {
		return 	driver.findElement(By.xpath("//div[@class='globalResultTitle']")).isDisplayed();
		}
		catch (Exception exc) {
			flag=false;		
		}
		return flag;
	}
	
	
	public void enterAuthorDropDownValue()
	{
		try {
			String xpath="//div[@class='ac_results']//li";
			Thread.sleep(1000);
			List<WebElement>data= driver.findElements(By.xpath(xpath));
			if(data.size()>=1) {
				for(int i=0;i<data.size();i++) {
					String CurrentOption = data.get(i).getText();
					 if(CurrentOption.equalsIgnoreCase("Araceli Paz González")){
			                data.get(i).click();
			            }
				}
		
			}			
					
		} 
			catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterAuthorDropDownValue <br>" + displayErrorMessage(exc));
		}
	}
	
	

	public FormsPage openFormsPage() throws Exception {
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchforms"))
					.isDisplayed()) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchforms"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new FormsPage(driver);
			}
		} catch (Exception ex) {
			try {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage"));

				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchforms"));
				WebDriverFactory.waitForElementUsingWebElement(driver,
						elementhandler.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage")),
						20);

				return new FormsPage(driver);
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : openFormsPage <br>" + displayErrorMessage(exc));
				return null;
			}
		}
		return null;

	}

	public void scrollToGivenElement() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementhandler.getElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage")));
	}

	/*
	 * Clicking Clear button for any of the search or advance search pages
	 */

	public void clickClear() {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage")),
							120);
			scrollToGivenElement();
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage"));
			Thread.sleep(1000);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickClear <br>" + displayErrorMessage(exc));
		}

	}
	
	public void clickClearonSearchPage() {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage")),
							120);
			Thread.sleep(3000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage"));
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickClearonSearchPage <br>" + displayErrorMessage(exc));
		}
	}
	
	public boolean isSearchPageFilledWithData() {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".verifysearchpagedata")),
							120);
			Thread.sleep(3000);
			
			return true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickClearonSearchPage <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public DocumentDisplayPage clickFirstLink() throws Exception {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstlink")),
					80);
			elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstlink"))
					.click();
			return new DocumentDisplayPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFirstLink <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	public boolean isFacetwidgetDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fuentewidget"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFacetwidgetDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	public boolean validateFuentoFacetitems() {
		try {
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
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateFuentoFacetitems <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	public boolean isCategoryResultDisplayed() {
		try {
			List<WebElement> categoryresultlist = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".categoryresultlist"));
			for (int i = 0; i < categoryresultlist.size(); i++) {
				if (categoryresultlist.get(i).isDisplayed())
					flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCategoryResultDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	public boolean clickOnAnyItem() {
		try {
			List<WebElement> FuentoFacetlist = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fuentewidgetlist"));
			// List<WebElement>
			// categoryresultlist=elementhandler.findElements(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest
			// + ".categoryresultlist"));
			for (int i = 0; i < FuentoFacetlist.size(); i++) {
				String expectedcategory = FuentoFacetlist.get(i).getText();
				FuentoFacetlist.get(i).click();
				String actualcategory = elementhandler.getElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".categoryname"))
						.getText();
				if (expectedcategory.equalsIgnoreCase(actualcategory))
					flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnAnyItem <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	public SearchResultsPage clickOnSearchButtonDocumentDisplay() throws Exception {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage_freeword");
			// ((JavascriptExecutor)
			// driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(1000);
			return new SearchResultsPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickOnSearchButtonDocumentDisplay <br>" + displayErrorMessage(exc));
			return null;
		}

	}

	public boolean searchPageDisplayed() throws Exception {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage"))
					.isDisplayed();
			Thread.sleep(8000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : searchPageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}

		return true;
	}

	// Methods Added by Swagatika

	public boolean searchPageLeftPaneValdiation() throws Exception {
		try {

			// verifying whether advance search and tree of contents Facets are
			// displayed or not
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancesearchfacetinsearchpage"))
					.isDisplayed();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contenttreeonsearchpage"))
					.isDisplayed();

			// verifying inside links for Tree of Contents are displayed or not
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".collapsetolevel1"))
					.isDisplayed();
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandlevel2"))
					.isDisplayed();
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".showonlycurrentversion"))
					.isEnabled();

			switch (BaseTest.productUnderTest) {
			case "chparg":
				// verifying inside links for advance search are displayed or
				// not
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"))
						.isDisplayed();
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"))
						.isDisplayed();
				elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchjurisprudence"))
						.isDisplayed();
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest
						+ ".advancesearchopinionsandopininonsoftreasurylink")).isDisplayed();

				break;
			case "chpmex":
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"))
						.isDisplayed();
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"))
						.isDisplayed();
				elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchjurisprudence"))
						.isDisplayed();
			case "chppy":
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"))
						.isDisplayed();
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"))
						.isDisplayed();
				elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchjurisprudence"))
						.isDisplayed();

				break;
			case "chppe":
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"))
						.isDisplayed();
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"))
						.isDisplayed();
				elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchjurisprudence"))
						.isDisplayed();

				break;
			case "chpchile":
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"))
						.isDisplayed();
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchdoctrine"))
						.isDisplayed();
				break;
			case "chpbr":
				// Note: Not all users have all links enabled.
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchlegislation"))
						.isDisplayed();
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchroutes"))
						.isDisplayed();
				// elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".advancedsearchtables")).isDisplayed();
				// elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".advancedsearcharticles")).isDisplayed();
				// elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".advancedsearchcomments")).isDisplayed();
				// elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".advancedsearchadministrativedecisions")).isDisplayed();
				// elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".advancedsearchsummaries")).isDisplayed();
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchrics"))
						.isDisplayed();
				// elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".advancedsearchlaugh")).isDisplayed();
				elementhandler
						.getElement(PropertiesRepository
								.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advancedsearchripi"))
						.isDisplayed();
				break;
			}
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : searchPageLeftPaneValdiation <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;

	}
	
	public boolean isErrorPageDisplayedAgain() {
		boolean flag=false;
		try {
			return driver.findElement(By.xpath("//*[text()='Se ha producido un error, por favor intente más tarde.']")).isDisplayed();
		}		
		catch (Exception exc) {
			flag = false;
		}
		return flag;
	}

	public boolean searchPageMiddlePaneValdiation() {
		try {
			switch (BaseTest.productUnderTest) {
			case "chparg":
				return elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonsearchpage"))
						.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".thematicradiobtn_searchbutton"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage"))
								.isDisplayed();
			case "chpmex":
				return elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonsearchpage"))
						.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage"))
								.isDisplayed();
			case "chpbr":
				return elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
						.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository
										.getString("com.trgr.maf." + BaseTest.productUnderTest + ".optionsinradiobtns"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository
										.getString("com.trgr.maf." + BaseTest.productUnderTest + ".optionscontenttype"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository
										.getString("com.trgr.maf." + BaseTest.productUnderTest + ".optionsscope"))
								.isDisplayed();
			case "chppe":
				return elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonsearchpage"))
						.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage"))
								.isDisplayed();
			case "chpchile":
				return elementhandler
						.getElement(PropertiesRepository.getString(
								"com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonsearchpage"))
						.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".citaonlineonsearchpage"))
								.isDisplayed()

						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage"))
								.isDisplayed()
						&& elementhandler
								.getElement(PropertiesRepository.getString(
										"com.trgr.maf." + BaseTest.productUnderTest + ".clearbtnonsearchpage"))
								.isDisplayed();

			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : searchPageMiddlePaneValdiation <br>" + displayErrorMessage(exc));
			return false;

		}
		return false;

	}

	public boolean verifyThematicAreaDropdownsOnSearchPage() {
		boolean flag = false;
		try {
			String Expected_Thematic_Area_Dropdown[] = { "Todas", "Fiscal", "Laboral y Seguridad Social",
					"Contabilidad, Auditoria y Administracion", "Sociedades, Concursos y Quiebras" };
			List<String> expitems = new ArrayList<String>();
			for (String s : Expected_Thematic_Area_Dropdown)
				expitems.add(s);

			List<WebElement> DropdownValues = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Thematic_Area_DropdownOptions"));
			if (expitems.size() == DropdownValues.size()) {
				for (int i = 0; i < DropdownValues.size(); i++) {
					String actualitem = DropdownValues.get(i).getText();
					if (expitems.contains(actualitem)) {
						flag = true;
					} else {
						//System.out.println("Thematic dropdown values are not verified");
						flag = false;
					}

				}

			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyThematicAreaDropdownsOnSearchPage <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	// ===============================

	public boolean navigateToJurisprudenceSearchPage() {
		boolean flag = false;
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudenceadvlink"));
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudencesearchpage"))
					.isDisplayed();
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : navigateToJurisprudenceSearchPage <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean verifyAdvancedSearchWidgetContent() {
		boolean flag = false;
		try {
			String[] expected = { "Legislación", "Doctrina", "Jurisprudencia ", "Dictámenes y Opiniones del Fisco",
					"Actividades", "Contratos" };
			List<String> expitems = new ArrayList<String>();
			for (String s : expected)
				expitems.add(s);
			try {
				List<WebElement> contentlist = elementhandler.findElements(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".advsearchwidgetcontent"));
				if (expitems.size() == contentlist.size()) {
					for (int i = 0; i < expected.length; i++) {
						String actualitem = contentlist.get(i).getText();
						if (expitems.contains(actualitem))
							flag = true;
					}

				}
			} catch (Exception e) {
				flag = false;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyAdvancedSearchWidgetContent <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean verifyJurisprudenceSearchFieldsDispalyed() {
		boolean flag = false;
		try {
			if (elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".courtfield"))
					.isDisplayed()
					&& elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".dateofjudgement"))
							.isDisplayed()) {
				flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyJurisprudenceSearchFieldsDispalyed <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean verifyTreeContentLinkDispalyed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".treeofcontentlink"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyAdvancedSearchWidgetContent <br>" + displayErrorMessage(exc));
			return false;
		}

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

	public boolean isTheSuggestionsDropdownDisplayed() {
		try {
			/*WebDriverFactory
			.waitForElementUsingWebElement(driver,
					elementhandler.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdown")),
					20);*/
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdown"))
					.isDisplayed()
					|| elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thdropdown"))
							.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isTheSuggestionsDropdownDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}

	}

	public boolean isSearchStringhighlightedOnCombo(String searchString) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".dropdownresulthighlight");
			if (elementhandler.getElement(locator).isEnabled()) {
				String text = elementhandler.getElement(locator).getText().toLowerCase();
				flag = text.contains(searchString.toLowerCase()) || searchString.toLowerCase().contains(text);
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSearchStringhighlightedOnCombo <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	public boolean isTitlePresentInSearchSuggestions(String title) {
		boolean titleFound = false;
		try {
			Thread.sleep(2000);
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".dropdownresulthighlight");
			List<WebElement> titlesList = elementhandler.findElements(selector);
			for (int row = 0; row < titlesList.size(); row++) {
				titleFound = titlesList.get(row).getText().trim().equalsIgnoreCase(title.trim());
				if (titleFound)
					break;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isTitlePresentInSearchSuggestions <br>" + displayErrorMessage(exc));
		}
		return titleFound;
	}

	public void ScrollToGivenSearchString(String searchData) {
		searchData = searchData.split("\\s")[0];
		try {
			WebElement baseList = elementhandler.findElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".scrollvalue"));
			List<WebElement> suggestionList = baseList.findElements(By.tagName("div"));
			java.util.Iterator<WebElement> iterator = suggestionList.iterator();

			int iteratorCount = 0;
			while (iterator.hasNext()) {
				WebElement value = iterator.next();
				iteratorCount++;
				if (iteratorCount == 10) // instead of scrolling through the
											// entire list trying to restrict
											// the count and click on the 10th
											// item.
				{
					if (value.isDisplayed()) {
						value.click();
						break;
					} else {
						iteratorCount--;
					}
					break;
				} else if (value.getText().toUpperCase().contains(searchData.toUpperCase())) {
					value.click();
					break;
				}

			}

			Thread.sleep(1000);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ScrollToGivenSearchString <br>" + displayErrorMessage(ex));

		}

	}

	public int getResultDocCountForFstSearchString() {
		try {
			return Integer.parseInt(elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultcountfrstset"))
					.getText().split("\\s")[0]);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Jurisprudence Page <br>" + displayErrorMessage(ex));
			return 0;
		}
	}

	public boolean isResultCountDisplayedForFstSearchString() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".resultcountfrstset"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isResultCountDisplayedForFstSearchString <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void enterSecondThematicSearchString(String secondSearchString) {
		try {
			WebDriverFactory
					.waitForElementUsingWebElement(driver,
							elementhandler.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sndthematicsearchbox")),
							90);
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sndthematicsearchbox"))
					.sendKeys(secondSearchString);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterThematicSearchOnQuickSearch <br>" + displayErrorMessage(exc));
		}

	}

	public void enterThirdThematicSearchString(String thirdSearchString) {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thirdthematicsearchboxonsearchpage")),
					90);
			elementhandler
					.getElement(PropertiesRepository.getString(
							"com.trgr.maf." + BaseTest.productUnderTest + ".thirdthematicsearchboxonsearchpage"))
					.sendKeys(thirdSearchString);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterThirdThematicSearchString <br>" + displayErrorMessage(exc));
		}

	}

	public boolean isSecondSearchTextBoxDisplayed() {
		try {
			Thread.sleep(1000);
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".sndthematicsearchbox"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isSecondSearchTextBoxDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	public boolean isThirdSearchTextBoxDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thrdthematicsearchbox"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isThirdSearchTextBoxDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean OneThematicSearchTxtBoxIsRemainingUponClear() {
		boolean first = false;
		try {

			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thematicsearchboxonsearchpage");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			
			first = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(locator));
			boolean second = false, third = false;
			if (first) {
				try {
					locator = PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".sndthematicsearchbox");
					second = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(locator));
					locator = PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".thrdthematicsearchbox");
					third = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(locator));
				} catch (Exception e) {
					// Expected
				} finally {
					first = first && !second && !third;
				}
			}

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : OneThematicSearchTxtBoxIsRemainingUponClear <br>" + displayErrorMessage(ex));
		}
		return first;
	}

	public void author(String author) {
		try {
			elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".author"))
					.sendKeys(author);
			;

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Clicking Last Reform <br>" + displayErrorMessage(ex));

		}

	}

	public boolean isDefaultContentTreeDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".contenttreeonsearchpage"))
					.isDisplayed();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isDefaultContentTreeDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void ClickOnExpandLevel2Link() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandlevel2");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(3000);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnExpandLevel2Link <br>" + displayErrorMessage(ex));

		}

	}

	public boolean isContentTreeExpanded() {
		try {
			Thread.sleep(6000);
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

	public boolean isContentTreeCollapsed() {
		try {
			Thread.sleep(6000);
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".treenodescollapsed"))
					.isDisplayed();

		} catch (Exception ex) {
			return false;
		}
	}

	public void ClickOnCollapseLevel1Link() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".collapsetolevel1"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickOnExpandLevel2Link <br>" + displayErrorMessage(ex));

		}

	}

	public void clickOnLawOnContentTree() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".leylink"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnLawOnContentTree <br>" + displayErrorMessage(ex));

		}

	}

	public boolean isGivenNodeDisplayed(String nodeName) {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandedlink"))
					.getText().contains(nodeName);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isContentTreeExpanded <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void clickOnGivenLinkTextOnContentTree(String linkTextToClick) {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".linktext2click")
							+ linkTextToClick);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnLawOnContentTree <br>" + displayErrorMessage(ex));

		}

	}

	public void selectGivenValueFromThematicDropdown(String valueOfThematicCombo) {
		try {
			elementhandler.selectByVisibleText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown"),
					valueOfThematicCombo);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : selectGivenValueFromThematicDropdown <br>" + displayErrorMessage(ex));

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
					List<WebElement> listOfCategoriesLink = listOfCategories.get(rowNum).findElements(By.tagName("i"));
					if (listOfCategoriesLink.size() > 0) {
						String text = listOfCategoriesLink.get(0).getText();
						System.out.println(text);
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
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
								listOfCategoriesLink.get(0));
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
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",element);
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
	public DocumentDisplayPage clickDocumentInSubContentTree(WebElement element, String itemName) {
		DocumentDisplayPage docDisplayPage = null;
		try {
			List<WebElement> listOfCategories = element.findElements(By.tagName("div"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getAttribute("class").equals("docName")) {
						WebElement docLink = listOfCategories.get(rowNum).findElement(By.tagName("a"));
						String linkText = docLink.getText();
						//System.out.println(linkText.trim());
						//System.out.println(itemName.trim());
						if (linkText.trim().equals(itemName.trim())) {
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", docLink);
							docLink.click();
							Thread.sleep(7000);
							docDisplayPage = new DocumentDisplayPage(driver);
							docDisplayPage.isDocumentDisplayed();
							break;
						}
					}
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickDocumentInSubContentTree <br>" + displayErrorMessage(exc));
		}
		return docDisplayPage;
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
					Thread.sleep(3000);
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
					if (listOfCategories.get(rowNum).getText().equals(itemName)) {
						WebElement expandImg = listOfCategories.get(rowNum).findElement(By.tagName("span"));
						if (expandImg.getAttribute("class").equals("collapsed")) {
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", expandImg);
							expandImg.click();
							Thread.sleep(4000);
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
	 * This method clicks on the check box of the item in any level of content tree
	 */
	public void clickCheckBoxOfItemInSubContentTree(WebElement element, String itemName) {

		try {
			List<WebElement> listOfCategories = element.findElements(By.tagName("li"));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getText().equals(itemName)) {
						List<WebElement> expandImg = listOfCategories.get(rowNum).findElements(By.tagName("input"));
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", expandImg.get(1));
						expandImg.get(1).click();
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
			Thread.sleep(2000);
			List<WebElement> listOfCategories = element.findElements(By.tagName("li"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",listOfCategories.get(0));
			for (int rowNum = 0; rowNum < listOfCategories.size(); rowNum++) {
				if (WebDriverFactory.isDisplayed(driver, listOfCategories.get(rowNum))) {
					if (listOfCategories.get(rowNum).getText().trim().equals(itemName.trim())) {
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

	public void selectSearchByTerm() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbytermonrouterios"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectSearchByTerm <br>" + displayErrorMessage(ex));
		}
	}

	public void clickPlusFirst() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".fiscallink"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : Clicking Last Reform <br>" + displayErrorMessage(ex));

		}

	}

	public LegislationPage openLegislationPage() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".legislation"));
			return new LegislationPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : openLegislationPage <br>" + displayErrorMessage(ex));
			return null;
		}

	}

	public void clickOnFirstNodeOnContentTree() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstnodeoncontenttree"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnFirstNodeOnContentTree <br>" + displayErrorMessage(ex));

		}

	}
	
	public void clickOnFirstNodeofTOC() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstnodeoftoc"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnFirstNodeofTOC <br>" + displayErrorMessage(ex));

		}

	}
	

	public String getExpandedNodeName() {

		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".firstnodeoncontenttree"))
					.getText();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : getExpandedNodeName <br>" + displayErrorMessage(ex));
			return "";

		}
	}

	public void selectNaturalLanguageOption() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".naturallang"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectNaturalLanguageOption <br>" + displayErrorMessage(ex));

		}
	}

	public void enterNaturalLanguageSearchOnSearchPage(String searchText) {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".naturallangboxonsearchpage"))
					.sendKeys(searchText);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : enterNaturalLanguageSearchOnSearchPage <br>" + displayErrorMessage(ex));

		}

	}

	public boolean searchReturnedZeroResults() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".errorblock"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : searchReturnedZeroResults <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public void enterAbbrevationText(String abbrevationText) {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".abbrevation_field"))
					.sendKeys(abbrevationText);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterAbbrevationText <br>" + displayErrorMessage(ex));
		}

	}

	/*
	 * Enters the article number or item number on the
	 */
	public void enterArticleNumber(String articleNum) {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".abbrevation_widget_article"))
					.sendKeys(articleNum);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterArticleNumber <br>" + displayErrorMessage(ex));

		}

	}

	/*
	 * This method clicks on the search button on the abbrevation section
	 * results from the abbrevation search directly returns the document display
	 * page
	 */
	public DocumentDisplayPage clickOnAbbrevationSearch() {
		try {
			Thread.sleep(500);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".abbrevation_widget_search"));
			return new DocumentDisplayPage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnAbbrevationSearch <br>" + displayErrorMessage(ex));
			return null;
		}

	}

	public String getMarginalText() {
		try {
			Thread.sleep(500);
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".marginalText"))
					.getText();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterArticleNumber <br>" + displayErrorMessage(ex));
			return "";

		}
	}

	public String buildDocumentViewElement(String marginalText) {
		try {
			Thread.sleep(500);
			if (marginalText.contains("\\")) {
				return marginalText = marginalText.replaceAll("\\\\", "-");
			}
			return marginalText;

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterArticleNumber <br>" + displayErrorMessage(ex));
			return "";

		}
	}

	// select drop down value

	public void selectThematicArea(String value) {
		try {
			String selctor = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown");
			/*
			 * elementhandler.clickElement(PropertiesRepository.getString(
			 * "com.trgr.maf." + BaseTest.productUnderTest + ".marginalText"));
			 * elementhandler.getDropdown(selctor);
			 */
			elementhandler.selectByVisibleText(selctor, value);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectThematicArea <br>" + displayErrorMessage(ex));
		}
	}

	public boolean isClearBelowThematicTextVisible() {
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbuttonbelowthematicsearch"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isClearBelowThematicTextVisible <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean isModifySearchRetainsSearchStrings() {
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
					.getAttribute("value").length() > 0
					|| elementhandler
							.getElement(PropertiesRepository.getString(
									"com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
							.getAttribute("value").length() > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isModifySearchRetainsSearchStrings <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean isNewSearchEmptiesSearchStrings() {
		try {
			String freeword = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage");
			String value = elementhandler.getElement(freeword).getAttribute("value");
			if ((value.length()) == 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isNewSearchEmptiesSearchStrings <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	public boolean isLastLinkDisplayedInContentTreeEquals(String linkname) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".contenttree_alllinks");
			List<WebElement> allnodes = elementhandler.findElements(locator);
			int size = allnodes.size();
			String lastelement = allnodes.get(size - 1).getText();
			if (lastelement.contentEquals(linkname)) {
				flag = true;
			}

		} catch (Exception ex) {
			return false;
		}
		return flag;
	}

	public HomePage clickHomeTab() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".homepagetab");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					elementhandler.findElement(locator));
			elementhandler.clickElement(locator);
			return new HomePage(driver);
		} catch (Exception ex) {
			return null;
		}

	}

	/*
	 * checks if AdvancedSearch Widget is displayed in searchpage returns true
	 * on success
	 */
	public boolean isAdvancedSearchWidgetDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".advancesearchfacetinsearchpage"))
					.isDisplayed();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isAdvancedSearchWidgetDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * checks if 'Expand Level 2' link is displayed returns true on success
	 */
	public boolean isExpandLevel2LinkDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".expandlevel2"))
					.isDisplayed();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isExpandLevel2LinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * checks if 'Collapse to Level 1' link is displayed returns true on success
	 */
	public boolean isCollapseToLevel1LinkDisplayed() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".collapsetolevel1"))
					.isDisplayed();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isCollapseToLevel1LinkDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * checks if 'Show Current version' radio button is displayed returns true
	 * on success
	 */
	public boolean isShowCurrentVersionRadioDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".showonlycurrentversion"))
					.isDisplayed();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isShowCurrentVersionRadioDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * checks if 'Show All version' radio button is displayed returns true on
	 * success
	 */
	public boolean isShowAllVersionRadioDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".showallversion"))
					.isDisplayed();

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isShowAllVersionRadioDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * checks if link present in AdvancedSearch Widget returns true on success
	 */
	public boolean isLinkPresentInAdvancedSearchWidget(String expectedLink) {
		boolean itemFound = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".advancedsearchlinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			for (int row = 0; row < alllinks.size(); row++) {
				String autualLink = alllinks.get(row).getText().trim();
				itemFound = autualLink.equals(expectedLink);
				if (itemFound)
					break;
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isLinkPresentInAdvancedSearchWidget <br>" + displayErrorMessage(exc));
			return false;
		}
		return itemFound;
	}

	/*
	 * checks if 'Any links' present in AdvancedSearch Widget returns true on
	 * success
	 */
	public boolean isLinkPresentInAdvancedSearchWidget() {
		boolean itemFound = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".advancedsearchlinks");
			List<WebElement> alllinks = elementhandler.findElements(locator);
			itemFound = alllinks.size() > 0;

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isLinkPresentInAdvancedSearchWidget <br>" + displayErrorMessage(exc));
			return false;
		}
		return itemFound;
	}

	/*
	 * This method checks if the current tab is Search or not returns true on
	 * success
	 */
	public boolean isCurrentTabEqualsSearch() throws Exception {
		boolean flag = false;
		try {
			WebElement currentTab = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".curtab"));
			flag = currentTab.getAttribute("id").equals("search");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCurrentTabEqualsSearch <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks if the current tab is having Orange colour returns
	 * true on success
	 */
	public boolean isCurrentTabColorEqualsOrange() throws Exception {
		boolean flag = false;
		try {
			WebElement searchatb = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".curtab"));
			String color = searchatb.getCssValue("color");
			String hex = Color.fromString(color).asHex();
			if (hex.contains("#f89f29"))
				flag = true;

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isCurrentTabColorEqualsOrange <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public NewsPage ClickonNewsTab() throws IllegalArgumentException, IOException {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newstablink")), 30);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newstablink"));
			return new NewsPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ClickonNewsTab <br>" + displayErrorMessage(exc));
			return null;
		}
	}


	/*
	 * This method is used to verify freeword field is displayed	
	 */
	public boolean isFreewordFieldDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage"))
					.isDisplayed();
		} catch (Exception ex) {
			return false;

		}
	}

	public void clickThematicSearchRadioButton() {
		try {
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ThematicSearchRadiobuttonsearchpage"));
			Thread.sleep(1000);
		} catch (Exception exc) {
			//extentLogger.log(LogStatus.INFO,
				//	"Error in : clickThematicSearchRadioButton <br>" + displayErrorMessage(exc));
		}
	}

	// this method is to select the search button when thematic radio button is
	// selected
	public SearchResultsPage clickonSearchwhenThematicisSelected() throws Exception {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thematicradiobtn_searchbutton");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickonSearchwhenThematicisSelected <br>" + displayErrorMessage(exc));
			return null;
		}
		return new SearchResultsPage(driver);
	}

	// this method is to select the search button when Natural Language radio
	// button is selected
	public SearchResultsPage clickonSearchwhenNatLanguageisSelected() throws Exception {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".natlang_searchbutton");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickonSearchwhenThematicisSelected <br>" + displayErrorMessage(exc));
			return null;
		}
		return new SearchResultsPage(driver);
	}
	
	/*
	 * This method selects the first option on the dropdown list
	 */
	public void selectFirstTitleOnSearchSuggestions(int thesaurusSuggestionIndex)
	{
		try
		{
			driver.findElement(By.xpath("//div[@id='textoInput1_1']")).click();
		
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : selectFirstTitleOnSearchSuggestions <br>" + displayErrorMessage(exc));
		}
		
	}
	
	public void selectFirstTilteonSuggestionPage() {
		try {
			Thread.sleep(2000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thematicfirstdropdownserachpage");
			elementhandler.clickElement(locator);
		}
		catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : selectFirstTilteonSuggestionPage <br>" + displayErrorMessage(exc));
		}
		
	}
	
	/*
	 * This method selects the first option on the dropdown list in chpmex
	 */
	public void selectFirstTitleOnSearchSuggestionsInMex(int thesaurusSuggestionIndex)
	{
		try
		{
			driver.findElement(By.xpath("//div[@id='termSuggestions']//div[@id='textoInput" + thesaurusSuggestionIndex +"_0']")).click();
		
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : selectFirstTitleOnSearchSuggestions <br>" + displayErrorMessage(exc));
		}
		
	}

	public boolean selectTitleInSearchSuggestions(String title) {
		boolean selected = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".searchsuggestionslist");
			
			WebElement elements = elementhandler.findElement(selector);
			 List<WebElement> titlesList = elements.findElements(By.tagName("span"));
		 
			for (int row = 0; row < titlesList.size(); row++)
			{
				String titleText =titlesList.get(row).getText();
				if (titleText.trim().contains(title)) {
					titlesList.get(row).click();
					Thread.sleep(6000);
					selected = true;
					break;
				}
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : selectTitleInSearchSuggestions <br>" + displayErrorMessage(exc));
			selected = false;
		}
		return selected;

	}
	
	public boolean selectTitleInTheamaticSearchSuggestions(String title) {
		boolean selected = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".dropdownresulthighlight");
			List<WebElement> titlesList = elementhandler.findElements(selector);
			for (int row = 0; row < titlesList.size(); row++) {
				String text = titlesList.get(row).getText();
				if (text.trim().contains(title)) {
					titlesList.get(row).click();
					Thread.sleep(6000);
					selected = true;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : selectTitleInSearchSuggestions <br>" + displayErrorMessage(exc));
			selected = false;
		}
		return selected;

	}

	/*public boolean selectSecondTitleInSearchSuggestions(String title) {
		boolean selected = false;
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".searchsuggestionslist");
			List<WebElement> titlesList = elementhandler.findElements(selector);
			for (int row = 0; row < titlesList.size(); row++) {
				String text = titlesList.get(row).getText();
				if (text.trim().contains(title)) {
					titlesList.get(row).click();
					Thread.sleep(2000);
					selected = true;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : selectSecondTitleInSearchSuggestions <br>" + displayErrorMessage(exc));
			selected = false;
		}
		return selected;

	}*/

	public String getFreewordTextOnSearchPage() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonsearchpage");
			String text = elementhandler.getElement(selector).getAttribute("value");
			return text;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getFreewordTextOnHomePage <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public String getThematicTextOnSearchPage() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicsearchboxonsearchpage");
			String text = elementhandler.getElement(selector).getAttribute("value");
			return text;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getThematicTextOnHomePage <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	// this method is to select the search button when thematic radio button is
	// selected
	public void clickonClearwhenThematicisSelected() throws Exception {
		try {
			Thread.sleep(3000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".thematicradiobtn_clearbutton");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickonClearwhenThematicisSelected <br>" + displayErrorMessage(exc));
		}
	}

	
	public JurisprudencePage clickjurisprudencia() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".jurisprudencia"));
			return new JurisprudencePage(driver);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickjurisprudencia <br>" + displayErrorMessage(ex));
			return null;
		}

	}

	public void clickonCreateShortcutforSpecificNode(String nodename) {
		try {
			String locator = "xpath=//a[text()='" + nodename + "']/..//img[@title='Crear acceso directo']";
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					elementhandler.findElement(locator));
			elementhandler.clickElement(locator);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : clickonCreateShortcutforSpecificNode <br>" + displayErrorMessage(ex));
		}
	}

	public void enterShortcutLinkName(String name) {
		try {
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".shortcutname"), name);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : enterShortcutLinkName <br>" + displayErrorMessage(ex));
		}
	}

	public void clickSaveShortcut() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".shortcutsavebutton"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveShortcut <br>" + displayErrorMessage(ex));
		}
	}

	public void acceptSaveShortcut() {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".saveacceptbutton"));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : acceptSaveShortcut <br>" + displayErrorMessage(ex));
		}
	}
	
	/*
	 * 
	 */
	public boolean isThematicRadioButtonDisplayed() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ThematicSearchRadiobuttonsearchpage"))
					.isDisplayed();
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : isThematicRadioButtonDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}
	
	//Description:"this method is used to check whether the searchkeyword is present in the search textbox after clicking
	//on modify search in search results page"
	//this is created specifically for chpmex region
	public boolean isModifySearchRetainsSearchStringsInMex() {
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".enterTextInSearchBox"))
					.getAttribute("value").length() > 0
					|| elementhandler
							.getElement(PropertiesRepository.getString(
									"com.trgr.maf." + BaseTest.productUnderTest + ".enterTextInSearchBox"))
							.getAttribute("value").length() > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isModifySearchRetainsSearchStrings <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	

}
