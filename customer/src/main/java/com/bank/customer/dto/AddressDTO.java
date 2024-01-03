package com.bank.customer.dto;

import com.bank.shared.dto.BaseDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddressDTO extends BaseDTO {
	private String zipCode;
	private String buildingNumber;
	private String street;
	private String city;
	private String region;
	private String country;
}
