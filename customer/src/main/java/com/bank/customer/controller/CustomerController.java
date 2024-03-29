package com.bank.customer.controller;

import com.bank.shared.dto.CustomerDTO;
import com.bank.customer.service.CustomerService;
import com.bank.shared.controller.BaseController;
import com.bank.shared.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer-service/api")
public class CustomerController extends BaseController<CustomerDTO> {

	@Autowired
	private CustomerService customerService;

	@Override
	protected BaseService getService() {
		return customerService;
	}
}
