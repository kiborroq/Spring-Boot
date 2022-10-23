package edu.school21.springboot.exception;

import org.springframework.http.HttpStatus;

public class CinemaRuntimeException extends RuntimeException {

	private final Integer statusCode;

	public CinemaRuntimeException(String message, Integer statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public CinemaRuntimeException(String message, Integer statusCode, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public CinemaRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

	public Integer getStatusCode() {
		return statusCode;
	}
}
