package de.iav.backend.service;

import de.iav.backend.model.AppUser;
import de.iav.backend.model.AppUserDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final IdService idService;

    public AppUserService(AppUserRepository appUserRepository, IdService idService) {
        this.appUserRepository = appUserRepository;
        this.idService = idService;
    }

    public List<AppUser> listAllAppUser() {return appUserRepository.findAll();}
    public AppUser getAppUserById(String id) {
        return appUserRepository.findAppUserByTicketAppUserId(id);
    }
    public List<AppUser> getAllAppUserByName(String firstname, String lastname){
        return appUserRepository.findAppUsersByFirstnameOrLastname(firstname, lastname);
    }
    public Optional<Ticket> listAllTicketByAppUserId(String id){
        return appUserRepository.findAllByTicketAppUserId(id);
    }
    public AppUser addAppUser(AppUserDTO appUserRequest)
    {
        AppUser newAppUser = convertToEntity(appUserRequest);
        return appUserRepository.save(newAppUser);
    }
    public AppUser updateAppUserById (String id, AppUser appUserToUpdate){
        Optional<AppUser> existingAppUser = appUserRepository.findById(id);
        if(existingAppUser.isPresent()){
            return appUserRepository.save(appUserToUpdate);
        }
        throw new NoSuchElementException("Element with "+ id +" not found!");
    }
    public void deleteAppUserById(String id){
        appUserRepository.deleteById(id);
    }
    private AppUser convertToEntity(AppUserDTO requestDTO) {
        return new AppUser(
                idService.generateId(),
                requestDTO.getFirstname(),
                requestDTO.getLastname(),
                requestDTO.getTickets()
                );
    }
}
