package org.springframework.samples.petclinic.vet; // PRECISA ser .vet

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.samples.petclinic.model.NamedEntity;

public record VetResponse(
    Integer id,
    String firstName,
    String lastName,
    List<String> specialties
) {
    public static VetResponse mapToResponse(Vet vet) {
        return new VetResponse(
            vet.getId(),
            vet.getFirstName(),
            vet.getLastName(),
            vet.getSpecialties().stream()
                .map(NamedEntity::getName)
                .collect(Collectors.toList())
        );
    }
}
