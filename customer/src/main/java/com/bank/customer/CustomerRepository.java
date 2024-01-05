package com.bank.customer;

import com.bank.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

	@Query("SELECT COUNT(c) FROM CustomerEntity c WHERE c.email = :email")
	long countByEmail(@Param("email") String email);

	@Query("SELECT COUNT(c) FROM CustomerEntity c WHERE c.mobileNumber = :mobileNumber")
	long countByMobileNumber(@Param("mobileNumber") String mobileNumber);


}
