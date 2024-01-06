# Warba Banking System

### Technologies Used

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

* **URL:** jdbc:postgresql://localhost:5432/warba_bank_customer_db
* **Username:** postgres
* **Password:** 12345678

### Service Configuration

* **Service Port:** 9101
* **Application Name:** account-service
* **Eureka Client Configuration:** Registers with Eureka Server at http://localhost:8761/eureka

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
* **Server Port:**  9001 
* **Application Name:** customer-service Eureka Client Configuration: Connects to Eureka Server at http://localhost:8761/eureka
 
### Features 
* **Create Customer:** POST /customers/create - Creates a new customer. 
* **Get Customer:** GET /customers/{customerCode} - Retrieves a customer based on their code. Block
* **Customer:** POST /customers/block/{customerCode} - Blocks a customer account based on their code. Validation The service
performs thorough validation checks for customer data:


### Notification Service
The Notification Service within the Warba Banking System handles notifications via email and SMS to customers in case of Account Registration.

### Configuration
Ensure a PostgreSQL database is running with the following configurations:

* **URL:** jdbc:postgresql://localhost:5432/warba_bank_customer_db
* **Username:** postgres
* **Password:** 12345678


### Configure the Kafka server
* **Bootstrap Servers:** localhost:9092
* **Consumer Group ID:** notification


### Service Configuration
* **Server Port:** 9301
* **Application Name:** notification-service
* **Eureka Client Configuration:** Connects to Eureka Server at http://localhost:8761/eureka


### Kafka Configuration
Listens to topics **sms-notification** and **mail-notification** for SMS and Email notifications respectively.

### Features
* **Sending SMS Notification:** Listens to sms-notification Kafka topic.
* **Sending Email Notification:** Listens to mail-notification Kafka topic.
* **Storing Notifications:** Persists notification details in the PostgreSQL database.


## **Eureka Server**
The Eureka Server acts as a service registry in the Warba Banking System, enabling service discovery and registration.

### Configuration
* **Server Port:** 8761
* **Application Name:** eureka-server

### Features
**Service Discovery:** Allows services to register themselves and discover other services.


## **API Gateway**
The API Gateway in the Warba Banking System serves as an entry point, managing and routing incoming requests to the appropriate services.

### Configuration
* **Server Port:** 9201
* **Application Name:** api-gateway

### Routes Configuration
The API Gateway manages routing for the following services:

**Customer Service:**

* **Base URL:** /customer-service/**
* **Service Name:** CUSTOMER-SERVICE
* **Route Name:** customer-service-route

**Account Service:**

* **Base URL:** /account-service/**
* **Service Name:** ACCOUNT-SERVICE
* **Route Name:** account-service-route

### Features
**Routing:** Directs incoming requests to the corresponding microservices based on defined routes.



##Missing Parts
1. **Spring Security Implementation**
 
**Objective:** Secure endpoints and manage access to system resources.
   Possible Implementation:
   **Authentication:** 
* **Authentication:** Implement various authentication mechanisms like JWT, OAuth, or basic authentication.
* **Authorization:** Define roles, permissions, and access control based on user roles.
* **Secure Endpoints:** Configure security for APIs and endpoints to restrict unauthorized access.

2. **Auditing Fields in Entities**
   **Objective:** Track and manage entity-level changes, including creation, modification, and deletion.
**Potential Modification:**
* **Entity Auditing:** Add auditing fields (e.g., lastModifiedBy, lastModifiedDate) to the existing entities (Account and Customer).
* **Implement Listeners:** Use JPA entity listeners or Spring Data JPA's auditing features to automatically update these fields on entity modifications.
* **Integration with Spring Data:** Utilize Spring Data's auditing capabilities for automatic population of auditing fields.