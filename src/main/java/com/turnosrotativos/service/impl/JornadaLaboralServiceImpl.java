package com.turnosrotativos.service.impl;

import com.turnosrotativos.dto.JornadaLaboralDTO;
import com.turnosrotativos.dto.JornadaLaboralResponse;
import com.turnosrotativos.entity.ConceptoLaboral;
import com.turnosrotativos.entity.Empleado;
import com.turnosrotativos.entity.JornadaLaboral;
import com.turnosrotativos.exceptions.BusinessException;
import com.turnosrotativos.repository.IJornadaLaboralRepository;
import com.turnosrotativos.service.IJornadaLaboralService;
import com.turnosrotativos.util.JornadaLaboralHelperService;
import com.turnosrotativos.validator.JornadaLaboralValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JornadaLaboralServiceImpl implements IJornadaLaboralService {
    @Autowired
    private IJornadaLaboralRepository jornadaLaboralRepository;

    @Autowired
    private JornadaLaboralValidator jornadaLaboralValidator;

    @Autowired
    private JornadaLaboralHelperService jornadaLaboralHelperService;

    @Override
    public JornadaLaboralResponse crearJornadaLaboral(JornadaLaboralDTO jornadaDTO) {
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

    @Override
    public List<JornadaLaboralResponse> obtenerJornadasLaborales(String nroDocumentoStr, String fechaDesdeStr, String fechaHastaStr) {
        Long nroDocumento = (nroDocumentoStr != null) ? Long.parseLong(nroDocumentoStr) : null;
        LocalDate fechaDesde = (fechaDesdeStr != null) ? LocalDate.parse(fechaDesdeStr) : null;
        LocalDate fechaHasta = (fechaHastaStr != null) ? LocalDate.parse(fechaHastaStr) : null;

        jornadaLaboralValidator.validarParametros(fechaDesde, fechaHasta);
        List<JornadaLaboral> jornadas;

        if (nroDocumento != null) {
            Long empleadoId = jornadaLaboralHelperService.getEmpleadoByNroDocumento(nroDocumento).getId();

            if (fechaDesde != null && fechaHasta != null) {
                jornadas = jornadaLaboralRepository.findByEmpleadoIdAndFechaBetween(empleadoId, fechaDesde, fechaHasta);
            } else if (fechaDesde != null) {
                jornadas = jornadaLaboralRepository.findByEmpleadoIdAndFechaGreaterThanEqual(empleadoId, fechaDesde);
            } else if (fechaHasta != null) {
                jornadas = jornadaLaboralRepository.findByEmpleadoIdAndFechaLessThanEqual(empleadoId, fechaHasta);
            } else {
                jornadas = jornadaLaboralRepository.findByEmpleadoId(empleadoId);
            }
        } else if (fechaDesde != null && fechaHasta != null) {
            jornadas = jornadaLaboralRepository.findByFechaBetween(fechaDesde, fechaHasta);
        } else if (fechaDesde != null) {
            jornadas = jornadaLaboralRepository.findByFechaGreaterThanEqual(fechaDesde);
        } else if (fechaHasta != null) {
            jornadas = jornadaLaboralRepository.findByFechaLessThanEqual(fechaHasta);
        } else {
            jornadas = jornadaLaboralRepository.findAll();
        }
        return jornadas.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    private JornadaLaboralResponse convertToResponseDTO(JornadaLaboral jornada) {
        JornadaLaboralResponse responseDTO = new JornadaLaboralResponse();
        responseDTO.setId(jornada.getId());
        responseDTO.setNroDocumento(jornada.getEmpleado().getNroDocumento());
        responseDTO.setNombreCompleto(jornada.getEmpleado().getNombre() + " " + jornada.getEmpleado().getApellido());
        responseDTO.setFecha(jornada.getFecha());
        responseDTO.setConcepto(jornada.getConcepto().getNombre());
        responseDTO.setHsTrabajadas(jornada.getHorasTrabajadas());
        return responseDTO;
    }
}
