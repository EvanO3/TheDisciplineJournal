package com.journal.discipline.tracker.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.journal.discipline.tracker.DTOs.APIResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Model.User;
import com.journal.discipline.tracker.Repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired

    private UserRepository userRespository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user =userRespository.findByUsername(username).orElseThrow(() -> new ApiException("Failed to retrieve user with username: " + username));
     return new UserDetailsImpl(user);
    }
    
}
