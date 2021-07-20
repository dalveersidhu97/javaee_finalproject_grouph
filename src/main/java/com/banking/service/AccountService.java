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
	float getAccountBalance(Login l, int accountId);
	Account deposit(Account ac);
	Account deduct(Account ac);
	Account transferToSelf();
	Account transferToOther();
	
}

public class AccountService implements AccountServiceInterface {
	@Autowired
	public AccountDao accountDao;
	@Autowired
	public TransactionService tService;

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

	public Account transferToSelf() {
		// TODO Auto-generated method stub
		return null;
	}

	public Account transferToOther() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAccount(int accountId) {
		return (getAccount(accountId)==null)?false:true;
	}
	
	public boolean isSelfAccount(Login l, int accountId) {
		return (getSelfAccount(l, accountId)==null)?false:true;
	}
	public Account getCustomerAccount(String emailOrAccountId, String type) {
		return accountDao.getCustomerAccount(emailOrAccountId, type);
	}
	public Account getAccount(int accountId) {
		return accountDao.getAccount(accountId);
	}
	
	public Account getSelfAccount(Login l, int accountId) {
		return accountDao.getAccount(l, accountId);
	}
	
	public float getAccountBalance(Login l, int accountId) {
		return accountDao.getAccount(l, accountId).getBalance();
	}

}