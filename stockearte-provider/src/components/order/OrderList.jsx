import React, { useEffect, useState } from 'react'
import { getOrders, processOrder } from '../../services/orderService';
import { Link } from 'react-router-dom';

export const OrderList = () => {

    const [orders, setOrders] = useState([]);

    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        const data = await getOrders();
        setOrders(data);
    };

    const handleProcessOrder = async (orderId, action) => {
        try {
            await processOrder(orderId, action);
            fetchOrders(); // Recargar órdenes después de procesar
        } catch (error) {
            console.error('Error al procesar la orden:', error);
        }
    };


    return (
        <>
            <div className="mt-8">
                <h2 className="text-2xl font-bold mb-4">Órdenes de Compra</h2>
                <table className="w-full border-collapse border">
                    <thead>
                        <tr className="bg-gray-200">
                            <th className="border p-2">ID Orden</th>
                            <th className="border p-2">Tienda</th>
                            <th className="border p-2">Estado</th>
                            <th className="border p-2">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders.map((order) => (
                            <tr key={order.id}>
                                <td className="border p-2">{order.id}</td>
                                <td className="border p-2">{order.codigoTienda}</td>
                                <td className="border p-2">{order.estado}</td>
                                <td className="border p-2">
                                    <Link to={`/orders/${order.id}`} className="text-blue-500 hover:text-blue-700 mr-2">
                                        Ver Detalles
                                    </Link>
                                    {order.estado === 'SOLICITADA' && (
                                        <>
                                            <button
                                                onClick={() => handleProcessOrder(order.id, 'ACCEPT')}
                                                className="bg-green-500 text-white px-2 py-1 rounded mr-2 hover:bg-green-600"
                                            >
                                                Aceptar
                                            </button>
                                            <button
                                                onClick={() => handleProcessOrder(order.id, 'REJECT')}
                                                className="bg-red-500 text-white px-2 py-1 rounded hover:bg-red-600"
                                            >
                                                Rechazar
                                            </button>
                                        </>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </>
    )
}
