import React from 'react'
import { Link } from 'react-router-dom'

export const Navbar = () => {
    return (
        <>
            <header className="bg-green-600 text-white p-4">
                <div className="container mx-auto flex justify-between items-center">
                    <Link to="/" className="text-2xl font-bold">Panel del Proveedor</Link>
                    <nav>
                        <ul className="flex space-x-4">
                            <li><Link to="/productos" className="hover:text-green-200">Gestión de Productos</Link></li>
                            <li><Link to="/ordenes" className="hover:text-green-200">Gestión de Órdenes</Link></li>
                            <li><Link to="/novedades" className="hover:text-green-200">Gestión de Novedades</Link></li>
                        </ul>
                    </nav>
                </div>
            </header>
        </>
    )
}
