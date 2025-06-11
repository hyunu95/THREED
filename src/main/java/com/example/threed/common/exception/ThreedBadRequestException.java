package com.example.threed.common.exception;

import org.springframework.http.HttpStatus;

public class ThreedBadRequestException extends ThreedException {

	public ThreedBadRequestException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}

}
