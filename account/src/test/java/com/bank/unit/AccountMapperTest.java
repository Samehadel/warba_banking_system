package com.bank.unit;

import com.bank.account.entity.AccountEntity;
import com.bank.account.mapper.AccountMapper;
import com.bank.shared.dto.AccountDTO;
import com.bank.shared.enums.AccountStatusEnum;
import com.bank.shared.enums.AccountTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountMapperTest {
	private final AccountMapper accountMapper = new AccountMapper();

	@Test
	void testMapToEntity() {
		AccountDTO accountDTO = getAccountDTO();
		AccountEntity mappedEntity = accountMapper.mapToEntity(accountDTO);
		assertDTOMatchEntity(accountDTO, mappedEntity);
	}

	private AccountDTO getAccountDTO() {
		return AccountDTO.builder()
				.accountType(AccountTypeEnum.CERTIFICATE_OF_DEPOSIT)
				.accountHolderName("John Doe")
				.accountStatus(AccountStatusEnum.ACTIVE)
				.monthlyLimit(BigDecimal.TEN)
				.dailyLimit(BigDecimal.TWO)
				.balance(BigDecimal.ONE)
				.build();
	}

	private void assertDTOMatchEntity(AccountDTO accountDTO, AccountEntity mappedEntity) {
		assertEquals(accountDTO.getAccountType(), mappedEntity.getAccountType());
		assertEquals(accountDTO.getAccountHolderName(), mappedEntity.getAccountHolderName());

		assertNull(mappedEntity.getId());
		assertNull(mappedEntity.getAccountNumber());
		assertNull(mappedEntity.getAccountStatus());
		assertNull(mappedEntity.getBalance());
		assertNull(mappedEntity.getDailyLimit());
		assertNull(mappedEntity.getMonthlyLimit());
		assertNull(mappedEntity.getActive());
		assertNull(mappedEntity.getBlocked());
	}

	@Test
	void testMapToDTO() {
		AccountEntity accountEntity = getAccountEntity();
		AccountDTO mappedDTO = accountMapper.mapToDTO(accountEntity);
		assertEntityMatchDTO(accountEntity, mappedDTO);
	}


	private AccountEntity getAccountEntity() {
		AccountEntity accountEntity = AccountEntity.builder()
				.id(15L)
				.accountHolderName("John Smith")
				.accountNumber("01444002251003")
				.accountType(AccountTypeEnum.MONEY_MARKET)
				.accountStatus(AccountStatusEnum.ACTIVE)
				.dailyLimit(BigDecimal.TEN)
				.monthlyLimit(BigDecimal.ONE)
				.balance(BigDecimal.TEN)
				.build();

		return accountEntity;
	}

	private void assertEntityMatchDTO(AccountEntity accountEntity, AccountDTO mappedDTO) {
		assertEquals(accountEntity.getAccountNumber(), mappedDTO.getAccountNumber());
		assertEquals(accountEntity.getAccountType(), mappedDTO.getAccountType());
		assertEquals(accountEntity.getAccountStatus(), mappedDTO.getAccountStatus());
		assertEquals(accountEntity.getAccountHolderName(), mappedDTO.getAccountHolderName());
		assertEquals(accountEntity.getMonthlyLimit(), mappedDTO.getMonthlyLimit());
		assertEquals(accountEntity.getDailyLimit(), mappedDTO.getDailyLimit());
		assertEquals(accountEntity.getBalance(), mappedDTO.getBalance());
	}
}
