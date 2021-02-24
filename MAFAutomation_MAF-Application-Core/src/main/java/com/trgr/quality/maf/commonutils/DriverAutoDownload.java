package com.trgr.quality.maf.commonutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class DriverAutoDownload {
	
	//Class Variables
	private static UnzipUtility uzp;
	private static String chromeVersionfilePath = "C:\\Workspace\\CHROME_CURRENT_VERSION.txt";
	private static EdgeDriverService edgeService;
	private static WebDriver driver; 
	private static String lloPath = "C:\\Workspace\\MAFAutomation_LLO-Application-Test\\drivers\\";
	private static String chpTestPath = "C:\\Workspace\\MAFAutomation_CHP-Application-Test\\drivers\\";
	private static String MAFCorePath = "C:\\Workspace\\MAFAutomation_MAF-Application-Core\\drivers\\";
	private static String RTBrazilPath = "C:\\Workspace\\MAFAutomation_RTBrazil\\drivers\\";
	private static String script = "C:\\Workspace\\DetectBrowserVersion.vbs";
	private static String executable = "C:\\Windows\\System32\\wscript.exe";
	private static String downloadedChromeDriverPath = "C:\\Users\\C275973\\Downloads\\chromedriver_win32.zip";
    
//================================ FUNCTIONALITY ==========================================//
	
	private static void executeVBSScript(String executable, String script) throws IOException{
		//CMD
		String cmdArr [] = {executable, script};
		Runtime.getRuntime ().exec (cmdArr);
	}
	
	
	private static WebDriver initializeEdgeDriver() throws IOException {
		File file = new File("./drivers/msedgedriver.exe");
		System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
		edgeService = new EdgeDriverService.Builder()
				.usingDriverExecutable(new File("./drivers/msedgedriver.exe")).usingAnyFreePort().build();
		edgeService.start();
		WebDriver driver = new RemoteWebDriver(edgeService.getUrl(), DesiredCapabilities.edge());
		return driver;
	}
	
	/**
	 * Reads given file and returns its contents in string format
	 * 
	 * @param fileName
	 * @throws IOException
	 * 
	 * 
	 */
	private static String readFile(String fileName) throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while (line != null){
				sb.append(line);
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}	
	}
	
//=================================== MAIN PROGRAM EXECUTION ===================================//
	
	public static void main(String[] args) throws IOException, InterruptedException {
		 
		//Execute VBS Script to get Browser Version 
		executeVBSScript(executable,script);
		
		//Read File Data
		String chromeVersion = readFile(chromeVersionfilePath);
		System.out.println(chromeVersion);
		
		
		//Initialize Driver
		driver = initializeEdgeDriver();
		
		//Fetch chromedriver repo
		driver.get("https://chromedriver.storage.googleapis.com/index.html");
		Thread.sleep(3000);
		//Scroll to desired view and find linktext. Check if chromeversion exists in list, if so then download else quit
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,1750)", "");
		WebElement element = driver.findElement(By.linkText("87.0.4280.20"));
	    if(element != null)
	    {
			element.click();
			Thread.sleep(1500);
			WebElement download = driver.findElement(By.linkText("chromedriver_win32.zip"));
			download.click();
			Thread.sleep(10000);
			driver.quit();			
		} else {
			driver.quit();
		}
	    
	    //Store products Path
	    ArrayList<String> products = new ArrayList<String>();
	    products.add(lloPath);
	    products.add(MAFCorePath);
	    products.add(chpTestPath);
	    products.add(RTBrazilPath);
	    
	    //Unzip to each product /drivers directory thus updating
	    for(int i=0; i<products.size(); i++){
	    	uzp = new UnzipUtility();
	    	uzp.unzip(downloadedChromeDriverPath, products.get(i));
	    }		
	}
	
	
	
	
}
