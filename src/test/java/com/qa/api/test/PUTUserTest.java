package com.qa.api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.POJO.User;
import com.qa.api.basetest.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PUTUserTest extends BaseTest {
	
	@Test
	public void createUserWithBuillder()
	{
		String emailID = "sunil" + System.currentTimeMillis() + "@gmail.com";
		System.out.println(emailID);
		User user = User.builder()
				.name("Jiya")
				.email(StringUtility.getRandomEmailId())
				.gender("male")
				.status("active")
				.build();
		
		Response  response = restClient.post("/public/v2/users", user, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
		String userId = response.jsonPath().getString("id");
		System.out.println("User Id is here => " +userId);
		
		//GET User details
	   Response responseGet = restClient.get("/public/v2/users/" +userId, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	   Assert.assertEquals(responseGet.getStatusCode(), 200);
	   Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
	   Assert.assertEquals(responseGet.jsonPath().getString("name"), user.getName());
	   
	   
	   //Update the details before hitting put request.
	   user.setName("Piya");
	   user.setStatus("inactive");
	   
	   
	   //Update the details  - Put 
	   Response responsePut = restClient.put("/public/v2/users/" +userId, user, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	   Assert.assertEquals(responsePut.getStatusCode(), 200);
	   Assert.assertEquals(responsePut.jsonPath().getString("id"), userId);
	   Assert.assertEquals(responsePut.jsonPath().getString("name"), user.getName());
	   Assert.assertEquals(responsePut.jsonPath().getString("status"), user.getStatus());
	   
	   
	   
	}
	
//	@Test
//	public void getUsersTest()
//	{
//		Map<String, String> params = new HashMap<>();
//		params.put("status", "active");
//		params.put("gender", "male");
//		
//		Response response =restClient.get("/public/v2/users", params, AuthType.BEARER_TOKEN, ContentType.JSON);
//		response.prettyPrint();
//		
//		Assert.assertEquals(response.getStatusCode(), 200);
//		
//		
//		
//	}


}
