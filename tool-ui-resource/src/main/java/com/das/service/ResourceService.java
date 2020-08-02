package com.das.service;

import java.util.List;

import com.das.model.Resource;
import com.das.model.ServerInfo;

public interface ResourceService {

	void save(Resource serverModel) throws Exception;
	
	List<Resource> findAll() throws Exception;
	
	List<ServerInfo> findAllServer() throws Exception;
	
	void publish(List<Resource> resources, String serverName) throws Exception;
	
	void unpublish(List<Resource> resources) throws Exception;
	
}
