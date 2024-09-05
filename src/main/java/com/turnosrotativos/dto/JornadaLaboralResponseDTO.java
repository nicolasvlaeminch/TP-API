package com.turnosrotativos.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JornadaLaboralResponseDTO {
    private Long id;
    private Long nroDocumento;
    private String nombreCompleto;
    private LocalDate fecha;
    private String concepto;
    private Integer hsTrabajadas;
}
