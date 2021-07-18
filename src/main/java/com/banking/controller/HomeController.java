package com.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banking.beans.Customer;
import com.banking.beans.Login;
import com.banking.beans.Register;

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
	
	@RequestMapping("/")
	public String showHome(Model m) {
		m.addAttribute("login", new Login());
		m.addAttribute("register", new Register());
		return "index";
	}

}
