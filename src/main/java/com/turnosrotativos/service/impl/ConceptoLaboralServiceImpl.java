package com.turnosrotativos.service.impl;

import com.turnosrotativos.entity.ConceptoLaboral;
import com.turnosrotativos.repository.IConceptoLaboralRepository;
import com.turnosrotativos.service.IConceptoLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConceptoLaboralServiceImpl implements IConceptoLaboralService {
    @Autowired
    private IConceptoLaboralRepository conceptoLaboralRepository;

    @Override
    public List<ConceptoLaboral> obtenerConceptosLaborales(Long id, String nombre) {
        if (id != null && nombre != null) {
            return conceptoLaboralRepository.findAll().stream()
                    .filter(c -> c.getId().equals(id) && c.getNombre().contains(nombre))
                    .toList();
        } else if (id != null) {
            return conceptoLaboralRepository.findById(id).map(List::of).orElse(List.of());
        } else if (nombre != null) {
            return conceptoLaboralRepository.findByNombreContaining(nombre);
        } else {
            return conceptoLaboralRepository.findAll();
        }
    }
}
