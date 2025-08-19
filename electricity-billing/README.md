# Electricity Bill Management System (EBMS)

A complete JSP/Servlet + Derby application with separate `frontend` and `backend` folders.

## Tech
- JSP, Servlets (Java 11)
- Apache Derby (Network Client)
- Maven + Jetty for local run

## Quick start

1. Start Derby network server (in a separate terminal):

```bash
java -jar ~/.m2/repository/org/apache/derby/derbynet/10.16.1.1/derbynet-10.16.1.1.jar start -p 1527
```

2. Build and run the backend:

```bash
cd backend
mvn clean package
mvn org.eclipse.jetty:jetty-maven-plugin:run
```

3. Open the app: `http://localhost:8080`

- Admin demo login: `admin / admin123`
- Register a customer for customer flows

## Features
- Customer registration and login
- Customer dashboard (total consumption, last payment, due)
- View bills with search
- Pay bill (full/partial)
- Admin: list customers, delete if no dues
- Admin: register bill

## Database
- On first run, tables are auto-created in schema `APP` with database `ebmsdb` (created automatically).

