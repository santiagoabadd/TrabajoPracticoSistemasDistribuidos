import React, { useState } from 'react'
import { OrderList } from '../components/order/OrderList';
import { OrderDetail } from '../components/order/OrderDetail';

export const OrderManagementPage = () => {

    const [selectedOrderId, setSelectedOrderId] = useState(null);

    return (
        <>
            <div className="container mx-auto px-4">
                <h1 className="text-3xl font-bold my-8">Gestión de Órdenes</h1>
                <OrderList onSelectOrder={setSelectedOrderId} />
                {selectedOrderId && <OrderDetail orderId={selectedOrderId} />}
            </div>

        </>
    )
}
