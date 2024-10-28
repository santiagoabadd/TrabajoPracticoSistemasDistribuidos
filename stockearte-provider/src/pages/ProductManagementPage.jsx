import React from 'react'
import { ProductList } from '../components/product/ProductList';
import { ProductForm } from '../components/product/ProductForm';

export const ProductManagementPage = () => {
    return (
        <>
            <div className="container mx-auto px-4">
                <h1 className="text-3xl font-bold my-8">Gestión de Productos</h1>
                <ProductForm />
                <ProductList />
            </div>

        </>
    )
}
