package com.skillstorm.misc;

import java.util.Random;

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
		int index = 6 + json.indexOf("\"id\"");
		return json.substring(index, index+32);
	}
	
	//find the userName section of the user json
	public String findUserName(String json)
	{
		int index = 12 + json.indexOf("\"userName\":");
		String newStr = json.substring(index);
		return newStr.substring(0, newStr.indexOf("\","));
		
	}
	
	
	
}
