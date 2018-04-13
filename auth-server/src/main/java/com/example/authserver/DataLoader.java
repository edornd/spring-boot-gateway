package com.example.authserver;

import com.example.authserver.api.persistence.entity.Role;
import com.example.authserver.api.persistence.entity.User;
import com.example.authserver.api.persistence.repository.RoleRepository;
import com.example.authserver.api.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        Role userRole = new Role("ROLE_USER");
        Role adminrole = new Role("ROLE_ADMIN");

        roleRepository.save(userRole);
        roleRepository.save(adminrole);

        User user = new User.Builder("user")
                .name("John", "Travolta")
                .password(encoder.encode("john"))
                .roles(userRole)
                .build();
        User admin = new User.Builder("admin")
                .name("Samuel", "Jackson")
                .password(encoder.encode("motherfucka"))
                .roles(userRole, adminrole)
                .build();

        userRepository.save(user);
        userRepository.save(admin);
    }
}
