import React, { useContext, useEffect, useState } from 'react'
import { fetchUsers, deleteUser, fetchStores } from '../../api/mockAPIStoresUsers';
import { UserDetail } from './UserDetail';
import { AuthContext } from '../../context/AuthContext';
import Swal from 'sweetalert2';

export const UserList = () => {

    const [users, setUsers] = useState([]);
    const [stores, setStores] = useState([]);
    const [filter, setFilter] = useState({ username: '', store: '' });
    const [selectedUser, setSelectedUser] = useState(null);
    const [isAddingUser, setIsAddingUser] = useState(false);
    const { user } = useContext(AuthContext);

    useEffect(() => {
            fetchUsers().then(data => setUsers(data));
            fetchStores().then(data => setStores(data));
    }, [])



    const handleFilterChange = (e) => {
        setFilter({ ...filter, [e.target.name]: e.target.value })
    }

    const filteredUsers = users.filter(user =>
        user.username.toLowerCase().includes(filter.username.toLowerCase()) &&
        user.store.toLowerCase().includes(filter.store.toLowerCase())
    )

    const handleEditUser = (user) => {
        setSelectedUser(user);
        setIsAddingUser(false);
    };

    const handleDeleteUser = async (userId) => {
        try {
          const result = await Swal.fire({
            title: '¿Estás seguro?',
            text: "No podrás revertir esta acción!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Sí, eliminar!'
          });
    
          if (result.isConfirmed) {
            await deleteUser(userId);
            setUsers(users.filter(user => user.id !== userId));
            Swal.fire('Eliminado!', 'El usuario ha sido eliminado.', 'success');
          }
        } catch (error) {
          Swal.fire('Error!', 'Hubo un error al eliminar el usuario.', 'error');
          console.error('Error deleting user:', error);
        }
      };

    const handleUpdateUser = (updatedUser) => {
        if (isAddingUser) {
          setUsers([...users, updatedUser]);
        } else {
          setUsers(users.map(u => u.id === updatedUser.id ? updatedUser : u));
        }
        setSelectedUser(null);
        setIsAddingUser(false);
    };

    const handleAddUser = () => {
        setSelectedUser(null);
        setIsAddingUser(true);
    };


    return (
        <>
            <div className="container mx-auto px-4 py-8">
                <h2 className="text-2xl font-bold mb-4">Users</h2>
                {user.role === 'Casa Central' && (
                    <button
                        onClick={handleAddUser}
                        className="mb-4 bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded"
                    >
                        Add User
                    </button>
                )}
                <div className="mb-4 grid grid-cols-1 md:grid-cols-2 gap-4">
                    <input
                        type="text"
                        placeholder="Filter by username"
                        name="username"
                        value={filter.username}
                        onChange={handleFilterChange}
                        className="border rounded px-2 py-1"
                    />
                    <input
                        type="text"
                        placeholder="Filter by store"
                        name="store"
                        value={filter.store}
                        onChange={handleFilterChange}
                        className="border rounded px-2 py-1"
                    />
                </div>
                <table className="min-w-full bg-white">
                    <thead>
                        <tr>
                            <th className="py-2 px-4 border-b">Username</th>
                            <th className="py-2 px-4 border-b">Stores</th>
                            <th className="py-2 px-4 border-b">Status</th>
                            <th className="py-2 px-4 border-b">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredUsers.map(user => (
                            <tr key={user.id}>
                                <td className="py-2 px-4 border-b">{user.username}</td>
                                <td className="py-2 px-4 border-b">{user.store}</td>
                                <td className="py-2 px-4 border-b">{user.status}</td>
                                <td className="py-2 px-4 border-b">
                                    <button 
                                        onClick={() => handleEditUser(user)}
                                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-2 rounded mr-2"
                                    >
                                        Edit
                                    </button>
                                    
                                    <button 
                                        onClick={() => handleDeleteUser(user.id)}
                                        className="bg-red-500 hover:bg-red-700 text-white font-bold py-1 px-2 rounded"
                                    >
                                        Eliminar
                                    </button>
                                    
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            {(selectedUser || isAddingUser) && (
                <UserDetail
                user={selectedUser}
                stores={stores}
                onClose={() => { setSelectedUser(null); setIsAddingUser(false); }}
                onUpdate={handleUpdateUser}
                isAdding={isAddingUser}
                />
            )}
        </>
    )
}
