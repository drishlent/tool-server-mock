package com.das.config;

import com.drishlent.dlite.EmbeddedModel;
import com.drishlent.dlite.table.RDTableModel;

public interface ConfigModel extends EmbeddedModel {

	void createServerConf(String serverName, String port, boolean isAutoRun);
	
	RDTableModel getServerTableModel();
	
}
