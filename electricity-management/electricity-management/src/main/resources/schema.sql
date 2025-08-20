-- Run these statements manually in Derby ij or any SQL tool connected to Derby Network Server

CREATE TABLE customers (
  customer_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  login_id VARCHAR(100) UNIQUE NOT NULL,
  name VARCHAR(200) NOT NULL,
  email VARCHAR(200) NOT NULL,
  address VARCHAR(500) NOT NULL,
  phone VARCHAR(50) NOT NULL,
  password VARCHAR(200) NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER'
);

CREATE TABLE bills (
  bill_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  customer_id INT NOT NULL,
  meter_reading DOUBLE NOT NULL,
  bill_amount DOUBLE NOT NULL,
  paid_amount DOUBLE NOT NULL DEFAULT 0,
  month VARCHAR(20) NOT NULL,
  year INT NOT NULL,
  status VARCHAR(20) NOT NULL,
  CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Seed admin user
INSERT INTO customers (login_id,name,email,address,phone,password,role)
VALUES ('admin','Administrator','admin@example.com','-','-','admin','ADMIN');

