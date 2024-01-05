package com.bank.shared.dto;

import com.bank.shared.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO extends BaseDTO {
	private String zipCode;
	private String buildingNumber;
	private String street;
	private String city;
	private String region;
	private String country;
}
