package com.bank.customer.service;

import com.bank.customer.dto.CustomerDTO;
import com.bank.shared.model.BankResponse;
import com.bank.shared.model.ErrorCodes;
import com.bank.shared.util.BankResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService {
	@Override
	public BankResponse<CustomerDTO> create(CustomerDTO dto) throws Exception {
		throw new Exception("Create Customer not implemented yet");
	}

	@Override
	public BankResponse<CustomerDTO> update(CustomerDTO dto) throws Exception {
		throw new Exception("Update Customer not implemented yet");
	}

	@Override
	public BankResponse get(Long id) throws Exception {
		throw new Exception("Get Customer not implemented yet");
	}

	@Override
	public BankResponse<Void> delete(Long id) throws Exception {
		throw new Exception("Delete Customer not implemented yet");
	}
}
