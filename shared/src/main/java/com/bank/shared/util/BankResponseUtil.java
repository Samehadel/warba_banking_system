package com.bank.shared.util;

import com.bank.shared.model.BankResponse;
import com.bank.shared.model.StatusEnum;

public class BankResponseUtil {

	public static BankResponse getSuccessResponse() {
		return buildBankResponse(StatusEnum.SUCCESS, null, null, null);
	}

	public static BankResponse getSuccessResponse(Object data) {
		return buildBankResponse(StatusEnum.SUCCESS, null, null, data);
	}

	public static BankResponse getFailedResponse(String errorCode, String errorMessage) {
		return buildBankResponse(StatusEnum.FAILED, errorMessage, errorCode, null);
	}

	public static BankResponse getFailedResponse(Object data) {
		return buildBankResponse(StatusEnum.FAILED, null, null, data);
	}

	public static BankResponse getFailedResponse(Object data, String errorMessage) {
		return buildBankResponse(StatusEnum.FAILED, errorMessage, null, data);
	}

	public static BankResponse getFailedResponse(Object data, String errorMessage, String errorCode) {
		return buildBankResponse(StatusEnum.FAILED, errorMessage, errorCode, data);
	}

	private static BankResponse buildBankResponse(StatusEnum status, String errorMessage, String errorCode, Object data) {
		BankResponse response = BankResponse
				.builder()
				.status(status)
				.errorMessage(errorMessage)
				.errorCode(errorCode)
				.data(data)
				.build();
		return response;
	}

}
