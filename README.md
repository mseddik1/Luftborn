# Luftborn Automation Framework
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=flat&logo=selenium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-FF6C37?style=flat&logo=testng&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat&logo=githubactions&logoColor=white)
![Allure](https://img.shields.io/badge/Allure-FF6C37?style=flat&logo=allure&logoColor=white)

> This project simulates a real-world automation system used by QA engineers to validate UI and backend services in production environments.

A scalable **UI + API automation framework** designed to validate web applications and backend services using modern automation best practices.

This project demonstrates the implementation of a **production-ready automation framework** including:

- Web UI Automation
- API Automation
- CI/CD integration
- Dockerized execution
- Parallel execution
- Advanced reporting
- Logging and debugging

---

# Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Framework Architecture](#framework-architecture)
- [Project Structure](#project-structure)
- [Test Scenarios](#test-scenarios)
- [API Key Setup](#api-key-setup)
- [Running Tests](#running-tests)
- [Running Test Suites](#running-test-suites)
- [Running with Docker](#running-with-docker)
- [CI/CD Pipeline](#cicd-pipeline)
- [Reporting](#reporting)
- [Advanced Features](#advanced-features)
- [Design Decisions](#design-decisions)
- [Conclusion](#conclusion)

---

# Project Overview

This automation framework validates both **UI workflows and backend APIs** while following **clean architecture principles**.

The framework demonstrates:

- Web UI automation using Selenium
- API automation using RestAssured
- Integration with CI/CD pipelines
- Docker containerized test execution
- Detailed reporting using Allure

The framework emphasizes:

- Maintainability
- Reusability
- Clean code practices
- Scalable test execution

---

# Tech Stack

| Technology | Purpose |
|------------|--------|
| Java | Programming language |
| Selenium WebDriver | UI automation |
| RestAssured | API automation |
| TestNG | Test framework |
| Maven | Build and dependency management |
| Allure | Test reporting |
| Docker | Containerized execution |
| GitHub Actions | CI/CD pipeline |
| SLF4J + Logback | Logging |

---

# Framework Architecture

The framework follows **clean architecture principles** and **separation of concerns**.

## Base Layer

Handles test setup and teardown.

Responsibilities:

- Driver initialization
- Environment configuration
- Test lifecycle management

Example:

```
BaseTests
```

---

## Page Object Model (POM)

UI interactions are implemented using **Page Object Model**.

Benefits:

- Separation of UI logic from test logic
- Reusable UI components
- Easier maintenance
- Improved readability of tests

Page classes handle:

- Element locators
- Page actions
- UI validations

---

## API Client Layer

API requests are abstracted using a reusable **ApiClient** and dedicated **API service classes**.

This design separates request handling from test logic and keeps tests clean and maintainable.

Benefits:

- Cleaner test classes
- Centralized API request logic
- Reusable API operations
- Easier API maintenance

Example:

```
UserService
```

---

## Utilities Layer

Contains reusable helper functions used across the framework.

Examples:

- Allure attachments
- Logging utilities
- Retry logic
- Config Manager
- Secret Manager

---

# Project Structure

```text
Luftborn
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── apis
│   │   │   │   ├── api
│   │   │   │   ├── models
│   │   │   │   └── services
│   │   │   ├── com
│   │   │   │   └── ebay
│   │   │   │       └── pages
│   │   │   └── utils
│   │   └── resources
│   │
│   └── test
│       ├── java
│       │   ├── api
│       │   │   ├── base
│       │   │   └── tests
│       │   ├── com
│       │   │   └── ebay
│       │   │          └── base
│       │   │          └── tests
│       │   ├── listeners
│       │   └── utils
│       └── resources
│               ├── config
│               ├── schemas
│               └── secret
│       
├── suites
│      └── smoke
├── target
└──  test-output
        ├── logs
        ├── screenshots
        └── visual

```



---

# Test Scenarios

## UI Automation

Application under test:

```
https://www.ebay.com
```

### Search Tests

Automated scenarios:

- User should be able to apply filters



---

## API Automation

API under test:

```
https://reqres.in/api/
```

### User API

Endpoint:

```
GET /users
```

Validations:

- Status code
- Response data validation
- JSON schema validation
- Response time



Endpoint:

```
POST /user
```

Validations:

- Status code
- Response data validation
- JSON schema validation
- Response time

---



# API Key Setup

This project requires a REQRES_API_KEY to run tests successfully.
>  Never commit your API key to version control. The `secret.properties` file is already added to `.gitignore`.

You can configure it using one of the following methods:
## Option 1: Set as Environment Variable 
### macOS / Linux
using bash:
```bash
export REQRES_API_KEY=your_api_key_here
source ~/.bashrc
```
using zsh:
```zsh
echo 'export REQRES_API_KEY=your_api_key_here' >> ~/.zshrc
source ~/.zshrc
```

###  Windows

```shell
$env:REQRES_API_KEY="your_api_key_here"
```
## Option 2: Use secret.properties File
1. Locate the file `secret-example.properties` in the `/src/test/resources/secret`
2. Copy it and rename the copy to: `secret.properties` and paste it in the same place
3. Open `secret.properties` and set your API key: `REQRES_API_KEY=your_api_key_here`

---

# Running Tests


### Run all tests:

```bash
mvn clean test
```

---

# Running Test Suites

### Run Smoke Suite

```bash
mvn test -DsuiteXmlFile=src/test/suites/smoke/Smoke_Suite_API.xml
mvn test -DsuiteXmlFile=src/test/suites/smoke/Smoke_Suite_COM.xml
```


### Run Master Suite

```bash
mvn test -DsuiteXmlFile=src/test/suites/smoke/Master_Suite.xml
```

---


---

# Running with Docker

Build Docker image:

```bash
docker build --no-cache --platform linux/amd64 -t luftborn-tests .
```

Run tests inside Docker container:

```bash
 docker run --rm --platform linux/amd64  -e REQRES_API_KEY="YOUR_REQRES_KEY" --name luftborn-container luftborn-tests
  ```


---

# CI/CD Pipeline

The project integrates with **GitHub Actions**.

The pipeline automatically:

- Runs on pull requests
- Builds the project
- Executes automation tests
- Generates test reports
- Fails the pipeline if tests fail

This ensures unstable code cannot be merged into the main branch.

---

# Reporting

The framework integrates **Allure reporting**.

Generate report locally:

```bash
allure serve target/allure-results
```

Report includes:

- Test steps
- Execution results
- Failure details
- Screenshots
- Execution timeline

---

# Advanced Features

The framework includes several advanced automation features:

- Page Object Model
- API client abstraction
- API data models for request/response serialization and deserialization
- Parallel test execution
- Cross-browser support
- Retry mechanism for flaky tests
- Automatic failure screenshots
- Structured logging
- JSON schema validation
- Dockerized test execution
- CI/CD integration
- Test tagging (smoke / regression)

---

# Design Decisions

## Page Object Model

Used to separate UI logic from test logic, improving maintainability and readability.

## API Client Abstraction

Centralizes API requests and simplifies API test maintenance.

## Docker Support

Ensures consistent execution environments across machines and CI pipelines.

## CI/CD Integration

Allows automatic test execution and prevents unstable code from being merged into the main branch.

---

# Conclusion

This project demonstrates the design and implementation of a **production-ready automation framework** combining:

- UI automation
- API automation
- DevOps integration
- Scalable Architecture

The framework follows modern **automation engineering and SDET best practices**, focusing on reliability, maintainability, and scalability.