package com.qa.api.test;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.POJO.User;
import com.qa.api.basetest.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class CreateUserTest extends BaseTest {
	
	@Test
	public void CreateUserTestMethod()
	{
		User user = new User("Sunil", StringUtility.getRandomEmailId(), "male", "active");
		
		Response  response = restClient.post("/public/v2/users", user, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
	}
	
	
	@Test
	public void createUserWithBuillder()
	{
		User user = User.builder()
				.name("Anil")
				.email(StringUtility.getRandomEmailId())
				.gender("male")
				.status("active")
				.build();
		
		Response  response = restClient.post("/public/v2/users", user, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
	}
	
//	@Test
//	public void CreateUserWithJsonFile()
//	{
//		File jsonFile = new File (".\\src\\test\\resources\\jsons\\user.json");
//		Response  response = restClient.post("/public/v2/users", jsonFile, null, AuthType.BEARER_TOKEN, ContentType.JSON);
//		Assert.assertEquals(response.getStatusCode(), 201);
		
//	}

}
