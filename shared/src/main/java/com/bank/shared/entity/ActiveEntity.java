package com.bank.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class ActiveEntity extends BaseEntity {

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;

	@Column(name = "IS_BLOCKED", nullable = false)
	private Boolean blocked;
}
