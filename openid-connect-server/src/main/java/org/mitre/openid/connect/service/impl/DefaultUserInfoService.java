/*******************************************************************************
 * Copyright 2018 The MIT Internet Trust Consortium
 *
 * Portions copyright 2011-2013 The MITRE Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.mitre.openid.connect.service.impl;

import org.mitre.oauth2.model.ClientDetailsEntity;
import org.mitre.oauth2.model.ClientDetailsEntity.SubjectType;
import org.mitre.oauth2.service.ClientDetailsEntityService;
import org.mitre.openid.connect.model.DefaultUserInfo;
import org.mitre.openid.connect.model.UserInfo;
import org.mitre.openid.connect.repository.UserInfoRepository;
import org.mitre.openid.connect.repository.impl.UserApiClient;
import org.mitre.openid.connect.repository.impl.UserDto;
import org.mitre.openid.connect.service.PairwiseIdentiferService;
import org.mitre.openid.connect.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the UserInfoService
 *
 * @author Michael Joseph Walsh, jricher
 *
 */
@Service
public class DefaultUserInfoService implements UserInfoService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultUserInfoService.class);

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private ClientDetailsEntityService clientService;

	@Autowired
	private PairwiseIdentiferService pairwiseIdentifierService;


	@Override
	public UserInfo getByUsername(String username) {
		logger.info("getByUsername: " + username);
		UserApiClient apiClient = new UserApiClient();
		UserDto dto = apiClient.findByCredentials(username);
		DefaultUserInfo userInfo = new DefaultUserInfo();
		userInfo.setId(Long.parseLong(dto.id));
		userInfo.setSub(dto.id);
		userInfo.setEmail(dto.login);
		// return userInfoRepository.getByUsername(username);
		logger.info("getByUsername: " + userInfo);
		return userInfo;
	}

	@Override
	public UserInfo getByUsernameAndClientId(String username, String clientId) {

		logger.info("getByUsernameAndClientId: " + username);
		logger.info("getByUsernameAndClientId: " + clientId);

		ClientDetailsEntity client = clientService.loadClientByClientId(clientId);

		UserInfo userInfo = getByUsername(username);

		if (client == null || userInfo == null) {
			return null;
		}

		if (SubjectType.PAIRWISE.equals(client.getSubjectType())) {
			String pairwiseSub = pairwiseIdentifierService.getIdentifier(userInfo, client);
			userInfo.setSub(pairwiseSub);
		}

		return userInfo;

	}

	@Override
	public UserInfo getByEmailAddress(String email) {
		return userInfoRepository.getByEmailAddress(email);
	}

}
