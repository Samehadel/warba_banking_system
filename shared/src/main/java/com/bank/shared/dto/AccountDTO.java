package com.bank.shared.dto;

import com.bank.shared.enums.AccountStatusEnum;
import com.bank.shared.enums.AccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO extends BaseDTO {
	private String customerCode;
	private String accountNumber;
	private AccountTypeEnum accountType;
	private AccountStatusEnum accountStatus;
	private String accountHolderName;
	private BigDecimal balance;
	private BigDecimal dailyLimit;
	private BigDecimal monthlyLimit;

}
