package com.bank.account.entity;

import com.bank.shared.entity.ActiveEntity;
import com.bank.shared.entity.BaseEntity;
import com.bank.shared.enums.AccountStatusEnum;
import com.bank.shared.enums.AccountTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNT")
public class AccountEntity extends ActiveEntity {
	@Id
	@GeneratedValue(generator = "ACCOUNT_ID_SEQ", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ACCOUNT_ID_SEQ", sequenceName = "ACCOUNT_ID_SEQ", allocationSize = 1)
	private Long id;

	@Column(name = "ACCOUNT_NUMBER", nullable = false, unique = true)
	private String accountNumber;

	@Column(name = "ACCOUNT_HOLDER_NAME", nullable = false)
	private String accountHolderName;

	@Enumerated(EnumType.STRING)
	@Column(name = "ACCOUNT_TYPE", nullable = false)
	private AccountTypeEnum accountType;

	@Enumerated(EnumType.STRING)
	@Column(name = "ACCOUNT_STATUS", nullable = false)
	private AccountStatusEnum accountStatus;

	@Column(name = "BALANCE", nullable = false)
	private BigDecimal balance;

	@Column(name = "DAILY_LIMIT", nullable = false)
	private BigDecimal dailyLimit;

	@Column(name = "MONTHLY_LIMIT", nullable = false)
	private BigDecimal monthlyLimit;


}
