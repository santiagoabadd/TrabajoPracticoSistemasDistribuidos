import React, { useState } from "react";
import "./ProductForm.css";

interface ProductFormProps {
  onAddProduct: (product: {
    nombre: string;
    codigo: string;
    foto: string;
    color: string;
    talle: string;
    stock: number;
  }) => void;
}

export default function ProductForm({ onAddProduct }: ProductFormProps) {
  const [product, setProduct] = useState({
    nombre: "",
    codigo: "",
    foto: "",
    color: "",
    talle: "",
    stock: 0,
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setProduct((prev) => ({
      ...prev,
      [name]: name === "stock" ? parseInt(value) : value,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onAddProduct(product);
    setProduct({
      nombre: "",
      codigo: "",
      foto: "",
      color: "",
      talle: "",
      stock: 0,
    });
  };

  return (
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
  );
}
