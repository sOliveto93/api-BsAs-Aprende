# 🛒 API REST - Sistema de eCommerce

Este proyecto es una API REST desarrollada en **Java 21 + Spring Boot** que permite gestionar clientes, productos, pedidos y líneas de pedido. Pensado como base para una tienda online simple o para prácticas académicas con arquitectura por capas y DTOs.
https://github.com/sOliveto93/api-BsAs-Aprende-front -> repo del front
---

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- Jakarta Validation
- Maven
- Swagger
- Docker
- Deploy en Render https://render.com/
- Postgress Render
- JUnit
- Mockito
---

## ⚙️ Configuración y ejecución
https://api-bsas-aprende.onrender.com 

https://radiant-manatee-f7d18e.netlify.app/ front para probar en produccion (si no funciona hay que esperar que despliegue el back)

(el deploy esta en Render por lo tanto al principio hay tiempo de espera mientras levanta el back)

## Ejecuta en local (cambiar entorno de spring a dev)

1. Cloná el repositorio:

```bash
   git clone https://github.com/tu-usuario/api-BsAs-Aprende
   cd api-BsAs-Aprende
```
2. Perfil por default para pruebas
   application.properties
```java
spring.profiles.active=dev
```
3. Ejecutá la aplicación:
```bash
./mvnw spring-boot:run
```
4. Accedé a H2 Console (opcional):

```bash
URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

Usuario: sa

Password: (vacío)
```
4. Accedé a  documentacion en Swagger (opcional):
- URL: http://localhost:8080/swagger-ui.html --> entorno dev
- URL : https://api-bsas-aprende.onrender.com/swagger-ui.html --> entorno produccion

## 🧱 Estructura del proyecto 
```
com.ba_aprende.ecommerce
├── 📦 controller        → Controladores REST
├── 📨 dto              → Clases DTO para entrada/salida
├── 🧬 entity           → Entidades JPA
├── 🚨 exception        → Excepciones personalizadas + handler global
├── 🗃️ repository       → Repositorios JPA
├── ⚙️ service          → Lógica de negocio
└── 🚀 EcommerceApplication.java  → Clase principal (Main)
```
## 🔌 Endpoints principales
🧑 Clientes
- GET /cliente/getAll       → Obtener todos los clientes(activos e inactivos)
- GET /cliente/getById/{id} → Obtener cliente por ID(activos e inactivos)
- GET /cliente/getByDni/{id} → Obtener cliente(activos e inactivos) por dni ej->/cliente/getByDni/12345678


- POST /cliente/create → Crear cliente

- DELETE /cliente/deleteById/{id} → Dar de baja cliente(baja logica)

📦 Productos
- GET /producto/getAll{id} → Obtener todos los productos
- GET /producto/getById/{id} → Obtener los productos por ID
- GET /producto/getByName → Obtener producto por nombre ej → /productos/getByName?nombre=Teclado Redragon (Respetar mayusculas y espacios)
- 
- POST /producto/create → crear producto

- PUT /productos/updateProduct → Modifica un el precio y el stock de un producto dado.
```json
{
   id:1,
   precio:12420,
   stock:15
}
```

- DELETE /producto/deleteById/{id} → Elimina el producto(baja fisica) 

📋 Pedidos

- GET /pedido/getAll → Obtener todos los pedidos.
- GET /pedido/getById/{id} → Obtener pedido por ID.
- GET /pedido/getPedidosByCliente/{id} → Obtener pedidos asociados a un cliente determinado por ID.

- POST /pedido/create → Crear pedido 

Crear un pedido - Ejemplo JSON
POST /pedido/create
```json
{
  "idCliente": 1, <-- obligatorio que exista el cliente y que este activo
  "lineas": [
    {
      "productoId": 2,
      "cantidad": 3
    },
    {
      "productoId": 4,
      "cantidad": 1
    }
  ]
}
```
- DELETE /pedido/deleteById/{id} → Elimina el pedido (baja fisica) 


## Manejo de errores
El sistema responde con códigos y mensajes claros según la situación:

Código	Descripción
- 400	Datos inválidos o stock insuficiente
- 403	Cliente dado de baja / inactivo (permisos rebocados en la app)
- 404	Recurso no encontrado
- 409	Conflicto (ej-> usuario con ese dni ya existe)

- 500	Error interno del servidor

Ejemplo:

```json
{
  "mensaje": "Cliente con el id 5 está dado de baja. No puede registrar pedidos",
  "codigo": 403
}
```

## 📝 Autor
Desarrollado por Sebastián Oliveto como parte de un proyecto para Buenos Aires Aprende Curso Java Back-End.
Podés contactarme para dudas o sugerencias. [Linked](https://www.linkedin.com/in/sebastianoliveto/)
