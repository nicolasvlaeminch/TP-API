package com.turnosrotativos.service.impl;

import com.turnosrotativos.dto.JornadaLaboralDTO;
import com.turnosrotativos.dto.JornadaLaboralResponseDTO;
import com.turnosrotativos.entity.ConceptoLaboral;
import com.turnosrotativos.entity.Empleado;
import com.turnosrotativos.entity.JornadaLaboral;
import com.turnosrotativos.repository.IJornadaLaboralRepository;
import com.turnosrotativos.service.IJornadaLaboralService;
import com.turnosrotativos.util.JornadaLaboralHelperService;
import com.turnosrotativos.validator.JornadaLaboralValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JornadaLaboralServiceImpl implements IJornadaLaboralService {
    @Autowired
    private IJornadaLaboralRepository jornadaLaboralRepository;

    @Autowired
    private JornadaLaboralValidator jornadaLaboralValidator;

    @Autowired
    private JornadaLaboralHelperService jornadaLaboralHelperService;

    @Override
    public JornadaLaboralResponseDTO crearJornadaLaboral(JornadaLaboralDTO jornadaDTO) {
        jornadaLaboralValidator.validarJornadaLaboral(jornadaDTO);

        Empleado empleado = jornadaLaboralHelperService.getEmpleadoById(jornadaDTO.getIdEmpleado());
        ConceptoLaboral concepto = jornadaLaboralHelperService.getConceptoById(jornadaDTO.getIdConcepto());

        JornadaLaboral jornada = JornadaLaboral.builder()
                .empleado(empleado)
                .concepto(concepto)
                .fecha(jornadaDTO.getFecha())
                .horasTrabajadas(jornadaDTO.getHorasTrabajadas())
                .build();

        JornadaLaboral nuevaJornada = jornadaLaboralRepository.save(jornada);

        return convertToResponseDTO(nuevaJornada);
    }

    // Método para convertir la entidad JornadaLaboral a DTO de respuesta
    private JornadaLaboralResponseDTO convertToResponseDTO(JornadaLaboral jornada) {
        JornadaLaboralResponseDTO responseDTO = new JornadaLaboralResponseDTO();
        responseDTO.setId(jornada.getId());
        responseDTO.setNroDocumento(jornada.getEmpleado().getNroDocumento());
        responseDTO.setNombreCompleto(jornada.getEmpleado().getNombre() + " " + jornada.getEmpleado().getApellido());
        responseDTO.setFecha(jornada.getFecha());
        responseDTO.setConcepto(jornada.getConcepto().getNombre());
        responseDTO.setHsTrabajadas(jornada.getHorasTrabajadas());
        return responseDTO;
    }
}
