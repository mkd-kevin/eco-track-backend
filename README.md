# EcoTrack Backend

## Project Information

**Project Name:** EcoTrack Backend  
**Student Name:** Manzi Kevin 
**Student ID:** 28242
**University:** Adventist University of Central Africa (AUCA)  
**Course:** INSY 8322 – Web Technology and Internet

## Project Description

EcoTrack is a waste collection management backend application developed using Spring Boot and PostgreSQL.

The system supports the management of residents, collectors, collection areas, waste types, collection requests, and waste records. It also provides reporting, bulk updates, dashboard statistics, and pagination.

## Technologies Used

- Java 21
- Spring Boot 3.5.16
- Spring Data JPA
- PostgreSQL 18
- Maven
- Postman
- Git and GitHub

## Requirements

Before running the application, install:

- Java Development Kit (JDK) 21
- PostgreSQL
- Postman (for API testing)

## Database Configuration

Create a PostgreSQL database named:

`ecotrack_fresh_db`

The application uses the following database configuration in `application.properties`:

- Database URL: `jdbc:postgresql://localhost:5432/ecotrack_fresh_db`
- Database username: `postgres`
- Database password: supplied through the `DB_PASSWORD` environment variable.

For security reasons, I can advice to do not store or commit database passwords or other sensitive credentials in the repository.

## How to Run the Application

1. Clone the repository:

```bash
git clone https://github.com/mkd-kevin/eco-track-backend.git
```

2. Open the project directory:

```bash
cd eco-track-backend
```

3. Set the PostgreSQL password in PowerShell:

```powershell
$secure = Read-Host "Enter PostgreSQL password" -AsSecureString
$env:DB_PASSWORD = [System.Net.NetworkCredential]::new("", $secure).Password
```

4. Run the Spring Boot application:

```powershell
.\mvnw.cmd spring-boot:run
```

5. The application runs at:

`http://localhost:8080`

## API Testing

The backend APIs can be tested using Postman.

The Postman collection will be provided as:

`eco-track-midterm.postman_collection.json`

Import this file into Postman to test the project endpoints.

## GitHub Repository

https://github.com/mkd-kevin/eco-track-backend
