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
	public static final int NO_ROW_EFFECTED = -1;
	
	@Autowired
	public EncryptionService enService;
	@Autowired
	public AccountDao accountDao;
	
	// delete the customer if error to prevent user already exists message for register again
	public void registerRollback(Register r) {
		try {
			template.execute("delete from Customers where email='"+r.getEmail()+"'");
		}catch(Exception e) {e.printStackTrace();}
	}
	
	// return customer object if email exists
	public Customer emailExists(String email) {
		String sql = "select * from Customers where email = '"+email+"';";
		return getCustomer(sql);
	}
	
	// return login if user name exists
	public Login usernameExists(String username) {
		String sql = "select * from Login where username = '"+username+"';";
		return getLogin(sql);
		
	}
	
	// get customer object from accountID
	public Customer getCustomerFromAccountId(int accountId) {
		return getCustomer("select * from Customers c inner join Accounts a on c.ID=a.customerID where a.ID = "+accountId);
	}
	
	// get login object
	public Customer getCustomer(Login l) {
		return getCustomer("select * from Customers c inner join Login l on l.customerID=c.ID where username = '"+l.getUsername()+"' and password='"+l.getPassword()+"';");
	}
	
	// validate the user for login
	public Login validateLogin(Login l) {
		String sql = "select * from Login where username='" + l.getUsername() + "' and password='"
				+ l.getPassword() + "';";
		return getLogin(sql);
	}
	
	public int register(Register r) {
		Customer c1 = null;
	
		// return if the email or user name already exists
		if(usernameExists(r.getUsername())!=null)
			return USERNAME_ALREADY_EXISTS;
	
		if(emailExists(r.getEmail()) != null)
			return EMAIL_ALREADY_EXISTS;
		
		String sql = "INSERT INTO Customers (firstName, lastName, email) Values ('"+r.getFirstName()+"', '"+r.getLastName()+"', '"+r.getEmail()+"');";
		try {
			// insert into Customers 
			if(template.update(sql)==1) {
				
				sql = "select * from Customers where email = '"+r.getEmail()+"'";
				c1 = getCustomer(sql);
				sql = "INSERT INTO Login (customerID, username, password) Values ("+c1.getId()+", '"+r.getUsername()+"', '"+r.getPassword()+"');";
				
				// insert into Login
				if(template.update(sql)!=1) {
					// roll back if there is any error
					registerRollback(r);
					return NO_ROW_EFFECTED;
				}
				// create saving and credit account with zero initial balance
				if(!accountDao.createAccounts(c1, new String[]{"Chequing", "Savings", "Credit"}, new  float[] {0, 0, (float) 1000.00})) {
					registerRollback(r);
					return NO_ROW_EFFECTED;
				}
				return 1;
			}
		} catch (DuplicateKeyException e) {
			// roll back if there is any error
			registerRollback(r);
			e.printStackTrace();
			return USER_ALREADY_EXISTS;
			
		} catch (Exception e) {
			e.printStackTrace();
			// roll back if there is any error
		}
		registerRollback(r);
		return NO_ROW_EFFECTED;
	}
	
	// Get the customer object by running sql ... this function will be reused for getting Customer object for any sql query
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
	
	// reusable function for getting Login by running sql
	public Login getLogin(String sql) {
		try {
			return template.query(sql, new ResultSetExtractor<Login>() {
				public Login extractData(ResultSet rs) throws SQLException, DataAccessException {

					if(rs.next()) {
						Login l = new Login();
						l.setCustomerId(rs.getInt("customerID"));
						l.setUsername(rs.getString("username"));
						l.setPassword(rs.getString("password"));
						return l;
					}
					return null;
				}
			});
		} catch (Exception  e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
}