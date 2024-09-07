package com.turnosrotativos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class JornadaLaboralRequest {
    
    @Positive(message = "El campo ‘nroDocumento’ solo puede contener números enteros.")
    private String nroDocumento;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Los campos ‘fechaDesde’ y ‘fechaHasta’ deben respetar el formato yyyy-mm-dd.")
    private String fechaDesde;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Los campos ‘fechaDesde’ y ‘fechaHasta’ deben respetar el formato yyyy-mm-dd.")
    private String fechaHasta;
}
