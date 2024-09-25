package com.unla.users_service.service;

import com.unla.users_service.dtos.UserResponse;
import com.unla.users_service.repository.UserRepository;
import com.unla.users_service.model.User;
import com.userservice.grpc.*;
import com.userservice.grpc.UserServiceGrpc.UserServiceImplBase;

import jakarta.transaction.Transactional;
import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@GrpcService
@RequiredArgsConstructor
public class UserServiceGrpc extends UserServiceImplBase {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        String username = request.getUsername();
        User user = userRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("User not found"));

        GetUserResponse response = GetUserResponse.newBuilder()
                .setUsername(user.getUserName())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setRol(user.getRol())
                .setCodigoTienda(user.getCodigoTienda())
                .setActivo(user.isActivo())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void registerUser(RegisterUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        User user=new User();
        user.setUserName(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRol(request.getRol());
        user.setEmail(request.getEmail());
        user.setCodigoTienda(request.getCodigoTienda());
        user.setPassword(request.getPassword());
        user.setActivo(request.getActivo());



        String message;
        if (isUserExists(user.getUserName())) {
            message = "Ese nombre de usuario esta ocupado";
        } else {
            userRepository.save(user);
        }

        GetUserResponse response = GetUserResponse.newBuilder()
                .setUsername(user.getUserName())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setRol(user.getRol())
                .setCodigoTienda(user.getCodigoTienda())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



    private boolean isUserExists(String username) {
        return userRepository.findByUserName(username).isPresent();
    }

    @Override
    public void getUserByTienda(GetUsersByTiendaRequest request, StreamObserver<GetUsersByTiendaResponse> responseObserver) {
        String codigoTienda = request.getCodigoTienda();
        List<User> users = userRepository.findByCodigoTienda(codigoTienda);

        List<UserInfo> userInfos = users.stream()
                .map(user -> UserInfo.newBuilder()
                        .setId(user.getId())
                        .setUsername(user.getUserName())
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setCodigoTienda(user.getCodigoTienda())
                        .setActivo(user.isActivo())
                        .setRol(user.getRol())
                        .build())
                .collect(Collectors.toList());

        GetUsersByTiendaResponse response = GetUsersByTiendaResponse.newBuilder()
                .addAllUser(userInfos)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UpdateUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        Long id = request.getId();

        User user = userRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if(!request.getUsername().equals(user.getUserName())){
            User userExist = userRepository.findByUserName(request.getUsername()).orElse(null);
            if(userExist!=null){
                throw new RuntimeException("Ya existe un usuario con ese username");
            }
        }

        user.setUserName(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRol(request.getRol());
        user.setCodigoTienda(request.getCodigoTienda());
        user.setPassword(request.getPassword());
        user.setActivo(request.getActivo());

        userRepository.save(user);

        GetUserResponse response = GetUserResponse.newBuilder()
                .setUsername(user.getUserName())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setRol(user.getRol())
                .setCodigoTienda(user.getCodigoTienda())
                .setActivo(user.isActivo())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void deleteUser(DeleteUserRequest request, StreamObserver<DeleteUserResponse> responseObserver) {
        long id = request.getId();
        boolean success;
        String message;

        try {
            userRepository.deleteById(id);
            success = true;
            message = "Usuario eliminado con éxito";
        } catch (Exception e) {
            success = false;
            message = "Error al eliminar usuario: " + e.getMessage();
        }

        DeleteUserResponse response = DeleteUserResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllUsers(Empty request, StreamObserver<GetAllUsersResponse> responseObserver) {
        List<User> users = userRepository.findAll();

        List<UserInfo> userInfos = users.stream()
                .map(user -> UserInfo.newBuilder()
                        .setId(user.getId())
                        .setUsername(user.getUserName())
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setCodigoTienda(user.getCodigoTienda())
                        .setActivo(user.isActivo())
                        .setRol(user.getRol())
                        .build())
                .collect(Collectors.toList());

        GetAllUsersResponse response = GetAllUsersResponse.newBuilder()
                .addAllUser(userInfos)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



}