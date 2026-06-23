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