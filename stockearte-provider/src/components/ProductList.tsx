import React from "react";
import ProductItem from "./ProductItem";
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

interface ProductListProps {
  products: Product[];
  onDeleteProduct: (id: number) => void;
  onUpdateStock: (id: number, stock: number) => void;
}

const ProductList: React.FC<ProductListProps> = ({
  products,
  onDeleteProduct,
  onUpdateStock,
}) => {
  return (
    <div className="product-list">
      <div className="product-grid">
        {products.map((product) => (
          <ProductItem
            key={product.id}
            product={product}
            onUpdateStock={onUpdateStock}
          />
        ))}
      </div>
    </div>
  );
};

export default ProductList;
