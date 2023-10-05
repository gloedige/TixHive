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

    public AppUser findUserById(String id) {
        return appUserRepository.findById(id).orElseThrow();
    }
}
