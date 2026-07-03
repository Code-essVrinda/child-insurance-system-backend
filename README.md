# Child Insurance System - Backend

> A Spring Boot REST API for managing child insurance policies, claims and payments.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.2-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-DB-blue)
![Razorpay](https://img.shields.io/badge/Payments-Razorpay-052cc0)
![Build](https://img.shields.io/badge/Build-Maven-red)

This is my capstone project. It is the backend for a child insurance system.
Parents can register, add their children, buy policies and file claims.
Admin can manage everything.

The frontend is a separate project. This repo is only the backend (REST API).

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Data Model](#data-model)
- [Author](#author)

---

## Features

- 👨‍👩‍👧 **Parent accounts** - sign up, login and manage profile.
- 🧒 **Children** - a parent can add one or more children.
- 📄 **Policies** - create policies and enroll a child into a policy.
- 💰 **Premium calculation** - premium amount is worked out for a policy.
- 🧾 **Claims** - three types: Health, Education and Endowment.
- 💳 **Payments** - online payment using Razorpay.
- 📧 **Email reminders** - emails are sent (like premium due reminders).
- 🛡️ **Admin panel APIs** - admin can manage parents, policies and claims.
- 📚 **API docs** - Swagger UI is included.

---

## Tech Stack

| Area          | Technology                        |
| ------------- | --------------------------------- |
| Language      | Java 17                           |
| Framework     | Spring Boot 3.1.2                 |
| Security      | Spring Security (BCrypt hashing)  |
| Data / ORM    | Spring Data JPA (Hibernate)       |
| Database      | PostgreSQL                        |
| Payments      | Razorpay Java SDK                 |
| Email         | Spring Mail (SMTP / Gmail)        |
| API Docs      | Springdoc OpenAPI (Swagger UI)    |
| Build Tool    | Maven                             |

---

## Project Structure

The main code is inside `src/main/java/com/capstone/child/insurance/system`.

```
system/
├── controller/   REST endpoints (Parent, Child, Policy, Claims, Payment ...)
├── service/      business logic (interface + Impl for each module)
├── dao/          repositories that talk to the database
├── entity/       database tables (Parent, Child, Policy, Payment ...)
├── exceptions/   custom exceptions for each module
└── advice/       exception handlers, send proper error response
```

Each module (Parent, Child, Policy, Claim ...) follows the same pattern:
`Controller -> Service -> Repository -> Entity`.

---

## Getting Started

### Requirements

- Java 17 or higher
- PostgreSQL
- Maven (or just use the included `mvnw` wrapper)

### Steps

**1. Clone the repo**

```bash
git clone <your-repo-url>
cd child-insurance-system-backend
```

**2. Create the database in PostgreSQL**

```sql
CREATE DATABASE presentation;
```

**3. Set the config** (see [Configuration](#configuration) below).

**4. Run the app**

```bash
./mvnw spring-boot:run
```

The app starts on **port 8090**.

**5. Open the API docs**

```
http://localhost:8090/swagger-ui/index.html
```

---

## Configuration

Config is in `src/main/resources/application.properties`.

Secrets are **not** kept in the code. They are read from environment
variables. You need to set these before running:

| Variable             | Meaning                          |
| -------------------- | -------------------------------- |
| `DB_PASSWORD`        | your PostgreSQL password         |
| `DB_USERNAME`        | your PostgreSQL user (optional, defaults to `postgres`) |
| `EMAIL`              | your Gmail address (for reminder emails) |
| `PASSWORD`           | your Gmail app password          |
| `RAZORPAY_KEY_ID`    | your Razorpay key id             |
| `RAZORPAY_KEY_SECRET`| your Razorpay key secret         |

The database url is set in `src/main/resources/application.properties`
(`jdbc:postgresql://localhost:5432/presentation`). Change it if needed.

---

## API Endpoints

All endpoints start with `/api/v1` and use plural resource names.
Here are the main base paths:

| Base Path                                   | What it is for                     |
| ------------------------------------------- | ---------------------------------- |
| `/api/v1/parents`                           | parent accounts and login          |
| `/api/v1/parents/{parentId}/children`       | children of a parent               |
| `/api/v1/admins`                            | admin accounts and login           |
| `/api/v1/policies`                          | policies                           |
| `/api/v1/enrollments`                       | enroll a child into a policy       |
| `/api/v1/payments`                          | payments and Razorpay transactions |
| `/api/v1/health-claims`                     | health claims                      |
| `/api/v1/education-claims`                  | education claims                   |
| `/api/v1/endowment-claims`                  | endowment claims                   |
| `/api/v1/reminders`                         | send reminder emails               |

The full list with request/response details is in **Swagger UI**.

---

## Data Model

Main entities:

- **Parent** - parent/guardian account (also holds bank details).
- **Child** - a child that belongs to a parent.
- **Policy** - an insurance policy.
- **ChildPolicyEnrollment** - links a child to a policy.
- **PremiumCalculation** - premium amount for a policy.
- **Payment / TransactionDetail** - payment records.
- **HealthClaim / EducationClaim / EndowmentClaim** - the three claim types.
- **Reminder** - reminder / email data.
- **Admin** - admin user.

---

## Security Notes

Some things I already fixed:

- Passwords are now hashed with BCrypt (not plain text anymore).
- Razorpay keys and the DB password are read from environment variables,
  not written in the code.
- Spring Security is added and all CORS is set in one place.
- Java version in `pom.xml` is now 17 (needed for Spring Boot 3).

Still to do (future work):

- Endpoints are open for now. Login works but there is no real token
  auth yet. Next step is to add JWT and lock the endpoints.

---

## Author

Made by **Vrinda Shinde** as a capstone project.
