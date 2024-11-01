-- Crear base de datos
CREATE DATABASE IF NOT EXISTS puntored_db;

-- Usar la base de datos creada
USE puntored_db;

-- Crear tabla Transactions
CREATE TABLE transactions (
    id BIGINT NOT NULL AUTO_INCREMENT,
    cell_phone VARCHAR(255), -- Número de celular
    value INT, -- Valor de la transacción
    supplier_id VARCHAR(255), -- ID del proveedor
    transaction_date DATETIME, -- Fecha de la transacción
    transactional_id VARCHAR(255), -- ID transaccional
    PRIMARY KEY (id)
);


-- Insertar datos de ejemplo en la tabla Transactions
INSERT INTO transactions (cell_phone, value, transaction_date, transactional_id, supplier_id) VALUES 
('3210338900', 4000, '2024-11-01 16:41:40', '8ddabb11-a0d4-4e13-bfdc-7aee4f572929', '8753'),
('3124567890', 5500, '2024-11-01 17:15:30', 'a1bcde34-5678-4f12-8a9d-1234abcd5678', '4821'),
('3209876543', 3000, '2024-11-01 18:00:00', 'b2cdfe56-7890-4b34-9cde-2345bcde6789', '5627'),
('3001234567', 7000, '2024-11-01 18:45:20', 'c3def789-1234-4d56-8def-3456cdef7890', '3789'),
('3157890123', 1200, '2024-11-01 19:30:10', 'd4ef0123-4567-4e78-9f01-4567def01234', '9201');
