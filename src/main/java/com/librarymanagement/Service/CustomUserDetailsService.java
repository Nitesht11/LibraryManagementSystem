package com.librarymanagement.Service;

import com.librarymanagement.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



// this is only for the initial login
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
     private UserRepository userRepository;
    @Override

    // load by username contacts the  database to verify user by string Username
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->
                new RuntimeException("user Not Found"));
    }
}
// for initial login user sends credential then customeUser Detail will verify the user
// then load the user thus JWT process -> Jwt generate token-> revet the token
// this process is for initial login only
