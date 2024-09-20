import { React, useContext } from 'react'
import { AuthContext } from '../../context/AuthContext';
import logo from '../../assets/store-front.png'
import { Link } from 'react-router-dom';

export const NavBar = () => {

    const { user, logout } = useContext(AuthContext);

    return (
        <>
            <nav className="bg-white border-gray-200 px-4 lg:px-6 py-2.5 dark:bg-black">
                <div className="flex flex-wrap justify-between items-center mx-auto max-w-screen-xl">
                    <Link to="/" className="flex items-center">
                        <img src={logo} className="mr-3 h-6 sm:h-9" alt='logo' />
                        <span className="self-center text-xl font-semibold whitespace-nowrap dark:text-white">Stockearte</span>
                    </Link>
                    <div className="flex items-center lg:order-2">
                        <p className="text-gray-800 dark:text-white mr-4">Welcome, {user.email}</p>
                        <button className='bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded' onClick={logout}>Logout</button>
                    </div>
                    <div className="hidden justify-between items-center w-full lg:flex lg:w-auto lg:order-1" id="mobile-menu-2">
                        <ul className="flex flex-col mt-4 font-medium lg:flex-row lg:space-x-8 lg:mt-0">
                            <li>
                                <Link to="/" className="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 lg:hover:bg-transparent lg:border-0 lg:hover:text-primary-700 lg:p-0 dark:text-gray-400 lg:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white lg:dark:hover:bg-transparent dark:border-gray-700">Dashboard</Link>
                            </li>
                            {(user.role === 'Casa Central' || user.role === 'central') && (
                                <>
                                    <li>
                                        <Link to="/stores" className="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 lg:hover:bg-transparent lg:border-0 lg:hover:text-primary-700 lg:p-0 dark:text-gray-400 lg:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white lg:dark:hover:bg-transparent dark:border-gray-700">Stores</Link>
                                    </li>
                                    <li>
                                        <Link to="/users" className="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 lg:hover:bg-transparent lg:border-0 lg:hover:text-primary-700 lg:p-0 dark:text-gray-400 lg:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white lg:dark:hover:bg-transparent dark:border-gray-700">Users</Link>
                                    </li>
                                </>
                            )}
                            <li>
                                <Link to="/products" className="block py-2 pr-4 pl-3 text-gray-700 border-b border-gray-100 hover:bg-gray-50 lg:hover:bg-transparent lg:border-0 lg:hover:text-primary-700 lg:p-0 dark:text-gray-400 lg:dark:hover:text-white dark:hover:bg-gray-700 dark:hover:text-white lg:dark:hover:bg-transparent dark:border-gray-700">Products</Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </>
    )
}
