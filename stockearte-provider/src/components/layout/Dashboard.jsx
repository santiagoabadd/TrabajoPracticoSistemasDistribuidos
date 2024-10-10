import React from 'react'
import { ProductList } from '../product/ProductList';
import { OrderList } from '../order/OrderList';
import { useKafka } from '../../hooks/useKafka';

export const Dashboard = () => {

  const { messages: latestOrderUpdates } = useKafka(`/CODIGO_TIENDA/solicitudes`);
  const latestOrderUpdate = latestOrderUpdates[latestOrderUpdates.length - 1];

  return (
    <>
      <div className="container mx-auto px-4">
        <h1 className="text-3xl font-bold my-8">Panel de Control del Proveedor</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          <div>
            <h2 className="text-2xl font-bold mb-4">Resumen de Stock</h2>
            <ProductList />
          </div>
          <div>
            <h2 className="text-2xl font-bold mb-4">Órdenes Pendientes</h2>
            <OrderList />
          </div>
        </div>
        {latestOrderUpdate && (
          <div className="mt-4 p-4 bg-yellow-100 border border-yellow-400 rounded">
            <h3 className="font-bold">Actualización de Orden Recibida:</h3>
            <p>Orden ID: {latestOrderUpdate.orderId}</p>
            <p>Nuevo Estado: {latestOrderUpdate.newStatus}</p>
            <p>Observaciones: {latestOrderUpdate.observaciones}</p>
          </div>
        )}
      </div>
    </>
  )
}
