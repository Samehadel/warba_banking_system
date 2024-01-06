package com.bank.customer.unit;


import com.bank.customer.controller.CustomerController;
import com.bank.customer.service.CustomerService;

import com.bank.shared.dto.CustomerDTO;
import com.bank.shared.model.BankResponse;
import com.bank.shared.model.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.bank.customer.util.MockingUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {
	@InjectMocks
	private CustomerController customerController;

	@Mock
	private CustomerService customerService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateCustomer() {
		when(customerService.create(getAnyCustomerDTO())).thenReturn(getSuccessBankResponseCustomerDTO());
		BankResponse<CustomerDTO> response = customerController.create(createValidCustomer());
		assertEquals(StatusEnum.SUCCESS, response.getStatus());
		assertNotNull(response.getData());
		assertNull(response.getErrorCode());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testCreateAccount_FailedResponse() {
		when(customerService.create(getAnyCustomerDTO())).thenReturn(getFailedBankResponse());
		BankResponse<CustomerDTO> response = customerController.create(createValidCustomer());
		assertEquals(StatusEnum.FAILED, response.getStatus());
		assertNull(response.getData());
		assertNotNull(response.getErrorCode());
		assertNotNull(response.getErrorMessage());
	}


	@Test
	public void testGetAccounts() {
		when(customerService.get(getAnyString())).thenReturn(getSuccessBankResponseCustomerDTO());
		BankResponse<CustomerDTO> response = customerController.get("123");
		assertEquals(StatusEnum.SUCCESS, response.getStatus());
		assertNotNull(response.getData());
		assertNull(response.getErrorCode());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetCustomer_FailedResponse() {
		when(customerService.get(getAnyString())).thenReturn(getFailedBankResponse());
		BankResponse<CustomerDTO> response = customerController.get("123");
		assertEquals(StatusEnum.FAILED, response.getStatus());
		assertNull(response.getData());
		assertNotNull(response.getErrorCode());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testBlockCustomer() {
		when(customerService.block(getAnyString())).thenReturn(getSuccessBankResponseCustomerDTO());
		BankResponse<Void> response = customerController.block("123");
		assertEquals(StatusEnum.SUCCESS, response.getStatus());
		assertNotNull(response.getData());
		assertNull(response.getErrorCode());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testBlockAccount_FailedResponse() {
		when(customerService.block(getAnyString())).thenReturn(getFailedBankResponse());
		BankResponse<Void> response = customerController.block("123");
		assertEquals(StatusEnum.FAILED, response.getStatus());
		assertNull(response.getData());
		assertNotNull(response.getErrorCode());
		assertNotNull(response.getErrorMessage());
	}

}
