package org.springframework.samples.petclinic.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record VetRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    Set<Integer> specialtyIds // IDs das especialidades (Radiologia, Cirurgia, etc.)
) {}
