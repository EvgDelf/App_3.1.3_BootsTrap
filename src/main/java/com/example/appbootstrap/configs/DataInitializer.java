package com.example.appbootstrap.configs;

import com.example.appbootstrap.model.Role;

import com.example.appbootstrap.model.User;
import com.example.appbootstrap.repository.RoleRepository;
import com.example.appbootstrap.repository.UserRepository;
import com.example.appbootstrap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (roleRepository.count() == 0) {

            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);

            List<Role> roles = new ArrayList<>();
            roles.add(adminRole);
            roles.add(userRole);

            User user = new User();
            user.setUsername("admin");
            user.setLastName("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setAge(43);
            user.setEmail("asd@asd.com");
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
