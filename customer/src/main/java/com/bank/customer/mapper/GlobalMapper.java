package com.bank.customer.mapper;

public interface GlobalMapper <E, D> {
	E mapToEntity(D dto);
	D mapToDTO(E entity);
}
