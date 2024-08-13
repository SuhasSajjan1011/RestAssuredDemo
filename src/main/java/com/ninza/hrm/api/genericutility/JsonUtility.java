package com.ninza.hrm.api.genericutility;

import static io.restassured.RestAssured.given;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.response.Response;
/**
 * 
 * @author Deepak
 *
 */
public class JsonUtility {
	FileUtility fLib = new FileUtility();
	/**
	 * read data from excel based on json key
	 * @param key
	 * @return
	 * @throws Throwable
	 * @throws ParseException
	 */
	public String getDataFrpomJsonFile(String key) throws Throwable, ParseException {
		FileReader fileR = new FileReader("./configAppData/appCommonData.json");
		JSONParser parser = new JSONParser();
	    Object obj = parser.parse(fileR);		
	   JSONObject map = (JSONObject)obj;
	   String data = (String) map.get(key);
	   return data;
	}
	public String getAccessToken() throws Throwable
	{
		//API-1 get authentication API-1
		Response resp = given()
			.formParam("client_id", fLib.getDataFromPropertiesFile("ClientID"))
			.formParam("client_secret", fLib.getDataFromPropertiesFile("ClientSecret"))
			.formParam("grant_type", "client_credentials")
		.when()
			.post("http://49.249.28.218:8180/auth/realms/ninza/protocol/openid-connect/token");
		resp.then()
			.log().all();
		
		//capture the token from the response
		String token = resp.jsonPath().get("access_token");
		return token;
	}
		

}
