package com.mycompany.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.ecommerce.dto.Customer;
import com.mycompany.ecommerce.dto.Merchant;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.service.CustomerService;
import com.mycompany.ecommerce.service.MerchantService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	Customer customer;
	@Autowired
	CustomerService customerService;
	
	@GetMapping
	public String loadHome() {
		return "Customer";
	}

	@GetMapping("/signup")
	public String loadSignup(ModelMap map) {
		map.put("customer", customer);
		return "CustomerSignup";
	}
	
	@GetMapping("/verifyotp")
	public String verifyOtp(@RequestParam int userid,@RequestParam int otp,ModelMap modelMap) {
	return customerService.verifyOtp(userid,otp,modelMap);
		
	}
	

	@PostMapping("/signup")
	public String signup(@Valid Customer customer,BindingResult result, ModelMap modelMap) {
		if(result.hasErrors())
			return "CustomerSignup";
		else
			return customerService.signup(customer, modelMap);
	}
	
	@PostMapping("/login")
	public String login(LoginHelper loginHelper,ModelMap modelMap) {
		
		return customerService.login(loginHelper,modelMap);
	}
	
}
