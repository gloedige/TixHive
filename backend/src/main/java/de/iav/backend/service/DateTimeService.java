package de.iav.backend.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateTimeService {
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
