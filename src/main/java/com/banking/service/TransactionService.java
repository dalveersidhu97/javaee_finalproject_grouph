package com.banking.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

import com.banking.beans.*;
import com.banking.beans.Transaction.TransactionValue;
import com.banking.dao.TransactionDao;

interface TransactionServiceInterface {
	public Transaction validateTransaction(HttpServletRequest request, Login l);
	public Transaction createPendingTransaction(Transaction t);
	public boolean confirmTransaction(Transaction t);
}

public class TransactionService implements TransactionServiceInterface{

	@Autowired
	UtilityService utilityService;
	@Autowired
	AccountService accountService;
	@Autowired
	TransactionDao transactionDao;
	
	public Transaction validateTransaction(HttpServletRequest request, Login l) {
		List<CategoryOption> options = utilityService.getCategoryOptionsList((String)request.getAttribute("categoryName"));
		
		
		float amount = Float.parseFloat((String)request.getParameter("amount"));
		int accountId = Integer.parseInt((String)request.getParameter("accountId"));
		
		// check if the customer has enough amount
		if(amount > accountService.getAccountBalance(l, accountId)) {
			System.out.println("insufficient balance");
			return null; // insufficient balance
		}
		// create transaction object
		Transaction transaction = new Transaction();
		List<TransactionValue> valueList = new ArrayList<TransactionValue>();
		
		transaction.setFromAccountId(accountId);
		transaction.setAmount(amount);
		transaction.setCustomerId(l.getCustomerId());
		transaction.setRemark((String)request.getParameter("remark"));
		
		// get only those request attributes which belong to this transaction category
		for(CategoryOption o : options) {
			
			if(!requestContains(o.getInputName(), request)) {
				System.out.println("null categoryOption");
				return null; // empty required field
			}
			TransactionValue tValue = new TransactionValue();
			// set transaction option values
			tValue.setOptionId(o.getId());
			tValue.setOptionValue((String)request.getParameter(o.getInputName()));
			valueList.add(tValue); // add to value list
		}
		
		// add pending transaction to database
		transaction.setStatus("pending");
		return transactionDao.createPendingTransaction(transaction);

	}

	public Transaction createPendingTransaction(Transaction t) {
		return transactionDao.createPendingTransaction(t);
	}

	public boolean confirmTransaction(Transaction t) {
		// TODO Auto-generated method stub
		return false;
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
