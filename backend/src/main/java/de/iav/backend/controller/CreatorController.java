package de.iav.backend.controller;

import de.iav.backend.model.Creator;
import de.iav.backend.model.CreatorDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.repository.CreatorRepository;
import de.iav.backend.repository.TicketRepository;
import de.iav.backend.service.CreatorService;
import org.apache.catalina.Store;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tixhive/creator")
public class CreatorController {
    private final CreatorService creatorService;
    private final TicketRepository ticketRepository;
    private final CreatorRepository creatorRepository;
    public CreatorController(CreatorService creatorService, TicketRepository ticketRepository, CreatorRepository creatorRepository) {
        this.creatorService = creatorService;
        this.ticketRepository = ticketRepository;
        this.creatorRepository = creatorRepository;
    }
    @GetMapping
    public List<Creator> listAllCreator(){return creatorService.listAllCreator();}
    @GetMapping("/{id}")
    public Creator getCreatorById(@PathVariable String id){return creatorService.getCreatorById(id);}
    @GetMapping("/name/{name}")
    public List<Creator> getAllCreatorByName(@PathVariable String name){return creatorService.getAllCreatorByName(name);}
    @GetMapping("/ticket/{id}")
    public Optional<Ticket> listAllTicketByCreatorId(@PathVariable String id){
        return creatorService.listAllTicketByCreatorId(id);
    }
    @PostMapping
    public Creator addCreator(@RequestBody CreatorDTO creatorInfo){
        return creatorService.addCreator(creatorInfo);
    }
    @DeleteMapping("/{id}")
    public void deleteCreatorById(@PathVariable String id){creatorService.deleteCreatorById(id);}
    @PutMapping ("/{creatorId}/tickets/{ticketId}")
    public Creator addTicketToCreator(@PathVariable String creatorId,@PathVariable String ticketId){
        Creator creator = getCreatorById(creatorId);
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        List<Ticket>newTicketList = new ArrayList<>(creator.tickets());
        newTicketList.add(ticket);

        return creatorRepository.save(new Creator(
                creator.ticketCreatorId(),
                creator.firstname(),
                creator.lastname(),
                newTicketList
        ));
    }

    @PutMapping("/update/{id}")
    public Creator updateCreatorById(@PathVariable String id,@RequestBody Creator creatorToUpdate){
        return creatorService.updateCreatorById(id, creatorToUpdate);
    }

}
