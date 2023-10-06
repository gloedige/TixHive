package de.iav.backend.service;

import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AppUserRepository appUserRepository;

    public UserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser findUserByEmail(String email) {
        return appUserRepository.findByEmail(email).orElseThrow();
    }
}
