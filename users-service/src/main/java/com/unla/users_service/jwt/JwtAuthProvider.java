package com.unla.users_service.jwt;

import com.unla.users_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.unla.users_service.model.User;
import java.util.Collections;
import java.util.List;


@Component
public class JwtAuthProvider implements AuthenticationProvider {


    private final UserRepository userRepository;




    public JwtAuthProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(!authentication.isAuthenticated()) {
            String username=authentication.getName();
            String password=(String)authentication.getCredentials();
            UserDetails userDetails = new DomainUserDetailsService(userRepository).loadUserByUsername(authentication.getName());
            if(!password.equals(userDetails.getPassword())){
                throw new BadCredentialsException("Password incorrecta");
            }
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    static class DomainUserDetailsService implements UserDetailsService {


        private final UserRepository userRepository;

        @Autowired
        DomainUserDetailsService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user=userRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("USer not found"));
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRol()));
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), simpleGrantedAuthorities);

        }

    }
}