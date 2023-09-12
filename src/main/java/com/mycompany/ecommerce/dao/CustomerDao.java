package com.mycompany.ecommerce.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.ecommerce.dto.Customer;
import com.mycompany.ecommerce.dto.Merchant;
import com.mycompany.ecommerce.repository.CustomerRepository;
import com.mycompany.ecommerce.repository.MerchantRepository;
@Repository
public class CustomerDao {

	@Autowired
	CustomerRepository customerRepository;
	
	public Customer fetchByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public Customer fetchByMobile(long mobile) {
		return customerRepository.findByMobile(mobile);
	}

	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	public Customer findById(int userid) {
		Optional<Customer> opt=customerRepository.findById(userid);
		if(opt.isPresent())
		return opt.get();
		else
			return null;
	}
	
	
}
