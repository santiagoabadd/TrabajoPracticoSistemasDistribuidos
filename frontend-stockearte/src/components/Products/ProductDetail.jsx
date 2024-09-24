import React, { useState, useEffect } from 'react'
import { mockFetchProducts } from '../../api/mockAPIProducts';

export const ProductDetail = ({ product, onClose, onUpdate, userRole, userStoreId, isAdding }) => {

  const [productData, setProductData] = useState({
    id: '',
    nombre: '',
    codigo: '',
    foto: '',
  });

  const [storeProductData, setStoreProductData] = useState({
    talle: '',
    color: '',
    stock: 0,
    assignedStores: [],
    selectedStoreId: '',
  });

  const [loading, setLoading] = useState(true);
  const [stores, setStores] = useState([]); 

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {

        const storesResponse = await fetch('http://localhost:5000/getTiendas'); 
        const storesData = await storesResponse.json();
        setStores(storesData.tiendasInfo);
        if (!isAdding && product) {
          

          setProductData({
            id: product.id,
            nombre: product.nombre,
            codigo: product.codigo,
            foto: product.foto,
          });

          setStoreProductData({
            talle: product.talle,
            color: product.color,
            stock: product.stock[userStoreId] || 0,
            assignedStores: Object.keys(product.stock || {}),
          });
          
        } else if (isAdding) {
          setProductData({
            id: generateProductId(),
            codigo: generateProductCode(),
            nombre: '',
            foto: '',
          });
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [product, userRole, userStoreId, isAdding]);

  const generateProductId = () => `P${Date.now()}`;
  const generateProductCode = () => Math.random().toString(36).substring(2, 12).toUpperCase();

  const handleProductSubmit = async (e) => {
    e.preventDefault();
    try {
   
      const response = await fetch('http://localhost:5000/registerProduct', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          nombre: productData.nombre,
          codigo: productData.codigo,
          foto: productData.foto,
        }),
      });

      if (response.ok) {
        
        const searchResponse = await fetch(`http://localhost:5000/getProduct/${productData.codigo}`);
      
     
        const createdProduct = await searchResponse.json();
        console.log('Producto creado:', createdProduct);
        
        
    
     
      console.log(userRole);
      console.log(userRole);
        console.log(createdProduct); 
        console.log(userRole);
        if (userRole === 'Tienda' || userRole === 'Casa Central') {
          await fetch('http://localhost:5000/registerTiendaProducto', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
              productId: Number(createdProduct.id), 
              tiendaId: Number(storeProductData.selectedStoreId), 
              stock: Number(storeProductData.stock),
              talle: storeProductData.talle,
              color: storeProductData.color,
            }),
          });
        }

        onUpdate(createdProduct);
        onClose();
      }
    } catch (error) {
      console.error('Error creating product:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setProductData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleStoreProductChange = (e) => {
    const { name, value } = e.target;
    setStoreProductData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleStoreSelectChange = (e) => {
    const { value } = e.target;
    setStoreProductData((prevData) => ({
      ...prevData,
      selectedStoreId: value, 
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
        <h3 className="text-2xl font-bold mb-6">{isAdding ? 'Add New Product' : 'Edit Product'}</h3>
        <form onSubmit={handleProductSubmit} className="space-y-4">
          
          <div>
            <label htmlFor="nombre" className="block text-sm font-medium text-gray-700 mb-1">Name</label>
            <input
              id="nombre"
              name="nombre"
              type="text"
              value={productData.nombre}
              onChange={handleInputChange}
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Product Name"
              required
            />
          </div>
          <div>
            <label htmlFor="codigo" className="block text-sm font-medium text-gray-700 mb-1">Code</label>
            <input
              id="codigo"
              name="codigo"
              type="text"
              value={productData.codigo}
              className="w-full px-3 py-2 border rounded-md bg-gray-100"
              placeholder="Code"
              readOnly
            />
          </div>
          <div>
            <label htmlFor="foto" className="block text-sm font-medium text-gray-700 mb-1">Photo</label>
            <input
              id="foto"
              name="foto"
              type="text"
              value={productData.foto}
              onChange={handleInputChange}
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Photo URL"
              required
            />
          </div>

        
          <div>
            <label htmlFor="selectedStoreId" className="block text-sm font-medium text-gray-700 mb-1">Select Store</label>
            <select
              id="selectedStoreId"
              name="selectedStoreId"
              value={storeProductData.selectedStoreId}
              onChange={handleStoreSelectChange}
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            >
              <option value="">Choose a store</option>
              {stores.map(store => (
                <option key={store.id} value={store.id}>{store.codigo}</option>
              ))}
            </select>
          </div>

          <div>
            <label htmlFor="talle" className="block text-sm font-medium text-gray-700 mb-1">Size</label>
            <input
              id="talle"
              name="talle"
              type="text"
              value={storeProductData.talle}
              onChange={handleStoreProductChange}
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Size"
              required
            />
          </div>
          <div>
            <label htmlFor="color" className="block text-sm font-medium text-gray-700 mb-1">Color</label>
            <input
              id="color"
              name="color"
              type="text"
              value={storeProductData.color}
              onChange={handleStoreProductChange}
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Color"
              required
            />
          </div>
          <div>
            <label htmlFor="stock" className="block text-sm font-medium text-gray-700 mb-1">Stock</label>
            <input
              id="stock"
              name="stock"
              type="number"
              value={storeProductData.stock}
              onChange={handleStoreProductChange}
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Stock"
              min="0"
              required
            />
          </div>

          <button
            type="submit"
            className="w-full py-2 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700"
          >
            {isAdding ? 'Add Product' : 'Update Product'}
          </button>
        </form>
      </div>
    </div>
  );
};