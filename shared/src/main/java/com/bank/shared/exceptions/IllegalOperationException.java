package com.bank.shared.exceptions;

public class IllegalOperationException extends RuntimeException {
	public IllegalOperationException(String message) {
		super(message);
	}
}
