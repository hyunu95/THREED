package com.example.threed.auth.domain;

import com.example.threed.auth.config.AuthProperties;

public interface JwtToken {

	String getSecretKey(AuthProperties authProperties);

	String getValue();

}
