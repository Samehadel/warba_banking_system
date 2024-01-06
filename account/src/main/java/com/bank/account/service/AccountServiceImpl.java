package com.bank.account.service;

import com.bank.account.AccountRepository;
import com.bank.account.clients.CustomerServiceClient;
import com.bank.account.configuration.AppConfig;
import com.bank.account.entity.AccountEntity;
import com.bank.account.mapper.AccountMapper;
import com.bank.shared.constants.EventsConstants;
import com.bank.shared.dto.AccountDTO;
import com.bank.shared.dto.CustomerDTO;
import com.bank.shared.dto.NotificationDTO;
import com.bank.shared.dto.OfficialIdDTO;
import com.bank.shared.enums.AccountStatusEnum;
import com.bank.shared.enums.NotificationTypeEnum;
import com.bank.shared.exceptions.IllegalOperationException;
import com.bank.shared.exceptions.InvalidDataException;
import com.bank.shared.exceptions.MissingRequiredFieldsException;
import com.bank.shared.model.BankResponse;
import com.bank.shared.util.BankResponseUtil;
import com.bank.shared.util.CollectionUtil;
import com.bank.shared.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

	private final AccountMapper accountMapper = new AccountMapper();

	@Autowired
	private AppConfig appConfig;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerServiceClient customerServiceClient;
	@Autowired
	private KafkaTemplate<String, NotificationDTO> kafkaTemplate;

	@Override
	public BankResponse<AccountDTO> create(AccountDTO dto) {
		try {
			log.info("Starting create account for customer {}", dto.getCustomerCode());
			validateAccountRequiredInfo(dto);
			CustomerDTO customerDTO = findCustomerByCode(dto.getCustomerCode());
			validateCustomer(customerDTO);
			validateCustomerAccounts(dto);
			BankResponse<AccountDTO> response = createAccount(dto);
			pushNotification(dto, customerDTO);
			return response;
		} finally {
			log.info("Finished create account for customer {}", dto.getCustomerCode());
		}
	}

	private void pushNotification(AccountDTO dto, CustomerDTO customerDTO) {
		try {
			log.info("Starting push notification for customer {}", dto.getCustomerCode());
			if(!StringUtil.isNullOrEmpty(customerDTO.getEmail())) {
				kafkaTemplate.send(EventsConstants.NOTIFICATION_MAIL_TOPIC, createEmailNotification(dto.getAccountNumber(), customerDTO));
			} else if(!StringUtil.isNullOrEmpty(customerDTO.getPhoneNumber())) {
				kafkaTemplate.send(EventsConstants.NOTIFICATION_SMS_TOPIC, createSMSNotification(dto.getAccountNumber(), customerDTO));
			}
		} catch (Exception e) {
			log.error("Error while push notification for customer {}", dto.getCustomerCode(), e);
		} finally {
			log.info("Finished push notification for customer {}", dto.getCustomerCode());
		}
	}

	private NotificationDTO createEmailNotification(String accountNumber, CustomerDTO customerDTO) {
		return NotificationDTO.builder()
				.message("Account created successfully with number " + accountNumber)
				.receiverIdentifier(customerDTO.getEmail())
				.type(NotificationTypeEnum.ACCOUNT_REGISTRATION)
				.build();
	}

	private NotificationDTO createSMSNotification(String accountNumber, CustomerDTO customerDTO) {
		return NotificationDTO.builder()
				.message("Account created successfully with number " + accountNumber)
				.receiverIdentifier(customerDTO.getPhoneNumber())
				.type(NotificationTypeEnum.ACCOUNT_REGISTRATION)
				.build();
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

	private void validateCustomer(CustomerDTO customerDTO) {
		log.info("Starting validate customer {}", customerDTO.getCustomerCode());

		if(!Boolean.TRUE.equals(customerDTO.getActive()) ||
				Boolean.TRUE.equals(customerDTO.getBlocked())) {
			throw new IllegalOperationException("Customer is not active");
		}
		validateCustomerOfficialIDs(customerDTO.getOfficialIDs());
	}

	private CustomerDTO findCustomerByCode(String customerCode) {
		BankResponse<CustomerDTO> response = customerServiceClient.get(customerCode);
		if (null == response || null == response.getData()) {
			throw new MissingRequiredFieldsException("Customer not found");
		}
		CustomerDTO customerDTO = response.getData();
		return customerDTO;
	}

	private void validateCustomerAccounts(AccountDTO accountDTO) {
		log.info("Starting validate customer accounts {}", accountDTO.getCustomerCode());
		String customerCode = accountDTO.getCustomerCode();
		long customerNumberOfAccount = accountRepository.countByCustomerCode(customerCode, AccountStatusEnum.getPendingList());
		if (customerNumberOfAccount >= appConfig.getMaxNumberAccounts()) {
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
			log.info("Starting get accounts {}", code);
			if(StringUtil.isNullOrEmpty(code)) {
				throw new MissingRequiredFieldsException("Customer code is required");
			}
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
	public BankResponse<Void> block(String accountNumber) {
		try {
			log.info("Starting block account for accountNumber {}", accountNumber);
			if(StringUtil.isNullOrEmpty(accountNumber)) {
				throw new MissingRequiredFieldsException("Customer accountNumber is required");
			}

			AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);

			accountEntity.setBlocked(Boolean.TRUE);
			accountEntity.setActive(Boolean.FALSE);

			accountRepository.save(accountEntity);
			return BankResponseUtil.getSuccessResponse();
		} finally {
			log.info("Finished block account for accountNumber {}", accountNumber);
		}
	}
}
