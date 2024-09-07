package com.turnosrotativos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoDTO {
    private Long id;

    @NotNull(message = "Es necesario ingresar su DNI")
    private Long nroDocumento;

    @NotBlank(message = "Es necesario ingresar su nombre")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Solo se permiten letras en el campo ‘nombre’.")
    private String nombre;

    @NotBlank(message = "Es necesario ingresar su apellido")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Solo se permiten letras en el campo ‘apellido’.")
    private String apellido;

    @NotBlank(message = "Es necesario ingresar su Email")
    @Email(message = "El email ingresado no es correcto.")
    private String email;

    @NotNull(message = "Es necesario ingresar su fecha de nacimiento")
    @Past(message = "La fecha de nacimiento no puede ser posterior al día de la fecha.")
    private LocalDate fechaNacimiento;

    @NotNull(message = "Es necesario ingresar su fecha de ingreso")
    @Past(message = "La fecha de ingreso no puede ser posterior al día de la fecha.")
    private LocalDate fechaIngreso;

    private LocalDate fechaCreacion;
}
