package org.springframework.samples.petclinic.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // LISTAR TODOS COM PAGINAÇÃO (Novo - Necessário para o Controller Senior)
    @Transactional(readOnly = true)
    public Page<Owner> findAll(Pageable pageable) {
        return ownerRepository.findAll(pageable);
    }

    // Mantido para compatibilidade se necessário
    @Transactional(readOnly = true)
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Owner> findById(Integer id) {
        return ownerRepository.findById(id);
    }

    @Transactional
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Transactional(readOnly = true)
    public Page<Owner> findByLastName(String lastName, int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return ownerRepository.findByLastNameStartingWith(lastName != null ? lastName : "", pageable);
    }

    @Transactional
    public void deleteById(Integer id) {
        // Uma boa prática: verificar se existe antes de tentar deletar
        if (ownerRepository.findById(id).isPresent()) {
            ownerRepository.deleteById(id);
        }
    }
}
