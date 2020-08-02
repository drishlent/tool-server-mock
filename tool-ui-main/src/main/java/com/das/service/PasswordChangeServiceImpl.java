package com.das.service;

import java.util.Map;
import java.util.logging.Logger;

import com.drishlent.dlite.annotation.Service;
import com.drishlent.dlite.model.UserInfo.PasswordChangeStatus;
import com.drishlent.dlite.service.PasswordChangeService;

@Service
public class PasswordChangeServiceImpl implements PasswordChangeService {

	private final Logger mLogger = Logger.getLogger(PasswordChangeServiceImpl.class.getName());

	
	@Override
	public PasswordChangeStatus passwordChange(Map<String, String> fieldDetails) throws Exception {
		mLogger.info("Coming in service Impl");
		
		return PasswordChangeStatus.FAILURE;
	}

}
