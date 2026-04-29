package com.example.busstation.security;

import com.example.busstation.model.AppUser;
import com.example.busstation.model.Role;
import com.example.busstation.repository.AppUserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeds default application users on first startup.
 * Skipped when the app_users table already contains at least one row.
 *
 * Default credentials (change in production via the user management API):
 *   admin    / admin123    (ADMIN)
 *   operator / operator123 (OPERATOR)
 *   viewer   / viewer123   (VIEWER)
 */
@Component
public class DataInitializer implements ApplicationRunner {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                new AppUser("admin",    passwordEncoder.encode("admin123"),    Role.ADMIN),
                new AppUser("operator", passwordEncoder.encode("operator123"), Role.OPERATOR),
                new AppUser("viewer",   passwordEncoder.encode("viewer123"),   Role.VIEWER)
            ));
        }
    }
}

