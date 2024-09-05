package com.turnosrotativos.service;

import com.turnosrotativos.dto.JornadaLaboralDTO;
import com.turnosrotativos.dto.JornadaLaboralResponseDTO;

public interface IJornadaLaboralService {
    JornadaLaboralResponseDTO crearJornadaLaboral(JornadaLaboralDTO jornadaDTO);
}
