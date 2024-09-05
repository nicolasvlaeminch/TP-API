package com.turnosrotativos.repository;

import com.turnosrotativos.entity.JornadaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IJornadaLaboralRepository extends JpaRepository<JornadaLaboral, Long> {
    List<JornadaLaboral> findByEmpleadoIdAndFecha(Long empleadoId, LocalDate fecha);
    List<JornadaLaboral> findByEmpleadoIdAndFechaBetween(Long empleadoId, LocalDate start, LocalDate end);
    boolean existsByFechaAndConceptoNombre(LocalDate fecha, String conceptoNombre);
}
