package com.das.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.das.domain.model.MockServiceMessage;
import com.das.domain.model.MockServiceResource;
import com.das.domain.model.ServerDetails;
import com.das.model.Resource;
import com.das.model.ServerInfo;
import com.das.repository.MockServiceResourceRepository;
import com.das.repository.ServerDetailsRepository;
import com.das.service.ResourceService;
import com.das.utility.PublishUtility;
import com.drishlent.dlite.annotation.Inject;
import com.drishlent.dlite.annotation.Service;
import com.drishlent.dlite.service.InitService;

@Service
public class ResourceServiceImpl implements ResourceService, InitService {

	final static Logger mLogger = Logger.getLogger(ResourceServiceImpl.class);
	
	
	@Inject
	private MockServiceResourceRepository mockServiceResourceRepository;
	
	@Inject
	private ServerDetailsRepository serverDetailsRepository;
	
	@Inject
	private EntityManager entityManager;
	
	@Override
	public void save(Resource resource) throws Exception {
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
	}

	@Override
	public List<Resource> findAll() throws Exception {
		List<MockServiceResource>  mockServiceResources = mockServiceResourceRepository.findAll(); 
		List<ServerInfo> servers = null;
		List<Resource> resources = new ArrayList<Resource>();
		if (!mockServiceResources.isEmpty()) {
			servers = findAllServer();
		}
		
		Resource resource;
		for (MockServiceResource mockServiceResource : mockServiceResources) {
			resource = new Resource();
			resource.setResourceId(mockServiceResource.getId());
			resource.setBasePath(mockServiceResource.getBasePath());
			resource.setResourcePath(mockServiceResource.getResourcePath());
			resource.setHttpMethod(mockServiceResource.getHttpMethod());
			
			MockServiceMessage message = mockServiceResource.getMessage();
			resource.setResponseCode(message.getResponseCode());
			resource.setRequestMessage(message.getRequestMsg() != null ? new String(message.getRequestMsg()) : null);
			resource.setResponseMessage(message.getResponseMsg() != null ? new String(message.getResponseMsg()) : null);
			
			resource.setServerId(mockServiceResource.getServerId());
			resource.setServerName(getServerName(servers, mockServiceResource.getServerId()));
			resources.add(resource);
		}
		
		return resources;
	}

	@Override
	public List<ServerInfo> findAllServer() throws Exception {
		List<ServerInfo> servers = new ArrayList<ServerInfo>();
		ServerInfo serverInfo;
		for(ServerDetails serverDetail : serverDetailsRepository.findAll()) {
			serverInfo = new ServerInfo(serverDetail.getId(), serverDetail.getName());
			servers.add(serverInfo);
		}
		
		serverInfo = new ServerInfo(12L, "Dummy");
		//servers.add(serverInfo);
		
		return servers;
	}

	
	private String getServerName(List<ServerInfo> servers, Long serverId) {
		if (servers == null || servers.isEmpty() || serverId == null) {
			return "";	
		}
		
		for (ServerInfo serverInfo : servers) {
			if (serverId.equals(serverInfo.getServerId())) {
				return serverInfo.getServerName();
			}
		}
		
		return "";
	}

	@Override
	public void publish(List<Resource> resources, String serverName) throws Exception {
		Long serverId = findServerId(serverName);
		updateServerId(resources, serverId);
		PublishUtility.publishToServer(resources);
	}

	@Override
	public void unpublish(List<Resource> resources) throws Exception {
		updateServerId(resources, null);
		PublishUtility.unpublishToServer(resources);
	}
	
	private void updateServerId(List<Resource> resources, Long serverId) throws Exception {
		EntityTransaction tx = null;
		try {
			tx = entityManager.getTransaction();
			tx.begin();
			for (Resource resource : resources) {
				Query query = entityManager.createNamedQuery("MockServiceResource.UpdateServerId");
				query.setParameter("serverId", serverId); 
				query.setParameter("id", resource.getResourceId()); 
				query.executeUpdate();
				
				if (serverId != null) {
					resource.setServerId(serverId);
				}
			}
			entityManager.flush();
			tx.commit();
			
		} catch (RuntimeException pException) {
		    tx.rollback();
		    throw pException;
		}
		
	}
	
	private Long findServerId(String serverName) throws Exception {
		List<ServerInfo> servers = findAllServer();
		Long serverId = null;
		for (ServerInfo serverInfo : servers) {
			if (serverName.equals(serverInfo.getServerName())) {
				serverId = serverInfo.getServerId();
			}
		}
		return serverId;
	}
	
	
	private void doPublishToServer() throws Exception {
		mLogger.info("Publising to Servere : ------------ initated");
		PublishUtility.publishToServer(findAll());
		mLogger.info("Publising to Servere : ------------ done");
	}

	@Override
	public void init() throws Exception {
		doPublishToServer();
	}
	
	
}
