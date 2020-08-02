package com.das.splash;

import java.util.logging.Logger;

import com.das.init.LoaderComponent;
import com.drishlent.dlite.annotation.Inject;
import com.drishlent.dlite.annotation.SplashInfo;
import com.drishlent.dlite.annotation.Value;

public class SplashHandler {

	private final static Logger mLogger = Logger.getLogger(SplashHandler.class.getName());
	
	@Value("service.login.url")
	private String loginServiceUrl;

	@Inject
	private LoaderComponent loaderComponent;
	
	
	
	public SplashHandler() {
	}
	
	@SplashInfo(msg="Checking Account")
	public boolean doCheckAccountFile() throws Exception {
		mLogger.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX "+loginServiceUrl);
		mLogger.info("Checking account file : ------------ initated");
		Thread.sleep(5000);
		mLogger.info("Checking account file : ------------ done");
		return true;
	}
	
	@SplashInfo(msg="starting server(s)")
	public boolean doStartServer() throws Exception {
		//loaderComponent.doStartServer();
		return true;
	}
	
	@SplashInfo(msg="publish to server")
	public boolean doPublishToServer() throws Exception {
		//loaderComponent.doPublishToServer();
		return true;
	}
	
	@SplashInfo(msg="Finsh Account")
	public boolean doFinshAccountFile() throws Exception {
		Thread.sleep(1000);
		return true;
	}
}
