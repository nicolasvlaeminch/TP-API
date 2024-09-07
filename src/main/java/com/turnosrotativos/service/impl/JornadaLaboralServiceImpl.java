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
    public List<JornadaLaboralResponse> obtenerJornadasLaborales(Long nroDocumento, LocalDate fechaDesde, LocalDate fechaHasta) {
        jornadaLaboralValidator.validarParametros(fechaDesde, fechaHasta);
        List<JornadaLaboral> jornadas;

        // Si se proporciona un número de documento
        if (nroDocumento != null) {
            // Obtén el ID del empleado basado en el número de documento
            Long empleadoId = jornadaLaboralHelperService.getEmpleadoByNroDocumento(nroDocumento).getId();

            // Filtra las jornadas basadas en el ID del empleado y las fechas proporcionadas (si están presentes)
            if (fechaDesde != null && fechaHasta != null) {
                // Busca jornadas del empleado dentro del rango de fechas especificado
                jornadas = jornadaLaboralRepository.findByEmpleadoIdAndFechaBetween(empleadoId, fechaDesde, fechaHasta);
            } else if (fechaDesde != null) {
                // Busca jornadas del empleado a partir de la fecha desde
                jornadas = jornadaLaboralRepository.findByEmpleadoIdAndFechaGreaterThanEqual(empleadoId, fechaDesde);
            } else if (fechaHasta != null) {
                // Busca jornadas del empleado hasta la fecha hasta
                jornadas = jornadaLaboralRepository.findByEmpleadoIdAndFechaLessThanEqual(empleadoId, fechaHasta);
            } else {
                // Si no se proporcionan fechas, busca todas las jornadas del empleado
                jornadas = jornadaLaboralRepository.findByEmpleadoId(empleadoId);
            }
        } else if (fechaDesde != null && fechaHasta != null) {
            // Si no se proporciona número de documento pero se proporcionan fechas, busca todas las jornadas dentro del rango de fechas
            jornadas = jornadaLaboralRepository.findByFechaBetween(fechaDesde, fechaHasta);
        } else if (fechaDesde != null) {
            // Si no se proporciona número de documento pero se proporciona fecha desde, busca todas las jornadas a partir de la fecha desde
            jornadas = jornadaLaboralRepository.findByFechaGreaterThanEqual(fechaDesde);
        } else if (fechaHasta != null) {
            // Si no se proporciona número de documento pero se proporciona fecha hasta, busca todas las jornadas hasta la fecha hasta
            jornadas = jornadaLaboralRepository.findByFechaLessThanEqual(fechaHasta);
        } else {
            // Si no se proporciona ni número de documento ni fechas, busca todas las jornadas
            jornadas = jornadaLaboralRepository.findAll();
        }

        // Convierte la lista de jornadas a una lista de DTOs y la retorna
        return jornadas.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    // Método para convertir la entidad JornadaLaboral a DTO de respuesta
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
