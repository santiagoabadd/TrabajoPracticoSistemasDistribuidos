import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../../context/AuthContext';
import { ProductDetail } from './ProductDetail';
import Swal from 'sweetalert2';
import axios from 'axios';


interface Product {
  id?: string; 
  codigo: string;
  nombre: string;
  foto: string;
}

export const ProductList: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [filter, setFilter] = useState<{ nombre: string; codigo: string }>({
    nombre: '',
    codigo: '',
  });
  const { user } = useContext(AuthContext);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [isAddingProduct, setIsAddingProduct] = useState(false);

  useEffect(() => {
    if (user.logged) {
      fetchProducts();
    }
  }, [user.idTienda, user.role]);

  const fetchProducts = async () => {
    try {
      const options = user.role === 'Tienda' ? { params: { idTienda: user.idTienda } } : {};
      const response = await axios.get("http://localhost:5000/getProducts", options);
      const data = response.data;
      setProducts(Array.isArray(data.productsInfo) ? data.productsInfo : []);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFilter({ ...filter, [e.target.name]: e.target.value });
  };

  const filteredProducts = products.filter(product =>
    product.nombre.toLowerCase().includes(filter.nombre.toLowerCase()) &&
    product.codigo.includes(filter.codigo)
  );

  const handleDeleteProduct = async (productId: string) => {
    try {
      const result = await Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!',
      });

      if (result.isConfirmed) {
       
        const updatedProducts = products.filter(product => product.id !== productId);
        setProducts(updatedProducts);

        Swal.fire('Deleted!', 'The product has been deleted.', 'success');
      }
    } catch (error) {
      Swal.fire('Error!', 'There was an error deleting the product.', 'error');
      console.error('Error deleting product:', error);
    }
  };

  const handleEdit = (product: Product) => {
    setSelectedProduct(product);
    setIsAddingProduct(false);
  };

  const handleAddProduct = () => {
    const newProduct: Product = {
      codigo: Math.random().toString(36).substring(2, 12).toUpperCase(),
      nombre: '',
      foto: '',
    };
    setSelectedProduct(newProduct);
    setIsAddingProduct(true);
  };

  const handleCloseDetail = () => {
    setSelectedProduct(null);
    setIsAddingProduct(false);
  };

  const handleUpdateProduct = async (updatedProduct: Product) => {
    if (isAddingProduct) {
      setProducts(prevProducts => [...prevProducts, updatedProduct]);
    } else {
      setProducts(prevProducts =>
        prevProducts.map(p => (p.id === updatedProduct.id ? updatedProduct : p))
      );
    }
    handleCloseDetail();
    fetchProducts(); 
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
        <div className="mb-4 grid grid-cols-1 md:grid-cols-2 gap-4">
          <input
            type="text"
            placeholder="Filter by nombre"
            name="nombre"
            value={filter.nombre}
            onChange={handleFilterChange}
            className="border rounded px-2 py-1"
          />
          <input
            type="text"
            placeholder="Filter by codigo"
            name="codigo"
            value={filter.codigo}
            onChange={handleFilterChange}
            className="border rounded px-2 py-1"
          />
        </div>
        <table className="min-w-full bg-white">
          <thead>
            <tr>
              <th className="py-2 px-4 border-b">Name</th>
              <th className="py-2 px-4 border-b">Code</th>
              <th className="py-2 px-4 border-b">Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredProducts.map(product => (
              <tr key={product.id}>
                <td className="py-2 px-4 border-b">{product.nombre}</td>
                <td className="py-2 px-4 border-b">{product.codigo}</td>
                <td className="py-2 px-4 border-b">
                  <button
                    onClick={() => handleEdit(product)}
                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-2 rounded mr-2"
                  >
                    Edit
                  </button>
                  {(user.role === 'Casa Central' || user.role === 'central') && (
                    <button
                      onClick={() => handleDeleteProduct(product.id!)}
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
      {selectedProduct && (
        <ProductDetail
          product={selectedProduct}
          isAdding={isAddingProduct}
          onClose={handleCloseDetail}
          onUpdate={handleUpdateProduct}
          userRole={user.role} 
            userStoreId={user.codigoTienda}  
        />
      )}
    </>
  );
};
