// com.example.threed.auth.service.client.GoogleOAuthClient.java

package com.example.threed.auth.service.client;

import com.example.threed.auth.config.GoogleOAuthProperties;
import com.example.threed.auth.dto.token.GoogleTokenResponse;
import com.example.threed.auth.dto.userinfo.GoogleUserInfo;
import com.example.threed.auth.dto.userinfo.OAuthUserInfo;
import com.example.threed.common.exception.ThreedBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component("GOOGLE")
@RequiredArgsConstructor
public class GoogleOAuthClient implements OAuthClient {

	private final RestTemplate restTemplate = new RestTemplate();
	private final GoogleOAuthProperties googleOAuthProperties;

	@Override
	public String requestAccessToken(String code) {
		String tokenUri = "https://oauth2.googleapis.com/token";

		String requestBody = "grant_type=authorization_code"
			+ "&client_id=" + googleOAuthProperties.getClientId()
			+ "&client_secret=" + googleOAuthProperties.getClientSecret()
			+ "&redirect_uri=" + googleOAuthProperties.getRedirectUri()
			+ "&code=" + code;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

		GoogleTokenResponse response = restTemplate.exchange(
			tokenUri,
			HttpMethod.POST,
			request,
			GoogleTokenResponse.class
		).getBody();

		if (response == null) {
			throw new ThreedBadRequestException("구글 토큰 응답이 비어있습니다.");
		}
		return response.accessToken();
	}

	@Override
	public OAuthUserInfo requestUserInfo(String accessToken) {
		String uri = "https://www.googleapis.com/oauth2/v2/userinfo";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		HttpEntity<Void> entity = new HttpEntity<>(headers);

		return restTemplate.exchange(
			uri,
			HttpMethod.GET,
			entity,
			GoogleUserInfo.class
		).getBody();
	}
}
