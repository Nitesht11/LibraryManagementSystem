package com.librarymanagement.Service;

import com.librarymanagement.DTO.LoginRequestDTO;
import com.librarymanagement.DTO.LoginResponseDTO;
import com.librarymanagement.DTO.RegisterRequestDTO;
import com.librarymanagement.Entity.User;
import com.librarymanagement.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class
AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
//    @Autowired
//    private JwtService jwtService;

    public User registerNormalUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("user Already registered");
        }

        Set<String> roles = new HashSet<String>();
        roles.add("Role_User");

        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User registerAdminUser(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByUsername(registerRequestDTO.getUsername()).isPresent()) {
            throw new RuntimeException("user Already registerd");

        }
        Set<String> roles = new HashSet<String>();
        roles.add("Role_Admin");  // for the admin we r assinging 2 roles admin & user
        roles.add("Role_User");


        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRoles(roles);

        return userRepository.save(user);
    }


    // now the imp part  login proces  giving the token

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        // here we  use auth manager

        authenticationManager.authenticate(

                // this things we will authenticate
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword())
                );
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(()-> new RuntimeException("user Not found"));

        // Now fetching the JWT token
//        String token = jwtService.generateToken(userDetails);


        return LoginResponseDTO.builder()
//                .token(token)
                .username(user.getUsername()).roles(user.getRoles()).build();
    }
}