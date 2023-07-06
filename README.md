# Project Description: 

This backend project implements a Login-Registration System using Spring Boot, PostgreSQL for database storage, and Redis for session management. It provides secure user authentication, registration, and efficient session handling for web applications.


# Key Features:

* User Registration: 
Users can register by providing their name, email, and password. The system ensures the uniqueness of email to maintain data integrity

* User Authentication: 
Registered users can securely log in using their credentials. The system verifies the provided credentials and grants access upon successful authentication.

* Password Security: 
Passwords are securely stored using a hashing algorithm, guaranteeing the confidentiality of user data.

* Email Validation: 
The system validates email addresses during registration to ensure they adhere to the required format and are valid.

* Persistent Storage: 
User data, including registration details and credentials, is stored in a PostgreSQL database. This allows for efficient data retrieval and management.

* Session Management: 
Redis is utilized for session management, providing a scalable and high-performance solution for storing and managing user sessions. This ensures seamless user authentication across multiple requests and enhances system performance.

* Error Handling: 
Comprehensive error handling mechanisms are implemented to provide meaningful error messages and appropriate HTTP status codes in case of exceptions or validation failures.

 
