Ktor Server Demo
================

This repository contains a demo project showcasing the use of Ktor, a Kotlin framework for building asynchronous servers. The demo illustrates how to handle various HTTP methods (GET, POST, PUT, DELETE), implement JWT token authentication, and manage user authentication. This project serves as a practical example for developers looking to understand the basics of setting up a Ktor server and implementing essential features for a web application.

Features
--------

*   **HTTP Method Handling**: Demonstrates how to handle GET, POST, PUT, and DELETE requests.
*   **JWT Token Authentication**: Shows how to generate and validate JWT tokens for secure API endpoints.
*   **User Authentication**: Implements basic user authentication mechanisms, including registration and login endpoints.

Project Structure
-----------------

*   **Main Application**: Entry point of the Ktor server.
*   **Routing**: Defines routes for handling different HTTP methods and endpoints.
*   **Authentication**: Contains logic for user authentication and JWT token management.
*   **Models**: Data models used in the application.
*   **Utilities**: Helper functions and utilities.

Getting Started
---------------

### Prerequisites

*   Kotlin
*   Gradle

### Installation

    
    1. Clone the repository:
       git clone https://github.com/yourusername/ktor-server-demo.git
       cd ktor-server-demo
    
    2. Build the project:
       ./gradlew build
    
    3. Run the server:
       ./gradlew run
            

### API Endpoints

*   **GET /items**: Retrieve a list of items.
*   **POST /items**: Create a new item.
*   **PUT /items/{id}**: Update an existing item by ID.
*   **DELETE /items/{id}**: Delete an item by ID.

### Authentication

*   **POST /register**: Register a new user.
*   **POST /login**: Authenticate a user and return a JWT token.
*   **Protected Endpoints**: Use the provided JWT token to access secured endpoints.

### Usage

    
    1. Register a new user:
       curl -X POST \
        http://127.0.0.1:8080/api/v1/auth/register \
        -H 'Content-Type: application/json' \
        -d '{
        "email": "pankaj.sharma@yopmail.com",
        "fullName": "Pankaj",
        "password": "Pankaj@123"
       }'
    
    2. Login:
       curl -X POST \
        http://127.0.0.1:8080/api/v1/auth/login \
        -H 'Content-Type: application/json' \
        -d '{
        "email": "pankaj.sharma@yopmail.com",
        "password": "Pankaj@123"
       }'
    
    3. Access user info (secured endpoint):
       curl -X GET "http://0.0.0.0:8080/api/v1/user" -H "Authorization: Bearer eyJhbGciOiJMCIsInN"
            

Contributing
------------

Contributions are welcome! Please fork the repository and submit a pull request.

License
-------

Copyright 2024 Pankaj

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

This demo project provides a foundational understanding of Ktor and its capabilities. It can be extended and customized to fit various application needs. Happy coding!
