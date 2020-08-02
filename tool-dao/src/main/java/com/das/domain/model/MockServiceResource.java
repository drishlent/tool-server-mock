package com.das.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "MOCK_RESOURCE")
@Entity
@NamedQueries({
    @NamedQuery(name="MockServiceResource.UpdateServerId", query="UPDATE MockServiceResource m SET m.serverId=:serverId where m.id=:id")            
})
public class MockServiceResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String basePath;
	
	@Column(nullable = false)
	private String resourcePath;
	
	@Column(nullable = false)
    private String httpMethod;
	
	@Column(nullable = true)
    private Long serverId;
	
	@OneToOne(mappedBy="resource", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private MockServiceMessage message;

	public Long getId() {
		return id;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	
	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public MockServiceMessage getMessage() {
		return message;
	}

	public void setMessage(MockServiceMessage message) {
		this.message = message;
	}
	
    
}
