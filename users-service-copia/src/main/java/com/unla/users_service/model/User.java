package com.unla.users_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name= "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String rol;
    private boolean activo;
    private String password;

    private long idTienda;


}
