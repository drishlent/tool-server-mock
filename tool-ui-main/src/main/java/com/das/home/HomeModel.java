package com.das.home;

import com.drishlent.dlite.EmbeddedModel;
import com.drishlent.dlite.table.RDTableModel;

public interface HomeModel extends EmbeddedModel {

	RDTableModel getServerTableModel();
	
}
