package de.iav.backend.repository;

import de.iav.backend.model.AppUser;
import de.iav.backend.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends MongoRepository <AppUser, String> {
    AppUser findAppUserByTicketAppUserId(String id);
    Optional<Ticket> findAllByTicketAppUserId (String creatorId);
    List<AppUser> findAppUsersByFirstnameOrLastname (String firstname, String lastname);
}
