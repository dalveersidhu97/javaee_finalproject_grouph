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

/**
 * 
 * @author Group-H
 * @date 03-07-2021
 * @description TransactionController is the route handler for all transaction
 *              type and the routes related to transactions
 * 
 */

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

		Login l = loggedIn(m, request);
		// show home if the user is logged in
		if (l==null) return "redirect:/finalproject_grouph/login";

		// create pending transaction
		Transaction transaction = transactionService.validateTransaction(request, l, m,
				request.getParameter("categoryName"));

		if (transaction == null) {
			return "redirect:/categories/" + String.join(" ", request.getParameter("categoryName").split("-"));
		}

		// store data in session
		request.getSession().setAttribute("transaction", transaction);

		// show confirm transaction page
		m.addAttribute("redirect", "./transaction-status");
		m.addAttribute("utilityServiceObject", utilityService);
		return viewService.model(m).view("confirmTransaction");

		// return "redirect:/transaction-status";
	}

	@RequestMapping("/selfTransferProcess")
	public String processSelfTransfer(Model m, HttpServletRequest request) {

		Login l = loggedIn(m, request);
		// show home if the user is logged in
		if (l==null) return "redirect:/login";

		// create pending transactions
		Transaction transaction = transactionService.validateSelfTransfer(request, l, m, 1);

		if (transaction == null) {
			// m.addAttribute("status", "transfer fail");
			return "redirect:/transfer/self";
			// return viewService.model(m).view("transactionStatus");
		}

		// store data in session
		request.getSession().setAttribute("transaction", transaction);

		// show confirm transaction page
		return "redirect:/transfer-transaction-status";
	}

	@RequestMapping("/emailTransferProcess")
	public String processEmailTransfer(Model m, HttpServletRequest request) {

		Login l = loggedIn(m, request);
		// show home if the user is logged in
		if (l==null) return "redirect:/login";
		
		// if no account selected show error
		if(request.getAttribute("accountId")==null || request.getAttribute("accountId").equals("")) {
			m.addAttribute("errorMessage", "Select account!");
			return "redirect:/transfer/by-email";
		}
		String emailOrAccountId = request.getParameter("eamilOrAccountId").trim();
		
		Account account = accountService.getCustomerAccount(emailOrAccountId, "Savings");
		if (account == null) {
			m.addAttribute("errorMessage", "Invalid email or account number");
			return "redirect:/transfer/by-email";
		}
		// create pending transactions
		Transaction transaction = transactionService.validateWithinBankTransfer(request, l, m, account.getId()); // -1 multiplier

		if (transaction == null) {
			m.addAttribute("status", "transfer fail");
			return viewService.model(m).view("transactionStatus");
		}

		// store data in session
		request.getSession().setAttribute("transaction", transaction);

		// show confirm transaction page
		m.addAttribute("redirect", "./transfer-transaction-status");
		m.addAttribute("customerServiceObject", customerService);
		return viewService.model(m).view("confirmEmailTransaction");

		// return "redirect:/transfer-transaction-status";
	}

	@RequestMapping("/transfer-transaction-status")
	public String confirmTransferTransaction(Model m, HttpServletRequest request) {

		Login l = loggedIn(m, request);
		// show home if the user is logged in
		if (l==null) return "redirect:/login";

		// show success or fail
		if (!transactionService.finaliseWithinBankTransaction(request)) {
			m.addAttribute("status", "transfer fail");
			return viewService.model(m).view("transactionStatus");
		}
		m.addAttribute("status", "transfer success");
		return viewService.model(m).view("transactionStatus");
	}

	@RequestMapping("/transaction-status")
	public String confirmTransaction(Model m, HttpServletRequest request) {

		Login l = loggedIn(m, request);
		// show home if the user is logged in
		if (l==null) return "redirect:/login";

		// finalise transaction
		boolean isSuccessful = transactionService
				.finaliseTransaction((Transaction) request.getSession().getAttribute("transaction"));

		// show success or fail
		if (isSuccessful)
			m.addAttribute("status", "success");
		else
			m.addAttribute("status", "fail");
		
		return viewService.model(m).view("transactionStatus");
	}
	
	@RequestMapping("/transfer/self")
	public String showTransferSelf(Model m, HttpServletRequest request) {
		
		Login l = loggedIn(m, request);
		// show home if the user is logged in
		if (l==null) return "redirect:/login";
		
		m.addAttribute("accountsList", accountService.getAccountsList(l));
		
		return viewService.model(m).view("selfTransferForm");
	}
	
	@RequestMapping("/transfer/by-email")
	public String showTransferByEmail(Model m, HttpServletRequest request) {
		
		Login l = loggedIn(m, request);
		// show home if the user is logged in
		if (l==null) return "redirect:/login";
		
		m.addAttribute("accountsList", accountService.getAccountsList(l));
		
		return viewService.model(m).view("emailTransferForm");
	}
	
	@RequestMapping("/transaction-details/{transactionId}")
	public String transactionDetails(Model m, HttpServletRequest request, @PathVariable String transactionId) {
		
		Login l = loggedIn(m, request);
		// show home if the user is logged in
		if (l==null) return "redirect:/login";

		// get transaction and store in request
		Transaction t = transactionService.getTransaction(transactionId);
		Account ac = accountService.getAccount(t.getFromAccountId());
		m.addAttribute("transaction", t);
		m.addAttribute("fromAccount", ac);
		
		// show transaction detail page
		return viewService.model(m).view("transaction-details");
	}
	
	public Login loggedIn(Model m, HttpServletRequest request) {
		
		// show home if the user is logged in
		Login l = customerService.isLoggedIn(request);
		if (l == null) {
			// else show login page
			m.addAttribute("login", new Login());
			m.addAttribute("register", new Register());
			m.addAttribute("message", "Please login first!");
			return null;
		}
		m.addAttribute("customer", customerService.getCustomer(l));
		m.addAttribute("categoriesList", utilityService.getCategoryList());
		return l;
	}

}
