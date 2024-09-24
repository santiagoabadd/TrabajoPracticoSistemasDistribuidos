import React, { useState, useEffect } from 'react'
import axios from "axios"

export const StoreDetail = ({ store, onClose, onUpdate, isAdding }) => {

  const [storeData, setStoreData] = useState({
    codigo: '',
    direccion: '',
    ciudad: '',
    provincia: '',
    estado: 'enabled',
  });
  

  useEffect(() => {
    if (store) {
      setStoreData(store);
    }else {
      setStoreData({
        codigo: '',
        direccion: '',
        ciudad: '',
        provincia: '',
        estado: true
      });
    }
  }, [store]);


  const handleSubmit = async (e) => {
    e.preventDefault();
  
    try {
      const apiUrl = isAdding
        ? "http://localhost:5000/registerTienda"
        : "http://localhost:5000/updateTienda";
  
      const tiendaData = {
        ...storeData,
        enabled: storeData.estado === 'enabled',
      };
  
      const response = await axios.post(apiUrl, tiendaData);
  

      onUpdate(tiendaData);
    } catch (error) {
      console.error('Error updating store:', error);
    }
  };





  return (
    <>
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full flex items-center justify-center">
          <div className="relative p-8 border w-full max-w-md shadow-lg rounded-md bg-white">
            <h3 className="text-xl font-bold mb-4">{isAdding ? 'Add New Store' : 'Edit Store'}</h3>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label htmlFor="codigo" className="block text-sm font-medium text-gray-700">Codigo</label>
                <input
                  id="codigo"
                  type="text"
                  value={storeData.codigo}
                  onChange={(e) => setStoreData({...storeData, codigo: e.target.value})}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                  placeholder="Codigo"
                  required
                />
              </div>
              <div>
                <label htmlFor="direccion" className="block text-sm font-medium text-gray-700">Direccion</label>
                <input
                  id="direccion"
                  type="text"
                  value={storeData.direccion}
                  onChange={(e) => setStoreData({...storeData, direccion: e.target.value})}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                  placeholder="Direccion"
                  required
                />
              </div>
              <div>
                <label htmlFor="ciudad" className="block text-sm font-medium text-gray-700">ciudad</label>
                <input
                  id="ciudad"
                  type="text"
                  value={storeData.ciudad}
                  onChange={(e) => setStoreData({...storeData, ciudad: e.target.value})}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                  placeholder="ciudad"
                  required
                />
              </div>
              <div>
                <label htmlFor="provincia" className="block text-sm font-medium text-gray-700">provincia</label>
                <input
                  id="provincia"
                  type="text"
                  value={storeData.provincia}
                  onChange={(e) => setStoreData({...storeData, provincia: e.target.value})}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                  placeholder="provincia"
                  required
                />
              </div>
              <div>
                <label htmlFor="estados" className="block text-sm font-medium text-gray-700">estados</label>
                <select
                  id="estados"
                  value={storeData.estados}
                  onChange={(e) => setStoreData({...storeData, estados: e.target.value})}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                >
                  <option value="enabled">Enabled</option>
                  <option value="disabled">Disabled</option>
                </select>
              </div>
              <div className="flex justify-end space-x-3 mt-6">
                <button
                  type="button"
                  onClick={onClose}
                  className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  {isAdding ? 'Add Store' : 'Update Store'}
                </button>
              </div>
            </form>
          </div>
        </div>
    </>
  )
}
