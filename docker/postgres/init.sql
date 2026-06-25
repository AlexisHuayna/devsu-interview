-- ==========================================
-- DATABASES
-- ==========================================

CREATE DATABASE customer_db;
CREATE DATABASE account_db;

-- ==========================================
-- CUSTOMER DB
-- ==========================================

\c customer_db;

-- ==========================================
-- PERSONS
-- ==========================================

CREATE TABLE persons (
    id BIGSERIAL PRIMARY KEY,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    name VARCHAR(100) NOT NULL,

    gender VARCHAR(20) NOT NULL CHECK (
        gender IN ('MALE', 'FEMALE')
    ),

    age INTEGER NOT NULL CHECK (
        age > 0
    ),

    identification VARCHAR(20) NOT NULL UNIQUE,

    address VARCHAR(200) NOT NULL,

    phone VARCHAR(20) NOT NULL
);

-- ==========================================
-- CUSTOMERS
-- ==========================================

CREATE TABLE customers (
    id BIGINT PRIMARY KEY,

    password VARCHAR(255) NOT NULL,

    active BOOLEAN NOT NULL,

    CONSTRAINT fk_customer_person
        FOREIGN KEY (id)
        REFERENCES persons(id)
        ON DELETE CASCADE
);

-- ==========================================
-- INDEXES
-- ==========================================

CREATE INDEX idx_customer_active
ON customers(active);

-- ==========================================
-- ACCOUNT DB
-- ==========================================

\c account_db;

-- ==========================================
-- CUSTOMER SNAPSHOTS
-- ==========================================

CREATE TABLE customer_snapshots (
    id BIGSERIAL PRIMARY KEY,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    customer_id BIGINT NOT NULL UNIQUE,

    name VARCHAR(100) NOT NULL,

    active BOOLEAN NOT NULL
);

CREATE UNIQUE INDEX idx_customer_snapshot_customer
ON customer_snapshots(customer_id);

-- ==========================================
-- ACCOUNTS
-- ==========================================

CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    account_number VARCHAR(20) NOT NULL UNIQUE,

    account_type VARCHAR(20) NOT NULL CHECK (
        account_type IN ('SAVINGS', 'CHECKING')
    ),

    initial_balance NUMERIC(19,2) NOT NULL CHECK (
        initial_balance >= 0
    ),

    available_balance NUMERIC(19,2) NOT NULL CHECK (
        available_balance >= 0
    ),

    active BOOLEAN NOT NULL,

    customer_id BIGINT NOT NULL,

    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX idx_account_customer
ON accounts(customer_id);

-- ==========================================
-- TRANSACTIONS
-- ==========================================

CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,

    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    transaction_date TIMESTAMP NOT NULL,

    transaction_type VARCHAR(20) NOT NULL CHECK (
        transaction_type IN ('DEPOSIT', 'WITHDRAWAL')
    ),

    amount NUMERIC(19,2) NOT NULL CHECK (
        amount > 0
    ),

    balance NUMERIC(19,2) NOT NULL,

    account_id BIGINT NOT NULL,

    CONSTRAINT fk_transaction_account
        FOREIGN KEY (account_id)
        REFERENCES accounts(id)
);

CREATE INDEX idx_transactions_account_transaction_date
ON transactions(account_id, transaction_date);