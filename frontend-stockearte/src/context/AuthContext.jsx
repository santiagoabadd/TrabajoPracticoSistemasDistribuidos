import React, { createContext, useEffect, useState } from "react";
import { mockFetch } from '../api/mockAPIContext';
import { fetchUserById } from '../api/mockAPIStoresUsers'; 

export const AuthContext = createContext();

export const AuthProvider = ({children}) => {

    const [user, setUser] = useState({
        email: null,
        logged: false,
        role: null,
        idTienda: null
    })

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

    }



    const register = async (values) => {
        try {
            const response = await mockFetch('/api/register', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(values)
            });
            const data = await response.json();
            if (response.ok) {
              setUser({
                email: data.email,
                logged: true,
                role: data.role,
                idTienda: data.role === 'Tienda' ? data.idTienda : null
              });
            } else {
              throw new Error(data.message);
            }
          } catch (error) {
            console.error('Registration error:', error);
          }
    }  

    const logout = () => {
        setUser({
            email: null,
            logged: false,
            role: null,
            idTienda: null
        });
    }


  return (
    <>
        <AuthContext.Provider value={{
            user,
            login,
            register,
            logout
        }}>
            {children}
        </AuthContext.Provider>
    </>
  )
}
