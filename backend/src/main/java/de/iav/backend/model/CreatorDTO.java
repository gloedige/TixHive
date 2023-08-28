package de.iav.backend.model;

import lombok.Getter;

import java.util.List;
@Getter
public class CreatorDTO {
    private String firstname;
    private String lastname;
    List<Ticket> tickets;


}
