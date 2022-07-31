# microservice-springboot

This repo contains code for a sample project using microservices architecture in Spring Boot. There are two microservices: User microservice and Department microservice.
These two microservices speak with each other through a service registry which is basically Eureka server with which the two microservices register themselves as clients. A cloud gateway is configured for routing the requests to the individual microservice. Additionally, a config server repo is created for fetching the Eureka client config from the github repo(https://github.com/vvj01/config-server).
