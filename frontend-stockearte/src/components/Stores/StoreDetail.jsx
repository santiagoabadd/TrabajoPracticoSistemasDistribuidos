import React, { useState, useEffect } from 'react'

export const StoreDetail = ({ store, onClose, onUpdate, isAdding }) => {

  const [storeData, setStoreData] = useState({
    code: '',
    address: '',
    city: '',
    province: '',
    enabled: 'enabled',
  });
  

  useEffect(() => {
    if (store) {
      setStoreData(store);
    }else {
      setStoreData({
        id: '',
        code: '',
        address: '',
        city: '',
        province: '',
        status: 'enabled',
      });
    }
  }, [store]);


  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Simulación de una llamada a un cliente gRPC para agregar o actualizar la tienda
      await new Promise(resolve => setTimeout(resolve, 500));

      const updatedStore = {
        ...storeData,
        id: isAdding ? `T${Math.floor(Math.random() * 1000)}` : storeData.id,
      };

      onUpdate(updatedStore);
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
                <label htmlFor="code" className="block text-sm font-medium text-gray-700">Code</label>
                <input
                  id="code"
                  type="text"
                  value={storeData.code}
                  onChange={(e) => setStoreData({...storeData, code: e.target.value})}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                  placeholder="Code"
                  required
                />
              </div>
              <div>
                <label htmlFor="address" className="block text-sm font-medium text-gray-700">Address</label>
                <input
                  id="address"
                  type="text"
                  value={storeData.address}
                  onChange={(e) => setStoreData({...storeData, address: e.target.value})}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                  placeholder="Address"
                  required
                />
              </div>
              <div>
                <label htmlFor="city" className="block text-sm font-medium text-gray-700">City</label>
                <input
                  id="city"
                  type="text"
                  value={storeData.city}
                  onChange={(e) => setStoreData({...storeData, city: e.target.value})}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                  placeholder="City"
                  required
                />
              </div>
              <div>
                <label htmlFor="province" className="block text-sm font-medium text-gray-700">Province</label>
                <input
                  id="province"
                  type="text"
                  value={storeData.province}
                  onChange={(e) => setStoreData({...storeData, province: e.target.value})}
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                  placeholder="Province"
                  required
                />
              </div>
              <div>
                <label htmlFor="status" className="block text-sm font-medium text-gray-700">Status</label>
                <select
                  id="status"
                  value={storeData.status}
                  onChange={(e) => setStoreData({...storeData, status: e.target.value})}
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
