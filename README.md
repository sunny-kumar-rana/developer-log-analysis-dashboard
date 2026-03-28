# Developer Log Analysis Dashboard

## Overview

A web-based log analysis tool built using Java Servlets, JSP, and JDBC. The application allows users to upload log files, parse them, store structured data in a database, and perform searches and analysis through a simple dashboard interface.

## Features

* Upload log files via web interface
* Parse raw logs into structured data
* Store logs in a relational database
* Search logs based on different criteria
* Dashboard view for quick insights
* Modular architecture (DAO, Service, Servlet layers)

## Tech Stack

* **Backend:** Java (Servlets, JSP)
* **Database:** Oracle (or any JDBC-compatible DB)
* **Build Tool:** Maven
* **Frontend:** JSP, HTML, CSS
* **Server:** Apache Tomcat

## Project Structure

```
src/
 ├── main/
 │   ├── java/com/logtool/
 │   │   ├── dao/            # Database access layer
 │   │   ├── model/          # Data models
 │   │   ├── parser/         # Log parsing logic
 │   │   ├── service/        # Business logic
 │   │   ├── servlet/        # Controllers (Servlets)
 │   │   └── util/           # Utility classes (DB connection)
 │   └── webapp/
 │       ├── dashboard.jsp
 │       ├── search.jsp
 │       ├── upload.jsp
 │       └── WEB-INF/web.xml
 └── Database Schema.txt
```

## How It Works

1. User uploads a log file via `upload.jsp`
2. `UploadLogServlet` processes the file
3. `LogParser` extracts structured log entries
4. Parsed data is saved using DAO layer
5. User can:

   * View logs in dashboard
   * Search logs using filters

## Setup Instructions

### 1. Prerequisites

* Java 8+
* Maven
* Apache Tomcat
* Oracle DB (or compatible database)

### 2. Database Setup

* Create tables using `Database Schema.txt`
* Update DB credentials in:

```
com.logtool.util.DBConnection
```

### 3. Build Project

```
mvn clean install
```

### 4. Deploy

* Deploy the generated `.war` file to Tomcat
* Start the server

### 5. Access

```
http://localhost:8080/<project-name>/
```

## Key Components

### LogParser

Handles parsing of raw log files into structured objects.

### DAO Layer

* `LogDAO` → handles log data
* `UploadDAO` → handles upload metadata

### Service Layer

* `LogService` → business logic and coordination

### Servlets

* `UploadLogServlet` → file upload handling
* `DashboardServlet` → dashboard data
* `SearchServlet` → search functionality

## Future Improvements

* Real-time log streaming
* Advanced filtering and analytics
* UI modernization (React integration)
* Pagination and performance optimization
* Role-based access control

## Notes

* Ensure proper file upload size configuration in Tomcat
* Large log files may require optimization in parsing and DB insertion
* Database indexing is recommended for faster search queries

## License

This project is for educational purposes.
