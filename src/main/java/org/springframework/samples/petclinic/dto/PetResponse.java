package org.springframework.samples.petclinic.dto;

import java.time.LocalDate;
import org.springframework.samples.petclinic.owner.Pet;

public record PetResponse(
    Integer id,
    String name,
    LocalDate birthDate,
    String typeName,
    String ownerName // Mantemos no record para não quebrar outros arquivos, mas passamos um texto fixo
) {
    public static PetResponse mapToResponse(Pet pet) {
        return new PetResponse(
            pet.getId(),
            pet.getName(),
            pet.getBirthDate(),
            pet.getType().getName(),
            "N/A" // Mantemos no record para não quebrar outros arquivos, mas passamos um texto fixo
        );
    }
}
