package com.bank.shared.mapper;

public interface GlobalMapper <E, D> {
	E mapToEntity(D dto);
	D mapToDTO(E entity);
}
