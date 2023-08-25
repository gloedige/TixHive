package de.iav.backend.repository;

import de.iav.backend.model.TicketCreator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketCreatorRepository extends MongoRepository <TicketCreator, String> {

}
