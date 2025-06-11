package com.example.threed.common.exception;

import org.springframework.http.HttpStatus;

public class ThreedNotFoundException extends ThreedException {

	public ThreedNotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

}
