package org.mitre.openid.connect.repository.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.UserInfo;

public class UserApiClient {
	public UserDto findByCredentials(String login){
		try {
			URL url = new URL("http://api-lb.fms-alpha.com/UserApi/user/search.json?type=login&login=" + login);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("accept", "application/json");
			InputStream responseStream = connection.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			UserDto dto = mapper.readValue(responseStream, UserDto.class);
			return dto;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
