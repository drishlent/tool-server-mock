package com.das.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.das.domain.model.MockServiceMessage;
import com.das.domain.model.MockServiceResource;
import com.das.model.SwaggerResource;
import com.das.repository.MockServiceResourceRepository;
import com.das.service.SwaggerService;
import com.drishlent.dlite.annotation.Inject;
import com.drishlent.dlite.annotation.Service;

@Service
public class SwaggerServiceImpl implements SwaggerService {

	final static Logger mLogger = Logger.getLogger(SwaggerServiceImpl.class);
	
	
	@Inject
	private MockServiceResourceRepository mockServiceResourceRepository;
	
	
	@Override
	public void save(SwaggerResource resource) throws Exception {
		MockServiceResource mockServiceResource = new MockServiceResource();
		mockServiceResource.setBasePath(resource.getBasePath());
		mockServiceResource.setResourcePath(resource.getResourcePath());
		mockServiceResource.setHttpMethod(resource.getHttpMethod());
		
		MockServiceMessage message = new MockServiceMessage();
		message.setResponseCode(resource.getResponseCode());
		message.setRequestMsg(resource.getRequestMessage() != null ? resource.getRequestMessage().toCharArray() : null);
		message.setResponseMsg(resource.getResponseMessage() != null ? resource.getResponseMessage().toCharArray() : null);
		
		mockServiceResource.setMessage(message);
		message.setResource(mockServiceResource);
		
		MockServiceResource db = mockServiceResourceRepository.insert(mockServiceResource);
		resource.setResourceId(db.getId());
		mLogger.info("Saved : "+resource);
	}


	@Override
	public void saveAll(List<SwaggerResource> resources) throws Exception {
		for (SwaggerResource resource : resources) {
			save(resource);
		}
	}

	
}
