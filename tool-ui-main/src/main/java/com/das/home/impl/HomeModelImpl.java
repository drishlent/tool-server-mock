package com.das.home.impl;

import com.das.domain.model.ServerDetails;
import com.das.home.HomeModel;
import com.drishlent.dlite.AbstractEmbeddedModel;
import com.drishlent.dlite.table.RDTableColumn;
import com.drishlent.dlite.table.RDTableModel;



public class HomeModelImpl extends AbstractEmbeddedModel implements HomeModel {

	private RDTableModel mRDTableModelTab1;
	
	
	@Override
	public void initModel() {
		mRDTableModelTab1 = new RDTableModel();
	}

	@Override
	public void startModel() {
		getController().getStatusBarEvaluator().done();
	}

	@Override
	public RDTableModel getServerTableModel() {
		mRDTableModelTab1.addColumn(new RDTableColumn<ServerDetails, Integer>("Sl.", 2, "Serial Number") {
		      @Override
		      public Integer getValue(ServerDetails serverDetails) {
		        return 1;
		      }

		    });

		mRDTableModelTab1.addColumn(new RDTableColumn<ServerDetails, String>("Server Name", 30, "Server Name") {
		      @Override
		      public String getValue(ServerDetails serverDetails) {
		        return serverDetails.getName();
		      }

		    });
		
		mRDTableModelTab1.addColumn(new RDTableColumn<ServerDetails, Integer>("Port", 30, "Server Port") {
		      @Override
		      public Integer getValue(ServerDetails serverDetails) {
		        return serverDetails.getPort();
		      }

		    });
		
		mRDTableModelTab1.addColumn(new RDTableColumn<ServerDetails, Boolean>("AutoRun", 30, "Server Auto Run") {
		      @Override
		      public Boolean getValue(ServerDetails serverDetails) {
		        return serverDetails.getAutorun();
		      }

		    });
		
		mRDTableModelTab1.addColumn(new RDTableColumn<ServerDetails, Boolean>("Running", 30, "Server Auto Run") {
		      @Override
		      public Boolean getValue(ServerDetails serverDetails) {
		        return serverDetails.isRunning();
		      }

		    });
		
		
		return mRDTableModelTab1;
	}
}
