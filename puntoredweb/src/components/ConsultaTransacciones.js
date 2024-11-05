import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { DataGrid } from '@mui/x-data-grid';
import { Button, Typography, Paper } from '@mui/material';

// Configurar un interceptor de petición para registrar las peticiones
axios.interceptors.request.use(
    (config) => {
        console.log('Realizando petición a:', config.url);
        console.log('Método:', config.method);
        console.log('Datos:', config.data);
        return config;
    },
    (error) => {
        console.error('Error en la petición', error);
        return Promise.reject(error);
    }
);

// Configurar un interceptor de respuesta para registrar las respuestas
axios.interceptors.response.use(
    (response) => {
        console.log('Respuesta recibida de:', response.config.url);
        console.log('Datos:', response.data); // Aquí puedes ver los datos de la respuesta
        return response;
    },
    (error) => {
        console.error('Error en la respuesta:', error);
        return Promise.reject(error);
    }
);

const ConsultaTransacciones = () => {
    const [transacciones, setTransacciones] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [suppliers, setSuppliers] = useState([]);

    // Estado para controlar si los proveedores han sido cargados
    const [suppliersLoaded, setSuppliersLoaded] = useState(false);

    // Cargar proveedores y transacciones
    useEffect(() => {
        const token = localStorage.getItem('token');

        // Intentamos cargar los proveedores desde localStorage
        const storedSuppliers = localStorage.getItem('suppliers');
        if (storedSuppliers) {
            setSuppliers(JSON.parse(storedSuppliers));
            setSuppliersLoaded(true); // Indicamos que los proveedores están listos
        } else {
            const fetchSuppliers = async () => {
                try {
                    const response = await axios.get(`${process.env.REACT_APP_PUNTO_RED_URL}/getSuppliers`, {
                        headers: {
                            'Authorization': token,
                        },
                    });
                    setSuppliers(response.data);
                    localStorage.setItem('suppliers', JSON.stringify(response.data));
                    setSuppliersLoaded(true); // Indicamos que los proveedores están listos
                } catch (err) {
                    console.error('Error fetching suppliers', err);
                    setError('No se pudieron cargar los proveedores.');
                }
            };
            fetchSuppliers();
        }
    }, []); // Este effect solo se ejecuta una vez cuando el componente se monta

    // Ahora que los proveedores están disponibles, cargamos las transacciones
    useEffect(() => {
        if (!suppliersLoaded || suppliers.length === 0) return; // No hacer nada si no se cargaron los proveedores aún

        const token = localStorage.getItem('token');
        const fetchTransacciones = async () => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_API_URL}/getAllTransactions`, {
                    headers: {
                        Authorization: token,
                    },
                });
                const formattedTransacciones = response.data.map((transaccion, index) => ({
                    id: index,
                    ...transaccion,
                    formattedDate: formatDate(transaccion.transactionDate),
                    formattedValue: formatCurrency(transaccion.value),
                    supplierName: getSupplierName(transaccion.supplierId, suppliers),
                }));
                setTransacciones(formattedTransacciones);
            } catch (err) {
                setError('Error al cargar las transacciones.');
            }
        };

        fetchTransacciones();
    }, [suppliersLoaded, suppliers]); // Este effect depende de suppliersLoaded y suppliers

    const getSupplierName = (supplierId, suppliers) => {
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
        { field: 'id', headerName: 'ID de Transacción', minWidth: 100, flex: 1 },
        { field: 'cellPhone', headerName: 'Teléfono', minWidth: 100, flex: 1 },
        { field: 'formattedValue', headerName: 'Valor', minWidth: 100, flex: 1 },
        { field: 'supplierName', headerName: 'Proveedor', minWidth: 100, flex: 1 },
        { field: 'formattedDate', headerName: 'Fecha', minWidth: 150, flex: 1 },
    ];

    return (
        <Paper style={{ padding: '20px' }}>
            <div style={{ marginBottom: '20px', display: 'flex', gap: '10px' }}>
                <Typography variant="h4" gutterBottom>Listado de Transacciones</Typography>
                <Button variant="outlined" onClick={() => navigate('/recarga')}>
                    Hacer recarga
                </Button>
                <Button variant="outlined" color="secondary" onClick={handleLogout}>
                    Cerrar sesión
                </Button>
            </div>
            {error && <Typography color="error">{error}</Typography>}
            <DataGrid
                rows={transacciones}
                columns={columns}
                pageSize={5}
                rowsPerPageOptions={[5, 10, 20]}
                disableSelectionOnClick
            />
        </Paper>
    );
};

export default ConsultaTransacciones;
