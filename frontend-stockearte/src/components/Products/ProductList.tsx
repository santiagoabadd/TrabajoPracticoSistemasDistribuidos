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
  color: string;
  talle: string;
  stock?: number;
  associatedStores?: number[]; 
}

interface Store {
  id: number; 
  codigo: string; 
}

export const ProductList: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [filter, setFilter] = useState<{ nombre: string; codigo: string }>({
    nombre: '',
    codigo: '',
  });
  const { user } = useContext(AuthContext);
  const [stores, setStores] = useState<Store[]>([]);

  useEffect(() => {
    if (user.logged) {
      if(user.role==="Tienda"){
        fetchProductsWithStock();
      fetchStores(); 
      }else{
        fetchProducts();
      fetchStores(); 
      }
      
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

  const fetchProductsWithStock = async () => {
    try {
      const options = user.role === 'Tienda' ? { params: { idTienda: user.idTienda } } : {};
      const response = await axios.get("http://localhost:5000/obtenerProductosTienda", options);
      const data = response.data;
      setProducts(Array.isArray(data.products) ? data.products : []);
      console.log(products)
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const fetchStores = async () => {
    try {
      const response = await axios.get("http://localhost:5000/getTiendas");
      const data = response.data;
      setStores(Array.isArray(data.tiendasInfo) ? data.tiendasInfo : []);
    } catch (error) {
      console.error('Error fetching stores:', error);
    }
  };

  

  const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFilter({ ...filter, [e.target.name]: e.target.value });
  };

  const filteredProducts = products.filter(product =>
    product.nombre.toLowerCase().includes(filter.nombre.toLowerCase()) &&
    product.codigo.includes(filter.codigo)
  );

  const handleStoreChange = (productId: string, selectedStoreIds: number[]) => {
    setProducts(products.map(product =>
      product.id === productId ? { ...product, associatedStores: selectedStoreIds } : product
    ));
  };

  const handleSaveAssociations = async (productId: string, storeIds: number[]) => {
    try {
      await axios.post("http://localhost:5000/asociarProductos", {
        productId: productId,
        tiendaIds: storeIds,
      });
      Swal.fire('Success!', 'Stores associated successfully.', 'success');
    } catch (error) {
      console.error('Error associating stores:', error);
      Swal.fire('Error!', 'There was an error associating stores.', 'error');
    }
  };

  return (
    <>
      <div className="container mx-auto px-4 py-8">
        <h2 className="text-2xl font-bold mb-4">Products</h2>
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
        {user.role === 'Casa Central' && (
        <table className="min-w-full bg-white">
          <thead>
            <tr>
              <th className="py-2 px-4 border-b">Nombre</th>
              <th className="py-2 px-4 border-b">Codigo</th>
              <th className="py-2 px-4 border-b">Color</th>
              <th className="py-2 px-4 border-b">Talle</th>
              <th className="py-2 px-4 border-b">Tiendas Asociadas</th>
              <th className="py-2 px-4 border-b">Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredProducts.map(product => (
              <tr key={product.id}>
                <td className="py-2 px-4 border-b">{product.nombre}</td>
                <td className="py-2 px-4 border-b">{product.codigo}</td>
                <td className="py-2 px-4 border-b">{product.color}</td>
                <td className="py-2 px-4 border-b">{product.talle}</td>
                <td className="py-2 px-4 border-b">
                  <select
                    multiple
                    value={product.associatedStores?.map(String) || []} 
                    onChange={e => handleStoreChange(product.id!, Array.from(e.target.selectedOptions, option => Number(option.value)))}
                    className="border rounded px-2 py-1"
                  >
                    {stores.map(store => (
                      <option key={store.id} value={store.id}>
                        {store.codigo}
                      </option>
                    ))}
                  </select>
                </td>
                <td className="py-2 px-4 border-b">
                  <button
                    onClick={() => handleSaveAssociations(product.id!, product.associatedStores || [])}
                    className="bg-green-500 hover:bg-green-700 text-white font-bold py-1 px-2 rounded"
                  >
                    Guardar Asociaciones
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        )}
        {user.role === 'Tienda' && (
        <table className="min-w-full bg-white">
          <thead>
            <tr>
              <th className="py-2 px-4 border-b">Nombre</th>
              <th className="py-2 px-4 border-b">Codigo</th>
              <th className="py-2 px-4 border-b">Color</th>
              <th className="py-2 px-4 border-b">Talle</th>
              <th className="py-2 px-4 border-b">Tiendas Asociadas</th>
              <th className="py-2 px-4 border-b">Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredProducts.map(product => (
              <tr key={product.id}>
                <td className="py-2 px-4 border-b">{product.nombre}</td>
                <td className="py-2 px-4 border-b">{product.codigo}</td>
                <td className="py-2 px-4 border-b">{product.color}</td>
                <td className="py-2 px-4 border-b">{product.talle}</td>
                <td className="py-2 px-4 border-b">
                  <select
                    multiple
                    value={product.associatedStores?.map(String) || []} 
                    onChange={e => handleStoreChange(product.id!, Array.from(e.target.selectedOptions, option => Number(option.value)))}
                    className="border rounded px-2 py-1"
                  >
                    {stores.map(store => (
                      <option key={store.id} value={store.id}>
                        {store.codigo}
                      </option>
                    ))}
                  </select>
                </td>
                <td className="py-2 px-4 border-b">
                  <button
                    onClick={() => handleSaveAssociations(product.id!, product.associatedStores || [])}
                    className="bg-green-500 hover:bg-green-700 text-white font-bold py-1 px-2 rounded"
                  >
                    Guardar Asociaciones
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        )}
      </div>
    </>
  );
};