package com.banking.service;

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

	Customer validateUser(Login login);
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

	public Customer validateUserToken(Login login) {
		return customerDao.validateUser(login);
	}

	public Customer validateUser(Login login) {
		// encrypting to match with the encrypted password in database
		login.setPassword(enService.encrypt(login.getPassword()));
		return validateUserToken(login);
	}
}