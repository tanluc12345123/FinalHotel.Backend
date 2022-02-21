# Weather Backend- V1

**Weather Backend**

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [User Stories](#user-stories)
* [Weather API provider](#weather-api-provider)
* [Requirements](#requirements)
* [Notes](#notes)

## General info
A simple weather checking API service.
	
## Technologies
* Java 1.8
* Spring Boot 2.4.3
* MySQL 8
## Project Architecture
The project has built base on Onion Architecture. ***The Layers Responsibilities:***

***Bootstrap (weather)***

* Contains all the configuration files, import all the Spring configurations files and responsible to make the application as a whole works

***Application Service (weather-api)***

* Application layer without business rules & knowledge: application services, transactional aspects, security aspects, etc.

***Domain (weather-domain)***

* Take care of all the business rules, and business are inside DomainEntity

* Handle all the objects of the domain
* Don't know anything about Spring, JPA, Jackson (JSON) or any other framework (Lombok excepted)

* Domain service call the domain repositories

* Contains repository interfaces for Data layer

**Infrastructure (data-jpa/data-rest/message-producer)**

***Data-jpa***

* Handle connection with DB (data-jpa)
* Contain adapters for Domain repositories/stores which have an implementation in JPA
* Map object from JPA entities to/from Domain objects 

***Data-rest***

* Handle connection to dependent REST endpoint (clients)
* Contain adapters for Domain repositories/stores which have an implementation in REST calls
* Map DTO from Domain objects
## Setup
**Prerequisites:**

You need to update the database config to point to your desired database first.
After then execute 2 sql files: weather_city.sql and weather_weather.sql
**Run:**
```
$ ./gradlew clean build
$ ./gradlew :weather:bootRun

```
**Now we can test out API in our browser by visiting:**
```
http://localhost:8080/swagger-ui.html
```

## User stories

|Requirements|User Story|Priority|
|---|---|---|
|Search for today weather of a specific city|As an API consumer, I should be able to search for today's weather by inputting the city name.|High|
||||
|Save weather data|As an API consumer, I should be able to save weather data for retrieval|High|
||||
|Get historical weather data|As an API consumer,  I should be able to look for weather data from past periods|High
||||
|Delete historical weather data|As an API consumer,  I should be able to delete an existing weather record|Medium
||||
|Update historical weather data|As an API consumer, I should be able to update an existing weather record|Medium
||||
|Able to ensure existing function is working|As an API contributor, I should be able to make sure my contribution won’t break existing function|Low

## Weather API Provider

https://openweathermap.org/current
> Access current weather data for any location on Earth including over 200,000 cities! We collect and process weather data from different sources such as global and local weather models, satellites, radars and vast network of weather stations. Data is available in JSON, XML, or HTML format.

## Requirements
* The server should be written in Java Spring / Spring Boot and Hibernate. The client will consume RESTFUL APIs provided by the server.
* Follow coding best practices.
* Understanding of API Designs (both REST and GraphQL are accepted with no preference)
* Understanding  of Spring Boot framework
* Show considerations for Speed, Efficiency, and Scalability in your APIs and Database design and coding
* Show how existing functions can be tested efficiently when introducing new feature

## Notes
* Ensure that you understand the requirements of the assignment before continuing.
* Add in adequate comments to explain your codes. (If you need to write a documentation to explain your solution, please do so)
* Tell the consumer how to consume your API. Documentation is great, actual way to run your APIs are even greater.
* Design and attach your .sql file when you submit your project.
* If additional setup is required, please update README.md that explains the steps to get the assignment working.
* Think about how big the data can become and what to do about it
* Think about scaling your app. You do not need to implement it now.
