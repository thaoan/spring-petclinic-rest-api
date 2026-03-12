package org.springframework.samples.petclinic.owner;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends Repository<Visit, Integer> {

    void save(Visit visit);

    // Mudamos aqui: Usamos @Query para explicar ao Hibernate como chegar no Pet
    // No PetClinic original, a tabela 'visits' tem uma coluna 'pet_id'
    @Query("SELECT v FROM Visit v WHERE v.id IN (SELECT v2.id FROM Pet p JOIN p.visits v2 WHERE p.id = :petId)")
    List<Visit> findByPetId(@Param("petId") Integer petId);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    Optional<Visit> findById(Integer id);
}
