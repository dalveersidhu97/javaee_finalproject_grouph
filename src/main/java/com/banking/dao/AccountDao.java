package com.banking.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.banking.beans.Account;
import com.banking.beans.Customer;
import com.banking.beans.Login;

public class AccountDao {

	private JdbcTemplate template;
	
	public List<Account> getAccountsList(Login l) {
		String sql = "select * from Accounts where customerID = "+l.getCustomerId()+";";
		 return template.query(sql, new RowMapper<Account>(){  
			    
			 public Account mapRow(ResultSet rs, int rownumber) throws SQLException {  
		    	
				 Account ac = new Account();  
				 ac.setId(rs.getInt("ID"));
				 ac.setCustomerId(rs.getInt("customerID"));
				 ac.setType(rs.getString("accountType"));
				 ac.setNumber(rs.getString("accountNumber"));
				 ac.setBalance(rs.getFloat("balance"));
				 ac.setActive(rs.getBoolean("isActive"));
				 return ac;
		    }  
		 });
	}
	
	public float getAccountBalance(Login l, int accountId) {
		String sql = "select * from Accounts where customerID = "+l.getCustomerId()+" and ID="+accountId+";";
		return template.query(sql,new ResultSetExtractor<Float>(){
			public Float extractData(ResultSet rs) throws SQLException, DataAccessException {
		    	if(rs.next())
		    		return rs.getFloat("balance");
		    	return null;
		     } 	 
		  });
	}

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

}
