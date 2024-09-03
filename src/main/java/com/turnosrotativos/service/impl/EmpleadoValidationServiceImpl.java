package com.turnosrotativos.service.impl;

import com.turnosrotativos.dto.EmpleadoDTO;
import com.turnosrotativos.exceptions.BusinessException;
import com.turnosrotativos.repository.IEmpleadoRepository;
import com.turnosrotativos.service.IEmpleadoValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class EmpleadoValidationServiceImpl implements IEmpleadoValidationService {
    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Override
    public void validarEmpleado(EmpleadoDTO empleadoDTO) {
        validarEdad(empleadoDTO.getFechaNacimiento());
        validarNumeroDocumento(empleadoDTO.getNroDocumento());
        validarEmail(empleadoDTO.getEmail());
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
