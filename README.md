# Backend: Sistema de Gestión para Ferretería "El Tornillo Feliz"

Este repositorio contiene el código fuente del backend del sistema de gestión para la ferretería "El Tornillo Feliz". El backend es una API RESTful desarrollada con Spring Boot y Java, utilizando MongoDB como base de datos. Se encarga de la lógica de negocio para la gestión de inventario y la contabilidad básica del negocio.

## 📝 Descripción del Proyecto

[cite_start]El sistema busca digitalizar y centralizar la gestión de inventario y contabilidad de una ferretería tradicional, la cual actualmente maneja sus procesos de forma manual, lo que genera errores, duplicidades, pérdidas de ingresos y dificultades en el seguimiento de ventas y gastos.

Este backend proporciona los servicios necesarios para:

* [cite_start]**Gestión de Inventario**: Permite agregar, actualizar, eliminar y listar productos, así como generar alertas de bajo stock.
* [cite_start]**Contabilidad Básica**: Facilita el registro de ingresos (ventas) y egresos (compras), y la consulta de balances diarios o mensuales.
* [cite_start]**Control y Reportes**: Ofrece un historial organizado de entradas y salidas, y sienta las bases para reportes visuales de ganancias y pérdidas.

## 🛠️ Tecnologías Utilizadas

* [cite_start]**Backend API**: Java 17 + Spring Boot 3.5.0
* [cite_start]**Base de datos**: MongoDB (NoSQL)
* [cite_start]**Control de Versiones**: Git & GitHub
* **Dependencias de Spring Boot**:
    * [cite_start]`Spring Web`: Para construir APIs RESTful
    * [cite_start]`Spring Data MongoDB`: Para la integración con MongoDB
    * [cite_start]`Spring Boot DevTools`: Para facilitar el desarrollo (recarga automática)
    * [cite_start]`Lombok`: Para reducir código repetitivo (getters/setters, constructores, etc.)
    * [cite_start]`Validation`: Para la validación de datos en los modelos (opcional, pero buena práctica)

## 🚀 Configuración y Ejecución del Backend

Sigue estos pasos para configurar y ejecutar el backend en tu entorno local.

### Prerrequisitos

* **Java 17 JDK**: Asegúrate de tener instalado Java Development Kit 17.
* **Maven**: El proyecto está configurado con Maven, que debe estar accesible desde tu línea de comandos o IDE.
* **MongoDB**: Necesitas una instancia de MongoDB en ejecución. Puedes instalar MongoDB localmente o usar un servicio en la nube como MongoDB Atlas.

### Pasos para la Ejecución

1.  **Clonar el Repositorio:**
    ```bash
    git clone [https://github.com/mipa57/inventario.git](https://github.com/mipa57/inventario.git)
    cd inventario
    ```

2.  **Configurar la Base de Datos MongoDB:**
    * Abre el archivo `src/main/resources/application.properties`.
    * Configura la conexión a tu base de datos MongoDB. Si estás ejecutando MongoDB localmente, la configuración por defecto suele ser suficiente. Si usas MongoDB Atlas u otra instancia remota, actualiza la `uri` de conexión.

    ```properties
    # Para MongoDB Local
    spring.data.mongodb.host=localhost
    spring.data.mongodb.port=27017
    spring.data.mongodb.database=ferreteria_db

    # O para MongoDB Atlas (ejemplo, reemplaza con tu URI real)
    # spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster-url>/ferreteria_db?retryWrites=true&w=majority
    ```

3.  **Construir el Proyecto (con Maven):**
    Abre una terminal en la raíz del proyecto y ejecuta:
    ```bash
    mvn clean install
    ```
    Esto descargará las dependencias y construirá el proyecto.

4.  **Ejecutar la Aplicación Spring Boot:**
    Puedes ejecutar la aplicación de varias maneras:

    * **Desde la terminal:**
        ```bash
        mvn spring-boot:run
        ```
    * **Desde tu IDE (IntelliJ IDEA, Eclipse, VS Code):**
      Abre el proyecto en tu IDE. Localiza la clase `InventarioApplication.java` (en `src/main/java/com/ferreteria/inventario/`). Haz clic derecho y selecciona "Run 'InventarioApplication.main()'" o usa la opción de ejecutar de tu IDE.

    La aplicación se iniciará en `http://localhost:8080` por defecto.

## 💡 Estructura del Proyecto (Backend)

La estructura del proyecto sigue las convenciones de Spring Boot y una arquitectura en capas:

src/main/java/com/ferreteria/inventario/
├── InventarioApplication.java       # Clase principal de la aplicación
├── config/                          # Configuraciones generales (ej. CORS, Seguridad)
├── controller/                      # Controladores REST para manejar las peticiones HTTP
│   ├── ProductoController.java      # Endpoints para la gestión de productos
│   └── ContabilidadController.java  # Endpoints para la gestión de transacciones contables
├── model/                           # Clases de modelos (POJOs) que mapean a documentos de MongoDB
│   ├── Producto.java                # Representa un producto en el inventario
│   └── Transaccion.java             # Representa un ingreso o egreso
├── repository/                      # Interfaces para el acceso a datos (Spring Data MongoDB)
│   ├── ProductoRepository.java      # Repositorio para operaciones CRUD de productos
│   └── TransaccionRepository.java   # Repositorio para operaciones CRUD de transacciones
└── service/                         # Lógica de negocio (opcional, para mayor complejidad)
├── ProductoService.java
└── ContabilidadService.java


