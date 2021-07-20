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
	public boolean confirmTransaction(Transaction t);
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
	
	public List<Transaction> getTransactionListByCustomerId(int customerId){
		return transactionDao.getTransactionListByCustomerId(customerId);
	}
	
	public List<Transaction> getTransactionListByCustomerAccountId(int customerId, int accountId){
		return transactionDao.getTransactionListByCustomerAccountId(customerId, accountId);
	}
	
	public Transaction validateTransaction(HttpServletRequest request, Login l, Model m, String categoryName) {
		try {
			List<CategoryOption> options = utilityService.getCategoryOptionsList(categoryName);
		
		
			float amount = Float.parseFloat((String)request.getParameter("amount"));
			amount = (amount<0)?-amount:amount; // absolute amount
			amount = (categoryName.contains("eposit"))?-amount:amount; // temporary for deposit transactions
			int accountId = Integer.parseInt((String)request.getParameter("accountId"));
			
			// check if the customer has enough amount
			if(amount > accountService.getAccountBalance(l, accountId)) {
				m.addAttribute("errorMessage", "Insufficient balance!");
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
					m.addAttribute("errorMessage", "Please fill all the input fields!");
					return null; // empty required field
				}
				TransactionValue tValue = new TransactionValue();
				// set transaction option values
				tValue.setOptionId(o.getId());
				tValue.setOptionValue((String)request.getParameter(o.getInputName()));
				valueList.add(tValue); // add to value list
			}
			transaction.setTransactionValues(valueList);
			// add pending transaction to database
			transaction.setStatus("pending");
			
			return transactionDao.createPendingTransaction(transaction);
		
		}catch(Exception e) {
			e.printStackTrace();
			m.addAttribute("errorMessage", "Incorrect input!");
			return null;
		}
	}

	public boolean finaliseTransaction(Transaction t) {
		return transactionDao.commit(t);
	}
	
	public Transaction createPendingTransaction(Transaction t) {
		return transactionDao.createPendingTransaction(t);
	}

	public boolean confirmTransaction(Transaction t) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Transaction validateTransfer(HttpServletRequest request, Login l, int toAccountId) {
		// verify fromAccount balance and toAcount account
		Account fromAccount = accountService.getSelfAccount(l, Integer.parseInt((String)request.getParameter("fromAccountId")));
		Account toAccount = accountService.getAccount(toAccountId);
		
		if(fromAccount==null || toAccount==null)
			return null;
		
		// create pending transaction
		Transaction t = new Transaction();
		float amount = Float.parseFloat((String)request.getParameter("amount"));
		amount = (amount<0)?-amount:amount; // absolute amount
		t.setAmount(amount);
		t.setRemark(request.getParameter("remark"));
		t.setFromAccountId(fromAccount.getId());
		t.setCustomerId(l.getCustomerId());
		
		Transaction pendingDeduct = createPendingTransaction(t);
		if(pendingDeduct!=null) request.getSession().setAttribute("toAccountId", toAccount.getId());
		return pendingDeduct;
	}
	
	public Transaction validateSelftTransfer(HttpServletRequest request, Login l) {
		int toAccountId = Integer.parseInt(request.getParameter("toAccountId"));		
		return validateTransfer(request, l, toAccountId);
	}
	
	public boolean finaliseTransferDeposite(HttpServletRequest request) {
		
		int toAccountId = (Integer)request.getSession().getAttribute("toAccountId");
		Account toAccount = accountService.getAccount(toAccountId);
		Transaction t1 = (Transaction) request.getSession().getAttribute("transaction");
		
		// create second pending transaction
		Transaction t2 = new Transaction();
		t2.setAmount(-t1.getAmount());
		t2.setRemark(t1.getRemark());
		t2.setFromAccountId(toAccount.getId());
		t2.setCustomerId(customerService.getCustomerFromAccountId(toAccount.getCustomerId()).getId());
		Transaction pendingDeposit = createPendingTransaction(t2);
		
		if(pendingDeposit==null)
			return false;
		
		return finaliseTransaction(pendingDeposit);
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
