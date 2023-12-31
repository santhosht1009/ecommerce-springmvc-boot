package com.mycompany.ecommerce.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.ecommerce.dao.MerchantDao;
import com.mycompany.ecommerce.dto.Customer;
import com.mycompany.ecommerce.dto.Merchant;
import com.mycompany.ecommerce.dto.Product;
import com.mycompany.ecommerce.helper.AES;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.helper.MailHelper;

import jakarta.servlet.http.HttpSession;

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
		merchant.setPassword(AES.encrypt(merchant.getPassword(), "123"));
		merchantDao.save(merchant);
		mailHelper.sendOtp(merchant);
		modelMap.put("id", merchant.getId());
		return "VerifyOtp";
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
	System.out.println(merchant);
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


public String login(LoginHelper loginHelper,ModelMap modelMap, HttpSession session) {
Merchant merchant=merchantDao.fetchByEmail(loginHelper.getEmail());
if (merchant == null) {
	modelMap.put("neg", "Incorrect Email");
	return "Merchant";
} else {
	if (AES.decrypt(merchant.getPassword(), "123").equals(loginHelper.getPassword())) {
		if (merchant.isStatus()) {
			session.setMaxInactiveInterval(150);
			session.setAttribute("merchant", merchant);
			modelMap.put("pos", "Login Success");
			return "MerchantHome";
		} else {
			modelMap.put("neg", "Verify Your OTP First");
			return "Merchant";
		}
	} else {
		modelMap.put("neg", "Incorrect Password");
		return "Merchant";
	}
}
	
}


public String addProduct(Product product, MultipartFile pic, ModelMap map, Merchant merchant,HttpSession session) throws IOException {
	byte[] picture = new byte[pic.getInputStream().available()];
	pic.getInputStream().read(picture);

	product.setPicture(picture);
	List<Product> list = merchant.getProducts();

	if (list == null)
		list = new ArrayList<Product>();

	list.add(product);
	merchant.setProducts(list);

	merchantDao.save(merchant);
	session.setMaxInactiveInterval(150);
	session.setAttribute("merchant", merchant);
	map.put("pos", "Product Added Success");
	return "MerchantHome";
}

}
