package org.springframework.samples.petclinic.vet;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.dto.VetRequest; // Você precisará criar este DTO
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/vets")
@Tag(name = "Veterinarians", description = "Management of the clinical staff and specialties")
public class VetController {

    private final VetRepository vetRepository;

    public VetController(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @GetMapping
    @Operation(summary = "List all veterinarians", description = "Returns a list of all vets with their specialties")
    public Collection<Vet> showVetList() {
        return this.vetRepository.findAllWithSpecialties();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new veterinarian")
    public Vet createVet(@Valid @RequestBody VetRequest request) {
        Vet vet = new Vet();
        vet.setFirstName(request.firstName());
        vet.setLastName(request.lastName());
        return this.vetRepository.save(vet);
    }

    @PutMapping("/{vetId}")
    @Operation(summary = "Update a veterinarian")
    public Vet updateVet(@PathVariable int vetId, @Valid @RequestBody VetRequest request) {
        return this.vetRepository.findById(vetId).map(vet -> {
            vet.setFirstName(request.firstName());
            vet.setLastName(request.lastName());
            return this.vetRepository.save(vet);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vet not found"));
    }

    @DeleteMapping("/{vetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a veterinarian")
    public void deleteVet(@PathVariable int vetId) {
        if (!vetRepository.existsById(vetId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vet not found");
        }
        this.vetRepository.deleteById(vetId);
    }
}
