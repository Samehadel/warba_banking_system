package com.bank.account.mapper;

import com.bank.account.entity.AccountEntity;
import com.bank.shared.dto.AccountDTO;
import com.bank.shared.mapper.GlobalMapper;

public class AccountMapper implements GlobalMapper <AccountEntity, AccountDTO> {

	@Override
	public AccountEntity mapToEntity(AccountDTO dto) {
		AccountEntity entity = AccountEntity.builder()
				.accountType(dto.getAccountType())
				.accountHolderName(dto.getAccountHolderName())
				.build();

		return entity;
	}

	@Override
	public AccountDTO mapToDTO(AccountEntity entity) {
		AccountDTO dto = AccountDTO.builder()
				.accountNumber(entity.getAccountNumber())
				.accountType(entity.getAccountType())
				.accountStatus(entity.getAccountStatus())
				.accountHolderName(entity.getAccountHolderName())
				.monthlyLimit(entity.getMonthlyLimit())
				.dailyLimit(entity.getDailyLimit())
				.balance(entity.getBalance())
				.build();

		return dto;
	}
}
