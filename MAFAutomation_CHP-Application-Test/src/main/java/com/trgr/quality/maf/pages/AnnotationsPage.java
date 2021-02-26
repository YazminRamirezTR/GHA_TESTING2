/**
 * 
 */
package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

/**
 * Main Class for AnnotationsPage
 * This class defines all methods for 'AnnotationsPage'
 * @author Sarath Manoharam
 * @version 1.0
 * @since December 27, 2016
 */
public class AnnotationsPage extends BasePage {
	
	public AnnotationsPage(WebDriver driver) throws IOException, IllegalArgumentException 
	{
		super(driver);
	}
	
	/*
	 * This method verifies the availability of Annotations option in the document display page
	 * returns true on success
	 */
	public boolean isAnnotationsOptionPresent(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_label");
			WebElement webElement = elementhandler.getElement(stringText);
			stringText = webElement.getText();
			flag = stringText.trim().equals("Anotaciones") || stringText.trim().equals("Anotações");
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isAnnotationsPresent <br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method clicks on Annotations option in the document display page
	 * returns true on success
	 */
	public boolean clickAnnotationsDropDown(){
		
		boolean flag = false;
		try{
			Thread.sleep(2000);
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_label");
			WebElement webElement = elementhandler.getElement(stringText);
			webElement.isDisplayed();
			webElement.click();
			
			flag = true;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickAnnotationsDropDown<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method verifies the visibility of 'Add note' option in annotations dropdown
	 * returns true on success
	 */
	public boolean isAddNoteOptionVisible(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_dropdown_addnote");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				stringText = elementhandler.getText(stringText);
				flag = stringText.equals("Añadir nota") || stringText.equals("Adicionar nota");
			}
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isAddNoteOptionVisible<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method verifies the visibility of 'Hide annotations' option in annotations dropdown
	 * returns true on success
	 */
	public boolean isHideNoteOptionVisible(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_dropdown_hidenote");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				stringText = elementhandler.getText(stringText);
				flag = stringText.equals("Ocultar Anotaciones")||stringText.equals("Ocultar anotações");
			}
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isHideNoteOptionVisible<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method verifies the 'Hide annotations' option is hidden in annotations dropdown
	 * returns true on success
	 */
	public boolean isHideNoteOptionHidden(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_dropdown_hidenote");
			WebElement webElement = elementhandler.getElement(stringText);
			if(!webElement.isDisplayed()){
				flag = true;
			}
			
		}catch(Exception exc){
			//Exception is expected - no need to log error
			flag = true;
		}
		return flag;
	}
	
	/*
	 * This method verifies the visibility of 'Show annotations' option in annotations dropdown
	 * returns true on success
	 */
	public boolean isShowNoteOptionVisible(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_dropdown_shownote");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				stringText = elementhandler.getText(stringText);
				flag = stringText.equals("Mostrar Anotaciones") || stringText.equals("Mostrar anotações");
			}
			
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isShowNoteOptionVisible<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method verifies the 'Show annotations' option is not visible in annotations dropdown
	 * returns true on success
	 */
	public boolean isShowNoteOptionHidden(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_dropdown_shownote");
			WebElement webElement = elementhandler.getElement(stringText);
			if(!webElement.isDisplayed()){
				flag = true;
			}
			
		}catch(Exception exc){
			//Exception - expected , and error logging is not required
			flag = true;
		}
		return flag;
	}
	
	/*
	 * This method clicks on 'Add note' option in annotations drop down
	 * returns true on success
	 */
	public boolean clickAddNoteOption(){
		
		boolean flag = false;
		try{
			Thread.sleep(1000);
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_dropdown_addnote");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				webElement.click();
				flag = true;
			}
			
			if(isErrorMaximumCountDisplayed())
			{
				Thread.sleep(2000);
				
				driver.findElement(By.xpath("//button//span[text()='Cerrar']")).click();
				Thread.sleep(2000);
			List<WebElement>delete=driver.findElements(By.xpath("//li[@class='removeNoteItem']//span//img"));
			Thread.sleep(2000);
			for(int i=0;i<delete.size();i++) {
				driver.findElement(By.xpath("//li[@class='removeNoteItem']//span//img")).click();
				
						Thread.sleep(1000);
						driver.findElement(By.xpath("(//button//span[text()='Eliminar'])[3]")).click();
						Thread.sleep(1000);
			}
			String text = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_label");
			WebElement webElement1 = elementhandler.getElement(text);
			webElement1.isDisplayed();
			webElement1.click();
			Thread.sleep(3000);
			webElement.click();
				
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickAddNoteOption<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	/*
	 * This method clicks on 'Maximum count ' option in documents  drop down
	 * returns true on success
	 */
	
	public static boolean isErrorMaximumCountDisplayed() 
	{
		boolean flag=false;

		try {
			Thread.sleep(1000);
	
			return driver.findElement(By.xpath("//p[@class='rsNoteError']")).isDisplayed();
		}
		catch(Exception exc)
		{
			
			flag = false;
		}
		return flag;
	}
	/*
	 * This method clicks on 'Hide note' option in annotations drop down
	 * returns true on success
	 */
	public boolean clickHideNoteOption(){
		
		boolean flag = false;
		try{
			Thread.sleep(1000);
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_dropdown_hidenote");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				webElement.click();
				flag = true;
				Thread.sleep(1000);
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickHideNoteOption<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method clicks on 'Show note' option in annotations drop down
	 * returns true on success
	 */
	public boolean clickShowNoteOption(){
		
		boolean flag = false;
		try{
			Thread.sleep(1000);
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_dropdown_shownote");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				webElement.click();
				flag = true;
				Thread.sleep(1000);
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickShowNoteOption<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method verifies the presence of on 'Add note' pop-up window
	 * returns true on success
	 */
	public boolean isAddNotePopUpPresent(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_popup_header");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				flag = true;
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isAddNotePopUpPresent<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method verifies the content in the 'Edit note' pop-up window
	 * returns true if the content matches
	 */
	public boolean verifyEditPopUpContent(String docContent){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_popup_docnote_text");
			stringText = elementhandler.getText(stringText);
			if(stringText.equals(docContent)){
				flag = true;
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : verifyEditPopUpContent<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method closes the 'Add note' pop-up window
	 * returns true on success
	 */
	public boolean closeAnnotationsPopUp(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_popup_close");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				webElement.click();
				flag = !isAddNotePopUpPresent();
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : closeAnnotationsPopUp<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	
	/*
	 * This method clicks on 'Cancel' button in the 'Add note' pop-up window
	 * returns true on success
	 */
	public boolean clickCancelAnnotationsPopUp(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_popup_cancel");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				webElement.click();
				Thread.sleep(1000);
				flag = !isAddNotePopUpPresent();
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelAnnotationsPopUp<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method clicks on 'Anadir Note' button in the 'Add note' pop-up window
	 * returns true on success
	 */
	public boolean clickAddNoteButton(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_popup_add");
			WebElement webElement = elementhandler.getElement(stringText);
			webElement.click();
			Thread.sleep(1000);
			flag = true;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickAddNoteButton<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method clicks on 'Confirm' button in the 'Edit note' pop-up window
	 * returns true on success
	 */
	public boolean clickEditConfirmButton(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_popup_add");
			WebElement webElement = elementhandler.getElement(stringText);
			webElement.click();
			flag = true;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickEditConfirmButton<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method writes some data the 'Add note' pop-up window
	 * returns true on success
	 */
	public boolean writeDocumentNote(String docNote){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_popup_docnote_text");
			elementhandler.writeText(stringText, docNote);
			flag = true;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : writeDocumentNote<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method checks for add note confirmation message
	 * returns true on success
	 */
	public boolean verifyAddNoteConfirmation(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_confirmation_message");
			stringText = elementhandler.getText(stringText);
			flag = stringText.contains("Su nota ha sido añadida con éxito") || stringText.contains("Sua nota foi adicionada com sucesso");
			for(int i=0;!flag && i<10;i++){
				Thread.sleep(300);
				stringText = elementhandler.getText(stringText);
				flag = stringText.contains("Su nota ha sido añadida con éxito") || stringText.contains("Sua nota foi adicionada com sucesso");
			}
			Thread.sleep(1000);
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : verifyAddNoteConfirmation<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method checks for edit note confirmation message
	 * returns true on success
	 */
	public boolean verifyEditNoteConfirmation(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_confirmation_message");
			stringText = elementhandler.getText(stringText);
			
			flag = 	stringText.contains("Su nota ha sido modificada con éxito") ||
					stringText.contains("Sua nota foi atualizada com sucesso");
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : verifyEditNoteConfirmation<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method checks for document notes in sequential order, until a match is found
	 * returns true on success
	 */
	public boolean isDocumentNoteExist(String docNote){
		
		boolean flag = false;
		try{
			List <WebElement> notesInStickContainer = elementhandler.findElements(PropertiesRepository.getString("com.trgr.maf."+productUnderTest+".annotations_docnote_all_notes"));
			for(int rowNum=0; rowNum<notesInStickContainer.size(); rowNum++){
				if(WebDriverFactory.isDisplayed(driver, notesInStickContainer.get(rowNum))){
					if(notesInStickContainer.get(rowNum).getText().equals(docNote)){
						flag = true;
						break;
					}
				}
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isDocumentNoteExist<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	
	
	/*
	 * This method checks for document notes in sequential order
	 * returns true on failure
	 */
	public boolean isNotDocumentNoteExist(String docNote){
		
		boolean flag = true;
		try{
			List <WebElement> notesInStickContainer = elementhandler.findElements(PropertiesRepository.getString("com.trgr.maf."+productUnderTest+".annotations_docnote_all_notes"));
			for(int rowNum=0; rowNum<notesInStickContainer.size(); rowNum++){
				if(WebDriverFactory.isDisplayed(driver, notesInStickContainer.get(rowNum))){
					if(notesInStickContainer.get(rowNum).getText().equals(docNote)){
						flag = false;
						break;
					}
				}
			}
			
		}catch(Exception exc){
			//Exception is expected for normal scenario. Hence not logging in report.
			flag = true;
		}
		return flag;
	}
	
	/*
	 * This method checks for the visibility of document note.
	 * returns true on success
	 */
	public boolean isDocumentNoteDisplayed(){
		
		boolean flag = false;
		String docNote;
		WebElement webElement=null;
		try{
			docNote = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_docnote_first_note");
			webElement = elementhandler.getElement(docNote);
			
			if(webElement.isDisplayed()){
					flag = true;
			}
		
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isDocumentNoteDisplayed<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method checks for document notes in sequential order, until a match is found
	 * returns position as integer
	 * returns 0, if no match is found  
	 */
	public int findDocumentNotePosition(String docNote){
		
		int position = 0;
		try{
			List <WebElement> notesInStickContainer = elementhandler.findElements(PropertiesRepository.getString("com.trgr.maf."+productUnderTest+".annotations_docnote_all_notes"));
			for(int rowNum=0; rowNum<notesInStickContainer.size(); rowNum++){
				if(WebDriverFactory.isDisplayed(driver, notesInStickContainer.get(rowNum))){
					if(notesInStickContainer.get(rowNum).getText().equals(docNote)){
						position = rowNum+1;
						break;
					}
				}
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : findDocumentNotePosition<br>"+displayErrorMessage(exc));
			position = 0;
		}
		return position;
	}
	
	/*
	 * This method clicks on the document note edit image
	 * returns true on success
	 */
	public boolean clickDocumentNoteEditImage(int docNumber){
		
		boolean flag = false;
		String stickContainerItems,editImage;
		try{
			stickContainerItems = PropertiesRepository.getString("com.trgr.maf."+productUnderTest+".annotations_docnote_container_list");
			List <WebElement> notesInStickContainer = elementhandler.findElements(stickContainerItems);
			if(notesInStickContainer.size() >= docNumber){
				editImage = stickContainerItems +"["+docNumber+"]"+"//img";
				List <WebElement> imagesInStickContainer = elementhandler.findElements(editImage);
				for(int row=0;row<imagesInStickContainer.size();row++){
					String imageTitle = imagesInStickContainer.get(row).getAttribute("title");
					if(imageTitle.equals("Editar")){
						imagesInStickContainer.get(row).click();
						flag = true;
						break;
					}
				}
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickDocumentNoteEditImage<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method clicks on the document note delete image
	 * returns true on success
	 */
	public boolean clickDocumentNoteDeleteImage(int docNumber){
		
		boolean flag = false;
		String stickContainerItems,deleteImage;
		try{
			Thread.sleep(1000);
			stickContainerItems = PropertiesRepository.getString("com.trgr.maf."+productUnderTest+".annotations_docnote_container_list");
			List <WebElement> notesInStickContainer = elementhandler.findElements("xpath=//li[@class='removeNoteItem']");
			if(notesInStickContainer.size() >= docNumber){
				/*deleteImage = stickContainerItems +"["+docNumber+"]"+"//img";
				deleteImage = "(" + deleteImage + ")[2]";
				List <WebElement> imagesInStickContainer = elementhandler.findElements(deleteImage);
				for(int row=0;row<notesInStickContainer.size();row++){
					String imageTitle = notesInStickContainer.get(row).getAttribute("title");
					if(imageTitle.equals("Excluir") || imageTitle.equals("Eliminar")){
						notesInStickContainer.get(row).click();
						flag = true;
						Thread.sleep(1000);
						break;
						}
						}*/
				
			
				notesInStickContainer.get(1).click();
				if(BaseTest.productUnderTest.contains("chpmex")) {
					
		
				String deleteButton = "xpath=//*[@id='ui-dialog-title-confirmationDeleteDialogNote']//following::div//span[text()='Eliminar']";
				WebElement ele = elementhandler.findElement(deleteButton);
				ele.click();
				}
				
				else if(BaseTest.productUnderTest.contains("chpbr")) {
					String deleteButton = "xpath=//*[@id='ui-dialog-title-confirmationDeleteDialogNote']//following::div//span[text()='Excluir']";
					WebElement ele = elementhandler.findElement(deleteButton);
					ele.click();
				}
				
					flag=true;
				
			//	elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf."+productUnderTest+".annotations_delete_dialogue_eliminar")).click();
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickDocumentNoteDeleteImage<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	public boolean isDeleteBoxClicking() {
		boolean flag = false;
		String stringText,docContent;
		try{
			List <WebElement> notesInStickContainer = elementhandler.findElements("xpath=//li[@class='removeNoteItem']");
		
			if(notesInStickContainer.size() >0)
			{
				notesInStickContainer.get(1).click();
			}
			Thread.sleep(1000);
			
			
			flag = true;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isDeleteBoxClicking<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method updates the the document note edit image
	 * Replaces/add new line based on the flag 'appendAtNextLine' 
	 * returns true on success
	 */
	public boolean editDocumentNote(String editNote,boolean appendAtNextLine){
		
		boolean flag = false;
		String stringText,docContent;
		try{
			stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_popup_docnote_text");
			WebElement webElement = elementhandler.getElement(stringText);
			docContent = elementhandler.getText(stringText);
			if(appendAtNextLine){
				editNote = docContent +"\n"+ editNote;
			}
			webElement.clear();
			elementhandler.writeText(stringText, editNote);
			flag = true;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : editDocumentNote<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * Overloaded method to replace document content in edit window
	 * returns true on success
	 */
	public boolean editDocumentNote(String editNote){
		return editDocumentNote(editNote,false);
	}
	
	/*
	 * This method verifies the presence of on 'Eliminar Nota<delete confirmation' dialogue pop-up
	 * returns true on success
	 */
	public boolean isDeleteDialoguePresent(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_delete_dialogue_title");
			stringText=elementhandler.getText(stringText);
			flag = 	stringText.trim().equals("Eliminar nota") ||
					stringText.trim().equals("Excluir nota");
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isDeleteDialoguePresent<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method verifies the presence of on 'Eliminar Nota<delete confirmation' dialogue and message
	 * returns true on success
	 */
	public boolean verifyDeleteDialogueMessage(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_delete_dialogue_msg");
			stringText=elementhandler.getText(stringText);
			flag = 	stringText.contains("¿Está seguro que quiere eliminar esta nota?") ||
					stringText.contains("Tem certeza de que deseja excluir a nota?");
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : verifyDeleteDialogueMessage<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	
	/*
	 * This method clicks on 'Cancel' button in the 'Delete notes' dialogue box
	 * returns true on success
	 */
	public boolean clickCancelDeletePopUp(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_delete_dialogue_cancel");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				webElement.click();
				flag = !isDeleteDialoguePresent();
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelDeletePopUp<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method clicks on 'Eliminar' button in the 'Delete notes' dialogue box
	 * returns true on success
	 */
	public boolean clickEliminarButton(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_delete_dialogue_eliminar");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				webElement.click();
				flag = true;
				Thread.sleep(2000);
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickEliminarButton<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method closes the 'Delete note' dialogue box
	 * returns true on success
	 */
	public boolean closeDeletePopUp(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_delete_dialogue_close");
			WebElement webElement = elementhandler.getElement(stringText);
			if(webElement.isDisplayed()){
				webElement.click();
				flag = !isDeleteDialoguePresent();
			}
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : closeDeletePopUp<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method checks for delete note confirmation message
	 * returns true on success
	 */
	public boolean verifyDeleteConfirmation(){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_confirmation_message");
			stringText = elementhandler.getText(stringText);
			flag =	stringText.contains("Su nota ha sido borrada con éxito") ||
					stringText.contains("Nota excluída com sucesso.");	
			}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : verifyDeleteConfirmation<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}
	

	
	 /*
	 * This method verifies the text of an element, and returns true on success
	 */
	 public boolean verifyElementTextInAnnotations(String element,String expectedText)
	 {
		boolean flag=false;
		String actualText=null;
		try{
			actualText = elementhandler.getText(PropertiesRepository.getString("com.trgr.maf."+BaseTest.productUnderTest + "."+element));
			if(actualText.contains(expectedText))
				flag = true;
		}catch(NoSuchElementException exc){
			extentLogger.log(LogStatus.INFO, "Error in : verifyElementTextInAnnotations<br>"+displayErrorMessage(exc));
			flag=false;
		}
		return flag; 
	 }
	
	/*
	 * This method can be used to click on a web element
	 * returns true on success
	 */
	public boolean clickWebElement(String element){
		
		boolean flag = false;
		try{
			String stringText = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+"."+element);
			WebElement webElement = elementhandler.getElement(stringText);
			webElement.click();
			flag = true;
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : clickWebElement<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

}
