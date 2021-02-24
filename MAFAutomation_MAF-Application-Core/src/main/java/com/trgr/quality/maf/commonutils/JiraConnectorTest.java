package com.trgr.quality.maf.commonutils;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.atlassian.jira.rest.client.api.domain.Issue;

public class JiraConnectorTest 
{
	JiraConnector jiraTest = null;
	
	@Before
	public void testSetup() throws URISyntaxException
	{
		jiraTest =new JiraConnector();
	}
	
	@After
	public void CloseConnection() throws IOException
	{
		jiraTest.closeConnection();
	}
	
	@Test
	public void getOneIssue() throws Exception {
		
		String issueNum = "MAFQABANG-187";

		Issue issue = jiraTest.getIssue(issueNum);
		if(issue!=null)
		{
			boolean isExpectedIssueReceived = issue.getKey().toString().contains(issueNum);
			assertTrue(isExpectedIssueReceived, "Getting one jira issue");	
			System.out.println(issue.getSummary());
		}
		
		
	}
		
	@Test
	public void getOneIssueStatus() throws Exception {
		
		String issueNum = "MAFQABANG-1";

		Issue issue = jiraTest.getIssue(issueNum);
		if(issue!=null)
		{
			String issueStatus = issue.getStatus().getName();
			assertTrue(issueStatus.contains("Open"), "Getting one jira issue");
		}
	
	}	
	
	
	 /* Create JIRA issue 
	 */
	@Test
	public void createDefectForTestFailure() throws Exception {

		jiraTest.createJIRAIssue(1, "arun.karthikeyan", "Search", "Creating defect from automation", "MAFQABANG");
	}


}
