package com.journal.discipline.tracker.Jwt;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.journal.discipline.tracker.Security.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter{

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private JwtUtils jwtUtils;

  

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                try{
                    /*Get the JWT from the header */
                    String jwt = parseJWT(request);

                    //check if token is valid
                    if(jwt !=null && jwtUtils.validateJwtToken(jwt)){
                        /*Extract the username */
    
                        String username = jwtUtils.getUsernameFromJwt(jwt);

                        
                        //load the user
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        /*Create an authentication object */
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        /*This helps get more details of where the request came from i.e the IP or if there was a session ID attached it it */
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        //set the context to tell spring the user should be authenticated

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }



                }catch(Exception e){
                    System.out.println("Cannot set user authentication: " + e.getMessage());
                }
                //tells spring the filter you made is done and to proceed to the next
                filterChain.doFilter(request, response);
    }


    /*Helper function to parse JWT from header */

    private String parseJWT(HttpServletRequest request){
        String jwt = jwtUtils.getJwtTokenFromHeader(request);
        return jwt;
    }
    
}
