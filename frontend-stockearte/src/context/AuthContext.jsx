import React, { createContext, useEffect, useState } from "react";
import { mockFetch } from "../api/mockAPIContext";
import { fetchUserById } from "../api/mockAPIStoresUsers";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState({
    email: null,
    logged: false,
    role: null,
    idTienda: null,
    codigoTienda:null,
    idUsuario:null
  });
  useEffect(() => {
    console.log(user);
  }, [user]);
  /*
    const login = async (values) => {
        try {
            const response = await mockFetch('/api/login', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(values)
            });
            const data = await response.json();
            if (response.ok) {
              const userDetails = await fetchUserById(data.email);
              setUser({
                email: data.email,
                logged: true,
                role: data.role,
                idTienda: data.role === 'Tienda' ? userDetails.store : null
              });
            } else {
              throw new Error(data.message);
            }
          } catch (error) {
            console.error('Login error:', error);
          }

    }*/
  const login = async (values) => {
    try {
      const response = await fetch("http://localhost:5000/authorize", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          userName: values.email, // Ajusta según cómo uses el email
          password: values.password, // Asegúrate de que el valor correcto se esté pasando
        }),
      });



      const data = await response.json();
      const userDetails = data.user;

      const storeResponse = await fetch(`http://localhost:5000/getTienda/${userDetails.idTienda}`);
    if (!storeResponse.ok) {
      throw new Error(`HTTP error! Status: ${storeResponse.status}`);
    }

    const storeData = await storeResponse.json();
    const codigoTienda = storeData.codigo; // Asegúrate de que la propiedad exista

    console.log(codigoTienda);
      console.log(userDetails);

      setUser({
        email: data.user.username,
        logged: true,
        role: data.user.rol,
        idTienda: data.user.idTienda,
        codigoTienda: codigoTienda,
        idUsuario:data.user.id
      });

      console.log(data.user.username);
      console.log(user);
    } catch (error) {
      console.error("Login error:", error);
    }
  };

  const register = async (values) => {
    try {
      const response = await fetch("http://localhost:5000/registerUser", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(values),
      });
      const data = await response.json();
      if (response.ok) {
        setUser({
          email: data.email,
          logged: true,
          role: data.role,
          idTienda: data.role === "Tienda" ? data.idTienda : null,
          codigoTienda: data.codigoTienda
        });
      } else {
        throw new Error(data.message);
      }
    } catch (error) {
      console.error("Registration error:", error);
    }
  };

  const logout = () => {
    setUser({
      email: null,
      logged: false,
      role: null,
      idTienda: null,
      codigoTienda: null,
    });
  };

  return (
    <>
      <AuthContext.Provider
        value={{
          user,
          login,
          register,
          logout,
        }}
      >
        {children}
      </AuthContext.Provider>
    </>
  );
};
