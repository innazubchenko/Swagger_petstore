# Pet Store Test Project for BetterMe

This repository contains an automated testing project for the Pet Store API using:

- Java(at least 8 version)
- Maven
- TestNG
- RestAssured
- Allure

The goals of this project is to test the Pet Store API's due to Swagger documentation to ensure that functionality works as expected. 
Framework is created using RestAssured for API, TestNG for test run and Allure for generating UI test reports.

## Local Setup and Execution Guide

Check these steps to set up and run the project on your local machine:

### Preconditions

Ensure the following tools are installed on your machine:

- **Java**: [Download Java](https://www.oracle.com/java/technologies/downloads/)
- **Maven**: [Maven Installation Guide](https://maven.apache.org/guides/index.html)
- **Allure**: [Allure Installation Guide](https://allurereport.org/docs/install/)
- **Git** (if you need to clone the repository and track changes): [Git Installation Guide](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

### Configure the Project

1. [x] **Clone the repository to your local machine**
2. [x] **Run on your local machine:**
- Download Maven dependencies: mvn install
- Execute all tests from suite: mvn test
- Generate Allure Report: mvn allure:report
- View report from browser (HTML view): mvn allure:serve