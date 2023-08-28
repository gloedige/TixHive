package de.iav.backend.service;

import de.iav.backend.model.Creator;
import de.iav.backend.model.CreatorDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.model.TicketRequestDTO;
import de.iav.backend.repository.CreatorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CreatorService {
    private final CreatorRepository creatorRepository;
    private final IdService idService;

    public CreatorService(CreatorRepository creatorRepository, IdService idService) {
        this.creatorRepository = creatorRepository;
        this.idService = idService;
    }

    public List<Creator> listAllCreator() {return creatorRepository.findAll();}
    public Optional<Creator> getCreatorById(String id) {
        return creatorRepository.findById(id);
    }
    public List<Creator> getAllCreatorByName(String name){
        return creatorRepository.findAllByLastnameOrFirstname(name);
    }
    public Optional<Ticket> listAllTicketByCreatorId(String id){
        return creatorRepository.findAllByTicketCreatorId(id);
    }
    public Creator addCreator(CreatorDTO creatorRequest)
    {
        Creator newCreator = convertToEntity(creatorRequest);
        return creatorRepository.save(newCreator);
    }
    public Creator updateCreatorById (String id, Creator creatorToUpdate){
        Optional<Creator> existingCreator = creatorRepository.findById(id);
        if(existingCreator.isPresent()){
            return creatorRepository.save(creatorToUpdate);
        }
        throw new NoSuchElementException("Element with "+ id +" not found!");
    }
    public void deleteCreatorById(String id){
        creatorRepository.deleteById(id);
    }
    private Creator convertToEntity(CreatorDTO requestDTO) {
        return new Creator(
                idService.generateId(),
                requestDTO.getFirstname(),
                requestDTO.getLastname(),
                requestDTO.getTickets()
                );
    }
}
