
import React from 'react'
import { useState, useEffect } from 'react'
import "./Novedades.css";


interface Novedad {
    id:number
  nombre: string
  codigo: string
  foto: string
  color: string
  talle: string
}

const Novedades: React.FC = () => {
  const [novedades, setNovedades] = useState<Novedad[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [message, setMessage] = useState<{ type: 'success' | 'error', text: string } | null>(null)

  useEffect(() => {
    fetchNovedades()
  }, [])

  const fetchNovedades = async () => {
    try {
      const response = await fetch('http://localhost:5000/getNovedades')
      if (!response.ok) {
        throw new Error('Error al obtener novedades')
      }
      const data = await response.json()
      setNovedades(data.novedades)
      setLoading(false)
    } catch (err) {
      setError('Error al cargar las novedades')
      setLoading(false)
    }
  }

  const handleAddProduct = async (novedad: Novedad) => {
    try {
      const addResponse = await fetch('http://localhost:5000/registerProduct', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(novedad),
      })
      if (!addResponse.ok) {
        throw new Error('Error al añadir producto')
      }

      const deleteResponse = await fetch(`http://localhost:5000/deleteNovedad/${novedad.id}`, {
        method: 'DELETE',
      })
      if (!deleteResponse.ok) {
        throw new Error('Error al eliminar novedad')
      }

      await fetchNovedades()
      setMessage({ type: 'success', text: "Producto añadido y novedad eliminada correctamente." })
    } catch (err) {
      setError('Error al procesar la operación')
      setMessage({ type: 'error', text: "No se pudo completar la operación." })
    }
  }

  if (loading) return <div className="loading">Cargando...</div>
  if (error) return <div className="error">{error}</div>

  return (
    <div className="container">
      <h1 className="title">Novedades</h1>
      {message && (
        <div className={`message ${message.type}`}>
          {message.text}
        </div>
      )}
      {novedades.length === 0 ? (
        <p className="no-items">No hay novedades disponibles.</p>
      ) : (
        <div className="grid">
          {novedades.map((novedad) => (
            <div key={novedad.codigo} className="card">
              <div className="card-header">
                <h2 className="card-title">{novedad.nombre}</h2>
              </div>
              <div className="card-content">
                <p><strong>Código:</strong> {novedad.codigo}</p>
                <p><strong>Nombre:</strong> {novedad.nombre}</p>
                <p><strong>Color:</strong> {novedad.color}</p>
                <p><strong>Talle:</strong> {novedad.talle}</p>
              </div>
              <div className="card-footer">
                <button 
                  onClick={() => handleAddProduct(novedad)}
                  className="button"
                >
                  Añadir Producto
                </button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

export default Novedades