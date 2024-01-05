package com.bank.shared.controller;

import com.bank.shared.dto.BaseDTO;
import com.bank.shared.model.BankResponse;
import com.bank.shared.service.BaseService;
import org.springframework.web.bind.annotation.*;

@RestController
public abstract class BaseController<D extends BaseDTO> {
	protected abstract BaseService<D> getService();

	@PostMapping("/create")
	public BankResponse<D> create(@RequestBody D request) {
		return getService().create(request);
	}

	@GetMapping("/get/{code}")
	public BankResponse<D> get(@PathVariable("code") String code) {
		return getService().get(code);
	}

	@PutMapping("/block/{code}")
	public BankResponse<Void> block(@PathVariable("code") String code) {
		return getService().block(code);
	}

}
