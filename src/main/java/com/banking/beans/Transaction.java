package com.banking.beans;

import java.sql.Date;
import java.util.List;

public class Transaction {
	
	private int id;
	private int customerId;
	private int fromAccountId;
	private Date commitDate;
	private float amount;
	private String remark;
	private String status;
	private List<TransactionValue> transactionValues;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getFromAccountId() {
		return fromAccountId;
	}
	public void setFromAccountId(int fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	public Date getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<TransactionValue> getTransactionValues() {
		return transactionValues;
	}
	public void setTransactionValues(List<TransactionValue> transactionValues) {
		this.transactionValues = transactionValues;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public static class TransactionValue{
		
		private int id;
		private int optionId;
		private String optionValue;
		private int transactionId;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getOptionId() {
			return optionId;
		}
		public void setOptionId(int optionId) {
			this.optionId = optionId;
		}
		public String getOptionValue() {
			return optionValue;
		}
		public void setOptionValue(String optionValue) {
			this.optionValue = optionValue;
		}
		public int getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(int transactionId) {
			this.transactionId = transactionId;
		}
		
		
	}

}

