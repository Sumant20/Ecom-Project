# 🛒 Ecom-Project

This is the backend service for an internal e-commerce project developed at Cognizant. It exposes RESTful APIs for product management tasks such as adding, updating (with image support), deleting, and searching products by keywords. The application is built using **Spring Boot** and integrates **MySQL** via **Spring Data JPA**. Security is enforced using **JWT-based authentication**, ensuring that only authorized users can access the endpoints. The frontend is developed separately using **React.js**.

## 🔧 Technologies Used

- Java 11
- Spring Boot
- Spring Data JPA
- MySQL
- JWT (JSON Web Tokens)
- Spring Security
- Postman
- Maven

## 🚀 Features

- **Product Management APIs:**
  - Add new products
  - Update products (including image upload functionality)
  - Delete products
  - Search products by keyword

- **Authentication & Security:**
  - JWT-based login and access control
  - Role-based endpoint access
  - Spring Security integration for endpoint protection

- **Database Integration:**
  - MySQL for persistent storage
  - Spring Data JPA for ORM and repository abstraction

- **API Testing:**
  - All endpoints manually tested via Postman
  - Authorization headers configured for protected routes

