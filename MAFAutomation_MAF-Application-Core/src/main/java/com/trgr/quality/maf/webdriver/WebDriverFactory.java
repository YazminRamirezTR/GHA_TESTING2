package com.trgr.quality.maf.webdriver;



import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.GlobalProperties;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;

public class WebDriverFactory extends BaseTest{

public static WebDriver driver; 
public static EdgeDriverService edgeService;
public static String browserType;
public static boolean GridEnabled;		
		

	public static WebDriver getInstance(String BrowserType) throws Exception
	{	
		browserType=BrowserType;
		
		switch(browserType){
			
		   	case GlobalProperties.CHROME:
		   		driver = initializeChromeDriver();
		   		log.info("Chrome driver instance is launched successfully");
		   		break;

		   	case GlobalProperties.IE:
		   		driver = initializeIEdriver();		
		   		log.info("IE driver instance is launched successfully");
		   		break;
			
		   	case GlobalProperties.FIREFOX:
		   		driver=new FirefoxDriver();
		   		log.info("Firefox instance is launched successfully");
		   		break;
				
		   	case GlobalProperties.EDGE:
		   		driver = initializeEdgeDriver();
		   		log.info("Edge Driver instance launched succesfully");	
		   		break;
		}
		return driver; 
	}
	
	
	private static WebDriver initializeEdgeDriver() throws Exception {
			
		try{
			File file = new File("./drivers/msedgedriver.exe");
			System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
			edgeService = new EdgeDriverService.Builder()
					.usingDriverExecutable(new File("./drivers/msedgedriver.exe")).usingAnyFreePort().build();
			edgeService.start();
			WebDriver driver = new RemoteWebDriver(edgeService.getUrl(), DesiredCapabilities.edge());
			return driver;
		} catch (WebDriverException wde) {
			
			log.info("Binary for msedge not found, preparing legacy version ...");
			
			File file = new File("./drivers/MicrosoftWebDriver.exe");
			System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
			edgeService = new EdgeDriverService.Builder()
					.usingDriverExecutable(new File("./drivers/MicrosoftWebDriver.exe")).usingAnyFreePort().build();
			edgeService.start();
			WebDriver driver = new RemoteWebDriver(edgeService.getUrl(), DesiredCapabilities.edge());
			return driver;
		}
	}
	
