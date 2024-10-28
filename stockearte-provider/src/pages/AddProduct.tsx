import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./AddProduct.css";

interface Product {
  nombre: string;
  codigo: string;
  foto: string;
  color: string;
  talle: string;
  stock: number;
}

const AddProduct: React.FC = () => {
  const [product, setProduct] = useState<Product>({
    nombre: "",
    codigo: "",
    foto: "",
    color: "",
    talle: "",
    stock: 0,
  });

  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setProduct((prev) => ({
      ...prev,
      [name]: name === "stock" ? parseInt(value) : value,
    }));
  };

  const addProduct = async (product: Omit<Product, "id">) => {
    try {
      const response = await fetch(
        "http://localhost:8080/proveedor/productos/create",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(product),
        }
      );
      const newProduct = await response.json();
      // Note: We're not updating the products state here as it's handled in the ProductList component
      navigate("/");
    } catch (error) {
      console.error("Error adding product:", error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await addProduct(product);
  };

  return (
    <div className="add-product">
      <h2>Añadir un nuevo producto</h2>
      <form onSubmit={handleSubmit} className="product-form">
        <input
          type="text"
          name="nombre"
          value={product.nombre}
          onChange={handleChange}
          placeholder="Nombre"
          required
        />
        <input
          type="text"
          name="codigo"
          value={product.codigo}
          onChange={handleChange}
          placeholder="Código"
          required
        />
        <input
          type="text"
          name="foto"
          value={product.foto}
          onChange={handleChange}
          placeholder="URL de la foto"
          required
        />
        <input
          type="text"
          name="color"
          value={product.color}
          onChange={handleChange}
          placeholder="Color"
          required
        />
        <input
          type="text"
          name="talle"
          value={product.talle}
          onChange={handleChange}
          placeholder="Talle"
          required
        />
        <input
          type="number"
          name="stock"
          value={product.stock}
          onChange={handleChange}
          placeholder="Stock"
          required
        />
        <button type="submit">Añadir Producto</button>
      </form>
    </div>
  );
};

export default AddProduct;
