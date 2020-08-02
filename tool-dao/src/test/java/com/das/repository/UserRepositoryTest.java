package com.das.repository;

import org.junit.Assert;
import org.junit.Test;

import com.das.BaseTest;
import com.das.domain.model.User;
import com.das.domain.model.UserRole;
import com.drishlent.dlite.annotation.Inject;
import com.drishlent.dlite.annotation.TestUIComponent;


public class UserRepositoryTest extends BaseTest {

	@Inject
	private UserRepository userRepository;
	
	@Test
	public void testUserRepository() throws Exception { 
		User user = new User();
		user.setUserid("sachhida");
		user.setName("XXXX");
		user.setPassword("ccccc");
		user.setMobilenumber("9910203799");
		
		
		UserRole role = new UserRole();
		role.setName("admin");
		role.setUser(user);
		
		user.addRole(role);
		
		
		User userDB = userRepository.insert(user); 
		Assert.assertNotNull(userDB.getRoles().iterator().next().getId());
		
		System.out.println(userDB);
		
	}
}
