import React, { useEffect, useState } from 'react'
import mockData from '../../data/MOCK_DATA.json';
import { StoreDetail } from './StoreDetail';
import Swal from 'sweetalert2';

export const StoreList = () => {

    const [stores, setStores] = useState([]);
    const [filter, setFilter] = useState({ code: '', status: '' });
    const [isAddingStore, setIsAddingStore] = useState(false);
    const [selectedStore, setSelectedStore] = useState(null);


    useEffect(() => {
        fetchStores();
      }, []);

      const fetchStores = async () => {
        // Asumiendo que mockData.tienda es el array de tiendas
        const storesData = mockData.tienda || [];
        const formattedStores = storesData.map(store => ({
            id: store.id_tienda,
            code: store.id_tienda,
            status: store.habilitada === "true" ? "enabled" : "disabled",
            address: store.direccion,
            city: store.ciudad,
            province: store.provincia
        }));
        setStores(formattedStores);
    };



    const handleFilterChange = (e) => {
        setFilter({ ...filter, [e.target.name]: e.target.value })
    }


    const filteredStores = stores.filter(store =>
        store.code.toLowerCase().includes(filter.code.toLowerCase()) &&
        (filter.status === '' || store.status === filter.status)
    );



    const handleAddStore = () => {
        setSelectedStore(null);
        setIsAddingStore(true);
    };

    const handleEditStore = (store) => {
        setSelectedStore(store);
        setIsAddingStore(false);
    };

    const handleDeleteStore = async (storeId) => {
        try {
            // Mostrar la alerta de confirmación con SweetAlert2
            const result = await Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'Cancel'
            });
    
            // Si el usuario confirma la eliminación
            if (result.isConfirmed) {
                // Aquí iría la llamada a la API para eliminar la tienda
                // await deleteStore(storeId); // Si es un backend real
    
                // Actualizar el estado de las tiendas
                const updatedStores = stores.filter(store => store.id !== storeId);
                setStores(updatedStores);
    
                // Mostrar mensaje de éxito con SweetAlert2
                Swal.fire(
                    'Deleted!',
                    'The store has been deleted.',
                    'success'
                );
            }
        } catch (error) {
            // Manejo de errores
            Swal.fire(
                'Error!',
                'There was an error deleting the store.',
                'error'
            );
            console.error('Error deleting store:', error);
        }
    };

    const handleStoreUpdate = (updatedStore) => {
        if (isAddingStore) {
            setStores([...stores, updatedStore]);
          } else {
            setStores(stores.map(store => 
              store.id === updatedStore.id ? updatedStore : store
            ));
          }
          handleCloseDetail();
    };

    const handleCloseDetail = () => {
        setSelectedStore(null);
        setIsAddingStore(false);
    };


    return (
        <> 
            <div className="container mx-auto px-4 py-8">
                <h2 className="text-2xl font-bold mb-4">Stores</h2>
                <button onClick={handleAddStore} className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mb-4">
                    Add New Store
                </button>
                <div className="mb-4 grid grid-cols-1 md:grid-cols-2 gap-4">
                    <input
                        type="text"
                        placeholder="Filter by code"
                        name="code"
                        value={filter.code}
                        onChange={handleFilterChange}
                        className="border rounded px-2 py-1"
                    />
                    <select
                        name="status"
                        value={filter.status}
                        onChange={handleFilterChange}
                        className="border rounded px-2 py-1"
                    >
                        <option value="">All statuses</option>
                        <option value="enabled">Enabled</option>
                        <option value="disabled">Disabled</option>
                    </select>
                </div>
                <table className="min-w-full bg-white">
                    <thead>
                        <tr>
                            <th className="py-2 px-4 border-b">Code</th>
                            <th className="py-2 px-4 border-b">Address</th>
                            <th className="py-2 px-4 border-b">City</th>
                            <th className="py-2 px-4 border-b">Province</th>
                            <th className="py-2 px-4 border-b">Status</th>
                            <th className="py-2 px-4 border-b">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredStores.map(store => (
                            <tr key={store.id}>
                                <td className="py-2 px-4 border-b">{store.code}</td>
                                <td className="py-2 px-4 border-b">{store.address}</td>
                                <td className="py-2 px-4 border-b">{store.city}</td>
                                <td className="py-2 px-4 border-b">{store.province}</td>
                                <td className="py-2 px-4 border-b">{store.status}</td>
                                <td className="py-2 px-4 border-b">
                                    <button 
                                        onClick={() => handleEditStore(store)}
                                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-2 rounded mr-2"
                                    >
                                        Edit
                                    </button>

                                    <button 
                                        onClick={() => handleDeleteStore(store.id)}
                                        className="bg-red-500 hover:bg-red-700 text-white font-bold py-1 px-2 rounded">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            {(selectedStore || isAddingStore) && (
                <StoreDetail
                    store={selectedStore}
                    onClose={handleCloseDetail}
                    onUpdate={handleStoreUpdate}
                    isAdding={isAddingStore}
                />
            )}
            {/* {selectedStore && (
                <StoreDetail 
                    storeId={selectedStore.id} 
                    onClose={handleCloseDetail} 
                    onUpdate={handleUpdateStore}
                />
            )} */}
        </>
    )
}
