package com.mycompany.ecommerce.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.ecommerce.dto.Merchant;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.service.MerchantService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("merchant")
public class MerchantController {
	@Autowired
	Merchant merchant;
	@Autowired
	MerchantService  merchantService;
	@Autowired
	Product  product;
	@GetMapping("")
	public String loadHome() {
		return "Merchant";
	}

	@GetMapping("/signup")
	public String loadSignup(ModelMap map) {
		map.put("merchant", merchant);
		return "MerchantSignup";
	}

	@GetMapping("/verifyotp")
	public String verifyOtp(@RequestParam int userid,@RequestParam int otp,ModelMap modelMap) {
	return merchantService.verifyOtp(userid,otp,modelMap);
		
	}
	
	@PostMapping("/signup")
	public String signup(@Valid Merchant merchant,BindingResult bindingResult, ModelMap modelMap) {
		if(bindingResult.hasErrors())
		{
			return "MerchantSignup";}
		else
			{return merchantService.signup(merchant, modelMap);}
	}
	
	@PostMapping("/login")
	public String login(LoginHelper loginHelper,ModelMap modelMap, HttpSession session) {
		
		return merchantService.login(loginHelper,modelMap,session);
	}
	

	
	
	@GetMapping("/addproduct")
	public String addProduct(ModelMap map, HttpSession session) {
		Merchant merchant = (Merchant) session.getAttribute("merchant");
		if (merchant != null) {
			map.put("product", product);
			return "AddProduct";
		} else {
			map.put("neg", "Invalid Session");
			return "Main";
		}
	}

	@PostMapping("/add-product")
	public String addProduct(@Valid Product product, BindingResult result, @RequestParam MultipartFile pic,
			ModelMap map, HttpSession session) throws IOException {
		Merchant merchant = (Merchant) session.getAttribute("merchant");
		if (merchant != null) {
			if (result.hasErrors())
				return "AddProduct";
			else {
				 return merchantService.addProduct(product,pic,map,merchant,session);
			}
		} else {
			map.put("neg", "Invalid Session");
			return "Main";
		}
	}
	
	
}
