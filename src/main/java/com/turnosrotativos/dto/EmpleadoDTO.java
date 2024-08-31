package com.turnosrotativos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoDTO {
    private Long id;
    @NotNull(message = "Es necesario ingresar su DNI")
    private Long nroDocumento;
    @NotBlank(message = "Es necesario ingresar su nombre")
    private String nombre;
    @NotBlank(message = "Es necesario ingresar su apellido")
    private String apellido;
    @NotBlank(message = "Es necesario ingresar su Email")
    private String email;
    @NotNull(message = "Es necesario ingresar su fecha de nacimiento")
    private LocalDate fechaNacimiento;
    @NotNull(message = "Es necesario ingresar su fecha de ingreso")
    private LocalDate fechaIngreso;
    private LocalDate fechaCreacion;
}
