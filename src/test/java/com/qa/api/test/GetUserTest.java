package com.qa.api.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.basetest.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends BaseTest {
	
	
	
	@Test
	public void getUsersTest()
	{
		Map<String, String> params = new HashMap<>();
		params.put("status", "active");
		params.put("gender", "male");
		
		Response response =restClient.get("/public/v2/users", params, AuthType.BEARER_TOKEN, ContentType.JSON);
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
		
	}

	@Test
	public void getSingleUserTest()
	{
//		Map<String, String> params = new HashMap<>();
//		params.put("status", "active");
//		params.put("gender", "male");
		
		Response response =restClient.get("/public/v2/users/7565836", null, AuthType.BEARER_TOKEN, ContentType.JSON);
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		

		
		
		
	}
	
	

}
