package com.turnosrotativos.util;

import com.turnosrotativos.entity.ConceptoLaboral;
import com.turnosrotativos.entity.Empleado;
import com.turnosrotativos.exceptions.BusinessException;
import com.turnosrotativos.repository.IConceptoLaboralRepository;
import com.turnosrotativos.repository.IEmpleadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class JornadaLaboralHelperService {
    private final IEmpleadoRepository empleadoRepository;
    private final IConceptoLaboralRepository conceptoLaboralRepository;

    public JornadaLaboralHelperService(IEmpleadoRepository empleadoRepository,
                                       IConceptoLaboralRepository conceptoLaboralRepository) {
        this.empleadoRepository = empleadoRepository;
        this.conceptoLaboralRepository = conceptoLaboralRepository;
    }

    public Empleado getEmpleadoById(Long idEmpleado) {
        return empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new BusinessException("No existe el empleado ingresado.", HttpStatus.NOT_FOUND));
    }

    public ConceptoLaboral getConceptoById(Long idConcepto) {
        return conceptoLaboralRepository.findById(idConcepto)
                .orElseThrow(() -> new BusinessException("No existe el concepto ingresado.", HttpStatus.NOT_FOUND));
    }

    public Empleado getEmpleadoByNroDocumento(Long nroDocumento) {
        return empleadoRepository.findByNroDocumento(nroDocumento)
                .orElseThrow(() -> new BusinessException("No existe el empleado con el número de documento ingresado.", HttpStatus.NOT_FOUND));
    }
}
