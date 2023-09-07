package com.mycompany.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.ecommerce.dto.Customer;


import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	Customer customer;
	
	@GetMapping
	public String loadHome() {
		return "Customer";
	}

	@GetMapping("/signup")
	public String loadSignup(ModelMap map) {
		map.put("customer", customer);
		return "CustomerSignup";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid Customer customer,BindingResult result) {
		if(result.hasErrors())
			return "CustomerSignup";
		else
		return customer.toString();
	}
}
