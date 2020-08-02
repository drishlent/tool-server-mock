package com.das.repository;

import org.junit.Assert;
import org.junit.Test;

import com.das.BaseTest;
import com.das.domain.model.MockServiceMessage;
import com.das.domain.model.MockServiceResource;
import com.drishlent.dlite.annotation.Inject;


public class MockServiceResourceRepositoryTest extends BaseTest {

	@Inject
	private MockServiceResourceRepository mockServiceResourceRepository;
	
	@Test
	public void testMockRepository() throws Exception { 
		MockServiceResource resource = new MockServiceResource();
		resource.setBasePath("/v1");
		resource.setResourcePath("/login");
		resource.setHttpMethod("GET");
		resource.setServerId(10L);
		
		MockServiceMessage message = new MockServiceMessage();
		message.setResponseCode(200);
		message.setResponseMsg("{\"issued_at\" : \"1465811574182\",\"scope\" : \"\"}".toCharArray());
		message.setRequestMsg(null);
		resource.setMessage(message);
		message.setResource(resource);
		
		MockServiceResource resourceDB = mockServiceResourceRepository.insert(resource); 
		Assert.assertNotNull(resourceDB.getId());
		Assert.assertNotNull(resourceDB.getMessage().getId());
		Assert.assertNotNull(message.getResponseCode());
		Assert.assertNotNull(message.getResource());
		Assert.assertNull(message.getRequestMsg());
		
		System.out.println(resourceDB.getMessage().getResponseMsg());
		
	}
	
}
