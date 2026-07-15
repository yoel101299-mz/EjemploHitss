# Ejemplo Hitss - Backend Base (Arquitectura Hexagonal con Quarkus)

Este proyecto es una plantilla base (boilerplate) profesional construida sobre **Quarkus** utilizando **Arquitectura Hexagonal (Puertos y Adaptadores)** y un empaquetado desacoplado orientado a **Características (Package by Feature)**. Está diseñado para aislar completamente las reglas del negocio de los componentes tecnológicos y de infraestructura.

---

## 🚀 Arquitectura del Proyecto

El software sigue los principios del Diseño Guiado por el Dominio (DDD) y la Arquitectura Hexagonal. El código se organiza de manera que el núcleo de la aplicación no dependa de frameworks, bases de datos ni herramientas externas:

* **`domain/` (El Corazón)**: Contiene el modelo de negocio puro, entidades lógicas y excepciones de negocio. Es Java puro (Cero dependencias de Quarkus, Hibernate o Panache).
* **`ports/` (Los Límites)**: Define las interfaces y contratos que exponen el comportamiento del hexágono.
  * `inbound/`: Interfaces para los casos de uso que disparan los actores externos (Drivers, ej: Controladores REST).
  * `outbound/`: Interfaces SPI que definen lo que el negocio necesita de los sistemas externos (Driven, ej: Repositorios de persistencia).
* **`application/` (El Orquestador)**: Aloja los servicios que implementan la lógica de los casos de uso, coordinando el flujo de datos a través de los puertos.
* **`infrastructure/` (El Mundo Exterior)**: Contiene toda la tecnología y dependencias del framework. Aquí vive Quarkus, la serialización JSON, la persistencia real con Panache y las migraciones de **Flyway**.
* **`shared/` (Transversal)**: Código auxiliar compartido entre múltiples módulos (clases abstractas de dominio, configuraciones globales, etc.).

---

## 🛠️ Tecnologías Core

* **Java 21**
* **Quarkus 3.x**
* **PostgreSQL** (Motor de Base de Datos Relacional)
* **Flyway** (Evolución y control de versiones del esquema de BD)
* **MapStruct** (Mapeo estricto y desacoplado entre capas)
* **Lombok** (Optimización de código Boilerplate)

---

## 🐳 Entorno de Base de Datos (Docker)

El almacenamiento del sistema corre de manera aislada en un contenedor de **PostgreSQL**.


# 🚀 Levantar el Proyecto

## 1. Configurar variables de entorno

Antes de compilar el proyecto, crea un archivo **`.env`** en la raíz del proyecto con las siguientes variables:

```properties
DB_USERNAME=...
DB_PASSWORD=...
DB_HOST=0.0.0.0
DB_PORT=5432
DB_NAME=...
CMC_API_KEY=...
```

> **Nota:** Este archivo no debe subirse al repositorio. Asegúrate de incluir `.env` en el `.gitignore`.

---

## 2. Compilar la aplicación nativa

Genera el ejecutable nativo de Quarkus ejecutando:

```bash
mvn clean package -Pnative -DskipTests
```

Al finalizar, el ejecutable será generado en:

```text
target/*-runner
```

---

## 3. Construir e iniciar los contenedores

Una vez compilada la aplicación, levanta toda la infraestructura mediante Docker Compose:

```bash
docker compose up -d --build
```

Este comando realizará las siguientes acciones:

- Construirá la imagen de la aplicación utilizando el ejecutable nativo.
- Creará el contenedor de PostgreSQL.
- Creará el contenedor de la aplicación Quarkus.
- Configurará la red entre ambos servicios.
- Inicializará la base de datos mediante las migraciones de Flyway.

---

## 4. Verificar los contenedores

Comprueba que los servicios se encuentren en ejecución:

```bash
docker ps
```

También puedes consultar los logs de la aplicación:

```bash
docker compose logs -f app
```

o de PostgreSQL:

```bash
docker compose logs -f postgres
```

---

## 5. Detener el proyecto

Para detener los contenedores:

```bash
docker compose down
```

Si además deseas eliminar el volumen de PostgreSQL:

```bash
docker compose down -v
```

> **Advertencia:** El parámetro `-v` elimina completamente la información almacenada en la base de datos.

---

## 📂 Flujo completo

```bash
# 1. Configurar variables de entorno
cp .env.example .env

# 2. Compilar la aplicación nativa
mvn clean package -Pnative -DskipTests

# 3. Levantar la infraestructura
docker compose up -d --build

# 4. Verificar contenedores
docker ps

# 5. Consultar logs (opcional)
docker compose logs -f app
```