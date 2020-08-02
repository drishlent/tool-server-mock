package com.das.service;

import java.util.List;

import com.das.model.ServerModel;

public interface ServerDetailsService {

	void save(ServerModel serverModel) throws Exception;
	
	List<ServerModel> findAll() throws Exception;
	
	void startServer(ServerModel serverModel);
	
	void stopServer(ServerModel serverModel);
	
}
