package com.unla.users_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserParseError {
    private int lineNumber;
    private String userName;
    private String errorMessage;
}
