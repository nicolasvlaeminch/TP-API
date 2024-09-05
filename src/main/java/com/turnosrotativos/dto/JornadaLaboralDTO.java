package com.turnosrotativos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JornadaLaboralDTO {
    private Long id;

    @NotNull(message = "'idEmpleado' es obligatorio.")
    private Long idEmpleado;

    @NotNull(message = "'idConcepto' es obligatorio.")
    private Long idConcepto;

    @NotNull(message = "'fecha' es obligatoria.")
    private LocalDate fecha;

    private Integer horasTrabajadas;
}
