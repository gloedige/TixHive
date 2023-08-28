package de.iav.backend.controller;

import de.iav.backend.model.Creator;
import de.iav.backend.model.CreatorDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.service.CreatorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tixhive/creator")
public class CreatorController {
    private final CreatorService creatorService;
    public CreatorController(CreatorService creatorService) {
        this.creatorService = creatorService;
    }
    @GetMapping
    public List<Creator> listAllCreator(){return creatorService.listAllCreator();}
    @GetMapping("/{id}")
    public Optional<Creator> getCreatorById(@PathVariable String id){return creatorService.getCreatorById(id);}
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
    @PutMapping("/{id}")
    public Creator updateCreatorById(@PathVariable String id,@RequestBody Creator creatorToUpdate){
        return creatorService.updateCreatorById(id, creatorToUpdate);
    }
    @DeleteMapping("/{id}")
    public void deleteCreatorById(@PathVariable String id){creatorService.deleteCreatorById(id);}

}
