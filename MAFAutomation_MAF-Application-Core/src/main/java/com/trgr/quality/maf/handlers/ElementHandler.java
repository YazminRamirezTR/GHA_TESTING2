package com.trgr.quality.maf.handlers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ElementHandler extends BaseHandler {

	public ElementHandler(WebDriver driver) {
		super(driver);
	}

	public WebElement getElement(String selector) {
		if (selector.contains("partialLinkText")) {
			return driver.findElement(By.partialLinkText(selector.split("=")[1]));
		}else if (selector.contains("linkText")) {
			return driver.findElement(By.partialLinkText(selector.split("=")[1]));
		}else {
			return findElement(selector);
		}
	}

	public void clickElement(String selector, String... waitFor) {
		try {
			WebElement element;
			if (selector.contains("partialLinkText")) {
				element = driver.findElement(By.partialLinkText(selector.split("=")[1]));
			}else if(selector.contains("LinkText")){
				element = driver.findElement(By.linkText(selector.split("=")[1]));
			}else{
				element = findElement(selector);
			}

			if (element != null) {
//				JavascriptExecutor js=    (JavascriptExecutor)driver;
//				js.executeScript("arguments[0].click();", element);
				
				Actions act = new Actions(driver);
				act.moveToElement(element).click().build().perform();
				
				//element.click();
				if (waitFor != null && waitFor.length > 0) {
					setWebDriverWait(waitFor[0]);
				}
			}
		} catch (WebDriverException e) {
			throw new WebDriverException("Unable to locate the element \n " + e);
		}
	}

	public void writeText(String selector, String text, String... waitFor) {
		try {
			WebElement textElement = findElement(selector);
			if (textElement != null) {
				textElement.click();
				textElement.clear();
				textElement.click();
				textElement.sendKeys(text);
				if (waitFor != null && waitFor.length > 0) {
					setWebDriverWait(waitFor[0]);
				} 
			}
		} catch (WebDriverException e) {
			throw new WebDriverException("Unable to locate the element \n " + e);
		}
	}

	public void hoverOverMenuItem(String selector, String... waitFor) {
		try {
			WebElement we = findElement(selector);
			Actions actions = new Actions(driver);
			if (we != null) {
				actions.moveToElement(we).build().perform();
				if (waitFor != null && waitFor.length > 0) {
					setWebDriverWait(waitFor[0]);
				}
			}
		} catch (WebDriverException e) {
			throw new WebDriverException("Unable to locate the element \n " + e);
		}
	}

	public String getText(String selector) {
		String text = null;
		try {
			WebElement textElement = findElement(selector);
			if (textElement != null) {
				text = textElement.getText();
			}
		} catch (WebDriverException e) {
			throw new WebDriverException("Unable to locate the element \n " + e);
		}
		return text;
	}

	public String getTextFromValueAttribute(String selector) {
		String text = null;
		try {
			WebElement textElement = findElement(selector);
			if (textElement != null) {
				text = textElement.getAttribute("value");
			}
		} catch (WebDriverException e) {
			throw new WebDriverException("Unable to locate the element \n " + e);
		}
		return text;
	}

	public String getlinkFromHrefAttribute(String selector) {
		String text = null;
		try {
			WebElement textElement = findElement(selector);
			if (textElement != null) {
				text = textElement.getAttribute("href");
			}
		} catch (WebDriverException e) {
			throw new WebDriverException("Unable to locate the element \n " + e);
		}
		return text;
	}
	public Select getDropdown(String selector) {
		Select dropDown = null;
		try {
			WebElement we = findElement(selector);
			if (we != null) {
				dropDown = new Select(we);
			}
		} catch (WebDriverException e) {
			throw new WebDriverException("Unable to locate the element \n " + e);
		}
		return dropDown;
	}

	public void selectByVisibleText(String selector, String textToSelect, String... waitFor) {
		try {
			Select dropdown = getDropdown(selector);
			if (dropdown != null) {
				dropdown.selectByVisibleText(textToSelect);
				if (waitFor != null && waitFor.length > 0) {
					setWebDriverWait(waitFor[0]);
				}
			}
		} catch (WebDriverException e) {
			throw new WebDriverException("Unable to select value from the dropdown \n " + e);
		}
	}

	public void selectByValue(String selector, String value, String... waitFor) {
		try {
			Select dropdown = getDropdown(selector);
			if (dropdown != null) {
				dropdown.selectByValue(value);
				if (waitFor != null && waitFor.length > 0) {
					setWebDriverWait(waitFor[0]);
				}
			}
		} catch (WebDriverException e) {
			throw new WebDriverException("Unable to select value from the dropdown \n " + e);
		}
	}

	public void selectByIndex(String selector, int index, String... waitFor) {
		try {
			Select dropdown = getDropdown(selector);
			if (dropdown != null) {
				dropdown.selectByIndex(index);
				if (waitFor != null && waitFor.length > 0) {
					setWebDriverWait(waitFor[0]);
				}
			}
		} catch (WebDriverException e) {
			throw new WebDriverException("Unable to select value from the dropdown \n " + e);
		}
	}

}
