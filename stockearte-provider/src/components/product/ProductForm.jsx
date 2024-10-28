import React, { useState } from 'react'
import { createProduct } from '../../services/productService';
import { useKafka } from '../../hooks/useKafka';

export const ProductForm = () => {

    const [product, setProduct] = useState({
        nombre: '',
        codigo: '',
        foto: '',
        color: '',
        talle: '',
        stock: 0
    });

    const { sendMessage } = useKafka('/novedades');

    const handleChange = (e) => {
        setProduct({ ...product, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const createdProduct = await createProduct(product);
            await sendMessage({
                codigoProducto: createdProduct.codigo,
                talles: [createdProduct.talle],
                colores: [createdProduct.color],
                urls: [createdProduct.foto]
            });
            setProduct({ nombre: '', codigo: '', foto: '', color: '', talle: '', stock: 0 });
            alert('Producto creado con éxito y enviado como novedad');
        } catch (error) {
            console.error('Error al crear producto:', error);
            alert('Error al crear producto');
        }
    };

    return (
        <>
            <form onSubmit={handleSubmit} className="max-w-md mx-auto mt-8">
                <h2 className="text-2xl font-bold mb-4">Crear Nuevo Producto</h2>
                <div className="mb-4">
                    <label htmlFor="nombre" className="block mb-1">Nombre</label>
                    <input
                        type="text"
                        id="nombre"
                        name="nombre"
                        value={product.nombre}
                        onChange={handleChange}
                        className="w-full border p-2 rounded"
                        required
                    />
                </div>
                <div className="mb-4">
                    <label htmlFor="codigo" className="block mb-1">Código</label>
                    <input
                        type="text"
                        id="codigo"
                        name="codigo"
                        value={product.codigo}
                        onChange={handleChange}
                        className="w-full border p-2 rounded"
                        required
                    />
                </div>
                <div className="mb-4">
                    <label htmlFor="foto" className="block mb-1">URL de la foto</label>
                    <input
                        type="text"
                        id="foto"
                        name="foto"
                        value={product.foto}
                        onChange={handleChange}
                        className="w-full border p-2 rounded"
                        required
                    />
                </div>
                <div className="mb-4">
                    <label htmlFor="color" className="block mb-1">Color</label>
                    <input
                        type="text"
                        id="color"
                        name="color"
                        value={product.color}
                        onChange={handleChange}
                        className="w-full border p-2 rounded"
                        required
                    />
                </div>
                <div className="mb-4">
                    <label htmlFor="talle" className="block mb-1">Talle</label>
                    <input
                        type="text"
                        id="talle"
                        name="talle"
                        value={product.talle}
                        onChange={handleChange}
                        className="w-full border p-2 rounded"
                        required
                    />
                </div>
                <div className="mb-4">
                    <label htmlFor="stock" className="block mb-1">Stock Inicial</label>
                    <input
                        type="number"
                        id="stock"
                        name="stock"
                        value={product.stock}
                        onChange={handleChange}
                        className="w-full border p-2 rounded"
                        required
                    />
                </div>
                <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
                    Crear Producto
                </button>
            </form>
        </>
    )
}
