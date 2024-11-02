import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Button, Typography, Tooltip, IconButton } from '@mui/material';
import PrintIcon from '@mui/icons-material/Print';

const ResumenCompra = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const { transactionDetails } = location.state || {};

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    const handleGoToRecarga = () => {
        navigate('/recarga');
    };

    // Imprimir del navegador
    const handlePrintTransaction = () => {
        window.print();
    };

    const formatCurrency = (value) => {
        return new Intl.NumberFormat('es-CO', {
            style: 'currency',
            currency: 'COP',
            minimumFractionDigits: 0,
            maximumFractionDigits: 0,
        }).format(value);
    };

    if (!transactionDetails) {
        return <div>No hay detalles de la transacción disponibles.</div>;
    }

    return (
        <div>
            <div style={{ display: 'flex', alignItems: 'center' }}>
                <Typography variant="h4" style={{ marginRight: '10px' }}>
                    Resumen de la Transacción
                </Typography>
                <Tooltip title="Imprimir Transacción">
                    <IconButton onClick={handlePrintTransaction} style={{ padding: '0' }}>
                        <PrintIcon />
                    </IconButton>
                </Tooltip>
            </div>

            <div style={{ marginTop: '20px' }}>
                <Typography>Número de Teléfono: <strong>{transactionDetails.cellPhone}</strong></Typography>
                <Typography>Valor: <strong>{formatCurrency(transactionDetails.value)}</strong></Typography> {/* Formato de moneda */}
                <Typography>ID de Transacción: <strong>{transactionDetails.transactionalID}</strong></Typography>
            </div>

            <div style={{ marginTop: '20px', display: 'flex', gap: '10px' }}>
                <Button variant="contained" color="primary" onClick={handleGoToRecarga}>
                    Hacer otra recarga
                </Button>
                <Button variant="outlined" color="secondary" onClick={handleLogout}>
                    Cerrar sesión
                </Button>
            </div>
        </div>
    );
};

export default ResumenCompra;
