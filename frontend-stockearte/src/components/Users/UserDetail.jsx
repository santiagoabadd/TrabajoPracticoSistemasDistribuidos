import React, { useState, useEffect } from "react";
import {
  updateUser,
  createUser,
  fetchStores,
} from "../../api/mockAPIStoresUsers";

export const UserDetail = ({ user, onClose, onUpdate, isAdding }) => {
  const [userData, setUserData] = useState({
    username: "",
    password: "",
    store: "",
    name: "",
    surname: "",
    enabled: true,
    role: "Tienda",
  });

  const [stores, setStores] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadStores = async () => {
      try {
        const storesData = await fetchStores();
        setStores(storesData);
      } catch (error) {
        console.error("Error fetching stores:", error);
      } finally {
        setLoading(false);
      }
    };

    loadStores();

    if (user) {
      setUserData(user);
    }
    console.log(userData);
  }, [user]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setUserData((prevData) => ({
      ...prevData,
      [name]: type === "checkbox" ? checked : value,
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
      console.error("Error saving user:", error);
    }
  };

  if (loading) {
    return (
      <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center">
        <div className="animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-blue-500"></div>
      </div>
    );
  }

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full flex justify-center items-center">
      <div className="relative p-8 border w-full max-w-md shadow-lg rounded-md bg-white">
        <h3 className="text-2xl font-bold mb-6">
          {isAdding ? "Add User" : "Edit User"}
        </h3>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label
              htmlFor="username"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Username
            </label>
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
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Password
            </label>
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
            <label
              htmlFor="store"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              Store
            </label>
            <select
              id="store"
              name="store"
              value={userData.store}
              onChange={handleChange}
              className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            >
              <option value="">Select Store</option>
              {stores.map((store) => (
                <option key={store.id} value={store.id}>
                  {store.codigo}
                </option>
              ))}
            </select>
          </div>

          <button
            type="submit"
            className="w-full py-2 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700"
          >
            {isAdding ? "Add User" : "Update User"}
          </button>
        </form>
      </div>
    </div>
  );
};
