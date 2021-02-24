package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.handlers.BaseHandler;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class MyDocumentPage extends BasePage {

	BaseHandler basehandler;
	boolean flag = false;
	String deletefoldername;
	String renamefoldername;

	public MyDocumentPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		WebElement newFolderBtnElement = elementhandler.findElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".createfolder"));
		WebDriverFactory.waitForElementUsingWebElement(driver, newFolderBtnElement, 30);
		basehandler = new BaseHandler(driver);
	}

	// This method is used to verify basic folders present in My Document Page
	
	public boolean isBasicFoldersPresent() {
		try {

			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mycasefolder")), 40);
			if ((elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".myclientfolder"))
					.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mycasefolder"))
							.isDisplayed())
					&& (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentfolder"))
							.isDisplayed()))
				flag = true;
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isBasicFoldersPresent <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}


	// This method is used to save folder  while creating new folder in my document page.
	public void saveFolder() {
		try {

			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".savenewfoldername")),
					30);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".savenewfoldername"));
			Thread.sleep(2000);

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : saveFolder <br>" + displayErrorMessage(e));

		}
	}

	// This method is used to enter folder name while creating new folder in my document page.
	public void enterFolderName(String expfoldername) {
		try {

			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newfoldername")),
					30);
			elementhandler
			.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".newfoldername"))
			.sendKeys(expfoldername);
			Thread.sleep(2000);

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : enterFolderName <br>" + displayErrorMessage(e));

		}
	}


	// This method is used to click on CreateFolder icon in the my document page
	public void clickCreateFolder() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".createfolder")), 30);
			// Element is visible but not clickable . So we have to wait few more seconds
			Thread.sleep(2000);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".createfolder"));

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCreateFolder <br>" + displayErrorMessage(e));

		}
	}

	
	// This method is used to verify folder exist in my Document folder
	public boolean isFolderExistInMyDocumentFolder(String expectedname) {
		boolean flag = false;
		try {
			Thread.sleep(3000);//sleep is required - not loading all elements
			List<WebElement> foldernames = basehandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentfolderlist"));
			for (int i = 0; i < foldernames.size(); i++) {
				String actualfoldername = foldernames.get(i).getText();
				if (actualfoldername.contains(expectedname)) {
					flag = true;
					break;
				}

			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFolderExistInMyDocumentFolder <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean isListOfFoldersVisibile() {
		boolean flag = false;
		try {
			Thread.sleep(3000);
			List<WebElement> foldernames = basehandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentfolderlist"));
			for (int i = 0; i < foldernames.size(); i++) {				
				 flag= foldernames.get(i).isDisplayed();
				}

		

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isListOfFoldersVisibile <br>" + displayErrorMessage(e));
			flag= false;
		}
		return flag;
	}
	
	
	public boolean isMyDocumentFoldercollapsed()
	{
		boolean flag=false;
		try{
			
			String locator=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydoc_expand");
			flag= elementhandler.findElement(locator).getAttribute("class").contains("jstree-closed");
			
		}catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isMyDocumentFoldercollapsed <br>" + displayErrorMessage(e));
			flag= false;
		}
		return flag;
	}
	
	public boolean isMyDocumentFolderExpanded()
	{
		boolean flag=false;
		try{
			
			String locator=PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydoc_expand");
			flag= elementhandler.findElement(locator).getAttribute("class").contains("jstree-open");
			
		}catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isMyDocumentFolderExpanded <br>" + displayErrorMessage(e));
			flag= false;
		}
		return flag;
	}
// This method is used to verify document added to my document folder.
	public boolean isGivenDocumentSavedInFolder(String expectedname) {
		boolean flag = false;
		try {
			Thread.sleep(5000);
			List<WebElement> folderList = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows"));

			for (int row = 1; row <= folderList.size(); row++) {
				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows") + "[" + row + "]"
						+ "/td[2]/a/span";
				String actualtitlename = elementhandler.findElement(locator).getText();

				if (actualtitlename.trim().equals(expectedname.trim())) {

					flag = true;
					break;
				}

			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyFolderExistInMyDocumentFolder <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	
	
	//This method is used to click on Paper bin folder

	public void clickOnPaperbin() {
		try {

			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".paperbin"));
			Thread.sleep(2000);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnPaperbin <br>" + displayErrorMessage(e));
		}
	}

	

	public void clickOnRenameFolder() {
		try {
			Thread.sleep(1000);
			if (elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renamefolder"))
					.isDisplayed()) {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renamefolder"));
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRenameFolder <br>" + displayErrorMessage(e));
		}
	}

	public void enterRenameInput(String renameinput) {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renameinput")), 20);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renameinput")));
			elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renameinput")).clear();
			elementhandler.writeText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renameinput"),
					renameinput);

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : enterRenameInput <br>" + displayErrorMessage(e));
		}
	}

	public void saveRename() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renamesave");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
			Thread.sleep(1000);

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : saveRename <br>" + displayErrorMessage(e));
		}
	}

	public void selectspecificFolder(String foldername) {

		try {
			List<WebElement> foldernamelist = basehandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentfolderlist"));
			for (int i = 0; i < foldernamelist.size(); i++) {
				String subfoldername = foldernamelist.get(i).getText();
				if (subfoldername.trim().equals(foldername)) {
					
					JavascriptExecutor js =(JavascriptExecutor) driver;
					js.executeScript("arguments[0].click()", foldernamelist.get(i));
					break;
				}
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : selectFirstFolder <br>" + displayErrorMessage(e));
		}

	}

	public boolean isMyDocumentTextDisplayed() {
		try {
			return elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumenttext"))
					.isDisplayed();
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isMyDocumentTextDisplayed <br>" + displayErrorMessage(e));
			return false;
		}
	}

	public void expandMyDocumentFolder() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentfolder"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : expandMyDocumentFolder <br>" + displayErrorMessage(e));

		}
	}

	

	

	// This method is used to select a specific folder. 
	public void checkSpecificFolder(String expectedfoldername) {
		try {
			List<WebElement> folderList = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows"));
			Thread.sleep(3000);
			for (int row = 1; row <= folderList.size(); row++) {
				String movefoldertext = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows") + "[" + row + "]"
						+ "/td[2]/a/span";
				String movedfoldername = elementhandler.findElement(movefoldertext).getText();

				if (movedfoldername.trim().equals(expectedfoldername.trim())) {
					String movefoldercheckbox = PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows") + "[" + row
							+ "]" + "/td[1]/span";
					
					((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
							elementhandler.findElement(movefoldercheckbox));
					elementhandler.clickElement(movefoldercheckbox);
					
				}
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : checkSpecificFolder <br>" + displayErrorMessage(e));
		}
	}
	
	public void clickOnActionManager()
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionmanager")), 30);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionmanager"));
			Thread.sleep(2000);
		}catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnActionManager <br>" + displayErrorMessage(e));
		}
	}
	
	public void clickOnMoveFolderLink()
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".movefolder")), 30);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".movefolder"));
			Thread.sleep(1000);
		}catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnMoveFolderLink <br>" + displayErrorMessage(e));
		}
	}
	
	public void SelectFolderToMove()
	{
		try{
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".foldertobemoved"));
			
		}catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : SelectFolderToMove <br>" + displayErrorMessage(e));
		}
	}
	
	public void clickOnMoveButton()
	{
		try{
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".movebutton"));
			Thread.sleep(5000);
			
		}catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnMoveButton <br>" + displayErrorMessage(e));
		}
	}
	
	// This method is used to click on My Notes Folder
	public void clickMyNotes() {
		try {
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mynotes")),
					20);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mynotes")));
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mynotes"));
			Thread.sleep(2000);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickMyNotes <br>" + displayErrorMessage(e));
		}
	}
	public boolean isMisDocumentLinkPresent(String Carpeta2) {
		boolean flag = false;
		WebElement webElement=null;
		try{
		
		//String	docNote = PropertiesRepository.getString("com.trgr.maf."+ BaseTest.productUnderTest+".annotations_docnote_first_note");
			webElement=	driver.findElement(By.xpath("//a[@class='jstree-clicked']"));
			String actual = webElement.getText();
			
					if(actual.contains(Carpeta2))
					{
						flag = true;						
					}
					
				
		}catch(Exception exc){
			extentLogger.log(LogStatus.INFO, "Error in : isMisDocumentLinkPresent<br>"+displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void clickOnMyDocument() {
		try {
			Thread.sleep(1000);
			WebDriverFactory.waitForElementUsingWebElement(driver,
					elementhandler.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentfolder")),
					30);	
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					elementhandler.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentfolder")));
			//elementhandler.clickElement(
				//	PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mydocumentfolder"));
			Thread.sleep(4000);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnMyDocument <br>" + displayErrorMessage(e));
		}
	}

	
	
	

	

	public boolean ExpandAndCollapse() {
		boolean flag = false;
		try {

			Thread.sleep(5000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandbutton")), 50);
			
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandbutton")));
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandbutton"));
			Thread.sleep(5000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".collapsebutton")),
					50);
			Thread.sleep(5000);
			if ((elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".collapsebutton"))
					.isDisplayed())) {
				collapseFolder();
				flag = true;
			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : ExpandAndCollapse <br>" + displayErrorMessage(e));
		}
		return flag;
	}

	public void collapseFolder() {
		try {
			Thread.sleep(5000);
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".collapsebutton")),
					50);
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".collapsebutton"));
			Thread.sleep(5000);
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : collapseFolder <br>" + displayErrorMessage(e));
		}

	}

	public boolean verifyNoteDisplayed(String exp_doc, String docNote) {
		boolean flag = false;
		try {
			List<WebElement> rows = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows"));

			for (int i = 1; i < rows.size(); i++) {
				String doc_path = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows") + "[" + i + "]"
						+ "/td[2]/a/span";
				String actual_doc = elementhandler.findElement(doc_path).getText();
				//System.out.println(actual_doc);
				//System.out.println(exp_doc);
				if (actual_doc.equals(exp_doc.trim())) {
					elementhandler.clickElement(doc_path);
					List<WebElement> note_list = elementhandler.findElements(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".addednotetext"));
					for (int j = 0; j < note_list.size(); j++) {
						String addednote_text = note_list.get(j).getText();
						//System.out.println(addednote_text);
						//System.out.println(docNote);
						if (addednote_text.equals(docNote)) {
							flag = true;
							break;
						}
					}
					break;
				}
			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : verifyNoteDisplayed <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean isNoteDocumentDisplayedInMyDocuments(String exp_doc) {
		boolean noteDisplayed = false;
		try {
			List<WebElement> rows = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows"));

			for (int i = 1; i <= rows.size(); i++) {
				String doc_path = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows") + "[" + i + "]"
						+ "/td[2]/a/span";
				String actual_doc = elementhandler.findElement(doc_path).getText();
				if (actual_doc.trim().equals(exp_doc.trim())) {
					noteDisplayed = true;
					break;
				}
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isNoteDisplayedInMyDocuments <br>" + displayErrorMessage(e));
			noteDisplayed = false;
		}
		return noteDisplayed;
	}

	public DocumentDisplayPage clickDocumentInMyDocuments(String exp_doc) {
		DocumentDisplayPage docDisplayPage = null;
		try {
			List<WebElement> rows = elementhandler.findElements(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows"));
			for (int i = 1; i <= rows.size(); i++) {
				String doc_path = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".actionfolderrows") + "[" + i + "]"
						+ "/td[2]/a/span";
				String actual_doc = elementhandler.findElement(doc_path).getText().trim();
				if (actual_doc.equals(exp_doc.trim())) {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
							elementhandler.getElement(doc_path));
					elementhandler.clickElement(doc_path);
					docDisplayPage = new DocumentDisplayPage(driver);
					break;
				}
			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDocumentInMyDocuments <br>" + displayErrorMessage(e));

		}
		return docDisplayPage;
	}

	// This method is used to delete folder or document in my document page. This method is applicable to chparg,chpury and chppy only

	public void deleteSubFolderInMyDocument(String expfolder) {

		try {
			Thread.sleep(1000);
			String deleteImageTitle= PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deleteimagetitlevalue");
			String locator="xpath=//*[normalize-space(text())='"+expfolder+"']/..//*[@title='"+deleteImageTitle+"']";			
			String selector = "xpath=//*[normalize-space(text())='"+expfolder+"']";
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(selector));
			//Thread.sleep(3000);
			WebElement folderElement = elementhandler.findElement(selector);
			new Actions(driver).moveToElement(folderElement).moveToElement(elementhandler.getElement(selector)).click(elementhandler.getElement(locator)).build().perform();
//			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
//					elementhandler.getElement(selector));
			if(IsPopUpWindowPresent()){
				clickOnAlertPopUP();
			}
			else{
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".delete"));
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : deleteSubFolderInMyDocument <br>" + displayErrorMessage(e));
			
		}
	}

	
	// This method is used to select the folder to rename
	public void selectFolderToRename(String expfolder) {
		try {
			Thread.sleep(3000);
			String renameImageTitle = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".renameimagetitlevalue");
			String locator = "xpath=//span[normalize-space(text())='" + expfolder + "']/..//*[@title='"
					+ renameImageTitle + "']";
			String selector = "xpath=//*[normalize-space(text())='" + expfolder + "']";
			
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
					elementhandler.getElement(selector));
			WebElement folderElement = elementhandler.findElement(selector);
			new Actions(driver).moveToElement(folderElement).moveToElement(elementhandler.getElement(locator)).build().perform();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();",
					elementhandler.getElement(locator));
			
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : selectFolderToRename <br>" + displayErrorMessage(e));

		}
	}

	
	public void clickDeleteLink()
	{
		try{
			WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".delete")), 30);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".delete"));
			if(IsPopUpWindowPresent()){
				clickOnAlertPopUP();
				Thread.sleep(5000);
			}
		}catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDeleteLink <br>" + displayErrorMessage(e));
		}
	}
	
	// This method is used to verify folder is exist in my Notes folder
	public boolean isFolderExistInMyNotesFolder(String expectedname) {
		boolean flag = false;
		try {
			List<WebElement> foldernames = basehandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".mynotesfolderlist"));
			for (int i = 0; i < foldernames.size(); i++) {
				String actualfoldername = foldernames.get(i).getAttribute("title");
				if (actualfoldername.contains(expectedname)) {
					flag = true;
				}

			}

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isFolderExistInMyNotesFolder <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}
	
}