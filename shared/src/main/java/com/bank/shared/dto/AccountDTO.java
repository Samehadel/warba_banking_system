package com.bank.shared.dto;

import com.bank.shared.enums.AccountStatusEnum;
import com.bank.shared.enums.AccountTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountDTO extends BaseDTO {
	private String accountNumber;
	private AccountTypeEnum accountType;
	private AccountStatusEnum accountStatus;
	private String accountHolderName;
	private BigDecimal balance;
	private BigDecimal dailyLimit;
	private BigDecimal monthlyLimit;

}
