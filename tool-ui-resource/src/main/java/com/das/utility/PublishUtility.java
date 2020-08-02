package com.das.utility;

import java.util.List;

import com.das.model.Resource;
import com.drishlent.dlite.model.MockServiceMessageModel;
import com.drishlent.dlite.model.MockServiceModel;
import com.drishlent.dlite.register.MockServiceContainer;

public class PublishUtility {

	public static void publishToServer(List<Resource> resources) {
		for (Resource resource : resources) {
			if (resource.getServerName() == null || resource.getServerName().isEmpty()) {
				continue;
			}
			
			try {
				Long serverId = resource.getServerId();
				MockServiceMessageModel msg = new MockServiceMessageModel();
				msg.setRequestMsg(resource.getRequestMessage());
				msg.setResponseMsg(resource.getResponseMessage());
				msg.setResponseCode(resource.getResponseCode());
				
				MockServiceContainer.getInstance().register(new MockServiceModel(serverId, resource.getBasePath()+resource.getResourcePath(), resource.getHttpMethod()), msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
	}
	
	public static void unpublishToServer(List<Resource> resources) {
		for (Resource resource : resources) {
			try {
				Long serverId = resource.getServerId();
				
				MockServiceContainer.getInstance().unregister(new MockServiceModel(serverId, resource.getBasePath()+resource.getResourcePath(), resource.getHttpMethod()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
	}
	
	
}
