package org.springframework.samples.petclinic.owner;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.dto.OwnerRequest;
import org.springframework.samples.petclinic.dto.OwnerResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/owners")
// Define o grupo no Swagger com nome e descrição em inglês
@Tag(name = "Owners", description = "Management of Pet Owners and their contact information")
public class OwnerRestController {

    private final OwnerService ownerService;

    // Injeção de dependência via construtor (Melhor prática que @Autowired)
    public OwnerRestController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // Listar todos (GET)
	@Operation(summary = "List all owners", description = "Returns a list of all registered pet owners in the system")
    @GetMapping
    public List<OwnerResponse> getAllOwners() {
        return this.ownerService.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Buscar por ID específico (GET)
    @GetMapping("/{ownerId}")
    public OwnerResponse getOwnerById(@PathVariable("ownerId") int ownerId) {
        return this.ownerService.findById(ownerId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));
    }

	// UPDATE - Atualizar um dono existente
@Operation(summary = "Update an owner", description = "Updates the details of an existing owner by ID")
@PutMapping("/{ownerId}")
public OwnerResponse updateOwner(@PathVariable int ownerId, @Valid @RequestBody OwnerRequest request) {
    return this.ownerService.findById(ownerId).map(owner -> {
        owner.setFirstName(request.firstName());
        owner.setLastName(request.lastName());
        owner.setAddress(request.address());
        owner.setCity(request.city());
        owner.setTelephone(request.telephone());
        return mapToResponse(this.ownerService.save(owner));
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found"));
}

// DELETE - Remover um dono
@Operation(summary = "Delete an owner", description = "Removes an owner from the system")
@DeleteMapping("/{ownerId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteOwner(@PathVariable int ownerId) {
    if (!this.ownerService.findById(ownerId).isPresent()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found");
    }
    // Note: Você precisará garantir que o OwnerService tenha o método delete
    this.ownerService.deleteById(ownerId);
}

    // Criar novo proprietário (POST)
	@Operation(summary = "Create a new owner", description = "Registers a new pet owner and persists it in the database")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerResponse createOwner(@Valid @RequestBody OwnerRequest request) {
        Owner owner = new Owner();
        owner.setFirstName(request.firstName());
        owner.setLastName(request.lastName());
        owner.setAddress(request.address());
        owner.setCity(request.city());
        owner.setTelephone(request.telephone());

        Owner savedOwner = this.ownerService.save(owner);
        return mapToResponse(savedOwner);
    }


    private OwnerResponse mapToResponse(Owner owner) {
        return new OwnerResponse(
                owner.getId(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getAddress(),
                owner.getCity(),
                owner.getTelephone()
        );
    }
}
