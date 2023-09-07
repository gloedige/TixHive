package de.iav.backend.service;
//package de.iav.backend.security;

import de.iav.backend.security.AppUser;
import de.iav.backend.dto.AppUserDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService{
    private final AppUserRepository appUserRepository;
    private final IdService idService;
    //private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, IdService idService) {
        this.appUserRepository = appUserRepository;
        this.idService = idService;
    }

    public List<AppUser> listAllAppUser() {return appUserRepository.findAll();}
    public AppUser getAppUserById(String id) {
        return appUserRepository.findAppUserByAppUserId(id);
    }
    public Optional<AppUser> getAppUserByEmail(String email){
        return appUserRepository.findAppUsersByEmail(email);
    }
    public Optional<Ticket> listAllTicketByAppUserId(String id){
        return appUserRepository.findAllByAppUserId(id);
    }
    public AppUser addAppUser(AppUserDTO appUserRequest) //TODO Methode umbenennen in register und anpassen!
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findAppUsersByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("user not found!"));
        return User.builder()
                .username(appUser.email())
                .password(appUser.password())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + appUser.role().name())))
                .build();
    }
}
