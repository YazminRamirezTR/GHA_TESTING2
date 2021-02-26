package com.trgr.quality.maf.pages;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;

public class HistoryPage extends BasePage {

	public HistoryPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);
		WebDriverFactory.waitForElementUsingWebElement(driver, elementhandler.getElement(
				PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".historylinkverify")),
				20);
	}

	public boolean isHistoryLinkvalidated() {
	boolean historyLinkvalidated = false;
		try {
			String historyLinkText = elementhandler.getText(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".historylinkverify"));

			historyLinkvalidated = historyLinkText.equalsIgnoreCase("Historial");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isHistoryLinkAvailable <br>" + displayErrorMessage(exc));
			historyLinkvalidated = false;
		}
		return historyLinkvalidated;
	}

	public boolean validateHistoryPage(String colname) throws InterruptedException {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".allcolumnsofhistorypage");
			List<WebElement> allcols = elementhandler.findElements(locator);

			for (int i = 0; i < allcols.size(); i++) {
				String name = allcols.get(i).getText();
				if (name.contains(colname)) {
					flag = true;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateHistoryPage <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean validatingSeeAllEventsColumns(String colname) {
		boolean flag = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".allcolsofviewalleventstable");
			List<WebElement> allcols = elementhandler.findElements(locator);

			for (int i = 0; i < allcols.size(); i++) {
				String name = allcols.get(i).getText();
				if (name.contains(colname)) {
					flag = true;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : validateHistoryPage <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public void clickHistoryLink() {
		try {
			String locator = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".historylinkverify");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickHistoryLink <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks 'view all events' link on History page
	 */
	public void clickViewAllEvents() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".viewalleventsonhistorypage");
			Thread.sleep(3000);
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickViewAllEvents <br>" + displayErrorMessage(exc));
		}
	}

	public void clickStartNewHistory() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".startanewhistorylink");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickStartNewHistory <br>" + displayErrorMessage(exc));
		}
	}

	public void enterClientID(String Clientid) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".newhistoryenterclientidtextbox");
			elementhandler.writeText(locator, Clientid);
			;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterClientID <br>" + displayErrorMessage(exc));
		}
	}

	public void enterName(String name) {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".newhistoryenternametextbox");
			elementhandler.writeText(locator, name);
			;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterName <br>" + displayErrorMessage(exc));
		}
	}

	public void newHistoryClickSave() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".newhistorysavebutton");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : newHistoryClickSave <br>" + displayErrorMessage(exc));
		}
	}

	public void clickBacktoHistoryPage() {
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".backtohistorypagelink");
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickBacktoHistoryPage <br>" + displayErrorMessage(exc));
		}

	}

	public boolean deleteAllHistory() {
		boolean historyDeleted = false;
		try {
			historyDeleted = clickClearAllHistoryLink() && clickDeleteConfirmButton();
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : deleteAllHistory <br>" + displayErrorMessage(exc));
			historyDeleted = false;
		}
		return historyDeleted;
	}

	/*
	 * This method clicks 'clear all history' link on History list
	 */
	public boolean clickClearAllHistoryLink() {
		try {
			Thread.sleep(3000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearallhistorylink"));
			return true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickClearAllHistoryLink <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method clicks 'Yes' button on Delete History confirmation.
	 */
	public boolean clickDeleteConfirmButton() {
		try {
			Thread.sleep(5000);
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deletehistoryyesbutton"));
			return true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDeleteConfirmButton <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	/*
	 * This method returns the number of events in the History List. returns
	 * integer
	 */
	public int getNumberOfEventsPresent() {
		int numberOfEventsInTable = -1;
		try {
			numberOfEventsInTable = elementhandler
					.findElements(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"))
					.size();
			numberOfEventsInTable -= 1; // Deducting 1 for header
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getNumberOfEventsPresent <br>" + displayErrorMessage(exc));
			numberOfEventsInTable = -1;
		}
		return numberOfEventsInTable;
	}

	/*
	 * This method deletes the 'eventToDelete' from the list of history events
	 * returns true on success
	 */
	public boolean deleteHistoryByName(String eventToDelete) throws InterruptedException {
		boolean historyDeleted = false;
		boolean eventFoundInList = false;
		boolean deleteLinkExistForEvent = false;
		String nameColumXpath = null, deleteColumXpath = null;
		int eventRowNumber = -1;
		try {
			List<WebElement> rows = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));

			for (int rowNum = 2; rowNum <= rows.size(); rowNum++) {
				nameColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='nameHeader']";
				String eventName = elementhandler.findElement(nameColumXpath).getText();
				if (eventName.equals(eventToDelete)) {
					eventFoundInList = true;
					eventRowNumber = rowNum;
					break;
				}
			}

			if (eventFoundInList) {
				deleteColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ eventRowNumber + "]" + "/td[@class='deleteHeader']";
				String deleteLinkText = elementhandler.findElement(deleteColumXpath).getText();
				String deleteLinkExpected = "Borrar";
				if (deleteLinkExpected.equals(deleteLinkText))
					deleteLinkExistForEvent = true;
				else
					extentLogger.log(LogStatus.INFO, "Delete link is not present for the event " + eventToDelete);
			}

			if (deleteLinkExistForEvent) {
				elementhandler.clickElement(deleteColumXpath + "/a");
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".deletehistoryyesbutton"));
				historyDeleted = true;
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : deleteHistoryByName <br>" + displayErrorMessage(exc));
			historyDeleted = false;
		}
		return historyDeleted;

	}

	/*
	 * This method finds the row number of 'eventToFind' from the list of
	 * history events checks in sequential order and returns the first match
	 * returns row number, or -1 on failure.
	 */
	public int getHistoryRowNumber(String eventToFind) throws InterruptedException {
		int eventRowNumber = -1;

		try {
			List<WebElement> rows = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			for (int rowNum = 2; rowNum <= rows.size(); rowNum++) {
				String nameColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='nameHeader']";
				String eventName = elementhandler.findElement(nameColumXpath).getText();
				if (eventName.equals(eventToFind)) {
					eventRowNumber = rowNum;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getHistoryRowNumber <br>" + displayErrorMessage(exc));
			eventRowNumber = -1;
		}
		return eventRowNumber;

	}

	/*
	 * This method finds the presence of 'Delete' link for the item in list of
	 * history events returns true on success
	 */
	public boolean isDeleteLinkPresentForItem(int eventRowNumber) throws InterruptedException {
		boolean deleteLinkExistForEvent = false;

		try {
			String deleteColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='deleteHeader']";
			String deleteLinkText = elementhandler.findElement(deleteColumXpath).getText();
			String deleteLinkExpected = "Borrar";
			if (deleteLinkExpected.equals(deleteLinkText))
				deleteLinkExistForEvent = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isDeleteLinkPresentForItem <br>" + displayErrorMessage(exc));
			deleteLinkExistForEvent = false;
		}
		return deleteLinkExistForEvent;
	}

	/*
	 * This method finds the presence of 'Reset' link for the item in list of
	 * history events returns true on success
	 */
	public boolean isResetLinkPresetForItem(int eventRowNumber) {
		boolean resetLinkExistForEvent = false;

		try {
			String resetColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='lifetimeHeader']";
			String resetLinkText = elementhandler.findElement(resetColumXpath).getText();
			String resetLinkExpected[] = { "Reiniciar", "Resetear", "Reset" };
			if (resetLinkText != null)
				for (String expectedText : resetLinkExpected)
					if (resetLinkText.endsWith(expectedText)) {
						resetLinkExistForEvent = true;
						break;
					}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isResetLinkPresetForItem <br>" + displayErrorMessage(exc));
			resetLinkExistForEvent = false;
		}
		return resetLinkExistForEvent;
	}

	/*
	 * This method finds the presence of '14 Reset' for the item in list of
	 * history events. (reset after 14 days.) returns true on success
	 */
	public boolean isDeleteAfterTwoWeeks(int eventRowNumber) {
		boolean resetToTwoWeek = false;

		try {
			String resetColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='lifetimeHeader']";
			String resetLinkText = elementhandler.findElement(resetColumXpath).getText();
			String resetLinkExpected[] = { "14 Reiniciar", "14 Resetear", "14 Reset" };
			if (resetLinkText != null)
				for (String expectedText : resetLinkExpected)
					if (resetLinkText.contains(expectedText)) {
						resetToTwoWeek = true;
						break;
					}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isResetAfterTwoWeeks <br>" + displayErrorMessage(exc));
			resetToTwoWeek = false;
		}
		return resetToTwoWeek;
	}

	/*
	 * This method clicks 'Reset' link for the item in 'eventRowNumber' for the
	 * list of history events
	 */
	public void clickResetLinkForItem(int eventRowNumber) {
		try {
			String resetColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='lifetimeHeader']/a";
			elementhandler.clickElement(resetColumXpath);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickResetLinkForItem <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks 'Delete' link for the item in 'eventRowNumber' for the
	 * list of history events
	 */
	public void clickDeleteLinkForItem(int eventRowNumber) {
		try {
			String deleteColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='deleteHeader']/a";
					Thread.sleep(3000);
			elementhandler.clickElement(deleteColumXpath);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDeleteLinkForItem <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks if 'eventToFind' exist the list of history events
	 * returns true on success
	 */
	public boolean isHistoryNamePresent(String eventToFind) throws InterruptedException {
		boolean historyFound = false;
		try {
			List<WebElement> rows = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			//System.out.println(rows.size());
			for (int rowNum = 2; rowNum <= rows.size(); rowNum++) {
				String nameColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='nameHeader']";
				String eventName = elementhandler.findElement(nameColumXpath).getText();
				if (eventName.equals(eventToFind)) {
					historyFound = true;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isHistoryItemPresent <br>" + displayErrorMessage(exc));
			historyFound = false;
		}
		return historyFound;
	}

	/*
	 * This method finds the presence of 'Delete' link for the item in list of
	 * history events returns History Name
	 */
	public String getFirstHistoryWithDeleteLink() {
		String firstHistoryWithDeleteLink = null;
		try {
			List<WebElement> rows = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			for (int rowNum = 2; rowNum <= rows.size(); rowNum++) {
				if (isDeleteLinkPresentForItem(rowNum)) {
					String nameColumXpath = PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
							+ rowNum + "]" + "/td[@class='nameHeader']";
					firstHistoryWithDeleteLink = elementhandler.findElement(nameColumXpath).getText();
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : getFirstHistoryWithDeleteLink <br>" + displayErrorMessage(exc));
			firstHistoryWithDeleteLink = null;
		}
		return firstHistoryWithDeleteLink;
	}

	/*
	 * This method returns the name of actual history. (Current history name).
	 */
	public String getActualHistoryName() {
		String actualHistoryName = null;
		try {
			List<WebElement> rowsList = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			for (int rowNum = 2; rowNum <= rowsList.size(); rowNum++) {
				String viewColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='viewHeader']";
				String viewName = elementhandler.findElement(viewColumXpath).getText();
				String expectedViewName = "(Actual)";

				if (viewName.contains(expectedViewName)) {
					actualHistoryName = getHistoryNameAtRow(rowNum, rowsList);
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getActualHistoryName <br>" + displayErrorMessage(exc));
		}
		return actualHistoryName;
	}

	/*
	 * This method finds the name of the event for 'rowNum' in the list of
	 * history events returns name, or null on failure.
	 */
	public String getHistoryNameAtRow(int rowNum, List<WebElement> rowsList) {
		String historyName = null;

		try {
			if ((rowNum >= 2) && (rowNum <= rowsList.size())) {
				String nameColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='nameHeader']";
				historyName = elementhandler.findElement(nameColumXpath).getText();
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getHistoryNameAtRow <br>" + displayErrorMessage(exc));
		}
		return historyName;
	}

	/*
	 * This method finds the name of the event for 'rowNum' in the list of
	 * history events returns name, or null on failure.
	 */
	public String getHistoryNameAtRow(int rowNum) {
		String historyName = null;

		try {
			List<WebElement> rowsList = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			if ((rowNum >= 2) && (rowNum <= rowsList.size())) {
				String nameColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='nameHeader']";
				historyName = elementhandler.findElement(nameColumXpath).getText();
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getHistoryNameAtRow <br>" + displayErrorMessage(exc));
		}
		return historyName;
	}

	/*
	 * This method finds the Client ID of the event for 'rowNum' in the list of
	 * history events returns name, or null on failure.
	 */
	public String getClientIDAtRow(int rowNum) {
		String clientID = null;

		try {
			List<WebElement> rowsList = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			if ((rowNum >= 2) && (rowNum <= rowsList.size())) {
				String nameColumXpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
						+ rowNum + "]" + "/td[@class='clientIdHeader']";
				clientID = elementhandler.findElement(nameColumXpath).getText();
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : getClientIDAtRow <br>" + displayErrorMessage(exc));
		}
		return clientID;
	}

	/*
	 * This method finds the total number of events in the list of history
	 * events returns number
	 */
	public int countOfHistoryItems() {
		int totalItems = 0;
		try {
			List<WebElement> rowsList = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable"));
			totalItems = rowsList.size() - 1;

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : countOfHistoryItems <br>" + displayErrorMessage(exc));
		}
		return totalItems;
	}

	/*
	 * This method clicks 'Rename' link for the item in 'eventRowNumber' for the
	 * list of history events
	 */
	public void clickRenameLinkForItem(int eventRowNumber) {
		try {
			String renameColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='renameTrail']/a";
			elementhandler.clickElement(renameColumXpath);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickRenameLinkForItem <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks the presence of 'Rename' title in the Rename History
	 * page
	 */
	public boolean isRenameHistoryTitlePresent() {
		boolean titlePresent = false;
		try {
			boolean elementPresent = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rename_history_title")));
			if (elementPresent) {
				String title = elementhandler.getText(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rename_history_title"));
				if (title != null && title.contains("Renombrar"))
					titlePresent = true;
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isRenameHistoryTitlePresent <br>" + displayErrorMessage(exc));
		}
		return titlePresent;
	}

	/*
	 * This method writes the name in the edit box returns true on success
	 */
	public boolean writeRenameText(String name) {

		boolean flag = false;
		try {
			elementhandler.writeText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rename_history_name_inputbox"), name);
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : writeRenameText<br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method clicks 'Save' button in Rename History
	 */
	public void clickSaveRenameButton() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rename_history_save_button"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSaveRenameButton <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks 'Cancel' button in Rename History
	 */
	public void clickCancelRenameButton() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".rename_history_cancel_button"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCancelRenameButton <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method checks if the 'List Of Events' are displayed or not returns
	 * true on success
	 */
	public boolean isAllEventsListDisplayed() {
		boolean eventsSeeColumnPresent = false;
		try {
			eventsSeeColumnPresent = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".seecolumnheaderofalleventstable")));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isAllEventsListDisplayed <br>" + displayErrorMessage(exc));
		}
		return eventsSeeColumnPresent;
	}

	/*
	 * This method checks if the details of single history is displayed or not
	 * returns true on success
	 */
	public boolean isViewAllHistoryLinkDisplayed() {
		boolean viewAllLinkPresent = false;
		try {
			viewAllLinkPresent = WebDriverFactory.isDisplayed(driver, elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".viewalleventsonhistorypage")));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isViewAllHistoryLinkDisplayed <br>" + displayErrorMessage(exc));
		}
		return viewAllLinkPresent;
	}

	/*
	 * This method checks if the type of first column is document or not.
	 * returns true on success
	 */
	public boolean isFirstTrailEqualsDocument() {
		boolean eventTypeIsDocument = false;
		try {
			List<WebElement> typeColumn = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".view_history_table_column_type"));
			String columnType = typeColumn.get(0).getAttribute("class");
			if (columnType.equals("DOCUMENT"))
				eventTypeIsDocument = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isFirstTrailEqualsDocument <br>" + displayErrorMessage(exc));
		}
		return eventTypeIsDocument;
	}

	/*
	 * This method checks if the type of type is 'document' or not. returns true
	 * on success
	 */
	public boolean isTrailTypeEqualsDocument(int rowNum) {
		boolean eventTypeIsDocument = false;
		try {
			List<WebElement> typeColumn = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".view_history_table_column_type"));
			if (rowNum >= 1 && rowNum <= typeColumn.size()) {
				String columnType = typeColumn.get(rowNum - 1).getAttribute("class");
				eventTypeIsDocument = columnType.equals("DOCUMENT");
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isTrailTypeEqualsDocument <br>" + displayErrorMessage(exc));
		}
		return eventTypeIsDocument;
	}

	/*
	 * This method clicks the link present in 'Action' column for the first
	 * document. returns DocumentDisplayPage
	 */
	public DocumentDisplayPage clickGoToDocument(int rowNumber) {
		DocumentDisplayPage docPage = null;

		try {
			if (isTrailTypeEqualsDocument(1)) {
				List<WebElement> actionColumn = elementhandler.findElements(PropertiesRepository.getString(
						"com.trgr.maf." + BaseTest.productUnderTest + ".view_history_table_column_action") + "/a");
				actionColumn.get(0).click();
				Thread.sleep(1000);
				docPage = new DocumentDisplayPage(driver);
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickGoToDocument <br>" + displayErrorMessage(exc));
		}
		return docPage;
	}

	/*
	 * This method checks if the description value matches with the expected
	 * value returns true on success
	 */
	public boolean isEqualsFirstTrailDescription(String expectedDescription) {
		boolean descriptionMatchFound = false;
		try {
			List<WebElement> typeColumn = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".view_history_table_column_description"));
			String actualDescription = typeColumn.get(0).getText();
			descriptionMatchFound = actualDescription.equals(expectedDescription.trim());
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isEqualsFirstTrailDescription <br>" + displayErrorMessage(exc));
			descriptionMatchFound = false;
		}
		return descriptionMatchFound;
	}

	/*
	 * This method clicks 'Rename' link for the item in 'eventRowNumber' for the
	 * list of history events
	 */
	public void clickViewLinkForItem(int eventRowNumber) {
		try {
			String viewColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='viewHeader']/a";
			elementhandler.clickElement(viewColumXpath);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickViewLinkForItem <br>" + displayErrorMessage(exc));
		}
	}

	/*
	 * This method clicks 'Rename' link for the item 'itemName' in list of
	 * history events
	 */
	public void clickViewLinkForItem(String itemName) {
		try {
			int eventRowNumber = getHistoryRowNumber(itemName);
			String viewColumXpath = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".allrowsofalleventstable") + "["
					+ eventRowNumber + "]" + "/td[@class='viewHeader']/a";
			elementhandler.clickElement(viewColumXpath);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickViewLinkForItem <br>" + displayErrorMessage(exc));
		}
	}

	// This method checks the logged in Client ID

	public boolean verifyClientID(String clientid) {
		boolean flag = false;
		try {

			String clientidcolxpath = null;
			String date1 = null;
			String date = HistoryDateColumn();

			List<WebElement> rows = elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".view_history_details_table_body"));

			for (int i = 2; i < rows.size(); i++) {
				clientidcolxpath = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".viewallhistorypage_clientidcolumn");
				String cid = elementhandler.findElement(clientidcolxpath).getText();

				date1 = PropertiesRepository.getString(
						"com.trgr.maf." + BaseTest.productUnderTest + ".viewallhistorypage_lastaccessedcolumn");
				String date2 = elementhandler.findElement(date1).getText();

				if ((cid != null) && (cid == clientid && date2 == date)) {
					extentLogger.log(LogStatus.INFO, "Client ID Available");
					flag = true;
					break;
				}
			}
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickViewLinkForItem <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;

	}

	public String HistoryDateColumn() {
		Date date = new Date();
		DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

		String date1 = dateformat.format(date);
		//System.out.println(date1);
		return date1;
	}
	
	public String getUniqueHistoryName(String name)

	{
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmss");
		String datetime = ft.format(dNow);
		return name + datetime;
	}
	
}
