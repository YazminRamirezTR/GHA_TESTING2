package com.trgr.quality.maf.pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import com.trgr.quality.maf.webdriver.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Main Class for Tools Page This class defines all methods for 'ToolsPage'
 * 
 * @author Sarath Manoharam
 * @version 1.1
 * @since December 13, 2016
 */
public class ToolsPage extends BasePage {

	public ToolsPage(WebDriver driver) throws IOException, IllegalArgumentException {
		super(driver);

	}

	public boolean isGivenErrorMsgDisplayed(String errormessage) {
		// TODO Auto-generated method stub
		return false;
	}

	// verifying Tools Tab is displayed in the main tab and then clicks on it
	public boolean isToolsTabDisplayed() {
		boolean flag = false;

		try {
			if (elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolstab")) != null) {
				flag = true;
			} else
				flag = false;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isToolsTabDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	// Clicks on Tools Tab present in the main tab
	public void clickToolsTab() throws InterruptedException {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolstab"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickToolsTab <br>" + displayErrorMessage(exc));
		}
	}

	// Verify the display of any widget in Tools. (Accepts widget name, &
	// element locator as argument).
	public boolean isToolsWidgetDisplayed(String widgetName, String elementLocator) {

		boolean flag = false;
		String text = null;

		try {
			text = elementhandler.getText(PropertiesRepository.getString(elementLocator));
			if (text.toString().equals(widgetName.toString()))
				flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isToolsWidgetDisplayed <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	// Verify the display of all widgets in Tools Page
	public boolean isAllWidgetsDisplayed() {
		boolean flag = false;

		switch (BaseTest.productUnderTest) {

		case "chparg":

			flag = isToolsWidgetDisplayed("TIP", "com.trgr.maf." + BaseTest.productUnderTest + ".tip")
					&& isToolsWidgetDisplayed("Bienes Personales",
							"com.trgr.maf." + BaseTest.productUnderTest + ".bienes_personales")
					&& isToolsWidgetDisplayed("Checklist", "com.trgr.maf." + BaseTest.productUnderTest + ".checklist")
					&& isToolsWidgetDisplayed("Búsqueda permanente y Primera Hora",
							"com.trgr.maf." + BaseTest.productUnderTest + ".búsqueda_permanente_y_primera_hora");
			break;

		case "chpmex":

			flag = isToolsWidgetDisplayed("Tablas Inteligentes Personalizadas",
					"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas")
					&& isToolsWidgetDisplayed("Checklist", "com.trgr.maf." + BaseTest.productUnderTest + ".checklist")
					&& isToolsWidgetDisplayed("Primera Hora",
							"com.trgr.maf." + BaseTest.productUnderTest + ".primera_hora");
			break;

		case "chpury":

			flag = isToolsWidgetDisplayed("Calculadores", "com.trgr.maf." + BaseTest.productUnderTest + ".calculadores")
					&& isToolsWidgetDisplayed("Formularios",
							"com.trgr.maf." + BaseTest.productUnderTest + ".formularios")
					&& isToolsWidgetDisplayed("Indicadores",
							"com.trgr.maf." + BaseTest.productUnderTest + ".indicadores")
					&& isToolsWidgetDisplayed("Tablas inteligentes personalizadas",
							"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas")
					&& isToolsWidgetDisplayed("Búsqueda permanente",
							"com.trgr.maf." + BaseTest.productUnderTest + ".búsqueda_permanente")
					&& isToolsWidgetDisplayed("Primera Hora",
							"com.trgr.maf." + BaseTest.productUnderTest + ".primera_hora");
			break;

		case "chppy":

			flag = isToolsWidgetDisplayed("Calculadores", "com.trgr.maf." + BaseTest.productUnderTest + ".calculadores")
					&& isToolsWidgetDisplayed("Tablas inteligentes personalizadas",
							"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas")
					&& isToolsWidgetDisplayed("Tablas inteligentes personalizadas",
							"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas_2")
					&& isToolsWidgetDisplayed("Búsqueda permanente",
							"com.trgr.maf." + BaseTest.productUnderTest + ".búsqueda_permanente")
					&& isToolsWidgetDisplayed("Primera Hora",
							"com.trgr.maf." + BaseTest.productUnderTest + ".primera_hora");
			break;

		case "chppe":

			flag = isToolsWidgetDisplayed("Herramientas",
					"com.trgr.maf." + BaseTest.productUnderTest + ".herramientas");
			break;

		case "chpchile":

			flag = isToolsWidgetDisplayed("Herramientas",
					"com.trgr.maf." + BaseTest.productUnderTest + ".herramientas")
			;
			break;
			
		case "chpbr":

			flag = isToolsWidgetDisplayed("Tabelas Inteligentes",
					"com.trgr.maf." + BaseTest.productUnderTest + ".tabelas_inteligentes")
					&& isToolsWidgetDisplayed("Ferramentas de Consulta",
							"com.trgr.maf." + BaseTest.productUnderTest + ".ferramentas_de_consulta")
					&& isToolsWidgetDisplayed("Calculadoras",
							"com.trgr.maf." + BaseTest.productUnderTest + ".calculadoras")
					&& isToolsWidgetDisplayed("Downloads",
							"com.trgr.maf." + BaseTest.productUnderTest + ".downloads_tool")
					&& isToolsWidgetDisplayed("NESH", "com.trgr.maf." + BaseTest.productUnderTest + ".nesh_tool")
					&& isToolsWidgetDisplayed("TIPI", "com.trgr.maf." + BaseTest.productUnderTest + ".tipi_tool");
			break;
		}

		return flag;
	}

	/*
	 * Verify the collapse functionality of any widget in Tools. Accepts widget
	 * name and Locator name as arguments. return true after collapsing Expanded
	 * Widget, OR returns true if already collapsed
	 */
	public boolean collapseWidget(String widgetName, String elementLocator) throws InterruptedException {
		boolean flag = false;

		try {
			if (elementhandler.getElement(PropertiesRepository.getString(elementLocator + "_maximized")) != null) {
				elementhandler.clickElement(PropertiesRepository.getString(elementLocator + "_toggle"));
				flag = true;
			} else if (elementhandler
					.getElement(PropertiesRepository.getString(elementLocator + "_minimized")) != null) {
				flag = true;
			}

			Thread.sleep(1000);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : collapseWidget <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	// Verify the collapse functionality of all widgets in Tools Page
	public boolean collapseAllWidgets() {
		boolean flag = false;
		try {
			switch (BaseTest.productUnderTest) {
			case "chparg":

				flag = collapseWidget("TIP", "com.trgr.maf." + BaseTest.productUnderTest + ".tip")
						&& collapseWidget("Bienes Personales",
								"com.trgr.maf." + BaseTest.productUnderTest + ".bienes_personales")
						&& collapseWidget("Checklist", "com.trgr.maf." + BaseTest.productUnderTest + ".checklist")
						&& collapseWidget("Búsqueda permanente y Primera Hora",
								"com.trgr.maf." + BaseTest.productUnderTest + ".búsqueda_permanente_y_primera_hora")
						&& collapseWidget("Calculadores", "com.trgr.maf." + BaseTest.productUnderTest + ".calculadores");
				break;

			case "chpmex":

				flag = collapseWidget("Tablas Inteligentes Personalizadas",
						"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas")
						&& collapseWidget("Checklist", "com.trgr.maf." + BaseTest.productUnderTest + ".checklist")
						&& collapseWidget("Primera Hora",
								"com.trgr.maf." + BaseTest.productUnderTest + ".primera_hora");
				break;

			case "chpury":

				flag = collapseWidget("Calculadores", "com.trgr.maf." + BaseTest.productUnderTest + ".calculadores")
						&& collapseWidget("Formularios", "com.trgr.maf." + BaseTest.productUnderTest + ".formularios")
						&& collapseWidget("Indicadores", "com.trgr.maf." + BaseTest.productUnderTest + ".indicadores")
						&& collapseWidget("Tablas inteligentes personalizadas",
								"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas")
						&& collapseWidget("Búsqueda permanente",
								"com.trgr.maf." + BaseTest.productUnderTest + ".búsqueda_permanente")
						&& collapseWidget("Primera Hora",
								"com.trgr.maf." + BaseTest.productUnderTest + ".primera_hora");
				break;

			case "chppy":

				flag = collapseWidget("Calculadores", "com.trgr.maf." + BaseTest.productUnderTest + ".calculadores")
						&& collapseWidget("Tablas inteligentes personalizadas",
								"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas")
						&& collapseWidget("Tablas inteligentes personalizadas",
								"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas_2")
						&& collapseWidget("Búsqueda permanente",
								"com.trgr.maf." + BaseTest.productUnderTest + ".búsqueda_permanente")
						&& collapseWidget("Primera Hora",
								"com.trgr.maf." + BaseTest.productUnderTest + ".primera_hora");
				break;

			case "chppe":

				flag = collapseWidget("Herramientas", "com.trgr.maf." + BaseTest.productUnderTest + ".herramientas")
						;
				break;
				
			case "chpchile":

				flag = collapseWidget("Herramientas", "com.trgr.maf." + BaseTest.productUnderTest + ".herramientas")
						&& collapseWidget("aplicacion-practica", "com.trgr.maf." + BaseTest.productUnderTest + ".practicalapplication");
				break;

			case "chpbr":

				flag = collapseWidget("Tabelas Inteligentes",
						"com.trgr.maf." + BaseTest.productUnderTest + ".tabelas_inteligentes")
						&& collapseWidget("Ferramentas de Consulta",
								"com.trgr.maf." + BaseTest.productUnderTest + ".ferramentas_de_consulta")
						&& collapseWidget("Calculadoras", "com.trgr.maf." + BaseTest.productUnderTest + ".calculadoras")
						&& collapseWidget("Downloads", "com.trgr.maf." + BaseTest.productUnderTest + ".downloads_tool")
						&& collapseWidget("NESH", "com.trgr.maf." + BaseTest.productUnderTest + ".nesh_tool")
						&& collapseWidget("TIPI", "com.trgr.maf." + BaseTest.productUnderTest + ".tipi_tool");
				break;
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : collapseAllWidgets <br>" + displayErrorMessage(exc));
		}

		return flag;
	}

	/*
	 * Verify the expand functionality of any widget in Tools. Accepts widget
	 * name and element locator as arguments. return true after expanding
	 * Widget, OR returns true if already expanded.
	 */
	public boolean expandWidget(String widgetName, String elementLocator) throws InterruptedException {
		boolean flag = false;

		try {
			if (elementhandler.getElement(PropertiesRepository.getString(elementLocator + "_minimized")) != null) 
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						elementhandler.findElement(PropertiesRepository.getString(elementLocator + "_minimized")));
				elementhandler.clickElement(PropertiesRepository.getString(elementLocator + "_toggle"));
				Thread.sleep(2000);
				flag = true;
			} else if (elementhandler
					.getElement(PropertiesRepository.getString(elementLocator + "_maximized")) != null) 
			{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						elementhandler.findElement(PropertiesRepository.getString(elementLocator + "_maximized")));
				flag = true;
			}

			Thread.sleep(1000);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : expandWidget <br>" + displayErrorMessage(exc));
			flag = false;
		}

		return flag;
	}

	// Verify the expand functionality of all widgets in Tools Page
	public boolean expandAllWidgets() throws InterruptedException {
		boolean flag = false;
		try{
		switch (BaseTest.productUnderTest) {

		case "chparg":

			flag = expandWidget("TIP", "com.trgr.maf." + BaseTest.productUnderTest + ".tip")
					&& expandWidget("Bienes Personales",
							"com.trgr.maf." + BaseTest.productUnderTest + ".bienes_personales")
					&& expandWidget("Checklist", "com.trgr.maf." + BaseTest.productUnderTest + ".checklist")
					&& expandWidget("Búsqueda permanente y Primera Hora","com.trgr.maf." + BaseTest.productUnderTest + ".búsqueda_permanente_y_primera_hora")
					&& expandWidget("Calculadores", "com.trgr.maf." + BaseTest.productUnderTest + ".calculadores");
			break;

		case "chpmex":

			flag = expandWidget("Tablas Inteligentes Personalizadas",
					"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas")
					&& expandWidget("Checklist", "com.trgr.maf." + BaseTest.productUnderTest + ".checklist")
					&& expandWidget("Primera Hora", "com.trgr.maf." + BaseTest.productUnderTest + ".primera_hora");
			break;

		case "chpury":

			flag = expandWidget("Calculadores", "com.trgr.maf." + BaseTest.productUnderTest + ".calculadores")
					&& expandWidget("Formularios", "com.trgr.maf." + BaseTest.productUnderTest + ".formularios")
					&& expandWidget("Indicadores", "com.trgr.maf." + BaseTest.productUnderTest + ".indicadores")
					&& expandWidget("Tablas inteligentes personalizadas",
							"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas")
					&& expandWidget("Búsqueda permanente",
							"com.trgr.maf." + BaseTest.productUnderTest + ".búsqueda_permanente")
					&& expandWidget("Primera Hora", "com.trgr.maf." + BaseTest.productUnderTest + ".primera_hora");
			break;

		case "chppy":

			flag = expandWidget("Calculadores", "com.trgr.maf." + BaseTest.productUnderTest + ".calculadores")
					&& expandWidget("Tablas inteligentes personalizadas",
							"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas")
					&& expandWidget("Tablas inteligentes personalizadas",
							"com.trgr.maf." + BaseTest.productUnderTest + ".tablas_inteligentes_personalizadas_2")
					&& expandWidget("Búsqueda permanente",
							"com.trgr.maf." + BaseTest.productUnderTest + ".búsqueda_permanente")
					&& expandWidget("Primera Hora", "com.trgr.maf." + BaseTest.productUnderTest + ".primera_hora");
			break;

		case "chppe":

			flag = expandWidget("Herramientas", "com.trgr.maf." + BaseTest.productUnderTest + ".herramientas");
			break;
			
		case "chpchile":

			flag = expandWidget("Herramientas", "com.trgr.maf." + BaseTest.productUnderTest + ".herramientas")
					&& expandWidget("Aplicacion Practica", "com.trgr.maf." + BaseTest.productUnderTest + ".practicalapplication");
			break;
			
		case "chpbr":

			flag = expandWidget("Tabelas Inteligentes",
					"com.trgr.maf." + BaseTest.productUnderTest + ".tabelas_inteligentes")
					&& expandWidget("Ferramentas de Consulta",
							"com.trgr.maf." + BaseTest.productUnderTest + ".ferramentas_de_consulta")
					&& expandWidget("Calculadoras", "com.trgr.maf." + BaseTest.productUnderTest + ".calculadoras")
					&& expandWidget("Downloads", "com.trgr.maf." + BaseTest.productUnderTest + ".downloads_tool")
					&& expandWidget("NESH", "com.trgr.maf." + BaseTest.productUnderTest + ".nesh_tool")
					&& expandWidget("TIPI", "com.trgr.maf." + BaseTest.productUnderTest + ".tipi_tool");
			break;

		}}catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : expandAllWidget <br>" + displayErrorMessage(exc));
			return false;
		}

		return flag;
	}

	public boolean validateCreateShortcutinToolsTab() {
		boolean flag = false;
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolsPagecreateshortcut"))
					.isDisplayed()) {
				elementhandler.clickElement(PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolsPagecreateshortcut"));
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : validateCreateShortcutinToolsTab <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	/*
	 * Checks if 'Daily expiration tool' link is available in the widget returns
	 * true on success
	 */
	public boolean isDailyExpirationToolAvailable() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".daily_expiration_tool_link");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDailyExpirationToolAvailable <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Checks if 'Daily expiration' header is available in the page returns true
	 * on success
	 */
	public boolean isDailyExpirationHeaderDisplayed() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".daily_expiration_tool_headertitle");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDailyExpirationHeaderDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Checks if 'Daily expiration -Due Date' edit box is available in the page
	 * returns true on success
	 */
	public boolean isDailyExpirationEditBoxDisplayed() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".daily_expiration_tool_expdate");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDailyExpirationHeaderDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * Checks if 'Daily expiration -Table' exist in the page returns true on
	 * success
	 */
	public boolean isDailyExpirationTableDisplayed() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".daily_expiration_tool_table_header");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isDailyExpirationTableDisplayed <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * This method clicks on 'Daily expiration tool' link in the widget
	 */
	public void clickDailyExpirationTool() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".daily_expiration_tool_link");
			elementhandler.clickElement(selector);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickDailyExpirationTool <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method writes data on 'Daily expiration -Due Date' edit box in the
	 * page
	 */
	public void setExpirationDate(String expDate) {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".daily_expiration_tool_expdate");
			elementhandler.writeText(selector, expDate);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : setExpirationDate <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * Checks if 'Daily expiration -Error message' exist in the page returns
	 * true on success
	 */
	public boolean isNoDocsFoundErrorDisplayedInDailyExpirationTool() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".daily_expiration_tool_errorMessage");
			return WebDriverFactory.isDisplayed(driver, elementhandler.getElement(selector));
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isNoDocsFoundErrorDisplayedInDailyExpirationTool <br>" + displayErrorMessage(ex));
			return false;
		}
	}

	/*
	 * This method reads the data on 'Daily expiration -Due Date' edit box in
	 * the page
	 */
	public String getExpirationDate() {
		String expDate = "";
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".daily_expiration_tool_expdate");
			expDate = elementhandler.getElement(selector).getAttribute("value");
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : getExpirationDate <br>" + displayErrorMessage(ex));
		}
		return expDate;
	}

	/*
	 * This method clicks on 'Clean' button in Daily Expiration Tool page
	 */
	public void clickClearDailyExpirationDate() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".daily_expiration_tool_clear");
			elementhandler.clickElement(selector);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickClearDailyExpirationDate <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method clicks on 'Create' button in Daily Expiration Tool page
	 */
	public void clickCreateDailyExpiration() {
		try {
			String selector = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".daily_expiration_tool_create");
			elementhandler.clickElement(selector);
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : clickCreateDailyExpiration <br>" + displayErrorMessage(ex));
		}
	}

	/*
	 * This method clicks on the Cotizaciones option to expand on the tools page
	 * If the element is not present, catch block will handle the exception and
	 * log the screen shot on the report returns nothing.
	 */
	public void expandCotizaciones() {

		try {

			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".búsqueda_Cotizaciones"));

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : expandCotizaciones <br>" + displayErrorMessage(e));

		}

	}

	public boolean getlinkfromCotizaciones() {
		boolean flag = false;
		try {

			flag = elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Obligaciones"))
					.isDisplayed();
			flag = flag && elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Acciones"))
					.isDisplayed();
			flag = flag && elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Titulos"))
					.isDisplayed();

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : validateCreateShortcutinToolsTab <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method checks to see if the given data is present on the search page
	 * after the edit search link is clicked
	 */
	public boolean isGivenSearchDataRetainedOnPage(String period) {
		try {
			return elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Perido"))
					.getText().contains(period);
					} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isGivenSearchDataRetainedOnPage <br>" + displayErrorMessage(ex));
			return false;
		}

	}
	
	/*
	 * This method checks to see if the given data is present on the search page
	 * after the new search link is clicked
	 */
	public boolean isGivenSearchDataDeletedFromPage() {
		boolean flag = false;
		try {
			int length = elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Perido"))
					.getText().length();
			if(length==0)
				flag=true;
					} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isGivenSearchDataDeletedFromPage <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}
	
	/*
	 * This method checks to see if the given data is present on the search page
	 * after the new search link is clicked
	 */
	public boolean isGivenSearchDataDeletedOnPage(String period) {
		boolean flag = false;
		try {
			int length = elementhandler
					.getElement(PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Perido"))
					.getText().length();
			if (length == 0)
				flag = true;
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isGivenSearchDataDeletedOnPage <br>" + displayErrorMessage(ex));
			flag = false;
		}
		return flag;
	}

	/*
	 * This method clicks on the Obligaciones page Returns handle to
	 * ObligacionesPage
	 */
	public ObligacionesPage clickToOpenObligacionesPage() {

		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".negobligation"));
			return new ObligacionesPage(driver);

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO,
					"Error in : validateCreateShortcutinToolsTab <br>" + displayErrorMessage(e));
			return null;

		}
	}

	/*
	 * public boolean ExpandCotizaciones() { boolean expanded=false;
	 * 
	 * try{ String selector = PropertiesRepository.getString("com.trgr.maf." +
	 * productUnderTest + ".búsqueda_Cotizaciones"); String classtext =
	 * elementhandler.findElement(selector).getAttribute("class"); expanded =
	 * classtext.contains("collapsable-hitarea"); if(!expanded){
	 * elementhandler.clickElement(selector); expanded =
	 * classtext.contains("collapsable-hitarea"); }
	 * 
	 * }catch(Exception exc){ extentLogger.log(LogStatus.INFO,
	 * "Error in : ExpandCotizaciones <br>"+displayErrorMessage(exc)); expanded
	 * = false; }
	 * 
	 * return expanded; }
	 */

	/*
	 * This method expands the Personales Widget ONLY if the widget is in
	 * collapsed state
	 */
	public void ExpandBienesPersonalesWidget() {
		boolean expanded = false;

		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".bienespersonales");
			String classtext = elementhandler.findElement(selector).getAttribute("class");
			expanded = classtext.contains("minimize-control");
			if (!expanded) {
				elementhandler.clickElement(selector);
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ExpandBienesPersonalesWidget <br>" + displayErrorMessage(exc));

		}

	}

	/*
	 * This method checks to see if the Personal Widget is expanded. Returns
	 * True if exapanded else False.
	 */
	public boolean isBienesPersonalesWidgetExpanded() {
		boolean expanded = false;

		try {
			String selector = PropertiesRepository.getString("com.trgr.maf." + productUnderTest + ".bienespersonales");
			String classtext = elementhandler.findElement(selector).getAttribute("class");
			expanded = classtext.contains("minimize-control");
			if (!expanded) {
				elementhandler.clickElement(selector);
				expanded = classtext.contains("minimize-control");
			}

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : ExpandBienesPersonalesWidget <br>" + displayErrorMessage(exc));
			expanded = false;
		}

		return expanded;
	}

	public ActionsPage openActionsPage() {
		try {
			elementhandler.getElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clickaction"))
					.click();
			return new ActionsPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : openActionsPage <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public ForeignCurrencyPage clickOnForeignCurrencyLink() {
		try {
			elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".foreigncurrencylink")).click();
			return new ForeignCurrencyPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnForeignCurrencyLink <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public boolean errorBlockDisplayed() {
		try {

			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".errorblock"))
					.isDisplayed();
		} catch (Exception exc) {
			return false;

		}
	}

	public boolean noSearchResultsDisplayed() {
		try {
			return elementhandler
					.getElement(
							PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".noresults"))
					.isDisplayed();
		} catch (Exception exc) {
			return false;

		}
	}

	public boolean isSearchFieldsCleared() {
		boolean flag = false;
		try {

			String firstfield = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".periodfiscal");
			String secondfield = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".denominaciontextbox");
			String thirdfield = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clase");

			flag = elementhandler.getTextFromValueAttribute(firstfield).contains("")
					&& elementhandler.getTextFromValueAttribute(secondfield).contains("")
					&& elementhandler.getTextFromValueAttribute(thirdfield).contains("");

		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : isSearchFieldsCleared <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean enterFreeWordOnSearchPage(String SearchWithInText) {
		boolean flag = false;
		try {
			elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Searchwithintext"))
					.sendKeys(SearchWithInText);
			flag = true;
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterFreeWordOnSearchPage <br>" + displayErrorMessage(exc));
			flag = false;
		}
		return flag;
	}

	public boolean clickSearhwithINSearchButton() {
		boolean flag = false;
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Searchwithinsearchbutton"));
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickSearhwithINSearchButton <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean verifyDenominacionFilteredWithValue(String expectedValue) {
		boolean validated = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".denominationcolumnvalues");
			List<WebElement> elements = elementhandler.findElements(locator);
			for (int row = 0; row < elements.size(); row++) {
				String actualValue = elements.get(row).getText();
				validated = (actualValue.contains(expectedValue));
				if (!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifyDenominacionFilteredWithValue <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}

	public ValuationWheelsPage clickValuationofWheels() {
		try {
			elementhandler.getElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".valuationofwheelslink")).click();
			return new ValuationWheelsPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickValuationofWheels <br>" + displayErrorMessage(exc));
			return null;
		}
	}

	public void selectPeriodFiscalDropdown(String Period) {
		try {
			Thread.sleep(1000);
			String selctor = PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".periodfiscal");
			elementhandler.selectByVisibleText(selctor, Period);
			Thread.sleep(1000);

		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO, "Error in : selectPeriodFiscalDropdown <br>" + displayErrorMessage(ex));
		}
	}

	public boolean clickonClearButton() {
		boolean flag = false;
		try {
			if (elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolsclearbutton"))
					.isDisplayed()) {
				elementhandler.clickElement(
						PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".clearbutton"));
				flag = true;
			}
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : clickonClearButton <br>" + displayErrorMessage(e));
			flag = false;
		}
		return flag;
	}

	public boolean verifySearchwithinFilteredValueforValuationofWheels(String expectedValue) {
		boolean validated = false;
		try {
			String locator = PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".denominationcolumnvaluesforvaluationwheels");
			List<WebElement> elements = elementhandler.findElements(locator);
			String expectedArray[] = expectedValue.split("\\s");
			boolean textFound = false;

			for (int row = 0; row < elements.size(); row++) {
				String actualValue = elements.get(row).getText();
				for (int i = 0; i < expectedArray.length; i++) {
					textFound = (actualValue.contains(expectedArray[i]));
					if (textFound)
						break;
				}
				validated = textFound;
				if (!validated)
					break;
			}
		} catch (Exception ex) {
			extentLogger.log(LogStatus.INFO,
					"Error in : verifySearchwithinFilteredValueforValuationofWheels <br>" + displayErrorMessage(ex));
			return false;
		}
		return validated;
	}

	/*
	 * Click on the Títulos Públicos link upon expanding the Cotizaciones option
	 */
	public PublicTitlesPage clickOnPublicTitlesLink() {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".titulospublicos"));
			return new PublicTitlesPage(driver);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnPublicTitlesLink <br>" + displayErrorMessage(exc));
		}
		return null;
	}

	// This method used to expand Fiscal node in the checklist widget
	public void expandFiscalNode() throws Exception {
		try {
			elementhandler.clickElement(
					PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandfiscalnode"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : expandFiscalNode <br>" + displayErrorMessage(exc));
		}
	}

	// This method used to expand Labour and Socail node in the checklist widget
	public void expandLabourAndSocialNode() throws Exception {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expandlaboursocialnode"));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : expandLabourAndSocialNode <br>" + displayErrorMessage(exc));
		}
	}

	// This method is used to Click on the given link under the fiscal node.
	public void clickOnGivenLink(String linkname) {

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)", "");
			elementhandler.clickElement("LinkText="+linkname);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnGivenLink <br>" + linkname + displayErrorMessage(exc));
		}
	}

	// This method is used to verify if  expected text fields present in the page after
	// clicking the given link

	public boolean isTextFieldsPresent(String textfield) {
		try {
			
			List<WebElement> list=elementhandler.findElements(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolschecklist_textfields"));
			for(int i=0;i<list.size();i++)
			{
				String actualtextfield=list.get(i).getText();
				if(textfield.contentEquals(actualtextfield))
				{
					return true;
				}
					
			}
			
			
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isTextFieldsPresent <br>" + displayErrorMessage(exc));
			return false;
		}
		return false;
	}

	// This method is used to verify if radio buttons present in the page
	public boolean isRadioButtonsPresent() {
		try {
			return WebDriverFactory.isDisplayed(driver, elementhandler.findElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolschecklist_radiobuttons")));

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isRadioButtonsPresent <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	// This method is used to click on the clear button in the page
	public void clickOnClearButton() {

		try {
			String locator=PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolschecklist_cleanbutton");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnClearButton <br>" + displayErrorMessage(exc));
		}
	}

	// This method is used to click on the recommend button in the page
	public void clickOnRecommendButton() {
		try {
			String locator=PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolschecklist_recommendbutton");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			elementhandler.clickElement(locator);
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRecommendButton <br>" + displayErrorMessage(exc));
		}
	}
	
	/**
	 * Create method for close Recommend Popup message and scroll back into top of the current page 
	 */
	public void clickOnRecommendPopupClose() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".recommend_popuptext_close"));
			// After closed the popup message, below locator will help it to scroll into top
			String locator=PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolschecklist_Empresa");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : clickOnRecommendPopupClose <br>" + displayErrorMessage(exc));
		}
	}

	// This method is used to verify if expected popup text displayed in the pop up after
	// clicking recommend button. It returns true if text displayed.
	public boolean isRecommendPopUpTextDisplayed(String popuptext) {
		try {
			return  elementhandler.getText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".recommend_popuptext")).contains(popuptext);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO,
					"Error in : isRecommendPopUpTextDisplayed <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	// This method is used to enter input value in the Business field text box

	public void enterBusinessInput(String input) {
		try {
			String locator=PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolschecklist_Empresa");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",elementhandler.getElement(locator));
			//elementhandler.clickElement(locator);
			elementhandler.writeText(locator, input);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterBusinessInput <br>" + displayErrorMessage(exc));

		}
	}

	// This method is used to enter input value in the Interviewed field text
	// box

	public void enterInterviewedInput(String input) {
		try {
			elementhandler.writeText(PropertiesRepository
					.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolschecklist_Entrevistado"), input);

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : enterInterviewedInput <br>" + displayErrorMessage(exc));

		}
	}

	// This method is used to check the all radio buttons

	public void checkRadioButtons() {
		try {
			elementhandler.clickElement(PropertiesRepository
					.getString("com.trgr.maf." + productUnderTest + ".toolschecklist_radiobuttons"));
		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : checkRadioButtons <br>" + displayErrorMessage(exc));

		}
	}

	// This method is used to Check if all the radio buttons are selected

	public boolean isRadioButtonsSelected() {
		boolean isselected = false;
		try {
			isselected = elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + productUnderTest + ".toolschecklist_radiobuttons"))
					.isSelected();

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isRadioButtonsSelected <br>" + displayErrorMessage(exc));
			isselected = false;
		}
		return isselected;
	}

	// verify if input text fields cleared after clicking on clean button

	public boolean isInputFieldsCleared() {
		try {
			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".toolschecklist_Empresa"))
					.getAttribute("value").contains("")
					&& elementhandler
							.getElement(PropertiesRepository.getString(
									"com.trgr.maf." + BaseTest.productUnderTest + ".toolschecklist_Entrevistado"))
							.getAttribute("value").contains("");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isInputFieldsCleared <br>" + displayErrorMessage(exc));
			return false;
		}
	}

	// This method is used to expand checklist widget

	public void expandCheckListWidget() {
		try {
			expandWidget("Checklist", "com.trgr.maf." + BaseTest.productUnderTest + ".checklist");
		} catch (Exception e) {
			extentLogger.log(LogStatus.INFO, "Error in : expandCheckListWidget <br>" + displayErrorMessage(e));
		}
	}

	// This method is used to verify if checklist widget is collapsed. It
	// returns true if collapsed.

	public boolean isCheckListWidgetcollapsed() throws Exception {
		try {

			return elementhandler
					.getElement(PropertiesRepository
							.getString("com.trgr.maf." + BaseTest.productUnderTest + ".checklist_widget"))
					.getAttribute("class").contains("controlButton maximize-control");

		} catch (Exception exc) {
			extentLogger.log(LogStatus.INFO, "Error in : isCheckListWidgetcollapsed <br>" + displayErrorMessage(exc));
			return false;
		}

	}

	
	// This method is used to verify if given node is expanded. It
		// returns true if expanded.

		public boolean isGivenNodeExpanded(String nodename) throws Exception {
			try {
				String locator="xpath=//span[text()='" +nodename +"']/..";
				return elementhandler
						.getElement(locator)
						.getAttribute("class").contains("collapsable");

			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : isGivenNodeExpanded <br>" + displayErrorMessage(exc));
				return false;
			}

		}
		
		public  boolean isGivenItemIsLink(String itemname)
		{
			try {
				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".node_linknames");

				List<WebElement> links=elementhandler.findElements(locator);

				for(int i=0;i<links.size();i++)
				{
					String actuallinkname=links.get(i).getText();
					if(itemname.equals(actuallinkname))
					{
						return links.get(i).findElements(By.tagName("a")).size() == 1;
					}

				}



			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : isGivenItemIsLink <br>" + displayErrorMessage(exc));
				return false;
			}
			return false;
		}

		  //Click on the link under obligaciones widget section:
		
		public boolean isGivenLinkClickable(String linkname)
		{
			try {
				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".node_linknames");

				List<WebElement> links=elementhandler.findElements(locator);

				for(int i=0;i<links.size();i++)
				{
					String actuallinkname=links.get(i).getText();
					if(linkname.equals(actuallinkname))
					{
					   return links.get(i).getAttribute("class").contains("noAccess");
					}

				}


			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : isGivenLinkClickable <br>" + displayErrorMessage(exc));
				return false;
			}
			return false;
		}
		  //Expand the obligaciones widget section:	
		
		public void expandObligationsWidget() {
			try {
				expandWidget("Checklist", "com.trgr.maf." + BaseTest.productUnderTest + ".expand_Obligations_Widget");
			} catch (Exception e) {
				extentLogger.log(LogStatus.INFO, "Error in : expandObligationsWidget <br>" + displayErrorMessage(e));
			}
		}
		
		// Method for clicking following link in different (Smart Table - Federal Contributions Obligations) pages
		

		public void oblicgationFollowingPages() {
			try {
				String federalFollowing = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".federal_Obligations_following");
				String obligationsFollowing = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".Obligations_following");
				String subjectsFollowing = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".subjects_Obligations_following");
				String conceptsFollowing = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".concepts_Obligations_following");
				String expiryYearFollowing = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expiryYear_following");

				/*if (federalFollowing != null && !federalFollowing.isEmpty()
						|| obligationsFollowing != null && !obligationsFollowing.isEmpty()
						|| subjectsFollowing != null && !subjectsFollowing.isEmpty()
						|| conceptsFollowing != null && !conceptsFollowing.isEmpty()
						|| expiryYearFollowing != null && !expiryYearFollowing.isEmpty()) {*/

					if (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".federal_radio_button"))
							.isDisplayed()) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
								elementhandler.getElement(federalFollowing));
						elementhandler.findElement(federalFollowing).click();

					} else if (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".obligation_label"))
							.isDisplayed()) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
								elementhandler.getElement(obligationsFollowing));
						elementhandler.findElement(obligationsFollowing).click();
					} else if (elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".subjects_label"))
							.isDisplayed()) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
								elementhandler.getElement(subjectsFollowing));
						elementhandler.findElement(subjectsFollowing).click();

					} else if ((elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".concepts_label"))
							.isDisplayed())) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
								elementhandler.getElement(conceptsFollowing));
						elementhandler.findElement(conceptsFollowing).click();
					} else if ((elementhandler
							.getElement(PropertiesRepository
									.getString("com.trgr.maf." + BaseTest.productUnderTest + ".expiryyear_label"))
							.isDisplayed())) {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
								elementhandler.getElement(expiryYearFollowing));
						elementhandler.findElement(expiryYearFollowing).click();
					}
				
			} catch (Exception e) {
				extentLogger.log(LogStatus.INFO, "Error in : oblicgationFollowingPages <br>" + displayErrorMessage(e));
			}
		}
		
		 // Passing checkbox parameter to select the value in obligaciones page	
		public void selectObligacionesCheckBox(String checkBoxValue) {
	           try {
	           List<WebElement> checkboxList=driver.findElements(By.xpath("//input[@name='obligation' and @type='checkbox']"));
	           
	           for(int i=0;i<checkboxList.size();i++)
	           {
	        	   WebElement checkBoxElement=checkboxList.get(i);
	        	   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", checkboxList.get(i));
	                  String value=checkBoxElement.getAttribute("value");
	                  if (value.equalsIgnoreCase(checkBoxValue))
	                        checkBoxElement.click(); 
	           }
	           } catch (Exception exc) {
	                 extentLogger.log(LogStatus.INFO, "Error in : Select Obligaciones CheckBox <br>" + displayErrorMessage(exc));
	           }
	           
	    }
		
		
  //# dynamic check box for Smart Table - Obligations of Foreign Trade (Subject)
		public void subjectdynamiccheckbox(String checkBoxValue) {
			try {
				List<WebElement> checkBoxList = driver
						.findElements(By.xpath("//input[@name='affectedPerson' and @type='checkbox']"));
				if (checkBoxValue =="ALL" ) {
					for (WebElement ele : checkBoxList) {
						if (!ele.isSelected()) {
							ele.click();
						}
					}
				} else {
					for (int i = 0; i < checkBoxList.size(); i++) {
						WebElement checkBoxElement = checkBoxList.get(i);
						String value = checkBoxElement.getAttribute("value");
						if (value.equalsIgnoreCase(checkBoxValue))
							checkBoxElement.click();
					}

				}
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : SubjectDynamicCheckBox <br>" + displayErrorMessage(exc));
			}

		}

	   //# dynamic check box for Smart Table - Obligations of Foreign Trade (concept)
		public void conceptdynamiccheckbox(String checkBoxValue) {
			try {
				List<WebElement> checkBoxList = driver.findElements(By.xpath("//input[@name='concept' and @type='checkbox']"));
				if (checkBoxValue == "ALL") {
				for (WebElement ele : checkBoxList) {
					if (!ele.isSelected()) {
						ele.click();
					}
				}
				}else {
					for (int i = 0; i < checkBoxList.size(); i++) {
						WebElement checkBoxElement = checkBoxList.get(i);
						String value = checkBoxElement.getAttribute("value");
						if (value.equalsIgnoreCase(checkBoxValue))
							checkBoxElement.click();
					}

				}
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : ConceptDynamicCheckBox <br>" + displayErrorMessage(exc));
			}

		}
		// Clicks on radio button on Expiry year in Obligations of Foreign Trade
		public void clickRadioButtonOnExpiryYear(String year) throws InterruptedException {
			try {
				/*
				 * elementhandler.clickElement( PropertiesRepository.getString("com.trgr.maf." +
				 * BaseTest.productUnderTest + ".dueDateYear"));
				 */
				List<WebElement> radioButtons = driver.findElements(By.xpath("//*[@id='dueDateYear1']"));
				for (int i = 0; i < radioButtons.size(); i++) {
					WebElement radioButtonElement = radioButtons.get(i);

					String value = radioButtonElement.getAttribute("value");
					if (year.equalsIgnoreCase(value))
						radioButtonElement.click();
					break;
				}
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO,
						"Error in : clickRadioButtonOnExpiryYear: <br>" + displayErrorMessage(exc));
			}
		}
		// Passing  parameter to select the Expiry month to click the radiobutton	
		public void selectSpecificRadioButton(String month) {
			try {
				List<WebElement> radioButtonList = driver
						.findElements(By.xpath("//input[@name='dueDateMonth' and @type='radio']"));
				List<WebElement> labelList = driver.findElements(By.xpath("//*[@id='dueDateMonth']/p/label"));
				for (int i = 0; i < radioButtonList.size(); i++) {
					WebElement checkBoxElement = radioButtonList.get(i);
					WebElement labelElements = labelList.get(i);
					String labelText = labelElements.getText();
					// String value = labelText+checkBoxElement.getAttribute("value");
					if (month.equalsIgnoreCase(labelText)) {
						checkBoxElement.click();
					break;
						}
					}

			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : selectSpecificRadioButton <br>" + displayErrorMessage(exc));
			}

		}


			// Click the Obligations Create Button
		public void clickObligationsCreateButton() {
			try {
				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".obligations_createbutton");
				Thread.sleep(3000);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						elementhandler.getElement(locator));
				elementhandler.clickElement(locator);
			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : clickObligationsCreateButton <br>" + displayErrorMessage(exc));
			}
		}

		
		// Validate the SmartTable is Dispayed
		public boolean smartTableHeaderIsDispayed(String expectedText) {
			boolean flag = false;
			try {

				String locator = PropertiesRepository
						.getString("com.trgr.maf." + BaseTest.productUnderTest + ".smart_table_header");
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
						elementhandler.findElement(locator));
				// WebDriverFactory.waitForElementUsingWebElement(driver,
				// elementhandler.getElement(locator), 30);
				String actualText = elementhandler.getText(locator);
				flag = actualText.equals(expectedText);

			} catch (Exception exc) {
				extentLogger.log(LogStatus.INFO, "Error in : smartTableHeaderIsDispayed <br>" + displayErrorMessage(exc));
				flag = false;
			}
			return flag;
		}
		
}
		
