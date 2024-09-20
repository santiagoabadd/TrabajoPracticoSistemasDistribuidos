

import MOCK_DATA from '../data/MOCK_DATA.json';

const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

export const mockFetchProducts = async (url, options = {}) => {
  await delay(500); // Simulate network latency

  if (url === '/api/products' && options.method === 'GET') {
    return handleGetProducts(options);
  } else if (url === '/api/products' && options.method === 'POST') {
    return handleAddProduct(options);
  } else if (url.startsWith('/api/products/') && options.method === 'PUT') {
    return handleUpdateProduct(url, options);
  } else if (url === '/api/stores') {
    return handleGetStores();
  }

  return { ok: false, json: () => Promise.resolve({ message: 'Not found' }) };
};

const handleGetProducts = (options) => {
  // Combinar información de producto y producto_tienda
  const products = MOCK_DATA.producto.map(product => {
    const productInStores = MOCK_DATA.producto_tienda.filter(pt => pt.id_producto === product.id_producto);
    // const store = productInStore ? MOCK_DATA.tienda.find(t => t.id_tienda === productInStore.id_tienda) : null;

    return {
      id: product.id_producto,
      name: product.nombre_producto,
      code: product.id_producto,
      size: product.talle,
      color: product.color,
      stock: productInStores.reduce((acc, pt) => {
        acc[pt.id_tienda] = pt.stock;
        return acc;
      }, {}),
      assignedStores: productInStores.map(pt => pt.id_tienda)
    };
  });

  // Si se proporciona un id_tienda en las opciones, filtrar productos por tienda
  if (options.idTienda) {
    return {
      ok: true,
      json: () => Promise.resolve(products.filter(product => product.assignedStores.includes(options.idTienda)))
    };
  }

  return { ok: true, json: () => Promise.resolve(products) };
};

const handleAddProduct = (options) => {
  const newProduct = JSON.parse(options.body);
  MOCK_DATA.producto.push({
    id_producto: newProduct.id,
    nombre_producto: newProduct.name,
    talle: newProduct.size,
    color: newProduct.color
  });

  // Add entries to producto_tienda for each assigned store
  newProduct.assignedStores.forEach(storeId => {
    MOCK_DATA.producto_tienda.push({
      id_producto: newProduct.id,
      id_tienda: storeId,
      stock: newProduct.stock[storeId] || 0
    });
  });

  return { ok: true, json: () => Promise.resolve(newProduct) };
};

const handleUpdateProduct = (url, options) => {
  const productId = url.split('/').pop();
  const updatedProduct = JSON.parse(options.body);

  const productIndex = MOCK_DATA.producto.findIndex(p => p.id_producto === productId);
  if (productIndex !== -1) {
    MOCK_DATA.producto[productIndex] = {
      id_producto: updatedProduct.id,
      nombre_producto: updatedProduct.name,
      talle: updatedProduct.size,
      color: updatedProduct.color,
    };

    // Update producto_tienda
    MOCK_DATA.producto_tienda = MOCK_DATA.producto_tienda.filter(pt => pt.id_producto !== productId);
    Object.entries(updatedProduct.stock).forEach(([storeId, stockQuantity]) => {
      const existingIndex = MOCK_DATA.producto_tienda.findIndex(pt => pt.id_producto === productId && pt.id_tienda === storeId);
      if (existingIndex !== -1) {
        MOCK_DATA.producto_tienda[existingIndex].stock = stockQuantity;
      } else {
        MOCK_DATA.producto_tienda.push({
          id_producto: productId,
          id_tienda: storeId,
          stock: stockQuantity
        });
      }
    });

    return { ok: true, json: () => Promise.resolve(updatedProduct) };
  }

  return { ok: false, json: () => Promise.resolve({ message: 'Product not found' }) };
};

const handleGetStores = () => {
  return { ok: true, json: () => Promise.resolve(MOCK_DATA.tienda) };
};