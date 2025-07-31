# Kotlin Spring Boot Article API (Clean Architecture Refactor)

This project is a Spring Boot REST API for managing articles, refactored to follow Clean Architecture principles. It demonstrates a clear separation of concerns, making the codebase more modular, testable, and maintainable.

## Table of Contents

- [Kotlin Spring Boot Article API (Clean Architecture Refactor)](#kotlin-spring-boot-article-api-clean-architecture-refactor)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
  - [Architecture](#architecture)
  - [Project Structure](#project-structure)
  - [Technologies Used](#technologies-used)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Running the Application](#running-the-application)
  - [API Endpoints](#api-endpoints)
    - [Articles (`/api/v1/articles`)](#articles-apiv1articles)
  - [Contributing](#contributing)
  - [License](#license)

## Overview

The `KotlinSpringArticleApi` provides a simple RESTful interface for performing CRUD (Create, Read, Update, Delete) operations on articles. The primary goal of this repository is to showcase the application of Clean Architecture in a Kotlin Spring Boot environment, demonstrating how to achieve:

* **Framework Independence:** Core business logic is independent of Spring, JPA, or web frameworks.
* **Testability:** Business rules can be tested in isolation without external dependencies.
* **Maintainability:** Clear separation of concerns reduces complexity and facilitates easier changes.

## Architecture

This project is structured around the principles of Clean Architecture, dividing the codebase into distinct layers with strict dependency rules:

1.  **Domain Layer (Core Business Rules):**
    * Contains the `Article` entity (pure Kotlin data class, no framework annotations).
    * Defines **Ports** (interfaces) for inbound operations (Use Cases like `CreateArticleUseCase`) and outbound operations (Repository interfaces like `ArticleRepositoryPort`).
    * **Dependencies:** None (this is the innermost layer).

2.  **Application Layer (Application Business Rules):**
    * Implements the Use Case interfaces defined in the Domain Layer (e.g., `CreateArticleService`). These services contain the application-specific business logic.
    * Contains **Adapters** that implement the outbound ports (e.g., `ArticlePersistenceAdapter` implements `ArticleRepositoryPort` and uses Spring Data JPA internally).
    * Includes utility functions like `SlugGenerator`.
    * **Dependencies:** Depends only on the Domain Layer.

3.  **Presentation Layer (UI/API Layer):**
    * Consists of Spring `RestController`s (`ArticleController`).
    * Defines **Data Transfer Objects (DTOs)** for request and response payloads (`CreateArticleRequest`, `UpdateArticleRequest`, `ArticleDto`).
    * Handles HTTP requests, maps DTOs to Use Case commands, invokes Use Cases, and maps domain results back to DTOs for responses.
    * Handles presentation-specific error mapping (e.g., `NoSuchElementException` to `404 Not Found`).
    * **Dependencies:** Depends on the Application Layer and its own DTOs.

4.  **Infrastructure Layer (External Details):**
    * (Implicitly handled by Spring Data JPA and H2 Database). This layer would contain the concrete implementations for database access (our `ArticleJpaEntity` and `SpringArticleRepository` are the adapters bridging to this, but the actual JPA framework and database drivers are the infrastructure).
    * **Dependencies:** Depends on all inner layers and external frameworks/tools.

The key principle is the **Dependency Rule**: Dependencies always flow inwards. The outer layers depend on inner layers, but inner layers have no knowledge of outer layers.

## Technologies Used

* **Kotlin:** Primary programming language.
* **Spring Boot:** Framework for building the application.
* **Spring Data JPA:** For database interaction.
* **H2 Database:** In-memory database for development and testing.
* **Gradle:** Build automation tool.

## Getting Started

### Prerequisites

* JDK 17 or higher
* Gradle (usually bundled with Spring Boot projects)
* Git

### Running the Application

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/DaveCDowdy/KotlinSpringArticleApi.git](https://github.com/DaveCDowdy/KotlinSpringArticleApi.git)
    cd KotlinSpringArticleApi
    ```
2.  **Run the application:**
    ```bash
    ./gradlew bootRun
    ```
    (On Windows, use `.\gradlew.bat bootRun`)

    The application will start on `http://localhost:8080`.

## API Endpoints

The API is exposed under the `/api/v1/articles` path.

### Articles (`/api/v1/articles`)

| Method | Endpoint | Description | Request Body (JSON) | Response Body (JSON) | HTTP Status |
| :----- | :------- | :---------- | :------------------ | :------------------- | :---------- |
| `POST` | `/api/v1/articles` | Creates a new article. | `{"title": "...", "content": "..."}` | `ArticleDto` | `201 Created` / `409 Conflict` |
| `GET` | `/api/v1/articles` | Retrieves all articles. | N/A | `List<ArticleDto>` | `200 OK` |
| `GET` | `/api/v1/articles/{slug}` | Retrieves an article by its slug. | N/A | `ArticleDto` | `200 OK` / `404 Not Found` |
| `PUT` | `/api/v1/articles/{slug}` | Updates an existing article. | `{"title": "...", "content": "..."}` | `ArticleDto` | `200 OK` / `404 Not Found` / `409 Conflict` |
| `DELETE` | `/api/v1/articles/{slug}` | Deletes an article by its slug. | N/A | N/A | `204 No Content` / `404 Not Found` |

## Contributing

Feel free to open issues or submit pull requests if you have suggestions for improvements or find any bugs.

## License

This project is open-sourced under the MIT License. See the `LICENSE` file for more 
