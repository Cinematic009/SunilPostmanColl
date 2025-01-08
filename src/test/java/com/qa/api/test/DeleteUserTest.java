package com.qa.api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.POJO.User;
import com.qa.api.basetest.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest {
	
	@Test
	public void createUserWithBuillder()
	{
		String emailID = "sunil" + System.currentTimeMillis() + "@gmail.com";
		System.out.println(emailID);
		User user = User.builder()
				.name("bezos")
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
	   user.setName("Jeff");
	   user.setEmail(StringUtility.getRandomEmailId());
	   
	   
	   //Update the details  - Put 
	   Response responsePatch = restClient.put("/public/v2/users/" +userId, user, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	   Assert.assertEquals(responsePatch.getStatusCode(), 200);
	   Assert.assertEquals(responsePatch.jsonPath().getString("id"), userId);
	   Assert.assertEquals(responsePatch.jsonPath().getString("name"), user.getName());
	   Assert.assertEquals(responsePatch.jsonPath().getString("email"), user.getEmail());
	   
	   //Delete the User - Delete
	   
	   Response responseDelete = restClient.delete("/public/v2/users/" +userId, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	   Assert.assertEquals(responseDelete.getStatusCode(), 204);

	   //Recheck the GET Request - GET
	   
	   Response responseGetAfterDelete = restClient.get("/public/v2/users/" +userId, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	   Assert.assertEquals(responseGetAfterDelete.getStatusCode(), 404);
	   Assert.assertEquals(responseGetAfterDelete.jsonPath().getString("message"), "Resource not found");
	   
	   
	}   

}
