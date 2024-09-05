package com.turnosrotativos.service;

import com.turnosrotativos.dto.EmpleadoDTO;

public interface IEmpleadoValidationService {
    void validarRegistrarEmpleado(EmpleadoDTO empleadoDTO);
    void validarActualizarEmpleado(EmpleadoDTO empleadoDTO);
    void validarEmpleadoPorId(Long idEmpleado);
}
