# Juna

## Overview

Juna is a multifaceted software project, composed of various microservices. The project involves using a number of different languages and technologies, such as Vue.js, Java, JavaScript, and HTML.

## Components

The project consists of several components:

- `authentication-service`: A service handling user authentication.
- `data-analytics`: A module for data analysis.
- `eureka-server`: A service registry for microservices.
- `frontend`: The user interface of the application, created with Vue.js.
- `gateway-service`: A service that acts as a gateway to other services.
- `mentor-service`: A service related to mentor functionality.
- `vacancies-service`: A service that manages job vacancies.

## Getting Started

In order to launch application locally you need Docker to be up and running. Go to docker-compose.yml file and start all services. 
 `eureka-server` should be started before all other BE services.
 In order to start `frontend` do `npm run serve` from project root.

## Stack
- Java, Vue.js, JS, HTML, CSS
- Spring Boot, Data, Security, Cloud
- PosgreSQL, MongoDB
- Docker
