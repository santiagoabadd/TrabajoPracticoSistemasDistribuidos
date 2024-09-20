import MOCK_DATA from '../data/MOCK_DATA.json';

const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

export const mockFetch = async (url, options) => {
  await delay(500); // Simular latencia de red

  if (url === '/api/login') {
    return handleLogin(JSON.parse(options.body));
  } else if (url === '/api/register') {
    return handleRegister(JSON.parse(options.body));
  } else if (url === '/api/auth-status') {
    return handleAuthStatus();
  }

  return { ok: false, json: () => Promise.resolve({ message: 'Not found' }) };
};

const handleLogin = (credentials) => {
  const user = MOCK_DATA.usuario.find(u => u.nombre_usuario === credentials.email && u.clave === credentials.password);
  
  if (user) {
    const role = MOCK_DATA.rol.find(r => r.id_rol === user.id_rol).nombre_rol;
    return {
      ok: true,
      json: () => Promise.resolve({ email: user.nombre_usuario, role })
    };
  }
  
  return { ok: false, json: () => Promise.resolve({ message: 'Invalid credentials' }) };
};

const handleRegister = (userData) => {
  const existingUser = MOCK_DATA.usuario.find(u => u.nombre_usuario === userData.email);
  
  if (existingUser) {
    return { ok: false, json: () => Promise.resolve({ message: 'User already exists' }) };
  }
  
  // En una implementación real, aquí se añadiría el nuevo usuario a la base de datos
  const role = 'Tienda'; // Asumimos que nuevos registros son siempre 'Tienda'
  return {
    ok: true,
    json: () => Promise.resolve({ email: userData.email, role })
  };
};

const handleAuthStatus = () => {
  // Simular una verificación de estado de autenticación
  // En una implementación real, esto verificaría una sesión o token
  return { ok: true, json: () => Promise.resolve({ email: null, role: null }) };
};