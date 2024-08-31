package com.turnosrotativos.repository;

import com.turnosrotativos.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {
    boolean existsByNroDocumento(long nroDocumento);
    boolean existsByEmail(String email);

}
