package com.mycompany.ecommerce.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mycompany.ecommerce.dao.MerchantDao;
import com.mycompany.ecommerce.dto.Customer;
import com.mycompany.ecommerce.dto.Merchant;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.helper.MailHelper;

@Service
public class MerchantService {

@Autowired
MerchantDao merchantDao;

@Autowired
MailHelper mailHelper;

public String signup(Merchant merchant, ModelMap modelMap) {
	Merchant merchant1 = merchantDao.fetchByEmail(merchant.getEmail());
	Merchant merchant2 = merchantDao.fetchByMobile(merchant.getMobile());
	if (merchant1 == null && merchant2 == null) {
		int otp = new Random().nextInt(100000, 999999);
		merchant.setOtp(otp);
		merchantDao.save(merchant);
		mailHelper.sendOtp(merchant);
		modelMap.put("id", merchant.getId());
		return "VerifyOtp1";
	} else {
		if (merchant1 != null) {
			if (merchant1.isStatus()) {
				modelMap.put("neg", "Email Already Exists");
				return "MerchantSignup";
			} else {
				if (merchant2 != null) {
					mailHelper.sendOtp(merchant1);
					modelMap.put("id", merchant1.getId());
					return "VerifyOtp";
				} else {
					modelMap.put("neg", "Same Email with Different Number Exists");
					return "MerchantSignup";
				}
			}
		} else {
			modelMap.put("neg", "Phone Number Already Exists");
			return "MerchantSignup";
		}
	}
}

public String verifyOtp(int userid, int otp, ModelMap modelMap) {
	Merchant merchant=merchantDao.findById(userid);
	
	if(merchant==null)
	{modelMap.put("neg", "Something went wrong");
		return "Main";
		}
	else
	{
			if(merchant.getOtp()==otp)
			{
				merchant.setStatus(true);
				merchantDao.save(merchant);
				modelMap.put("pos", "Email verified successfully");
				return "Merchant";
			}else
			{
				modelMap.put("neg", "Enter Otp is Not Matching");
				modelMap.put("id", userid);
				return "VerifyOtp";
			}
			
	}
}


public String login(LoginHelper loginHelper,ModelMap modelMap) {
Merchant merchant=merchantDao.fetchByEmail(loginHelper.getEmail());
if(merchant!=null) {
	if(loginHelper.getPassword().equals(merchant.getPassword())) {
		return "Home";
	}else {
		modelMap.put("neg", "Password is not matching");
		return "Merchant";
	}
		
}else {
	modelMap.put("neg", "Email is not Present");
	return "Merchant";
	
}
	
}

}
