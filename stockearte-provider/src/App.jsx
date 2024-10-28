import './App.css'
import { BrowserRouter, Route, Router, Routes } from 'react-router-dom'
import { Navbar } from './components/layout/Navbar'
import { Dashboard } from './components/layout/Dashboard'
import { ProductManagementPage } from './pages/ProductManagementPage'
import { OrderManagementPage } from './pages/OrderManagementPage'
import { Footer } from './components/layout/Footer'
import { NoveltiesPage } from './pages/NoveltiesPage'

function App() {

  return (
    <>
      <BrowserRouter>
      
        <div className="flex flex-col min-h-screen">
          <Navbar />
          <main className="flex-grow">
            <Routes>
              <Route path="/" element={<Dashboard/>} />
              <Route path="/productos" element={<ProductManagementPage/>} />
              <Route path="/ordenes" element={<OrderManagementPage/>} />
              <Route path="/novedades" element={<NoveltiesPage />} />
            </Routes>
          </main>
          <Footer />
        </div>
      
      </BrowserRouter>
    </>
  )
}

export default App
