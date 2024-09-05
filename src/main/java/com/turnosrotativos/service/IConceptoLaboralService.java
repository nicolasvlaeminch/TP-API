package com.turnosrotativos.service;

import com.turnosrotativos.entity.ConceptoLaboral;

import java.util.List;

public interface IConceptoLaboralService {
    List<ConceptoLaboral> obtenerConceptosLaborales(Long id, String nombre);
}
