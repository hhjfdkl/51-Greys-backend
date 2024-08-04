package com.skillstorm;

import org.junit.jupiter.api.Test;

import com.skillstorm.misc.StringManipulator;
import com.skillstorm.services.EmployeeService;

public class EmployeeServiceTests {

	@Test
	public void runSpPostRequest()
	{
		EmployeeService service = new EmployeeService();
//		service.createUserSP("nate", "koroman", "nk@hotmail.gov");
	}
	
	@Test
	public void stringTest()
	{
		System.out.println(StringManipulator.getInstance()
				.userRequest("nate", "koroman", "nk@hotmail.gov"));
	}
	
	@Test
	public void string2Test()
	{
		String str = "\"name\":{\"formatted\":\"fmae\",\"familyName\":\"Mae\",\"givenName\":\"Fannie\"},\"active\":true,\"id\":\"0a09000490f017848190f0d9fde3000e\",\"userType\":\"employee\",\"userName\":\"fmae\",";
		int index = str.lastIndexOf("\"id\"");
		System.out.println(index);
		System.out.println(str.substring(index+6, index+6+32));
		System.out.println(StringManipulator.getInstance().findUserId(str));
	}
}
