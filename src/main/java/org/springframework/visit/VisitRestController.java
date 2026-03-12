package org.springframework.samples.petclinic.owner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.dto.VisitRequest;
import org.springframework.samples.petclinic.dto.VisitResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visits")
@Tag(name = "Visits", description = "Clinical history and pet appointments management")
public class VisitRestController {

    private final VisitService visitService;

    public VisitRestController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/pet/{petId}")
    @Operation(summary = "Get pet history", description = "Returns all medical visits for a specific pet")
    public List<VisitResponse> getVisitsByPet(@PathVariable int petId) {
        return visitService.findByPetId(petId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Record a visit", description = "Creates a new clinical visit for a pet")
    public VisitResponse addVisit(@Valid @RequestBody VisitRequest request) {
        return visitService.addVisit(request);
    }
	@PutMapping("/{visitId}")
@Operation(summary = "Update visit", description = "Updates the description or date of an existing visit")
public VisitResponse updateVisit(@PathVariable int visitId, @Valid @RequestBody VisitRequest request) {
    return visitService.updateVisit(visitId, request);
}

    @DeleteMapping("/{visitId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete visit", description = "Removes a visit record from the database")
    public void deleteVisit(@PathVariable int visitId) {
        try {
            visitService.deleteVisit(visitId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found");
        }
    }
}
