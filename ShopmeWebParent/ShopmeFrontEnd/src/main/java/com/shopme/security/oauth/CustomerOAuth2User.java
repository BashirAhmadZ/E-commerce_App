package com.shopme.security.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomerOAuth2User implements OAuth2User {

	private String clientName;
	private OAuth2User oauth2user;
	private String fullName;
	
	public CustomerOAuth2User(String clientName, OAuth2User oauth2user) {
		super();
		this.clientName = clientName;
		this.oauth2user = oauth2user;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return oauth2user.getAttributes();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return oauth2user.getAuthorities();
	}
	
	@Override
	public String getName() {
		return oauth2user.getAttribute("name");
	}
	
	public String getEmail() {
		return oauth2user.getAttribute("email");
	}
	
	public String getFullName() {
		return fullName != null ? fullName : oauth2user.getAttribute("name");
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setOauth2user(OAuth2User oauth2user) {
		this.oauth2user = oauth2user;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
