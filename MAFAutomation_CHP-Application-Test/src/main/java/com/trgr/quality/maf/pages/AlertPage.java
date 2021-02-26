package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class AlertPage extends BasePage
{

	public AlertPage(WebDriver driver) throws IOException,	IllegalArgumentException 
	{
		super(driver);
		WebDriverWait wait = new WebDriverWait(driver,120);
		if(BaseTest.productUnderTest.equals("chpmex")) {
			wait.until(ExpectedConditions.textToBePresentInElement(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".myalertsheader")), "Alertas"));
		}
		else if(BaseTest.productUnderTest.equals("chpbr")) {
			wait.until(ExpectedConditions.textToBePresentInElement(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".myalertsheader")), "Alertas"));
		}
		else {
	
		wait.until(ExpectedConditions.textToBePresentInElement(elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".myalertsheader")), "Mis Alertas"));
		
		
		}
	}
	
	
	public void CreateAlert(String AlertName, String email) 
	{
		try{
			clickCreateNewAlert();
			selectPredesignAlert();
			clickNextButtonInPredesignAlert();
			writeNameField(AlertName);
			writeEmailField(email);
			Thread.sleep(2000);
			clickSaveInCreateAlertPage();
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : CreateAlert <br>"+displayErrorMessage(exc));
		}
		
	}
	
	
	public boolean ModifyAlert(String searchkeywordforalert) throws InterruptedException
	{
		try{	
				Thread.sleep(1000);
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alerttablefilter")).clear();
				elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alerttablefilter")).sendKeys(searchkeywordforalert);
					if(BaseTest.productUnderTest.equals("chppy")) {
			WebElement sortOnDate = elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".sortondatefilter"));
			sortOnDate.click();
			sortOnDate.click();
				}
				elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".modifyalertlink"));
				Select select=new Select(driver.findElement(By.id("freq")));
				select.selectByIndex(2);
				elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alertsavebutton"));
				return true;
		} catch(Exception exc)
		{
			extentLogger.log(LogStatus.INFO, "Error in : Modify Alert <br>"+displayErrorMessage(exc));
			return false;
		}
		
		
	}
	
	public boolean DeleteAlert(String searchkeywordforalert) throws InterruptedException
	{
		Boolean flag;
		try{
			
			//elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".myalertslink"));	
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alerttablefilter")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alerttablefilter")).sendKeys(searchkeywordforalert);
			Thread.sleep(2000);
				if(BaseTest.productUnderTest.equals("chppy")) {
			WebElement sortOnDate = elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".sortondatefilter"));
			sortOnDate.click();
			sortOnDate.click();
				} 
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".removealertlink"));
			Thread.sleep(2000);
			clickDeleteAlertYesButton();
			flag = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alerttableinfoforzeroresults")).isDisplayed();
		WebElement element=	elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alerttablefilter"));
		element.clear();
			Actions a = new Actions(driver);
			a.sendKeys(element, Keys.ENTER).build().perform();
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : DeleteAlert <br>"+displayErrorMessage(exc));
			 flag=false;
		}
		return flag;
	}
	
	public void clickDeleteAlertYesButton() {
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf."+productUnderTest+ ".removealertyesbutton");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					elementhandler.findElement(locator));
			elementhandler.clickElement(locator);
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickDeleteAlertYesButton <br>"+displayErrorMessage(exc));
		}
	}
	
	
	public boolean SuspendAlert(String suspendtodate)
	{
		Boolean flag;
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".suspendalertlink")),30);
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendalertlink"));
			//elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendradiobutton")).isSelected();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendtodate")).sendKeys(suspendtodate);
			Thread.sleep(500);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendtodate")).sendKeys(Keys.TAB);
			Thread.sleep(500);
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".modifiedalertsavelink")),30);
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".modifiedalertsavelink"));
			flag =  isReactivateAlertLinkPresent();
		}catch(Exception exc){
			
			flag=false;
		}
		return flag;
	}
	
	
	
	public boolean ReactivateAlert() throws InterruptedException
	{
		Boolean flag=false;
		try{
			
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".reactivatealertlink")).isDisplayed();
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".reactivatealertlink")),30);
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".reactivatealertlink"));
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".activatealertradiobutton")).isSelected();
			WebDriverFactory.waitForElementUsingWebElement(driver,elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".modifiedalertsavelink")),30);
			Thread.sleep(1000);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".modifiedalertsavelink"));
			Thread.sleep(2000);
			flag =  elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendalertlink")).isDisplayed();
		
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : ReactivateAlert <br>"+displayErrorMessage(exc));
		}
			return flag;
	}
	
	public String getUniqueAlertName(String name)
	
	{
		  Date dNow = new Date();
	      SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
	      String datetime = ft.format(dNow);
	      return name+datetime;
	 }
	
	public boolean EmpltyFieldValidation()
	{
		boolean flag=false;
		try{
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".myalertslink"));
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".createnewalertlink"));
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".nextbutton"));
			Thread.sleep(2000);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteremailidfield")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteralertnamefield")).clear();
			Thread.sleep(2000);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alertsavebutton"));
			
			String ExpectedErrorMessage = null;
			
			switch(productUnderTest){
			case "chparg":
			case "chpmex":
			case "chpury":
			case "chppy":
				Thread.sleep(2000);
				ExpectedErrorMessage = "Se produjo un error con los datos enviados. Por favor revise la informacion e intente nuevamente.";
				break;
			default:
				ExpectedErrorMessage = "product not applicable";
			}
			Thread.sleep(2000);
			String ActualErrorMessage = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".errormessage"));
			
			if(ActualErrorMessage.contains(ExpectedErrorMessage))
				flag=true;
			else
				flag=false;
			
		
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : EmpltyFieldValidation <br>"+displayErrorMessage(exc));
			flag=false;
		}
		return flag;
	}
	
	/*
	 * This method clicks on Sort image - sort by date asc
	 */
	public void sortAlertByDateAsc()
	{
		try{
			sortAlertByDate();
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".sort_modifydate_asc"));
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : sortAlertByDateAsc <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on Sort image - sort by date desc
	 */
	public void sortAlertByDateDesc()
	{
		try{
			sortAlertByDate();
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".sort_modifydate_desc"));
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : sortAlertByDateDesc <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on Sort image - sort by date
	 */
	public void sortAlertByDate()
	{
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".sort_modifydate");
			WebElement webElement = elementhandler.findElement(locator);
			if(webElement.isDisplayed())
				elementhandler.clickElement(locator);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : sortAlertByDate <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method verifies the type of first alert
	 * Returns true if alert type is 'Follow Document'
	 */
	public boolean isFirstAlertEqualsFollowDocument()
	{
		boolean flag = false;
		
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".alerttype_firstrow");
			WebElement webElement = elementhandler.getElement(locator);
			String message = webElement.getAttribute("title");
			flag = message.contains("Documentos en seguimiento") || message.contains("Acompanhamento de documento");
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isFirstAlertEqualsFollowDocument <br>"+displayErrorMessage(exc));
			flag = false;
		}
		
		return flag;
	}
	
	/*
	 * This method returns the modified date of alert in first row
	 */
	public String getModifiedDateOfFirstAlert()
	{
		String message = null;
		
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".modified_date_firstrow");
			message = elementhandler.getText(locator);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : getModifiedDateOfFirstAlert <br>"+displayErrorMessage(exc));
		}
		
		return message;
	}
	
	/*
	 * This method filters the  list of alerts
	 */
	public void filterAlertListByText(String filter)
	{
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".alerttablefilter");
			WebElement webElement = elementhandler.getElement(locator);
			
			if(!webElement.isDisplayed())
			{
				driver.navigate().refresh();
				WebDriverFactory.waitForElementUsingWebElement(driver, webElement, 30);
			}
			WebDriverFactory.waitForElementUsingWebElement(driver, webElement, 30);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",webElement);
			webElement.clear();
			webElement.sendKeys(filter);
			//First time filter is not working for chparg
			// adding additional charactor, and removing it.
			//webElement.sendKeys("1");
			webElement.sendKeys(Keys.BACK_SPACE);
			
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : filterAlertListByText <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method verifies if alert title is displayed
	 */
	public boolean isAlertPageTitleDisplayed()
	{
		boolean flag=false;
		try{
			String title = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".alert_page_title"));
			if(title.contains("Alertas")){
					flag = true;
			}else if(title.contains("Mis Alertas")){
				flag=true;
			}else{
				flag=false;}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isAlertPageTitleDisplayed <br>"+displayErrorMessage(exc));
		}
		return flag;
	}
	
	/*
	 * This method verifies if an alert exist in the alert list
	 * returns true on success
	 */
	public boolean isAlertNameExist(String alertName)
	{
		try{			
			Thread.sleep(1000);
			//sorting on the date filter to bring up the newly added alert on top

			WebElement sortOnDate = elementhandler.findElement(PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".sortondatefilter"));
			sortOnDate.click();
			sortOnDate.click();

			String actualName = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".alertname_firstrow"));
			if(actualName.contains(alertName))
			{
				return true;
			}
			else
			{
				filterAlertListByText(alertName);
				actualName = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".alertname_firstrow"));
				return actualName.contains(alertName);
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isAlertNameExist <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	/*
	 * This method clicks on 'create new alert' link in the alert list
	 */
	public void clickCreateNewAlert() 
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".createnewalertlink"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickCreateNewAlert <br>"+displayErrorMessage(exc));
		}
	}
	
	public boolean isPresentAlertLink() {
		
		try {
			Thread.sleep(4000);;
			return driver.findElement(By.xpath("//a[text()='Crear nueva alerta']")).isDisplayed();
		}
		
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isPresentAlertLink <br>"+displayErrorMessage(exc));
			return false;
		}
		
	}
	/*
	 * This method checks if the 'PredesignAlerts' is present or not
	 * return true on success
	 */
	public boolean isPredesignAlertsPresent() 
	{
		boolean isTitlePresent=false;
		
		try{
			Thread.sleep(4000);
			String actualTitle =elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alert_predesign_title"));
			Thread.sleep(4000);
			if(actualTitle.equalsIgnoreCase("Alertas pre-diseñadas"))
				isTitlePresent = true;
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isPredesignAlertsPresent <br>"+displayErrorMessage(exc));
		}
		
		return isTitlePresent;
	}
	
	/*
	 * This method checks if the PredesignAlerts - Subjects is present or not
	 * return true on success
	 */
	public boolean isPredesignAlertsSubjectDisplayed() 
	{
		boolean isSubjectDisplayed=false;
		
		try{
			isSubjectDisplayed = elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alert_predesign_content")).isDisplayed();
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isPredesignAlertsSubjectDisplayed <br>"+displayErrorMessage(exc));
		}
		
		return isSubjectDisplayed;
	}

	/*
	 * This method checks if the 'Reactivate' alert link is present or not
	 * return true on success
	 */
	public boolean isReactivateAlertLinkPresent() 
	{
		boolean linkPresent=false;
		
		try{
			Thread.sleep(2500);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".reactivatealertlink")), 30);
			String elementText = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".reactivatealertlink"));
			switch(productUnderTest){
			case "chparg":
				linkPresent = true;
				break;
			case "chpmex":
			case "chpury":
			case "chppy":	
				if(elementText.equals("Reactivar"))
					linkPresent = true;
				break;
			case "chpbr":
				if(elementText.equals("Reativar"))
					linkPresent = true;
				break;
			case "chpchile":
				if(elementText.equals("Activar"))
					linkPresent = true;
				break;
			
			}
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isReactivateAlertLinkPresent <br>"+displayErrorMessage(exc));
		}
		
		return linkPresent;
	}
	
	/*
	 * This method clicks on 'Reactivate' alert link
	 */
	public void clickReactivateAlert() 
	{
		try{
			
			Thread.sleep(2500);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+ ".reactivatealertlink")), 30);
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".reactivatealertlink"));
			Thread.sleep(1000);
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickReactivateAlert <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on 'Suspend' alert link
	 */
	public void clickSuspendAlert() 
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".suspendalertlink"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickSuspendAlert <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on 'Cancel' on Suspend/Reactivate page
	 */
	public void clickCancelOnSuspendOrReactivatePage() 
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alert_suspend_or_reactivate_cancel"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelOnSuspendOrReactivatePage <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method verifies that no element with text 'ID Cliente' exist in the page
	 * returns true on success
	 */
	public boolean isClientIDNotDisplayed() 
	{
		boolean clientIDNotDisplayed=false;
		try{
			clientIDNotDisplayed = !(WebDriverFactory.isDisplayed(driver, elementhandler.getElement("//*[text()='ID Cliente']")));
		}
		catch(Exception exc){
			//Exception is expected for success scenario
			clientIDNotDisplayed = true;
		}
		return clientIDNotDisplayed;
	}
	
	public boolean isPresentNonExistingFields(String data) {
		boolean clientIDNotDisplayed=false;
		try{
			//String loc=//*[text()='\+data+\']";
			
		WebElement ele=	driver.findElement(By.xpath("//*[text()='"+data+"']"));
		System.out.println(ele);
		}
		catch(Exception exc){
			//Exception is expected for success scenario
			clientIDNotDisplayed = true;
		}
		return clientIDNotDisplayed;
	}
	
	/*
	 * This method verifies that no element with text 'Formato' exist in the page
	 * returns true on success
	 */
	public boolean isFormatNotDisplayed() 
	{
		boolean formatNotDisplayed=false;
		try{
			formatNotDisplayed = !(WebDriverFactory.isDisplayed(driver, elementhandler.getElement("//*[text()='Formato']")));
		}
		catch(Exception exc){
			//Exception is expected for success scenario
			formatNotDisplayed = true;
		}
		return formatNotDisplayed;
	}
	
	/*
	 * This method verifies that no element with text 'Notificacion' exist in the page
	 * returns true on success
	 */
	public boolean isNoticeNotDisplayed() 
	{
		boolean noticeNotDisplayed=false;
		try{
			noticeNotDisplayed = !(WebDriverFactory.isDisplayed(driver, elementhandler.getElement("//*[text()='Notificacion']")));
		}
		catch(Exception exc){
			//Exception is expected for success scenario
			noticeNotDisplayed = true;
		}
		return noticeNotDisplayed;
	}
	
	/*
	 * This method clicks on 'Cancel' on PredesignAlert page
	 */
	public boolean clickCancelButtonInPredesignAlert() 
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alert_predesign_cancel"));
			return true;
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelButtonInPredesignAlert <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	/*
	 * This method clicks on 'Next' on PredesignAlert page
	 */
	public void clickNextButtonInPredesignAlert() 
	{
		try{
			
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".nextbutton"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickNextButtonInPredesignAlert <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method selects the 'PredesignAlerts' by name
	 * return true on success
	 */
	public boolean selectPredesignAlertByName(String predesignName) 
	{
		boolean predesignNameSelected=false;
		
		WebElement radioButton = null;
		
		try{
			String allPredesignLabelsXpath = PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + ".alert_predesign_labels_xpath");
			String pathOfRadioButton = allPredesignLabelsXpath + "[normalize-space(text())='"+predesignName+"']/..//input[@id='subject']";
			radioButton = elementhandler.getElement(pathOfRadioButton);
			
			if(radioButton.getAttribute("type").equals("radio")){
				if(!radioButton.isSelected())
					radioButton.click();
				predesignNameSelected = true;
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : selectPredesignAlertByName <br>"+displayErrorMessage(exc));
			predesignNameSelected=false;
		}
		return predesignNameSelected;
	}
	
	/*
	 * This method selects the 'PredesignAlerts' by name
	 * return true on success
	 */
	public boolean selectPredesignAlertConclusiveAgreements() 
	{
		return selectPredesignAlertByName("Acuerdos conclusivos");
	}
	
	/*
	 * This method selects the 'PredesignAlerts', based on product under test
	 * return true on success
	 */
	public boolean selectPredesignAlert() 
	{
		boolean selected=false;
		
		switch(productUnderTest){
		case "chpmex":
			selected=selectPredesignAlertByName("Acuerdos conclusivos");
			break;
		case "chparg":
			selected=selectPredesignAlertByName("Retenciones y percepciones de IVA o Ganancias");
			break;
		case "chpchile":
			selected=selectPredesignAlertByName("Rentas presuntas del transporte de carga");
			break;
		}
		return selected;
	}
	
	/*
	 * This method writes name on create alert page
	 */
	public void writeNameField(String AlertName) 
	{
		try{
			Thread.sleep(1000);
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteralertnamefield")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteralertnamefield")).sendKeys(AlertName);
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : writeNameField <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method writes email on create alert page
	 */
	public void writeEmailField(String email) 
	{
		try{
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteremailidfield")).clear();
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".enteremailidfield")).sendKeys(email);
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : writeEmailField <br>"+displayErrorMessage(exc));
		}
	}
	
	/*
	 * This method clicks on save button on create alert page
	 */
	public void clickSaveInCreateAlertPage() 
	{
		try{
			elementhandler.clickElement(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest+".alertsavebutton"));
		}
		catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveInCreateAlertPage <br>"+displayErrorMessage(exc));
		}
	}
	
	public boolean isCreateNewAlertLinkPresent() {
		try {
			return elementhandler
					.findElement(
							PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".createnewalertlink"))
					.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCreateNewAlertLinkPresent <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	public boolean validateColumnsinALertTable()
	{
		try{
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".alerttable_kindcolumn"))
					.isDisplayed()
					&& elementhandler
							.findElement(PropertiesRepository
									.getString("com.trgr.maf." + productUnderTest + ".alerttable_firstnamecolumn"))
							.isDisplayed()
					&& elementhandler
							.findElement(PropertiesRepository
									.getString("com.trgr.maf." + productUnderTest + ".alerttable_datecreated"))
							.isDisplayed()
					&& elementhandler
							.findElement(PropertiesRepository
									.getString("com.trgr.maf." + productUnderTest + ".alerttable_searchsummary"))
							.isDisplayed()
					&& elementhandler
							.findElement(PropertiesRepository
									.getString("com.trgr.maf." + productUnderTest + ".alerttable_nextrun"))
							.isDisplayed()
					&& elementhandler
							.findElement(PropertiesRepository
									.getString("com.trgr.maf." + productUnderTest + ".alerttable_actions"))
							.isDisplayed();

		}catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateColumnsinALertTable <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	
	public boolean isFilterAlertPortletDisplayed()
	{
		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".alertpage_filteralertportlet"))
					.isDisplayed();
		}catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFilterAlertPortletDisplayed <br>"+displayErrorMessage(exc));
			return false;
		}
	}
	public boolean validateAlertTypesAlertPage(String alerttype)
	{
		boolean flag = false;
		try{
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".alertpage_typeofalerts");
			List<WebElement> allalerts = elementhandler.findElements(locator);
			for(int i=0;i<allalerts.size();i++)
			{
				String alertname = allalerts.get(i).getText();
				if(alertname.equalsIgnoreCase(alerttype))
					{
					flag = true;
					break;
					}
			}
			
		}catch (Exception exc) {
			flag= false;
		}
		return flag;
	}
	
	public void deleteAllAlerts() {
		try{
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".deleteallalerts");
			List<WebElement> allalerts = elementhandler.findElements(locator);
			
			for(int i=0;i<allalerts.size();i++)
			{
				//Click first occurance of delete link
				WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 30);
				Thread.sleep(2000);//Element is not clickable
				elementhandler.clickElement(locator);
				clickDeleteAlertYesButton();
			}
		}catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : deleteAllAlerts <br>"+displayErrorMessage(exc));
		}
	}
	
	public HomePage clickHomeTab() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".homepagetab");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					elementhandler.findElement(locator));
			elementhandler.clickElement(locator);
			return new HomePage(driver);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickHomeTab <br>"+displayErrorMessage(ex));
			return null;
		}

	}
	
	/*
	 * This method is checking for create new alert is displayed or not For raised
	 * defect id - MAFAUTO-295
	 */
	public boolean newAlertLinkIsDisplayed() {
		boolean flag = false;
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".createnewalertlink"))
					.isDisplayed();
			return true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : writeNameField <br>" + displayErrorMessage(exc));
			return false;
		}

	}

}
	
