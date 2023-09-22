package com.mycompany.ecommerce.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.mycompany.ecommerce.dao.AdminDao;
import com.mycompany.ecommerce.dto.Admin;
import com.mycompany.ecommerce.dto.Customer;
import com.mycompany.ecommerce.helper.LoginHelper;
import com.mycompany.ecommerce.helper.MailHelper;

import jakarta.servlet.http.HttpSession;

@Service
public class AdminService {
@Autowired
	AdminDao adminDao;
@Autowired
MailHelper mailHelper;
	
	
	public String login(LoginHelper loginHelper, ModelMap modelMap) {
		
		Admin admin=adminDao.fetchByEmail(loginHelper.getEmail());
		if(admin!=null) {
		if(loginHelper.getPassword().equals(admin.getPassword()))
		{	
			int otp = new Random().nextInt(100000, 999999);
			admin.setOtp(otp);
			adminDao.save(admin);
			mailHelper.sendOtp(admin);
			modelMap.put("id", admin.getId());
			modelMap.put("email", admin.getEmail());
			return "VerifyOtp2";
			}else {
			modelMap.put("neg", "Password is not matching");
			return "Admin";
		}
		
		
		}else {
			modelMap.put("neg", "Email is not Present");
			return "Admin";
		}

	}


	public String verifyOtp(int id, int otp,String email, ModelMap modelMap,HttpSession httpSession) {
		Admin admin=adminDao.findById(id);
		
		if(admin==null)
		{modelMap.put("neg", "Something went wrong");
			return "Main";
			}
		else
		{
				if(admin.getOtp()==otp)
				{
				httpSession.setAttribute("admin", email);
					return "AdminHome";
				}else
				{
					modelMap.put("neg", "Enter Otp is Not Matching");
					modelMap.put("id", id);
					return "VerifyOtp2";
				}
				
		}
	}

}
