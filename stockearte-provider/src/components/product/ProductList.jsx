import React, { useState, useEffect } from 'react'
import { getProducts, updateStock } from '../../services/productService';
import { getPausedOrders, reprocessPausedOrders } from '../../services/orderService';
import { StockUpdate } from '../stock/StockUpdate';
import { Alert } from '../ui/alert/Alert';

export const ProductList = () => {

    const [products, setProducts] = useState([]);
    const [pausedOrders, setPausedOrders] = useState([]);
    const [updateMessage, setUpdateMessage] = useState(null);

    useEffect(() => {
        fetchProducts();
        fetchPausedOrders();
    }, []);

    const fetchProducts = async () => {
        try {
            const data = await getProducts();
            setProducts(data);
        } catch (error) {
            console.error('Error fetching products:', error);
            setUpdateMessage({ type: 'error', text: 'Error al cargar productos' });
        }
    };

    const fetchPausedOrders = async () => {
        try {
            const data = await getPausedOrders();
            setPausedOrders(data);
        } catch (error) {
            console.error('Error fetching paused orders:', error);
            setUpdateMessage({ type: 'error', text: 'Error al cargar órdenes pausadas' });
        }
    };

    const handleStockUpdate = async (productId, newStock) => {
        try {
            await updateStock(productId, newStock);
            setProducts(products.map(p =>
                p.id === productId ? { ...p, stock: newStock } : p
            ));
            await reprocessPausedOrders();
            fetchPausedOrders(); // Refresh paused orders after reprocessing
            setUpdateMessage({ type: 'success', text: 'Stock actualizado y órdenes reprocesadas' });
        } catch (error) {
            console.error('Error updating stock:', error);
            setUpdateMessage({ type: 'error', text: 'Error al actualizar stock' });
        }
    };


    return (
        <>
            <div className="mt-8">
                <h2 className="text-2xl font-bold mb-4">Lista de Productos</h2>
                {updateMessage && (
                    <Alert variant={updateMessage.type === 'error' ? 'destructive' : 'default'}>
                        {updateMessage.text}
                    </Alert>
                )}
                <table className="w-full border-collapse border">
                    <thead>
                        <tr className="bg-gray-200">
                            <th className="border p-2">Código</th>
                            <th className="border p-2">Nombre</th>
                            <th className="border p-2">Stock Actual</th>
                            <th className="border p-2">Actualizar Stock</th>
                        </tr>
                    </thead>
                    <tbody>
                        {products.map((product) => (
                            <tr key={product.id}>
                                <td className="border p-2">{product.codigo}</td>
                                <td className="border p-2">{product.nombre}</td>
                                <td className="border p-2">{product.stock}</td>
                                <td className="border p-2">
                                    <StockUpdate product={product} onUpdate={handleStockUpdate} />
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                {pausedOrders.length > 0 && (
                    <div className="mt-8">
                        <h2 className="text-2xl font-bold mb-4">Órdenes Pausadas</h2>
                        <ul>
                            {pausedOrders.map((order) => (
                                <li key={order.id} className="mb-2">
                                    Orden #{order.id} - Tienda: {order.codigoTienda}
                                </li>
                            ))}
                        </ul>
                    </div>
                )}
            </div>
        </>
    )
}
