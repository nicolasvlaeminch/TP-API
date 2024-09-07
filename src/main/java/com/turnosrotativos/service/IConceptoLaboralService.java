package com.turnosrotativos.service;

import com.turnosrotativos.dto.ConceptoLaboralResponse;

import java.util.List;

public interface IConceptoLaboralService {
    List<ConceptoLaboralResponse> obtenerConceptosLaborales(Long id, String nombre);
}
