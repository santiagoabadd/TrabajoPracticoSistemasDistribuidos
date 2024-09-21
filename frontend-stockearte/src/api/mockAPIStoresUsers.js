import MOCK_DATA from '../data/MOCK_DATA.json';

export const fetchStores = () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const stores = MOCK_DATA.tienda.map(store => ({
        id: store.id_tienda,
        code: store.id_tienda,
        status: store.habilitada === "true" ? "enabled" : "disabled",
        address: store.direccion,
        city: store.ciudad,
        province: store.provincia
      }));
      resolve(stores);
    }, 500); // Simula un retraso de red de 500ms
  });
};


export const fetchUsers = () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const users = MOCK_DATA.usuario.map(user => {
        const store = MOCK_DATA.tienda.find(store => store.id_tienda === user.id_tienda);
        return {
          id: user.nombre_usuario,
          username: user.nombre_usuario,
          password: user.clave,
          store: store ? store.id_tienda : 'Unknown',
          status: user.habilitado === "TRUE" ? "Enabled" : "Disabled",
          name: user.nombre,
          surname: user.apellido,
          role: MOCK_DATA.rol.find(rol => rol.id_rol === user.id_rol)?.nombre_rol || 'Unknown'
        };
      });
      resolve(users);
    }, 500);
  });
};


export const fetchUserById = (userId) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const user = MOCK_DATA.usuario.find(user => user.nombre_usuario === userId);
      if (user) {
        const role = MOCK_DATA.rol.find(rol => rol.id_rol === user.id_rol)?.nombre_rol || 'Unknown';
        resolve({
          id: user.nombre_usuario,
          username: user.nombre_usuario,
          password: user.clave,
          store: user.id_tienda,
          name: user.nombre,
          surname: user.apellido,
          enabled: user.habilitado === "TRUE",
          role: role
        });
      } else {
        reject(new Error('User not found'));
      }
    }, 500);
  });
};

// Función para actualizar un usuario
export const updateUser = (userId, userData) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const userIndex = MOCK_DATA.usuario.findIndex(user => user.nombre_usuario === userId);
      if (userIndex !== -1) {
        MOCK_DATA.usuario[userIndex] = {
          ...MOCK_DATA.usuario[userIndex],
          nombre_usuario: userData.username,
          clave: userData.password,
          id_tienda: userData.store,
          nombre: userData.name,
          apellido: userData.surname,
          habilitado: userData.enabled ? "TRUE" : "FALSE",
          id_rol: userData.store === 'Casa Central' ? '1' : '2'
        };
        resolve({
          id: userData.username,
          username: userData.username,
          password: userData.password,
          store: userData.store,
          status: userData.enabled ? "Enabled" : "Disabled",
          name: userData.name,
          surname: userData.surname,
          role: userData.store === 'Casa Central' ? 'Casa Central' : 'Tienda'
        });
      } else {
        reject(new Error('User not found'));
      }
    }, 500);
  });
};

export const createUser = (userData) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const newUser = {
        nombre_usuario: userData.username,
        clave: userData.password,
        id_tienda: userData.store,
        nombre: userData.name,
        apellido: userData.surname,
        habilitado: userData.enabled ? "TRUE" : "FALSE",
        id_rol: userData.store === 'Casa Central' ? '1' : '2' // Assuming '1' is for central users and '2' for store users
      };
      MOCK_DATA.usuario.push(newUser);
      resolve({
        id: newUser.nombre_usuario,
        username: newUser.nombre_usuario,
        password: newUser.clave,
        store: newUser.id_tienda,
        status: newUser.habilitado === "TRUE" ? "Enabled" : "Disabled",
        name: newUser.nombre,
        surname: newUser.apellido,
        role: newUser.id_tienda === 'Casa Central' ? 'Casa Central' : 'Tienda'
      });
    }, 500);
  });
};

export const deleteUser = (userId) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const userIndex = MOCK_DATA.usuario.findIndex(user => user.nombre_usuario === userId);
      if (userIndex !== -1) {
        MOCK_DATA.usuario.splice(userIndex, 1);
        resolve({ success: true, message: 'User deleted successfully' });
      } else {
        reject(new Error('User not found'));
      }
    }, 500);
  });
};