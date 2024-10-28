import React from "react";
import { useState, useEffect } from "react";

interface PurchaseOrder {
  codigoArticulo: string;
  estado: string | null;
  codigoTienda: string;
  totalCantidadSolicitada: number;
}

interface FilterParams {
  codigoArticulo?: string;
  fechaInicio?: string;
  fechaFin?: string;
  estado?: string;
  codigoTienda?: string;
}

export const PurchaseOrders: React.FC = () => {
  const [purchaseOrders, setPurchaseOrders] = useState<PurchaseOrder[]>([]);
  const [filters, setFilters] = useState<FilterParams>({});
  const [startDate, setStartDate] = useState<string>("");
  const [endDate, setEndDate] = useState<string>("");

  useEffect(() => {
    fetchPurchaseOrders();
  }, []);

  const fetchPurchaseOrders = async () => {
    try {
      const response = await fetch("http://localhost:8085/getAllOrders", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });

      // Verificar si la respuesta es exitosa (status code en el rango 200-299)
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();
      setPurchaseOrders(data.ordenComprasResumen); // Asignar los datos recibidos
    } catch (error) {
      console.error("Error fetching purchase orders:", error.message);
    }
  };

  const handleFilterChange = (name: string, value: string) => {
    setFilters((prev) => ({
      ...prev,
      [name]: value || undefined,
    }));
  };

  const handleDateChange = (name: string, value: string) => {
    if (name === "fechaInicio") {
      setStartDate(value);
    } else {
      setEndDate(value);
    }
    handleFilterChange(name, value);
  };

  const applyFilters = () => {
    fetchPurchaseOrders();
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Purchase Orders</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4 mb-4">
        <div>
          <label
            htmlFor="codigoArticulo"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Product Code
          </label>
          <input
            type="text"
            id="codigoArticulo"
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
            value={filters.codigoArticulo || ""}
            onChange={(e) =>
              handleFilterChange("codigoArticulo", e.target.value)
            }
            placeholder="Enter product code"
          />
        </div>

        <div>
          <label
            htmlFor="fechaInicio"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Start Date
          </label>
          <input
            type="date"
            id="fechaInicio"
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
            value={startDate}
            onChange={(e) => handleDateChange("fechaInicio", e.target.value)}
          />
        </div>

        <div>
          <label
            htmlFor="fechaFin"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            End Date
          </label>
          <input
            type="date"
            id="fechaFin"
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
            value={endDate}
            onChange={(e) => handleDateChange("fechaFin", e.target.value)}
          />
        </div>

        <div>
          <label
            htmlFor="estado"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Status
          </label>
          <select
            id="estado"
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
            value={filters.estado || ""}
            onChange={(e) => handleFilterChange("estado", e.target.value)}
          >
            <option value="">Select status</option>
            <option value="pending">Pending</option>
            <option value="completed">Completed</option>
            <option value="cancelled">Cancelled</option>
          </select>
        </div>

        <div>
          <label
            htmlFor="codigoTienda"
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            Store
          </label>
          <input
            type="text"
            id="codigoTienda"
            className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
            value={filters.codigoTienda || ""}
            onChange={(e) => handleFilterChange("codigoTienda", e.target.value)}
            placeholder="Enter store code"
          />
        </div>
      </div>

      <button
        onClick={applyFilters}
        className="mb-4 px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
      >
        Apply Filters
      </button>

      <div className="overflow-x-auto">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Product Code
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Status
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Store Code
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Total Quantity Requested
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {purchaseOrders.map((order, index) => (
              <tr key={index}>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {order.codigoArticulo}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {order.estado || "N/A"}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {order.codigoTienda}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {order.totalCantidadSolicitada}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
