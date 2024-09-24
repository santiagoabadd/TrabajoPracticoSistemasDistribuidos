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

@app.route("/getUsersByTienda/<string:codigoTienda>", methods=["GET"])
@cross_origin()
def getUsersByTienda(codigoTienda):
    client = GrpcClient()
    result = client.GetUserByTienda(codigoTienda)
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

# Rutas del servicio Tienda

@app.route("/getTienda/<string:codigo>", methods=["GET"])
@cross_origin()
def getTienda(codigo):
    client = GrpcClient()
    result = client.GetTienda(codigo)
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




# Rutas del servicio Producto

@app.route("/getProduct/<string:codigo>", methods=["GET"])
@cross_origin()
def getProduct(codigo):
    client = GrpcClient()
    result = client.GetProduct(codigo)
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
