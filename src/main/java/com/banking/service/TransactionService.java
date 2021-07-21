package com.banking.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import com.banking.beans.*;
import com.banking.beans.Transaction.TransactionValue;
import com.banking.dao.TransactionDao;

interface TransactionServiceInterface {
	public Transaction validateTransaction(HttpServletRequest request, Login l, Model m, String categoryName);
	public Transaction createPendingTransaction(Transaction t);
	public boolean finaliseTransaction(Transaction t);
}

public class TransactionService implements TransactionServiceInterface{

	@Autowired
	UtilityService utilityService;
	@Autowired
	AccountService accountService;
	@Autowired
	TransactionDao transactionDao;
	@Autowired
	CustomerService customerService;
	
	public List<Transaction> getTransactionListByCustomerId(int customerId, List<Account> accountList){
		return transactionDao.getTransactionListByCustomerId(customerId, accountList);
	}
	
	public List<Transaction> getTransactionListByAccountId(int accountId){
		return transactionDao.getTransactionListByAccountId(accountId);
	}
	// validate input fields and set error message 
	public Transaction validateTransactionData(HttpServletRequest request, Login l, Model m, String categoryName) {
	
		try {
			float amount = Float.parseFloat((String)request.getParameter("amount"));
			amount = (amount<0)?-amount:amount; // absolute amount
			amount = (categoryName.contains("eposit"))?amount:-amount; // temporary for deposit transactions
			int accountId = Integer.parseInt((String)request.getParameter("accountId"));
			
			// check if the customer has enough amount
			if(-amount > accountService.getAccountBalance(l, accountId)) {
				m.addAttribute("errorMessage", "Insufficient balance!");
				return null; // insufficient balance
			}
			// create transaction object
			Transaction transaction = new Transaction();
			transaction.setFromAccountId(accountId);
			transaction.setAmount(amount);
			transaction.setCustomerId(l.getCustomerId());
			transaction.setRemark((String)request.getParameter("remark"));
			// add pending transaction to database
			transaction.setStatus("pending");
			return transaction;
		}catch(Exception e) {
			e.printStackTrace();
			m.addAttribute("errorMessage", "Incorrect input!");
		}
		return null;
	}
	
	public Transaction validateTransaction(HttpServletRequest request, Login l, Model m, String categoryName) {
		try {
			Transaction validatedTransaction = validateTransactionData(request, l, m, categoryName);
			if(validatedTransaction==null) return null;
			List<CategoryOption> options = utilityService.getCategoryOptionsList(categoryName);
			List<TransactionValue> valueList = new ArrayList<TransactionValue>();
			
			// get only those request attributes which belong to this transaction category
			for(CategoryOption o : options) {
	
				if(!requestContains(o.getInputName(), request)) {
					m.addAttribute("errorMessage", "Please fill all the input fields!");
					return null; // empty required field
				}
				TransactionValue tValue = new TransactionValue();
				// set transaction option values
				tValue.setOptionId(o.getId());
				tValue.setOptionValue((String)request.getParameter(o.getInputName()));
				valueList.add(tValue); // add to value list
			}
			validatedTransaction.setTransactionValues(valueList);
			// add pending transaction to database
			validatedTransaction.setStatus("pending");
			return createPendingTransaction(validatedTransaction);
		}catch(Exception e) {
			e.printStackTrace();
			m.addAttribute("errorMessage", "Incorrect input!");
			return null;
		}
	}

	public boolean finaliseTransaction(Transaction t) {
		return transactionDao.finaliseTransaction(t);
	}
	
	public Transaction createPendingTransaction(Transaction t) {
		return transactionDao.createPendingTransaction(t);
	}

	public WithinBankTransaction validateWithinBankTransfer(HttpServletRequest request, Login l, Model m, int toAccountId) {
		// verify fromAccount balance and toAcount account
		Account fromAccount = accountService.getSelfAccount(l, Integer.parseInt((String)request.getParameter("accountId")));
		Account toAccount = accountService.getAccount(toAccountId);
		
		if(fromAccount==null || toAccount==null) {
			m.addAttribute("errorMessage", "Invalid account!");
			return null;
		}
		Transaction vt = validateTransactionData(request, l, m, " ");
		if(vt==null) return null;
		// create pending transaction
		WithinBankTransaction t = new WithinBankTransaction();
		float amount = vt.getAmount();
		amount = (amount<0)?-amount:amount; // absolute amount
		t.setAmount(amount);
		t.setRemark(vt.getRemark());
		t.setFromAccountId(vt.getFromAccountId());
		t.setCustomerId(vt.getCustomerId());
		t.setToAccountId(toAccount.getId());
		
		return (WithinBankTransaction) createPendingTransaction(t);
	}
	
	public Transaction validateSelfTransfer(HttpServletRequest request, Login l, Model m, float amountSign) {
		int toAccountId = Integer.parseInt(request.getParameter("toAccountId"));		
		return validateWithinBankTransfer(request, l, m, toAccountId);
	}
	
	public boolean finaliseWithinBankTransaction(HttpServletRequest request) {
		WithinBankTransaction withinBankT = (WithinBankTransaction) request.getSession().getAttribute("transaction");
		return transactionDao.finaliseWithinBankTransaction(withinBankT);
	}

	// checks if the request has specified attribute
	private boolean requestContains(String attribute, HttpServletRequest request) {
		Enumeration attrs =  request.getParameterNames();
		while(attrs.hasMoreElements()) {
		    if(attrs.nextElement().equals(attribute)) {
		    	if(!((String)request.getParameter(attribute)).trim().equals(""))
		    		return true;
		    	else return false;
		    }
		}
		return false;
	}
}
