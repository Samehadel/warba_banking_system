package com.bank.account.controller;

import com.bank.account.entity.AccountEntity;
import com.bank.account.service.AccountService;
import com.bank.shared.controller.BaseController;
import com.bank.shared.dto.AccountDTO;
import com.bank.shared.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account-service/api")
public class AccountController extends BaseController<AccountDTO> {

	@Autowired
	private AccountService accountService;

	@Override
	protected BaseService getService() {
		return accountService;
	}
}
