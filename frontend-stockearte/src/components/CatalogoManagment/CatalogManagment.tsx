"use client";

import React, { useState, useEffect, useContext } from "react";
import { AuthContext } from "../../context/AuthContext";

interface Product {
  tiendaId: number;
  productoId: number;
  nombre: string;
  codigo: string;
  foto: string;
  color: string;
  talle: string;
}

interface Catalog {
  id: number;
  nombre: string;
  products: { id: number }[];
}

export default function CatalogManagement() {
  const [products, setProducts] = useState<Product[]>([]);
  const [catalogs, setCatalogs] = useState<Catalog[]>([]);
  const [selectedProducts, setSelectedProducts] = useState<number[]>([]);
  const [newCatalogName, setNewCatalogName] = useState("");
  const [selectedCatalog, setSelectedCatalog] = useState<number | null>(null);
  const { user } = useContext(AuthContext);

  useEffect(() => {
    fetchProducts();
    fetchCatalogs();
    console.log(products, catalogs);
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await fetch(
        `http://localhost:5000/obtenerProductosTiendaByTienda/${user.idTienda}`
      );
      const data = await response.json();
      setProducts(data.productos);
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };
  const fetchCatalogs = async () => {
    try {
      const response = await fetch("http://localhost:8085/getAllCatalogos", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          idTienda:Number(user.idTienda),
        }),
      });
      
      const data = await response.json();
      setCatalogs(data.catalogo);
    } catch (error) {
      console.error("Error fetching catalogs:", error);
    }
  };

  const handleProductSelection = (productId: number) => {
    setSelectedProducts((prev) =>
      prev.includes(productId)
        ? prev.filter((id) => id !== productId)
        : [...prev, productId]
    );
  };

  const createCatalog = async () => {
    try {
      const response = await fetch("http://localhost:8085/postCatalogo", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          idTienda: user.idTienda, 
          nombre: newCatalogName,
          products: selectedProducts.map((id) => ({ id })),
        }),
      });
      if (response.ok) {
        alert("Catálogo creado exitosamente");
        setNewCatalogName("");
        setSelectedProducts([]);
        fetchCatalogs();
      }
    } catch (error) {
      console.error("Error creating catalog:", error);
    }
  };

  const addProductsToCatalog = async () => {
    if (!selectedCatalog) return;

    try {
      const response = await fetch(
        "http://localhost:8085/addProductsToCatalogo",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            id: selectedCatalog,
            products: selectedProducts.map((id) => ({ id })),
          }),
        }
      );
      if (response.ok) {
        alert("Productos agregados al catálogo exitosamente");
        setSelectedProducts([]);
        setSelectedCatalog(null);
        fetchCatalogs();
      }
    } catch (error) {
      console.error("Error adding products to catalog:", error);
    }
  };

  const deleteCatalog = async (catalogId: number) => {
    try {
      const response = await fetch(`http://localhost:8085/eliminarCatalogo/${catalogId}`, {
        method: "DELETE",
      });
  
      if (response.ok) {
        alert("Catálogo eliminado exitosamente");
        fetchCatalogs();
      } else {
        alert("Error al eliminar el catálogo");
      }
    } catch (error) {
      console.error("Error deleting catalog:", error);
    }
  };

  const exportCatalogToPdf = async (catalogId: number) => {
    try {
      const response = await fetch(`http://localhost:8085/PrintCatalogoPdf/${catalogId}`);
      if (response.ok) {
        const data = await response.json();
        if (data.pdfData) {
          // Decodifica la cadena base64
          const pdfBlob = base64ToBlob(data.pdfData, "application/pdf");
          
          // Crea una URL para el Blob
          const url = window.URL.createObjectURL(pdfBlob);
          
          // Crea un enlace de descarga
          const a = document.createElement("a");
          a.style.display = "none";
          a.href = url;
          a.download = `catalog-${catalogId}.pdf`;
          document.body.appendChild(a);
          a.click();
          
          // Revoca la URL del Blob para liberar memoria
          window.URL.revokeObjectURL(url);
        }
      }
    } catch (error) {
      console.error("Error exporting catalog to PDF:", error);
    }
  };
  
  // Función para convertir base64 a Blob
  const base64ToBlob = (base64: string, mimeType: string) => {
    const byteCharacters = atob(base64);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    return new Blob([byteArray], { type: mimeType });
  };
  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Gestión de Catálogos</h1>
      <div className="mb-8">
        <h2 className="text-xl font-semibold mb-2">Productos</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {products.map((product) => (
            <div key={product.productoId} className="border p-4 rounded">
              <img
                src={product.foto}
                alt={product.nombre}
                className="w-full h-48 object-cover mb-2"
              />
              <h3 className="font-semibold">{product.nombre}</h3>
              <p>Código: {product.codigo}</p>
              <p>Color: {product.color}</p>
              <p>Talle: {product.talle}</p>
              <button
                onClick={() => handleProductSelection(product.productoId)}
                className={`mt-2 px-4 py-2 rounded ${
                  selectedProducts.includes(product.productoId)
                    ? "bg-blue-500 text-white"
                    : "bg-gray-200 text-gray-800"
                }`}
              >
                {selectedProducts.includes(product.productoId)
                  ? "Seleccionado"
                  : "Seleccionar"}
              </button>
            </div>
          ))}
        </div>
      </div>

      <div className="mb-8">
        <h2 className="text-xl font-semibold mb-2">Crear Nuevo Catálogo</h2>
        <input
          type="text"
          value={newCatalogName}
          onChange={(e) => setNewCatalogName(e.target.value)}
          placeholder="Nombre del nuevo catálogo"
          className="border p-2 mr-2"
        />
        <button
          onClick={createCatalog}
          className="bg-green-500 text-white px-4 py-2 rounded"
          disabled={!newCatalogName || selectedProducts.length === 0}
        >
          Crear Catálogo
        </button>
      </div>

      <div className="mb-8">
        <h2 className="text-xl font-semibold mb-2">
          Agregar Productos a Catálogo Existente
        </h2>
        <select
          value={selectedCatalog || ""}
          onChange={(e) => setSelectedCatalog(Number(e.target.value))}
          className="border p-2 mr-2"
        >
          <option value="">Seleccionar catálogo</option>
          {catalogs.map((catalog) => (
            <option key={catalog.id} value={catalog.id}>
              {catalog.nombre}
            </option>
          ))}
        </select>
        <button
          onClick={addProductsToCatalog}
          className="bg-blue-500 text-white px-4 py-2 rounded"
          disabled={!selectedCatalog || selectedProducts.length === 0}
        >
          Agregar Productos al Catálogo
        </button>
      </div>

      <div>
        <h2 className="text-xl font-semibold mb-2">Catálogos Existentes</h2>
        <table className="w-full border-collapse border">
          <thead>
            <tr className="bg-gray-200">
              <th className="border p-2">ID</th>
              <th className="border p-2">Nombre</th>
              <th className="border p-2">Acciones</th>
            </tr>
          </thead>
          <tbody>
            {catalogs.map((catalog) => (
              <tr key={catalog.id}>
                <td className="border p-2">{catalog.id}</td>
                <td className="border p-2">{catalog.nombre}</td>
                <td className="border p-2">
                  <button
                    onClick={() => exportCatalogToPdf(catalog.id)}
                    className="bg-purple-500 text-white px-4 py-2 rounded"
                  >
                    Exportar a PDF
                  </button>
                  <button
              onClick={() => deleteCatalog(catalog.id)} // Call delete function
              className="bg-red-500 text-white px-4 py-2 rounded"
            >
              Eliminar
            </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
