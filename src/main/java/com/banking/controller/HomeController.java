package com.banking.controller;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banking.beans.Login;
import com.banking.beans.Register;
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
	
	@RequestMapping("/")
	public String showHome(Model m) {
		
		m.addAttribute("login", new Login());
		m.addAttribute("register", new Register());
		
		return viewService.model(m).views(Arrays.asList("login.jsp", "signup.jsp"));
	}
	
	
	@RequestMapping("/login")
	public String showLogin(Model m) {
		
		m.addAttribute("login", new Login());
		
		return viewService.model(m).view("login.jsp");
	}

}
