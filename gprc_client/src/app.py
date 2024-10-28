import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.dirname(CURRENT_DIR))

from flask import Flask, request, jsonify, make_response
from flask_cors import CORS, cross_origin
from google.protobuf.json_format import MessageToJson
from src.grpcClient import GrpcClient


app = Flask(__name__)
CORS(app) 

@app.route("/")
def hello():
    return "Hello, World!"

# Rutas del servicio Usuario

@app.route("/getUser/<string:username>", methods=["GET"])
@cross_origin()
def getUser(username):
    client = GrpcClient()
    result = client.GetUser(username)
    return MessageToJson(result)

@app.route("/getUsersByTienda/<int:idTienda>", methods=["GET"])
@cross_origin()
def getUsersByTienda(idTienda):
    client = GrpcClient()
    result = client.GetUserByTienda(idTienda)
    return MessageToJson(result)

@app.route("/getUsers", methods=["GET"])
@cross_origin()
def getAllUsers():
    client = GrpcClient()
    result = client.GetAllUsers()
    return MessageToJson(result)

@app.route("/registerUser", methods=["POST"])
@cross_origin()
def registerUser():
    data = request.get_json()
    client = GrpcClient()
    result = client.RegisterUser(data)
    return make_response("ok")

@app.route("/updateUser", methods=["PUT"])
@cross_origin()
def updateUser():
    data = request.get_json()
    client = GrpcClient()
    result = client.UpdateUser(data)
    return make_response("ok")

@app.route("/deleteUser/<int:id>", methods=["DELETE"])
@cross_origin()
def deleteUser(id):
    client = GrpcClient()
    result = client.DeleteUser(id)
    return MessageToJson(result)


@app.route("/authorize", methods=["POST"])
@cross_origin()
def authorize():
    data = request.get_json()
    client = GrpcClient()
    result = client.authorize(data)
    return MessageToJson(result)

# Rutas del servicio Tienda

@app.route("/getTienda/<int:id>", methods=["GET"])
@cross_origin()
def getTienda(id):
    client = GrpcClient()
    result = client.GetTienda(id)
    return MessageToJson(result)

@app.route("/getTiendasByEstado/<string:estado>", methods=["GET"])
@cross_origin()
def getTiendasByEstado(estado):
    client = GrpcClient()
    result = client.GetTiendasByEstado(estado)
    return MessageToJson(result)

@app.route("/getTiendas", methods=["GET"])
@cross_origin()
def getAllTiendas():
    client = GrpcClient()
    result = client.ListarTiendas()
    return MessageToJson(result)

@app.route("/registerTienda", methods=["POST"])
@cross_origin()
def registerTienda():
    data = request.get_json() 
    print(data) 
    client = GrpcClient()
    result = client.RegistrarTienda(data)
    return make_response("ok")

@app.route("/updateTienda", methods=["PUT"])
@cross_origin()
def updateTienda():
    data = request.get_json() 
    client = GrpcClient()
    result = client.UpdateTienda(data)
    return make_response("ok")

@app.route("/deleteTienda/<string:codigo>", methods=["DELETE"])
@cross_origin()
def deleteTienda(codigo):
    client = GrpcClient()
    result = client.DeleteTienda(codigo)
    return MessageToJson(result)

@app.route("/obtenerProductosTiendaByTienda/<int:idTienda>", methods=["GET"])
@cross_origin()
def getTiendaProductosByTienda(idTienda):
    client = GrpcClient()
    result = client.ObtenerProductosPorTienda(idTienda)
    return MessageToJson(result)

@app.route("/obtenerProductosTienda", methods=["GET"])
@cross_origin()
def obtenerProductosTienda():
    client = GrpcClient()
    result = client.ObtenerProductos()
    return MessageToJson(result)

@app.route("/asociarProductos", methods=["POST"])
@cross_origin()
def asociarProductos():
    data = request.get_json() 
    client = GrpcClient()
    result = client.AsociarProductos(data)
    return MessageToJson(result)

# Rutas del servicio Orden
@app.route("/registerOrden", methods=["POST"])
@cross_origin()
def registerOrden():
    data = request.get_json() 
    print(data) 
    client = GrpcClient()
    result = client.CreateOrder(data)
    return MessageToJson(result)

@app.route("/updateOrden/<int:id>", methods=["PUT"])
@cross_origin()
def updateOrden(id):
    client = GrpcClient()
    result = client.ChangeOrderState(id)
    return MessageToJson(result)

# Rutas del servicio Novedad
@app.route("/getNovedades", methods=["GET"])
@cross_origin()
def getAllNovedades():
    client = GrpcClient()
    result = client.ObtenerNovedades()
    return MessageToJson(result)

@app.route("/deleteNovedad/<int:id>", methods=["DELETE"])
@cross_origin()
def deleteNovedad(id):
    client = GrpcClient()
    result = client.DeleteNovedad(id)
    return MessageToJson(result)


# Rutas del servicio Producto

@app.route("/getProduct/<int:id>", methods=["GET"])
@cross_origin()
def getProduct(id):
    client = GrpcClient()
    result = client.GetProduct(id)
    return MessageToJson(result)

@app.route("/getProductByNombre/<string:nombre>", methods=["GET"])
@cross_origin()
def getProductByNombre(nombre):
    client = GrpcClient()
    result = client.GetProductByNombre(nombre)
    return MessageToJson(result)

@app.route("/getProducts", methods=["GET"])
@cross_origin()
def getAllProducts():
    client = GrpcClient()
    result = client.GetAllProducts()
    return MessageToJson(result)

@app.route("/registerProduct", methods=["POST"])
@cross_origin()
def registerProduct():
    data = request.get_json()
    client = GrpcClient()
    result = client.CreateProduct(data)
    return jsonify({"message": "Product created successfully"}), 200

@app.route("/updateProduct", methods=["PUT"])
@cross_origin()
def updateProduct():
    data = request.get_json() 
    client = GrpcClient()
    result = client.UpdateProduct(data)
    return jsonify({"message": "Product updated successfully"}), 200

@app.route("/deleteProduct/<string:codigo>", methods=["DELETE"])
@cross_origin()
def deleteProduct(codigo):
    client = GrpcClient()
    result = client.DeleteProduct(codigo)
    return MessageToJson(result)

@app.route("/registerTiendaProducto", methods=["POST"])
@cross_origin()
def registerTiendaProducto():
    data = request.get_json() 
    client = GrpcClient()
    result = client.AddTiendaProduct(data)
    
    return jsonify({"message": "Relation product store created successfully"}), 200
