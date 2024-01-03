package com.bank.customer.dto;

import com.bank.customer.enums.OfficialIdTypeEnum;
import com.bank.shared.dto.BaseDTO;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@Data
public class OfficialIdDTO extends BaseDTO {
	private OfficialIdTypeEnum type;
	private String value;
	private Date expiryDate;
}
