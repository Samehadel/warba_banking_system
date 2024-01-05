package com.bank.account.service;

import com.bank.account.AccountRepository;
import com.bank.account.clients.CustomerServiceClient;
import com.bank.account.entity.AccountEntity;
import com.bank.account.mapper.AccountMapper;
import com.bank.shared.dto.AccountDTO;
import com.bank.shared.dto.CustomerDTO;
import com.bank.shared.dto.OfficialIdDTO;
import com.bank.shared.enums.AccountStatusEnum;
import com.bank.shared.enums.AccountTypeEnum;
import com.bank.shared.exceptions.IllegalOperationException;
import com.bank.shared.exceptions.InvalidDataException;
import com.bank.shared.exceptions.MissingRequiredFieldsException;
import com.bank.shared.model.BankResponse;
import com.bank.shared.model.StatusEnum;
import com.bank.shared.util.BankResponseUtil;
import com.bank.shared.util.CollectionUtil;
import com.bank.shared.util.StringUtil;
import com.google.common.io.CharSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

	private final AccountMapper accountMapper = new AccountMapper();

	@Value("${account.max.number.accounts}")
	private long maxNumberAccounts;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerServiceClient customerServiceClient;


	@Override
	public BankResponse<AccountDTO> create(AccountDTO dto) {
		try {
			log.info("Starting create account for customer {}", dto.getCustomerCode());
			validateAccountRequiredInfo(dto);
			validateCustomer(dto.getCustomerCode());
			validateCustomerAccounts(dto);
			return createAccount(dto);
		} finally {
			log.info("Finished create account for customer {}", dto.getCustomerCode());
		}
	}

	private void validateAccountRequiredInfo(AccountDTO dto) {
		log.info("Starting validate account required info {}", dto);
		if (StringUtil.isNullOrEmpty(dto.getCustomerCode())) {
			throw new MissingRequiredFieldsException("Customer code is required");
		}
		if (null == dto.getAccountType()) {
			throw new MissingRequiredFieldsException("Account type is required");
		}
	}

	private void validateCustomer(String customerCode) {
		log.info("Starting validate customer {}", customerCode);
		BankResponse<CustomerDTO> response = customerServiceClient.get(customerCode);
		if (null == response || null == response.getData()) {
			throw new MissingRequiredFieldsException("Customer not found");
		}
		CustomerDTO customerDTO = response.getData();
		if(!Boolean.TRUE.equals(customerDTO.getActive()) ||
				Boolean.TRUE.equals(customerDTO.getBlocked())) {
			throw new IllegalOperationException("Customer is not active");
		}
		validateCustomerOfficialIDs(customerDTO.getOfficialIDs());
	}

	private void validateCustomerAccounts(AccountDTO accountDTO) {
		log.info("Starting validate customer accounts {}", accountDTO.getCustomerCode());
		String customerCode = accountDTO.getCustomerCode();
		long customerNumberOfAccount = accountRepository.countByCustomerCode(customerCode, AccountStatusEnum.getPendingList());
		if (customerNumberOfAccount >= maxNumberAccounts) {
			throw new IllegalOperationException("Customer reached the maximum number of accounts");
		}
		long customerExistingAccountWithSameType = accountRepository.countByCustomerCodeAndAccountType(customerCode, accountDTO.getAccountType(), AccountStatusEnum.PENDING_APPROVAL);
		if (customerExistingAccountWithSameType > 0) {
			throw new IllegalOperationException("Customer already has an account with the same type");
		}
	}

	private void validateCustomerOfficialIDs(Set<OfficialIdDTO> officialIDs) {
		log.info("Starting Validating customer official IDs");
		if (CollectionUtil.isNullOrEmpty(officialIDs)) {
			throw new IllegalOperationException("At least one official ID is required");
		}
		for (OfficialIdDTO officialIdDTO : officialIDs) {
			if (StringUtil.isNullOrEmpty(officialIdDTO.getValue())) {
				throw new IllegalOperationException("Official ID number is required");
			}
			if (null == officialIdDTO.getType()) {
				throw new IllegalOperationException("Official ID type is required");
			}
			if (null == officialIdDTO.getExpiryDate()) {
				throw new IllegalOperationException("Official ID expiry date is required");
			}
			Date today = new Date();
			if (officialIdDTO.getExpiryDate().before(today)) {
				throw new InvalidDataException("Official ID is expired");
			}
		}
	}

	private BankResponse<AccountDTO> createAccount(AccountDTO dto) {
		log.info("Starting create account {}", dto);
		AccountEntity entity = accountMapper.mapToEntity(dto);
		entity.setAccountStatus(AccountStatusEnum.PENDING_APPROVAL);
		entity = accountRepository.save(entity);
		dto = accountMapper.mapToDTO(entity);
		return BankResponseUtil.getSuccessResponse(dto);
	}



	@Override
	public BankResponse<AccountDTO> get(String code) {
		try {
			log.info("Starting get account {}", code);
			List<AccountEntity> customerAccounts = accountRepository.findByCustomerCode(code);
			List<AccountDTO> accountDTOS = getAccountDTOs(customerAccounts);
			return BankResponseUtil.getSuccessResponse(accountDTOS);
		} finally {
			log.info("Finished get account {}", code);
		}
	}

	private List<AccountDTO> getAccountDTOs(List<AccountEntity> customerAccounts) {
		List<AccountDTO> accountDTOs = new ArrayList<>();
		for (AccountEntity accountEntity : customerAccounts) {
			accountDTOs.add(accountMapper.mapToDTO(accountEntity));
		}
		return accountDTOs;
	}

	@Override
	public BankResponse<Void> block(String code) {
		return null;
	}
}
