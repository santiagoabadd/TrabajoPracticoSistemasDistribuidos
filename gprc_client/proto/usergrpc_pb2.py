# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: usergrpc.proto
# Protobuf Python Version: 5.27.2
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import runtime_version as _runtime_version
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.internal import builder as _builder
_runtime_version.ValidateProtobufRuntimeVersion(
    _runtime_version.Domain.PUBLIC,
    5,
    27,
    2,
    '',
    'usergrpc.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x0eusergrpc.proto\"\xa0\x01\n\x13RegisterUserRequest\x12\x10\n\x08username\x18\x01 \x01(\t\x12\x11\n\tfirstName\x18\x02 \x01(\t\x12\x10\n\x08lastName\x18\x03 \x01(\t\x12\r\n\x05\x65mail\x18\x04 \x01(\t\x12\x0b\n\x03rol\x18\x05 \x01(\t\x12\x14\n\x0c\x63odigoTienda\x18\x06 \x01(\t\x12\x0e\n\x06\x61\x63tivo\x18\x07 \x01(\x08\x12\x10\n\x08password\x18\x08 \x01(\t\"\"\n\x0eGetUserRequest\x12\x10\n\x08username\x18\x01 \x01(\t\"{\n\x0fGetUserResponse\x12\x10\n\x08username\x18\x01 \x01(\t\x12\x11\n\tfirstName\x18\x02 \x01(\t\x12\x10\n\x08lastName\x18\x03 \x01(\t\x12\x0b\n\x03rol\x18\x04 \x01(\t\x12\x14\n\x0c\x63odigoTienda\x18\x05 \x01(\t\x12\x0e\n\x06\x61\x63tivo\x18\x07 \x01(\x08\"/\n\x17GetUsersByTiendaRequest\x12\x14\n\x0c\x63odigoTienda\x18\x01 \x01(\t\"3\n\x18GetUsersByTiendaResponse\x12\x17\n\x04user\x18\x01 \x03(\x0b\x32\t.UserInfo\"\x80\x01\n\x08UserInfo\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x10\n\x08username\x18\x02 \x01(\t\x12\x11\n\tfirstName\x18\x03 \x01(\t\x12\x10\n\x08lastName\x18\x04 \x01(\t\x12\x0b\n\x03rol\x18\x05 \x01(\t\x12\x14\n\x0c\x63odigoTienda\x18\x06 \x01(\t\x12\x0e\n\x06\x61\x63tivo\x18\x07 \x01(\x08\"\x1f\n\x11\x44\x65leteUserRequest\x12\n\n\x02id\x18\x01 \x01(\x03\"6\n\x12\x44\x65leteUserResponse\x12\x0f\n\x07success\x18\x01 \x01(\x08\x12\x0f\n\x07message\x18\x02 \x01(\t\"\x07\n\x05\x45mpty\".\n\x13GetAllUsersResponse\x12\x17\n\x04user\x18\x01 \x03(\x0b\x32\t.UserInfo\"\x9e\x01\n\x11UpdateUserRequest\x12\x10\n\x08username\x18\x01 \x01(\t\x12\x11\n\tfirstName\x18\x02 \x01(\t\x12\x10\n\x08lastName\x18\x03 \x01(\t\x12\r\n\x05\x65mail\x18\x04 \x01(\t\x12\x0b\n\x03rol\x18\x05 \x01(\t\x12\x14\n\x0c\x63odigoTienda\x18\x06 \x01(\t\x12\x0e\n\x06\x61\x63tivo\x18\x07 \x01(\x08\x12\x10\n\x08password\x18\x08 \x01(\t\"0\n\nJwtRequest\x12\x10\n\x08userName\x18\x01 \x01(\t\x12\x10\n\x08password\x18\x02 \x01(\t\"\x1c\n\x08JwtToken\x12\x10\n\x08jwtToken\x18\x01 \x01(\t2\xd3\x02\n\x0bUserService\x12\x36\n\x0cRegisterUser\x12\x14.RegisterUserRequest\x1a\x10.GetUserResponse\x12,\n\x07GetUser\x12\x0f.GetUserRequest\x1a\x10.GetUserResponse\x12\x46\n\x0fGetUserByTienda\x12\x18.GetUsersByTiendaRequest\x1a\x19.GetUsersByTiendaResponse\x12\x35\n\nDeleteUser\x12\x12.DeleteUserRequest\x1a\x13.DeleteUserResponse\x12+\n\x0bGetAllUsers\x12\x06.Empty\x1a\x14.GetAllUsersResponse\x12\x32\n\nUpdateUser\x12\x12.UpdateUserRequest\x1a\x10.GetUserResponse24\n\x0b\x41uthService\x12%\n\tauthorize\x12\x0b.JwtRequest\x1a\t.JwtToken\"\x00\x42\x18\n\x14\x63om.userservice.grpcP\x01\x62\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'usergrpc_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  _globals['DESCRIPTOR']._loaded_options = None
  _globals['DESCRIPTOR']._serialized_options = b'\n\024com.userservice.grpcP\001'
  _globals['_REGISTERUSERREQUEST']._serialized_start=19
  _globals['_REGISTERUSERREQUEST']._serialized_end=179
  _globals['_GETUSERREQUEST']._serialized_start=181
  _globals['_GETUSERREQUEST']._serialized_end=215
  _globals['_GETUSERRESPONSE']._serialized_start=217
  _globals['_GETUSERRESPONSE']._serialized_end=340
  _globals['_GETUSERSBYTIENDAREQUEST']._serialized_start=342
  _globals['_GETUSERSBYTIENDAREQUEST']._serialized_end=389
  _globals['_GETUSERSBYTIENDARESPONSE']._serialized_start=391
  _globals['_GETUSERSBYTIENDARESPONSE']._serialized_end=442
  _globals['_USERINFO']._serialized_start=445
  _globals['_USERINFO']._serialized_end=573
  _globals['_DELETEUSERREQUEST']._serialized_start=575
  _globals['_DELETEUSERREQUEST']._serialized_end=606
  _globals['_DELETEUSERRESPONSE']._serialized_start=608
  _globals['_DELETEUSERRESPONSE']._serialized_end=662
  _globals['_EMPTY']._serialized_start=664
  _globals['_EMPTY']._serialized_end=671
  _globals['_GETALLUSERSRESPONSE']._serialized_start=673
  _globals['_GETALLUSERSRESPONSE']._serialized_end=719
  _globals['_UPDATEUSERREQUEST']._serialized_start=722
  _globals['_UPDATEUSERREQUEST']._serialized_end=880
  _globals['_JWTREQUEST']._serialized_start=882
  _globals['_JWTREQUEST']._serialized_end=930
  _globals['_JWTTOKEN']._serialized_start=932
  _globals['_JWTTOKEN']._serialized_end=960
  _globals['_USERSERVICE']._serialized_start=963
  _globals['_USERSERVICE']._serialized_end=1302
  _globals['_AUTHSERVICE']._serialized_start=1304
  _globals['_AUTHSERVICE']._serialized_end=1356
# @@protoc_insertion_point(module_scope)
