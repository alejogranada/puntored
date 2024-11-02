# Puntored API Collection

## Descripción

Esta carpeta contiene la colección de endpoints de la API de Puntored en formato Postman, facilitando pruebas y validación de los servicios de la API. 

## Estructura de Archivos

- **Puntored_API_Postman_Collection.json**: Archivo que contiene la colección de endpoints para Postman. Este archivo permite importar directamente los endpoints en Postman para probar los diferentes servicios de la API de Puntored.

- **README.md**: Describe el propósito de los archivos dentro de la carpeta y explica cómo usar la colección de Postman para probar los servicios de la API.

## Uso

### Esta colección de Postman permite interactuar con los endpoints de la API de Puntored, tanto en los endpoints expuetos como local. 
### La colección cubre la autenticación, obtención de proveedores, recargas, y manejo de transacciones.

1. **Importar la Colección**: Abre Postman y selecciona la opción de "Importar". Carga el archivo `Puntored_API_Postman_Collection.json` para agregar la colección a tu espacio de trabajo.
2. **Ejecutar Pruebas**: Dentro de Postman, selecciona los endpoints en la colección importada para realizar pruebas y validar la funcionalidad de la API.

Con esta configuración, tendrás acceso rápido a los endpoints de la API de Puntored y podrás realizar pruebas para verificar su funcionamiento.


## Requisitos Previos

1. **Postman**: Asegúrate de tener [Postman](https://www.postman.com/downloads/) instalado para importar y ejecutar esta colección.
2. **Configuración de Variables**:
   - **x-api-key**: La clave de API necesaria para autorizar solicitudes a ciertos endpoints.
   - **authToken**: Token de autenticación generado tras la autenticación inicial.

## Endpoints Principales

### 1. Autenticación
   - **Ruta**: `POST /api/auth`
   - **Descripción**: Obtiene un token de autenticación necesario para las solicitudes posteriores.
   - **Headers**:
     - `x-api-key`: Clave de API proporcionada.
   - **Body**:
     ```json
     {
       "user": "user",
       "password": "pass"
     }
     ```
   - **Nota**: Al autenticarte, el token se almacena en la variable `authToken` automáticamente.

### 2. Obtener Proveedores
   - **Ruta**: `GET /api/getSuppliers`
   - **Descripción**: Recupera una lista de proveedores disponibles.
   - **Headers**:
     - `Authorization`: Bearer {{authToken}} (Token generado en el paso de autenticación).
   - **Prueba**: Valida que la respuesta sea un arreglo de objetos.

### 3. Realizar Recarga
   - **Ruta**: `POST /api/buy`
   - **Descripción**: Realiza una recarga de saldo a un número de celular.
   - **Headers**:
     - `Authorization`: Bearer {{authToken}}
   - **Body**:
     ```json
     {
       "cellPhone": "3210338900",
       "value": 1000,
       "supplierId": "8753"
     }
     ```
   - **Prueba**: Verifica que la respuesta contenga el mensaje `Recarga exitosa` y almacena el `transactionalID`.

### 4. Listar Transacciones (Local)
   - **Ruta**: `GET /api/getAllTransactions`
   - **Descripción**: Recupera todas las transacciones almacenadas localmente.
   - **Prueba**: Verifica que la respuesta sea un arreglo de objetos.

### 5. Obtener Proveedores (Local)
   - **Ruta**: `GET /api/suppliers`
   - **Descripción**: Recupera la lista de proveedores desde el entorno local.
   - **Headers**:
     - `x-api-key`: Clave de API proporcionada.

### 6. Autenticación (Local)
   - **Ruta**: `GET /api/authenticate`
   - **Descripción**: Endpoint local para la autenticación.

### 7. Realizar Recarga (Local)
   - **Ruta**: `POST /api/buy`
   - **Descripción**: Realiza una recarga en el entorno local.
   - **Body**:
     ```json
     {
       "cellPhone": "3210338900",
       "value": 10000,
       "supplierId": "4689"
     }
     ```

### 8. Guardar Transacción (Local)
   - **Ruta**: `POST /api/saveTransaction`
   - **Descripción**: Guarda una transacción en el entorno local.
   - **Body**:
     ```json
     {
       "cellPhone": "3210338900",
       "value": 4000,
       "supplierId": "8753",
       "transactionDate": "2024-11-01T16:41:40",
       "transactionalID": "8ddabb11-a0d4-4e13-bfdc-7aee4f572929"
     }
     ```

### 9. Obtener Transacciones por Celular (Local)
   - **Ruta**: `GET /api/getUserTransactions`
   - **Descripción**: Obtiene las transacciones asociadas a un número de celular específico.
   - **Query Params**:
     - `cellPhone`: Número de celular para consultar.

## Importación de la Colección

1. Descarga la colección en formato `.json` y abre Postman.
2. Selecciona **Importar** y carga el archivo descargado.
3. La colección debería aparecer en tu lista de colecciones de Postman.

## Uso de la Colección

1. Ejecuta el endpoint **Auth** para autenticarte y generar el token de autorización.
2. Utiliza los endpoints **Get Suppliers**, **Buy Recharge**, y **List Transactions** según lo requieras.
3. Configura la variable `x-api-key` si deseas realizar pruebas en el entorno de producción o el entorno local.

## Variables

- **x-api-key**: Clave de API para autorizar solicitudes.
- **authToken**: Token de autorización para el usuario actual (se almacena automáticamente tras la autenticación).

## Notas Adicionales

- Al ejecutar la colección por primera vez, asegúrate de ejecutar el endpoint de **Auth** para generar el `authToken`, que se requerirá en otros endpoints.
- Las pruebas automáticas incluidas en cada solicitud validarán respuestas clave para verificar el éxito de cada operación.
