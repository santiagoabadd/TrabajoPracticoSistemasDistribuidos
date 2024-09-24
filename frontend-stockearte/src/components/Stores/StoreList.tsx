import React, { useEffect, useState } from 'react';
import { StoreDetail } from './StoreDetail';
import Swal from 'sweetalert2';
import axios from "axios";

interface Store {
    codigo: string;
    estado: boolean;
    direccion: string;
    ciudad: string;
    provincia: string;
}

export const StoreList = () => {
    const [stores, setStores] = useState<Store[]>([]);
    const [filter, setFilter] = useState({ codigo: '', estado: '' });
    const [isAddingStore, setIsAddingStore] = useState(false);
    const [selectedStore, setSelectedStore] = useState<Store | null>(null);

    useEffect(() => {
        fetchStores();
    }, []);

    const fetchStores = async () => {
        try {
            const response = await axios.get("http://localhost:5000/getTiendas");
            const data = response.data; 
            setStores(Array.isArray(data.tiendasInfo) ? data.tiendasInfo : []);
        } catch (error) {
            console.error('Error fetching stores:', error);
        }
    };

    const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setFilter({ ...filter, [e.target.name]: e.target.value });
    };

    const filteredStores = stores.filter(store =>
        store.codigo.toLowerCase().includes(filter.codigo.toLowerCase()) &&
        (filter.estado === '' || (store.estado ? 'enabled' : 'disabled') === filter.estado)
    );

    const handleAddStore = () => {
        setSelectedStore(null);
        setIsAddingStore(true);
    };

    const handleEditStore = (store: Store) => {
        setSelectedStore(store);
        setIsAddingStore(false);
    };

    const handleDeleteStore = async (storeId: string) => {
        try {
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

            if (result.isConfirmed) {
                const updatedStores = stores.filter(store => store.codigo !== storeId);
                setStores(updatedStores);
                Swal.fire('Deleted!', 'The store has been deleted.', 'success');
            }
        } catch (error) {
            Swal.fire('Error!', 'There was an error deleting the store.', 'error');
            console.error('Error deleting store:', error);
        }
    };

    const handleStoreUpdate = (updatedStore: Store) => {
        if (isAddingStore) {
            setStores([...stores, updatedStore]);
        } else {
            setStores(stores.map(store => store.codigo === updatedStore.codigo ? updatedStore : store));
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
                        name="codigo"
                        value={filter.codigo}
                        onChange={handleFilterChange}
                        className="border rounded px-2 py-1"
                    />
                    <select
                        name="estado"
                        value={filter.estado}
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
                            <tr key={store.codigo}>
                                <td className="py-2 px-4 border-b">{store.codigo}</td>
                                <td className="py-2 px-4 border-b">{store.direccion}</td>
                                <td className="py-2 px-4 border-b">{store.ciudad}</td>
                                <td className="py-2 px-4 border-b">{store.provincia}</td>
                                <td className="py-2 px-4 border-b">{store.estado ? 'Enabled' : 'Disabled'}</td>
                                <td className="py-2 px-4 border-b">
                                    <button 
                                        onClick={() => handleEditStore(store)}
                                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-2 rounded mr-2"
                                    >
                                        Edit
                                    </button>
                                    <button 
                                        onClick={() => handleDeleteStore(store.codigo)}
                                        className="bg-red-500 hover:bg-red-700 text-white font-bold py-1 px-2 rounded"
                                    >
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
        </>
    );
};