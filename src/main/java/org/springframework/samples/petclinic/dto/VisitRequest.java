package org.springframework.samples.petclinic.dto;

import java.time.LocalDate;

public record VisitRequest(
    LocalDate date,
    String description,
    Integer petId
) {}
