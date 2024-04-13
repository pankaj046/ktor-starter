# Project Name

## Project Start
Brief description of what the project is about and how to get started with it.

## Installation
Steps to install the project and any dependencies needed.

## Deployment
Instructions on how to deploy the project to a live environment.

## Usage
Guidelines on how to use the project and any examples if applicable.

## Contributing
Information on how others can contribute to the project.

## Contact
Ways to reach out in case of questions or feedback.


## CURl Request
Register

`curl -X POST \
    http://127.0.0.1:8080/api/v1/auth/register \
    -H 'Content-Type: application/json' \
    -d '{
    "email": "pankaj.sharma@yopmail.com",
    "fullName": "Pankaj",
    "password": "Pankaj@123"
}'`

Login

`curl -X POST \
    http://127.0.0.1:8080/api/v1/auth/login \
    -H 'Content-Type: application/json' \
    -d '{
    "email": "pankaj.sharma@yopmail.com",
    "password": "Pankaj@123"
}'`

USER Info

`curl -X GET "http://0.0.0.0:8080/api/v1/user" -H "Authorization: Bearer eyJhbGciOiJMCIsInN"
`