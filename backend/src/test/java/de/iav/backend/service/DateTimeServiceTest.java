package de.iav.backend.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DateTimeServiceTest {
    @Test
    void getCurrentDateTime_whenCalled_thenReturnsCurrentDateTime() {
        //GIVEN
        DateTimeService dateTimeService = new DateTimeService();

        //WHEN
        LocalDateTime currentDateTime = dateTimeService.getCurrentDateTime();

        //THEN
        assertNotNull(currentDateTime);
    }

}