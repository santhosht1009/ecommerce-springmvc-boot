package com.mycompany.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.service.AdminService;



@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminService adminService;
	
	
	@GetMapping
	public String loadHome() {
		return "Admin";
	}
	
	@PostMapping("/login")
	public String login(LoginHelper loginHelper,ModelMap modelMap) {
		
		return adminService.login(loginHelper,modelMap);
	}
	
	@GetMapping("/verifyotp")
	public String verifyOtp(@RequestParam int id,@RequestParam int otp,ModelMap modelMap) {
	return adminService.verifyOtp(id,otp,modelMap);
		
	}
}
