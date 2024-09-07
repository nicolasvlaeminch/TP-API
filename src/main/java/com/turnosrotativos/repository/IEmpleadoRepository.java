package com.turnosrotativos.repository;

import com.turnosrotativos.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {
    boolean existsByNroDocumento(Long nroDocumento);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    Optional<Empleado> findByNroDocumento(Long nroDocumento);
}
