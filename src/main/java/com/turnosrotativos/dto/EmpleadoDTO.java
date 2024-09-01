package com.turnosrotativos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
    @Email(message = "“El email ingresado no es correcto.”")
    private String email;
    @NotNull(message = "Es necesario ingresar su fecha de nacimiento")
    @Past(message = "“La fecha de nacimiento no puede ser posterior al día de la fecha.”")
    private LocalDate fechaNacimiento;
    @NotNull(message = "Es necesario ingresar su fecha de ingreso")
    @Past(message = "“La fecha de ingreso no puede ser posterior al día de la fecha.”")
    private LocalDate fechaIngreso;
    private LocalDate fechaCreacion;
}
