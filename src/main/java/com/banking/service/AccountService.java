package com.banking.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.banking.beans.*;
import com.banking.dao.*;

/**
 * 
 * @author Group-H
 * @date 12 July, 2021
 * @description This class interact with userDao bean to provide a layer of
 *              abstraction.
 * 
 */

interface AccountServiceInterface {
	
	List<Account> getAccountsList(Login l);
	Account deposit(Account ac);
	Account deduct(Account ac);
	Account withdraw();
	Account transferToSelf();
	Account transferToOther();
	
}

public class AccountService implements AccountServiceInterface {
	@Autowired
	public AccountDao accountDao;

	public List<Account> getAccountsList(Login l) {
		// TODO Auto-generated method stub
		return accountDao.getAccountsList(l);
	}

	public Account deposit(Account ac) {
		// TODO Auto-generated method stub
		return null;
	}

	public Account deduct(Account ac) {
		// TODO Auto-generated method stub
		return null;
	}

	public Account withdraw() {
		// TODO Auto-generated method stub
		return null;
	}

	public Account transferToSelf() {
		// TODO Auto-generated method stub
		return null;
	}

	public Account transferToOther() {
		// TODO Auto-generated method stub
		return null;
	}

	

}