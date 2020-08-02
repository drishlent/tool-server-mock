package com.das.resource.impl;

import java.util.ArrayList;
import java.util.List;

import com.das.model.Resource;
import com.das.model.ServerInfo;
import com.das.resource.ResourceController;
import com.das.resource.ResourceModel;
import com.drishlent.dlite.AbstractEmbeddedModel;
import com.drishlent.dlite.EmbeddedController;
import com.drishlent.dlite.RDSwingWorker;
import com.drishlent.dlite.table.RDTableColumn;
import com.drishlent.dlite.table.RDTableModel;



public class ResourceModelImpl extends AbstractEmbeddedModel implements ResourceModel {
	
	private RDTableModel mResourceTableModel;
	private ResourceController resourceController;
	private List<ServerInfo> servers;
	
	@Override
	public void initModel() {
		mResourceTableModel = new RDTableModel();
		loadServerInfoData();
	}

	@Override
	public void startModel() {
		getController().getStatusBarEvaluator().done();
		loadServerConfData();
	}
	
	@Override
	public void setController(EmbeddedController controller) {
		super.setController(controller); 
		resourceController = (ResourceController) controller;
	}
	
	@Override
	public List<ServerInfo> getServerList() {
		return servers == null ? new ArrayList<ServerInfo>() : servers;
	}
	

	@Override
	public void createResource(String basePath, String resourcePath, String method, 
			Integer responseCode, String requestMessage, String responseMessage) {
		final Resource resource = new Resource();
		resource.setBasePath(basePath);
		resource.setResourcePath(resourcePath);
		resource.setResponseCode(responseCode);
		resource.setHttpMethod(method);
		resource.setRequestMessage(requestMessage);
		resource.setResponseMessage(responseMessage);
		
		RDSwingWorker<Resource> lRDSwingWorker = new RDSwingWorker<Resource>() {
			
			@Override
			protected Resource doInBackground() throws Exception {
				getController().getStatusBarEvaluator().init();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("getting inserted");
				resourceController.insert(resource);
				return resource;
			}
			
			@Override
			protected void finished() throws Exception {
				Resource dbresource = get(); 
				getController().getStatusBarEvaluator().done();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("successfully inserted");
				mResourceTableModel.insertTableLine(dbresource);	
			}
		};
			
		lRDSwingWorker.execute();	
	}
	
	@Override
	public RDTableModel getResourceTableModel() {
		mResourceTableModel.addColumn(new RDTableColumn<Resource, Integer>("Sl.", 2, "Serial Number") {
		      @Override
		      public Integer getValue(Resource resource) {
		        return 1;
		      }

		    });

		mResourceTableModel.addColumn(new RDTableColumn<Resource, String>("Server", 30, "Server Auto Run") {
		      @Override
		      public String getValue(Resource resource) {
		        return resource.getServerName();
		      }

		    });
		
		mResourceTableModel.addColumn(new RDTableColumn<Resource, String>("Path", 30, "Server Name") {
		      @Override
		      public String getValue(Resource resource) {
		        return resource.getBasePath()+resource.getResourcePath();
		      }

		    });
		
		mResourceTableModel.addColumn(new RDTableColumn<Resource, String>("Method", 30, "Server Auto Run") {
		      @Override
		      public String getValue(Resource resource) {
		        return resource.getHttpMethod();
		      }

		    });
		
		mResourceTableModel.addColumn(new RDTableColumn<Resource, Integer>("ResponseCode", 30, "Server Auto Run") {
		      @Override
		      public Integer getValue(Resource resource) {
		        return resource.getResponseCode();
		      }

		    });
		
		mResourceTableModel.addColumn(new RDTableColumn<Resource, String>("Status", 30, "Server Auto Run") {
		      @Override
		      public String getValue(Resource resource) {
		        return resource.getStatus();
		      }

		    });
		
		
		
		return mResourceTableModel;
	}

	private void loadServerConfData() {
		RDSwingWorker<List<Resource>> lRDSwingWorker = new RDSwingWorker<List<Resource>>() {
			
			@Override
			protected List<Resource> doInBackground() throws Exception {
				getController().getStatusBarEvaluator().init();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("getting Resource fetched");
				List<Resource> all = resourceController.loadAll(); 
				return all;
			}
			
			@Override
			protected void finished() throws Exception {
				List<Resource> DBresource = get(); 
				getController().getStatusBarEvaluator().done();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("successfully loaded");
				mResourceTableModel.setData(DBresource);
			}
		};
			
		lRDSwingWorker.execute();
	}
	
	private void loadServerInfoData() {
		RDSwingWorker<List<ServerInfo>> lRDSwingWorker = new RDSwingWorker<List<ServerInfo>>() {
			
			@Override
			protected List<ServerInfo> doInBackground() throws Exception {
				getController().getStatusBarEvaluator().init();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("getting ServerInfo fetched");
				List<ServerInfo> all = resourceController.findAllServer(); 
				return all;
			}
			
			@Override
			protected void finished() throws Exception {
				List<ServerInfo> DBresource = get(); 
				getController().getStatusBarEvaluator().done();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("successfully loaded");
				servers  = DBresource;
			}
		};
			
		//lRDSwingWorker.execute();
		
		try {
			servers = resourceController.findAllServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(final List<Resource> resources, final String serverName) {
		RDSwingWorker<List<Resource>> lRDSwingWorker = new RDSwingWorker<List<Resource>>() {
			
			@Override
			protected List<Resource> doInBackground() throws Exception {
				getController().getStatusBarEvaluator().init();
				String msg = serverName == null ? "getting unpublished " : "getting published ";
				getController().getStatusBarEvaluator().get1StLabelMessage().setText(msg);
				if (serverName != null) {
					resourceController.publish(resources, serverName);
				} else {
					resourceController.unpublish(resources);
				}
				return resources;
			}
			
			@Override
			protected void finished() throws Exception {
				List<Resource> dbresources = get(); 
				getController().getStatusBarEvaluator().done();
				String msg = serverName == null ? "successfully unpublished " : "successfully published ";
				getController().getStatusBarEvaluator().get1StLabelMessage().setText(msg);
				for (Resource resource : dbresources) {
					mResourceTableModel.updateTableLine(resource);	
				}
			}
		};
			
		lRDSwingWorker.execute();	
	}

	
	

}
