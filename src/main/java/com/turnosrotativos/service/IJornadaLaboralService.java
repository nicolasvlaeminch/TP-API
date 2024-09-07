package com.turnosrotativos.service;

import com.turnosrotativos.dto.JornadaLaboralDTO;
import com.turnosrotativos.dto.JornadaLaboralResponse;

import java.time.LocalDate;
import java.util.List;

public interface IJornadaLaboralService {
    JornadaLaboralResponse crearJornadaLaboral(JornadaLaboralDTO jornadaDTO);

    List<JornadaLaboralResponse> obtenerJornadasLaborales(Long nroDocumento, LocalDate fechaDesde, LocalDate fechaHasta);
}