	private static WebDriver initializeIEdriver() throws Exception {
		
			File file = new File("./drivers/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			DesiredCapabilities deCap = DesiredCapabilities.internetExplorer();
			deCap.setCapability("EnableNativeEvents", false);
			deCap.setCapability("ignoreZoomSetting", true);
			deCap.setCapability(CapabilityType.BROWSER_NAME, "IE");
			deCap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			deCap.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
			WebDriver driver = new InternetExplorerDriver();
			return driver;
		
	}
	private static WebDriver initializeChromeDriver() throws IOException
	{	
		
			// start Chrome in maximized window
			String downloadFilepath = "C:/Downloads";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);

			final ChromeOptions options = new ChromeOptions();

			options.addArguments("--start-maximized", "--allow-running-insecure-content");
			options.setExperimentalOption("prefs", chromePrefs);
			final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
			options.addArguments("--test-type", "ignore-certifcate-errors");
			//options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
			options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
			desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

			//options.addArguments("--headless"); // only if you are ACTUALLY running headless
			//options.addArguments("--no-sandbox");
			// //https://stackoverflow.com/a/50725918/1689770
			//options.addArguments("--disable-infobars");
			// //https://stackoverflow.com/a/43840128/1689770
			//options.addArguments("--disable-dev-shm-usage");
			// //https://stackoverflow.com/a/50725918/1689770
			// options.addArguments("--disable-browser-side-navigation"); // https://stackoverflow.com/a/49123152/1689770
			//options.addArguments("--disable-gpu");
			// //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc

			// disabled for temporaily bindu for debugging
			// options.addArguments("chrome.switches","--disable-extensions");

			/*
			 * driverService = new ChromeDriverService.Builder().usingDriverExecutable(new
			 * File("./drivers/chromedriver.exe")).usingAnyFreePort().build();
			 * driverService.start();
			 * log.info("Chrome driver instance is intiated successfully"); return driver =
			 * new ChromeDriver((ChromeDriverService) driverService, options);
			 */
			File file = new File("./drivers/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			return driver = new ChromeDriver(options);
		
	}


	public static  void  managedriver(WebDriver driver)
	{
		//driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(PropertiesRepository.getLong("global.implicit.wait"), TimeUnit.SECONDS);
		
	}
	
	public static void removedriver()
	{		
		if (driver != null)
		{
			driver.close();
		}
		
		/*if (driverService != null)
		{
			driverService.stop();
		}*/
	}
	
	public WebElement fetchElement(By by) {
	    return driver.findElement(by);
	}
	
	/**
	 * Waits up to 10 seconds for an element to appear on a page.
	 * 
	 * @param driver
	 *            a Selenium driver
	 * @param by
	 *            an element identifier for which to wait
	 * @return a web element
	 */
	public static WebElement waitForElement(final WebDriver driver, final By by)
	{
		return waitForElementUsingBy(driver, by, null);
	}


	/**
	 * Waits for an element to appear on a page. A default wait of 10 seconds is used if {@code maxWaitInSeconds} is provided as {@code null}.
	 * 
	 * @param driver
	 *            a Selenium driver
	 * @param by
	 *            an element identifier for which to wait
	 * @param maxWaitInSeconds
	 *            the number seconds for which to wait on {@code by} to appear on a page
	 * @return a web element
	 */
	public static WebElement waitForElementUsingBy(final WebDriver driver, final By by, final Integer maxWaitInSeconds)
	{
		final int waitInSeconds = (maxWaitInSeconds != null) ? maxWaitInSeconds : 30;
		return (new WebDriverWait(driver, waitInSeconds)).until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public static WebElement waitForElementUsingWebElement(final WebDriver driver, final WebElement element, final Integer maxWaitInSeconds)
	{
		final int waitInSeconds = (maxWaitInSeconds != null) ? maxWaitInSeconds : 30;
		return (new WebDriverWait(driver, waitInSeconds)).until(ExpectedConditions.visibilityOf(element));
	}
	
	public static Boolean waitForElementNotPresnt(final WebDriver driver, final By by, final Integer maxWaitInSeconds)
	{
		final int waitInSeconds = (maxWaitInSeconds != null) ? maxWaitInSeconds : 30;
	
		return (new WebDriverWait(driver, waitInSeconds).until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(by))));
		
		
	}

	/**
	 * Waits up to 10 seconds for an element containing the provided text to appear on a page.
	 * 
	 * @param driver
	 *            a Selenium driver
	 * @param by
	 *            an element identifier for which to wait
	 * @param text
	 *            a string upon which to search within the provided element identifier
	 */
	public static void waitForTextInElement(final WebDriver driver, final By by, final String text)
	{
		waitForTextInElement(driver, by, text, null);
	}


	/**
	 * Waits for an element containing the provided text to appear on a page. A default wait of 10 seconds is used if {@code maxWaitInSeconds} is provided as {@code null}.
	 * 
	 * @param driver
	 *            a Selenium driver
	 * @param by
	 *            an element identifier for which to wait
	 * @param text
	 *            a string upon which to search within the provided element identifier
	 * @param maxWaitInSeconds
	 *            the number seconds for which to wait on {@code by} to appear on a page
	 */
	@SuppressWarnings("deprecation")
	public static void waitForTextInElement(final WebDriver driver, final By by, final String text, final Integer maxWaitInSeconds)
	{
		final int waitInSeconds = (maxWaitInSeconds != null) ? maxWaitInSeconds : 10;
		(new WebDriverWait(driver, waitInSeconds)).until(ExpectedConditions.textToBePresentInElementLocated(by, text));
	}


