
drop schema BankingProject;

create schema BankingProject;
use BankingProject;
-- select * from Customers;
-- select * from Login;
CREATE TABLE Customers (
    ID int primary key auto_increment NOT NULL,
    firstName varchar(30) NOT NULL ,
    lastName varchar(30) NOT NULL,
    email varchar(100) unique NULL
);

CREATE TABLE Login (
    customerID int primary key NOT NULL,
    username varchar(100) NOT NULL unique,
    password varchar(255) NOT NULL,
    FOREIGN KEY (customerID) REFERENCES Customers(ID) on delete cascade
);

    
CREATE TABLE Accounts (
    ID int primary key auto_increment NOT NULL,
    customerID int,
    accountType varchar(20) NOT NULL,
    balance decimal(10, 3) NOT NULL DEFAULT 0 CHECK(balance>=0),
    isActive boolean DEFAULT TRUE,
    FOREIGN KEY (customerID) REFERENCES Customers(ID) on delete cascade
);
ALTER TABLE Accounts AUTO_INCREMENT = 10000;

CREATE TABLE TransactionCategories (
    ID int primary key auto_increment NOT NULL,
    categoryName varchar(255) NOT NULL unique
);

    
CREATE TABLE TransactionCategoryOptions (
    ID int primary key auto_increment NOT NULL,
    categoryID int NOT NULL,
    optionTitle varchar(100) NOT NULL,
    inputName varchar(100) not null,
    inputType varchar(30) NOT NULL default 'text',
    FOREIGN KEY (categoryID) REFERENCES TransactionCategories(ID)
);

CREATE TABLE Transactions (
    ID int primary key auto_increment NOT NULL,
    customerID int NOT NULL,
    fromAccountId int NOT NULL,
    commitDate timestamp  not null default current_timestamp,
    amount decimal(10, 3) NOT NULL,
    remark varchar(100) default '',
    status ENUM ('completed', 'failed', 'incomplete', 'deposited') default 'incomplete',
    FOREIGN KEY (customerID) REFERENCES Customers(ID),
    FOREIGN KEY (fromAccountId) REFERENCES Accounts(ID)
);
ALTER TABLE Transactions AUTO_INCREMENT = 10000;

CREATE TABLE TransactionValues (
    ID int primary key auto_increment NOT NULL,
    optionID int NOT NULL,
    optionValue varchar(100) NOT NULL,
    transactionID int NOT NULL,
    FOREIGN KEY (transactionID) REFERENCES Transactions(ID) ON DELETE CASCADE,
    FOREIGN KEY (optionID) REFERENCES TransactionCategoryOptions(ID)
);

    
CREATE TABLE WithinBankTransactions (
    transactionID int NOT NULL,
    toAccountID int NOT NULL,  
    PRIMARY KEY(transactionID),
    FOREIGN KEY (transactionID) REFERENCES Transactions(ID) ON DELETE CASCADE,
    FOREIGN KEY (toAccountID) REFERENCES Accounts(ID)
);
	
CREATE TABLE OtherTransactions (
    transactionID int,
    categoryID int,
    PRIMARY KEY(transactionID),
    FOREIGN KEY (transactionID) REFERENCES Transactions(ID) ON DELETE CASCADE
);

    
INSERT INTO BankingProject.TransactionCategories (ID, categoryName) Values (32, 'Bank Transfer');
INSERT INTO BankingProject.TransactionCategories (ID, categoryName) Values (45, 'Water bill');
INSERT INTO BankingProject.TransactionCategories (ID, categoryName) Values (46, 'Electricity bill');
INSERT INTO BankingProject.TransactionCategories (ID, categoryName) Values (47, 'Withdraw money');
INSERT INTO BankingProject.TransactionCategories (ID, categoryName) Values (48, 'Deposit money');
INSERT INTO BankingProject.TransactionCategories (ID, categoryName) Values (49, 'Phone bill');

select * from BankingProject.TransactionCategories;

INSERT INTO BankingProject.TransactionCategoryOptions (categoryID, optionTitle, inputName, inputType) Values (32, 'Account number', 'account_number', 'text');
INSERT INTO BankingProject.TransactionCategoryOptions (categoryID, optionTitle, inputName, inputType) Values (32, 'IFSC Code', 'ifsc_code', 'text');
INSERT INTO BankingProject.TransactionCategoryOptions (categoryID, optionTitle, inputName, inputType) Values (45, 'Bill number', 'account_number', 'text');
INSERT INTO BankingProject.TransactionCategoryOptions (categoryID, optionTitle, inputName, inputType) Values (46, 'Bill number', 'account_number', 'text');
INSERT INTO BankingProject.TransactionCategoryOptions (categoryID, optionTitle, inputName, inputType) Values (49, 'Phone number', 'account_number', 'text');


-- select * from BankingProject.TransactionCategoryOptions;
-- select optionTitle, optionValue, fromAccountId from BankingProject.TransactionValues tv inner join BankingProject.Transactions t on t.ID=tv.transactionID 
-- inner join BankingProject.TransactionCategoryOptions tco on tco.ID = tv.optionID where t.ID = 10007;


-- 	INSERT INTO Customers (firstName, lastName, email) Values ('Dalveer', 'Singh', 'dalveersidhu97@gmail.com');
-- 	select * from BankingProject.Customers;
--     
-- 	INSERT INTO BankingProject.Login (customerID, username, password) Values (1, 'dalveersidhu97', 'password');
--     select * from BankingProject.Login;
--     
-- 	INSERT INTO BankingProject.Accounts (customerID, accountType, balance) Values (1, 'Savings', 500.87);
--     INSERT INTO BankingProject.Accounts (customerID, accountType, balance) Values (1, 'Credit', 50.33);
--     select * from Accounts;

-- 	INSERT INTO BankingProject.Transactions (ID, customerID, fromAccountId, amount, remark) Values (232343, 1, 10000, 500.45, 'Rent');
--     select * from BankingProject.Transactions;
--     
-- 	INSERT INTO BankingProject.TransactionValues (optionID, optionValue, transactionID) Values (1, 'ORBC12545', 232343);
--     INSERT INTO BankingProject.TransactionValues (optionID, optionValue, transactionID) Values (1, 'ORBC001', 232343);	
-- 	select * from BankingProject.TransactionValues tv inner join Transactions t on t.ID=tv.transactionID inner join TransactionCategoryOptions tco on tco.ID=tv.optionID where transactionID=9011355;
--     
-- delete from Transactions where ID = 232343;
    
-- 	INSERT INTO BankingProject.OtherTransactions (transactionID, categoryID) Values (232343, 32);
--     
--     select * from BankingProject.OtherTransactions ot 
-- 		inner join BankingProject.Transactions t on t.ID = ot.transactionID 
-- 		inner join BankingProject.TransactionCategories tg on tg.ID = ot.categoryID
--         inner join BankingProject.TransactionValues tv on t.ID = tv.transactionID
--         inner join BankingProject.TransactionCategoryOptions tco on tco.ID = tv.optionID;


-- get account details for logged in user
-- select * from Accounts a inner join Customers c on a.customerID = c.ID;
-- category list
-- select * from TransactionCategories;
-- category options list
-- select * from TransactionCategoryOptions tco inner join TransactionCategories tc on tc.ID = tco.categoryID where categoryID = 45;
-- update Accounts set balance=balance+(-50) where ID=10000;
-- Select * from BankingProject.Transactions t inner join WithinBankTransactions wt on t.ID=wt.transactionID;
-- select * from BankingProject.WithinBankTransactions;
-- delete from BankingProject.Transactions where status = 'incomplete' or amount < 100;
-- drop schema BankingProject;