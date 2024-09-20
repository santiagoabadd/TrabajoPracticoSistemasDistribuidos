import { React, useContext, useState } from "react"
import { Link } from "react-router-dom"
import { AuthContext } from "../../context/AuthContext"

export const LoginScreen = () => {

    const { login } = useContext(AuthContext)

    const [values, setValues] = useState({
        email: '',
        password: ''
    })

    const handleInput = (e) => {
        setValues({
            ...values,
            [e.target.name]: e.target.value
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        login(values)
    }

    return (
        <>
            <div className="flex items-center justify-center min-h-screen bg-gray-100">
                <div className="px-8 py-6 mt-4 text-left bg-white shadow-lg">
                    <h3 className="text-2xl font-bold text-center">Login to your account</h3>
                    <form onSubmit={handleSubmit}>
                        <div className="mt-4">
                            <div>
                                <label className="block" htmlFor="email">Email</label>
                                <input type="text" placeholder="Email"
                                    className="w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-600"
                                    name="email"
                                    value={values.email}
                                    onChange={handleInput}
                                />
                            </div>
                            <div className="mt-4">
                                <label className="block">Password</label>
                                <input type="password" placeholder="Password"
                                    className="w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-600"
                                    name="password"
                                    value={values.password}
                                    onChange={handleInput}
                                />
                            </div>
                            <div className="flex items-baseline justify-between">
                                <button className="px-6 py-2 mt-4 text-white bg-blue-600 rounded-lg hover:bg-blue-900">Login</button>
                                <Link to="/register" className="text-sm text-blue-600 hover:underline">Register</Link>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </>
    )
}
