package de.iav.backend.repository;

import de.iav.backend.model.Ticket;
import de.iav.backend.security.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends MongoRepository <AppUser, String> {
    AppUser findAppUserByAppUserId(String id);
    Optional<Ticket> findAllByAppUserId (String creatorId);
    Optional<AppUser> findAppUsersByEmail (String email);
}
