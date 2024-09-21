import React, { useContext, useEffect, useState } from 'react'
import { AuthContext } from '../../context/AuthContext';
import { mockFetchProducts } from '../../api/mockAPIProducts';
import { ProductDetail } from './ProductDetail';
import Swal from 'sweetalert2';

export const ProductList = () => {

  const [products, setProducts] = useState([]);
  const [filter, setFilter] = useState({ name: '', code: '', size: '', color: '' });
  const { user } = useContext(AuthContext);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [isAddingProduct, setIsAddingProduct] = useState(false);
  const [stores, setStores] = useState([]);
   

  useEffect(() => {
    if (user.logged) {
      fetchProducts();
      fetchStores();
    }
  }, [user.idTienda, user.role])

  const fetchStores = async () => {
    try {
      const response = await mockFetchProducts('/api/stores');
      if (!response.ok) {
        throw new Error('Failed to fetch stores');
      }
      const data = await response.json();
      setStores(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error('Error fetching stores:', error);
    }
  };


  const fetchProducts = async () => {
    try {
      const options = user.role === 'Tienda' ? { idTienda: user.idTienda } : {};
      const response = await mockFetchProducts('/api/products',  { method: 'GET', ...options });
      if (!response.ok) {
        throw new Error('Failed to fetch products');
      }
      const data = await response.json();
      // Procesar los datos para incluir la información de las tiendas asignadas
      const processedData = data.map(product => ({
        ...product,
        assignedStores: Object.keys(product.stock || {})
      }));
      setProducts(processedData);
    } catch (error) {
      console.error('Error fetching products:', error);
    } finally {
    }
  };


  const handleFilterChange = (e) => {
    setFilter({ ...filter, [e.target.name]: e.target.value })
  }

  const filteredProducts = products.filter(product => 
    product.name.toLowerCase().includes(filter.name.toLowerCase()) &&
    product.code.includes(filter.code) &&
    product.size.toLowerCase().includes(filter.size.toLowerCase()) &&
    product.color.toLowerCase().includes(filter.color.toLowerCase())
  )


  const handleDeleteProduct = async (productId) => {
    try {
        const result = await Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        });

        if (result.isConfirmed) {
            const updatedProducts = products.filter(product => product.id !== productId);
            setProducts(updatedProducts);

            Swal.fire(
                'Deleted!',
                'The product has been deleted.',
                'success'
            );
        }
    } catch (error) {
        Swal.fire(
            'Error!',
            'There was an error deleting the product.',
            'error'
        );
        console.error('Error deleting product:', error);
    }
};



  const handleEdit = (product) => {
    setSelectedProduct(product);
    setIsAddingProduct(false);
  };

  const handleAddProduct = () => {
    const newProduct = {
      id: `P${Date.now()}`,
      code: Math.random().toString(36).substring(2, 12).toUpperCase(),
      name: '',
      size: '',
      color: '',
      stock: {},
      assignedStores: [],
      store: user.role === 'Tienda' ? user.idTienda : ''
    };
    setSelectedProduct(newProduct);
    setIsAddingProduct(true);
  };

  const handleCloseDetail = () => {
    setSelectedProduct(null);
    setIsAddingProduct(false);
  };


  const handleUpdateProduct = async (updatedProduct) => {

    
    if (isAddingProduct) {
      setProducts(prevProducts => [...prevProducts, {
        ...updatedProduct,
        assignedStores: updatedProduct.assignedStores || Object.keys(updatedProduct.stock || {})
      }]);
    } else {
      setProducts(prevProducts => prevProducts.map(p => 
        p.id === updatedProduct.id 
          ? {
              ...updatedProduct,
              assignedStores: updatedProduct.assignedStores || Object.keys(updatedProduct.stock || {})
            } 
          : p
      ));
    }
    handleCloseDetail();
    fetchProducts(); // Refresh the product list after update
  };


  return (
    <>
        <div className="container mx-auto px-4 py-8">
            <h2 className="text-2xl font-bold mb-4">Products</h2>
            {(user.role === 'Casa Central' || user.role === 'central') && (
              <button
                onClick={handleAddProduct}
                className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mb-4"
              >
                Add New Product
              </button>
            )}
            <div className="mb-4 grid grid-cols-1 md:grid-cols-4 gap-4">
                <input
                type="text"
                placeholder="Filter by name"
                name="name"
                value={filter.name}
                onChange={handleFilterChange}
                className="border rounded px-2 py-1"
                />
                <input
                type="text"
                placeholder="Filter by code"
                name="code"
                value={filter.code}
                onChange={handleFilterChange}
                className="border rounded px-2 py-1"
                />
                <input
                type="text"
                placeholder="Filter by size"
                name="size"
                value={filter.size}
                onChange={handleFilterChange}
                className="border rounded px-2 py-1"
                />
                <input
                type="text"
                placeholder="Filter by color"
                name="color"
                value={filter.color}
                onChange={handleFilterChange}
                className="border rounded px-2 py-1"
                />
            </div>
            <table className="min-w-full bg-white">
                <thead>
                <tr>
                    <th className="py-2 px-4 border-b">Name</th>
                    <th className="py-2 px-4 border-b">Code</th>
                    <th className="py-2 px-4 border-b">Size</th>
                    <th className="py-2 px-4 border-b">Color</th>
                    <th className="py-2 px-4 border-b">Stock</th>
                    {(user.role === 'Casa Central' || user.role === 'central')  && <th className="py-2 px-4 border-b">Store</th>}
                    <th className="py-2 px-4 border-b">Actions</th>
                </tr>
                </thead>
                <tbody>
                {filteredProducts.map(product => (
                    <tr key={product.id}>
                    <td className="py-2 px-4 border-b">{product.name}</td>
                    <td className="py-2 px-4 border-b">{product.code}</td>
                    <td className="py-2 px-4 border-b">{product.size}</td>
                    <td className="py-2 px-4 border-b">{product.color}</td>
                    <td className="py-2 px-4 border-b">
                    {user.role === 'Tienda' 
                        ? product.stock[user.idTienda] || 0
                        : Object.entries(product.stock).map(([storeId, quantity]) => (
                            <div key={storeId}>{storeId}: {quantity}</div>
                          ))
                      }
                    </td>
                    {(user.role === 'Casa Central' || user.role === 'central') && (
                    <td className="py-2 px-4 border-b">
                      <select className="w-full border rounded px-2 py-1">
                        <option value="">Assigned Stores</option>
                        {product.assignedStores.map(storeId => (
                          <option key={storeId} value={storeId}>
                            {storeId}
                          </option>
                        ))}
                      </select>
                    </td>)}
                    <td className="py-2 px-4 border-b">
                        <button 
                            onClick={() => handleEdit(product)}
                            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-2 rounded mr-2"
                        >
                          {user.role === 'Tienda' ? 'Edit Stock' : 'Edit'}
                        </button>
                        {(user.role === 'Casa Central' || user.role === 'central') && (
                        <button 
                            onClick={() => handleDeleteProduct(product.id)}
                            className="bg-red-500 hover:bg-red-700 text-white font-bold py-1 px-2 rounded"
                        >
                            Delete
                        </button>
                        )}
                    </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
        {(selectedProduct || isAddingProduct) && (
            <ProductDetail 
              product={selectedProduct}
              onClose={handleCloseDetail} 
              onUpdate={handleUpdateProduct}
              userRole={user.role}
              userStoreId={user.idTienda}
              isAdding={isAddingProduct}
            />
        )}
    
    </>
  )
}
