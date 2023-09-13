package com.mycompany.ecommerce.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.mycompany.ecommerce.dao.CustomerDao;
import com.mycompany.ecommerce.dao.MerchantDao;
import com.mycompany.ecommerce.dto.Customer;
import com.mycompany.ecommerce.dto.Merchant;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.helper.MailHelper;
@Service
public class CustomerService {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	MailHelper mailHelper;
	
	public String signup(Customer customer, ModelMap modelMap) {
		Customer customer1 = customerDao.fetchByEmail(customer.getEmail());
		Customer customer2 = customerDao.fetchByMobile(customer.getMobile());
		if (customer1 == null && customer2 == null) {
			int otp = new Random().nextInt(100000, 999999);
			customer.setOtp(otp);
			customerDao.save(customer);
			mailHelper.sendOtp(customer);
			modelMap.put("id", customer.getId());
			return "VerifyOtp1";
		} else {
			if (customer1 != null) {
				if (customer1.isStatus()) {
					modelMap.put("neg", "Email Already Exists");
					return "CustomerSignup";
				} else {
					if (customer2 != null) {
						mailHelper.sendOtp(customer1);
						modelMap.put("id", customer1.getId());
						return "VerifyOtp1";
					} else {
						modelMap.put("neg", "Same Email with Different Number Exists");
						return "CustomerSignup";
					}
				}
			} else {
				modelMap.put("neg", "Phone Number Already Exists");
				return "CustomerSignup";
			}
		}
	}



	public void save(Customer customer) {
		customerDao.save(customer);
		
	}

	public String verifyOtp(int userid, int otp, ModelMap modelMap) {

Customer customer=customerDao.findById(userid);
		
if(customer==null)
{modelMap.put("neg", "Something went wrong");
	return "Main";
	}
else
{
		if(customer.getOtp()==otp)
		{
			customer.setStatus(true);
			customerDao.save(customer);
			modelMap.put("pos", "Email verified successfully");
			return "Customer";
		}else
		{
			modelMap.put("neg", "Enter Otp is Not Matching");
			modelMap.put("id", userid);
			return "VerifyOtp1";
		}
		
}
		
	}



	public String login(LoginHelper loginHelper,ModelMap modelMap) {
	Customer customer=customerDao.fetchByEmail(loginHelper.getEmail());
	if(customer!=null) {
		if(customer.isStatus())
		{
		
		if(loginHelper.getPassword().equals(customer.getPassword())) {
			return "CustomerHome";
		}else {
			modelMap.put("neg", "Password is not matching");
			return "Customer";
		}}else
		{
			modelMap.put("neg", "Account is Not Verified! Otp Sent to Your Email to Verify");
			mailHelper.sendOtp(customer);
			modelMap.put("id", customer.getId());
			return "VerifyOtp1";
		}
			
	
	
	}else {
		modelMap.put("neg", "Email is not Present");
		return "Customer";
		
	}
		
	}
	
}
