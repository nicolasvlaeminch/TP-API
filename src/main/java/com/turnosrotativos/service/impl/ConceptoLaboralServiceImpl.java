package com.turnosrotativos.service.impl;

import com.turnosrotativos.dto.ConceptoLaboralResponse;
import com.turnosrotativos.entity.ConceptoLaboral;
import com.turnosrotativos.repository.IConceptoLaboralRepository;
import com.turnosrotativos.service.IConceptoLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConceptoLaboralServiceImpl implements IConceptoLaboralService {
    @Autowired
    private IConceptoLaboralRepository conceptoLaboralRepository;

    @Override
    public List<ConceptoLaboralResponse> obtenerConceptosLaborales(Long id, String nombre) {
        List<ConceptoLaboral> conceptos;

        if (id != null && nombre != null) {
            conceptos = conceptoLaboralRepository.findAll().stream()
                    .filter(c -> c.getId().equals(id) && c.getNombre().contains(nombre))
                    .collect(Collectors.toList());
        } else if (id != null) {
            conceptos = conceptoLaboralRepository.findById(id).map(List::of).orElse(List.of());
        } else if (nombre != null) {
            conceptos = conceptoLaboralRepository.findByNombreContaining(nombre);
        } else {
            conceptos = conceptoLaboralRepository.findAll();
        }

        // Mapear la lista de ConceptoLaboral a ConceptoLaboralResponse
        return conceptos.stream()
                .map(this::convertirAConceptoLaboralResponse)
                .collect(Collectors.toList());
    }

    private ConceptoLaboralResponse convertirAConceptoLaboralResponse(ConceptoLaboral conceptoLaboral) {
        ConceptoLaboralResponse response = new ConceptoLaboralResponse();
        response.setId(conceptoLaboral.getId());
        response.setNombre(conceptoLaboral.getNombre());
        response.setHsMinimo(conceptoLaboral.getHsMinimo());
        response.setHsMaximo(conceptoLaboral.getHsMaximo());
        response.setLaborable(conceptoLaboral.getLaborable());
        return response;
    }
}
