import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.dirname(CURRENT_DIR))

from flask import Flask, request, jsonify, make_response
from flask_cors import CORS, cross_origin
from google.protobuf.json_format import MessageToJson
from src.grpcClient import GrpcClient

test_user = {
        'username': 'santiagoabad321',
        'firstName': 'Santiaggo',
        'lastName': 'Abagd',
        'email': 'santiagog@example.com',
        'rol': 'admin',
        'codigoTienda': '12345',
		'activo':True,
		'password':'password'

    }

test_user_update = {
        'username': 'santiagoabad21',
        'firstName': 'Santiaggosasfaajgfgjffgjfs',
        'lastName': 'Abagd',
        'email': 'santiagog@example.com',
        'rol': 'admin',
        'codigoTienda': '12345',
		'activo':False,
		'password':'password'
    }

test_tienda = {
        'codigo': 'as1234',
        'estado': True,
		'direccion': 'Arna 30',
        'ciudad': 'Lanus',
        'provincia': 'Buenos Aires',
        
        

    }

test_tienda_update = {
        'codigo': 'as1234',
        'estado': True,
		'direccion': 'pintos 30',
        'ciudad': 'Banfield',
        'provincia': 'Buenos Aires',
        
        

    }

app = Flask(__name__)

@app.route("/")
def hello():
    return "Hello, World!"

#Rutas del servicio Usuario

@app.route("/getUser", methods=["GET"])
@cross_origin()
def getUser():
	client = GrpcClient()
	result = client.GetUser()
	return MessageToJson(result)

@app.route("/getUsersByTienda", methods=["GET"])
@cross_origin()
def getUsersByTienda():
	client = GrpcClient()
	result = client.GetUserByTienda()
	return MessageToJson(result)

@app.route("/getUsers", methods=["GET"])
@cross_origin()
def getAllUsers():
	client = GrpcClient()
	result = client.GetAllUsers()
	return MessageToJson(result)


@app.route("/registerUser", methods=["GET"])
@cross_origin()
def registerUser():
	client = GrpcClient()
	result = client.RegisterUser(test_user)
	return make_response("ok")

@app.route("/updateUser", methods=["GET"])
@cross_origin()
def updateUser():
	client = GrpcClient()
	result = client.UpdateUser(test_user_update)
	return make_response("ok")  

@app.route("/deleteUser", methods=["GET"])
@cross_origin()
def deleteUser():
	client = GrpcClient()
	result = client.DeleteUser()
	return MessageToJson(result)


#Rutas del servicio Tienda

@app.route("/getTienda", methods=["GET"])
@cross_origin()
def getTienda():
	client = GrpcClient()
	result = client.GetTienda()
	return MessageToJson(result)

@app.route("/getTiendasByEstado", methods=["GET"])
@cross_origin()
def getTiendasByEstado():
	client = GrpcClient()
	result = client.GetTiendasByEstado()
	return MessageToJson(result)

@app.route("/getTiendas", methods=["GET"])
@cross_origin()
def getAllTiendas():
	client = GrpcClient()
	result = client.ListarTiendas()
	return MessageToJson(result)


@app.route("/registerTienda", methods=["GET"])
@cross_origin()
def registerTienda():
	client = GrpcClient()
	result = client.RegistrarTienda(test_tienda)
	return make_response("ok")

@app.route("/updateTienda", methods=["GET"])
@cross_origin()
def updateTienda():
	client = GrpcClient()
	result = client.UpdateTienda(test_tienda_update)
	return make_response("ok")  

@app.route("/deleteTienda", methods=["GET"])
@cross_origin()
def deleteTienda():
	client = GrpcClient()
	result = client.DeleteTienda()
	return MessageToJson(result)