import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Typography, CircularProgress } from '@mui/material';

const Login = () => {
    const [user, setUser] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const validateForm = () => {
        if (!user || !password) {
            setError('Usuario y Contraseña son requeridos');
            return false;
        }
        return true;
    };

    const handleLogin = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        if (!validateForm()) return;

        try {
            const response = await axios.post(
                `${process.env.REACT_APP_PUNTO_RED_URL}/auth`,
                { user, password },
                {
                    headers: {
                        'x-api-key': process.env.REACT_APP_PUNTO_RED_API_KEY,
                    },
                }
            );

            localStorage.setItem('token', response.data.token);
            navigate('/recarga');
        } catch (error) {
            console.error('handleLogin.error:', error);
            if (error.response) {
                setError(error.response.data?.message || 'Credenciales incorrectas.');
            } else if (error.request) {
                setError('No se pudo conectar con el servidor');
            } else {
                setError('Error desconocido');
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={{ maxWidth: '400px', margin: 'auto', padding: '20px' }}>
            <Typography variant="h5" component="h2" gutterBottom>
                Inicio de Sesión
            </Typography>
            <form onSubmit={handleLogin}>
                <TextField
                    label="Usuario"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={user}
                    onChange={(e) => setUser(e.target.value)}
                    required
                />
                <TextField
                    label="Contraseña"
                    type="password"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <Button type="submit" variant="contained" color="primary" fullWidth disabled={loading}>
                    {loading ? <CircularProgress size={24} /> : 'Iniciar Sesión'}
                </Button>
            </form>
            {error && <Typography color="error" variant="body2" style={{ marginTop: '10px' }}>{error}</Typography>}
        </div>
    );
};

export default Login;
