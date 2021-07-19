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
import com.banking.beans.Transaction;
import com.banking.beans.UtilityCategory;
import com.banking.service.AccountService;
import com.banking.service.CustomerService;
import com.banking.service.TransactionService;
import com.banking.service.UtilityService;
import com.banking.service.ViewService;

@Controller
public class TransactionController {
	
	@Autowired
	ViewService viewService;
	@Autowired
	UtilityService utilityService;
	@Autowired
	CustomerService customerService;
	@Autowired
	AccountService accountService;
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping("/transactionProcess")
	public String processTransaction(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request);
		if(l==null) {
			// else show login page
			m.addAttribute("login", new Login());
			m.addAttribute("register", new Register());
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		}
		
		// create pending transaction
		Transaction transaction = transactionService.validateTransaction(request, l);
		
		if(transaction == null)
			System.out.println("null");
		else
			System.out.println("not null");
			
		// store data in session
		request.getSession().setAttribute("transaction", transaction);
		// show confirm transaction page
		
		return viewService.model(m).view("home");
	}
	
	@RequestMapping("/confirmTransaction")
	public String confirmTransaction(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request);
		if(l==null) {
			// else show login page
			m.addAttribute("login", new Login());
			m.addAttribute("register", new Register());
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		}
		
		// commit transaction
		
		// show success or fail

		return viewService.model(m).view("home");
	}
	
	

}
