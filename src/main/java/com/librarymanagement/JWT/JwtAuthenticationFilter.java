package com.librarymanagement.JWT;

import com.librarymanagement.Respository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component

@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter
{
    @Autowired
    private JwtService jwtService;

    @Autowired
   private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
     final String authHeader = request.getHeader("Authorization");
     final  String JwtToken;
     final String username;

// check if auth header is present n start with bearer
     if(authHeader==null|| !authHeader.startsWith("Bearer")){
         filterChain.doFilter(request,response);
         return;
     }

//  if all okay then extrct jwt token from header

        String jwtToken = authHeader.substring(7);
        username= jwtService.extractUsername(jwtToken);

// check if we have userame and no entry exist  yet in contextholder
      if (username!= null && SecurityContextHolder.getContext().getAuthentication()==null){

          var userDetails =userRepository.findByUsername(username)
                  .orElseThrow(()-> new RuntimeException(" user not found"));

// validaet the token
          if(jwtService.isTokenValid(jwtToken,userDetails)){
//               create authentiction  with user roles
              List<SimpleGrantedAuthority>authorities=userDetails.getRoles()
                      .stream()
                      .map(SimpleGrantedAuthority::new)
                      .collect(Collectors.toList());

              UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(userDetails, null,authorities) ;
//                      set  Authen details
                      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    update the security context holder with authentication
                      SecurityContextHolder.getContext().setAuthentication(authToken);

          }

      }filterChain.doFilter(request,response);
    }
}
