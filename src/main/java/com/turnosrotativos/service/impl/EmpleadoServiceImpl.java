package com.turnosrotativos.service.impl;

import com.turnosrotativos.dto.EmpleadoDTO;
import com.turnosrotativos.entity.Empleado;
import com.turnosrotativos.exceptions.BusinessException;
import com.turnosrotativos.repository.IEmpleadoRepository;
import com.turnosrotativos.service.IEmpleadoService;
import com.turnosrotativos.validator.EmpleadoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {
    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoValidator empleadoValidator;

    public EmpleadoDTO registrarEmpleado(EmpleadoDTO empleadoDTO) {
        if (empleadoValidator.esMenorDeEdad(empleadoDTO.getFechaNacimiento())) {
            throw new BusinessException("La edad del empleado no puede ser menor a 18 años.", HttpStatus.BAD_REQUEST);
        }

        if (empleadoValidator.existeNroDocumento(empleadoDTO.getNroDocumento())) {
            throw new BusinessException("Ya existe un empleado con el documento ingresado.", HttpStatus.CONFLICT);
        }

        if (empleadoValidator.existeEmail(empleadoDTO.getEmail())) {
            throw new BusinessException("Ya existe un empleado con el email ingresado.", HttpStatus.CONFLICT);
        }

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
