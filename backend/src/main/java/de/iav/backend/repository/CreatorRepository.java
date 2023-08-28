package de.iav.backend.repository;

import de.iav.backend.model.Creator;
import de.iav.backend.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreatorRepository extends MongoRepository <Creator, String> {
    Creator findByTicketCreatorId(String id);
    Optional<Ticket> findAllByTicketCreatorId (String creatorId);
    List<Creator> findAllByLastnameOrFirstname (String name);
}
