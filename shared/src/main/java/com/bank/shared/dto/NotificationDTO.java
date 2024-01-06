package com.bank.shared.dto;

import com.bank.shared.enums.NotificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO extends BaseDTO {
	private String message;
	private NotificationTypeEnum type;
	private String customerCode;
	private String receiverIdentifier;
}
