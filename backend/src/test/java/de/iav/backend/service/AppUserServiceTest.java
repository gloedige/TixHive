package de.iav.backend.service;

import de.iav.backend.model.Ticket;
import de.iav.backend.repository.AppUserRepository;
import de.iav.backend.repository.TicketRepository;
import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppUserServiceTest {
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    IdService idService;
    AppUserService appUserService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        appUserService = new AppUserService(appUserRepository, idService);
    }
    @Test
    void listAllAppUser_whenUserIsNotEmpty_thenReturnListOfAllUser() {
        //GIVEN
        List<AppUser> expectedAppUserList = new ArrayList<>();
        AppUser expectedAppUser1 = new AppUser(
                "1",
                "a.b@c.de",
                "1234",
                AppUserRole.USER,
                null);
        AppUser expectedAppUser2 = new AppUser(
                "2",
                "a.b@c.de",
                "1234",
                AppUserRole.USER,
                null);
        expectedAppUserList.add(expectedAppUser1);
        expectedAppUserList.add(expectedAppUser2);

        //WHEN
        when(appUserRepository.findAll()).thenReturn(expectedAppUserList);
        List<AppUser> actualAppUserList = appUserService.listAllAppUser();

        //THEN
        assertEquals(expectedAppUserList, actualAppUserList);
        verify(appUserRepository, times(1)).findAll();

    }

    @Test
    void getAppUserById() {
    }

    @Test
    void getAppUserByEmail() {
    }

    @Test
    void listAllTicketByAppUserId() {
    }

    @Test
    void addAppUser() {
    }

    @Test
    void updateAppUserById() {
    }

    @Test
    void deleteAppUserById() {
    }

    @Test
    void loadUserByUsername() {
    }
}