# Backend de Nance

> **API REST** para la gestión de inventario y comercio electrónico, desarrollada con Spring Boot 3, autenticación JWT y MySQL. Concebida para dar soporte a la plataforma Nance mediante control de acceso basado en roles y una interfaz de API clara y documentada.

---

## Índice

- [Descripción general](#descripción-general)
- [Funcionalidades](#funcionalidades)
- [Pila tecnológica](#pila-tecnológica)
- [Puesta en marcha](#puesta-en-marcha)
- [Referencia de la API](#referencia-de-la-api)
- [Documentación interactiva](#documentación-interactiva)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Consideraciones de seguridad](#consideraciones-de-seguridad)
- [Ejecución de pruebas](#ejecución-de-pruebas)

---

## Descripción general

Este backend expone, mediante una API REST construida con Spring Boot, la lógica de negocio de la plataforma Nance: autenticación de usuarios, control de acceso por roles y administración del catálogo de productos. La persistencia se apoya en MySQL a través de Spring Data JPA, y la documentación de la API se genera de forma automática con SpringDoc OpenAPI.

---

## Funcionalidades

- **Autenticación JWT**: mecanismo sin estado basado en *tokens*, con una vigencia de diez horas.
- **Control de acceso basado en roles**: roles `ADMIN` y `CLIENT`, con seguridad aplicada a nivel de método.
- **Gestión de productos**: operaciones CRUD completas sobre el catálogo (nombre, precio, existencias, categoría, marca, color e imagen).
- **Documentación interactiva (Swagger/OpenAPI)**: disponible en `/swagger-ui.html`.
- **CORS configurado**: preparado para el desarrollo local del frontend en los puertos `3000`, `5173` y `8081`.
- **Codificación de contraseñas mediante BCrypt**: función *hash* de referencia en la industria para su protección.
- **Inicializador de datos**: puebla automáticamente la base de datos con los usuarios `ADMIN` y `CLIENT` predeterminados al iniciar la aplicación.
- **MySQL en AWS EC2**: fuente de datos lista para producción, con generación automática del esquema (DDL) mediante Hibernate.

---

## Pila tecnológica

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Marco de trabajo | Spring Boot 3.4 |
| Seguridad | Spring Security + JWT (jjwt 0.11.5) |
| Persistencia | Spring Data JPA / Hibernate |
| Base de datos | MySQL 8 (AWS EC2) / H2 (entorno de desarrollo, opcional) |
| Documentación | SpringDoc OpenAPI 2.2 (interfaz Swagger) |
| Herramienta de compilación | Apache Maven 3.9 |
| Utilidades | Lombok, Bean Validation |

---

## Puesta en marcha

### Requisitos previos

- Java 17 o una versión posterior
- Maven 3.9 o superior (o bien, el envoltorio `./mvnw` incluido en el proyecto)
- Una instancia de MySQL 8 (local o remota)

### 1. Clonar el repositorio

```bash
git clone https://github.com/su-organizacion/nance-backend.git
cd nance-backend
```

### 2. Configurar la base de datos

Editar el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://<HOST>:3306/nance_db
spring.datasource.username=<SU_USUARIO_DE_BASE_DE_DATOS>
spring.datasource.password=<SU_CONTRASEÑA>
spring.jpa.hibernate.ddl-auto=update
```

> **Sugerencia:** para el desarrollo local, es posible emplear la base de datos H2 embebida descomentando sus propiedades y comentando el bloque correspondiente a MySQL.

### 3. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

El servidor quedará disponible en `http://localhost:8080`.

### 4. Usuarios predeterminados (creados automáticamente)

| Rol | Correo electrónico | Contraseña |
|---|---|---|
| Administrador | `admin@nance.cl` | `admin123` |
| Cliente | `cliente@nance.cl` | `cliente123` |

---

## Referencia de la API

### Autenticación

| Método | *Endpoint* | Autenticación | Descripción |
|---|---|---|---|
| `POST` | `/api/auth/login` | Público | Autenticación del usuario y obtención de un token JWT |
| `POST` | `/api/auth/register` | Público | Registro de un nuevo usuario (con rol `CLIENT` de manera predeterminada) |

**Cuerpo de la solicitud de inicio de sesión:**
```json
{
  "email": "admin@nance.cl",
  "password": "admin123"
}
```

**Respuesta del inicio de sesión:**
```json
{
  "token": "<jwt_token>",
  "email": "admin@nance.cl",
  "role": "ADMIN",
  "id": 1
}
```

---

### Productos

| Método | *Endpoint* | Autenticación | Descripción |
|---|---|---|---|
| `GET` | `/api/products` | Público | Obtención del listado completo de productos |
| `POST` | `/api/products` | `ADMIN` | Creación de un nuevo producto |
| `PUT` | `/api/products/{id}` | `ADMIN` | Actualización de un producto según su identificador |
| `DELETE` | `/api/products/{id}` | `ADMIN` | Eliminación de un producto según su identificador |

**Campos del producto:** `id`, `name`, `description`, `price`, `stock`, `category`, `image_url`, `color`, `brand` y `attribute`.

---

### Uso del token JWT

Para acceder a los *endpoints* protegidos, el token debe incluirse en la cabecera `Authorization`:

```
Authorization: Bearer <su_token_jwt>
```

---

## Documentación interactiva

Una vez que la aplicación esté en ejecución, puede accederse a través de:

```
http://localhost:8080/swagger-ui/index.html
```

La especificación completa de OpenAPI está disponible en:

```
http://localhost:8080/v3/api-docs
```

---

## Estructura del proyecto

```
src/
└── main/
    └── java/com/nance/backend/
        ├── config/           # Seguridad, CORS, filtro JWT e inicializador de datos
        ├── controller/       # Controladores REST (autenticación y productos)
        ├── dto/              # DTO de solicitud y de respuesta
        ├── model/            # Entidades JPA (usuario, producto y rol)
        ├── repository/       # Repositorios de Spring Data JPA
        └── service/          # Capa de lógica de negocio
```

---

## Consideraciones de seguridad

- La clave secreta empleada para firmar los tokens JWT se encuentra actualmente codificada de forma fija en `JwtUtil.java`. Para un entorno de producción, se recomienda externalizarla mediante variables de entorno o un gestor de secretos.
- Las credenciales de la base de datos declaradas en `application.properties` deberían inyectarse mediante variables de entorno en los despliegues de producción.
- De manera predeterminada, los tokens expiran a las diez horas.

---

## Ejecución de pruebas

```bash
./mvnw test
```

---

<p align="center">Desarrollado con Spring Boot</p>
