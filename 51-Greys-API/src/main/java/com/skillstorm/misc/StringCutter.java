package com.skillstorm.misc;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;

public class StringCutter {
	
//	private static StringCutter c;
//	
//	private StringCutter() {}
//	
//	public static StringCutter getInstance()
//	{
//		if(c==null)
//			c = new StringCutter();
//		return c;
//	}
	
	@Value("${app-id}")
	private String appId;
	
	//manually construct a user post json
	public String userPostRequest(String firstName, String lastName, String email)
	{	
		long time = System.currentTimeMillis() / 1000L;
		String result = "{\"userName\":\"E"
				+ time
				+ "\",\"name\":{\"givenName\":\"" 
				+ firstName 
				+ "\",\"familyName\":\""
				+ lastName
				+ "\"},\"emails\":[{\"value\":\""
				+ email
				+ "\"}]}";
		return result;
	}
	
	public String userPutRequest(String userName, String firstName, String lastName, String email)
	{
		String result = "{\"userName\":\""
				+ userName
				+ "\",\"name\":{\"givenName\":\"" 
				+ firstName 
				+ "\",\"familyName\":\""
				+ lastName
				+ "\"},\"emails\":[{\"value\":\""
				+ email
				+ "\"}]}";
		return result;
	}
	
	//find the "id": section of the user json
	public String findUserId(String json)
	{
		//the magic number 6 is how many non-whitespace characters away the open quote is
		int index = 6 + json.indexOf("\"id\"");
		return json.substring(index, index + 32); //32-length string for id
	}
	
	//find the userName section of the user json
	public String findUserName(String json)
	{
		//12 non-whitespaces away from the start
		int index = 12 + json.indexOf("\"userName\":");
		String newStr = json.substring(index);
		return newStr.substring(0, newStr.indexOf("\","));
		
	}
	
	
	
	//Makes the account json object to create a salesforce account
	public String accountPostRequest(String spId, String email, String lastName)
	{
		long time = System.currentTimeMillis()/1000L;
		String sfEmail = lastName + ".a51grey." + time + "@chatter.salesforce.com";
		return "{"
		+    "\"identity\":{"
		+        "\"value\":\"" + spId + "\""
		+    "},"
		+    "\"application\":{"
		+        "\"value\":\"0a09000491131850819113cad8240129\""	//for some reason the variable doesn't register here
		+    "},"
		+    "\"active\":true,"
		+    "\"urn:ietf:params:scim:schemas:sailpoint:1.0:Application:Schema:Salesforce:account\": {"
		+        "\"FirstName\": \"a51grey\","
		+        "\"LastName\": \"a51grey\","
		+        "\"CommunityNickname\":\"AF" + time + "\","
		+        "\"ProfileName\":\"Chatter Free User\","
		+        "\"Username\": \"" + sfEmail + "\","
		+        "\"Email\": \"" + sfEmail + "\","
		+        "\"Alias\": \"a51greys\","
		+        "\"TimeZoneSidKey\": \"America/Los_Angeles\","
		+        "\"LocaleSidKey\": \"en_US\","
		+        "\"EmailEncodingKey\": \"UTF-8\","
		+        "\"LanguageLocaleKey\": \"en_US\""
		+    "}"
		+	"}";

	}
	
	//find the account id in the user json response
	public String findAccountId(String json)
	{
		//12 non-whitespaces away from the start
		int index = 12 + json.indexOf("\"accounts\"");
		String newStr = json.substring(index);
		//9 non-whitespaces away from the start
		index = 9 + newStr.indexOf("\"value\"");
		return newStr.substring(index, index + 32); //accounts also are 32-length id
	}
	
	//find the account type
	public String findAccountType(String json)
	{
		int index = 15 + json.indexOf("\"ProfileName\"");
		return json.substring(index, index + 12); //this gets us "Chatter Free" and also gets enough info for other acct types (if implemented)
	}
		
}
	
	
	

