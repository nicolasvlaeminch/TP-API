package com.turnosrotativos.validator;

import com.turnosrotativos.dto.EmpleadoDTO;
import com.turnosrotativos.exceptions.BusinessException;
import com.turnosrotativos.repository.IEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class EmpleadoValidator {
    @Autowired
    private IEmpleadoRepository empleadoRepository;

    public void validarRegistrarEmpleado(EmpleadoDTO empleadoDTO) {
        validarEdad(empleadoDTO.getFechaNacimiento());
        validarNumeroDocumento(empleadoDTO.getNroDocumento());
        validarEmail(empleadoDTO.getEmail());
    }

    public void validarActualizarEmpleado(EmpleadoDTO empleadoDTO) {
        validarEdad(empleadoDTO.getFechaNacimiento());
    }

    private void validarEdad(LocalDate fechaNacimiento) {
        if (Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18) {
            throw new BusinessException("La edad del empleado no puede ser menor a 18 años.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validarNumeroDocumento(Long nroDocumento) {
        if (empleadoRepository.existsByNroDocumento(nroDocumento)) {
            throw new BusinessException("Ya existe un empleado con el documento ingresado.", HttpStatus.CONFLICT);
        }
    }

    private void validarEmail(String email) {
        if (empleadoRepository.existsByEmail(email)) {
            throw new BusinessException("Ya existe un empleado con el email ingresado.", HttpStatus.CONFLICT);
        }
    }

    public void validarEmpleadoPorId(Long idEmpleado) {
        if (!empleadoRepository.existsById(idEmpleado)) {
            throw new BusinessException("No se encontró el empleado con Id: " + idEmpleado, HttpStatus.NOT_FOUND);
        }
    }
}
