package com.banking.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banking.beans.Account;
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
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping("/")
	public String showHome(Model m, HttpServletRequest request) {
		
		List<String> viewList = new ArrayList<String>(Arrays.asList("home"));
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request, m);
		if(l==null) 
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		
		List<Account> accountList = accountService.getAccountsList(l);
		
		// get details for the logged in user
		m.addAttribute("customer", customerService.getCustomer(l));
		m.addAttribute("categoriesList", utilityService.getCategoryList());
		m.addAttribute("accountsList", accountList);
		
		List<Transaction> tranactionList = transactionService.getTransactionListByCustomerId(l.getCustomerId(), accountList);
		
		if(tranactionList.size()>0) {
			m.addAttribute("tranactionsList", tranactionList);
			viewList.add("showTransactions");
		}
		
		return viewService.model(m).views(viewList);
	}
	
	@RequestMapping("/categories/{categoryName}")
	public String showHome(Model m, HttpServletRequest request, @PathVariable String categoryName) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request, m);
		if(l==null) 
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		
		String category = String.join(" ", categoryName.split("-"));
		
		m.addAttribute("customer", customerService.getCustomer(l));
		m.addAttribute("categoryName", category);
		m.addAttribute("optionsList", utilityService.getCategoryOptionsList(category));
		m.addAttribute("accountsList", accountService.getAccountsList(l));
		
		return viewService.model(m).view("transaction");
	}
	
	@RequestMapping("/transfer/self")
	public String showTransferSelf(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request, m);
		if(l==null) 
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		
		m.addAttribute("customer", customerService.getCustomer(l));
		m.addAttribute("accountsList", accountService.getAccountsList(l));
		
		return viewService.model(m).view("selfTransferForm");
	}
	
	@RequestMapping("/transfer/by-email")
	public String showTransferByEmail(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request, m);
		if(l==null) 
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		
		m.addAttribute("customer", customerService.getCustomer(l));
		m.addAttribute("accountsList", accountService.getAccountsList(l));
		
		return viewService.model(m).view("emailTransferForm");
	}

}
