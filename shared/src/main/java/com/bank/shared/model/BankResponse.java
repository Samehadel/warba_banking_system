package com.bank.shared.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankResponse<T> implements Serializable {
	private T data;
	private String errorCode;
	private String errorMessage;
	private StatusEnum status;
}
