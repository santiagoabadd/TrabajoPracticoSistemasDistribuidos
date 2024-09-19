package com.unla.users_service.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserResponse {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String rol;
    private String tienda;
    private boolean activo;
    private String password;
}
