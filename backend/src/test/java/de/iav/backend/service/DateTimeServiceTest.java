package de.iav.backend.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DateTimeServiceTest {
    @Test
    void getCurrentDateTime_whenCalled_thenReturnsCurrentDateTime() {
        //GIVEN
        DateTimeService dateTimeService = new DateTimeService();

        //WHEN
        java.time.LocalDateTime currentDateTime = dateTimeService.getCurrentDateTime();

        //THEN
        assertNotNull(currentDateTime);
    }

}