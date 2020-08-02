package com.das.swagger;

import java.util.List;

import com.das.model.SwaggerResource;
import com.drishlent.dlite.EmbeddedModel;
import com.drishlent.dlite.table.RDTableModel;

public interface SwaggerModel extends EmbeddedModel {

	void createResource(String basePath, String resourcePath, String method, 
			Integer responseCode, String requestMessage, String responseMessage);
	
	RDTableModel getResourceTableModel();
	
	void insert(List<SwaggerResource> resources);
	
	void process(String fileName);
	
	void updateTable(SwaggerResource resource);
	
}
