##  REST API Implementation with JWT authentication and caching
### we are using Formula 1 datasource provided by `https://ergast.com/mrd/`
This project demonstrates the implementation of security using Spring Boot 3.0 and JSON Web Tokens (JWT). It includes the following features:

### Features
* User registration with JWT authentication (using in-memory h2 database)
* Using spring cache to reduce load on Ergast API servers and reduce the response time 
* Using WebClient to access data



### Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+


To build and run the project, follow these steps:

* Clone the repository: `git clone `
* Navigate to the project directory: cd `Ergast-API`
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run

### The application is using JWT authentication as its security level, so in order to be able to use it first step is to register your user in the system
* the steps to register in the app is as follows:
* use `Postman` or any other appropriate platform you have to send a `POST` request
* use this url `http://localhost:8080/api/v1/auth/register` with the following body:
    ```
  {
    "firstname": "your name",
    "lastname": "your lastname",
    "email": "youremail@email.com",
    "password": "your password"
  }
  ```
* send your `POST` request
* you will receive `accessToken` in Response body
* copy it and use it for the upcoming API calls like the instructions

### how to use `accessToken`
* when creating your GET request to call the endpoints, you should use the generated `accessToken` within the  `Authorization` tab. Set the `Type` to `Bearer Token` and use the `accessToken` from previous step as the `Token` value
* now you should be authorized to use any of the following app APIs

### List of API calls available within this project

#### http://localhost:8080/api/v1/seasons
* returns list of seasons
#### http://localhost:8080/api/v1/season/{season}/races
* returns listing of the races for a specific season
* {seasons} should be replaced with some season value
* example: http://localhost:8080/api/v1/season/2012/races

#### http://localhost:8080/api/v1/season/{season}/finals
* returns listing of the final standing of drivers with pilot, points and constructors for a specific season
* {season} should be replaced with some season value
* example: http://localhost:8080/api/v1/season/2012/finals

#### http://localhost:8080/api/v1/season/{season}/race/{race}/qualifying
* When selecting a race returns the starting grid with the position, pilot name and qualifying time for each pilot
* {season} & {race} should be replaced with appropriate values
* example: http://localhost:8080/api/v1/season/2012/race/5/qualifying

#### http://localhost:8080/api/v1/season/{season}/race/{race}/results
* When selecting a season and race, it returns the result of the race with the position, pilot name and number of points for each pilot
* {season} & {race} should be replaced with appropriate values
* example: http://localhost:8080/api/v1/season/2012/race/5/results


note that we are using  spring provided cache to store the responses to prevent extra hits and provide faster response
you can check this by comparing response time when hitting the same request.