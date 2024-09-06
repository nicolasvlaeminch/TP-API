package com.turnosrotativos.repository;

import com.turnosrotativos.entity.JornadaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IJornadaLaboralRepository extends JpaRepository<JornadaLaboral, Long> {
    List<JornadaLaboral> findByEmpleadoIdAndFecha(Long empleadoId, LocalDate fecha);
    List<JornadaLaboral> findByEmpleadoIdAndFechaBetween(Long empleadoId, LocalDate start, LocalDate end);
    boolean existsByFechaAndConceptoNombreAndEmpleadoId(LocalDate fecha, String conceptoNombre, Long empleadoId);
    long countByFechaAndConceptoId(LocalDate fecha, Long conceptoId);

    // Consulta por rango de fechas
    List<JornadaLaboral> findByFechaBetween(LocalDate start, LocalDate end);

    // Consulta por fecha desde
    List<JornadaLaboral> findByFechaGreaterThanEqual(LocalDate fechaDesde);

    // Consulta por fecha hasta
    List<JornadaLaboral> findByFechaLessThanEqual(LocalDate fechaHasta);

    List<JornadaLaboral> findByEmpleadoIdAndFechaLessThanEqual(Long empleadoId, LocalDate fechaHasta);

    List<JornadaLaboral> findByEmpleadoIdAndFechaGreaterThanEqual(Long empleadoId, LocalDate fechaDesde);
    List<JornadaLaboral> findByEmpleadoId(Long empleadoId);
}