## 🌐 Endpoints de la API (Ejemplos)

A continuación, se describen algunos de los endpoints RESTful principales que el backend expondrá.

### Módulo de Inventario (`/api/productos`)

* **`GET /api/productos`**
    * **Descripción**: Obtiene una lista de todos los productos en el inventario.
    * **Respuesta Exitosa (200 OK)**:
        ```json
        [
            {
                "id": "60d5ec49f8c6b7e6f4a5b6c7",
                "codigo": "P001",
                "nombre": "Martillo",
                "cantidad": 50,
                "precio_unitario": 12.50
            },
            {
                "id": "60d5ec49f8c6b7e6f4a5b6c8",
                "codigo": "T005",
                "nombre": "Caja de Tornillos",
                "cantidad": 200,
                "precio_unitario": 5.75
            }
        ]
        ```

* **`GET /api/productos/{id}`**
    * **Descripción**: Obtiene un producto específico por su ID.
    * **Parámetros**: `id` (string)
    * **Respuesta Exitosa (200 OK)**:
        ```json
        {
            "id": "60d5ec49f8c6b7e6f4a5b6c7",
            "codigo": "P001",
            "nombre": "Martillo",
            "cantidad": 50,
            "precio_unitario": 12.50
        }
        ```
    * **Respuesta (404 Not Found)**: Si el producto no existe.

* **`POST /api/productos`**
    * **Descripción**: Agrega un nuevo producto al inventario.
    * **Cuerpo de la Petición (Request Body)**:
        ```json
        {
            "codigo": "P002",
            "nombre": "Destornillador Phillips",
            "cantidad": 100,
            "precio_unitario": 7.99
        }
        ```
    * **Respuesta Exitosa (201 Created)**: Retorna el producto creado con su ID.

* **`PUT /api/productos/{id}`**
    * **Descripción**: Actualiza la información de un producto existente por su ID.
    * **Parámetros**: `id` (string)
    * **Cuerpo de la Petición (Request Body)**:
        ```json
        {
            "codigo": "P001",
            "nombre": "Martillo de Carpintero",
            "cantidad": 45,
            "precio_unitario": 13.00
        }
        ```
    * **Respuesta Exitosa (200 OK)**: Retorna el producto actualizado.
    * **Respuesta (404 Not Found)**: Si el producto no existe.

* **`DELETE /api/productos/{id}`**
    * **Descripción**: Elimina un producto del inventario por su ID.
    * **Parámetros**: `id` (string)
    * **Respuesta Exitosa (204 No Content)**: Si se eliminó correctamente.
    * **Respuesta (404 Not Found)**: Si el producto no existe.

### Módulo de Contabilidad (`/api/transacciones`)

* **`POST /api/transacciones/venta`**
    * **Descripción**: Registra una venta. Esto implicaría la disminución del stock del producto vendido y el registro monetario.
    * **Cuerpo de la Petición (Request Body)**:
        ```json
        {
            "fecha": "2025-05-21",
            "monto": 200.00,
            "detalle": "Venta de tornillos y tuercas"
        }
        ```
    * **Respuesta Exitosa (201 Created)**: Retorna la transacción registrada.

* **`POST /api/transacciones/compra`**
    * **Descripción**: Registra una compra de insumos o productos. Esto implicaría el aumento del stock de los productos comprados y el registro monetario.
    * **Cuerpo de la Petición (Request Body)**:
        ```json
        {
            "fecha": "2025-05-20",
            "monto": 150.00,
            "detalle": "Compra de stock de martillos"
        }
        ```
    * **Respuesta Exitosa (201 Created)**: Retorna la transacción registrada.

* **`GET /api/transacciones/balance/{periodo}`**
    * **Descripción**: Calcula el balance (ingresos - egresos) para un período específico (ej. "diario", "semanal", "mensual").
    * **Parámetros**: `periodo` (string, ej. "daily", "weekly", "monthly")
    * **Respuesta Exitosa (200 OK)**:
        ```json
        {
            "periodo": "hoy",
            "ingresos": 500.00,
            "egresos": 200.00,
            "balance": 300.00
        }
        ```

* **`GET /api/transacciones/historial`**
    * **Descripción**: Obtiene un historial de todas las transacciones.
    * **Respuesta Exitosa (200 OK)**:
        ```json
        [
            {
                "id": "...",
                "fecha": "2025-05-21",
                "tipo": "venta",
                "monto": 200.00,
                "detalle": "Venta de tornillos"
            },
            {
                "id": "...",
                "fecha": "2025-05-20",
                "tipo": "compra",
                "monto": 150.00,
                "detalle": "Compra de martillos"
            }
        ]
        ```

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Si deseas colaborar, por favor, haz un "fork" del repositorio y envía tus "pull requests".

## 📄 Licencia

MIT License