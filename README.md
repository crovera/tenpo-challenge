# Tenpo Challenge
_____________

## Getting started

### Technologies:
- Java 18
- Spring Boot
- Maven
- Postgres
- Redis
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

### Why Redis?

JWT tokens are used to handle authentication and authorization, these tokens are stateless, therefore there isn't a way
to invalidate a token when a user logs out. There are plenty of ways to mitigate this problem.

My first approach was to use a local cache to save the tokens of the logged users, when a user logged out that token
is deleted. This way I can reject any request with a still valid token if it is not in cache.

This approach may work in development, but in a production environment where there is more than one instance running
the same application, it is not possible to control which one will service which request and therefore there is no way
to guarantee that the same instance will always respond to the same user.

A solution to this could be to store the sessions in the postgres database, which can be accessed from every instance,
however this can be really slow and very taxing for the database.

To solve this it is possible to use a distributed cache, in this case I decided to use Redis, which provide a solid
solution to persist data that can be accessed from multiple instances. Being an in-memory data store response times are
fast, which is exactly what is needed to provide a good user experience.

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

### How to rebuild from code

> Note that this requires you to have Java 18 and maven installed on your PC.

1. Install dependencies
```
mvn clean install
```
2. Run docker compose rebuild.
```
docker-compose -f docker-compose-rebuild.yml up
```
