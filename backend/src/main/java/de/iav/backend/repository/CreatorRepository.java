package de.iav.backend.repository;

import de.iav.backend.model.Creator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepository extends MongoRepository <Creator, String> {

}
