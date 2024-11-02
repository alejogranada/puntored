const express = require('express');
const { exec } = require('child_process');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 3000;

// Ejecutar la aplicación Spring Boot
const springApp = exec('java -jar target/PuntoredAPI-0.0.1-SNAPSHOT.jar');

springApp.stdout.on('data', (data) => {
    console.log(`stdout: ${data}`);
});

springApp.stderr.on('data', (data) => {
    console.error(`stderr: ${data}`);
});

// Redirigir las solicitudes al backend de Spring Boot
app.use((req, res) => {
    const url = `http://localhost:8080${req.originalUrl}`;
    req.pipe(request(url)).pipe(res);
});

// Iniciar el servidor
app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
