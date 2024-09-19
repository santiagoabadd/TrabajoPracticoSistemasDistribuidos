import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.dirname(CURRENT_DIR))

CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
PROTO_DIR = os.path.join(CURRENT_DIR, '../proto')  # Ajusta la ruta según tu estructura de carpetas
sys.path.append(PROTO_DIR)



import grpc
import proto.usergrpc_pb2_grpc as usergrpc_grpc
import proto.usergrpc_pb2 as usergrpc_pb2
import proto.tiendagrpc_pb2_grpc as tiendagrpc_grpc
import proto.tiendagrpc_pb2 as tiendagrpc_pb2
from google.protobuf.json_format import MessageToJson

class GrpcClient(object):

	def __init__(self):
		self.host = 'localhost'
		self.server_port = 9090

		self.channel = grpc.insecure_channel('{}:{}'.format(self.host, self.server_port))
		self.stub = usergrpc_grpc.UserServiceStub(self.channel)

		self.tienda_channel = grpc.insecure_channel('{}:{}'.format(self.host, self.server_port))
		self.tienda_stub = tiendagrpc_grpc.TiendaServiceStub(self.tienda_channel)


	def GetUserByTienda(self):
		request = usergrpc_pb2.GetUsersByTiendaRequest(codigoTienda="12345")
		return self.stub.GetUserByTienda(request)
	
	def GetAllUsers(self):
		request = usergrpc_pb2.Empty()
		return self.stub.GetAllUsers(request)
	
	def GetUser(self):
		request = usergrpc_pb2.GetUserRequest(username="santiagoabad1")
		return self.stub.GetUser(request)
	
	def RegisterUser(self, user):
		pUser = usergrpc_pb2.RegisterUserRequest(
			username = user['username'],
			firstName= user['firstName'],
			lastName= user['lastName'],
			email= user['email'],
			rol= user['rol'],
			codigoTienda= user['codigoTienda'],
			activo=user['activo'],
			password=user['password']
		)

		return self.stub.RegisterUser(pUser)
	
	def UpdateUser(self, user):
		pUser = usergrpc_pb2.UpdateUserRequest(
			username = user['username'],
			firstName= user['firstName'],
			lastName= user['lastName'],
			email= user['email'],
			rol= user['rol'],
			codigoTienda= user['codigoTienda'],
			activo=user['activo'],
			password=user['password']
		)

		return self.stub.UpdateUser(pUser)

	def DeleteUser(self):
		request = usergrpc_pb2.DeleteUserRequest(username="santiagoabad1")
		return self.stub.DeleteUser(request)
		
	

	def GetUserByTienda(self):
		request = usergrpc_pb2.GetUsersByTiendaRequest(codigoTienda="12345")
		return self.stub.GetUserByTienda(request)
	

	## Metodos Tienda ///////////////////

	def ListarTiendas(self):
		request =	tiendagrpc_pb2.EmptyTienda()
		return self.tienda_stub.ListarTiendas(request)
	
	def GetTienda(self):
		request = tiendagrpc_pb2.GetTiendaRequest(codigo="as1234")
		return self.tienda_stub.GetTienda(request)
	
	def GetTiendasByEstado(self):
		request = tiendagrpc_pb2.GetTiendaByEstadoRequest(estado=True)
		return self.tienda_stub.GetTiendasByEstado(request)
	
	

	def RegistrarTienda(self, tienda):
		pTienda = tiendagrpc_pb2.RegistrarTiendaRequest(
			codigo= tienda['codigo'],
			estado= tienda['estado'],
			direccion= tienda['direccion'],
			ciudad= tienda['ciudad'],
			provincia= tienda['provincia'],
			
		)

		return self.tienda_stub.RegistrarTienda(pTienda)
	
	def UpdateTienda(self, tienda):
		pTienda = tiendagrpc_pb2.UpdateTiendaRequest(
			codigo= tienda['codigo'],
			estado= tienda['estado'],
			direccion= tienda['direccion'],
			ciudad= tienda['ciudad'],
			provincia= tienda['provincia'],
		)

		return self.tienda_stub.UpdateTienda(pTienda)

	def DeleteTienda(self):
		request = tiendagrpc_pb2.DeleteTiendaRequest(id=1)
		return self.tienda_stub.DeleteTienda(request)
		
	

	

if __name__ == '__main__':
	client = GrpcClient()
	result = client.GetUser()
	print(MessageToJson(result))
	