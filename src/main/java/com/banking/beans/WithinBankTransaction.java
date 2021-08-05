package com.banking.beans;

/**
 * 
 * @author Group-H
 * @date 03-07-2021
 * @description - It model for Within bank transaction table
 * 
 */

public class WithinBankTransaction extends Transaction{

	private int transactionId;
	private int toAccountId;
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(int toAccountId) {
		this.toAccountId = toAccountId;
	}

}
