package com.banking.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.banking.beans.Account;
import com.banking.beans.Customer;
import com.banking.beans.Login;

public class AccountDao {

	private JdbcTemplate template;
	
	public boolean createAccounts(Customer c, String[] accountTypes, float[] initialBalances) {
		for(int i=0;i<accountTypes.length;i++) {
			if(!createAccount(c, accountTypes[i], initialBalances[i]))
				return false;
		}
		return true;
	}
	
	public boolean createAccount(Customer c, String accountType, float initialBalance) {
		String sql = "INSERT INTO Accounts (customerID, accountType, balance) Values ("+c.getId()+", '"+accountType+"', "+initialBalance+");";
		try {
			// insert into Customers 
			if(template.update(sql)==1) 
				return true;
			return false;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Account> getAccountsList(Login l) {
		String sql = "select * from Accounts where customerID = "+l.getCustomerId()+";";
		 return template.query(sql, new RowMapper<Account>(){  
			    
			 public Account mapRow(ResultSet rs, int rownumber) throws SQLException {  
		    	
				 Account ac = new Account();  
				 ac.setId(rs.getInt("ID"));
				 ac.setCustomerId(rs.getInt("customerID"));
				 ac.setType(rs.getString("accountType"));
				 ac.setBalance(rs.getFloat("balance"));
				 ac.setActive(rs.getBoolean("isActive"));
				 return ac;
		    }  
		 });
	}
	
	public Account getAccount(String sql) {
		return template.query(sql,new ResultSetExtractor<Account>(){
			public Account extractData(ResultSet rs) throws SQLException, DataAccessException {
		    	if(rs.next()) {
		    		Account ac = new Account();
		    		ac.setId(rs.getInt("ID"));
		    		ac.setCustomerId(rs.getInt("customerID"));
		    		ac.setType(rs.getString("accountType"));
		    		ac.setActive(rs.getBoolean("isActive"));
		    		ac.setBalance(rs.getFloat("balance"));
		    		return ac;
		    	}
		    	return null;
		     } 	 
		  });
	}
	
	public Account getAccount(Login l, int accountId) {
		String sql = "select * from Accounts where customerID = "+l.getCustomerId()+" and ID="+accountId+";";
		return getAccount(sql);
	}
	
	public Account getAccount(int accountId) {
		String sql = "select * from Accounts where ID="+accountId+";";
		return getAccount(sql);
	}
	
	public Account getCustomerAccount(String emailOrAccountId, String type) {
		String sql = "select * from Accounts a inner join Customers c on c.ID=a.customerID where accountType='"+type+"' and ( c.email='"+emailOrAccountId+"' or a.ID='"+emailOrAccountId+"');";
		return getAccount(sql);
	}
	public Account getCustomerAccount(int customerId, String type) {
		String sql = "select * from Accounts a inner join Customers c on c.ID=a.customerID where c.ID="+customerId+";";
		return getAccount(sql);
	}
	
	public boolean updateBalanceBy(float amount, int accountId) {
		String sql = "update Accounts set balance=balance+("+amount+") where ID="+accountId;
		if(template.update(sql)==1)
			return true;
		return false;
	}
	
	public boolean transferBalance(int fromAccountId, int toAccountId, float amount) {
		if(!updateBalanceBy(-amount, fromAccountId)) 
			return false;
		if(!updateBalanceBy(amount, toAccountId))
			updateBalanceBy(-amount, fromAccountId);
		return false;
	}

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

}
