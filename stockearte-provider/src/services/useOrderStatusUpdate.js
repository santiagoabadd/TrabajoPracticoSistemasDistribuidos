import { useEffect } from 'react';
import { subscribeToKafka } from './useKafka';
import { updateOrderStatus } from '../services/orderService';

export const useOrderStatusUpdate = (codigoTienda) => {
    useEffect(() => {
        const topic = `/${codigoTienda}/solicitudes`;
        const subscription = subscribeToKafka(topic, handleOrderUpdate);

        return () => {
            subscription.unsubscribe();
        };
    }, [codigoTienda]);

    const handleOrderUpdate = async (message) => {
        const { idOrden, nuevoEstado, observaciones } = message;
        try {
            await updateOrderStatus(idOrden, nuevoEstado, observaciones);
            // Aquí podrías disparar una acción para actualizar el estado en la UI
        } catch (error) {
            console.error('Error al actualizar el estado de la orden:', error);
        }
    };
};