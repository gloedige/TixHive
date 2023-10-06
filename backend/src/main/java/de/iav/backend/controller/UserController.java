package de.iav.backend.controller;

import de.iav.backend.security.AppUser;
import de.iav.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tixhive/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    public AppUser findUserById(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }
}
