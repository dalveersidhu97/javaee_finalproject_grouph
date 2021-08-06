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
 * @description Controller class for root routes such as '/', '/welcome'. It checks if the user is logged In and serves the
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
		
		Login l = loggedIn(m, request);
		// show home if the user is logged in
		if (l==null) return "redirect:/login";
		
		// get customer accounts list
		List<Account> accountList = accountService.getAccountsList(l);
		m.addAttribute("accountsList", accountList);
		
		// get transactions list
		List<Transaction> tranactionList = transactionService.getTransactionListByCustomerId(l.getCustomerId(), accountList);
		if(tranactionList.size()>0) {
			m.addAttribute("tranactionsList", tranactionList);
			viewList.add("showTransactions");
		}
		
		return viewService.model(m).views(viewList);
	}
	
	@RequestMapping("/categories/{categoryName}")
	public String showHome(Model m, HttpServletRequest request, @PathVariable String categoryName) {
		
		Login l = loggedIn(m, request);
		// show home if the user is logged in
		if (l==null) return "redirect:/login";
		
		String category = String.join(" ", categoryName.split("-"));
		m.addAttribute("categoryName", category);
		m.addAttribute("optionsList", utilityService.getCategoryOptionsList(category));
		m.addAttribute("accountsList", accountService.getAccountsList(l));
		
		return viewService.model(m).view("transaction");
	}
	
	public Login loggedIn(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request);
		if (l == null) {
			// else show login page
			m.addAttribute("login", new Login());
			m.addAttribute("register", new Register());
			return null;
		}
		m.addAttribute("customer", customerService.getCustomer(l));
		m.addAttribute("categoriesList", utilityService.getCategoryList());
		return l;
	}
}
