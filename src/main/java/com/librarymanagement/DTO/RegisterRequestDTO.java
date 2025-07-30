package com.librarymanagement.DTO;

import lombok.Builder;
import lombok.Data;

@Data

@Builder                                  // lombok (when new regist user it need to deserialize Dto)
public class RegisterRequestDTO {

    private String username;
    private String email;
    private String password;
}