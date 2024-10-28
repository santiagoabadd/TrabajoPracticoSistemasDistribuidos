import React, { useState } from "react";
import "./ProductItem.css";

interface Product {
  id: number;
  nombre: string;
  codigo: string;
  foto: string;
  color: string;
  talle: string;
  stock: number;
}

interface ProductItemProps {
  product: Product;
  onUpdateStock: (id: number, stock: number) => void;
}

const ProductItem: React.FC<ProductItemProps> = ({
  product,
  onUpdateStock,
}) => {
  const [newStock, setNewStock] = useState(product.stock);

  const handleStockChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewStock(parseInt(e.target.value));
  };

  const handleUpdateStock = () => {
    onUpdateStock(product.id, newStock);
  };

  return (
    <div className="product-item">
      <h3>{product.nombre}</h3>
      <img src={product.foto} alt={product.nombre} />
      <p>
        <strong>Código:</strong> {product.codigo}
      </p>
      <p>
        <strong>Color:</strong> {product.color}
      </p>
      <p>
        <strong>Talle:</strong> {product.talle}
      </p>
      <p>
        <strong>Stock</strong> {product.stock}
      </p>
      <div className="stock-update">
        <label htmlFor={`stock-${product.id}`}>Stock:</label>
        <input
          id={`stock-${product.id}`}
          type="number"
          value={newStock}
          onChange={handleStockChange}
        />
        <button onClick={handleUpdateStock}>Update Stock</button>
      </div>
    </div>
  );
};

export default ProductItem;
