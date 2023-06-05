# authmechanism

•	Features

-> Role-based authorization with Spring Security.

-> User registration and login with JWT authentication.

-> Password encryption using BCrypt.

•	Technologies

-->	Spring Boot 3.0.7

-->	Spring Security

-->	JSON Web Tokens (JWT)

-->	BCrypt

-->	Maven


• To get started with this project, you will need to have the following installed on your local machine:

-->JDK 8+

-->Maven 3+

-->To build and run the project, follow these steps:

• Clone the repository: git clone https://github.com/RajPradhan75/authmechanism.git

-->Navigate to the project directory

-->Build the project: mvn clean install

-->Run the project: mvn spring-boot:run

--> The application will be available at http://localhost:9090.



• API’s based on roles

Admin has access to CREATE, UPDATE AND DELETE the products

Public/Anonymous user has access to view all products

• Public API:

GET Request

http://localhost:9090/api/v1/products -> Will fetch all the products

Registration and Login API’s:

• Registration:

http://localhost:9090/api/v1/auth/register 

-> will register the user as admin or user based on email.



Example:

POST request

{   
    "firstname":"yash",
    
    "lastname":"sharma",
    
    "email":"yash@gmail.com",
    
    "password":"yash123"
    
}

• Login:

http://localhost:9090/api/v1/auth/login 

-> role based login based on credentials and generates token.

Example for User Login:

POST request

{

    "email":"raj@gmail.com",
    
    "password":"1234"
    
}

Admin Login:

POST request

{

    "email":"admin@gmail.com",
    
    "password":"admin123"   
    
}

Example of Admin based access:

POST request

http://localhost:9090/api/v1/management/products

{

    "id": 5,
    
    "name": "HP",
    
    "description": "Windows"
    
}