	/**
	 * Makes a new Selenium {@code Action} to perform hovering of the provided element identifier
	 * 
	 * @param driver
	 *            a Selenium driver
	 * @param by
	 *            an element identifier upon which to hover
	 */
	public static void hover(final WebDriver driver, final By by)
	{
		final WebElement elementToHover = driver.findElement(by);
		new Actions(driver).moveToElement(elementToHover).build().perform();
	}
	
	
	/*
	 * Check if an element is highlighted
	 */
	public static String isTextHighlighted(final WebDriver driver, String text)
	{
		return (String) ((JavascriptExecutor)driver).executeScript("return window.getSelection().toString();");
		
	}

  /*
   * Is AlertWindow present
   */
	public static boolean isAlertPresent(WebDriver driver) 
	    { 
	        try 
	        { 
	        	driver.switchTo().alert(); 
	            return true; 
	        }   // try 
	        catch (NoAlertPresentException Ex) 
	        { 
	            return false; 
	        }   // catch 
	    } 
	  
 
	//************Method to enter into a textbox/check a Checkbox based on its label*******************************************************
	public static void EnterTextIntoTextbox(WebDriver driver, String labelname,String ValuetobeEntered)
    {
		//SeleniumUtils.highlightElement(driver, By.xpath("//label[contains(text(),'"+labelname+"')]/following::input"));
           driver.findElement(By.xpath("//label[contains(text(),'"+labelname+"')]/following::input")).sendKeys(ValuetobeEntered);
    }
	
	public static void EnterTextIntoTextArea(WebDriver driver, String labelname,String ValuetobeEntered)
    {
           driver.findElement(By.xpath("//label[contains(text(),'"+labelname+"')]/following::textarea")).sendKeys(ValuetobeEntered);
    }

    //Generic Method for creating Button using selenium. Button Value needs to be passed.
    public static void ClickOnButton(WebDriver driver,String buttonValue)
    {
           //driver.findElement(By.xpath("//input[@type='submit' and @value=buttonValue]")).click();
           driver.findElement(By.xpath("//input[@type='submit' and @value='"+buttonValue+"']")).click();

		
	}
    
	/**
	 * Checks to see if given text is present on the page
	 * 
	 * @param driver
	 *            a Selenium driver
	 * @param by
	 *            an element identifier to check for the text
	 */
	public static Boolean IsTextDisplayedOnPage(final WebDriver driver, String text)
	{
		return waitForElement(driver, By.cssSelector("BODY")).getText().contains(text);
		
	}
	

    public static void ClickOnLinkWithGivenHrefText(final WebElement driver, String text)
    {
    	//Get all the hyperlink elements
    	List<WebElement> list=driver.findElements(By.xpath("//li[@class='searchInfo']/a"));

       	for(int i=0 ; i<list.size() ; i++)
    	{
    	    if(list.get(i).getAttribute("href").contains(text))
    	    {
    	       list.get(i).click();
    	       break;
    	    }
    	}
     
    }
    
	/**
	 * Checks to see if specified locator is currently displayed on screen
	 * 
	 * @param locator
	 *            - locator string
	 * @return - true or false depending on whether or not the locator is
	 *         displayed
	 */
	public static Boolean isDisplayed(final WebDriver driver, final WebElement locator)
	{
		try
		{
			return locator.isDisplayed();
		}
		catch (Exception ex)
		{
			return false;
		}
	
	}

	/**
	 * Refreshes the current browser page
	 * @throws InterruptedException 
	 */
	public static void Refresh() throws InterruptedException
	{
		driver.navigate().refresh();
		Thread.sleep(1000);
	}

	
	
	/**
	 * wait for page to load
	*/

	
	/**
	 * Gives the current data in format specified.
	 */
	public static String DateFormat(String format)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		Date date = new Date();
		String todaysdate = dateFormat.format(date);
		System.out.println(todaysdate);
		return todaysdate;

	}

	
	
}
