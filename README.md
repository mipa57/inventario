# 🛠️ Inventario Ferretería – El Tornillo Feliz

Bienvenido a **El Tornillo Feliz**, un sistema web de gestión para una ferretería tradicional. Esta aplicación ayuda a mantener el inventario al día y llevar la contabilidad básica del negocio de manera simple, rápida y efectiva.

---

## 🚀 Funcionalidades principales

- 🧾 Gestión de productos (alta, baja, modificación).
- 📉 Alertas por bajo stock.
- 💰 Módulo contable básico (en desarrollo).
- 🔐 Autenticación segura para empleados (próximamente).
- 📊 Reportes visuales e históricos (pendiente).
- 🌐 Accesible desde cualquier navegador.

---

## ⚙️ Tecnologías utilizadas

| Componente       | Tecnología               |
|------------------|--------------------------|
| Backend API      | Java 17 + Spring Boot    |
| Base de Datos    | MongoDB (Atlas)          |
| Frontend (inicial)| HTML + Bootstrap (en progreso) |
| Control de versiones | GitHub                |
| Pruebas API      | REST Client (VS Code)    |

---

## 📂 Estructura de datos

### Producto

```json
{
  "codigo": "P001",
  "nombre": "Martillo",
  "cantidad": 50,
  "precioUnitario": 12.50
}
📬 Pruebas con REST Client en VS Code
Podés testear todas las operaciones (GET, POST, PUT, DELETE) con el archivo test.http.

🧪 ¿Cómo usarlo?
Instalá la extensión REST Client en Visual Studio Code.

Creá un archivo llamado test.http en la raíz del proyecto.

Pegá el siguiente contenido:

http
Copiar
Editar
### 🔍 Listar todos los productos
GET http://localhost:8080/api/productos
Content-Type: application/json

###

### 🚨 Ver productos con bajo stock (por defecto límite 5)
GET http://localhost:8080/api/productos/bajo-stock
Content-Type: application/json

###

### 🚨 Ver productos con stock menor o igual a 10
GET http://localhost:8080/api/productos/bajo-stock?limite=10
Content-Type: application/json

###

### ➕ Crear un nuevo producto
POST http://localhost:8080/api/productos
Content-Type: application/json

{
  "codigo": "P100",
  "nombre": "Alicate",
  "cantidad": 8,
  "precioUnitario": 15.75
}

###

### ✏️ Actualizar un producto (REEMPLAZAR {id})
PUT http://localhost:8080/api/productos/{id}
Content-Type: application/json

{
  "codigo": "P100",
  "nombre": "Alicate reforzado",
  "cantidad": 12,
  "precioUnitario": 18.50
}

###

### 🗑️ Eliminar un producto (REEMPLAZAR {id})
DELETE http://localhost:8080/api/productos/{id}
Content-Type: application/json
📌 Notas
Reemplazá {id} por un ID real que obtengas de los productos existentes.

El sistema está en desarrollo constante. Se aceptan mejoras, ideas y pull requests.

🧑‍💻 Autor
Miguel Gustavo – Jardinero de día, programador de noche. 🌿💻

GitHub

🧰 Licencia
Proyecto de uso educativo y libre distribución. 🚜✨