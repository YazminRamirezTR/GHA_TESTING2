package com.trgr.quality.maf.jsonreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.trgr.quality.maf.basetest.BaseTest;

public class JsonReader extends BaseTest {
	JSONParser parser = new JSONParser();

	public JSONObject unKnownJsonStructureReader(String filepath) throws IOException, ParseException {

		JSONObject jsonObject = null;
		try {

			Object obj = parser.parse(new FileReader(filepath));

			jsonObject = (JSONObject) obj;
		}
			
		 catch (FileNotFoundException e) {
			e.printStackTrace();

		} 
		
		return jsonObject;
	}
	
	/* 
	 * This method reads the JSon Array for product under test
	 * On Success, Returns JSONArray Data for product under test
	 * throws exception on failure
	 */
	public JSONArray readJSONDataFromFile(String jiraNumber,  ExtentTest extentLogger) throws Exception {
		JSONArray dataArray = null;
		File file=null;
		try{
			file = new File("..\\MAFAutomation_MAF-Application-Testdata\\target\\classes\\"+productUnderTest+"\\" + jiraNumber + ".json");
			//file = new File("..\\MAF-Application-Testdata\\target\\classes\\" + jiraNumber +"\\"+productUnderTest+"\\"+jiraNumber+".json");
			JsonReader fileReader = new JsonReader();
			JSONObject jsonObject = fileReader.unKnownJsonStructureReader(file.getAbsolutePath());
			dataArray = (JSONArray) jsonObject.get(jiraNumber);
		}catch(Exception e){
			dataArray = null;
		}finally{
			if(dataArray==null){
				extentLogger.log(LogStatus.FAIL, "Test Data not found for : "+ applicationNameToPrint(BaseTest.productUnderTest)+ "("+ BaseTest.productUnderTest + ")" +"<br>"+printFileContentAsLink(jiraNumber));
				throw new Exception("JSONDataReadError");
			}
			else
			{
				extentLogger.log(LogStatus.INFO, printFileContentAsLink(jiraNumber));
			}
		}
		return dataArray;
	}
	
	
	/* 
	 * This method reads the key value from json object
	 * On Success, Returns value as String
	 * throws exception on failure
	 */
	public String readKeyValueFromJsonObject(JSONObject jsonObject,String key, ExtentTest extentLogger) throws Exception {
		String data = null;
		try{
			data = jsonObject.get(key).toString();
		}catch(Exception e){
			data = null;
		}finally{
			if(data==null){
				extentLogger.log(LogStatus.FAIL, "Given key is not found: "+key +"<br>"+ jsonObject.toJSONString());
				throw new Exception("JSONDataReadError");
			}
		}
		return data;
	}
	
	/* 
	 * This method reads the key value as JSon Array from json object
	 * On Success, Returns value as JSon Array
	 * throws exception on failure
	 */
	public JSONArray readJSonArrayFromJsonObject(JSONObject jsonObject,String key, ExtentTest extentLogger) throws Exception {
		JSONArray data_array = null;
		try{
			data_array = (JSONArray) jsonObject.get(key);
		}catch(Exception e){
			data_array = null;
		}finally{
			if(data_array==null){
				extentLogger.log(LogStatus.FAIL, "Unable to Read JSon Array from key: "+key +"<br>"+ jsonObject.toJSONString());
				throw new Exception("JSONDataReadError");
			}
		}
		return data_array;
	}
	
	/* This method returns file content as a link */
	protected String printFileContentAsLink(String jiraNumber) {
		String gitLink = "https://git.sami.int.thomsonreuters.com/MAF-GCMS-Bangalore/MAF-Application-Testdata/tree/working-branch/src/main/resources/";
		gitLink+=productUnderTest+"/"+jiraNumber+".json";
		String message = null;
		try{
			message = "<a href='"+gitLink+"' target='_blank'>TestData File</a>";
		}catch(Exception e){
			
		}
		return message;
	}


}
