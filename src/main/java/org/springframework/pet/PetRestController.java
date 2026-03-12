package org.springframework.samples.petclinic.owner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.dto.PetRequest;
import org.springframework.samples.petclinic.dto.PetResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pets")
@Tag(name = "Pets", description = "Operations for pet management and categorization")
public class PetRestController {

    private final PetService petService;

    public PetRestController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    @Operation(summary = "List all pets", description = "Returns a complete list of all registered pets")
    public List<PetResponse> getAllPets() {
        return petService.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{petId}")
    @Operation(summary = "Get pet by ID", description = "Finds a specific pet record by its unique ID")
    public PetResponse getPetById(@PathVariable int petId) {
        return petService.findById(petId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new pet", description = "Creates a new pet record and links it to an owner")
    public PetResponse createPet(@Valid @RequestBody PetRequest request) {
        Pet pet = petService.savePet(request);
        return mapToResponse(pet);
    }

    @PutMapping("/{petId}")
    @Operation(summary = "Update pet", description = "Updates an existing pet's information")
    public PetResponse updatePet(@PathVariable int petId, @Valid @RequestBody PetRequest request) {
        Pet updatedPet = petService.updatePet(petId, request);
        return mapToResponse(updatedPet);
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete pet", description = "Removes a pet record from the database")
    public void deletePet(@PathVariable int petId) {
        if (petService.findById(petId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        }
        petService.deleteById(petId);
    }

    private PetResponse mapToResponse(Pet pet) {
        return new PetResponse(
                pet.getId(),
                pet.getName(),
                pet.getBirthDate(),
                pet.getType().getName(),
                pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName()
        );
    }
}
