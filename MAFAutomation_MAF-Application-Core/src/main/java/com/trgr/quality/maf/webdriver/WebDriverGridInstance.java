package com.trgr.quality.maf.webdriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.trgr.quality.maf.basetest.BaseTest;
import com.trgr.quality.maf.fileconfiger.GlobalProperties;

public class WebDriverGridInstance extends BaseTest {

	public static WebDriver buildInstance(String port, String nodeUrl) throws MalformedURLException{
		
		WebDriver driver = null;
		
		switch(port){
		
			case GlobalProperties.CHROME_NODE:
				
				String downloadFilepath = "C:/Downloads";
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", downloadFilepath);
				
				final ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized", "--allow-running-insecure-content");
				// options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("--test-type", "ignore-certifcate-errors");
				options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
				options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
				// options.addArguments("--headless"); // only if you are ACTUALLY running
				// headless
				// options.addArguments("--no-sandbox");
				// //https://stackoverflow.com/a/50725918/1689770
				// options.addArguments("--disable-infobars");
				// //https://stackoverflow.com/a/43840128/1689770
				// options.addArguments("--disable-dev-shm-usage");
				// //https://stackoverflow.com/a/50725918/1689770
				options.addArguments("--disable-browser-side-navigation"); // https://stackoverflow.com/a/49123152/1689770
				// options.addArguments("--disable-gpu");
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
				
				driver = new RemoteWebDriver(new URL(nodeUrl+":4090/wd/hub"), options);
				log.info("Chrome Driver Browser Launched");
				break;
				
			case GlobalProperties.FIREFOX_NODE:
				
				FirefoxOptions fop = new FirefoxOptions();
				driver = new RemoteWebDriver(new URL(nodeUrl+port+"/wd/hub"), fop);
				log.info("Firefox Driver Browser Launched");
				break;
				
			case GlobalProperties.IE_NODE:
				
				DesiredCapabilities deCapIE = DesiredCapabilities.firefox();
				deCapIE.setBrowserName("internet explorer");
				deCapIE.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				deCapIE.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				deCapIE.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				deCapIE.setCapability("ignoreProtectedModeSettings", true);
				deCapIE.setCapability("nativeEvents", false);
				deCapIE.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
				deCapIE.setCapability(InternetExplorerDriver.LOG_LEVEL, "DEBUG");

		
				deCapIE.setPlatform(Platform.WIN10);
				
				driver = new RemoteWebDriver(new URL(nodeUrl+port+"/wd/hub"),deCapIE);
		
				log.info("IE Driver Browser Launched");
				break;
		}
		return driver;
	}
	
	
}
