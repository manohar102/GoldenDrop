# GoldenDrop 🍾📦

> **Liquor Supply, Store Inventory & Sales Management Platform**

GoldenDrop is a robust, scalable backend system engineered for managing liquor store operations, inventory tracking, daily sales records, and supply chain logistics. Built with **Spring Boot 3** and **Java 17**, it features high-performance data handling, Flyway database migrations, JWT-based security, automated notifications via Email and WhatsApp, and cloud integration with AWS.

---

## 🚀 Key Features

- 🏬 **Store & Location Management**: Centralized store profiling, outlet configuration, and metadata tracking.
- 🍷 **Product Catalog & Stock Tracking**: Real-time product tracking, stock level views, and dynamic inventory deduction.
- 🧾 **Sales & Billing Operations**: Comprehensive sale recording, transaction history, and inventory adjustment upon sale.
- 🔐 **Security & RBAC**: JWT token authentication (`X-AUTH`) and fine-grained Role-Based Access Control (RBAC).
- 📩 **Multi-Channel Notifications**: Automated transactional alerts and sales receipts sent via **Email** (SMTP) and **WhatsApp Cloud API**.
- ☁️ **AWS & Cloud Ready**: AWS S3/Cloud integration supported locally via **LocalStack** and Amazon Corretto in Docker environments.
- 📜 **Database Versioning**: Production-ready MySQL 8 migrations powered by **Flyway**.
- 📖 **Interactive API Documentation**: Embedded Swagger UI / OpenAPI 3.0 specs for seamless client integration.

---

## 🛠️ Technology Stack

- **Core Framework**: Java 17, Spring Boot `3.2.2`
- **Security & Auth**: Spring Security, JJWT (`0.12.3`)
- **Data Persistence**: Spring Data JPA, Hibernate, MySQL 8
- **Database Migrations**: Flyway Core `10.6.0`
- **Mapping & Boilerplate**: MapStruct `1.5.5`, Lombok `1.18.30`
- **Cloud & Storage**: Spring Cloud AWS, AWS SDK, LocalStack
- **Notifications**: Spring Boot Mail, WhatsApp Cloud API
- **API Docs**: SpringDoc OpenAPI `2.0.4` (Swagger UI)
- **Code Quality**: Spotless Gradle Plugin (Palantir Java Format)
- **Containerization**: Docker, Docker Compose (Amazon Corretto 17 Base)

---

## 📋 Prerequisites

Before running the application, ensure you have the following installed:

- **Java Development Kit (JDK)** 17 or higher
- **Gradle** 7.4+ (or use the bundled `gradlew`)
- **MySQL** 8.0+
- **Docker** and **Docker Compose** *(optional, for containerized deployment)*

---

## ⚙️ Configuration & Setup

### 1. Database Setup
Ensure MySQL is running and create the database schema specified in `application.properties`:

```sql
CREATE DATABASE goldendrop;
```

Flyway will automatically apply database migrations located at `src/main/resources/db/001_Schema` when the application starts.

### 2. Environment Configuration
Key configurations are defined in `src/main/resources/application.properties`:

| Configuration Key | Description | Default / Example |
| :--- | :--- | :--- |
| `server.port` | Application server port | `8080` |
| `spring.datasource.url` | MySQL Database Connection URL | `jdbc:mysql://<host>:3306/goldendrop` |
| `jwt.signing.key` | Secret key for JWT signing | *Configured key* |
| `spring.mail.*` | SMTP Configuration for email alerts | Google SMTP |
| `notification.whatsapp.*` | WhatsApp API credentials | WhatsApp Bearer Token & Phone ID |

---

## 🏃 Getting Started

### Run Locally (Gradle)

1. Clone the repository:
   ```bash
   git clone https://github.com/your-org/goldendrop.git
   cd GoldenDrop
   ```

2. Format code (Spotless):
   ```bash
   ./gradlew spotlessApply
   ```

3. Build and test the project:
   ```bash
   ./gradlew clean build
   ```

4. Launch the application:
   ```bash
   ./gradlew bootRun
   ```

The server will start at `http://localhost:8080`.

---

## 🐳 Docker Deployment

To build and run GoldenDrop inside a Docker container:

1. Build the Docker image:
   ```bash
   docker build -t golden-drop-server:latest .
   ```

2. Run with Docker Compose:
   ```bash
   docker-compose up -d
   ```

---

## 📑 API Documentation (Swagger)

Once the application is running, access the interactive OpenAPI / Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 📂 Project Structure

```
GoldenDrop/
├── src/
│   ├── main/
│   │   ├── java/com/techbuddy/goldendrop/
│   │   │   ├── controller/      # REST API Controllers (Product, SaleRecord, StockDetail, Store, User)
│   │   │   ├── model/           # JPA Entities & Enums
│   │   │   ├── repository/      # Spring Data Repositories
│   │   │   ├── service/         # Business Logic & Service layer
│   │   │   ├── security/        # JWT Authentication & Authorization filters
│   │   │   ├── notification/    # Email & WhatsApp notification services
│   │   │   ├── dto/ & request/  # DTOs & Request Models
│   │   │   └── mapper/          # MapStruct mappers
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/001_Schema/   # Flyway Migration SQL scripts
├── Dockerfile                   # Multi-stage Docker build setup
├── docker-compose.yml           # Container orchestration definition
├── build.gradle                 # Project dependencies & build configuration
└── README.md                    # Project documentation
```
