package com.das.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.das.domain.model.ServerDetails;
import com.das.model.ServerModel;
import com.das.repository.ServerDetailsRepository;
import com.das.service.ServerDetailsService;
import com.drishlent.dlite.annotation.Inject;
import com.drishlent.dlite.annotation.Service;
import com.drishlent.dlite.register.MockServerContainer;
import com.drishlent.dlite.server.MockServer;
import com.drishlent.dlite.server.MockServerStatus;
import com.drishlent.dlite.service.InitService;

@Service
public class ServerDetailsServiceImpl implements ServerDetailsService, InitService {

	final static Logger mLogger = Logger.getLogger(ServerDetailsServiceImpl.class);
	
	
	
	@Inject
	private ServerDetailsRepository serverDetailsRepository;
	
	@Override
	public void save(ServerModel serverModel) throws Exception {
		final ServerDetails serverDetails = new ServerDetails();
		
		serverDetails.setName(serverModel.getName());
		serverDetails.setPort(Integer.valueOf(serverModel.getPort()));
		serverDetails.setAutorun(serverModel.isAutorun());
		
		ServerDetails db = serverDetailsRepository.insert(serverDetails); 
		serverModel.setId(db.getId());
		serverModel.setStatus(MockServerStatus.UNDEFINED.name());
	}

	@Override
	public List<ServerModel> findAll() throws Exception {
		List<ServerDetails>  serverDetails = serverDetailsRepository.findAll(); 
		
		List<ServerModel> serverModels = new ArrayList<ServerModel>();
		
		ServerModel serverModel;
		for (ServerDetails serverDetail : serverDetails) {
			serverModel = new ServerModel();
			serverModel.setId(serverDetail.getId());
			serverModel.setName(serverDetail.getName());
			serverModel.setPort(serverDetail.getPort());
			serverModel.setAutorun(serverDetail.getAutorun());
			serverModel.setStatus(getStatus(serverDetail.getPort()));
			
			serverModels.add(serverModel);
		}
		
		return serverModels;
	}

	@Override
	public void startServer(ServerModel serverModel) {
		List<MockServer> allServers = MockServerContainer.getInstance().get("mockservers");
		boolean isRunning = false;
		if (allServers != null) {
			for (MockServer mockServer : allServers) {
				if (serverModel.getPort().equals(mockServer.getPort())) {
					serverModel.setStatus(mockServer.status().name());
					if (MockServerStatus.RUNNING.equals(mockServer.status())) {
						isRunning = true;
					}
				}
			}
		}
		
		if (!isRunning) {
			MockServer server = new MockServer(serverModel.getPort(), "/", serverModel.getId());
			server.start();
			serverModel.setStatus(server.status().name());
		}
	}

	@Override
	public void stopServer(ServerModel serverModel) {
		List<MockServer> allServers = MockServerContainer.getInstance().get("mockservers");
		boolean isRunning = false;
		MockServer server = null;
		for (MockServer mockServer : allServers) {
			if (serverModel.getPort().equals(mockServer.getPort())) {
				server = mockServer;
				if (MockServerStatus.RUNNING.equals(mockServer.status())) {
					isRunning = true;
				}
			}
		}
		
		if (isRunning) {
			server.stop();
			serverModel.setStatus(server.status().name());
		}
	}

	private String getStatus(int port) {
		List<MockServer> allServers = MockServerContainer.getInstance().get("mockservers");
		if (allServers != null) {
			for (MockServer mockServer : allServers) {
				if (port == mockServer.getPort()) {
					return mockServer.status().name();
				}
			}
		}
		
		return MockServerStatus.UNDEFINED.name();
	}
	
	
	private void doStartServer() throws Exception {
		mLogger.info("Server start : ------------ initated");
		List<ServerModel> serverModels = findAll();
		for (ServerModel serverModel : serverModels) {
			if (serverModel.isAutorun()) {
				startServer(serverModel);
			}
		}
		
		mLogger.info("Server start : ------------ done");
	}

	@Override
	public void init() throws Exception {
		doStartServer();
	}
	
}
