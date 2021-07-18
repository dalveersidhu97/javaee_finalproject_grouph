package com.banking.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.banking.beans.Customer;
import com.banking.beans.Login;
import com.banking.beans.Register;
import com.banking.dao.CustomerDao;

/**
 * 
 * @author Group-H
 * @date 12 July, 2021
 * @description This class interact with userDao bean to provide a layer of
 *              abstraction.
 * 
 */

interface UserServiceInterface {
	
	int register(Register customer);
	Login validateLogin(Login login);
}

public class CustomerService implements UserServiceInterface {
	@Autowired
	public CustomerDao customerDao;
	@Autowired
	public EncryptionService enService;

	public int register(Register register) {

		// Encrypting password
		//String encrypted = enService.encrypt(customer.getPassword().trim());

		// return 0 row effected if could not encrypt password
//		if (encrypted == null)
//			return 0;

		//customer.setPassword(encrypted);

		return customerDao.register(register);
	}

	public Login validateLogin(Login login) {
		// encrypting to match with the encrypted password in database
		//login.setPassword(enService.encrypt(login.getPassword()));
		return customerDao.validateLogin(login);
	}

	public boolean isLoggedIn(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		String username = null, password = null;
		
		for(Cookie cookie : cookies) {
			
			if(cookie.getName().equals("username"))
				username = cookie.getValue();
			if(cookie.getName().equals("password"))
				password = cookie.getValue();
		}
		
		
		if(null==username || null==password || username.equals("") || password.equals("")) {
			System.out.println("cookeis: not logged");
			return false;
		}
		Login login = new Login();
		login.setUsername(username);
		login.setPassword(password);
		
		if(validateLogin(login)!=null)
			return true;
		
		System.out.println("not logged in");
		return false;
	}

}