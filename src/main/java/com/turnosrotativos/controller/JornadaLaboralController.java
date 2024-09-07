package com.turnosrotativos.controller;

import com.turnosrotativos.dto.JornadaLaboralDTO;
import com.turnosrotativos.dto.JornadaLaboralRequest;
import com.turnosrotativos.dto.JornadaLaboralResponse;
import com.turnosrotativos.service.IJornadaLaboralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jornada")
public class JornadaLaboralController {
    @Autowired
    private IJornadaLaboralService jornadaLaboralService;

    @PostMapping
    public ResponseEntity<JornadaLaboralResponse> crearJornadaLaboral(@Valid @RequestBody JornadaLaboralDTO jornadaDTO) {
        JornadaLaboralResponse nuevaJornadaDTO = jornadaLaboralService.crearJornadaLaboral(jornadaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaJornadaDTO);
    }

    @GetMapping
    public ResponseEntity<List<JornadaLaboralResponse>> obtenerJornadasLaborales(@Valid JornadaLaboralRequest jornadaLaboralRequest) {
        List<JornadaLaboralResponse> jornadas = jornadaLaboralService.obtenerJornadasLaborales(
                jornadaLaboralRequest.getNroDocumento(), jornadaLaboralRequest.getFechaDesde(), jornadaLaboralRequest.getFechaHasta());
        return ResponseEntity.ok(jornadas);
    }
}
