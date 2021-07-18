package com.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.banking.beans.Login;
import com.banking.beans.Register;
import com.banking.service.CustomerService;

@Controller
public class RegisterController {
	
	CustomerService customerService;
	
	@RequestMapping("/register")
	public String showRegister(Model m) {
		
		m.addAttribute("login", new Login());
		m.addAttribute("register", new Register());
		
		
		
		return "register";
	}
	
	@RequestMapping(value="/registerProcess", method=RequestMethod.POST)
	public String processRegisteration(@ModelAttribute Register register, Model m) {
		
		customerService.register(register);
		
		m.addAttribute("register", new Register());
		return "register";
	}

	
}