package com.trgr.quality.maf.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.testng.annotations.Test;
import com.trgr.quality.maf.basetest.BaseTest;

public class ReportOfAllTestSuitesFrmJenkins 
{
	public StringBuilder contentBuilder = new StringBuilder();
	public StringBuilder htmlBuilder = new StringBuilder();
	public Proxy proxy;
	
	private String getTestSummaryFilePath(String productName)
	{
		String testSummaryPath ="https://jenkins.tr-corporate-prod.aws-int.thomsonreuters.com/fenric-tr-win/job/";
		
		switch(productName)
		{
		case "rtbrazil":
			testSummaryPath= testSummaryPath + "RTBrazil-Regression/ws/RTBrazil/shortTestSummary.html";
			break;
		case "llofourarg":
			testSummaryPath= testSummaryPath + "LLOARG-Regression/ws/LLO/shortTestSummary.html";
			break;
		case "llochile":
			testSummaryPath= testSummaryPath + "LLOCHILE-Regression/ws/LLO/shortTestSummary.html";
			break;
		case "llopy":
			testSummaryPath= testSummaryPath + "LLOPY-Regression/ws/LLO/shortTestSummary.html";
			break;
		case "lloury":
			testSummaryPath= testSummaryPath + "LLOURY-Regression/ws/LLO/shortTestSummary.html";
			break;
		case "chpmex":
			testSummaryPath= testSummaryPath + "CHPMEX_Regression/ws/MEX/shortTestSummary.html";
			break;
		case "chparg":
			testSummaryPath= testSummaryPath + "CHPARG_Regression/ws/CHP/shortTestSummary.html";
			break;
		case "chpbr":
			testSummaryPath= testSummaryPath + "CHPBR_Regression/ws/CHP/shortTestSummary.html";
			break;
		case "chpury":
			testSummaryPath= testSummaryPath + "CHPURY_Regression/ws/CHP/shortTestSummary.html";
			break;
		case "chppy":
			testSummaryPath= testSummaryPath + "CHPPY_Regression/ws/CHP/shortTestSummary.html";
			break;
		case "chppe":
			testSummaryPath= testSummaryPath + "CHPPERU_Regression/ws/CHP/shortTestSummary.html";
			break;
		case "chpchile":
			testSummaryPath= testSummaryPath + "CHPCHILE_Regression/ws/CHP/shortTestSummary.html";
			break;
			default:
				testSummaryPath="application not found";
				break;
				
		}
		return testSummaryPath;
		
	}

	/*
	 * Gathers the test summary related to CHP and LLO Applications latest run
	 */
	@Test
	public void summaryOfAllSuites() throws IOException
	{
		String filePath ="";
		
		BufferedReader bufferReader;
		ArrayList<String> lstOfApps = new ArrayList<>();
		
		lstOfApps.add("chparg");
		lstOfApps.add("chpbr");
		lstOfApps.add("chpmex");
		lstOfApps.add("chppy");
		lstOfApps.add("chppe");
		lstOfApps.add("chpchile");
		lstOfApps.add("chpury");
		lstOfApps.add("llofourarg");
		lstOfApps.add("llochile");
		lstOfApps.add("lloury");
		lstOfApps.add("llopy");
		lstOfApps.add("rtbrazil");
		
		
		//creating the HTML page for structure
		createFailedTestSummaryHTMLTable();
		
		for (String productName : lstOfApps) 
		{
			this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.181.51.198",80));
			filePath = getTestSummaryFilePath(productName);
			
			URL url = new URL(filePath);
			
			//Read the short summary file content
			try {
			    //bufferReader = new BufferedReader(new FileReader(filePath));
				//bufferReader = new BufferedReader(new InputStreamReader(url.openStream()));
			    URLConnection connection = null;
			    if (this.proxy != null)
			        connection = url.openConnection(this.proxy);
			    else
			        connection = url.openConnection();

			    //connection.setRequestProperty("User-Agent", AGENT);
			     
			     BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			    String str;
			    while ((str = br.readLine()) != null) 
			    {
			    	//getting only the required information from the test report
			    	str = str.split("Access")[0];
			    	
			    	//get the application name from the report
			    	String appName = str.split("Application Name:</b>")[1];
			    	str = appName;
			    	appName = appName.split("<br />")[0];
			    	
			    	//get the environment used
			    	String environmentUsed = str.split("Environment used:</b>")[1];
			    	str = environmentUsed;
			    	environmentUsed = environmentUsed.split("<br />")[0];
			    	
			    	
			    	//get the Date of the run
			    	String dateOfExecution = str.split("Date of Execution:</b>")[1];
			    	str = dateOfExecution;
			    	dateOfExecution = dateOfExecution.split("<br />")[0];
			    	
			    	//get the pass%
			    	String passPercentage = str.split("red")[1];
			    	str = passPercentage;
			    	passPercentage = passPercentage.split("%")[0];
			    	passPercentage = passPercentage.substring(passPercentage.length()-5, passPercentage.length());
			    	if(passPercentage.contains(">"))
			    		passPercentage = passPercentage.split(">")[1];
			    	
			    	//Link for the complete report
			    	String jenkinsTestReportUrl = buildJenkinsPublishedTestReportURL(productName);
			    	
		    		addFailedTestToReport(appName, environmentUsed, dateOfExecution, passPercentage+"%", "<a href='" + jenkinsTestReportUrl + "' target='_blank'> Full Test Report </a>");
       
			    }
			    br.close();
			} 
			catch (Exception e) 
			{
			}
				
		}
		
		htmlBuilder.append("</table></body><br /><br /></html>");
		writeFullSummary(htmlBuilder.toString(), "./fullTestSummary.html");
		
	}
	
	 private void createFailedTestSummaryHTMLTable()
	 {     	    	
		 htmlBuilder.append("<p><b>Test Report for all applications:</b></p>");
	                  
	         //adding the table with the structure needed
		 htmlBuilder.append("<table border=\"5\" width=\"50%\" cellpadding=\"4\" cellspacing=\"3\" background-color=\"#b0bec5\">");
		 htmlBuilder.append("<tr><th><b>Application Name</b></th><th><b>Environment</b></th><th><b>Date</b></th><th><b>Pass %</b></th><th><b>Complete Report </b></th></tr>");    	   
	  }
	
	private void addFailedTestToReport(String applicationName, String environment, String date, 
    		String passPercentage, String jenkinsUrl) throws IOException 
    {
		htmlBuilder.append("<tr><td align=\"center\">"+ applicationName +"</td><td align=\"center\">"+  environment +"</td><td align=\"center\">"+ date+"</td><td align=\"center\">"+ passPercentage+"</td><td align=\"center\">"+ jenkinsUrl+"</td></tr>");     
             		
	}
	
	public void writeFullSummary(String fileContent, String fileName)  throws IOException 
	{
		
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
	        
	        //writer.write("<b> Test Results Summary </b>");
	        writer.write(fileContent);
	        writer.close();
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
}
