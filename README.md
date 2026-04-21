# Nance's Backend

> **RESTful API** for inventory management and e-commerce, built with Spring Boot 3, JWT authentication, and MySQL. Designed to power the Nance platform with role-based access control and a clean, documented API surface.

---


## ✨ Features

- 🔐 **JWT Authentication** — Stateless token-based auth with 10-hour expiry
- 👥 **Role-Based Access Control** — `ADMIN` and `CLIENT` roles with method-level security
- 📦 **Product Management** — Full CRUD for product catalog (name, price, stock, category, brand, color, image)
- 📄 **Swagger / OpenAPI** — Interactive API docs at `/swagger-ui.html`
- 🌐 **CORS Configured** — Ready for local frontend development on ports `3000`, `5173`, `8081`
- 🛡️ **BCrypt Password Encoding** — Industry-standard password hashing
- 🚀 **Data Initializer** — Auto-seeds default `ADMIN` and `CLIENT` users on startup
- 🗄️ **MySQL on AWS EC2** — Production-ready datasource with Hibernate auto-DDL

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4 |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL 8 (AWS EC2) / H2 (dev, optional) |
| Documentation | SpringDoc OpenAPI 2.2 (Swagger UI) |
| Build Tool | Apache Maven 3.9 |
| Utilities | Lombok, Bean Validation |

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.9+ (or use the included `./mvnw` wrapper)
- MySQL 8 instance (local or remote)

### 1. Clone the repository

```bash
git clone https://github.com/your-org/nance-backend.git
cd nance-backend
```

### 2. Configure the database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://<HOST>:3306/nance_db
spring.datasource.username=<YOUR_DB_USER>
spring.datasource.password=<YOUR_DB_PASSWORD>
spring.jpa.hibernate.ddl-auto=update
```

> **Tip:** For local development, you can switch to the embedded H2 database by uncommenting the H2 properties and commenting out the MySQL block.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`.

### 4. Default users (auto-seeded)

| Role | Email | Password |
|---|---|---|
| Admin | `admin@nance.cl` | `admin123` |
| Client | `cliente@nance.cl` | `cliente123` |

---

## 📡 API Reference

### Authentication

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/auth/login` | Public | Authenticate and receive a JWT token |
| `POST` | `/api/auth/register` | Public | Register a new user (defaults to `CLIENT` role) |

**Login request body:**
```json
{
  "email": "admin@nance.cl",
  "password": "admin123"
}
```

**Login response:**
```json
{
  "token": "<jwt_token>",
  "email": "admin@nance.cl",
  "role": "ADMIN",
  "id": 1
}
```

---

### Products

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/products` | Public | List all products |
| `POST` | `/api/products` | `ADMIN` | Create a new product |
| `PUT` | `/api/products/{id}` | `ADMIN` | Update a product by ID |
| `DELETE` | `/api/products/{id}` | `ADMIN` | Delete a product by ID |

**Product fields:** `id`, `name`, `description`, `price`, `stock`, `category`, `image_url`, `color`, `brand`, `attribute`

---

### Using the JWT token

Include the token in the `Authorization` header for all protected endpoints:

```
Authorization: Bearer <your_jwt_token>
```

---

## 📖 Interactive Documentation

Once the application is running, visit:

```
http://localhost:8080/swagger-ui/index.html
```

Full OpenAPI spec available at:

```
http://localhost:8080/v3/api-docs
```

---

## 🗂️ Project Structure

```
src/
└── main/
    └── java/com/nance/backend/
        ├── config/           # Security, CORS, JWT filter, data initializer
        ├── controller/       # REST controllers (Auth, Product)
        ├── dto/              # Request/Response DTOs
        ├── model/            # JPA entities (User, Product, Role)
        ├── repository/       # Spring Data JPA repositories
        └── service/          # Business logic layer
```

---

## 🔒 Security Notes

- The JWT secret key is currently hardcoded in `JwtUtil.java`. For production, externalize it via environment variables or a secrets manager.
- Database credentials in `application.properties` should be injected via environment variables in production deployments.
- Tokens expire after **10 hours** by default.

---

## 🧪 Running Tests

```bash
./mvnw test
```
---
*Built with ❤️ using Spring Boot*
