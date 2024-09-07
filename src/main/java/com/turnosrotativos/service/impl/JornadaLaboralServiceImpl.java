package com.turnosrotativos.service.impl;

import com.turnosrotativos.dto.JornadaLaboralDTO;
import com.turnosrotativos.dto.JornadaLaboralResponse;
import com.turnosrotativos.entity.ConceptoLaboral;
import com.turnosrotativos.entity.Empleado;
import com.turnosrotativos.entity.JornadaLaboral;
import com.turnosrotativos.repository.IJornadaLaboralRepository;
import com.turnosrotativos.service.IJornadaLaboralService;
import com.turnosrotativos.util.JornadaLaboralHelperService;
import com.turnosrotativos.validator.JornadaLaboralValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @PersistenceContext
    private EntityManager entityManager;

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

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<JornadaLaboral> cq = cb.createQuery(JornadaLaboral.class);
        Root<JornadaLaboral> jornada = cq.from(JornadaLaboral.class);

        List<Predicate> predicates = new ArrayList<>();

        if (nroDocumento != null) {
            Long empleadoId = jornadaLaboralHelperService.getEmpleadoByNroDocumento(nroDocumento).getId();
            predicates.add(cb.equal(jornada.get("empleado").get("id"), empleadoId));
        }

        if (fechaDesde != null) {
            predicates.add(cb.greaterThanOrEqualTo(jornada.get("fecha"), fechaDesde));
        }

        if (fechaHasta != null) {
            predicates.add(cb.lessThanOrEqualTo(jornada.get("fecha"), fechaHasta));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        List<JornadaLaboral> jornadas = entityManager.createQuery(cq).getResultList();

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
