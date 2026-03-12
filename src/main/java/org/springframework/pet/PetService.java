package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.dto.PetRequest;
import org.springframework.samples.petclinic.dto.PetResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final OwnerRepository ownerRepository;

    public PetService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Transactional(readOnly = true)
    public List<Pet> findAll() {
        return ownerRepository.findAll().stream()
                .flatMap(owner -> owner.getPets().stream())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Pet> findById(Integer id) {
        return ownerRepository.findAll().stream()
                .flatMap(owner -> owner.getPets().stream())
                .filter(pet -> pet.getId().equals(id))
                .findFirst();
    }

    @Transactional
    public Pet savePet(PetRequest request) {
        Owner owner = this.ownerRepository.findById(request.ownerId())
            .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + request.ownerId()));

        Pet pet = new Pet();
        pet.setName(request.name());
        pet.setBirthDate(request.birthDate());

        PetType type = this.ownerRepository.findPetTypes().stream()
            .filter(t -> t.getId().equals(request.typeId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Pet Type not found with ID: " + request.typeId()));

        pet.setType(type);
        owner.addPet(pet);
        this.ownerRepository.save(owner);
        return pet;
    }

    @Transactional
    public Pet updatePet(int petId, PetRequest request) {
        Pet pet = findById(petId)
            .orElseThrow(() -> new RuntimeException("Pet not found"));

        pet.setName(request.name());
        pet.setBirthDate(request.birthDate());

        // Atualiza o tipo se mudou
        PetType type = this.ownerRepository.findPetTypes().stream()
            .filter(t -> t.getId().equals(request.typeId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Pet Type not found"));
        pet.setType(type);

        return ownerRepository.save(pet.getOwner()).getPets().stream()
            .filter(p -> p.getId().equals(petId))
            .findFirst().get();
    }

    @Transactional
    public void deleteById(int id) {
        Pet pet = findById(id)
            .orElseThrow(() -> new RuntimeException("Pet not found"));

        Owner owner = pet.getOwner();
        owner.getPets().remove(pet);
        ownerRepository.save(owner);
    }
}
