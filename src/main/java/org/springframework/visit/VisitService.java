package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.dto.VisitRequest;
import org.springframework.samples.petclinic.dto.VisitResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private final OwnerRepository ownerRepository;
    private final VisitRepository visitRepository;

    public VisitService(OwnerRepository ownerRepository, VisitRepository visitRepository) {
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
    }

    @Transactional
    public VisitResponse addVisit(VisitRequest request) {
        // Busca o Pet através do OwnerRepository
        Pet pet = this.ownerRepository.findPetById(request.petId());

        if (pet == null) {
            throw new RuntimeException("Pet não encontrado com o ID: " + request.petId());
        }

        Visit visit = new Visit();
        visit.setDate(request.date());
        visit.setDescription(request.description());

        // Vincula a visita ao pet
        pet.addVisit(visit);

        // Salva o dono (a cascata do JPA cuidará de salvar a nova visita no banco)
        this.ownerRepository.save(this.ownerRepository.findAll().stream()
            .filter(o -> o.getPets().contains(pet))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Dono do pet não encontrado")));

        // IMPORTANTE: Usamos o mapToResponse para garantir que os 4 campos do Record sejam preenchidos
        return VisitResponse.mapToResponse(visit);
    }

    @Transactional(readOnly = true)
    public List<VisitResponse> findByPetId(int petId) {
        // Busca as visitas e usa o método de mapeamento do seu Record
        return visitRepository.findByPetId(petId).stream()
                .map(VisitResponse::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteVisit(int visitId) {
        if (!visitRepository.existsById(visitId)) {
            throw new RuntimeException("Visit not found");
        }
        visitRepository.deleteById(visitId);
    }
	@Transactional
public VisitResponse updateVisit(int visitId, VisitRequest request) {
    // Aqui usamos o visitRepository que criamos antes
    Visit visit = visitRepository.findById(visitId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Visit not found"));

    visit.setDate(request.date());
    visit.setDescription(request.description());

    // O save do Repository resolve o update se o ID já existir
    visitRepository.save(visit);

    return VisitResponse.mapToResponse(visit);
}
}
