package de.iav.backend.service;

import de.iav.backend.security.AppUser;
import de.iav.backend.dto.AppUserDTO;
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
        return appUserRepository.findAppUserByAppUserId(id);
    }
    public List<AppUser> getAllAppUserByEmail(String email){
        return appUserRepository.findAppUsersByEmail(email);
    }
    public Optional<Ticket> listAllTicketByAppUserId(String id){
        return appUserRepository.findAllByAppUserId(id);
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
                requestDTO.getEmail(),
                requestDTO.getPassword(),
                requestDTO.getRole(),
                requestDTO.getTickets()
                );
    }
}
