package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.handlers.ElementHandler;


public class BasePage extends BaseTest
{
	protected static WebDriver driver;
	protected static ElementHandler elementhandler;
	protected static boolean AcceptTermsandConditionflag=true;
	
	
	public BasePage(WebDriver driver) throws IOException, IllegalArgumentException
	{
		super();
		BasePage.driver = driver;
		elementhandler=new ElementHandler(driver);	
	}
	
	/*
	 * This method is generic and takes the link text and tries to click on the link if present on the page
	 * Returns true / false based on the element display
	 */
	public void clickOnGivenLinkByLinkText(String linkToClick, boolean isCompleteLinkText) 
	{
		try {
			if(isCompleteLinkText)
			{
				elementhandler.getElement("linkText=" + linkToClick + "").click();
			}
			
			else
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement("linkText=" + linkToClick + ""));
				elementhandler.getElement("partialLinkText=" + linkToClick + "").click();
			}
			
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "clickOnGivenLinkIfPresent" + linkToClick );
			
		}
		
	}
	/*
	 * This method is generic and takes the link text and tries to click on the link if present on the page
	 * Returns true / false based on the element display
	 */
	public void verifyEpocaFilterElementPresent(String linkToClick, boolean isCompleteLinkText)
	{
		try {
		if(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchfileterepoca")).isDisplayed())
		{
		 WebElement link=elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchfileterepoca"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",link);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchfileterepoca")).click();
		}
		Thread.sleep(2000);
		if(isCompleteLinkText) 
		{
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement("linkText=" + linkToClick + ""));
			elementhandler.getElement("partialLinkText=" + linkToClick + "").click();
		}
		else
		{
			elementhandler.getElement("linkText=" + linkToClick + "").click();
		}
		
		}
		catch (Exception ex) {
				extentLogger.log(LogStatus.INFO, "verifyEpocaFilterElementPresent" + displayErrorMessage(ex) );
				
			}
	}
	
	public void clickOnLegislationRelations() 
	{
		try {
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".legislationrelation"));
			
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnLegislationRelations <br>" + displayErrorMessage(ex));
			
		}
		
	}
	
	/*
	 * This method is generic and takes the link text to check if the link is present on the page
	 * Returns true / false based on the element display
	 */
	public boolean isGivenLinkDisplayed(String linkToClick) 
	{
		try {
			return elementhandler.getElement("linktext=" + linkToClick + "").isDisplayed();
			
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "isGivenLinkDisplayed" + linkToClick );
			return false;
		}
		
	}
	
	/*
	 * This memthod verifies if the given tool tip is displayed upon hovering on the link / text
	 * Returns true / false based on the element display
	 */
	public boolean isGivenToolTipDisplayed(String expectedtooltipVal, String expectedLink)
	{
		try {
			WebElement locator = elementhandler.getElement("linkText=" + expectedLink + "");
			
			new Actions(driver).moveToElement(locator).build().perform();
			
			return driver.findElement(By.xpath("//a[contains(text(),'"+expectedLink+"')]//following-sibling::div[@class='relationshipHover']")).getText().contains(expectedtooltipVal);
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isGivenToolTipDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
		
	}
	
	public boolean IsPopUpWindowPresent()
	{
		 try {
		        driver.switchTo().alert();
		        return true;
		    } // try
	    catch (Exception e) {
	        return false;
	    } // catch
		
	}
	
	
	
	public void clickOnAlertPopUP() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 200);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			// exception handling
		}
	}

	
	public boolean ValidateandclickOnAlertPopUP() throws Exception {
		try {
			
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return true;
		} catch (Exception e) {
				return false;
		}
	}
	
	public void cancelPrintViewInBrowser(WebDriver driver){
		try{
			// Choosing the second window which is the print dialog.
			// Switching to opened window of print dialog.
			Thread.sleep(9000);
			driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
			// Runs javascript code for cancelling print operation.
			// This code only executes for Chrome browsers.
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("document.getElementsByClassName('cancel')[0].click();");
		
		}catch(Exception exc){
			//Fail
			
		}finally{
			try{
				//Switches to main window after print dialog operation.
				driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
				Thread.sleep(3000);
			}catch(Exception ex){
			}
		}
	}
	
	/*
	 * This method clicks on the search button by scrolling to the element whereever it is on the page
	 * Returns the Handle to the Search Results Page
	 * If the element is not present, catch block will handle the exception and log the screen shot on the extent report
	 */
	public SearchResultsPage clickOnSearch() throws Exception {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonsearchpage");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(1000); 
			return new SearchResultsPage(driver);
		} catch (Exception exc) {
			
			//Search Results page is not displayed if there is an error / no search results message displayed
			if(!elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".errormesg")).isDisplayed())
			{
			   extentLogger.log(LogStatus.INFO, "Error in : clickOnSearch <br>" + displayErrorMessage(exc));
				
			}
			
		}
		 return null;

	}
	
	/*
	 * This method checks for the error message block on any page and compares the text with the given string
	 * Returns True if the value macthes; Returns False if the comparision fails.
	 * If the element is not present, catch block will handle the exception and log the screen shot on the extent report
	 */
	public boolean isErrorMessageDisplayed(String errorMsg) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".errorblockAlert");
			String errtext = elementhandler.getElement(locator).getText();
			if (errtext.contains(errorMsg))
				flag = true;

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isErrorMessageDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
		return flag;
	}
	
	/*
	 * This method switches the driver control to Child tab
	 * Accepts the tab number as argument
	 */
	public void switchToChildTab(int tabNumber)
	{
		try{
			if(driver.getWindowHandles().size()>tabNumber)
				driver.switchTo().window(driver.getWindowHandles().toArray()[tabNumber].toString());
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : switchToChildTab <br>" + displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method switches the driver control to Parent tab
	 */
	public void switchToParentTab()
	{
		try{
			driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : switchToParentTab <br>" + displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method closes all tabs other than Parent tab
	 */
	public void closeAllChildTabs()
	{
		try{
			Set <String> windows = driver.getWindowHandles();
			Iterator<String> it = windows.iterator();
			String parent = it.next();
			while(it.hasNext()){
				driver.switchTo().window(it.next());
				driver.close();
			}
			driver.switchTo().window(parent);
		} catch (Exception exc) {
			driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
			extentLogger.log(LogStatus.INFO, "Error in : closeAllChildTabs <br>" + displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method drags and drops the source element to target element
	 */
	public void dragAndDropWebElement(WebElement source,WebElement target){
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(source)
		   .moveToElement(target)
		   .release(target)
		   .build();
		dragAndDrop.perform();
	}
	
	
	
	//Implementation of the new functionality
	
		//Verify whether the Global Search title is displayed
		//index = 1 for homepage and searchpage
		public boolean isGlobalSearchTitleDisplayed(int index)

		{
			try {
				
				String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".globalSearchTitle");
				locator = String.format(locator, index);
				WebElement element=elementhandler.getElement(locator);
				return element.isDisplayed();
				
				
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : isGlobalSearchTitleDisplayed <br>" + displayErrorMessage(exc));
				return false;
			}
		}

		//click on 'AskCheckpoint' link
		//this function is applicable for home page and searchpage
		public void clickAskCheckpointLink()
		{
			//SoftAssert assert = new SoftAssert();
			try {
				String locator = "xpath=//a[contains(text(),'Pregúntale a Checkpoint ')]";
				WebElement element = elementhandler.getElement(locator);
				boolean isDisplayed=element.isDisplayed();
				if(isDisplayed)
				{
					element.click();
				}
				else {
					//assert.assertTrue(isDisplayed, "Ask checkpoint link is not displayed");
					extentLogger.log(LogStatus.INFO, "Error in : clickAskCheckpointLink" );
				}
			}
			
			catch (Exception ex) {
				extentLogger.log(LogStatus.INFO,
						"Error in : clickAskCheckpointLink <br>" + displayErrorMessage(ex));
				
			}

		}
		
		
		//verify the tool tip message of the help icon
		//this function is applicable for home page and searchpage
		public boolean helpIconText()
		{
			
			String expectedText = "La búsqueda inteligente es un modo de búsqueda amigable que permite realizar sus consultas mediante preguntas o palabras clave. ";
			try {
				String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helpIcon");
				WebElement element = elementhandler.getElement(locator);
				Actions action = new Actions(driver);
				action.moveToElement(element).build().perform();
				Thread.sleep(200);
				String text = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".helpIcon")).getAttribute("title");
				 if(text.contains(expectedText))
				 {
					 return true;
				 }
			}
			
			catch (Exception ex) {
				extentLogger.log(LogStatus.INFO,
						"Error in : helpIconText <br>" + displayErrorMessage(ex));
				
			}
			return false;

		}
		
		/*
		 * This method verifies whether all options are present in Thematic Area drop down
		 * returns true on success, false on failure
		 */
		//this function is applicable for home page and searchpage
		
		public boolean isThematicAreaDropdownOptionsAvailableInMex() {
			boolean flag = false;
			try {


				String exptdropdowns= "";
				exptdropdowns ="Todas,Fiscal,Laboral,Seguridad Social,Contabilidad y Auditoría,Comercio Exterior,Administrativo,Amparo,Civil,Constitucional,Corporativo,Financiero,Mercantil,Penal";
				String expDropdownsArray[]=exptdropdowns.split(",");
								

				List<WebElement> alltabs = elementhandler.findElements(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicArea_DropdownOptions"));
				if (expDropdownsArray.length == alltabs.size()) {
					for (int i = 0; i < alltabs.size(); i++) {
						String actdrpdown = alltabs.get(i).getText();
						for (int j = 0; j < (expDropdownsArray.length); j++) {
							String actdrpdown1 = expDropdownsArray[j].trim().toString();
							if (actdrpdown1.equalsIgnoreCase(actdrpdown)) {
								flag = true;
								break;
							}
						}
					}
				}

			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO,
						"Error in : isThematicAreaDropdownOptionsAvailableInMex <br>" + displayErrorMessage(exc));
				return false;
			}
			return flag;
		}

				
		
		//this function is used to select the given option from thematicarea dropdown	
		
		public boolean thematicAreaDropdownContainsValueInMex(String expectedOption) {
			boolean flag = false;
			try {
				WebElement dropdownoption = elementhandler.findElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".thematicdropdown"));
								
				Select sel=new Select(dropdownoption);
				sel.selectByVisibleText(expectedOption);
				flag = true;

			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO,
						"Error in : thematicAreaDropdownContainsValueInMex <br>" + displayErrorMessage(exc));
				flag = false;
			}
			return flag;
		}
	
		

		/* this method is used to enter the text in global search text box */
		
		public void enterTextInSearchField(String searchtext) {
			
			try {
				Thread.sleep(5000);
				String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".enterTextInSearchBox");				
				WebElement element=elementhandler.getElement(locator);
				element.sendKeys(searchtext);
				
				 
			}catch (Exception exc) {
				extentLogger.log(LogStatus.INFO,
						"Error in : enterTextInSearchField <br>" + displayErrorMessage(exc));
				
			}
			
			
		}
	
		
     //check the placeholder text in global search textbox
     //this method is used in search page and home page
		public boolean isPlaceHolderTextDisplayed()

		{
			try {
				
				return elementhandler.findElement(
								PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".verifyPlaceHolderText"))
						.isDisplayed();
				
				
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : isPlaceHolderTextDisplayed <br>" + displayErrorMessage(exc));
				return false;
			}
		}

		 //check the placeholder text in Ask checkpoint textbox
	     //this method is used in search page and home page
		 //<Created Date :  24-Oct-2018 >   ; <author : Havya> 
			public boolean isPlaceHolderTextDisplayedForAskChp()

			{
				try {
					
					return elementhandler.findElement(
									PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".verifyPlaceHolderTextInAskChp"))
							.isDisplayed();
					
					
				} catch (Exception exc) {
					extentLogger.log(LogStatus.INFO, "Error in : isPlaceHolderTextDisplayed <br>" + displayErrorMessage(exc));
					return false;
				}
			}

			
			 //selecting the suggestions from drop down 
			//this function applicable for home page and search page
			public void selectingSuggestionsDropdownDisplayed(String sugg) {
					try {
						WebElement ele1 = driver.findElement(By.xpath("//*[@id='termSuggestions']"));
						List<WebElement> list = ele1.findElements(By.tagName("div"));
						
						for(WebElement ele:list) {
							if(ele.getText().contains(sugg)) {
								ele.click();
								break;
							}
							
						}
						
					} catch (Exception ex) {
						extentLogger.log(LogStatus.INFO,
								"Error in : selectingSuggestionsDropdownDisplayed <br>" + displayErrorMessage(ex));
						
					}

				} 
			
			
			//Validating the results for the search term
			//this function applicable for home page and search page
			//<Created Date :  25-Oct-2018 >   ; <author : Saikiran> 
			public boolean isSearchResultsDisplayed()

			{
				try {
					
					return elementhandler.findElement(
									PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".verifyNoOfResults"))
							.isDisplayed();
					
					
				} catch (Exception exc) {
					extentLogger.log(LogStatus.INFO, "Error in : isSearchResultsDisplayed <br>" + displayErrorMessage(exc));
					return false;
				}
			}
	

			
			//Description:In this function we are checking see Matches(Ver coincidencias) link is present are not for specific document
		     //<Created Date :  25-Oct-2018 >   ; <author : Roja> 
           //ex:first see match(Ver coincidencias) link  index=1,for second index=2
	  
		
			public boolean isVerCoincidenciasLinkDisplayed(int index)

			{
				try {
					
					String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".seematchlink");
					locator = String.format(locator, index);
					WebElement element=elementhandler.getElement(locator);
					return element.isDisplayed();
					
					
				} catch (Exception exc) {
					extentLogger.log(LogStatus.INFO, "Error in : isSeeMatchLinkDisplayed <br>" + displayErrorMessage(exc));
					return false;
				}

			}
			
			//Description:In this function we are click on (Ver coincidencias) see Matches link
		     //<Created Date :  26-Oct-2018 >   ; <author : Roja> 
             //ex:first see match(Ver coincidencias) link  index=1,for second index=2
		    
			public void clickOnVerCoincidenciasLink(int index)

			{
				try {
					
					String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".seematchlink");
					locator = String.format(locator, index);
					WebElement element=elementhandler.getElement(locator);
					element.click();
					
					
				} catch (Exception exc) {
					extentLogger.log(LogStatus.INFO, "Error in : clickOnSeeMatchLinkDisplayed <br>" + displayErrorMessage(exc));
					
				}
			}
			
			
			
		
			//Description:In this function verifying unit+rubrik link is present or not
		     //<Created Date :  26-Oct-2018 >   ; <author : Roja> 
           
			public boolean isUnitRubricLinkDisplayed()

			{
          try {
        	  Thread.sleep(5000);
        	  String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".unitRubricLink");
        	  Thread.sleep(5000);
        	  WebElement element=elementhandler.getElement(locator);
       
				return element.isDisplayed();
					
					
				} catch (Exception exc) {
					extentLogger.log(LogStatus.INFO, "Error in : isUnitRubricLinkDisplayed <br>" + displayErrorMessage(exc));
					return false;
				}
			}
			
			
			 //Description:"click on the unit+rubric link"
			 //<Created Date :  29-Oct-2018 >   ; <author : Havya> 
			
			public void clickOnUnitAndRubricLink(String linkname) {
				try {

					String locator = "xpath=.//a[contains(text(),'" + linkname + "')]";
					Thread.sleep(1000);
					elementhandler.findElement(locator).click();

				}

				catch (Exception exc) {
					extentLogger.log(LogStatus.INFO, "Error in : clickOnUnitAndRubricLink <br>" + displayErrorMessage(exc));
					
				}
			}
			
		

					 //Description:"verifying ver mas link is displayed or not"
					 //<Created Date :  30-Oct-2018 >   ; <author : Roja> 
					public boolean isVerMasLinkDisplayed()

					{
		          try {
		        	  String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".verMasLink");
							
		        	  WebElement element=elementhandler.getElement(locator);
		       
						return element.isDisplayed();
							
							
						} catch (Exception exc) {
							extentLogger.log(LogStatus.INFO, "Error in : isVerMasLinkDisplayed <br>" + displayErrorMessage(exc));
							return false;
						}
					}
					
					
					 //Description:"verifying document is not present alert message
					 //<Created Date :  31-Oct-2018 >   ; <author : Roja> 
					public boolean isMsgDisplayed()

					{
		          try {
		        	  String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".msgdisplayed");
							
		        	  WebElement element=elementhandler.getElement(locator);
		       
						return element.isDisplayed();
							
							
						} catch (Exception exc) {
							extentLogger.log(LogStatus.INFO, "Error in : isMsgDisplayed <br>" + displayErrorMessage(exc));
							return false;
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
					// Below method will be returned Error message
					public String getErrorAlertMessage() {
						try {
							WebDriverWait wait = new WebDriverWait(driver, 40);
							String getErrorAlertMessage = PropertiesRepository
									.getString("com.toceditor." + BaseTest.productUnderTest + ".searchmsg");

							WebElement errAlertMsg = elementhandler.findElement(getErrorAlertMessage);
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", errAlertMsg);
							
							wait.until(ExpectedConditions.visibilityOf(errAlertMsg));
							String errAlrtMsg = errAlertMsg.getText();
							return errAlrtMsg;

						} catch (Exception exc) {
							extentLogger.log(LogStatus.INFO, "Error in : getAlertMessage <br>" + displayErrorMessage(exc));
							return null;
						

					}
					}
					
					//Description:"verifying document count for each doctype(Legislation,Doctrina,...)
					 //<Created Date :  02-Nov-2018 >   ; <author : Kavitha> 
					public boolean DocTypeResultCountInSearch()
					{
						boolean flag = true;
						String locator = "xpath=//div[@class='globalResultTitle']";
						List<WebElement> docTypeList = elementhandler.findElements(locator);
						for(int i=1;i<=docTypeList.size();i++)
						{
							String loc = "xpath=(//div[@class='globalResultTitle'])[" +i+ "]//following-sibling::div[@class='result']";
							List<WebElement> list = elementhandler.findElements(loc);
							if(list.size()!=2)
							{
								flag=false;
								return flag;
								
							}
						}
						return flag;
					}
					
					//Description:"verifying error message is displayed for empty search box
					 //<Created Date :  02-Nov-2018 >   ; <author : Kavitha>
					public boolean isEmptySearchErrorMsgDisplayed()
					{
		          try {
		        	  	//SearchResultsPage searchResultsPage;
		        	  	WebDriverWait wait = new WebDriverWait(driver, 200);
		        	  	//searchResultsPage = searchpage.clickOnSearch();
		        	  	boolean waitUntil = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[@id='msjAlert']")));	
		        	  	return waitUntil;
						} catch (Exception exc) {
							extentLogger.log(LogStatus.INFO, "Error in : isEmptySearchErrorMsgDisplayed <br>" + displayErrorMessage(exc));
							return false;
						}
					}
					
					//Description:"verifying document snippet is displayed for no unit+rubric documents
					 //<Created Date :  02-Nov-2018 >   ; <author : Kavitha>
					public boolean isDocumentSnippetDisplayed()
					{
						boolean isDisplayed=false;
						String locator = "xpath=//div[@class='documentSnippet']";
						WebElement element = elementhandler.findElement(locator);
						isDisplayed=element.isDisplayed();
						return isDisplayed;
					}
					
					//Description:verifying the error message "No documents were found for your search."
					 //<Created Date :  05-Nov-2018 >   ; <author : Kavitha>
					public boolean isNoDocumentErrorDisplayed()
					{
						boolean isDisplayed = false;
						String locator = "xpath=//div[@id='errorBlock']";
						WebElement element = elementhandler.findElement(locator);
						isDisplayed = element.isDisplayed();
						return isDisplayed;
					}

}

