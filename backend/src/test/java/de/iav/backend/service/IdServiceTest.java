package de.iav.backend.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class IdServiceTest {

    @Test
    void getId_whenGenerateIdIsCalled_thenIdIsGenerated() {
        //GIVEN
        IdService idService = new IdService();

        //WHEN
        String id = idService.generateId();

        //THEN
        assertNotNull(id);
    }

}