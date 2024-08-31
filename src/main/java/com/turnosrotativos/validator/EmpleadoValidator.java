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

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    private static final Pattern LETTERS_ONLY_PATTERN = Pattern.compile("^[a-zA-Z]+$");

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

    public boolean esFechaIngresoValida(LocalDate fechaIngreso) {
        return !fechaIngreso.isAfter(LocalDate.now());
    }

    public boolean esFechaNacimientoValida(LocalDate fechaNacimiento) {
        return !fechaNacimiento.isAfter(LocalDate.now());
    }

    public boolean esEmailValido(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean esNombreValido(String nombre) {
        return nombre != null && LETTERS_ONLY_PATTERN.matcher(nombre).matches();
    }

    public boolean esApellidoValido(String apellido) {
        return apellido != null && LETTERS_ONLY_PATTERN.matcher(apellido).matches();
    }

    public boolean esCampoObligatorio(String campo) {
        return campo != null && !campo.trim().isEmpty();
    }

    public boolean esCampoObligatorio(Long campo) {
        return campo != null;
    }

    public boolean esCampoObligatorio(LocalDate campo) {
        return campo != null;
    }
}
