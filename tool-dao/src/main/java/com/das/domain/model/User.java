package com.das.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "USER_DETAILS")
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false)
	private String userid;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String mobilenumber;

	@Column(nullable = false)
	private String password;
	

	@OneToMany(mappedBy = "user", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private List<UserRole> roles = new ArrayList<UserRole>();


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMobilenumber() {
		return mobilenumber;
	}


	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<UserRole> getRoles() {
		return roles;
	}


	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	
	public void addRole(UserRole userRole){
		userRole.setUser(this); 
		roles.add(userRole);
	}
	


	@Override
	public String toString() {
		return "User [userid=" + userid + ", name=" + name + ", mobilenumber=" + mobilenumber + ", password=" + password
				+ ", roles=" + roles + "]";
	}
	
	
}
