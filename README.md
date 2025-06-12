
# 📚 Relatos de Papel - Back-End (Microservicios)

Este repositorio contiene la implementación del back-end de la aplicación **Relatos de Papel**, desarrollado con Java y Spring Boot bajo una arquitectura de microservicios. Los servicios se comunican a través de Eureka y están expuestos mediante Spring Cloud Gateway.

---

## 🌐 Microservicios

### 1.  `ms-books-catalogue`
> Gestiona el catálogo de libros y permite búsquedas avanzadas.

**Context path:** `/api/v1/catalogue`

### Categorías (gestión de categorías)

> **Base path:** `/api/v1/catalogue/categories`

| Método HTTP | URI | Descripción | Parámetros | Respuestas |
|-------------|-----|-------------|------------|------------|
| `POST` | `/categories` | Crear una nueva categoría | Body: `CreateCategoryRequest` | `200 OK`: Categoría creada<br>`400 Bad Request`: Error de validación<br>`500`: Error interno |
| `GET` | `/categories/{categoryId}` | Obtener una categoría por su ID | Path: `categoryId` | `200 OK`: Categoría encontrada<br>`400`: ID inválido<br>`500`: Error interno |
| `GET` | `/categories` | Listar categorías paginadas | Query: `page`, `size` | `200 OK`: Lista paginada<br>`400`: Parámetros inválidos<br>`500`: Error interno |
| `GET` | `/categories/all` | Listar todas las categorías (sin paginar) | Ninguno | `200 OK`: Lista completa<br>`500`: Error interno |
| `PUT` | `/categories/{categoryId}` | Actualizar una categoría por ID | Path: `categoryId`, Body: `CreateCategoryRequest` | `200 OK`: Categoría actualizada<br>`400`: Datos inválidos<br>`500`: Error interno |
| `DELETE` | `/categories/{categoryId}` | Eliminar una categoría por ID | Path: `categoryId` | `204 No Content`: Eliminada<br>`400`: ID inválido<br>`500`: Error interno |

### Autores (gestión de autores)

> **Base path:** `/api/v1/catalogue/authors`

| Método HTTP | URI | Descripción | Parámetros | Respuestas |
|-------------|-----|-------------|------------|------------|
| `POST` | `/authors` | Crear un nuevo autor | Body: `CreateAuthorRequest` | `200 OK`: Autor creado<br>`400 Bad Request`: Error de validación<br>`500`: Error interno |
| `GET` | `/authors/{authorId}` | Obtener un autor por su ID | Path: `authorId` | `200 OK`: Autor encontrado<br>`400`: ID inválido<br>`500`: Error interno |
| `GET` | `/authors` | Buscar autores por nombre y apellido con paginación | Query: `authorName`, `authorLastName`, `page`, `size` | `200 OK`: Lista de autores<br>`400`: Parámetros inválidos<br>`500`: Error interno |
| `PUT` | `/authors/{authorId}` | Actualizar un autor por ID | Path: `authorId`, Body: `CreateAuthorRequest` | `200 OK`: Autor actualizado<br>`400`: Datos inválidos<br>`500`: Error interno |
| `DELETE` | `/authors/{authorId}` | Eliminar un autor por ID | Path: `authorId` | `204 No Content`: Eliminado<br>`400`: ID inválido<br>`500`: Error interno |


### Libros (gestión de Libros)

> **Base path:** `/api/v1/catalogue/books`

| Método HTTP | URI | Operación asociada | Respuesta |
|-------------|-----|---------------------|-----------|
| `GET` | `/api/v1/catalogue/books` | 1. Obtener todos los libros | `200 OK`, `400 Bad Request`, `404 Not Found`, `500 Internal Server Error` |
| `GET` | `/api/v1/catalogue/books/{idLibro}` | 2. Obtener un libro por ID | `200 OK`, `400 Bad Request`, `404 Not Found`, `500 Internal Server Error` |
| `POST` | `/api/v1/catalogue/books` | 3. Crear un nuevo libro | `201 Created`, `400 Bad Request`, `500 Internal Server Error` |
| `PUT` | `/api/v1/catalogue/books/{idLibro}` | 4. Actualizar un libro completo | `200 OK`, `400 Bad Request`, `404 Not Found`, `500 Internal Server Error` |
| `PATCH` | `/api/v1/catalogue/books/{idLibro}` | 5. Actualización parcial (opcional) | `200 OK`, `400 Bad Request`, `404 Not Found`, `500 Internal Server Error` |
| `DELETE` | `/api/v1/catalogue/books/{idLibro}` | 6. Eliminar un libro | `204 No Content`, `400 Bad Request`, `404 Not Found`, `500 Internal Server Error` |
| `GET` | `/api/v1/catalogue/books/search` | 7. Búsqueda por criterios combinados | `200 OK`, `400 Bad Request`, `500 Internal Server Error` |

