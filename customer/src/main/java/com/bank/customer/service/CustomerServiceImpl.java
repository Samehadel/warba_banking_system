package com.bank.customer.service;

import com.bank.customer.CustomerRepository;
import com.bank.customer.constatnts.ErrorMessages;
import com.bank.shared.dto.CustomerDTO;
import com.bank.shared.dto.OfficialIdDTO;
import com.bank.customer.entity.CustomerEntity;
import com.bank.shared.enums.OfficialIdTypeEnum;
import com.bank.customer.mapper.CustomerMapper;
import com.bank.shared.exceptions.DataNotFoundException;
import com.bank.shared.exceptions.DataUniquenessException;
import com.bank.shared.exceptions.InvalidDataException;
import com.bank.shared.exceptions.MissingRequiredFieldsException;
import com.bank.shared.model.BankResponse;
import com.bank.shared.util.BankResponseUtil;
import com.bank.shared.util.CollectionUtil;
import com.bank.shared.util.RegexValidator;
import com.bank.shared.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	private final CustomerMapper customerMapper = new CustomerMapper();


	@Override
	public BankResponse<CustomerDTO> create(CustomerDTO dto) {
		try {
			log.info("Starting Creating customer {}", dto);
			doValidateBeforeSave(dto);
			CustomerEntity customerEntity = customerMapper.mapToEntity(dto);
			customerEntity = customerRepository.save(customerEntity);
			return BankResponseUtil.getSuccessResponse(customerMapper.mapToDTO(customerEntity));
		} finally {
			log.info("Finished create customer {}", dto);
		}
	}

	private void doValidateBeforeSave(CustomerDTO dto) {
		log.info("Starting Validating customer before save {}", dto);
		validateCustomerRequiredInfo(dto);
		validateInfoFormat(dto);
		validateInfoUniqueness(dto);
	}

	private void validateCustomerRequiredInfo(CustomerDTO dto) {
		log.info("Starting Validating customer required info {}", dto);
		if (StringUtil.isNullOrEmpty(dto.getFirstName())) {
			throw new MissingRequiredFieldsException(ErrorMessages.MISSING_FIRST_NAME);
		}
		if (StringUtil.isNullOrEmpty(dto.getLastName())) {
			throw new MissingRequiredFieldsException(ErrorMessages.MISSING_LAST_NAME);
		}
		if (StringUtil.isNullOrEmpty(dto.getPhoneNumber()) && StringUtil.isNullOrEmpty(dto.getEmail())) {
			throw new MissingRequiredFieldsException(ErrorMessages.MISSING_PHONE_NUMBER_OR_EMAIL);
		}
		validateCustomerOfficialIDs(dto.getOfficialIDs());
	}

	private void validateCustomerOfficialIDs(Set<OfficialIdDTO> officialIDs) {
		log.info("Starting Validating customer official IDs");
		if (CollectionUtil.isNullOrEmpty(officialIDs)) {
			throw new MissingRequiredFieldsException(ErrorMessages.MISSING_OFFICIAL_IDS);
		}
		for (OfficialIdDTO officialIdDTO : officialIDs) {
			if (StringUtil.isNullOrEmpty(officialIdDTO.getValue())) {
				throw new MissingRequiredFieldsException(ErrorMessages.MISSING_OFFICIAL_ID_VALUE);
			}
			if (null == officialIdDTO.getType()) {
				throw new MissingRequiredFieldsException(ErrorMessages.MISSING_OFFICIAL_ID_TYPE);
			}
			if (null == officialIdDTO.getExpiryDate()) {
				throw new MissingRequiredFieldsException(ErrorMessages.MISSING_OFFICIAL_ID_EXPIRY_DATE);
			}
			Date today = new Date();
			if (officialIdDTO.getExpiryDate().before(today)) {
				throw new InvalidDataException(ErrorMessages.INVALID_OFFICIAL_ID_EXPIRY_DATE);
			}
		}
	}

	private void validateInfoFormat(CustomerDTO dto) {
		log.info("Starting Validating customer info format {}", dto);
		if (!StringUtil.isNullOrEmpty(dto.getEmail()) && !RegexValidator.isValidEmail(dto.getEmail())) {
			throw new InvalidDataException(ErrorMessages.INVALID_EMAIL);
		}
		if (!StringUtil.isNullOrEmpty(dto.getPhoneNumber()) && !RegexValidator.isValidPhoneNumber(dto.getPhoneNumber())) {
			throw new InvalidDataException(ErrorMessages.INVALID_PHONE_NUMBER);
		}
		for (OfficialIdDTO officialIdDTO : dto.getOfficialIDs()) {
			if (officialIdDTO.getType() == OfficialIdTypeEnum.NATIONAL_ID && !RegexValidator.isValidNationalId(officialIdDTO.getValue())) {
				throw new InvalidDataException(ErrorMessages.INVALID_NATIONAL_ID);
			} else if (officialIdDTO.getType() == OfficialIdTypeEnum.PASSPORT && !RegexValidator.isValidPassportNumber(officialIdDTO.getValue())) {
				throw new InvalidDataException(ErrorMessages.INVALID_PASSPORT_NUMBER);
			}
		}
	}

	private void validateInfoUniqueness(CustomerDTO dto) {
		log.info("Starting Validating customer info uniqueness {}", dto);

		long countByEmail = customerRepository.countByEmail(dto.getEmail());
		if (countByEmail > 0) {
			throw new DataUniquenessException(ErrorMessages.EMAIL_ALREADY_EXISTS);
		}

		long countByPhoneNumber = customerRepository.countByPhoneNumber(dto.getPhoneNumber());
		if (countByPhoneNumber > 0) {
			throw new DataUniquenessException(ErrorMessages.PHONE_NUMBER_ALREADY_EXISTS);
		}

		for (OfficialIdDTO officialIdDTO : dto.getOfficialIDs()) {
			long countByOfficialId = customerRepository.countByOfficialId(officialIdDTO.getValue(), officialIdDTO.getType());
			if (countByOfficialId > 0) {
				throw new DataUniquenessException(ErrorMessages.OFFICIAL_ID_ALREADY_EXISTS);
			}
		}
	}


	@Override
	public BankResponse get(String code) {
		try {
			log.info("Starting get customer by code {}", code);
			if(StringUtil.isNullOrEmpty(code)) {
				throw new MissingRequiredFieldsException("Customer code is required");
			}

			CustomerEntity customerEntity = findCustomerByCustomerCode(code);
			CustomerDTO customerDTO = customerMapper.mapToDTO(customerEntity);
			return BankResponseUtil.getSuccessResponse(customerDTO);
		} finally {
			log.info("Finished get customer by code {}", code);
		}
	}

	@Override
	public BankResponse<Void> block(String code) {
		try {
			log.info("Starting block customer by code {}", code);
			if(StringUtil.isNullOrEmpty(code)) {
				throw new MissingRequiredFieldsException("Customer code is required");
			}

			CustomerEntity customerEntity = findCustomerByCustomerCode(code);

			customerEntity.setBlocked(Boolean.TRUE);
			customerEntity.setActive(Boolean.FALSE);

			customerRepository.save(customerEntity);
			return BankResponseUtil.getSuccessResponse();
		} finally {
			log.info("Finished block customer by code {}", code);
		}
	}

	private CustomerEntity findCustomerByCustomerCode(String code) {
		CustomerEntity customerEntity = customerRepository
				.findByCustomerCode(code)
				.orElseThrow(() -> new DataNotFoundException("Customer not found with code " + code));
		return customerEntity;
	}
}
