package com.turnosrotativos.validator;

import com.turnosrotativos.dto.JornadaLaboralDTO;
import com.turnosrotativos.entity.ConceptoLaboral;
import com.turnosrotativos.entity.Empleado;
import com.turnosrotativos.entity.JornadaLaboral;
import com.turnosrotativos.exceptions.BusinessException;
import com.turnosrotativos.repository.IJornadaLaboralRepository;
import com.turnosrotativos.util.JornadaLaboralHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class JornadaLaboralValidator {
    @Autowired
    private IJornadaLaboralRepository jornadaLaboralRepository;

    @Autowired
    private JornadaLaboralHelperService jornadaLaboralHelperService;

    public void validarJornadaLaboral(JornadaLaboralDTO jornadaDTO) {
        Empleado empleado = jornadaLaboralHelperService.getEmpleadoById(jornadaDTO.getIdEmpleado());
        ConceptoLaboral concepto = jornadaLaboralHelperService.getConceptoById(jornadaDTO.getIdConcepto());

        validarCamposObligatorios(jornadaDTO, concepto);
        validarRangoHorasTrabajadas(jornadaDTO, concepto);
        validarMaxHorasDiariasTrabajadas(jornadaDTO, empleado, concepto);
        validarMaxHorasSemanales(jornadaDTO, empleado);
        validarMaxHorasMensuales(jornadaDTO, empleado);
        validarDiaLibre(jornadaDTO, empleado, concepto);
        validarTurnosSemanales(jornadaDTO, empleado, concepto);
        validarMaxDiasLibresMensuales(jornadaDTO, empleado);
        validarMaxEmpleadosPorConceptoDiario(jornadaDTO, concepto);
    }

    private void validarCamposObligatorios(JornadaLaboralDTO jornadaDTO, ConceptoLaboral concepto) {
        if ((concepto.getNombre().equals("Turno Normal") || concepto.getNombre().equals("Turno Extra"))
                && jornadaDTO.getHorasTrabajadas() == null) {
            throw new BusinessException("'hsTrabajadas' es obligatorio para el concepto ingresado.", HttpStatus.BAD_REQUEST);
        }

        if (concepto.getNombre().equals("Día Libre") && jornadaDTO.getHorasTrabajadas() != null) {
            throw new BusinessException("El concepto ingresado no requiere el ingreso de 'hsTrabajadas'.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validarRangoHorasTrabajadas(JornadaLaboralDTO jornadaDTO, ConceptoLaboral concepto) {
        if ((concepto.getNombre().equals("Turno Normal") || concepto.getNombre().equals("Turno Extra"))
                && (jornadaDTO.getHorasTrabajadas() < concepto.getHsMinimo()
                || jornadaDTO.getHorasTrabajadas() > concepto.getHsMaximo())) {

            throw new BusinessException("El rango de horas que se puede cargar para este concepto es de " + concepto.getHsMinimo()
                    + " - " + concepto.getHsMaximo(), HttpStatus.BAD_REQUEST);
        }
    }

    private void validarMaxHorasDiariasTrabajadas(JornadaLaboralDTO jornadaDTO, Empleado empleado, ConceptoLaboral concepto) {
        List<JornadaLaboral> jornadasDelDia = jornadaLaboralRepository.findByEmpleadoIdAndFecha(empleado.getId(), jornadaDTO.getFecha());

        // Verificar si ya existe una jornada con el mismo concepto en el mismo día.
        for (JornadaLaboral jornada : jornadasDelDia) {
            if (jornada.getConcepto().getNombre().equals(concepto.getNombre())) {
                throw new BusinessException("El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.", HttpStatus.BAD_REQUEST);
            }
        }

        // Calcular el total de horas trabajadas en el día
        int totalHorasDia = 0;
        for (JornadaLaboral jornada : jornadasDelDia) {
            if (jornada.getHorasTrabajadas() != null) {
                totalHorasDia += jornada.getHorasTrabajadas();
            }
        }

        if (jornadaDTO.getHorasTrabajadas() != null) {
            totalHorasDia += jornadaDTO.getHorasTrabajadas();
        }

        if (totalHorasDia > 14) {
            throw new BusinessException("Un empleado no puede cargar más de 14 horas trabajadas en un día.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validarMaxHorasSemanales(JornadaLaboralDTO jornadaDTO, Empleado empleado) {
        // Determinar el primer día de la semana para la fecha dada.
        LocalDate fecha = jornadaDTO.getFecha();
        LocalDate inicioDeLaSemana = fecha.with(DayOfWeek.MONDAY);
        LocalDate finDeLaSemana = inicioDeLaSemana.plusDays(6);

        // Obtener las jornadas del empleado en la semana especificada.
        List<JornadaLaboral> jornadasDeLaSemana = jornadaLaboralRepository.findByEmpleadoIdAndFechaBetween(empleado.getId(), inicioDeLaSemana, finDeLaSemana);

        // Calcular el total de horas trabajadas en la semana.
        int totalHorasSemana = 0;
        for (JornadaLaboral jornada : jornadasDeLaSemana) {
            if (jornada.getHorasTrabajadas() != null) {
                totalHorasSemana += jornada.getHorasTrabajadas();
            }
        }

        // Incluir las horas de la jornada actual.
        if (jornadaDTO.getHorasTrabajadas() != null) {
            totalHorasSemana += jornadaDTO.getHorasTrabajadas();
        }

        // Verificar si el total supera el límite permitido.
        if (totalHorasSemana > 52) {
            throw new BusinessException("El empleado ingresado supera las 52 horas semanales.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validarMaxHorasMensuales(JornadaLaboralDTO jornadaDTO, Empleado empleado) {
        // Determinar el primer y último día del mes para la fecha dada.
        LocalDate fecha = jornadaDTO.getFecha();
        LocalDate inicioDelMes = fecha.withDayOfMonth(1);
        LocalDate finDelMes = inicioDelMes.plusMonths(1).minusDays(1);

        // Obtener las jornadas del empleado en el mes especificado.
        List<JornadaLaboral> jornadasDelMes = jornadaLaboralRepository.findByEmpleadoIdAndFechaBetween(empleado.getId(), inicioDelMes, finDelMes );

        // Calcular el total de horas trabajadas en el mes usando un bucle.
        int totalHorasMes = 0;
        for (JornadaLaboral jornada : jornadasDelMes) {
            if (jornada.getHorasTrabajadas() != null) {
                totalHorasMes += jornada.getHorasTrabajadas();
            }
        }

        // Incluir las horas de la jornada actual.
        if (jornadaDTO.getHorasTrabajadas() != null) {
            totalHorasMes += jornadaDTO.getHorasTrabajadas();
        }

        // Verificar si el total supera el límite permitido.
        if (totalHorasMes > 190) {
            throw new BusinessException("El empleado ingresado supera las 190 horas mensuales.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validarDiaLibre(JornadaLaboralDTO jornadaDTO, Empleado empleado, ConceptoLaboral conceptoLaboral) {
        // Consultar si ya existe un registro con el concepto "Día Libre" en la misma fecha.
        boolean existeDiaLibre = jornadaLaboralRepository.existsByFechaAndConceptoNombreAndEmpleadoId(
                jornadaDTO.getFecha(),
                "Día Libre",
                empleado.getId());

        boolean existeTurnoNormal = jornadaLaboralRepository.existsByFechaAndConceptoNombreAndEmpleadoId(
                jornadaDTO.getFecha(),
                "Turno Normal",
                empleado.getId());

        boolean existeTurnoExtra = jornadaLaboralRepository.existsByFechaAndConceptoNombreAndEmpleadoId(
                jornadaDTO.getFecha(),
                "Turno Extra",
                empleado.getId());

        if (existeDiaLibre) {
            throw new BusinessException("El empleado ingresado cuenta con un día libre en esa fecha.", HttpStatus.BAD_REQUEST);
        } else if (Objects.equals(conceptoLaboral.getNombre(), "Día Libre") && (existeTurnoNormal || existeTurnoExtra)) {
            throw new BusinessException("El empleado ya cuenta con un turno asignado en esta fecha.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validarTurnosSemanales(JornadaLaboralDTO jornadaDTO, Empleado empleado, ConceptoLaboral concepto) {
        // Obtener el inicio de la semana (lunes) y el fin de la semana (domingo) para la fecha proporcionada.
        LocalDate fecha = jornadaDTO.getFecha();
        LocalDate inicioDeLaSemana = fecha.with(java.time.DayOfWeek.MONDAY);
        LocalDate finDeLaSemana = inicioDeLaSemana.plusDays(6);

        List<JornadaLaboral> jornadasDeLaSemana = jornadaLaboralRepository.findByEmpleadoIdAndFechaBetween(empleado.getId(), inicioDeLaSemana, finDeLaSemana);

        // Validar el tipo de turno actual y actualizar el conteo.
        if (concepto.getNombre().equals("Turno Normal")) {
            // Contar el número de turnos normales en la semana.
            long turnosNormalesSemana = jornadasDeLaSemana.stream()
                    .filter(jornada -> jornada.getConcepto().getNombre().equals("Turno Normal"))
                    .count();

            // Verificar si el número total de turnos normales supera el límite permitido.
            if (turnosNormalesSemana > 4) {
                throw new BusinessException("El empleado ingresado ya cuenta con 5 turnos normales esta semana.", HttpStatus.BAD_REQUEST);
            }
        } else if (concepto.getNombre().equals("Turno Extra")) {
            // Contar el número de turnos extra en la semana.
            long turnosExtrasSemana = jornadasDeLaSemana.stream()
                    .filter(jornada -> jornada.getConcepto().getNombre().equals("Turno Extra"))
                    .count();

            // Verificar si el número total de turnos extra supera el límite permitido.
            if (turnosExtrasSemana > 2) {
                throw new BusinessException("El empleado ingresado ya cuenta con 3 turnos extra esta semana.", HttpStatus.BAD_REQUEST);
            }
        } else if (concepto.getNombre().equals("Día Libre")) {
            // Contar el número de días libres en la semana.
            long diasLibresSemana = jornadasDeLaSemana.stream()
                    .filter(jornada -> jornada.getConcepto().getNombre().equals("Día Libre"))
                    .count();

            // Verificar si el número total de días libres supera el límite permitido.
            if (diasLibresSemana > 1) {
                throw new BusinessException("El empleado no cuenta con más días libres esta semana.", HttpStatus.BAD_REQUEST);
            }
        }
    }

    private void validarMaxDiasLibresMensuales(JornadaLaboralDTO jornadaDTO, Empleado empleado) {
        // Determinar el primer y último día del mes para la fecha dada
        LocalDate fecha = jornadaDTO.getFecha();
        LocalDate inicioDelMes = fecha.withDayOfMonth(1);
        LocalDate finDelMes = inicioDelMes.plusMonths(1).minusDays(1);

        // Contar los días libres del empleado en el mes especificado
        long diasLibresDelMes = jornadaLaboralRepository
                .findByEmpleadoIdAndFechaBetween(empleado.getId(), inicioDelMes, finDelMes)
                .stream().filter(jornada -> "Día Libre".equals(jornada.getConcepto().getNombre())).count();

        // Verificar si el total de días libres supera el límite permitido
        if (diasLibresDelMes > 4) {
            throw new BusinessException("El empleado no cuenta con más días libres este mes.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validarMaxEmpleadosPorConceptoDiario(JornadaLaboralDTO jornadaDTO, ConceptoLaboral concepto) {
        long empleadosPorConcepto = jornadaLaboralRepository.countByFechaAndConceptoId(jornadaDTO.getFecha(), concepto.getId());

        if (empleadosPorConcepto > 1) {
            throw new BusinessException("Ya existen 2 empleados registrados para este concepto en la fecha ingresada.", HttpStatus.BAD_REQUEST);
        }
    }

    public void validarParametros(LocalDate fechaDesde, LocalDate fechaHasta) {
        if (fechaDesde != null && fechaHasta != null && fechaDesde.isAfter(fechaHasta)) {
            throw new BusinessException("El campo ‘fechaDesde’ no puede ser mayor que ‘fechaHasta’.", HttpStatus.BAD_REQUEST);
        }
    }
}
