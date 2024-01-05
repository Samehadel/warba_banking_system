package com.bank.shared.service;

import com.bank.shared.model.BankResponse;

public interface BaseService <D, C>{
	BankResponse<D> create(D dto) throws Exception;
	BankResponse<D> update(D dto) throws Exception;
	BankResponse<D> get(String code) throws Exception;
	BankResponse<Void> delete(Long id) throws Exception;
}
