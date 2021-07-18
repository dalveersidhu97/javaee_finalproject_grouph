package com.banking.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.banking.beans.Login;
import com.banking.beans.Register;
import com.banking.dao.CustomerDao;
import com.banking.service.CustomerService;
import com.banking.service.ViewService;

@Controller
public class RegisterController {
	
	@Autowired
	ViewService viewService;
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("/signup")
	public String showSignup(Model m) {
		
		m.addAttribute("register", new Register());
		return viewService.model(m).view("signup.jsp");
	}
	
	@RequestMapping(value="/registerProcess", method=RequestMethod.POST)
	public String processRegisteration(@ModelAttribute Register register, Model m) {
		
		int registerResutl = customerService.register(register);
		String view = "signup";
		String errorMessage = "";
		
		if(registerResutl == CustomerDao.EMAIL_ALREADY_EXISTS) {
			errorMessage = "This email has been already taken!";
		}
		if(registerResutl == CustomerDao.USERNAME_ALREADY_EXISTS) {
			errorMessage = "Username already taken!";
		}
		if(registerResutl == CustomerDao.USER_ALREADY_EXISTS) {
			errorMessage = "Customer already exists, please check your input!";
		}
		
		if(registerResutl == 1) {
			view = "home";
		}
		
		m.addAttribute("errorMessage", errorMessage);
		
		return viewService.model(m).view(view);
	}

	
}