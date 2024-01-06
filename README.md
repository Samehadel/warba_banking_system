# Warba Banking System

### Prerequisites

* Java V21
* Spring Boot Dependencies V3.2.1
* Spring Cloud Dependencies V2023.0.0
* Maven
* PostgreSQL
* Hibernate
* Kafka

## **Account Service**

The Account Service within the Warba Banking System is responsible for managing customer accounts, providing
functionalities for account creation, retrieval, and status management.

### Configuration

The running spring profile is dev. If used, ensure a PostgreSQL database is running with the following configurations:

* URL: jdbc:postgresql://localhost:5432/warba_bank_customer_db
* Username: postgres
* Password: 12345678

### Service Configuration

* Service Port: 9101
* Application Name: account-service
* Eureka Client Configuration: Registers with Eureka Server at http://localhost:8761/eureka

### Features

* Create Account: **POST** /accounts/create - Creates a new account for a customer.
* Get Accounts: **GET** /accounts/{customerCode} - Retrieves accounts associated with a customer.
* Block Account: **POST** /accounts/block/{accountNumber} - Blocks an account based on the account number.

### Integrations

* **Feign Client - Customer Service:** Interacts with the Customer Service for customer-related operations.
* **Kafka Integration:** Sends notifications (email or SMS) upon account creation.



## Customer Service

The Customer Service within the Warba Banking System manages customer-related operations, including creation, retrieval,
and status management.

### Configuration:

The running spring profile is dev. If used, ensure a PostgreSQL database is running with the following configurations:

* **URL**: jdbc:postgresql://localhost:5432/warba_bank_customer_db
* **Username**: postgres
* **Password**: 12345678

### Service Configuration 
* Server Port:  9001 
* Application Name: customer-service Eureka Client Configuration: Connects to Eureka Server at http://localhost:8761/eureka
 
### Features 
* Create Customer: POST /customers/create - Creates a new customer. 
* Get Customer: GET /customers/{customerCode} - Retrieves a customer based on their code. Block
* Customer: POST /customers/block/{customerCode} - Blocks a customer account based on their code. Validation The service
performs thorough validation checks for customer data:


