# Mpesa Express Java API

This is a Java Spring Boot application that provides RESTful API endpoints for integrating with the Mpesa Express (STK Push) service.

## Prerequisites

- Java JDK (version 8 or later)
- Maven
- Spring Boot
-

## Getting Started

1. Clone the repository:

```bash

   git clone https://github.com/okonueugene/mpesa-express-java.git
   cd mpesa-express-java

```

2. Build and run the application:

```bash

 mvn clean install
 java -jar target/mpesa-express-java-1.0.jar

```

3. Test the application:

Using Postman, send a POST request with parameters phoneNumber and amount to the following endpoint:

    http://localhost:8080/api/v1/mpesa/stkpush

The response should be:

```json
{
  "merchantRequestID": "12345-67890-12345",
  "checkoutRequestID": "12345-67890-12345",
  "responseCode": "0",
  "responseDescription": "Success. Request accepted for processing",
  "customerMessage": "Success. Request accepted for processing"
}
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

### Reference Documentation

For further reference, please consider the following sections:

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/)
- [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/#build-image)
- [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#web)
- [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)

### Guides

The following guides illustrate how to use some features concretely:

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
- [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

## Acknowledgments

- [Safaricom Developer Portal](https://developer.safaricom.co.ke/)
- [Mpesa Express API](https://developer.safaricom.co.ke/docs?javascript#lipa-na-m-pesa-online-payment-api)
- [Mpesa Express API - Postman Collection](https://documenter.getpostman.com/view/8854915/SVtN3Wzy?version=latest)
