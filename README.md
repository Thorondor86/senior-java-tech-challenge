# Senior Java Tech Challenge

API para gestión de productos y precios históricos con soporte multi-moneda.

## 🚀 Ejecución con Docker

```bash
docker compose up --build
```
- Base de datos: PostgreSQL (productsdb, user=app, pass=app)
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Tests k6 se ejecutan automáticamente al levantar los contenedores.

## 🔐 Autenticación

- Usuario: admin
- Contraseña: admin
- Tipo: Basic Auth

## 📡 Endpoints

- POST /products
- POST /products/{id}/prices
- GET /products/{id}/prices
- GET /products/{id}/prices/at?date=YYYY-MM-DD