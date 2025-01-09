package com.fertilizer.payload.response;

public class JwtAuthenticationResponse {
	private String accessToken;
	private String refreshToken;
	private String tokenType = "Bearer";
	//private SSOTokenAndCookieUrl ssoData;

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public JwtAuthenticationResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

//	public JwtAuthenticationResponse(String accessToken, String refreshToken, SSOTokenAndCookieUrl ssoToken) {
//		this.accessToken = accessToken;
//		this.refreshToken = refreshToken;
//		this.ssoData = ssoToken;
//	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationResponse [accessToken=" + accessToken + ", refreshToken=" + refreshToken
				+ ", tokenType=" + tokenType + "]";
	}

//	public SSOTokenAndCookieUrl getSsoToken() {
//		return ssoData;
//	}
//
//	public void setSsoToken(SSOTokenAndCookieUrl ssoToken) {
//		this.ssoData = ssoToken;
//	}

//	@Override
//	public String toString() {
//		return "JwtAuthenticationResponse [accessToken=" + accessToken + ", refreshToken=" + refreshToken
//				+ ", tokenType=" + tokenType + ", ssoToken=" + ssoData + "]";
//	}
	
	
}
