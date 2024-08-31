package com.turnosrotativos.controller;

import com.turnosrotativos.dto.EmpleadoDTO;
import com.turnosrotativos.service.IEmpleadoService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
//@RequestMapping("/api/v1")
public class EmpleadoController {

    @Autowired
    private IEmpleadoService empleadoService;

    @RequestMapping(value = "/empleado", method = RequestMethod.POST)
    public ResponseEntity<EmpleadoDTO> registrarEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {

        EmpleadoDTO nuevoEmpleadoDTO = empleadoService.registrarEmpleado(empleadoDTO);
        return ResponseEntity.created(URI.create("/empleado/" + nuevoEmpleadoDTO.getId())).body(nuevoEmpleadoDTO);
    }

}
