import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { DataGrid } from '@mui/x-data-grid';
import { Button, Typography, Paper } from '@mui/material';

const ConsultaTransacciones = () => {
    const [transacciones, setTransacciones] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [suppliers, setSuppliers] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem('token');

        const fetchSuppliers = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/suppliers', {
                    headers: {
                        'Authorization': token,
                    },
                });
                setSuppliers(response.data);                
            } catch (err) {
                console.error('Error fetching suppliers', err);
                setError('No se pudieron cargar los proveedores.');
            }
        };

        fetchSuppliers();

        const fetchTransacciones = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/getAllTransactions', {
                    headers: {
                        Authorization: token,
                    },
                });
                const formattedTransacciones = response.data.map((transaccion, index) => ({
                    id: index,
                    ...transaccion,
                    formattedDate: formatDate(transaccion.transactionDate),
                    formattedValue: formatCurrency(transaccion.value),
                    supplierName: getSupplierName(transaccion.supplierId),
                }));
                setTransacciones(formattedTransacciones);
            } catch (err) {
                setError('Error al cargar las transacciones.');
            }
        };

        fetchTransacciones();
    }, []);

    const getSupplierName = (supplierId) => {
        const supplier = suppliers.find(p => p.id === supplierId);
        return supplier ? supplier.name : 'Otro';
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    const formatCurrency = (value) => {
        return new Intl.NumberFormat('es-CO', {
            style: 'currency',
            currency: 'COP',
            minimumFractionDigits: 0,
            maximumFractionDigits: 0,
        }).format(value);
    };

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const options = { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit', hour12: false };
        return date.toLocaleString('es-CO', options).replace(',', ''); // Eliminar la coma
    };

    const columns = [
        { field: 'id', headerName: 'ID de Transacción', minWidth: 100, flex: 1 }, // Establecer minWidth
        { field: 'cellPhone', headerName: 'Teléfono', minWidth: 100, flex: 1 },
        { field: 'formattedValue', headerName: 'Valor', minWidth: 100, flex: 1 }, // Usamos el campo formateado
        { field: 'supplierName', headerName: 'Proveedor', minWidth: 100, flex: 1 },
        { field: 'formattedDate', headerName: 'Fecha', minWidth: 150, flex: 1 },
    ];

    return (
        <Paper style={{ padding: '20px', height: 400 }}>
            <Typography variant="h4" gutterBottom>Listado de Transacciones</Typography>
            {error && <Typography color="error">{error}</Typography>}
            <DataGrid
                rows={transacciones}
                columns={columns}
                pageSize={5}
                rowsPerPageOptions={[5, 10, 20]}
                disableSelectionOnClick
                autoHeight
                initialState={{
                    columns: {
                        columnVisibilityModel: {
                            // Configure visibility for each column here
                        },
                    },
                }}
            />

            <div style={{ marginTop: '20px', display: 'flex', gap: '10px' }}>
                <Button variant="outlined" onClick={() => navigate('/recarga')}>
                    Hacer recarga
                </Button>
                <Button variant="outlined" color="secondary" onClick={handleLogout}>
                    Cerrar sesión
                </Button>
            </div>
        </Paper>
    );
};

export default ConsultaTransacciones;
