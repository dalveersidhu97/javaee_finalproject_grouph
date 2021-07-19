package com.banking.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.banking.beans.Account;
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

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

}
