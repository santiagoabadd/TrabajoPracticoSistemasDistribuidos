package com.unla.users_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBulkDTO {

    private String userName;
    private String firstName;
    private String lastName;
    private String tienda;
    private String password;
}
