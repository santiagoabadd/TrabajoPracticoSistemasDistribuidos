import React, { useContext, useEffect, useState } from "react";
import axios from "axios";
import Swal from "sweetalert2";
import { UserDetail } from "./UserDetail";
import { AuthContext } from "../../context/AuthContext";

interface User {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  codigoTienda: string;
  rol: string;
  activo: boolean;
}

interface UserEdit {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  rol: string;
  codigoTienda: string;
  activo: boolean;
  password: string;
}

interface UsersResponse {
  user: User[];
}

interface Store {
  codigo: string;
  estado: boolean;
  direccion: string;
  ciudad: string;
  provincia: string;
}

interface Filter {
  username: string;
  codigoTienda: string;
}

export const UserList: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [stores, setStores] = useState<Store[]>([]);
  const [filter, setFilter] = useState<Filter>({
    username: "",
    codigoTienda: "",
  });
  const [selectedUser, setSelectedUser] = useState<UserEdit | null>(null);
  const [isAddingUser, setIsAddingUser] = useState<boolean>(false);
  const { user } = useContext(AuthContext);

  useEffect(() => {
    const fetchUsers = async () => {
      const response = await axios.get("http://localhost:5000/getUsers");
      const data = response.data;
      console.log("-------->", data.user);
      console.log("Tipo de data.user:", typeof data.user);
      if (Array.isArray(data.user)) {
        setUsers(data.user);
      } else {
        setUsers([]);
      }
    };

    const fetchStores = async () => {
      const response = await axios.get("http://localhost:5000/getTiendas");
      const data = response.data;
      setStores(Array.isArray(data.tiendasInfo) ? data.tiendasInfo : []);
    };

    fetchUsers();
    fetchStores();
    console.log("------->", users);
  }, [selectedUser]);

  const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFilter({ ...filter, [e.target.name]: e.target.value });
  };

  const filteredUsers = users.filter(
    (user) =>
      user.username.toLowerCase().includes(filter.username.toLowerCase()) &&
      user.codigoTienda
        .toLowerCase()
        .includes(filter.codigoTienda.toLowerCase())
  );

  const handleEditUser = (user: User) => {
    setSelectedUser({
      ...user,
      password: "",
      email: "asfasf",
    });
    setIsAddingUser(false);
  };

  const handleDeleteUser = async (userId: number) => {
    try {
      const result = await Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!",
      });

      if (result.isConfirmed) {
        await axios.delete(`http://localhost:5000/deleteUser/${userId}`);
        setUsers(users.filter((user) => user.id !== userId));
        Swal.fire("Deleted!", "The user has been deleted.", "success");
      }
    } catch (error) {
      Swal.fire("Error!", "There was an error deleting the user.", "error");
      console.error("Error deleting user:", error);
    }
  };

  const handleUpdateUser = (updatedUser: User) => {
    console.log("--> a mo dificar", updatedUser);
    if (isAddingUser) {
      setUsers([...users, updatedUser]);
    } else {
      setUsers(users.map((u) => (u.id === updatedUser.id ? updatedUser : u)));
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
        {user.rol === "Casa Central" && (
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
            name="codigoTienda"
            value={filter.codigoTienda}
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
            {filteredUsers.map((user) => (
              <tr key={user.id}>
                <td className="py-2 px-4 border-b">{user.username}</td>
                <td className="py-2 px-4 border-b">{user.codigoTienda}</td>
                <td className="py-2 px-4 border-b">{user.activo}</td>
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
                    Delete
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
          onClose={() => {
            setSelectedUser(null);
            setIsAddingUser(false);
          }}
          onUpdate={handleUpdateUser}
          isAdding={isAddingUser}
        />
      )}
    </>
  );
};
