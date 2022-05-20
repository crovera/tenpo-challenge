# Tenpo Challenge
_____________

## Getting started

### Technologies:
- Java 18
- Spring Boot
- Maven
- Postgres
- Docker
- JWT
- OpenAPI

### Project structure:

There are multiple ways to organize code in Spring Boot, each with pros and cons, in this case I decided to follow the
"package by feature" structure, this results in packages with high modularity and high cohesion, with minimal coupling
between them, which makes the project much easier to read and modify.

The following packages can be found:
- **user:** Everything related to the users, in this case the sign up.
- **session:** Login, logout and authorization, here I take advantage of spring security features,
implementing the already provided filters and handlers.
- **calculator:** Handles mathematical operations. The user must be logged in.
- **audit:** Makes a record of every operation requested to the API, saving which operation it was,
along with the username, method, time and response code. This info can be accessed by an authenticated user. 
- **shared:** Code shared between more than one feature.
- **exception:** Custom exception handler.
- **security:** Security related. In this case encoder and web security configs.
- **doc:** Project auto documentation. In this case swagger configs.
- **cache:** Cache related. In this case ehcache configs.

### How to run

1. Clone the repository.
```
git clone https://github.com/crovera/tenpo-challenge.git
```
2. Navigate to the project folder.
```
cd tenpo-challenge
```
3. Run docker compose.
```
docker-compose up
```
4. See the documentation below to know how to use it.

### How to use

1. OpenAPI/Swagger documentation:  
http://localhost:8080/swagger-ui.html

2. Postman collection:  
https://www.getpostman.com/collections/11ab08b51db95f54058e
