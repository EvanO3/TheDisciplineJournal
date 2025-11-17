package com.journal.discipline.tracker.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
/*TODO:
 * Configure filter chain
 * Configure In Memory authentication
 * Configure password hash using bycrpt
 */
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.journal.discipline.tracker.Jwt.AuthEntryPoint;
import com.journal.discipline.tracker.Jwt.AuthTokenFilter;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthEntryPoint unauthorizedHandler;

    @Bean 
    public AuthTokenFilter authenticationJWTokenFilter(){
        return new AuthTokenFilter();
    }


 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
         http.csrf(csrf -> csrf.disable())
         .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
         .authorizeHttpRequests((auth) -> 
        auth.requestMatchers("/api/auth/**")
        .permitAll()
        .requestMatchers("/h2-console/**").permitAll()
        .requestMatchers("/api/user").permitAll()
        .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .formLogin(form -> form.disable())
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
        .sessionManagement(Session -> Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(authenticationJWTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    /*Adding this allows you to create a rest controller for authentication */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


  
    @Bean
    public DaoAuthenticationProvider authenticationProvider (){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        String username = "JamesBlake";
        String adminUser="SantanDave";

        UserDetails userDetails = User.withUsername(username)
        .password(passwordEncoder().encode("user"))
        .roles("USER")
        .build();

        UserDetails adminDetails = User.withUsername(adminUser)
        .password(passwordEncoder().encode("admin"))
        .roles("ADMIN")
        .build();
        return new InMemoryUserDetailsManager(userDetails, adminDetails);
        
    }

    /*password Encoder bean */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}


