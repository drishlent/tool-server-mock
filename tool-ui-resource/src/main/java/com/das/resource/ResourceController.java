package com.das.resource;

import java.util.List;

import com.das.model.Resource;
import com.das.model.ServerInfo;
import com.drishlent.dlite.EmbeddedController;

public interface ResourceController extends EmbeddedController {

	void insert(Resource serverModel) throws Exception;
	
	List<Resource> loadAll() throws Exception;
	
	List<ServerInfo> findAllServer() throws Exception;
	
	void publish(List<Resource> resources, String serverName) throws Exception;
	
	void unpublish(List<Resource> resources) throws Exception;
	
}
