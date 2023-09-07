package de.iav.backend.controller;

import de.iav.backend.security.AppUser;
import de.iav.backend.dto.AppUserDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.repository.AppUserRepository;
import de.iav.backend.repository.TicketRepository;
import de.iav.backend.service.AppUserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tixhive/appuser")
public class AppUserController {
    private final AppUserService appUserService;
    private final TicketRepository ticketRepository;
    private final AppUserRepository appUserRepository;
    public AppUserController(AppUserService appUserService, TicketRepository ticketRepository, AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
        this.ticketRepository = ticketRepository;
        this.appUserRepository = appUserRepository;
    }
    @GetMapping
    public List<AppUser> listAllAppUser(){return appUserService.listAllAppUser();} //TODO Method nicht relevant
    @GetMapping("/{id}")
    public AppUser getAppUserById(@PathVariable String id){return appUserService.getAppUserById(id);}
    @GetMapping("/email/{email}")
    public Optional<AppUser> getAllAppUserByEmail(@PathVariable String email){return appUserService.getAppUserByEmail(email);}
    @GetMapping("/ticket/{id}")
    public Optional<Ticket> listAllTicketByAppUserId(@PathVariable String id){
        return appUserService.listAllTicketByAppUserId(id);
    }
    @PostMapping
    public AppUser addAppUser(@RequestBody AppUserDTO appUserInfo){
        return appUserService.addAppUser(appUserInfo);
    }
    @DeleteMapping("/{id}")
    public void deleteAppUserById(@PathVariable String id){appUserService.deleteAppUserById(id);}
    @PutMapping ("/{appUserId}/tickets/{ticketId}")
    public AppUser addTicketToAppUser(@PathVariable String appUserId, @PathVariable String ticketId){
        AppUser appUser = getAppUserById(appUserId);
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        List<Ticket>newTicketList = appUser.tickets();
        if(newTicketList == null) {
            newTicketList = new ArrayList<>();
        }
        newTicketList.add(ticket);

        return appUserRepository.save(new AppUser(
                appUser.appUserId(),
                appUser.email(),
                appUser.password(),
                appUser.role(),
                newTicketList
        ));
    }

    @PutMapping("/update/{id}")
    public AppUser updateAppUserById(@PathVariable String id, @RequestBody AppUser appUserToUpdate){
        return appUserService.updateAppUserById(id, appUserToUpdate);
    }

}