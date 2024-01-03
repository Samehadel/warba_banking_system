package com.bank.customer.entity;

import com.bank.customer.enums.OfficialIdTypeEnum;
import com.bank.shared.entity.BaseEntity;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "OFFICIAL_ID")
public class OfficialIdEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFFICIAL_ID_SEQ")
	@SequenceGenerator(name = "OFFICIAL_ID_SEQ", sequenceName = "OFFICIAL_ID_SEQ", allocationSize = 1)
	@Column(name = "OFFICIAL_ID")
	private Long id;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "TYPE")
	private OfficialIdTypeEnum type;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "EXPIRY_DATE")
	private Date expiryDate;

	public OfficialIdTypeEnum getType() {
		return type;
	}

	public void setType(OfficialIdTypeEnum type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}
