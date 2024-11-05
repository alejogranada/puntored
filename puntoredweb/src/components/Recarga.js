import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { TextField, Select, MenuItem, Button, Typography, Snackbar } from '@mui/material';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

const RecargaComponent = ({ token }) => {
    const navigate = useNavigate();
    const [suppliers, setSuppliers] = useState([]);
    const [cellPhone, setCellPhone] = useState('');
    const [value, setValue] = useState('');
    const [selectedSupplierId, setSelectedSupplierId] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    // Obtener la lista de proveedores
    useEffect(() => {
        const fetchSuppliers = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get(`${process.env.REACT_APP_PUNTO_RED_URL}/getSuppliers`, {
                    headers: {
                        'Authorization': token,
                    },
                });
                setSuppliers(response.data);
                // Guardar los proveedores en localStorage
                localStorage.setItem('suppliers', JSON.stringify(response.data));
                // Seleccionar el primer proveedor si existe
                if (response.data.length > 0) {
                    setSelectedSupplierId(response.data[0].id);
                }
            } catch (err) {
                console.error('Error fetching suppliers', err);
                setError('No se pudieron cargar los proveedores.');
            }
        };

        fetchSuppliers();
    }, [token]);

    const handleRecarga = async () => {
        setError('');
        setSuccessMessage('');
        setLoading(true); // Inicia el estado de carga
        const token = localStorage.getItem('token');

        try {
            const response = await axios.post(
                `${process.env.REACT_APP_API_URL}/buy`,
                { cellPhone, value, supplierId: selectedSupplierId },
                {
                    headers: {
                        'Authorization': token,
                    },
                }
            );

            setSuccessMessage(`Recarga exitosa! ID de transacción: ${response.data.transactionalID}`);
            navigate('/resumen', { state: { transactionDetails: response.data } });
        } catch (error) {
            console.log('handleRecarga.response:', error);
            setError('Error en la recarga: ' + (error.response?.data?.message || 'Intente de nuevo.'));
        } finally {
            setLoading(false); // Termina el estado de carga
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    return (
        <div style={{ maxWidth: '400px', margin: 'auto', padding: '20px' }}>
            <Typography variant="h5" gutterBottom>
                Realizar Recarga
            </Typography>
            <TextField
                label="Número de celular"
                variant="outlined"
                fullWidth
                margin="normal"
                value={cellPhone}
                onChange={(e) => setCellPhone(e.target.value)}
                required
            />
            <TextField
                label="Valor de la recarga"
                type="number"
                variant="outlined"
                fullWidth
                margin="normal"
                value={value}
                onChange={(e) => setValue(e.target.value)}
                required
            />
            <Select
                value={selectedSupplierId}
                onChange={(e) => setSelectedSupplierId(e.target.value)}
                displayEmpty
                fullWidth
                margin="normal"
                variant="outlined"
            >
                <MenuItem value="" disabled>
                    Seleccione un proveedor
                </MenuItem>
                {suppliers.map((supplier) => (
                    <MenuItem key={supplier.id} value={supplier.id}>
                        {supplier.name}
                    </MenuItem>
                ))}
            </Select>
            <div style={{ marginTop: '15px' }}>
                <Button
                    variant="contained"
                    color="primary"
                    fullWidth
                    onClick={handleRecarga}
                    disabled={loading}
                >
                    {loading ? 'Cargando...' : 'Realizar Recarga'}
                </Button>
            </div>

            {/* Mensajes de éxito y error */}
            <Snackbar open={!!successMessage} autoHideDuration={6000} onClose={() => setSuccessMessage('')}>
                <Alert onClose={() => setSuccessMessage('')} severity="success">
                    {successMessage}
                </Alert>
            </Snackbar>
            <Snackbar open={!!error} autoHideDuration={6000} onClose={() => setError('')}>
                <Alert onClose={() => setError('')} severity="error">
                    {error}
                </Alert>
            </Snackbar>

            <div style={{ marginTop: '20px', display: 'flex', gap: '10px' }}>
                <Button variant="outlined" onClick={() => navigate('/consultas')}>
                    Ver Listado de Transacciones
                </Button>
                <Button variant="outlined" color="secondary" onClick={handleLogout}>
                    Cerrar sesión
                </Button>
            </div>

        </div>
    );
};

export default RecargaComponent;
