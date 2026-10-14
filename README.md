# Librería

Esta API REST sirve para registrar libros, usuarios y préstamos de una biblioteca. Antes de guardar un préstamo revisa que el usuario esté activo y que todavía quede algún ejemplar disponible.

La fecha del préstamo se coloca automáticamente. La fecha de devolución queda vacía hasta que el libro se regresa. La contraseña del usuario se recibe al registrarlo, pero no aparece en las respuestas de la API.

## Requisitos

- Java 21.
- MySQL 8 o una versión compatible.
- Git, solamente si se va a clonar el repositorio.

El proyecto incluye Maven Wrapper, por lo que no hace falta instalar Maven.

## Descargar y ejecutar en Windows

```powershell
git clone https://github.com/ErickGHR/libreria-api.git
cd libreria-api
.\run-local.ps1
```

El script pregunta la contraseña local de MySQL. La contraseña no queda guardada en el código y la base `libreria` se crea automáticamente si el usuario tiene permiso.

También se puede iniciar con:

```powershell
$env:DB_USER="root"
$env:DB_PASSWORD="TU_CONTRASEÑA"
.\mvnw.cmd spring-boot:run
```

La dirección de la API es `http://localhost:8080`.

## Endpoints principales

```text
/api/libros
/api/usuarios
/api/prestamos
```

Los tres recursos permiten consultar, crear, modificar y eliminar registros. Para marcar la devolución de un libro se usa:

```text
PATCH /api/prestamos/{id}/devolver
```

## Orden recomendado para probar

1. Registrar un libro.
2. Registrar un usuario.
3. Crear un préstamo usando el ISBN y el id del usuario.
4. Consultar los préstamos.
5. Marcar la devolución y comprobar que se guarda la fecha.

La colección `postman/Libreria.postman_collection.json` ya tiene esas peticiones preparadas para Postman.

## Pruebas automáticas

```powershell
.\mvnw.cmd test
```

Para detener la aplicación se usa `Ctrl + C`.
