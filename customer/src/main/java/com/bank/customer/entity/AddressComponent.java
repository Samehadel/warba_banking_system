package com.bank.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AddressComponent {
	@Column(name = "ZIP_CODE")
	private String zipCode;

	@Column(name = "BUILDING_NUMBER")
	private String buildingNumber;

	@Column(name = "STREET")
	private String street;

	@Column(name = "CITY")
	private String city;

	@Column(name = "Region")
	private String region;

	@Column(name = "COUNTRY")
	private String country;
}
