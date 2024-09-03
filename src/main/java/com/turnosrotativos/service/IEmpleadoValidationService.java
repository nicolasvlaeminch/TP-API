package com.turnosrotativos.service;

import com.turnosrotativos.dto.EmpleadoDTO;

public interface IEmpleadoValidationService {
    void validarEmpleado(EmpleadoDTO empleadoDTO);
    void validarEmpleadoPorId(Long idEmpleado);
}
