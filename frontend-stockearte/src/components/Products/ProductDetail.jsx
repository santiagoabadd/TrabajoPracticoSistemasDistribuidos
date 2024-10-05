import React, { useState, useEffect } from 'react';

export const ProductDetail = ({ product, onClose, onUpdate, isAdding, userRole }) => {
  const [productData, setProductData] = useState({
    nombre: '',
    codigo: '',
    foto: '',
    talle: '',
    color: '',
  });

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!isAdding && product) {
      setProductData({
        nombre: product.nombre,
        codigo: product.codigo,
        foto: product.foto,
        talle: product.talle,
        color: product.color,
      });
    } else if (isAdding) {
      setProductData({
        nombre: '',
        codigo: generateProductCode(),
        foto: '',
        talle: '',
        color: '',
      });
    }
    setLoading(false);
  }, [product, isAdding]);

  const generateProductCode = () => {
    return Math.random().toString(36).substring(2, 12).toUpperCase();
  };

  const handleProductSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:5000/registerProduct', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(productData), 
      });

      if (response.ok) {
        const createdProduct = await response.json();
        console.log('Producto creado:', createdProduct);

        onUpdate(createdProduct);
        onClose();
      }
    } catch (error) {
      console.error('Error creando el producto:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProductData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  if (loading) {
    return (
      <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center">
        <div className="animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full" style={{ zIndex: 1000 }}>
      <div className="relative p-8 border w-full max-w-2xl mx-auto my-10 shadow-lg rounded-md bg-white">
        <button onClick={onClose} className="absolute top-2 right-2 text-gray-600 hover:text-gray-800">&times;</button>
        <h3 className="text-2xl font-bold mb-6">{isAdding ? 'Agregar Nuevo Producto' : 'Editar Producto'}</h3>
        
       
        {userRole === 'Casa Central' && (
          <form onSubmit={handleProductSubmit} className="space-y-4">
            <div>
              <label htmlFor="nombre" className="block text-sm font-medium text-gray-700 mb-1">Nombre</label>
              <input
                id="nombre"
                name="nombre"
                type="text"
                value={productData.nombre}
                onChange={handleInputChange}
                className="w-full px-4 py-2 border rounded-md"
                required
              />
            </div>

            <div>
              <label htmlFor="codigo" className="block text-sm font-medium text-gray-700 mb-1">Código</label>
              <input
                id="codigo"
                name="codigo"
                type="text"
                value={productData.codigo}
                readOnly
                className="w-full px-4 py-2 border rounded-md bg-gray-100"
              />
            </div>

            <div>
              <label htmlFor="foto" className="block text-sm font-medium text-gray-700 mb-1">Foto (URL)</label>
              <input
                id="foto"
                name="foto"
                type="text"
                value={productData.foto}
                onChange={handleInputChange}
                className="w-full px-4 py-2 border rounded-md"
                required
              />
            </div>

            <div>
              <label htmlFor="talle" className="block text-sm font-medium text-gray-700 mb-1">Talle</label>
              <input
                id="talle"
                name="talle"
                type="text"
                value={productData.talle}
                onChange={handleInputChange}
                className="w-full px-4 py-2 border rounded-md"
                required
              />
            </div>

            <div>
              <label htmlFor="color" className="block text-sm font-medium text-gray-700 mb-1">Color</label>
              <input
                id="color"
                name="color"
                type="text"
                value={productData.color}
                onChange={handleInputChange}
                className="w-full px-4 py-2 border rounded-md"
                required
              />
            </div>

            <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">
              {isAdding ? 'Agregar Producto' : 'Actualizar Producto'}
            </button>
          </form>
        )}
        {userRole !== 'Casa Central' && (
          <p className="text-red-600">No tienes permisos para editar este producto.</p>
        )}
      </div>
    </div>
  );
};