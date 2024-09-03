package com.turnosrotativos.repository;

import com.turnosrotativos.entity.ConceptoLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConceptoLaboralRepository extends JpaRepository<ConceptoLaboral, Integer> {
    List<ConceptoLaboral> findByNombreContaining(String nombre);
}
