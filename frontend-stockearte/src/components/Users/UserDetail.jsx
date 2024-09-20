import React, { useState, useEffect } from 'react'
import { updateUser, createUser, fetchStores } from '../../api/mockAPIStoresUsers';

export const UserDetail = ({ user, stores, onClose, onUpdate, isAdding }) => {

  

  const [userData, setUserData] = useState({
    username: '',
    password: '',
    store: '',
    name: '',
    surname: '',
    enabled: true,
    role: 'Tienda'
  });

  console.log(user);


  useEffect(() => {
    if (user) {
      setUserData(user);
    }
  }, [user]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setUserData(prevData => ({
      ...prevData,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      let updatedUser;
      if (isAdding) {
        updatedUser = await createUser(userData);
      } else {
        updatedUser = await updateUser(user.id, userData);
      }
      onUpdate(updatedUser);
      onClose();
    } catch (error) {
      console.error('Error saving user:', error);
    }
  };


  return (
    <>
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full flex justify-center items-center">
          <div className="relative p-8 border w-full max-w-md shadow-lg rounded-md bg-white">
            <h3 className="text-2xl font-bold mb-6">{isAdding ? 'Add User' : 'Edit User'}</h3>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label htmlFor="username" className="block text-sm font-medium text-gray-700 mb-1">Username</label>
                <input
                  id="username"
                  name="username"
                  type="text"
                  value={userData.username}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
              </div>
              <div>
                <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">Password</label>
                <input
                  id="password"
                  name="password"
                  type="password"
                  value={userData.password}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
              </div>
              <div>
                <label htmlFor="store" className="block text-sm font-medium text-gray-700 mb-1">Store</label>
                <select
                  id="store"
                  name="store"
                  value={userData.store}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                >
                  <option value="">Select Store</option>
                  {stores.map(store => (
                    <option key={store.id} value={store.id}>{store.code}</option>
                  ))}
                </select>
              </div>
              <div>
                <label htmlFor="name" className="block text-sm font-medium text-gray-700 mb-1">Name</label>
                <input
                  id="name"
                  name="name"
                  type="text"
                  value={userData.name}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
              </div>
              <div>
                <label htmlFor="surname" className="block text-sm font-medium text-gray-700 mb-1">Surname</label>
                <input
                  id="surname"
                  name="surname"
                  type="text"
                  value={userData.surname}
                  onChange={handleChange}
                  className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                />
              </div>
              <div>
                <label htmlFor="enabled" className="flex items-center">
                  <input
                    id="enabled"
                    name="enabled"
                    type="checkbox"
                    checked={userData.enabled}
                    onChange={handleChange}
                    className="mr-2"
                  />
                  <span className="text-sm font-medium text-gray-700">Status</span>
                </label>
              </div>
              <div className="flex justify-end space-x-4 mt-6">
                <button type="button" onClick={onClose} className="px-4 py-2 bg-gray-300 text-gray-800 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500">
                  Cancel
                </button>
                <button type="submit" className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
                  {isAdding ? 'Add User' : 'Edit User'}
                </button>
              </div>
            </form>
          </div>
        </div>
    </>
  )
}
