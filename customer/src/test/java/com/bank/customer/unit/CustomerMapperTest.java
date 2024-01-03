package com.bank.customer.unit;
import com.bank.customer.dto.AddressDTO;
import com.bank.customer.dto.CustomerDTO;
import com.bank.customer.dto.OfficialIdDTO;
import com.bank.customer.entity.AddressComponent;
import com.bank.customer.entity.CustomerEntity;
import com.bank.customer.entity.OfficialIdEntity;
import com.bank.customer.enums.OfficialIdTypeEnum;
import com.bank.customer.mapper.AddressMapper;
import com.bank.customer.mapper.CustomerMapper;
import com.bank.customer.mapper.MapperFactory;
import com.bank.customer.mapper.OfficialIdMapper;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerMapperTest {

	@Test
	public void testCustomerMapper() {
		// Create mock DTO and Entity instances
		CustomerDTO customerDTO = getCustomerDTO();

		OfficialIdDTO officialIdDTO = getOfficialIdDTO();
		customerDTO.addOfficialId(officialIdDTO);

		CustomerMapper customerMapper = (CustomerMapper) MapperFactory.getMapper(CustomerDTO.class);
		CustomerEntity customerEntity = customerMapper.mapToEntity(customerDTO);

		assertCustomerDTOMatchEntity(customerDTO, customerEntity);

		CustomerDTO mappedDTO = customerMapper.mapToDTO(customerEntity);

		assertCustomerDTOMatchEntity(mappedDTO, customerEntity);
	}

	private void assertCustomerDTOMatchEntity(CustomerDTO customerDTO, CustomerEntity customerEntity) {
		assertEquals(customerDTO.getFirstName(), customerEntity.getFirstName());
		assertEquals(customerDTO.getLastName(), customerEntity.getLastName());
		assertEquals(customerDTO.getEmail(), customerEntity.getEmail());
		assertEquals(customerDTO.getPhoneNumber(), customerEntity.getPhoneNumber());
		assertEquals(customerDTO.getActive(), customerEntity.getActive());
		assertEquals(customerDTO.getBlocked(), customerEntity.getBlocked());
		assertAddressDTOMatchEntity(customerDTO.getAddressDTO(), customerEntity.getAddressComponent());
		assertOfficialIdDTOMatchEntity(customerDTO.getOfficialIDs(), customerEntity.getOfficialIDs());
	}

	private void assertOfficialIdDTOMatchEntity(Set<OfficialIdDTO> officialIdDTOSet, Set<OfficialIdEntity> officialIdEntitySet) {
		List<OfficialIdDTO> officialIdDTOList = officialIdDTOSet.stream().collect(Collectors.toList());
		List<OfficialIdEntity> officialIdEntityList = officialIdEntitySet.stream().collect(Collectors.toList());

		for (int i = 0; i < officialIdDTOList.size(); i++) {
			OfficialIdDTO officialIdDTO = officialIdDTOList.get(i);
			OfficialIdEntity officialIdEntity = officialIdEntityList.get(i);
			assertEquals(officialIdDTO.getType(), officialIdEntity.getType());
			assertEquals(officialIdDTO.getValue(), officialIdEntity.getValue());
			assertEquals(officialIdDTO.getExpiryDate(), officialIdEntity.getExpiryDate());
		}

	}

	private void assertAddressDTOMatchEntity(AddressDTO addressDTO, AddressComponent addressComponent) {
		assertEquals(addressDTO.getStreet(), addressComponent.getStreet());
		assertEquals(addressDTO.getCity(), addressComponent.getCity());
		assertEquals(addressDTO.getZipCode(), addressComponent.getZipCode());
		assertEquals(addressDTO.getBuildingNumber(), addressComponent.getBuildingNumber());
		assertEquals(addressDTO.getCountry(), addressComponent.getCountry());
		assertEquals(addressDTO.getRegion(), addressComponent.getRegion());
	}

	private OfficialIdDTO getOfficialIdDTO() {
		return OfficialIdDTO.builder()
				.type(OfficialIdTypeEnum.NATIONAL_ID)
				.value("Value")
				.expiryDate(new Date(12222000L))
				.build();
	}

	private CustomerDTO getCustomerDTO() {
		return CustomerDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.email("john.doe@example.com")
				.phoneNumber("123456789")
				.active(true)
				.blocked(false)
				.addressDTO(AddressDTO.builder()
						.street("Street")
						.city("City")
						.zipCode("12345")
						.buildingNumber("123")
						.country("Country")
						.region("Region")
						.build())
				.build();
	}
}
