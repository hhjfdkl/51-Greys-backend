package com.skillstorm.misc;

import java.util.Random;

public class StringManipulator {
	
	private static StringManipulator c;
	
	private StringManipulator() {}
	
	public static StringManipulator getInstance()
	{
		if(c==null)
			c = new StringManipulator();
		return c;
	}
	
//manually construct a user post json
	public String userPostRequest(String firstName, String lastName, String email)
	{		
		String result = "{\"userName\":\"E";
		for(int i = 0; i < 14; i++)
			result += new Random().nextInt(10);
		result += "\",\"name\":{\"givenName\":\"" 
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
