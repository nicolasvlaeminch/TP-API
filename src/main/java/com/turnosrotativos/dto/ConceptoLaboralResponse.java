package com.turnosrotativos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConceptoLaboralResponse {
    private Long id;

    private String nombre;

    private Integer hsMinimo;

    private Integer hsMaximo;

    private Boolean laborable;
}
