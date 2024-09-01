package com.turnosrotativos.validator;

import com.turnosrotativos.repository.IEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

@Component
public class EmpleadoValidator {
    private final IEmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoValidator(IEmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public boolean esMenorDeEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18;
    }

    public boolean existeNroDocumento(Long nroDocumento) {
        return empleadoRepository.existsByNroDocumento(nroDocumento);
    }

    public boolean existeEmail(String email) {
        return empleadoRepository.existsByEmail(email);
    }
}
