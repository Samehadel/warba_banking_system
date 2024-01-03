package com.bank.customer.mapper;

import com.bank.customer.dto.AddressDTO;
import com.bank.customer.dto.CustomerDTO;
import com.bank.customer.dto.OfficialIdDTO;
import com.bank.customer.entity.AddressComponent;
import com.bank.customer.entity.CustomerEntity;
import com.bank.customer.entity.OfficialIdEntity;

public class MapperFactory {
	public static GlobalMapper getMapper(Class<?> aClass) {
		if(aClass.getSimpleName().equals(CustomerEntity.class) || aClass.getSimpleName().equals(CustomerDTO.class)) {
			return new CustomerMapper();
		} else if (aClass.getSimpleName().equals(AddressComponent.class) || aClass.getSimpleName().equals(AddressDTO.class)) {
			return new AddressMapper();
		} else if (aClass.getSimpleName().equals(OfficialIdEntity.class) || aClass.getSimpleName().equals(OfficialIdDTO.class)) {
			return new OfficialIdMapper();
		}
		return null;
	}
}
