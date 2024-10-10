import { api } from './api';

export const getProducts = async () => {
  const response = await api.get('/productos');
  return response.data;
};

export const createProduct = async (productData) => {
  const response = await api.post('/productos', productData);
  return response.data;
};

export const updateStock = async (productId, newStock) => {
  const response = await api.put(`/productos/${productId}/stock`, { stock: newStock });
  return response.data;
};