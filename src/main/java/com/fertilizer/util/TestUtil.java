package com.fertilizer.util;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


public class TestUtil {
	@LocalServerPort
    public int randomServerPort;

	public static final String AUTHORIZATION = "Authorization";
	public RestTemplate restTemplate;

	public void setAuthorization(HttpHeaders headers, LinkedHashMap<?, ?> jwtAuthenticationResponse) {
		headers.set(AUTHORIZATION,jwtAuthenticationResponse.get("tokenType").toString()+" "+String.join(jwtAuthenticationResponse.get("refreshToken").toString(), jwtAuthenticationResponse.get("accessToken").toString()));
	}

	public HttpHeaders getHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
