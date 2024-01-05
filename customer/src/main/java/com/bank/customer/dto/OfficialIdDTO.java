package com.bank.customer.dto;

import com.bank.customer.enums.OfficialIdTypeEnum;
import com.bank.shared.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficialIdDTO extends BaseDTO {
	private OfficialIdTypeEnum type;
	private String value;
	private Date expiryDate;
}
