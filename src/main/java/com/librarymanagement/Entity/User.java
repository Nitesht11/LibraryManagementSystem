package com.librarymanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data                                  // for getter setter
@Table(name = " users")
public class User implements UserDetails {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private String username;
    private String email;
    private String password;

    @ElementCollection ( fetch= FetchType.EAGER)      // to get fast response -> if i call user in sevice roles will also load
    private Set<String> roles;                       // every user can be user or admin thus set collection

    // we need convert  roles from set to simple grant Authority because
     // spring secu cant usderstand  this string roles
    // we need to wrap up and send as collection
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()    //  string roles coverted in streams
                .map(SimpleGrantedAuthority::new)  // wrap the roles in Simple grant authority
                .collect(Collectors.toList());      // return it as collection
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
