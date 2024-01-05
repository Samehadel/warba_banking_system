package com.bank.account.clients;

import com.bank.shared.dto.CustomerDTO;
import com.bank.shared.model.BankResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CUSTOMER-SERVICE", path = "customer-service/api")
public interface CustomerServiceClient {

	@GetMapping("/get/{code}")
	BankResponse<CustomerDTO> get(@PathVariable("code") String code);
}
