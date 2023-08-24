package de.iav.backend.repository;

import de.iav.backend.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findAllByPriorityOrderByPriority (String priority);
    List<Ticket> findAllByStatusOrderByStatus (String status);
    List<Ticket> findAllByCreationDateOrderByCreationDate (LocalDateTime creationDate);
}
