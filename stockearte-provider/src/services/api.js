import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080/api', // Ajusta según tu configuración
});

