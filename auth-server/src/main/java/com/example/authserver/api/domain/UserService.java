package com.example.authserver.api.domain;

import com.example.authserver.api.persistence.entity.User;
import com.example.authserver.api.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(identifier);
        if (!userOptional.isPresent()){
            userOptional = userRepository.findByEmail(identifier);
            if (!userOptional.isPresent())
                throw new UsernameNotFoundException("Identifier not found: " + identifier);
        }
        return new UserPrincipal(userOptional.get());
    }
}
