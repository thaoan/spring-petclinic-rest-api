package org.springframework.samples.petclinic.dto;

import java.time.LocalDate;
import org.springframework.samples.petclinic.owner.Visit;

public record VisitResponse(
    Integer id,
    LocalDate date,
    String description,
    String petName // Mantemos o campo no record
) {
    public static VisitResponse mapToResponse(Visit visit) {
        return new VisitResponse(
            visit.getId(),
            visit.getDate(),
            visit.getDescription(),
            "N/A" // Alterado aqui: como a classe Visit não conhece o Pet, usamos "N/A"
        );
    }
}
