package com.bank.customer.service;

import com.bank.customer.CustomerRepository;
import com.bank.customer.dto.CustomerDTO;
import com.bank.customer.dto.OfficialIdDTO;
import com.bank.customer.entity.CustomerEntity;
import com.bank.customer.mapper.CustomerMapper;
import com.bank.shared.exceptions.MissingRequiredFieldsException;
import com.bank.shared.model.BankResponse;
import com.bank.shared.model.ErrorCodes;
import com.bank.shared.util.BankResponseUtil;
import com.bank.shared.util.CollectionUtil;
import com.bank.shared.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	private final CustomerMapper customerMapper = new CustomerMapper();


	@Override
	public BankResponse<CustomerDTO> create(CustomerDTO dto) throws Exception {
		try {
			log.info("Creating customer {}", dto);
			validateCustomerRequiredInfo(dto);
			validateInfoUniqness(dto);
			CustomerEntity customerEntity = customerMapper.mapToEntity(dto);
			customerEntity = customerRepository.save(customerEntity);
			return BankResponseUtil.getSuccessResponse(customerMapper.mapToDTO(customerEntity));
		} finally {
			log.info("Finished create customer {}", dto);
		}
	}

	private void validateCustomerRequiredInfo(CustomerDTO dto) {
		if (StringUtil.isNullOrEmpty(dto.getFirstName())) {
			throw new MissingRequiredFieldsException("First name is required");
		}
		if (StringUtil.isNullOrEmpty(dto.getLastName())) {
			throw new MissingRequiredFieldsException("Last name is required");
		}
		if (StringUtil.isNullOrEmpty(dto.getPhoneNumber()) && StringUtil.isNullOrEmpty(dto.getEmail())) {
			throw new MissingRequiredFieldsException("Phone number or email is required");
		}
		validateCustomerOfficialIDs(dto.getOfficialIDs());
	}

	private void validateCustomerOfficialIDs(Set<OfficialIdDTO> officialIDs) {
		if(CollectionUtil.isNullOrEmpty(officialIDs)) {
			throw new MissingRequiredFieldsException("At least one official ID is required");
		}
		for(OfficialIdDTO officialIdDTO : officialIDs) {
			if(StringUtil.isNullOrEmpty(officialIdDTO.getValue())) {
				throw new MissingRequiredFieldsException("Official ID number is required");
			}
			if(null == officialIdDTO.getType()) {
				throw new MissingRequiredFieldsException("Official ID type is required");
			}
			if(null == officialIdDTO.getExpiryDate()) {
				throw new MissingRequiredFieldsException("Official ID expiry date is required");
			}
		}
	}

	@Override
	public BankResponse<CustomerDTO> update(CustomerDTO dto) throws Exception {
		throw new Exception("Update Customer not implemented yet");
	}

	@Override
	public BankResponse get(Long id) throws Exception {
		throw new Exception("Get Customer not implemented yet");
	}

	@Override
	public BankResponse<Void> delete(Long id) throws Exception {
		throw new Exception("Delete Customer not implemented yet");
	}
}
