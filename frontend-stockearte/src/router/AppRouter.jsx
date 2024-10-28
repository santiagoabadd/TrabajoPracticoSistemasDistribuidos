import React, { useContext } from "react"
import { AuthContext } from "../context/AuthContext"
import { NavBar } from "../components/NavBar/NavBar"
import { Footer } from "../components/Footer/Footer"
import { LoginScreen } from "../components/LoginScreen/LoginScreen"
import { DashBoard } from "../components/DashBoard/DashBoard"
import { RegisterScreen } from "../components/LoginScreen/RegisterScreen"
import { ProductList } from "../components/Products/ProductList"
import { StoreList } from "../components/Stores/StoreList"
import { UserList } from "../components/Users/UserList"
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom"
import Novedades from "../components/Novedades/Novedades"
import { PurchaseOrders } from "../components/PurchaseOrder/PurchaseOrders"


export const AppRouter = () => {

    const { user } = useContext(AuthContext)

  return (
    <>
        <BrowserRouter>
        { 
            user.logged
            ? <>
                <NavBar/>
                <Routes>
                    <Route path='/' element={<DashBoard/>} />
                    <Route path='/products' element={<ProductList/>} />
                    {(user.role === 'Casa Central' || user.role === 'central') && (
                        <>
                            <Route path='/stores' element={<StoreList/>} />
                            <Route path='/users' element={<UserList/>} />
                            <Route path='/novedades' element={<Novedades/>} />
                            <Route path='/ordenes' element={<PurchaseOrders/>} />

                        </>
                    )}
                    <Route path='*' element={<Navigate to="/" />} />
                </Routes>
                <Footer/> 
              </>
              : <Routes>                  
                  <Route path='/login' element={<LoginScreen />} />
                  <Route path='/register' element={<RegisterScreen />} />
                  <Route path='*' element={<Navigate to="/login" />} />
                </Routes>
        }
        
        </BrowserRouter>
    </>
  )
}
