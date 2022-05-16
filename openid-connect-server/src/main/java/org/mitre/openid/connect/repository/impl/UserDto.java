package org.mitre.openid.connect.repository.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserDto {
	public final String id;
	public final String login;
	public final String password;
	public final String userName;

	public UserDto(@JsonProperty("id") String id,
				   @JsonProperty("login") String login,
				   @JsonProperty("password") String password,
				   @JsonProperty("userName") String userName
	) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.userName = userName;
	}
}
