package com.turnosrotativos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoDTO {
    private Long id;

    @NotNull(message = "‘nroDocumento’ es obligatorio.")
    private Long nroDocumento;

    @NotBlank(message = "‘nombre’ es obligatorio.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Solo se permiten letras en el campo ‘nombre’.")
    private String nombre;

    @NotBlank(message = "‘apellido’ es obligatorio.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Solo se permiten letras en el campo ‘apellido’.")
    private String apellido;

    @NotBlank(message = "‘email’ es obligatorio.")
    @Email(message = "El email ingresado no es correcto.")
    private String email;

    @NotNull(message = "‘fechaNacimiento’ es obligatorio.")
    @Past(message = "La fecha de nacimiento no puede ser posterior al día de la fecha.")
    private LocalDate fechaNacimiento;

    @NotNull(message = "‘fechaIngreso’ es obligatorio.")
    @Past(message = "La fecha de ingreso no puede ser posterior al día de la fecha.")
    private LocalDate fechaIngreso;

    private LocalDate fechaCreacion;
}
