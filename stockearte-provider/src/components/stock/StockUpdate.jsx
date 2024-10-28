import React, { useState } from 'react'
import { updateStock } from '../../services/productService';

export const StockUpdate = ({ product, onUpdate }) => {

    const [newStock, setNewStock] = useState(product.stock);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await updateStock(product.id, newStock);
            onUpdate(product.id, newStock);
        } catch (error) {
            console.error('Error al actualizar stock:', error);
        }
    };

    return (
        <>
            <form onSubmit={handleSubmit} className="mt-4">
                <div className="flex items-center space-x-2">
                    <input
                        type="number"
                        value={newStock}
                        onChange={(e) => setNewStock(e.target.value)}
                        className="border p-2 rounded w-24"
                        min="0"
                    />
                    <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                        Actualizar Stock
                    </button>
                </div>
            </form>
        </>
    )
}
