package com.turnosrotativos.service.impl;

import com.turnosrotativos.dto.EmpleadoDTO;
import com.turnosrotativos.entity.Empleado;
import com.turnosrotativos.exceptions.BusinessException;
import com.turnosrotativos.repository.IEmpleadoRepository;
import com.turnosrotativos.service.IEmpleadoService;
import com.turnosrotativos.service.IEmpleadoValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {
    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private IEmpleadoValidationService empleadoValidationService;

    public EmpleadoDTO registrarEmpleado(EmpleadoDTO empleadoDTO) {
        empleadoValidationService.validarEmpleado(empleadoDTO);

        Empleado empleado = new Empleado();
        empleado.setNroDocumento(empleadoDTO.getNroDocumento());
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setApellido(empleadoDTO.getApellido());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleado.setFechaIngreso(empleadoDTO.getFechaIngreso());
        empleado.setFechaCreacion(LocalDate.now());

        Empleado nuevoEmpleado = empleadoRepository.save(empleado);

        return convertToDTO(nuevoEmpleado);
    }

    public List<EmpleadoDTO> obtenerEmpleados() {
        List<Empleado> empleados = empleadoRepository.findAll();

        return empleados.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EmpleadoDTO obtenerEmpleadoPorId(Long empleadoId) {
        empleadoValidationService.validarEmpleadoPorId(empleadoId);

        return empleadoRepository.findById(empleadoId).map(this::convertToDTO).orElse(null);
    }

    @Override
    public EmpleadoDTO actualizarEmpleado(Long empleadoId, EmpleadoDTO empleadoDTO) {
        // Recuperar el empleado existente
        Empleado empleadoExistente = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new BusinessException("Empleado no encontrado con ID: " + empleadoId, HttpStatus.NOT_FOUND));

        // Validar datos actualizados (solo si cambian)
        empleadoValidationService.validarEmpleado(empleadoDTO);

        // Actualizar los campos del empleado
        empleadoExistente.setNroDocumento(empleadoDTO.getNroDocumento());
        empleadoExistente.setNombre(empleadoDTO.getNombre());
        empleadoExistente.setApellido(empleadoDTO.getApellido());
        empleadoExistente.setEmail(empleadoDTO.getEmail());
        empleadoExistente.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleadoExistente.setFechaIngreso(empleadoDTO.getFechaIngreso());

        // Guardar los cambios
        Empleado empleadoActualizado = empleadoRepository.save(empleadoExistente);

        return convertToDTO(empleadoActualizado);
    }

    private EmpleadoDTO convertToDTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setNroDocumento(empleado.getNroDocumento());
        dto.setNombre(empleado.getNombre());
        dto.setApellido(empleado.getApellido());
        dto.setEmail(empleado.getEmail());
        dto.setFechaNacimiento(empleado.getFechaNacimiento());
        dto.setFechaIngreso(empleado.getFechaIngreso());
        dto.setFechaCreacion(empleado.getFechaCreacion());

        return dto;
    }
}
