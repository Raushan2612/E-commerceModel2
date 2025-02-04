package com.cts.ppstores.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {
	public EmailAlreadyUsedException(String message) {
		super(message);
	}
}
