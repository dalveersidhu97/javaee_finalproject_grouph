package com.banking.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.banking.beans.Account;
import com.banking.beans.Transaction;
import com.banking.beans.Transaction.TransactionValue;

public class TransactionDao {

	@Autowired
	AccountDao accountDao;
	
	private JdbcTemplate template;

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public List<Transaction> getTransactionList(String sql){
		 return template.query(sql, new RowMapper<Transaction>(){  
			    
			 public Transaction mapRow(ResultSet rs, int rownumber) throws SQLException {  
		    	
				Transaction t = new Transaction();
				t.setId(rs.getInt("ID"));
				t.setAmount(rs.getFloat("amount"));
				t.setRemark(rs.getString("remark"));
				t.setFromAccountId(rs.getInt("fromAccountID"));
				t.setCustomerId(rs.getInt("customerID"));
				t.setStatus(rs.getString("status"));
				t.setCommitDate(rs.getDate("commitDate"));
				return t;
		    }  
		 });
	}
	
	public List<Transaction> getTransactionListByCustomerId(int customerId){
		return getTransactionList("Select * from Transactions where customerID="+customerId+" order by commitDate desc");
	}
	
	public List<Transaction> getTransactionListByCustomerAccountId(int customerId, int accountId){
		return getTransactionList("Select * from Transactions where customerID="+customerId+" and fromAccountID="+accountId+" order by commitDate desc");
	}

	public Transaction createPendingTransaction(Transaction t) {
		int tId = (int)Math.floor(Math.random()*(10000000-100000+1)+100000);
		t.setId(tId); // generate random number between 10000000 - 100000
		
		String sql = "INSERT INTO Transactions (ID, customerID, fromAccountId, amount, remark) Values ("+t.getId()+","+t.getCustomerId()+","+t.getFromAccountId()+","+-t.getAmount()+",'"+t.getRemark()+"');";
		try {
			// insert into Customers 
			int rowsEffected = template.update(sql);
			System.out.println("rowsEffected:"+rowsEffected);
			if(rowsEffected==1)
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

	public boolean commit(Transaction t) {
		
		List<TransactionValue> valuesList = t.getTransactionValues();
		
		try {
			// if value list size is 0... only deduct amount
			if(valuesList==null||valuesList.size()==0)
				return finishTransaction(t); // deduct amount and set transaction status

			// prepare SQL statement
			String sql = "INSERT INTO TransactionValues (optionID, optionValue, transactionID) Values";
			for(TransactionValue tv : valuesList)
				sql += "("+tv.getOptionId()+", '"+tv.getOptionValue()+"', "+t.getId()+"),";
			sql = sql.substring(0, sql.length() - 1);
			
			if(template.update(sql)==valuesList.size()) 
				return finishTransaction(t); // deduct amount and set transaction status
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		setTransactionSatus(t.getId(), "failed");
		return false;	
	}
	
	public boolean finishTransaction(Transaction t) {
		
		if(!accountDao.updateBalanceBy(-t.getAmount(),t.getFromAccountId()))
			return !setTransactionSatus(t.getId(), "failed");
			
		return setTransactionSatus(t.getId(), "completed");
	}
	
	public boolean setTransactionSatus(int transactionId, String status) {
		String sql = "update Transactions set status='"+status+"' where ID = "+transactionId+";";
		if(template.update(sql)==1)
			return true;
		return false;
	}
}