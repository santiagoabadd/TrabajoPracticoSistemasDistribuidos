package com.unla.users_service.service;


import com.unla.users_service.dtos.UserResponse;
import com.unla.users_service.model.User;
import com.unla.users_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    public User registrarUsuario(User object) {
        try{
            return userRepository.save(object);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public List<UserResponse> getAllUsers() {
        var clients = userRepository.findAll();
        return clients.stream().map(this::mapToUserResponse).toList();
    }

    public UserResponse getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return mapToUserResponse(user.get());
    }


    public UserResponse findByUserName(String userName) {
        return mapToUserResponse(userRepository.findByUserName(userName).get());
    }



    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userName((user.getUserName()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .rol(user.getRol())
                .activo(user.isActivo())
                .password(user.getPassword())
                .build();
    }
}
