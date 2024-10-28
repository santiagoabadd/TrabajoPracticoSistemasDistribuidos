import React, { useState, useEffect } from "react";
import ProductItem from "../components/ProductItem";
import "./ProductList.css";

interface Product {
  id: number;
  nombre: string;
  codigo: string;
  foto: string;
  color: string;
  talle: string;
  stock: number;
}

const ProductList: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await fetch("http://localhost:8080/proveedor/productos");
      const data = await response.json();
      setProducts(data);
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  const updateStock = async (id: number, stock: number) => {
    try {
      const response = await fetch(
        `http://localhost:8080/proveedor/productos/${id}/${stock}`,
        {
          method: "PUT",
        }
      );
      if (response.ok) {
        setProducts(
          products.map((product) =>
            product.id === id ? { ...product, stock } : product
          )
        );
      } else {
        throw new Error("Failed to update stock");
      }
    } catch (error) {
      console.error("Error updating stock:", error);
    }
  };

  return (
    <div className="product-list">
      <h2>Lista De Productos</h2>
      <div className="product-grid">
        {products.map((product) => (
          <ProductItem
            key={product.id}
            product={product}
            onUpdateStock={updateStock}
          />
        ))}
      </div>
    </div>
  );
};

export default ProductList;
