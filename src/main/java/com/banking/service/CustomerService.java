package com.banking.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.banking.beans.Customer;
import com.banking.beans.Login;
import com.banking.beans.Register;
import com.banking.dao.CustomerDao;

/**
 * 
 * @author Group-H
 * @date August 03, 2021
 * @description CustomerService communicates with CustomerDao performs all the operations regarding customer by using CustomerDao
 */

interface CustomerServiceInterface {
	
	int register(Register customer);
	Login validateLogin(Login login);
	Customer getCustomer(Login login);
	Customer getCustomerFromAccountId(int id);
}

public class CustomerService implements CustomerServiceInterface {
	@Autowired
	public CustomerDao customerDao;
	@Autowired
	public EncryptionService enService;

	public int register(Register register) {

		//Encrypting password
		String encrypted = enService.encrypt(register.getPassword().trim());

		//return 0 row effected if could not encrypt password
		if (encrypted == null)
			return 0;
		register.setPassword(encrypted);
		return customerDao.register(register);
	}

	public Login validateLogin(Login login) {
		// encrypting to match with the encrypted password in database
		login.setPassword(enService.encrypt(login.getPassword()));
		return validateLoginToken(login);
	}
	
	public Login validateLoginToken(Login login) {
		return customerDao.validateLogin(login);
	}
	
	// return Login object if the user is logged in
	public Login isLoggedIn(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null) return null; // return null if there is no cookie
		
		String username = null, password = null;
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("username"))
				username = cookie.getValue();
			if(cookie.getName().equals("password"))
				password = cookie.getValue();
		}
		
		if(null==username || null==password || username.equals("") || password.equals(""))
			return null; // null if user name and password are not set
			
		Login login = new Login();
		login.setUsername(username);
		login.setPassword(password);
		
		// validate if user name and password and return login object or null
		return validateLoginToken(login);
		
	}
	
	// return login object if the user is logged in else return null
	public Login isLoggedIn(HttpServletRequest request, Model m) {
		Login l = isLoggedIn(request);
		if(l!=null) 
			return l;
		m.addAttribute("login", new Login());
		m.addAttribute("register", new Register());
		return null;
	}
	
	public Customer getCustomer(Login login) {
		return customerDao.getCustomer(login);
	}

	public Customer getCustomerFromAccountId(int id) {
		return customerDao.getCustomerFromAccountId(id);
	}

}