package com.trgr.quality.maf.commonutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;

public class ExtentReportTestNGListener extends BaseTest implements IReporter {
	protected static ExtentReports extent;
	protected static String reportPath ;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	Calendar cal = Calendar.getInstance();
	int totalTestsRun, totalPassedTests, totalFailedTests, totalSkippedTests;
	double passPercentage;
	String testSuiteStartTime="", testSuiteEndTime;
	boolean failedTestHeaderFlag = true;
	long totalStartTime;
	long totalEndTime;
	SimpleDateFormat dateOfExecution = new SimpleDateFormat("dd-MM-yyyy");
	String applicationName;
	public StringBuilder htmlStringBuilder=new StringBuilder();
	SimpleDateFormat summaryFormat = new SimpleDateFormat("hh:mm:ss");
	
     @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) 
    {
    	
    	 //calculating the total summary of the regression suite
    	  for (ISuite suite : suites) {
              Map<String, ISuiteResult> result = suite.getResults();
              
              for (ISuiteResult r : result.values()) {
                  ITestContext context = r.getTestContext();
                  
                  totalTestsRun = totalTestsRun + context.getPassedTests().size() + context.getFailedTests().size() + context.getSkippedTests().size();
                  totalPassedTests =  totalPassedTests + context.getPassedTests().size();
                  totalFailedTests = totalFailedTests + context.getFailedTests().size();
                  totalSkippedTests = totalSkippedTests + context.getSkippedTests().size();     
                  totalStartTime = totalStartTime + context.getStartDate().getTime() ;
                  totalEndTime = totalEndTime + context.getEndDate().getTime() ;
                  
              }
                   
          }
    	  
    	  //calculating pass % for the suite and restrict it to 2 decimal
    	  passPercentage = (new Integer(totalPassedTests).doubleValue() *100) /new Integer(totalTestsRun).doubleValue();
    	  passPercentage= BigDecimal.valueOf(passPercentage).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    	  
    	  //calculate the start and end time of the suite
    	  testSuiteStartTime = testSuiteStartTime + summaryFormat.format(new Date(totalStartTime));	
          testSuiteEndTime = summaryFormat.format(new Date(totalEndTime));                  
          
      	try {
      		applicationName = applicationNameToPrint(BaseTest.productUnderTest);
			createTestSummaryHTMLTable();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    	  //iterating through the test context for individual failed test report.
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
 
               try {
				createFailedTestSummaryReport(context.getFailedTests());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
        }
        
        htmlStringBuilder.append("</table></body><br /><br /></html>");
	     try {
			WriteToFile(htmlStringBuilder.toString(),"./shortTestSummary.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
  
    public void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;
    
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extent.startTest(result.getMethod().getMethodName());
  
                test.getTest().setStartedTime(getTime(result.getStartMillis()));
                test.getTest().setEndedTime(getTime(result.getEndMillis()));
    
                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);
  
                String message = "Test " + status.toString().toLowerCase() + "ed";
  
                if (result.getThrowable() != null)
                    message = result.getThrowable().getMessage();
  
                test.log(status, message);
  
                extent.endTest(test);
            }
        }
    }
    
    /*
	    * This method builds the Published Test Report (Extent Report) Url based on the product given tested 
	    */
	    public String buildJenkinsPublishedTestReportURL(String productUnderTest)
	    {
	    	String jenkinsUrl ="https://jenkins.tr-corporate-prod.aws-int.thomsonreuters.com/fenric-tr-win/job/";
			switch(productUnderTest)
			{
			case "rtbrazil":
				return jenkinsUrl + "RTBrazil-Regression/TestReport";
			case "chpmex":
				return jenkinsUrl + "CHPMEX_Regression/TestReport";
			case "chparg":
				return jenkinsUrl + "CHPARG_Regression/TestReport";
			case "chpbr":
				return jenkinsUrl + "CHPBR_Regression/TestReport";
			case "chpury":
				return jenkinsUrl + "CHPURY_Regression/TestReport";
			case "chppy":
				return jenkinsUrl + "CHPPY_Regression/TestReport";
			case "chppe":
				return jenkinsUrl + "CHPPERU_Regression/TestReport";
			case "chpchile":
				return jenkinsUrl + "CHPCHILE_Regression/TestReport";
			case "lloury":
				return jenkinsUrl + "LLOURY-Regression/TestReport";
			case "llopy":
				return jenkinsUrl + "LLOPY-Regression/TestReport";
			case "llofourarg":
				return jenkinsUrl + "LLOARG-Regression/TestReport";
			case "llochile":
				return jenkinsUrl + "LLOCHILE-Regression/TestReport";
				
			}
			return jenkinsUrl;
		}
    
    /*
     * Using the IResultMap object, sorts the failed tests using group and passes the necessary
     * values to build the HTML report using failed test values on the table
     */
    public void createFailedTestSummaryReport(IResultMap tests) throws Exception
    {
    	String testCategory = "", testClassName = "", testName = "", testResult = "", testcaseJiraNum ="" ;
       	String testDuration = null;
    	String defectNums ="";
    	    	
    	 if (tests.size() > 0) {
             for (ITestResult result : tests.getAllResults()) {
               
                 for (String group : result.getMethod().getGroups())
                 {
                	 if(BaseTest.productUnderTest.equals(group))
                	 {
                		 testCategory = group;
                	 }
                	 if(testCategory.length() ==0)
                	 {
                		testCategory = group; 
                	 }
                 }  
                 
				 
                if(!result.isSuccess())
                 {
                	
                	 testClassName = result.getTestClass().getName();
                	 testName = result.getMethod().getMethodName();
                	 testResult = "FAILED";
                	 testDuration = summaryFormat.format(result.getEndMillis() - result.getStartMillis());
                	 testcaseJiraNum = result.getMethod().getDescription();
                	 //adding the link for easy navigation from test report
                	 if(testcaseJiraNum.contains("MAFQABANG"))
                	 {
                		 testcaseJiraNum = "<a href='" + "http://ent.jira.int.thomsonreuters.com/browse/" + testcaseJiraNum + "' target='_blank'>" + testcaseJiraNum + "</a>";
                	 }
                	 else
                	 {
                		 testcaseJiraNum = "<a href='" + "http://jira.legal.thomsonreuters.com:8090/browse/" + testcaseJiraNum + "' target='_blank'>" + testcaseJiraNum + "</a>";
                	 }	 
                	 try
                	 {
                		 defectNums = result.getAttribute("defect").toString();
                		 if(defectNums.length() ==0) 
                    	 {
                    		 defectNums ="Investigate";
                    	 }
                    	 else
                    	 {
                    		 //creating link for the defect to access from the report
                    		 defectNums="<a href='" + "http://jira.legal.thomsonreuters.com:8090/browse/" + defectNums + "' target='_blank'>" + defectNums + "</a>";
                    	 }
                    	
                	 }
                	 catch(Exception ex)
                	 {
                		 //due to the assert all for each test, any exception on the test will result in Assertion.
                		 //Hence not using the result.getThrowable().toString()
                		 defectNums = 	 "Investigate";
                		 
                	 }
                		 
                	 if(testName.length() >0)
                	 {
                		 createFailedTestSummaryHTMLTable();
                		 addFailedTestToReport(testcaseJiraNum, testClassName, testName, testResult,  defectNums, testCategory);
                	 }
                 }
                 
             }
         }   	 
    	
    }
    
       
    private void createTestSummaryHTMLTable() throws IOException
    {
    	  htmlStringBuilder.append("<html><head><title>Automation Summary Report for</title></head>");
          htmlStringBuilder.append("<body>");
          htmlStringBuilder.append("<p><b>Test Summary Report: </b><br />");
          htmlStringBuilder.append("<b>Application Name:</b> "+ applicationName + "<br />");
          htmlStringBuilder.append("<b>Environment used:</b> "+ BaseTest.Envirnomentundertest + "<br />");
          System.out.println(BaseTest.Envirnomentundertest);
          htmlStringBuilder.append("<b>Browserd used:</b> "+ BaseTest.BrowserType + "<br />");
          htmlStringBuilder.append("<b>Date of Execution:</b> "+ dateOfExecution.format(new Date()) + "<br />");
          htmlStringBuilder.append("<b>Start Date/Time:</b> "+ testSuiteStartTime + "<br />");
          htmlStringBuilder.append("<b>End Date/Time:</b> "+ testSuiteEndTime + "</p>");
          
          htmlStringBuilder.append("<table border=\"5\" width=\"50%\" cellpadding=\"4\" cellspacing=\"3\" background-color=\"#b0bec5\">");
          htmlStringBuilder.append("<tr><th><b>Total Tests</b></th><th><b>Passed Tests</b></th><th><b>Failed Tests</b></th><th><b>Skipped Tests</b></th><th><b>Pass %</b></th></tr>"); 
          htmlStringBuilder.append("<tr><td align=\"center\">"+ totalTestsRun +"</td><td style=\"color:green\" align=\"center\">"+  totalPassedTests +"</td><td style=\"color:red\" align=\"center\">"+ totalFailedTests+"</td><td align=\"center\">"+ totalSkippedTests+"</td><td align=\"center\">"+ passPercentage+"%</td></tr>");
          
     	 htmlStringBuilder.append("</table><br />");
     	 
     	 String jenkinsTestReportUrl = buildJenkinsPublishedTestReportURL(BaseTest.productUnderTest);
         
         htmlStringBuilder.append("<p>Access complete regression report"+ "<a href='" + jenkinsTestReportUrl + "' target='_blank'> here </a></p>");
        
     	
    }
    
    /*
     * Creates the table with the structure needed
     */
    private void createFailedTestSummaryHTMLTable()
    {     
    	if(failedTestHeaderFlag)
    	{
         htmlStringBuilder.append("<p><b> Failed Tests:</b></p>");
                  
         //adding the table with the structure needed
	      htmlStringBuilder.append("<table border=\"5\" width=\"50%\" cellpadding=\"4\" cellspacing=\"3\" background-color=\"#b0bec5\">");
	      htmlStringBuilder.append("<tr><th><b>Test Class</b></th><th><b>TestCase JIRA#</b></th><th><b>Test Name</b></th><th><b>Test Result</b></th><th><b>Reason for failure</b></th><th><b>Category</b></th></tr>");
	      failedTestHeaderFlag = false;
    	}   
    }
    
    
    /*
     * Adding the failed test details to report
     */
	private void addFailedTestToReport(String testcaseJiraNum, String testClass, String testName, 
    		String testResult, String defectNums, String testCategory) throws IOException 
    {
		 htmlStringBuilder.append("<tr><td>"+ testClass +"</td><td>"+ testcaseJiraNum +"</td><td>"+  testName +"</td><td style=\"color:red\">"+ testResult+"</td><td>"+ defectNums+"</td><td>"+ testCategory+"</td></tr>");   
	}
	
	
	/*
	 * This method reads the previous summary report to check if the passed in 
	 * testname is present in the previous report as failed test
	 
	public boolean getStatusOfFailedTest(String testName) throws Exception
	{
	    String previousResultFileName = "previousrun_shortTestSummary.html";
		String projectPath = System.getProperty("user.dir");
        String tempFile = projectPath + File.separator+previousResultFileName;
        File file = new File(tempFile);
        
       if (file.exists())
        {
        	//Read the short summary file content
			try {
			    //bufferReader = new BufferedReader(new FileReader(filePath));
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferReader = new BufferedReader(fileReader); 
			    String str;
			    while ((str = bufferReader.readLine()) != null) 
			    {
			    	//getting only the required information from the test report
			    	str = str.split("Access")[1];
			    	
			    	return str.contains(testName);
			    }
			    bufferReader.close();
			} 
			catch (Exception e) 
			{
			}
        	
        }
        
       //If the test name is not found return false
        return false;
	}
*/    
	
	/*
	 * Writing the content to summary report
	 */
    public static void WriteToFile(String fileContent, String fileName) throws IOException {
        String projectPath = System.getProperty("user.dir");
        String tempFile = projectPath + File.separator+fileName;
        File file = new File(tempFile);
        // if file does exists, then delete and create a new file
        if (file.exists()) {
            try {
                File newFileName = new File(projectPath + File.separator+ "backup_"+fileName);
                file.renameTo(newFileName);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //write to file with OutputStreamWriter
        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        Writer writer=new OutputStreamWriter(outputStream);
        
     //   writer.write("<b> Test Results Summary </b>");
        writer.write(fileContent);
        writer.close();

    }

	public Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();        
    }


}