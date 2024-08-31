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

        // Validación de fecha de nacimiento
        if (!empleadoValidator.esFechaNacimientoValida(empleadoDTO.getFechaNacimiento())) {
            throw new BusinessException("La fecha de nacimiento no puede ser posterior al día de la fecha.", HttpStatus.BAD_REQUEST);
        }

        // Validación de fecha de ingreso
        if (!empleadoValidator.esFechaIngresoValida(empleadoDTO.getFechaIngreso())) {
            throw new BusinessException("La fecha de ingreso no puede ser posterior al día de la fecha.", HttpStatus.BAD_REQUEST);
        }

        // Validación de email
        if (!empleadoValidator.esEmailValido(empleadoDTO.getEmail())) {
            throw new BusinessException("El email ingresado no es correcto.", HttpStatus.BAD_REQUEST);
        }

        // Validación de campos obligatorios
        if (!empleadoValidator.esCampoObligatorio(empleadoDTO.getNombre())) {
            throw new BusinessException("‘nombre’ es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (!empleadoValidator.esCampoObligatorio(empleadoDTO.getApellido())) {
            throw new BusinessException("‘apellido’ es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (!empleadoValidator.esCampoObligatorio(empleadoDTO.getEmail())) {
            throw new BusinessException("‘email’ es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (!empleadoValidator.esCampoObligatorio(empleadoDTO.getNroDocumento())) {
            throw new BusinessException("‘nroDocumento’ es obligatorio.", HttpStatus.BAD_REQUEST);
        }
        if (!empleadoValidator.esCampoObligatorio(empleadoDTO.getFechaNacimiento())) {
            throw new BusinessException("‘fechaNacimiento’ es obligatoria.", HttpStatus.BAD_REQUEST);
        }
        if (!empleadoValidator.esCampoObligatorio(empleadoDTO.getFechaIngreso())) {
            throw new BusinessException("‘fechaIngreso’ es obligatoria.", HttpStatus.BAD_REQUEST);
        }

        // Validación de nombre y apellido
        if (!empleadoValidator.esNombreValido(empleadoDTO.getNombre())) {
            throw new BusinessException("Solo se permiten letras en el campo ‘nombre’.", HttpStatus.BAD_REQUEST);
        }

        if (!empleadoValidator.esApellidoValido(empleadoDTO.getApellido())) {
            throw new BusinessException("Solo se permiten letras en el campo ‘apellido’.", HttpStatus.BAD_REQUEST);
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
