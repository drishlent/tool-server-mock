package com.das.service;

import java.util.Map;
import java.util.logging.Logger;

import com.das.domain.model.User;
import com.das.domain.model.UserRole;
import com.das.init.LoaderComponent;
import com.das.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.drishlent.dlite.annotation.Inject;
import com.drishlent.dlite.annotation.Service;
import com.drishlent.dlite.annotation.Value;
import com.drishlent.dlite.model.UserInfo;
import com.drishlent.dlite.model.UserInfo.RegistrationStatus;
import com.drishlent.dlite.model.UserInfo.UserStatus;
import com.drishlent.dlite.service.LoginService;
import com.drishlent.dlite.service.RegistrationService;

@Service
public class LoginServiceImpl implements LoginService, RegistrationService {
	private final Logger mLogger = Logger.getLogger(LoginServiceImpl.class.getName());

	@Inject
	private ObjectMapper mapper;
	
	@Value("service.login.url")
	private String loginServiceUrl;
	
	@Value("service.registration.url")
	private String regisServiceUrl;
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private LoaderComponent loaderComponent;
	
	@Override
	public UserInfo login(final String userId, final String password) throws Exception {
		mLogger.info("Loading value from property "+loginServiceUrl);
		UserInfo userInfo = new UserInfo();
		
		User user = userRepository.findById(userId); 
		
		if (user != null) {
			if (password.equals(user.getPassword())) {
				userInfo.setStatus(UserStatus.SUCCESS);
				userInfo.setUserId(userId);
				
				loaderComponent.load();
			} else {
				userInfo.setStatus(UserStatus.WRONG_PASSWORD);
			}
			
		} else {
			userInfo.setStatus(UserStatus.INVALID_USER);
		}
		
		///------------
		userInfo.setStatus(UserStatus.SUCCESS);
		userInfo.setUserId("Rintu");
		///------------
		
		return userInfo;
	}

	@Override
	public RegistrationStatus register(Map<String, String> fieldDetails) throws Exception {
		mLogger.info("Loading value from property "+regisServiceUrl);
		
		String id = fieldDetails.get("id");
		String password = fieldDetails.get("password");
		String name = fieldDetails.get("name");
		String mobile = fieldDetails.get("mobile");
		
		User user = new User();
		user.setUserid(id);
		user.setName(name);
		user.setPassword(password);
		user.setMobilenumber(mobile);
		
		UserRole role = new UserRole();
		role.setName("admin");
		role.setUser(user);
		
		user.addRole(role);
		
		User userDB = userRepository.insert(user); 
		if(userDB.getRoles().iterator().next().getId() != null) {
			return RegistrationStatus.SUCCESS;
		} 
		 
		return RegistrationStatus.FAILURE;
	}
	

}
