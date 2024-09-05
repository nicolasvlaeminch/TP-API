package com.turnosrotativos.controller;

import com.turnosrotativos.entity.ConceptoLaboral;
import com.turnosrotativos.service.IConceptoLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/concepto")
public class ConceptoLaboralController {
    @Autowired
    private IConceptoLaboralService conceptoLaboralService;

    @GetMapping
    public ResponseEntity<List<ConceptoLaboral>> obtenerConceptosLaborales(
            @RequestParam(required = false) Long id, @RequestParam(required = false) String nombre) {

        List<ConceptoLaboral> conceptos = conceptoLaboralService.obtenerConceptosLaborales(id, nombre);

        return ResponseEntity.ok(conceptos);
    }
}
