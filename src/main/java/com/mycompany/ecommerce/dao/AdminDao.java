package com.mycompany.ecommerce.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.ecommerce.dto.Admin;
import com.mycompany.ecommerce.dto.Customer;
import com.mycompany.ecommerce.repository.AdminRepository;

@Repository
public class AdminDao {
@Autowired
	AdminRepository adminRepository;
	
	public Admin fetchByEmail(String email) {
		return adminRepository.findByEmail(email);
	}

	public Admin save(Admin admin) {
		return adminRepository.save(admin);
		
	}
	
	public Admin findById(int id) {
		Optional<Admin> opt=adminRepository.findById(id);
		if(opt.isPresent())
		return opt.get();
		else
			return null;
	}
	
}
