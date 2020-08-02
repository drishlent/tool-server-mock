package com.das.init;



import org.apache.log4j.Logger;

import com.drishlent.dlite.annotation.Component;
import com.drishlent.dlite.annotation.Inject;
import com.drishlent.dlite.executer.InitServiceExecuter;

@Component
public class LoaderComponent {

	final static Logger mLogger = Logger.getLogger(LoaderComponent.class);
	
	
	@Inject
	private InitServiceExecuter initServiceExecuter;
	
	
	public void load() {
		try {
			mLogger.info("Started Loading");
			initServiceExecuter.notifyInitService();
			mLogger.info("Successfully Loaded");
		} catch (Exception e) {
			mLogger.error(e); 
		}	
	}
	
}
