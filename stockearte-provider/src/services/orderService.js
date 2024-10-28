import { api } from './api';


export const createOrder = async (orderData) => {
  try {
      const response = await api.post('/ordenes', orderData);
      return response.data;
  } catch (error) {
      console.error('Error al crear la orden:', error);
      throw error;
  }
};

export const getOrders = async () => {
  const response = await api.get('/ordenes');
  return response.data;
};

export const getOrderDetails = async (orderId) => {
  const response = await api.get(`/ordenes/${orderId}`);
  return response.data;
};

export const updateOrderStatus = async (orderId, newStatus) => {
  const response = await api.put(`/ordenes/${orderId}/status`, { status: newStatus });
  return response.data;
};


export const getPausedOrders = async () => {
  try {
    const response = await api.get('/ordenes/pausadas');
    return response.data;
  } catch (error) {
    console.error('Error al obtener órdenes pausadas:', error);
    throw error;
  }
};

export const reprocessPausedOrders = async () => {
  try {
    const response = await api.post('/ordenes/reprocesar-pausadas');
    return response.data;
  } catch (error) {
    console.error('Error al reprocesar órdenes pausadas:', error);
    throw error;
  }
};

export const processOrder = async (orderId, action) => {
  try {
    let newStatus;
    if (action === 'ACCEPT') {
      newStatus = 'ACEPTADA';
    } else if (action === 'REJECT') {
      newStatus = 'RECHAZADA';
    } else {
      throw new Error('Acción no válida');
    }

    const updatedOrder = await updateOrderStatus(orderId, newStatus);
    return updatedOrder;
  } catch (error) {
    console.error('Error al procesar la orden:', error);
    throw error;
  }
};
