package com.librarymanagement.Controller;

import com.librarymanagement.DTO.LoginRequestDTO;
import com.librarymanagement.DTO.LoginResponseDTO;
import com.librarymanagement.DTO.RegisterRequestDTO;
import com.librarymanagement.Entity.User;
import com.librarymanagement.Service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping ("/api/auth")
public class AuthController {

     @Autowired
    private AuthenticationService authenticationService;

     @PostMapping("/registerNormalUser")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO){
         log.info("reqieste send"+registerRequestDTO);
        return  ResponseEntity.ok(authenticationService.registerNormalUser( registerRequestDTO  ));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
         return ResponseEntity.ok(authenticationService.login(loginRequestDTO));
    }
}
