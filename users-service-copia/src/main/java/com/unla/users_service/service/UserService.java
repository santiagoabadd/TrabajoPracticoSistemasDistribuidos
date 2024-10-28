package com.unla.users_service.service;


import com.tiendaservice.grpc.GetTiendaByCodigoRequest;
import com.tiendaservice.grpc.GetTiendaResponse;
import com.tiendaservice.grpc.TiendaServiceGrpc;
import com.unla.users_service.converts.UserBulkUploadConverter;
import com.unla.users_service.dtos.UserBulkDTO;
import com.unla.users_service.dtos.UserParseError;
import com.unla.users_service.dtos.UserResponse;
import com.unla.users_service.model.User;
import com.unla.users_service.repository.UserRepository;
import io.spring.guides.gs_producing_web_service.UserParseErrorSoap;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final UserBulkUploadConverter userConverter;
    private final TiendaServiceGrpc.TiendaServiceBlockingStub tiendaServiceBlockingStub;

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

    public List<UserParseErrorSoap> uploadUsersFromCsv(InputStream csvInputStream) throws Exception {
            List<UserParseError> errors = parseCsvFile(csvInputStream);
            List<UserParseErrorSoap> errorsSoap=userConverter.convertUserParseErrorListToSoap(errors);
            return errorsSoap;
    }

    private List<UserParseError> parseCsvFile(InputStream inputStream) throws Exception {
        List<User> users = new ArrayList<>();
        List<UserParseError> errors = new ArrayList<>();

        try (Reader reader = new InputStreamReader(inputStream);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("userName", "firstName", "lastName", "tienda", "password").withSkipHeaderRecord(true))) {

            int lineNumber = 1;

            for (CSVRecord csvRecord : csvParser) {

                String userName = csvRecord.get("userName");
                String firstName = csvRecord.get("firstName");
                String lastName = csvRecord.get("lastName");
                String tienda = csvRecord.get("tienda");
                String password = csvRecord.get("password");
                GetTiendaByCodigoRequest getTiendaByCodigoRequest= GetTiendaByCodigoRequest.newBuilder().setCodigo(tienda).build();
                GetTiendaResponse tiendaResponse=tiendaServiceBlockingStub.getTiendaByCodigo(getTiendaByCodigoRequest);
                System.out.println("--->: "+tiendaResponse.getEstado() +" --- "+tiendaResponse.getCodigo() +" --- ");
                if (userName == null || userName.isEmpty() ||
                        firstName == null || firstName.isEmpty() ||
                        lastName == null || lastName.isEmpty() ||
                        tienda == null || tienda.isEmpty() ||
                        password == null || password.isEmpty()) {
                    UserParseError error = new UserParseError(lineNumber, userName, "Al menos 1 campo esta vacio o es null");
                    errors.add(error);
                    System.out.println("ERROR NUMBER " + lineNumber + ": " + error.toString());
                } else if (!userRepository.findByUserName(userName).isEmpty()) {
                    UserParseError error = new UserParseError(lineNumber, userName, "Ya existe un usuario con el nombre: " + userName);
                    errors.add(error);
                    System.out.println("ERROR NUMBER " + lineNumber + ": " + error.toString());
                } else if(tiendaResponse==null) {
                    UserParseError error = new UserParseError(lineNumber, userName, "No existe una tienda con el codigo: " +  tiendaResponse.getCodigo());
                    errors.add(error);
                    System.out.println("ERROR NUMBER " + lineNumber + ": " + error.toString());
                } else if(tiendaResponse.getEstado()==false){
                    UserParseError error = new UserParseError(lineNumber, userName, "La tienda con el codigo: " +  tiendaResponse.getCodigo() + " no esta activa.");
                    errors.add(error);
                    System.out.println("ERROR NUMBER " + lineNumber + ": " + error.toString());
                } else{

                    User user = new User();
                    user.setUserName(userName);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setActivo(true);
                    user.setRol("tienda");
                    user.setPassword(password);

                    users.add(user);
                    userRepository.save(user);
                }

                lineNumber++;
            }
        }

        System.out.println("ERRORS: " + errors.toString());
        return errors;
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
