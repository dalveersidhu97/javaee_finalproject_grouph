create schema BankingProject;
use BankingProject;
-- select * from Customers;
-- select * from Login;
CREATE TABLE Customers (
    ID int primary key auto_increment NOT NULL,
    firstName varchar(30) NOT NULL ,
    lastName varchar(30) NOT NULL,
    email varchar(100) NOT NULL
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
    ID int primary key NOT NULL,
    customerID int NOT NULL,
    fromAccountId int NOT NULL,
    commitDate timestamp  not null default current_timestamp,
    amount decimal(10, 3) NOT NULL,
    remark varchar(100) default '',
    status ENUM ('completed','failed','incomplete', 'deposited') default 'incomplete',
    FOREIGN KEY (customerID) REFERENCES Customers(ID),
    FOREIGN KEY (fromAccountId) REFERENCES Accounts(ID)
);


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
select * from BankingProject.TransactionCategories;

INSERT INTO BankingProject.TransactionCategoryOptions (categoryID, optionTitle, inputName, inputType) Values (32, 'Account number', 'account_number', 'text');
INSERT INTO BankingProject.TransactionCategoryOptions (categoryID, optionTitle, inputName, inputType) Values (32, 'IFSC Code', 'ifsc_code', 'text');
INSERT INTO BankingProject.TransactionCategoryOptions (categoryID, optionTitle, inputName, inputType) Values (45, 'Bill number', 'account_number', 'text');
INSERT INTO BankingProject.TransactionCategoryOptions (categoryID, optionTitle, inputName, inputType) Values (46, 'Bill number', 'account_number', 'text');
select * from BankingProject.TransactionCategoryOptions;

