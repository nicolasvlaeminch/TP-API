package com.turnosrotativos.service;

import com.turnosrotativos.dto.EmpleadoDTO;

import java.util.List;

public interface IEmpleadoService {
    EmpleadoDTO registrarEmpleado(EmpleadoDTO empleadoDTO);
    List<EmpleadoDTO> obtenerEmpleados();
    EmpleadoDTO obtenerEmpleadoPorId(Long empleadoId);
    EmpleadoDTO actualizarEmpleado(Long empleadoId, EmpleadoDTO empleadoDTO);
}
