# Library Management API

## Description

A separate Spring Boot REST application for managing books in an in-memory `ArrayList`. It does not use MySQL or JPA. Data is cleared when the application stops.

## Technologies

- Java 21
- Maven
- Spring Boot 3.5.16
- Spring Web

Spring Boot 3.5.16 supports Java 21; the Spring system requirements list Java 17 as its minimum. See the [official system requirements](https://docs.spring.io/spring-boot/3.5/system-requirements.html).

## Structure

```text
LibraryManagementAPI/
├── pom.xml
├── README.md
├── requests.http
└── src/main/java/org/example/library/
    ├── LibraryManagementApiApplication.java
    ├── controller/BookController.java
    └── model/Book.java
```

## Run

Open `LibraryManagementAPI` as a Maven project in IntelliJ, select Java 21, reload Maven, and run `LibraryManagementApiApplication`. Or, from this directory, run:

```text
mvn spring-boot:run
```

The API listens at `http://localhost:8080`. No database configuration is required.

## Endpoints

| Method | Path | Result |
|---|---|---|
| POST | `/books` | Adds the supplied book; returns 201. |
| GET | `/books` | Returns all books; returns 200. |
| GET | `/books/{id}` | Returns one book; returns 404 if missing. |
| PUT | `/books/{id}` | Updates title, author, category, and copies; returns 404 if missing. |
| DELETE | `/books/{id}` | Deletes a book; returns 204, or 404 if missing. |

POST requires a unique `id`; a missing or duplicate ID returns 409 Conflict. The URL ID controls PUT, and the request body's `id` is ignored during update.

## Sample requests and responses

Add a book (`POST /books`, `Content-Type: application/json`):

```json
{
  "id": 1,
  "title": "Effective Java",
  "author": "Joshua Bloch",
  "category": "Programming",
  "availableCopies": 4
}
```

Response: **201 Created**, with the same JSON book in the body.

`GET /books` returns **200 OK** and a JSON array, for example:

```json
[
  { "id": 1, "title": "Effective Java", "author": "Joshua Bloch", "category": "Programming", "availableCopies": 4 }
]
```

`GET /books/1` returns **200 OK** with that book. A missing ID returns **404 Not Found**.

Update (`PUT /books/1`):

```json
{
  "id": 1,
  "title": "Effective Java, Third Edition",
  "author": "Joshua Bloch",
  "category": "Programming",
  "availableCopies": 6
}
```

Response: **200 OK** with the updated book. A missing ID returns **404 Not Found**.

`DELETE /books/1` returns **204 No Content**. A missing ID returns **404 Not Found**. The complete request sequence, including missing-ID cases, is in `requests.http`; open it in IntelliJ and use **Run** on each request while the app is running.

## Testing

Run the API, then execute the requests in `requests.http` in order. Verify POST returns 201, GET calls return the inserted book, PUT returns the changed fields, DELETE returns 204, and missing GET/PUT/DELETE calls return 404. Because storage is in memory, POST the sample book again after restarting the application.
