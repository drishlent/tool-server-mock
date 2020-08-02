package com.das.model;

public class SwaggerResource {

	private Long resourceId;
	private String basePath;
	private String resourcePath;
	private String httpMethod;
	private String requestMessage;
	private String responseMessage;
	private Integer responseCode;
	private String serverName;
	private Long serverId;
	private String status;

	public ResourcePathMethod getResourcePathMethod() {
		return new ResourcePathMethod(resourcePath, httpMethod);
	}
	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
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

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Resource [resourceId=" + resourceId + ", basePath=" + basePath + ", resourcePath=" + resourcePath
				+ ", httpMethod=" + httpMethod + ", requestMessage=" + requestMessage + ", responseMessage="
				+ responseMessage + ", responseCode=" + responseCode + ", serverName=" + serverName + ", status="
				+ status + "]";
	}

}
