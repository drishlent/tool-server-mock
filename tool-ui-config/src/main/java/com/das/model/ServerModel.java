package com.das.model;

public class ServerModel {

	private Long id;
	private String name;
	private Integer port;
	private Boolean autorun = Boolean.FALSE;
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean isAutorun() {
		return autorun;
	}

	public void setAutorun(Boolean autorun) {
		this.autorun = autorun;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ServerModel [id=" + id + ", name=" + name + ", port=" + port + ", autorun=" + autorun + ", status="
				+ status + "]";
	}

}
