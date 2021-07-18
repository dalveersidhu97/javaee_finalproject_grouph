package com.banking.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banking.beans.Login;
import com.banking.beans.Register;
import com.banking.service.CustomerService;
import com.banking.service.ViewService;

/**
 * 
 * @author Group-H
 * @date 12 July, 2021
 * @description Controller class for root routes such as '/', '/welcome',
 *              '/logout'. It checks if the user is logged In and serves the
 *              profile page or login page accordingly.
 */

@Controller
public class HomeController {
	
	@Autowired
	ViewService viewService;
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping("/")
	public String showHome(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		if(customerService.isLoggedIn(request))
			return viewService.model(m).view("home");
		
		// else show login page
		m.addAttribute("login", new Login());
		m.addAttribute("register", new Register());
		
		return viewService.model(m).views(Arrays.asList("login", "signup"));
	}

}
