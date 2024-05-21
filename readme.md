# Simple Lucene Search Application

This is a simple search application built with Java Spring Boot and Apache Lucene. The application provides functionality for indexing and searching documents. A TermQuery is used as an example to demonstrate the Search Process.

## Prerequisites

- Java 17 or higher
- Maven
- Apache Lucene core and analyzers
- Spring Boot
- Spotless Java

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/swethakann/simple-lucene-search.git
cd simple-lucene-search
```

### Build the Project
```sh
mvn clean install
```

### Run the application
```sh
mvn spring-boot:run
```

## API Endpoints

### Index a Document
This is a GET and not a POST request since we index a static set of files given in this repository for demonstration purposes.

Endpoint: GET /index

#### Example
```sh
curl -X GET "http://localhost:8080/index"
```

### Delete an index
Endpoint: DELETE /delete

#### Example
```sh
curl -X DELETE "http://localhost:8080/delete"
```

### Search
Endpoint: POST /search

#### Example
```sh
curl -X POST http://localhost:8080/search \
     -H "Content-Type: application/json" \
     -d '{
           "type": "TermQuery",
           "maxHits": 4,
           "fieldName": "fileContent",
           "fieldValue": "lucene"
         }'
```

## Acknowledgements
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Apache Lucene](https://lucene.apache.org/)





