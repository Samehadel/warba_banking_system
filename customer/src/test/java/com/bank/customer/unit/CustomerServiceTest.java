package com.bank.customer.unit;

import com.bank.customer.CustomerRepository;
import com.bank.customer.entity.CustomerEntity;
import com.bank.customer.mapper.CustomerMapper;
import com.bank.customer.service.CustomerServiceImpl;
import com.bank.shared.dto.CustomerDTO;
import com.bank.shared.exceptions.DataUniquenessException;
import com.bank.shared.exceptions.InvalidDataException;
import com.bank.shared.exceptions.MissingRequiredFieldsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.bank.customer.util.MockingUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {
	@InjectMocks
	private CustomerServiceImpl customerService;

	@Mock
	private CustomerRepository customerRepository;

	private CustomerMapper customerMapper = new CustomerMapper();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateCustomer_ValidCustomer() {
		CustomerDTO customerDTO = createValidCustomer();

		mockCountByEmail(0L);
		mockCountByPhoneNumber(0L);
		mockCountByOfficialId(0L);
		mockSaveCustomer(customerMapper.mapToEntity(customerDTO));
		customerService.create(customerDTO);
		verify(customerRepository, times(1)).save(getAnyCustomerEntity());
	}

	@Test
	public void testCreateCustomer_InvalidCustomer_MissingRequiredFields() {
		CustomerDTO customerDTO = createInValidCustomer_MissingFirstName();
		callAndValidate(customerDTO);

		customerDTO = createInValidCustomer_MissingLastName();
		callAndValidate(customerDTO);

		customerDTO = createInValidCustomer_MissingEmailAndPhoneNumber();
		callAndValidate(customerDTO);

		customerDTO = createInValidCustomer_MissingOfficialId();
		callAndValidate(customerDTO);

		customerDTO = createInValidCustomer_MissingOfficialIdValue();
		callAndValidate(customerDTO);

		customerDTO = createInValidCustomer_MissingOfficialIdType();
		callAndValidate(customerDTO);

		customerDTO = createInValidCustomer_MissingOfficialIdExpiryDate();
		callAndValidate(customerDTO);
	}

	private void callAndValidate(CustomerDTO customerDTO) {
		try {
			customerService.create(customerDTO);
			assertEquals(1, 2);
		} catch (MissingRequiredFieldsException e) {
			verify(customerRepository, times(0)).countByEmail(getAnyString());
			verify(customerRepository, times(0)).countByEmail(getAnyString());
			verify(customerRepository, times(0)).countByOfficialId(getAnyString(), getAnyOfficialIdType());
			verify(customerRepository, times(0)).save(getAnyCustomerEntity());
		}
	}

	@Test
	public void testCreateCustomer_InvalidCustomer_OfficialIDExpired() {
		CustomerDTO customerDTO = createInValidCustomer_OfficialIDExpired();
		try {
			customerService.create(customerDTO);
			assertEquals(1, 2);
		} catch (InvalidDataException e) {
			verify(customerRepository, times(0)).countByEmail(getAnyString());
			verify(customerRepository, times(0)).countByEmail(getAnyString());
			verify(customerRepository, times(0)).countByOfficialId(getAnyString(), getAnyOfficialIdType());
			verify(customerRepository, times(0)).save(getAnyCustomerEntity());
		}
	}

	@Test
	public void testCreateCustomer_NotUniquePhone() {
		CustomerDTO customerDTO = createValidCustomer();

		mockCountByEmail(0L);
		mockCountByPhoneNumber(1L);
		mockCountByOfficialId(0L);
		try {
			customerService.create(customerDTO);
			assertEquals(1, 2);
		} catch (DataUniquenessException e) {
			verify(customerRepository, times(0)).save(getAnyCustomerEntity());
		}
	}

	@Test
	public void testCreateCustomer_NotUniqueEmail() {
		CustomerDTO customerDTO = createValidCustomer();

		mockCountByEmail(1L);
		mockCountByPhoneNumber(0L);
		mockCountByOfficialId(0L);
		try {
			customerService.create(customerDTO);
			assertEquals(1, 2);
		} catch (DataUniquenessException e) {
			verify(customerRepository, times(0)).save(getAnyCustomerEntity());
		}
	}

	@Test
	public void testCreateCustomer_NotUniqueOfficialID() {
		CustomerDTO customerDTO = createValidCustomer();

		mockCountByEmail(0L);
		mockCountByPhoneNumber(0L);
		mockCountByOfficialId(1L);
		try {
			customerService.create(customerDTO);
			assertEquals(1, 2);
		} catch (DataUniquenessException e) {
			verify(customerRepository, times(0)).save(getAnyCustomerEntity());
		}
	}



	private void mockCountByEmail(Long count) {
		when(customerRepository.countByEmail(getAnyString())).thenReturn(count);
	}

	private void mockCountByPhoneNumber(Long count) {
		when(customerRepository.countByPhoneNumber(getAnyString())).thenReturn(count);
	}

	private void mockCountByOfficialId(Long count) {
		when(customerRepository.countByOfficialId(getAnyString(), getAnyOfficialIdType())).thenReturn(count);
	}

	private void mockSaveCustomer(CustomerEntity customerEntity) {
		when(customerRepository.save(getAnyCustomerEntity())).thenReturn(customerEntity);
	}
}
