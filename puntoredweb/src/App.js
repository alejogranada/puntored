import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Recarga from './components/Recarga';
import ResumenCompra from './components/ResumenCompra';
import ConsultaTransacciones from './components/ConsultaTransacciones';
import PrivateRoute from './components/PrivateRoute';
import { ThemeProvider, createTheme } from '@mui/material/styles';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />

          {/* Rutas protegidas */}
          <Route path="/recarga" element={<PrivateRoute><Recarga /></PrivateRoute>} />
          <Route path="/resumen" element={<PrivateRoute><ResumenCompra /></PrivateRoute>} />
          <Route path="/consultas" element={<PrivateRoute><ConsultaTransacciones /></PrivateRoute>} />

          {/* Redirección a login si se accede a una ruta no permitida */}
          <Route path="*" element={<Login />} />
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
