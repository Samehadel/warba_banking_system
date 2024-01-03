package com.bank.customer.entity;

import com.bank.shared.entity.ActiveEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity extends ActiveEntity {

	@Id
	@Column(name = "CUSTOMER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_ID_SEQ")
	@SequenceGenerator(name = "CUSTOMER_ID_SEQ", sequenceName = "CUSTOMER_ID_SEQ", allocationSize = 1)
	private Long customerId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL", unique = true, nullable = false)
	private String email;

	@Column(name = "PHONE_NUMBER", unique = true, nullable = false)
	private String phoneNumber;

	@Column(name = "ADDRESS")
	private AddressComponent addressComponent;

	@OneToMany
	@JoinColumn(name = "CUSTOMER_ID")
	private Set<OfficialIdEntity> officialIDs;


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public AddressComponent getAddressComponent() {
		return addressComponent;
	}

	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}

	public void addOfficialId(OfficialIdEntity officialId) {
		if(officialIDs == null) {
			officialIDs = new HashSet<>();
		}
		officialIDs.add(officialId);
	}

	public Set<OfficialIdEntity> getOfficialIDs() {
		return officialIDs;
	}
}
