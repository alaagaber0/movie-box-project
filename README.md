# MovieBox

MovieBox is a full-stack web application for managing and browsing a movie collection. It consists of:

- **Backend**: Java 17, Spring Boot, Spring Data JPA, Spring Security (JWT), MySQL
- **Frontend**: Angular 16 (Template-driven)

---

## Table of Contents
1. [Features](#features)
2. [Tech Stack](#tech-stack)
3. [Prerequisites](#prerequisites)
4. [Database Setup](#database-setup)
5. [Backend Setup](#backend-setup)
6. [Frontend Setup](#frontend-setup)
7. [Configuration Files](#configuration-files)
8. [API Endpoints](#api-endpoints)
9. [Project Structure](#project-structure)
10. [Contributing](#contributing)

---

## Features
- User authentication (JWT)
- Role-based access control:
  - **Admin**: Add single movie, add batch movies, delete single or batch movies, search by title
  - **User**: List movies, view movie details, search by title
- CRUD operations through REST API
- Simple, responsive Angular frontend with client-side validation

## Tech Stack
- **Backend**
  - Java 17
  - Spring Boot
  - Spring Data JPA (Hibernate)
  - Spring Security with JWT
  - MySQL
- **Frontend**
  - Angular 16
  - FormsModule (Template-driven forms)
  - HttpClientModule

## Prerequisites
- Java 17 JDK
- Maven or Gradle
- MySQL Server
- Node.js & npm
- Angular CLI (v16)

## Database Setup
1. Create a new MySQL database (e.g., `moviebox`):
   ```sql
   CREATE DATABASE moviebox;
   ```
2. Configure connection in `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/moviebox?useSSL=false&serverTimezone=UTC
   spring.datasource.username=YOUR_DB_USER
   spring.datasource.password=YOUR_DB_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

## Backend Setup
1. Navigate to the **backend** folder:
   ```bash
   cd backend
   ```
2. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   Or with Gradle:
   ```bash
   ./gradlew bootRun
   ```
3. The server will start on `http://localhost:8080`.

## Frontend Setup
1. Navigate to the **moviebox** folder:
   ```bash
   cd moviebox
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Serve the application:
   ```bash
   ng serve --open
   ```
4. The app will be available at `http://localhost:4200`.

## Configuration Files
- **Backend**: `backend/src/main/resources/application.properties` (database URL, credentials, JWT settings)
- **Frontend**: `moviebox/src/environments/environment.ts`
  ```ts
  export const environment = {
    production: false,
    apiBase: 'http://localhost:8080'
  };
  ```

## API Endpoints
**Public**
| Method | Endpoint            | Description         |
|--------|---------------------|---------------------|
| POST   | `/auth/register`    | Register new user   |
| POST   | `/auth/login`       | Authenticate user   |

**Admin (Requires Authorization)**
| Method | Endpoint                              | Description                         |
|--------|---------------------------------------|-------------------------------------|
| GET    | `/admin/api/movies?title={title}`     | List or search movies by title      |
| POST   | `/admin/api/add`                      | Add a single movie                  |
| POST   | `/admin/api/add_batch`                | Add multiple movies in batch        |
| DELETE | `/admin/api/delete_batch`             | Delete multiple movies by IDs       |
| DELETE | `/admin/api/{id}`                     | Delete a single movie by ID         |

**User (Requires Authorization)**
| Method | Endpoint                              | Description                         |
|--------|---------------------------------------|-------------------------------------|
| GET    | `/user/api/movies`                    | List all movies                     |
| GET    | `/user/api/{id}`                      | Get movie details by ID             |
| GET    | `/user/api/search?title={title}`      | Search movies by title              |

## Project Structure
```
moviebox-root/
├── backend/          # Spring Boot application
│   ├── src/main/java
│   ├── src/main/resources/application.properties
│   └── pom.xml or build.gradle
└── moviebox/         # Angular frontend
    ├── angular.json
    ├── package.json
    └── src/
        └── app/
            ├── login/
            ├── register/
            ├── admin-dashboard/
            ├── user-dashboard/
            └── shared/ (optional for common components)
```

## Contributing
1. Fork the repository  
2. Create a feature branch (`git checkout -b feature/YourFeature`)  
3. Commit your changes (`git commit -m 'Add YourFeature'`)  
4. Push to the branch (`git push origin feature/YourFeature`)  
5. Open a Pull Request

---
*This README was generated based on the project specifications.*