---

### 2.  `ms-books-payments`
> Gestiona el registro de pedidos (compras) y validaciones contra el catálogo.

**Context path:** `/api/v1/payments`

| Método   | Endpoint                                         | Operación asociada                                   | Estado involucrado      | Validaciones necesarias                                                                 | Códigos de respuesta                                             | JSON de entrada                                                                 |
|----------|--------------------------------------------------|------------------------------------------------------|--------------------------|------------------------------------------------------------------------------------------|------------------------------------------------------------------|----------------------------------------------------------------------------------|
| `POST`   | `/api/payments/orders`                          | 1.Crear una nueva orden/carrito con uno o más libros | `PENDING`                | Validar cada libro contra catálogo: existencia, stock, visibilidad                      | `201 Created`, `400 Bad Request`, `404 Not Found`, `409 Conflict` | ```json { "userId": "joha123", "items": [ { "itemId": "libro1", "quantity": 2 }, { "itemId": "libro2", "quantity": 1 } ] } ``` |
| `PATCH`  | `/api/payments/orders/{orderId}/add-item`       | 2.Añadir un nuevo ítem a una orden/carrito existente | `PENDING`                | Validar ítem, verificar que la orden esté en estado `PENDING`                           | `200 OK`, `400 Bad Request`, `404 Not Found`, `409 Conflict`     | ```json { "itemId": "libro3", "quantity": 1 } ```                              |
| `PATCH`  | `/api/payments/orders/{orderId}/pay`            | 3.Pagar la orden                                     | `PENDING → PAID`         | Revalidar visibilidad y stock. Descontar stock en catálogo                              | `200 OK`, `400 Bad Request`, `404 Not Found`, `409 Conflict`     | —                                                                                |
| `GET`    | `/api/payments/orders/user/{userId}`            | 4.Consultar todas las órdenes de un usuario          | —                        | Validar existencia de usuario (si se requiere)                                           | `200 OK`, `404 Not Found`                                        | —                                                                                |
| `GET`    | `/api/payments/orders/{orderId}`                | 5.Consultar detalle de una orden específica          | —                        | Validar que exista, y que pertenezca al usuario (opcional)                              | `200 OK`, `404 Not Found`                                        | —                                                                                |
| `DELETE` | `/api/payments/orders/{orderId}`                | 6.Eliminar una orden/carrito no pagado               | `PENDING`                | Solo permitir si no está pagada (`PENDING`)                                              | `204 No Content`, `404 Not Found`, `409 Conflict`               | —                                                                                |

---

## 🧩 Arquitectura

```
[ Gateway ]
     ↓
[Eureka Server]
     ↓
 ┌───────────────┐     ┌────────────────────┐
 │ms-books-catalogue│ ↔ │ms-books-payments    │
 └───────────────┘     └────────────────────┘
```

- Comunicación entre microservicios vía **Eureka Discovery**
- Rutas expuestas y balanceadas por **Spring Cloud Gateway**
- Cada microservicio tiene su propia base de datos relacional

---

## 📌 Tecnologías

- Java 21
- Spring Boot 3.5.0
- Spring Cloud 2021.x
- Spring Data JPA
- PostgreSQL.
- Eureka Discovery Server
- Spring Cloud Gateway
- Maven

---

## 🧪 Pruebas

- Postman
- Swagger (pendiente)
- Logs visibles en consola
- Panel de Eureka: `http://localhost:8761`

---

## 🧠 Autores

Grupo  Ramen X:
- Sergio Muñoz Vela
- Nicolás Valencia
- Marly Johana Yepes
