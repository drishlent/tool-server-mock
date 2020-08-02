package com.das.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "MOCK_MESSAGE")
@Entity
public class MockServiceMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Lob
	private char[] requestMsg;
	
	@Lob
	private char[] responseMsg;
	
	@Column(nullable = false)
	private Integer responseCode;
	
	@OneToOne
	@JoinColumn(name="RESOURCE_ID", nullable=false)
	private MockServiceResource resource;

	public Long getId() {
		return id;
	}

	public char[] getRequestMsg() {
		return requestMsg;
	}

	public void setRequestMsg(char[] requestMsg) {
		this.requestMsg = requestMsg;
	}

	public char[] getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(char[] responseMsg) {
		this.responseMsg = responseMsg;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public MockServiceResource getResource() {
		return resource;
	}

	public void setResource(MockServiceResource resource) {
		this.resource = resource;
	}
	
	
}
