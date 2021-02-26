package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class DeliveryPage extends BasePage {

	public DeliveryPage(WebDriver driver) throws IllegalArgumentException, IOException {
		super(driver);
	}

	public void clickCheckBox() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checkbox"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCheckBox <br>" + displayErrorMessage(e));
		}

	}

	public void emailClicked() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".emaillink"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : emailClicked <br>" + displayErrorMessage(e));
		}
	}

	public void clickRTFRadioButton() {

		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttrtf"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickRTFRadioButton <br>" + displayErrorMessage(e));
		}
	}

	public void clickPDFRdioButton() {
		try {
			elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttpdf"));
			}
		catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPDFRdioButton <br>" + displayErrorMessage(e));
		}
	}
	
	public void clickPDFRadioButtonforDelivery() {
		try {
		if(BaseTest.productUnderTest.equals("chpmex")) {
			String title=driver.getTitle();
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttpdf"));
			String title2=driver.getTitle();
			if(!title2.equals(title))
			{
				driver.navigate().back();
			}
		}		
		else {
		elementhandler.clickElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttpdf"));
		  }
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPDFRadioButtonforDelivery <br>" + displayErrorMessage(e));
	}
	}
		
	public void clickResultListRdioButton() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttonresultlist"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickResultListRdioButton <br>" + displayErrorMessage(e));

		}
	}

	public void clickFullDocumentRdioButton() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttonfulldocument"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickFullDocumentRdioButton <br>" + displayErrorMessage(e));

		}
	}

	public void clickOkButton() {
		try {
			
			if(IsPopUpWindowPresent()){
				clickOnAlertPopUP();
				Thread.sleep(5000);
			}
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ok")), 40);
			Thread.sleep(2500);
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".ok");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(1000);
		
		
			
	
			
			
			}
			
		 catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOkButton <br>" + displayErrorMessage(e));

		}
	}

	public boolean deliverTextDisplayed() {
		try {
			Thread.sleep(4000);

			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".text")), 40);
			Thread.sleep(2500);
			return elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".text"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : deliverTextDisplayed <br>" + displayErrorMessage(e));
			return false;

		}

	}

	public void deliverReturnHomePage() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".home"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : deliverReturnHomePage <br>" + displayErrorMessage(e));

		}

	}

	//Clicks on cancel button 
	public void clickCancelButton() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".cancel"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelButton <br>" + displayErrorMessage(e));

		}

	}

	public boolean deliverySearchResultList() {
		try {
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".listofdocuments"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : deliverySearchResultList <br>" + displayErrorMessage(e));
			return false;

		}
		return true;

	}

	public void emailTo(String Emailname) {
		try {
			Thread.sleep(4000);
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".emailto"),
					Emailname);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : emailTo <br>" + displayErrorMessage(e));

		}
	}

	public void emailToClear() {
		try {
			elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".emailto"))
					.clear();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : emailToClear <br>" + displayErrorMessage(e));

		}
	}

	public boolean radiobuttonFullDocument() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttonfulldocument"))
					.isSelected();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : radiobuttonFullDocument <br>" + displayErrorMessage(e));
			return false;
		}
		return true;

	}

	public boolean radiobuttonResultListDocument() {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttonresultlist"))
					.isSelected();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : radiobuttonResultListDocument <br>" + displayErrorMessage(e));
			return false;

		}
		return true;

	}

	public boolean radiobuttonRTF() {
		try {
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttrtf"))
					.isSelected();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : radiobuttonRTF <br>" + displayErrorMessage(e));
			return false;

		}
		return true;

	}

	public boolean radiobuttonPDF() {
		try {
			elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".radiobuttpdf"))
					.isSelected();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : radiobuttonPDF <br>" + displayErrorMessage(e));
			return false;

		}
		return true;

	}
	
	public void selectRadioButtonDeliveryPage(String radiobutton) {
		try {
			
			if(radiobutton.trim().equals("RTF (Word)"))
			{
				Thread.sleep(1000);
				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttrtf");
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						elementhandler.getElement(locator));
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".radiobuttrtf"));
				
			
			}
			
			else if (radiobutton.equals("PDF"))
			{
				Thread.sleep(1000);
				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".radiobuttpdf");
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						elementhandler.getElement(locator));
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + productUnderTest + ".radiobuttpdf"));
			}
			
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : selectRadioButtonDeliveryPage <br>" + displayErrorMessage(e));
		}

		
	}

	public void clickSearch() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchbtnonhomepage"));
			Thread.sleep(1000); // Need to remove this once the search string is
								// updated to more specific to return results
								// faster
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearch <br>" + displayErrorMessage(e));
		}

	}

	public void enterFreewordOnQuickSearch(String freeword) {
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".freewordsearchboxonhomepage"))
					.sendKeys(freeword);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : enterFreewordOnQuickSearch <br>" + displayErrorMessage(e));
		}
	}

	/*
	 * This method checks for the presence of Export button , and returns
	 * boolean
	 */
	public boolean isResultListExportButtonDisplayed() {
		boolean flag = false;
		WebElement webElement = null;

		try {
			String ss = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".export_document_image");
			webElement = elementhandler.getElement(ss);
			flag = webElement.isDisplayed();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isResultListExportButtonDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	/*
	 * This method clicks on Export button, and returns true on success
	 */
	public boolean clickDocumentExportButton() {
		boolean flag = false;
		try {
			if (isResultListExportButtonDisplayed()) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".export_document_image"));
				flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDocumentExportButton <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	/*
	 * This method checks for various elements in export page, and returns true
	 * on success
	 */
	public boolean verifyResultListExportPage() {
		boolean flag = false;

		flag = (verifyElementTextInExportPage("lista_de_resultados_label", "Lista de resultados")
				|| verifyElementTextInExportPage("lista_de_resultados_label", "Lista de Resultados"))
				&& verifyElementTextInExportPage("documentos_completos_label", "Documentos completos")
				&& (verifyElementTextInExportPage("formato_de_archivo_text", "Formato de archivo")
						|| verifyElementTextInExportPage("formato_de_archivo_text", "Formato do arquivo"));

		return flag;
	}

	/*
	 * This method looks for texts in on Doc Export page, and returns true on
	 * success
	 */
	public boolean verifyDocumentExportPage() {
		boolean flag = false;

		flag = verifyElementTextInExportPage("formato_de_archivo_text", "Formato de archivo")
				|| verifyElementTextInExportPage("formato_de_archivo_text", "Formato do arquivo");

		return flag;
	}

	
	
	/*
	 * This method verifies the text of an element, and returns true on success
	 */
	public boolean verifyElementTextInExportPage(String element, String expectedText) {
		boolean flag = false;
		String actualText = null;
		try {
			String locator =PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + "." + element +"");
			
			actualText = elementhandler.getElement(locator).getText();
			if (actualText.contains(expectedText))
				flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyElementTextInExportPage <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * button, and returns true on success
	 */
	public boolean clickAcceptButton() {
		boolean flag = false;
		try {
			Thread.sleep(6000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".accept_button_exportpage");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(6000);
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickAcceptButton <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	/*
	 * This method clicks on Cancel button, and returns true on success
	 */
	public boolean clickCancel() {
		boolean flag = false;
		try {
			String locator =PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".cancel_button_exportpage");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.getElement(locator).click();
			
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCancel <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks for 'Delivery Completed' status & message text on
	 * Export, and returns true on success
	 */
	public boolean verifyDeliveryStatus() {
		boolean flag = false;

		String actualText = null;
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".delivery_status_delivery_complete")),
					60);

			actualText = elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".delivery_status_label"));
			if (actualText.contains("Su pedido está completo")
					|| actualText.contains("Seu pedido de delivery foi finalizado"))
				flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyDeliveryStatus <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks for 'Delivery Completed' status on Export, and returns
	 * true on success
	 */
	public boolean isDeliveryCompleted() {
		boolean flag = false;

		try {
			Thread.sleep(4000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".delivery_status_delivery_complete")),
					30);
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDeliveryCompleted <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method verifies the presence of Results list page after clicking
	 * 'volver' button, and returns true on success
	 */
	public boolean verifyReturnToResultList() {
		boolean flag = false;
		String text = null;
		WebElement webElement = null;
		try {
			webElement = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".volver_button_exportpage"));
			webElement.click();
			text = elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".result_list_header_title"));
			if (text.equals("Resultado de la Búsqueda") || text.equals("Resultado da busca")
					|| text.equals("Resultado de la búsqueda"))
				flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyReturnToResultList <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method verifies the presence of document display after clicking
	 * 'volver', and returns true on success
	 */
	public boolean verifyReturnToDocumentDisplay() {
		boolean flag = false;

		WebElement webElement = null;
		try {
			webElement = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".volver_button_exportpage"));
			webElement.click();
			Thread.sleep(3000);
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".document_display_main_title");
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(locator), 60);
			webElement = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".document_display_main_title"));
			flag = WebDriverFactory.isDisplayed(driver, elementhandler.findElement(locator));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyReturnToDocumentDisplay <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method clicks on any web element, and returns true on success
	 */
	public boolean clickWebElement(String element) {
		boolean flag = false;
		WebElement webElement = null;
		try {
			webElement = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + "." + element));
			webElement.click();
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickWebElement <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method enables a radio button Arguments - name of button, & its
	 * value returns boolean
	 */
	public boolean enableRadioButton(String button) {
		boolean flag = false;
		WebElement webElement = null;

		try {
			webElement = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + "." + button));

			if (webElement.getAttribute("type").equals("radio")) {
				if (!webElement.isSelected())
					webElement.click();
				flag = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enableRadioButton <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	
	
	/*
	 * This method sends input range to 'Documentos completos' edit box
	 * Arguments - Range returns boolean
	 */
	public boolean setDocumentRange(String range) {
		boolean flag = false;
		WebElement webElement = null;
		if (productUnderTest.equals("chpbr"))// Result Range selection is not
												// available for chpbr
			return true;

		try {
			webElement = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".documentos_completos_editbox"));
			webElement.clear();
			webElement.click();
			webElement.sendKeys(range);
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : setDocumentRange <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method sends input range to 'List of Results' edit box Arguments -
	 * Range returns boolean
	 */
	public boolean setListOfResultsRange(String range) {
		boolean flag = false;
		WebElement webElement = null;
		if (productUnderTest.equals("chpbr"))// Result Range selection is not
												// available for chpbr
			return true;

		try {
			webElement = elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".lista_de_resultados_editbox"));
			webElement.clear();
			webElement.sendKeys(range);
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : setListOfResultsRange <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void enternumberDocument(String DocumentNumber) {
		try {
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".numberdocument"),
					DocumentNumber);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : enternumberDocument <br>" + displayErrorMessage(e));

		}
	}

	public SearchPage deliveryReturnToSearchPage() throws IllegalArgumentException, IOException {
		try {
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".searchpage"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : deliveryReturnToSearchPage <br>" + displayErrorMessage(e));

		}
		return new SearchPage(driver);
	}

	public void clickPrintButton() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".print"));

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPrintButton <br>" + displayErrorMessage(e));

		}
	}

	public void clickPrintButtonWindow() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".printwindow"));

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in :clickPrintButtonWindow <br>" + displayErrorMessage(e));

		}
	}

	public void emailclicked() throws IllegalArgumentException, IOException {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".emaillink"));

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : emailClicked <br>" + displayErrorMessage(ex));

		}
	}


	public void navigateBack() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".return"));
    
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPrint <br>" + displayErrorMessage(e));

		}
	}

	//clicks on Print icon
	public void clickPrint() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".printlink"));

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickPrint <br>" + displayErrorMessage(e));

		}
	}

	public void clickExportButton() {

		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".export_document_image"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickExportButton <br>" + displayErrorMessage(exc));
		}

	}

	public boolean listofResultDisplay() {

		try {
			return elementhandler
					.findElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".lista_de_resultados_label"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : listofResultDisplay <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean exForListofResult() {
		boolean flag = false;
		String actualText = null;
		try {
			actualText = elementhandler.getText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".exoflistofresult"));
			if (actualText.contains("1,3,5"))
				flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : exForListofResult <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isCompleteDocumentOptionPresent() {

		try {
			return elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".numberdocument"))
					.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isCompleteDocumentOptionPresent <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	public boolean isRangeCompleteDocumentOptionPresent() {
		boolean flag = false;
		String actualText = null;
		try {
			actualText = elementhandler.getText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".completedocmax"));
			if (actualText.contains("max. 5"))
				flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isRangeCompleteDocumentOptionPresent <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean isRTFandPDFFileformatDispaly() {

		try {
			return elementhandler
					.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".rtf"))
					.isDisplayed()
					&& elementhandler
							.findElement(PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".pdf"))
							.isDisplayed();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isRTFandPDFFileformatDispaly <br>" + displayErrorMessage(exc));
			return false;
		}
	}

}
