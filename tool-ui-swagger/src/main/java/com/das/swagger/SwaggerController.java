package com.das.swagger;

import java.util.List;

import com.das.model.SwaggerResource;
import com.drishlent.dlite.EmbeddedController;

public interface SwaggerController extends EmbeddedController {

	void insert(SwaggerResource serverModel) throws Exception;
	
	void insertAll(List<SwaggerResource> resources) throws Exception;
	
}
