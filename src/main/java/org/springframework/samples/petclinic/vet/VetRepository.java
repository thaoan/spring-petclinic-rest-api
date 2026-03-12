package org.springframework.samples.petclinic.vet;

import java.util.Collection;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for Vet domain objects.
 */
public interface VetRepository extends JpaRepository<Vet, Integer> {

    /**
     * Busca todos os veterinários e já traz suas especialidades (JOIN FETCH).
     * Isso evita o Erro 500 de "LazyInitializationException".
     */
    @Query("SELECT DISTINCT vet FROM Vet vet LEFT JOIN FETCH vet.specialties")
    @Transactional(readOnly = true)
    List<Vet> findAllWithSpecialties();

    /**
     * Retrieve all Vets from the data store.
     */
    @Transactional(readOnly = true)
    @Cacheable("vets")
    List<Vet> findAll() throws DataAccessException;

    /**
     * Retrieve all Vets from data store in Pages.
     */
    @Transactional(readOnly = true)
    @Cacheable("vets")
    Page<Vet> findAll(Pageable pageable) throws DataAccessException;

}
