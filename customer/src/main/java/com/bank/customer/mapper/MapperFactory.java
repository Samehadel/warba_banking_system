package com.bank.customer.mapper;

import com.bank.customer.dto.AddressDTO;
import com.bank.customer.dto.CustomerDTO;
import com.bank.customer.dto.OfficialIdDTO;
import com.bank.customer.entity.AddressComponent;
import com.bank.customer.entity.CustomerEntity;
import com.bank.customer.entity.OfficialIdEntity;

public class MapperFactory {
	public static GlobalMapper getMapper(Class<?> aClass) {
		if(aClass.equals(CustomerEntity.class) || aClass.equals(CustomerDTO.class)) {
			return new CustomerMapper();
		} else if (aClass.equals(AddressComponent.class) || aClass.equals(AddressDTO.class)) {
			return new AddressMapper();
		} else if (aClass.equals(OfficialIdEntity.class) || aClass.equals(OfficialIdDTO.class)) {
			return new OfficialIdMapper();
		}
		return null;
	}
}
