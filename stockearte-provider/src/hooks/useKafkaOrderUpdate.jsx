import { useState, useEffect } from 'react';
import { useKafka } from '../hooks/useKafka';
import { updateOrderStatus } from '../services/orderService';


export const useKafkaOrderUpdate = (codigoTienda) => {
    const [latestUpdate, setLatestUpdate] = useState(null);
    const { messages } = useKafka(`/${codigoTienda}/solicitudes`);

    useEffect(() => {
        if (messages.length > 0) {
            const latestMessage = messages[messages.length - 1];
            setLatestUpdate(latestMessage);
            updateOrderStatus(latestMessage.orderId, latestMessage.newStatus, latestMessage.observaciones);
        }
    }, [messages, codigoTienda]);

    return latestUpdate;
}
