package de.iav.backend.dto;

import de.iav.backend.model.AppUserRole;
import de.iav.backend.model.Ticket;
import lombok.Getter;

import java.util.List;
@Getter
public class AppUserDTO {
    private String email;
    private String password;
    private AppUserRole role;
    List<Ticket> tickets;


}
