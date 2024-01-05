package com.bank.shared.service;

import com.bank.shared.model.BankResponse;

public interface BaseService <D>{
	BankResponse<D> create(D dto);
	BankResponse<D> get(String code);
	BankResponse<Void> block(String code);
}
