package com.store.testcases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.store.common.util.JsonMapper;
import com.store.common.util.Utilities;
import com.store.constants.APIStatusCodes;
import com.store.constants.Resources;
import com.store.customException.CustomExceptions;
import com.store.pojo.request.PostListsDTO;
import com.store.pojo.response.GetListsDTO;
import com.store.reader.Config;
import com.store.reporterLogger.ReportLogger;
import com.store.requestbody.APIRequestBody;
import com.store.restClient.RestClientHeader;
import com.store.restClient.RestClientWrapper;
import com.store.restClient.RestServerResponse;
import com.store.testcasebase.TestBase;

import io.restassured.RestAssured;


public class PetStoreTest extends TestBase {

	private static List<RestClientHeader> headers = new ArrayList<>();
	private static String baseUrl;
	private static RestClientWrapper restClient;
	private static GetListsDTO getListsDTO;
	static Map<String, String> dataMap = new HashMap<String, String>();
	static RestServerResponse serverResponse = null;
	static String requestPayload = null;
	private static String uniqueNumber;
	private final String newStatus = "sold";
	private static PostListsDTO ObjPostLists;

	@BeforeClass
	public void setupTest() throws Exception {
		headers.add(new RestClientHeader("Content-Type", "application/json"));
		baseUrl = Config.BASEURL;
		
	}

	@BeforeMethod
	public void setUpRequest() {

		httpRequest = RestAssured.given();
		restClient = new RestClientWrapper(baseUrl, httpRequest);
	}
    
	@Test(priority = 2, dataProvider = "Excel Data Provider", dataProviderClass = APIRequestBody.class,
			description = "Add new pet to the store",enabled=true)
	public static void addNewPet(List<String> columnName, List<String> columnValue) throws Exception  {
        try {
		
		dataMap = Utilities.excelDataToMap(columnName, columnValue);
		
		serverResponse  =addNewPetTestDriver(columnName, columnValue);
		ReportLogger.info("Response body is "+serverResponse.getBody());
		
	
		Assert.assertEquals(serverResponse.getCode(),APIStatusCodes.STATUS_200_OK,"Status code verification for New Pet");
		Assert.assertEquals(getListsDTO.getId(),dataMap.get("id"),"Validating for Id field value");
		Assert.assertEquals(getListsDTO.getStatus(),dataMap.get("status"),"Validating for Status field value");
		uniqueNumber =getListsDTO.getId();
		ReportLogger.info("Unique Id is "+uniqueNumber);
        }
		catch (final Exception e) {
			e.printStackTrace();
		}	
		
		catch (final Throwable e) {

			Assert.fail(Utilities.customAssertMessage(e.getMessage(), 
					requestPayload, serverResponse.getBody()));
			e.printStackTrace();

		}
		
	} 

	@Test(priority = 1, description = "Lists api-> Get All Pet lists",enabled=true)
	public static void getAllPetsList() throws Exception {
		try {  
		RestServerResponse serverResponse = restClient.getAll(Resources.AVAILABLE_PETLIST);
		ReportLogger.info("Response body is "+serverResponse.getBody());
		@SuppressWarnings("unchecked")
		List<GetListsDTO> getListsDTO = (List<GetListsDTO>) JsonMapper.fromJsonArray(serverResponse.getBody(),
				GetListsDTO.class);
		int responseJsonSize=getListsDTO.size();
		if (responseJsonSize == 0) {
			ReportLogger.info("Size of the JSON data is " + responseJsonSize);
			throw new CustomExceptions("Data not present");
		}
		ReportLogger.info("Total records are " + getListsDTO.size());
		Assert.assertEquals(serverResponse.getCode(),APIStatusCodes.STATUS_200_OK,"Status code verification for Get All Pet List");
	}
		
		
		catch (final Exception e) {
			e.printStackTrace();
		}	
		
		catch (final Throwable e) {

			Assert.fail(Utilities.customAssertMessage(e.getMessage(), 
					requestPayload, serverResponse.getBody()));
			e.printStackTrace();

		}
		
		
	}
	
	
	@Test(priority = 3, description = "Update Pet Status by updating the value as Sold", dependsOnMethods = { "addNewPet" })
	public  void updatePetStatus() throws Exception {
		try {
		
		dataMap.put("status", newStatus);
		
		RestServerResponse serverResponse = restClient.put(Resources.CREATE_PET, headers, dataMap);
		ReportLogger.info("Response for patch test"+serverResponse.getBody());
		Assert.assertEquals(serverResponse.getCode(),APIStatusCodes.STATUS_200_OK,"Status code verification for Put Request");
		
		RestServerResponse getServerResponse = restClient.get(Resources.CREATE_PET, headers, uniqueNumber);
		getListsDTO = JsonMapper.fromJson(getServerResponse.getBody(), GetListsDTO.class);
		Assert.assertEquals( getServerResponse.getCode(), APIStatusCodes.STATUS_200_OK,"Status code verification for Get Request");
		Assert.assertEquals( getListsDTO.getId(),dataMap.get("id"),"Validating for Id field value");
		Assert.assertEquals( getListsDTO.getStatus(),newStatus,"Validating for Status field value");
		
		}
		catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	} 
	
	
	@Test(priority = 4, description = "Delete sold Pet from the store", dependsOnMethods = { "addNewPet" })
	public void deletePetFromStore() {
		try {
		RestServerResponse serverResponse = restClient.delete(Resources.CREATE_PET, headers, uniqueNumber);
		ReportLogger.info("Response for patch test"+serverResponse.getBody());
		Assert.assertEquals( serverResponse.getCode(), APIStatusCodes.STATUS_200_OK,"Status code verification");
		getListsDTO = JsonMapper.fromJson(serverResponse.getBody(), GetListsDTO.class);
		Assert.assertEquals( getListsDTO.getMessage(),dataMap.get("id"),"Validating for Response Message field value");
		Assert.assertEquals( getListsDTO.getCode(),"200","Validating for Response Code field value");
		
		
		RestServerResponse getServerResponse = restClient.get(Resources.CREATE_PET, headers, uniqueNumber);
		ReportLogger.info("Response body is "+getServerResponse.getBody());
		getListsDTO = JsonMapper.fromJson(getServerResponse.getBody(), GetListsDTO.class);
		Assert.assertEquals( getServerResponse.getCode(), APIStatusCodes.STATUS_404_NOT_FOUND,"Status code verification");
		Assert.assertEquals( getListsDTO.getMessage(),dataMap.get("Message"),"Validating for Response Message field value");
	

		}
		catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	} 
	
	
	public static RestServerResponse addNewPetTestDriver(List<String> columnName, List<String> columnValue) throws Throwable,Exception {

		// To read request parameters and values from the csv and form a java request
     	dataMap = Utilities.excelDataToMap(columnName, columnValue);
        ObjPostLists = PostListsDTO.getloadedObject(dataMap);
     	 
 		requestPayload = JsonMapper.toJson(ObjPostLists);
 		serverResponse = restClient.post(Resources.CREATE_PET, requestPayload);
 		getListsDTO = JsonMapper.fromJson(serverResponse.getBody(), GetListsDTO.class);

		return serverResponse;



	} 


}
