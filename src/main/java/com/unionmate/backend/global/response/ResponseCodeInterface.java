package com.unionmate.backend.global.response;

import org.springframework.http.HttpStatus;

public interface ResponseCodeInterface {
	int getCode();
	HttpStatus getStatus();
	String getMessage();
}
