package com.bank.customer.dto;

import com.bank.shared.dto.BaseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class CustomerDTO extends BaseDTO {

	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private AddressDTO addressDTO;
	private Set<OfficialIdDTO> officialIDs;
	private Boolean active;
	private Boolean blocked;

	public void addOfficialId(OfficialIdDTO officialIdDTO) {
		if (officialIDs == null) {
			officialIDs = new HashSet<>();
		}
		officialIDs.add(officialIdDTO);
	}

}
