package com.das.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
/*@JsonPropertyOrder({
    "id"
})*/
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	
	@JsonProperty("id")
	private String userid;

	private String mailid;

	private String mobilenumber;

	private String password;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Account() {
	}

	public String getId() {
		return this.userid;
	}

	public void setId(String userid) {
		this.userid = userid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMailid() {
		return this.mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public String getMobilenumber() {
		return this.mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}