package com.mycompany.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.ecommerce.dto.Admin;
import com.mycompany.ecommerce.dto.Customer;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	public Admin findByEmail(String email);


}
