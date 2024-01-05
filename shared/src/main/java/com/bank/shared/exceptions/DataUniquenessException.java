package com.bank.shared.exceptions;

public class DataUniquenessException extends RuntimeException {
	public DataUniquenessException(String message) {
		super(message);
	}
}
