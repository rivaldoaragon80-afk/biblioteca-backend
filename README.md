# 📚 Sistema de Gestión de Biblioteca - Backend

API REST para la gestión completa de una biblioteca, incluyendo préstamos de libros, administración de usuarios y autenticación JWT.

## 🚀 Características

- ✅ **Autenticación y Autorización** con JWT
- ✅ **Control de acceso basado en roles** (ADMIN, USUARIO)
- ✅ **CRUD completo de Libros** con validaciones
- ✅ **Sistema de préstamos** con gestión de estados
- ✅ **Gestión de usuarios** con encriptación de contraseñas
- ✅ **Validación de datos** con Jakarta Validation
- ✅ **Manejo centralizado de errores**
- ✅ **Documentación interactiva** con Swagger/OpenAPI
- ✅ **Logging estructurado** con SLF4J
- ✅ **Auditoría automática** (timestamps de creación/actualización)
- ✅ **Monitoreo** con Spring Boot Actuator

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.3.4**
- **Spring Security** con JWT
- **Spring Data JPA** con Hibernate
- **MySQL 8**
- **Lombok** para reducir código boilerplate
- **SpringDoc OpenAPI 3** para documentación
- **Maven** como herramienta de construcción

## 📋 Requisitos

- Java 17 o superior
- Maven 3.8+
- MySQL 8.0+
- Puerto 8081 disponible (o configurar otro)

## ⚙️ Configuración

### 1. Base de Datos

Crear la base de datos en MySQL:

```sql
CREATE DATABASE biblioteca_db;
```

### 2. Variables de Entorno

Se recomienda configurar las siguientes variables de entorno para producción:

```bash
export DB_URL=jdbc:mysql://localhost:3306/biblioteca_db?useSSL=false&serverTimezone=UTC
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_password
export JWT_SECRET=tuClaveSecretaMuySeguraDeAlMenos256Bits
export JWT_EXPIRATION=86400000
```

### 3. application.properties

El archivo ya incluye valores por defecto que pueden ser sobrescritos con variables de entorno.

## 🚀 Ejecución

### Compilar el proyecto

```bash
mvn clean install
```

### Ejecutar la aplicación

```bash
mvn spring-boot:run
```

O usando el JAR generado:

```bash
java -jar target/biblioteca-backend-1.0.0.jar
```

La aplicación estará disponible en: `http://localhost:8081`

## 📖 Documentación API

### Swagger UI

Accede a la documentación interactiva en:

```
http://localhost:8081/swagger-ui.html
```

### OpenAPI JSON

```
http://localhost:8081/v3/api-docs
```

## 🔐 Endpoints Principales

### Autenticación

- `POST /auth/registro` - Registrar nuevo usuario
- `POST /auth/login` - Iniciar sesión (obtener token JWT)

### Libros

- `GET /api/libros` - Listar todos los libros (requiere autenticación)
- `GET /api/libros/{id}` - Obtener libro por ID
- `POST /api/libros` - Crear libro (solo ADMIN)
- `PUT /api/libros/{id}` - Actualizar libro (solo ADMIN)
- `DELETE /api/libros/{id}` - Eliminar libro (solo ADMIN)

### Ejemplo de uso con JWT

1. **Login**:
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}'
```

2. **Usar el token**:
```bash
curl -X GET http://localhost:8081/api/libros \
  -H "Authorization: Bearer TU_TOKEN_JWT_AQUI"
```

## 📊 Monitoreo

Spring Boot Actuator endpoints:

- `GET /actuator/health` - Estado de salud
- `GET /actuator/info` - Información de la aplicación
- `GET /actuator/metrics` - Métricas de la aplicación

## 🏗️ Estructura del Proyecto

```
src/main/java/com/example/biblioteca/
├── controller/          # Controladores REST
├── dto/                # Data Transfer Objects
├── model/              # Entidades JPA
├── repository/         # Repositorios Spring Data
├── service/            # Lógica de negocio
│   └── impl/          # Implementaciones
├── security/           # Configuración de seguridad y JWT
└── config/             # Configuraciones generales

src/main/java/com/example/
├── exception/          # Manejo de excepciones
└── config/             # Configuraciones compartidas
```

## 🔒 Seguridad

- Contraseñas encriptadas con BCrypt
- JWT con firma HMAC-SHA256
- Tokens con expiración configurable (default: 24 horas)
- Protección CSRF deshabilitada (API REST stateless)
- Validación de entrada en todos los endpoints

## 📝 Mejoras Implementadas

### ✅ Seguridad
- JWT filter activado y funcional
- Credenciales externalizadas con variables de entorno
- Manejo robusto de errores de autenticación

### ✅ Código Limpio
- Uso de Lombok (@Data, @Builder, @RequiredArgsConstructor)
- Validaciones con Jakarta Validation
- DTOs separados de entidades
- Logging estructurado en servicios

### ✅ Robustez
- GlobalExceptionHandler completo
- Excepciones personalizadas
- Transacciones configuradas
- Auditoría automática con timestamps

### ✅ Documentación
- Swagger/OpenAPI completamente configurado
- README detallado
- Comentarios y logs descriptivos

### ✅ Monitoreo
- Spring Boot Actuator
- Health checks
- Métricas de aplicación

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia Apache 2.0.

## 👥 Autores

- Equipo de Desarrollo - *Trabajo inicial*

## 📞 Soporte

Para soporte, contactar a: soporte@biblioteca.com