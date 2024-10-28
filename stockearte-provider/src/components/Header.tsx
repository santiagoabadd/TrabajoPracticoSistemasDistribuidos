import React from "react";
import { Link } from "react-router-dom";
import "./Header.css";

const Header: React.FC = () => {
  return (
    <header className="header">
      <div className="header-content">
        <h1>MANEJO DE PRODUCTOS DEL PROVEEDOR</h1>
        <nav>
          <ul>
            <li>
              <Link to="/">Lista De Productos</Link>
            </li>
            <li>
              <Link to="/add">Añadir Productos</Link>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  );
};

export default Header;
