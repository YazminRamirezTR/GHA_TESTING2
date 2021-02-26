package com.trgr.quality.maf.basetest;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IResultMap;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.trgr.quality.maf.webdriver.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;
import com.trgr.quality.maf.commonutils.JiraConnector;
import com.trgr.quality.maf.fileconfiger.PropertiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

	public static String Envirnomentundertest;
	public static String productUnderTest;
	public static String ProductUrl;
	public static WebDriver driver;
	protected static ExtentReports extentReports;
	protected static ExtentTest extentLogger;
	protected static String reportPath;
	public static String BrowserType;
	protected static Logger log = LoggerFactory.getLogger(BaseTest.class);
	public String jiraLink;
	public JiraConnector jiraConnect;

	IResultMap tests;

	@BeforeSuite(alwaysRun = true)
	@Parameters({  "Environment", "ProductToTest", "Browser" })
	public static void setup( @Optional String environment, @Optional String productToTest, @Optional String browser)
			throws Exception {
		PropertiesRepository.appendProperties("TestConfig.properties");

		PropertiesRepository.appendProperties("log4j.properties");
		/* Implemented log4j ,Just enable below comments and use the feature */
		log.info("Start logging");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		log.info("Start logging started ");
		Calendar cal = Calendar.getInstance();

		if (environment != null && !environment.isEmpty())
			Envirnomentundertest = environment;
		else
			Envirnomentundertest = PropertiesRepository.getString("com.trgr.maf.environmentToTest");

		if (productToTest != null && !productToTest.isEmpty())
			productUnderTest = productToTest;
		else
			productUnderTest = PropertiesRepository.getString("com.trgr.maf.productToTest");
		
		/*if(GridEnabled){
			driver = WebDriverGridInstance.buildInstance(port, HubURL);
		} 
		else {}*/
		if (browser != null && !browser.isEmpty()) {
			BrowserType = browser;	
			driver = WebDriverFactory.getInstance(BrowserType);
		} else {
			BrowserType = PropertiesRepository.getString("global.browser.name");
			driver = WebDriverFactory.getInstance(BrowserType);
		}
		
		WebDriverFactory.managedriver(driver);

		// Building the product url based on the environment being tested for
		// each product

		switch (productUnderTest) {
		case "spain":
			if (Envirnomentundertest.equalsIgnoreCase("dev")) {
			PropertiesRepository.appendProperties("gcmsspain.properties");
			ProductUrl = PropertiesRepository.getString("com.trgr.gcms.spain.dev.url");
			break;
			}else if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("gcmsspain.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.gcms.spain.qc.url");
				break;
			} else {
				PropertiesRepository.appendProperties("gcmsspain.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.gcms.spain.client.url");
				break;
			}
		case "wlasia":
			if (Envirnomentundertest.equalsIgnoreCase("prod")) {
				PropertiesRepository.appendProperties("westlawasia.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.wlasia.prod.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("westlawasia.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.wlasia.client.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("westlawasia.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.wlasia.qc.url");
				break;
			}
			else {
				PropertiesRepository.appendProperties("westlawasia.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.wlasia.dev.url");
				break;
			}
		case "rtbrazil":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("rtbrazil.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.rtbrazil.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("rtbrazil.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.rtbrazil.client.url");
				break;
			} else {
				PropertiesRepository.appendProperties("rtbrazil.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.rtbrazil.prod.url");
				break;
			}
		case "chparg":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("chparg.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chparg.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("chparg.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chparg.client.url");
				break;
			} else {
				break;
			}
		case "chpury":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("chpury.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpury.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("chpury.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpury.client.url");
				break;
			} else {
				break;
			}
		case "chppy":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("chppy.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chppy.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("chppy.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chppy.client.url");
				break;
			} else {
				break;
			}
		case "chpmex":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("chpmex.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpmex.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("chpmex.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpmex.client.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("prod")) {
				// Integrated client Environment for MAF
				PropertiesRepository.appendProperties("chpmex.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpmex.prod.url");
				break;
			} else {
				break;
			}
		case "llofourarg":
			if (Envirnomentundertest.equalsIgnoreCase("qc2")) {
				PropertiesRepository.appendProperties("llofourarg.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.llofourarg.qc2.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				log.info("llofourarg properties is loaded successfully");
				PropertiesRepository.appendProperties("llofourarg.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.llofourarg.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("llofourarg.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.llofourarg.client.url");
				break;
			} else {
				PropertiesRepository.appendProperties("llofourarg.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.llofourarg.prod.url");
				break;
			}
		case "llochile":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("llochile.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.llochile.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("llochile.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.llochile.client.url");
				break;
			} else {
				break;
			}
		case "llopy":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("llopy.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.llopy.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("llopy.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.llopy.client.url");
				break;
			} else {
				break;
			}
		case "lloury":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("lloury.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.lloury.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("lloury.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.lloury.client.url");
				break;
			} else {
				break;
			}
		case "llope":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("llope.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.llope.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("llope.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.llope.client.url");
				break;
			} else {
				break;
			}
		case "chppe":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("chppe.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chppe.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("chppe.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chppe.client.url");
				break;
			} else {
				break;
			}
		case "chpbr":
			if (Envirnomentundertest.equalsIgnoreCase("qc")) {
				PropertiesRepository.appendProperties("chpbr.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpbr.qc.url");
				break;
			} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
				PropertiesRepository.appendProperties("chpbr.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpbr.client.url");
				break;
			} else {
				PropertiesRepository.appendProperties("chpbr.properties");
				ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpbr.prod.url");
				break;
			}
		
			case "chpchile":
				if (Envirnomentundertest.equalsIgnoreCase("qc")) {
					PropertiesRepository.appendProperties("chpchile.properties");
					ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpchile.client.url");
					break;
				} else if (Envirnomentundertest.equalsIgnoreCase("client")) {
					PropertiesRepository.appendProperties("chpchile.properties");
					ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpchile.client.url");
					break;
				} else {
					PropertiesRepository.appendProperties("chpchile.properties");
					ProductUrl = PropertiesRepository.getString("com.trgr.maf.chpchile.client.url");
					break;
				}
			}

		// reportPath = "./extentReport/extentReport-" + productUnderTest+"/testReport_"+ dateFormat.format(cal.getTime()) + ".html";
		reportPath = "./" + "testReport" + ".html";

		// Setup extent report object
		extentReports = new ExtentReports(reportPath, true);
		// extentReports.assignProject();
		extentReports.addSystemInfo(productUnderTest.toUpperCase(), Envirnomentundertest);
		
		//Printing the user credentials used for the regression on the report
		String username = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + "." +"username");
		String password = PropertiesRepository.getString("com.trgr.maf." + BaseTest.productUnderTest + "." +"password");
		extentReports.addSystemInfo("Username", username );
		extentReports.addSystemInfo("Password", password );
		//adding browser info
		extentReports.addSystemInfo("Browser", BrowserType);
		
       //Save the previous run / existing short summary report from the folder
		String shortSummaryReport ="shortTestSummary.html";
		backupPreviousRunResults(shortSummaryReport);

	}
	
	
	  public static void backupPreviousRunResults(String fileName) throws IOException {
	        String projectPath = System.getProperty("user.dir");
	        String tempFile = projectPath + File.separator+fileName;
	        File file = new File(tempFile);
	       
	        // if file does exists, then delete and create a new file
	        if (file.exists()) {

	                File newFileName = new File(projectPath + File.separator+ "previousrun_"+fileName);
	                file.renameTo(newFileName);
	                
	            }       
	    }
	
	/*
	 * Based on the abbrevation of product under test define the entire application name
	 */
	  public String applicationNameToPrint(String productUnderTest)
	    {
			switch(productUnderTest)
			{
			case "rtbrazil":
				return "RT Brazil";	
			case "chpmex":
				return "Checkpoint Mexico";
			case "chparg":
				return "Checkpoint Argentina";
			case "chpbr":
				return "Checkpoint Brazil";
			case "chpury":
				return "Checkpoint Uruguay";
			case "chppy":
				return "Checkpoint Paraguay";
			case "chppe":
				return "Checkpoint Peru";
			case "chpchile":
				return "Checkpoint Chile";
			case "llofourarg":
				return "LLO Arg 4.0";
			case "llochile":
				return "LLO Chile 4.0";
			case "llope":
				return "LLO Peru 4.0";
			case "lloury":
				return "LLO Uruguay 4.0";
			case "llopy":
				return "LLO Paraguay 4.0";
			}
			return productUnderTest;
	    }

	  
	  
	  /*============== START- SET UP WITH EXTENT REPORT ==============*/ 

	  /*
	   * Setting up the extent test for each test run
	   * This method needs to be called in the beginning of the test to define the test name 
	   * and to assign the test category for the report
	   */
	public ExtentTest setUpExtentTest(ExtentTest extentLogger, String categoryName, String testName) {
		extentLogger = extentReports.startTest(testName);
		extentLogger.assignCategory(categoryName);

		return extentLogger;
	}
	
	/*============== END - SET UP WITH EXTENT REPORT ==============*/ 
	
	
	
	/*============== START -METHODS USED FOR VARIOUS EXTENT REPORT LOGGING AT TEST LEVEL ==============*/
	
	
	/*
	 * This method is used for logging the JIRA number with Issue summary on the extent report and INFO statement
	 */
	public ExtentTest logJiraTestCaseDescription(ExtentTest extentTest, String issueSummary, String jiraNum)
			throws IOException {
		if (jiraNum.contains("MAFQABANG")) {
			jiraLink = "http://ent.jira.int.thomsonreuters.com/browse/";
		} else {
			jiraLink = "http://jira.legal.thomsonreuters.com:8090/browse/";
		}

		extentTest.log(LogStatus.INFO,
				"<a href='" + jiraLink + jiraNum + "' target='_blank'>" + jiraNum + "</a> :" + issueSummary);

		return extentTest;

	}
	
	/*
	 * This method is used for the logging the extent test result based on the "testResultStatus" flag
	 * If the Jira Number contains MAFQABANG jira instance - provide the Jira Link for MAFQABANG instance else
	 * provide jira link for the MAFAUTO jira instance which is for CHP MEX jira test cases
	 */
	public void logExtentStatus(ExtentTest extentTest, boolean testResultStatus, String issueSummary, String key,
			String testData, String jiraNum) throws IOException {
		if (jiraNum.contains("MAFQABANG")) {
			jiraLink = "http://ent.jira.int.thomsonreuters.com/browse/";
		} else {
			jiraLink = "http://jira.legal.thomsonreuters.com:8090/browse/";
		}
		if (testResultStatus)
			extentTest.log(LogStatus.PASS, "<a href='" + jiraLink + jiraNum + "' target='_blank'>" + jiraNum + "</a> :"
					+ issueSummary + "<br>" + key + " : " + testData);
		else
			extentTest.log(LogStatus.FAIL, "<a href='" + jiraLink + jiraNum + "' target='_blank'>" + jiraNum + "</a> :"
					+ issueSummary + "<br>" + key + " : " + testData + "<br>" + takesScreenshot_Embedded());

	}
	
	/*
	 * This method is used for the logging the extent test result based on the "testResultStatus" flag with the defect number
	 * If the Jira Number contains MAFQABANG jira instance - provide the Jira Link for MAFQABANG instance else
	 * provide jira link for the MAFAUTO jira instance which is for CHP MEX jira test cases
	 */
	public void logExtentStatusWithDefectInfo(ExtentTest extentTest, boolean testResultStatus, String issueSummary, String key,
			String testData, String jiraNum, String defectNum) throws IOException {
		
		String defectLink="<a href='" + "http://jira.legal.thomsonreuters.com:8090/browse/" + defectNum + "' target='_blank'>" + defectNum + "</a>";
		if (jiraNum.contains("MAFQABANG")) {
			jiraLink = "http://ent.jira.int.thomsonreuters.com/browse/";
		} else {
			jiraLink = "http://jira.legal.thomsonreuters.com:8090/browse/";
		}
		if (testResultStatus)
			extentTest.log(LogStatus.PASS, "<a href='" + jiraLink + jiraNum + "' target='_blank'>" + jiraNum + "</a> :"
					+ issueSummary + "<br>" + key + " : " + testData);
		else
			extentTest.log(LogStatus.FAIL, "<a href='" + jiraLink + jiraNum + "' target='_blank'>" + jiraNum + "</a> :"
					+ issueSummary + "<br>" + key + " : " + testData + "<br>" + "Failed due to defect: " + defectLink  + "<br>" + takesScreenshot_Embedded());

	}
	
	
	/*
	 * This method is used for logging extent test result based on the "testResultStatus" flag passed but this 
	 * method will take the test data information like key and its value
	 */
	public void logExtentStatus(ExtentTest extentTest, boolean testResultStatus, String issueSummary, String jiraNum)
			throws IOException {

		if (jiraNum.contains("MAFQABANG")) {
			jiraLink = "http://ent.jira.int.thomsonreuters.com/browse/";
		} else {
			jiraLink = "http://jira.legal.thomsonreuters.com:8090/browse/";
		}
		if (testResultStatus)
			extentTest.log(LogStatus.PASS,
					"<a href='" + jiraLink + jiraNum + "' target='_blank'>" + jiraNum + "</a> :" + issueSummary);
		else
			extentTest.log(LogStatus.FAIL, "<a href='" + jiraLink + jiraNum + "' target='_blank'>" + jiraNum + "</a> :"
					+ issueSummary + "<br>" + takesScreenshot_Embedded());

	}

	/*
	 * This method is used for the logging the extent test result based on the "testResultStatus" flag
	 * If product undertest is CHP MEX this method is used to show the 1:1 mapping with MAFAUTO and MAFQABANG test cases
	 */
	public void logExtentStatus(ExtentTest extentTest, boolean testResultStatus, String issueSummary, String[] jiraNum)
			throws IOException 
	{
		String newJiraLink = "";
		
		if(!productUnderTest.contains("chpmex"))
		{
			String oneTestJira = "";
			for (int i = 0; i < jiraNum.length; i++) 
			{
				if(jiraNum[i].contains("MAFQABANG")) 
				{
					oneTestJira = jiraNum[i];
					break;
				}
			}
			logExtentStatus(extentTest, testResultStatus, issueSummary, oneTestJira);
		}
		else
		{
			for (int i = 0; i < jiraNum.length; i++) 
			{
				if (jiraNum[i].contains("MAFQABANG")) {
					jiraLink = "http://ent.jira.int.thomsonreuters.com/browse/";
					newJiraLink = newJiraLink + " "+ "<a href='" + jiraLink + jiraNum[i] + "' target='_blank'>" + jiraNum[i] + "</a>";
				}
				else 
				{
					jiraLink = "http://jira.legal.thomsonreuters.com:8090/browse/";
					if(newJiraLink.length() >2)
					newJiraLink = newJiraLink.split("<==>")[0] + ", " +"<a href='" + jiraLink + jiraNum[i] + "' target='_blank'>" + jiraNum[i] + "</a>" + " <==>";
					else
						newJiraLink = "<a href='" + jiraLink + jiraNum[i] + "' target='_blank'>" + jiraNum[i] + "</a>" + " <==>";
				}
			}
			
				if (testResultStatus)
					extentTest.log(LogStatus.PASS,
							newJiraLink +":" + issueSummary);
				else
					extentTest.log(LogStatus.FAIL, newJiraLink + ":" + issueSummary + "<br>" + takesScreenshot_Embedded());
		
		}
		
			
	}

	/*
	 * This method is used only for logging INFO log statements on the report with IssueSummary, Testdata field name and field value.
	 */
	
	public void logExtentNoResultsAsInfo(ExtentTest extentTest, String issueSummary, String key, String testData,
			String jiraNum) throws IOException {
		if (jiraNum.contains("MAFQABANG")) {
			jiraLink = "http://ent.jira.int.thomsonreuters.com/browse/";
		} else {
			jiraLink = "http://jira.legal.thomsonreuters.com:8090/browse/";
		}
		extentTest.log(LogStatus.INFO, "<a href='" + jiraLink + jiraNum + "' target='_blank'>" + jiraNum + "</a> :"
				+ issueSummary + "<br>" + key + ":" + testData + "<br>" + takesScreenshot_Embedded());
	}
	
	/*============== END -METHODS USED FOR VARIOUS EXTENT REPORT LOGGING AT TEST LEVEL ==================*/
	
	

	/*============== START - LOG TEST RELATED EXCEPTIONS WITH EXTENT REPORT ==============*/
	
	 /*
	   * Log the exceptions originated from tests
	   * 
	   */
	  public void logTestExceptionToExtentLogger(String testName, Exception testExp, ExtentTest extentLogger) throws IOException
	  {
		  
		  if((testExp.getMessage()==null) || !(testExp.getMessage().equals("JSONDataReadError")))
				extentLogger.log(LogStatus.FAIL, "Something went wrong with <b> " + testName+  " </b>Exiting test.<br>" + takesScreenshot_Embedded()
					+ "<br>" + displayErrorMessage(testExp));
	  }
	  
	  /*============== END - LOG TEST RELATED EXCEPTIONS WITH EXTENT REPORT ==============*/
	  
	  
	  /*============== START - SET THE TEST TO FAIL ==============*/
	  /*
	   * Setting the extent test to fail when the test has only INFO logs for test data / exceptions
	   */
	  public void setTestToFailUponOnlyOneINFOLog(ExtentTest extentLogger) 
	  {
		  List<Log> lstOfLogsForATest = extentLogger.getTest().getLogList();
		  boolean isOnlyInfoLogged = false;
		  
		  for (Log log : lstOfLogsForATest) {
			 if(log.getLogStatus().toString().contains("info"))
			 {
				 isOnlyInfoLogged = true;
			 }
			 else
			 {
				 isOnlyInfoLogged = false;
			 }
		}
		  
		  if(isOnlyInfoLogged)
		  {
			  extentLogger.getTest().setStatus(LogStatus.FAIL);
		  }
		  
	  }
	  
	  /*============== END - SET THE TEST TO FAIL ==============*/

	
	public String getIssueTitle(String issueKey, String issueSummary) throws Exception {
		
		/*  jiraConnect = new JiraConnector(); 
		  Issue issueHandle = jiraConnect.getIssue(issueKey);
		  
		  if(issueHandle!=null)  
			  issueSummary = issueHandle.getSummary(); 
			  
		 */
		return issueSummary;
	}

	/**
	 * Takes a screen shot of the browser and Refer the link in extend Reports
	 * 
	 * @throws IOException
	 */ /*
	protected String takeScreenShot(String testName) throws IOException {
		int randomNum = RandomUtils.generatePsuedoRandomNumber(1, 100);
		final WebDriver augmentedDriver = new Augmenter().augment(driver);
		final File screenShot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenShot, new File("./screenshots/" + testName + randomNum + ".jpg"));
		String imageLocation = System.getProperty("user.dir") + "\\screenshots\\" + testName + randomNum + ".jpg";
		return imageLocation;
	}*/

	/*
	 * This method takes the Screen shot of the browser and Embed error image in
	 * Extend Reports
	 */
	protected String takesScreenshot_Embedded() throws IOException {

		String encodedImage = "";
		try {
			encodedImage = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return String.format("<a href='data:image/png;base64,%s' target='_blank'>Click here to get screenshot</a></br>",
				encodedImage);

	}

	/* This method prints the error message in Extend Reports */
	protected String displayErrorMessage(Exception exc) {
		String error = null;
		error = "Please find Detail Error Below:<br>"
				+ getFormattedExceptionHoverMessage(exc, "Exception in " + exc.getClass().toString());
		return error;
	}

	/* This method prints HTML formatted message from exception object */
	protected String getFormattedExceptionHoverMessage(Exception exc, String message) {

		return "<span style='color:blue;font-weight:bold;' title='" + exc.getMessage() + "'>" + message + "</span";
	}
	
	
	
	@AfterSuite(alwaysRun = true)
	public void driverclose() {
		
	//	WebDriverFactory.removedriver();
		extentReports.endTest(extentLogger);
		extentReports.flush();
		if(BrowserType.contains("edge")){
			WebDriverFactory.edgeService.stop();
		} else {
		WebDriverFactory.removedriver();
		}
	}

}
