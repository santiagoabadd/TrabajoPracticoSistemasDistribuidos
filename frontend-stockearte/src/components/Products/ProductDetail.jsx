import React, { useState, useEffect } from 'react'
import { mockFetchProducts } from '../../api/mockAPIProducts';

export const ProductDetail = ({ product, onClose, onUpdate, userRole, userStoreId, isAdding   }) => {

  const [productData, setProductData] = useState({
    id: '',
    name: '',
    code: '',
    size: '',
    // photo: '',
    color: '',
    // stock: {},
    assignedStores: [],
    stock: {},
  });
  const [stores, setStores] = useState([]);
  const [loading, setLoading] = useState(true);


  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        if (userRole === 'Casa Central' || userRole === 'central') {
          const storesResponse = await mockFetchProducts('/api/stores');
          const storesData = await storesResponse.json();
          console.log('Stores loaded:', storesData);
          setStores(storesData || []);
        }

        if (!isAdding && product) {
          // Cargar los datos del producto existente
          setProductData({
            id: product.id,
            name: product.name,
            code: product.code,
            size: product.size,
            // photo: product.photo,
            color: product.color,
            // stock: product.stock || {},
            assignedStores: Object.keys(product.stock || {}),
            stock: product.stock || {},
          });
        } else if (isAdding) {
          setProductData({
            id: generateProductId(),
            code: generateProductCode(),
            name: '',
            size: '',
            color: '',
            // stock: {},
            assignedStores: [],
            stock: {},
          });
        }
      } catch (error) {
        console.error('Error fetching data:', error);
        setStores([]);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [product, userRole, userStoreId, isAdding]);

  const generateProductId = () => {
    return `P${Date.now()}`;
  };

  const generateProductCode = () => {
    return Math.random().toString(36).substring(2, 12).toUpperCase();
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      let updatedProductData = { ...productData };
      
      if (userRole === 'Casa Central') {
        updatedProductData.stock = productData.assignedStores.reduce((acc, storeId) => {
          acc[storeId] = productData.stock[storeId] || 0;
          return acc;
        }, {});
      } else if (userRole === 'Tienda') {
        // Only update stock for this store
        updatedProductData.stock = {
          ...product.stock,
          [userStoreId]: parseInt(productData.stock[userStoreId] || 0, 10)
        };
      }
  
      const options = userRole === 'Tienda' ? { idTienda: userStoreId } : {};
      const method = isAdding ? 'POST' : 'PUT';
      const url = isAdding ? '/api/products' : `/api/products/${product.id}`;
      
      const response = await mockFetchProducts(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedProductData),
        ...options
      });
  
      if (response.ok) {
        const responseData = await response.json();
        onUpdate(responseData);
        onClose();
      }
    } catch (error) {
      console.error('Error updating/adding product:', error);
    }
  };

  const handleStoreSelection = (e) => {
    const selectedStores = Array.from(e.target.selectedOptions, option => option.value);
    console.log(selectedStores);
    setProductData(prevData => ({
      ...prevData,
      assignedStores: selectedStores,
      // stock: selectedStores.reduce((acc, storeId) => {
      //   acc[storeId] = prevData.stock[storeId] || 0;
      //   return acc;
      // }, {}),
    }));
  };

  const handleStockChange = (e) => {
    const newStock = parseInt(e.target.value, 10) || 0;
    setProductData(prevData => ({
      ...prevData,
      stock: {
        ...prevData.stock,
        [userStoreId]: newStock
      }
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
    <>
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full" style={{zIndex: 1000}}>
          <div className="relative p-8 border w-full max-w-2xl mx-auto my-10 shadow-lg rounded-md bg-white">
            <button onClick={onClose} className="absolute top-2 right-2 text-gray-600 hover:text-gray-800">&times;</button>
            <h3 className="text-2xl font-bold mb-6">{isAdding ? 'Add New Product' : 'Edit Product'}</h3>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label htmlFor="name" className="block text-sm font-medium text-gray-700 mb-1">Name</label>
                <input
                  id="name"
                  type="text"
                  value={productData.name}
                  onChange={(e) => setProductData({...productData, name: e.target.value})}
                  className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Product Name"
                  required
                  disabled={userRole === 'Tienda'}
                />
              </div>
              <div>
                <label htmlFor="code" className="block text-sm font-medium text-gray-700 mb-1">Code</label>
                <input
                  id="code"
                  type="text"
                  value={productData.code}
                  className="w-full px-3 py-2 border rounded-md bg-gray-100"
                  placeholder="Code"
                  readOnly
                />
              </div>
              <div>
                <label htmlFor="size" className="block text-sm font-medium text-gray-700 mb-1">Size</label>
                <input
                  id="size"
                  type="text"
                  value={productData.size}
                  onChange={(e) => setProductData({...productData, size: e.target.value})}
                  className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Size"
                  required
                  disabled={userRole === 'Tienda'}
                />
              </div>
              <div>
                <label htmlFor="color" className="block text-sm font-medium text-gray-700 mb-1">Color</label>
                <input
                  id="color"
                  type="text"
                  value={productData.color}
                  onChange={(e) => setProductData({...productData, color: e.target.value})}
                  className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Color"
                  required
                  disabled={userRole === 'Tienda'}
                />
              </div>
              {userRole === 'Casa Central' && (
                <div>
                  <label htmlFor="stores" className="block text-sm font-medium text-gray-700 mb-1">Assign to Stores</label>
                  <select
                    id="stores"
                    multiple
                    value={productData.assignedStores}
                    onChange={handleStoreSelection}
                    className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    size={5}
                  >
                    {stores.map((store) => (
                      <option key={store.id_tienda} value={store.id_tienda}>
                        {store.id_tienda}
                      </option>
                    ))}
                  </select>
                  <p className="text-sm text-gray-500 mt-1">Hold Ctrl (Cmd on Mac) to select multiple stores</p>
                </div>
                
                
              )}
              {userRole === 'Casa Central' && (
              <div>
                  <label htmlFor="stock" className="block text-sm font-medium text-gray-700 mb-1">Stock</label>
                  <input
                    id="stock"
                    type="number"
                    value={productData.stock[userStoreId] || 0}
                    onChange={handleStockChange}
                    className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    placeholder="Stock"
                    min="0"
                    disabled={userRole === 'Casa Central'}
                  />
              </div>
              )}
              {userRole === 'Tienda' && (
                <div>
                  <label htmlFor="stock" className="block text-sm font-medium text-gray-700 mb-1">Stock</label>
                  <input
                    id="stock"
                    type="number"
                    value={productData.stock[userStoreId] || 0}
                    onChange={handleStockChange}
                    className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                    placeholder="Stock"
                    min="0"
                  />
                </div>
              )}
              <div className="flex justify-end space-x-4 mt-6">
                <button type="button" onClick={onClose} className="px-4 py-2 bg-gray-300 text-gray-800 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500">
                  Cancel
                </button>
                <button type="submit" className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
                  {isAdding ? 'Add Product' : 'Update Product'}
                </button>
              </div>
            </form>
          </div>
        </div>
    </>
  )
}
