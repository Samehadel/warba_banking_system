package com.bank.shared.controller;

import com.bank.shared.dto.BaseDTO;
import com.bank.shared.model.BankResponse;
import com.bank.shared.model.BaseCriteriaFilter;
import com.bank.shared.service.BaseService;
import org.springframework.web.bind.annotation.*;

@RestController
public abstract class BaseController<D extends BaseDTO, C extends BaseCriteriaFilter> {
	protected abstract BaseService<D, C> getService();

	@PostMapping("/create")
	public BankResponse<D> create(@RequestBody D request) throws Exception {
		return getService().create(request);
	}

	@GetMapping("/get/{code}")
	public BankResponse<D> get(@PathVariable("code") String code) throws Exception {
		return getService().get(code);
	}

	@PostMapping("/update")
	public BankResponse<D> update(@RequestBody D request) throws Exception{
		return getService().update(request);
	}

	@PostMapping("/delete/{id}")
	public BankResponse<Void> delete(@PathVariable("id") Long id) throws Exception {
		return getService().delete(id);
	}

}
