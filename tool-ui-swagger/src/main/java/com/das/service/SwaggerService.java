package com.das.service;

import java.util.List;

import com.das.model.SwaggerResource;

public interface SwaggerService {

	void save(SwaggerResource serverModel) throws Exception;
	
	void saveAll(List<SwaggerResource> resources) throws Exception;
	
}
