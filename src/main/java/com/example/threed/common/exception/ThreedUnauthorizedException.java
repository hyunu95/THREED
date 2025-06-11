package com.example.threed.common.exception;

import org.springframework.http.HttpStatus;

public class ThreedUnauthorizedException extends ThreedException {

	public ThreedUnauthorizedException(String message) {
		super(message, HttpStatus.UNAUTHORIZED);
	}

}
