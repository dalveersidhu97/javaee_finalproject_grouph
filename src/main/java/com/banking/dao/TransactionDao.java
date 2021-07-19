package com.banking.dao;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.banking.beans.Transaction;

public class TransactionDao {

	private JdbcTemplate template;

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public Transaction createPendingTransaction(Transaction t) {
		int tId = (int)Math.floor(Math.random()*(10000000-100000+1)+100000);
		t.setId(tId); // generate random number between 10000000 - 100000
		
		String sql = "INSERT INTO Transactions (ID, customerID, fromAccountId, amount, remark) Values ("+t.getId()+","+t.getCustomerId()+","+t.getFromAccountId()+","+t.getAmount()+",'"+t.getRemark()+"');";
		try {
			// insert into Customers 
			if(template.update(sql)==1)
				return t;
			System.out.println("PendingTransaction: null");
			return null;
		} catch (DuplicateKeyException e) {
			// try again with different transaction id
			System.out.println("Retrying...");
			createPendingTransaction(t);
		} catch (Exception e) {
			e.printStackTrace();
			// roll back if there is any error
		}
		return null;
	}
	
	
	
	
}
