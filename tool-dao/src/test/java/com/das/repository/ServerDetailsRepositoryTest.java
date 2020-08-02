package com.das.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.das.BaseTest;
import com.das.domain.model.ServerDetails;
import com.drishlent.dlite.annotation.Inject;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServerDetailsRepositoryTest extends BaseTest {

	@Inject
	private ServerDetailsRepository serverDetailsRepository;
	
	
	@Test
	public void test_ServerDetails_1_Insert() throws Exception { 
		ServerDetails serverDetails = new ServerDetails();
		serverDetails.setName("Test");
		serverDetails.setPort(8085);
		
		serverDetails = serverDetailsRepository.insert(serverDetails);
		
		System.out.println(serverDetails);
		Assert.assertNotNull(serverDetails.getId());
		serverDetails.getId();
	}
	
	@Test
	public void test_ServerDetails_2_FindAll() throws Exception { 
		
		List<ServerDetails> serverDetails = serverDetailsRepository.findAll();
		
		System.out.println(serverDetails);
		Assert.assertNotNull(serverDetails);
		Assert.assertEquals(1, serverDetails.size());
		
	}
	
	@Test
	public void test_ServerDetails_3_FindBy() throws Exception { 
		Long id =1L;
		ServerDetails serverDetail = serverDetailsRepository.findById(id);
		
		System.out.println(serverDetail);
		Assert.assertNotNull(serverDetail);
		Assert.assertEquals(id, serverDetail.getId());
		
	}
	
	
	
}
