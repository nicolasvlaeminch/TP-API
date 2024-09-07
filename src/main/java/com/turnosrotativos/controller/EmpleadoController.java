package com.turnosrotativos.controller;

import com.turnosrotativos.dto.EmpleadoDTO;
import com.turnosrotativos.service.IEmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    @Autowired
    private IEmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<EmpleadoDTO> registrarEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoDTO nuevoEmpleadoDTO = empleadoService.registrarEmpleado(empleadoDTO);
        return ResponseEntity.created(URI.create("/empleado/" + nuevoEmpleadoDTO.getId())).body(nuevoEmpleadoDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> obtenerEmpleados() {
        List<EmpleadoDTO> empleadosDTO = empleadoService.obtenerEmpleados();
        return ResponseEntity.ok(empleadosDTO);
    }

    @GetMapping("/{empleadoId}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleadoPorId(@PathVariable Long empleadoId) {
        EmpleadoDTO empleadoDTO = empleadoService.obtenerEmpleadoPorId(empleadoId);
        return ResponseEntity.ok(empleadoDTO);
    }

    @PutMapping("/{empleadoId}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleado(
            @PathVariable Long empleadoId,
            @Valid @RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoDTO empleadoActualizadoDTO = empleadoService.actualizarEmpleado(empleadoId, empleadoDTO);
        return ResponseEntity.ok(empleadoActualizadoDTO);
    }

    @DeleteMapping("/{empleadoId}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long empleadoId) {
        empleadoService.eliminarEmpleadoPorId(empleadoId);
        return ResponseEntity.noContent().build();
    }
}
