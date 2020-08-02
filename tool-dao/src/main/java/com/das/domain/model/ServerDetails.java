package com.das.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "SERVER_DETAILS")
@Entity
public class ServerDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "NAME", nullable = false, unique=true)
	private String name;
	
	@Column(name = "PORT", nullable = false, unique=true)
	private Integer port;
	
	@Column(name = "AUTORUN", nullable = false)
	private Boolean autorun = Boolean.FALSE;
	
	@Transient
	private boolean running;
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Boolean getAutorun() {
		return autorun;
	}

	public void setAutorun(Boolean autorun) {
		this.autorun = autorun;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
