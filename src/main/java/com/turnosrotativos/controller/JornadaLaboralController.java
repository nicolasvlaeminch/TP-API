package com.turnosrotativos.controller;

import com.turnosrotativos.dto.JornadaLaboralDTO;
import com.turnosrotativos.dto.JornadaLaboralResponseDTO;
import com.turnosrotativos.service.IJornadaLaboralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jornada")
public class JornadaLaboralController {
    @Autowired
    private IJornadaLaboralService jornadaLaboralService;

    @PostMapping
    public ResponseEntity<JornadaLaboralResponseDTO> crearJornadaLaboral(@Valid @RequestBody JornadaLaboralDTO jornadaDTO) {
        JornadaLaboralResponseDTO nuevaJornadaDTO = jornadaLaboralService.crearJornadaLaboral(jornadaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaJornadaDTO);
    }
}
