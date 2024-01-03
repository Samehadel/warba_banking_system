package com.bank.shared.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class BankResponse<T> implements Serializable {
	private T data;
	private String errorCode;
	private String errorMessage;
	private StatusEnum status;
}
