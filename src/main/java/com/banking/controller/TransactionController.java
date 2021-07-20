package com.banking.controller;

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
		Transaction transaction = transactionService.validateTransaction(request, l, m, request.getParameter("categoryName"));
		
		if(transaction == null) {
			return "redirect:/categories/"+String.join(" ", request.getParameter("categoryName").split("-"));
		}

		// store data in session
		request.getSession().setAttribute("transaction", transaction);
		
		// show confirm transaction page
		return "redirect:/transaction-status";
	}
	
	@RequestMapping("/transferProcess")
	public String processTransfer(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request);
		if(l==null) {
			// else show login page
			m.addAttribute("login", new Login());
			m.addAttribute("register", new Register());
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		}
		
		// create pending transactions
		Transaction transaction = transactionService.validateSelftTransfer(request, l);
		
		if(transaction== null) {
			m.addAttribute("status", "transfer fail");
			return viewService.model(m).view("transactionStatus");
		}

		// store data in session
		request.getSession().setAttribute("transaction", transaction);
		
		// show confirm transaction page
		return "redirect:/transfer-transaction-status";
	}
	
	@RequestMapping("/emailTransferProcess")
	public String processEmailTransfer(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request);
		if(l==null) {
			// else show login page
			m.addAttribute("login", new Login());
			m.addAttribute("register", new Register());
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		}
		
		String emailOrAccountId = request.getParameter("eamilOrAccountId").trim();
		
		Account account = accountService.getCustomerAccount(emailOrAccountId, "Savings");
		if(account==null) {
			m.addAttribute("errorMessage", "Invalid email or account number");
			return "redirect:/transfer/by-email";
		}
		// create pending transactions
		Transaction transaction = transactionService.validateTransfer(request, l, account.getId());
		
		if(transaction== null) {
			m.addAttribute("status", "transfer fail");
			return viewService.model(m).view("transactionStatus");
		}

		// store data in session
		request.getSession().setAttribute("transaction", transaction);
		
		// show confirm transaction page
		return "redirect:/transfer-transaction-status";
	}
	
	@RequestMapping("/transfer-transaction-status")
	public String confirmTransferTransaction(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request);
		if(l==null) {
			// else show login page
			m.addAttribute("login", new Login());
			m.addAttribute("register", new Register());
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		}
		
		Transaction pendingDeduct = (Transaction)request.getSession().getAttribute("transaction");
		
		// show success or fail
		if(!transactionService.finaliseTransaction(pendingDeduct)) {
			m.addAttribute("status",  "transfer deduct fail");
			return viewService.model(m).view("transactionStatus");
		}
		if(!transactionService.finaliseTransferDeposite(request)) {
			m.addAttribute("status",  "transfer deposit fail");
			return viewService.model(m).view("transactionStatus");
		}
		m.addAttribute("status",  "transfer success");
		return viewService.model(m).view("transactionStatus");
	}
	
	@RequestMapping("/transaction-status")
	public String confirmTransaction(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request);
		if(l==null) {
			// else show login page
			m.addAttribute("login", new Login());
			m.addAttribute("register", new Register());
			return viewService.model(m).views(Arrays.asList("login", "signup"));
		}
		
		// finalise transaction
		boolean isSuccessful = transactionService.finaliseTransaction((Transaction)request.getSession().getAttribute("transaction"));
		
		// show success or fail
		if(isSuccessful)
			m.addAttribute("status", "success");
		else
			m.addAttribute("status", "fail");
		
		return viewService.model(m).view("transactionStatus");
	}
	
	

}
