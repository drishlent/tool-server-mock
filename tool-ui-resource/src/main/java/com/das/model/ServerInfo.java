package com.das.model;

public final class ServerInfo {

	private final Long serverId;
	private final String serverName;
	
	public ServerInfo(Long serverId, String serverName) {
		this.serverId = serverId;
		this.serverName = serverName;
	}
	
	public Long getServerId() {
		return serverId;
	}
	
	public String getServerName() {
		return serverName;
	}

	@Override
	public String toString() {
		return serverName;
	}
	
	
}
