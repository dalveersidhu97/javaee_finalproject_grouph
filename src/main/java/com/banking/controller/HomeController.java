package com.banking.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banking.beans.CategoryOption;
import com.banking.beans.Login;
import com.banking.beans.Register;
import com.banking.beans.UtilityCategory;
import com.banking.service.AccountService;
import com.banking.service.CustomerService;
import com.banking.service.UtilityService;
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
	UtilityService utilityService;
	@Autowired
	CustomerService customerService;
	@Autowired
	AccountService accountService;
	
	@RequestMapping("/")
	public String showHome(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request);
		if(l==null) {
			// else show login page
			m.addAttribute("login", new Login());
			m.addAttribute("register", new Register());
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		}
		
		m.addAttribute("categoriesList", utilityService.getCategoryList());
		m.addAttribute("accountsList", accountService.getAccountsList(l));
		return viewService.model(m).view("home");
	}
	
	@RequestMapping("/categories/{categoryName}")
	public String showHome(Model m, HttpServletRequest request, @PathVariable String categoryName) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request);
		if(l==null) {
			// else show login page
			m.addAttribute("login", new Login());
			m.addAttribute("register", new Register());
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		}
		
		String category = String.join(" ", categoryName.split("-"));
		m.addAttribute("categoryName", category);
		m.addAttribute("optionsList", utilityService.getCategoryOptionsList(category));
		
		return viewService.model(m).view("transaction");
		

	}

}
