package org.mitre.openid.connect.service.impl;

import org.mitre.openid.connect.repository.impl.UserApiClient;
import org.mitre.openid.connect.repository.impl.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class FujisanUserDetailService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(FujisanUserDetailService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserApiClient client = new UserApiClient();
		UserDto dto = client.findByCredentials(username);
		if (dto != null) {
			logger.info("loadUserByUsername ============================ user found");
			return new User(username, dto.password, AuthorityUtils.createAuthorityList("ROLE_USER"));
		}
		logger.info("user not found");
		throw new UsernameNotFoundException("not found : " + username);
	}
}
