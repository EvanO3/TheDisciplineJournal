package com.journal.discipline.tracker.Security;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.journal.discipline.tracker.Model.User;


public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID =1L;

    private final User user;

    public UserDetailsImpl(User user){
        this.user= user;

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+user.getRoles().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public UUID getUserId(){
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getUsername();

    }




    @Override
    public boolean isAccountNonExpired() {
        return true; // simplify for now
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // simplify for now
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // simplify for now
    }

    @Override
    public boolean isEnabled() {
        return true; // simplify for now
    }

    // Optional helper: get the raw User entity if needed
    public User getUser() {
        return user;
    }
    
}
