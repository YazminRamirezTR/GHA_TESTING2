package com.trgr.quality.maf.commonutils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Nullable;
import com.atlassian.util.concurrent.Promise;

public class JiraConnector {

	private JiraRestClient jiraRestClient = null;
	protected static Logger logger = LoggerFactory.getLogger(JiraConnector.class);
	private String jiraServerURI = "http://jira.bjz.apac.ime.reuters.com/";
	
	
	
	public JiraConnector() {

		try
		{
			AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
			setJiraRestClient(factory.createWithBasicHttpAuthentication(new URI(jiraServerURI), "arun.karthikeyan", "pswd"));
			logger.info("Jira Connection is successful");	
			
		}
		catch(Exception ex)
		{
			System.out.println("connection failure");
		}
		

	}
	
	public void closeConnection() throws IOException {
		getJiraRestClient().close();
	}
	

	public JiraRestClient getJiraRestClient() {
		return jiraRestClient;
	}
	
	public void setJiraRestClient(JiraRestClient jiraRestClient) {
			this.jiraRestClient = jiraRestClient;
		
		}


	public String createStory(String ProjectKey, long IssueType, String Title, Iterable<String> ComponentNameList,
			String Summary, long PriorityId, @Nullable Iterable<String> AffactedVersionName,
			@Nullable String AssigneeName) throws IOException {
		String createdID = "";
		try {
			IssueInputBuilder issueBuilder = new IssueInputBuilder(ProjectKey, IssueType, Title);

			issueBuilder.setComponentsNames(ComponentNameList);
			if (AffactedVersionName != null)
				issueBuilder.setAffectedVersionsNames(AffactedVersionName);
			issueBuilder.setDescription(Summary);
			issueBuilder.setPriorityId(PriorityId);
			if (AssigneeName != null)
				issueBuilder.setAssigneeName(AssigneeName);
			BasicIssue newIssue = getJiraRestClient().getIssueClient().createIssue(issueBuilder.build()).claim();
			createdID = newIssue.getKey();
			System.out.print("Create:\t" + newIssue.getId() + "\t" + newIssue.getKey());

		} catch (Exception e) {
			System.out.print("ERROR:\t" + e.getMessage());
		} finally {
			// closeConnection();
		}

		return createdID;
	}

	public int createJIRAIssue(int issueType, String assigneeName, String componentToAdd, String title,
			String projectKey) throws Exception {
		String ProjectKey = projectKey;
		long IssueType = issueType;
		String issueIdCreated = null;
		String Title = title;
		List<String> ComponentNameList = new ArrayList<>();
		String Summary = "First try to create defect from automation upon test failure";
		long PriorityId = 2;
		String AssigneeName = assigneeName;
		ComponentNameList.add(componentToAdd);

		try {
			issueIdCreated = createStory(ProjectKey, IssueType, Title, ComponentNameList, Summary, PriorityId, null,
					AssigneeName);
			return Integer.parseInt(issueIdCreated);
		} catch (Exception e) {
			e.getMessage();
		}
		return Integer.parseInt(issueIdCreated);
	}

	
	
	public Issue getIssue(String issueKey) throws Exception {
		
		Promise<Issue> issuePromise = null;
	try
		{
			issuePromise = getJiraRestClient().getIssueClient().getIssue(issueKey);
			
			return issuePromise.claim();
		}
		catch(Exception ex)
		{
			logger.info(ex.toString());
			//return ex.getCause().toString().split("\\'")[1];
			return issuePromise.get();
		}		
		
	}
	
}
