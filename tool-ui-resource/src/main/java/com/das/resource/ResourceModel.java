package com.das.resource;

import java.util.List;

import com.das.model.Resource;
import com.das.model.ServerInfo;
import com.drishlent.dlite.EmbeddedModel;
import com.drishlent.dlite.table.RDTableModel;

public interface ResourceModel extends EmbeddedModel {

	void createResource(String basePath, String resourcePath, String method, 
			Integer responseCode, String requestMessage, String responseMessage);
	
	RDTableModel getResourceTableModel();
	
	List<ServerInfo> getServerList();
	
	void update(List<Resource> resources, String serverName);
	
}
