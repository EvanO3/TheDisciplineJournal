package com.journal.discipline.tracker.Jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint{

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
                /*First set the content type i.e what i will send back to the user */

                logger.error("Unauthorized error: {}", authException.getMessage());

          
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                /*Building the response to send when there is an error */
                 final Map<String, Object> body = new HashMap<>();

                 body.put("Status", HttpServletResponse.SC_UNAUTHORIZED);
                 body.put("Error", "Unauthorized");
                 body.put("message", authException.getMessage());
                 body.put("Path ", request.getServletPath());

                 final ObjectMapper mapper = new ObjectMapper();
                 mapper.writeValue(response.getOutputStream(), body);


     
    }
    
}
