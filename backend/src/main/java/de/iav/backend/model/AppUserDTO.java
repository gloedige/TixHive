package de.iav.backend.model;

import lombok.Getter;

import java.util.List;
@Getter
public class AppUserDTO {
    private String email;
    private String password;
    private AppUserRole role;
    List<Ticket> tickets;


}
