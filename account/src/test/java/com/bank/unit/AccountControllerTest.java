package com.bank.unit;

import com.bank.account.controller.AccountController;
import com.bank.account.service.AccountService;
import com.bank.shared.dto.AccountDTO;
import com.bank.shared.model.BankResponse;
import com.bank.shared.model.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.bank.util.MockingUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AccountControllerTest {
	@InjectMocks
	private AccountController accountController;

	@Mock
	private AccountService accountService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateAccount() {
		when(accountService.create(getAnyAccountDTO())).thenReturn(getSuccessBankResponseAccountDTO());
		BankResponse<AccountDTO> response = accountController.create(createValidAccountDTO());
		assertEquals(StatusEnum.SUCCESS, response.getStatus());
		assertNotNull(response.getData());
		assertNull(response.getErrorCode());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testCreateAccount_FailedResponse() {
		when(accountService.create(getAnyAccountDTO())).thenReturn(getFailedBankResponseAccountDTO());
		BankResponse<AccountDTO> response = accountController.create(createValidAccountDTO());
		assertEquals(StatusEnum.FAILED, response.getStatus());
		assertNull(response.getData());
		assertNotNull(response.getErrorCode());
		assertNotNull(response.getErrorMessage());
	}


	@Test
	public void testGetAccounts() {
		when(accountService.get(getAnyString())).thenReturn(getSuccessBankResponseAccountDTO());
		BankResponse<AccountDTO> response = accountController.get("123");
		assertEquals(StatusEnum.SUCCESS, response.getStatus());
		assertNotNull(response.getData());
		assertNull(response.getErrorCode());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetAccounts_FailedResponse() {
		when(accountService.get(getAnyString())).thenReturn(getFailedBankResponseAccountDTO());
		BankResponse<AccountDTO> response = accountController.get("123");
		assertEquals(StatusEnum.FAILED, response.getStatus());
		assertNull(response.getData());
		assertNotNull(response.getErrorCode());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testBlockAccount() {
		when(accountService.block(getAnyString())).thenReturn(getSuccessBankResponseAccountDTO());
		BankResponse<Void> response = accountController.block("123");
		assertEquals(StatusEnum.SUCCESS, response.getStatus());
		assertNotNull(response.getData());
		assertNull(response.getErrorCode());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testBlockAccount_FailedResponse() {
		when(accountService.block(getAnyString())).thenReturn(getFailedBankResponseAccountDTO());
		BankResponse<Void> response = accountController.block("123");
		assertEquals(StatusEnum.FAILED, response.getStatus());
		assertNull(response.getData());
		assertNotNull(response.getErrorCode());
		assertNotNull(response.getErrorMessage());
	}

}
