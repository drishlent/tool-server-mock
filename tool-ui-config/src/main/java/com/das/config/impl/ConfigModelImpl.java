package com.das.config.impl;

import java.util.List;

import com.das.config.ConfigController;
import com.das.config.ConfigModel;
import com.das.model.ServerModel;
import com.drishlent.dlite.AbstractEmbeddedModel;
import com.drishlent.dlite.EmbeddedController;
import com.drishlent.dlite.RDSwingWorker;
import com.drishlent.dlite.table.RDTableColumn;
import com.drishlent.dlite.table.RDTableModel;



public class ConfigModelImpl extends AbstractEmbeddedModel implements ConfigModel {
	
	private RDTableModel mRDTableModelTab1;
	private ConfigController configController;
	
	@Override
	public void initModel() {
		mRDTableModelTab1 = new RDTableModel();
	}

	@Override
	public void startModel() {
		getController().getStatusBarEvaluator().done();
		loadServerConfData();
	}
	
	@Override
	public void setController(EmbeddedController controller) {
		super.setController(controller); 
		configController = (ConfigController) controller;
	}

	@Override
	public void createServerConf(final String serverName, final String port, final boolean isAutoRun) {
		final ServerModel serverDetails = new ServerModel();
		
		serverDetails.setName(serverName);
		serverDetails.setPort(Integer.valueOf(port));
		serverDetails.setAutorun(isAutoRun);
		
		RDSwingWorker<ServerModel> lRDSwingWorker = new RDSwingWorker<ServerModel>() {
			
			@Override
			protected ServerModel doInBackground() throws Exception {
				getController().getStatusBarEvaluator().init();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("getting inserted");
				configController.insert(serverDetails);
				return serverDetails;
			}
			
			@Override
			protected void finished() throws Exception {
				ServerModel dbServerModel = get(); 
				getController().getStatusBarEvaluator().done();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("successfully inserted");
				mRDTableModelTab1.insertTableLine(dbServerModel);	
			}
		};
			
		lRDSwingWorker.execute();	
	}
	
	@Override
	public RDTableModel getServerTableModel() {
		mRDTableModelTab1.addColumn(new RDTableColumn<ServerModel, Integer>("Sl.", 2, "Serial Number") {
		      @Override
		      public Integer getValue(ServerModel serverModel) {
		        return 1;
		      }

		    });

		mRDTableModelTab1.addColumn(new RDTableColumn<ServerModel, String>("Server Name", 30, "Server Name") {
		      @Override
		      public String getValue(ServerModel serverModel) {
		        return serverModel.getName();
		      }

		    });
		
		mRDTableModelTab1.addColumn(new RDTableColumn<ServerModel, Integer>("Port", 30, "Server Port") {
		      @Override
		      public Integer getValue(ServerModel serverModel) {
		        return serverModel.getPort();
		      }

		    });
		
		mRDTableModelTab1.addColumn(new RDTableColumn<ServerModel, Boolean>("AutoRun", 30, "Server Auto Run") {
		      @Override
		      public Boolean getValue(ServerModel serverModel) {
		        return serverModel.isAutorun();
		      }

		    });
		
		mRDTableModelTab1.addColumn(new RDTableColumn<ServerModel, String>("Status", 30, "Server Auto Run") {
		      @Override
		      public String getValue(ServerModel serverModel) {
		        return serverModel.getStatus();
		      }

		    });
		
		
		return mRDTableModelTab1;
	}

	private void loadServerConfData() {
		RDSwingWorker<List<ServerModel>> lRDSwingWorker = new RDSwingWorker<List<ServerModel>>() {
			
			@Override
			protected List<ServerModel> doInBackground() throws Exception {
				getController().getStatusBarEvaluator().init();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("getting fetched");
				List<ServerModel> all = configController.loadAll(); 
				return all;
			}
			
			@Override
			protected void finished() throws Exception {
				List<ServerModel> DBserverModel = get(); 
				getController().getStatusBarEvaluator().done();
				getController().getStatusBarEvaluator().get1StLabelMessage().setText("successfully loaded");
				mRDTableModelTab1.setData(DBserverModel);
			}
		};
			
		lRDSwingWorker.execute();
	}
	

}
