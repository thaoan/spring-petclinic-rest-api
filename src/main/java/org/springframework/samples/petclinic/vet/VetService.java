package org.springframework.samples.petclinic.vet;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.samples.petclinic.vet.VetResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VetService {

    private final VetRepository vetRepository;

    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Transactional(readOnly = true)
    public List<VetResponse> findAllVets() {
        Collection<Vet> vets = this.vetRepository.findAll();
        return vets.stream()
            .map(VetResponse::mapToResponse)
            .collect(Collectors.toList());
    }
}
