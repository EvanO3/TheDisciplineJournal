package com.journal.discipline.tracker.Jwt;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtClaims {
    
    private String sub; // this will be the username
    private UUID id;
}
