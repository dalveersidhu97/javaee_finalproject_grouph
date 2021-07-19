package com.banking.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		return viewService.model(m).view("signup");
	}
	
	@RequestMapping(value="/registerProcess", method=RequestMethod.POST)
	public String processRegisteration(@Valid @ModelAttribute Register register, BindingResult br, Model m) {
		
		String view = "signup";
		String errorMessage = "";
		
		if(br.hasErrors()) 
			return viewService.model(m).view(view);
		
		
		int registerResutl = customerService.register(register);

		
		if(registerResutl == CustomerDao.EMAIL_ALREADY_EXISTS)
			errorMessage = "This email has been already taken!";
		
		if(registerResutl == CustomerDao.USERNAME_ALREADY_EXISTS)
			errorMessage = "Username already taken!";
		
		if(registerResutl == CustomerDao.USER_ALREADY_EXISTS) 
			errorMessage = "Customer already exists, please check your input!";
		
		if(registerResutl == 1) {
			m.addAttribute("message", "Registration successfull, You can login now!");
			m.addAttribute("login", new Login());
			view = "login";
		}
		
		m.addAttribute("errorMessage", errorMessage);
		
		return viewService.model(m).view(view);
	}

	
}