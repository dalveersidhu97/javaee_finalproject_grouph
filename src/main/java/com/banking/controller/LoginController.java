package com.banking.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.banking.beans.Login;
import com.banking.service.CustomerService;
import com.banking.service.ViewService;

@Controller
public class LoginController {
	
	@Autowired
	ViewService viewService;
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("/login")
	public String showLogin(Model m, HttpServletRequest request) {
		
		if(customerService.isLoggedIn(request))
			// redirect to home if already logged in
			return "redirect:/";
		
		m.addAttribute("login", new Login());
		return viewService.model(m).view("login");
	}
	
	@RequestMapping("/logout")
	public String logout(Model m, HttpServletResponse response) {
		// logout and redirect to home
		response.addCookie(new Cookie("username", ""));
		response.addCookie(new Cookie("password", ""));
		return "redirect:/";
		
	}
	
	@RequestMapping(value="/loginProcess", method=RequestMethod.POST)
	public String processLogin(@Valid @ModelAttribute Login l, BindingResult br, Model m, HttpServletResponse response, HttpServletRequest request) {
		
		String view = "login";
		
		if(customerService.isLoggedIn(request))
			// redirect to home if already logged in
			return "redirect:/";
		
		if(br.hasErrors()) 
			return viewService.model(m).view(view);
		
		// validate user name and password
		Login login = customerService.validateLogin(l);
		
		if(login == null) {
			m.addAttribute("errorMessage", "Invalid username of password");
			return viewService.model(m).view(view);
		}
		
		// set cookies
		
		response.addCookie(new Cookie("username", login.getUsername()));
		response.addCookie(new Cookie("password", login.getPassword()));
		
		// redirect to home if login is success
		return "redirect:/";
	
	}

	
}