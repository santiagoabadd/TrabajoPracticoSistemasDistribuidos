import React, { useContext } from 'react'
import { AuthContext } from '../../context/AuthContext';
import { Link } from 'react-router-dom';

export const DashBoard = () => {

    const { user } = useContext(AuthContext);

    return (
        <>
            <div className="container min-h-screen flex flex-col mx-auto px-4 py-8">
                <h1 className="text-3xl font-bold mb-6">Dashboard</h1>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    <Link to="/products" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-4 px-6 rounded">
                        Manage Products
                    </Link>
                    {(user.role === 'Casa Central' || user.role === 'central') && (
                        <>
                            <Link to="/stores" className="bg-green-500 hover:bg-green-700 text-white font-bold py-4 px-6 rounded">
                                Manage Stores
                            </Link>
                            <Link to="/users" className="bg-yellow-500 hover:bg-yellow-700 text-white font-bold py-4 px-6 rounded">
                                Manage Users
                            </Link>
                            <Link to="/novedades" className="bg-yellow-500 hover:bg-yellow-700 text-white font-bold py-4 px-6 rounded">
                                Manage Novedades
                            </Link>
                        </>
                    )}
                </div>
            </div>
        </>
    )
}
