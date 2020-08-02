package com.das.config;

import java.util.List;

import com.das.model.ServerModel;
import com.drishlent.dlite.EmbeddedController;

public interface ConfigController extends EmbeddedController {

	void insert(ServerModel serverModel) throws Exception;
	
	List<ServerModel> loadAll() throws Exception;
	
	void startServer(ServerModel serverModel);
	
	void stopServer(ServerModel serverModel);
}
