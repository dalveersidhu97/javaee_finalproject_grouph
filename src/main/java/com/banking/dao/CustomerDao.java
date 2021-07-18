package com.banking.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.banking.beans.Customer;
import com.banking.beans.Login;
import com.banking.beans.Register;
import com.banking.service.EncryptionService;

/**
 * 
 * @author Group-H
 * @date 12 July, 2021
 * @description DAO Bean for database operations for User such as register and
 *              login.
 * 
 */

public class CustomerDao {
	
	private JdbcTemplate template;
	
	public static final int USER_ALREADY_EXISTS = -4;
	public static final int EMAIL_ALREADY_EXISTS = -5;
	public static final int USERNAME_ALREADY_EXISTS = -6;
	
	@Autowired
	public EncryptionService enService;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int register(Register r) {
		Customer c1 = null;
		
		// return if the email or user name already exists
		
		if(emailExists(r.getEmail()) != null)
			return EMAIL_ALREADY_EXISTS;
		
		if(usernameExists(r.getUsername())!=null)
			return USERNAME_ALREADY_EXISTS;
		
		String sql = "INSERT INTO Customers (firstName, lastName, email) Values ('"+r.getFirstName()+"', '"+r.getLastName()+"', '"+r.getEmail()+"');";
		try {
			// insert into Customers 
			if(template.update(sql)==1) {
				sql = "select * from Customers where email = '"+r.getEmail()+"'";
				c1 = getCustomer(sql);
				sql = "INSERT INTO Login (cutomerID, username, password) Values ("+c1.getId()+", '"+r.getUsername()+"', '"+r.getPassword()+"');";
				// insert into Login
				if(template.update(sql)==1)
					return 1;
				// roll back if there is any error
				registerRollback(r);
			}
		} catch (DuplicateKeyException e) {
			
			// roll back if there is any error
			registerRollback(r);
			e.printStackTrace();
			return USER_ALREADY_EXISTS;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			// roll back if there is any error
			registerRollback(r);
			return -1;
		}
		return -1;
	}
	
	public void registerRollback(Register r) {
		try {
			template.execute("delete from Customers where email='"+r.getEmail()+"'");
			template.execute("delete from Login where username='"+r.getUsername()+"'");
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public Customer emailExists(String email) {
		String sql = "select * from Customers where email = '"+email+"';";
		return template.query(sql,new ResultSetExtractor<Customer>(){
			public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {

		    	Customer c=null;
		    	if(rs.next()) {
		    		c=new Customer();
			        c.setId(rs.getInt("ID"));  
			        c.setFirstName(rs.getString("firstname"));  
			        c.setLastName(rs.getString("lastname"));
			        c.setEmail(rs.getString("email"));
			    }
		    	return c;
		     } 	 
			 
		  });
	}
	
	public Login usernameExists(String username) {
		String sql = "select * from Login where username = '"+username+"';";
		return template.query(sql,new ResultSetExtractor<Login>(){
			public Login extractData(ResultSet rs) throws SQLException, DataAccessException {

				Login l=null;
		    	if(rs.next()) {
		    		l=new Login();
			        l.setCustomerId(rs.getInt("cutomerID"));  
			        l.setUsername(rs.getString("username"));  
			        l.setPassword(rs.getString("password"));
			    }
		    	return l;
		     } 	 
			 
		  });
		
	}
	
	public Customer getCustomer(String sql){  
		 return template.query(sql,new ResultSetExtractor<Customer>(){
			public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {

		    	Customer c=null;
		    	if(rs.next()) {
		    		c=new Customer();
			        c.setId(rs.getInt("ID"));  
			        c.setFirstName(rs.getString("firstname"));  
			        c.setLastName(rs.getString("lastname"));
			        c.setEmail(rs.getString("email"));
			    }
		    	return c;
		     } 	 
			 
		  });    
	}
	
	public Customer validateUser(Login l) {

		String sql = "select * from Assignment4.User where username='" + l.getUsername() + "' and password='"
				+ l.getPassword() + "';";
		
		try {
			return template.query(sql, new ResultSetExtractor<Customer>() {

				public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					Customer c = new Customer();

					rs.next();
					c.setFirstName(rs.getString("firstname"));
					c.setFirstName(rs.getString("lastname"));
					c.setEmail(rs.getString("email"));
					return c;
				}
			});
		} catch (Exception  e) {
			e.printStackTrace();
			// null if there is any error
			return null;
		}
	}

}