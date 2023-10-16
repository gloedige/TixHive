package de.iav.backend.service;

import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRepository;
import de.iav.backend.security.AppUserRole;
import de.iav.backend.security.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppUserServiceTest {
    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final AppUserService AppUserService = new AppUserService(appUserRepository, passwordEncoder);
    private final UserService userService = new UserService(appUserRepository);

    @Test
    void loadUserByUsername_whenUserExist_thenBuildUser() {
        // Arrange
        String username = "testUser";
        AppUser appUser = new AppUser(
                "1",
                username,
                "test@example.com",
                "password",
                AppUserRole.USER,
                new ArrayList<>()
        );

        when(appUserRepository.findByEmail(username)).thenReturn(Optional.of(appUser));

        AppUserService appUserService = new AppUserService(appUserRepository, passwordEncoder);

        // Act
        UserDetails userDetails = appUserService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String username = "nonExistentUser";

        when(appUserRepository.findByEmail(username)).thenReturn(Optional.empty());

        AppUserService authUserService = new AppUserService(appUserRepository, passwordEncoder);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            authUserService.loadUserByUsername(username);
        });
    }
}