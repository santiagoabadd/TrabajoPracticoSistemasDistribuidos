import React, { useState, useEffect } from 'react'
import { useKafka } from '../hooks/useKafka';
import { createProduct } from '../services/productService';


export const NoveltiesPage = () => {
    const [novelties, setNovelties] = useState([]);
    const { messages } = useKafka('/novedades');

    useEffect(() => {
        setNovelties(messages);
    }, [messages]);


    const handleAddProduct = async (novelty) => {
        try {
            await createProduct({
                codigo: novelty.codigoProducto,
                talles: novelty.talles,
                colores: novelty.colores,
                fotos: novelty.urls
            });
            setNovelties(prevNovelties => prevNovelties.filter(n => n.codigoProducto !== novelty.codigoProducto));
        } catch (error) {
            console.error('Error al agregar producto:', error);
        }
    };

    return (
        <>
            <div className="container mx-auto px-4">
                <h1 className="text-3xl font-bold my-8">Novedades de Productos</h1>
                {novelties.map((novelty, index) => (
                    <div key={index} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
                        <h2 className="text-2xl font-bold mb-4">{novelty.codigoProducto}</h2>
                        <h3 className="text-xl font-bold mt-4 mb-2">Talles y Colores Disponibles</h3>
                        {novelty.talles.map((talle, talleIndex) => (
                            <div key={talleIndex}>
                                <p><strong>Talle:</strong> {talle}</p>
                                <p><strong>Colores:</strong> {novelty.colores[talleIndex].join(', ')}</p>
                            </div>
                        ))}
                        <h3 className="text-xl font-bold mt-4 mb-2">Fotos</h3>
                        <div className="flex flex-wrap">
                            {novelty.urls.map((url, urlIndex) => (
                                <img key={urlIndex} src={url} alt={`Producto ${novelty.codigoProducto}`} className="w-1/4 p-2" />
                            ))}
                        </div>
                        <button
                            onClick={() => handleAddProduct(novelty)}
                            className="mt-4 bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
                        >
                            Agregar al Catálogo
                        </button>
                    </div>
                ))}
            </div>

        </>
    )
}
