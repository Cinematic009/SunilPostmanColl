package com.qa.api.basetest;

import org.testng.annotations.BeforeMethod;

import com.qa.api.client.RestClient;

public class BaseTest {
	
	protected RestClient restClient;
	
	@BeforeMethod
	public void Setup()
	{
		restClient=new RestClient();
	}

}
