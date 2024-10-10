import React, { useState, useEffect } from 'react'
import { getOrderDetails, updateOrderStatus, createOrder } from '../../services/orderService';
import { useKafka } from '../../hooks/useKafka';

export const OrderDetail = ({ orderId }) => {

    const [order, setOrder] = useState(null);
    const [newOrder, setNewOrder] = useState({
        codigoTienda: '',
        items: [],
        observaciones: ''
    });

    useEffect(() => {
        if (orderId) {
            fetchOrderDetails();
        }
    }, [orderId]);

    const { sendMessage } = useKafka('/orden-de-compra');
    const { sendMessage: sendReceptionMessage } = useKafka('/recepcion');

    const fetchOrderDetails = async () => {
        const data = await getOrderDetails(orderId);
        setOrder(data);
    };

    const handleStatusUpdate = async (newStatus) => {
        try {
            await updateOrderStatus(orderId, newStatus);
            fetchOrderDetails();
        } catch (error) {
            console.error('Error al actualizar estado de la orden:', error);
        }
    };

    const handleCreateOrder = async () => {
        try {
            const createdOrder = await createOrder(newOrder);
            await sendMessage({
                id: createdOrder.id,
                codigoTienda: createdOrder.codigoTienda,
                items: createdOrder.items,
                fechaSolicitud: new Date().toISOString()
            });
            setOrder(createdOrder);
        } catch (error) {
            console.error('Error al crear la orden:', error);
        }
    };

    const handleInputChange = (e) => {
        setNewOrder({ ...newOrder, [e.target.name]: e.target.value });
    };

    const handleAddItem = () => {
        setNewOrder({
            ...newOrder,
            items: [...newOrder.items, { codigoArticulo: '', color: '', talle: '', cantidadSolicitada: 0 }]
        });
    };

    const handleItemChange = (index, field, value) => {
        const updatedItems = newOrder.items.map((item, i) =>
            i === index ? { ...item, [field]: value } : item
        );
        setNewOrder({ ...newOrder, items: updatedItems });
    };

    const handleMarkAsReceived = async () => {
        try {
            await updateOrderStatus(orderId, 'RECIBIDA');
            await sendReceptionMessage({
                id: order.id,
                codigoTienda: order.codigoTienda,
                fechaRecepcion: new Date().toISOString()
            });
            fetchOrderDetails();
        } catch (error) {
            console.error('Error al marcar la orden como recibida:', error);
        }
    };

    if (orderId && !order) return <div>Cargando...</div>;

    return (
        <>
            <div className="mt-8">
                {order ? (
                    <>
                        <h2 className="text-2xl font-bold mb-4">Detalles de la Orden #{order.id}</h2>
                        <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
                            <p><strong>Estado:</strong> {order.estado}</p>
                            <p><strong>Tienda:</strong> {order.codigoTienda}</p>
                            <p><strong>Fecha de Solicitud:</strong> {order.fechaSolicitud}</p>
                            <h3 className="text-xl font-bold mt-4 mb-2">Ítems</h3>
                            <ul>
                                {order.items.map((item, index) => (
                                    <li key={index} className="mb-2">
                                        {item.cantidadSolicitada} x {item.codigoArticulo} - {item.color}, Talle: {item.talle}
                                    </li>
                                ))}
                            </ul>
                            {order.estado === 'SOLICITADA' && (
                                <div className="mt-4">
                                    <button
                                        onClick={() => handleStatusUpdate('ACEPTADA')}
                                        className="bg-green-500 text-white px-4 py-2 rounded mr-2 hover:bg-green-600"
                                    >
                                        Aceptar Orden
                                    </button>
                                    <button
                                        onClick={() => handleStatusUpdate('RECHAZADA')}
                                        className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
                                    >
                                        Rechazar Orden
                                    </button>
                                </div>
                            )}
                            {order.estado === 'ACEPTADA' && (
                                <div className="mt-4">
                                    <button
                                        onClick={handleMarkAsReceived}
                                        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                                    >
                                        Marcar como Recibida
                                    </button>
                                </div>
                            )}
                        </div>
                    </>
                ) : (
                    <>
                        <h2 className="text-2xl font-bold mb-4">Crear Nueva Orden</h2>
                        <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
                            <input
                                type="text"
                                name="codigoTienda"
                                value={newOrder.codigoTienda}
                                onChange={handleInputChange}
                                placeholder="Código de Tienda"
                                className="mb-4 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                            />
                            <textarea
                                name="observaciones"
                                value={newOrder.observaciones}
                                onChange={handleInputChange}
                                placeholder="Observaciones"
                                className="mb-4 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                            />
                            <h3 className="text-xl font-bold mt-4 mb-2">Ítems</h3>
                            {newOrder.items.map((item, index) => (
                                <div key={index} className="mb-4">
                                    <input
                                        type="text"
                                        value={item.codigoArticulo}
                                        onChange={(e) => handleItemChange(index, 'codigoArticulo', e.target.value)}
                                        placeholder="Código de Artículo"
                                        className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                    />
                                    <input
                                        type="text"
                                        value={item.color}
                                        onChange={(e) => handleItemChange(index, 'color', e.target.value)}
                                        placeholder="Color"
                                        className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                    />
                                    <input
                                        type="text"
                                        value={item.talle}
                                        onChange={(e) => handleItemChange(index, 'talle', e.target.value)}
                                        placeholder="Talle"
                                        className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                    />
                                    <input
                                        type="number"
                                        value={item.cantidadSolicitada}
                                        onChange={(e) => handleItemChange(index, 'cantidadSolicitada', parseInt(e.target.value))}
                                        placeholder="Cantidad Solicitada"
                                        className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                    />
                                </div>
                            ))}
                            <button
                                onClick={handleAddItem}
                                className="bg-blue-500 text-white px-4 py-2 rounded mr-2 hover:bg-blue-600"
                            >
                                Agregar Ítem
                            </button>
                            <button
                                onClick={handleCreateOrder}
                                className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
                            >
                                Crear Orden
                            </button>
                        </div>
                    </>
                )}
            </div>
        </>
    )
}
