package com.turnosrotativos.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "concepto_laboral")
public class ConceptoLaboral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "hs_minimo")
    private Integer hsMinimo;

    @Column(name = "hs_maximo")
    private Integer hsMaximo;

    private Boolean laborable;
}
