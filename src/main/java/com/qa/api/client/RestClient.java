package com.qa.api.client;

import java.io.File;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.api.configManager.ConfigManager;
import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.FrameworkException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;

public class RestClient {
	
	private String baseUrl = ConfigManager.get("baseUrl");
	
	//Define Response specifications
	
	private ResponseSpecification response200= expect().statusCode(200);
	private ResponseSpecification response200or404= expect().statusCode(anyOf(equalTo(200), equalTo(404)));
	private ResponseSpecification response201= expect().statusCode(201);
	private ResponseSpecification response400= expect().statusCode(400);
	private ResponseSpecification response500= expect().statusCode(500);
	private ResponseSpecification response404= expect().statusCode(404);
	private ResponseSpecification response204= expect().statusCode(204);
	
	
	private RequestSpecification setupRequest(AuthType authtype, ContentType contentType)
	{
	 RequestSpecification request=RestAssured.given().log().all()
		            .baseUri(baseUrl)
		            .contentType(contentType)
	                 .log().all();
		
		switch (authtype) {
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer " +ConfigManager.get("bearerToken"));
			break;
			
		case OAuth2:
			request.header("Authorization", "Bearer " +generateToken());
			break;
			
		case API_KEY:
			request.header("Authorization", ConfigManager.get("APIKey"));
			break;
			
		case No_AUTH:
			request.header("Authorization", null);
			break;

		default:
			throw new FrameworkException("Please pass correct Authorisation");
		}
		return request;
		            
	}
	
	private String generateToken()
	{
		return RestAssured.given()
				          .formParam("client_Id", ConfigManager.get("ClientId"))
				          .formParam("client_secret", ConfigManager.get("ClientSecret"))
				          .formParam("grant_type", ConfigManager.get("GrantType"))
				          .post(ConfigManager.get("TokenUrl"))
				          .then()
				          .extract()
				          .path("access_token");
				          
	}
	
	
	//****************************CRUDE Methods***************
	
	/**
	 * This method is used to call GET API
	 * @param endPoint
	 * @param querryParams
	 * @param authtype
	 * @param contentType
	 * @return   It returns the get api response
	 */
	
	public Response get(String endPoint, Map<String, String> querryParams, AuthType authtype, ContentType contentType )
	{
		
		RequestSpecification request =setupRequest(authtype, contentType);
		
		if(querryParams!=null)
		{
			request.queryParams(querryParams);
		}
		
		Response response= request.get(endPoint).then().spec(response200or404).extract().response();
		response.prettyPrint();
		return response;
	}
	
	/**
	 *  This Method is used to call POST API
	 * @param <T>
	 * @param endPoint
	 * @param body
	 * @param querryParams
	 * @param authtype
	 * @param contentType
	 * @return
	 */
	
	public <T> Response post(String endPoint, T body , Map<String, String> querryParams, AuthType authtype, ContentType contentType)
	{
         RequestSpecification request =setupRequest(authtype, contentType);
		
		if(querryParams!=null)
		{
			request.queryParams(querryParams);
		}
		else
		{
			System.out.println("No Querry parameters are there in given request");
		}
		
		 if (body != null) {
		        try {
		            ObjectMapper mapper = new ObjectMapper();
		            String jsonString = mapper.writeValueAsString(body);
		            request.body(jsonString); // Set the serialized JSON string as the body
		        } catch (Exception e) {
		            throw new RuntimeException("Failed to serialize request body", e);
		        }
		 }
		
		Response response= request.post(endPoint).then().log().all().spec(response201).extract().response();
		response.prettyPrint();
		return response;
	}
	
	public  Response post(String endPoint, File file , Map<String, String> querryParams, AuthType authtype, ContentType contentType)
	{
         RequestSpecification request =setupRequest(authtype, contentType);
		
		if(querryParams!=null)
		{
			request.queryParams(querryParams);
		}
		else
		{
			System.out.println("No Querry parameters are there in given request");
		}
		
		
		
		Response response= request.post(endPoint).then().log().all().spec(response201).extract().response();
		response.prettyPrint();
		return response;
	}
	
	public <T> Response put(String endPoint, T body , Map<String, String> querryParams, AuthType authtype, ContentType contentType)
	{
         RequestSpecification request =setupRequest(authtype, contentType);
		
		if(querryParams!=null)
		{
			request.queryParams(querryParams);
		}
		else
		{
			System.out.println("No Querry parameters are there in given request");
		}
		
		 if (body != null) {
		        try {
		            ObjectMapper mapper = new ObjectMapper();
		            String jsonString = mapper.writeValueAsString(body);
		            request.body(jsonString); // Set the serialized JSON string as the body
		        } catch (Exception e) {
		            throw new RuntimeException("Failed to serialize request body", e);
		        }
		 }
		
		Response response= request.put(endPoint).then().log().all().spec(response200).extract().response();
		response.prettyPrint();
		return response;
	}
	
	public <T> Response patch(String endPoint, T body , Map<String, String> querryParams, AuthType authtype, ContentType contentType)
	{
         RequestSpecification request =setupRequest(authtype, contentType);
		
		if(querryParams!=null)
		{
			request.queryParams(querryParams);
		}
		else
		{
			System.out.println("No Querry parameters are there in given request");
		}
		
		 if (body != null) {
		        try {
		            ObjectMapper mapper = new ObjectMapper();
		            String jsonString = mapper.writeValueAsString(body);
		            request.body(jsonString); // Set the serialized JSON string as the body
		        } catch (Exception e) {
		            throw new RuntimeException("Failed to serialize request body", e);
		        }
		 }
		
		Response response= request.patch(endPoint).then().log().all().spec(response200).extract().response();
		response.prettyPrint();
		return response;
	}
	
	public Response delete(String endPoint, Map<String, String> querryParams, AuthType authtype, ContentType contentType )
	{
		
		RequestSpecification request =setupRequest(authtype, contentType);
		
		if(querryParams!=null)
		{
			request.queryParams(querryParams);
		}
		
		Response response= request.delete(endPoint).then().spec(response204).extract().response();
		response.prettyPrint();
		return response;
	} 
	
	
	
	

}
