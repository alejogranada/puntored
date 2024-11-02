# Repositorio Puntored Prueba

Este repositorio contiene la implementación de una API para la plataforma Puntored, con el proposito de contectarse a la API de un tercero y ofrecer servicio de recargas de operadores móviles
La estructura está organizada en varios directorios y archivos específicos para facilitar el desarrollo de la parte del backend y una página Web que se conecte al API desarrollada.

## Estructura del Proyecto

- **/puntoredapi**: Carpeta con la API en SpringBoot para exponer los endpoint consumidos por la Web para realizar las recargas segun el operador. Se tiene una Autenticación con el API utilizando token de tipo “Bearer”.

- **/puntoredweb**: Carpeta con la parte FrontEnd que le permita interactuar al usuario para realizar el proceso de compra de la recarga,  resumen y consulta de las transacciones.

## Uso del Proyecto **Backend**

1. **Construcción**: Utiliza `mvnw clean install` para construir el proyecto y gestionar dependencias.
2. **Ejecución**: Ejecuta la aplicación utilizando `PuntoredApiApplication` o el comando `mvnw spring-boot:run`.
3. **Pruebas**: Las pruebas están en `test/java/com/example/PuntoredAPI/` y se pueden ejecutar con `mvnw test` (Opcional TODO).

## Documentación y Recursos Adicionales

- En la carpeta `src/main/resources/static/docs/bd`:
  - **Script de base de datos (`puntored_db.sql`)**: Para crear y poblar la base de datos `puntored_db` necesaria para la aplicación.
  - **README.md**: Explicación de la estructura y propósito del script de base de datos.
- En la carpeta `src/main/resources/static/docs/postman`:
  - **Colección de Postman (`Puntored_API_Postman_Collection.json`)**: Para importar en Postman y realizar pruebas de los endpoints de la API.
  - **README.md**: Guía de uso de la colección de Postman para pruebas.

