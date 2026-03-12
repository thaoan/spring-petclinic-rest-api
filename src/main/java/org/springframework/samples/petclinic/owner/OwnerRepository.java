package org.springframework.samples.petclinic.owner;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {

 
    Page<Owner> findByLastNameStartingWith(String lastName, Pageable pageable);

    @Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
    @Transactional(readOnly = true)
    List<PetType> findPetTypes();

    @Query("SELECT pet FROM Pet pet WHERE pet.id = :id")
    @Transactional(readOnly = true)
    Pet findPetById(@Param("id") Integer id);

    // Adicionamos esse para o Service não reclamar da lista completa se precisar
    List<Owner> findAll();
}
