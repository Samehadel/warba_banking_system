package com.bank.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

@Data
@MappedSuperclass
public class BaseEntity {

	@CreationTimestamp
	@Column(name = "CREATION_DATE", nullable = false)
	private Date creation_date;

	@UpdateTimestamp
	@Column(name = "UPDATE_DATE")
	private Date update_date;

}
