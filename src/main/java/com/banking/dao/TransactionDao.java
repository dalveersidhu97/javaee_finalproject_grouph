package com.banking.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.banking.beans.Account;
import com.banking.beans.Customer;
import com.banking.beans.Transaction;
import com.banking.beans.Transaction.TransactionValue;
import com.banking.beans.WithinBankTransaction;

/**
 * 
 * @author Group-H
 * @date 03-07-2021
 * @description TransactionDao provides functionality for Getting and Inserting
 *              information regarding Transactions from or to database such as
 *              getting all Transactions List, get specific account
 *              transactions, MAKE transaction
 * 
 */

public class TransactionDao {

	@Autowired
	AccountDao accountDao;

	private JdbcTemplate template;

	// Get all transactions for specific Customer by customer id
	public List<Transaction> getTransactionListByCustomerId(int customerId, List<Account> accountList) {
		return getTransactionList(
				"Select * from Transactions t left join WithinBankTransactions wt on t.ID=wt.transactionID where customerID="
						+ customerId + " or toAccountID=" + accountList.get(0).getId() + " or toAccountID="
						+ accountList.get(1).getId() + " order by commitDate desc");
	}

	// Get all transactions for specific account
	public List<Transaction> getTransactionListByAccountId(int accountId) {
		return getTransactionList(
				"Select * from Transactions t left join WithinBankTransactions wt on t.ID=wt.transactionID where fromAccountID="
						+ accountId + " or toAccountID=" + accountId + " order by commitDate desc");
	}

	// Create a transaction with status = Incomplete - the status will be updated to
	// Success if the balance if transfered successfully, if there is any error in
	// transferring balance, the status will remain saved as incomplete
	public Transaction createPendingTransaction(Transaction t) {

//		int tId = (int) Math.floor(Math.random() * (10000000 - 100000 + 1) + 100000);
//		t.setId(tId); // generate random number between 10000000 - 100000

		String sql = "INSERT INTO Transactions (customerID, fromAccountId, amount, remark) Values (" + t.getCustomerId()
				+ "," + t.getFromAccountId() + "," + t.getAmount() + ",'" + t.getRemark() + "');";
		try {

			// return the incomplete transaction, Transaction Status will be set to FAILED
			// or COMPLETED later when the Transfer will happen
			if (template.update(sql) == 1) {

				sql = "Select ID from Transactions where customerID=" + t.getCustomerId() + " and fromAccountId="
						+ t.getFromAccountId() + " order by ID desc limit 1";
				// select the inserted transactionID back for later use
				int ID = template.query(sql, new ResultSetExtractor<Integer>() {
					public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
						rs.next();
						return rs.getInt("ID");
					}
				});

				t.setId(ID); // set transaction id

				return t;
			}
		} catch (DuplicateKeyException e) {

			// transaction id already exists.....try again with different
			// transaction id
			createPendingTransaction(t);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public boolean finaliseTransaction(Transaction t) {

		List<TransactionValue> valuesList = t.getTransactionValues();

		try {

			// if value list size is 0... only deduct amount
			if (valuesList == null || valuesList.size() == 0)
				return updateBalanceAndSetStatus(t); // deduct amount and set transaction status

			// prepare SQL statement
			String sql = "INSERT INTO TransactionValues (optionID, optionValue, transactionID) Values";

			for (TransactionValue tv : valuesList)
				sql += "(" + tv.getOptionId() + ", '" + tv.getOptionValue() + "', " + t.getId() + "),";

			sql = sql.substring(0, sql.length() - 1); // remove comma form last

			if (template.update(sql) == valuesList.size()) // if all the option fields are inserted
				return updateBalanceAndSetStatus(t); // deduct amount and set transaction status

		} catch (Exception e) {
			e.printStackTrace();
		}
		setTransactionSatus(t.getId(), "failed");
		return false;
	}

	// Save more details of the transaction and UPDATE status = completed
	public boolean finaliseWithinBankTransaction(WithinBankTransaction t) {

		if (!accountDao.transferBalance(t.getFromAccountId(), t.getToAccountId(), t.getAmount()))
			return !setTransactionSatus(t.getId(), "failed");

		template.update("insert into WithinBankTransactions (transactionID, toAccountID) values (" + t.getId() + ", "
				+ t.getToAccountId() + ")");

		return setTransactionSatus(t.getId(), "completed");
	}

	// update balance and set Transaction status
	public boolean updateBalanceAndSetStatus(Transaction t) {

		if (!accountDao.updateBalanceBy(t.getAmount(), t.getFromAccountId()))
			return !setTransactionSatus(t.getId(), "failed");

		return setTransactionSatus(t.getId(), "completed");
	}

	// update transaction status
	public boolean setTransactionSatus(int transactionId, String status) {

		String sql = "update Transactions set status='" + status + "' where ID = " + transactionId + ";";

		if (template.update(sql) == 1)
			return true;

		return false;
	}

	// get list of transaction .. this function is for reuse for different queries
	// but returning same type
	public List<Transaction> getTransactionList(String sql) {

		return template.query(sql, new RowMapper<Transaction>() {

			public Transaction mapRow(ResultSet rs, int rownumber) throws SQLException {

				Transaction t = new Transaction();
				t.setId(rs.getInt("ID"));
				t.setAmount(rs.getFloat("amount"));
				t.setRemark(rs.getString("remark"));
				t.setFromAccountId(rs.getInt("fromAccountID"));
				t.setCustomerId(rs.getInt("customerID"));
				t.setStatus(rs.getString("status"));
				t.setCommitDate(rs.getDate("commitDate"));
				return t;
			}
		});
	}

	// Getters and Setters

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

}
