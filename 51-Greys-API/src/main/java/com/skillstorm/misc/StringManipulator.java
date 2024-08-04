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
	public String userRequest(String firstName, String lastName, String email)
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
	
	//find the "id": section of the json
	public String findUserId(String json)
	{
		int index = 6 + json.indexOf("\"id\"");
		return json.substring(index, index+32);
	}
}
