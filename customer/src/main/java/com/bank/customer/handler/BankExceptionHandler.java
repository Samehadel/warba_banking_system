package com.bank.customer.handler;

import com.bank.shared.exceptions.DataNotFoundException;
import com.bank.shared.exceptions.DataUniquenessException;
import com.bank.shared.exceptions.InvalidDataException;
import com.bank.shared.exceptions.MissingRequiredFieldsException;
import com.bank.shared.model.BankResponse;
import com.bank.shared.model.ErrorCodes;
import com.bank.shared.util.BankResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class BankExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<BankResponse> handleException(HttpServletRequest request, Exception e) {
		log.error("Error occurred due to: {}", e.getMessage(), e);
		BankResponse<Object> failedResponse = BankResponseUtil.getFailedResponse(ErrorCodes.SERVER_ERROR, e.getMessage());
		return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MissingRequiredFieldsException.class)
	public ResponseEntity<BankResponse> handleMissingRequiredFieldsException(HttpServletRequest request, MissingRequiredFieldsException e) {
		log.error("Error occurred due to: {}", e.getMessage(), e);
		BankResponse<Object> failedResponse = BankResponseUtil.getFailedResponse(ErrorCodes.MISSING_REQUIRED_FIELDS_ERROR, e.getMessage());
		return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DataUniquenessException.class)
	public ResponseEntity<BankResponse> handleDataUniquenessException(HttpServletRequest request, DataUniquenessException e) {
		log.error("Error occurred due to: {}", e.getMessage(), e);
		BankResponse<Object> failedResponse = BankResponseUtil.getFailedResponse(ErrorCodes.DATA_UNIQUENESS_ERROR, e.getMessage());
		return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<BankResponse> handleInvalidDataException(HttpServletRequest request, InvalidDataException e) {
		log.error("Error occurred due to: {}", e.getMessage(), e);
		BankResponse<Object> failedResponse = BankResponseUtil.getFailedResponse(ErrorCodes.INVALID_DATA_ERROR, e.getMessage());
		return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<BankResponse> handleDataNotFoundException(HttpServletRequest request, DataNotFoundException e) {
		log.error("Error occurred due to: {}", e.getMessage(), e);
		BankResponse<Object> failedResponse = BankResponseUtil.getFailedResponse(ErrorCodes.DATA_NOT_FOUND_ERROR, e.getMessage());
		return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
