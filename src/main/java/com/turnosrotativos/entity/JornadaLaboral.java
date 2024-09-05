package com.turnosrotativos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "jornadas_laborales")
public class JornadaLaboral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "concepto_laboral_id", nullable = false)
    private ConceptoLaboral concepto;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "horas_trabajadas")
    private Integer horasTrabajadas;
}
